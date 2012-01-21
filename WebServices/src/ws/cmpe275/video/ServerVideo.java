package ws.cmpe275.video;

import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


import org.example.www.video.GetVideo;
import org.example.www.video.GetVideoResponse;
import org.example.www.video.Message;
import org.example.www.video.ParallelInfo;
import org.example.www.video.ParallelInfoResponse;
import org.example.www.video.PassInfo;
import org.example.www.video.PassInfoResponse;
import org.example.www.video.SearchVideo;
import org.example.www.video.SearchVideoResponse;

import ws.cmpe275.db.bean.LookupData;
import ws.cmpe275.db.bean.MetaData;

public class ServerVideo implements VideoSkeletonInterface{
	public static final String ServiceName = "VideoServer";
	@Override
	public GetVideoResponse getVideo(GetVideo getVideo) {
		DataSource ds = new FileDataSource(getVideo.getVideoPath());
		DataHandler hdr = new DataHandler(ds);
		GetVideoResponse gdr = new GetVideoResponse(); 
		gdr.setMessages(hdr);
		return gdr;
	}

	@Override
	public SearchVideoResponse searchVideo(SearchVideo sd) {
		ArrayList<MetaData> al;
		
		System.out.println("STARTING mthread_doc**********");
		mthread_video m = new mthread_video(sd);
		try {
			m.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//SearchVideoResponse sdr = m.sendResult();
		SearchVideoResponse sdr = new SearchVideoResponse();
		
		return sdr;
	}

	@Override
	public PassInfoResponse passInfo(PassInfo passInfo) {
		System.out.println("video passInfo called.....");
		System.out.println("query = "+passInfo.getQueryName());
		System.out.println("query = "+passInfo.getOrgServer());

	/*	Message[] msg = passInfo.getMessage();
		
		for(Message c : msg){
			System.out.println("video "+"author= "+c.getAuthor()+" "+c.getIpath()+" "+c.getLat()+" "+c.getLon()+" "+c.getType());
		}*/
		
		if(ServiceName.equals(passInfo.getOrgServer()))
		{
			System.out.println("This is the originating server replay back to the user");
		}
		
		System.out.println("STARTING mthread_video**********");
		mthread_video m = new mthread_video(passInfo);
		
		try {
			m.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Successfully finished Video Server operation");	
		PassInfoResponse pir = new PassInfoResponse();
		pir.setResult(m.str);
		return pir;
	}

	@Override
	public ParallelInfoResponse parallelInfo(ParallelInfo pi) {
		String qry_str;
		ArrayList<MetaData> al;
		boolean data_found = true;
		Message[] list = null;
		
		//Create query string from the user information
		qry_str = FormQuery(pi);
		System.out.println("***image query string="+qry_str);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(qry_str,"hibernate_video.cfg.xml");
		System.out.println("#####((((( size = "+al.size());
		
		//calculate total message in the Message
		int i = 0;
		for(MetaData c : al){
			i++;
		}
		int total_message = i;
		System.out.println("****total message= "+total_message);
		if(total_message == 0)
			data_found = false;
		
		if(data_found)
			list = new Message[total_message];
		
		i = 0;
		for (MetaData c : al)   
		{
			list[i] = new Message();
			list[i].setResult("YES");
			list[i].setAuthor(c.getAuthor());			
			list[i].setStime(c.getItime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
			
			i++;
		}
			
		ParallelInfoResponse pir = new ParallelInfoResponse();
		pir.setMessage(list);
		
		return pir;
	
		
	}
	//Used to create query string from the user information
	public String FormQuery(ParallelInfo se){
		
		String qstr = "where ";
		String [] arr = new String[6];
		arr[0] = se.getAuthor();
		arr[1] = se.getStime();
		arr[2] = se.getLat();
		arr[3] = se.getLon();
		arr[4] = se.getTags();
		arr[5] = se.getType();
						
		if(!arr[0].equals("-")){
			qstr += " author = " +"'"+arr[0]+"'";
		}
		if(!arr[1].equals("-")){
			if(arr[0].equals("-"))
				qstr += "itime = "+"'"+arr[1]+"'";
				else
					qstr += " AND "+"itime = "+"'"+arr[1]+"'";
		}
		
		if(!arr[2].equals("-")){
			if(arr[0].equals("-") && arr[1].equals("-"))
				qstr += "lat = "+"'"+arr[2]+"'";
				else
					qstr += " AND "+"lat = "+"'"+arr[2]+"'";
		}
		
		if(!arr[3].equals("-")){
			if(arr[0].equals("-") && arr[1].equals("-")&& arr[2].equals("-"))
				qstr += "lon = "+"'"+arr[3]+"'";
				else
					qstr += " AND "+"lon = "+"'"+arr[3]+"'";
		}
		
		if(!arr[4].equals("-")){
			
			if(arr[0].equals("-") && arr[1].equals("-")&& arr[2].equals("-") && arr[3].equals("-"))
				qstr += "tags = "+"'"+arr[4]+"'";
				else
					qstr += " AND "+"tags = "+"'"+arr[4]+"'";
		}
		
		if(!arr[5].equals("-"))
		{
			if(arr[0].equals("-") && arr[1].equals("-")&& arr[2].equals("-") && arr[3].equals("-") && arr[4].equals("-"))
			qstr += "type = "+"'"+arr[5]+"'";
			else
				qstr += " AND "+"type = "+"'"+arr[5]+"'";
		}
		System.out.println("qstr*** :"+ qstr);
		
		return qstr;
	}
}

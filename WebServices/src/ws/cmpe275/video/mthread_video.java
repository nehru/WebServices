package ws.cmpe275.video;

import java.util.ArrayList; 

import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;
import org.example.www.salvideosearcher.Element;
import org.example.www.salvideosearcher.Query;
import org.example.www.salvideosearcher.QueryResponse;
import org.example.www.video.Message;
import org.example.www.video.PassInfo;
import org.example.www.video.SearchVideo;
import org.example.www.video.SearchVideoResponse;

import ws.cmpe275.db.bean.MetaData;


public class mthread_video implements Runnable{
	Thread t;
	String str;
	SearchVideo srloc;
	PassInfo pi;
	public static final String ServiceName = "VideoServer";
	public static final String ENDPOINT_LOOKUP = "http://localhost:8084/axis2/services/Lookup";
	ws.cmpe275.lookup.LookupStub server_lookup, stub_lookup;
	public static final String ENDPOINT_SALVIDEO = "http://localhost:8086/axis2/services/SALVideoSearcher";
	ws.cmpe275.salvideosearcher.SALVideoSearcherStub stub_salvideo;
	
	boolean salvideo_data_found = true;
	boolean current_data_found = true;
	boolean prev_data_found = true;
	int total_prev_msg=0;
	int total_local_msg=0;
	int total_salvideo_msg =0;
	Element[] el;
	
	
	org.example.www.site.Message[] list1;
	Message[] rec;
	
	mthread_video(SearchVideo svd){
		this.srloc = svd;
		t = new Thread(this,"Video Worker Thread");
		System.out.println("video worker Thread "+t);
		t.start();	
	}
	
	mthread_video(PassInfo pi){
		this.pi = pi;
		t = new Thread(this, "Video Worker Thread");
		System.out.println("Video worker Thread "+t);
		t.start(); 	
	}
	
	
	
	
	@Override
	public void run() {
		System.out.println("******** inside run() ");
		//str = search_video_list(this.srloc);
		this.str = search_video_list_pass(this.pi);
	}

//	SearchVideoResponse sendResult(){
	//	return str;
	//}
	
	public String search_video_list_pass(PassInfo pi) {
		String qry_str;
		ArrayList<MetaData> al;
		ArrayList<MetaData> bl;
		
		
		System.out.println("search_video_list********");
		
		//Create query string from the user information
	//	qry_str = FormQuery(sd);
	//************************************************************
		System.out.println("**** query ="+pi.getQueryName());
		String aa = pi.getQueryName();
		String[] pp = aa.split(" ");
		String f1,f2;
		int index=0;
		int k=0;
		for(String s: pp){
			System.out.println("String="+s);
			if(s.equals("author")){
				index = k;
			}
			k++;
		}
		String author_name = pp[index+2];
		String author = author_name.replace("'", "");
		System.out.println("author_name= "+author);
		
		
		try{
			ws.cmpe275.salvideosearcher.SALVideoSearcherStub server = connect();
			Query sh = new Query();
			sh.setAuthor(author);
			sh.setText("-");
			sh.setTags("-");
			sh.setType("-");
			
		    QueryResponse qr = server.query(sh);
			//code = qr.getReturnCode();
			el = qr.getResults();
			int i = 0;
			for(Element e: el){
				System.out.println("author= "+e.getAuthor()+" "+ "tags= "+e.getTags()+" "+"type= "+e.getType()+" "+"path= "+e.getUrl());
				i++;
			}
			total_salvideo_msg = i;
			if(total_salvideo_msg == 0)
					salvideo_data_found = false;
			
			System.out.println("total_salvideo_messages="+total_salvideo_msg);
			 			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		//*************************************************
		//Calling hibernate to query data from third party web service database
/*		ws.cmpe275.db.bean.HibernateData fe_sal = new ws.cmpe275.db.bean.HibernateData();
		bl = fe_sal.ConnectDB(pi.getQueryName(),"hibernate_salvideo.cfg.xml");
		if(bl.size() == 0)
		{
			salvideo_data_found = false;
		}
		
		total_salvideo_msg = bl.size();
		int i = 0;
		Message[] slist = new Message[total_salvideo_msg];
		//Copying local data into the Message list
		if(salvideo_data_found)
		{
			for (MetaData c : bl)   
			{
				slist[i] = new Message();
				slist[i].setResult("YES");
				slist[i].setAuthor(c.getAuthor());			
				slist[i].setStime(c.getItime());
				slist[i].setLat(c.getLat());
				slist[i].setLon(c.getLon());
				slist[i].setTags(c.getTags());
				slist[i].setType(c.getType());
				slist[i].setIpath(c.getIpath());
				
				i++;
			}
			//calculate total message in the Message in the video service
			i = 0;
			for(Message c : slist){
			//	System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				i++;
			}
			this.total_salvideo_msg = i;
			System.out.println("****FROM SALVIDEO total message= "+this.total_salvideo_msg);	
		}*/
	//***********************************************************	
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(pi.getQueryName(),"hibernate_video.cfg.xml");
		//String sresult = al.get(0).getResult();
		
		if(al.size() == 0)
		{
			current_data_found = false;
		}
		
		SearchVideoResponse str = new SearchVideoResponse();
		
		total_local_msg = al.size();
		int i = 0;
		Message[] list = new Message[total_local_msg];
		
		//Copying local data into the Message list
		if(current_data_found)
		{
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
	 
//********************************************************		
			//calculate total message in the Message in the video service
			i = 0;
			for(Message c : list){
			//	System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				i++;
			}
			this.total_local_msg = i;
			System.out.println("****total message= "+this.total_local_msg);	
		}
		
		//checking for previous node message
		if(this.pi.getMessage() == null)
		{
			prev_data_found = false;
		}
		
		if(prev_data_found)
		{
			//reading messages from previous node
			rec = pi.getMessage();
			i = 0;
			for(Message d : rec){
				//System.out.println("Previous message "+"author= "+d.getAuthor()+" "+d.getIpath()+" "+d.getStime().getTime());
				i++;
			}
			
			this.total_prev_msg = i;
		}
		int tcount = this.total_local_msg + this.total_prev_msg+this.total_salvideo_msg;
		System.out.println("video ######### tcount= "+tcount);
		
		System.out.println("prev_data_found= "+prev_data_found);
		System.out.println("current_data_found= "+current_data_found);
		System.out.println("total_salvideo_msg= "+total_salvideo_msg);
		
		
		if(current_data_found || prev_data_found ||salvideo_data_found)
		list1 = new org.example.www.site.Message[tcount];
		
		i=0;
		if(current_data_found)
		{
			for(Message c : list)
			{
				list1[i] = new org.example.www.site.Message();
				list1[i].setResult("YES");
				list1[i].setAuthor(c.getAuthor());			
				list1[i].setStime(c.getStime());
				list1[i].setLat(c.getLat());
				list1[i].setLon(c.getLon());
				list1[i].setTags(c.getTags());
				list1[i].setType(c.getType());
				list1[i].setIpath(c.getIpath());
				
				i++;
			}
		}
		int nt = i;
		
		if(prev_data_found)
		{
			for(Message d : rec)
			{
				list1[nt] = new org.example.www.site.Message();
				list1[nt].setResult("YES");
				list1[nt].setAuthor(d.getAuthor());			
				list1[nt].setStime(d.getStime());
				list1[nt].setLat(d.getLat());
				list1[nt].setLon(d.getLon());
				list1[nt].setTags(d.getTags());
				list1[nt].setType(d.getType());
				list1[nt].setIpath(d.getIpath());
				
				nt++;
			}
		}
		
		i = nt;
		
		if(salvideo_data_found)
		{
			for(Element c : el)
			{
				list1[i] = new org.example.www.site.Message();
				list1[i].setResult("YES");
				list1[i].setAuthor(c.getAuthor());			
				//list1[i].setStime("1-2-3");
				list1[i].setLat("-");
				list1[i].setLon("-");
				list1[i].setTags(c.getTags());
				list1[i].setType(c.getType());
				list1[i].setIpath(c.getUrl());
				
				i++;
			}
		}
		
		
		//display all messages sent to next node
	/*	for(org.example.www.site.Message m : list1){
			System.out.println("VIDEO ALL message "+"author= "+m.getAuthor()+" "+m.getIpath()+" "+m.getStime().getTime());
			i++;
		}*/
		
		//Call lookup service for next node.
		String next_node = GetNextNode(ServiceName);
		System.out.println("+++++ Video THREAD current node = "+ServiceName+"  next node = "+ next_node);
		
		//Send message to next node 
		//ws.cmpe275.video.VideoStub server;
		
		try {
			ws.cmpe275.site.SiteStub server = connect(next_node);
			org.example.www.site.PassInfo pif = new org.example.www.site.PassInfo();
			pif.setMessage(list1);
			pif.setQueryName(pi.getQueryName());
			pif.setOrgServer(pi.getOrgServer());
			
			System.out.println("before sending to other site");
			
			//deb temp
			server.passInfo(pif);
		
			System.out.println("after sending to other site");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//********************************************************		
	  //str.setMessage(list);
		return "YES";		
	}
	
	
	
	public SearchVideoResponse search_video_list(SearchVideo sd) {
		String qry_str;
		ArrayList<MetaData> al;
		
		System.out.println("search_video_list********");
		
		//Create query string from the user information
		qry_str = FormQuery(sd);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(qry_str,"hibernate_video.cfg.xml");
		String sresult = al.get(0).getResult();
		
		SearchVideoResponse str = new SearchVideoResponse();
		
		int count = al.size();
		int i = 0;
		Message[] list = new Message[count];
		
		//No data for the query then "NO" is sent as a result to the client
		if(sresult.equals("NO")){
			list[i] = new Message();
			list[i].setResult(sresult);
		}
		else{	
			//If data is found then all these data's are sent to the client and the result as "YES".
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
	  }
	  str.setMessage(list);
		return str;		
	}
	
	
	
	
	//Used to create query string from the user information
	String FormQuery(SearchVideo se){
		
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
	
	public  String GetNextNode(String saddress)
	{
		String rec = null;
		try {
			ws.cmpe275.lookup.LookupStub server = connect_lookup(ENDPOINT_LOOKUP);
			SearchLookup slu = new SearchLookup();
			slu.setServerName(saddress);
			SearchLookupResponse slr = server.searchLookup(slu);
			rec = slr.getNextURL();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rec;
	}
	
	public synchronized ws.cmpe275.lookup.LookupStub connect_lookup(String ep) throws Exception
	{
		if(stub_lookup == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub_lookup);
			stub_lookup = new ws.cmpe275.lookup.LookupStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub_lookup);
		}
		return stub_lookup;
	}
	
	public synchronized ws.cmpe275.site.SiteStub connect(String ep) throws Exception
	{
		ws.cmpe275.site.SiteStub stub = null;
		if(stub == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub);
			stub = new ws.cmpe275.site.SiteStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub);
		}
		return stub;
	}
	
	private synchronized ws.cmpe275.salvideosearcher.SALVideoSearcherStub connect() throws Exception
	{
		if(stub_salvideo == null)
		{
			stub_salvideo = new ws.cmpe275.salvideosearcher.SALVideoSearcherStub(ENDPOINT_SALVIDEO);
		}
		return stub_salvideo;
	}
	
}

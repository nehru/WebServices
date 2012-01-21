package ws.cmpe275.salvideosearcher;

import java.util.ArrayList;

import org.example.www.salvideosearcher.Element;
import org.example.www.salvideosearcher.Get;
import org.example.www.salvideosearcher.GetResponse;
import org.example.www.salvideosearcher.Query;
import org.example.www.salvideosearcher.QueryResponse;
import org.example.www.video.Message;

import ws.cmpe275.db.bean.DbbElement;
import ws.cmpe275.db.bean.MetaData;
import ws.cmpe275.site.SiteSkeletonInterface;

public class ServerSALVideoSearcher implements SALVideoSearcherSkeletonInterface{
	String qry_str;
	String query;
	ArrayList<MetaData> al;
	int total_salvideo_msg =0;
	boolean salvideo_data_found = true;
	
	@Override
	public GetResponse get(Get get) {
		 
		return null;
	}

	@Override
	public QueryResponse query(Query qy) {
		 System.out.println("SALVideoSearcher Query called");
		 System.out.println("author= "+qy.getAuthor() +" "+"type= "+qy.getType());
		
		//Create query string from the user information
			qry_str = FormQuery(qy);
			this.query = qry_str;
		 
			System.out.println("***SALVideo query string="+qry_str);
			
				 
			//Calling hibernate to query data from mysql database
			ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
			al = fe.ConnectDB(this.query,"hibernate_salvideo.cfg.xml");
			//String sresult = al.get(0).getResult();
			
			if(al.size() == 0)
			{
				salvideo_data_found = false;
			}
			
			total_salvideo_msg = al.size();
			int i = 0;
			Element[] slist = new Element[total_salvideo_msg];
			
			//Copying local data into the Message list
			if(salvideo_data_found)
			{
				for (MetaData c : al)   
				{
					slist[i] = new Element();
					
					
					//slist[i].setResult("YES");
					slist[i].setAuthor(c.getAuthor());			
					//slist[i].setStime(c.getItime());
					//slist[i].setLat(c.getLat());
					//slist[i].setLon(c.getLon());
					slist[i].setTags(c.getTags());
					slist[i].setType(c.getType());
					//slist[i].setIpath(c.getIpath());
					slist[i].setUrl(c.getIpath());
					i++;
				}
				//calculate total message in the Message in the video service
				i = 0;
				for(Element c : slist){
				//	System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
					i++;
				}
				this.total_salvideo_msg = i;
				System.out.println("****FROM SALVIDEO total message= "+this.total_salvideo_msg);	
			}
			
		 
		 
		QueryResponse qr = new QueryResponse();
		qr.setResults(slist);
		qr.setReturnMsg("YES");
		qr.setReturnCode(1);
		return qr;
	}

	//Used to create query string from the user information
	public String FormQuery(Query se){
		
		String qstr = "where ";
		String [] arr = new String[4];
		arr[0] = se.getAuthor();
		arr[1] = se.getText();
		arr[2] = se.getTags();
		arr[3] = se.getType();
		
								
		if(!arr[0].equals("-")){
			qstr += " author = " +"'"+arr[0]+"'";
		}
		if(!arr[1].equals("-")){
			if(arr[0].equals("-"))
				qstr += "text = "+"'"+arr[1]+"'";
				else
					qstr += " AND "+"text = "+"'"+arr[1]+"'";
		}
		
		if(!arr[2].equals("-")){
			if(arr[0].equals("-") && arr[1].equals("-"))
				qstr += "tags = "+"'"+arr[2]+"'";
				else
					qstr += " AND "+"tags = "+"'"+arr[2]+"'";
		}
		
		if(!arr[3].equals("-")){
			if(arr[0].equals("-") && arr[1].equals("-")&& arr[2].equals("-"))
				qstr += "type = "+"'"+arr[3]+"'";
				else
					qstr += " AND "+"type = "+"'"+arr[3]+"'";
		}
		
		
		System.out.println("qstr*** :"+ qstr);
		
		return qstr;
	}
	
}

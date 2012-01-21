package ws.cmpe275.idl;

import java.text.DateFormat;
import java.util.ArrayList;

import org.example.www.demo.Message;
import org.example.www.demo.Search;
import org.example.www.demo.SearchResponse;
import org.example.www.document.PassInfo;
import org.example.www.document.PassInfoResponse;
import org.example.www.document.SearchDocument;

import ws.cmpe275.db.bean.MetaData;

public class mthread implements Runnable{
	Thread t;
	SearchResponse str;
	Search srloc;
	String query;
	Server svr;
	boolean data_found = true;
	String ret;
	boolean jtest = false;
	
	mthread(Server svr) {
		this.svr = svr;
		this.srloc = svr.getSearch();
		// Creating a worker thread
		t = new Thread(this, "Image Worker Thread");
		System.out.println("Image Worker thread: " + t);
		t.start(); // Start the thread
		}
	
	@Override
	public void run() {		
		this.ret = search_list(this.srloc);	
	}

	SearchResponse getSearchResponse(){
		return str;
	}

	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	//public SearchResponse search_list(Search se) {
	public String search_list(Search se) {
		DateFormat formatter; 
		String qry_str;
				
		System.out.println("Search_list*******");
		
		//System.out.println("Author name :"+ se.getAuthor()+" "+se.getStime()+" "+se.getLat()+" "+se.getLon()+" "+se.getTags()+ " "+se.getType());
		ArrayList<MetaData> al;
		
		String type = se.getType();
		if(se.getType().equals("jtest"))
		{
			jtest = true;
			System.out.println("junit test");
		}
		
		
		//Create query string from the user information
		qry_str = FormQuery(se);
		this.query = qry_str;
		
		System.out.println("***image query string="+qry_str);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(qry_str,"hibernate_image.cfg.xml");
		if(al.size() == 0)
			data_found = false;
		
		System.out.println("#####((((( size = "+al.size()+"data_found="+data_found);
		
		if(jtest == true){
			return "YES";
		}
		
		
		//String sresult = al.get(0).getResult();
				
		SearchResponse str = new SearchResponse();
		
		int count = al.size();
		System.out.println("##### count= "+count);
		int i = 0;
		Message[] list = new Message[count];
		
		//No data for the query then "NO" is sent as a result to the client
	//	if(!data_found){
	//		System.out.println("##### No Message found");
		//	list[i] = new Message();
		//	list[i].setResult(sresult);
	//	}
		//else{	
			//If data is found then all these data's are sent to the client and the result as "YES".
		if(data_found){
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
		
		
//***************************************************		
		//Call lookup service for next node.
		String next_node = svr.GetNextNode(svr.getServiceName());
		System.out.println("+++++ THREAD current node = "+svr.getServiceName()+"  next node = "+ next_node);
			
		org.example.www.document.Message[] li=null;
		//Send message to next node 
		ws.cmpe275.document.DocumentStub server;
		
		try {
			server = svr.connect(next_node);
			PassInfo pi = new PassInfo();
			
			if(data_found){
			//calculate total message in the Message
			i = 0;
			for(Message c : list){
				System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				i++;
			}
			int total_message = i;
			System.out.println("****total message= "+total_message);
			
			
			li = new org.example.www.document.Message[total_message];
			i = 0;
			for(Message c : list){
				//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				li[i] = new org.example.www.document.Message();
				li[i].setAuthor(c.getAuthor());
				li[i].setStime(c.getStime());
				li[i].setLat(c.getLat());
				li[i].setLon(c.getLon());
				li[i].setTags(c.getTags());
				li[i].setType(c.getType());
			    li[i].setIpath(c.getIpath());
			    
				i++;
			}
			}
			
			pi.setMessage(li);
			pi.setOrgServer(svr.getServiceName());
			pi.setQueryName(this.query);
			System.out.println("finished Sending to document node");
			
			
			PassInfoResponse pir = server.passInfo(pi);	
			String ret = pir.getResult();
			System.out.println("from document result ="+ret);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
//***************************************************		
	  //return this message to the client
	//  str.setResult(ret);
	//str.setMessage(list);
	  return "YES";  
	}

	//Used to create query string from the user information
	public String FormQuery(Search se){
		
		String qstr = "where ";
		String [] arr = new String[6];
		arr[0] = se.getAuthor();
		arr[1] = se.getStime();
		arr[2] = se.getLat();
		arr[3] = se.getLon();
		arr[4] = se.getTags();
		
		if(jtest == true)
			arr[5] = "-";
		else
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

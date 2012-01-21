package ws.cmpe275.document;

import java.util.ArrayList;

import org.example.www.document.Message;
import org.example.www.document.PassInfo;
import org.example.www.document.SearchDocument;
import org.example.www.document.SearchDocumentResponse;
import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;
 

import ws.cmpe275.db.bean.MetaData;

public class mthread_doc implements Runnable{
	Thread t;
	public static final String ServiceName = "DocumentServer";
	public static final String ENDPOINT_LOOKUP = "http://localhost:8084/axis2/services/Lookup";
	ws.cmpe275.lookup.LookupStub server_lookup, stub_lookup;
	 
	String str;
	SearchDocument srloc;
	ServerDoc sdoc;
	boolean current_data_found = true;
	boolean prev_data_found = true;
	org.example.www.video.Message[] list1;
	PassInfo pi;
	int total_prev_msg=0;
	int total_local_msg=0;
	Message[] rec;
	Message[] list;
	
	mthread_doc(SearchDocument sd){
		this.srloc = sd;
		t = new Thread(this, "Document Worker Thread");
		System.out.println("Document worker Thread "+t);
		t.start();
	}
	
	mthread_doc(PassInfo pi){
		this.pi = pi;
		t = new Thread(this, "Document Worker Thread");
		System.out.println("Document worker Thread "+t);
		t.start();
	}
	
	
		
	@Override
	public void run() {
		System.out.println("******** inside run() ");
			//str = search_doc_list(this.srloc);
			this.str = search_doc_list_pass(this.pi);
	}
 

	//SearchDocumentResponse sendResult(){
		//return str;
//	}
	
	
	public String search_doc_list_pass(PassInfo pi) {
		String qry_str;
		ArrayList<MetaData> al;
		
		
		System.out.println("search_doc_list********");
		
		
		//Create query string from the user information
	//	qry_str = FormQuery(sd);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		
		System.out.println("doc query="+pi.getQueryName());
		
		al = fe.ConnectDB(pi.getQueryName(),"hibernate_doc.cfg.xml");
		//String sresult = al.get(0).getResult();
		
		if(al.size() == 0)
		{
			current_data_found = false;
		}
		
		SearchDocumentResponse str = new SearchDocumentResponse();
		
		System.out.println("*************** current_data_found "+ current_data_found);	
		
		int i = 0;
		
		
		//Copying local data into the Message list
		if(current_data_found)
		{
			this.total_local_msg = al.size();
			System.out.println("####total_local_msg = "+this.total_local_msg);
			list = new Message[this.total_local_msg];
			
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
	//  }
		
			System.out.println("*************** debug document searched ");	
		
			//***************************************************
			//calculate total message in the Message
	/*		i = 0;
			for(Message c : list){
				System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				i++;
			}
			total_local_msg = i;
			System.out.println("****total message= "+total_local_msg);
		*/
		
		}
	
		System.out.println("****outside total message= "+this.total_local_msg);
		
		
	//checking for previous node message
	if(this.pi.getMessage() == null)
	{
		prev_data_found = false;
	}
	
	System.out.println("****total prev_data_found = "+ prev_data_found);	
	
	//copying previous node messages
	if(prev_data_found)
	{	
		System.out.println("debug ########### rec = "+this.pi.getMessage());
		//reading messages from previous node
		rec = this.pi.getMessage();
		//System.out.println("########### rec = "+rec);
		
		//calculate total message from the previous node
		i = 0;
		for(Message d : rec){
			//System.out.println("Previous message "+"author= "+d.getAuthor()+" "+d.getIpath()+" "+d.getStime().getTime());
			i++;
		}
		
		this.total_prev_msg = i;
	}
		int tcount = total_local_msg + total_prev_msg;
		
		System.out.println("total_local_msg= "+this.total_local_msg+" "+"total_prev_msg= "+this.total_prev_msg);
		System.out.println("tcount= "+tcount);
		
		if(current_data_found || prev_data_found)
		{
			list1 = new org.example.www.video.Message[tcount];
			System.out.println("list1 created....");
		}
	if(current_data_found){
		i=0;
		for(Message c : list){
			list1[i] = new org.example.www.video.Message();
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
	
	if(prev_data_found)
	{
		for(Message d : rec)
		{
			list1[i] = new org.example.www.video.Message();
			list1[i].setResult("YES");
			list1[i].setAuthor(d.getAuthor());			
			list1[i].setStime(d.getStime());
			list1[i].setLat(d.getLat());
			list1[i].setLon(d.getLon());
			list1[i].setTags(d.getTags());
			list1[i].setType(d.getType());
			list1[i].setIpath(d.getIpath());
			
			i++;
		}
		//display all messages sent to next node
	/*	for(org.example.www.video.Message m : list1){
			System.out.println("ALL message "+"author= "+m.getAuthor()+" "+m.getIpath()+" "+m.getStime().getTime());
			i++;
		}*/
		
	}
	
	//} //Not to send any message to other node
	
	//Call lookup service for next node.
	String next_node = GetNextNode(ServiceName);
	System.out.println("+++++ DOCUMENT THREAD current node = "+ServiceName+"  next node = "+ next_node);
	
	
	//Send message to next node 
	//ws.cmpe275.video.VideoStub server;
	try {
		ws.cmpe275.video.VideoStub server = connect(next_node);
		org.example.www.video.PassInfo pif = new org.example.www.video.PassInfo();
		
		pif.setMessage(list1);
			
		pif.setQueryName(pi.getQueryName());
		pif.setOrgServer(pi.getOrgServer());
	
		//deb temp
		server.passInfo(pif);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	//***************************************************
	
	  //str.setMessage(list);
		return "YES";
		
	}
	
	
	
	public SearchDocumentResponse search_doc_list(SearchDocument sd) {
		String qry_str;
		ArrayList<MetaData> al;
		
		System.out.println("search_doc_list********");
		
		
		//Create query string from the user information
		qry_str = FormQuery(sd);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(qry_str,"hibernate_doc.cfg.xml");
		String sresult = al.get(0).getResult();
		
		SearchDocumentResponse str = new SearchDocumentResponse();
		
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
	String FormQuery(SearchDocument se){
		
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
	
	public synchronized ws.cmpe275.video.VideoStub connect(String ep) throws Exception
	{
		ws.cmpe275.video.VideoStub stub = null;
		if(stub == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub);
			stub = new ws.cmpe275.video.VideoStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub);
		}
		return stub;
	}
	
}

package ws.cmpe275.idl;


import ws.cmpe275.db.bean.*; 
import ws.cmpe275.document.DocumentStub;
import ws.cmpe275.document.ServerDoc;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;

import org.example.www.demo.GetItem;
import org.example.www.demo.GetItemResponse;
import org.example.www.demo.Message;
import org.example.www.demo.ParallelInfo;
import org.example.www.demo.ParallelInfoResponse;
import org.example.www.demo.PassInfoResponse;
import org.example.www.demo.RegisterImage;
import org.example.www.demo.RegisterImageResponse;
import org.example.www.demo.Search;
import org.example.www.demo.SearchResponse;
import org.example.www.document.LocalDocQuery;
import org.example.www.document.LocalDocQueryResponse;
import org.example.www.document.PassInfo;
import org.example.www.document.SearchDocument;
import org.example.www.document.SearchDocumentResponse;
import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;

import ws.cmpe275.global.Param;
import ws.cmpe275.idl.SearchSkeletonInterface;
import ws.cmpe275.lookup.LookupStub;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


public class Server implements SearchSkeletonInterface{
	//static final String Lab2Dir = "D:\\work7\\Lab2-36\\";
	
	 public static final String ENDPOINT = "http://localhost:8081/axis2/services/Document";
	 public static final String ENDPOINT_LOOKUP = "http://localhost:8084/axis2/services/Lookup";
	 
	 public static final String MY_ADDRESS = "http://localhost:8080/axis2/services/Search";
	 
	 public static final String ServiceName = "ImageServer";
	 private String rec;
	 private String endPoint; 
	 ws.cmpe275.document.DocumentStub server;
	 ws.cmpe275.document.DocumentStub stub;
	 ws.cmpe275.lookup.LookupStub server_lookup, stub_lookup;
	 private Search search;
	 private Message[] received_msg;
	 SearchResponse resp;
	 
	@Override
	public SearchResponse search(Search se) {
		this.search = se;
		DateFormat formatter; 
		String qstr = "where ";
		//System.out.println("Author name :"+ se.getAuthor()+" "+se.getStime()+" "+se.getLat()+" "+se.getLon()+" "+se.getTags()+ " "+se.getType());
		ArrayList<MetaData> al;
		
		System.out.println("STARTING mthread**********");
		mthread m = new mthread(this);
		try {
			m.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("FINISHED mthread**********");
		
		//read data from local storage
		SearchResponse resp= new SearchResponse();
		resp.setResult(m.ret);
		
		//rec = str.getMessage();
		//str.setResult(str.getResult());
		//rec = str.getResult();
		//send result to client
		//str= m.sendResult();
		
		//Sent to client or to last node
		return resp;
	}


	@Override
	public GetItemResponse getItem(GetItem getItem) {


		DataSource ds = new FileDataSource(getItem.getItem());
		DataHandler hdr = new DataHandler(ds);
		
		GetItemResponse res = new GetItemResponse();
		res.setMessages(hdr);
		
		return res;
	}

	public synchronized ws.cmpe275.document.DocumentStub connect(String ep) throws Exception
	{
		if(stub == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub);
			stub = new ws.cmpe275.document.DocumentStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub);
		}
		return stub;
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
	
	
	@Override
	public RegisterImageResponse registerImage(RegisterImage registerImage) {
		
		System.out.println("Image Service registering with Lookup Service");
		System.out.println("***** From client ="+registerImage.getMessage());
		
		RegisterImageResponse rir = new RegisterImageResponse();
		rir.setResult("YES");
		return rir;
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
	
 public String FormQuery(Search se){
		
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


public Search getSearch() {
	return search;
}


public void setSearch(Search search) {
	this.search = search;
}


public static String getServiceName() {
	return ServiceName;
}


@Override
public PassInfoResponse passInfo(org.example.www.demo.PassInfo passInfo) {
	System.out.println("Image passInfo called*******");
	System.out.println("query = "+passInfo.getQueryName());
	System.out.println("query = "+passInfo.getOrgServer());
	Message[] msg = passInfo.getMessage();
	
	if(msg == null){
		System.out.println("****** NO message search query found in the ring");
	}
	
	FileOutputStream fd = null;
	PrintWriter wr = null;
	
	
	this.received_msg = msg;
	
/*	for(Message c : msg){
		System.out.println("author= "+c.getAuthor()+" "+c.getIpath()+" "+c.getLat()+" "+c.getLon()+" "+c.getType());
	}*/
	
	if(ServiceName.equals(passInfo.getOrgServer())  && (msg != null))
	{
		System.out.println("This is the originating server replay back to the user");
//	}
	
	//******************************************************
	StringBuilder br = new StringBuilder();
	
	for(Message c : msg){
		System.out.println("author= "+c.getAuthor()+" "+c.getIpath()+" "+c.getLat()+" "+c.getLon()+" "+c.getType());
		//br.append(c.getAuthor()).append(",").append(c.getStime().getTime()).append(",").append(c.getLat()).append(",").append(c.getLon()).append(",").append(c.getTags()).append(",").append(c.getType()).append(",").append(c.getIpath()).append(System.getProperty("line.separator"));
		br.append(c.getAuthor()).append(",").append(c.getTags()).append(",").append(c.getType()).append(",").append(c.getIpath()).append(System.getProperty("line.separator"));
	}
	try {
		fd = new FileOutputStream(new File(passInfo.getOrgServer() + ".txt"));
		wr = new PrintWriter(fd);
		wr.write(br.toString());
		wr.flush();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	//copy file to the FileReceived Folder in the project folder
	File FromFile = new File("C:/t1/bin/"+passInfo.getOrgServer()+".txt");
	File ToFile = new File(Param.Lab2Dir+"/FileReceived/"+passInfo.getOrgServer()+".txt");
	FileInputStream  Source = null;
	FileOutputStream  Destination = null;
	byte[] buf = new byte[4096];
	int bread;
	
	 try {
		 Source = new FileInputStream(FromFile);
		 Destination = new FileOutputStream(ToFile);
		
		 while((bread = Source.read(buf)) != -1)
		 Destination.write(buf, 0, bread);
		System.out.println(passInfo.getOrgServer()+".txt copied");
	 }
	 catch (IOException e) {
	
		e.printStackTrace();
	 }
	 
	 	finally
		{
	 		if (Source != null)
	 		{
	 			try
	 			{
	 				Source.close();
	 			}
	 			catch (IOException e)
	 			{
	 				System.err.println("Error is " + e.getMessage());
	 			}
	 		}
	 		if(Destination != null)
	 		{
	 			try{
	 				Destination.close();
	 			}
	 			catch(IOException e)
	 			{
	 				System.err.println("Error is " + e.getMessage());
	 			}
	 		}
	 		
	 		
		}
	}
	//******************************************************
	PassInfoResponse pir = new PassInfoResponse();
	
	if(msg != null)
		pir.setResult("YES");
	else
		pir.setResult("NO");
	
	return pir;
	}


@Override
public ParallelInfoResponse parallelInfo(ParallelInfo pi) {
	String qry_str;
	ArrayList<MetaData> al;
	boolean data_found = true;
	Message[] list = null;
	//org.example.www.document.Message[] li=null;
	//Message[] msg = new Message[1];
	//msg[0] = new Message();
	//msg[0].setAuthor("Peter");
	
	System.out.println("ParallenInfo called");
//	System.out.println("author= "+pi.getAuthor());
	
	//Create query string from the user information
	qry_str = FormQuery(pi);
	System.out.println("***image query string="+qry_str);
	
	//Calling hibernate to query data from mysql database
	ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
	al = fe.ConnectDB(qry_str,"hibernate_image.cfg.xml");
	
	
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
	
	
/*	Message[] li=null;
	Message[] msg = new Message[1];
	msg[0] = new Message();
	msg[0].setAuthor("Peter");
	pir.setMessage(msg);
	*/
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

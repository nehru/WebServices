package ws.demo.client;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import javax.activation.DataHandler;

 
import org.example.www.demo.GetItem;
import org.example.www.demo.GetItemResponse;
import org.example.www.demo.Message;
import org.example.www.demo.Search;
import org.example.www.demo.SearchResponse;

import ws.cmpe275.global.Param;

public class Client {

	
	 public static final String ENDPOINT = "http://localhost:8080/axis2/services/Search";
	 private String endPoint; 
	 private ws.cmpe275.idl.SearchStub stub;
	 Param pa;
	
	 
	 public Client()
	 {
		 this(ENDPOINT);
		 pa = new Param();
		 
	 }
		
	public Client(String endpoint2) {
		endPoint = endpoint2;
	}

	private synchronized ws.cmpe275.idl.SearchStub connect() throws Exception
	{
		if(stub == null)
		{
			stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
		}
		return stub;
	}

		
	public String queryData(String auth, String st, String lat, String lon, String tags, String type)
	{
		String rec = null;
		
		if(((!lat.equals("-")) && (lon.equals("-"))) || ((lat.equals("-")) &&(!lon.equals("-")))){
			System.out.println("Please enter both latitude and longitude");
		
			//Message[] mm = new Message[1];
			//mm[0] = new Message();
			//mm[0].setResult("NO-VALID-DATA");
			rec = "NO";
			return rec;
		}
		
		
		try{
			ws.cmpe275.idl.SearchStub server = connect();
			Search sh = new Search();
			sh.setAuthor(auth);
			sh.setStime(st);
			sh.setLat(lat);
			sh.setLon(lon);
			sh.setTags(tags);
			sh.setType(type);
			
			
			
			SearchResponse se = server.search(sh);
			//rec = se.getMessage();
			rec = se.getResult();
			System.out.println("from Search result="+rec);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("end of query program");
		return rec;
	}

//******************************************	
	public void acquireItem(String path)
	{
		String lfile = path;
		try{
			ws.cmpe275.idl.SearchStub server = connect();
			GetItem gi = new GetItem();
			gi.setItem(path);
			
			GetItemResponse gr = server.getItem(gi);
			System.out.println("File received from server");
			DataHandler ha = gr.getMessages();
			
			String pa = lfile;
			System.out.println("pa = "+pa);
			String[] st = pa.split("\\\\");
			
			for(int i=0;i<st.length;i++)
			{
				System.out.println(st[i]);
			}
			
			int cnt = st.length;
			System.out.println("cnt = "+cnt);
			String fname = st[cnt-1];
						
			 			
			String opath = Param.Lab2Dir + "\\FileReceived\\"+fname;
			
			System.out.println("File stored in "+opath);
						
			FileOutputStream out = new FileOutputStream(opath);
			ha.writeTo(out);
			out.flush();
			out.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
//******************************************	
	public static void main(String[] args) {
		Message[] qauthor,qdate,qloc,qtag,qtype;	
		int i;
		String resul;
		String ret;
		try{
		Client prg = new Client();
				
		System.out.println("Client program calling web service");
		
		//********************************************************************************
		System.out.println("##### Query based on author #####");
		
		//queryData(String auth, String st, String lat, String lon, String tags, String type)
		//qauthor = prg.queryData("Manson","-","-","-","-","-");
		ret = prg.queryData("Ashok","-","-","-","-","-");
		
		
		if(ret.equals("YES"))
		{
			System.out.println("Successfully data sent to next node");
		}
		
	//	resul = qauthor[0].getResult();
	//	System.out.println("resul= "+resul);
		//*************************************************
		 
		File fd = new File(Param.Lab2Dir + "/FileReceived/ImageServer.txt");
		
		System.out.println("Waiting for search data from different web services");
		
		boolean fileFound = false;
		int x = 0;
		while(!fileFound)
		{
			x++;
			fileFound = fd.exists();
			//System.out.println("File Not found!!!"); 
			Thread.sleep(1000);
			
			if(x==10)
			{
				System.out.println("File Not found");
				return;
			}
		}
		System.out.println("##### File found #####"); 
		
		File file = new File(Param.Lab2Dir+"/FileReceived/ImageServer.txt");
        
		StringBuffer cts = new StringBuffer();
        BufferedReader brdr = null;
		
        brdr = new BufferedReader(new FileReader(file));
        String text = null;
        i=0;
        int k = 1;
        String[] str = new String[100];
        String[] dd = null;
        int line = 0;
        try{
        	while ((text = brdr.readLine()) != null)
        	{
        		//  contents.append(text).append(System.getProperty("line.separator"));
        		
        		line++; 
        		System.out.println(line +" "+ text);
        		if(k == 1){
        			dd = text.split(",");
        			//System.out.println(i+"\t"+dd[0]+"\t"+dd[1]+"\t"+dd[2]+"\t"+dd[3]+"\t"+dd[4]+"\t  "+dd[6]);
	        		str[i] = dd[3];
	        		i++;
        			        			
        			k=0;
        		}
        		
        		//	dd = text.split(",");
        		
        	/*	if(dd[2].equals("-") && dd[3].equals("-")){
        			System.out.println(text);
        		}
        		else
        		{
	        		System.out.println(i+"\t"+dd[0]+"\t"+dd[1]+"\t"+dd[2]+"\t"+dd[3]+"\t"+dd[4]+"\t  "+dd[6]);
	        		str[i] = dd[6];
	        		i++;
        		}*/
        		
        		
        		
        	}
        }
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
        finally
        {
        	try
        	{
        		if(brdr != null)
        			brdr.close();
        	}
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
        
    /*    for(String e: str){
        	System.out.println("string= "+e);
        }*/
        
       
       prg.acquireItem(str[0]);
      //  prg.acquireItem(str[6]);
      //  prg.acquireItem(str[4]);
       // prg.acquireItem(str[5]);
       // prg.acquireItem(str[4]);	
		
		//********************************************************************************
/*		System.out.println("##### Query based on DataTime #####");
		
		qdate = prg.queryData("-","2005-05-30 11:23:52","-","-","-","-");
		resul = qdate[0].getResult();
		
		if(resul.equals("NO")){
			System.out.println("****NO Search Data Found*****");
		}
		else{
		
			i = 1;
			for(Message m : qdate){
				System.out.println(i+" Resource "+m.getAuthor()+" "+m.getStime().getTime()+" "+m.getLat()+" "+m.getLon()+" "+m.getTags()+" "+m.getType()+" "+m.getIpath());
				i++;
			}
					
			System.out.println("User selected item 1 from the list");
			
			prg.acquireItem(qdate[0].getIpath());
		}
		
		//**********************************************************************************
		System.out.println("##### Query based on Latitude and Longitude #####");
		
		qloc = prg.queryData("-","-","103.0","934.3","-","-");
		resul = qloc[0].getResult();
				
		if(resul.equals("NO-VALID-DATA")){
			System.out.println("****Need to pass both latitude and longitude*****");
		}
		else if(resul.equals("NO")){
			System.out.println("****NO Search Data Found*****");
		}
		else{
		
			i = 1;
			for(Message m : qloc){
				System.out.println(i+" Resource "+m.getAuthor()+" "+m.getStime().getTime()+" "+m.getLat()+" "+m.getLon()+" "+m.getTags()+" "+m.getType()+" "+m.getIpath());
				i++;
			}
					
			System.out.println("User selected item 1 from the list");
			
			prg.acquireItem(qloc[0].getIpath());
		
		}
		
		//*********************************************************************
		System.out.println("##### Query based on tag #####");
		
		qtag = prg.queryData("-","-","-","-","sunrise","-");
		resul = qtag[0].getResult();
		
		if(resul.equals("NO")){
			System.out.println("****NO Search Data Found*****");
		}
		else{
		
			i = 1;
			for(Message m : qtag){
				System.out.println(i+" Resource "+m.getAuthor()+" "+m.getStime().getTime()+" "+m.getLat()+" "+m.getLon()+" "+m.getTags()+" "+m.getType()+" "+m.getIpath());
				i++;
			}
					
			System.out.println("User selected item 1 from the list");
			
			prg.acquireItem(qtag[0].getIpath());
		}
		
		//*************************************************************************
		System.out.println("##### Query based on author and type #####");
		
		qtype = prg.queryData("Ram","-","-","-","-","jpeg");
		resul = qtype[0].getResult();
		
		if(resul.equals("NO")){
			System.out.println("****NO Search Data Found*****");
		}
		else{
		
			i = 1;
			for(Message m : qtype){
				System.out.println(i+" Resource "+m.getAuthor()+" "+m.getStime().getTime()+" "+m.getLat()+" "+m.getLon()+" "+m.getTags()+" "+m.getType()+" "+m.getIpath());
				i++;
			}
					
			System.out.println("User selected item 9 from the list");
			
			prg.acquireItem(qtype[0].getIpath());
		}*/
		//*********************************************************************
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();   
        }
	
	}	
	
}

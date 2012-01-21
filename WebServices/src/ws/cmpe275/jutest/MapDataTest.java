package ws.cmpe275.jutest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.example.www.demo.Search;
import org.example.www.demo.SearchResponse;

import junit.framework.TestCase;

 

 

public class MapDataTest extends TestCase{
	 public static final String ENDPOINT = "http://localhost:8080/axis2/services/Search";
	 private String endPoint; 
	 private ws.cmpe275.idl.SearchStub stub;	
	 private ws.cmpe275.idl.SearchStub server;
	 
	protected void setUp() {
		//System.out.println("setup.......");
		
		 
	}
	
	protected void tearDown() {
	}
	
	private synchronized ws.cmpe275.idl.SearchStub connect() throws Exception
	{
		if(stub == null)
		{
			stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
		}
		return stub;
	}
	
	public void testHemsData(){
		String qry_str;
		ws.cmpe275.idl.SearchStub server;
		try {
			server = connect();
			Search sh = new Search();
			sh.setAuthor("Ashok");
			sh.setStime("-");
			sh.setLat("-");
			sh.setLon("-");
			sh.setTags("-");
			sh.setType("jtest");
			SearchResponse se = server.search(sh);
		    String response = se.getResult();
		    System.out.println("response= "+response);
		    assertEquals(response, "YES");
		    
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		//qry_str = FormQuery(se);
		
		
		
		
		
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
	
	
	
	
/*	public void testHemsData()
	{
		
		//System.out.println("dataflowtest.......");
		try{
				assertEquals("debug", "debug");
			 
						
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			 
		}
		System.out.println("testing done");
	}*/
	
	
}

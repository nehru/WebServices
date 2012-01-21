package ws.cmpe275.mtomtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.activation.DataHandler;

import org.example.www.demo.GetItem;
import org.example.www.demo.GetItemResponse;
import org.example.www.demo.Search;
import org.example.www.demo.SearchResponse;

import ws.cmpe275.global.Param;

import junit.framework.TestCase;

 

 

public class MtomDataTest extends TestCase{
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
	
	public void testMtomData(){
		 acquireItem("c:\\image\\cab.jpg");
		
		
		
		
		
	}
	
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
		
}

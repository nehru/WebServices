package ws.demo.client;

import java.io.FileOutputStream;

import javax.activation.DataHandler;

//import org.example.www.demo.GetItem;
//import org.example.www.demo.GetItemResponse;
import org.example.www.parallelproc.GetItem;
import org.example.www.parallelproc.GetItemResponse;
import org.example.www.parallelproc.ParallelQuery;
import org.example.www.parallelproc.ParallelQueryResponse;
import org.example.www.parallelproc.Message;

import ws.cmpe275.global.Param;



public class ParallelRun {
	
	public static final String ENDPOINT_PARALLEL = "http://localhost:8085/axis2/services/ParallelProc";
	 private String endPoint; 
	 ws.cmpe275.parallelproc.ParallelProcStub stub;
	 
	 
	 public ParallelRun()
	 {
		 this(ENDPOINT_PARALLEL);
	 }
		
	public ParallelRun(String endpoint2) {
		endPoint = endpoint2;
	}
	 
	private synchronized ws.cmpe275.parallelproc.ParallelProcStub connect() throws Exception
	{
		if(stub == null)
		{
			stub = new ws.cmpe275.parallelproc.ParallelProcStub(ENDPOINT_PARALLEL);
		}
		return stub;
	}
	 
	public ParallelQueryResponse queryData(String auth, String st, String lat, String lon, String tags, String type)
	{
		org.example.www.parallelproc.Message[] rec = null;
		
		if(((!lat.equals("-")) && (lon.equals("-"))) || ((lat.equals("-")) &&(!lon.equals("-")))){
			System.out.println("Please enter both latitude and longitude");
		
			//Message[] mm = new Message[1];
			//mm[0] = new Message();
			//mm[0].setResult("NO-VALID-DATA");
			//rec = "NO";
			//return rec;
		}
		
		
		try{
			ws.cmpe275.parallelproc.ParallelProcStub server = connect();
			ParallelQuery sh = new ParallelQuery();
			sh.setAuthor(auth);
			sh.setStime(st);
			sh.setLat(lat);
			sh.setLon(lon);
			sh.setTags(tags);
			sh.setType(type);
			
			ParallelQueryResponse se = server.parallelQuery(sh);
			rec = se.getMessage();
			System.out.println("from ParallelRun client result="+rec);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("end of query program");
		ParallelQueryResponse pqr = new ParallelQueryResponse();
		pqr.setMessage(rec);
		
		return pqr;
	}
	 
	public void acquireItem(String path)
	{
		String lfile = path;
		try{
			//ws.cmpe275.idl.SearchStub server = connect();
			ws.cmpe275.parallelproc.ParallelProcStub server = connect();
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
						
			String opath = Param.Lab2Dir+"\\FileReceived\\"+fname;
			
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
	
	 
	public static void main(String[] args) {
		boolean messageFound = true;
		ParallelQueryResponse ret;
		String[] path;
		ParallelRun prg = new ParallelRun();
		ret = prg.queryData("ashok","-","-","-","-","-");
		Message[] ms = ret.getMessage();
		int i=0;
		//calculate total message from the service
		for(Message m : ms){
			 i++;
		}
		int total_msg = i;
		
		if(total_msg == 0){
			messageFound = false;
			return;
		}
		else
			path = new String[total_msg];
		i = 0;
		if(messageFound == true){
			for(Message m : ms){
				System.out.println((i+1)+" "+"author= "+m.getAuthor()+" "+"path= "+m.getIpath());
				path[i] = m.getIpath();
				i++;
			}
		}  
		prg.acquireItem(path[4]);
	}

}

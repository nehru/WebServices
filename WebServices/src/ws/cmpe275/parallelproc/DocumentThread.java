package ws.cmpe275.parallelproc;

import org.example.www.document.Message;
import org.example.www.document.ParallelInfo;
import org.example.www.document.ParallelInfoResponse;
import org.example.www.parallelproc.ParallelQuery;

public class DocumentThread implements Runnable{
	Thread t;
	public static final String ENDPOINT = "http://localhost:8081/axis2/services/Document";
	private ws.cmpe275.document.DocumentStub stub;
	private ServerParallelProc spp;
	private org.example.www.parallelproc.Message[] li;
	
	DocumentThread(ServerParallelProc sp){
		this.spp = sp;
		// Creating a worker thread
		t = new Thread(this, "DocumentThread Worker Thread");
		System.out.println("DocumentThread Worker thread: " + t);
		t.start(); // Start the thread
		}
	
	
	@Override
	public void run() {
		ParallelQuery pq = spp.getParQ();	
		
		try {
			
			ws.cmpe275.document.DocumentStub server = connect();
			ParallelInfo sh = new ParallelInfo();
			sh.setAuthor(pq.getAuthor());
			sh.setStime(pq.getStime());
			sh.setLat(pq.getLat());
			sh.setLon(pq.getLon());
			sh.setTags(pq.getTags());
			sh.setType(pq.getType());
											  
			ParallelInfoResponse pr = server.parallelInfo(sh);
			
			//Message[] msg = pr.getMessage();
			 Message[] msg = pr.getMessage();
			int i=0;
			for(Message m : msg){
				//System.out.println("from image "+" "+m.getAuthor()+" "+m.getIpath()+" "+m.getType());
				i++;
			}
			int total_msg = i;
			System.out.println("****total_msg = "+total_msg);
			
			if(total_msg !=0){
				for(Message m : msg){
					System.out.println("from document "+" "+m.getAuthor()+" "+m.getIpath()+" "+m.getType());
				}
				
			}
			 
			//spp.setMsg(msg);
			li = new org.example.www.parallelproc.Message[total_msg];
			
			
			//li = new org.example.www.document.Message[total_message];
			i = 0;
			for(Message c : msg){
				//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				li[i] = new org.example.www.parallelproc.Message();
				li[i].setAuthor(c.getAuthor());
				li[i].setStime(c.getStime());
				li[i].setLat(c.getLat());
				li[i].setLon(c.getLon());
				li[i].setTags(c.getTags());
				li[i].setType(c.getType());
			    li[i].setIpath(c.getIpath());
			    
				i++;
			}
			
		/*	for(org.example.www.parallelproc.Message m: li){
				i++;
				System.out.println("in document "+" "+m.getAuthor()+" "+m.getIpath()+" "+m.getType());
		 
			}*/
			
			
			//passing the message to the ServerParallelProc
			this.spp.setDocMsg(li);
			//Thread.sleep(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	System.out.println("run method ");
 
		
		
	}

	private synchronized ws.cmpe275.document.DocumentStub connect() throws Exception
	{
		if(stub == null)
		{
			stub = new ws.cmpe275.document.DocumentStub(ENDPOINT);
		}
		return stub;
	}
}

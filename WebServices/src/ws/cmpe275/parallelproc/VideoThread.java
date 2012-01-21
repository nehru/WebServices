package ws.cmpe275.parallelproc;

import org.example.www.parallelproc.ParallelQuery;
import org.example.www.video.Message;
import org.example.www.video.ParallelInfo;
import org.example.www.video.ParallelInfoResponse;


public class VideoThread implements Runnable{
	 
	Thread t;
	public static final String ENDPOINT = "http://localhost:8082/axis2/services/Video";
	private ws.cmpe275.video.VideoStub stub;
	private ServerParallelProc spp;
	private org.example.www.parallelproc.Message[] li;
	
	
	VideoThread(ServerParallelProc sp){
		this.spp = sp;
		// Creating a worker thread
		t = new Thread(this, "VideoThread Worker Thread");
		System.out.println("VideoThread Worker thread: " + t);
		t.start(); // Start the thread
		}
	
	@Override
	public void run() {
		
		ParallelQuery pq = spp.getParQ();	
		
		try {
			
			ws.cmpe275.video.VideoStub server = connect();
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
			//this.spp.setDocMsg(li);
			this.spp.setVideoMsg(li);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	System.out.println("run method ");
 
		
		
		
	}
	private synchronized ws.cmpe275.video.VideoStub connect() throws Exception
	{
		if(stub == null)
		{
			stub = new ws.cmpe275.video.VideoStub(ENDPOINT);
		}
		return stub;
	}
}

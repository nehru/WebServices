package ws.cmpe275.parallelproc;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.example.www.parallelproc.GetItem;
import org.example.www.parallelproc.GetItemResponse;
import org.example.www.parallelproc.Message;
import org.example.www.parallelproc.ParallelQuery;
import org.example.www.parallelproc.ParallelQueryResponse;

import ws.cmpe275.idl.mthread;

public class ServerParallelProc implements ParallelProcSkeletonInterface{
	 ParallelQuery parQ;
	 Message[] mainMsg;
	 Message[] docMsg;
	 Message[] videoMsg;
	 Message[] siteMsg;
	 
	 Message[] list;
	 
	 int total_doc_msg=0;
	 int total_main_msg=0;
	 int total_video_msg=0;
	 int total_site_msg=0;
	 
	 Message[] cliMsg;
	 
	@Override
	public ParallelQueryResponse parallelQuery(ParallelQuery pq) {
		
		this.parQ = pq;
		
				
		System.out.println("************ parallelQuery invoked");
		System.out.println("author= "+pq.getAuthor());
		
		System.out.println("STARTING mthread**********");
		
		ImageThread m1 = new ImageThread(this);
		DocumentThread m2 = new DocumentThread(this);
		VideoThread m3 = new VideoThread(this);
		SiteThread m4 = new SiteThread(this);
		try {
			m1.t.join();
			m2.t.join();
			m3.t.join();
			m4.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("FINISHED mthread**********");
		 int i = 0;
		
		 //calculate total doc message
		if(docMsg != null){
			
			for(Message m: docMsg){
				i++;
			}
			this.total_doc_msg = i;
			System.out.println("total_doc_msg= "+total_doc_msg);
		}
		
		i=0;
		if(mainMsg != null){
			
			for(Message m: mainMsg){
				i++;
			}
			this.total_main_msg = i;
			System.out.println("total_main_msg= "+total_main_msg);
		}
		
		i=0;
		if(videoMsg != null){
			
			for(Message m: videoMsg){
				i++;
			}
			this.total_video_msg = i;
			System.out.println("total_video_msg= "+total_video_msg);
		}
		
		i=0;
		if(siteMsg != null){
			
			for(Message m: siteMsg){
				i++;
			}
			this.total_site_msg = i;
			System.out.println("total_video_msg= "+total_site_msg);
		}
		
		
		
		
		
		
		int total_alloc = this.total_doc_msg + this.total_main_msg+this.total_video_msg+this.total_site_msg;
		System.out.println("total_alloc= "+total_alloc);
		
		list = new Message[total_alloc];
		i=0;
		for(Message c : mainMsg){
			//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
			list[i] = new Message();
			list[i].setAuthor(c.getAuthor());
			list[i].setStime(c.getStime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
		    
			i++;
		}
		
		for(Message c : docMsg){
			//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
			list[i] = new Message();
			list[i].setAuthor(c.getAuthor());
			list[i].setStime(c.getStime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
		    
			i++;
		}
		
		for(Message c : videoMsg){
			//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
			list[i] = new Message();
			list[i].setAuthor(c.getAuthor());
			list[i].setStime(c.getStime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
		    
			i++;
		}
		
		for(Message c : siteMsg){
			//System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
			list[i] = new Message();
			list[i].setAuthor(c.getAuthor());
			list[i].setStime(c.getStime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
		    
			i++;
		}
		
		
		
		
		
		ParallelQueryResponse pqr = new ParallelQueryResponse();
		pqr.setMessage(list);
		
		 
		
	 
		for(Message m : list){
			System.out.println("serverParallelProc "+m.getAuthor()+" "+m.getIpath());
		} 
		
		
		
		return pqr;
	}

	public ParallelQuery getParQ() {
		return parQ;
	}

	public void setParQ(ParallelQuery parQ) {
		this.parQ = parQ;
	}


	public Message[] getMainMsg() {
		return mainMsg;
	}

	public void setMainMsg(Message[] mainMsg) {
		this.mainMsg = mainMsg;
	}

	@Override
	public GetItemResponse getItem(GetItem getItem) {
		DataSource ds = new FileDataSource(getItem.getItem());
		DataHandler hdr = new DataHandler(ds);
		
		GetItemResponse res = new GetItemResponse();
		res.setMessages(hdr);
		
		return res;
		 
	}

	public synchronized Message[] getDocMsg() {
		return docMsg;
	}

	public synchronized void setDocMsg(Message[] docMsg) {
		this.docMsg = docMsg;
	}

	public Message[] getVideoMsg() {
		return videoMsg;
	}

	public void setVideoMsg(Message[] videoMsg) {
		this.videoMsg = videoMsg;
	}

	public Message[] getSiteMsg() {
		return siteMsg;
	}

	public void setSiteMsg(Message[] siteMsg) {
		this.siteMsg = siteMsg;
	}

}

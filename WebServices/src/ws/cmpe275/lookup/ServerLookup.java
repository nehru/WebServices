package ws.cmpe275.lookup;

import java.util.ArrayList;

import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;

import ws.cmpe275.db.bean.LookupData;


public class ServerLookup implements LookupSkeletonInterface{
	public static final String ENDPOINT_LOOKUP = "http://localhost:8084/axis2/services/Lookup";

	@Override
	public SearchLookupResponse searchLookup(SearchLookup slk) {
		ArrayList<LookupData> al;
		
		System.out.println("STARTING mthread_lookup********** "+slk.getServerName());
		mthread_lookup m = new mthread_lookup(slk);
		try {
			m.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		SearchLookupResponse sdr = m.sendResult();
		return sdr;
	}
	
	 
	
	 
	
	

	

	 
	
	
	
}

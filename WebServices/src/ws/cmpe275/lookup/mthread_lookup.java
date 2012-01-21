package ws.cmpe275.lookup;

import java.util.ArrayList;

import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;

import ws.cmpe275.db.bean.LookupData;

public class mthread_lookup implements Runnable{
	Thread t;
	SearchLookup sl;
	SearchLookupResponse str;
	
	mthread_lookup(SearchLookup slk){
		this.sl = slk;
		t = new Thread(this,"Lookup Worker Thread");
		System.out.println("Lookup worker Thread "+t);
		t.start();	
	}
	
	SearchLookupResponse sendResult(){
		return str;
	}
	
	public SearchLookupResponse search_lookup_list(SearchLookup sd) {
		String qry_str;
		ArrayList<LookupData> al;
		System.out.println("search_lookup_list********");
		
	//	qry_str = "where server ="+"'"+sd.getServerName()+"'";
	//	System.out.println("search_lookup_list******** "+qry_str);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		
		//sending originating server name to query the db for next node
		al = fe.LookupDB(sd.getServerName(),"hibernate_lookup.cfg.xml");
		
		SearchLookupResponse str = new SearchLookupResponse();
		
		int count = al.size();
		int i = 0;
		String next_url = null;
		for (LookupData c : al)   
		{
			System.out.println("server ="+c.getServer());
			System.out.println("url ="+c.getUrl());
			next_url = c.getUrl();
			System.out.println("status ="+c.getStatus());
			System.out.println("id="+c.getId());
		}
		
		str.setNextURL(next_url);
		
		return str;
	}
	
	
	
	@Override
	public void run() {
		
		str = search_lookup_list(this.sl);
	}

}

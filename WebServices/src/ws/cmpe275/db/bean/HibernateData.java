
package ws.cmpe275.db.bean;

import java.util.ArrayList;
import java.util.Vector;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




public class HibernateData {
		
	public synchronized ArrayList ConnectDB(String str, String cfgFile){
	Session session = null;
	String qstr = null;
	ArrayList<MetaData> alist = new ArrayList<MetaData>();
	 
		try{
			System.out.println("calling connect db***");
			// This step will read hibernate.cfg.xml and prepare hibernate for use
			SessionFactory sessionFactory = new Configuration().configure(cfgFile).buildSessionFactory();
			session =sessionFactory.openSession();
			session.beginTransaction(); 
			Vector<MetaData> col = new Vector<MetaData>(session.createQuery("from MetaData "+ str).list());
			
			System.out.println("IN HIBERNATE****");
			
			if(col.isEmpty()){
				System.out.println("##### NO data found");
			}
			for(MetaData m : col){
				System.out.println("auth="+m.getAuthor()+" "+m.getIpath()+" "+m.getId());
			}
			
			
			if(col.isEmpty()){
				System.out.println("##### NO data found");
			//	MetaData md = new MetaData();
			//	md.setResult("NO");	
			//	alist.add(md);
			}
			else{
			
			for (MetaData c : col)
			{
				MetaData md = new MetaData();
				md.setResult("YES");
				md.setAuthor(c.getAuthor());
				md.setItime(c.getItime());
				md.setLat(c.getLat());
				md.setLon(c.getLon());
				md.setTags(c.getTags());
				md.setType(c.getType());
				md.setIpath(c.getIpath());
				alist.add(md);			
			}
			session.getTransaction().commit();
			session.close();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			//session.flush();
			//session.close();

			}
		
		return alist;  
	}
	
	public synchronized ArrayList LookupDB(String str, String cfgFile){
		Session session = null;
		String qstr = null;
		ArrayList<LookupData> alist = new ArrayList<LookupData>();
		 
			try{
				System.out.println("calling connect db***");
				// This step will read hibernate.cfg.xml and prepare hibernate for use
				SessionFactory sessionFactory = new Configuration().configure(cfgFile).buildSessionFactory();
				session =sessionFactory.openSession();
				session.beginTransaction(); 
				Vector<LookupData> col = new Vector<LookupData>(session.createQuery("from LookupData").list());
				System.out.println("size= "+col.size());
				
								
				int inx=0;
				int inx_next = 0;
				if(col.isEmpty()){
					LookupData md = new LookupData();
					md.setResult("NO");	
					alist.add(md);
				}
				else{
				
				for (LookupData c : col)
				{
					System.out.println("[lookup]from client str= "+str);
					System.out.println("server id= "+c.getId()+" "+c.getUrl());
					System.out.println("status= "+c.getStatus());
					System.out.println("server = "+c.getServer());
					
					//int sid = (((int)c.getId() +1)%4);
					//System.out.println("sid= "+sid);
					
					if(c.getServer().equals(str) && c.getStatus().equals("YES")){
						System.out.println("******match found index= "+inx);
						
						//inx_next = ((inx+1)%col.size());
						System.out.println("++++++++++inx_next= "+inx_next);
						System.out.println("++++++++++++next_node_url= "+col.get(inx_next).getUrl());
						
						System.out.println("++++++++++col.get(inx_next).getStatus()= "+col.get(inx_next).getStatus());
						
						
						if(col.get(inx_next).getStatus().equals("YES")){
							System.out.println("***********next node yes found"+col.get(inx_next).getUrl());
							break;
						}
					}
					else{
						System.out.println("******inx= "+inx);
						System.out.println("******server= "+c.getServer());
						++inx;
					}
					
				}
										
					inx_next = ((inx+1)%col.size());
					System.out.println("++++++++++inx_next= "+inx_next);
					System.out.println("++++++++++++next_node_url= "+col.get(inx_next).getUrl());
					
					
					LookupData md = new LookupData();
					md.setResult("YES");
					
					md.setServer(col.get(inx_next).getServer());
					md.setUrl(col.get(inx_next).getUrl());
					md.setStatus(col.get(inx_next).getStatus());
					
					//md.setServer(c.getServer());
					//md.setUrl(c.getUrl());
					//md.setStatus(c.getStatus());
					
					alist.add(md);			
			//	}
				session.getTransaction().commit();
				session.close();
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}finally{
				//session.flush();
				//session.close();

				}
			
			return alist;  
		}
		
	public synchronized ArrayList ConnectSALDB(String str, String cfgFile){
		Session session = null;
		String qstr = null;
		ArrayList<DbbElement> alist = new ArrayList<DbbElement>();
		 
			try{
				System.out.println("calling connect db***");
				// This step will read hibernate.cfg.xml and prepare hibernate for use
				SessionFactory sessionFactory = new Configuration().configure(cfgFile).buildSessionFactory();
				session =sessionFactory.openSession();
				session.beginTransaction(); 
				Vector<DbbElement> col = new Vector<DbbElement>(session.createQuery("from DbbElement "+ str).list());
				
				System.out.println("IN HIBERNATE DbbElement****");
				
				if(col.isEmpty()){
					System.out.println("##### NO data found");
				}
				for(DbbElement m : col){
					System.out.println("auth="+m.getAuthor()+" "+m.getPath()+" "+m.getId());
				}
				
				
				if(col.isEmpty()){
					System.out.println("##### NO data found");
				//	MetaData md = new MetaData();
				//	md.setResult("NO");	
				//	alist.add(md);
				}
				else{
				
				for (DbbElement c : col)
				{
					DbbElement md = new DbbElement();
					 
					md.setAuthor(c.getAuthor());
					md.setName(c.getName());
					md.setDescription(c.getDescription());
					md.setTimestamp(c.getTimestamp());
					md.setLatitude(c.getLatitude());
					md.setLongitude(c.getLongitude());
					md.setTags(c.getTags());
					md.setPath(c.getPath());
					
					alist.add(md);			
				}
				session.getTransaction().commit();
				session.close();
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}finally{
				//session.flush();
				//session.close();

				}
			
			return alist;  
		}

	
	
}

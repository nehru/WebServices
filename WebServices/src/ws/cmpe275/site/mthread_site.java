package ws.cmpe275.site;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.example.www.lookup.SearchLookup;
import org.example.www.lookup.SearchLookupResponse;
import org.example.www.site.Message;
import org.example.www.site.PassInfo;
import org.example.www.site.SearchSite;
import org.example.www.site.SearchSiteResponse;

import ws.cmpe275.db.bean.MetaData;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;





public class mthread_site implements Runnable{
	Thread t;
	String str;
	SearchSite srloc;
	PassInfo pi;
	public static final String ServiceName = "SiteServer";
	public static final String ENDPOINT_LOOKUP = "http://localhost:8084/axis2/services/Lookup";
	ws.cmpe275.lookup.LookupStub server_lookup, stub_lookup;
	
	boolean current_data_found = true;
	boolean prev_data_found = true;
	int total_prev_msg=0;
	int total_local_msg=0;
	org.example.www.demo.Message[] list1;
	Message[] rec;
	int total_yahoo_msg = 0;
	
	private String snippet; 
	private String beginHTML = "<HTML><HEAD><TITLE>Search Results</TITLE></HEAD><BODY>"; 
	private String endHTML = "</BODY></HTML>";
	ArrayList<String> bl;
	boolean yahoo_data_found = true;
	
	
	mthread_site(SearchSite svd){
		this.srloc = svd;
		t = new Thread(this,"Site Worker Thread");
		System.out.println("site worker Thread "+t);
		t.start();	
	}
	
	mthread_site(PassInfo pi){
		this.pi = pi;
		 bl = new ArrayList<String>();
		t = new Thread(this, "Site Worker Thread");
		System.out.println("Site worker Thread "+t);
		t.start();
	}
	
	
	@Override
	public void run() {
		System.out.println("******** inside run() ");
		//str = search_site_list(this.srloc); 
		str = search_site_list_pass(this.pi);
	}

	//SearchSiteResponse sendResult(){
	//	return str;
//	}
	
	public String search_site_list_pass(PassInfo pi) {
		String qry_str;
		ArrayList<MetaData> al;
		
		//Create query string from the user information
	//	qry_str = FormQuery(sd);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(pi.getQueryName(),"hibernate_site.cfg.xml");
		//String sresult = al.get(0).getResult();
		
		if(al.size() == 0)
		{
			current_data_found = false;
		}
		
		SearchSiteResponse str = new SearchSiteResponse();
		
		this.total_local_msg = al.size();
		int i = 0;
		Message[] list = new Message[this.total_local_msg];
		
		//Copying local data into the Message list
		if(current_data_found)
		{
			for (MetaData c : al)   
			{
				list[i] = new Message();
				list[i].setResult("YES");
				list[i].setAuthor(c.getAuthor());			
				list[i].setStime(c.getItime());
				list[i].setLat(c.getLat());
				list[i].setLon(c.getLon());
				list[i].setTags(c.getTags());
				list[i].setType(c.getType());
				list[i].setIpath(c.getIpath());
				
				i++;
			}
	
			//*******************************************************	
			//calculate total message in the Message
	/*		i = 0;
			for(Message c : list){
				System.out.println("author= "+c.getAuthor()+" "+c.getIpath());
				i++;
			}
			int total_local_msg = i;
			System.out.println("****total message= "+total_local_msg);
		*/
		
		}
		//Search yahoo web service for sites
		
		//*******************************************************
		i=0;
		handleSearch(pi.getQueryName());
		if(this.bl != null){ 
		for(String s:this.bl){
	        	System.out.println("[ Yahoo=] "+s);
	        	i++;
	        }
		}
		total_yahoo_msg = i;
		if(total_yahoo_msg == 0)
			yahoo_data_found = false;
		System.out.println("total_yahoo_msg = "+total_yahoo_msg);
		
		//*******************************************************
		
		//checking for previous node message
		if(this.pi.getMessage() == null)
		{
			prev_data_found = false;
		}
		
		
		if(prev_data_found)
		{
			//reading messages from previous node
			rec = pi.getMessage();
			i = 0;
			for(Message d : rec){
				//System.out.println("Previous message "+"author= "+d.getAuthor()+" "+d.getIpath()+" "+d.getStime().getTime());
				i++;
			}
			
			this.total_prev_msg = i;
		}
		int tcount = this.total_local_msg + this.total_prev_msg+this.total_yahoo_msg;
		
		//Allocate Message array to sent to next node contain local and previous Messages
		if(current_data_found || prev_data_found)
		list1 = new org.example.www.demo.Message[tcount];
		
		i=0;
		if(current_data_found)
		{
			for(Message c : list)
			{
				list1[i] = new org.example.www.demo.Message();
				list1[i].setResult("YES");
				list1[i].setAuthor(c.getAuthor());			
				list1[i].setStime(c.getStime());
				list1[i].setLat(c.getLat());
				list1[i].setLon(c.getLon());
				list1[i].setTags(c.getTags());
				list1[i].setType(c.getType());
				list1[i].setIpath(c.getIpath());
				
				i++;
			}
		}
		int nt = i;
		
		if(prev_data_found)
		{
			for(Message d : rec)
			{
				list1[nt] = new org.example.www.demo.Message();
				list1[nt].setResult("YES");
				list1[nt].setAuthor(d.getAuthor());			
				list1[nt].setStime(d.getStime());
				list1[nt].setLat(d.getLat());
				list1[nt].setLon(d.getLon());
				list1[nt].setTags(d.getTags());
				list1[nt].setType(d.getType());
				list1[nt].setIpath(d.getIpath());
				
				nt++;
			}
		}
		
		int ntt = nt;
		
		Calendar cc = cdate("12-8-2003-12-34-52");
		
		if(yahoo_data_found)
		{
			for(String d : this.bl)
			{
				list1[ntt] = new org.example.www.demo.Message();
				list1[ntt].setResult("Yahoo");
				list1[ntt].setAuthor(d);			
				list1[ntt].setStime(cc);
				list1[ntt].setLat("-");
				list1[ntt].setLon("-");
				list1[ntt].setTags("-");
				list1[ntt].setType("-");
				list1[ntt].setIpath("-");
				ntt++;
			}
		}
		
		
		
		//display all messages sent to next node
	/*	for(org.example.www.demo.Message m : list1){
			System.out.println("SITE ALL message "+"author= "+m.getAuthor()+" "+m.getIpath()+" "+m.getStime().getTime());
			i++;
		}*/
		
		//Call lookup service for next node.
		String next_node = GetNextNode(ServiceName);
		System.out.println("+++++ SITE THREAD current node = "+ServiceName+"  next node = "+ next_node);
		
		//Send message to next node 
		try {
		
			System.out.println("************* before sending to image server");
			ws.cmpe275.idl.SearchStub server = connect(next_node);
			org.example.www.demo.PassInfo pif = new org.example.www.demo.PassInfo();
			pif.setMessage(list1);
			pif.setQueryName(pi.getQueryName());
			pif.setOrgServer(pi.getOrgServer());
			
			server.passInfo(pif);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	//*******************************************************	
	 // str.setMessage(list);
		return "YES";		
	}
	
	
	
	
	public SearchSiteResponse search_site_list(SearchSite sd) {
		String qry_str;
		ArrayList<MetaData> al;
		
		//Create query string from the user information
		qry_str = FormQuery(sd);
		
		//Calling hibernate to query data from mysql database
		ws.cmpe275.db.bean.HibernateData fe = new ws.cmpe275.db.bean.HibernateData();
		al = fe.ConnectDB(qry_str,"hibernate_site.cfg.xml");
		String sresult = al.get(0).getResult();
		
		SearchSiteResponse str = new SearchSiteResponse();
		
		int count = al.size();
		int i = 0;
		Message[] list = new Message[count];
		
		//No data for the query then "NO" is sent as a result to the client
		if(sresult.equals("NO")){
			list[i] = new Message();
			list[i].setResult(sresult);
		}
		else{	
			//If data is found then all these data's are sent to the client and the result as "YES".
		for (MetaData c : al)   
		{
			list[i] = new Message();
			list[i].setResult("YES");
			list[i].setAuthor(c.getAuthor());			
			list[i].setStime(c.getItime());
			list[i].setLat(c.getLat());
			list[i].setLon(c.getLon());
			list[i].setTags(c.getTags());
			list[i].setType(c.getType());
			list[i].setIpath(c.getIpath());
			
			i++;
		}
	  }
	  str.setMessage(list);
		return str;		
	}
		
	//Used to create query string from the user information
	String FormQuery(SearchSite se){
		
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

	public  String GetNextNode(String saddress)
	{
		String rec = null;
		try {
			ws.cmpe275.lookup.LookupStub server = connect_lookup(ENDPOINT_LOOKUP);
			SearchLookup slu = new SearchLookup();
			slu.setServerName(saddress);
			SearchLookupResponse slr = server.searchLookup(slu);
			rec = slr.getNextURL();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rec;
	}
	
	public synchronized ws.cmpe275.lookup.LookupStub connect_lookup(String ep) throws Exception
	{
		if(stub_lookup == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub_lookup);
			stub_lookup = new ws.cmpe275.lookup.LookupStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub_lookup);
		}
		return stub_lookup;
	}
	
	public synchronized ws.cmpe275.idl.SearchStub connect(String ep) throws Exception
	{
		ws.cmpe275.idl.SearchStub stub = null;
		if(stub == null)
		{
			//stub = new ws.cmpe275.idl.SearchStub(ENDPOINT);
			System.out.println("before connect "+ep+ " "+stub);
			stub = new ws.cmpe275.idl.SearchStub(ep);
			System.out.println("AFTER connect "+ep + " "+stub);
		}
		return stub;
	}
	
	//Search routines for yahoo web search
	
	public String searchYahoo(String query, String format) {
       
		System.out.println("inside searchyahoo query="+query);
		try {
            snippet = beginHTML;
            String epr = "http://api.search.yahoo.com/WebSearchService/V1/webSearch";
            File configFile = new File("axis2.xml");
            ConfigurationContext configurationContext = ConfigurationContextFactory
                    .createConfigurationContextFromFileSystem(null, configFile
                            .getAbsolutePath());

            ServiceClient client = new ServiceClient(configurationContext, null);
            Options options = new Options();
            client.setOptions(options);
            options.setTo(new EndpointReference(epr));
            options.setProperty(Constants.Configuration.MESSAGE_TYPE, HTTPConstants.MEDIA_TYPE_X_WWW_FORM);
            options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
            OMElement response = client.sendReceive(getPayloadForYahooSearchCall(query, format));
            generateSnippet(response);
            return snippet;

        } catch (Exception e) {
            e.printStackTrace();
            snippet = "<H2>Error occurred during the invocation to Yahoo search service</H2>" +
                    "<p>" + e.getMessage() + "</p>" + endHTML;
        
        
        }
        return null;
    }

	private void generateSnippet(OMElement response) {
        String title = null;
        String summary = null;
        String clickUrl = null;
        String url = null;
        OMElement result = null;
        //get an iterator for Result elements
        Iterator itr = response.getChildElements();
        Iterator innerItr;
        while (itr.hasNext()) {
            result = (OMElement) itr.next();
            innerItr = result.getChildElements();
            if (innerItr.hasNext()) {
                title = ((OMElement) innerItr.next()).getText();
                summary = ((OMElement) innerItr.next()).getText();
                url = ((OMElement) innerItr.next()).getText();
                clickUrl = ((OMElement) innerItr.next()).getText();
                if (title != null) {
                    snippet += "<a href=" + clickUrl + ">" + title + "</a>" + "<br>" + summary +
                            "<br>" + url + "<br>" + "<br>" + "\n";
                    this.bl.add(snippet);
                   // System.out.println("snippet= "+snippet);
                }
            }
        }
        snippet += endHTML;
       // System.out.println("snippet= "+snippet);
    }
	
	private static OMElement getPayloadForYahooSearchCall(String queryStr, String formatStr) throws UnsupportedEncodingException {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMElement rootElement = fac.createOMElement("webSearch", null);

        OMElement appId = fac.createOMElement("appid", null, rootElement);
        appId.setText("ApacheRestDemo");

        OMElement query = fac.createOMElement("query", null, rootElement);
        query.setText(URLEncoder.encode(queryStr, "UTF-8"));

        OMElement outputType = fac.createOMElement("output", null, rootElement);
        outputType.setText("json");

        if (formatStr != null && formatStr.length() != 0) {
            OMElement format = fac.createOMElement("format", null, rootElement);
            format.setText(URLEncoder.encode(formatStr, "UTF-8"));
        }
        return rootElement;
    }
	
	private void handleSearch(String qry){
		ArrayList<String> bl;
		String query = qry;
         String res;
       
        res =  searchYahoo(query, "");
             
        System.out.println("query = "+qry);
        
       
        
    }
	
	public static Calendar cdate(String dt)
	{
		DateFormat formatter ; 
		Date date ; 
		Calendar cal=null;
		String dte = null;
		
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
			date = (Date)formatter.parse(dt);
			cal=Calendar.getInstance();
			cal.setTime(date);
			
			dte = formatter.format(cal.getTime());
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 
		return cal;	 
	 }
}


package ws.cmpe275.db.bean;

public class LookupData {
	private long id;
	private String server;
	private String url;
	private String status;
	private String result;
	
	public long getId() {
		return id;
	}

	
	public void setId(long l) {
		id = l;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
}

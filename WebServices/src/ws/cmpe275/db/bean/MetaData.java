package ws.cmpe275.db.bean;


public class MetaData {
	
	private long id;
	private String author;
	private java.util.Calendar itime;
	private String lat;
	private String lon;
	private String tags;
	private String type;
	private String ipath; 

	private String result;
	
	public long getId() {
		return id;
	}

	
	public void setId(long l) {
		id = l;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public java.util.Calendar getItime() {
		return itime;
	}


	public void setItime(java.util.Calendar itime) {
		this.itime = itime;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLon() {
		return lon;
	}


	public void setLon(String lon) {
		this.lon = lon;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getIpath() {
		return ipath;
	}


	public void setIpath(String ipath) {
		this.ipath = ipath;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	

}

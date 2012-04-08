package com.vickystevens.code.friston;

public class MyGeoPoint {
	
	private double lat;
	private double lon;
	private String title;
	private String snippet;
 


	public MyGeoPoint(double lat, double lon, String title, String snippet){
		this.lat = lat;
		this.lon = lon;
		this.title = title;
		this.snippet = snippet;
	}



	public double getLat() {
		return lat;
	}



	public void setLat(double lat) {
		this.lat = lat;
	}



	public double getLon() {
		return lon;
	}



	public void setLon(double lon) {
		this.lon = lon;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getSnippet() {
		return snippet;
	}



	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}




	
}
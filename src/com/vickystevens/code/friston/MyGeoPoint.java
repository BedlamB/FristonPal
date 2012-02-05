package com.vickystevens.code.friston;

import com.google.android.maps.GeoPoint;

public class MyGeoPoint {
	
	private double lat;     //maybe store these as doubles and not have to call GetGeopoint everytime?
	private double lon;
	private String tag;
	private String msg;
 


	public MyGeoPoint(double lat, double lon, String tag, String msg){
		this.lat = lat;
		this.lon = lon;
		this.tag = tag;
		this.msg = msg;
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



	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}




	
}
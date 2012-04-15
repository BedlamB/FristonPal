package com.vickystevens.code.friston;

// TODO: Auto-generated Javadoc
/**
 * The Class MyGeoPoint.
 * @author Vicky Stevens
 * @version 1.0 Build 9000 15th April 2012.
 */
public class MyGeoPoint {
	
	/** The lat. */
	private double lat;
	
	/** The lon. */
	private double lon;
	
	/** The title. */
	private String title;
	
	/** The snippet. */
	private String snippet;
 


	/**
	 * Instantiates a new my geo point.
	 *
	 * @param lat the lat
	 * @param lon the lon
	 * @param title the title
	 * @param snippet the snippet
	 */
	public MyGeoPoint(double lat, double lon, String title, String snippet){
		this.lat = lat;
		this.lon = lon;
		this.title = title;
		this.snippet = snippet;
	}



	/**
	 * Gets the lat.
	 *
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}



	/**
	 * Sets the lat.
	 *
	 * @param lat the new lat
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}



	/**
	 * Gets the lon.
	 *
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}



	/**
	 * Sets the lon.
	 *
	 * @param lon the new lon
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}



	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * Gets the snippet.
	 *
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}



	/**
	 * Sets the snippet.
	 *
	 * @param snippet the new snippet
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}




	
}
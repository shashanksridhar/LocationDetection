package com.tk.mapoi.labels;

/**
 * The Class PlaceLatiLongi - Bean Class.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class PlaceLatiLongi {

	/** The place info. */
	private String placeInfo;

	/** The distance between place and centroid. */
	private double distance;

	/**
	 * Instantiates a new place lati longi.
	 * 
	 * @param placeInfo
	 *            the place info
	 * @param dist
	 *            the dist
	 */
	public PlaceLatiLongi(String placeInfo, double dist) {
		super();
		this.placeInfo = placeInfo;
		this.distance = dist;
	}

	/**
	 * Instantiates a new place lati longi.
	 */
	public PlaceLatiLongi() {
		super();
	}

	/**
	 * Gets the place info.
	 * 
	 * @return the place info
	 */
	public String getPlaceInfo() {
		return placeInfo;
	}

	/**
	 * Sets the place info.
	 * 
	 * @param placeInfo
	 *            the new place info
	 */
	public void setPlaceInfo(String placeInfo) {
		this.placeInfo = placeInfo;
	}

	/**
	 * Gets the distance.
	 * 
	 * @return the distance
	 */
	public double getdistance() {
		return distance;
	}

	/**
	 * Sets the lati.
	 * 
	 * @param distance
	 *            the new lati
	 */
	public void setLati(double distance) {
		this.distance = distance;
	}

}

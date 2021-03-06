package com.tk.mapoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Class ClusterInfoDB - Database table
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
@DatabaseTable
public class ClusterInfoDB {

	/** The Constant TIMESTAMP_FIELD_NAME. */
	public static final String TIMESTAMP_FIELD_NAME = "Timestamp";

	/** The id. */
	@DatabaseField(generatedId = true)
	private int id;

	/** The Latitude. */
	@DatabaseField
	private String Latitude;

	/** The Longitude. */
	@DatabaseField
	private String Longitude;

	/** The Timestamp. */
	@DatabaseField
	private Long Timestamp;

	/** The Clock g. */
	@DatabaseField
	private String ClockG;

	/** The cluster id. */
	@DatabaseField
	private String clusterId;

	/**
	 * Instantiates a new cluster info db.
	 */
	public ClusterInfoDB() {
	}

	/**
	 * Instantiates a new cluster info db.
	 * 
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @param timestamp
	 *            the timestamp
	 * @param ClockG
	 *            the clock g
	 * @param clusterId
	 *            the cluster id
	 */
	public ClusterInfoDB(String latitude, String longitude, Long timestamp,
			String ClockG, String clusterId) {
		super();
		this.Latitude = latitude;
		this.Longitude = longitude;
		this.Timestamp = timestamp;
		this.ClockG = ClockG;
		this.clusterId = clusterId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("\n" + id + "." + "Latitude=" + Latitude + "\nLongitude="
				+ Longitude + "\nTimestamp=" + Timestamp + "\nClock=" + ClockG
				+ "\nclusterId=" + clusterId);
	}

	/**
	 * Gets the clock g.
	 * 
	 * @return the clock g
	 */
	public String getClockG() {
		return ClockG;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public String getLatitude() {
		return Latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public String getLongitude() {
		return Longitude;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the timestamp.
	 * 
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return Timestamp;
	}

	/**
	 * Gets the cluster id.
	 * 
	 * @return the cluster id
	 */
	public String getClusterId() {
		return clusterId;
	}

}
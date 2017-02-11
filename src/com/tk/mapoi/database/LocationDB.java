package com.tk.mapoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Class LocationDB - Database table.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
@DatabaseTable
public class LocationDB {

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
	@DatabaseField(columnName = "Timestamp")
	private Long Timestamp;

	/** The Clock g. */
	@DatabaseField(columnName = "ClockG")
	private String ClockG;

	/** The cluster id. */
	@DatabaseField
	private String clusterId;

	/**
	 * Instantiates a new GPS infodb.
	 */
	public LocationDB() {
	}

	/**
	 * Instantiates a new GPS infodb.
	 * 
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @param timestamp
	 *            the timestamp
	 * @param ClockG
	 *            the clock g
	 */
	public LocationDB(String latitude, String longitude, Long timestamp,
			String ClockG) {
		super();
		this.Latitude = latitude;
		this.Longitude = longitude;
		this.Timestamp = timestamp;
		this.ClockG = ClockG;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("\n" + id + "." + "Latitude=" + Latitude + "\nLongitude="
				+ Longitude + "\nTimestamp=" + Timestamp + "\nClock=" + ClockG);
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
}
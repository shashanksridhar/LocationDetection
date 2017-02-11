package com.tk.mapoi.labels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tk.mapoi.helper.Utils;

/**
 * The Class PlaceJSONParser - Parses JSON object returned from Google Places.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class PlaceJSONParser {

	/** The min dist between 2 points initialized to 0. */
	private double minDist = 0.0D;

	/** The min dist set to know if min distance has been set. */
	private boolean minDistSet = false;

	/**
	 * Receives a JSONObject and returns a list.
	 * 
	 * @param jObject
	 *            the JSON object of places
	 * @param lati
	 *            the centroid latitude of cluster
	 * @param longi
	 *            the centroid longitude of cluster
	 * @return the string of place
	 */
	public String parse(JSONObject jObject, double lati, double longi) {

		minDist = 0.0D;
		minDistSet = false;
		JSONArray jPlaces = null;
		try {
			// Retrieves all the elements in the 'places' array
			jPlaces = jObject.getJSONArray("results");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/*
		 * Invoking getPlaces with the array of json object where each json
		 * object represent a place
		 */
		return getPlaces(jPlaces, lati, longi);
	}

	/**
	 * Gets the places after parsing JSON.
	 * 
	 * @param jPlaces
	 *            the places in JSON format
	 * @param lati
	 *            the centroid latitude of cluster
	 * @param longi
	 *            the centroid longitude of cluster
	 * @return the places
	 */
	private String getPlaces(JSONArray jPlaces, double lati, double longi) {
		int placesCount = jPlaces.length();
		String placeInfo = null;

		// Taking each place, parses and adds to list object
		for (int i = 0; i < placesCount; i++) {
			try {
				// Call getPlace with place JSON object to parse the place
				PlaceLatiLongi placeLatiLongi = getPlace(
						(JSONObject) jPlaces.get(i), lati, longi);
				// Get the place with minimum distance from centroid
				if (!minDistSet) {
					minDist = placeLatiLongi.getdistance();
					minDistSet = true;
					placeInfo = placeLatiLongi.getPlaceInfo();
				} else {
					if (minDist > placeLatiLongi.getdistance()) {
						minDist = placeLatiLongi.getdistance();
						placeInfo = placeLatiLongi.getPlaceInfo();
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// Return place with least distance
		return placeInfo;
	}

	/**
	 * Parsing the Place JSON object and returning an object of type
	 * PlaceLatiLongi.
	 * 
	 * @param jPlace
	 *            the JSON object of place
	 * @param lati
	 *            the centroid latitude of cluster
	 * @param longi
	 *            the centroid longitude of cluster
	 * @return the place object
	 */
	private PlaceLatiLongi getPlace(JSONObject jPlace, double lati, double longi) {

		JSONArray placeTypes = new JSONArray();
		StringBuffer typeList = new StringBuffer();
		PlaceLatiLongi placeLatiLongi = null;

		try {
			placeTypes = jPlace.getJSONArray("types");
			// Get latitude of the place
			double latPlace = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");
			// Get longitude of the place
			double longPlace = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");
			// Calculate distance between the place and centroid
			double dist = Utils
					.getDistancePts(lati, latPlace, longi, longPlace);
			for (int i = 0; i < placeTypes.length(); i++) {
				typeList.append(placeTypes.getString(i) + ";");
			}
			if (typeList.length() > 0)
				typeList.setLength(typeList.length() - 1);

			// Make an object with type information of the place and distance
			// between it to the centroid
			placeLatiLongi = new PlaceLatiLongi(typeList.toString(), dist);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return placeLatiLongi;
	}
}

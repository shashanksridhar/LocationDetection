package com.tk.mapoi.labels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;

import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.Constants;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.helper.Connectivity;
import com.tk.mapoi.helper.Utils;

/**
 * The Class GetLabels. Gets labels from Google Places. Labels home and work
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class GetLabels {

	/** Time points for homework cluster. */
	private int timePoints[][] = new int[24][2];

	/** The home cluster points. */
	private int homeClusterPoints = 0;

	/** The work cluster points. */
	private int workClusterPoints = 0;

	/** The home cluster percentage. */
	private double homeClusterPercentage = 0.0D;

	/** The work cluster percentage. */
	private double workClusterPercentage = 0.0D;

	/** The place. */
	private String place = null;

	/**
	 * Initialize time points to get home work cluster. first column - hour.
	 * second column - number of points for that hour.
	 */
	public void initializeTimePoints() {
		for (int i = 0; i < 24; i++) {
			timePoints[i][0] = i;
			timePoints[i][1] = 0;
		}
	}

	/**
	 * Populate time points as per hour for homework cluster and calculate home
	 * and work cluster.
	 * 
	 * @param neighbours
	 *            - List of neighbours
	 * @param centroidLat
	 *            - Centroid Latitude of cluster
	 * @param centroidLong
	 *            - Centroid Longitude of Cluster
	 * @param locListSize
	 *            - Total list size
	 */
	@SuppressLint("DefaultLocale")
	public void calculateHomeWorkCluster(List<LocationDB> neighbours,
			double centroidLat, double centroidLong, long locListSize) {
		for (LocationDB neighbourPt : neighbours) {
			String[] tokens = neighbourPt.getClockG().split(" ");
			String[] time = tokens[3].split(":");
			int hour = Integer.parseInt(time[0]);
			timePoints[hour][1] = timePoints[hour][1] + 1;
		}

		// Home Assumption - User stays maximum time at his home between 00:00
		// to 05:00
		// Work Assumption - User goes to work between 10:00 to 17:00
		for (int i = 0; i < 24; i++) {
			if (i >= 0 && i <= 5) {
				homeClusterPoints += timePoints[i][1];
			}
			if (i >= 10 && i < 17) {
				workClusterPoints += timePoints[i][1];
			}
		}

		// Calculate the percentage of time user stayed at that place
		homeClusterPercentage = 100 * homeClusterPoints / locListSize;
		workClusterPercentage = 100 * workClusterPoints / locListSize;

		// Reinitialize home work cluster points count
		homeClusterPoints = 0;
		workClusterPoints = 0;

		Editor homeWorkEditor = GlobalParams.HOMEWORK_POINTS.edit();

		// If this is the maximum time user has spent between 00:00-05:00
		// store it as home cluster
		if (homeClusterPercentage > GlobalParams.MAX_HOME_CLUSTER_PERCENTAGE
				&& homeClusterPercentage >= workClusterPercentage) {
			GlobalParams.MAX_HOME_CLUSTER_PERCENTAGE = homeClusterPercentage;
			homeWorkEditor.putString("homeLatitude",
					String.valueOf(centroidLat));
			homeWorkEditor.putString("homeLongitude",
					String.valueOf(centroidLong));
			homeWorkEditor.commit();
			return;
		}

		// If user stays at a place between 10:00-17:00
		// for more than Constants.WORK_CLUSTER_PERCENTAGE, store it as work
		// cluster
		if (workClusterPercentage >= Constants.WORK_CLUSTER_PERCENTAGE) {
			homeWorkEditor.putString("workLatitude",
					String.valueOf(centroidLat));
			homeWorkEditor.putString("workLongitude",
					String.valueOf(centroidLong));
		}

		homeWorkEditor.commit();

	}

	/**
	 * Gets the google places labels.
	 * 
	 * @param centroidLat
	 *            - Centroid Latitude of cluster
	 * @param centroidLong
	 *            - Centroid Longitude of Cluster
	 */
	public void getGooglePlaces(double centroidLat, double centroidLong) {

		// If connected to wifi, query google places, Else set it to null.
		if (Connectivity.isConnectedWifi(GlobalParams.APP_CONTEXT)) {
			// Form Query for Google places
			StringBuilder listPlaces = new StringBuilder(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
			listPlaces.append("location=" + centroidLat + "," + centroidLong);
			listPlaces.append("&radius=" + Constants.PLACES_RADIUS);
			listPlaces.append("&sensor=true");
			listPlaces.append("&key=" + Constants.PLACES_API_KEY);
			listPlaces.append("&types=" + Constants.CLUSTER_NAME_AIRPORT + "|"
					+ Constants.CLUSTER_NAME_AQUARIUM + "|"
					+ Constants.CLUSTER_NAME_ART_GALLERY + "|"
					+ Constants.CLUSTER_NAME_ATM + "|"
					+ Constants.CLUSTER_NAME_BAKERY + "|"
					+ Constants.CLUSTER_NAME_BANK + "|"
					+ Constants.CLUSTER_NAME_BAR + "|"
					+ Constants.CLUSTER_NAME_BEAUTY_SALON + "|"
					+ Constants.CLUSTER_NAME_BOWLING_ALLEY + "|"
					+ Constants.CLUSTER_NAME_BUS_STATION + "|"
					+ Constants.CLUSTER_NAME_CAFE + "|"
					+ Constants.CLUSTER_NAME_CASINO + "|"
					+ Constants.CLUSTER_NAME_CEMETRY + "|"
					+ Constants.CLUSTER_NAME_CHURCH + "|"
					+ Constants.CLUSTER_NAME_COURTSHOUSE + "|"
					+ Constants.CLUSTER_NAME_DENTIST + "|"
					+ Constants.CLUSTER_NAME_DOCTOR + "|"
					+ Constants.CLUSTER_NAME_FOOD + "|"
					+ Constants.CLUSTER_NAME_FUNERAL_HOME + "|"
					+ Constants.CLUSTER_NAME_GAS_STATION + "|"
					+ Constants.CLUSTER_NAME_GYM + "|"
					+ Constants.CLUSTER_NAME_HAIR_CARE + "|"
					+ Constants.CLUSTER_NAME_HEALTH + "|"
					+ Constants.CLUSTER_NAME_HOME + "|"
					+ Constants.CLUSTER_NAME_HOSPITAL + "|"
					+ Constants.CLUSTER_NAME_INDOOR_GAMES + "|"
					+ Constants.CLUSTER_NAME_LAUNDRY + "|"
					+ Constants.CLUSTER_NAME_LIBRARY + "|"
					+ Constants.CLUSTER_NAME_MEAL_DELIVERY + "|"
					+ Constants.CLUSTER_NAME_MEAL_TAKEAWAY + "|"
					+ Constants.CLUSTER_NAME_MEDICAL + "|"
					+ Constants.CLUSTER_NAME_MOSQUE + "|"
					+ Constants.CLUSTER_NAME_MOVIE + "|"
					+ Constants.CLUSTER_NAME_MUSEUM + "|"
					+ Constants.CLUSTER_NAME_NIGHT_CLUB + "|"
					+ Constants.CLUSTER_NAME_OTHER + "|"
					+ Constants.CLUSTER_NAME_OUTDOOR_GAMES + "|"
					+ Constants.CLUSTER_NAME_PARK + "|"
					+ Constants.CLUSTER_NAME_PARKING + "|"
					+ Constants.CLUSTER_NAME_PHARMACY + "|"
					+ Constants.CLUSTER_NAME_PHYSIOTHERAPIST + "|"
					+ Constants.CLUSTER_NAME_POLICE + "|"
					+ Constants.CLUSTER_NAME_POST_OFFICE + "|"
					+ Constants.CLUSTER_NAME_RESTAURANT + "|"
					+ Constants.CLUSTER_NAME_SALON + "|"
					+ Constants.CLUSTER_NAME_SCHOOL + "|"
					+ Constants.CLUSTER_NAME_SHOPPING + "|"
					+ Constants.CLUSTER_NAME_SHOPPNG_MALL + "|"
					+ Constants.CLUSTER_NAME_SPA + "|"
					+ Constants.CLUSTER_NAME_STADIUM + "|"
					+ Constants.CLUSTER_NAME_STORE + "|"
					+ Constants.CLUSTER_NAME_SUBWAY_STATION + "|"
					+ Constants.CLUSTER_NAME_SUPER_MARKET + "|"
					+ Constants.CLUSTER_NAME_TEMPLE + "|"
					+ Constants.CLUSTER_NAME_TRAIN_STATION + "|"
					+ Constants.CLUSTER_NAME_TRAVEL + "|"
					+ Constants.CLUSTER_NAME_UNIVERSITY + "|"
					+ Constants.CLUSTER_NAME_VET + "|"
					+ Constants.CLUSTER_NAME_WORK + "|"
					+ Constants.CLUSTER_NAME_WORSHIP + "|"
					+ Constants.CLUSTER_NAME_ZOO);

			String data = null;
			JSONObject jObject;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				// Execute query
				data = downloadUrl(listPlaces.toString());
				jObject = new JSONObject(data);

				// Get the place for the latitude and longitude from Google
				// places
				place = placeJsonParser.parse(jObject, centroidLat,
						centroidLong);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			place = null;
		}
	}

	/**
	 * Gets the cluster name id for google places.
	 * 
	 * @return Cluster id from Constants Class
	 */
	public int getClusterNameIDForGooglePlaces() {
		if (place == null) {
			return Constants.CLUSTER_ID_OTHER;
		}

		// Get the place and store its int value in clusterNameId
		if (place.contains(Constants.CLUSTER_NAME_UNIVERSITY)
				|| place.contains(Constants.CLUSTER_NAME_SCHOOL)) {
			return Constants.CLUSTER_ID_UNIVERSITY;
		} else if (place.contains(Constants.CLUSTER_NAME_MOVIE)) {
			return Constants.CLUSTER_ID_MOVIE;
		} else if (place.contains(Constants.CLUSTER_NAME_GYM)) {
			return Constants.CLUSTER_ID_GYM;
		} else if (place.contains(Constants.CLUSTER_NAME_TRAIN_STATION)
				|| place.contains(Constants.CLUSTER_NAME_SUBWAY_STATION)
				|| place.contains(Constants.CLUSTER_NAME_AIRPORT)
				|| place.contains(Constants.CLUSTER_NAME_BUS_STATION)) {
			return Constants.CLUSTER_ID_TRAVEL;
		} else if (place.contains(Constants.CLUSTER_NAME_STORE)
				|| place.contains(Constants.CLUSTER_NAME_SHOPPNG_MALL)
				|| place.contains(Constants.CLUSTER_NAME_SUPER_MARKET)) {
			return Constants.CLUSTER_ID_SHOPPING;
		} else if (place.contains(Constants.CLUSTER_NAME_HOSPITAL)
				|| place.contains(Constants.CLUSTER_NAME_DOCTOR)
				|| place.contains(Constants.CLUSTER_NAME_PHARMACY)
				|| place.contains(Constants.CLUSTER_NAME_PHYSIOTHERAPIST)
				|| place.contains(Constants.CLUSTER_NAME_DENTIST)
				|| place.contains(Constants.CLUSTER_NAME_HEALTH)
				|| place.contains(Constants.CLUSTER_NAME_VET)) {
			return Constants.CLUSTER_ID_MEDICAL;
		} else if (place.contains(Constants.CLUSTER_NAME_CHURCH)
				|| place.contains(Constants.CLUSTER_NAME_MOSQUE)
				|| place.contains(Constants.CLUSTER_NAME_TEMPLE)
				|| place.contains(Constants.CLUSTER_NAME_WORSHIP)) {
			return Constants.CLUSTER_ID_WORSHIP;
		} else if (place.contains(Constants.CLUSTER_NAME_RESTAURANT)
				|| place.contains(Constants.CLUSTER_NAME_CAFE)
				|| place.contains(Constants.CLUSTER_NAME_BAKERY)
				|| place.contains(Constants.CLUSTER_NAME_BAR)
				|| place.contains(Constants.CLUSTER_NAME_FOOD)
				|| place.contains(Constants.CLUSTER_NAME_MEAL_DELIVERY)
				|| place.contains(Constants.CLUSTER_NAME_MEAL_TAKEAWAY)
				|| place.contains(Constants.CLUSTER_NAME_NIGHT_CLUB)) {
			return Constants.CLUSTER_ID_RESTAURANT;
		} else if (place.contains(Constants.CLUSTER_NAME_ATM)
				|| place.contains(Constants.CLUSTER_NAME_BANK)) {
			return Constants.CLUSTER_ID_BANK;
		} else if (place.contains(Constants.CLUSTER_NAME_BEAUTY_SALON)
				|| place.contains(Constants.CLUSTER_NAME_SPA)
				|| place.contains(Constants.CLUSTER_NAME_HAIR_CARE)) {
			return Constants.CLUSTER_ID_SALON;
		} else if (place.contains(Constants.CLUSTER_NAME_BOWLING_ALLEY)
				|| place.contains(Constants.CLUSTER_NAME_CASINO)) {
			return Constants.CLUSTER_ID_INDOOR_GAMES;
		} else if (place.contains(Constants.CLUSTER_NAME_CEMETRY)
				|| place.contains(Constants.CLUSTER_NAME_FUNERAL_HOME)) {
			return Constants.CLUSTER_ID_CEMETRY;
		} else if (place.contains(Constants.CLUSTER_NAME_COURTSHOUSE)) {
			return Constants.CLUSTER_ID_COURTSHOUSE;
		} else if (place.contains(Constants.CLUSTER_NAME_GAS_STATION)) {
			return Constants.CLUSTER_ID_GAS_STATION;
		} else if (place.contains(Constants.CLUSTER_NAME_LAUNDRY)) {
			return Constants.CLUSTER_ID_LAUNDRY;
		} else if (place.contains(Constants.CLUSTER_NAME_LIBRARY)) {
			return Constants.CLUSTER_ID_LIBRARY;
		} else if (place.contains(Constants.CLUSTER_NAME_PARKING)) {
			return Constants.CLUSTER_ID_PARKING;
		} else if (place.contains(Constants.CLUSTER_NAME_PARK)
				|| place.contains(Constants.CLUSTER_NAME_AQUARIUM)
				|| place.contains(Constants.CLUSTER_NAME_ART_GALLERY)
				|| place.contains(Constants.CLUSTER_NAME_MUSEUM)
				|| place.contains(Constants.CLUSTER_NAME_ZOO)) {
			return Constants.CLUSTER_ID_LEISURE;
		} else if (place.contains(Constants.CLUSTER_NAME_POLICE)) {
			return Constants.CLUSTER_ID_POLICE;
		} else if (place.contains(Constants.CLUSTER_NAME_POST_OFFICE)) {
			return Constants.CLUSTER_ID_POST_OFFICE;
		} else if (place.contains(Constants.CLUSTER_NAME_STADIUM)) {
			return Constants.CLUSTER_ID_OUTDOOR_SPORTS;
		}

		return Constants.CLUSTER_ID_OTHER;

	}

	/**
	 * Get the google places from url.
	 * 
	 * @param strUrl
	 *            the url for Google places
	 * @return the string from Google places
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();
			iStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}

		return data;
	}

	/**
	 * Gets clusterNameId of cluster.
	 * 
	 * @param centroidLat
	 *            - Centroid Latitude of cluster
	 * @param centroidLong
	 *            - Centroid Longitude of cluster
	 * @return cluster id of place
	 */
	public int clusterName(double centroidLat, double centroidLong) {
		int clusterNameId = 0;

		// Get work and home cluster location stored in work and home
		// cluster
		String workLatitude = GlobalParams.HOMEWORK_POINTS.getString(
				"workLatitude", "0");
		String workLongitude = GlobalParams.HOMEWORK_POINTS.getString(
				"workLongitude", "0");
		String homeLatitude = GlobalParams.HOMEWORK_POINTS.getString(
				"homeLatitude", "0");
		String homeLongitude = GlobalParams.HOMEWORK_POINTS.getString(
				"homeLongitude", "0");

		// Check if current cluster corresponds to home
		if (Utils.getDistancePts(Double.parseDouble(homeLatitude), centroidLat,
				Double.parseDouble(homeLongitude), centroidLong) <= 2 * Constants.EPSILON) {
			clusterNameId = Constants.CLUSTER_ID_HOME;
			return clusterNameId;
		}

		// Check if it is work
		if (Utils.getDistancePts(Double.parseDouble(workLatitude), centroidLat,
				Double.parseDouble(workLongitude), centroidLong) <= 2 * Constants.EPSILON) {
			clusterNameId = Constants.CLUSTER_ID_WORK;
			return clusterNameId;
		}

		getGooglePlaces(centroidLat, centroidLong);
		return getClusterNameIDForGooglePlaces();
	}
}

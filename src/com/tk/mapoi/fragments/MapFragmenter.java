package com.tk.mapoi.fragments;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tk.mapoi.R;
import com.tk.mapoi.globals.Constants;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.database.ClusterInfoDB;

/**
 * The Class MapFragmenter - UI for MAPOI option in Left Panel
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class MapFragmenter extends Fragment {

	/**
	 * Instantiates a new map fragmenter.
	 */
	public MapFragmenter() {
	}

	/** The google map. */
	private static GoogleMap googleMap;

	/** The map view. */
	private MapView mapView;

	/**
	 * Gets the icon.
	 * 
	 * @param clusterId
	 *            the cluster id
	 * @return the icon
	 */
	public int getIcon(int clusterId) {

		switch (clusterId) {
		case Constants.CLUSTER_ID_OTHER:
			return R.drawable.ic_other_cluster;
		case Constants.CLUSTER_ID_HOME:
			return R.drawable.ic_home_cluster;
		case Constants.CLUSTER_ID_WORK:
			return R.drawable.ic_work_cluster;
		case Constants.CLUSTER_ID_UNIVERSITY:
			return R.drawable.ic_university_cluster;
		case Constants.CLUSTER_ID_RESTAURANT:
			return R.drawable.ic_restaurant_cluster;
		case Constants.CLUSTER_ID_SHOPPING:
			return R.drawable.ic_shopping_cluster;
		case Constants.CLUSTER_ID_LIBRARY:
			return R.drawable.ic_library_cluster;
		case Constants.CLUSTER_ID_BANK:
			return R.drawable.ic_bank_cluster;
		case Constants.CLUSTER_ID_CEMETRY:
			return R.drawable.ic_cemetry_cluster;
		case Constants.CLUSTER_ID_COURTSHOUSE:
			return R.drawable.ic_courthouse_cluster;
		case Constants.CLUSTER_ID_GAS_STATION:
			return R.drawable.ic_gas_cluster;
		case Constants.CLUSTER_ID_LAUNDRY:
			return R.drawable.ic_laundry_cluster;
		case Constants.CLUSTER_ID_PARKING:
			return R.drawable.ic_parking_cluster;
		case Constants.CLUSTER_ID_POLICE:
			return R.drawable.ic_police_cluster;
		case Constants.CLUSTER_ID_POST_OFFICE:
			return R.drawable.ic_post_office_cluster;
		case Constants.CLUSTER_ID_TRAVEL:
			return R.drawable.ic_travel_cluster;
		case Constants.CLUSTER_ID_LEISURE:
			return R.drawable.ic_amusement_cluster;
		case Constants.CLUSTER_ID_GYM:
			return R.drawable.ic_gym_cluster;
		case Constants.CLUSTER_ID_MEDICAL:
			return R.drawable.ic_hospital_cluster;
		case Constants.CLUSTER_ID_INDOOR_GAMES:
			return R.drawable.ic_indoor_sports_cluster;
		case Constants.CLUSTER_ID_MOVIE:
			return R.drawable.ic_movie_cluster;
		case Constants.CLUSTER_ID_OUTDOOR_SPORTS:
			return R.drawable.ic_outdoor_sports_cluster;
		case Constants.CLUSTER_ID_SALON:
			return R.drawable.ic_salon_cluster;
		case Constants.CLUSTER_ID_WORSHIP:
			return R.drawable.ic_worship_cluster;
		default:
			return R.drawable.ic_other_cluster;
		}
	}

	/**
	 * Gets the icon title.
	 * 
	 * @param clusterId
	 *            the cluster id
	 * @return the icon title
	 */
	@SuppressLint("DefaultLocale")
	public String getIconTitle(int clusterId) {

		switch (clusterId) {
		case Constants.CLUSTER_ID_OTHER:
			return "Not Identified";
		case Constants.CLUSTER_ID_HOME:
			return Constants.CLUSTER_NAME_HOME.toUpperCase();
		case Constants.CLUSTER_ID_WORK:
			return Constants.CLUSTER_NAME_WORK.toUpperCase();
		case Constants.CLUSTER_ID_UNIVERSITY:
			return Constants.CLUSTER_NAME_UNIVERSITY.toUpperCase() + "/"
					+ Constants.CLUSTER_NAME_SCHOOL.toUpperCase();
		case Constants.CLUSTER_ID_RESTAURANT:
			return Constants.CLUSTER_NAME_RESTAURANT.toUpperCase();
		case Constants.CLUSTER_ID_SHOPPING:
			return Constants.CLUSTER_NAME_SHOPPING.toUpperCase();
		case Constants.CLUSTER_ID_LIBRARY:
			return Constants.CLUSTER_NAME_LIBRARY.toUpperCase();
		case Constants.CLUSTER_ID_BANK:
			return Constants.CLUSTER_NAME_BANK.toUpperCase();
		case Constants.CLUSTER_ID_CEMETRY:
			return Constants.CLUSTER_NAME_CEMETRY.toUpperCase();
		case Constants.CLUSTER_ID_COURTSHOUSE:
			return Constants.CLUSTER_NAME_COURTSHOUSE.toUpperCase();
		case Constants.CLUSTER_ID_GAS_STATION:
			return Constants.CLUSTER_NAME_GAS_STATION.toUpperCase();
		case Constants.CLUSTER_ID_LAUNDRY:
			return Constants.CLUSTER_NAME_LAUNDRY.toUpperCase();
		case Constants.CLUSTER_ID_PARKING:
			return Constants.CLUSTER_NAME_PARKING.toUpperCase();
		case Constants.CLUSTER_ID_POLICE:
			return Constants.CLUSTER_NAME_POLICE.toUpperCase();
		case Constants.CLUSTER_ID_POST_OFFICE:
			return Constants.CLUSTER_NAME_POST_OFFICE.toUpperCase();
		case Constants.CLUSTER_ID_TRAVEL:
			return Constants.CLUSTER_NAME_TRAVEL.toUpperCase();
		case Constants.CLUSTER_ID_LEISURE:
			return Constants.CLUSTER_NAME_MUSEUM.toUpperCase() + "/"
					+ Constants.CLUSTER_NAME_PARK.toUpperCase();
		case Constants.CLUSTER_ID_GYM:
			return Constants.CLUSTER_NAME_GYM.toUpperCase();
		case Constants.CLUSTER_ID_MEDICAL:
			return Constants.CLUSTER_NAME_MEDICAL.toUpperCase();
		case Constants.CLUSTER_ID_INDOOR_GAMES:
			return Constants.CLUSTER_NAME_INDOOR_GAMES.toUpperCase();
		case Constants.CLUSTER_ID_MOVIE:
			return Constants.CLUSTER_NAME_MOVIE.toUpperCase();
		case Constants.CLUSTER_ID_OUTDOOR_SPORTS:
			return Constants.CLUSTER_NAME_OUTDOOR_GAMES.toUpperCase();
		case Constants.CLUSTER_ID_SALON:
			return Constants.CLUSTER_NAME_SALON.toUpperCase();
		case Constants.CLUSTER_ID_WORSHIP:
			return Constants.CLUSTER_NAME_WORSHIP.toUpperCase();
		default:
			return "Not Identified";
		}
	}

	/**
	 * Display current day's cluster
	 */
	public void showCurrentDayCluster() {
		long timestamp = System.currentTimeMillis();
		long currentDay = timestamp
				- ((timestamp + TimeZone.getDefault().getOffset(timestamp)) % (24L * 60L * 60L * 1000L));

		String currentTimeString = String.valueOf(new Date((System
				.currentTimeMillis())));
		String[] dateTokens = currentTimeString.split(" ");
		String[] timeTokens = dateTokens[3].split(":");
		int hour = Integer.parseInt(timeTokens[0]);
		int mins = Integer.parseInt(timeTokens[1]);
		SharedPreferences lastCluster;
		Set<String> clusterSet = null;

		// Don't show any cluster in the first few minutes of the
		// day - 00:00 - 00:10
		if (!(hour == 0 && mins <= 10)) {
			synchronized (GlobalParams.LAST_CLUSTER_LOCK) {
				lastCluster = getActivity().getApplicationContext()
						.getSharedPreferences("LastClusterInfo",
								Context.MODE_PRIVATE);
				clusterSet = lastCluster.getStringSet("lastClusterSet", null);
			}
		}

		// Get all clusters and display it on map with appropriate
		// labels
		double lati = 0.0D;
		double longi = 0.0D;
		if (clusterSet != null && clusterSet.size() != 0) {
			for (String cluster : clusterSet) {
				String[] tokens = cluster.split(";");
				if (currentDay != Long.parseLong(tokens[4])) {
					break;
				}
				lati = Double.parseDouble(tokens[0]);
				longi = Double.parseDouble(tokens[1]);
				int icon = getIcon(Integer.parseInt(tokens[2]));
				String iconTitle = getIconTitle(Integer.parseInt(tokens[2]));

				MarkerOptions marker = new MarkerOptions().position(new LatLng(
						lati, longi));

				marker.icon(BitmapDescriptorFactory.fromResource(icon));
				marker.title(iconTitle);

				googleMap.addMarker(marker);
			}
		}

		// Zoom to last known location of user
		SharedPreferences clusterLatilongi = getActivity()
				.getApplicationContext().getSharedPreferences(
						"currentLocation", Context.MODE_PRIVATE);
		double latitude = Double.parseDouble(clusterLatilongi.getString(
				"currentLati", "0"));
		double longitude = Double.parseDouble(clusterLatilongi.getString(
				"currentLongi", "0"));

		if (latitude != 0 && longitude != 0) {
			zoomCamera(latitude, longitude);
		}
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		Date currentDate = new Date(stamp.getTime());
		String dayTokens[] = currentDate.toString().split(" ");

		String toastMsg = "Showing POI For " + dayTokens[0] + " "
				+ dayTokens[1] + " " + dayTokens[2] + " "
				+ dayTokens[dayTokens.length - 1];
		Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Display older day's cluster
	 */
	public void showOlderDaysCluster() {
		// Show cluster for older days
		List<ClusterInfoDB> clusterList = GlobalParams.CLUSTER_OLD_DAY_LIST;

		double lati = 0.0D;
		double longi = 0.0D;
		if (clusterList != null && clusterList.size() != 0) {
			for (ClusterInfoDB clusterInfoDB : clusterList) {
				lati = Double.parseDouble(clusterInfoDB.getLatitude());
				longi = Double.parseDouble(clusterInfoDB.getLongitude());
				int icon = getIcon(Integer.parseInt(clusterInfoDB
						.getClusterId()));
				String iconTitle = getIconTitle(Integer.parseInt(clusterInfoDB
						.getClusterId()));

				MarkerOptions marker = new MarkerOptions().position(new LatLng(
						lati, longi));

				marker.icon(BitmapDescriptorFactory.fromResource(icon));
				marker.title(iconTitle);

				googleMap.addMarker(marker);

			}
			zoomCamera(lati, longi);

		} else {

			// If no clusters found for the day, zoom to last known
			// location
			SharedPreferences clusterLatilongi = getActivity()
					.getApplicationContext().getSharedPreferences(
							"currentLocation", Context.MODE_PRIVATE);
			double latitude = Double.parseDouble(clusterLatilongi.getString(
					"currentLati", "0"));
			double longitude = Double.parseDouble(clusterLatilongi.getString(
					"currentLongi", "0"));

			if (latitude != 0 && longitude != 0) {
				zoomCamera(latitude, longitude);
			}
		}

		String dayTokens[] = GlobalParams.CLUSTER_DISPLAY_DAY.toString().split(
				" ");

		String toastMsg = "Showing POI For " + dayTokens[0] + " "
				+ dayTokens[1] + " " + dayTokens[2] + " "
				+ dayTokens[dayTokens.length - 1];
		Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Zoom camera of map
	 * 
	 * @param latitude
	 *            - latitude to zoom
	 * @param longitude
	 *            - longitude to zoom
	 */
	public void zoomCamera(double latitude, double longitude) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(12).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	/**
	 * 
	 * Shows POIs for the date selected on the map
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

		MapsInitializer.initialize(getActivity());

		switch (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity())) {
		case ConnectionResult.SUCCESS:
			mapView = (MapView) rootView.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);

			// Gets to GoogleMap from the MapView and does initialization stuff
			if (mapView != null) {
				googleMap = mapView.getMap();
				googleMap.clear();

				// Display clusters
				if (GlobalParams.CURRENT_DAY) {
					showCurrentDayCluster();
				} else {
					showOlderDaysCluster();
				}
			}
			break;
		case ConnectionResult.SERVICE_MISSING:
			Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT)
					.show();
			break;
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
			Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			Toast.makeText(
					getActivity(),
					GooglePlayServicesUtil
							.isGooglePlayServicesAvailable(getActivity()),
					Toast.LENGTH_SHORT).show();
		}
		return rootView;
	}

	/**
	 * 
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}

	/**
	 * 
	 * @see android.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 
	 * @see android.app.Fragment#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
}

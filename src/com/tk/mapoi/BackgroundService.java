package com.tk.mapoi;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.tk.mapoi.cluster.GenerateClustersTask;
import com.tk.mapoi.cluster.GenerateClustersTaskOldDay;
import com.tk.mapoi.cluster.GetHomeWork;
import com.tk.mapoi.database.DatabaseHelper;
import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.Constants;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.helper.Connectivity;
import com.tk.mapoi.helper.LocationHelper;
import com.tk.mapoi.helper.Utils;

/**
 * BackgroundService --- the Service that runs when Alarm is called. Logs
 * location information in database and clusters it
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */

public class BackgroundService extends Service implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {

	/** The src. */
	private AndroidConnectionSource src;

	/** db for purging. */
	private DatabaseHelper dbPurge;

	/** The locationclient. */
	private LocationClient locationclient;

	/** Current Location info. */
	private LocationDB locInfodb = null;

	/** Location DB dao. */
	private RuntimeExceptionDao<LocationDB, Object> locDao;

	/**
	 * 
	 * @see android.app.Service#onCreate() Create Location client and location
	 *      db dao
	 */
	@Override
	public void onCreate() {
		// Check if google play services is available; create new location
		// client and connect
		int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resp == ConnectionResult.SUCCESS) {
			locationclient = new LocationClient(this, this, this);
			locationclient.connect();
		} else {
			Toast.makeText(this, "Google Play Service Error " + resp,
					Toast.LENGTH_LONG).show();
		}

		// Location DB dao
		OrmLiteSqliteOpenHelper sql = new DatabaseHelper(
				getApplicationContext());
		src = new AndroidConnectionSource(sql);
		try {
			locDao = RuntimeExceptionDao.createDao(src, LocationDB.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Set application context
		GlobalParams.APP_CONTEXT = getApplicationContext();

	}

	/**
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 *      Log location information in db and start clustering
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		// If background service is enabled
		if (prefs.getBoolean("keystring", true)) {
			// Setting Notification Services
			Utils.notificationBar(getApplicationContext());

			// Log Data
			logData();

			// Cluster and Label Data
			clusterAndLabelData();
		}
		return 1;
	}

	/**
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.
	 *      OnConnectionFailedListener
	 *      #onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// Connection status fail msg
	}

	/**
	 * 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
	 *      #onConnected(android.os.Bundle)
	 * 
	 *      Call location listener every minute
	 */
	@Override
	public void onConnected(Bundle arg0) {

		LocationRequest locRequest = new LocationRequest();

		if (Connectivity.isConnectedWifi(getApplicationContext())) {
			locRequest
					.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		} else {
			locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		}

		// Displacement in meters
		locRequest.setSmallestDisplacement(0);
		locRequest.setInterval(60000);
		locRequest.setFastestInterval(60000);
		locationclient.requestLocationUpdates(locRequest, this);
	}

	/**
	 * 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
	 *      #onDisconnected()
	 */
	@Override
	public void onDisconnected() {
		if (locationclient != null) {
			locationclient.removeLocationUpdates(this);
		}
	}

	/**
	 * 
	 * @see com.google.android.gms.location.LocationListener#onLocationChanged(android
	 *      .location.Location)
	 * 
	 *      Get current location and store it in locinfodb
	 */
	@Override
	public void onLocationChanged(Location loc) {

		loc = locationclient.getLastLocation();
		if (new LocationHelper().isBetterLocation(loc)) {
			String latitude = String.valueOf(loc.getLatitude());
			String longitude = String.valueOf(loc.getLongitude());
			long timestamp = System.currentTimeMillis();
			Date d = new Date(System.currentTimeMillis());
			String clock = String.valueOf(d);
			locInfodb = new LocationDB(latitude, longitude, timestamp, clock);

			// Put current latitude and longitude in Shared Prefs for map to
			// zoom to current location
			SharedPreferences latilongiSP = getApplicationContext()
					.getSharedPreferences("currentLocation",
							Context.MODE_PRIVATE);
			Editor latilongiEditor = latilongiSP.edit();
			latilongiEditor.putString("currentLati", latitude);
			latilongiEditor.putString("currentLongi", longitude);
			latilongiEditor.commit();
		}
	}

	/**
	 * Logs current location information to database
	 */
	public void logData() {
		// Log location information
		if (locInfodb != null) {
			try {
				locDao.create(locInfodb);
			} catch (Exception s) {
				s.printStackTrace();
			}
		}
	}

	/**
	 * Sends data for clustering for current day Clustering older days data not
	 * yet clustered Purges data in location table Calculates home and work
	 * cluster
	 */
	public void clusterAndLabelData() {

		// Getting last run time of cluster daemon and purge
		SharedPreferences clusterSP = getApplicationContext()
				.getSharedPreferences("Alarms", Context.MODE_PRIVATE);
		long lastPurge = clusterSP.getLong("lastPurge", 0);
		long lastClusterDaemon = clusterSP.getLong("lastClusterDaemon", 0);

		// Generate Clusters if last runtime is greater than
		// Constants.CLUSTER_DAEMON_INTERVAL
		if ((System.currentTimeMillis() - lastClusterDaemon) >= (Constants.CLUSTER_DAEMON_INTERVAL * 60L * 1000L)) {
			// Cluster Current Data
			clusterCurrentData(clusterSP);

			// Cluster Old Data
			clusterOldData();

		}

		// Purge to run once per day and getting home and work cluster every
		// Constants.CLUSTER_RUN_ONCE_PER_DAY_INTERVAL hours
		if ((System.currentTimeMillis() - lastPurge) >= (Constants.CLUSTER_RUN_ONCE_PER_DAY_INTERVAL * 60L * 60L * 1000L)) {
			// Purge Data older than Constants.PURGE_INTERVAL days
			purgeData(clusterSP);

			// Cluster home and work
			clusterHomeAndWork();
		}

	}

	/**
	 * Purges data older than Constants.PURGE_INTERVAL days
	 * 
	 * @param clusterSP
	 *            - Shared Prefs for setting the current time to last run of
	 *            this functionality
	 */
	@SuppressWarnings("rawtypes")
	public void purgeData(SharedPreferences clusterSP) {

		// Setting last runtime of purge
		clusterSP = getApplicationContext().getSharedPreferences("Alarms",
				Context.MODE_PRIVATE);
		Editor alarmEditor = clusterSP.edit();
		alarmEditor.putLong("lastPurge", System.currentTimeMillis());
		alarmEditor.commit();

		dbPurge = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		RuntimeExceptionDao<LocationDB, Integer> clusterDaoPurge = dbPurge
				.locInfodbRuntimeExceptionDao();
		QueryBuilder<LocationDB, Integer> queryBuilder = clusterDaoPurge
				.queryBuilder();
		queryBuilder.limit(999L);
		Where where = queryBuilder.where();
		PreparedQuery<LocationDB> pq;
		long currentTime = System.currentTimeMillis();
		try {

			where.le("Timestamp", currentTime - Constants.PURGE_INTERVAL);
			pq = queryBuilder.prepare();
			List<LocationDB> list = clusterDaoPurge.query(pq);
			clusterDaoPurge.delete(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calculates Home and Work cluster
	 */
	public void clusterHomeAndWork() {
		synchronized (GlobalParams.APP_CONTEXT) {
			GlobalParams.DB_GET_HOMEWORK = OpenHelperManager.getHelper(
					getApplicationContext(), DatabaseHelper.class);
			GlobalParams.HOMEWORK_POINTS = getApplicationContext()
					.getSharedPreferences("HomeWorkPoints",
							Context.MODE_PRIVATE);
			new GetHomeWork().execute("do");
		}
	}

	/**
	 * Clusters Older days data not yet stored in ClusterInfoDB table
	 */
	public void clusterOldData() {
		if (Connectivity.isConnectedWifi(getApplicationContext())) {
			GlobalParams.DB_CLUSTER_TABLE_OLD_DAY = OpenHelperManager
					.getHelper(this, DatabaseHelper.class);

			// Start clustering for older days
			new GenerateClustersTaskOldDay().execute("do");
		}
	}

	/**
	 * Clusters current day's location data
	 * 
	 * @param clusterSP
	 *            - shared Prefs to set last run of this functionality to
	 *            current time.
	 */
	public void clusterCurrentData(SharedPreferences clusterSP) {
		// Setting last runtime of cluster daemon to current time
		Editor alarmEditor = clusterSP.edit();
		alarmEditor.putLong("lastClusterDaemon", System.currentTimeMillis());
		alarmEditor.commit();

		// Start clustering
		new GenerateClustersTask().execute("do");
	}
}

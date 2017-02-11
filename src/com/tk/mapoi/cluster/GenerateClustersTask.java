package com.tk.mapoi.cluster;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.tk.mapoi.database.ClusterInfoDB;
import com.tk.mapoi.database.DatabaseHelper;
import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.helper.Connectivity;

/**
 * The Async Class GenerateClustersTask to cluster every
 * Constants.CLUSTER_DAEMON_INTERVAL
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class GenerateClustersTask extends AsyncTask<String, Integer, String> {

	/** Location list. */
	private List<LocationDB> locList;

	/** Cluster info list. */
	private Set<ClusterInfoDB> clusterInfoList = new HashSet<ClusterInfoDB>();

	/** To check if it is already running */
	private static boolean isRunning = false;

	/**
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected String doInBackground(String... url) {
		if (!isRunning) {
			isRunning = true;

			// Get location information for today
			GlobalParams.DB_CLUSTER = OpenHelperManager.getHelper(
					GlobalParams.APP_CONTEXT, DatabaseHelper.class);
			RuntimeExceptionDao<LocationDB, Integer> cellDao = GlobalParams.DB_CLUSTER
					.locInfodbRuntimeExceptionDao();
			QueryBuilder<LocationDB, Integer> queryBuilder = cellDao
					.queryBuilder();
			Where where = queryBuilder.where();
			PreparedQuery<LocationDB> pq;

			long timestamp = System.currentTimeMillis();
			long currentDay = timestamp
					- ((timestamp + TimeZone.getDefault().getOffset(timestamp)) % (24L * 60L * 60L * 1000L));

			try {
				where.ge("Timestamp", currentDay);
				pq = queryBuilder.prepare();
				locList = cellDao.query(pq);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			boolean getGooglePlaces = false;
			GlobalParams.HOMEWORK_POINTS = GlobalParams.APP_CONTEXT
					.getSharedPreferences("HomeWorkPoints",
							Context.MODE_PRIVATE);
			clusterInfoList = new HashSet<ClusterInfoDB>();
			// Cluster
			if (Connectivity.isConnectedWifi(GlobalParams.APP_CONTEXT)) {
				getGooglePlaces = true;
				clusterInfoList
						.addAll(new DBScan().applyDbscan(locList, false));
			}

			// Save cluster information in SharedPrefs to display
			synchronized (GlobalParams.LAST_CLUSTER_LOCK) {
				if (getGooglePlaces) {
					SharedPreferences lastCluster = GlobalParams.APP_CONTEXT
							.getSharedPreferences("LastClusterInfo",
									Context.MODE_PRIVATE);
					Set<String> clusterSet = new HashSet<String>();
					Editor clusterEditor = lastCluster.edit();
					for (ClusterInfoDB cluster : clusterInfoList) {
						String clusterSave = cluster.getLatitude() + ";"
								+ cluster.getLongitude() + ";"
								+ cluster.getClusterId() + ";"
								+ cluster.getTimestamp() + ";" + currentDay;
						clusterSet.add(clusterSave);
					}
					clusterEditor.putStringSet("lastClusterSet", clusterSet);
					clusterEditor.commit();
				}
			}
			isRunning = false;
		}
		return "done";
	}

}

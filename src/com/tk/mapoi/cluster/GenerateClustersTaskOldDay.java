package com.tk.mapoi.cluster;

import java.sql.SQLException;
import java.util.Date;
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
import com.tk.mapoi.helper.Utils;

/**
 * The Async Class GenerateClustersTaskOldDay for older days cluster which are
 * not yet clustered.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class GenerateClustersTaskOldDay extends
		AsyncTask<String, Integer, String> {

	/** The location list for cluster old day. */
	private List<LocationDB> locListClusterOldDay;

	/** The cluster info list for cluster old day. */
	private Set<ClusterInfoDB> clusterInfoListOldDay = new HashSet<ClusterInfoDB>();

	/** Current cluster info */
	private ClusterInfoDB clusterInfoDB;

	/** Cluster DB dao. */
	private RuntimeExceptionDao<ClusterInfoDB, Integer> clusterDaoClusterOldDay;

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
			GlobalParams.DB_CLUSTER_OLD_DAY = OpenHelperManager.getHelper(
					GlobalParams.APP_CONTEXT, DatabaseHelper.class);

			clusterDaoClusterOldDay = GlobalParams.DB_CLUSTER_TABLE_OLD_DAY
					.clusterInfoRuntimeExceptionDao();

			// Cluster all old days not yet clustered
			long timestamp = System.currentTimeMillis();
			long currentDay = timestamp
					- ((timestamp + TimeZone.getDefault().getOffset(timestamp)) % (24L * 60L * 60L * 1000L));

			// Get last clustered old day time
			SharedPreferences appData = GlobalParams.APP_CONTEXT
					.getSharedPreferences("AppData", Context.MODE_PRIVATE);
			long lastClusterPerDayRunDate = appData.getLong(
					"LastClusterOldDayRunDate", currentDay);

			while (lastClusterPerDayRunDate <= currentDay) {

				long yesterday = lastClusterPerDayRunDate - 1;
				long dayBefore = (yesterday - (24L * 60L * 60L * 1000L)) + 1;

				RuntimeExceptionDao<LocationDB, Integer> cellDaoClusterPerDay = GlobalParams.DB_CLUSTER_OLD_DAY
						.locInfodbRuntimeExceptionDao();
				QueryBuilder<LocationDB, Integer> queryBuilder = cellDaoClusterPerDay
						.queryBuilder();
				Where where = queryBuilder.where();
				PreparedQuery<LocationDB> pq;

				try {
					where.ge("Timestamp", dayBefore);
					where.and();
					where.le("Timestamp", yesterday);
					pq = queryBuilder.prepare();
					locListClusterOldDay = cellDaoClusterPerDay.query(pq);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// If connection lost to wifi break
				if (!(Connectivity.isConnectedWifi(GlobalParams.APP_CONTEXT))) {
					break;
				}

				// Apply DBScan cluster
				clusterInfoListOldDay = new HashSet<ClusterInfoDB>();
				GlobalParams.HOMEWORK_POINTS = GlobalParams.APP_CONTEXT
						.getSharedPreferences("HomeWorkPoints",
								Context.MODE_PRIVATE);
				if (Connectivity.isConnectedWifi(GlobalParams.APP_CONTEXT)) {
					clusterInfoListOldDay.addAll(new DBScan().applyDbscan(
							locListClusterOldDay, false));
				}

				// Store in Cluster DB
				if (clusterInfoListOldDay != null) {
					if (!(clusterInfoListOldDay.isEmpty())) {
						for (ClusterInfoDB cluster : clusterInfoListOldDay) {
							String currentTimeString = String.valueOf(new Date(
									cluster.getTimestamp()));
							String[] tokens = currentTimeString.split(" ");
							String month = Utils.getMonthNumber(tokens[1]);
							String day = tokens[2];
							String year = tokens[5];

							clusterInfoDB = new ClusterInfoDB(
									cluster.getLatitude(),
									cluster.getLongitude(),
									cluster.getTimestamp(), day + "-" + month
											+ "-" + year,
									cluster.getClusterId());
							try {
								clusterDaoClusterOldDay.create(clusterInfoDB);
							} catch (Exception s) {
							}
						}
					}
				}

				lastClusterPerDayRunDate = lastClusterPerDayRunDate
						+ (24L * 60L * 60L * 1000L);
			}
			// Update last cluster old day time
			Editor alarmEditor = appData.edit();
			alarmEditor.putLong("LastClusterOldDayRunDate",
					lastClusterPerDayRunDate);
			alarmEditor.commit();

			isRunning = false;
		}
		return "done";
	}
}

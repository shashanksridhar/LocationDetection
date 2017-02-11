package com.tk.mapoi.globals;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.content.SharedPreferences;

import com.tk.mapoi.database.ClusterInfoDB;
import com.tk.mapoi.database.DatabaseHelper;

/**
 * The Class GlobalParams - Stores publicly accessed variables used in the
 * project.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class GlobalParams {

	/** The last cluster lock to not display when last cluster is updated. */
	public static Lock LAST_CLUSTER_LOCK = new ReentrantLock();

	/** The cluster old day list. */
	public static List<ClusterInfoDB> CLUSTER_OLD_DAY_LIST = null;

	/** The current day. */
	public static boolean CURRENT_DAY = true;

	/** The cluster display day. */
	public static Date CLUSTER_DISPLAY_DAY = null;

	/** The max home cluster percentage. */
	public static double MAX_HOME_CLUSTER_PERCENTAGE = 0.0D;

	/** The shared prefs home work points. */
	public static SharedPreferences HOMEWORK_POINTS = null;

	/** Application Context */
	public static Context APP_CONTEXT = null;

	/** db to get homework. */
	public static DatabaseHelper DB_GET_HOMEWORK = null;

	/** db to cluster old day. */
	public static DatabaseHelper DB_CLUSTER_OLD_DAY = null;

	/** db to store cluster of per old cluster. */
	public static DatabaseHelper DB_CLUSTER_TABLE_OLD_DAY = null;

	/** db to cluster current day. */
	public static DatabaseHelper DB_CLUSTER = null;

}

package com.tk.mapoi.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tk.mapoi.R;

/**
 * The Class DatabaseHelper.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "mapoi.db";

	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;

	/** The Cluster dao. */
	private Dao<ClusterInfoDB, Integer> clusterDao = null;

	/** The GPS dao. */
	private Dao<LocationDB, Integer> locDao = null;

	/** The Cluster info db runtime dao. */
	private RuntimeExceptionDao<ClusterInfoDB, Integer> clusterInfoDBRuntimeDao = null;

	/** The GPS infodb runtime dao. */
	private RuntimeExceptionDao<LocationDB, Integer> locInfodbRuntimeDao = null;

	/**
	 * Instantiates a new database helper.
	 * 
	 * @param context
	 *            the context
	 */
	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/**
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase,
	 *      com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {

		try {
			TableUtils.createTable(connectionSource, ClusterInfoDB.class);
			TableUtils.createTable(connectionSource, LocationDB.class);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {

		try {
			TableUtils.dropTable(connectionSource, ClusterInfoDB.class, true);
			TableUtils.dropTable(connectionSource, LocationDB.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<ClusterInfoDB, Integer> getDao() throws SQLException {

		if (clusterDao == null) {
			clusterDao = getDao(ClusterInfoDB.class);

		}
		return clusterDao;

	}

	/**
	 * Gets the locDao.
	 * 
	 * @return the locDao
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Dao<LocationDB, Integer> getGDao() throws SQLException {

		if (locDao == null) {
			locDao = getDao(LocationDB.class);

		}
		return locDao;

	}

	/**
	 * Cluster info runtime exception dao.
	 * 
	 * @return the runtime exception dao
	 */
	public RuntimeExceptionDao<ClusterInfoDB, Integer> clusterInfoRuntimeExceptionDao() {

		if (clusterInfoDBRuntimeDao == null) {
			clusterInfoDBRuntimeDao = getRuntimeExceptionDao(ClusterInfoDB.class);
		}

		return clusterInfoDBRuntimeDao;

	}

	/**
	 * LocationDB runtime exception dao.
	 * 
	 * @return the runtime exception dao
	 */
	public RuntimeExceptionDao<LocationDB, Integer> locInfodbRuntimeExceptionDao() {

		if (locInfodbRuntimeDao == null) {
			locInfodbRuntimeDao = getRuntimeExceptionDao(LocationDB.class);
		}

		return locInfodbRuntimeDao;

	}

}

package com.tk.mapoi.cluster;

import java.util.List;

import android.os.AsyncTask;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.GlobalParams;

/**
 * The Async Class GetHomeWork.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class GetHomeWork extends AsyncTask<String, Integer, String> {

	/** The loc whole list for home work cluster. */
	private List<LocationDB> locWholeList;

	/** To check if it is already running */
	private static boolean isRunning = false;

	/**
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... url) {

		if (!isRunning) {
			isRunning = true;
			RuntimeExceptionDao<LocationDB, Integer> cellDaoGetHomeWork = GlobalParams.DB_GET_HOMEWORK
					.locInfodbRuntimeExceptionDao();

			locWholeList = (cellDaoGetHomeWork.queryForAll());
			new DBScan().applyDbscan(locWholeList, true);
			isRunning = false;
		}
		return "done";
	}
}
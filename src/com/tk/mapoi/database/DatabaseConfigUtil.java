package com.tk.mapoi.database;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * The Class DatabaseConfigUtil.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	/** The Constant classesClusterInfoDB. */
	private static final Class<?>[] classesClusterInfoDB = new Class[] { ClusterInfoDB.class };

	/** The Constant classesG. */
	private static final Class<?>[] classesG = new Class[] { LocationDB.class };

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SQLException
	 *             the SQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", classesG);
		writeConfigFile("ormlite_config.txt", classesClusterInfoDB);

	}
}

package com.tk.mapoi.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check device's network and wifi connectivity.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class Connectivity {

	/**
	 * Get the network info.
	 * 
	 * @param context
	 *            the context
	 * @return the network info
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * Check if there is any connectivity to a Wifi network.
	 * 
	 * @param context
	 *            the context
	 * @return true, if is connected wifi
	 */
	public static boolean isConnectedWifi(Context context) {
		NetworkInfo info = Connectivity.getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
	}
}

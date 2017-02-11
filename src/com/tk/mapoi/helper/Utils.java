package com.tk.mapoi.helper;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.tk.mapoi.MainActivity;
import com.tk.mapoi.R;
import com.tk.mapoi.database.LocationDB;
import com.tk.mapoi.globals.Constants;

/**
 * The Class Utils - Utility methods used in the project.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class Utils {

	/**
	 * Gets the month number.
	 * 
	 * @param month
	 *            - the month in string
	 * @return the month number
	 */
	@SuppressLint("DefaultLocale")
	public static String getMonthNumber(String month) {

		String lowerCaseMonth = month.toLowerCase();
		if (lowerCaseMonth.contains(Constants.MONTH_JANUARY)) {
			return "1";
		} else if (lowerCaseMonth.contains(Constants.MONTH_FEBRUARY)) {
			return "2";
		} else if (lowerCaseMonth.contains(Constants.MONTH_MARCH)) {
			return "3";
		} else if (lowerCaseMonth.contains(Constants.MONTH_APRIL)) {
			return "4";
		} else if (lowerCaseMonth.contains(Constants.MONTH_MAY)) {
			return "5";
		} else if (lowerCaseMonth.contains(Constants.MONTH_JUNE)) {
			return "6";
		} else if (lowerCaseMonth.contains(Constants.MONTH_JULY)) {
			return "7";
		} else if (lowerCaseMonth.contains(Constants.MONTH_AUGUST)) {
			return "8";
		} else if (lowerCaseMonth.contains(Constants.MONTH_SEPTEMBER)) {
			return "9";
		} else if (lowerCaseMonth.contains(Constants.MONTH_OCTOBER)) {
			return "10";
		} else if (lowerCaseMonth.contains(Constants.MONTH_NOVEMBER)) {
			return "11";
		} else {
			return "12";
		}
	}

	/**
	 * Notification bar.
	 * 
	 * @param context
	 *            the context
	 */
	@SuppressWarnings("deprecation")
	public static void notificationBar(Context context) {

		boolean wifiStatus = false;
		if (Connectivity.isConnectedWifi(context)) {
			wifiStatus = true;
		} else {
			wifiStatus = false;
		}

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		CharSequence details = Constants.NOTIFY_SERICE_OFF;

		if (prefs.getBoolean("keystring", true) && (wifiStatus == true)) {
			details = Constants.NOTIFY_SERICE_ON;
		}

		else if (prefs.getBoolean("keystring", true) && (wifiStatus == false)) {
			details = Constants.NOTIFY_WIFI_OFF;

		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setColor(0x339933);
		builder.setSmallIcon(R.drawable.ic_launcher_mapoi);
		builder.setContentTitle(Constants.NOTIFY_MAPOI_RUNNING);
		builder.setContentText(details);

		Notification notify = builder.build();
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		CharSequence title = Constants.NOTIFY_MAPOI_RUNNING;
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pending = PendingIntent
				.getActivity(context, 0, intent, 0);
		notify.setLatestEventInfo(context, title, details, pending);

		// To make non-removable notification
		notify.flags |= Notification.FLAG_NO_CLEAR;
		nm.notify(0, notify);
	}

	/**
	 * Cancel notification bar.
	 * 
	 * @param context
	 *            the context
	 */
	public static void cancelNotificationBar(Context context) {

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(0);
	}

	/**
	 * Gets the distance between 2 locations.
	 * 
	 * @param loc1
	 *            - Location 1
	 * @param loc2
	 *            - Location 2
	 * 
	 * @return the distance
	 */
	public static double getDistance(LocationDB loc1, LocationDB loc2) {

		float[] results = new float[1];

		Location.distanceBetween(Double.parseDouble(loc1.getLatitude()),
				Double.parseDouble(loc1.getLongitude()),
				Double.parseDouble(loc2.getLatitude()),
				Double.parseDouble(loc2.getLongitude()), results);
		return results[0];

	}

	/**
	 * Gets the distance between 2 points.
	 * 
	 * @param lat1
	 *            - Latitude of Place 1
	 * @param lat2
	 *            - Latitude of Place 2
	 * @param long1
	 *            - Longitude of Place 1
	 * @param long2
	 *            - Longitude of Place 2
	 * @return the distance between 2 points
	 */
	public static double getDistancePts(double lat1, double lat2, double long1,
			double long2) {

		float[] results = new float[1];

		Location.distanceBetween(lat1, long1, lat2, long2, results);

		return results[0];

	}
}

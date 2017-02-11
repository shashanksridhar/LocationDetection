package com.tk.mapoi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tk.mapoi.globals.Constants;

/**
 * AlarmReceiver --- the alarm object called every Constants.LOG_INTERVAL.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class AlarmReceiver extends BroadcastReceiver {

	/**
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 *      android.content.Intent) Calling BackgroundLogging Object Intent when
	 *      the alarm is called
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Intent i = new Intent(context, BackgroundService.class);
			context.startService(i);
		} else {
			Intent i = new Intent(context, BackgroundService.class);
			context.startService(i);
		}
	}

	/**
	 * Sets the alarm.
	 * 
	 * @param context
	 *            the context
	 */
	public void SetAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

		long log_interval = Constants.LOG_INTERVAL;

		// Repeating alarm for every Constants.LOG_INTERVAL
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				log_interval * 1000L * 60L, pi);
	}

	/**
	 * Cancel alarm.
	 * 
	 * @param context
	 *            the context
	 */
	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	/**
	 * Sets the onetime timer.
	 * 
	 * @param context
	 *            the new onetime timer
	 */
	public void setOnetimeTimer(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}

}

package com.tk.mapoi.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.tk.mapoi.AlarmReceiver;
import com.tk.mapoi.BackgroundService;
import com.tk.mapoi.R;
import com.tk.mapoi.helper.Utils;

/**
 * The Class SettingFragment - - UI for Service Settings option in Left Panel
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class SettingFragment extends Fragment {

	/** The alarm. */
	private AlarmReceiver alarm = new AlarmReceiver();

	/** The tg. */
	private Switch tg;

	/**
	 * Instantiates a new setting fragment.
	 */
	public SettingFragment() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_setting,
				container, false);

		// Background Service Switch
		tg = (Switch) rootView.findViewById(R.id.switch1);

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		tg.setChecked(prefs.getBoolean("keystring", true));// default is true

		tg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("keystring", tg.isChecked()); // value to
																// store
				editor.commit();
				if (tg.isChecked())
					Utils.notificationBar(getActivity().getApplicationContext());
				else {
					Utils.cancelNotificationBar(getActivity()
							.getApplicationContext());
					alarm.CancelAlarm(getActivity().getApplicationContext());
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							BackgroundService.class);
					getActivity().getApplicationContext().stopService(i);
				}

			}
		});

		tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {

					startAlarm();
					Toast.makeText(getActivity(), "Service Started!",
							Toast.LENGTH_SHORT).show();

				}

				else {

					alarm.CancelAlarm(getActivity().getApplicationContext());
					Intent i = new Intent(
							getActivity().getApplicationContext(),
							BackgroundService.class);
					getActivity().getApplicationContext().stopService(i);
					Toast.makeText(getActivity(), "Service Cancelled!",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		return rootView;
	}

	/**
	 * Start alarm.
	 */
	private void startAlarm() {

		Context context = getActivity().getApplicationContext();
		if (alarm != null) {
			alarm.SetAlarm(context);
		}

		else {
			Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
		}

	}

}

package com.tk.mapoi;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.tk.mapoi.globals.Constants;
import com.tk.mapoi.globals.GlobalParams;
import com.tk.mapoi.database.ClusterInfoDB;
import com.tk.mapoi.database.DatabaseHelper;
import com.tk.mapoi.fragments.InformationFragment;
import com.tk.mapoi.fragments.MapFragmenter;
import com.tk.mapoi.fragments.SettingFragment;
import com.tk.mapoi.helper.Utils;
import com.tk.mapoi.slidingmenu.NavDrawerItem;
import com.tk.mapoi.slidingmenu.NavDrawerListAdapter;

/**
 * The Class MainActivity.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	/** The m drawer layout. */
	private DrawerLayout mDrawerLayout;

	/** The m drawer list. */
	private ListView mDrawerList;

	/** The m drawer toggle. */
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	/** The m drawer title. */
	private CharSequence mDrawerTitle;

	// used to store app title
	/** The m title. */
	private CharSequence mTitle;

	// slide menu items
	/** The nav menu titles. */
	private String[] navMenuTitles;

	/** The nav menu icons. */
	private TypedArray navMenuIcons;

	/** The nav drawer items. */
	private ArrayList<NavDrawerItem> navDrawerItems;

	/** The adapter. */
	private NavDrawerListAdapter adapter;

	/** The year select. */
	private int yearSelect = 0;

	/** The month select. */
	private int monthSelect = 0;

	/** The day select. */
	private int daySelect = 0;

	/** The db. */
	private DatabaseHelper db;
	
	/** The alarm thread status. */
	public boolean alarmThreadStatus = false;

	/**
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Include the status bar notification on click of item in menu.
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		if (prefs.getBoolean("keystring", true)) {
			Utils.notificationBar(getApplicationContext());

			WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			if (!wifi.isWifiEnabled()) {

				// Build the alert dialog if WiFi is not connected
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("WiFi is Not Active");
				builder.setMessage("Please click OK to enable WiFi");
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(
										getApplicationContext(),
										"WiFi must be enabled to cluster location! Please turn on the WiFi",
										Toast.LENGTH_SHORT).show();

							}

						});

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialogInterface, int i) {
								// Show wifi settings when the user acknowledges
								// the alert dialog
								Intent intent = new Intent(
										Settings.ACTION_WIFI_SETTINGS);
								startActivity(intent);
							}
						});
				Dialog alertDialog = builder.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.show();
			}

			Utils.notificationBar(getApplicationContext());
		}

		if (!alarmThreadStatus) {
			if (prefs.getBoolean("keystring", true)) {
				alarmThreadStatus = true;

				SharedPreferences logSettings = getPreferences(0);
				SharedPreferences.Editor edt = logSettings.edit();
				edt.putInt("log_interval", Constants.LOG_INTERVAL);
				edt.commit();

				AlarmReceiver alarm = new AlarmReceiver();

				Context context = getApplicationContext();
				alarm.CancelAlarm(context);
				alarm.SetAlarm(context);
			}
		}
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// POI
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Logging Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));

		// Information
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener.
	 * 
	 * @see SlideMenuClickEvent
	 */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		/**
		 * 
		 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 *      .widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Display view for selected nav drawer item
			displayView(position);

		}
	}

	/**
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {

		// Show map for today
		case R.id.action_today:
			GlobalParams.CURRENT_DAY = true;

			// Set today's date in datepicker
			long currentTS = System.currentTimeMillis();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(currentTS);
			yearSelect = cal.get(Calendar.YEAR);
			monthSelect = cal.get(Calendar.MONTH);
			daySelect = cal.get(Calendar.DATE);

			// Display map
			displayView(0);
			return true;

			// Build date picker and show map for that date
		case R.id.action_datepicker:

			final Calendar c = Calendar.getInstance();

			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {

							// Get date from Datepicker
							Calendar c = Calendar.getInstance();
							c.set(year, monthOfYear, dayOfMonth);
							yearSelect = year;
							monthSelect = monthOfYear;
							daySelect = dayOfMonth;

							long timestamp = c.getTimeInMillis();
							Timestamp stamp = new Timestamp(timestamp);
							GlobalParams.CLUSTER_DISPLAY_DAY = new Date(stamp
									.getTime());

							try {
								if ((getDateCluster(timestamp))) {
									// Display cluster info the date selected
									GlobalParams.CURRENT_DAY = false;
									displayView(0);
								} else {
									// Display cluster info for today
									GlobalParams.CURRENT_DAY = true;
									displayView(0);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

						}

						@SuppressWarnings("rawtypes")
						/**
						 * Get cluster informtion for the date selected
						 * 
						 * @param timestamp of the date to be be displayed for cluster
						 * @return boolean  - false if date selected is today, true else
						 * @throws SQLException
						 */
						private boolean getDateCluster(long timestamp)
								throws SQLException {

							// Get timestamps for start and end of day selected
							long startOfDay = timestamp
									- ((timestamp + TimeZone.getDefault()
											.getOffset(timestamp)) % (24L * 60L * 60L * 1000L));
							long endOfDay = (startOfDay - 1 + 24L * 60L * 60L * 1000L);
							long currentTS = System.currentTimeMillis();
							long startOfToday = currentTS
									- ((currentTS + TimeZone.getDefault()
											.getOffset(currentTS)) % (24L * 60L * 60L * 1000L));
							if (startOfToday == startOfDay) {
								return false;
							}
							// Fetch info from database for the day selected
							db = OpenHelperManager.getHelper(MainActivity.this,
									DatabaseHelper.class);
							RuntimeExceptionDao<ClusterInfoDB, Integer> clusterdao = db
									.clusterInfoRuntimeExceptionDao();
							QueryBuilder<ClusterInfoDB, Integer> queryBuilder = clusterdao
									.queryBuilder()
									.distinct()
									.selectColumns("Latitude", "Longitude",
											"clusterId");
							Where where = queryBuilder.where();
							where.between("Timestamp", (startOfDay), (endOfDay));
							PreparedQuery<ClusterInfoDB> pq = queryBuilder
									.prepare();
							List<ClusterInfoDB> list = clusterdao.query(pq);

							// Store it in list
							GlobalParams.CLUSTER_OLD_DAY_LIST = list;
							return true;
						}
					}, mYear, mMonth, mDay);
			dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
			dpd.setTitle("Select POI Date");
			if (yearSelect != 0 && monthSelect != 0 && daySelect != 0) {
				dpd.updateDate(yearSelect, monthSelect, daySelect);
			}
			dpd.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * * Called when invalidateOptionsMenu() is triggered.
	 * 
	 * @param menu
	 *            the menu
	 * @return true, if successful
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_datepicker).setVisible(!drawerOpen);
		menu.findItem(R.id.action_today).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item.
	 * 
	 * @param position
	 *            the position
	 */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new MapFragmenter();
			break;
		case 1:
			fragment = new SettingFragment();
			break;
		case 2:
			fragment = new InformationFragment();
			break;

		default:
			break;

		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/**
	 * 
	 * @see android.app.Activity#setTitle(java.lang.CharSequence)
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/**
	 * 
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration
	 *      )
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (prefs.getBoolean("keystring", true)) {
			Utils.notificationBar(getApplicationContext());
		}
		GlobalParams.CURRENT_DAY = true;
	}

}
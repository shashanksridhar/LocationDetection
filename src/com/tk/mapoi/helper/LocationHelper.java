package com.tk.mapoi.helper;

import android.location.Location;

/**
 * The Class LocationHelper to help location fetching.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class LocationHelper {

	/** The Constant TWO_MINUTES. */
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	/** The current best location. */
	private static Location currentBestLocation = null;

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix.
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @return true, if is better location
	 */
	public boolean isBetterLocation(Location location) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			currentBestLocation = location;
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			currentBestLocation = location;
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			currentBestLocation = location;
			return true;
		} else if (isNewer && !isLessAccurate) {
			currentBestLocation = location;
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			currentBestLocation = location;
			return true;
		}
		return false;
	}

	/**
	 * Checks whether two providers are the same.
	 * 
	 * @param provider1
	 *            the provider1
	 * @param provider2
	 *            the provider2
	 * @return true, if is same provider
	 */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

}

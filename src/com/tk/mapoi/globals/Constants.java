package com.tk.mapoi.globals;

/**
 * The Class Constants - Stores constant variables used in the project
 */
public class Constants {

	/**
	 * Log interval to log data points(in minutes).
	 */
	public static int LOG_INTERVAL = 1;

	/** The epsilon for DBSCAN. */
	public static double EPSILON = 35.0;

	/** The min pts for DBSCAN. */
	public static int MIN_PTS = 9;

	/** The purge interval (in days). */
	public static long PURGE_INTERVAL = 15 * 24L * 60L * 60L * 1000L;

	/**
	 * The Constant CLUSTER_STORE_INTERVAL (in hours) for purge and home work
	 * cluster.
	 */
	public static final long CLUSTER_RUN_ONCE_PER_DAY_INTERVAL = 24;

	/** The Constant CLUSTER_DAEMON_INTERVAL (in mins). */
	public static final long CLUSTER_DAEMON_INTERVAL = 10;

	/** The Constant PLACES_API_KEY. */
	public static final String PLACES_API_KEY = "AIzaSyDIIR3iArgIzpB5YV57MoVXlicpfdr6xPM";

	/** The Constant PLACES_RADIUS. */
	public static final int PLACES_RADIUS = 60;

	/** The Constant CLUSTER_NAME_OTHER. */
	public static final String CLUSTER_NAME_OTHER = "other";

	/** The Constant CLUSTER_NAME_HOME. */
	public static final String CLUSTER_NAME_HOME = "home";

	/** The Constant CLUSTER_NAME_WORK. */
	public static final String CLUSTER_NAME_WORK = "work";

	/** The Constant CLUSTER_NAME_UNIVERSITY. */
	public static final String CLUSTER_NAME_UNIVERSITY = "university";

	/** The Constant CLUSTER_NAME_SCHOOL. */
	public static final String CLUSTER_NAME_SCHOOL = "school";

	/** The Constant CLUSTER_NAME_RESTAURANT. */
	public static final String CLUSTER_NAME_RESTAURANT = "restaurant";

	/** The Constant CLUSTER_NAME_CAFE. */
	public static final String CLUSTER_NAME_CAFE = "cafe";

	/** The Constant CLUSTER_NAME_BAR. */
	public static final String CLUSTER_NAME_BAR = "bar";

	/** The Constant CLUSTER_NAME_NIGHT_CLUB. */
	public static final String CLUSTER_NAME_NIGHT_CLUB = "night_club";

	/** The Constant CLUSTER_NAME_BAKERY. */
	public static final String CLUSTER_NAME_BAKERY = "bakery";

	/** The Constant CLUSTER_NAME_FOOD. */
	public static final String CLUSTER_NAME_FOOD = "food";

	/** The Constant CLUSTER_NAME_MEAL_DELIVERY. */
	public static final String CLUSTER_NAME_MEAL_DELIVERY = "meal_delivery";

	/** The Constant CLUSTER_NAME_MEAL_TAKEAWAY. */
	public static final String CLUSTER_NAME_MEAL_TAKEAWAY = "meal_takeaway";

	/** The Constant CLUSTER_NAME_SHOPPING. */
	public static final String CLUSTER_NAME_SHOPPING = "shopping";

	/** The Constant CLUSTER_NAME_STORE. */
	public static final String CLUSTER_NAME_STORE = "store";

	/** The Constant CLUSTER_NAME_SHOPPNG_MALL. */
	public static final String CLUSTER_NAME_SHOPPNG_MALL = "shopping_mall";

	/** The Constant CLUSTER_NAME_SUPER_MARKET. */
	public static final String CLUSTER_NAME_SUPER_MARKET = "grocery_or_supermarket";

	/** The Constant CLUSTER_NAME_MOVIE. */
	public static final String CLUSTER_NAME_MOVIE = "movie_theater";

	/** The Constant CLUSTER_NAME_GYM. */
	public static final String CLUSTER_NAME_GYM = "gym";

	/** The Constant CLUSTER_NAME_TRAVEL. */
	public static final String CLUSTER_NAME_TRAVEL = "travel";

	/** The Constant CLUSTER_NAME_TRAIN_STATION. */
	public static final String CLUSTER_NAME_TRAIN_STATION = "train_station";

	/** The Constant CLUSTER_NAME_SUBWAY_STATION. */
	public static final String CLUSTER_NAME_SUBWAY_STATION = "subway_station";

	/** The Constant CLUSTER_NAME_BUS_STATION. */
	public static final String CLUSTER_NAME_BUS_STATION = "bus_station";

	/** The Constant CLUSTER_NAME_AIRPORT. */
	public static final String CLUSTER_NAME_AIRPORT = "airport";

	/** The Constant CLUSTER_NAME_MEDICAL. */
	public static final String CLUSTER_NAME_MEDICAL = "medical";

	/** The Constant CLUSTER_NAME_HOSPITAL. */
	public static final String CLUSTER_NAME_HOSPITAL = "hospital";

	/** The Constant CLUSTER_NAME_DOCTOR. */
	public static final String CLUSTER_NAME_DOCTOR = "doctor";

	/** The Constant CLUSTER_NAME_PHARMACY. */
	public static final String CLUSTER_NAME_PHARMACY = "pharmacy";

	/** The Constant CLUSTER_NAME_PHYSIOTHERAPIST. */
	public static final String CLUSTER_NAME_PHYSIOTHERAPIST = "physiotherapist";

	/** The Constant CLUSTER_NAME_DENTIST. */
	public static final String CLUSTER_NAME_DENTIST = "dentist";

	/** The Constant CLUSTER_NAME_HEALTH. */
	public static final String CLUSTER_NAME_HEALTH = "health";

	/** The Constant CLUSTER_NAME_VET. */
	public static final String CLUSTER_NAME_VET = "veterinary_care";

	/** The Constant CLUSTER_NAME_WORSHIP. */
	public static final String CLUSTER_NAME_WORSHIP = "place_of_worship";

	/** The Constant CLUSTER_NAME_CHURCH. */
	public static final String CLUSTER_NAME_CHURCH = "church";

	/** The Constant CLUSTER_NAME_MOSQUE. */
	public static final String CLUSTER_NAME_MOSQUE = "mosque";

	/** The Constant CLUSTER_NAME_TEMPLE. */
	public static final String CLUSTER_NAME_TEMPLE = "hindu_temple";

	/** The Constant CLUSTER_NAME_BANK. */
	public static final String CLUSTER_NAME_BANK = "bank";

	/** The Constant CLUSTER_NAME_ATM. */
	public static final String CLUSTER_NAME_ATM = "atm";

	/** The Constant CLUSTER_NAME_SALON. */
	public static final String CLUSTER_NAME_SALON = "salon";

	/** The Constant CLUSTER_NAME_BEAUTY_SALON. */
	public static final String CLUSTER_NAME_BEAUTY_SALON = "beauty_salon";

	/** The Constant CLUSTER_NAME_HAIR_CARE. */
	public static final String CLUSTER_NAME_HAIR_CARE = "hair_care";

	/** The Constant CLUSTER_NAME_SPA. */
	public static final String CLUSTER_NAME_SPA = "spa";

	/** The Constant CLUSTER_NAME_INDOOR_GAMES. */
	public static final String CLUSTER_NAME_INDOOR_GAMES = "indoor_games";

	/** The Constant CLUSTER_NAME_BOWLING_ALLEY. */
	public static final String CLUSTER_NAME_BOWLING_ALLEY = "bowling_alley";

	/** The Constant CLUSTER_NAME_CASINO. */
	public static final String CLUSTER_NAME_CASINO = "casino";

	/** The Constant CLUSTER_NAME_CEMETRY. */
	public static final String CLUSTER_NAME_CEMETRY = "cemetry";

	/** The Constant CLUSTER_NAME_FUNERAL_HOME. */
	public static final String CLUSTER_NAME_FUNERAL_HOME = "funeral_home";

	/** The Constant CLUSTER_NAME_COURTSHOUSE. */
	public static final String CLUSTER_NAME_COURTSHOUSE = "courthouse";

	/** The Constant CLUSTER_NAME_GAS_STATION. */
	public static final String CLUSTER_NAME_GAS_STATION = "gas_station";

	/** The Constant CLUSTER_NAME_LAUNDRY. */
	public static final String CLUSTER_NAME_LAUNDRY = "laundry";

	/** The Constant CLUSTER_NAME_LIBRARY. */
	public static final String CLUSTER_NAME_LIBRARY = "library";

	/** The Constant CLUSTER_NAME_PARKING. */
	public static final String CLUSTER_NAME_PARKING = "parking";

	/** The Constant CLUSTER_NAME_PARK. */
	public static final String CLUSTER_NAME_PARK = "park";

	/** The Constant CLUSTER_NAME_AQUARIUM. */
	public static final String CLUSTER_NAME_AQUARIUM = "aquarium";

	/** The Constant CLUSTER_NAME_ART_GALLERY. */
	public static final String CLUSTER_NAME_ART_GALLERY = "art_gallery";

	/** The Constant CLUSTER_NAME_MUSEUM. */
	public static final String CLUSTER_NAME_MUSEUM = "museum";

	/** The Constant CLUSTER_NAME_ZOO. */
	public static final String CLUSTER_NAME_ZOO = "zoo";

	/** The Constant CLUSTER_NAME_POLICE. */
	public static final String CLUSTER_NAME_POLICE = "police";

	/** The Constant CLUSTER_NAME_POST_OFFICE. */
	public static final String CLUSTER_NAME_POST_OFFICE = "post_office";

	/** The Constant CLUSTER_NAME_STADIUM. */
	public static final String CLUSTER_NAME_STADIUM = "stadium";

	/** The Constant CLUSTER_NAME_OUTDOOR_GAMES. */
	public static final String CLUSTER_NAME_OUTDOOR_GAMES = "outdoor_games";

	/** The Constant CLUSTER_ID_OTHER. */
	public static final int CLUSTER_ID_OTHER = 0;

	/** The Constant CLUSTER_ID_HOME. */
	public static final int CLUSTER_ID_HOME = 1;

	/** The Constant CLUSTER_ID_WORK. */
	public static final int CLUSTER_ID_WORK = 2;

	/** The Constant CLUSTER_ID_UNIVERSITY. */
	public static final int CLUSTER_ID_UNIVERSITY = 3;

	/** The Constant CLUSTER_ID_RESTAURANT. */
	public static final int CLUSTER_ID_RESTAURANT = 4;

	/** The Constant CLUSTER_ID_SHOPPING. */
	public static final int CLUSTER_ID_SHOPPING = 5;

	/** The Constant CLUSTER_ID_MOVIE. */
	public static final int CLUSTER_ID_MOVIE = 6;

	/** The Constant CLUSTER_ID_GYM. */
	public static final int CLUSTER_ID_GYM = 7;

	/** The Constant CLUSTER_ID_TRAVEL. */
	public static final int CLUSTER_ID_TRAVEL = 8;

	/** The Constant CLUSTER_ID_MEDICAL. */
	public static final int CLUSTER_ID_MEDICAL = 9;

	/** The Constant CLUSTER_ID_WORSHIP. */
	public static final int CLUSTER_ID_WORSHIP = 10;

	/** The Constant CLUSTER_ID_BANK. */
	public static final int CLUSTER_ID_BANK = 11;

	/** The Constant CLUSTER_ID_SALON. */
	public static final int CLUSTER_ID_SALON = 12;

	/** The Constant CLUSTER_ID_INDOOR_GAMES. */
	public static final int CLUSTER_ID_INDOOR_GAMES = 13;

	/** The Constant CLUSTER_ID_CEMETRY. */
	public static final int CLUSTER_ID_CEMETRY = 14;

	/** The Constant CLUSTER_ID_COURTSHOUSE. */
	public static final int CLUSTER_ID_COURTSHOUSE = 15;

	/** The Constant CLUSTER_ID_GAS_STATION. */
	public static final int CLUSTER_ID_GAS_STATION = 16;

	/** The Constant CLUSTER_ID_LAUNDRY. */
	public static final int CLUSTER_ID_LAUNDRY = 17;

	/** The Constant CLUSTER_ID_LIBRARY. */
	public static final int CLUSTER_ID_LIBRARY = 18;

	/** The Constant CLUSTER_ID_PARKING. */
	public static final int CLUSTER_ID_PARKING = 19;

	/** The Constant CLUSTER_ID_LEISURE. */
	public static final int CLUSTER_ID_LEISURE = 20;

	/** The Constant CLUSTER_ID_POLICE. */
	public static final int CLUSTER_ID_POLICE = 21;

	/** The Constant CLUSTER_ID_POST_OFFICE. */
	public static final int CLUSTER_ID_POST_OFFICE = 22;

	/** The Constant CLUSTER_ID_OUTDOOR_SPORTS. */
	public static final int CLUSTER_ID_OUTDOOR_SPORTS = 23;

	/** The Constant MONTH_JANUARY. */
	public static final String MONTH_JANUARY = "jan";

	/** The Constant MONTH_FEBRUARY. */
	public static final String MONTH_FEBRUARY = "feb";

	/** The Constant MONTH_MARCH. */
	public static final String MONTH_MARCH = "mar";

	/** The Constant MONTH_APRIL. */
	public static final String MONTH_APRIL = "apr";

	/** The Constant MONTH_MAY. */
	public static final String MONTH_MAY = "may";

	/** The Constant MONTH_JUNE. */
	public static final String MONTH_JUNE = "jun";

	/** The Constant MONTH_JULY. */
	public static final String MONTH_JULY = "jul";

	/** The Constant MONTH_AUGUST. */
	public static final String MONTH_AUGUST = "aug";

	/** The Constant MONTH_SEPTEMBER. */
	public static final String MONTH_SEPTEMBER = "sep";

	/** The Constant MONTH_OCTOBER. */
	public static final String MONTH_OCTOBER = "oct";

	/** The Constant MONTH_NOVEMBER. */
	public static final String MONTH_NOVEMBER = "nov";

	/** The Constant MONTH_DECEMBER. */
	public static final String MONTH_DECEMBER = "dec";

	/**
	 * Constant for minimum percentage of time to stay in a place to be
	 * qualified as work
	 */
	public static final double WORK_CLUSTER_PERCENTAGE = 14.0D;
	
	/** Notification Service Off */ 
	public static String NOTIFY_SERICE_OFF = "Service is off";
	
	/** Notification Service On */ 
	public static String NOTIFY_SERICE_ON = "Service is on";
	
	/** Notification MAPOI running */ 
	public static String NOTIFY_MAPOI_RUNNING = "MAPOI is running!";
	
	/** Notification Wifi Off */ 
	public static String NOTIFY_WIFI_OFF = "Connect to WiFi to Cluster Places";

}

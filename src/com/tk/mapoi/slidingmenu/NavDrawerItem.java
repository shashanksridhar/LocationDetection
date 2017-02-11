package com.tk.mapoi.slidingmenu;

/**
 * The Class NavDrawerItem.
 * 
 * @author Team SNAPS (Shashank,Nandita,Ankit,Pooja,Shreyas)
 */
public class NavDrawerItem {

	/** The title. */
	private String title;

	/** The icon. */
	private int icon;

	/** The count. */
	private String count = "0";
	// boolean to set visiblity of the counter
	/** The is counter visible. */
	private boolean isCounterVisible = false;

	/**
	 * Instantiates a new nav drawer item.
	 */
	public NavDrawerItem() {
	}

	/**
	 * Instantiates a new nav drawer item.
	 * 
	 * @param title
	 *            the title
	 * @param icon
	 *            the icon
	 */
	public NavDrawerItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
	}

	/**
	 * Instantiates a new nav drawer item.
	 * 
	 * @param title
	 *            the title
	 * @param icon
	 *            the icon
	 * @param isCounterVisible
	 *            the is counter visible
	 * @param count
	 *            the count
	 */
	public NavDrawerItem(String title, int icon, boolean isCounterVisible,
			String count) {
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the icon.
	 * 
	 * @return the icon
	 */
	public int getIcon() {
		return this.icon;
	}

	/**
	 * Gets the count.
	 * 
	 * @return the count
	 */
	public String getCount() {
		return this.count;
	}

	/**
	 * Gets the counter visibility.
	 * 
	 * @return the counter visibility
	 */
	public boolean getCounterVisibility() {
		return this.isCounterVisible;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the icon.
	 * 
	 * @param icon
	 *            the new icon
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count
	 *            the new count
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * Sets the counter visibility.
	 * 
	 * @param isCounterVisible
	 *            the new counter visibility
	 */
	public void setCounterVisibility(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
}
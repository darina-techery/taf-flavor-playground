package data.ui;

import data.AppStrings;

public enum MenuItem {
	ACTIVITY_FEED(AppStrings.get().activityFeed),
	DREAM_TRIPS(AppStrings.get().dreamTrips),
	NOTIFICATIONS(AppStrings.get().notifications),
	MESSENGER(AppStrings.get().messenger),
	LOCAL(AppStrings.get().local),
	BOOK_TRAVEL(AppStrings.get().bookTravel),
	TRIP_IMAGES(AppStrings.get().tripImages),
	MEMBERSHIP(AppStrings.get().membership),
	BUCKET_LIST(AppStrings.get().bucketList),
	MY_PROFILE(AppStrings.get().myProfile),
	REP_TOOLS(AppStrings.get().repTools),
	SEND_FEEDBACK(AppStrings.get().sendFeedback),
	SETTINGS(AppStrings.get().settings),
	HELP(AppStrings.get().help),
	LEGAL_TERMS(AppStrings.get().legalTerms),
	LOGOUT(AppStrings.get().logout);

	private final String text;

	MenuItem(String text) {
		this.text = text;
	}

	public static MenuItem byName(String name) {
		for (MenuItem item : MenuItem.values()) {
			if (item.text.equals(name)) {
				return item;
			}
		}
		throw new IllegalArgumentException("No menu item exists with name "+name);
	}


	@Override
	public String toString() {
		return text;
	}
}

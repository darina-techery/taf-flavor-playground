package data.ui;

import data.AppStrings;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public enum MenuItem {
	ACTIVITY_FEED(  AppStrings.get().activityFeed,  MobileBy.AccessibilityId("menu_feed")),
	DREAM_TRIPS(    AppStrings.get().dreamTrips,    MobileBy.AccessibilityId("menu_dreamtrips")),
	NOTIFICATIONS(  AppStrings.get().notifications, MobileBy.AccessibilityId("menu_notifications")),
	MESSENGER(      AppStrings.get().messenger,     MobileBy.AccessibilityId("menu_messenger")),
	LOCAL(          AppStrings.get().local,         MobileBy.AccessibilityId("menu_discover")),
	BOOK_TRAVEL(    AppStrings.get().bookTravel,    MobileBy.AccessibilityId("menu_bookTravel")),
	TRIP_IMAGES(    AppStrings.get().tripImages,    MobileBy.AccessibilityId("menu_tripImages")),
	MEMBERSHIP(     AppStrings.get().membership,    MobileBy.AccessibilityId("menu_membership")),
	BUCKET_LIST(    AppStrings.get().bucketList,    MobileBy.AccessibilityId("menu_bucketList")),
	MY_PROFILE(     AppStrings.get().myProfile,     MobileBy.AccessibilityId("menu_profile")),
	REP_TOOLS(      AppStrings.get().repTools,      MobileBy.AccessibilityId("menu_repTools")),
	SEND_FEEDBACK(  AppStrings.get().sendFeedback,  MobileBy.AccessibilityId("menu_feedback")),
	SETTINGS(       AppStrings.get().settings,      MobileBy.AccessibilityId("menu_settings")),
	HELP(           AppStrings.get().help,          MobileBy.AccessibilityId("menu_faq")),
	LEGAL_TERMS(    AppStrings.get().legalTerms,    MobileBy.AccessibilityId("legal_terms")),
	LOGOUT(         AppStrings.get().logout,        MobileBy.AccessibilityId("menu_logout"));

	private final String text;
	private final By iosLocator;

	MenuItem(String text, By iosLocator) {
		this.text = text;
		this.iosLocator = iosLocator;
	}

	public static MenuItem byName(String name) {
		for (MenuItem item : MenuItem.values()) {
			if (item.text.equals(name)) {
				return item;
			}
		}
		throw new IllegalArgumentException("No menu item exists with name "+name);
	}

	public By getIosLocator() {
		return iosLocator;
	}

	@Override
	public String toString() {
		return text;
	}
}

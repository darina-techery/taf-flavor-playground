package data.ui;

import data.AppStrings;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public enum MenuItem {
	ACTIVITY_FEED(  AppStrings.get().activityFeed,  MobileBy.AccessibilityId("menu_feed"), By.xpath("//android.widget.ImageView[@content-desc='feed']")),
	DREAM_TRIPS(    AppStrings.get().dreamTrips,    MobileBy.AccessibilityId("menu_dreamtrips"), By.xpath("//android.widget.ImageView[@content-desc='triplist']")),
	NOTIFICATIONS(  AppStrings.get().notifications, MobileBy.AccessibilityId("menu_notifications"), By.xpath("//android.widget.ImageView[@content-desc='notifications']")),
	MESSENGER(      AppStrings.get().messenger,     MobileBy.AccessibilityId("menu_messenger"), By.xpath("//android.widget.ImageView[@content-desc='messenger']")),
	LOCAL(          AppStrings.get().local,         MobileBy.AccessibilityId("menu_discover"), By.xpath("//android.widget.ImageView[@content-desc='dtl']")),
	BOOK_TRAVEL(    AppStrings.get().bookTravel,    MobileBy.AccessibilityId("menu_bookTravel"), By.xpath("//android.widget.ImageView[@content-desc='ota']")),
	TRIP_IMAGES(    AppStrings.get().tripImages,    MobileBy.AccessibilityId("menu_tripImages"), By.xpath("//android.widget.ImageView[@content-desc='trip_tab_images']")),
	MEMBERSHIP(     AppStrings.get().membership,    MobileBy.AccessibilityId("menu_membership"), By.xpath("//android.widget.ImageView[@content-desc='membership']")),
	BUCKET_LIST(    AppStrings.get().bucketList,    MobileBy.AccessibilityId("menu_bucketList"), By.xpath("//android.widget.ImageView[@content-desc='bucket_tabs']")),
	MY_PROFILE(     AppStrings.get().myProfile,     MobileBy.AccessibilityId("menu_profile"), By.xpath("//android.widget.ImageView[@content-desc='account_profile']")),
	REP_TOOLS(      AppStrings.get().repTools,      MobileBy.AccessibilityId("menu_repTools"), By.xpath("//android.widget.ImageView[@content-desc='rep_tools']")),
	SEND_FEEDBACK(  AppStrings.get().sendFeedback,  MobileBy.AccessibilityId("menu_feedback"), By.xpath("//android.widget.ImageView[@content-desc='send_feedback']")),
	SETTINGS(       AppStrings.get().settings,      MobileBy.AccessibilityId("menu_settings"), By.xpath("//android.widget.ImageView[@content-desc='settings']")),
	HELP(           AppStrings.get().help,          MobileBy.AccessibilityId("menu_faq"), By.xpath("//android.widget.ImageView[@content-desc='help']")),
	LEGAL_TERMS(    AppStrings.get().legalTerms,    MobileBy.AccessibilityId("legal_terms"), By.xpath("//android.widget.ImageView[@content-desc='triplist']")),
	LOGOUT(         AppStrings.get().logout,        MobileBy.AccessibilityId("menu_logout"), By.xpath("//android.widget.ImageView[@content-desc='triplist']"));

	private final String text;
	private final By iosLocator;
	private final By androidTablet;

	MenuItem(String text, By iosLocator, By androidTablet) {
		this.text = text;
		this.iosLocator = iosLocator;
		this.androidTablet = androidTablet;
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

	public By getAndroidTablet() {
		return androidTablet;
	}

	@Override
	public String toString() {
		return text;
	}
}

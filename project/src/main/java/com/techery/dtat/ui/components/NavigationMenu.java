package com.techery.dtat.ui.components;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import com.techery.dtat.ui.BaseUiModule;
import com.techery.dtat.utils.ui.ByHelper;

public class NavigationMenu extends BaseUiModule {

	@iOSFindBy(className = "XCUIElementTypeNavigationBar")
	public MobileElement menuBar;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Menu Opened']")
	@iOSFindBy(accessibility = "menu_more")
	public MobileElement menuButton;

	@AndroidFindBy(id = "drawerList")
	public MobileElement menuDrawer;

	@AndroidFindBy(id = "toolbar_actionbar")
	@iOSFindBy(className = "XCUIElementTypeTabBar")
	public MobileElement titleBar;

	@AndroidFindBy(id = "action_search")
	public MobileElement searchButton;

	@AndroidFindBy(id = "action_filter")
	public MobileElement filterButton;

	@AndroidFindBy(id = "action_map")
	public MobileElement mapButton;

	@AndroidFindBy(xpath = "//android.view.ViewGroup/android.widget.TextView[@text='Logout']")
    @HowToUseLocators(iOSAutomation = LocatorGroupStrategy.CHAIN)
	@iOSFindBy(accessibility = "alertView")
	@iOSFindBy(accessibility = "Logout")
	public MobileElement btnLogout;

	public final By getMenuItemLocator(MenuItem item) {
		if (Configuration.isAndroid()) {
			if (Configuration.isAndroidPhone()) {
				return ByHelper.getLocatorByText(item.toString());
			}
			else {
				return item.getAndroidTablet();
			}
		} else {
			return item.getIosLocator();
		}
	}
}

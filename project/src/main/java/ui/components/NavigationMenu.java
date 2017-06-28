package ui.components;

import data.Configuration;
import data.ui.MenuItem;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import ui.BaseUiModule;
import utils.ui.ByHelper;

public class NavigationMenu extends BaseUiModule {
	@iOSFindBy(className = "XCUIElementTypeTabBar")
	public MobileElement menuBar;

	@AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Menu Opened']")
	@iOSFindBy(accessibility = "menu_more")
	public MobileElement menuButton;

	@AndroidFindBy(id = "drawerList")
	public MobileElement menuDrawer;

	@AndroidFindBy(id = "toolbar_actionbar")
	public MobileElement titleBar;

	@AndroidFindBy(id = "action_search")
	public MobileElement searchButton;

	@AndroidFindBy(id = "action_filter")
	public MobileElement filterButton;

	@AndroidFindBy(id = "action_map")
	public MobileElement mapButton;

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

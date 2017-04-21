package screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSFindBy;

public class NavigationMenu extends BaseScreen {
	@iOSFindBy(className = "XCUIElementTypeTabBar")
	public MobileElement menuBar;
}

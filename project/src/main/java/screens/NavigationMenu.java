package screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;

public class NavigationMenu extends BaseScreen {
	@iOSFindBy(className = "XCUIElementTypeTabBar")
	public MobileElement menuBar;

	@AndroidFindBy(id = "Menu Opened")
	public MobileElement menuButton;

	@AndroidFindBy(id = "drawerList")
	public MobileElement scrollableMenu;

	public final By getAndroidMenuItemLocator(MenuItem item) {
		String itemName = item.text;
		return By.xpath("//*[@text='"+itemName+"']");
	}
}

package ui.components;

import data.Configuration;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import ui.BaseUiModule;

import java.util.List;

public class Alert extends BaseUiModule {

	public static final By ALERT_AREA_LOCATOR = Configuration.isAndroid() ? null : By.className("XCUIElementTypeAlert");

	@iOSFindBy(className = "XCUIElementTypeAlert")
	public MobileElement alertBox;

	@iOSFindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton")
	public List<MobileElement> buttons;

}

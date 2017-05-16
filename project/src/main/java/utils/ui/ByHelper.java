package utils.ui;

import org.openqa.selenium.By;

public final class ByHelper {
	private ByHelper() {}

	public static By getLocatorByText(String text) {
		String attributeName = ElementHelper.getTextAttributeName();
		return By.xpath(String.format("//*[@%s='%s']", attributeName, text));
	}
}

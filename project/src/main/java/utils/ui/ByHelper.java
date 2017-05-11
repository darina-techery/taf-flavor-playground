package utils.ui;

import data.Configuration;
import org.openqa.selenium.By;

public final class ByHelper {
	private ByHelper() {}

	public static By getLocatorForText(String text) {
		String attributeName = Configuration.isAndroid() ? "text" : "value";
		return By.xpath(String.format("//*[@%s='%s']", attributeName, text));
	}
}

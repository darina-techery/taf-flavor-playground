package utils.ui;

import data.Configuration;
import io.appium.java_client.MobileElement;

public final class ElementHelper {
	private ElementHelper() {}

	public static String getTextAttributeName() {
		return Configuration.isAndroid() ? "text" : "value";
	}

	public static String getText(MobileElement e) {
		String attributeName = getTextAttributeName();
		return e.getAttribute(attributeName);
	}
}

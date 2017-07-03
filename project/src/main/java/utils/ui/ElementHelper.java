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

	public static boolean isCheckboxChecked(MobileElement checkbox) {
		return Boolean.parseBoolean(checkbox.getAttribute("checked"));
	}

	public static String describeElement(MobileElement e) {
		String result = "";
		if (Configuration.isIOS()) {
			String[] attributes = {"accessibilityContainer",
					"accessible",
					"enabled",
					"frame",
					"label",
					"name",
					"rect",
					"type",
					"value",
					"visible"};
			for (String s : attributes) {
				result += s + ": " + e.getAttribute(s) + "\n";
			}
		} else {
			result = "Not implemented for Android yet";
		}
		return result;
	}
}

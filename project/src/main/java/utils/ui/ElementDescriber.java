package utils.ui;

import data.Configuration;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;

public class ElementDescriber {
	public static final String DEFAULT_ELEMENT_DESCRIPTION = "Element (not present in DOM now)";
	public static final String IOS_ELEMENT_DESCRIPTION = "Element (skipped tag lookup for iOS)";

	private ElementDescriber(){}

	public static String describe(MobileElement element) {
		String description;
		try {
			if (Configuration.isAndroid()) {
				String tagName = element.getTagName();
				String[] tagComponents = tagName.split("\\.");
				tagName = tagComponents[tagComponents.length - 1];
				if (element.getAttribute("resourceId") == null) {
					description = String.format("Element <%s>", tagName);
				} else {
					String resourceId = element.getAttribute("resourceId");
					if (resourceId == null) {
						resourceId = "null";
					} else if (resourceId.contains("/")) {
						resourceId = resourceId.split("/")[1];
					}
					description = String.format("Element <%s[@id=%s]>",
							tagName,
							resourceId);
				}
			} else {
				description = IOS_ELEMENT_DESCRIPTION;
			}
		} catch (NoSuchElementException e) {
			description = null;
		}
		return description;
	}
}

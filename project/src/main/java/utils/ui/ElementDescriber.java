package utils.ui;

import data.Configuration;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

public class ElementDescriber {
	public static final String DEFAULT_ELEMENT_DESCRIPTION = "Element (not present in DOM now)";
	public static final String IOS_ELEMENT_DESCRIPTION = "Element (skipped tag lookup for iOS)";

	private ElementDescriber(){}

	public static String describe(MobileElement element) {
		//ToDO: Now element.toString return all needed info about element for logs. Need some runs to check it is ok

//		String description;
//		try {
//			if (Configuration.isAndroid()) {
//				String tagName = element.getTagName();
//				String[] tagComponents = tagName.split("\\.");
//				tagName = tagComponents[tagComponents.length - 1];
//				String resourceId = null;
//				try {
//					resourceId = element.getAttribute("resourceId");
//				} catch (WebDriverException e) {
//					//Some elements cause exception when resource-id requested
//					// (uiautomator issue)
//				}
//				if (resourceId == null) {
//					description = String.format("Element <%s>", tagName);
//				} else {
//					if (resourceId.contains("/")) {
//						resourceId = resourceId.split("/")[1];
//					}
//					description = String.format("Element <%s[@id=%s]>",
//							tagName,
//							resourceId);
//				}
//			} else {
//				description = IOS_ELEMENT_DESCRIPTION;
//			}
//		} catch (NoSuchElementException e) {
//			description = null;
//		}
		return element.toString();
	}
}

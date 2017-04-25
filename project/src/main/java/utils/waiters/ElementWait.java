package utils.waiters;

import data.Configuration;
import driver.HasDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;

public class ElementWait<R> extends BaseWait<MobileElement, R>
		implements HasDriver {

	private static final String DEFAULT_ELEMENT_DESCRIPTION = "Element (not present in DOM now)";
	private static final String IOS_ELEMENT_DESCRIPTION = "Element (skipped tag lookup for iOS)";

	private String elementDescriptionInDOM;

	public ElementWait() {
		super();
		addIgnorableException(NoSuchElementException.class);
	}

	@Override
	protected String describeTestableObject() {
		if (testableObject == null) {
			return "";
		}
		String description;
		if (elementDescriptionInDOM == null) {
			try {
				if (Configuration.isAndroid()) {
					String tagName = testableObject.getTagName();
					String[] tagComponents = tagName.split("\\.");
					tagName = tagComponents[tagComponents.length - 1];
					if (testableObject.getAttribute("resourceId") == null) {
						elementDescriptionInDOM = String.format("Element <%s>", tagName);
					} else {
						String resourceId = testableObject.getAttribute("resourceId");
						if (resourceId == null) {
							resourceId = "null";
						} else if (resourceId.contains("/")) {
							resourceId = resourceId.split("/")[1];
						}
						elementDescriptionInDOM = String.format("Element <%s[@id=%s]>",
								tagName,
								resourceId);
					}
				} else {
					elementDescriptionInDOM = IOS_ELEMENT_DESCRIPTION;
				}
			} catch (NoSuchElementException e) {
				elementDescriptionInDOM = null;
			}
		}
		description = (elementDescriptionInDOM == null ? DEFAULT_ELEMENT_DESCRIPTION : elementDescriptionInDOM);
		return description;
	}

	@Override
	protected void setDefaultPrecondition() {
		when(e -> e.getSize() != null);
	}

}

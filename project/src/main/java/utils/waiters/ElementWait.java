package utils.waiters;

import driver.HasDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;
import utils.ui.ElementDescriber;

public class ElementWait<R> extends BaseWait<MobileElement, R>
		implements HasDriver {

	private static final String DEFAULT_ELEMENT_DESCRIPTION = "Element (not present in DOM now)";

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
			elementDescriptionInDOM = ElementDescriber.describe(testableObject);
		}
		description = (elementDescriptionInDOM == null ? DEFAULT_ELEMENT_DESCRIPTION : elementDescriptionInDOM);
		return description;
	}

	@Override
	protected void setDefaultPrecondition() {
		when(e -> e.getSize() != null);
	}

}

package utils.ui;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;

public class SwipeHelper {
	private SwipeHelper() {}

	public static void swipeRight(MobileElement inElement) {
		Swipe swipe = buildGenericSwipe(null, inElement);
		swipe.direction(SwipeElementDirection.RIGHT);
		swipe.withOffsetFromBorderRatio(0.1);
		swipe.swipe();
	}

	public static void swipeLeft(MobileElement inElement) {
		Swipe swipe = buildGenericSwipe(null, inElement);
		swipe.direction(SwipeElementDirection.LEFT);
		swipe.withOffsetFromBorderRatio(0.1);
		swipe.swipe();
	}

	public static void scrollOneScreenDown() {
		Swipe swipe = new Swipe();
		swipe.withOffsetFromBorderRatio(0.05);
		swipe.swipe();
	}

	public static void scrollDownToText(String text) {
		By textLocator = ByHelper.getLocatorForText(text);
		Swipe swipe = buildGenericSwipe(textLocator, null);
		swipe.swipe();
	}

	public static void scrollDownToText(String text, MobileElement container) {
		By textLocator = ByHelper.getLocatorForText(text);
		Swipe swipe = buildGenericSwipe(textLocator, container);
		swipe.swipe();
	}

	public static void scrollDownToText(String text, By container) {
		By textLocator = ByHelper.getLocatorForText(text);
		Swipe swipe = buildGenericSwipe(textLocator, container);
		swipe.swipe();
	}

	public static void scrollDownToElement(MobileElement toElement) {
		Swipe swipe = buildGenericSwipe(toElement, null);
		swipe.swipe();
	}

	public static void scrollDownToElement(MobileElement toElement, MobileElement inContainer) {
		Swipe swipe = buildGenericSwipe(toElement, inContainer);
		swipe.swipe();
	}

	public static void scrollDownToElement(MobileElement toElement, By inContainer) {
		Swipe swipe = buildGenericSwipe(toElement, inContainer);
		swipe.swipe();
	}

	public static void scrollDownToElement(By toElement) {
		Swipe swipe = buildGenericSwipe(toElement, null);
		swipe.swipe();
	}

	public static void scrollDownToElement(By toElement, MobileElement inContainer) {
		Swipe swipe = buildGenericSwipe(toElement, inContainer);
		swipe.swipe();
	}

	public static void scrollDownToElement(By toElement, By inContainer) {
		Swipe swipe = buildGenericSwipe(toElement, inContainer);
	}

	private static Swipe buildGenericSwipe(final Object toElement, final Object inContainer) {
		final String errorTemplate = "%s should be MobileElement or By, but %s found";
		verifyProvidedObject(toElement, "Target element");
		verifyProvidedObject(inContainer, "Container");
		Swipe swipe = new Swipe();
		if (toElement != null) {
			if (toElement instanceof By) {
				swipe.toElement((By) toElement);
			} else {
				swipe.toElement((MobileElement) toElement);
			}
		}
		if (inContainer != null) {
			if (inContainer instanceof By) {
				swipe.container((By) inContainer);
			} else {
				swipe.container((MobileElement) inContainer);
			}
		}
		return swipe;
	}

	private static void verifyProvidedObject(final Object elementOrLocator, final String objectDescription) {
		final String errorTemplate = "%s should be MobileElement or By, but %s found";
		boolean isExpectedArgumentType = elementOrLocator == null
				|| elementOrLocator instanceof MobileElement
				|| elementOrLocator instanceof By;
		if (!isExpectedArgumentType) {
			throw new IllegalArgumentException(String.format(errorTemplate,objectDescription, elementOrLocator.getClass()));
		}
	}

}

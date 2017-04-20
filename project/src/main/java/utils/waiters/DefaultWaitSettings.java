package utils.waiters;

import driver.DriverProvider;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;

import java.util.function.Consumer;

class DefaultWaitSettings {
	static void describeIsDisplayed(BaseWait wait) {
		wait.describe("check if element is displayed");
	}

	static void describeAreDisplayed(BaseWait wait) {
		wait.describe("check if all elements are displayed");
	}

	static void describeClick(BaseWait wait) {
		wait.describe("click");
	}

	static void describeGetText(BaseWait wait) {
		wait.describe("get text");
	}

	static void describeGetAttribute(BaseWait wait, String attributeName) {
		wait.describe("get attribute ["+attributeName+"]");
	}

	static void describeSetText(BaseWait wait, CharSequence text) {
		wait.describe("set text [" + text+ "]");
	}

	static void describeClear(BaseWait wait) {
		wait.describe("clear contents");
	}

	static void describeIsAbsent(BaseWait wait) {
		wait.describe("is absent");
	}

	static void describeExists(BaseWait wait) {
		wait.describe("exists");
	}

	static boolean getBooleanResult(BaseWait wait, boolean defaultResultWhenNull) {
		Boolean result = (Boolean) wait.go();
		return result == null ? defaultResultWhenNull : result;
	}

	static void untilTrue(BaseWait<?, Boolean> wait) {
		wait.until(result -> result);
	}

	static Consumer<MobileElement> buildTypeTextOperation(CharSequence text) {
		return el -> {
			el.click();
			el.clear();
			el.sendKeys(text);
			try {
				DriverProvider.get().hideKeyboard();
			} catch (Exception e) {
				LogManager.getLogger().warn("Failed to hide keyboard: ", e);
			}
		};
	}

}

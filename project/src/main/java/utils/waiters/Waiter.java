package utils.waiters;

import driver.HasDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import utils.log.CommonLogMessages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public interface Waiter extends CommonLogMessages, HasDriver {

	default <R> ElementWait<R> wait(MobileElement e, Class<R> resultType) {
		ElementWait<R> wait = new ElementWait<R>();
		wait.with(e);
		return wait;
	}

	default <R> ByWait<R> wait(By locator, Class<R> resultType) {
		ByWait<R> wait = new ByWait<R>();
		wait.with(locator);
		return wait;
	}

	default boolean isDisplayed(MobileElement e) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		wait.calculate(RemoteWebElement::isDisplayed);
		DefaultWaitSettings.describeIsDisplayed(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default boolean isDisplayed(By by) {
		return isDisplayed(by, null);
	}

	default boolean isDisplayed(By by, WebElement parentElement) {
		ByWait<Boolean> wait = wait(by, Boolean.class);
		wait.parent(parentElement);
		DefaultWaitSettings.describeIsDisplayed(wait);
		wait.findAndCalculate(RemoteWebElement::isDisplayed);
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default boolean areAllDisplayedForElements(List<MobileElement> list) {
		ElementWait<Boolean> wait = new ElementWait<>();
		wait.calculate(()-> {
			for (MobileElement el : list) {
				if (!el.isDisplayed()) {
					return false;
				}
			}
			return true;
		});
		DefaultWaitSettings.describeAreDisplayed(wait);
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default boolean areAllDisplayedForLocators(List<By> list) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.when(() -> true);
		wait.calculate(()-> {
			for (By locator : list) {
				MobileElement el = wait.singleElementSearch.apply(locator);
				if (!el.isDisplayed()) {
					return false;
				}
			}
			return true;
		});
		DefaultWaitSettings.describeAreDisplayed(wait);
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default boolean anyContainsTextForElements(List<MobileElement> list, String text) {
		ElementWait<Boolean> wait = new ElementWait<>();
		wait.calculate(() -> {
			for (MobileElement el: list) {
				if (el.getText().contains(text)) {
					return true;
				}
			}
			return false;
		});
		wait.describe("Check if any of provided elements contains text ["+text+"]");
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default boolean anyContainsTextForLocators(List<By> list, String text) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.calculate(() -> {
			for (By locator: list) {
				MobileElement el = wait.singleElementSearch.apply(locator);
				if (el.getText().contains(text)) {
					return true;
				}
			}
			return false;
		});
		wait.describe("Check if any of elements by provided locators contains text ["+text+"]");
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default void click(MobileElement e) {
		ElementWait<Void> wait = wait(e, Void.class);
		DefaultWaitSettings.describeClick(wait);
		wait.execute(RemoteWebElement::click).go();
	}

	default void click(By by) {
		click(by, null);
	}

	default void click(By by, WebElement parentElement) {
		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
		wait.findAndExecute(RemoteWebElement::click);
		wait.describe("click");
		wait.go();
	}

	default String getText(MobileElement e) {
		ElementWait<String> wait = wait(e, String.class);
		DefaultWaitSettings.describeGetText(wait);
		return wait.execute(RemoteWebElement::getText).go();
	}

	default String getText(By by) {
		return getText(by, null);
	}

	default String getText(By by, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		DefaultWaitSettings.describeGetText(wait);
		wait.findAndCalculate(RemoteWebElement::getText);
		return wait.go();
	}

	default void setText(MobileElement e, CharSequence text) {
		ElementWait<Void> wait = wait(e, Void.class);
		DefaultWaitSettings.describeSetText(wait, text);
		wait.when(el -> el.isDisplayed() && el.isEnabled());
		wait.execute(DefaultWaitSettings.buildTypeTextOperation(text));
		wait.go();
	}

	default void setText(By by, CharSequence text) {
		setText(by, text, null);
	}

	default void setText(By by, CharSequence text, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		DefaultWaitSettings.describeSetText(wait, text);
		wait.findAndExecute(DefaultWaitSettings.buildTypeTextOperation(text));
		wait.go();
	}

	default String getAttribute(MobileElement e, String attributeName) {
		ElementWait<String> wait = wait(e, String.class);
		DefaultWaitSettings.describeGetAttribute(wait, attributeName);
		wait.calculate(el->el.getAttribute(attributeName));
		return wait.go();
	}

	default String getAttribute(By by, String attributeName) {
		return getAttribute(by, attributeName, null);
	}

	default String getAttribute(By by, String attributeName, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		DefaultWaitSettings.describeGetAttribute(wait, attributeName);
		wait.findAndCalculate(e->e.getAttribute(attributeName));
		return wait.go();
	}

	default boolean isAbsent(MobileElement e) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		DefaultWaitSettings.describeIsAbsent(wait);
		wait.calculate(el -> {
			boolean result;
			try {
				result = !el.isDisplayed();
			} catch (NoSuchElementException ex) {
				result = true;
			}
			return result;
		});
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, true);
	}

	default boolean isAbsent(By by) {
		return isAbsent(by, null);
	}

	default boolean isAbsent(By by, WebElement parentElement) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.parent(parentElement).with(by);
		wait.config(WaitConfig.get().duration(Duration.ofSeconds(5)));
		wait.when(locator->true);
		wait.calculate(locator -> {
			List<MobileElement> elements = wait.multiElementSearch.apply(locator);
			return elements.isEmpty() || elements.stream().noneMatch(RemoteWebElement::isDisplayed);
		});
		DefaultWaitSettings.describeIsAbsent(wait);
		DefaultWaitSettings.untilTrue(wait);
		return DefaultWaitSettings.getBooleanResult(wait, true);
	}

	default void clear(MobileElement e) {
		ElementWait<Void> wait = wait(e, Void.class);
		DefaultWaitSettings.describeClear(wait);
		wait.execute(RemoteWebElement::clear).go();
		try {
			getDriver().hideKeyboard();
		} catch (Exception ex) {
			LogManager.getLogger().warn("Failed to hide keyboard: ", ex);
		}
	}

	default boolean exists(MobileElement e, WaitConfig config) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		wait.calculate(el -> el.getSize() != null);
		DefaultWaitSettings.describeExists(wait);
		return DefaultWaitSettings.getBooleanResult(wait, false);
	}

	default MobileElement find(By by) {
		return find(by, null);
	}

	default MobileElement find(By by, WebElement parentElement) {
		ByWait<MobileElement> wait = wait(by, MobileElement.class)
				.parent(parentElement);
		wait.calculate(wait.singleElementSearch).describe("find");
		return wait.go();
	}

	default List<MobileElement> findAll(By by) {
		return findAll(by, null);
	}

	default List<MobileElement> findAll(By by, WebElement parentElement) {
		ByWait<List<MobileElement>> wait = new ByWait<>();
		wait.parent(parentElement).with(by);
		wait.calculate(wait.multiElementSearch).describe("find all");
		return wait.go();
	}

	default void tap(By by) {
		tap(by, null);
	}

	default void tap(By by, WebElement parentElement) {
		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
		wait.findAndExecute(e -> e.tap(1, 5));
		wait.describe("tap");
		wait.go();
	}

	default boolean exists(By by) {
		return exists(by, null);
	}

	default boolean exists(By by, WebElement parentElement) {
		return find(by, parentElement) != null;
	}

	default int getCount(By by) {
		return getCount(by, null);
	}

	default int getCount(By by, WebElement parentElement) {
		return findAll(by, parentElement).size();
	}

}
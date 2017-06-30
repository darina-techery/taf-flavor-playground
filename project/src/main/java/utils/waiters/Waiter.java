package utils.waiters;

import data.Configuration;
import driver.DriverProvider;
import driver.HasDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import utils.ADBUtils;
import utils.log.CommonLogMessages;
import utils.ui.ElementHelper;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public class Waiter implements CommonLogMessages, HasDriver {
	private Duration d;
	public Waiter(Duration d) {
		this.d = d;
	}
	public Waiter() {}

	public <R> ElementWait<R> wait(MobileElement e, Class<R> resultType) {
		ElementWait<R> wait = new ElementWait<R>();
		wait.duration(d);
		wait.with(e);
		return wait;
	}

	public <R> ByWait<R> wait(By locator, Class<R> resultType) {
		ByWait<R> wait = new ByWait<R>();
		wait.duration(d);
		wait.with(locator);
		return wait;
	}

	public boolean isDisplayed(MobileElement e) {
		return waitDisplayed(e);
	}

	public boolean waitDisplayed(MobileElement e) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		wait.calculate(RemoteWebElement::isDisplayed);
		describeIsDisplayed(wait);
		return getBooleanResult(wait, false);
	}

	public boolean isDisplayed(By by) {
		return waitDisplayed(by, null);
	}

	public boolean waitDisplayed(By by) {
		return waitDisplayed(by, null);
	}

	public boolean waitDisplayed(By by, WebElement parentElement) {
		ByWait<Boolean> wait = wait(by, Boolean.class);
		wait.parent(parentElement);
		describeIsDisplayed(wait);
		wait.findAndCalculate(RemoteWebElement::isDisplayed);
		untilTrue(wait);
		return getBooleanResult(wait, false);
	}

	public boolean areAllDisplayedForElements(List<MobileElement> list) {
		ElementWait<Boolean> wait = new ElementWait<>();
		wait.duration(d);
		wait.calculate(()-> {
			for (MobileElement el : list) {
				if (!el.isDisplayed()) {
					return false;
				}
			}
			return true;
		});
		describeAreDisplayed(wait);
		untilTrue(wait);
		return getBooleanResult(wait, false);
	}

	public boolean areAllDisplayedForLocators(List<By> list) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.duration(d);
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
		describeAreDisplayed(wait);
		untilTrue(wait);
		return getBooleanResult(wait, false);
	}

	public boolean anyContainsTextForElements(List<MobileElement> list, String text) {
		ElementWait<Boolean> wait = new ElementWait<>();
		wait.duration(d);
		wait.calculate(() -> {
			for (MobileElement el: list) {
				if (el.getText().contains(text)) {
					return true;
				}
			}
			return false;
		});
		wait.describe("Check if any of provided elements contains text ["+text+"]");
		untilTrue(wait);
		return getBooleanResult(wait, false);
	}

	public boolean anyContainsTextForLocators(List<By> list, String text) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.duration(d);
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
		untilTrue(wait);
		return getBooleanResult(wait, false);
	}

	public void click(MobileElement e) {
		ElementWait<Void> wait = wait(e, Void.class);
		describeClick(wait);
		wait.execute(RemoteWebElement::click).go();
	}

	public void click(By by) {
		click(by, null);
	}

	public void click(By by, WebElement parentElement) {
		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
		wait.findAndExecute(RemoteWebElement::click);
		wait.describe("click");
		wait.go();
	}

	public String getText(MobileElement e) {
		ElementWait<String> wait = wait(e, String.class);
		describeGetText(wait);
		return wait.calculate(RemoteWebElement::getText).go();
	}

	public String getText(By by) {
		return getText(by, null);
	}

	public String getText(By by, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		describeGetText(wait);
		wait.findAndCalculate(RemoteWebElement::getText);
		return wait.go();
	}

	public void setText(MobileElement e, String text) {
		ElementWait<Void> wait = wait(e, Void.class);
		describeSetText(wait, text);
		wait.when(el -> el.isDisplayed() && el.isEnabled());
		wait.execute(buildSetTextOperation(text));
		wait.go();
	}

	public void setText(By by, String text) {
		setText(by, text, null);
	}

	public void setText(By by, String text, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		describeSetText(wait, text);
		wait.findAndExecute(buildSetTextOperation(text));
		wait.go();
	}

	public void sendKeys(MobileElement e, CharSequence text) {
		ElementWait<Void> wait = wait(e, Void.class);
		describeSetText(wait, text);
		wait.when(el -> el.isDisplayed() && el.isEnabled());
		wait.execute(el -> el.sendKeys(text));
		wait.go();
	}

	public void sendKeys(By by, CharSequence text) {
		sendKeys(by, text, null);
	}

	public void sendKeys(By by, CharSequence text, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		describeSetText(wait, text);
		wait.findAndExecute(el -> el.sendKeys(text));
		wait.go();
	}

	public String getAttribute(MobileElement e, String attributeName) {
		ElementWait<String> wait = wait(e, String.class);
		describeGetAttribute(wait, attributeName);
		wait.calculate(el->el.getAttribute(attributeName));
		return wait.go();
	}

	public String getAttribute(By by, String attributeName) {
		return getAttribute(by, attributeName, null);
	}

	public String getAttribute(By by, String attributeName, WebElement parentElement) {
		ByWait<String> wait = wait(by, String.class);
		wait.parent(parentElement);
		describeGetAttribute(wait, attributeName);
		wait.findAndCalculate(e->e.getAttribute(attributeName));
		return wait.go();
	}

	public boolean isAbsent(MobileElement e) {
		return waitAbsent(e);
	}

	public boolean waitAbsent(MobileElement e) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		wait.duration(d);
		describeIsAbsent(wait);
		wait.calculate(el -> {
			boolean result;
			try {
				result = !el.isDisplayed();
			} catch (NoSuchElementException | StaleElementReferenceException ex) {
				result = true;
			}
			return result;
		});
		untilTrue(wait);
		return getBooleanResult(wait, true);
	}

	public boolean isAbsent(By by) {
		return waitAbsent(by, null);
	}

	public boolean isAbsent(By by, WebElement parentElement) {
		return waitAbsent(by, parentElement);
	}

	public boolean waitAbsent(By by) {
		return waitAbsent(by, null);
	}

	public boolean waitAbsent(By by, WebElement parentElement) {
		ByWait<Boolean> wait = new ByWait<>();
		wait.parent(parentElement).with(by);
		wait.duration(d);
		wait.when(locator->true);
		wait.calculate(locator -> {
			List<MobileElement> elements = wait.multiElementSearch.apply(locator);
			return elements.isEmpty() || elements.stream().noneMatch(RemoteWebElement::isDisplayed);
		});
		describeIsAbsent(wait);
		untilTrue(wait);
		return getBooleanResult(wait, true);
	}

	public void clear(MobileElement e) {
		ElementWait<Void> wait = wait(e, Void.class);
		describeClear(wait);
		wait.execute(RemoteWebElement::clear).go();
		try {
			DriverProvider.get().hideKeyboard();
		} catch (Exception ex) {
			LogManager.getLogger().warn("Failed to hide keyboard: ", ex);
		}
	}

	public void check(MobileElement checkbox) {
		ElementWait<Void> wait = wait(checkbox, Void.class);
		wait.describe("check a checkbox");
		wait.execute(RemoteWebElement::click);
		wait.until(() -> ElementHelper.isCheckboxChecked(checkbox));
		wait.go();
	}

	public void check(By checkboxLocator) {
		ByWait<MobileElement> wait = wait(checkboxLocator, MobileElement.class);
		wait.describe("check a checkbox");
		wait.findAndCalculate(e -> { e.click(); return e;});
		wait.until(ElementHelper::isCheckboxChecked);
		wait.go();
	}

	public void uncheck(MobileElement checkbox) {
		ElementWait<Void> wait = wait(checkbox, Void.class);
		wait.describe("uncheck a checkbox");
		wait.execute(RemoteWebElement::click);
		wait.until(() -> !ElementHelper.isCheckboxChecked(checkbox));
		wait.go();
	}

	public void uncheck(By checkboxLocator) {
		ByWait<MobileElement> wait = wait(checkboxLocator, MobileElement.class);
		wait.describe("uncheck a checkbox");
		wait.findAndCalculate(e -> { e.click(); return e;});
		wait.until(e -> !ElementHelper.isCheckboxChecked(e));
		wait.go();
	}

	public boolean exists(MobileElement e, WaitConfig config) {
		ElementWait<Boolean> wait = wait(e, Boolean.class);
		wait.calculate(el -> el.getSize() != null);
		describeExists(wait);
		return getBooleanResult(wait, false);
	}

	public MobileElement find(By by) {
		return find(by, null);
	}

	public MobileElement find(By by, WebElement parentElement) {
		ByWait<MobileElement> wait = wait(by, MobileElement.class)
				.parent(parentElement);
		wait.duration(d);
		wait.calculate(wait.singleElementSearch).describe("find");
		return wait.go();
	}

	public List<MobileElement> findAll(By by) {
		return findAll(by, null);
	}

	public List<MobileElement> findAll(By by, WebElement parentElement) {
		ByWait<List<MobileElement>> wait = new ByWait<>();
		wait.duration(d);
		wait.parent(parentElement).with(by);
		wait.calculate(wait.multiElementSearch).describe("find all");
		return wait.go();
	}
	
	public void tap(By by) {
		tap(by, null);
	}

	public void tap(By by, WebElement parentElement) {
		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
		wait.duration(d);
		wait.findAndExecute(e -> e.tap(1, 5));
		wait.describe("tap");
		wait.go();
	}

	public boolean exists(By by) {
		return exists(by, null);
	}

	public boolean exists(By by, WebElement parentElement) {
		return find(by, parentElement) != null;
	}

	public int getCount(By by) {
		return getCount(by, null);
	}

	public int getCount(By by, WebElement parentElement) {
		return findAll(by, parentElement).size();
	}

	private void describeIsDisplayed(BaseWait wait) {
		wait.describe("check if element is displayed");
	}

	private void describeAreDisplayed(BaseWait wait) {
		wait.describe("check if all elements are displayed");
	}

	private void describeClick(BaseWait wait) {
		wait.describe("click");
	}

	private void describeGetText(BaseWait wait) {
		wait.describe("get text");
	}

	private void describeGetAttribute(BaseWait wait, String attributeName) {
		wait.describe("get attribute ["+attributeName+"]");
	}

	private void describeSetText(BaseWait wait, CharSequence text) {
		wait.describe("set text [" + text+ "]");
	}

	private void describeClear(BaseWait wait) {
		wait.describe("clear contents");
	}

	private void describeIsAbsent(BaseWait wait) {
		wait.describe("is absent");
	}

	private void describeExists(BaseWait wait) {
		wait.describe("exists");
	}

	private boolean getBooleanResult(BaseWait wait, boolean defaultResultWhenNull) {
		Boolean result = (Boolean) wait.go();
		return result == null ? defaultResultWhenNull : result;
	}

	private void untilTrue(BaseWait<?, Boolean> wait) {
		wait.until(result -> result);
	}

	private Consumer<MobileElement> buildSetTextOperation(String text) {
		return el -> {
			if (Configuration.isAndroid()) {
				((AndroidElement)el).replaceValue(text);
			} else {
				el.click();
				el.clear();
				el.sendKeys("");
				el.setValue(text);
			}
			try {
				if (Configuration.isIOS() || ADBUtils.isKeyboardShown()) {
					DriverProvider.get().hideKeyboard();
				}
			} catch (Exception e) {
				LogManager.getLogger().warn("Failed to hide keyboard: ", e);
			}
		};
	}

}
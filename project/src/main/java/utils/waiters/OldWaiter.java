package utils.waiters;

import driver.HasDriver;
import utils.log.CommonLogMessages;

public class OldWaiter implements CommonLogMessages, HasDriver {
//
//	public static <R> ElementWait<R> wait(MobileElement e, Class<R> resultType) {
//		ElementWait<R> wait = new ElementWait<R>();
//		wait.with(e);
//		return wait;
//	}
//
//	public static <R> ByWait<R> wait(By locator, Class<R> resultType) {
//		ByWait<R> wait = new ByWait<R>();
//		wait.with(locator);
//		return wait;
//	}
//
//	public static boolean isDisplayed(MobileElement e) {
//		return waitDisplayed(e, null);
//	}
//
//	public static boolean waitDisplayed(MobileElement e, Duration duration) {
//		ElementWait<Boolean> wait = wait(e, Boolean.class);
//		wait.duration(duration);
//		wait.calculate(RemoteWebElement::isDisplayed);
//		describeIsDisplayed(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static boolean isDisplayed(By by) {
//		return waitDisplayed(by, null, null);
//	}
//
//	public static boolean isDisplayed(By by, WebElement parentElement) {
//		return waitDisplayed(by, parentElement, null);
//	}
//
//	public static boolean waitDisplayed(By by, Duration duration) {
//		return waitDisplayed(by, null, duration);
//	}
//
//	public static boolean waitDisplayed(By by, WebElement parentElement, Duration duration) {
//		ByWait<Boolean> wait = wait(by, Boolean.class);
//		wait.duration(duration);
//		wait.parent(parentElement);
//		describeIsDisplayed(wait);
//		wait.findAndCalculate(RemoteWebElement::isDisplayed);
//		untilTrue(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static boolean areAllDisplayedForElements(List<MobileElement> list) {
//		ElementWait<Boolean> wait = new ElementWait<>();
//		wait.calculate(()-> {
//			for (MobileElement el : list) {
//				if (!el.isDisplayed()) {
//					return false;
//				}
//			}
//			return true;
//		});
//		describeAreDisplayed(wait);
//		untilTrue(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static boolean areAllDisplayedForLocators(List<By> list) {
//		ByWait<Boolean> wait = new ByWait<>();
//		wait.when(() -> true);
//		wait.calculate(()-> {
//			for (By locator : list) {
//				MobileElement el = wait.singleElementSearch.apply(locator);
//				if (!el.isDisplayed()) {
//					return false;
//				}
//			}
//			return true;
//		});
//		describeAreDisplayed(wait);
//		untilTrue(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static boolean anyContainsTextForElements(List<MobileElement> list, String text) {
//		ElementWait<Boolean> wait = new ElementWait<>();
//		wait.calculate(() -> {
//			for (MobileElement el: list) {
//				if (el.getText().contains(text)) {
//					return true;
//				}
//			}
//			return false;
//		});
//		wait.describe("Check if any of provided elements contains text ["+text+"]");
//		untilTrue(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static boolean anyContainsTextForLocators(List<By> list, String text) {
//		ByWait<Boolean> wait = new ByWait<>();
//		wait.calculate(() -> {
//			for (By locator: list) {
//				MobileElement el = wait.singleElementSearch.apply(locator);
//				if (el.getText().contains(text)) {
//					return true;
//				}
//			}
//			return false;
//		});
//		wait.describe("Check if any of elements by provided locators contains text ["+text+"]");
//		untilTrue(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static void click(MobileElement e) {
//		ElementWait<Void> wait = wait(e, Void.class);
//		describeClick(wait);
//		wait.execute(RemoteWebElement::click).go();
//	}
//
//	public static void click(By by) {
//		click(by, null);
//	}
//
//	public static void click(By by, WebElement parentElement) {
//		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
//		wait.findAndExecute(RemoteWebElement::click);
//		wait.describe("click");
//		wait.go();
//	}
//
//	public static String getText(MobileElement e) {
//		ElementWait<String> wait = wait(e, String.class);
//		describeGetText(wait);
//		return wait.execute(RemoteWebElement::getText).go();
//	}
//
//	public static String getText(By by) {
//		return getText(by, null);
//	}
//
//	public static String getText(By by, WebElement parentElement) {
//		ByWait<String> wait = wait(by, String.class);
//		wait.parent(parentElement);
//		describeGetText(wait);
//		wait.findAndCalculate(RemoteWebElement::getText);
//		return wait.go();
//	}
//
//	public static void setText(MobileElement e, String text) {
//		ElementWait<Void> wait = wait(e, Void.class);
//		describeSetText(wait, text);
//		wait.when(el -> el.isDisplayed() && el.isEnabled());
//		wait.execute(buildSetTextOperation(text));
//		wait.go();
//	}
//
//	public static void setText(By by, String text) {
//		setText(by, text, null);
//	}
//
//	public static void setText(By by, String text, WebElement parentElement) {
//		ByWait<String> wait = wait(by, String.class);
//		wait.parent(parentElement);
//		describeSetText(wait, text);
//		wait.findAndExecute(buildSetTextOperation(text));
//		wait.go();
//	}
//
//	public static void sendKeys(MobileElement e, CharSequence text) {
//		ElementWait<Void> wait = wait(e, Void.class);
//		describeSetText(wait, text);
//		wait.when(el -> el.isDisplayed() && el.isEnabled());
//		wait.execute(el -> el.sendKeys(text));
//		wait.go();
//	}
//
//	public static void sendKeys(By by, CharSequence text) {
//		sendKeys(by, text, null);
//	}
//
//	public static void sendKeys(By by, CharSequence text, WebElement parentElement) {
//		ByWait<String> wait = wait(by, String.class);
//		wait.parent(parentElement);
//		describeSetText(wait, text);
//		wait.findAndExecute(el -> el.sendKeys(text));
//		wait.go();
//	}
//
//	public static String getAttribute(MobileElement e, String attributeName) {
//		ElementWait<String> wait = wait(e, String.class);
//		describeGetAttribute(wait, attributeName);
//		wait.calculate(el->el.getAttribute(attributeName));
//		return wait.go();
//	}
//
//	public static String getAttribute(By by, String attributeName) {
//		return getAttribute(by, attributeName, null);
//	}
//
//	public static String getAttribute(By by, String attributeName, WebElement parentElement) {
//		ByWait<String> wait = wait(by, String.class);
//		wait.parent(parentElement);
//		describeGetAttribute(wait, attributeName);
//		wait.findAndCalculate(e->e.getAttribute(attributeName));
//		return wait.go();
//	}
//
//	public static boolean isAbsent(MobileElement e) {
//		return waitAbsent(e, Duration.ofSeconds(5));
//	}
//
//	public static boolean waitAbsent(MobileElement e, Duration timeout) {
//		ElementWait<Boolean> wait = wait(e, Boolean.class);
//		wait.duration(timeout);
//		describeIsAbsent(wait);
//		wait.calculate(el -> {
//			boolean result;
//			try {
//				result = !el.isDisplayed();
//			} catch (NoSuchElementException | StaleElementReferenceException ex) {
//				result = true;
//			}
//			return result;
//		});
//		untilTrue(wait);
//		return getBooleanResult(wait, true);
//	}
//
//	public static boolean isAbsent(By by) {
//		return waitAbsent(by, null, Duration.ofSeconds(5));
//	}
//
//	public static boolean isAbsent(By by, WebElement parentElement) {
//		return waitAbsent(by, parentElement, Duration.ofSeconds(5));
//	}
//
//	public static boolean waitAbsent(By by, WebElement parentElement, Duration duration) {
//		ByWait<Boolean> wait = new ByWait<>();
//		wait.parent(parentElement).with(by);
//		wait.duration(duration);
//		wait.when(locator->true);
//		wait.calculate(locator -> {
//			List<MobileElement> elements = wait.multiElementSearch.apply(locator);
//			return elements.isEmpty() || elements.stream().noneMatch(RemoteWebElement::isDisplayed);
//		});
//		describeIsAbsent(wait);
//		untilTrue(wait);
//		return getBooleanResult(wait, true);
//	}
//
//	public static void clear(MobileElement e) {
//		ElementWait<Void> wait = wait(e, Void.class);
//		describeClear(wait);
//		wait.execute(RemoteWebElement::clear).go();
//		try {
//			DriverProvider.get().hideKeyboard();
//		} catch (Exception ex) {
//			LogManager.getLogger().warn("Failed to hide keyboard: ", ex);
//		}
//	}
//
//	public static boolean exists(MobileElement e, WaitConfig config) {
//		ElementWait<Boolean> wait = wait(e, Boolean.class);
//		wait.calculate(el -> el.getSize() != null);
//		describeExists(wait);
//		return getBooleanResult(wait, false);
//	}
//
//	public static MobileElement find(By by) {
//		return find(by, null, null);
//	}
//
//	public static MobileElement find(By by, WebElement parentElement) {
//		return find(by, parentElement, null);
//	}
//
//	public static MobileElement find(By by, Duration duration) {
//		return find(by, null, duration);
//	}
//
//	public static MobileElement find(By by, WebElement parentElement, Duration duration) {
//		ByWait<MobileElement> wait = wait(by, MobileElement.class)
//				.parent(parentElement);
//		wait.duration(duration);
//		wait.calculate(wait.singleElementSearch).describe("find");
//		return wait.go();
//	}
//
//	public static List<MobileElement> findAll(By by) {
//		return findAll(by, null);
//	}
//
//	public static List<MobileElement> findAll(By by, WebElement parentElement) {
//		ByWait<List<MobileElement>> wait = new ByWait<>();
//		wait.parent(parentElement).with(by);
//		wait.calculate(wait.multiElementSearch).describe("find all");
//		return wait.go();
//	}
//
//	public static void tap(By by) {
//		tap(by, null, null);
//	}
//
//	public static void tap(By by, WebElement parentElement) {
//		tap(by, parentElement, null);
//	}
//
//	public static void tap(By by, Duration duration) {
//		tap(by, null, duration);
//	}
//
//	public static void tap(By by, WebElement parentElement, Duration duration) {
//		ByWait<Void> wait = wait(by, Void.class).parent(parentElement);
//		wait.duration(duration);
//		wait.findAndExecute(e -> e.tap(1, 5));
//		wait.describe("tap");
//		wait.go();
//	}
//
//	public static boolean exists(By by) {
//		return exists(by, null, null);
//	}
//
//	public static boolean exists(By by, WebElement parentElement) {
//		return find(by, parentElement, null) != null;
//	}
//
//	public static boolean exists(By by, Duration duration) {
//		return exists(by, null, duration);
//	}
//
//	public static boolean exists(By by, WebElement parentElement, Duration duration) {
//		return find(by, parentElement, duration) != null;
//	}
//
//	public static int getCount(By by) {
//		return getCount(by, null);
//	}
//
//	public static int getCount(By by, WebElement parentElement) {
//		return findAll(by, parentElement).size();
//	}
//
//	private static void describeIsDisplayed(BaseWait wait) {
//		wait.describe("check if element is displayed");
//	}
//
//	private static void describeAreDisplayed(BaseWait wait) {
//		wait.describe("check if all elements are displayed");
//	}
//
//	private static void describeClick(BaseWait wait) {
//		wait.describe("click");
//	}
//
//	private static void describeGetText(BaseWait wait) {
//		wait.describe("get text");
//	}
//
//	private static void describeGetAttribute(BaseWait wait, String attributeName) {
//		wait.describe("get attribute ["+attributeName+"]");
//	}
//
//	private static void describeSetText(BaseWait wait, CharSequence text) {
//		wait.describe("set text [" + text+ "]");
//	}
//
//	private static void describeClear(BaseWait wait) {
//		wait.describe("clear contents");
//	}
//
//	private static void describeIsAbsent(BaseWait wait) {
//		wait.describe("is absent");
//	}
//
//	private static void describeExists(BaseWait wait) {
//		wait.describe("exists");
//	}
//
//	private static boolean getBooleanResult(BaseWait wait, boolean defaultResultWhenNull) {
//		Boolean result = (Boolean) wait.go();
//		return result == null ? defaultResultWhenNull : result;
//	}
//
//	private static void untilTrue(BaseWait<?, Boolean> wait) {
//		wait.until(result -> result);
//	}
//
//	private static Consumer<MobileElement> buildSetTextOperation(String text) {
//		return el -> {
//			if (Configuration.isAndroid()) {
//				((AndroidElement)el).replaceValue(text);
//			} else {
//				el.click();
//				el.clear();
//				el.sendKeys("");
//				el.setValue(text);
//			}
//			try {
//				if (Configuration.isIOS() || ADBUtils.isKeyboardShown()) {
//					DriverProvider.get().hideKeyboard();
//				}
//			} catch (Exception e) {
//				LogManager.getLogger().warn("Failed to hide keyboard: ", e);
//			}
//		};
//	}

}
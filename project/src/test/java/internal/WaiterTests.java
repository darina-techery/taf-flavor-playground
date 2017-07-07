package internal;

import base.BaseTest;
import data.AppStrings;
import data.Configuration;
import driver.DriverProvider;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.DriverSteps;
import ui.internal.LoginScreenForWaiterTests;
import ui.screens.LoginScreen;
import utils.ADBUtils;
import utils.exceptions.FailedTestException;
import utils.log.CommonLogMessages;
import utils.waiters.ByWait;
import utils.waiters.Waiter;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static ui.internal.LoginScreenForWaiterTests.*;

public final class WaiterTests extends BaseTest implements CommonLogMessages {
	private final DriverSteps driverSteps = getStepsComponent().driverSteps();
	private LoginScreen loginScreen;
	private LoginScreenForWaiterTests uiTestScreen;

	@BeforeClass
	public void prepareAppAndTestScreens() {
		driverSteps.resetApplication();
		loginScreen = new LoginScreen();
		uiTestScreen = new LoginScreenForWaiterTests();
		driverSteps.readMainAppStrings(Configuration.getParameters().locale);
	}

	@Test
//	@RunOn({Platform.ANDROID_PHONE, Platform.ANDROID_TABLET})
	public void testIsKeyboardShown() {
		//TODO: add listener
		if (Configuration.isAndroid()) {
			try {
				Assert.assertThat("keyboard is shown", ADBUtils.isKeyboardShown(), is(false));
				loginScreen.fldLogin.click();
				loginScreen.fldLogin.clear();
				Assert.assertThat("keyboard is shown", ADBUtils.isKeyboardShown(), is(true));
			} finally {
				DriverProvider.get().hideKeyboard();
			}
		}
	}

	@Test(enabled = true)
	public void testIsDisplayedForVisibleElement() {
		boolean isDisplayed = new Waiter().isDisplayed(loginScreen.fldLogin);
		Assert.assertThat("isDisplayed for login field returns true", isDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testIsDisplayedForInvisibleElement() {
		boolean isDisplayed = new Waiter().isDisplayed(uiTestScreen.invalidElement);
		Assert.assertThat("isDisplayed for absent field returns false", isDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testIsDisplayedForVisibleLocator() {
		By locator = getLoginFieldLocator();
		boolean isDisplayed = new Waiter().isDisplayed(locator);
		Assert.assertThat("isDisplayed for login field locator returns true", isDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testIsDisplayedForInvisibleLocator() {
		boolean isDisplayed = new Waiter().isDisplayed(INVALID_LOCATOR);
		Assert.assertThat("isDisplayed for absent field locator returns false", isDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedForVisibleElements() {
		boolean areDisplayed = new Waiter().areAllDisplayedForElements(Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword));
		Assert.assertThat("areAllDisplayedForElements returns true when all elements are displayed",
				areDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedWhenSomeElementIsAbsent() {
		boolean areDisplayed = new Waiter().areAllDisplayedForElements(
				Arrays.asList(loginScreen.fldLogin, uiTestScreen.invalidElement));
		Assert.assertThat("areAllDisplayedForElements returns false when not all elements are displayed",
				areDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedForVisibleLocators() {
		List<By> locators = getAllFieldLocators();
		boolean areDisplayed = new Waiter().areAllDisplayedForLocators(locators);
		Assert.assertThat("areAllDisplayedForLocators returns true when all elements are displayed",
				areDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedWhenSomeLocatorIsAbsent() {
		List<By> locators = Arrays.asList(getLoginFieldLocator(), INVALID_LOCATOR);
		boolean areDisplayed = new Waiter().areAllDisplayedForLocators(locators);
		Assert.assertThat("areAllDisplayedForLocators returns false when not all elements are displayed",
				areDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testSetTextForElement() {
		String text = getMethodName();
		new Waiter().setText(loginScreen.fldLogin, text);
		Assert.assertThat("Text ["+text+"] was entered into login field", loginScreen.fldLogin.getText(), is(text));
	}

	@Test(enabled = true)
	public void testSetTextForLocator() {
		String text = getMethodName();
		By locator = getLoginFieldLocator();
		new Waiter().setText(locator, text);
		Assert.assertThat("Text ["+text+"] was entered into login field found by locator", loginScreen.fldLogin.getText(), is(text));
	}

	@Test(enabled = true)
	public void getAttributeForElement() {
		new Waiter().clear(loginScreen.fldLogin);
		String attributeName = Configuration.isAndroid() ? "text" : "value";
		String hint = new Waiter().getAttribute(loginScreen.fldLogin, attributeName);
		Assert.assertThat("'"+attributeName+"' attribute for login field contains a hint", hint, is(AppStrings.get().userIdHint));
	}

	@Test(enabled = true)
	public void getAttributeForLocator() {
		new Waiter().clear(loginScreen.fldLogin);
		By loginFieldLocator = getLoginFieldLocator();
		String attributeName = Configuration.isAndroid() ? "text" : "value";
		String hint = new Waiter().getAttribute(loginFieldLocator, attributeName);
		Assert.assertThat("'"+attributeName+"' attribute for login field contains a hint", hint, is(AppStrings.get().userIdHint));
	}

	@Test(enabled = true)
	public void testIsAbsentForAbsentElement(){
		boolean isAbsent = new Waiter().isAbsent(uiTestScreen.invalidElement);
		Assert.assertThat("Absent element was found absent", isAbsent, is(true));
	}

	@Test(enabled = true)
	public void testIsAbsentForPresentElement(){
		boolean isAbsent = new Waiter().isAbsent(loginScreen.fldLogin);
		Assert.assertThat("Present element was NOT found absent", isAbsent, is(false));
	}

	@Test(enabled = true)
	public void testIsAbsentForAbsentLocator(){
		boolean isAbsent = new Waiter().isAbsent(INVALID_LOCATOR);
		Assert.assertThat("Absent locator was found absent", isAbsent, is(true));
	}

	@Test(enabled = true)
	public void testIsAbsentForPresentLocator(){
		By locator = getLoginFieldLocator();
		boolean isAbsent = new Waiter().isAbsent(locator);
		Assert.assertThat("Present locator was NOT found absent", isAbsent, is(false));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForElementsSuccess() {
		List<MobileElement> elements = Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword);
		String text = getMethodName();
		new Waiter().setText(loginScreen.fldLogin, text);
		boolean containsText = new Waiter().anyContainsTextForElements(elements, text);
		Assert.assertThat("One of fields (login, password) contains text ["+text+"]", containsText, is(true));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForElementsFailure() {
		List<MobileElement> elements = Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword);
		String actualText = getMethodName();
		String expectedText = "No such text is found";
		new Waiter().setText(loginScreen.fldLogin, actualText);
		boolean containsText = new Waiter().anyContainsTextForElements(elements, expectedText);
		Assert.assertThat("Check of fields (login, password) contains text ["+expectedText+"]", containsText, is(false));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForLocatorsSuccess() {
		List<By> locators = getAllFieldLocators();
		String text = getMethodName();
		new Waiter().setText(loginScreen.fldLogin, text);
		boolean containsText = new Waiter().anyContainsTextForLocators(locators, text);
		Assert.assertThat("One of fields (login, password) contains text ["+text+"]", containsText, is(true));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForLocatorsFailure() {
		List<By> locators = getAllFieldLocators();
		String actualText = getMethodName();
		String expectedText = "No such text is found";
		new Waiter().setText(loginScreen.fldLogin, actualText);
		boolean containsText = new Waiter().anyContainsTextForLocators(locators, expectedText);
		Assert.assertThat("Check of fields (login, password) contains text ["+expectedText+"]", containsText, is(false));
	}

	@Test(enabled = true)
	public void testWaitFailsWhenFailOnTimeout() {
		boolean exceptionCaught = false;
		ByWait<Void> wait = new Waiter().wait(INVALID_LOCATOR, Void.class);
		wait.findAndExecute(RemoteWebElement::click);
		wait.failOnTimeout(true);
		try {
			wait.go();
		} catch (FailedTestException e) {
			exceptionCaught = true;
			Assert.assertThat("Test was failed with a proper exception when Waiter fails with failOnTimeout(true)",
					e.getMessage(), containsString(wait.getClass().getSimpleName() + " failed in Method [" + getMethodName() +"]"));
		}
		Assert.assertThat("Exception was thrown when Waiter fails with failOnTimeout(true)", exceptionCaught, is(true));
	}

	@AfterClass(alwaysRun = true)
	public void resetIOSApp() {
		if (Configuration.isIOS()) {
			driverSteps.resetApplication();
		}
	}

	@AfterClass(alwaysRun = true)
	private void sendTeardownNotificationToDriver() {
		DriverProvider.removeDriverListeners();
	}

	@AfterSuite(alwaysRun = true)
	public void shutdownDriver(){
		driverSteps.shutdownApplication();
	}

	private By getLoginFieldLocator() {
		return Configuration.isAndroid() ? VALID_LOGIN_LOCATOR_ANDROID : VALID_LOGIN_LOCATOR_IOS;
	}

	private By getPasswordFieldLocator() {
		return Configuration.isAndroid() ? VALID_PASSWORD_LOCATOR_ANDROID : VALID_PASSWORD_LOCATOR_IOS;
	}

	private List<By> getAllFieldLocators() {
		return Configuration.isAndroid() ? Arrays.asList( VALID_LOGIN_LOCATOR_ANDROID, VALID_PASSWORD_LOCATOR_ANDROID )
		: Arrays.asList( VALID_LOGIN_LOCATOR_IOS, VALID_PASSWORD_LOCATOR_IOS);
	}
}

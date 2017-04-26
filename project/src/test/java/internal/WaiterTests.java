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
import screens.LoginScreen;
import screens.internal.LoginScreenForWaiterTests;
import steps.DriverSteps;
import utils.exceptions.FailedTestException;
import utils.log.CommonLogMessages;
import utils.waiters.ByWait;
import utils.waiters.WaitConfig;
import utils.waiters.Waiter;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static screens.internal.LoginScreenForWaiterTests.*;
import static utils.waiters.Waiter.*;

public final class WaiterTests extends BaseTest implements CommonLogMessages {
	private final DriverSteps driverSteps = getStepsComponent().driverSteps();
	private LoginScreen loginScreen;
	private LoginScreenForWaiterTests uiTestScreen;

	@BeforeClass
	public void prepareAppAndTestScreens() {
		if (Configuration.isAndroid()) {
			driverSteps.resetApplication();
		}
		loginScreen = new LoginScreen();
		uiTestScreen = new LoginScreenForWaiterTests();
		driverSteps.readMainAppStrings(Configuration.getParameters().locale);
	}

	@Test(enabled = true)
	public void testIsDisplayedForVisibleElement() {
		boolean isDisplayed = isDisplayed(loginScreen.fldLogin);
		Assert.assertThat("isDisplayed for login field returns true", isDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testIsDisplayedForInvisibleElement() {
		boolean isDisplayed = isDisplayed(uiTestScreen.invalidElement);
		Assert.assertThat("isDisplayed for absent field returns false", isDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testIsDisplayedForVisibleLocator() {
		By locator = getLoginFieldLocator();
		boolean isDisplayed = isDisplayed(locator);
		Assert.assertThat("isDisplayed for login field locator returns true", isDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testIsDisplayedForInvisibleLocator() {
		boolean isDisplayed = isDisplayed(INVALID_LOCATOR);
		Assert.assertThat("isDisplayed for absent field locator returns false", isDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedForVisibleElements() {
		boolean areDisplayed = areAllDisplayedForElements(Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword));
		Assert.assertThat("areAllDisplayedForElements returns true when all elements are displayed",
				areDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedWhenSomeElementIsAbsent() {
		boolean areDisplayed = areAllDisplayedForElements(
				Arrays.asList(loginScreen.fldLogin, uiTestScreen.invalidElement));
		Assert.assertThat("areAllDisplayedForElements returns false when not all elements are displayed",
				areDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedForVisibleLocators() {
		List<By> locators = getAllFieldLocators();
		boolean areDisplayed = areAllDisplayedForLocators(locators);
		Assert.assertThat("areAllDisplayedForLocators returns true when all elements are displayed",
				areDisplayed, is(true));
	}

	@Test(enabled = true)
	public void testAreAllDisplayedWhenSomeLocatorIsAbsent() {
		List<By> locators = Arrays.asList(getLoginFieldLocator(), INVALID_LOCATOR);
		boolean areDisplayed = areAllDisplayedForLocators(locators);
		Assert.assertThat("areAllDisplayedForLocators returns false when not all elements are displayed",
				areDisplayed, is(false));
	}

	@Test(enabled = true)
	public void testSetTextForElement() {
		String text = getMethodName();
		setText(loginScreen.fldLogin, text);
		Assert.assertThat("Text ["+text+"] was entered into login field", loginScreen.fldLogin.getText(), is(text));
	}

	@Test(enabled = true)
	public void testSetTextForLocator() {
		String text = getMethodName();
		By locator = getLoginFieldLocator();
		setText(locator, text);
		Assert.assertThat("Text ["+text+"] was entered into login field found by locator", loginScreen.fldLogin.getText(), is(text));
	}

	@Test(enabled = true)
	public void getAttributeForElement() {
		clear(loginScreen.fldLogin);
		String attributeName = Configuration.isAndroid() ? "text" : "value";
		String hint = getAttribute(loginScreen.fldLogin, attributeName);
		Assert.assertThat("'"+attributeName+"' attribute for login field contains a hint", hint, is(AppStrings.get().userIdHint));
	}

	@Test(enabled = true)
	public void getAttributeForLocator() {
		clear(loginScreen.fldLogin);
		By loginFieldLocator = getLoginFieldLocator();
		String attributeName = Configuration.isAndroid() ? "text" : "value";
		String hint = getAttribute(loginFieldLocator, attributeName);
		Assert.assertThat("'"+attributeName+"' attribute for login field contains a hint", hint, is(AppStrings.get().userIdHint));
	}

	@Test(enabled = true)
	public void testIsAbsentForAbsentElement(){
		boolean isAbsent = isAbsent(uiTestScreen.invalidElement);
		Assert.assertThat("Absent element was found absent", isAbsent, is(true));
	}

	@Test(enabled = true)
	public void testIsAbsentForPresentElement(){
		boolean isAbsent = isAbsent(loginScreen.fldLogin);
		Assert.assertThat("Present element was NOT found absent", isAbsent, is(false));
	}

	@Test(enabled = true)
	public void testIsAbsentForAbsentLocator(){
		boolean isAbsent = isAbsent(INVALID_LOCATOR);
		Assert.assertThat("Absent locator was found absent", isAbsent, is(true));
	}

	@Test(enabled = true)
	public void testIsAbsentForPresentLocator(){
		By locator = getLoginFieldLocator();
		boolean isAbsent = isAbsent(locator);
		Assert.assertThat("Present locator was NOT found absent", isAbsent, is(false));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForElementsSuccess() {
		List<MobileElement> elements = Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword);
		String text = getMethodName();
		setText(loginScreen.fldLogin, text);
		boolean containsText = anyContainsTextForElements(elements, text);
		Assert.assertThat("One of fields (login, password) contains text ["+text+"]", containsText, is(true));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForElementsFailure() {
		List<MobileElement> elements = Arrays.asList(loginScreen.fldLogin, loginScreen.fldPassword);
		String actualText = getMethodName();
		String expectedText = "No such text is found";
		setText(loginScreen.fldLogin, actualText);
		boolean containsText = anyContainsTextForElements(elements, expectedText);
		Assert.assertThat("Check of fields (login, password) contains text ["+expectedText+"]", containsText, is(false));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForLocatorsSuccess() {
		List<By> locators = getAllFieldLocators();
		String text = getMethodName();
		setText(loginScreen.fldLogin, text);
		boolean containsText = anyContainsTextForLocators(locators, text);
		Assert.assertThat("One of fields (login, password) contains text ["+text+"]", containsText, is(true));
	}

	@Test(enabled = true)
	public void testAnyContainsTextForLocatorsFailure() {
		List<By> locators = getAllFieldLocators();
		String actualText = getMethodName();
		String expectedText = "No such text is found";
		setText(loginScreen.fldLogin, actualText);
		boolean containsText = anyContainsTextForLocators(locators, expectedText);
		Assert.assertThat("Check of fields (login, password) contains text ["+expectedText+"]", containsText, is(false));
	}

	@Test(enabled = true)
	public void testWaitFailsWhenFailOnTimeout() {
		boolean exceptionCaught = false;
		ByWait<Void> wait = Waiter.wait(INVALID_LOCATOR, Void.class);
		wait.findAndExecute(RemoteWebElement::click);
		wait.config(WaitConfig.get().failOnTimeout(true));
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

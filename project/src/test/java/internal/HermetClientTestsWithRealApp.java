package internal;

import base.BaseTestWithDriver;
import com.google.common.reflect.TypeToken;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import data.ui.MenuItem;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.HermetProxySteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import user.UserCredentials;
import utils.FileUtils;
import utils.JsonUtils;
import utils.runner.Assert;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HermetClientTestsWithRealApp extends BaseTestWithDriver {
	private LoginSteps loginSteps = getUiStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getUiStepsComponent().navigationSteps();

	private HermetProxySteps proxySteps = new HermetProxySteps();

	@BeforeClass
	public void reset(){
		getUiStepsComponent().driverSteps().resetApplication();
	}

	@BeforeMethod
	public void deleteCreateStubs() throws IOException {
		proxySteps.cleanupAllStubsForMainUrl();
	}

	@Test
	public void test_CreateLoginStub_verifyLoginToApp() throws IOException {
		proxySteps.createLoginStub();
		loginSteps.loginWithInvalidCredentials(new UserCredentials("foo", "foo"));
		navigationSteps.assertLandingPageLoaded();
	}

	@Test
	public void test_DeleteLoginStub_verifyLoginToAppCrashesWithoutStub() throws IOException {
		loginSteps.loginWithInvalidCredentials(new UserCredentials("foo", "foo"));
		By locator = ByHelper.getLocatorByText("Failed to login with provided credentials");
		Assert.assertThat("Alert 'failed to login with provided credentials' is shown",
				Waiter.isDisplayed(locator));
	}

	@Test
	public void test_stubTripsListWithDemoData_verifyDreamTripsPageLoaded() throws IOException {
		proxySteps.createDemoStubForTripsList();
		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.openSectionFromMenu(MenuItem.DREAM_TRIPS);

		By tripLocator = By.id("card_view");
		By headerLocator = ByHelper.getLocatorByText("Hermet Test Demo Package");
		Assert.assertThat("Dream trips are displayed", Waiter.isDisplayed(tripLocator));
		Assert.assertThat("Test trip header displayed", Waiter.isDisplayed(headerLocator));
	}

	@Test
	public void test_stubTripsList_verifyDreamTripsPageLoaded() throws IOException {
		proxySteps.createStubForTripsList();
		Type dataType = new TypeToken<List<TripWithoutDetails>>(){}.getType();
		File tripsFile = FileUtils.getResourceFile("hermet/trip_search_response.json");
		List<TripWithoutDetails> expectedData = JsonUtils.toObject(tripsFile, dataType, JsonUtils.Converter.DREAM_TRIPS);
		String expectedTripTitle = expectedData.get(0).name();

		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.openSectionFromMenu(MenuItem.DREAM_TRIPS);

		By tripLocator = By.id("card_view");
		By headerLocator = ByHelper.getLocatorByText(expectedTripTitle);
		Assert.assertThat("Dream trips are displayed", Waiter.isDisplayed(tripLocator));
		Assert.assertThat("Test trip header displayed", Waiter.isDisplayed(headerLocator));
	}
}

package internal;

import base.BaseTestWithDriver;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import data.ui.MenuItem;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.HermetProxySteps;
import steps.LoginSteps;
import steps.NavigationSteps;
import ui.screens.DreamTripsListScreen;
import user.UserCredentials;
import utils.FileUtils;
import utils.runner.Assert;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HermetClientTestsWithRealApp extends BaseTestWithDriver {
	private LoginSteps loginSteps = getStepsComponent().loginSteps();
	private NavigationSteps navigationSteps = getStepsComponent().navigationSteps();

	private HermetProxySteps proxySteps = new HermetProxySteps();

	@BeforeClass
	public void reset(){
		getStepsComponent().driverSteps().resetApplication();
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
				new Waiter().isDisplayed(locator));
	}

	@Test
	public void test_stubTripsListWithDemoData_verifyDreamTripsPageLoaded() throws IOException {
		List<TripWithoutDetails> expectedTrips = proxySteps.createDemoStubForTripsList();
		String tripName = expectedTrips.get(0).name();
		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.openSectionFromMenu(MenuItem.DREAM_TRIPS);

		By tripLocator = DreamTripsListScreen.CARD_LOCATOR;
		By headerLocator = ByHelper.getLocatorByText(tripName);
		Assert.assertThat("Dream trips are displayed", new Waiter().isDisplayed(tripLocator));
		Assert.assertThat("Test trip header displayed", new Waiter().isDisplayed(headerLocator));
	}

	@Test
	public void test_stubTripsList_verifyDreamTripsPageLoaded() throws IOException {
		File tripsFile = FileUtils.getResourceFile("hermet/trip_search_response.json");
		List<TripWithoutDetails> expectedData = proxySteps.createStubForTripsList(tripsFile);
		String expectedTripTitle = expectedData.get(0).name();

		loginSteps.loginWithValidCredentials(defaultUser);
		navigationSteps.openSectionFromMenu(MenuItem.DREAM_TRIPS);

		By tripLocator = By.id("card_view");
		By headerLocator = ByHelper.getLocatorByText(expectedTripTitle);
		Assert.assertThat("Dream trips are displayed", new Waiter().isDisplayed(tripLocator));
		Assert.assertThat("Test trip header displayed", new Waiter().isDisplayed(headerLocator));
	}
}

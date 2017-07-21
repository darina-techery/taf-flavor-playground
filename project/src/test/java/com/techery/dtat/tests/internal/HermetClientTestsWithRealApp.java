package com.techery.dtat.tests.internal;

import com.techery.dtat.tests.base.BaseTestWithRestart;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import com.techery.dtat.data.ui.MenuItem;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.techery.dtat.steps.HermetProxySteps;
import com.techery.dtat.steps.LoginSteps;
import com.techery.dtat.steps.NavigationSteps;
import com.techery.dtat.ui.screens.DreamTripsListScreen;
import com.techery.dtat.user.UserCredentials;
import com.techery.dtat.utils.FileUtils;
import com.techery.dtat.utils.runner.Assert;
import com.techery.dtat.utils.ui.ByHelper;
import com.techery.dtat.utils.waiters.Waiter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HermetClientTestsWithRealApp extends BaseTestWithRestart {
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

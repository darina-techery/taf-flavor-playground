package com.techery.dtat.tests;

import com.techery.dtat.tests.base.BaseTestForLoggedInUserWithRestart;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.techery.dtat.steps.DreamTripsSteps;
import com.techery.dtat.steps.HermetProxySteps;
import com.techery.dtat.user.UserRole;
import com.techery.dtat.utils.annotations.LoginAs;

import java.io.IOException;

public class DreamTripsTests extends BaseTestForLoggedInUserWithRestart {

	private DreamTripsSteps dreamTripsSteps = getStepsComponent().dreamTripsSteps();
	private HermetProxySteps hermetSteps = new HermetProxySteps();
	private static final String TRIP_STUB_FILE_NAME = "hermet/platinum_recent_trip_romania.json";
	private TripWithDetails expectedTrip;
	private String expectedTripName;

	@BeforeMethod
	public void cleanupHermetStubs() throws IOException {
		hermetSteps.cleanupCreatedStubsForMainUrl();
		expectedTrip = hermetSteps.createStubForTripListAndFirstTripDetails(TRIP_STUB_FILE_NAME);
		expectedTripName = expectedTrip.name();
	}

	@Test
	public void openDreamTripsScreen() throws IOException {
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.assertDreamTripsListLoaded();
	}

	@Test
	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213520")
	@Issue("https://techery.atlassian.net/browse/DTAUT-421")
	public void checkTripDetails() throws IOException {
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(expectedTripName);
		dreamTripsSteps.assertAllTripDetailsAreDisplayed(expectedTrip);
	}

	@Test
	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213524")
	@Issue("https://techery.atlassian.net/browse/DTAUT-422")
	public void checkTripDescriptions() throws IOException {
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(expectedTripName);
		dreamTripsSteps.assertAllTripDescriptionTextsPresent(expectedTrip);
	}

	@Test
	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213521")
	@Issue("https://techery.atlassian.net/browse/DTAUT-425")
	@LoginAs(role = UserRole.PLATINUM_USER)
	public void checkPlatinumUserCanBookPlatinumTrip() {
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(expectedTripName);
		dreamTripsSteps.assertBookTripButtonIsActive();
	}

	@Test
	@TestCaseId("https://techery.testrail.net/index.php?/cases/view/213522")
	@Issue("https://techery.atlassian.net/browse/DTAUT-426")
	@LoginAs(role = UserRole.DTG_VETERAN)
	public void checkDtgUserCannotBookPlatinumTrip(){
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(expectedTripName);
		dreamTripsSteps.assertBookTripButtonIsDisabled();
	}

	@AfterMethod
	public void cleanup() throws IOException {
		hermetSteps.cleanupCreatedStubsForMainUrl();
	}
}

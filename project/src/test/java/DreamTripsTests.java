import base.BaseTestAfterLogin;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.DreamTripsSteps;
import steps.HermetProxySteps;

import java.io.IOException;

public class DreamTripsTests extends BaseTestAfterLogin {

	private DreamTripsSteps dreamTripsSteps = getUiStepsComponent().dreamTripsSteps();
	private HermetProxySteps hermetSteps = new HermetProxySteps();
	private static final String TRIP_STUB_FILE_NAME = "hermet/platinum_recent_trip_romania.json";

	@BeforeMethod
	public void cleanupHermetStubs() throws IOException {
//		hermetSteps.cleanupCreatedStubsForMainUrl();
		hermetSteps.cleanupAllStubsForMainUrl();
	}

	@Test
	public void openDreamTripsScreen() throws IOException {
		hermetSteps.createDefaultStubForTripsList();
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.assertDreamTripsListLoaded();
	}

	@Test
	public void checkTripDetails() throws IOException {
		TripWithDetails expectedTrip = hermetSteps
				.createStubForTripListAndFirstTripDetails(TRIP_STUB_FILE_NAME);
		String tripName = expectedTrip.name();

		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(tripName);
		dreamTripsSteps.assertAllTripDetailsAreDisplayed(expectedTrip);
	}

	@Test
	public void checkTripDescriptions() throws IOException {
		TripWithDetails expectedTrip = hermetSteps
				.createStubForTripListAndFirstTripDetails(TRIP_STUB_FILE_NAME);
		String tripName = expectedTrip.name();

		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openPredefinedTripByName(tripName);
		dreamTripsSteps.assertAllTripDescriptionTextsPresent(expectedTrip);
	}

	@AfterMethod
	public void cleanup() throws IOException {
		hermetSteps.cleanupCreatedStubsForMainUrl();
	}
}

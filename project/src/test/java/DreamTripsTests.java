import base.BaseTestAfterLogin;
import org.testng.annotations.Test;
import steps.DreamTripsSteps;
import utils.runner.Assert;

import static org.hamcrest.Matchers.equalTo;

public class DreamTripsTests extends BaseTestAfterLogin {

	private DreamTripsSteps dreamTripsSteps = getUiStepsComponent().dreamTripsSteps();

	@Test
	public void openDreamTripsScreen(){
		dreamTripsSteps.openDreamTripsScreen();
	}

	@Test
	public void checkTripDetails(){
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openTripWithSpecificName("Las Vegas");
		Assert.assertThat("Stub response equals predefined one",
				"ololo",
				equalTo("ololo"));
	}

	@Test
	public void checkTripTextsInDetails(){
		dreamTripsSteps.openDreamTripsScreen();
		dreamTripsSteps.openTripWithSpecificName("Las Vegas");
		dreamTripsSteps.getAllTextsFromOpenedTripDetails();
		Assert.assertThat("Stub response equals predefined one",
				"ololo",
				equalTo("ololo"));
	}
}

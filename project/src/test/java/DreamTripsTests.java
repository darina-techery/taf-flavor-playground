import base.BaseTestAfterLogin;
import org.testng.annotations.Test;
import steps.DreamTripsSteps;

public class DreamTripsTests extends BaseTestAfterLogin {

	private DreamTripsSteps dreamTripsSteps = getUiStepsComponent().dreamTripsSteps();

	@Test
	public void openDreamTripsScreen(){
		dreamTripsSteps.openDreamTripsScreen();
	}
}

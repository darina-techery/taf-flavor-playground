package steps;

import actions.DriverActions;
import data.Configuration;
import org.openqa.selenium.ScreenOrientation;
import ru.yandex.qatools.allure.annotations.Step;

public class DriverSteps {

	private final DriverActions actions;

	public DriverSteps(DriverActions actions) {
		this.actions = actions;
	}

	@Step("Reset application")
	public void resetApplication(){
		actions.resetApplication();
	}

	@Step("Change screen orientation")
	public void rotateScreen(){
		ScreenOrientation screenOrientationBefore = actions.getScreenOrientation();
		if (screenOrientationBefore == ScreenOrientation.LANDSCAPE) {
			actions.rotateScreen(ScreenOrientation.PORTRAIT);
		} else {
			actions.rotateScreen(ScreenOrientation.LANDSCAPE);
		}
	}

	@Step("Shutdown application")
	public void shutdownApplication() {
		actions.closeApp();
	}


}

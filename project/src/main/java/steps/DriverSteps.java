package steps;

import actions.BaseDriverActions;
import org.openqa.selenium.ScreenOrientation;
import ru.yandex.qatools.allure.annotations.Step;

public class DriverSteps {

	private final BaseDriverActions actions;

	public DriverSteps(BaseDriverActions actions) {
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

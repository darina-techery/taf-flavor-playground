package com.techery.dtat.steps;

import com.techery.dtat.actions.DriverActions;
import com.techery.dtat.data.AppStrings;
import org.openqa.selenium.ScreenOrientation;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;

import java.util.Map;

public class DriverSteps {

	private final DriverActions actions;

	@UseActions
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

	@Step("Initialize app strings")
	public void readMainAppStrings(String locale) {
		Map<String, String> appStrings = actions.extractAppStrings(locale);
		AppStrings.add(appStrings);
	}

}

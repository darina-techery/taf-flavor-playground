package steps;

import actions.AlertActions;
import ru.yandex.qatools.allure.annotations.Step;

public class AlertSteps {
	private final AlertActions alertActions;
	public AlertSteps(AlertActions alertActions) {
		this.alertActions = alertActions;
	}

	@Step()
	public void declinePermissionRequestAlert() {

	}

}

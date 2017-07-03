package actions;

import ui.screens.TermsAndConditionsScreen;
import utils.waiters.Waiter;

import java.time.Duration;

public abstract class TermsAndConditionsActions {
	protected TermsAndConditionsScreen termsAndConditionsScreen = new TermsAndConditionsScreen();

	public boolean isTermsAndConditionsPopUpPresent() {
		return new Waiter(Duration.ofSeconds(3)).isDisplayed(termsAndConditionsScreen.chkAccept);
	}

	public void acceptTermsAndConditions() {
		Waiter wait = new Waiter();
		wait.check(termsAndConditionsScreen.chkAccept);
		wait.click(termsAndConditionsScreen.btnAccept);
	}

	public void acceptTermsAndConditionsIfRequested() {
		if (isTermsAndConditionsPopUpPresent()) {
			acceptTermsAndConditions();
		} else {
			//if the screen is absent it is ok
		}
	}
}

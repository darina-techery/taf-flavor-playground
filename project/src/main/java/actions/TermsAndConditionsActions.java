package actions;

import ui.screens.TermsAndConditionsScreen;
import utils.waiters.Waiter;

import java.time.Duration;

public abstract class TermsAndConditionsActions {
	protected TermsAndConditionsScreen termsAndConditionsScreen = new TermsAndConditionsScreen();

	public boolean isPresent() {
		return new Waiter(Duration.ofSeconds(3)).isDisplayed(termsAndConditionsScreen.chkAccept);
	}

	public void accept() {
		Waiter wait = new Waiter();
		wait.check(termsAndConditionsScreen.chkAccept);
		wait.click(termsAndConditionsScreen.btnAccept);
	}

	public void acceptIfRequired() {
		if (isPresent()) {
			accept();
		}
	}
}

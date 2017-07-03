package actions;

import org.openqa.selenium.UnhandledAlertException;
import utils.waiters.AnyWait;
import utils.waiters.Waiter;

import java.time.Duration;

public class IPadLoginActions extends LoginActions {

	private IPadAlertActions iPadAlertActions = new IPadAlertActions();

	public boolean waitUntilLoginScreenGone() {
		AnyWait<Void, Boolean> wait = new AnyWait<>();
		wait.describe("wait until logged in or access request shown.");
		wait.duration(Duration.ofMinutes(1));
		wait.calculate(()-> {
			boolean result = new Waiter(Duration.ofSeconds(1)).isAbsent(loginPage.fldLogin);
			if (!result) {
				result = iPadAlertActions.isPermissionRequestAlertDisplayed();
			}
			return result;
		});
		wait.until(Boolean::booleanValue);
		try {
			wait.go();
			return wait.isSuccess();
		} catch (UnhandledAlertException e) {
			if (iPadAlertActions.isPermissionRequestAlertDisplayed()) {
				return true;
			} else {
				throw e;
			}
		}
	}
}

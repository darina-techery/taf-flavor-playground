package com.techery.dtat.actions;

import org.openqa.selenium.UnhandledAlertException;
import com.techery.dtat.utils.waiters.AnyWait;
import com.techery.dtat.utils.waiters.Waiter;

import java.time.Duration;

public class IOSLoginActions extends LoginActions {

	private IOSAlertActions iosAlertActions = new IOSAlertActions();

	public boolean waitUntilLoginScreenGone() {
		AnyWait<Void, Boolean> wait = new AnyWait<>();
		wait.describe("wait until logged in or access request shown.");
		wait.duration(Duration.ofMinutes(1));
		wait.calculate(()-> {
			boolean result = new Waiter(Duration.ofSeconds(1)).isAbsent(loginPage.fldLogin);
			if (!result) {
				result = iosAlertActions.isPermissionRequestAlertDisplayed();
			}
			return result;
		});
		wait.until(Boolean::booleanValue);
		try {
			wait.go();
			return wait.isSuccess();
		} catch (UnhandledAlertException e) {
			if (iosAlertActions.isPermissionRequestAlertDisplayed()) {
				return true;
			} else {
				throw e;
			}
		}
	}
}

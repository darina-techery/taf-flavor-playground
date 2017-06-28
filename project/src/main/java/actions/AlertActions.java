package actions;

import data.Configuration;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import utils.waiters.Waiter;

import java.time.Duration;

public abstract class AlertActions extends BaseUiActions {
	public void declinePermissionRequestAlert() {
		/*---Not required on Android---*/
		if (Configuration.isIOS()){
			By declineButtonLocator = MobileBy.AccessibilityId("Don’t Allow");
			Duration waitForAlertDuration = Duration.ofSeconds(10);
			Waiter waiter = new Waiter(waitForAlertDuration);
			try {
				if (waiter.waitDisplayed(declineButtonLocator)) {
					//close alert 1 out of 2
					waiter.find(declineButtonLocator).tap(1, 5);
					//close alert 2 out of 2 (if exists)
					waiter.find(declineButtonLocator).tap(1, 5);
				}
			} catch (Exception e) {
			/* it will only be thrown if no alerts were found, and it is valid */
			}
		}
	}
}

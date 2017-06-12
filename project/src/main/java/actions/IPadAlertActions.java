package actions;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import utils.waiters.Waiter;

import java.time.Duration;

public class IPadAlertActions extends AlertActions {
	public void declinePermissionRequestAlert() {
		By declineButtonLocator = MobileBy.AccessibilityId("Don’t Allow");
		Duration waitForAlertDuration = Duration.ofSeconds(10);
		try {
			if (Waiter.waitDisplayed(declineButtonLocator, waitForAlertDuration)) {
				//close alert 1 out of 2
				Waiter.find(declineButtonLocator, waitForAlertDuration).tap(1, 5);
				//close alert 2 out of 2 (if exists)
				Waiter.find(declineButtonLocator, waitForAlertDuration).tap(1, 5);
			}
		} catch (Exception e) {
			/* it will only be thrown if no alerts were found, and it is valid */
		}
	}
}

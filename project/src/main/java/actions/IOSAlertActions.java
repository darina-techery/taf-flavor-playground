package actions;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.List;

public class IOSAlertActions extends AlertActions {
	private static final By ALERT_BUTTON_LOCATOR = By.className("XCUIElementTypeButton");
	private static final Duration DISMISS_ALERT_TIMEOUT = Duration.ofSeconds(2);
	private static final Duration ALERT_SHOWN_TIMEOUT = Duration.ofSeconds(10);

	public boolean isPermissionRequestAlertDisplayed() {
		List<MobileElement> alertButtons = new Waiter(ALERT_SHOWN_TIMEOUT).findAll(ALERT_BUTTON_LOCATOR);
		return alertButtons != null && alertButtons.size() == 2;
	}

	public void acceptPermissionRequestAlert() {
		Waiter waiter = new Waiter(ALERT_SHOWN_TIMEOUT);
		try {
			if (waiter.isDisplayed(alert.alertBox)) {
//				//close alert 1 out of 3
				tapAcceptAlertButton();
//				//close alert 2 out of 3 (if exists)
				tapAcceptAlertButton();
//				//close alert 3 out of 3 (if exists)
				tapAcceptAlertButton();
			}
		} catch (Exception e) {
		/* it will only be thrown if no alerts were found, and it is valid */
		}
	}

	private void tapAcceptAlertButton() {
		int acceptButtonIndex = 1;
		new Waiter(DISMISS_ALERT_TIMEOUT)
				.findAll(ALERT_BUTTON_LOCATOR, alert.alertBox)
				.get(acceptButtonIndex)
				.tap(1, 5);
	}
}

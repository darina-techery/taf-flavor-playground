package actions;

import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import utils.waiters.ByWait;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.List;

public class IOSAlertActions extends AlertActions {
	private static final By ALERT_BUTTON_LOCATOR = By.className("XCUIElementTypeButton");
	private static final Duration DISMISS_ALERT_TIMEOUT = Duration.ofSeconds(3);
	private static final Duration ALERT_SHOWN_TIMEOUT = Duration.ofSeconds(10);

	public boolean isPermissionRequestAlertDisplayed() {
		List<MobileElement> alertButtons = new Waiter(ALERT_SHOWN_TIMEOUT).findAll(ALERT_BUTTON_LOCATOR);
		return alertButtons != null && alertButtons.size() == 2;
	}

	public void acceptPermissionRequestAlert() {
		Waiter waiter = new Waiter(ALERT_SHOWN_TIMEOUT);
		try {
			if (waiter.isDisplayed(alert.alertBox)) {
				tapAcceptAlertButton();
			}
		} catch (Exception e) {
			LogManager.getLogger().info("Exception when trying to accept permission request: ", e);
			e.printStackTrace();
			/* it will only be thrown if no alerts were found, and it is valid */
		}
	}

	private void tapAcceptAlertButton() {
		int acceptButtonIndex = 1;
		ByWait<Void> buttonWait = new ByWait<>();
		buttonWait.duration(ALERT_SHOWN_TIMEOUT);
		buttonWait.with(ALERT_BUTTON_LOCATOR);
		buttonWait.parent(alert.alertBox);
		buttonWait.findAllAndExecute(buttons -> {
			try {
				if (!buttons.isEmpty()) {
					buttons.get(acceptButtonIndex).click();
				}
			} catch (Exception e) {
				LogManager.getLogger().info("button not found", e);
			}
		});
		buttonWait.until(()-> new Waiter(Duration.ofMillis(500)).isAbsent(alert.alertBox));
		buttonWait.describe("Click 'Allow' or 'OK' button on permission requests until alert box is gone.");
		buttonWait.go();
	}
}

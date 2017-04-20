package actions;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import utils.waiters.Waiter;

public class IPhoneLoginActions extends LoginActions implements Waiter {

	private IPhoneDriverActions driverActions = new IPhoneDriverActions();

	@Override
	public void loginToApp() {
		super.loginToApp();
		By decline = MobileBy.AccessibilityId("Don’t Allow");
		driverActions.declineAlert(decline);
	}
}

package actions;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class IPhoneLoginActions extends LoginActions {

	private IPhoneDriverActions driverActions = new IPhoneDriverActions();

	@Override
	public void submit() {
		super.submit();
		By decline = MobileBy.AccessibilityId("Don’t Allow");
		driverActions.declineAlert(decline);
	}
}

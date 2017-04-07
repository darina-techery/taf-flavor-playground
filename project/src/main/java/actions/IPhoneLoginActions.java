package actions;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IPhoneLoginActions extends BaseLoginActions {

	private IPhoneDriverActions driverActions = new IPhoneDriverActions();

	@Override
	public void waitForScreen() {
		new WebDriverWait(getDriver(), 30)
				.until(ExpectedConditions.elementToBeClickable(loginPage.fldLogin));
	}

	@Override
	public void loginToApp() {
		super.loginToApp();
		By decline = MobileBy.AccessibilityId("Don’t Allow");
		driverActions.declineAlert(decline);
	}
}

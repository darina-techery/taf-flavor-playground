package actions;

import ui.screens.LoginScreen;
import utils.runner.Assert;
import utils.waiters.Waiter;

import java.time.Duration;
import java.util.Arrays;

public abstract class LoginActions extends BaseUiActions {

	private LoginScreen loginPage = new LoginScreen();
	private Waiter baseWait = new Waiter();

	public void setLogin(String username) {
		baseWait.setText(loginPage.fldLogin, username);
	}

	public void setPassword(String password) {
		baseWait.setText(loginPage.fldPassword, password);
	}

	public void submit(){
		baseWait.click(loginPage.btnLogin);
	}

	public String getCurrentLoginValue(){
		return baseWait.getText(loginPage.fldLogin);
	}

	@Override
	public void waitForScreen() {
		boolean areFieldsPresent = baseWait.areAllDisplayedForElements(Arrays.asList(loginPage.fldLogin, loginPage.fldPassword));
		Assert.assertThat("Login and password fields are present on the screen", areFieldsPresent);
	}

	public boolean waitUntilLoginScreenGone() {
		return new Waiter(Duration.ofMinutes(1)).waitAbsent(loginPage.fldLogin);
	}

	public boolean isScreenActive() {
		return baseWait.isDisplayed(loginPage.fldLogin);
	}

}

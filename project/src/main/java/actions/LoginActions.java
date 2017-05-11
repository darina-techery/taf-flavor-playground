package actions;

import screens.LoginScreen;
import utils.runner.Assert;

import java.util.Arrays;

import static utils.waiters.Waiter.*;

public abstract class LoginActions extends BaseActions {

	LoginScreen loginPage = new LoginScreen();

	public void setLogin(String username) {
		setText(loginPage.fldLogin, username);
	}

	public void setPassword(String password) {
		setText(loginPage.fldPassword, password);
	}

	public void submit(){
		click(loginPage.btnLogin);
	}

	public String getCurrentLoginValue(){
		return getText(loginPage.fldLogin);
	}

	@Override
	public void waitForScreen() {
		boolean areFieldsPresent = areAllDisplayedForElements(Arrays.asList(loginPage.fldLogin, loginPage.fldPassword));
		Assert.assertThat("Login and password fields are present on the screen", areFieldsPresent);
	}

	public boolean isScreenActive() {
		return isDisplayed(loginPage.fldLogin);
	}

}

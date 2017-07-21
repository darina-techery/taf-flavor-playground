package com.techery.dtat.actions;

import com.techery.dtat.data.AppStrings;
import com.techery.dtat.ui.screens.LoginScreen;
import com.techery.dtat.utils.runner.Assert;
import com.techery.dtat.utils.waiters.Waiter;

import java.time.Duration;
import java.util.Arrays;

public abstract class LoginActions extends BaseUiActions {

	static final String LOGIN_HINT = AppStrings.get().userIdHint;
	static final String PASSWORD_HINT = AppStrings.get().userPasswordHint;

	LoginScreen loginPage = new LoginScreen();
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

	public String getLoginFieldValue(){
		String loginValue = new Waiter().getText(loginPage.fldLogin);
		return (loginValue.equals(LOGIN_HINT)) ? "" : loginValue;
	}

	public String getPasswordFieldValue() {
		String passwordValue = new Waiter().getText(loginPage.fldPassword);
		return (passwordValue.equals(PASSWORD_HINT)) ? "" : passwordValue;
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

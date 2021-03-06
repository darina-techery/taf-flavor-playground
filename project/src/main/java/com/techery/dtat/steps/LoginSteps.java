package com.techery.dtat.steps;

import com.techery.dtat.actions.AlertActions;
import com.techery.dtat.actions.LoginActions;
import com.techery.dtat.actions.NavigationActions;
import com.techery.dtat.actions.rest.UserAPIActions;
import com.techery.dtat.user.UserCredentials;
import com.techery.dtat.user.UserCredentialsProvider;
import com.techery.dtat.user.UserSessionManager;
import com.techery.dtat.utils.annotations.LoginAs;
import com.techery.dtat.utils.annotations.UseActions;
import com.techery.dtat.utils.exceptions.FailedTestException;
import com.techery.dtat.utils.runner.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class LoginSteps {
	private final LoginActions loginActions;
	private final NavigationActions navigationActions;
	private final AlertActions alertActions;
	private final UserAPIActions userAPIActions;

	@UseActions
	public LoginSteps(LoginActions loginActions, AlertActions alertActions, NavigationActions navigationActions,
		UserAPIActions userAPIActions) {
		this.alertActions = alertActions;
		this.loginActions = loginActions;
		this.navigationActions = navigationActions;
		this.userAPIActions = userAPIActions;
	}

	@Step("Login user before test (default or provided in @LoginAs)")
	public void loginUserBeforeTest(UserCredentials defaultUser, LoginAs loginData) {
		if (loginData != null) {
			UserCredentials userCredentials = new UserCredentialsProvider().getUserByRole(loginData.role());
			loginEvenIfLoggedId(userCredentials);
			UserSessionManager.setActiveUser(userCredentials);
		} else {
			loginIfRequired(defaultUser);
			UserSessionManager.setActiveUser(defaultUser);
		}
	}

	@Step("Submit provided login credentials: '{0}' ")
	public void submitCredentials(UserCredentials user) {
		loginActions.waitForScreen();
		loginActions.setLogin(user.getUsername());
		loginActions.setPassword(user.getPassword());
		loginActions.submit();
	}

	@Step("Login to application with valid credentials: '{0}'")
	public void loginWithValidCredentials(UserCredentials user) {
		userAPIActions.authenticateUserInBackground(user);
		userAPIActions.acceptTermsAndConditionsInBackground();
		submitCredentials(user);
		if (!loginActions.waitUntilLoginScreenGone()) {
			throw new FailedTestException("Failed to login with provided valid credentials as "+user.getUsername());
		}
		alertActions.acceptPermissionRequestAlert();
	}

	@Step("Try to login to application with invalid credentials: '{0}'")
	public void loginWithInvalidCredentials(UserCredentials user) {
		submitCredentials(user);
	}

	@Step("Login to application if required")
	public void loginIfRequired(UserCredentials user) {
		if (loginActions.isScreenActive()) {
			loginWithValidCredentials(user);
		}
	}

	@Step("Get state: if login screen is active")
	public boolean isScreenActive() {
		return loginActions.isScreenActive();
	}

	@Step("Get state: if login screen is active")
	public void assertThatLoginScreenIsShown() {
		Assert.assertThat("Login screen should be active", isScreenActive());
	}

	@Step("Login to application as '{0}', even if already logged in")
	public void loginEvenIfLoggedId(UserCredentials user) {
		if (!loginActions.isScreenActive()) {
			navigationActions.logout();
			alertActions.confirmLogout();
		}
		loginWithValidCredentials(user);
	}

	@Step("Verify that password field is empty")
	public void assertThatPasswordFieldEmpty() {
		String currentPasswordValue = loginActions.getPasswordFieldValue();
		Assert.assertThat("Password field should be empty", currentPasswordValue, isEmptyString());
	}
}

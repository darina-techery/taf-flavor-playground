package steps;

import actions.AlertActions;
import actions.LoginActions;
import actions.NavigationActions;
import actions.TermsAndConditionsActions;
import ru.yandex.qatools.allure.annotations.Step;
import user.UserCredentials;
import utils.annotations.UseActions;
import utils.exceptions.FailedTestException;

public class LoginSteps {
	private final LoginActions loginActions;
	private final NavigationActions navigationActions;
	private final AlertActions alertActions;
	private final TermsAndConditionsActions termsAndConditionsActions;

	@UseActions
	public LoginSteps(LoginActions loginActions, AlertActions alertActions, NavigationActions navigationActions,
	                  TermsAndConditionsActions termsAndConditionsActions) {
		this.alertActions = alertActions;
		this.loginActions = loginActions;
		this.navigationActions = navigationActions;
		this.termsAndConditionsActions = termsAndConditionsActions;
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
		submitCredentials(user);
		if (!loginActions.waitUntilLoginScreenGone()) {
			throw new FailedTestException("Failed to login with provided valid credentials as "+user.getUsername());
		}
		alertActions.acceptPermissionRequestAlert();
		termsAndConditionsActions.acceptTermsAndConditionsIfRequested();
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

	@Step("Login to application as '{0}', even if already logged in")
	public void loginEvenIfLoggedId(UserCredentials user) {
		if (!loginActions.isScreenActive()) {
			navigationActions.logout();
			alertActions.confirmLogout();
		}
		loginWithValidCredentials(user);
	}
}

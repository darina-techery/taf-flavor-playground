package actions;

import screens.LoginScreen;

public abstract class LoginActions extends BaseActions {

	LoginScreen loginPage = new LoginScreen();

	public void setLogin(String username) {
		loginPage.fldLogin.click();
		loginPage.fldLogin.clear();
		loginPage.fldLogin.sendKeys(username);
	}

	public void setPassword(String password) {
		loginPage.fldPassword.click();
		loginPage.fldPassword.clear();
		loginPage.fldPassword.sendKeys(password);
	}

	public void loginToApp(){
		loginPage.btnLogin.click();
	}

	public String getCurrentLoginValue(){
		return loginPage.fldLogin.getText();
	}

}

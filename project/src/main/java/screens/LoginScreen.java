package screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class LoginScreen extends BaseScreen {
	@iOSFindBy(accessibility = "USER_ID")
	@AndroidFindBy(id = "et_username")
	public MobileElement fldLogin;

	@iOSFindBy(accessibility = "ACCEPTALERT")
	public MobileElement fldLoginTablet;

	@iOSFindBy(accessibility = "PASSWORD")
	@AndroidFindBy(id = "et_password")
	public MobileElement fldPassword;

	@iOSFindBy(accessibility = "LOGINBTN")
	@AndroidFindBy(id = "btn_login")
	public MobileElement btnLogin;
}

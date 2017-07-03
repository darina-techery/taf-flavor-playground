package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import ui.BaseUiModule;

public class TermsAndConditionsScreen extends BaseUiModule {
	@AndroidFindBy(id = "accept_checkbox")
	public MobileElement chkAccept;

	@AndroidFindBy(id = "acceptTermsAndConditions")
	public MobileElement btnAccept;

	@AndroidFindBy(id = "reject")
	public MobileElement btnReject;
}

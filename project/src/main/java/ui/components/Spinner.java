package ui.components;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import ui.BaseUiModule;

public class Spinner extends BaseUiModule {
	@AndroidFindBy(id = "spinnerIcon")
	public MobileElement spinnerIcon;
}

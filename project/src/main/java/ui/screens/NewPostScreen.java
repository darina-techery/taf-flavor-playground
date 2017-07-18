package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.iOSFindBy;
import ui.BaseUiModule;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.CHAIN;

public class NewPostScreen extends BaseUiModule {

	@HowToUseLocators(androidAutomation = CHAIN)
	@AndroidFindBy(id = "container_user")
	@AndroidFindBy(id = "avatar")
	@iOSFindBy(accessibility = "avatar")
	public MobileElement avatar;

	@HowToUseLocators(androidAutomation = CHAIN)
	@AndroidFindBy(id = "container_user")
	@AndroidFindBy(id = "name")
	@iOSFindBy(accessibility = "userNameLabel")
	public MobileElement lblUsername;

	@AndroidFindBy(id = "close")
	@iOSFindBy(accessibility = "closePost")
	public MobileElement btnClose;

	@HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
	@AndroidFindBy(id = "photos")
	@AndroidFindBy(id = "post")
	@iOSFindBy(accessibility = "postContentCollectionView")
	@iOSFindBy(className = "XCUIElementTypeTextView")
	public MobileElement fldPostContent;

	@HowToUseLocators(androidAutomation = CHAIN)
	@AndroidFindBy(id = "container_post")
	@AndroidFindBy(id = "image")
	@iOSFindBy(accessibility = "addImageButton")
	public MobileElement btnAddImage;

	@HowToUseLocators(androidAutomation = CHAIN)
	@AndroidFindBy(id = "container_post")
	@AndroidFindBy(id = "location")
	@iOSFindBy(accessibility = "tagLocationButton")
	public MobileElement btnAddLocation;

	@HowToUseLocators(androidAutomation = CHAIN)
	@AndroidFindBy(id = "container_post")
	@AndroidFindBy(id = "post_button")
	@iOSFindBy(accessibility = "createPostButton")
	public MobileElement btnCreatePost;
}

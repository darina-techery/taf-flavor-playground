package ui.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.apache.logging.log4j.core.config.Configuration;
import org.openqa.selenium.By;
import ui.BaseUiModule;

import java.util.List;

public class BucketListScreen extends BaseUiModule {


    @AndroidFindBy(id = "bucket_cell_root")
 	@iOSFindBy(accessibility = "feedCell")
 	public List<MobileElement> bucketListContainers;

    @iOSFindBy(uiAutomator = ".collectionViews()[0].cells()[0].textFields()[0]")
    @AndroidFindBy(id = "textViewName")
    public List<MobileElement> bucketListTextEntries;

    /*----------- LINKS --------------*/
    @AndroidFindBy(id = "appbar")
    public MobileElement menuUpper;

    @AndroidFindBy(id = "pager")
    public MobileElement listPager;

	 /*----------- BUTTONS ------------*/

    @iOSFindBy(uiAutomator = ".navigationBars().firstWithPredicate(\"buttons.name contains 'filter icon' \").staticTexts()[0]")
    @AndroidFindBy(xpath = "//*[contains(@text, 'Bucket List')]")
    public MobileElement fldPageTitle;

    @iOSFindBy(uiAutomator = ".collectionViews()[0].cells()[0].textFields()[0]")
    @AndroidFindBy(id = "textViewName")
    public MobileElement fldFirstItem;

    @iOSFindBy(accessibility = "Add")
    @AndroidFindBy(id = "action_quick")
    public MobileElement btnAddNewItem;

    @iOSFindBy(uiAutomator = ".textFields()[0]")
    @AndroidFindBy(id = "editTextQuickInput")
    public MobileElement fldCreateItemInput;

    @iOSFindBy(uiAutomator = ".collectionViews()[0].cells()")
    @AndroidFindBy(id = "textViewName")
    public List<MobileElement> listFldsBucketListItems;

    @iOSFindBy(uiAutomator = ".collectionViews()[0].cells()")
    @AndroidFindBy(id = "buttonNew")
    public MobileElement btnCreateMyOwnList;

    @iOSFindBy(uiAutomator = ".collectionViews()[0].cells()")
    @AndroidFindBy(id = "buttonPopular")
    public MobileElement btnChoseFromPopular;

    @iOSFindBy(uiAutomator = ".scrollViews()")
    public MobileElement fldDialer;

}

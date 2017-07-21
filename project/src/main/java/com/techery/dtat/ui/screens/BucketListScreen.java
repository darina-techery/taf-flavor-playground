package com.techery.dtat.ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import com.techery.dtat.ui.BaseUiModule;

import java.util.List;

public class BucketListScreen extends BaseUiModule {


    @AndroidFindBy(id = "bucket_cell_root")
 	@iOSFindBy(accessibility = "feedCell")
 	public List<MobileElement> bucketListContainers;

    @AndroidFindBy(id = "textViewName")
    public List<MobileElement> bucketListTextEntries;

    @AndroidFindBy(id = "appbar")
    public MobileElement tabBar;


	 /*----------- BUTTONS ------------*/
    @iOSFindBy(accessibility = "Done")
    public MobileElement btnDoneNaming;

    @iOSFindBy(accessibility = "Close")
    public MobileElement btnCancelNaming;

    @iOSFindBy(accessibility = "Add")
    @AndroidFindBy(id = "action_quick")
    public MobileElement btnAddNewItem;


    /*------------ FIELDS ------------*/
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Bucket List')]")
    public MobileElement fldPageTitle;

    @AndroidFindBy(id = "editTextQuickInput")
    public MobileElement fldNewBucketItemName;

}

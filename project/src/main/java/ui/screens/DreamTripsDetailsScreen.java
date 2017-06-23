package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import ui.BaseUiModule;

import java.util.List;

public class DreamTripsDetailsScreen extends BaseUiModule {

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "viewPagerGallery")
    public MobileElement imgPicOfTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPoints")
    public MobileElement txtPointsForTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPrice")
    public MobileElement txtPriceOfTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPlace")
    public MobileElement txtPlaceOfTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewName")
    public MobileElement txtNameOfTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewScheduleDescription")
    public MobileElement txtTripDuration;

    public static final String IOS_COLLECTION = "XCUIElementTypeCollectionView";
    public static final String IOS_CELL = "XCUIElementTypeCell";
    public static final String IOS_TEXT = "XCUIElementTypeStaticText";

    @iOSFindBy(xpath = "//"+ IOS_COLLECTION+"/"+IOS_CELL+"[6]/"+IOS_TEXT+"[1]")
    public MobileElement txtTripName;

    @iOSFindBy(xpath = "//"+ IOS_COLLECTION+"/"+IOS_CELL+"[6]/"+IOS_TEXT+"[2]")
    public MobileElement txtTripPrice;

    @iOSFindBy(xpath = "//"+ IOS_COLLECTION+"/"+IOS_CELL+"[6]/"+IOS_TEXT+"[3]")
    public MobileElement txtLocation;

    @iOSFindBy(xpath = "//"+ IOS_COLLECTION+"/"+IOS_CELL+"[3]/"+IOS_TEXT+"[1]")
    @AndroidFindBy(id = "textViewDate")
    public MobileElement txtTripDates;

    @iOSFindBy(xpath = "//"+ IOS_COLLECTION+"/"+IOS_CELL+"[3]/"+IOS_TEXT+"[2]")
    public MobileElement txtDuration;

    @iOSFindBy(accessibility = "tripDetails_bookTripButton")
    @AndroidFindBy(id = "textViewBookIt")
    public MobileElement btnBookIt;

    @iOSFindBy(xpath = "//"+IOS_COLLECTION+"/"+IOS_CELL+"[5]/"+IOS_TEXT+"[2]")
    @AndroidFindBy(id = "textViewDescription")
    public MobileElement txtShortDescriptionOfTrip;

    @iOSFindBy(xpath = "//"+IOS_COLLECTION+"/"+IOS_CELL+"[5]/"+IOS_TEXT+"[2]")
    @AndroidFindBy(id = "expandable_text")
    public MobileElement txtLongDescriptionOfTrip;

    @AndroidFindBy(id = "expandable_text")
    public List<MobileElement> listLongDescriptionOfTrip;

//    @iOSFindBy(uiAutomator = "")
//    @AndroidFindBy(id = "fdsf")
    public MobileElement txtPlatinumInclusionsInTrip;

//    @iOSFindBy(uiAutomator = "")
//    @AndroidFindBy(id = "fdef")
    public MobileElement txtItineraryOfTrip;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "post")
    public MobileElement btnPostComment;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "listViewContent")
    public MobileElement areaToScrollText;

    @iOSFindBy(accessibility = "trip favorite icon")
    public MobileElement btnLikeIt;

}

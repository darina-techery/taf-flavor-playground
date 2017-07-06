package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import ui.BaseUiModule;

import java.util.List;

public class DreamTripsDetailsScreen extends BaseUiModule {

    @iOSFindBy(accessibility = "picCollectionOfTrip")
    @AndroidFindBy(id = "viewPagerGallery")
    public MobileElement imgPicOfTrip;

    @iOSFindBy(accessibility = "tripDetails_tripPoints")
    @AndroidFindBy(id = "textViewPoints")
    public MobileElement txtPointsForTrip;

    @iOSFindBy(accessibility = "tripDetails_tripPrice")
    @AndroidFindBy(id = "textViewPrice")
    public MobileElement txtPriceOfTrip;

    @iOSFindBy(accessibility = "FEATURED TRIP")
    public MobileElement lblFeaturedTrip;

    @iOSFindBy(accessibility = "tripDetails_tripLocation")
    @AndroidFindBy(id = "textViewPlace")
    public MobileElement txtLocation;

    @iOSFindBy(accessibility = "tripDetails_tripName")
    @AndroidFindBy(id = "textViewName")
    public MobileElement txtNameOfTrip;

    @iOSFindBy(accessibility = "tripDetails_tripDuration")
    @AndroidFindBy(id = "textViewScheduleDescription")
    public MobileElement txtTripDuration;

    @AndroidFindBy(id = "textViewDate")
    @iOSFindBy(accessibility = "tripDetails_tripDates")
    public MobileElement txtTripDates;

    @iOSFindBy(accessibility = "tripDetails_bookTripButton")
    @AndroidFindBy(id = "textViewBookIt")
    public MobileElement btnBookIt;

    @iOSFindBy(accessibility = "add Trip to BL")
    public MobileElement btnAddToBucketList;

    @AndroidFindBy(id = "textViewDescription")
    public MobileElement txtShortDescriptionOfTrip;

    @AndroidFindBy(id = "expandable_text")
    public MobileElement txtLongDescriptionOfTrip;

    @AndroidFindBy(id = "expandable_text")
    @iOSFindBy(accessibility = "txtDetailsDescriptionOfTrip")
    public List<MobileElement> listLongDescriptionOfTrip;




//    @iOSFindBy(uiAutomator = "")
//    @AndroidFindBy(id = "fdsf")
    public MobileElement txtPlatinumInclusionsInTrip;

//    @iOSFindBy(uiAutomator = "")
//    @AndroidFindBy(id = "fdef")
    public MobileElement txtItineraryOfTrip;

    @iOSFindBy(accessibility = "Post")
    @AndroidFindBy(id = "post")
    public MobileElement btnPostComment;

    @iOSFindBy(xpath = "XCUIElementTypeButton[@accessibilityContainer='Post']/../XCUIElementTypeTextView")
    public MobileElement fldComment;

//    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "listViewContent")
    public MobileElement areaToScrollText;

    @iOSFindBy(accessibility = "trip favorite icon")
    public MobileElement btnLikeIt;

}

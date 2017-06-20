package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import ui.BaseUiModule;

import java.util.List;

public class DreamTripsDetailsScreen extends BaseUiModule {

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "viewPagerGallery")
    public MobileElement imgPicOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPoints")
    public MobileElement txtPointsForTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPrice")
    public MobileElement txtPriceOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewPlace")
    public MobileElement txtPlaceOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewName")
    public MobileElement txtNameOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewScheduleDescription")
    public MobileElement txtTripDuration;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewDate")
    public MobileElement txtTripDates;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewBookIt")
    public MobileElement txtBookIt;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "textViewDescription")
    public MobileElement txtShortDescriptionOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "expandable_text")
    public MobileElement txtLongDescriptionOfTrip;

    @AndroidFindBy(id = "expandable_text")
    public List<MobileElement> listLongDescriptionOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "fdsf")
    public MobileElement txtPlatinumInclusionsInTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "fdef")
    public MobileElement txtItineraryOfTrip;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "post")
    public MobileElement btnPostComment;

    @iOSFindBy(uiAutomator = "")
    @AndroidFindBy(id = "listViewContent")
    public MobileElement areaToScrollText;




}

package ui.screens;

import data.Configuration;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import ui.BaseUiModule;

import java.util.List;

public class DreamTripsListScreen extends BaseUiModule {

    @AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[contains(@resource-id,'recyclerViewTrips')]//android.widget.RelativeLayout")
    public MobileElement dreamTripFirstItem;

//    @iOSFindBy(accessibility = "Search")
//    @AndroidFindBy(id = "itemLayout")
//    //TODO: WAT
//    public MobileElement dreamTripFirstItem2;

    @iOSFindBy(accessibility = "Search")
    @AndroidFindBy(id = "action_search")
    public MobileElement btnSearch;

//    @iOSFindBy(uiAutomator = ".searchBars()[0]")
    @AndroidFindBy(id = "search_src_text")
    public MobileElement fldSearch;

    @iOSFindBy(className = "XCUIElementTypeCell")
    @AndroidFindBy(id = "card_view")
    public List<MobileElement> tripCards;

//    public List<DreamTripsListItem> tripsListItems;

    public static final By CARD_LOCATOR = Configuration.isAndroid() ? By.id("card_view") : By.className("XCUIElementTypeCell");

    public static final By LOCATION_BY = MobileBy.AccessibilityId("tripDetails_tripLocation");

    public static final By ADD_TO_BUCKET_LIST_BUTTON_BY = MobileBy.AccessibilityId("tripDetails_addToBLButton");

    public static final By LIKE_BUTTON = MobileBy.AccessibilityId("tripDetails_likeButton");

    public static final By TRIP_PRICE_BY = MobileBy.AccessibilityId("tripDetails_tripPrice");

    public static final By TRIP_POINTS_BY = MobileBy.AccessibilityId("tripDetails_tripPoints");

    public static final By TRIP_NAME_BY = MobileBy.AccessibilityId("tripDetails_tripName");

    @iOSFindBy(accessibility = "tripDetails_tripName")
    public MobileElement tripName;

    @iOSFindBy(accessibility = "tripDetails_tripDates")
    public MobileElement dates;

}

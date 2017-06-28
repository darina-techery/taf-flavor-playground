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

    @AndroidFindBy(id = "card_view")
    public MobileElement dreamTripFirstItem;

    @iOSFindBy(accessibility = "Search")
    @AndroidFindBy(id = "action_search")
    public MobileElement btnSearch;

//    @iOSFindBy(uiAutomator = ".searchBars()[0]")
    @AndroidFindBy(id = "search_src_text")
    public MobileElement fldSearch;

    @iOSFindBy(className = "XCUIElementTypeCell")
    @AndroidFindBy(id = "card_view")
    public List<MobileElement> tripCards;

    public static final By CARD_LOCATOR = Configuration.isAndroid() ? By.id("card_view") : By.className("XCUIElementTypeCell");

    public static final By TRIP_NAME_BY = Configuration.isAndroid() ? By.id("textViewName") : MobileBy.AccessibilityId("tripDetails_tripName");

}

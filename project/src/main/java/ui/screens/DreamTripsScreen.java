package ui.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import ui.BaseUiModule;

import java.util.List;

public class DreamTripsScreen extends BaseUiModule {

    @AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[contains(@resource-id,'recyclerViewTrips')]//android.widget.RelativeLayout")
    public MobileElement dreamTripFirstItem;

    @iOSFindBy(accessibility = "Search")
    @AndroidFindBy(id = "itemLayout")
    public MobileElement dreamTripFirstItem2;

    @iOSFindBy(accessibility = "Search")
    @AndroidFindBy(id = "action_search")
    public MobileElement btnSearch;

    @iOSFindBy(uiAutomator = ".searchBars()[0]")
    @AndroidFindBy(id = "search_src_text")
    public MobileElement fldSearch;

}

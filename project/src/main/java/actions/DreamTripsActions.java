package actions;

import org.openqa.selenium.By;
import ui.screens.DreamTripsDetailsScreen;
import ui.screens.DreamTripsListScreen;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

import java.util.HashMap;
import java.util.Map;

public abstract class DreamTripsActions extends BaseUiActions {
    protected DreamTripsDetailsScreen dreamTripsDetailsScreen = new DreamTripsDetailsScreen();
    protected DreamTripsListScreen dreamTripsScreen = new DreamTripsListScreen();

    public abstract void searchTrip(String tripName);

    public abstract void openFirstTripInList();

    public abstract void openTripByName(String name);

	public boolean isCardListShown() {
        return Waiter.isDisplayed(DreamTripsListScreen.CARD_LOCATOR);
    }

    public boolean isTripShownInList(String tripName) {
        By tripLocator = ByHelper.getLocatorByText(tripName);
        return Waiter.isDisplayed(tripLocator);
    }

    public Map<String, String> listGeneralTripInfo() {
        return new HashMap<>();
    }
}

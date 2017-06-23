package actions;

import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsListScreen;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

public class DroidDreamTripsActions extends DreamTripsActions {
    @Override
    @Step("Search trip by name ''{0}''")
    public void searchTrip(String tripName) {
        DreamTripsListScreen dreamTripsScreen = new DreamTripsListScreen();
        Waiter.click(dreamTripsScreen.btnSearch);
        Waiter.setText(dreamTripsScreen.fldSearch, tripName);
    }

    @Step("Open first trip in list")
    public void openFirstTripInList() {
        Waiter.click(dreamTripsScreen.dreamTripFirstItem);
        Waiter.isDisplayed(dreamTripsDetailsScreen.imgPicOfTrip);
    }

	@Override
	public void openTripByName(String name) {
		By tripNameLocator = ByHelper.getLocatorByText(name);
		Waiter.click(tripNameLocator);
		Waiter.isDisplayed(dreamTripsDetailsScreen.imgPicOfTrip);
	}

	@Override
	public boolean isCardListShown() {
		return Waiter.isDisplayed(DreamTripsListScreen.CARD_LOCATOR);
	}

}

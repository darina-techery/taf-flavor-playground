package actions;

import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsListScreen;
import utils.exceptions.FailedTestException;
import utils.exceptions.NotImplementedException;
import utils.waiters.Waiter;

public class IPhoneDreamTripsActions extends DreamTripsActions {
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
		for (MobileElement tripItem: dreamTripsScreen.tripCards) {
			if (Waiter.getText(DreamTripsListScreen.TRIP_NAME_BY, tripItem).equals(name)) {
				Waiter.click(DreamTripsListScreen.TRIP_NAME_BY, tripItem);
			}
		}
		throw new FailedTestException("Trip with name "+name+" was not found in trips list.");
	}

	@Override
	public boolean isCardListShown() {
		throw new NotImplementedException();
	}

}

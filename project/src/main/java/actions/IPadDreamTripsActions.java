package actions;

import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsListScreen;
import utils.exceptions.NotImplementedException;
import utils.waiters.Waiter;

public class IPadDreamTripsActions extends DreamTripsActions {
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
		throw new NotImplementedException();
	}

	@Override
	public boolean isCardListShown() {
		throw new NotImplementedException();
	}

}

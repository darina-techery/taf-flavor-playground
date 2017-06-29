package actions;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsListScreen;
import utils.exceptions.FailedTestException;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

import java.time.Duration;

public abstract class DreamTripsActions extends BaseUiActions {
    DreamTripsListScreen dreamTripsScreen = new DreamTripsListScreen();

	@Step("Search trip by name ''{0}''")
	public void searchTrip(String tripName) {
		DreamTripsListScreen dreamTripsScreen = new DreamTripsListScreen();
		Waiter waiter = new Waiter();
		waiter.click(dreamTripsScreen.btnSearch);
		waiter.setText(dreamTripsScreen.fldSearch, tripName);
	}

	@Step("Open first trip in list")
	public void openFirstTripInList() {
		Waiter waiter = new Waiter();
		waiter.click(dreamTripsScreen.dreamTripFirstItem);
	}

	@Step("Click on trip name ''{0}''")
	public void openTripByName(String name) {
		Waiter waiter = new Waiter();
		for (MobileElement tripItem: dreamTripsScreen.tripCards) {
			if (waiter.getText(DreamTripsListScreen.TRIP_NAME_BY, tripItem).equals(name)) {
				waiter.click(DreamTripsListScreen.TRIP_NAME_BY, tripItem);
				return;
			}
		}
		throw new FailedTestException("Trip with name "+name+" was not found in trips list.");
	}


	public boolean isCardListShown() {
        return new Waiter().isDisplayed(DreamTripsListScreen.CARD_LOCATOR);
    }

	@Override
	public void waitForScreen() {
		new Waiter(Duration.ofSeconds(30)).waitDisplayed(DreamTripsListScreen.CARD_LOCATOR);
	}

	public boolean isTripShownInList(String tripName) {
        By tripLocator = ByHelper.getLocatorByText(tripName);
        return new Waiter().isDisplayed(tripLocator);
    }
}

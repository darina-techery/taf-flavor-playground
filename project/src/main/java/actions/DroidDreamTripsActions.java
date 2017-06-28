package actions;

import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import ui.screens.DreamTripsListScreen;
import utils.ui.ByHelper;
import utils.waiters.Waiter;

import java.util.HashMap;
import java.util.Map;

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


	@Override
	public Map<String, String> listGeneralTripInfo() {
        Map<String, String> actualTripData = new HashMap<>();
        actualTripData.put("duration",dreamTripsDetailsScreen.txtTripDuration.getText());
        actualTripData.put("price",dreamTripsDetailsScreen.txtPriceOfTrip.getText());
        actualTripData.put("name",dreamTripsDetailsScreen.txtNameOfTrip.getText());
        actualTripData.put("location",dreamTripsDetailsScreen.txtPlaceOfTrip.getText());
        actualTripData.put("points",dreamTripsDetailsScreen.txtPointsForTrip.getText());
        return actualTripData;
    }

}

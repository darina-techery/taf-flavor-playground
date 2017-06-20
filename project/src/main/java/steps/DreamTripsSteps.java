package steps;

import actions.DreamTripsActions;
import actions.MenuActions;
import actions.NavigationActions;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class DreamTripsSteps {
	private final DreamTripsActions dreamTripsActions;
	private final NavigationActions navigationActions;
	private final MenuActions menuActions;

	@UseActions
	public DreamTripsSteps(DreamTripsActions dreamTripsActions,
	                       NavigationActions navigationActions,
	                       MenuActions menuActions){
		this.dreamTripsActions = dreamTripsActions;
		this.navigationActions = navigationActions;
		this.menuActions = menuActions;
	}

	@Step("Go to Dream Trips screen")
	public void openDreamTripsScreen(){
		menuActions.selectMenuItem(MenuItem.DREAM_TRIPS);
	}

	@Step("Open Trip with specified name")
	public void openTripWithSpecificName(String tripName) {
		dreamTripsActions.searchTrip(tripName);
		dreamTripsActions.openFirstTripInList();
	}

	@Step("Getting all texts from opened Trip")
	public void getAllTextsFromOpenedTripDetails() {
		dreamTripsActions.getAllExpandableTexts();
	}


}

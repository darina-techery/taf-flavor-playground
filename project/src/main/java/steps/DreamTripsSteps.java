package steps;

import actions.DreamTripsActions;
import actions.MenuActions;
import actions.NavigationActions;
import actions.rest.TripsRestActions;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;

public class DreamTripsSteps {
	private final DreamTripsActions dreamTripsActions;
	private final NavigationActions navigationActions;
	private final MenuActions menuActions;
	private final TripsRestActions tripsRestActions;

	@UseActions
	public DreamTripsSteps(DreamTripsActions dreamTripsActions,
	                       NavigationActions navigationActions,
	                       MenuActions menuActions){
		this.dreamTripsActions = dreamTripsActions;
		this.navigationActions = navigationActions;
		this.menuActions = menuActions;
		tripsRestActions = new TripsRestActions();
	}

	@Step("Go to Dream Trips screen")
	public void openDreamTripsScreen(){
		menuActions.selectMenuItem(MenuItem.DREAM_TRIPS);
	}


}

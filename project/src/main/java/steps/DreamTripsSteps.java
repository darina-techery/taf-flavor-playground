package steps;

import actions.DreamTripDetailsActions;
import actions.DreamTripsActions;
import actions.NavigationActions;
import actions.model.ExpectedDataActions;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import utils.annotations.UseActions;
import utils.runner.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class DreamTripsSteps {
	private final DreamTripsActions dreamTripsActions;
	private final NavigationActions navigationActions;
	private final DreamTripDetailsActions tripDetailsActions;
	private final ExpectedDataActions dataActions = new ExpectedDataActions();

	@UseActions
	public DreamTripsSteps(DreamTripsActions dreamTripsActions,
	                       NavigationActions navigationActions,
	                       DreamTripDetailsActions tripDetailsActions){
		this.dreamTripsActions = dreamTripsActions;
		this.navigationActions = navigationActions;
		this.tripDetailsActions = tripDetailsActions;
	}

	@Step("Go to Dream Trips screen")
	public void openDreamTripsScreen(){
		navigationActions.selectMenuItem(MenuItem.DREAM_TRIPS);
	}

	@Step("Open Trip with name '{0}'")
	public void openTripByName(String tripName) {
		dreamTripsActions.searchTrip(tripName);
		dreamTripsActions.openTripByName(tripName);
	}

	@Step("Open Trip with name '{0}'")
	public void openPredefinedTripByName(String tripName) {
		dreamTripsActions.openTripByName(tripName);
	}

	@Step("Verify all expected text entries with trip descriptions are present on the page")
	public void assertAllTripDescriptionTextsPresent(Set<String> expectedTexts) {
		Set<String> actualTexts = tripDetailsActions.getAllTextsFromTripDetails();
		//TODO: assert that...
	}

	@Step("Assert that Dream Trips list is loaded")
	public void assertDreamTripsListLoaded() {
		Assert.assertThat("Dream trips list is shown on the screen", dreamTripsActions.isCardListShown());
	}

	@Step("Assert that Dream Trips list contains trip '{0}'")
	public void assertDreamTripsListContainsTripName(String tripName) {
		Assert.assertThat("Trip "+tripName+" was not found in trips list",
				dreamTripsActions.isTripShownInList(tripName));
	}

	@Step("Verify trip details")
	public void assertAllTripDetailsAreDisplayed(TripWithDetails expectedTripDetails) throws IOException {
		Map<String, String> expectedTripData = dataActions.getDataForTripDetailsPage(expectedTripDetails);
		Map<String, String> actualTripData = new HashMap<>();
//		actualTripData.put(...)
		//TODO: populate actual trip data from tripDetailsActions;
		List<String> differences = new ArrayList<>();
		for (String key : expectedTripData.keySet()) {
			if (!actualTripData.containsKey(key)) {
				differences.add(key + " was not found in actual trip details, expected '"+expectedTripData.get(key)+"'");
			} else if (!actualTripData.get(key).equals(expectedTripData.get(key))) {
				differences.add(key + " was different in actual trip details: expected '"
						+expectedTripData.get(key)+"', but was '"+actualTripData.get(key)+"'");
			}
		}
		//compare dates
		Assert.assertThat("No differences should be found between actual and expected data", differences, is(empty()));
	}

	private String getTripDateDifference(Date expectedDate, String actualDateRange, String dateType) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
		String expectedDateString = dateFormat.format(expectedDate);
		String[] dateTokens = expectedDateString.split("\\s");
		for (String token : dateTokens) {
			if (!actualDateRange.contains(token)) {
				return "Expected trip "+dateType+" was "+expectedDateString+", but actual date range was "+actualDateRange;
			}
		}
		return null;
	}

}

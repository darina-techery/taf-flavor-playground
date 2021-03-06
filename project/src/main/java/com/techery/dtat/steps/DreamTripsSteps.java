package com.techery.dtat.steps;

import com.techery.dtat.actions.DreamTripDetailsActions;
import com.techery.dtat.actions.DreamTripsActions;
import com.techery.dtat.actions.NavigationActions;
import com.worldventures.dreamtrips.api.trip.model.TripDates;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.ui.MenuItem;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.annotations.UseActions;
import com.techery.dtat.utils.runner.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class DreamTripsSteps {
	private final DreamTripsActions tripsListActions;
	private final NavigationActions navigationActions;
	private final DreamTripDetailsActions tripDetailsActions;

	@UseActions
	public DreamTripsSteps(DreamTripsActions tripsListActions,
	                       NavigationActions navigationActions,
	                       DreamTripDetailsActions tripDetailsActions){
		this.tripsListActions = tripsListActions;
		this.navigationActions = navigationActions;
		this.tripDetailsActions = tripDetailsActions;
	}

	@Step("Go to Dream Trips screen")
	public void openDreamTripsScreen(){
		navigationActions.selectMenuItem(MenuItem.DREAM_TRIPS);
		tripsListActions.waitForScreen();
	}

	@Step("Open Trip with name '{0}'")
	public void openTripByName(String tripName) {
		tripsListActions.searchTrip(tripName);
		openPredefinedTripByName(tripName);
	}

	@Step("Open Trip with name '{0}'")
	public void openPredefinedTripByName(String tripName) {
		tripsListActions.openTripByName(tripName);
		tripDetailsActions.waitForScreen();
	}

	@Step("Assert that all expected text entries with trip descriptions are present on the page")
	public void assertAllTripDescriptionTextsPresent(TripWithDetails tripWithDetails) {
		Set<String> actualTextsFromUi = tripDetailsActions.getTripDescriptionsFromPage();
		Set<String> expectedTextsFromStubData = tripDetailsActions.getTripDescriptionsFromStub(tripWithDetails);

		final List<String> differences;
		if (Configuration.isIOS()) {
			differences = new ArrayList<>();
			expectedTextsFromStubData.forEach(expected -> {
				if (actualTextsFromUi.stream().filter(actual -> expected.startsWith(actual)).count() == 0) {
					differences.add("Expected value not found: '"+expected+"'\n");
				}
			});
			actualTextsFromUi.forEach(actual -> {
				if (expectedTextsFromStubData.stream().filter(expected -> expected.startsWith(actual)).count() == 0) {
					differences.add("Unexpected actual value found: '"+actual+"'\n");
				}
			});
		} else {
			differences = Assert.getDifferenceIgnoringWhitespaces("trip descriptions",
					actualTextsFromUi, expectedTextsFromStubData);
		}
		Assert.assertThat("No differences should be found between actual and expected trip descriptions",
				differences, is(empty()));
	}

	@Step("Assert that Dream Trips list is loaded")
	public void assertDreamTripsListLoaded() {
		Assert.assertThat("Dream trips list is shown on the screen", tripsListActions.isCardListShown());
	}

	@Step("Assert that Dream Trips list contains trip '{0}'")
	public void assertDreamTripsListContainsTripName(String tripName) {
		Assert.assertThat("Trip "+tripName+" was not found in trips list",
				tripsListActions.isTripShownInList(tripName));
	}

	@Step("Assert that trip details match expected")
	public void assertAllTripDetailsAreDisplayed(TripWithDetails expectedTripDetails) throws IOException {
		Map<String, String> expectedTripData = tripDetailsActions.getTripDetailsFromStub(expectedTripDetails);
		Map<String, String> actualTripData = tripDetailsActions.getTripDetailsFromPage();
		List<String> differences = Assert.getDifference("trip details", actualTripData, expectedTripData);

		TripDates dates = expectedTripDetails.dates();
		addDateDifferenceToDiffList(dates, differences);

		differences = differences.stream().filter(Objects::nonNull).collect(Collectors.toList());
		Assert.assertThat("No differences should be found between actual and expected trip data", differences, is(empty()));
	}

	@Step("Assert that 'Book trip' button is active")
	public void assertBookTripButtonIsActive() {
		List<String> differences = new ArrayList<>();
		boolean isButtonEnabled = tripDetailsActions.isBookItButtonActive();
		String buttonText = tripDetailsActions.getBookItButtonText();
		if (!isButtonEnabled) {
			differences.add("'Book trip' button should be enabled, but was disabled.");
		}
		if (!buttonText.equalsIgnoreCase("Book It!")) {
			differences.add("Expected 'Book it!' text on 'Book trip' button, but '"+buttonText+"' found.");
		}
		Assert.assertThat("'Book trip' button should be enabled and have proper text", differences, is(empty()));
	}

	@Step("Assert that 'Book trip' button is disabled")
	public void assertBookTripButtonIsDisabled() {
		List<String> differences = new ArrayList<>();
		boolean isButtonEnabled = tripDetailsActions.isBookItButtonActive();
		String buttonText = tripDetailsActions.getBookItButtonText();
		if (isButtonEnabled) {
			differences.add("'Book trip' button should be not enabled for this user");
		}
		if (!buttonText.equalsIgnoreCase("Book It!")) {
			differences.add("Expected 'Book it!' text on 'Book trip' button, but '"+buttonText+"' found.");
		}
		Assert.assertThat("'Book trip' button should be disabled and have proper text", differences, is(empty()));
	}

	private void addDateDifferenceToDiffList(TripDates expectedTripDates, List<String> diff) {
		String actualDateRange = tripDetailsActions.getDates();
		String dateDifference = getTripDateDifference(expectedTripDates.startOn(), actualDateRange, "Start date");
		diff.add(dateDifference);
		dateDifference = getTripDateDifference(expectedTripDates.endOn(), actualDateRange, "End date");
		diff.add(dateDifference);
		diff.remove(null);
	}

	private String getTripDateDifference(Date expectedDate, String actualDateRange, String dateName) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy");
		String expectedDateString = dateFormat.format(expectedDate);
		String[] dateTokens = expectedDateString.split("\\s");
		for (String token : dateTokens) {
			if (!actualDateRange.contains(token)) {
				return "Expected trip "+dateName+" was "+expectedDateString
						+ ", but actual date range was "+actualDateRange + "\n";
			}
		}
		return null;
	}

}

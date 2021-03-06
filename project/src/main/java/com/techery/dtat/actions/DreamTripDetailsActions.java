package com.techery.dtat.actions;

import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import org.jsoup.Jsoup;
import org.openqa.selenium.NoSuchElementException;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.ui.screens.DreamTripsDetailsScreen;
import com.techery.dtat.utils.StringHelper;
import com.techery.dtat.utils.exceptions.FailedTestException;
import com.techery.dtat.utils.ui.SwipeHelper;
import com.techery.dtat.utils.waiters.AnyWait;
import com.techery.dtat.utils.waiters.Waiter;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DreamTripDetailsActions extends BaseUiActions {
	DreamTripsDetailsScreen dreamTripsDetailsScreen = new DreamTripsDetailsScreen();

	@Override
	public void waitForScreen() {
		new Waiter().waitDisplayed(dreamTripsDetailsScreen.txtNameOfTrip);
	}

	public String getName() {
		return dreamTripsDetailsScreen.txtNameOfTrip.getText().trim();
	}
	public String getPrice() {
		return dreamTripsDetailsScreen.txtPriceOfTrip.getText().trim();
	}
	public String getPoints() {
		return dreamTripsDetailsScreen.txtPointsForTrip.getText().trim();
	}
	public String getDuration() {
		return dreamTripsDetailsScreen.txtTripDuration.getText().trim();
	}
	public String getLocation() {
		return dreamTripsDetailsScreen.txtLocation.getText().trim();
	}
	public String getDates() {
		return dreamTripsDetailsScreen.txtTripDates.getText().trim();
	}

	public boolean isBookItButtonActive() {
		return dreamTripsDetailsScreen.btnBookIt.isEnabled();
	}

	public String getBookItButtonText() {
		return dreamTripsDetailsScreen.btnBookIt.getText();
	}

	@Step("Get trip details from Trip Details screen")
	public Map<String, String> getTripDetailsFromPage() {
		Map<String, String> values = new HashMap<>();
		values.put("name", getName());
		values.put("price", getPrice());
		values.put("duration", getDuration());
		values.put("location", getLocation());
		values.put("points", getPoints());
		return values;
	}

	@Step("Get trip details from TripWithDetails object")
	public Map<String, String> getTripDetailsFromStub(TripWithDetails tripWithDetails) {
		Map<String, String> values = new HashMap<>();
		values.put("name", tripWithDetails.name());
		values.put("price", NumberFormat.getCurrencyInstance(new Locale("en", "US"))
				.format(tripWithDetails.price().amount()));
		values.put("duration", tripWithDetails.duration()+" Nights");
		values.put("location", tripWithDetails.location().name());
		values.put("points", tripWithDetails.rewardsLimit()+"");
		return values;
	}

	@Step("Get all trip descriptions from Trip Details screen")
	public Set<String> getTripDescriptionsFromPage() {
		final Set<String> textsFromTripDetails = new LinkedHashSet<>();
		addVisibleTextsFromTripDetails(textsFromTripDetails);
		AnyWait<Void, Void> activityWait = new AnyWait<>();
		activityWait.execute(()->{
			SwipeHelper.scrollDown();
			addVisibleTextsFromTripDetails(textsFromTripDetails);
		});
		activityWait.until(() -> dreamTripsDetailsScreen.btnPostComment.isDisplayed());
		activityWait.addIgnorableException(NoSuchElementException.class);
		activityWait.describe("Wait until all texts collected from Trip Details");
		activityWait.duration(Duration.ofMinutes(5));
		activityWait.go();
		if (!activityWait.isSuccess()) {
			throw new FailedTestException("FAILED to scroll Details page down until Post Comment button is displayed. " +
					"Swipe might be a problem.");
		}
		return textsFromTripDetails;
	}

	@Step("Get all trip descriptions from TripWithDetails object")
	public Set<String> getTripDescriptionsFromStub(TripWithDetails tripWithDetails) {
		Set<String> tripDescriptions = new HashSet<>();
		List<String> tripDescriptionTypes = Arrays.asList(
				"Short Description",
				"Long Description",
				"What Is Included",
				"Policies",
				"Additional Information",
				"Hotel Details",
				"Itinerary",
				"Destination",
				"Platinum Inclusions"
		);
		tripWithDetails.contentItems().forEach(content -> {
			if (tripDescriptionTypes.contains(content.name())) {
				String text = Jsoup.parse(content.description().replace("<br>", "\n")).text();
				tripDescriptions.add(StringHelper.removeExtraSpacesAndNewlines(text));
			}
		});
		return tripDescriptions;
	}

	@Step("Get all text from visible expandable text elements")
	private void addVisibleTextsFromTripDetails(Set<String> collectedTexts) {
		collectedTexts.addAll(dreamTripsDetailsScreen.listLongDescriptionOfTrip.stream()
				.map(element -> new Waiter(Duration.ofSeconds(2)).getText(element))
				.filter(Objects::nonNull)
				.map(String::trim)
				.map(StringHelper::removeExtraSpacesAndNewlines)
				.collect(Collectors.toList()));
	}
}

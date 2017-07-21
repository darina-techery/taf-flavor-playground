package com.techery.dtat.steps;

import com.techery.dtat.actions.rest.TripsAPIActions;
import com.worldventures.dreamtrips.api.trip.model.Trip;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import ru.yandex.qatools.allure.annotations.Step;
import com.techery.dtat.utils.exceptions.FailedTestException;

import java.io.IOException;
import java.util.List;

public class TripsAPISteps {
	private final TripsAPIActions apiActions;
	public TripsAPISteps() {
		apiActions = new TripsAPIActions();
	}

	@Step("Get first actual trip details")
	public Trip getFirstTripDetails() throws IOException {
		List<TripWithoutDetails> trips = apiActions.getUpcomingTripsList();
		if (trips == null || trips.isEmpty()) {
			throw new FailedTestException("No trips found.");
		}
		String firstTripUid = trips.get(0).uid();
		return apiActions.getTripDetails(firstTripUid);
	}

	@Step("Get trip details by its uid '{0}'")
	public TripWithDetails getTripDetailsByItsUid(String tripUid) throws IOException {
		return apiActions.getTripDetails(tripUid);
	}

}

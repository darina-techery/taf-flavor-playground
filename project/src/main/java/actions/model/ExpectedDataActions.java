package actions.model;

import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;

import java.util.HashMap;
import java.util.Map;

public class ExpectedDataActions {
	public Map<String, String> getDataForTripDetailsPage(TripWithDetails tripWithDetails) {
		Map<String, String> expectedValues = new HashMap<>();
		expectedValues.put("name", tripWithDetails.name());
		expectedValues.put("price", tripWithDetails.price().amount()+"$");
		expectedValues.put("duration", tripWithDetails.duration()+" nights");
		expectedValues.put("location", tripWithDetails.location().name());
		expectedValues.put("points", tripWithDetails.rewardsLimit()+"");
		return expectedValues;
	}
}

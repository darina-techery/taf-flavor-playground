package actions.model;

import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExpectedDataActions {
	public Map<String, String> getDataForTripDetailsPage(TripWithDetails tripWithDetails) {
		double price = tripWithDetails.price().amount();

		Map<String, String> expectedValues = new HashMap<>();
		expectedValues.put("name", tripWithDetails.name());
		expectedValues.put("price", NumberFormat.getCurrencyInstance(new Locale("en", "US"))
				.format(price));
		expectedValues.put("duration", tripWithDetails.duration()+" Nights");
		expectedValues.put("location", tripWithDetails.location().name());
		expectedValues.put("points", tripWithDetails.rewardsLimit()+"");
		return expectedValues;
	}
}

package com.techery.dtat.actions.rest;

import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import com.techery.dtat.rest.api.clients.DreamTripsAPIClient;
import com.techery.dtat.rest.api.services.DreamTripsAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TripsAPIActions {
	private final DreamTripsAPI dreamTripsAPI;
	public TripsAPIActions() {
		dreamTripsAPI = new DreamTripsAPIClient().create(DreamTripsAPI.class);
	}

	public TripWithDetails getTripDetails(String tripUid) throws IOException {
		return dreamTripsAPI.getTripDetails(tripUid).execute().body().entity();
	}

	public List<TripWithoutDetails> getUpcomingTripsList() throws IOException {
		String startDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		String endDate = LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
		return dreamTripsAPI.getBaseTripsList(startDate, endDate).execute().body();
	}
}

package steps;

import actions.rest.HermetProxyActions;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.worldventures.dreamtrips.api.entity.model.EntityHolder;
import com.worldventures.dreamtrips.api.session.model.Session;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import data.Configuration;
import org.apache.commons.lang3.RandomStringUtils;
import rest.api.hermet.HermetJsonStubBuilder;
import rest.api.hermet.HermetStubBuilder;
import rest.api.services.DreamTripsAPI;
import ru.yandex.qatools.allure.annotations.Step;
import utils.FileUtils;
import utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static rest.api.hermet.HermetStubBuilder.ResponsePart.BODY;

public class HermetProxySteps {
	private HermetProxyActions hermetProxyActions;

	public HermetProxySteps() {
		hermetProxyActions = new HermetProxyActions();
	}

	@Step("Create Hermet stub for login")
	public Session createLoginStub() throws IOException {
		HermetJsonStubBuilder stubBuilder = new HermetJsonStubBuilder();
		File dataFile = FileUtils.getResourceFile("hermet/login_response.json");
		stubBuilder.setResponse(BODY, dataFile);
		stubBuilder.addPredicate()
				.equals().method("POST").path("/api/sessions").build();
		JsonObject loginStub = stubBuilder.build();
		hermetProxyActions.addStub(Configuration.getParameters().apiURL, loginStub);
		return stubBuilder.getExpectedResponse(Session.class);
	}

	@Step("Create default stub for trips list")
	public List<TripWithoutDetails> createDefaultStubForTripsList() throws IOException {
		File contentFile = FileUtils.getResourceFile("hermet/trip_search_response.json");
		return createStubForTripsList(contentFile);
	}

	@Step("Create stub for trips list from file")
	public List<TripWithoutDetails> createStubForTripsList(File contentFile) throws IOException {
		HermetJsonStubBuilder stubBuilder = new HermetJsonStubBuilder();
		stubBuilder.setResponse(BODY, contentFile);
		stubBuilder.addPredicate()
				.equals().method("GET").end()
				.equals().path("/api/trips").build();
		JsonObject tripsSearchStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(tripsSearchStub);
		Type resultType = new TypeToken<List<TripWithoutDetails>>() {}.getType();
		return stubBuilder.getExpectedResponse(resultType);
	}

	@Step("Create stub for Platinum Recent trip details")
	public TripWithDetails createStubForPlatinumRecentTrip(String tripUid) throws IOException {
		File stubContent = FileUtils.getResourceFile("hermet/platinum_recent_trip_romania.json");
		return createStubForTripDetails(stubContent, tripUid);
	}

	@Step("Create stub for trip list and first trip from it")
	public TripWithDetails createStubForTripListAndFirstTripDetails(String tripJsonFileName) throws IOException {
		String uniqueUid = RandomStringUtils.randomAlphanumeric(10);

		File baseTripsData = FileUtils.getResourceFile("hermet/trip_search_response.json");
		File tripFile = FileUtils.getResourceFile(tripJsonFileName);
		HermetJsonStubBuilder stubBuilder = new HermetJsonStubBuilder();

		JsonElement testTrip = JsonUtils.toJsonElement(tripFile);
		JsonObject tripObject = testTrip.getAsJsonObject().get("item").getAsJsonObject();
		tripObject.addProperty("uid", uniqueUid);
		String tripName = tripObject.get("name").getAsString();

		JsonElement baseTrips = JsonUtils.toJsonElement(baseTripsData);
		JsonObject firstTripObject = baseTrips.getAsJsonArray().get(0).getAsJsonObject();
		firstTripObject.addProperty("uid", uniqueUid);
		firstTripObject.addProperty("name", tripName);

		stubBuilder.addPredicate()
				.equals().method("GET").path(DreamTripsAPI.TRIPS_SEARCH_PATH).build();
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY, baseTrips);
		JsonObject tripsListStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(tripsListStub);

		stubBuilder = new HermetJsonStubBuilder();
		stubBuilder.addPredicate().equals().method("GET").path("/api/" + uniqueUid);
		stubBuilder.setResponse(HermetStubBuilder.ResponsePart.BODY, testTrip);
		JsonObject testTripStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(testTripStub);

		Type responseType = new TypeToken<EntityHolder<TripWithDetails>>() {}.getType();
		EntityHolder<TripWithDetails> entityWithTrip = JsonUtils
				.toObject(testTrip, responseType, JsonUtils.Converter.DREAM_TRIPS);
		return entityWithTrip.entity();
	}

	@Step("Create stub for trip details")
	public TripWithDetails createStubForTripDetails(File contentFile, String tripUid) throws IOException {
		HermetJsonStubBuilder stubBuilder = new HermetJsonStubBuilder();
		stubBuilder.setResponse(BODY, contentFile);
		stubBuilder.addPredicate()
				.equals().method("GET").end()
				.equals().path("/api/" + tripUid).build();
		JsonObject tripDetailsStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(tripDetailsStub);
		Type responseType = new TypeToken<EntityHolder<TripWithDetails>>() {
		}.getType();
		EntityHolder<TripWithDetails> expectedResponse = stubBuilder.getExpectedResponse(responseType);
		return expectedResponse.entity();
	}

	@Step("Create stub for trips search with demo content")
	public List<TripWithoutDetails> createDemoStubForTripsList() throws IOException {
		File dataFile = FileUtils.getResourceFile("hermet/trip_search_response_demo.json");
		return createStubForTripsList(dataFile);
	}

	@Step("Remove all Hermet stubs for main API URL")
	public void cleanupAllStubsForMainUrl() throws IOException {
		hermetProxyActions.deleteAllStubsForMainService();
	}

	@Step("Remove Hermet stubs for main API URL created in this session")
	public void cleanupCreatedStubsForMainUrl() throws IOException {
		hermetProxyActions.deleteCreatedStubsForMainService();
	}
}

package steps;

import actions.rest.HermetProxyActions;
import com.google.gson.JsonObject;
import data.Configuration;
import rest.api.hermet.HermetStubBuilder;
import ru.yandex.qatools.allure.annotations.Step;
import utils.FileUtils;

import java.io.IOException;

import static rest.api.hermet.HermetStubBuilder.ResponsePart.BODY;

public class HermetProxySteps {
	private HermetProxyActions hermetProxyActions;

	public HermetProxySteps() {
		hermetProxyActions = new HermetProxyActions();
	}

	@Step("Create Hermet stub for login")
	public void createLoginStub() throws IOException {
		HermetStubBuilder stubBuilder = new HermetStubBuilder();
		stubBuilder.setResponse(BODY, FileUtils.getResourceFile("hermet/login_response.json"));
		stubBuilder.addPredicate()
				.equals().method("POST").path("/api/sessions").build();
		JsonObject loginStub = stubBuilder.build();
		hermetProxyActions.addStub(Configuration.getParameters().apiURL, loginStub);
	}

	@Step("Create stub for trips search")
	public void createStubForTripsList() throws IOException {
		HermetStubBuilder stubBuilder = new HermetStubBuilder();
		stubBuilder.setResponse(BODY, FileUtils.getResourceFile("hermet/trip_search_response.json"));
		stubBuilder.addPredicate()
				.equals().method("GET").end()
				.equals().path("/api/trips").build();
		JsonObject tripsSearchStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(tripsSearchStub);
	}

	@Step("Create stub for trips search with demo content")
	public void createDemoStubForTripsList() throws IOException {
		HermetStubBuilder stubBuilder = new HermetStubBuilder();
		stubBuilder.setResponse(BODY, FileUtils.getResourceFile("hermet/trip_search_response_demo.json"));
		stubBuilder.addPredicate()
				.equals().method("GET").end()
				.equals().path("/api/trips").build();
		JsonObject tripsSearchStub = stubBuilder.build();
		hermetProxyActions.addStubForMainService(tripsSearchStub);
	}

	@Step("Remove all Hermet stubs for main API URL")
	public void cleanupAllStubsForMainUrl() throws IOException {
		hermetProxyActions.deleteAllStubsForService(Configuration.getParameters().apiURL);
	}

	@Step("Remove Hermet stubs for main API URL created in this session")
	public void cleanupCreatedStubsForMainUrl() throws IOException {
		hermetProxyActions.deleteCreatedStubsForMainService();
	}
}

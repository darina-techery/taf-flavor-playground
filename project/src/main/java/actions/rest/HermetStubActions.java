package actions.rest;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetServiceManager;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.payloads.hermet.response.HermetStub;
import rest.api.services.HermetAPI;
import rest.helpers.FailedResponseParser;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

import java.io.IOException;
import java.util.List;

public class HermetStubActions {
	private final HermetAPI hermetAPI;
	private final Logger log = LogManager.getLogger();
	public HermetStubActions() {
		hermetAPI = new HermetAPIClient().create(HermetAPI.class);
	}

	public Response<Void> addStub(String targetUrl, JsonObject stubContent) throws IOException {
		String serviceId = HermetServiceManager.initService(targetUrl);
		return hermetAPI.addStub(serviceId, stubContent).execute();
	}

	public Response<Void> deleteStub(String targetUrl, String stubId) throws IOException {
		String serviceId = HermetServiceManager.initService(targetUrl);
		return hermetAPI.deleteStub(serviceId, stubId).execute();
	}

	public void deleteAllActiveServices(String targetUrl) throws IOException {
		Response<List<HermetProxyData>> response = hermetAPI.getActiveServices().execute();
		if (response.isSuccessful()) {
			List<HermetProxyData> services = response.body();
			if (services == null) {
				throw new FailedConfigurationException("No data available in response for "
						+ hermetAPI.getActiveServices().request().url() + "\n"
						+ new FailedResponseParser().describeFailedResponse(response, "Get all active services"));
			}
			services.stream().map(HermetProxyData::getId).forEach(serviceId -> {
				try {
					hermetAPI.deleteService(serviceId).execute();
				} catch (IOException e) {
					log.error("Failed to delete service "+serviceId, e);
				}
			});
		} else {
			log.error(new FailedResponseParser()
					.describeFailedResponse(response, "Get active Hermet services"));
		}
	}

	public void deleteAllStubsForService(String targetUrl) throws IOException {
		final String serviceId = HermetServiceManager.initService(targetUrl);
		Response<List<HermetStub>> response = hermetAPI.getStubsForService(serviceId).execute();
		if (response.isSuccessful()) {
			List<HermetStub> stubs = response.body();
			if (stubs == null) {
				throw new FailedConfigurationException("No data available in response for "
						+ hermetAPI.getActiveServices().request().url() + "\n"
						+ new FailedResponseParser().describeFailedResponse(response,
						"Get all active stubs for "+targetUrl));
			}
			stubs.stream().map(HermetStub::getId).forEach(id -> {
				try {
					hermetAPI.deleteStub(serviceId, id).execute();
				} catch (IOException e) {
					log.error("Failed to delete stub "+id+" for service "+serviceId, e);
				}
			});
		} else {
			log.error(new FailedResponseParser()
					.describeFailedResponse(response, "Get stubs for targetUrl "+targetUrl));
		}
	}

	public void deleteAllCreatedStubsForService(String targetUrl) {
		HermetServiceManager.cleanupCreatedStubsForService(targetUrl);
	}

}

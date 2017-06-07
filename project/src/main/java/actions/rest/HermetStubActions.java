package actions.rest;

import com.google.gson.JsonObject;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetServiceManager;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.payloads.hermet.response.HermetStub;
import rest.api.services.HermetAPI;
import rest.helpers.ResponseLogger;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

import java.io.IOException;
import java.util.List;

public class HermetStubActions {
	private final HermetAPI hermetAPI;
	public HermetStubActions() {
		hermetAPI = new HermetAPIClient().create(HermetAPI.class);
	}

	public boolean addStub(String targetUrl, JsonObject stubContent) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		Response<Void> response = hermetAPI.addStub(serviceId, stubContent).execute();
		return response.code() == 201;
	}

	public boolean deleteStub(String targetUrl, String stubId) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		Response<Void> response = hermetAPI.deleteStub(serviceId, stubId).execute();
		return response.code() == 204;
	}

	public boolean removeService(String targetUrl) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		Response<Void> response = hermetAPI.deleteService(serviceId).execute();
		return response.code() == 204;
	}

	public boolean removeAllServices(String targetUrl) throws IOException {
		Response<List<HermetProxyData>> response = hermetAPI.getActiveServices().execute();
		if (response.isSuccessful()) {
			List<HermetProxyData> services = response.body();
			if (services == null) {
				throw new FailedConfigurationException("No data available in response for "
						+ hermetAPI.getActiveServices().request().url() + "\n"
						+ new ResponseLogger().describeFailedResponse(response, "Get all active services"));
			}
			services.stream().map(HermetProxyData::getId).forEach(hermetAPI::deleteService);
		}
		return response.isSuccessful();
	}

	public boolean removeAllStubsForService(String targetUrl) throws IOException {
		final String serviceId = HermetServiceManager.getServiceId(targetUrl);
		Response<List<HermetStub>> response = hermetAPI.getStubsForService(serviceId).execute();
		if (response.isSuccessful()) {
			List<HermetStub> stubs = response.body();
			if (stubs == null) {
				throw new FailedConfigurationException("No data available in response for "
						+ hermetAPI.getActiveServices().request().url() + "\n"
						+ new ResponseLogger().describeFailedResponse(response,
						"Get all active stubs for "+targetUrl));
			}
			stubs.stream().map(HermetStub::getId).forEach(id -> hermetAPI.deleteStub(serviceId, id));
		}
		return response.isSuccessful();
	}

}

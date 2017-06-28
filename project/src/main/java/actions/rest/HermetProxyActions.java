package actions.rest;

import com.google.gson.JsonObject;
import data.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.api.clients.HermetAPIClient;
import rest.api.hermet.HermetServiceManager;
import rest.api.model.hermet.HermetProxyData;
import rest.api.model.hermet.response.HermetStub;
import rest.api.services.HermetAPI;
import rest.helpers.FailedResponseParser;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

import java.io.IOException;
import java.util.List;

public class HermetProxyActions {
	private final HermetAPI hermetAPI;
	private final Logger log = LogManager.getLogger();
	private String mainServiceUrl;
	public HermetProxyActions() {
		hermetAPI = new HermetAPIClient().create(HermetAPI.class);
		mainServiceUrl = Configuration.getParameters().apiURL;
	}

	/*
	- Actions for main service
	 */
	public void startMainService() {
		HermetServiceManager.getServiceId(mainServiceUrl);
	}

	public void deleteCreatedStubsForMainService() {
		deleteCreatedStubsForService(mainServiceUrl);
	}

	public void deleteAllStubsForMainService() throws IOException {
		deleteAllStubsForService(mainServiceUrl);
	}

	public void addStubForMainService(JsonObject stub) throws IOException {
		addStub(mainServiceUrl, stub);
	}

	public List<HermetStub> getStubsForMainService() throws IOException {
		return getStubsForService(mainServiceUrl);
	}

	/*
	- Actions for all services
	 */

	public void addStub(String targetUrl, JsonObject stubContent) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		hermetAPI.addStub(serviceId, stubContent).execute();
	}

	public List<HermetStub> getStubsForService(String targetUrl) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		Response<List<HermetStub>> result = hermetAPI.getStubsForService(serviceId).execute();
		return result.body();
	}

	public void deleteStub(String targetUrl, String stubId) throws IOException {
		String serviceId = HermetServiceManager.getServiceId(targetUrl);
		hermetAPI.deleteStub(serviceId, stubId).execute();
	}

	/**
	 * Warning: this method will delete all created proxies on Hermet server
	 * If some concurrent test run uses one of these services, it will cause its failure
	 * @throws IOException
	 */
	public void deleteAllServices() throws IOException {
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
		final String serviceId = HermetServiceManager.getServiceId(targetUrl);
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

	public void deleteCreatedStubsForService(String targetUrl) {
		HermetServiceManager.cleanupCreatedStubsForService(targetUrl);
	}

}

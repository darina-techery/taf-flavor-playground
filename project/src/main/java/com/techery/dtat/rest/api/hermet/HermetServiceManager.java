package com.techery.dtat.rest.api.hermet;

import org.apache.logging.log4j.LogManager;
import com.techery.dtat.rest.api.clients.HermetAPIClient;
import com.techery.dtat.rest.api.model.hermet.HermetProxyData;
import com.techery.dtat.rest.api.services.HermetAPI;
import com.techery.dtat.rest.helpers.HermetLocationParser;
import retrofit2.Response;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class HermetServiceManager {
	private final Map<String, String> targetUrlToServiceId = new HashMap<>();
	private final Map<String, List<String>> serviceIdToStubIds = new HashMap<>();

	private HermetAPI hermetAPI;

	private HermetServiceManager() {
		hermetAPI = new HermetAPIClient().create(HermetAPI.class);
	}

	private void createService(String targetUrl) throws IOException {
		HermetProxyData proxyData = new HermetProxyDataFactory().getProxyData(targetUrl);
		createService(proxyData);
	}

	private void createService(HermetProxyData proxyData) throws IOException {
		Response<Void> proxyResponse = hermetAPI.addService(proxyData).execute();
		String serviceId = HermetLocationParser.getServiceId(proxyResponse.raw());
		targetUrlToServiceId.put(proxyData.getTargetUrl(), serviceId);
	}

	public static void addStubFromResponse(okhttp3.Response response) {
		String serviceId = HermetLocationParser.getServiceId(response);
		String stubId = HermetLocationParser.getStubId(response);
		if (!SessionHolder.HOLDER.serviceIdToStubIds.containsKey(serviceId)) {
			SessionHolder.HOLDER.serviceIdToStubIds.put(serviceId, new ArrayList<>());
		}
		SessionHolder.HOLDER.serviceIdToStubIds.get(serviceId).add(stubId);
	}

	public static String getServiceId(String targetUrl) {
		if (!SessionHolder.HOLDER.targetUrlToServiceId.containsKey(targetUrl)) {
			try {
				SessionHolder.HOLDER.createService(targetUrl);
			} catch (IOException e) {
				throw new FailedConfigurationException(
						"Could not create or find existing Hermet service for "+targetUrl, e);
			}
		}
		return SessionHolder.HOLDER.targetUrlToServiceId.get(targetUrl);
	}

	public static void cleanupCreatedStubsForService(String targetUrl) {
		String serviceId = SessionHolder.HOLDER.targetUrlToServiceId.get(targetUrl);
		if (serviceId != null) {
			List<String> stubIds = SessionHolder.HOLDER.serviceIdToStubIds.get(serviceId);
			if (stubIds != null) {
				for (String stubId : stubIds) {
					try {
						SessionHolder.HOLDER.hermetAPI.deleteStub(serviceId, stubId).execute();
					} catch (IOException e) {
						LogManager.getLogger().error(
								"Failed to delete stub " + stubId + " for service " + serviceId, e);
					}
				}
			}
		}
	}

	public static List<String> getStubIdsByUrl(String targetUrl) {
		String serviceId = SessionHolder.HOLDER.targetUrlToServiceId.get(targetUrl);
		return getStubIdsByServiceId(serviceId);
	}

	public static List<String> getStubIdsByServiceId(String serviceId) {
		if (serviceId == null) {
			return new ArrayList<>();
		} else {
			return SessionHolder.HOLDER.serviceIdToStubIds.containsKey(serviceId)
					? SessionHolder.HOLDER.serviceIdToStubIds.get(serviceId)
					: new ArrayList<>();
		}
	}

	private static class SessionHolder {
		private static final HermetServiceManager HOLDER = new HermetServiceManager();
	}
}

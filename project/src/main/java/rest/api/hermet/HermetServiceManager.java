package rest.api.hermet;

import org.apache.logging.log4j.LogManager;
import rest.api.clients.HermetAPIClient;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.services.HermetAPI;
import rest.helpers.HermetResponseParser;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		String serviceId;
		HermetProxyData existingService = getExistingService(proxyData.getTargetUrl());
		if (existingService == null) {
			Response<Void> proxyResponse = hermetAPI.addService(proxyData).execute();
			serviceId = HermetResponseParser.getServiceId(proxyResponse.raw());
			LogManager.getLogger().info("Hermet service created: " +
					HermetResponseParser.getLocation(proxyResponse.raw()));
		} else {
			serviceId = existingService.getId();
			LogManager.getLogger().info("Hermet service already exists: " +
					proxyData.toString());
		}
		targetUrlToServiceId.put(proxyData.getTargetUrl(), serviceId);
	}

	private HermetProxyData getExistingService(String targetUrl) throws IOException {
		List<HermetProxyData> existingProxies = hermetAPI.getActiveServices().execute().body();
		if (existingProxies == null) {
			return null;
		}
		List<HermetProxyData> proxiesWithTargetUrl = existingProxies.stream()
				.filter(proxy -> proxy.getTargetUrl().equals(targetUrl)).collect(Collectors.toList());
		if (proxiesWithTargetUrl.size() > 1) {
			throw new FailedConfigurationException("More than 1 service exists for target url "+targetUrl
					+"\n\tServices found: "+proxiesWithTargetUrl.stream().map(HermetProxyData::toString)
					.collect(Collectors.joining("\n")));
		}
		return proxiesWithTargetUrl.isEmpty() ? null : proxiesWithTargetUrl.get(0);
	}

	public static void addStubFromResponse(okhttp3.Response response) {
		String serviceId = HermetResponseParser.getServiceId(response);
		String stubId = HermetResponseParser.getStubId(response);
		if (!SessionHolder.HOLDER.serviceIdToStubIds.containsKey(serviceId)) {
			SessionHolder.HOLDER.serviceIdToStubIds.put(serviceId, new ArrayList<>());
		}
		SessionHolder.HOLDER.serviceIdToStubIds.get(serviceId).add(stubId);
	}

	public static String initService(String targetUrl) {
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
			for (String stubId : stubIds) {
				try {
					SessionHolder.HOLDER.hermetAPI.deleteStub(serviceId, stubId).execute();
				} catch (IOException e) {
					LogManager.getLogger().error(
							"Failed to delete stub "+stubId+" for service "+serviceId, e);
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

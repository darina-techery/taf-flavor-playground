package rest.api.hermet;

import org.apache.logging.log4j.LogManager;
import rest.api.clients.HermetAPIClient;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.services.HermetAPI;
import rest.helpers.HermetResponseParser;
import retrofit2.Call;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

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

	private void createService(String targetUrl) {
		HermetProxyData proxyData = new HermetProxyDataFactory().getProxyData(targetUrl);
		createService(proxyData);
	}

	private void createService(HermetProxyData proxyData) {
		Call<Void> proxyCall;
		Response<Void> proxyResponse;
		try {
			proxyCall = hermetAPI.setupService(proxyData);
			proxyResponse = proxyCall.execute();
			String serviceId = HermetResponseParser.getServiceId(proxyResponse.raw());
			targetUrlToServiceId.put(proxyData.getTargetUrl(), serviceId);
			LogManager.getLogger().info("Hermet service: " +
					HermetResponseParser.getLocation(proxyResponse.raw()));
		} catch (IOException e) {
			throw new FailedConfigurationException(
					String.format("Failed to send request to setup proxy with parameters\n%s",
							proxyData.toString()), e);
		}
	}

	public static void addStubFromResponse(okhttp3.Response response) {
		String serviceId = HermetResponseParser.getServiceId(response);
		String stubId = HermetResponseParser.getStubId(response);
		if (!SessionHolder.HOLDER.serviceIdToStubIds.containsKey(serviceId)) {
			SessionHolder.HOLDER.serviceIdToStubIds.put(serviceId, new ArrayList<>());
		}
		SessionHolder.HOLDER.serviceIdToStubIds.get(serviceId).add(stubId);
	}

	public static String getServiceId(String targetUrl) {
		if (!SessionHolder.HOLDER.targetUrlToServiceId.containsKey(targetUrl)) {
			SessionHolder.HOLDER.createService(targetUrl);
		}
		return SessionHolder.HOLDER.targetUrlToServiceId.get(targetUrl);
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

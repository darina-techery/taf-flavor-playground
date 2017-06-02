package rest.api.hermet;

import data.Configuration;
import org.apache.logging.log4j.LogManager;
import rest.api.clients.HermetAPIClient;
import rest.api.payloads.hermet.HermetProxyData;
import rest.api.services.HermetAPI;
import retrofit2.Call;
import retrofit2.Response;
import utils.exceptions.FailedConfigurationException;

import javax.inject.Singleton;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class HermetSessionManager {
	private final Map<String, String> stubSessionIds = new HashMap<>();
	private final Map<String, List<String>> createdStubs = new HashMap<>();
	private HermetAPI hermetAPI;

	private HermetSessionManager() {
		hermetAPI = new HermetAPIClient().create(HermetAPI.class);
		createSession(Configuration.getParameters().apiURL);
	}

	private void createSession(String targetUrl) {
		HermetProxyData proxyData = new HermetProxyDataFactory().getProxyData(targetUrl);
		Call<Void> proxyCall;
		Response<Void> proxyResponse;
		try {
			proxyCall = hermetAPI.setupProxy(proxyData);
			proxyResponse = proxyCall.execute();
			String id = getSessionIdFromResponse(proxyResponse);
			stubSessionIds.put(targetUrl, id);
			LogManager.getLogger().info(String.format("Hermet session id for [%s]: %s", targetUrl, id));
		} catch (IOException e) {
			throw new FailedConfigurationException(
					String.format("Failed to send request to setup proxy with parameters\n%s",
							proxyData.toString()), e);
		}
	}

	private String getSessionIdFromResponse(Response<Void> proxyResponse) {
		String[] locationTokens = proxyResponse.headers().get("Location").split("/");
		return locationTokens[locationTokens.length - 1];
	}

	private static class SessionHolder {
		private static final HermetSessionManager HOLDER = new HermetSessionManager();
	}

	public static String getId(String targetUrl) {
		if (!SessionHolder.HOLDER.stubSessionIds.containsKey(targetUrl)) {
			SessionHolder.HOLDER.createSession(targetUrl);
		}
		return SessionHolder.HOLDER.stubSessionIds.get(targetUrl);
	}

	public static String createSessionWithCustomTimeout(String targetUrl, Duration timeout) {
		if (!SessionHolder.HOLDER.stubSessionIds.containsKey(targetUrl)) {
			SessionHolder.HOLDER.createSession(targetUrl);
		}
		return SessionHolder.HOLDER.stubSessionIds.get(targetUrl);
	}

	public static String addStubLocation(String targetUrl, String stubLocation) {
		return "";
	}

	class SessionData {
		private String targetUrl;
		private String sessionId;
		private List<String> stubLocations;
	}
}

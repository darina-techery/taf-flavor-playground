package rest.api.hermet;

import rest.api.payloads.hermet.response.HermetStub;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HermetSessionData {
	public final String serviceId;
	public final String targetUrl;
	private final List<HermetStub> hermetStubs;

	public HermetSessionData(String targetUrl, Response<Void> proxyResponse) {
		this.targetUrl = targetUrl;
		this.serviceId = getServiceIdFromResponse(proxyResponse);
		hermetStubs = new ArrayList<>();
	}

	public void addStub(HermetStub stub) {
		hermetStubs.add(stub);
	}

	public void deleteStubs() {
//		hermetStubs.forEach(stub -> {
//			try {
//				stub.delete();
//			} catch (IOException e) {
//				LogManager.getLogger().error("Failed to delete stub "+stub.getId());
//			}
//		});
	}

	private String getServiceIdFromResponse(Response<Void> proxyResponse) {
		String[] locationTokens = proxyResponse.headers().get("Location").split("/");
		return locationTokens[locationTokens.length - 1];
	}

}


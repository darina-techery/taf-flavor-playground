package rest.api.clients;

public class SharedServicesClient extends BaseAPIClient {
	public static final String SHARED_SERVICES_URL = "https://sharedservices.qa-worldventures.biz";
	@Override
	protected void initClient() {
		client = new RetrofitBuilder()
				.setBaseUrl(SHARED_SERVICES_URL)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.build();
	}
}

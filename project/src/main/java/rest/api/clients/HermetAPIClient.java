package rest.api.clients;

import data.Configuration;

public class HermetAPIClient extends BaseAPIClient{
	@Override
	protected void initClient() {
		client = new RetrofitBuilder()
				.setBaseUrl(Configuration.getParameters().stubUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.build();
	}
}

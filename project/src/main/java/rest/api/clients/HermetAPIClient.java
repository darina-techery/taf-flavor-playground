package rest.api.clients;

import rest.api.hermet.HermetProxyDataFactory;
import rest.api.interceptors.HermetInterceptor;

public class HermetAPIClient extends BaseAPIClient{
	@Override
	protected void initClient() {
//		String targetUrl = new HermetProxyDataFactory().getCommonProxyData().getTargetUrl();
		String targetUrl = new HermetProxyDataFactory().getCommonProxyData().getProxyHost();
		client = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
				.addInterceptor(new HermetInterceptor())
				.build();
	}

}

package rest.api.hermet;

import rest.api.payloads.hermet.HermetProxyData;

public final class HermetProxyDataFactory {
	private static final String DEFAULT_HERMET_PROXY_HOST = "techery-dt-staging.techery.io:5050";

	public HermetProxyData getProxyDataForUrl(String targetUrl) {
		HermetProxyData data = new HermetProxyData();
		String name = targetUrl.replaceAll("\\.", "-");
		data.setName(name);
		data.setTargetUrl(targetUrl);
		data.setProxyHost(DEFAULT_HERMET_PROXY_HOST);
		return data;
	}
}

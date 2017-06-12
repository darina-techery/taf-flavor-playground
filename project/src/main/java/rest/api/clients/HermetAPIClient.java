package rest.api.clients;

<<<<<<< HEAD
import rest.api.hermet.HermetProxyDataFactory;
import rest.api.interceptors.HermetInterceptor;
=======
import data.Configuration;
import rest.api.interceptors.HermetStubInterceptor;
>>>>>>> master

public class HermetAPIClient extends BaseAPIClient{
	@Override
	protected void initClient() {
//		String targetUrl = new HermetProxyDataFactory().getCommonProxyData().getTargetUrl();
		String targetUrl = "http://" + new HermetProxyDataFactory().getCommonProxyData().getStubsHost();
		client = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeaders(RetrofitBuilder.COMMON_HEADERS)
<<<<<<< HEAD
				.addInterceptor(new HermetInterceptor())
=======
				.addInterceptor(new HermetStubInterceptor())
>>>>>>> master
				.build();
	}

}

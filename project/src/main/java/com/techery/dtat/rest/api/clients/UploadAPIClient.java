package com.techery.dtat.rest.api.clients;

import org.apache.http.entity.ContentType;
import com.techery.dtat.rest.api.hermet.HermetProxyDataFactory;
import com.techery.dtat.rest.api.interceptors.AuthenticationInterceptor;
import com.techery.dtat.rest.api.interceptors.RetryInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadAPIClient extends BaseAPIClient {
	@Override
	protected void initClient() {
		String targetUrl = "http://" + new HermetProxyDataFactory().getCommonProxyData().getProxyHost();
		client = new RetrofitBuilder()
				.setBaseUrl(targetUrl)
				.addHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.getMimeType())
				.addHeaders(RetrofitBuilder.DT_HEADERS)
				.addInterceptor(new AuthenticationInterceptor())
				.addInterceptor(new RetryInterceptor())
				.build();
	}

	@Override
	protected GsonConverterFactory getConverterFactory() {
		return null;
	}
}

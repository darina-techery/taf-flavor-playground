package com.techery.dtat.rest.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class HeadersInterceptor implements Interceptor {
	private final Map<String, String> headers;

	public HeadersInterceptor(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		headers.keySet()
				.stream()
				.filter(key -> !chain.request().headers().names().contains(key))
				.forEach(key -> builder.addHeader(key, headers.get(key)));
		final Request request = builder.build();
		return chain.proceed(request);
	}
}

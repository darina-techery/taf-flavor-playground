package com.techery.dtat.rest.api.clients;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.http.entity.ContentType;
import com.techery.dtat.rest.api.interceptors.HeadersInterceptor;
import com.techery.dtat.rest.api.interceptors.APILoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RetrofitBuilder {
	public static final Map<String,String> COMMON_HEADERS = new HashMap<>();
	public static final Map<String,String> DT_HEADERS = new HashMap<>();

	private Map<String, String> headers = new HashMap<>();
	private List<Interceptor> interceptors = new ArrayList<>();
	private List<Converter.Factory> converterFactories = new ArrayList<>();
	private String baseUrl;

	static {
		COMMON_HEADERS.put("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		DT_HEADERS.put("Accept","application/com.dreamtrips.api+json;version=2");
		DT_HEADERS.put("DT-App-Version","1.19.0");
	}

	RetrofitBuilder addHeader(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	RetrofitBuilder addHeaders(Map<String, String> headers) {
		this.headers.putAll(headers);
		return this;
	}

	RetrofitBuilder addInterceptor(Interceptor interceptor) {
		interceptors.add(interceptor);
		return this;
	}

	RetrofitBuilder addGsonConverterFactory(Converter.Factory factory) {
		converterFactories.add(factory);
		return this;
	}

	RetrofitBuilder setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	Retrofit build() {
		if (baseUrl == null) {
			throw new FailedConfigurationException("Base URL required to build Retrofit client");
		}
		//create okhttp client to apply interceptors
		final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS);

		if (!headers.isEmpty()) {
			addInterceptor(new HeadersInterceptor(headers));
		}
		addInterceptor(new APILoggingInterceptor());
		interceptors.forEach(okHttpClientBuilder::addInterceptor);

		final OkHttpClient client = okHttpClientBuilder.build();
		final Retrofit.Builder restAdapterBuilder = new Retrofit.Builder()
				.client(client)
				.baseUrl(baseUrl);
		converterFactories.forEach(restAdapterBuilder::addConverterFactory);
		return restAdapterBuilder.build();
	}
}

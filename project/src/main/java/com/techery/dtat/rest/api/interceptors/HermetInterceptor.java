package com.techery.dtat.rest.api.interceptors;

import com.google.gson.Gson;
import okhttp3.*;
import okio.Buffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.techery.dtat.rest.api.clients.HermetAPIClient;
import com.techery.dtat.rest.api.clients.RetrofitBuilder;
import com.techery.dtat.rest.api.hermet.HermetServiceManager;
import com.techery.dtat.rest.api.model.hermet.HermetProxyData;
import com.techery.dtat.rest.api.services.HermetAPI;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HermetInterceptor implements Interceptor {
	private HermetAPI hermetAPI;
	private final Logger log = LogManager.getLogger();

	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request request = chain.request();
		final Response response;
		if (isAddServiceRequest(request)) {
			if (hermetAPI == null) {
				hermetAPI = new HermetAPIClient().create(HermetAPI.class);
			}
			//check if this service already exists;
			HermetProxyData activeService = getActiveServiceData(request);
			//if not, create and return HermetProxyData
			if (activeService == null) {
				response = chain.proceed(request);
				log.debug("Created a new service: " + response.header("Location"));
			} else {
				response = buildResponseWithRunningServiceData(request, activeService);
				log.debug("Picked up existing service: " + response.header("Location"));
			}
		} else {
			response = chain.proceed(request);
			if (isAddStubRequest(request)) {
				log.debug("Created a stub: " + response.header("Location"));
				HermetServiceManager.addStubFromResponse(response);
			}
		}
		return response;
	}

	private boolean isAddServiceRequest(Request request) {
		return request.method().equals("POST") && request.url().encodedPath().endsWith("services");
	}

	private boolean isAddStubRequest(Request request) {
		return request.method().equals("POST") && request.url().encodedPath().endsWith("stubs");
	}

	private boolean isDeleteStubRequest(Request request) {
		return request.method().equals("DELETE") && request.url().encodedPath().contains("stubs");
	}

	private HermetProxyData getActiveServiceData(Request newProxyRequest) throws IOException {
		String requestedProxyHost = getProxyHostFromNewProxyRequest(newProxyRequest);
		List<HermetProxyData> existingProxies = hermetAPI.getActiveServices().execute().body();
		if (existingProxies == null || existingProxies.isEmpty()) {
			return null;
		} else {
			List<HermetProxyData> proxiesWithProxyHost = existingProxies.stream()
					.filter(proxy -> proxy.getProxyHost().equals(requestedProxyHost))
					.collect(Collectors.toList());
			if (proxiesWithProxyHost.size() > 1) {
				throw new FailedConfigurationException(
						"More than 1 service exists for proxy host "+requestedProxyHost
								+ "\n\tActive services found: "
								+ proxiesWithProxyHost.stream().map(HermetProxyData::toString)
								.collect(Collectors.joining("\n")));
			}
			return proxiesWithProxyHost.isEmpty() ? null : proxiesWithProxyHost.get(0);
		}
	}

	private String getProxyHostFromNewProxyRequest(Request newProxyRequest) throws IOException {
		final Request copy = newProxyRequest.newBuilder().build();
		final Buffer buffer = new Buffer();
		copy.body().writeTo(buffer);
		HermetProxyData requestedProxy = new Gson().fromJson(buffer.readUtf8(), HermetProxyData.class);
		return requestedProxy.getProxyHost();
	}

	private Response buildResponseWithRunningServiceData(Request request, HermetProxyData existingProxyData) {
		String locationHeader = String.format("%s/api/services/%s",
				existingProxyData.getProxyHost(), existingProxyData.getId());
		Response.Builder builder = new Response.Builder()
				.code(201)
				.message("Return existing service data")
				.request(request)
				.protocol(Protocol.HTTP_1_0)
				.body(ResponseBody.create(MediaType.parse("application/json"), ""))
				.addHeader("Location", locationHeader);
		RetrofitBuilder.COMMON_HEADERS.forEach(builder::addHeader);
		return builder.build();
	}
}

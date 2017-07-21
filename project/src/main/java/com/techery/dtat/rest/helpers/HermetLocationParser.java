package com.techery.dtat.rest.helpers;

import com.techery.dtat.data.Configuration;
import okhttp3.Response;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HermetLocationParser {
	private HermetLocationParser() {}
	private static final FailedResponseParser responseLogger = new FailedResponseParser();

	public static String getServiceId(Response response) {
		Pattern serviceIdPattern = Pattern.compile(".*/services/(\\w*).*");
		return getLocationFragment(response, serviceIdPattern);
	}

	public static String getStubId(Response response) {
		Pattern stubIdPattern = Pattern.compile(".*/services/\\w*/stubs/(\\w*)");
		return getLocationFragment(response, stubIdPattern);
	}

	private static final String getLocationFragment(Response response, Pattern pattern) {
		String location = getLocation(response);
		Matcher matcher = pattern.matcher(location);
		if (!matcher.find()) {
			throw new FailedConfigurationException(
					"Unexpected response header format: expected " +
							"\"http://"+ Configuration.getParameters().apiURL+"/api/services/{serviceId}[/stubs/{stubId}]\", " +
							"but found "+location);
		}
		return matcher.group(1);
	}

	public static String getLocation(Response response) {
		String location = response.header("Location");
		if (location == null) {
			throw new NullPointerException("Location header was not found in response.\n"
					+ response.toString());

		}
		return location;
	}
}

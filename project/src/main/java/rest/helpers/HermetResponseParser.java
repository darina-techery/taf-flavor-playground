package rest.helpers;

import okhttp3.Response;
import utils.exceptions.FailedConfigurationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HermetResponseParser {
	private HermetResponseParser() {}
	private static final ResponseLogger responseLogger = new ResponseLogger();

	public static String getServiceId(Response response) {
		String location = getLocation(response);
		Pattern serviceIdPattern = Pattern.compile(".*/services/(\\w*).*");
		Matcher matcher = serviceIdPattern.matcher(location);
		if (!matcher.find()) {
			throw new FailedConfigurationException(
					"Unexpected response header format: expected " +
							"[http://techery-dt-staging.techery.io:5000/api/services/{serviceId}], " +
							"but found "+location);
		}
		return matcher.group(1);
	}

	public static String getStubId(Response response) {
		String location = getLocation(response);
		Pattern serviceIdPattern = Pattern.compile(".*/services/\\w*/stubs/(\\w*)");
		Matcher matcher = serviceIdPattern.matcher(location);
		if (!matcher.find()) {
			throw new FailedConfigurationException(
					"Unexpected response header format: expected " +
							"[http://techery-dt-staging.techery.io:5000/api/services/{serviceId}/stubs/{stubId}], " +
							"but found "+location);
		}
		return matcher.group(1);

	}

	public static String getLocation(Response response) {
		String location = response.header("Location");
		if (location == null) {
			throw new NullPointerException("Location header was not found in response.\n"+
					responseLogger.describeFailedResponse(response, "Hermet request"));
		}
		return location;
	}


	public static void main(String[] args) {
		String location = "http://techery-dt-staging.techery.io:5000/api/services/AVmSbf6y4r4GR1h0I1Qu/stubs/icaiahg";
		Pattern serviceIdPattern = Pattern.compile(".*/services/\\w*/stubs/(.*)");
		Matcher matcher = serviceIdPattern.matcher(location);
		System.out.println(matcher.find());
		System.out.println(matcher.groupCount());
		System.out.println(matcher.group(1));
	}
}

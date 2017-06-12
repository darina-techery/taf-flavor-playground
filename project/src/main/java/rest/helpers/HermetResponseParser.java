package rest.helpers;

import data.Configuration;
import okhttp3.Response;
import utils.exceptions.FailedConfigurationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HermetResponseParser {
	private HermetResponseParser() {}
<<<<<<< HEAD
	private static final FailedResponseParser responseLogger = new FailedResponseParser();

	public static String getServiceId(Response response) {
		Pattern serviceIdPattern = Pattern.compile(".*/services/(\\w*).*");
		return getLocationFragment(response, serviceIdPattern);
	}
=======
	private static final ResponseLogger responseLogger = new ResponseLogger();

	public static String getServiceId(Response response) {
		Pattern serviceIdPattern = Pattern.compile(".*/services/(\\w*).*");
		return getLocationFragment(response, serviceIdPattern);	}
>>>>>>> master

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
<<<<<<< HEAD
			throw new NullPointerException("Location header was not found in response.\n"
					+ response.toString());

		}
		return location;
	}
=======
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
>>>>>>> master
}

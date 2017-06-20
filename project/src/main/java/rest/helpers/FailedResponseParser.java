package rest.helpers;

import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

public class FailedResponseParser {

	public String describeFailedResponse(Response response, String purpose) {
		StringBuilder description;
		if (response == null) {
			description = buildEmptyResponseDescription(purpose);
		} else {
			description = buildResponseDescription(response, purpose);
			ResponseBody errorBody = response.errorBody();
			description.append("\n\tError body: ").append(getResponseBodyDescription(errorBody));
			description.append("\n\tError code: ").append(response.code());
		}
		return description.toString();
	}

	private String getResponseBodyDescription(ResponseBody body) {
		if (body != null) {
			try {
				return body.string();
			} catch (IOException e) {
				return "failed to retrieve due to IOException: " + e.getMessage();
			}
		} else {
			return "[no body]";
		}
	}

	private StringBuilder buildEmptyResponseDescription(String purpose) {
		return new StringBuilder("No response for request [").append(purpose).append("]");
	}

	private StringBuilder buildResponseDescription(Response rawResponse, String purpose) {
		StringBuilder description = new StringBuilder();
		description.append("Request [").append(purpose)
				.append("] failed.\n\tDetails: ")
				.append(rawResponse.message());
		return description;
	}


}

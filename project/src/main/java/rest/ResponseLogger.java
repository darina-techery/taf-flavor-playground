package rest;

import retrofit2.Response;

import java.io.IOException;

public class ResponseLogger {
	private final Response response;
	private final String purpose;

	public ResponseLogger(Response response, String purpose) {
		this.response = response;
		this.purpose = purpose;
	}

	public String describeFailedResponse() {
		StringBuilder description = new StringBuilder("");
		if (response == null) {
			description.append("No response for request [").append(purpose).append("]");
		} else {
			description.append("Request [").append(purpose).append("] failed.\n\tDetails: ")
					.append(response.message());
			if (response.errorBody() != null) {
				try {
					description.append("\n\tError body: "+response.errorBody().string());
				} catch (IOException e) {
					description.append("\n\tError body: failed to retrieve due to IOException: ")
							.append(e.getMessage());
				}
			}
			description.append("\n\tError code: ").append(response.code());
		}
		return description.toString();
	}
}

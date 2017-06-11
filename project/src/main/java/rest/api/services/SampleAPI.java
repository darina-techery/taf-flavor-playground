package rest.api.services;

import rest.api.payloads.internal.SampleResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SampleAPI {
	String SAMPLE_REQUEST_PATH = "/api/profile";

	@GET(SAMPLE_REQUEST_PATH)
	Call<SampleResponse> getSampleResponse();
}

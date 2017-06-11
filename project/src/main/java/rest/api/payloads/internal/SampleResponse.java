package rest.api.payloads.internal;

public class SampleResponse {
	private String id;

	public SampleResponse(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

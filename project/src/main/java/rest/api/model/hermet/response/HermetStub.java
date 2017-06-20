package rest.api.model.hermet.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HermetStub {
	private String id;
	private JsonObject response;
	private JsonArray predicates;
	private String serviceId;
	private String sessionId;
	private String expireAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JsonObject getResponse() {
		return response;
	}

	public void setResponse(JsonObject response) {
		this.response = response;
	}

	public JsonArray getPredicates() {
		return predicates;
	}

	public void setPredicates(JsonArray predicates) {
		this.predicates = predicates;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(String expireAt) {
		this.expireAt = expireAt;
	}

}

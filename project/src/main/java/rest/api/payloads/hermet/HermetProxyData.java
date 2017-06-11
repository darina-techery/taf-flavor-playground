package rest.api.payloads.hermet;

import com.google.gson.annotations.Expose;

import java.time.Duration;

public class HermetProxyData {
	public static final String CONFIG_FILE_NAME = "config/hermet_config.json";
	@Expose
	private String name;
	@Expose
	private String proxyHost;
	@Expose
	private long proxyTimeout;

	/**
	 * Requests to this URL will be proxied
	 */
	private String targetUrl;
	private String id;
	private String description;

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyTimeout(long proxyTimeoutInSeconds) {
		this.proxyTimeout = proxyTimeoutInSeconds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@Override
	public String toString() {
		return "name = '"+name+"', targetUrl = '"+targetUrl+"', proxyHost = '"+ proxyHost +"'";
	}

	public long getProxyTimeout() {
		return proxyTimeout;
	}

	public void setProxyTimeout(Duration proxyTimeout) {
		this.proxyTimeout = proxyTimeout.getSeconds();
	}
}
package com.techery.dtat.rest.api.model.hermet;

import com.google.gson.annotations.Expose;

import java.time.Duration;

public class HermetProxyData {
	public static final String CONFIG_FILE_NAME = "config/hermet_config.json";
	@Expose
	private String name;
	@Expose
	private String proxyUrl;
	@Expose
	private long proxyTimeout;
	@Expose
	private int stubsPort;
	@Expose
	private int proxyPort;

	public String getProxyUrl() {
		return proxyUrl;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}

	public int getStubsPort() {
		return stubsPort;
	}

	public void setStubsPort(int stubsPort) {
		this.stubsPort = stubsPort;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * Requests to this URL will be proxied
	 */
	private String targetUrl;

	/**
	 * This is a proxy host = proxy URL, proxy port
	 */
	private String proxyHost;
	private String id;
	private String description;

	public String getStubsHost() {
		return proxyUrl + ":" + stubsPort;
	}

	public String getProxyHost() {
		initProxyHost();
		return proxyHost;
	}

	public void initProxyHost() {
		proxyHost = proxyUrl + ":" + proxyPort;
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

	public long getProxyTimeout() {
		return proxyTimeout;
	}

	public void setProxyTimeout(Duration proxyTimeout) {
		this.proxyTimeout = proxyTimeout.getSeconds();
	}
}
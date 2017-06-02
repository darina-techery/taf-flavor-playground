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
	private String targetUrl;

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

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	@Override
	public String toString() {
		return "name = '"+name+"', targetUrl = '"+targetUrl+"', proxyHost = '"+proxyHost+"'";
	}

	public long getProxyTimeout() {
		return proxyTimeout;
	}

	public void setProxyTimeout(Duration proxyTimeout) {
		this.proxyTimeout = proxyTimeout.getSeconds();
	}
}
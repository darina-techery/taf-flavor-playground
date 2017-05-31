package rest.api.payloads.hermet;

public class HermetProxyData {
	private String name;
	private String targetUrl;
	private String proxyHost;

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
}
package rest.api.hermet;

import data.Configuration;
import data.TestDataReader;
import rest.api.payloads.hermet.HermetProxyData;
import utils.exceptions.InvalidDataFileException;

import java.io.FileNotFoundException;
import java.time.Duration;

public final class HermetProxyDataFactory {
	public HermetProxyData getCommonProxyData() {
		return getProxyData(Configuration.getParameters().apiURL);
	}

	public HermetProxyData getProxyData(String targetUrl) {
		HermetProxyData data = readDefaultValues();
		if (data.getName() == null) {
			String name = targetUrl.replaceAll("[\\W]+", "-");
			data.setName(name);
		}
		data.setTargetUrl(targetUrl);
		return data;
	}

	public HermetProxyData getProxyData(String targetUrl, Duration expirationTime) {
		HermetProxyData data = getProxyData(targetUrl);
		data.setProxyTimeout(expirationTime);
		return data;
	}

	private HermetProxyData readDefaultValues() {
		HermetProxyData data;
		try {
			data = new TestDataReader<>(HermetProxyData.CONFIG_FILE_NAME, HermetProxyData.class)
					.read();
		} catch (FileNotFoundException e) {
			throw new InvalidDataFileException(
					String.format("Config file for Hermet not found at %s", HermetProxyData.CONFIG_FILE_NAME), e);
		}
		return data;
	}
}

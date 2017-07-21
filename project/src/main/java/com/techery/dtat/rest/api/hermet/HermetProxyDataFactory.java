package com.techery.dtat.rest.api.hermet;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.TestDataReader;
import com.techery.dtat.rest.api.model.hermet.HermetProxyData;
import com.techery.dtat.utils.exceptions.InvalidDataFileException;

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
		data.initProxyHost();
		return data;
	}
}

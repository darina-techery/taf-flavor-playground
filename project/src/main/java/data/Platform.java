package data;

import utils.exceptions.FailedConfigurationException;

public enum Platform {
	ANDROID_PHONE("androidPhone"),
	ANDROID_TABLET("androidTablet"),
	IPHONE("iPhone"),
	IPAD("iPad");

	private String configValue;

	Platform(String configValue) {
		this.configValue = configValue;
	}

	public static Platform byName(String name) {
		for (Platform type : Platform.values()) {
			if (type.configValue.equalsIgnoreCase(name)) {
				return type;
			}
		}
		throw new FailedConfigurationException("Cannot parse platform name ["+name+"]");
	}

}

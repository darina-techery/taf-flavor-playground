package data;

import data.structures.RunParameters;
import utils.exceptions.InvalidDataFileException;

import java.io.FileNotFoundException;

public class Configuration {

	private final RunParameters runParameters = new RunParameters();

	public static final String CONFIG_FILE_NAME = "config/default_config.json";

	private static class ConfigurationHolder {
		private static final Configuration INSTANCE = new Configuration();
	}

	private Configuration() {
		RunParameters defaultParameters = readConfigFromFixture();
		runParameters.readFromSysEnv();
		runParameters.addMissingValues(defaultParameters);
	}

	public static RunParameters getParameters() {
		return ConfigurationHolder.INSTANCE.runParameters;
	}

	private RunParameters readConfigFromFixture(){
		try {
			TestDataReader<RunParameters> configReader = new TestDataReader<>(CONFIG_FILE_NAME, RunParameters.class);
			return configReader.read();
		} catch (FileNotFoundException e) {
			throw new InvalidDataFileException("Failed to locate config file ["+CONFIG_FILE_NAME+"]", e);
		}
	}

	public static boolean isAndroidPhone() {
		return getParameters().platform.equals(Platform.ANDROID_PHONE);
	}

	public static boolean isAndroidTablet() {
		return getParameters().platform.equals(Platform.ANDROID_TABLET);
	}

	public static boolean isIPhone() {
		return getParameters().platform.equals(Platform.IPHONE);
	}

	public static boolean isIPad() {
		return getParameters().platform.equals(Platform.IPAD);
	}

	public static boolean isIOS() { return isIPad() || isIPhone();}

	public static boolean isAndroid() { return isAndroidPhone() || isAndroidTablet(); }

	public static boolean isPhone(){
		return isAndroidPhone() || isIPhone();
	}

	public static boolean isTablet(){
		return isAndroidTablet() || isIPad();
	}

}

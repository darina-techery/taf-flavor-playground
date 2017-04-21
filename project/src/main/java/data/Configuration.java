package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import utils.exceptions.FailedConfigurationException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuration {

	public final RunParameters runParameters = new RunParameters();

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
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		try {
			JsonReader reader = new JsonReader(new FileReader(
					"src/test/resources/fixtures/default_config.fixtures.json"));
			return gson.fromJson(reader, RunParameters.class);
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException(e, "Failed to locate config file");
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

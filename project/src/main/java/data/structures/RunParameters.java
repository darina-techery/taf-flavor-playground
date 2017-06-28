package data.structures;

import com.google.gson.annotations.Expose;
import data.Platform;
import utils.StringHelper;
import utils.annotations.EnvVar;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;

public class RunParameters {
	@EnvVar("DEVICE")
	@Expose
	public String device;

	@EnvVar("LOCALE")
	@Expose
	public String locale;

	@EnvVar("API_URL")
	@Expose
	public String apiURL;

	@EnvVar("PLATFORM")
	@Expose
	String platformName;

	@EnvVar("RUN_ON_CI")
	@Expose
	String runOnCI;

	@Expose
	String droidAppName;

	@Expose
	String iosAppName;

	@EnvVar("APP_PATH")
	String appPath;

	public boolean isCIRun;

	public Platform platform;

	public String fullAppPath;

	private static final String APP_RESOURCE_DIRECTORY = "apps";

	public void readFromSysEnv() {
		for (Field f : this.getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(EnvVar.class)) {
				EnvVar envVar = f.getAnnotation(EnvVar.class);
				String value = System.getenv(envVar.value());
				setValue(f, value);
			}
		}
	}

	public void addMissingValues(RunParameters defaultParameters) {
		for (Field f : this.getClass().getDeclaredFields()) {
			try {
				if (f.isAnnotationPresent(Expose.class)) {
					String currentValue = (String) f.get(this);
					if (StringHelper.isNullOrEmpty(currentValue)) {
						String defaultValue = (String) f.get(defaultParameters);
						setValue(f, defaultValue);
					}
				}
			} catch (IllegalAccessException e) {
				throw new FailedConfigurationException("Failed to read ["+f.getName()+"] from preferred parameters set.", e);
			}
		}
		this.platform = Platform.byName(platformName);
		this.isCIRun = (!StringHelper.isNullOrEmpty(runOnCI)
				&& Arrays.asList("yes", "y", "true").contains(runOnCI));
		try {
			this.fullAppPath = getAbsolutePathToApp();
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("Failed to locate application under test.");
		}
	}

	private String getAbsolutePathToApp() throws FileNotFoundException {
		String applicationFileName = platform.equals(Platform.IPAD) || platform.equals(Platform.IPHONE)
				? iosAppName : droidAppName;
		if (this.appPath == null) {
			ClassLoader classLoader = getClass().getClassLoader();
			String resourcePath = APP_RESOURCE_DIRECTORY + File.separator + applicationFileName;
			URL resource = classLoader.getResource(resourcePath);
			if (resource == null) {
				throw new FileNotFoundException(String.format(
						"Application under test was not found as resource at path %s", resourcePath));
			}
			return resource.getPath();
		} else {
			File fullPath = new File(appPath, applicationFileName);
			if (!fullPath.exists()) {
				throw new FileNotFoundException(String.format(
						"Application under test was not found at [%s].", fullPath));
			}
			return fullPath.getAbsolutePath();
		}
	}

	private void setValue(Field f, String value) {
		f.setAccessible(true);
		try {
			f.set(this, value);
		} catch (IllegalAccessException e) {
			throw new FailedConfigurationException(
					"Could not set value ["+value+"] to parameter ["+f.getName()+"]", e);
		}
	}

}

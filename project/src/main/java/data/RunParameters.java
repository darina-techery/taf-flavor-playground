package data;

import com.google.gson.annotations.Expose;
import utils.StringUtils;
import utils.annotations.EnvVar;
import utils.exceptions.FailedConfigurationException;

import java.io.File;
import java.lang.reflect.Field;
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

	@EnvVar("APP_PATH")
	@Expose
	String appPath;

	@EnvVar("RUN_ON_CI")
	@Expose
	String runOnCI;

	@Expose
	String droidAppName;

	@Expose
	String iosAppName;

	public boolean isCIRun;

	public Platform platform;

	public String fullAppPath;

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
					if (StringUtils.isNullOrEmpty(currentValue)) {
						String defaultValue = (String) f.get(defaultParameters);
						setValue(f, defaultValue);
					}
				}
			} catch (IllegalAccessException e) {
				throw new FailedConfigurationException(e, "Failed to read ["+f.getName()+"] from preferred parameters set.");
			}
		}
		this.platform = Platform.byName(platformName);
		this.isCIRun = (!StringUtils.isNullOrEmpty(runOnCI)
				&& Arrays.asList("yes", "y", "true").contains(runOnCI));
		this.fullAppPath = getAbsolutePathToApp();
	}

	private String getAbsolutePathToApp(){
		String applicationFileName = platform.equals(Platform.IPAD) || platform.equals(Platform.IPHONE)
				? iosAppName : droidAppName;
		File fullPath = new File(appPath, applicationFileName);
		if (!fullPath.exists()) {
			throw new RuntimeException(String.format(
					"Application under test was not found at [%s].", fullPath));
		}
		return fullPath.getAbsolutePath();
	}

	private void setValue(Field f, String value) {
		f.setAccessible(true);
		try {
			f.set(this, value);
		} catch (IllegalAccessException e) {
			throw new FailedConfigurationException(e, "Could not set value ["+value+"] to parameter ["+f.getName()+"]");
		}
	}

}

package utils;

import data.Configuration;

public final class ADBUtils {
	private ADBUtils(){}

	private static String getAdbShellPrefix() {
		String deviceId = Configuration.getParameters().device;
		return String.format("adb -s %s shell", deviceId);
	}

	public static boolean isKeyboardShown() {
		String command = String.format("%s dumpsys input_method | grep mInputShown | sed 's/.*mInputShown=\\(.*\\)/\\1/'",
				getAdbShellPrefix());
		String output = CMDUtils.executeCommand(command);
		return Boolean.parseBoolean(output);
	}
}

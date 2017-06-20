package utils;

import data.Configuration;
import data.Platform;
import org.apache.logging.log4j.LogManager;
import utils.exceptions.FailedConfigurationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugUtils {
	public static void launchPhoneEmulator(int port){
		CMDUtils.launchEmulator("nexus5", port);
	}

	public static void launchTabletEmulator(int port){
		CMDUtils.launchEmulator("nexus9", port);
	}

	public static void pauseForAppiumDebug(String browserName, String inspectorHtmlLocation){
		String openInspectorCommand = "osascript -e 'tell application \""+browserName+"\"\n" +
				"  set myTab to make new tab at end of tabs of window 1\n" +
				"  set URL of myTab to \"file://"+inspectorHtmlLocation+"\"\n" +
				"  activate\n" +
				"end tell'";
		CMDUtils.executeCommand(openInspectorCommand);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			LogManager.getLogger().error(e);
		}
	}

	private static void rewriteDefaultConfig(Map<String, String> parameters) {
		Path config = new File("project/src/test/resources/" + Configuration.CONFIG_FILE_NAME).toPath();
		List<String> content = new ArrayList<>();
		String replacementPattern = ":\\s*\"[^\"]+";
		try (BufferedReader reader = Files.newBufferedReader(config)) {
			String line;
			while ((line = reader.readLine()) != null) {
				for (String key: parameters.keySet()) {
					if (line.contains(key)) {
						line = line.replaceAll(replacementPattern, ": \"" + parameters.get(key));
					}
				}
				content.add(line);
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		try (BufferedWriter writer = Files.newBufferedWriter(config)) {
			for (String line : content) {
				writer.write(line, 0, line.length());
				writer.newLine();
			}
			writer.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public static void updateAppUnderTest(Platform platform, String appName) {
		Map<String, String> params = new HashMap<>();
		switch (platform) {
			case ANDROID_PHONE:
			case ANDROID_TABLET:
				params.put("droidAppName", appName);
				break;
			case IPAD:
			case IPHONE:
				params.put("iosAppName", appName);
				break;
			default:
				throw new FailedConfigurationException("No known app for platform "+platform);
		}
		rewriteDefaultConfig(params);
	}

	public static void updatePlatformAndDevice(String platform, String device) {
		Map<String, String> params = new HashMap<>();
		params.put("device", device);
		params.put("platformName", platform);
		rewriteDefaultConfig(params);
	}
}

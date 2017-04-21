package utils;

import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

	public static void rewriteDefaultConfig(String platform, String device) {
		Path config = new File("project/src/test/resources/fixtures/default_config.fixtures.json").toPath();
		List<String> content = new ArrayList<>();
		String replacementPattern = ":\\s*\"[^\"]+";
		try (BufferedReader reader = Files.newBufferedReader(config)) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("device")) {
					line = line.replaceAll(replacementPattern, ": \"" + device);
				} else if (line.contains("platformName")) {
					line = line.replaceAll(replacementPattern, ": \"" + platform);
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
}

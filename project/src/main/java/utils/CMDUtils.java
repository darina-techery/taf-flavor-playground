package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.exceptions.FailedTestException;
import utils.waiters.AnyWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.String.format;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public final class CMDUtils {

	private static final Logger log = LogManager.getLogger(CMDUtils.class);
	private static final int CMD_RETRY_TIMEOUT_MILLIS = 1000;
	private static final String COMMAND_RETRY_TEMPLATE =
			"\n\tExecuted command: [%s]\n\tExpected response: [%s]\n\tFound: [%s]";
	private CMDUtils(){}

	public static String launchEmulator(String name, int port) {
		String emulator = "emulator-" + port;
		executeCommand("adb kill-server");
		//kill emu if its already launched
		executeCommand(format("adb -s %s emu kill", emulator));
		if (System.getenv("RUN_ON_CI") != null) {
			executeCommand("emulator @" + name + " -no-window -gpu on -memory 2080 -wipe-data -no-boot-anim -port " + port);
		}
		else {
			executeCommand("emulator @" + name + " -gpu on -memory 2080 -wipe-data -no-boot-anim -port " + port);
		}
		boolean isLaunched = waitForRuntimeMessageContains(
				format("adb -s %s shell dumpsys activity | grep mState", emulator),
				"RUNNING",
				60
		);
		if (!isLaunched){
			String emulatorState = executeCommand("adb -s %s shell dumpsys activity | grep mState");
			throw new FailedTestException(String.format("Could not launch emulator %s on port %d.\nActivity status: %s",
					name, port, emulatorState));
		}
		return emulator;
	}

	public static Boolean waitForRuntimeMessageContains(final String command, String expectedResponse, int waitSec) {
		AnyWait<Void, String> commandRunner = new AnyWait<>();
		commandRunner
				.duration(ofSeconds(waitSec))
				.retryInterval(ofMillis(CMD_RETRY_TIMEOUT_MILLIS))
				.calculate(() -> {
					String line = executeCommandAndGetFullResponse(command);
					log.debug(String.format(COMMAND_RETRY_TEMPLATE, command, expectedResponse, line));
					return line;
				})
				.until(line -> line.contains(expectedResponse))
				.go();
		return commandRunner.isSuccess();
	}


	public static String executeCommand(String command) {
		String response;
		try {
			BufferedReader buf = exec(command);
			response = buf.readLine();
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to execute command ["+command+"] and read response.", e);
		}
		return response == null ? "" : response;
	}

	public static String executeCommandAndGetFullResponse(String command) {
		String response;
		StringBuffer body = new StringBuffer();
		try {
			BufferedReader buf = exec(command);
			response = buf.readLine();
			while (response != null) {
				log.trace(response);
				body.append(response);
				response = buf.readLine();
			}
		}
		catch (IOException e) {
			log.error("Failed to execute command ["+command+"] and read full response", e);
		}
		return body.toString();
	}

	private static BufferedReader exec(String command) throws IOException {
		log.info("Execute >> "+command+"");
		Runtime run = Runtime.getRuntime();
		Process pr = run.exec(new String[]{"/bin/bash", "-c", command});
		return new BufferedReader(new InputStreamReader(pr.getInputStream()));
	}

	public static String getDreamTripBundleId(){
		String bundleId = executeCommandAndGetFullResponse("fbsimctl list_apps | grep DreamTrip | grep bundle_id | sed -n -e 's/.*\"bundle_id\" = \"\\(.*\\)\";/\\1/p'");//
		return bundleId;
	}

	public static void reInstallAndLaunchIOS(String bundleId, String appPath){
		executeCommand("xcrun simctl uninstall booted "+ bundleId);
		executeCommand("xcrun simctl install booted "+ appPath);
		executeCommand("xcrun simctl launch booted "+ bundleId);
	}

	public static void closeEmulatorAndroid() {
		executeCommand("adb kill-server");
		executeCommand("adb emu kill");
	}

	public static void closeSimulatorIOS(String device){
		executeCommand("xcrun simctl shutdown "+device);
		String deviceState = executeCommand("fbsimctl --state=booted list");
	}
}

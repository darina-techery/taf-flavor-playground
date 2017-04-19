package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class CMDUtils {

	private static final Logger log = LogManager.getLogger(CMDUtils.class);
	private CMDUtils(){}

	public static String executeCommand(String command) {
		log.info("Execute >> "+command+"");
		String[] cmd = {"/bin/sh", "-c", command};
		Runtime run = Runtime.getRuntime();
		Process pr;
		String response = "";
		try {
			pr = run.exec(cmd);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			response = buf.readLine();
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to calculate command ["+command+"] and read response.", e);
		}
		return response == null ? "" : response;
	}

	public static String getDreamTripBundleId(){
		return executeCommand("fbsimctl list_apps | grep DreamTrip | grep bundle_id | sed -n 's/.*=\\(.*\\)/\\1/p'").replaceAll("[\";' ']*","");
	}

	public static void reInstallAndLaunchIOS(String bundleId, String appPath){
		executeCommand("xcrun simctl uninstall booted "+ bundleId);
		executeCommand("xcrun simctl install booted "+ appPath);
		executeCommand("xcrun simctl launch booted "+ bundleId);
	}
}

package driver;

import data.Configuration;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

@Singleton
final class AppiumServiceProvider {

	private AppiumDriverLocalService service;

	AppiumDriverLocalService getService(){
		if (service == null) {
			String port = acquirePort();
			final AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
//			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_TIMESTAMP);
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOCAL_TIMEZONE);
			appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			appiumServiceBuilder.withArgument(GeneralServerFlag.TEMP_DIRECTORY, "./target/tmp");
			appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, port);
			appiumServiceBuilder.withIPAddress("127.0.0.1");

			if (Configuration.getParameters().isCIRun) {
				appiumServiceBuilder.usingAnyFreePort();
			}

			service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
			service.start();
			LogManager.getLogger().info("Starting Appium service on port {}", service.getUrl().getPort());
		}
		return service;
	}

	private String acquirePort() {
		try {
			Runtime.getRuntime().exec("killall node");
			ServerSocket socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			int localPort = socket.getLocalPort();
			socket.close();
			return String.valueOf(localPort);
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to acquire port for Appium", e);
		}
	}
}

package driver;

import data.Configuration;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

@Singleton
class AppiumServiceProvider {

	private AppiumDriverLocalService service;

	AppiumDriverLocalService getService(){
		if (service == null) {
			String port = getPort();
			final AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_TIMESTAMP);
			appiumServiceBuilder.withArgument(GeneralServerFlag.LOCAL_TIMEZONE);
			appiumServiceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			appiumServiceBuilder.withArgument(GeneralServerFlag.TEMP_DIRECTORY, "./target/tmp");
			appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, port);
			appiumServiceBuilder.withIPAddress("127.0.0.1");
			appiumServiceBuilder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"));

			if (Configuration.getParameters().isCIRun) {
				appiumServiceBuilder.usingAnyFreePort();
			}

			service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
			service.start();
		}

		return service;
	}

	private String getPort() {
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

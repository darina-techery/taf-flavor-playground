package com.techery.dtat.actions;

import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.Logger;
import com.techery.dtat.utils.CMDUtils;
import com.techery.dtat.utils.log.LogProvider;

import javax.annotation.Nonnull;
import java.util.Map;

public class IOSDriverActions extends DriverActions implements LogProvider {

	private final Logger log = getLogger();

	@Override
	public void resetApplication() {
		log.debug("Reinstall application for iOS");
		String bundleId = CMDUtils.getDreamTripBundleId();
		String appPath = (String) getDriver().getCapabilities().getCapability(MobileCapabilityType.APP);
		CMDUtils.reInstallAndLaunchIOS(bundleId, appPath);
		log.debug("Reboot driver: [START]");
		reInitDriver();
		log.debug("Reboot driver: [ END ]");
	}

	@Override
	public Map<String, String> extractAppStrings(@Nonnull String locale) {
		//TODO: https://techery.atlassian.net/browse/DTAUT-368 - update string map for iOS
//		String lang = getLanguage(locale);
//		Map<String, String> local = getDriver().getAppStringMap(lang, "Localizable_Local.strings");
//		Map<String, String> messenger = driver.getAppStringMap(lang, "Localizable_Messenger.strings");
//		Map<String, String> social = driver.getAppStringMap(lang, "Localizable_Social.strings");
//		all.putAll(local);
//		all.putAll(messenger);
//		all.putAll(social);
		return super.extractAppStrings(locale);
	}

}

package utils.ui;

import driver.DriverProvider;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class Screenshot {
	private static final Logger log = LogManager.getLogger(Screenshot.class);
	private static final String SCREENSHOTS_FOLDER = "target/screenshots";
	private static final String SCREENSHOT_EXTENSION = ".png";
	private static boolean DRIVER_READY_TO_TAKE_SCREENSHOTS = false;

	public static void enableScreenshots() {
		DRIVER_READY_TO_TAKE_SCREENSHOTS = true;
	}

	public static boolean isDriverReadyToTakeScreenshots() {
		return DRIVER_READY_TO_TAKE_SCREENSHOTS;
	}

	public static synchronized File getScreenshotOnFail(String testMethodName) {
		String folderPath = SCREENSHOTS_FOLDER + "/failure/";
		log.info("Capturing the image of the page on failure");
		return takeScreenshot(folderPath, testMethodName);
	}

	public static synchronized File getScreenshotOnTimeout(String filename) {
		String folderPath = SCREENSHOTS_FOLDER + "/timeout/";
		return takeScreenshot(folderPath, filename);
	}

	public static synchronized File getScreenshot(String filename) {
		return takeScreenshot(SCREENSHOTS_FOLDER, filename);
	}

	private static String replaceInvalidCharactersInFilename(String filename){
		Pattern pattern = Pattern.compile("[^\\w\\-.]+");
		return pattern.matcher(filename).replaceAll("_");
	}

	private static synchronized File takeScreenshot(String folderPath, String rawFileName) {
		File srcFile = DriverProvider.get().getScreenshotAs(OutputType.FILE);
		new File(folderPath).mkdirs();
		String fileName = replaceInvalidCharactersInFilename(rawFileName)
				+ StringUtils.getTimestampSuffix()
				+ SCREENSHOT_EXTENSION;
		File destFile = new File(folderPath + fileName);
		try {
			FileUtils.copyFile(srcFile, destFile);
			log.info("Screenshot created: \nfile://" + destFile.getAbsolutePath());
		} catch (IOException e) {
			log.error("Failed to save screenshot as ["+fileName+"] due to exception: ", e);
		}
		return destFile;
	}

}

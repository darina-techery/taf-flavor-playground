package utils.ui;

import driver.DriverProvider;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import utils.StringHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
				+ StringHelper.getTimestampSuffix()
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

	public BufferedImage makeScreenshotOfElement(final MobileElement element) throws IOException  {

		final BufferedImage img;
		final Point topLeft;
		final Point bottomRight;

		final byte[] screen;
		screen = DriverProvider.get().getScreenshotAs(OutputType.BYTES);

		img = ImageIO.read(new ByteArrayInputStream(screen));

		topLeft = element.getLocation();
		bottomRight = new Point(element.getSize().getWidth(),
				element.getSize().getHeight());

		return img.getSubimage(topLeft.getX(),
				topLeft.getY(),
				bottomRight.getX(),
				bottomRight.getY());
	}

	public void saveScreenToFile(final MobileElement e) throws IOException {
		BufferedImage buffer = makeScreenshotOfElement(e);
		File outputFile = new File("saved.png");
		ImageIO.write(buffer, "png", outputFile);
	}


	public Color averageColor(BufferedImage bufferedImage) {
		int x0=bufferedImage.getMinX();
		int y0=bufferedImage.getMinY();

		int x1 = x0 + bufferedImage.getWidth();
		int y1 = y0 + bufferedImage.getHeight();
		long sumr = 0, sumg = 0, sumb = 0;
		for (int x = x0; x < x1; x++) {
			for (int y = y0; y < y1; y++) {
				Color pixel = new Color(bufferedImage.getRGB(x, y));
				sumr += pixel.getRed();
				sumg += pixel.getGreen();
				sumb += pixel.getBlue();
			}
		}
		int num = bufferedImage.getWidth() * bufferedImage.getHeight();
		return new Color((int) sumr / num, (int) sumg / num, (int) sumb / num);
	}

	public String getColorName(Color color) {
		int rgb = color.getRGB();

		float hsb[] = new float[3];
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >>  8) & 0xFF;
		int b = (rgb      ) & 0xFF;
		Color.RGBtoHSB(r, g, b, hsb);

		if      (hsb[1] < 0.1 && hsb[2] > 0.9) return "nearlyWhite";
		else if (hsb[2] < 0.1) return "nearlyBlack";
		else {
			float deg = hsb[0]*360;
			if      (deg >=   0 && deg <  30) return "red";
			else if (deg >=  30 && deg <  90) return "yellow";
			else if (deg >=  90 && deg < 150) return "green";
			else if (deg >= 150 && deg < 210) return "cyan";
			else if (deg >= 210 && deg < 270) return "blue";
			else if (deg >= 270 && deg < 330) return "magenta";
			else return "red";
		}

	}

}

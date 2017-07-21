package com.techery.dtat.listeners;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.Platform;
import com.techery.dtat.driver.DriverProvider;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.testng.IAnnotationTransformer;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;
import ru.yandex.qatools.allure.annotations.Attachment;
import com.techery.dtat.utils.annotations.RunOn;
import com.techery.dtat.utils.annotations.SkipOn;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomTestListener extends TestListenerAdapter
		implements IAnnotationTransformer {
	private Logger log = LogManager.getLogger();

	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		log.info("\n * ---- TEST [{}] started at {}",
				getFullTestName(result),
				LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		Throwable failureReason = tr.getThrowable();
		String stacktrace = Arrays.stream(failureReason.getStackTrace())
				.map(StackTraceElement::toString)
				.collect(Collectors.joining("\n\t"));

		log.error(String.format("Test [%s] failed: %s\n\tStacktrace:%s",
				getFullTestName(tr),
				failureReason.getMessage(),
				stacktrace));
		try {
			createAttachmentOnFail();
		} catch (Exception e) {
			log.error("{}: can't capture test data on Fail due to error:\n{}", getFullTestName(tr), e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		try {
			createAttachmentOnSkip();
		} catch (Exception e) {
			log.error("{}: can't capture test data on Fail due to error:\n{}", iTestResult.getName(), e.getMessage());
		}

	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (!isMethodAllowedByPlatform(testMethod)) {
			annotation.setEnabled(false);
		}
	}

	private boolean isMethodAllowedByPlatform(Method testMethod) {
		Platform currentPlatform = Configuration.getParameters().platform;
		SkipOn skipOn = testMethod.getDeclaredAnnotation(SkipOn.class);
		if (skipOn != null && isPlatformInList(currentPlatform, skipOn.platforms())) {
			log.warn("Test [" + testMethod.getName() + "] is disabled due to Jira issue " + skipOn.jiraIssue());
			return false;
		}

		RunOn runOn = testMethod.getDeclaredAnnotation(RunOn.class);
		if (runOn != null && !isPlatformInList(currentPlatform, runOn.platforms())) {
			log.info("Test [" + testMethod.getName() + "] is not designed for " + currentPlatform + " and will be skipped.");
			return false;
		}
		return true;
	}

	private boolean isPlatformInList(Platform targetPlatform, Platform[] platforms) {
		return Arrays.stream(platforms)
				.anyMatch(platform -> platform.equals(Platform.ANY) || platform.equals(targetPlatform));
	}

	@Attachment(value = "Screen on fail", type = "image/png")
	public synchronized byte[] createAttachmentOnFail() throws IOException {
		return getScreenFromFile();
	}

	@Attachment(value = "Screen on skip", type = "image/png")
	public synchronized byte[] createAttachmentOnSkip() throws IOException {
		return getScreenFromFile();
	}

	private String getFullTestName(ITestResult tr) {
		return tr.getTestClass().getRealClass().getSimpleName() + "." + tr.getName();
	}

	private byte[] getScreenFromFile() throws IOException {
		File screenshotAs = DriverProvider.get().getScreenshotAs(OutputType.FILE);
		return FileUtils.readFileToByteArray(screenshotAs);
	}
}

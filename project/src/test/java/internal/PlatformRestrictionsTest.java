package internal;

import base.BaseTest;
import data.Configuration;
import data.Platform;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.annotations.RunOn;
import utils.annotations.SkipOn;
import utils.runner.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class PlatformRestrictionsTest extends BaseTest {

	@Test(dependsOnGroups = "prepare")
	public void verifyCalledMethods(ITestContext testContext) {
		List<String> differences = new ArrayList<>();
		ITestNGMethod[] methods = testContext.getAllTestMethods();
		for (ITestNGMethod method : methods) {
			if (method.isTest() && Arrays.stream(method.getGroups()).anyMatch(g -> g.equals("prepare"))) {
				Method m = method.getConstructorOrMethod().getMethod();
				if (shouldMethodBeCalled(m)) {
					if (method.getCurrentInvocationCount() != 1) {
						differences.add("Method "+m.getName()
								+ " should have been called, but was not. Current invocation count: "
								+ method.getCurrentInvocationCount() +"\n");
					}
				} else {
					if (method.getCurrentInvocationCount() != 0) {
						differences.add("Method "+m.getName()
								+ " should NOT have been called, but was. Current invocation count: "
								+ method.getCurrentInvocationCount() + "\n");
					}
				}
			}
		}
		Assert.assertThat("Methods were called and skipped properly", differences, is(empty()));
	}

	private boolean shouldMethodBeCalled(Method m) {
		Platform currentPlatform = Configuration.getParameters().platform;
		SkipOn skipOn = m.getDeclaredAnnotation(SkipOn.class);
		RunOn runOn = m.getDeclaredAnnotation(RunOn.class);
		if (skipOn != null && isPlatformInList(currentPlatform, skipOn.platforms())) {
			return false;
		}

		if (runOn != null && !isPlatformInList(currentPlatform, runOn.platforms())) {
			return false;
		}
		return true;
	}

	private boolean isPlatformInList(Platform targetPlatform, Platform[] platforms) {
		return Arrays.stream(platforms)
				.anyMatch(platform -> platform.equals(Platform.ANY) || platform.equals(targetPlatform));
	}

	@AfterMethod
	public void output(ITestResult itr){
		System.out.println(itr.getMethod().getMethodName() + " passed");
	}

	@Test(groups = "prepare")
	@RunOn(platforms = {Platform.ANDROID_PHONE})
	public void testIncludeAndroidPhone() {}

	@Test(groups = "prepare")
	@RunOn(platforms = {Platform.ANDROID_TABLET})
	public void testIncludeAndroidTablet() {}

	@Test(groups = "prepare")
	@RunOn(platforms = {Platform.IPHONE})
	public void testIncludeIPhone() {}

	@Test(groups = "prepare")
	@RunOn(platforms = {Platform.IPAD})
	public void testIncludeIPad() {}

	@Test(groups = "prepare")
	@SkipOn(platforms = {Platform.ANDROID_PHONE}, jiraIssue = "")
	public void testExcludeAndroidPhone() {}

	@Test(groups = "prepare")
	@SkipOn(platforms = {Platform.ANDROID_TABLET}, jiraIssue = "")
	public void testExcludeAndroidTablet() {}

	@Test(groups = "prepare")
	@SkipOn(platforms = {Platform.IPHONE}, jiraIssue = "")
	public void testExcludeIPhone() {}

	@Test(groups = "prepare")
	@SkipOn(platforms = {Platform.IPAD}, jiraIssue = "")
	public void testExcludeIPad() {}

	@Test(groups = "prepare")
	@RunOn(platforms = Platform.ANY)
	public void testIncludeAll() {}

	@Test(groups = "prepare")
	@SkipOn(platforms = {Platform.ANY}, jiraIssue = "")
	public void testExcludeAll() {}
}

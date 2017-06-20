package utils.runner;

import org.hamcrest.Matcher;
import ru.yandex.qatools.allure.annotations.Step;
import utils.ui.Screenshot;
import static org.hamcrest.core.Is.is;

public class Assert {
	@Step("Verify that ''{0}''")
	public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
		boolean failure = false;
		try {
			org.junit.Assert.assertThat(reason, actual, matcher);
		} catch (AssertionError e) {
			failure = true;
			throw e;
		} finally {
			if (failure && Screenshot.isDriverReadyToTakeScreenshots()) {
				Screenshot.getScreenshotOnFail(reason.replace("\\s", ""));
			}
		}
	}

	@Step("Verify that ''{0}''")
	public static void assertThat(String reason, boolean value) {
		assertThat(reason, value, is(true));
	}
}

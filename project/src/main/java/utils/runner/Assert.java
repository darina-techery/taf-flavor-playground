package utils.runner;

import org.hamcrest.Matcher;
import ru.yandex.qatools.allure.annotations.Step;
import utils.ui.Screenshot;

import java.util.*;

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

	public static List<String> getDifference(String dataDescription, Map<String, String> actual, Map<String, String> expected) {
		List<String> differences = new ArrayList<>();
		for (String key : expected.keySet()) {
			if (!actual.containsKey(key)) {
				differences.add(String.format("%s was not found in actual %s, expected '%s'\n",
						key, dataDescription, expected.get(key)));
			} else if (!actual.get(key).equals(expected.get(key))) {
				differences.add(String.format("%s was different in %s,: expected '%s', but was '%s'\n",
						key, dataDescription, expected.get(key), actual.get(key)));
			}
		}
		return differences;
	}

	public static List<String> getDifference(String dataDescription, Set<String> actual, Set<String> expected) {
		final List<String> differences = new ArrayList<>();
		Set<String> expectedCopy = new HashSet<>();
		expectedCopy.addAll(expected);
		expectedCopy.removeAll(actual);
		if (!expectedCopy.isEmpty()) {
			expectedCopy.forEach(str -> differences.add("Actual "+dataDescription+" contains no '"+str+"'\n"));
		}

		Set<String> actualCopy = new HashSet<>();
		actualCopy.addAll(actual);
		actualCopy.removeAll(expected);
		if (!actualCopy.isEmpty()) {
			actualCopy.forEach(str -> differences.add(
					"Actual "+dataDescription+" contains extra value missing in expected: '"+str+"'\n"));
		}
		return differences;
	}
}

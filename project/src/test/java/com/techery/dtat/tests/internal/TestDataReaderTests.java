package com.techery.dtat.tests.internal;

import com.techery.dtat.data.Configuration;
import com.techery.dtat.data.structures.RunParameters;
import com.techery.dtat.data.TestData;
import com.techery.dtat.data.TestDataReader;
import com.techery.dtat.user.UserCredentials;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import static com.techery.dtat.data.Configuration.CONFIG_FILE_NAME;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public final class TestDataReaderTests {

	private static final String DEFAULT_LOCALE = "en-US";
	private static final String DEFAULT_USER_KEY = "default_user";
	private static final String EXPECTED_USERNAME = "65663904";

	@TestData(file = UserCredentials.DATA_FILE_NAME, key = DEFAULT_USER_KEY)
	UserCredentials defaultUserFromAnnotation;

	UserCredentials defaultUserFromDataReader;

	@TestData(file = Configuration.CONFIG_FILE_NAME)
	RunParameters runParametersFromAnnotation;

	RunParameters runParametersFromDataReader;

	@BeforeClass
	public void injectDataMembers() throws FileNotFoundException, IllegalAccessException {
		TestDataReader.readDataMembers(this);
	}

	@Test
	public void readTestDataByKeyWithReader() throws FileNotFoundException {
		defaultUserFromDataReader = new TestDataReader<>(UserCredentials.DATA_FILE_NAME, UserCredentials.class).readByKey(DEFAULT_USER_KEY);
		Assert.assertThat("Default user submitCredentials is read from JSON file using TestDataReader",
				defaultUserFromDataReader.getUsername(), is(EXPECTED_USERNAME));
	}

	@Test
	public void readTestDataByKeyWithAnnotation() {
		Assert.assertThat("Default user submitCredentials is read from JSON file using annotation",
				defaultUserFromAnnotation.getUsername(), is(EXPECTED_USERNAME));
	}

	@Test
	public void readAllValuesWithReader() throws FileNotFoundException {
		TestDataReader<UserCredentials> userReader = new TestDataReader<>(UserCredentials.DATA_FILE_NAME, UserCredentials.class);
		Map<String, UserCredentials> allUserData = userReader.readAllValues();
		Assert.assertThat("All entries in "+ UserCredentials.DATA_FILE_NAME+" were read", allUserData.entrySet().size(), is(4));
		Assert.assertThat("All entries in "+ UserCredentials.DATA_FILE_NAME+" were parsed as proper type",
				allUserData.get(DEFAULT_USER_KEY).getClass(), is(UserCredentials.class));
	}

	@Test
	public void testExposedFieldsAreReadWithReader() throws FileNotFoundException {
		TestDataReader<RunParameters> configReader = new TestDataReader<>(CONFIG_FILE_NAME, RunParameters.class);
		runParametersFromDataReader = configReader.read();
		Assert.assertThat("Proper value is read", runParametersFromDataReader.locale, is(DEFAULT_LOCALE));
	}

	@Test
	public void testNonExposedFieldsAreIgnoredWithReader() throws FileNotFoundException {
		TestDataReader<RunParameters> configReader = new TestDataReader<>(CONFIG_FILE_NAME, RunParameters.class);
		runParametersFromDataReader = configReader.read();
		Assert.assertThat("Non-exposed value is not read", runParametersFromDataReader.platform, nullValue());
	}

	@Test
	public void testExposedFieldsAreReadWithAnnotation() {
		Assert.assertThat("Proper value is read", runParametersFromAnnotation.locale, is(DEFAULT_LOCALE));
	}

	@Test
	public void testNonExposedFieldsAreIgnoredWithAnnotation() {
		Assert.assertThat("Non-exposed value is not read", runParametersFromAnnotation.platform, nullValue());
	}

}

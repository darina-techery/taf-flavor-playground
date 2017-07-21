package com.techery.dtat.user;

import com.techery.dtat.data.TestDataReader;
import com.techery.dtat.utils.exceptions.FailedConfigurationException;

import java.io.FileNotFoundException;

public class UserCredentialsProvider {
	private TestDataReader<UserCredentials> reader;
	public UserCredentialsProvider() {
		try {
			reader = new TestDataReader<>(UserCredentials.DATA_FILE_NAME, UserCredentials.class);
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("Cannot locate data file with user credentials", e);
		}
	}
	public UserCredentials getUserByRole(UserRole role) {
		String key = role.getKey();
		try {
			return reader.readByKey(key);
		} catch (FileNotFoundException e) {
			throw new FailedConfigurationException("Cannot read user data by key ["+key+"]", e);
		}
	}
}

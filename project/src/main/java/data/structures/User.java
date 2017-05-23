package data.structures;

public class User {
	public static final String DATA_FILE_NAME = "user_credentials.json";
	public String username;
	public String password;

	@Override
	public String toString() {
		return username + " / " + password;
	}
}

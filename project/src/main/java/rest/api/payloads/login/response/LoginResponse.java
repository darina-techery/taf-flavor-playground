package rest.api.payloads.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class LoginResponse {

	@SerializedName("token")
	@Expose
	private String token;
	@SerializedName("sso_token")
	@Expose
	private String ssoToken;
	@SerializedName("permissions")
	@Expose
	private List<Permission> permissions = new ArrayList<Permission>();
	@SerializedName("settings")
	@Expose
	private List<Setting> settings = new ArrayList<Setting>();
	@SerializedName("user")
	@Expose
	private UserProfile user;

	/**
	 * @return The token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token The token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return The ssoToken
	 */
	public String getSsoToken() {
		return ssoToken;
	}

	/**
	 * @param ssoToken The sso_token
	 */
	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	/**
	 * @return The permissions
	 */
	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions The permissions
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return The settings
	 */
	public List<Setting> getSettings() {
		return settings;
	}

	/**
	 * @param settings The settings
	 */
	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	/**
	 * @return The user
	 */
	public UserProfile getUser() {
		return user;
	}

	/**
	 * @param user The user
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

}


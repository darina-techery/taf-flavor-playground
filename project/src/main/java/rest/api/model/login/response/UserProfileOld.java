package rest.api.model.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class UserProfileOld {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("username")
	@Expose
	private String username;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("first_name")
	@Expose
	private String firstName;
	@SerializedName("last_name")
	@Expose
	private String lastName;
	@SerializedName("enroll_date")
	@Expose
	private String enrollDate;
	@SerializedName("birth_date")
	@Expose
	private Object birthDate;
	@SerializedName("location")
	@Expose
	private Object location;
	@SerializedName("subscriptions")
	@Expose
	private List<String> subscriptions = new ArrayList<String>();
	@SerializedName("avatar")
	@Expose
	private Avatar avatar;
	@SerializedName("rank_id")
	@Expose
	private Integer rankId;
	@SerializedName("country_code")
	@Expose
	private String countryCode;
	@SerializedName("rovia_bucks")
	@Expose
	private Double roviaBucks;
	@SerializedName("dream_trips_points")
	@Expose
	private Integer dreamTripsPoints;
	@SerializedName("company")
	@Expose
	private String company;
	@SerializedName("background_photo_url")
	@Expose
	private Object backgroundPhotoUrl;
	@SerializedName("terms_accepted")
	@Expose
	private Boolean termsAccepted;
	@SerializedName("locale")
	@Expose
	private String locale;

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return The firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName The first_name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return The lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName The last_name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return The enrollDate
	 */
	public String getEnrollDate() {
		return enrollDate;
	}

	/**
	 * @param enrollDate The enroll_date
	 */
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * @return The birthDate
	 */
	public Object getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate The birth_date
	 */
	public void setBirthDate(Object birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return The location
	 */
	public Object getLocation() {
		return location;
	}

	/**
	 * @param location The location
	 */
	public void setLocation(Object location) {
		this.location = location;
	}

	/**
	 * @return The subscriptions
	 */
	public List<String> getSubscriptions() {
		return subscriptions;
	}

	/**
	 * @param subscriptions The subscriptions
	 */
	public void setSubscriptions(List<String> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * @return The avatar
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar The avatar
	 */
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return The rankId
	 */
	public Integer getRankId() {
		return rankId;
	}

	/**
	 * @param rankId The rank_id
	 */
	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}

	/**
	 * @return The countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The country_code
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return The roviaBucks
	 */
	public Double getRoviaBucks() {
		return roviaBucks;
	}

	/**
	 * @param roviaBucks The rovia_bucks
	 */
	public void setRoviaBucks(Double roviaBucks) {
		this.roviaBucks = roviaBucks;
	}

	/**
	 * @return The dreamTripsPoints
	 */
	public Integer getDreamTripsPoints() {
		return dreamTripsPoints;
	}

	/**
	 * @param dreamTripsPoints The dream_trips_points
	 */
	public void setDreamTripsPoints(Integer dreamTripsPoints) {
		this.dreamTripsPoints = dreamTripsPoints;
	}

	/**
	 * @return The company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company The company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return The backgroundPhotoUrl
	 */
	public Object getBackgroundPhotoUrl() {
		return backgroundPhotoUrl;
	}

	/**
	 * @param backgroundPhotoUrl The background_photo_url
	 */
	public void setBackgroundPhotoUrl(Object backgroundPhotoUrl) {
		this.backgroundPhotoUrl = backgroundPhotoUrl;
	}

	/**
	 * @return The termsAccepted
	 */
	public Boolean getTermsAccepted() {
		return termsAccepted;
	}

	/**
	 * @param termsAccepted The terms_accepted
	 */
	public void setTermsAccepted(Boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}

	/**
	 * @return The locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale The locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

}


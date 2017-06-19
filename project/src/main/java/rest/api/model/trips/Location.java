package rest.api.model.trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Location {

	@SerializedName("lat")
	@Expose
	private String lat;
	@SerializedName("lng")
	@Expose
	private String lng;
	@SerializedName("name")
	@Expose
	private String name;

	/**
	 * @return The lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat The lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return The lng
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * @param lng The lng
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name
	 */
	public void setName(String name) {
		this.name = name;
	}

}

package rest.api.model.trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Dates {

	@SerializedName("end_on")
	@Expose
	private String endOn;
	@SerializedName("start_on")
	@Expose
	private String startOn;

	/**
	 * @return The endOn
	 */
	public String getEndOn() {
		return endOn;
	}

	/**
	 * @param endOn The end_on
	 */
	public void setEndOn(String endOn) {
		this.endOn = endOn;
	}

	/**
	 * @return The startOn
	 */
	public String getStartOn() {
		return startOn;
	}

	/**
	 * @param startOn The start_on
	 */
	public void setStartOn(String startOn) {
		this.startOn = startOn;
	}

}

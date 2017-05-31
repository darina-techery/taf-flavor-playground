package rest.api.payloads.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Setting {

	@SerializedName("val")
	@Expose
	private Boolean value;
	@SerializedName("options")
	@Expose
	private List<String> options = new ArrayList<String>();
	@SerializedName("name")
	@Expose
	private String name;

	/**
	 * @return The val
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * @param value The val
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

	/**
	 * @return The options
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * @param options The options
	 */
	public void setOptions(List<String> options) {
		this.options = options;
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
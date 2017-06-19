package rest.api.model.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Permission {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("actions")
	@Expose
	private List<Object> actions = new ArrayList<Object>();

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

	/**
	 * @return The actions
	 */
	public List<Object> getActions() {
		return actions;
	}

	/**
	 * @param actions The actions
	 */
	public void setActions(List<Object> actions) {
		this.actions = actions;
	}

}

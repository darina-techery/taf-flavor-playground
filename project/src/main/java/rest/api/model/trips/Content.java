package rest.api.model.trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Content {

	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("tags")
	@Expose
	private List<Object> tags = new ArrayList<Object>();

	/**
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language The language
	 */
	public void setLanguage(String language) {
		this.language = language;
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

	/**
	 * @return The tags
	 */
	public List<Object> getTags() {
		return tags;
	}

	/**
	 * @param tags The tags
	 */
	public void setTags(List<Object> tags) {
		this.tags = tags;
	}

}

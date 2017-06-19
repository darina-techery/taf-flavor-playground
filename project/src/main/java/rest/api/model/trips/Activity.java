package rest.api.model.trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
class Activity {

	@SerializedName("icon")
	@Expose
	private Object icon;
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("parent_id")
	@Expose
	private Integer parentId;
	@SerializedName("position")
	@Expose
	private Integer position;

	/**
	 * @return The icon
	 */
	public Object getIcon() {
		return icon;
	}

	/**
	 * @param icon The icon
	 */
	public void setIcon(Object icon) {
		this.icon = icon;
	}

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
	 * @return The parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId The parent_id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return The position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position The position
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

}
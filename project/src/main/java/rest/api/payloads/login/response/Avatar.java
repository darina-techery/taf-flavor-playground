package rest.api.payloads.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Avatar {

	@SerializedName("original")
	@Expose
	private String original;
	@SerializedName("medium")
	@Expose
	private String medium;
	@SerializedName("thumb")
	@Expose
	private String thumb;

	/**
	 * @return The original
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @param original The original
	 */
	public void setOriginal(String original) {
		this.original = original;
	}

	/**
	 * @return The medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param medium The medium
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @return The thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * @param thumb The thumb
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

}


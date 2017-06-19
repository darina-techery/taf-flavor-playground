package rest.api.model.trips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TripsListResponse {

	@SerializedName("activities")
	@Expose
	private List<Activity> activities = new ArrayList<Activity>();
	@SerializedName("available")
	@Expose
	private Boolean available;
	@SerializedName("created_at")
	@Expose
	private Object createdAt;
	@SerializedName("dates")
	@Expose
	private Dates dates;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("duration")
	@Expose
	private Integer duration;
	@SerializedName("featured")
	@Expose
	private Boolean featured;
	@SerializedName("has_multiple_dates")
	@Expose
	private Boolean hasMultipleDates;
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("images")
	@Expose
	private List<Object> images = new ArrayList<Object>();
	@SerializedName("in_bucket_list")
	@Expose
	private Boolean inBucketList;
	@SerializedName("liked")
	@Expose
	private Boolean liked;
	@SerializedName("location")
	@Expose
	private Location location;
	@SerializedName("location_id")
	@Expose
	private Integer locationId;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("platinum")
	@Expose
	private Boolean platinum;
	@SerializedName("price")
	@Expose
	private Price price;
	@SerializedName("recent")
	@Expose
	private Boolean recent;
	@SerializedName("region")
	@Expose
	private Region region;
	@SerializedName("regions")
	@Expose
	private List<Region_> regions = new ArrayList<Region_>();
	@SerializedName("rewarded")
	@Expose
	private Boolean rewarded;
	@SerializedName("rewards_limit")
	@Expose
	private Integer rewardsLimit;
	@SerializedName("rewards_rules")
	@Expose
	private Object rewardsRules;
	@SerializedName("sold_out")
	@Expose
	private Boolean soldOut;
	@SerializedName("trip_id")
	@Expose
	private String tripId;
	@SerializedName("uid")
	@Expose
	private String uid;
	@SerializedName("updated_at")
	@Expose
	private Object updatedAt;

	/**
	 * @return The activities
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities The activities
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * @return The available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available The available
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/**
	 * @return The createdAt
	 */
	public Object getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt The created_at
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return The dates
	 */
	public Dates getDates() {
		return dates;
	}

	/**
	 * @param dates The dates
	 */
	public void setDates(Dates dates) {
		this.dates = dates;
	}

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
	 * @return The duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration The duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return The featured
	 */
	public Boolean getFeatured() {
		return featured;
	}

	/**
	 * @param featured The featured
	 */
	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	/**
	 * @return The hasMultipleDates
	 */
	public Boolean getHasMultipleDates() {
		return hasMultipleDates;
	}

	/**
	 * @param hasMultipleDates The has_multiple_dates
	 */
	public void setHasMultipleDates(Boolean hasMultipleDates) {
		this.hasMultipleDates = hasMultipleDates;
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
	 * @return The images
	 */
	public List<Object> getImages() {
		return images;
	}

	/**
	 * @param images The images
	 */
	public void setImages(List<Object> images) {
		this.images = images;
	}

	/**
	 * @return The inBucketList
	 */
	public Boolean getInBucketList() {
		return inBucketList;
	}

	/**
	 * @param inBucketList The in_bucket_list
	 */
	public void setInBucketList(Boolean inBucketList) {
		this.inBucketList = inBucketList;
	}

	/**
	 * @return The liked
	 */
	public Boolean getLiked() {
		return liked;
	}

	/**
	 * @param liked The liked
	 */
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	/**
	 * @return The location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location The location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return The locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId The location_id
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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
	 * @return The platinum
	 */
	public Boolean getPlatinum() {
		return platinum;
	}

	/**
	 * @param platinum The platinum
	 */
	public void setPlatinum(Boolean platinum) {
		this.platinum = platinum;
	}

	/**
	 * @return The price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price The price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return The recent
	 */
	public Boolean getRecent() {
		return recent;
	}

	/**
	 * @param recent The recent
	 */
	public void setRecent(Boolean recent) {
		this.recent = recent;
	}

	/**
	 * @return The region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @param region The region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return The regions
	 */
	public List<Region_> getRegions() {
		return regions;
	}

	/**
	 * @param regions The regions
	 */
	public void setRegions(List<Region_> regions) {
		this.regions = regions;
	}

	/**
	 * @return The rewarded
	 */
	public Boolean getRewarded() {
		return rewarded;
	}

	/**
	 * @param rewarded The rewarded
	 */
	public void setRewarded(Boolean rewarded) {
		this.rewarded = rewarded;
	}

	/**
	 * @return The rewardsLimit
	 */
	public Integer getRewardsLimit() {
		return rewardsLimit;
	}

	/**
	 * @param rewardsLimit The rewards_limit
	 */
	public void setRewardsLimit(Integer rewardsLimit) {
		this.rewardsLimit = rewardsLimit;
	}

	/**
	 * @return The rewardsRules
	 */
	public Object getRewardsRules() {
		return rewardsRules;
	}

	/**
	 * @param rewardsRules The rewards_rules
	 */
	public void setRewardsRules(Object rewardsRules) {
		this.rewardsRules = rewardsRules;
	}

	/**
	 * @return The soldOut
	 */
	public Boolean getSoldOut() {
		return soldOut;
	}

	/**
	 * @param soldOut The sold_out
	 */
	public void setSoldOut(Boolean soldOut) {
		this.soldOut = soldOut;
	}

	/**
	 * @return The tripId
	 */
	public String getTripId() {
		return tripId;
	}

	/**
	 * @param tripId The trip_id
	 */
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	/**
	 * @return The uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid The uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return The updatedAt
	 */
	public Object getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt The updated_at
	 */
	public void setUpdatedAt(Object updatedAt) {
		this.updatedAt = updatedAt;
	}

}

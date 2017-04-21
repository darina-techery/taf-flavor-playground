package data;

import utils.annotations.AppStringKey;
import utils.exceptions.FailedConfigurationException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class AppStrings {

	@AppStringKey(android = "user_id_hint", ios = "WV Member ID")
	public String userIdHint;

	@AppStringKey(android = "user_password_hint", ios = "WV Member Password")
	public String userPasswordHint;

	@AppStringKey(android = "notifications_title", ios = "Notifications")
	public String notification;

	@AppStringKey(android = "dtl_locations_title")
	public String selectLocation;

	@AppStringKey(android = "trip_detailstrip_details")
	public String tripDetails;

	@AppStringKey(android = "filters_show_favorite")
	public String showFavoriteTripsFilter;

	@AppStringKey(android = "training_videos", ios = "Training Videos")
	public String trainingVideos;

	@AppStringKey(android = "rep_enrollment", ios = "Rep Enrollment")
	public String repEnrollment;

	@AppStringKey(android = "success_stories", ios = "Success Stories")
	public String successStories;

	@AppStringKey(android = "invite_and_share", ios = "Invite & Share")
	public String inviteShare;

	@AppStringKey(android = "add_contact")
	public String addContact;

	@AppStringKey(ios = "Continue")
	public String continueString;

	@AppStringKey(android = "title_edit_template")
	public String editTemplate;

	@AppStringKey(android = "other_travel", ios = "OTA")
	public String bookTravel;

	@AppStringKey(android = "membership", ios = "Membership")
	public String membership;

	@AppStringKey(android = "my_profile", ios = "My Profile")
	public String myProfile;

	@AppStringKey(android = "faq", ios = "FAQ")
	public String faq;

	@AppStringKey(android = "terms", ios = "Terms")
	public String terms;

	@AppStringKey(ios = "Logout")
	public String logout;

	@AppStringKey(android = "feed_title", ios = "My Activity Feed")
	public String activityFeed;

	@AppStringKey(android = "privacy", ios = "Privacy Policy")
	public String privacyPolicy;

	@AppStringKey(android = "cookie", ios = "Cookie Policy")
	public String cookiePolicy;

	@AppStringKey(android = "bucket_list", ios = "Bucket List")
	public String bucketList;

	@AppStringKey(android = "messenger", ios = "Messenger")
	public String messenger;

	@AppStringKey(android = "conversation_list_spinner_item_all_chats", ios = "All Chats")
	public String allChats;

	@AppStringKey(android = "rep_tools", ios = "Rep Tools")
	public String repTools;

	@AppStringKey(android = "send_feedback", ios = "Send Feedback")
	public String sendFeedback;

	@AppStringKey(android = "settings", ios = "Settings")
	public String settings;

	@AppStringKey(android = "terms_of_service", ios = "Terms of Service")
	public String termsOfService;

	@AppStringKey(android = "trip_images", ios = "Trip Images")
	public String tripImages;

	@AppStringKey(android = "trips", ios = "DreamTrips")
	public String dreamTrips;

	@AppStringKey(android = "book_it", ios = "Book it!")
	public String bookIt;

	@AppStringKey(ios = "Search Trips")
	public String searchTrips;

	@AppStringKey(android = "congrats", ios = "Congrats!")
	public String alertCongrats;

	@AppStringKey(android = "bucket_added", ios = "Added to your bucket list")
	public String bucketListAddPhrase;

	@AppStringKey(ios = "FEATURED TRIP")
	public String featuredTrip;

	@AppStringKey(ios = "Post")
	public String post;

	@AppStringKey(android = "trip_liked", ios = "added to your Favorites")
	public String favoriteAddPhrase;

	@AppStringKey(android = "filter_duration_header", ios = "Duration (nights)")
	public String durationNights;

	@AppStringKey(android = "filter_price_header", ios = "Price ($)")
	public String priceFilter;

	@AppStringKey(android = "filter_region_header", ios = "Region")
	public String regionFilter;

	@AppStringKey(android = "filter_theme_header", ios = "Theme")
	public String themeFilter;

	@AppStringKey(ios = "Start Date")
	public String startDateFilter;

	@AppStringKey(ios = "End Date")
	public String endDateFilter;

	@AppStringKey(android = "filter_recently_added", ios = "Show recent trips first")
	public String recentlyAddedFilter;

	@AppStringKey(android = "recently_added", ios = "Recently Added")
	public String recentlyAdded;

	@AppStringKey(ios = "Show only favorite trips")
	public String showOnlyFavoriteFilter;

	@AppStringKey(android = "filter_show_sold_out", ios = "Show sold out trips")
	public String showSoldOutFilter;

	@AppStringKey(ios = "Apply Filters")
	public String applyFilters;

	@AppStringKey(ios = "Reset All Filters")
	public String resetFilters;

	@AppStringKey(ios = "Cancel")
	public String cancel;

	@AppStringKey(ios = "Done")
	public String done;

	@AppStringKey(ios = "Type here...")
	public String typeHere;

	@AppStringKey(ios = "Feed")
	public String btnFeed;

	@AppStringKey(android = "dtl", ios = "Discover")
	public String discover;

	@AppStringKey(ios = "More")
	public String btnMore;

	@AppStringKey(android = "logout_component")
	public String logOut;

	@AppStringKey(ios = "Close")
	public String close;

	@AppStringKey(android = "bucket_locations", ios = "Locations")
	public String locations;

	@AppStringKey(android = "bucket_activities", ios = "Activities")
	public String activities;

	@AppStringKey(android = "bucket_restaurants", ios = "Dining")
	public String dining;

	@AppStringKey(android = "legal_terms", ios = "Legal Terms")
	public String legalTerms;

	@AppStringKey(android = "show_all", ios = "ALL")
	public String filter_ShowAll;

	@AppStringKey(android = "all", ios = "ALL")
	public String filter_All;

	@AppStringKey(android = "show_completed", ios = "Completed")
	public String filter_Completed;

	@AppStringKey(android = "show_to_do", ios = "To Do")
	public String filter_ToDo;

	@AppStringKey(android = "feed_close_friends", ios = "Close Friends")
	public String filter_CloseFriends;

	@AppStringKey(android = "title_activity_friends", ios = "Friends")
	public String filter_Friends;

	@AppStringKey(android = "bucket_list_create_new", ios = "Create my own item")
	public String createMyOwnList;

	@AppStringKey(android = "bucket_list_choose", ios = "Create from popular items")
	public String chooseFromPopular;

	@AppStringKey(android = "bucket_list_location_popular", ios = "Popular Items")
	public String popularBucketLists;

	@AppStringKey(android = "users_who_liked_title", ios = "social.dreamtrips.people_who_liked_screen_title")
	public String likersPageTitle;

	@AppStringKey(android = "users_who_liked_title", ios = "Tommorow")
	public String tomorrow;

	@AppStringKey(android = "working_hard", ios = "We're working hard to make DreamTrips better. Please help us to improve.")
	public String workingHard;

	@AppStringKey(android = "feedback_send_btn", ios = "Send")
	public String sendButton;

	@AppStringKey(android = "category", ios = "Category")
	public String category;

	@AppStringKey(android = "feedback_select_category", ios = "Choose Category")
	public String chooseCategory;

	@AppStringKey(android = "feedback_message_hint", ios = "Please, type your message here")
	public String pleaseTypeMessage;

	@AppStringKey(ios = "More")
	public String more;

	@AppStringKey(ios = "Help", android = "help")
	public String help;

	@AppStringKey(ios = "Videos", android = "presentations")
	public String videos;

	@AppStringKey(ios = "Documents", android = "documents")
	public String documents;


	@AppStringKey(ios = "New item added")
	public String newItemAdded;

	@AppStringKey(ios = "Show offers only", android = "dtl_show_offers_only")
	public String showOffersOnly;

	@AppStringKey(ios = "Near me", android = "dtl_near_me_caption")
	public String nearMe;

	@AppStringKey(android = "show_all")
	public String showAll;

	@AppStringKey(android = "ss_filter_show_favorites")
	public String showFavorites;

	@AppStringKey(ios = "All Members")
	public String allMembers;

	@AppStringKey(ios = "Favorites")
	public String favorites;

	@AppStringKey(ios = "View:")
	public String viewSS;

	@AppStringKey(ios = "Search Stories")
	public String searchStories;

	@AppStringKey(ios = "General", android = "general")
	public String general;

	@AppStringKey(ios = "New Message", android = "settings_new_message")
	public String newMessage;

	@AppStringKey(ios = "Friend Requests", android = "settings_friend_requests")
	public String friendRequests;

	@AppStringKey(ios = "Photo Tagging", android = "settings_photo_tagging")
	public String photoTagging;

	@AppStringKey(ios = "Distance Units", android = "settings_distance_units")
	public String distanceUnits;

	@AppStringKey(ios = "mi", android = "abbreviated_miles")
	public String mi;

	@AppStringKey(ios = "km", android = "abbreviated_kilometers")
	public String km;

	@AppStringKey(ios = "WHAT NOTIFICATIONS YOU GET", android = "what_notifications_get")
	public String whatNotificationYouGet;

	@AppStringKey(ios = "Do you want to save changes before you proceed?", android = "save_changes_before_proceed")
	public String saveChangesBeforeProceed;

	@AppStringKey(ios = "Discard", android = "discard")
	public String discard;

	@AppStringKey(ios = "Save", android = "save")
	public String save;

	@AppStringKey(ios = "Kilometers", android = "settings_kilometers")
	public String kilometers;

	@AppStringKey(ios = "Miles", android = "settings_miles")
	public String miles;

	@AppStringKey(ios = "Show distance in:", android = "show_distance_in")
	public String showDistanceIn;

	private AppStrings() {
	}

	private void addAppStrings(Map<String, String> map, boolean isAndroid) throws IllegalAccessException {
		Field[] declaredFields = this.getClass().getDeclaredFields();
		String keyByPlatform = "";
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(AppStringKey.class)) {
				AppStringKey key = field.getAnnotation(AppStringKey.class);
				keyByPlatform = isAndroid ? key.android() : key.ios();
				field.setAccessible(true);
				String value = Optional.ofNullable( map.get(keyByPlatform) ).orElse("");
				field.set(this, value);
			}
		}
	}

	public static void add(Map<String, String> map, boolean isAndroid) {
		try {
			AppStringsHolder.INSTANCE.addAppStrings(map, isAndroid);
		}
		catch (IllegalAccessException e) {
			throw new FailedConfigurationException(e, "Failed to write values into App Strings map");
		}
	}

	public static AppStrings get() {
		return AppStringsHolder.INSTANCE;
	}

	private static class AppStringsHolder {
		private static final AppStrings INSTANCE = new AppStrings();
	}
}

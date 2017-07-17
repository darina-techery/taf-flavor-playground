package rest.api.services;

import com.worldventures.dreamtrips.api.entity.model.EntityHolder;
import com.worldventures.dreamtrips.api.hashtags.model.HashtagsSearchResponse;
import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.trip.model.TripWithDetails;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DreamTripsAPI {

	@GET("/api/profile")
	Call<PrivateUserProfile> getCurrentUserProfile();

	String TRIPS_SEARCH_PATH = "/api/trips";
	@GET(TRIPS_SEARCH_PATH + "?page=1&per_page=20&sold_out=0&liked=0&recent=0")
	Call<List<TripWithoutDetails>> getBaseTripsList(@Query("start_date") String startDate,
	                                                @Query("end_date") String endDate);

	@GET("/api/{uid}")
	Call<EntityHolder<TripWithDetails>> getTripDetails(@Path("uid") String tripUid);


	@GET("/api/hashtags/search?per_page=10&type=post")
	Call<HashtagsSearchResponse> getPostsByHashTag(@Query("before") String beforeDate,
                                                   @Query("query") String hashTags);

	@DELETE("/api/social/posts/{uid}")
	Call<Void> deletePost(@Path("uid") String postUid);

	@Multipart
	@POST("/api/profile/avatar")
	Call<ResponseBody> uploadAvatar(
//	Call<ResponseBody> uploadAvatar(@Part("description") RequestBody description,
	                                @Part MultipartBody.Part file);
}

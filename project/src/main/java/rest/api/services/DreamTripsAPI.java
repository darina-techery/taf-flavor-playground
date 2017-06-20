package rest.api.services;

import com.worldventures.dreamtrips.api.profile.model.PrivateUserProfile;
import com.worldventures.dreamtrips.api.trip.model.TripWithoutDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface DreamTripsAPI {
	@GET("/api/profile")
	Call<PrivateUserProfile> getCurrentUserProfile();

	@GET("/api/trips?page=1&per_page=20&sold_out=0&liked=0&recent=0")
	Call<List<TripWithoutDetails>> getTripsList(@Query("start_date") String start_date,
	                                            @Query("end_date") String end_date);

}

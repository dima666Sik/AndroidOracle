package ua.gura.com.example.androidoracle.activities.model.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DateNagerApi {
    @GET("api/v2/publicholidays/2022/{countryCode}")
    Call<List<HolidayNetworkEntity>> getHolidays(@Path("countryCode") String countryCode);

}

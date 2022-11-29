package ua.gura.com.example.androidoracle.activities.model.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DateNagerApi {
    @GET("api/v2/publicholidays/2022/AT")
//    Call<List<HolidayNetworkEntity>> getHolidays(@Query("name") String holidayName);
    Call<List<HolidayNetworkEntity>> getHolidays(@Query("name") String holidayName);

}

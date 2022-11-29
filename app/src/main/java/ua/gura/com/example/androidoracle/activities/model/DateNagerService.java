package ua.gura.com.example.androidoracle.activities.model;

import com.annimon.stream.Stream;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ua.gura.com.example.androidoracle.activities.logger.Logger;
import ua.gura.com.example.androidoracle.activities.model.network.DateNagerApi;
import ua.gura.com.example.androidoracle.activities.model.network.HolidayNetworkEntity;

public class DateNagerService {

    private DateNagerApi dateNagerApi;
    private Logger logger;

    public DateNagerService(DateNagerApi dateNagerApi, Logger logger) {
        this.logger = logger;
        this.dateNagerApi = dateNagerApi;
    }

    public Cancellable getHolidays(String nameHoliday, Callback<List<Holiday>> callback) {
        Call<List<HolidayNetworkEntity>> listCall = dateNagerApi.getHolidays(nameHoliday);
        listCall.enqueue(new retrofit2.Callback<List<HolidayNetworkEntity>>() {
            @Override
            public void onResponse(Call<List<HolidayNetworkEntity>> call, Response<List<HolidayNetworkEntity>> response) {
                if(response.isSuccessful()){
                    List<HolidayNetworkEntity> entities = response.body();
                    callback.onResults(convertToHolidays(entities));
                }else {
                    RuntimeException exception = new RuntimeException("Error!");
                    logger.e(exception);
                    callback.onError(exception);
                }
            }

            @Override
            public void onFailure(Call<List<HolidayNetworkEntity>> call, Throwable t) {
                callback.onError(t);
                logger.e(t);
            }
        });
        return listCall::cancel;
    }

    private List<Holiday> convertToHolidays(List<HolidayNetworkEntity> entities){
        return Stream.of(entities)
                .map(Holiday::new)
                .toList();
    }
}

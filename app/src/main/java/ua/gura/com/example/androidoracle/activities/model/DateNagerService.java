package ua.gura.com.example.androidoracle.activities.model;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;
import ua.gura.com.example.androidoracle.activities.logger.Logger;
import ua.gura.com.example.androidoracle.activities.model.db.HolidayDAO;
import ua.gura.com.example.androidoracle.activities.model.db.HolidayDB;
import ua.gura.com.example.androidoracle.activities.model.network.DateNagerApi;
import ua.gura.com.example.androidoracle.activities.model.network.HolidayNetworkEntity;

public class DateNagerService {

    private ExecutorService executorService;
    private HolidayDAO holidayDAO;
    private DateNagerApi dateNagerApi;
    private Logger logger;

    public DateNagerService(DateNagerApi dateNagerApi,
                            HolidayDAO holidayDAO,
                            ExecutorService executorService,
                            Logger logger) {
        this.logger = logger;
        this.holidayDAO = holidayDAO;
        this.executorService = executorService;
        this.dateNagerApi = dateNagerApi;
    }

    public Cancellable getHolidays(String holidayCountryCode, Callback<List<Holiday>> callback) {
        Future<?> future = executorService.submit(() -> {
            try {
                List<HolidayDB> entities = holidayDAO.getHolidays(holidayCountryCode);
                List<Holiday> holidays = convertToHolidays(entities);
                callback.onResults(holidays);

                Response<List<HolidayNetworkEntity>> listResponse = dateNagerApi.getHolidays(holidayCountryCode).execute();
                if (listResponse.isSuccessful()) {
                    List<HolidayDB> newHolidays =
                            networkToDBEntity(holidayCountryCode, listResponse
                                    .body());
                    holidayDAO.updateHolidaysForCountryCode(holidayCountryCode, newHolidays);
                    callback.onResults(convertToHolidays(newHolidays));
                }else {
                    if(!holidays.isEmpty()){
                        RuntimeException exception = new RuntimeException("Something happend!");
                        logger.e(exception);
                        callback.onError(exception);
                    }
                }
            } catch (Exception e) {
                logger.e(e);
                callback.onError(e);
            }
        });
        return new FutureCancellable(future);
    }

    private List<Holiday> convertToHolidays(List<HolidayDB> entities) {
        return Stream.of(entities)
                .map(Holiday::new)
                .toList();
    }

    private List<HolidayDB> networkToDBEntity(String holidayCountryCode,
                                              List<HolidayNetworkEntity> entities) {
        return Stream.of(entities)
                .map(networkEntity -> new HolidayDB(holidayCountryCode, networkEntity))
                .toList();
    }

    static class FutureCancellable implements Cancellable {
        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }
}

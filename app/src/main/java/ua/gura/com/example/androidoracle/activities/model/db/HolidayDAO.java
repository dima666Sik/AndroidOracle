package ua.gura.com.example.androidoracle.activities.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HolidayDAO {
    @Query("SELECT * FROM holidays WHERE countryCode = :countryCode ORDER BY date")
    List<HolidayDB> getHolidays(String countryCode);

    @Query("SELECT * FROM holidays WHERE name = :name AND date = :date")
    HolidayDB getByNameAndDate(String name, String date);

    @Insert
    void insertHolidays(List<HolidayDB> holidays);

    @Query("DELETE FROM holidays WHERE countryCode = :countryCode")
    void deleteHolidays(String countryCode);

    default void updateHolidaysForCountryCode(String countryCode,
                                              List<HolidayDB> entities){
        deleteHolidays(countryCode);
        insertHolidays(entities);
    }
}

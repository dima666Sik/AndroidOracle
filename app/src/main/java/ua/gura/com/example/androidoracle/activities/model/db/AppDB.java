package ua.gura.com.example.androidoracle.activities.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {HolidayDB.class})
public abstract class AppDB extends RoomDatabase {
    public abstract HolidayDAO getHolidayDAO();
}

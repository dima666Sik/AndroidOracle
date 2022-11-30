package ua.gura.com.example.androidoracle.activities.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ua.gura.com.example.androidoracle.activities.model.network.HolidayNetworkEntity;

@Entity(tableName = "holidays")
public class HolidayDB {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="date")
    private String date;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="launchYear")
    private int launchYear;

    @ColumnInfo(name="countryCode")
    private String countryCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(int launchYear) {
        this.launchYear = launchYear;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public HolidayDB() {
    }

    public HolidayDB(String countryCode, HolidayNetworkEntity entity) {
        this.countryCode = countryCode;
        this.id = getId();
        this.date = entity.getDate();
        this.launchYear = entity.getLaunchYear();
        this.name = entity.getName();
    }
}

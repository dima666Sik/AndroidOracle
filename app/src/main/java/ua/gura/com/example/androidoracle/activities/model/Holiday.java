package ua.gura.com.example.androidoracle.activities.model;

import ua.gura.com.example.androidoracle.activities.model.db.HolidayDB;
import ua.gura.com.example.androidoracle.activities.model.network.HolidayNetworkEntity;

public class Holiday {
    private final String date;
    private final String name;
    private final int launchYear;
    private final String countryCode;

    public Holiday(String date, String name, int launchYear, String countryCode) {
        this.date = date;
        this.name = name;
        this.launchYear = launchYear;
        this.countryCode = countryCode;
    }

    public Holiday(HolidayDB entity) {
        this(entity.getDate(),
                entity.getName(),
                entity.getLaunchYear(),
                entity.getCountryCode()
        );
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", launchYear=" + launchYear +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}


package ua.gura.com.example.androidoracle.activities.model;

import ua.gura.com.example.androidoracle.activities.model.network.HolidayNetworkEntity;

public class Holiday {
    private final String date;
    private final String name;
    private final int launchYear;

    public Holiday(String date, String name, int launchYear) {
        this.date = date;
        this.name = name;
        this.launchYear = launchYear;
    }

    public Holiday(HolidayNetworkEntity entity) {
        this(entity.getDate(),
                entity.getName(),
                entity.getLaunchYear()
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
}

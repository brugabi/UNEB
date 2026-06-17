package com.example.sqlite;

import android.location.Location;

public class Waypoint {
    private long id;
    private double latitude, longitude, altitude;

    public Waypoint() {
        this.id = 0;
        this.latitude = 0;
        this.longitude = 0;
    }

    public Waypoint(Location location) {
        this.id = 0;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.altitude = location.getAltitude();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

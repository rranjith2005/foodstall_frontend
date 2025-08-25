package com.saveetha.foodstall.model;

import org.osmdroid.util.GeoPoint;

// This class holds the data for a single stall's location
public class StallLocation {
    private String stallName;
    private GeoPoint geoPoint;

    public StallLocation(String stallName, GeoPoint geoPoint) {
        this.stallName = stallName;
        this.geoPoint = geoPoint;
    }

    public String getStallName() {
        return stallName;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
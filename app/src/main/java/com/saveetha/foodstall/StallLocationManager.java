package com.saveetha.foodstall;

import com.saveetha.foodstall.model.StallLocation;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import java.util.List;

// This class will store the list of stall locations in memory
public class StallLocationManager {

    private static final ArrayList<StallLocation> stallLocations = new ArrayList<>();

    // Add a default location to start with
    static {
        stallLocations.add(new StallLocation("Aliyas Stall", new GeoPoint(9.9177, 78.1197)));
    }

    public static List<StallLocation> getStallLocations() {
        return stallLocations;
    }

    public static void addStallLocation(StallLocation newLocation) {
        // Simple logic to add or update a stall by name
        for (int i = 0; i < stallLocations.size(); i++) {
            if (stallLocations.get(i).getStallName().equalsIgnoreCase(newLocation.getStallName())) {
                stallLocations.set(i, newLocation); // Update existing stall
                return;
            }
        }
        stallLocations.add(newLocation); // Add new stall
    }
}
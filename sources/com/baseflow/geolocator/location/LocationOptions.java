package com.baseflow.geolocator.location;

import java.util.Map;

/* loaded from: classes3.dex */
public class LocationOptions {
    public static final String USE_MSL_ALTITUDE_EXTRA = "geolocator_use_mslAltitude";
    private final LocationAccuracy accuracy;
    private final long distanceFilter;
    private final long timeInterval;
    private final boolean useMSLAltitude;

    private LocationOptions(LocationAccuracy locationAccuracy, long j2, long j3, boolean z2) {
        this.accuracy = locationAccuracy;
        this.distanceFilter = j2;
        this.timeInterval = j3;
        this.useMSLAltitude = z2;
    }

    public static LocationOptions parseArguments(Map<String, Object> map) {
        if (map == null) {
            return new LocationOptions(LocationAccuracy.best, 0L, 5000L, false);
        }
        Integer num = (Integer) map.get("accuracy");
        Integer num2 = (Integer) map.get("distanceFilter");
        Integer num3 = (Integer) map.get("timeInterval");
        Boolean bool = (Boolean) map.get("useMSLAltitude");
        LocationAccuracy locationAccuracy = LocationAccuracy.best;
        if (num != null) {
            int iIntValue = num.intValue();
            if (iIntValue == 0) {
                locationAccuracy = LocationAccuracy.lowest;
            } else if (iIntValue == 1) {
                locationAccuracy = LocationAccuracy.low;
            } else if (iIntValue == 2) {
                locationAccuracy = LocationAccuracy.medium;
            } else if (iIntValue == 3) {
                locationAccuracy = LocationAccuracy.high;
            } else if (iIntValue == 5) {
                locationAccuracy = LocationAccuracy.bestForNavigation;
            }
        }
        return new LocationOptions(locationAccuracy, num2 != null ? num2.intValue() : 0L, num3 != null ? num3.intValue() : 5000L, bool != null && bool.booleanValue());
    }

    public LocationAccuracy getAccuracy() {
        return this.accuracy;
    }

    public long getDistanceFilter() {
        return this.distanceFilter;
    }

    public long getTimeInterval() {
        return this.timeInterval;
    }

    public boolean isUseMSLAltitude() {
        return this.useMSLAltitude;
    }
}

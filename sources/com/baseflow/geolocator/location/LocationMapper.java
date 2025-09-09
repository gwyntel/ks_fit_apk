package com.baseflow.geolocator.location;

import android.location.Location;
import android.os.Build;
import com.kingsmith.miot.KsProperty;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class LocationMapper {
    private static boolean isMocked(Location location) {
        return Build.VERSION.SDK_INT >= 31 ? location.isMock() : location.isFromMockProvider();
    }

    public static Map<String, Object> toHashMap(Location location) {
        if (location == null) {
            return null;
        }
        HashMap map = new HashMap();
        map.put("latitude", Double.valueOf(location.getLatitude()));
        map.put("longitude", Double.valueOf(location.getLongitude()));
        map.put(com.alipay.sdk.m.t.a.f9743k, Long.valueOf(location.getTime()));
        map.put("is_mocked", Boolean.valueOf(isMocked(location)));
        if (location.hasAltitude()) {
            map.put("altitude", Double.valueOf(location.getAltitude()));
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26 && location.hasVerticalAccuracy()) {
            map.put("altitude_accuracy", Float.valueOf(location.getVerticalAccuracyMeters()));
        }
        if (location.hasAccuracy()) {
            map.put("accuracy", Double.valueOf(location.getAccuracy()));
        }
        if (location.hasBearing()) {
            map.put("heading", Double.valueOf(location.getBearing()));
        }
        if (i2 >= 26 && location.hasBearingAccuracy()) {
            map.put("heading_accuracy", Float.valueOf(location.getBearingAccuracyDegrees()));
        }
        if (location.hasSpeed()) {
            map.put(KsProperty.Speed, Double.valueOf(location.getSpeed()));
        }
        if (i2 >= 26 && location.hasSpeedAccuracy()) {
            map.put("speed_accuracy", Double.valueOf(location.getSpeedAccuracyMetersPerSecond()));
        }
        if (location.getExtras() != null) {
            if (location.getExtras().containsKey(NmeaClient.NMEA_ALTITUDE_EXTRA)) {
                map.put("altitude", Double.valueOf(location.getExtras().getDouble(NmeaClient.NMEA_ALTITUDE_EXTRA)));
            }
            if (location.getExtras().containsKey(NmeaClient.GNSS_SATELLITE_COUNT_EXTRA)) {
                map.put("gnss_satellite_count", Double.valueOf(location.getExtras().getDouble(NmeaClient.GNSS_SATELLITE_COUNT_EXTRA)));
            }
            if (location.getExtras().containsKey(NmeaClient.GNSS_SATELLITES_USED_IN_FIX_EXTRA)) {
                map.put("gnss_satellites_used_in_fix", Double.valueOf(location.getExtras().getDouble(NmeaClient.GNSS_SATELLITES_USED_IN_FIX_EXTRA)));
            }
            if (i2 >= 34 && location.hasMslAltitude()) {
                map.put("altitude", Double.valueOf(location.getMslAltitudeMeters()));
                if (location.hasMslAltitudeAccuracy()) {
                    map.put("altitude_accuracy", Float.valueOf(location.getMslAltitudeAccuracyMeters()));
                }
            }
        }
        return map;
    }
}

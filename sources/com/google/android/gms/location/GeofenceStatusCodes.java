package com.google.android.gms.location;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.CommonStatusCodes;

/* loaded from: classes3.dex */
public final class GeofenceStatusCodes extends CommonStatusCodes {
    public static final int GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION = 1004;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_REQUEST_TOO_FREQUENT = 1005;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;

    private GeofenceStatusCodes() {
    }

    @NonNull
    public static String getStatusCodeString(int i2) {
        switch (i2) {
            case 1000:
                return "GEOFENCE_NOT_AVAILABLE";
            case 1001:
                return "GEOFENCE_TOO_MANY_GEOFENCES";
            case 1002:
                return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
            case 1003:
            default:
                return CommonStatusCodes.getStatusCodeString(i2);
            case 1004:
                return "GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION";
        }
    }

    public static int zza(int i2) {
        if (i2 == 0) {
            return i2;
        }
        if (i2 < 1000 || i2 >= 1006) {
            return 13;
        }
        return i2;
    }
}

package com.baseflow.geolocator.location;

import android.content.Context;
import android.location.LocationManager;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class g {
    public static boolean a(LocationClient locationClient, Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }
}

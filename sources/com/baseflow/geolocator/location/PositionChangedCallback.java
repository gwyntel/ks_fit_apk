package com.baseflow.geolocator.location;

import android.location.Location;

@FunctionalInterface
/* loaded from: classes3.dex */
public interface PositionChangedCallback {
    void onPositionChanged(Location location);
}

package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import com.baseflow.geolocator.errors.ErrorCallback;

/* loaded from: classes3.dex */
public interface LocationClient {
    boolean checkLocationService(Context context);

    void getLastKnownPosition(PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback);

    void isLocationServiceEnabled(LocationServiceListener locationServiceListener);

    boolean onActivityResult(int i2, int i3);

    void startPositionUpdates(Activity activity, PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback);

    void stopPositionUpdates();
}

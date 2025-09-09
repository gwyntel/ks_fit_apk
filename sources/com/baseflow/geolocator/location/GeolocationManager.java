package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.google.android.gms.common.GoogleApiAvailability;
import io.flutter.plugin.common.PluginRegistry;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class GeolocationManager implements PluginRegistry.ActivityResultListener {
    private static GeolocationManager geolocationManagerInstance;
    private final List<LocationClient> locationClients = new CopyOnWriteArrayList();

    private GeolocationManager() {
    }

    public static synchronized GeolocationManager getInstance() {
        try {
            if (geolocationManagerInstance == null) {
                geolocationManagerInstance = new GeolocationManager();
            }
        } catch (Throwable th) {
            throw th;
        }
        return geolocationManagerInstance;
    }

    private boolean isGooglePlayServicesAvailable(Context context) {
        try {
            return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == 0;
        } catch (NoClassDefFoundError unused) {
            return false;
        }
    }

    public LocationClient createLocationClient(Context context, boolean z2, @Nullable LocationOptions locationOptions) {
        return z2 ? new LocationManagerClient(context, locationOptions) : isGooglePlayServicesAvailable(context) ? new FusedLocationClient(context, locationOptions) : new LocationManagerClient(context, locationOptions);
    }

    public void getLastKnownPosition(Context context, boolean z2, PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback) {
        createLocationClient(context, z2, null).getLastKnownPosition(positionChangedCallback, errorCallback);
    }

    public void isLocationServiceEnabled(@Nullable Context context, LocationServiceListener locationServiceListener) {
        if (context == null) {
            locationServiceListener.onLocationServiceError(ErrorCodes.locationServicesDisabled);
        }
        createLocationClient(context, false, null).isLocationServiceEnabled(locationServiceListener);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        Iterator<LocationClient> it = this.locationClients.iterator();
        while (it.hasNext()) {
            if (it.next().onActivityResult(i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public void startPositionUpdates(@NonNull LocationClient locationClient, @Nullable Activity activity, @NonNull PositionChangedCallback positionChangedCallback, @NonNull ErrorCallback errorCallback) {
        this.locationClients.add(locationClient);
        locationClient.startPositionUpdates(activity, positionChangedCallback, errorCallback);
    }

    public void stopPositionUpdates(@NonNull LocationClient locationClient) {
        this.locationClients.remove(locationClient);
        locationClient.stopPositionUpdates();
    }
}

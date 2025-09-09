package com.baseflow.geolocator.location;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.location.LocationListenerCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.core.location.LocationRequestCompat;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
class LocationManagerClient implements LocationClient, LocationListenerCompat {
    private static final long TWO_MINUTES = 120000;
    public Context context;

    @Nullable
    private Location currentBestLocation;

    @Nullable
    private String currentLocationProvider;

    @Nullable
    private ErrorCallback errorCallback;
    private boolean isListening = false;
    private final LocationManager locationManager;

    @Nullable
    private final LocationOptions locationOptions;
    private final NmeaClient nmeaClient;

    @Nullable
    private PositionChangedCallback positionChangedCallback;

    /* renamed from: com.baseflow.geolocator.location.LocationManagerClient$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12224a;

        static {
            int[] iArr = new int[LocationAccuracy.values().length];
            f12224a = iArr;
            try {
                iArr[LocationAccuracy.lowest.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12224a[LocationAccuracy.low.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f12224a[LocationAccuracy.high.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f12224a[LocationAccuracy.best.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f12224a[LocationAccuracy.bestForNavigation.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f12224a[LocationAccuracy.medium.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public LocationManagerClient(@NonNull Context context, @Nullable LocationOptions locationOptions) {
        this.locationManager = (LocationManager) context.getSystemService("location");
        this.locationOptions = locationOptions;
        this.context = context;
        this.nmeaClient = new NmeaClient(context, locationOptions);
    }

    static boolean a(Location location, Location location2) {
        if (location2 == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z2 = time > TWO_MINUTES;
        boolean z3 = time < -120000;
        boolean z4 = time > 0;
        if (z2) {
            return true;
        }
        if (z3) {
            return false;
        }
        float accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z5 = accuracy > 0.0f;
        boolean z6 = accuracy < 0.0f;
        boolean z7 = accuracy > 200.0f;
        boolean zEquals = location.getProvider() != null ? location.getProvider().equals(location2.getProvider()) : false;
        if (z6) {
            return true;
        }
        if (!z4 || z5) {
            return z4 && !z7 && zEquals;
        }
        return true;
    }

    private static int accuracyToQuality(@NonNull LocationAccuracy locationAccuracy) {
        int i2 = AnonymousClass1.f12224a[locationAccuracy.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return 104;
        }
        return (i2 == 3 || i2 == 4 || i2 == 5) ? 100 : 102;
    }

    @Nullable
    private static String determineProvider(@NonNull LocationManager locationManager, @NonNull LocationAccuracy locationAccuracy) {
        List<String> providers = locationManager.getProviders(true);
        if (locationAccuracy == LocationAccuracy.lowest) {
            return "passive";
        }
        if (providers.contains("fused") && Build.VERSION.SDK_INT >= 31) {
            return "fused";
        }
        if (providers.contains("gps")) {
            return "gps";
        }
        if (providers.contains("network")) {
            return "network";
        }
        if (providers.isEmpty()) {
            return null;
        }
        return providers.get(0);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public /* synthetic */ boolean checkLocationService(Context context) {
        return g.a(this, context);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void getLastKnownPosition(PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback) {
        Iterator<String> it = this.locationManager.getProviders(true).iterator();
        Location location = null;
        while (it.hasNext()) {
            Location lastKnownLocation = this.locationManager.getLastKnownLocation(it.next());
            if (lastKnownLocation != null && a(lastKnownLocation, location)) {
                location = lastKnownLocation;
            }
        }
        positionChangedCallback.onPositionChanged(location);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void isLocationServiceEnabled(LocationServiceListener locationServiceListener) {
        if (this.locationManager == null) {
            locationServiceListener.onLocationServiceResult(false);
        } else {
            locationServiceListener.onLocationServiceResult(checkLocationService(this.context));
        }
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public boolean onActivityResult(int i2, int i3) {
        return false;
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public /* synthetic */ void onFlushComplete(int i2) {
        androidx.core.location.m.a(this, i2);
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public /* synthetic */ void onLocationChanged(List list) {
        androidx.core.location.m.b(this, list);
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    @SuppressLint({"MissingPermission"})
    public void onProviderDisabled(String str) {
        if (str.equals(this.currentLocationProvider)) {
            if (this.isListening) {
                this.locationManager.removeUpdates(this);
            }
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
            }
            this.currentLocationProvider = null;
        }
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public void onProviderEnabled(@NonNull String str) {
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    @TargetApi(28)
    public void onStatusChanged(@NonNull String str, int i2, Bundle bundle) {
        if (i2 == 2) {
            onProviderEnabled(str);
        } else if (i2 == 0) {
            onProviderDisabled(str);
        }
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    @SuppressLint({"MissingPermission"})
    public void startPositionUpdates(Activity activity, PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback) {
        long timeInterval;
        float f2;
        int iAccuracyToQuality;
        if (!checkLocationService(this.context)) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        this.positionChangedCallback = positionChangedCallback;
        this.errorCallback = errorCallback;
        LocationAccuracy locationAccuracy = LocationAccuracy.best;
        LocationOptions locationOptions = this.locationOptions;
        if (locationOptions != null) {
            float distanceFilter = locationOptions.getDistanceFilter();
            LocationAccuracy accuracy = this.locationOptions.getAccuracy();
            timeInterval = accuracy == LocationAccuracy.lowest ? Long.MAX_VALUE : this.locationOptions.getTimeInterval();
            iAccuracyToQuality = accuracyToQuality(accuracy);
            f2 = distanceFilter;
            locationAccuracy = accuracy;
        } else {
            timeInterval = 0;
            f2 = 0.0f;
            iAccuracyToQuality = 102;
        }
        String strDetermineProvider = determineProvider(this.locationManager, locationAccuracy);
        this.currentLocationProvider = strDetermineProvider;
        if (strDetermineProvider == null) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        LocationRequestCompat locationRequestCompatBuild = new LocationRequestCompat.Builder(timeInterval).setMinUpdateDistanceMeters(f2).setMinUpdateIntervalMillis(timeInterval).setQuality(iAccuracyToQuality).build();
        this.isListening = true;
        this.nmeaClient.start();
        LocationManagerCompat.requestLocationUpdates(this.locationManager, this.currentLocationProvider, locationRequestCompatBuild, this, Looper.getMainLooper());
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    @SuppressLint({"MissingPermission"})
    public void stopPositionUpdates() {
        this.isListening = false;
        this.nmeaClient.stop();
        this.locationManager.removeUpdates(this);
    }

    @Override // android.location.LocationListener
    public synchronized void onLocationChanged(Location location) {
        if (a(location, this.currentBestLocation)) {
            this.currentBestLocation = location;
            if (this.positionChangedCallback != null) {
                this.nmeaClient.enrichExtrasWithNmea(location);
                this.positionChangedCallback.onPositionChanged(this.currentBestLocation);
            }
        }
    }
}

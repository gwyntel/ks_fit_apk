package com.baseflow.geolocator.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.security.SecureRandom;
import java.util.Objects;

/* loaded from: classes3.dex */
class FusedLocationClient implements LocationClient {
    private static final String TAG = "FlutterGeolocator";
    private final int activityRequestCode = generateActivityRequestCode();
    private final Context context;

    @Nullable
    private ErrorCallback errorCallback;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationCallback locationCallback;

    @Nullable
    private final LocationOptions locationOptions;
    private final NmeaClient nmeaClient;

    @Nullable
    private PositionChangedCallback positionChangedCallback;

    /* renamed from: com.baseflow.geolocator.location.FusedLocationClient$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f12223a;

        static {
            int[] iArr = new int[LocationAccuracy.values().length];
            f12223a = iArr;
            try {
                iArr[LocationAccuracy.lowest.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f12223a[LocationAccuracy.low.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f12223a[LocationAccuracy.medium.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public FusedLocationClient(@NonNull final Context context, @Nullable final LocationOptions locationOptions) {
        this.context = context;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationOptions = locationOptions;
        this.nmeaClient = new NmeaClient(context, locationOptions);
        this.locationCallback = new LocationCallback() { // from class: com.baseflow.geolocator.location.FusedLocationClient.1
            @Override // com.google.android.gms.location.LocationCallback
            public synchronized void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                if (!locationAvailability.isLocationAvailable() && !FusedLocationClient.this.checkLocationService(context) && FusedLocationClient.this.errorCallback != null) {
                    FusedLocationClient.this.errorCallback.onError(ErrorCodes.locationServicesDisabled);
                }
            }

            @Override // com.google.android.gms.location.LocationCallback
            public synchronized void onLocationResult(@NonNull LocationResult locationResult) {
                if (FusedLocationClient.this.positionChangedCallback == null) {
                    Log.e(FusedLocationClient.TAG, "LocationCallback was called with empty locationResult or no positionChangedCallback was registered.");
                    FusedLocationClient.this.fusedLocationProviderClient.removeLocationUpdates(FusedLocationClient.this.locationCallback);
                    if (FusedLocationClient.this.errorCallback != null) {
                        FusedLocationClient.this.errorCallback.onError(ErrorCodes.errorWhileAcquiringPosition);
                    }
                    return;
                }
                Location lastLocation = locationResult.getLastLocation();
                if (lastLocation == null) {
                    return;
                }
                if (lastLocation.getExtras() == null) {
                    lastLocation.setExtras(Bundle.EMPTY);
                }
                if (locationOptions != null) {
                    lastLocation.getExtras().putBoolean(LocationOptions.USE_MSL_ALTITUDE_EXTRA, locationOptions.isUseMSLAltitude());
                }
                FusedLocationClient.this.nmeaClient.enrichExtrasWithNmea(lastLocation);
                FusedLocationClient.this.positionChangedCallback.onPositionChanged(lastLocation);
            }
        };
    }

    private static LocationRequest buildLocationRequest(@Nullable LocationOptions locationOptions) {
        if (Build.VERSION.SDK_INT < 33) {
            return buildLocationRequestDeprecated(locationOptions);
        }
        LocationRequest.Builder builder = new LocationRequest.Builder(0L);
        if (locationOptions != null) {
            builder.setPriority(toPriority(locationOptions.getAccuracy()));
            builder.setIntervalMillis(locationOptions.getTimeInterval());
            builder.setMinUpdateIntervalMillis(locationOptions.getTimeInterval());
            builder.setMinUpdateDistanceMeters(locationOptions.getDistanceFilter());
        }
        return builder.build();
    }

    private static LocationRequest buildLocationRequestDeprecated(@Nullable LocationOptions locationOptions) {
        LocationRequest locationRequestCreate = LocationRequest.create();
        if (locationOptions != null) {
            locationRequestCreate.setPriority(toPriority(locationOptions.getAccuracy()));
            locationRequestCreate.setInterval(locationOptions.getTimeInterval());
            locationRequestCreate.setFastestInterval(locationOptions.getTimeInterval() / 2);
            locationRequestCreate.setSmallestDisplacement(locationOptions.getDistanceFilter());
        }
        return locationRequestCreate;
    }

    private static LocationSettingsRequest buildLocationSettingsRequest(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        return builder.build();
    }

    private synchronized int generateActivityRequestCode() {
        return new SecureRandom().nextInt(65536);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getLastKnownPosition$1(ErrorCallback errorCallback, Exception exc) {
        Log.e("Geolocator", "Error trying to get last the last known GPS location");
        if (errorCallback != null) {
            errorCallback.onError(ErrorCodes.errorWhileAcquiringPosition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isLocationServiceEnabled$0(LocationServiceListener locationServiceListener, Task task) {
        if (!task.isSuccessful()) {
            locationServiceListener.onLocationServiceError(ErrorCodes.locationServicesDisabled);
        }
        LocationSettingsResponse locationSettingsResponse = (LocationSettingsResponse) task.getResult();
        if (locationSettingsResponse == null) {
            locationServiceListener.onLocationServiceError(ErrorCodes.locationServicesDisabled);
        } else {
            LocationSettingsStates locationSettingsStates = locationSettingsResponse.getLocationSettingsStates();
            locationServiceListener.onLocationServiceResult((locationSettingsStates != null && locationSettingsStates.isGpsUsable()) || (locationSettingsStates != null && locationSettingsStates.isNetworkLocationUsable()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPositionUpdates$2(LocationSettingsResponse locationSettingsResponse) {
        requestPositionUpdates(this.locationOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPositionUpdates$3(Activity activity, ErrorCallback errorCallback, Exception exc) {
        if (!(exc instanceof ResolvableApiException)) {
            if (((ApiException) exc).getStatusCode() == 8502) {
                requestPositionUpdates(this.locationOptions);
                return;
            } else {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
                return;
            }
        }
        if (activity == null) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        ResolvableApiException resolvableApiException = (ResolvableApiException) exc;
        if (resolvableApiException.getStatusCode() != 6) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        try {
            resolvableApiException.startResolutionForResult(activity, this.activityRequestCode);
        } catch (IntentSender.SendIntentException unused) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
        }
    }

    @SuppressLint({"MissingPermission"})
    private void requestPositionUpdates(LocationOptions locationOptions) {
        LocationRequest locationRequestBuildLocationRequest = buildLocationRequest(locationOptions);
        this.nmeaClient.start();
        this.fusedLocationProviderClient.requestLocationUpdates(locationRequestBuildLocationRequest, this.locationCallback, Looper.getMainLooper());
    }

    private static int toPriority(LocationAccuracy locationAccuracy) {
        int i2 = AnonymousClass2.f12223a[locationAccuracy.ordinal()];
        if (i2 == 1) {
            return 105;
        }
        if (i2 != 2) {
            return i2 != 3 ? 100 : 102;
        }
        return 104;
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public /* synthetic */ boolean checkLocationService(Context context) {
        return g.a(this, context);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    @SuppressLint({"MissingPermission"})
    public void getLastKnownPosition(final PositionChangedCallback positionChangedCallback, final ErrorCallback errorCallback) {
        Task<Location> lastLocation = this.fusedLocationProviderClient.getLastLocation();
        Objects.requireNonNull(positionChangedCallback);
        lastLocation.addOnSuccessListener(new OnSuccessListener() { // from class: com.baseflow.geolocator.location.e
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                positionChangedCallback.onPositionChanged((Location) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.baseflow.geolocator.location.f
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                FusedLocationClient.lambda$getLastKnownPosition$1(errorCallback, exc);
            }
        });
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void isLocationServiceEnabled(final LocationServiceListener locationServiceListener) {
        LocationServices.getSettingsClient(this.context).checkLocationSettings(new LocationSettingsRequest.Builder().build()).addOnCompleteListener(new OnCompleteListener() { // from class: com.baseflow.geolocator.location.b
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FusedLocationClient.lambda$isLocationServiceEnabled$0(locationServiceListener, task);
            }
        });
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public boolean onActivityResult(int i2, int i3) {
        if (i2 == this.activityRequestCode) {
            if (i3 == -1) {
                LocationOptions locationOptions = this.locationOptions;
                if (locationOptions == null || this.positionChangedCallback == null || this.errorCallback == null) {
                    return false;
                }
                requestPositionUpdates(locationOptions);
                return true;
            }
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
            }
        }
        return false;
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    @SuppressLint({"MissingPermission"})
    public void startPositionUpdates(@Nullable final Activity activity, @NonNull PositionChangedCallback positionChangedCallback, @NonNull final ErrorCallback errorCallback) {
        this.positionChangedCallback = positionChangedCallback;
        this.errorCallback = errorCallback;
        LocationServices.getSettingsClient(this.context).checkLocationSettings(buildLocationSettingsRequest(buildLocationRequest(this.locationOptions))).addOnSuccessListener(new OnSuccessListener() { // from class: com.baseflow.geolocator.location.c
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.f12227a.lambda$startPositionUpdates$2((LocationSettingsResponse) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.baseflow.geolocator.location.d
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                this.f12228a.lambda$startPositionUpdates$3(activity, errorCallback, exc);
            }
        });
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void stopPositionUpdates() {
        this.nmeaClient.stop();
        this.fusedLocationProviderClient.removeLocationUpdates(this.locationCallback);
    }
}

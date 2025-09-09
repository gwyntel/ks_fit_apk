package com.baseflow.geolocator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import com.baseflow.geolocator.location.FlutterLocationServiceListener;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationAccuracyManager;
import com.baseflow.geolocator.location.LocationAccuracyStatus;
import com.baseflow.geolocator.location.LocationClient;
import com.baseflow.geolocator.location.LocationMapper;
import com.baseflow.geolocator.location.LocationOptions;
import com.baseflow.geolocator.location.PositionChangedCallback;
import com.baseflow.geolocator.permission.LocationPermission;
import com.baseflow.geolocator.permission.PermissionManager;
import com.baseflow.geolocator.permission.PermissionResultCallback;
import com.baseflow.geolocator.utils.Utils;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {
    private static final String TAG = "MethodCallHandlerImpl";

    /* renamed from: a, reason: collision with root package name */
    final Map f12199a = new HashMap();

    @Nullable
    private Activity activity;

    @Nullable
    private MethodChannel channel;

    @Nullable
    private Context context;
    private final GeolocationManager geolocationManager;
    private final LocationAccuracyManager locationAccuracyManager;
    private final PermissionManager permissionManager;

    MethodCallHandlerImpl(PermissionManager permissionManager, GeolocationManager geolocationManager, LocationAccuracyManager locationAccuracyManager) {
        this.permissionManager = permissionManager;
        this.geolocationManager = geolocationManager;
        this.locationAccuracyManager = locationAccuracyManager;
    }

    private void getLocationAccuracy(final MethodChannel.Result result, Context context) {
        LocationAccuracyStatus locationAccuracy = this.locationAccuracyManager.getLocationAccuracy(context, new ErrorCallback() { // from class: com.baseflow.geolocator.f
            @Override // com.baseflow.geolocator.errors.ErrorCallback
            public final void onError(ErrorCodes errorCodes) {
                MethodCallHandlerImpl.lambda$getLocationAccuracy$2(result, errorCodes);
            }
        });
        if (locationAccuracy != null) {
            result.success(Integer.valueOf(locationAccuracy.ordinal()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getLocationAccuracy$2(MethodChannel.Result result, ErrorCodes errorCodes) {
        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetCurrentPosition$5(boolean[] zArr, LocationClient locationClient, String str, MethodChannel.Result result, Location location) {
        if (zArr[0]) {
            return;
        }
        zArr[0] = true;
        this.geolocationManager.stopPositionUpdates(locationClient);
        this.f12199a.remove(str);
        result.success(LocationMapper.toHashMap(location));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetCurrentPosition$6(boolean[] zArr, LocationClient locationClient, String str, MethodChannel.Result result, ErrorCodes errorCodes) {
        if (zArr[0]) {
            return;
        }
        zArr[0] = true;
        this.geolocationManager.stopPositionUpdates(locationClient);
        this.f12199a.remove(str);
        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onGetLastKnownPosition$3(MethodChannel.Result result, Location location) {
        result.success(LocationMapper.toHashMap(location));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onGetLastKnownPosition$4(MethodChannel.Result result, ErrorCodes errorCodes) {
        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRequestPermission$0(MethodChannel.Result result, LocationPermission locationPermission) {
        result.success(Integer.valueOf(locationPermission.toInt()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRequestPermission$1(MethodChannel.Result result, ErrorCodes errorCodes) {
        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    private void onCancelGetCurrentPosition(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) ((Map) methodCall.arguments).get("requestId");
        LocationClient locationClient = (LocationClient) this.f12199a.get(str);
        if (locationClient != null) {
            locationClient.stopPositionUpdates();
        }
        this.f12199a.remove(str);
        result.success(null);
    }

    private void onCheckPermission(MethodChannel.Result result) {
        try {
            result.success(Integer.valueOf(this.permissionManager.checkPermissionStatus(this.context).toInt()));
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes.toString(), errorCodes.toDescription(), null);
        }
    }

    private void onGetCurrentPosition(MethodCall methodCall, final MethodChannel.Result result) {
        try {
            if (!this.permissionManager.hasPermission(this.context)) {
                ErrorCodes errorCodes = ErrorCodes.permissionDenied;
                result.error(errorCodes.toString(), errorCodes.toDescription(), null);
                return;
            }
            Map map = (Map) methodCall.arguments;
            boolean zBooleanValue = map.get("forceLocationManager") != null ? ((Boolean) map.get("forceLocationManager")).booleanValue() : false;
            LocationOptions arguments = LocationOptions.parseArguments(map);
            final String str = (String) map.get("requestId");
            final boolean[] zArr = {false};
            final LocationClient locationClientCreateLocationClient = this.geolocationManager.createLocationClient(this.context, zBooleanValue, arguments);
            this.f12199a.put(str, locationClientCreateLocationClient);
            this.geolocationManager.startPositionUpdates(locationClientCreateLocationClient, this.activity, new PositionChangedCallback() { // from class: com.baseflow.geolocator.d
                @Override // com.baseflow.geolocator.location.PositionChangedCallback
                public final void onPositionChanged(Location location) {
                    this.f12202a.lambda$onGetCurrentPosition$5(zArr, locationClientCreateLocationClient, str, result, location);
                }
            }, new ErrorCallback() { // from class: com.baseflow.geolocator.e
                @Override // com.baseflow.geolocator.errors.ErrorCallback
                public final void onError(ErrorCodes errorCodes2) {
                    this.f12207a.lambda$onGetCurrentPosition$6(zArr, locationClientCreateLocationClient, str, result, errorCodes2);
                }
            });
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes2 = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes2.toString(), errorCodes2.toDescription(), null);
        }
    }

    private void onGetLastKnownPosition(MethodCall methodCall, final MethodChannel.Result result) {
        try {
            if (this.permissionManager.hasPermission(this.context)) {
                Boolean bool = (Boolean) methodCall.argument("forceLocationManager");
                this.geolocationManager.getLastKnownPosition(this.context, bool != null && bool.booleanValue(), new PositionChangedCallback() { // from class: com.baseflow.geolocator.i
                    @Override // com.baseflow.geolocator.location.PositionChangedCallback
                    public final void onPositionChanged(Location location) {
                        MethodCallHandlerImpl.lambda$onGetLastKnownPosition$3(result, location);
                    }
                }, new ErrorCallback() { // from class: com.baseflow.geolocator.j
                    @Override // com.baseflow.geolocator.errors.ErrorCallback
                    public final void onError(ErrorCodes errorCodes) {
                        MethodCallHandlerImpl.lambda$onGetLastKnownPosition$4(result, errorCodes);
                    }
                });
            } else {
                ErrorCodes errorCodes = ErrorCodes.permissionDenied;
                result.error(errorCodes.toString(), errorCodes.toDescription(), null);
            }
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes2 = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes2.toString(), errorCodes2.toDescription(), null);
        }
    }

    private void onIsLocationServiceEnabled(MethodChannel.Result result) {
        this.geolocationManager.isLocationServiceEnabled(this.context, new FlutterLocationServiceListener(result));
    }

    private void onRequestPermission(final MethodChannel.Result result) {
        try {
            this.permissionManager.requestPermission(this.activity, new PermissionResultCallback() { // from class: com.baseflow.geolocator.g
                @Override // com.baseflow.geolocator.permission.PermissionResultCallback
                public final void onResult(LocationPermission locationPermission) {
                    MethodCallHandlerImpl.lambda$onRequestPermission$0(result, locationPermission);
                }
            }, new ErrorCallback() { // from class: com.baseflow.geolocator.h
                @Override // com.baseflow.geolocator.errors.ErrorCallback
                public final void onError(ErrorCodes errorCodes) {
                    MethodCallHandlerImpl.lambda$onRequestPermission$1(result, errorCodes);
                }
            });
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes.toString(), errorCodes.toDescription(), null);
        }
    }

    void h(Activity activity) {
        this.activity = activity;
    }

    void i(Context context, BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.w(TAG, "Setting a method call handler before the last was disposed.");
            j();
        }
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "flutter.baseflow.com/geolocator_android");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context = context;
    }

    void j() {
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Log.d(TAG, "Tried to stop listening when no MethodChannel had been initialized.");
        } else {
            methodChannel.setMethodCallHandler(null);
            this.channel = null;
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "getCurrentPosition":
                onGetCurrentPosition(methodCall, result);
                break;
            case "getLastKnownPosition":
                onGetLastKnownPosition(methodCall, result);
                break;
            case "openLocationSettings":
                result.success(Boolean.valueOf(Utils.openLocationSettings(this.context)));
                break;
            case "openAppSettings":
                result.success(Boolean.valueOf(Utils.openAppSettings(this.context)));
                break;
            case "isLocationServiceEnabled":
                onIsLocationServiceEnabled(result);
                break;
            case "checkPermission":
                onCheckPermission(result);
                break;
            case "requestPermission":
                onRequestPermission(result);
                break;
            case "getLocationAccuracy":
                getLocationAccuracy(result, this.context);
                break;
            case "cancelGetCurrentPosition":
                onCancelGetCurrentPosition(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}

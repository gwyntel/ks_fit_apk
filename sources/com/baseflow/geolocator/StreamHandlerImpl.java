package com.baseflow.geolocator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import androidx.annotation.Nullable;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import com.baseflow.geolocator.location.ForegroundNotificationOptions;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationClient;
import com.baseflow.geolocator.location.LocationMapper;
import com.baseflow.geolocator.location.LocationOptions;
import com.baseflow.geolocator.location.PositionChangedCallback;
import com.baseflow.geolocator.permission.PermissionManager;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import java.util.Map;

/* loaded from: classes3.dex */
class StreamHandlerImpl implements EventChannel.StreamHandler {
    private static final String TAG = "FlutterGeolocator";

    @Nullable
    private Activity activity;

    @Nullable
    private EventChannel channel;

    @Nullable
    private Context context;

    @Nullable
    private GeolocatorLocationService foregroundLocationService;

    @Nullable
    private GeolocationManager geolocationManager;

    @Nullable
    private LocationClient locationClient;
    private final PermissionManager permissionManager;

    public StreamHandlerImpl(PermissionManager permissionManager, GeolocationManager geolocationManager) {
        this.permissionManager = permissionManager;
        this.geolocationManager = geolocationManager;
    }

    private void disposeListeners(boolean z2) {
        GeolocationManager geolocationManager;
        Log.e(TAG, "Geolocator position updates stopped");
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService == null || !geolocatorLocationService.canStopLocationService(z2)) {
            Log.e(TAG, "There is still another flutter engine connected, not stopping location service");
        } else {
            this.foregroundLocationService.stopLocationService();
            this.foregroundLocationService.disableBackgroundMode();
        }
        LocationClient locationClient = this.locationClient;
        if (locationClient == null || (geolocationManager = this.geolocationManager) == null) {
            return;
        }
        geolocationManager.stopPositionUpdates(locationClient);
        this.locationClient = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onListen$0(EventChannel.EventSink eventSink, Location location) {
        eventSink.success(LocationMapper.toHashMap(location));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onListen$1(EventChannel.EventSink eventSink, ErrorCodes errorCodes) {
        eventSink.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    void c(Context context, BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.w(TAG, "Setting a event call handler before the last was disposed.");
            d();
        }
        EventChannel eventChannel = new EventChannel(binaryMessenger, "flutter.baseflow.com/geolocator_updates_android");
        this.channel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.context = context;
    }

    void d() {
        if (this.channel == null) {
            Log.d(TAG, "Tried to stop listening when no MethodChannel had been initialized.");
            return;
        }
        disposeListeners(false);
        this.channel.setStreamHandler(null);
        this.channel = null;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        disposeListeners(true);
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, final EventChannel.EventSink eventSink) {
        try {
            if (!this.permissionManager.hasPermission(this.context)) {
                ErrorCodes errorCodes = ErrorCodes.permissionDenied;
                eventSink.error(errorCodes.toString(), errorCodes.toDescription(), null);
                return;
            }
            if (this.foregroundLocationService == null) {
                Log.e(TAG, "Location background service has not started correctly");
                return;
            }
            Map map = (Map) obj;
            boolean zBooleanValue = (map == null || map.get("forceLocationManager") == null) ? false : ((Boolean) map.get("forceLocationManager")).booleanValue();
            LocationOptions arguments = LocationOptions.parseArguments(map);
            ForegroundNotificationOptions arguments2 = map != null ? ForegroundNotificationOptions.parseArguments((Map) map.get("foregroundNotificationConfig")) : null;
            if (arguments2 != null) {
                Log.e(TAG, "Geolocator position updates started using Android foreground service");
                this.foregroundLocationService.startLocationService(zBooleanValue, arguments, eventSink);
                this.foregroundLocationService.enableBackgroundMode(arguments2);
            } else {
                Log.e(TAG, "Geolocator position updates started");
                LocationClient locationClientCreateLocationClient = this.geolocationManager.createLocationClient(this.context, Boolean.TRUE.equals(Boolean.valueOf(zBooleanValue)), arguments);
                this.locationClient = locationClientCreateLocationClient;
                this.geolocationManager.startPositionUpdates(locationClientCreateLocationClient, this.activity, new PositionChangedCallback() { // from class: com.baseflow.geolocator.k
                    @Override // com.baseflow.geolocator.location.PositionChangedCallback
                    public final void onPositionChanged(Location location) {
                        StreamHandlerImpl.lambda$onListen$0(eventSink, location);
                    }
                }, new ErrorCallback() { // from class: com.baseflow.geolocator.l
                    @Override // com.baseflow.geolocator.errors.ErrorCallback
                    public final void onError(ErrorCodes errorCodes2) {
                        StreamHandlerImpl.lambda$onListen$1(eventSink, errorCodes2);
                    }
                });
            }
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes2 = ErrorCodes.permissionDefinitionsNotFound;
            eventSink.error(errorCodes2.toString(), errorCodes2.toDescription(), null);
        }
    }

    public void setActivity(@Nullable Activity activity) {
        if (activity == null && this.locationClient != null && this.channel != null) {
            d();
        }
        this.activity = activity;
    }

    public void setForegroundLocationService(@Nullable GeolocatorLocationService geolocatorLocationService) {
        this.foregroundLocationService = geolocatorLocationService;
    }
}

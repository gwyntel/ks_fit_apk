package com.baseflow.geolocator;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baseflow.geolocator.GeolocatorLocationService;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationAccuracyManager;
import com.baseflow.geolocator.permission.PermissionManager;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

/* loaded from: classes3.dex */
public class GeolocatorPlugin implements FlutterPlugin, ActivityAware {
    private static final String TAG = "FlutterGeolocator";

    @Nullable
    private GeolocatorLocationService foregroundLocationService;

    @Nullable
    private LocationServiceHandlerImpl locationServiceHandler;

    @Nullable
    private MethodCallHandlerImpl methodCallHandler;

    @Nullable
    private ActivityPluginBinding pluginBinding;

    @Nullable
    private StreamHandlerImpl streamHandler;
    private final ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.baseflow.geolocator.GeolocatorPlugin.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(GeolocatorPlugin.TAG, "Geolocator foreground service connected");
            if (iBinder instanceof GeolocatorLocationService.LocalBinder) {
                GeolocatorPlugin.this.initialize(((GeolocatorLocationService.LocalBinder) iBinder).getLocationService());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(GeolocatorPlugin.TAG, "Geolocator foreground service disconnected");
            if (GeolocatorPlugin.this.foregroundLocationService != null) {
                GeolocatorPlugin.this.foregroundLocationService.setActivity(null);
                GeolocatorPlugin.this.foregroundLocationService = null;
            }
        }
    };
    private final PermissionManager permissionManager = PermissionManager.getInstance();
    private final GeolocationManager geolocationManager = GeolocationManager.getInstance();
    private final LocationAccuracyManager locationAccuracyManager = LocationAccuracyManager.getInstance();

    private void bindForegroundService(Context context) {
        context.bindService(new Intent(context, (Class<?>) GeolocatorLocationService.class), this.serviceConnection, 1);
    }

    private void deregisterListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this.geolocationManager);
            this.pluginBinding.removeRequestPermissionsResultListener(this.permissionManager);
        }
    }

    private void dispose() {
        Log.d(TAG, "Disposing Geolocator services");
        MethodCallHandlerImpl methodCallHandlerImpl = this.methodCallHandler;
        if (methodCallHandlerImpl != null) {
            methodCallHandlerImpl.j();
            this.methodCallHandler.h(null);
            this.methodCallHandler = null;
        }
        StreamHandlerImpl streamHandlerImpl = this.streamHandler;
        if (streamHandlerImpl != null) {
            streamHandlerImpl.d();
            this.streamHandler.setForegroundLocationService(null);
            this.streamHandler = null;
        }
        LocationServiceHandlerImpl locationServiceHandlerImpl = this.locationServiceHandler;
        if (locationServiceHandlerImpl != null) {
            locationServiceHandlerImpl.a(null);
            this.locationServiceHandler.c();
            this.locationServiceHandler = null;
        }
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService != null) {
            geolocatorLocationService.setActivity(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize(GeolocatorLocationService geolocatorLocationService) {
        Log.d(TAG, "Initializing Geolocator services");
        this.foregroundLocationService = geolocatorLocationService;
        geolocatorLocationService.setGeolocationManager(this.geolocationManager);
        this.foregroundLocationService.flutterEngineConnected();
        StreamHandlerImpl streamHandlerImpl = this.streamHandler;
        if (streamHandlerImpl != null) {
            streamHandlerImpl.setForegroundLocationService(geolocatorLocationService);
        }
    }

    private void registerListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.addActivityResultListener(this.geolocationManager);
            this.pluginBinding.addRequestPermissionsResultListener(this.permissionManager);
        }
    }

    private void unbindForegroundService(Context context) {
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService != null) {
            geolocatorLocationService.flutterEngineDisconnected();
        }
        context.unbindService(this.serviceConnection);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        Log.d(TAG, "Attaching Geolocator to activity");
        this.pluginBinding = activityPluginBinding;
        registerListeners();
        MethodCallHandlerImpl methodCallHandlerImpl = this.methodCallHandler;
        if (methodCallHandlerImpl != null) {
            methodCallHandlerImpl.h(activityPluginBinding.getActivity());
        }
        StreamHandlerImpl streamHandlerImpl = this.streamHandler;
        if (streamHandlerImpl != null) {
            streamHandlerImpl.setActivity(activityPluginBinding.getActivity());
        }
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService != null) {
            geolocatorLocationService.setActivity(this.pluginBinding.getActivity());
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodCallHandlerImpl methodCallHandlerImpl = new MethodCallHandlerImpl(this.permissionManager, this.geolocationManager, this.locationAccuracyManager);
        this.methodCallHandler = methodCallHandlerImpl;
        methodCallHandlerImpl.i(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
        StreamHandlerImpl streamHandlerImpl = new StreamHandlerImpl(this.permissionManager, this.geolocationManager);
        this.streamHandler = streamHandlerImpl;
        streamHandlerImpl.c(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
        LocationServiceHandlerImpl locationServiceHandlerImpl = new LocationServiceHandlerImpl();
        this.locationServiceHandler = locationServiceHandlerImpl;
        locationServiceHandlerImpl.a(flutterPluginBinding.getApplicationContext());
        this.locationServiceHandler.b(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
        bindForegroundService(flutterPluginBinding.getApplicationContext());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        Log.d(TAG, "Detaching Geolocator from activity");
        deregisterListeners();
        MethodCallHandlerImpl methodCallHandlerImpl = this.methodCallHandler;
        if (methodCallHandlerImpl != null) {
            methodCallHandlerImpl.h(null);
        }
        StreamHandlerImpl streamHandlerImpl = this.streamHandler;
        if (streamHandlerImpl != null) {
            streamHandlerImpl.setActivity(null);
        }
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService != null) {
            geolocatorLocationService.setActivity(null);
        }
        if (this.pluginBinding != null) {
            this.pluginBinding = null;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        unbindForegroundService(flutterPluginBinding.getApplicationContext());
        dispose();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }
}

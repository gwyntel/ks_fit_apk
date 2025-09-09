package com.baseflow.permissionhandler;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes3.dex */
public final class PermissionHandlerPlugin implements FlutterPlugin, ActivityAware {

    @Nullable
    private MethodCallHandlerImpl methodCallHandler;
    private MethodChannel methodChannel;
    private PermissionManager permissionManager;

    @Nullable
    private ActivityPluginBinding pluginBinding;

    private void deregisterListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this.permissionManager);
            this.pluginBinding.removeRequestPermissionsResultListener(this.permissionManager);
        }
    }

    private void registerListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.addActivityResultListener(this.permissionManager);
            this.pluginBinding.addRequestPermissionsResultListener(this.permissionManager);
        }
    }

    private void startListening(Context context, BinaryMessenger binaryMessenger) {
        this.methodChannel = new MethodChannel(binaryMessenger, "flutter.baseflow.com/permissions/methods");
        MethodCallHandlerImpl methodCallHandlerImpl = new MethodCallHandlerImpl(context, new AppSettingsManager(), this.permissionManager, new ServiceManager());
        this.methodCallHandler = methodCallHandlerImpl;
        this.methodChannel.setMethodCallHandler(methodCallHandlerImpl);
    }

    private void startListeningToActivity(Activity activity) {
        PermissionManager permissionManager = this.permissionManager;
        if (permissionManager != null) {
            permissionManager.setActivity(activity);
        }
    }

    private void stopListening() {
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
        this.methodCallHandler = null;
    }

    private void stopListeningToActivity() {
        PermissionManager permissionManager = this.permissionManager;
        if (permissionManager != null) {
            permissionManager.setActivity(null);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        startListeningToActivity(activityPluginBinding.getActivity());
        this.pluginBinding = activityPluginBinding;
        registerListeners();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.permissionManager = new PermissionManager(flutterPluginBinding.getApplicationContext());
        startListening(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        stopListeningToActivity();
        deregisterListeners();
        this.pluginBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        stopListening();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }
}

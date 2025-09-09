package com.kingsmith.ksfit.flutter_launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/* loaded from: classes4.dex */
public class FlutterLauncherPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {
    private int LOCATION_SERVICE_REQUEST_CODE = 1101;
    private Activity activity;
    private ActivityPluginBinding binding;
    private MethodChannel channel;
    private Context context;
    private MethodChannel.Result mCurrentResult;

    private void installed(MethodCall methodCall, MethodChannel.Result result) {
        try {
            String str = (String) methodCall.argument("packageName");
            Context context = this.context;
            if (context != null) {
                result.success(Integer.valueOf(isPackageInstalled(str, context.getPackageManager()) ? 1 : 0));
            } else {
                result.error("INVALID_CONTEXT", "No application context found", null);
            }
        } catch (Exception e2) {
            result.error("ERROR_INSTALLED", e2.getMessage(), e2);
        }
    }

    private boolean isPackageInstalled(String str, PackageManager packageManager) throws PackageManager.NameNotFoundException {
        try {
            packageManager.getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        Log.d("FlutterLauncherPlugin", "onActivityResult requestCode: " + i2 + " resultCode: " + i3);
        if (i2 != this.LOCATION_SERVICE_REQUEST_CODE) {
            return false;
        }
        Log.d("FlutterLauncherPlugin", "LOCATION_SERVICE_REQUEST_CODE back ");
        this.mCurrentResult.success(Boolean.TRUE);
        return true;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        this.binding = activityPluginBinding;
        this.activity = activityPluginBinding.getActivity();
        activityPluginBinding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.context = flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_launcher");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        ActivityPluginBinding activityPluginBinding = this.binding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this);
            this.binding = null;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        this.mCurrentResult = result;
        if (methodCall.method.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
            result.success("Android " + Build.VERSION.RELEASE);
            return;
        }
        if (methodCall.method.equals("launch")) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            Log.d("FlutterLauncherPlugin", "intent = $intent");
            intent.setData(Uri.parse("market://details?id=" + this.context.getPackageName()));
            if (intent.resolveActivity(this.context.getPackageManager()) != null) {
                this.context.startActivity(intent);
                return;
            }
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + this.context.getPackageName()));
            if (intent.resolveActivity(this.context.getPackageManager()) == null) {
                this.context.startActivity(intent);
                return;
            }
            return;
        }
        if (methodCall.method.equals("launch_package_manager_page")) {
            Intent intent2 = new Intent();
            intent2.addFlags(268435456);
            intent2.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", this.context.getPackageName(), null));
            this.context.startActivity(intent2);
            return;
        }
        if (methodCall.method.equals("launch_location_source_settings")) {
            Intent intent3 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            intent3.addFlags(268435456);
            this.activity.startActivityForResult(intent3, this.LOCATION_SERVICE_REQUEST_CODE);
            return;
        }
        if (!methodCall.method.equals("goCallWithNumber")) {
            if (methodCall.method.equals("packageInstalled")) {
                installed(methodCall, result);
                return;
            } else {
                result.notImplemented();
                return;
            }
        }
        String str = (String) methodCall.argument("phoneNumber");
        Log.d("FlutterLauncherPlugin", " goCallWithNumber phoneNumber: " + str);
        Intent intent4 = new Intent();
        intent4.addFlags(268435456);
        intent4.setAction("android.intent.action.DIAL");
        intent4.setData(Uri.parse("tel:" + str));
        this.context.startActivity(intent4);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }
}

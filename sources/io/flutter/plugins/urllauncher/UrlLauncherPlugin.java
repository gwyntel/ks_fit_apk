package io.flutter.plugins.urllauncher;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

/* loaded from: classes4.dex */
public final class UrlLauncherPlugin implements FlutterPlugin, ActivityAware {
    private static final String TAG = "UrlLauncherPlugin";

    @Nullable
    private UrlLauncher urlLauncher;

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        UrlLauncher urlLauncher = this.urlLauncher;
        if (urlLauncher == null) {
            Log.wtf(TAG, "urlLauncher was never set.");
        } else {
            urlLauncher.setActivity(activityPluginBinding.getActivity());
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.urlLauncher = new UrlLauncher(flutterPluginBinding.getApplicationContext());
        f.g(flutterPluginBinding.getBinaryMessenger(), this.urlLauncher);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        UrlLauncher urlLauncher = this.urlLauncher;
        if (urlLauncher == null) {
            Log.wtf(TAG, "urlLauncher was never set.");
        } else {
            urlLauncher.setActivity(null);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        if (this.urlLauncher == null) {
            Log.wtf(TAG, "Already detached from the engine.");
        } else {
            f.g(flutterPluginBinding.getBinaryMessenger(), null);
            this.urlLauncher = null;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }
}

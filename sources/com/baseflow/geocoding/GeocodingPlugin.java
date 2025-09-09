package com.baseflow.geocoding;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

/* loaded from: classes3.dex */
public final class GeocodingPlugin implements FlutterPlugin {
    private static final String TAG = "GeocodingPlugin";

    @Nullable
    private Geocoding geocoding;

    @Nullable
    private MethodCallHandlerImpl methodCallHandler;

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Geocoding geocoding = new Geocoding(flutterPluginBinding.getApplicationContext());
        this.geocoding = geocoding;
        MethodCallHandlerImpl methodCallHandlerImpl = new MethodCallHandlerImpl(geocoding);
        this.methodCallHandler = methodCallHandlerImpl;
        methodCallHandlerImpl.a(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodCallHandlerImpl methodCallHandlerImpl = this.methodCallHandler;
        if (methodCallHandlerImpl == null) {
            Log.wtf(TAG, "Already detached from the engine.");
            return;
        }
        methodCallHandlerImpl.b();
        this.methodCallHandler = null;
        this.geocoding = null;
    }
}

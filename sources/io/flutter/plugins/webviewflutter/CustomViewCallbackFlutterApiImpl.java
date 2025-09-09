package io.flutter.plugins.webviewflutter;

import android.webkit.WebChromeClient;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;

/* loaded from: classes4.dex */
public class CustomViewCallbackFlutterApiImpl {
    private GeneratedAndroidWebView.CustomViewCallbackFlutterApi api;
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public CustomViewCallbackFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.api = new GeneratedAndroidWebView.CustomViewCallbackFlutterApi(binaryMessenger);
    }

    public void create(@NonNull WebChromeClient.CustomViewCallback customViewCallback, @NonNull GeneratedAndroidWebView.CustomViewCallbackFlutterApi.Reply<Void> reply) {
        if (this.instanceManager.containsInstance(customViewCallback)) {
            return;
        }
        this.api.create(Long.valueOf(this.instanceManager.addHostCreatedInstance(customViewCallback)), reply);
    }

    @VisibleForTesting
    void setApi(@NonNull GeneratedAndroidWebView.CustomViewCallbackFlutterApi customViewCallbackFlutterApi) {
        this.api = customViewCallbackFlutterApi;
    }
}

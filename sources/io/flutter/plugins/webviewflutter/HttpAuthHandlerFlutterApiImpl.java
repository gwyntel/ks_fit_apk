package io.flutter.plugins.webviewflutter;

import android.webkit.HttpAuthHandler;
import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;

/* loaded from: classes4.dex */
public class HttpAuthHandlerFlutterApiImpl {
    private final GeneratedAndroidWebView.HttpAuthHandlerFlutterApi api;
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public HttpAuthHandlerFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.api = new GeneratedAndroidWebView.HttpAuthHandlerFlutterApi(binaryMessenger);
    }

    public void create(@NonNull HttpAuthHandler httpAuthHandler, @NonNull GeneratedAndroidWebView.HttpAuthHandlerFlutterApi.Reply<Void> reply) {
        if (this.instanceManager.containsInstance(httpAuthHandler)) {
            return;
        }
        this.api.create(Long.valueOf(this.instanceManager.addHostCreatedInstance(httpAuthHandler)), reply);
    }
}

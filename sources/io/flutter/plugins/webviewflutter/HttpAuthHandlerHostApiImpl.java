package io.flutter.plugins.webviewflutter;

import android.webkit.HttpAuthHandler;
import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class HttpAuthHandlerHostApiImpl implements GeneratedAndroidWebView.HttpAuthHandlerHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public HttpAuthHandlerHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
    }

    private HttpAuthHandler getHttpAuthHandlerInstance(@NonNull Long l2) {
        HttpAuthHandler httpAuthHandler = (HttpAuthHandler) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(httpAuthHandler);
        return httpAuthHandler;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.HttpAuthHandlerHostApi
    public void cancel(@NonNull Long l2) {
        getHttpAuthHandlerInstance(l2).cancel();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.HttpAuthHandlerHostApi
    public void proceed(@NonNull Long l2, @NonNull String str, @NonNull String str2) {
        getHttpAuthHandlerInstance(l2).proceed(str, str2);
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.HttpAuthHandlerHostApi
    @NonNull
    public Boolean useHttpAuthUsernamePassword(@NonNull Long l2) {
        return Boolean.valueOf(getHttpAuthHandlerInstance(l2).useHttpAuthUsernamePassword());
    }
}

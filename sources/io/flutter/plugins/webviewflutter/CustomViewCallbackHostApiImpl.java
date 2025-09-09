package io.flutter.plugins.webviewflutter;

import android.webkit.WebChromeClient;
import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class CustomViewCallbackHostApiImpl implements GeneratedAndroidWebView.CustomViewCallbackHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public CustomViewCallbackHostApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
    }

    private WebChromeClient.CustomViewCallback getCustomViewCallbackInstance(@NonNull Long l2) {
        WebChromeClient.CustomViewCallback customViewCallback = (WebChromeClient.CustomViewCallback) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(customViewCallback);
        return customViewCallback;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CustomViewCallbackHostApi
    public void onCustomViewHidden(@NonNull Long l2) {
        getCustomViewCallbackInstance(l2).onCustomViewHidden();
    }
}

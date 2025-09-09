package io.flutter.plugins.webviewflutter;

import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebViewFlutterApiImpl {
    private GeneratedAndroidWebView.WebViewFlutterApi api;
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    public WebViewFlutterApiImpl(@NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
        this.binaryMessenger = binaryMessenger;
        this.instanceManager = instanceManager;
        this.api = new GeneratedAndroidWebView.WebViewFlutterApi(binaryMessenger);
    }

    public void create(@NonNull WebView webView, @NonNull GeneratedAndroidWebView.WebViewFlutterApi.Reply<Void> reply) {
        if (this.instanceManager.containsInstance(webView)) {
            return;
        }
        this.api.create(Long.valueOf(this.instanceManager.addHostCreatedInstance(webView)), reply);
    }

    public void onScrollChanged(@NonNull WebView webView, @NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull Long l5, @NonNull GeneratedAndroidWebView.WebViewFlutterApi.Reply<Void> reply) {
        GeneratedAndroidWebView.WebViewFlutterApi webViewFlutterApi = this.api;
        Long identifierForStrongReference = this.instanceManager.getIdentifierForStrongReference(webView);
        Objects.requireNonNull(identifierForStrongReference);
        webViewFlutterApi.onScrollChanged(identifierForStrongReference, l2, l3, l4, l5, reply);
    }

    @VisibleForTesting
    void setApi(@NonNull GeneratedAndroidWebView.WebViewFlutterApi webViewFlutterApi) {
        this.api = webViewFlutterApi;
    }
}

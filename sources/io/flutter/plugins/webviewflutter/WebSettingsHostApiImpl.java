package io.flutter.plugins.webviewflutter;

import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WebSettingsHostApiImpl implements GeneratedAndroidWebView.WebSettingsHostApi {
    private final InstanceManager instanceManager;
    private final WebSettingsCreator webSettingsCreator;

    public static class WebSettingsCreator {
        @NonNull
        public WebSettings createWebSettings(@NonNull WebView webView) {
            return webView.getSettings();
        }
    }

    public WebSettingsHostApiImpl(@NonNull InstanceManager instanceManager, @NonNull WebSettingsCreator webSettingsCreator) {
        this.instanceManager = instanceManager;
        this.webSettingsCreator = webSettingsCreator;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void create(@NonNull Long l2, @NonNull Long l3) {
        WebView webView = (WebView) this.instanceManager.getInstance(l3.longValue());
        Objects.requireNonNull(webView);
        this.instanceManager.addDartCreatedInstance(this.webSettingsCreator.createWebSettings(webView), l2.longValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    @NonNull
    public String getUserAgentString(@NonNull Long l2) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        return webSettings.getUserAgentString();
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setAllowFileAccess(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setAllowFileAccess(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setBuiltInZoomControls(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setBuiltInZoomControls(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setDisplayZoomControls(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setDisplayZoomControls(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setDomStorageEnabled(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setDomStorageEnabled(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setJavaScriptCanOpenWindowsAutomatically(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setJavaScriptEnabled(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setJavaScriptEnabled(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setLoadWithOverviewMode(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setLoadWithOverviewMode(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setMediaPlaybackRequiresUserGesture(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setSupportMultipleWindows(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setSupportMultipleWindows(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setSupportZoom(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setSupportZoom(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setTextZoom(@NonNull Long l2, @NonNull Long l3) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setTextZoom(l3.intValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setUseWideViewPort(@NonNull Long l2, @NonNull Boolean bool) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setUseWideViewPort(bool.booleanValue());
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebSettingsHostApi
    public void setUserAgentString(@NonNull Long l2, @Nullable String str) {
        WebSettings webSettings = (WebSettings) this.instanceManager.getInstance(l2.longValue());
        Objects.requireNonNull(webSettings);
        webSettings.setUserAgentString(str);
    }
}

package io.flutter.plugins.webviewflutter;

import android.webkit.WebView;
import io.flutter.embedding.engine.FlutterEngine;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class j5 {
    public static WebView a(FlutterEngine flutterEngine, long j2) {
        WebViewFlutterPlugin webViewFlutterPlugin = (WebViewFlutterPlugin) flutterEngine.getPlugins().get(WebViewFlutterPlugin.class);
        if (webViewFlutterPlugin == null || webViewFlutterPlugin.getInstanceManager() == null) {
            return null;
        }
        Object instanceManager = webViewFlutterPlugin.getInstanceManager().getInstance(j2);
        if (instanceManager instanceof WebView) {
            return (WebView) instanceManager;
        }
        return null;
    }
}

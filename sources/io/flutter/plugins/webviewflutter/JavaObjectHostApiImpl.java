package io.flutter.plugins.webviewflutter;

import androidx.annotation.NonNull;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import io.flutter.plugins.webviewflutter.WebViewHostApiImpl;

/* loaded from: classes4.dex */
public class JavaObjectHostApiImpl implements GeneratedAndroidWebView.JavaObjectHostApi {
    private final InstanceManager instanceManager;

    public JavaObjectHostApiImpl(@NonNull InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
    }

    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.JavaObjectHostApi
    public void dispose(@NonNull Long l2) {
        Object instanceManager = this.instanceManager.getInstance(l2.longValue());
        if (instanceManager instanceof WebViewHostApiImpl.WebViewPlatformView) {
            ((WebViewHostApiImpl.WebViewPlatformView) instanceManager).destroy();
        }
        this.instanceManager.remove(l2.longValue());
    }
}

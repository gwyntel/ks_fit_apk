package com.alipay.sdk.m.x;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.FrameLayout;

/* loaded from: classes2.dex */
public abstract class c extends FrameLayout {

    /* renamed from: c, reason: collision with root package name */
    public static final String f9865c = "v1";

    /* renamed from: d, reason: collision with root package name */
    public static final String f9866d = "v2";

    /* renamed from: a, reason: collision with root package name */
    public Activity f9867a;

    /* renamed from: b, reason: collision with root package name */
    public final String f9868b;

    public c(Activity activity, String str) {
        super(activity);
        this.f9867a = activity;
        this.f9868b = str;
    }

    public abstract void a(String str);

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        CookieSyncManager.createInstance(this.f9867a.getApplicationContext()).sync();
        CookieManager.getInstance().setCookie(str, str2);
        CookieSyncManager.getInstance().sync();
    }

    public abstract boolean b();

    public abstract void c();

    public boolean a() {
        return f9865c.equals(this.f9868b);
    }

    public static void a(WebView webView) {
        if (webView != null) {
            try {
                webView.resumeTimers();
            } catch (Throwable unused) {
            }
        }
    }
}

package com.huawei.secure.android.common.webview;

import android.util.Log;
import android.webkit.WebView;
import com.huawei.secure.android.common.util.d;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class SafeGetUrl {

    /* renamed from: c, reason: collision with root package name */
    private static final String f18547c = "SafeGetUrl";

    /* renamed from: d, reason: collision with root package name */
    private static final long f18548d = 200;

    /* renamed from: a, reason: collision with root package name */
    private String f18549a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f18550b;

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ CountDownLatch f18551a;

        a(CountDownLatch countDownLatch) {
            this.f18551a = countDownLatch;
        }

        @Override // java.lang.Runnable
        public void run() {
            SafeGetUrl safeGetUrl = SafeGetUrl.this;
            safeGetUrl.setUrl(safeGetUrl.f18550b.getUrl());
            this.f18551a.countDown();
        }
    }

    public SafeGetUrl() {
    }

    public String getUrlMethod() throws InterruptedException {
        if (this.f18550b == null) {
            return "";
        }
        if (com.huawei.secure.android.common.util.c.a()) {
            return this.f18550b.getUrl();
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        d.a(new a(countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException e2) {
            Log.e(f18547c, "getUrlMethod: InterruptedException " + e2.getMessage(), e2);
        }
        return this.f18549a;
    }

    public WebView getWebView() {
        return this.f18550b;
    }

    public void setUrl(String str) {
        this.f18549a = str;
    }

    public void setWebView(WebView webView) {
        this.f18550b = webView;
    }

    public SafeGetUrl(WebView webView) {
        this.f18550b = webView;
    }
}

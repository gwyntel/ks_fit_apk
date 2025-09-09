package com.alibaba.sdk.android.openaccount.webview.handler;

import android.webkit.WebView;

/* loaded from: classes2.dex */
public interface OverrideURLHandler {
    boolean handle(WebView webView, String str);

    boolean isURLSupported(String str);
}

package com.alibaba.sdk.android.openaccount.webview.handler;

import android.webkit.WebView;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;

/* loaded from: classes2.dex */
public abstract class AbstractOverrideUrlHandler implements OverrideURLHandler {
    private static final String TAG = "AbstractOverrideUrlHandler";
    private String targetUrl;
    private String[] targetUrls;

    public AbstractOverrideUrlHandler() {
    }

    @Override // com.alibaba.sdk.android.openaccount.webview.handler.OverrideURLHandler
    public boolean handle(WebView webView, String str) {
        try {
            return handleWithoutException(webView, str);
        } catch (Throwable th) {
            AliSDKLogger.e(TAG, th.getMessage(), th);
            return false;
        }
    }

    public abstract boolean handleWithoutException(WebView webView, String str);

    @Override // com.alibaba.sdk.android.openaccount.webview.handler.OverrideURLHandler
    public boolean isURLSupported(String str) {
        if (str == null) {
            return false;
        }
        String[] strArr = this.targetUrls;
        if (strArr == null) {
            String str2 = this.targetUrl;
            if (str2 != null) {
                return str.startsWith(str2);
            }
            return false;
        }
        for (String str3 : strArr) {
            if (str.startsWith(str3)) {
                return true;
            }
        }
        return false;
    }

    public AbstractOverrideUrlHandler(String[] strArr) {
        this.targetUrls = strArr;
    }

    public AbstractOverrideUrlHandler(String str) {
        this.targetUrl = str;
    }
}

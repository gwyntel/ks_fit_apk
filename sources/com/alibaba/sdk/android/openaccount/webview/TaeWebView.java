package com.alibaba.sdk.android.openaccount.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.FileUtils;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
/* loaded from: classes2.dex */
public class TaeWebView extends WebView {
    private static final String TAG = "TaeWebView";
    private static final String UA_ALIAPP_APPEND = " AliApp(BC/" + OpenAccountSDK.getVersion().toString() + ")";
    private static final String UA_TAESDK_APPEND;
    private String appCacheDir;
    private HashMap<String, String> contextParameters;

    @Autowired
    private ExecutorService executorService;
    private String lastReloadUrl;
    private Map<String, Object> nameToObj;
    private String startUrl;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(" tae_sdk_");
        sb.append(OpenAccountSDK.getVersion().toString());
        UA_TAESDK_APPEND = sb.toString();
    }

    public TaeWebView(Context context) {
        super(context);
        this.lastReloadUrl = "";
        this.nameToObj = new HashMap();
        this.contextParameters = new HashMap<>();
        Pluto.DEFAULT_INSTANCE.inject(this);
        initSettings(context, true);
    }

    @TargetApi(21)
    private void initSettings(Context context, boolean z2) {
        WebSettings settings = getSettings();
        try {
            settings.setJavaScriptEnabled(true);
        } catch (Exception unused) {
        }
        settings.setSavePassword(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setDomStorageEnabled(true);
        String path = context.getApplicationContext().getDir("cache", 0).getPath();
        this.appCacheDir = path;
        settings.setAppCachePath(path);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        if (CommonUtils.isNetworkAvailable(context)) {
            settings.setCacheMode(-1);
        } else {
            settings.setCacheMode(1);
        }
        settings.setBuiltInZoomControls(false);
        if (z2) {
            StringBuilder sb = new StringBuilder();
            String userAgentString = settings.getUserAgentString();
            if (userAgentString != null) {
                sb.append(userAgentString);
            }
            sb.append(UA_TAESDK_APPEND);
            sb.append(UA_ALIAPP_APPEND);
            settings.setUserAgentString(sb.toString());
        }
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        int intProperty = ConfigManager.getInstance().getIntProperty("mixedContentMode", -1);
        if (intProperty != -1) {
            settings.setMixedContentMode(intProperty);
        }
        removeRiskJavascriptInterfaces();
    }

    @TargetApi(11)
    private void removeRiskJavascriptInterfaces() {
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
    }

    public final void addBridgeObject(String str, Object obj) {
        this.nameToObj.put(str, obj);
    }

    @Override // android.webkit.WebView
    public final void addJavascriptInterface(Object obj, String str) {
    }

    public void clearCache() {
        try {
            clearCache(true);
        } catch (Exception e2) {
            AliSDKLogger.e("ui", "fail to clear cache ", e2);
        }
        this.executorService.postTask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.webview.TaeWebView.1
            @Override // java.lang.Runnable
            public void run() {
                if (TaeWebView.this.appCacheDir != null) {
                    try {
                        FileUtils.delete(new File(TaeWebView.this.appCacheDir));
                    } catch (Exception e3) {
                        AliSDKLogger.e("ui", "fail to delete cache " + TaeWebView.this.appCacheDir, e3);
                    }
                }
            }
        });
    }

    public Object getBridgeObj(String str) {
        return this.nameToObj.get(str);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str, Map<String, String> map) {
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "load url: " + str);
        }
        if (str != null) {
            String strNormalizeURL = normalizeURL(str);
            this.startUrl = strNormalizeURL;
            if (strNormalizeURL != null) {
                super.loadUrl(strNormalizeURL, map);
            }
        }
    }

    protected String normalizeURL(String str) {
        return str;
    }

    @Override // android.webkit.WebView
    public void reload() {
        String url = getUrl();
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "reload url: " + url);
        }
        if (TextUtils.equals(this.lastReloadUrl, url) || TextUtils.equals(this.lastReloadUrl, this.startUrl)) {
            return;
        }
        if (url == null) {
            loadUrl(this.startUrl);
            this.lastReloadUrl = this.startUrl;
        } else {
            this.lastReloadUrl = url;
            super.reload();
        }
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "load url: " + str);
        }
        if (str != null) {
            String strNormalizeURL = normalizeURL(str);
            this.startUrl = strNormalizeURL;
            if (strNormalizeURL != null) {
                super.loadUrl(strNormalizeURL);
            }
        }
    }

    public TaeWebView(Context context, boolean z2) {
        super(context);
        this.lastReloadUrl = "";
        this.nameToObj = new HashMap();
        this.contextParameters = new HashMap<>();
        Pluto.DEFAULT_INSTANCE.inject(this);
        initSettings(context, z2);
    }

    public void loadUrl(String str, boolean z2) {
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(TAG, "load url: " + str);
        }
        if (str != null) {
            if (z2) {
                str = normalizeURL(str);
            }
            if (str != null) {
                super.loadUrl(str);
            }
        }
    }

    public TaeWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lastReloadUrl = "";
        this.nameToObj = new HashMap();
        this.contextParameters = new HashMap<>();
        Pluto.DEFAULT_INSTANCE.inject(this);
        initSettings(context, true);
    }
}

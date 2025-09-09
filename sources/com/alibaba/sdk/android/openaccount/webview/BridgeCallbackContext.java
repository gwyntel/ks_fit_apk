package com.alibaba.sdk.android.openaccount.webview;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.pluto.Pluto;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class BridgeCallbackContext {
    public int requestId;
    public WebView webView;

    /* JADX INFO: Access modifiers changed from: private */
    public void callback(String str) {
        WebView webView = this.webView;
        if (webView == null) {
            return;
        }
        webView.loadUrl(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatJsonString(String str) {
        return str.replace("\\", "\\\\");
    }

    public Activity getActivity() {
        return (Activity) this.webView.getContext();
    }

    public void onFailure(int i2, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i2);
            jSONObject.put("message", str);
            onFailure(jSONObject.toString());
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void success(final String str) {
        ((ExecutorService) Pluto.DEFAULT_INSTANCE.getBean(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.webview.BridgeCallbackContext.1
            @Override // java.lang.Runnable
            public void run() {
                BridgeCallbackContext.this.callback(TextUtils.isEmpty(str) ? String.format("javascript:window.HavanaBridge.onSuccess(%s);", Integer.valueOf(BridgeCallbackContext.this.requestId)) : String.format("javascript:window.HavanaBridge.onSuccess(%s,'%s');", Integer.valueOf(BridgeCallbackContext.this.requestId), BridgeCallbackContext.formatJsonString(str)));
            }
        });
    }

    public void onFailure(final String str) {
        ((ExecutorService) Pluto.DEFAULT_INSTANCE.getBean(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.webview.BridgeCallbackContext.2
            @Override // java.lang.Runnable
            public void run() {
                BridgeCallbackContext.this.callback(TextUtils.isEmpty(str) ? String.format("javascript:window.HavanaBridge.onFailure(%s,'');", Integer.valueOf(BridgeCallbackContext.this.requestId)) : String.format("javascript:window.HavanaBridge.onFailure(%s,'%s');", Integer.valueOf(BridgeCallbackContext.this.requestId), BridgeCallbackContext.formatJsonString(str)));
            }
        });
    }
}

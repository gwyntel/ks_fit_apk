package com.alibaba.sdk.android.openaccount.webview;

import android.annotation.TargetApi;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class BridgeWebChromeClient extends WebChromeClient {
    private static final String TAG = "BridgeWebChromeClient";
    private static boolean evaluateJavascriptSupported = true;

    private static class JavaScriptTask implements Runnable {
        public String script;
        public WebView webView;

        public JavaScriptTask(WebView webView, String str) {
            this.webView = webView;
            this.script = str;
        }

        @Override // java.lang.Runnable
        @TargetApi(19)
        public void run() {
            try {
                if (BridgeWebChromeClient.evaluateJavascriptSupported) {
                    try {
                        this.webView.evaluateJavascript(this.script, null);
                        return;
                    } catch (Exception e2) {
                        AliSDKLogger.e("ui", "fail to evaluate the script " + e2.getMessage(), e2);
                    }
                }
                if (ConfigManager.getInstance().getBooleanProperty("executeJavaScriptWithLoadUrl", true)) {
                    String str = "javascript:" + this.script;
                    WebView webView = this.webView;
                    if (webView instanceof TaeWebView) {
                        ((TaeWebView) webView).loadUrl(str, false);
                    } else {
                        webView.loadUrl(str);
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    private void handleWindVaneNoHandler(WebView webView, String str) {
        try {
            int iIndexOf = str.indexOf(58, 9);
            webView.post(new JavaScriptTask(webView, String.format("window.WindVane&&window.WindVane.onFailure(%s,'{\"ret\":\"HY_NO_HANDLER\"');", str.substring(iIndexOf + 1, str.indexOf(47, iIndexOf)))));
        } catch (Exception e2) {
            AliSDKLogger.e("ui", "fail to handler windvane request, the error message is " + e2.getMessage(), e2);
        }
    }

    private HavanaBridgeProtocal parseMessage(String str) {
        Uri uri = Uri.parse(str);
        String host = uri.getHost();
        int port = uri.getPort();
        String lastPathSegment = uri.getLastPathSegment();
        uri.getQuery();
        int iIndexOf = str.indexOf("?");
        String strSubstring = iIndexOf == -1 ? null : str.substring(iIndexOf + 1);
        HavanaBridgeProtocal havanaBridgeProtocal = new HavanaBridgeProtocal();
        havanaBridgeProtocal.methodName = lastPathSegment;
        havanaBridgeProtocal.objName = host;
        havanaBridgeProtocal.param = strSubstring;
        havanaBridgeProtocal.requestId = port;
        return havanaBridgeProtocal;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        if (!ConfigManager.getInstance().getBooleanProperty("useToastForJsAlert", false)) {
            return false;
        }
        Toast.makeText(webView.getContext(), str2, 1).show();
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (str3 == null) {
            return false;
        }
        if (ConfigManager.getInstance().getStringProperty("blockJsPromptDefaultValues", "").indexOf(str3) != -1) {
            jsPromptResult.confirm("");
            return true;
        }
        if (str3.equals("wv_hybrid:")) {
            handleWindVaneNoHandler(webView, str2);
            jsPromptResult.confirm("");
            return true;
        }
        if (!TextUtils.equals(str3, "hv_hybrid:") || !(webView instanceof TaeWebView)) {
            return false;
        }
        TaeWebView taeWebView = (TaeWebView) webView;
        HavanaBridgeProtocal message = parseMessage(str2);
        BridgeCallbackContext bridgeCallbackContext = new BridgeCallbackContext();
        bridgeCallbackContext.requestId = message.requestId;
        bridgeCallbackContext.webView = taeWebView;
        Object bridgeObj = taeWebView.getBridgeObj(message.objName);
        if (bridgeObj == null) {
            Message messageCreateMessage = MessageUtils.createMessage(10000, message.objName);
            AliSDKLogger.log(TAG, messageCreateMessage);
            bridgeCallbackContext.onFailure(messageCreateMessage.code, messageCreateMessage.message);
            jsPromptResult.confirm("");
            return true;
        }
        try {
            Method method = bridgeObj.getClass().getMethod(message.methodName, BridgeCallbackContext.class, String.class);
            if (method.isAnnotationPresent(BridgeMethod.class)) {
                try {
                    method.invoke(bridgeObj, bridgeCallbackContext, TextUtils.isEmpty(message.param) ? "{}" : message.param);
                } catch (Exception e2) {
                    Message messageCreateMessage2 = MessageUtils.createMessage(10010, e2.getMessage());
                    AliSDKLogger.log(TAG, messageCreateMessage2, e2);
                    bridgeCallbackContext.onFailure(messageCreateMessage2.code, messageCreateMessage2.message);
                }
            } else {
                Message messageCreateMessage3 = MessageUtils.createMessage(MessageConstants.JS_BRIDGE_ANNOTATION_NOT_PRESENT, message.objName, message.methodName);
                AliSDKLogger.log(TAG, messageCreateMessage3);
                bridgeCallbackContext.onFailure(messageCreateMessage3.code, messageCreateMessage3.message);
            }
            jsPromptResult.confirm("");
            return true;
        } catch (NoSuchMethodException e3) {
            Message messageCreateMessage4 = MessageUtils.createMessage(MessageConstants.JS_BRIDGE_METHOD_NOT_FOUND, message.objName, message.methodName);
            AliSDKLogger.log(TAG, messageCreateMessage4, e3);
            bridgeCallbackContext.onFailure(messageCreateMessage4.code, messageCreateMessage4.message);
            jsPromptResult.confirm("");
            return true;
        }
    }
}

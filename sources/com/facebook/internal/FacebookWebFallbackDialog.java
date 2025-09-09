package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class FacebookWebFallbackDialog extends WebDialog {
    private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
    private static final String TAG = "com.facebook.internal.FacebookWebFallbackDialog";
    private boolean waitingForDialogToClose;

    private FacebookWebFallbackDialog(Context context, String str, String str2) {
        super(context, str);
        setExpectedRedirectUrl(str2);
    }

    public static FacebookWebFallbackDialog newInstance(Context context, String str, String str2) throws PackageManager.NameNotFoundException {
        WebDialog.initDefaultTheme(context);
        return new FacebookWebFallbackDialog(context, str, str2);
    }

    @Override // com.facebook.internal.WebDialog, android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        WebView webView = getWebView();
        if (!isPageFinished() || isListenerCalled() || webView == null || !webView.isShown()) {
            super.cancel();
            return;
        }
        if (this.waitingForDialogToClose) {
            return;
        }
        this.waitingForDialogToClose = true;
        webView.loadUrl("javascript:(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.facebook.internal.FacebookWebFallbackDialog.1
            @Override // java.lang.Runnable
            public void run() {
                FacebookWebFallbackDialog.super.cancel();
            }
        }, 1500L);
    }

    @Override // com.facebook.internal.WebDialog
    protected Bundle parseResponseUri(String str) {
        Bundle urlQueryString = Utility.parseUrlQueryString(Uri.parse(str).getQuery());
        String string = urlQueryString.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        urlQueryString.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_BRIDGE_ARGS);
        if (!Utility.isNullOrEmpty(string)) {
            try {
                urlQueryString.putBundle(NativeProtocol.EXTRA_PROTOCOL_BRIDGE_ARGS, BundleJSONConverter.convertToBundle(new JSONObject(string)));
            } catch (JSONException e2) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", e2);
            }
        }
        String string2 = urlQueryString.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        urlQueryString.remove(ServerProtocol.FALLBACK_DIALOG_PARAM_METHOD_RESULTS);
        if (!Utility.isNullOrEmpty(string2)) {
            if (Utility.isNullOrEmpty(string2)) {
                string2 = "{}";
            }
            try {
                urlQueryString.putBundle(NativeProtocol.EXTRA_PROTOCOL_METHOD_RESULTS, BundleJSONConverter.convertToBundle(new JSONObject(string2)));
            } catch (JSONException e3) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", e3);
            }
        }
        urlQueryString.remove("version");
        urlQueryString.putInt(NativeProtocol.EXTRA_PROTOCOL_VERSION, NativeProtocol.getLatestKnownVersion());
        return urlQueryString;
    }
}

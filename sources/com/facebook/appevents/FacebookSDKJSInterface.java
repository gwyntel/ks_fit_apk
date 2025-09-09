package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class FacebookSDKJSInterface {
    private static final String PARAMETER_FBSDK_PIXEL_REFERRAL = "_fb_pixel_referral_id";
    private static final String PROTOCOL = "fbmq-0.1";
    public static final String TAG = "FacebookSDKJSInterface";
    private Context context;

    public FacebookSDKJSInterface(Context context) {
        this.context = context;
    }

    private static Bundle jsonStringToBundle(String str) {
        try {
            return jsonToBundle(new JSONObject(str));
        } catch (JSONException unused) {
            return new Bundle();
        }
    }

    private static Bundle jsonToBundle(JSONObject jSONObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            bundle.putString(next, jSONObject.getString(next));
        }
        return bundle;
    }

    @JavascriptInterface
    public String getProtocol() {
        return PROTOCOL;
    }

    @JavascriptInterface
    public void sendEvent(String str, String str2, String str3) {
        if (str == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Can't bridge an event without a referral Pixel ID. Check your webview Pixel configuration");
            return;
        }
        InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(this.context);
        Bundle bundleJsonStringToBundle = jsonStringToBundle(str3);
        bundleJsonStringToBundle.putString(PARAMETER_FBSDK_PIXEL_REFERRAL, str);
        internalAppEventsLogger.logEvent(str2, bundleJsonStringToBundle);
    }
}

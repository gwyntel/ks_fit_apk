package com.xiaomi.account.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class OAuthUrlPaser {
    public static final String SKIP_THIRD_SNS_BIND_CODE = "/pass/sns/bind";
    public static final String SKIP_THIRD_SNS_CODE = "/pass/sns/login/load";

    public static Bundle parse(String str) {
        if (!str.contains(SKIP_THIRD_SNS_CODE) && !str.contains(SKIP_THIRD_SNS_BIND_CODE)) {
            String str2 = new String(str);
            int iIndexOf = str2.indexOf(63);
            if (iIndexOf > 0) {
                String strSubstring = str2.substring(iIndexOf + 1);
                if (strSubstring.startsWith("code=") || strSubstring.contains("&code=")) {
                    return parseUrl(str2);
                }
                if (strSubstring.startsWith("error=") || strSubstring.contains("&error=")) {
                    return parseUrl(str2);
                }
            } else {
                int iIndexOf2 = str2.indexOf(35);
                if (iIndexOf2 > 0) {
                    String strSubstring2 = str2.substring(iIndexOf2 + 1);
                    if (strSubstring2.startsWith("access_token=") || strSubstring2.contains("&access_token=")) {
                        return parseUrl(str2.replace(MqttTopic.MULTI_LEVEL_WILDCARD, "?"));
                    }
                    if (strSubstring2.startsWith("error=") || strSubstring2.contains("&error=")) {
                        return parseUrl(str2.replace(MqttTopic.MULTI_LEVEL_WILDCARD, "?"));
                    }
                }
            }
        }
        return null;
    }

    private static Bundle parseUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            try {
                for (NameValuePair nameValuePair : URLEncodedUtils.parse(new URI(str), "UTF-8")) {
                    if (!TextUtils.isEmpty(nameValuePair.getName()) && !TextUtils.isEmpty(nameValuePair.getValue())) {
                        bundle.putString(nameValuePair.getName(), nameValuePair.getValue());
                    }
                }
            } catch (URISyntaxException e2) {
                Log.e("openauth", e2.getMessage());
            }
        }
        return bundle;
    }
}

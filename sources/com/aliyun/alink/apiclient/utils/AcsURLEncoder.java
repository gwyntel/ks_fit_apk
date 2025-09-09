package com.aliyun.alink.apiclient.utils;

import androidx.webkit.ProxyConfig;
import com.xiaomi.mipush.sdk.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class AcsURLEncoder {
    public static final String URL_ENCODING = "UTF-8";

    public static String encode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    public static String percentEncode(String str) throws UnsupportedEncodingException {
        if (str != null) {
            return URLEncoder.encode(str, "UTF-8").replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%20").replace(ProxyConfig.MATCH_ALL_SCHEMES, "%2A").replace("%7E", Constants.WAVE_SEPARATOR);
        }
        return null;
    }
}

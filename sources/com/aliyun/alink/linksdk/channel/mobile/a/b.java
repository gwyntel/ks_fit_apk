package com.aliyun.alink.linksdk.channel.mobile.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f10995a = "https://iot-auth.ap-southeast-1.aliyuncs.com/auth/resource/mqtt";

    /* renamed from: b, reason: collision with root package name */
    private static String f10996b = "";

    /* renamed from: c, reason: collision with root package name */
    private static OkHttpClient f10997c;

    public interface a {
        void a();

        void b();
    }

    public static void a(a aVar) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("DynamicHostRequest", "action()");
        if (!TextUtils.isEmpty(f10996b)) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("DynamicHostRequest", "host is not empty, " + f10996b);
            MqttConfigure.mqttHost = f10996b;
            return;
        }
        if (f10997c == null) {
            f10997c = new OkHttpClient();
        }
        f10997c.newCall(new Request.Builder().url(f10995a).build()).enqueue(new c(aVar));
    }
}

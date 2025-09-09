package com.aliyun.alink.linksdk.channel.gateway.a;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnectConfig;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class b extends MqttPublishRequest {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicLong f10972a = new AtomicLong();

    /* renamed from: b, reason: collision with root package name */
    private String f10973b;

    /* renamed from: c, reason: collision with root package name */
    private String f10974c = "1.0";

    /* renamed from: d, reason: collision with root package name */
    private String f10975d;

    /* renamed from: e, reason: collision with root package name */
    private Object f10976e;

    public b(boolean z2, PersistentConnectConfig persistentConnectConfig, String str, String str2, Map<String, Object> map, Object obj) {
        if (TextUtils.isEmpty(str) || str.startsWith("/sys/") || str.startsWith("/ota/") || persistentConnectConfig == null) {
            this.topic = str;
        } else {
            String str3 = "/sys/" + persistentConnectConfig.productKey + "/" + persistentConnectConfig.deviceName + "/" + str;
            this.topic = str3;
            this.topic = str3.replace("//", "/");
        }
        this.f10975d = str2;
        this.isRPC = z2;
        this.replyTopic = this.topic + TmpConstant.URI_TOPIC_REPLY_POST;
        if (obj == null) {
            this.f10976e = new HashMap();
        } else {
            this.f10976e = obj;
        }
        if (map != null && map.containsKey("qos")) {
            try {
                this.qos = Integer.parseInt(String.valueOf(map.get("qos")));
            } catch (Exception e2) {
                e2.printStackTrace();
                this.qos = 0;
            }
        }
        String str4 = f10972a.incrementAndGet() + "";
        this.f10973b = str4;
        this.msgId = str4;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", (Object) this.f10973b);
        jSONObject.put("version", (Object) this.f10974c);
        jSONObject.put("params", obj);
        jSONObject.put("method", (Object) str2);
        this.payloadObj = jSONObject.toJSONString();
    }
}

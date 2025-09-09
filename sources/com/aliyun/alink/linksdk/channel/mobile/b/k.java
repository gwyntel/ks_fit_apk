package com.aliyun.alink.linksdk.channel.mobile.b;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class k extends MqttPublishRequest {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicLong f11046a = new AtomicLong();

    /* renamed from: b, reason: collision with root package name */
    private String f11047b;

    /* renamed from: c, reason: collision with root package name */
    private Map<String, Object> f11048c;

    /* renamed from: d, reason: collision with root package name */
    private Map<String, Object> f11049d;

    /* renamed from: e, reason: collision with root package name */
    private Object f11050e;

    public k(boolean z2, String str, Map<String, Object> map, Object obj) {
        l lVarA = m.a().a(null);
        if (str.startsWith("/sys/") || lVarA == null) {
            this.topic = str;
        } else {
            String str2 = "/sys/" + lVarA.f11052b + "/" + lVarA.f11053c + "/app/up/" + str;
            this.topic = str2;
            this.topic = str2.replace("//", "/");
        }
        this.isRPC = z2;
        if (z2) {
            this.replyTopic = this.topic.replace("/up/", "/down/") + TmpConstant.URI_TOPIC_REPLY_POST;
        }
        if (obj == null) {
            this.f11050e = new HashMap();
        } else {
            this.f11050e = obj;
        }
        String str3 = f11046a.incrementAndGet() + "";
        this.f11047b = str3;
        this.msgId = str3;
        if (this.f11048c == null) {
            this.f11048c = new HashMap();
        }
        this.f11048c.put("version", "1.0");
        this.f11048c.put("time", System.currentTimeMillis() + "");
        if (this.f11049d == null) {
            this.f11049d = new HashMap();
        }
        if (lVarA != null) {
            this.f11049d.put(TmpConstant.KEY_CLIENT_ID, lVarA.f11053c + "&" + lVarA.f11052b);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", (Object) this.f11047b);
        jSONObject.put("system", (Object) this.f11048c);
        jSONObject.put(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, (Object) this.f11049d);
        jSONObject.put("params", obj);
        this.payloadObj = jSONObject.toJSONString();
    }
}

package com.huawei.hms.hatool;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b1 implements o1 {

    /* renamed from: a, reason: collision with root package name */
    private String f16334a;

    /* renamed from: b, reason: collision with root package name */
    private String f16335b;

    /* renamed from: c, reason: collision with root package name */
    private String f16336c;

    /* renamed from: d, reason: collision with root package name */
    private String f16337d;

    /* renamed from: e, reason: collision with root package name */
    private String f16338e;

    /* renamed from: f, reason: collision with root package name */
    private String f16339f;

    @Override // com.huawei.hms.hatool.o1
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.f16334a);
        jSONObject.put("eventtime", this.f16337d);
        jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.f16335b);
        jSONObject.put("event_session_name", this.f16338e);
        jSONObject.put("first_session_event", this.f16339f);
        if (TextUtils.isEmpty(this.f16336c)) {
            return null;
        }
        jSONObject.put(TmpConstant.DEVICE_MODEL_PROPERTIES, new JSONObject(this.f16336c));
        return jSONObject;
    }

    public String b() {
        return this.f16337d;
    }

    public String c() {
        return this.f16334a;
    }

    public JSONObject d() throws JSONException {
        JSONObject jSONObjectA = a();
        jSONObjectA.put(TmpConstant.DEVICE_MODEL_PROPERTIES, n.b(this.f16336c, o0.d().a()));
        return jSONObjectA;
    }

    public void e(String str) {
        this.f16339f = str;
    }

    public void f(String str) {
        this.f16338e = str;
    }

    public void a(String str) {
        this.f16336c = str;
    }

    public void b(String str) {
        this.f16335b = str;
    }

    public void c(String str) {
        this.f16337d = str;
    }

    public void d(String str) {
        this.f16334a = str;
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        this.f16335b = jSONObject.optString(NotificationCompat.CATEGORY_EVENT);
        this.f16336c = n.a(jSONObject.optString(TmpConstant.DEVICE_MODEL_PROPERTIES), o0.d().a());
        this.f16334a = jSONObject.optString("type");
        this.f16337d = jSONObject.optString("eventtime");
        this.f16338e = jSONObject.optString("event_session_name");
        this.f16339f = jSONObject.optString("first_session_event");
    }
}

package com.huawei.hms.hatool;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h1 implements o1 {

    /* renamed from: a, reason: collision with root package name */
    private List<b1> f16381a;

    /* renamed from: b, reason: collision with root package name */
    private k0 f16382b;

    /* renamed from: c, reason: collision with root package name */
    private t0 f16383c;

    /* renamed from: d, reason: collision with root package name */
    private o1 f16384d;

    /* renamed from: e, reason: collision with root package name */
    private String f16385e = "";

    /* renamed from: f, reason: collision with root package name */
    private String f16386f;

    public h1(String str) {
        this.f16386f = str;
    }

    @Override // com.huawei.hms.hatool.o1
    public JSONObject a() throws JSONException {
        String str;
        List<b1> list = this.f16381a;
        if (list == null || list.size() == 0) {
            str = "Not have actionEvent to send";
        } else if (this.f16382b == null || this.f16383c == null || this.f16384d == null) {
            str = "model in wrong format";
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("header", this.f16382b.a());
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObjectA = this.f16384d.a();
            jSONObjectA.put(TmpConstant.DEVICE_MODEL_PROPERTIES, this.f16383c.a());
            try {
                jSONObjectA.put("events_global_properties", new JSONObject(this.f16385e));
            } catch (JSONException unused) {
                jSONObjectA.put("events_global_properties", this.f16385e);
            }
            jSONObject2.put("events_common", jSONObjectA);
            JSONArray jSONArray = new JSONArray();
            Iterator<b1> it = this.f16381a.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectA2 = it.next().a();
                if (jSONObjectA2 != null) {
                    jSONArray.put(jSONObjectA2);
                } else {
                    v.e("hmsSdk", "custom event is empty,delete this event");
                }
            }
            jSONObject2.put("events", jSONArray);
            try {
                String strA = n.a(k1.a(jSONObject2.toString().getBytes("UTF-8")), this.f16386f);
                if (TextUtils.isEmpty(strA)) {
                    v.e("hmsSdk", "eventInfo encrypt failed,report over!");
                    return null;
                }
                jSONObject.put(NotificationCompat.CATEGORY_EVENT, strA);
                return jSONObject;
            } catch (UnsupportedEncodingException unused2) {
                str = "getBitZip(): Unsupported coding : utf-8";
            }
        }
        v.e("hmsSdk", str);
        return null;
    }

    public void a(k0 k0Var) {
        this.f16382b = k0Var;
    }

    public void a(l lVar) {
        this.f16384d = lVar;
    }

    public void a(t0 t0Var) {
        this.f16383c = t0Var;
    }

    public void a(String str) {
        if (str != null) {
            this.f16385e = str;
        }
    }

    public void a(List<b1> list) {
        this.f16381a = list;
    }
}

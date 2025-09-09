package com.huawei.hms.framework.network.grs.g.j;

import android.content.Context;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private final GrsBaseInfo f16289a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f16290b;

    /* renamed from: c, reason: collision with root package name */
    private final Set<String> f16291c = new HashSet();

    public c(GrsBaseInfo grsBaseInfo, Context context) {
        this.f16289a = grsBaseInfo;
        this.f16290b = context;
    }

    private String e() throws JSONException {
        Set<String> setB = com.huawei.hms.framework.network.grs.f.b.a(this.f16290b.getPackageName()).b();
        if (setB.isEmpty()) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = setB.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        try {
            jSONObject.put(TmpConstant.DEVICE_MODEL_SERVICES, jSONArray);
            Logger.d("GrsRequestInfo", "post service list is:%s", jSONObject.toString());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    private String f() throws JSONException {
        Logger.v("GrsRequestInfo", "getGeoipService enter");
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = this.f16291c.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        try {
            jSONObject.put(TmpConstant.DEVICE_MODEL_SERVICES, jSONArray);
            Logger.v("GrsRequestInfo", "post query service list is:%s", jSONObject.toString());
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    public Context a() {
        return this.f16290b;
    }

    public GrsBaseInfo b() {
        return this.f16289a;
    }

    public String c() {
        return this.f16291c.size() == 0 ? e() : f();
    }

    public Set<String> d() {
        return this.f16291c;
    }

    public void a(String str) {
        this.f16291c.add(str);
    }
}

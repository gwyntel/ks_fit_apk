package com.alibaba.sdk.android.httpdns;

import android.text.Html;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
class e {

    /* renamed from: a, reason: collision with root package name */
    private String f8847a;

    /* renamed from: b, reason: collision with root package name */
    private long f8848b;

    /* renamed from: b, reason: collision with other field name */
    private String f17b;

    /* renamed from: c, reason: collision with root package name */
    private long f8849c;
    private String hostName;
    private String[] ips;

    e(com.alibaba.sdk.android.httpdns.b.e eVar) {
        this.hostName = eVar.host;
        this.f8849c = com.alibaba.sdk.android.httpdns.b.c.a(eVar.f8804n);
        this.f8848b = -1000L;
        ArrayList<com.alibaba.sdk.android.httpdns.b.g> arrayList = eVar.f7a;
        if (arrayList != null && arrayList.size() > 0) {
            int size = eVar.f7a.size();
            this.ips = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                this.ips[i2] = eVar.f7a.get(i2).f8806o;
            }
        }
        if (com.alibaba.sdk.android.httpdns.net64.a.a().m47a()) {
            ArrayList arrayList2 = new ArrayList();
            ArrayList<com.alibaba.sdk.android.httpdns.b.g> arrayList3 = eVar.f8b;
            if (arrayList3 != null && arrayList3.size() > 0) {
                for (int i3 = 0; i3 < eVar.f8b.size(); i3++) {
                    arrayList2.add(eVar.f8b.get(i3).f8806o);
                }
            }
            com.alibaba.sdk.android.httpdns.net64.a.a().a(this.hostName, arrayList2);
        }
        this.f8847a = eVar.f8801a;
        this.f17b = eVar.f8802b;
    }

    long a() {
        return this.f8848b;
    }

    long b() {
        return this.f8849c;
    }

    boolean c() {
        return a() == -1000;
    }

    String getCacheKey() {
        return this.f17b;
    }

    String[] getIps() {
        return this.ips;
    }

    void setCacheKey(String str) {
        this.f17b = str;
    }

    public String toString() {
        String str = "host: " + this.hostName + " ip cnt: " + this.ips.length + " ttl: " + this.f8848b;
        for (int i2 = 0; i2 < this.ips.length; i2++) {
            str = str + "\n ip: " + this.ips[i2];
        }
        return str;
    }

    e(String str) throws JSONException {
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject(str);
        this.hostName = jSONObject.getString("host");
        try {
            if (jSONObject.has("ips")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("ips");
                int length = jSONArray2.length();
                this.ips = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    this.ips[i2] = jSONArray2.getString(i2);
                }
            }
            if (com.alibaba.sdk.android.httpdns.net64.a.a().m47a() && jSONObject.has("ipsv6") && (jSONArray = jSONObject.getJSONArray("ipsv6")) != null) {
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    arrayList.add(jSONArray.getString(i3));
                }
                com.alibaba.sdk.android.httpdns.net64.a.a().a(this.hostName, arrayList);
            }
            if (jSONObject.has(PushConstants.EXTRA)) {
                this.f8847a = jSONObject.getString(PushConstants.EXTRA);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.f8848b = jSONObject.getLong(RemoteMessageConst.TTL);
        this.f8849c = System.currentTimeMillis() / 1000;
    }

    /* renamed from: a, reason: collision with other method in class */
    com.alibaba.sdk.android.httpdns.b.e m40a() {
        List<String> listA;
        com.alibaba.sdk.android.httpdns.b.e eVar = new com.alibaba.sdk.android.httpdns.b.e();
        eVar.host = this.hostName;
        eVar.f8804n = String.valueOf(this.f8849c);
        eVar.f8803m = com.alibaba.sdk.android.httpdns.b.b.i();
        String[] strArr = this.ips;
        if (strArr != null && strArr.length > 0) {
            eVar.f7a = new ArrayList<>();
            for (String str : this.ips) {
                com.alibaba.sdk.android.httpdns.b.g gVar = new com.alibaba.sdk.android.httpdns.b.g();
                gVar.f8806o = str;
                gVar.f8807p = String.valueOf(this.f8848b);
                eVar.f7a.add(gVar);
            }
        }
        if (com.alibaba.sdk.android.httpdns.net64.a.a().m47a() && (listA = com.alibaba.sdk.android.httpdns.net64.a.a().a(this.hostName)) != null && listA.size() > 0) {
            eVar.f8b = new ArrayList<>();
            for (String str2 : listA) {
                com.alibaba.sdk.android.httpdns.b.g gVar2 = new com.alibaba.sdk.android.httpdns.b.g();
                gVar2.f8806o = str2;
                gVar2.f8807p = String.valueOf(this.f8848b);
                eVar.f8b.add(gVar2);
            }
        }
        eVar.f8801a = this.f8847a;
        eVar.f8802b = this.f17b;
        return eVar;
    }

    /* renamed from: b, reason: collision with other method in class */
    boolean m43b() {
        return b() + a() < System.currentTimeMillis() / 1000 || c();
    }

    e(String str, String[] strArr, long j2, long j3, String str2, String str3) {
        this.hostName = str;
        this.ips = strArr;
        this.f8848b = j2;
        this.f8849c = j3;
        this.f8847a = str2;
        this.f17b = str3;
    }

    /* renamed from: a, reason: collision with other method in class */
    String m41a() {
        return this.f8847a;
    }

    /* renamed from: a, reason: collision with other method in class */
    Map<String, String> m42a() {
        HashMap map = new HashMap();
        if (this.f8847a != null) {
            try {
                JSONObject jSONObject = new JSONObject(Html.fromHtml(Html.fromHtml(this.f8847a).toString()).toString());
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.get(next) == null ? null : jSONObject.get(next).toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }
}

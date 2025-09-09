package com.aliyun.alink.linksdk.channel.mobile.b;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    private final String f11055a = "LINKSDK_CHANNNEL_MOBILE_TRIPLES";

    /* renamed from: b, reason: collision with root package name */
    private final String f11056b = "LINKSDK_CHANNNEL_MOBILE_PRODUCTKEY";

    /* renamed from: c, reason: collision with root package name */
    private final String f11057c = "LINKSDK_CHANNNEL_MOBILE_DEVICENAME";

    /* renamed from: d, reason: collision with root package name */
    private final String f11058d = "LINKSDK_CHANNNEL_MOBILE_DEVICESECRET";

    /* renamed from: e, reason: collision with root package name */
    private List<l> f11059e = null;

    /* renamed from: f, reason: collision with root package name */
    private l f11060f = null;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final m f11061a = new m();
    }

    public static m a() {
        return a.f11061a;
    }

    public void b() {
        this.f11060f = null;
    }

    private List<l> b(Context context) {
        if (context == null) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "getSaveMobileTriple(), params error");
            return null;
        }
        String strA = com.aliyun.alink.linksdk.channel.mobile.c.b.a(context, "LINKSDK_CHANNNEL_MOBILE_TRIPLES");
        if (TextUtils.isEmpty(strA)) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "getSaveMobileTriple(), empty data");
            return null;
        }
        try {
            JSONArray array = JSON.parseArray(strA);
            if (array == null) {
                return null;
            }
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "getSaveMobileTriple(), data = " + array.toJSONString());
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < array.size(); i2++) {
                JSONObject jSONObject = array.getJSONObject(i2);
                l lVar = new l(jSONObject.getString("pk"), jSONObject.getString(AlinkConstants.KEY_DN), jSONObject.getString("ds"));
                lVar.f11051a = jSONObject.getString("host");
                arrayList.add(lVar);
            }
            return arrayList;
        } catch (Exception unused) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "getSaveMobileTriple(), params error");
            return null;
        }
    }

    public l a(Context context) {
        l lVar = this.f11060f;
        if (lVar != null && lVar.a()) {
            return this.f11060f;
        }
        if (context == null) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileTripleValueManager", "getTripleValue(), context is empty");
            return null;
        }
        if (this.f11059e == null) {
            this.f11059e = b(context);
        }
        l lVarA = a(b.a(), this.f11059e);
        this.f11060f = lVarA;
        return lVarA;
    }

    public boolean a(Context context, l lVar) {
        if (context == null || lVar == null || !lVar.a()) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "saveTripleValue(), params error");
            return false;
        }
        if (TextUtils.isEmpty(lVar.f11051a)) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "saveTripleValue(), host is empty");
            return false;
        }
        this.f11060f = lVar;
        List<l> listB = b(context);
        this.f11059e = listB;
        if (listB == null) {
            this.f11059e = new ArrayList();
        }
        Iterator<l> it = this.f11059e.iterator();
        while (true) {
            if (it.hasNext()) {
                l next = it.next();
                if (lVar.f11051a.equals(next.f11051a)) {
                    next.f11052b = lVar.f11052b;
                    next.f11053c = lVar.f11053c;
                    next.f11054d = lVar.f11054d;
                    break;
                }
            } else {
                this.f11059e.add(lVar);
                break;
            }
        }
        JSONArray jSONArray = new JSONArray();
        for (l lVar2 : this.f11059e) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pk", (Object) lVar2.f11052b);
            jSONObject.put(AlinkConstants.KEY_DN, (Object) lVar2.f11053c);
            jSONObject.put("ds", (Object) lVar2.f11054d);
            jSONObject.put("host", (Object) lVar2.f11051a);
            jSONArray.add(jSONObject);
        }
        return com.aliyun.alink.linksdk.channel.mobile.c.b.a(context, "LINKSDK_CHANNNEL_MOBILE_TRIPLES", jSONArray.toJSONString());
    }

    private l a(String str, List<l> list) {
        if (!TextUtils.isEmpty(str) && list != null && list.size() != 0) {
            for (l lVar : list) {
                if (str.equals(lVar.f11051a) && lVar.a()) {
                    com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileTripleValueManager", "getTripleByHost,get!");
                    return lVar;
                }
            }
        }
        return null;
    }
}

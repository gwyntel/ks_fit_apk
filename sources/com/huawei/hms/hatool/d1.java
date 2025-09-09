package com.huawei.hms.hatool;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class d1 implements g {

    /* renamed from: a, reason: collision with root package name */
    private String f16345a;

    /* renamed from: b, reason: collision with root package name */
    private String f16346b;

    /* renamed from: c, reason: collision with root package name */
    private String f16347c;

    /* renamed from: d, reason: collision with root package name */
    private List<b1> f16348d;

    public d1(List<b1> list, String str, String str2, String str3) {
        this.f16345a = str;
        this.f16346b = str2;
        this.f16347c = str3;
        this.f16348d = list;
    }

    private void a() {
        d.a(q0.i(), "backup_event", n1.a(this.f16345a, this.f16347c, this.f16346b));
    }

    @Override // java.lang.Runnable
    public void run() {
        List<b1> list = this.f16348d;
        if (list == null || list.size() == 0) {
            v.d("hmsSdk", "failed events is empty");
            return;
        }
        if (c0.a(q0.i(), "cached_v2_1", q0.k() * 1048576)) {
            v.e("hmsSdk", "The cacheFile is full,Can not writing data! reqID:" + this.f16346b);
            return;
        }
        String strA = n1.a(this.f16345a, this.f16347c);
        List<b1> list2 = c1.b(q0.i(), "cached_v2_1", strA).get(strA);
        if (list2 != null && list2.size() != 0) {
            this.f16348d.addAll(list2);
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<b1> it = this.f16348d.iterator();
        while (it.hasNext()) {
            try {
                jSONArray.put(it.next().d());
            } catch (JSONException unused) {
                v.e("hmsSdk", "event to json error");
            }
        }
        String string = jSONArray.toString();
        if (string.length() > q0.h() * 1048576) {
            v.e("hmsSdk", "this failed data is too long,can not writing it");
            this.f16348d = null;
            return;
        }
        v.d("hmsSdk", "data send failed, write to cache file...reqID:" + this.f16346b);
        d.b(q0.i(), "cached_v2_1", strA, string);
        a();
    }
}

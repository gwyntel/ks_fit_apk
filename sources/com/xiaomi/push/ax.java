package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.util.Map;

/* loaded from: classes4.dex */
public class ax implements ar {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ax f23463a;

    /* renamed from: a, reason: collision with other field name */
    private int f195a = aw.f23462a;

    /* renamed from: a, reason: collision with other field name */
    private ar f196a;

    private ax(Context context) {
        this.f196a = aw.a(context);
        com.xiaomi.channel.commonutils.logger.b.m91a("create id manager is: " + this.f195a);
    }

    public void a() {
    }

    public String b() {
        return null;
    }

    public String c() {
        return null;
    }

    public String d() {
        return null;
    }

    public static ax a(Context context) {
        if (f23463a == null) {
            synchronized (ax.class) {
                try {
                    if (f23463a == null) {
                        f23463a = new ax(context.getApplicationContext());
                    }
                } finally {
                }
            }
        }
        return f23463a;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public boolean mo180a() {
        return this.f196a.mo180a();
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public String mo179a() {
        return a(this.f196a.mo179a());
    }

    public void a(Map<String, String> map) {
        if (map == null) {
            return;
        }
        String strB = b();
        if (!TextUtils.isEmpty(strB)) {
            map.put("udid", strB);
        }
        String strMo179a = mo179a();
        if (!TextUtils.isEmpty(strMo179a)) {
            map.put("oaid", strMo179a);
        }
        String strC = c();
        if (!TextUtils.isEmpty(strC)) {
            map.put("vaid", strC);
        }
        String strD = d();
        if (!TextUtils.isEmpty(strD)) {
            map.put("aaid", strD);
        }
        map.put("oaid_type", String.valueOf(this.f195a));
    }

    private String a(String str) {
        return str == null ? "" : str;
    }
}

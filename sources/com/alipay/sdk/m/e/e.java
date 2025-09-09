package com.alipay.sdk.m.e;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public static List<i> f9233a;

    static {
        ArrayList arrayList = new ArrayList();
        f9233a = arrayList;
        arrayList.add(new l());
        f9233a.add(new d());
        f9233a.add(new c());
        f9233a.add(new h());
        f9233a.add(new k());
        f9233a.add(new b());
        f9233a.add(new a());
        f9233a.add(new g());
    }

    public static final <T> T a(Object obj, Type type) {
        T t2;
        for (i iVar : f9233a) {
            if (iVar.a(com.alipay.sdk.m.f.a.a(type)) && (t2 = (T) iVar.a(obj, type)) != null) {
                return t2;
            }
        }
        return null;
    }

    public static final Object a(String str, Type type) {
        Object bVar;
        if (str == null || str.length() == 0) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.startsWith("[") && strTrim.endsWith("]")) {
            bVar = new org.json.alipay.a(strTrim);
        } else {
            if (!strTrim.startsWith("{") || !strTrim.endsWith(com.alipay.sdk.m.u.i.f9804d)) {
                return a((Object) strTrim, type);
            }
            bVar = new org.json.alipay.b(strTrim);
        }
        return a(bVar, type);
    }
}

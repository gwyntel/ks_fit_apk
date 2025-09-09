package com.meizu.cloud.pushsdk.d.l;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final HashMap<String, Class<?>> f19247a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private Class<?> f19248b;

    /* renamed from: c, reason: collision with root package name */
    private String f19249c;

    /* renamed from: d, reason: collision with root package name */
    private Object f19250d;

    private a(Object obj) {
        this.f19250d = obj;
    }

    public static a a(Object obj) {
        return new a(obj);
    }

    private a(String str) {
        this.f19249c = str;
    }

    public static a a(String str) {
        return new a(str);
    }

    public b a(Class<?>... clsArr) {
        return new b(this, clsArr);
    }

    public c a(String str, Class<?>... clsArr) {
        return new c(this, str, clsArr);
    }

    Class<?> a() throws ClassNotFoundException {
        Class<?> cls = this.f19248b;
        if (cls != null) {
            return cls;
        }
        Object obj = this.f19250d;
        if (obj != null) {
            return obj.getClass();
        }
        HashMap<String, Class<?>> map = f19247a;
        Class<?> cls2 = map.get(this.f19249c);
        if (cls2 == null) {
            cls2 = Class.forName(this.f19249c);
            map.put(this.f19249c, cls2);
        }
        return cls2;
    }
}

package com.umeng.analytics.pro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class cc implements Serializable {

    /* renamed from: d, reason: collision with root package name */
    private static Map<Class<? extends bq>, Map<? extends bx, cc>> f21557d = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    public final String f21558a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f21559b;

    /* renamed from: c, reason: collision with root package name */
    public final cd f21560c;

    public cc(String str, byte b2, cd cdVar) {
        this.f21558a = str;
        this.f21559b = b2;
        this.f21560c = cdVar;
    }

    public static void a(Class<? extends bq> cls, Map<? extends bx, cc> map) {
        f21557d.put(cls, map);
    }

    public static Map<? extends bx, cc> a(Class<? extends bq> cls) throws IllegalAccessException, InstantiationException {
        if (!f21557d.containsKey(cls)) {
            try {
                cls.newInstance();
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("IllegalAccessException for TBase class: " + cls.getName() + ", message: " + e2.getMessage());
            } catch (InstantiationException e3) {
                throw new RuntimeException("InstantiationException for TBase class: " + cls.getName() + ", message: " + e3.getMessage());
            }
        }
        return f21557d.get(cls);
    }
}

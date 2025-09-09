package com.xiaomi.push;

import java.util.Map;

/* loaded from: classes4.dex */
public class hc implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public static String f23867a = "wcc-ml-test10.bj";

    /* renamed from: b, reason: collision with root package name */
    public static String f23868b;

    /* renamed from: a, reason: collision with other field name */
    private int f531a;

    /* renamed from: a, reason: collision with other field name */
    private hf f532a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f533a = hb.f516a;

    /* renamed from: b, reason: collision with other field name */
    private boolean f534b = true;

    /* renamed from: c, reason: collision with root package name */
    private String f23869c;

    /* renamed from: d, reason: collision with root package name */
    private String f23870d;

    /* renamed from: e, reason: collision with root package name */
    private String f23871e;

    public hc(Map<String, Integer> map, int i2, String str, hf hfVar) {
        a(map, i2, str, hfVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] mo476a() {
        return null;
    }

    public void b(String str) {
        this.f23871e = str;
    }

    public String c() {
        if (this.f23870d == null) {
            this.f23870d = a();
        }
        return this.f23870d;
    }

    public static final String a() {
        String str = f23868b;
        return str != null ? str : aa.m170a() ? "sandbox.xmpush.xiaomi.com" : aa.b() ? "10.38.162.35" : "app.chat.xiaomi.net";
    }

    public String b() {
        return this.f23871e;
    }

    public void c(String str) {
        this.f23870d = str;
    }

    public static final void a(String str) {
        if (aa.b()) {
            return;
        }
        f23868b = str;
    }

    private void a(Map<String, Integer> map, int i2, String str, hf hfVar) {
        this.f531a = i2;
        this.f23869c = str;
        this.f532a = hfVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m474a() {
        return this.f531a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m475a() {
        return this.f533a;
    }

    public void a(boolean z2) {
        this.f533a = z2;
    }
}

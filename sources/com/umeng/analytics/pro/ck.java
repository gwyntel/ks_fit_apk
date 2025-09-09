package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public class ck {

    /* renamed from: a, reason: collision with root package name */
    public final String f21618a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f21619b;

    /* renamed from: c, reason: collision with root package name */
    public final short f21620c;

    public ck() {
        this("", (byte) 0, (short) 0);
    }

    public boolean a(ck ckVar) {
        return this.f21619b == ckVar.f21619b && this.f21620c == ckVar.f21620c;
    }

    public String toString() {
        return "<TField name:'" + this.f21618a + "' type:" + ((int) this.f21619b) + " field-id:" + ((int) this.f21620c) + ">";
    }

    public ck(String str, byte b2, short s2) {
        this.f21618a = str;
        this.f21619b = b2;
        this.f21620c = s2;
    }
}

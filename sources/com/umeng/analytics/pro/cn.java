package com.umeng.analytics.pro;

/* loaded from: classes4.dex */
public final class cn {

    /* renamed from: a, reason: collision with root package name */
    public final String f21626a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f21627b;

    /* renamed from: c, reason: collision with root package name */
    public final int f21628c;

    public cn() {
        this("", (byte) 0, 0);
    }

    public boolean a(cn cnVar) {
        return this.f21626a.equals(cnVar.f21626a) && this.f21627b == cnVar.f21627b && this.f21628c == cnVar.f21628c;
    }

    public boolean equals(Object obj) {
        if (obj instanceof cn) {
            return a((cn) obj);
        }
        return false;
    }

    public String toString() {
        return "<TMessage name:'" + this.f21626a + "' type: " + ((int) this.f21627b) + " seqid:" + this.f21628c + ">";
    }

    public cn(String str, byte b2, int i2) {
        this.f21626a = str;
        this.f21627b = b2;
        this.f21628c = i2;
    }
}

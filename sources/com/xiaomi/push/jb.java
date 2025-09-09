package com.xiaomi.push;

/* loaded from: classes4.dex */
public enum jb {
    RegIdExpired(0),
    PackageUnregistered(1),
    Init(2);


    /* renamed from: a, reason: collision with other field name */
    private final int f667a;

    jb(int i2) {
        this.f667a = i2;
    }

    public int a() {
        return this.f667a;
    }

    public static jb a(int i2) {
        if (i2 == 0) {
            return RegIdExpired;
        }
        if (i2 == 1) {
            return PackageUnregistered;
        }
        if (i2 != 2) {
            return null;
        }
        return Init;
    }
}

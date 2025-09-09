package com.xiaomi.push;

import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public final class db {

    /* renamed from: a, reason: collision with root package name */
    private int f23569a;

    /* renamed from: a, reason: collision with other field name */
    private String f262a;

    public db(String str, int i2) {
        this.f262a = str;
        this.f23569a = i2;
    }

    public int a() {
        return this.f23569a;
    }

    public String toString() {
        if (this.f23569a <= 0) {
            return this.f262a;
        }
        return this.f262a + ":" + this.f23569a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m267a() {
        return this.f262a;
    }

    public static db a(String str, int i2) throws NumberFormatException {
        int iLastIndexOf = str.lastIndexOf(":");
        if (iLastIndexOf != -1) {
            String strSubstring = str.substring(0, iLastIndexOf);
            try {
                int i3 = Integer.parseInt(str.substring(iLastIndexOf + 1));
                if (i3 > 0) {
                    i2 = i3;
                }
            } catch (NumberFormatException unused) {
            }
            str = strSubstring;
        }
        return new db(str, i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static InetSocketAddress m266a(String str, int i2) throws NumberFormatException {
        db dbVarA = a(str, i2);
        return new InetSocketAddress(dbVarA.m267a(), dbVarA.a());
    }
}

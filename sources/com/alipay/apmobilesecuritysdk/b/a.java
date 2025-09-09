package com.alipay.apmobilesecuritysdk.b;

import com.alipay.sdk.m.d0.d;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: b, reason: collision with root package name */
    public static a f9058b = new a();

    /* renamed from: a, reason: collision with root package name */
    public int f9059a = 0;

    public static a a() {
        return f9058b;
    }

    public final int b() {
        return this.f9059a;
    }

    public final String c() {
        String str;
        String strA = d.a();
        if (com.alipay.sdk.m.z.a.b(strA)) {
            return strA;
        }
        int i2 = this.f9059a;
        if (i2 == 1) {
            str = "://mobilegw.stable.alipay.net/mgw.htm";
        } else {
            if (i2 == 2) {
                return "https://mobilegwpre.alipay.com/mgw.htm";
            }
            if (i2 == 3) {
                str = "://mobilegw-1-64.test.alipay.net/mgw.htm";
            } else {
                if (i2 != 4) {
                    return "https://mobilegw.alipay.com/mgw.htm";
                }
                str = "://mobilegw.aaa.alipay.net/mgw.htm";
            }
        }
        return a("http", str);
    }

    public static String a(String str, String str2) {
        return str + str2;
    }

    public final void a(int i2) {
        this.f9059a = i2;
    }
}

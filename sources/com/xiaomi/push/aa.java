package com.xiaomi.push;

/* loaded from: classes4.dex */
public class aa {

    /* renamed from: a, reason: collision with root package name */
    private static int f23430a;

    /* renamed from: a, reason: collision with other field name */
    public static final String f161a;

    /* renamed from: a, reason: collision with other field name */
    public static boolean f162a;

    static {
        String str = ad.f23431a ? "ONEBOX" : "@SHIP.TO.2A2FE0D7@";
        f161a = str;
        f162a = false;
        f23430a = 1;
        if (str.equalsIgnoreCase("SANDBOX")) {
            f23430a = 2;
        } else if (str.equalsIgnoreCase("ONEBOX")) {
            f23430a = 3;
        } else {
            f23430a = 1;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m170a() {
        return f23430a == 2;
    }

    public static boolean b() {
        return f23430a == 3;
    }

    public static int a() {
        return f23430a;
    }

    public static void a(int i2) {
        f23430a = i2;
    }
}

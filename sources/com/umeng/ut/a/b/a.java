package com.umeng.ut.a.b;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public int f22984a = -1;
    public long timestamp = 0;
    public String signature = "";
    public byte[] data = null;

    /* renamed from: b, reason: collision with root package name */
    public long f22985b = 0;

    public static boolean a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                com.umeng.ut.a.c.e.b("", "result", str, com.umeng.ccg.a.f22022x, str2);
                if (str2.equals(com.umeng.ut.b.a.a.a.a(com.umeng.ut.a.c.b.c(str).getBytes(), 2))) {
                    com.umeng.ut.a.c.e.m85a("", "signature is ok");
                    return true;
                }
                com.umeng.ut.a.c.e.m85a("", "signature is error");
            }
        } catch (Exception e2) {
            com.umeng.ut.a.c.e.m85a("", e2);
        }
        return false;
    }
}

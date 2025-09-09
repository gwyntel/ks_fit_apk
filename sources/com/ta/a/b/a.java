package com.ta.a.b;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public int f20006a = -1;
    public long timestamp = 0;

    /* renamed from: a, reason: collision with other field name */
    public String f57a = "";
    public byte[] data = null;

    /* renamed from: b, reason: collision with root package name */
    public long f20007b = 0;

    public static boolean a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                com.ta.a.c.f.b("", "result", str, com.umeng.ccg.a.f22022x, str2);
                if (str2.equals(com.ta.utdid2.a.a.a.encodeToString(com.ta.a.c.b.d(str).getBytes(), 2))) {
                    com.ta.a.c.f.m77a("", "signature is ok");
                    return true;
                }
                com.ta.a.c.f.m77a("", "signature is error");
            }
        } catch (Exception e2) {
            com.ta.a.c.f.m77a("", e2);
        }
        return false;
    }
}

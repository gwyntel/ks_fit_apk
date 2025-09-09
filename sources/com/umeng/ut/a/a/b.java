package com.umeng.ut.a.a;

import android.text.TextUtils;
import com.umeng.ut.a.c.d;
import com.umeng.ut.a.c.e;

/* loaded from: classes4.dex */
public class b {
    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(com.umeng.ut.b.a.a.a.m87a(d.b(str.getBytes()), 2), "UTF-8");
        } catch (Exception e2) {
            e.a("", e2, new Object[0]);
            return "";
        }
    }
}

package com.ta.utdid2.device;

import com.ta.a.c.f;

/* loaded from: classes4.dex */
public class e {
    public static boolean a(com.ta.a.b.a aVar) {
        String str;
        try {
            str = new String(aVar.data, "UTF-8");
        } catch (Exception e2) {
            Object[] objArr = {e2};
            str = "";
            f.m77a("", objArr);
        }
        if (com.ta.a.b.a.a(str, aVar.f57a)) {
            return b.a(b.a(str).f20039d);
        }
        return false;
    }
}

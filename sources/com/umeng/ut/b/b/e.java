package com.umeng.ut.b.b;

/* loaded from: classes4.dex */
public class e {
    public static boolean a(com.umeng.ut.a.b.a aVar) {
        String str;
        try {
            str = new String(aVar.data, "UTF-8");
        } catch (Exception e2) {
            Object[] objArr = {e2};
            str = "";
            com.umeng.ut.a.c.e.m85a("", objArr);
        }
        if (com.umeng.ut.a.b.a.a(str, aVar.signature)) {
            return b.a(b.a(str).f23011d);
        }
        return false;
    }
}

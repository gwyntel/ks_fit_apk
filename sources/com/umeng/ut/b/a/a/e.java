package com.umeng.ut.b.a.a;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes4.dex */
class e {
    static String get(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            com.umeng.ut.a.c.e.b("", e2, new Object[0]);
            return str2;
        }
    }
}

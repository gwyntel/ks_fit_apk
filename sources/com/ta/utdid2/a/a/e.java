package com.ta.utdid2.a.a;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.ta.a.c.f;

/* loaded from: classes4.dex */
public class e {
    public static String get(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e2) {
            f.b("", e2, new Object[0]);
            return str2;
        }
    }
}

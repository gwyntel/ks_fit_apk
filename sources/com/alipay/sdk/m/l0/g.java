package com.alipay.sdk.m.l0;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class g {
    public static String a(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception unused) {
            return str2;
        }
    }
}

package com.xiaomi.push;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes4.dex */
public class q {
    public static String a(String str, String str2) {
        try {
            return (String) C0472r.a(null, "android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(null, str, str2);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("SystemProperties.get: " + e2);
            return str2;
        }
    }
}

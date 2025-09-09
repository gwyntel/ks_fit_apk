package com.meizu.cloud.pushsdk.d;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes4.dex */
public class i {
    /* JADX WARN: Multi-variable type inference failed */
    public static String a(String str) {
        com.meizu.cloud.pushsdk.d.l.d dVarA = com.meizu.cloud.pushsdk.d.l.a.a("android.os.SystemProperties").a(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).a(str);
        if (dVarA.f19257a) {
            return (String) dVarA.f19258b;
        }
        return null;
    }
}

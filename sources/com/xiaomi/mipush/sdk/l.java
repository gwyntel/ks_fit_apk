package com.xiaomi.mipush.sdk;

import com.xiaomi.push.is;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<e, a> f23414a = new HashMap<>();

    static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f23415a;

        /* renamed from: b, reason: collision with root package name */
        public String f23416b;

        public a(String str, String str2) {
            this.f23415a = str;
            this.f23416b = str2;
        }
    }

    static {
        a(e.ASSEMBLE_PUSH_HUAWEI, new a("com.xiaomi.assemble.control.HmsPushManager", "newInstance"));
        a(e.ASSEMBLE_PUSH_FCM, new a("com.xiaomi.assemble.control.FCMPushManager", "newInstance"));
        a(e.ASSEMBLE_PUSH_COS, new a("com.xiaomi.assemble.control.COSPushManager", "newInstance"));
        a(e.ASSEMBLE_PUSH_FTOS, new a("com.xiaomi.assemble.control.FTOSPushManager", "newInstance"));
    }

    private static void a(e eVar, a aVar) {
        if (aVar != null) {
            f23414a.put(eVar, aVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static a m166a(e eVar) {
        return f23414a.get(eVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static is m167a(e eVar) {
        return is.AggregatePushSwitch;
    }

    public static au a(e eVar) {
        int i2 = m.f23417a[eVar.ordinal()];
        if (i2 == 1) {
            return au.UPLOAD_HUAWEI_TOKEN;
        }
        if (i2 == 2) {
            return au.UPLOAD_FCM_TOKEN;
        }
        if (i2 == 3) {
            return au.UPLOAD_COS_TOKEN;
        }
        if (i2 != 4) {
            return null;
        }
        return au.UPLOAD_FTOS_TOKEN;
    }
}

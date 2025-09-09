package com.alibaba.sdk.android.httpdns.e;

import android.util.Log;
import com.taobao.accs.utl.UtilityImpl;
import java.util.Random;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: t, reason: collision with root package name */
    private String f8850t;

    /* renamed from: com.alibaba.sdk.android.httpdns.e.a$a, reason: collision with other inner class name */
    private static final class C0026a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f8851a = new a();
    }

    private a() {
        try {
            Random random = new Random();
            char[] cArr = new char[12];
            for (int i2 = 0; i2 < 12; i2++) {
                cArr[i2] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62));
            }
            this.f8850t = new String(cArr);
        } catch (Exception e2) {
            Log.d("SessionTrackMgr", e2.getMessage(), e2);
        }
    }

    public static a a() {
        return C0026a.f8851a;
    }

    public String getSessionId() {
        return this.f8850t;
    }

    public String l() {
        int networkType = com.alibaba.sdk.android.httpdns.c.a.a().getNetworkType();
        return networkType != 1 ? networkType != 2 ? networkType != 3 ? networkType != 4 ? "unknown" : UtilityImpl.NET_TYPE_4G : UtilityImpl.NET_TYPE_3G : UtilityImpl.NET_TYPE_2G : "wifi";
    }
}

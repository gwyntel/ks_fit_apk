package com.umeng.analytics.pro;

import android.content.SharedPreferences;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;

/* loaded from: classes4.dex */
public class ah implements ac {

    /* renamed from: a, reason: collision with root package name */
    private String f21295a;

    /* renamed from: b, reason: collision with root package name */
    private long f21296b;

    public ah(String str, long j2) {
        this.f21295a = str;
        this.f21296b = j2;
    }

    @Override // com.umeng.analytics.pro.ac
    public boolean a() {
        try {
            String str = au.f21354b + this.f21295a;
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA == null) {
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis() - sharedPreferencesA.getLong(str, 0L);
            if (jCurrentTimeMillis > this.f21296b * 1000) {
                return true;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "internal period skipped. elapse: " + jCurrentTimeMillis + "; config: " + (this.f21296b * 1000));
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    @Override // com.umeng.analytics.pro.ac
    public boolean b() {
        return !a();
    }

    @Override // com.umeng.analytics.pro.ac
    public long c() {
        return 0L;
    }
}

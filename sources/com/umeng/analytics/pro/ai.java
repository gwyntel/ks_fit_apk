package com.umeng.analytics.pro;

import android.content.SharedPreferences;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;

/* loaded from: classes4.dex */
public class ai implements ac {

    /* renamed from: a, reason: collision with root package name */
    private int f21297a;

    public ai(int i2) {
        this.f21297a = i2;
    }

    @Override // com.umeng.analytics.pro.ac
    public boolean a() {
        long j2 = 0;
        try {
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA != null) {
                j2 = sharedPreferencesA.getLong(au.f21353a, 0L);
                if (j2 >= this.f21297a) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "launch times skipped. times: " + j2 + " ; config: " + this.f21297a);
        return false;
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

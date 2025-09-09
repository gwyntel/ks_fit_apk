package com.umeng.analytics.pro;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes4.dex */
class z implements y {

    /* renamed from: a, reason: collision with root package name */
    private long f21953a = AnalyticsConfig.kContinueSessionMillis;

    z() {
    }

    @Override // com.umeng.analytics.pro.y
    public void a(long j2) {
        this.f21953a = j2;
    }

    @Override // com.umeng.analytics.pro.y
    public long a() {
        return this.f21953a;
    }

    @Override // com.umeng.analytics.pro.y
    public String a(Context context) {
        String appkey = UMUtils.getAppkey(context);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (appkey != null) {
            return UMUtils.MD5(jCurrentTimeMillis + appkey + "02:00:00:00:00:00");
        }
        throw new RuntimeException("Appkey is null or empty, Please check!");
    }

    @Override // com.umeng.analytics.pro.y
    public boolean a(long j2, long j3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return (j2 == 0 || jCurrentTimeMillis - j2 >= this.f21953a) && j3 > 0 && jCurrentTimeMillis - j3 > this.f21953a;
    }

    @Override // com.umeng.analytics.pro.y
    public void a(Context context, String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            SharedPreferences.Editor editorEdit = PreferenceWrapper.getDefault(context).edit();
            editorEdit.putString("session_id", str);
            editorEdit.putLong(w.f21934b, 0L);
            editorEdit.putLong(w.f21937e, jCurrentTimeMillis);
            editorEdit.putLong(w.f21938f, 0L);
            editorEdit.commit();
        } catch (Exception unused) {
        }
    }
}

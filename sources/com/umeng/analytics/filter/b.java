package com.umeng.analytics.filter;

import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;

/* loaded from: classes4.dex */
public class b extends EventList {

    /* renamed from: a, reason: collision with root package name */
    private d f21259a;

    /* renamed from: b, reason: collision with root package name */
    private Object f21260b;

    public b(String str, String str2) {
        super(str, str2);
        this.f21260b = new Object();
    }

    @Override // com.umeng.analytics.filter.EventList
    protected void eventListChange() {
        if (TextUtils.isEmpty(this.mEventList)) {
            return;
        }
        synchronized (this.f21260b) {
            this.f21259a = null;
            this.f21259a = new d(true, this.mEventList);
        }
    }

    @Override // com.umeng.analytics.filter.EventList
    public boolean matchHit(String str) {
        boolean zA;
        if (TextUtils.isEmpty(this.mEventList)) {
            return true;
        }
        synchronized (this.f21260b) {
            try {
                if (this.f21259a == null) {
                    this.f21259a = new d(true, this.mEventList);
                }
                zA = this.f21259a.a(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return zA;
    }

    @Override // com.umeng.analytics.filter.EventList
    public void setMD5ClearFlag(boolean z2) {
        AnalyticsConfig.CLEAR_EKV_WL = z2;
    }
}

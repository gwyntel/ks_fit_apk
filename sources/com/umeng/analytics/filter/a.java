package com.umeng.analytics.filter;

import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;

/* loaded from: classes4.dex */
public class a extends EventList {

    /* renamed from: a, reason: collision with root package name */
    private d f21257a;

    /* renamed from: b, reason: collision with root package name */
    private Object f21258b;

    public a(String str, String str2) {
        super(str, str2);
        this.f21258b = new Object();
    }

    @Override // com.umeng.analytics.filter.EventList
    protected void eventListChange() {
        if (TextUtils.isEmpty(this.mEventList)) {
            return;
        }
        synchronized (this.f21258b) {
            this.f21257a = null;
            this.f21257a = new d(false, this.mEventList);
        }
    }

    @Override // com.umeng.analytics.filter.EventList
    public boolean matchHit(String str) {
        boolean zA;
        if (TextUtils.isEmpty(this.mEventList)) {
            return false;
        }
        synchronized (this.f21258b) {
            try {
                if (this.f21257a == null) {
                    this.f21257a = new d(false, this.mEventList);
                }
                zA = this.f21257a.a(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return zA;
    }

    @Override // com.umeng.analytics.filter.EventList
    public void setMD5ClearFlag(boolean z2) {
        AnalyticsConfig.CLEAR_EKV_BL = z2;
    }
}

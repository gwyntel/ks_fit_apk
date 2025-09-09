package com.huawei.hms.api;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class BindingFailedResolveMgr {

    /* renamed from: b, reason: collision with root package name */
    static final BindingFailedResolveMgr f15811b = new BindingFailedResolveMgr();

    /* renamed from: c, reason: collision with root package name */
    private static final Object f15812c = new Object();

    /* renamed from: a, reason: collision with root package name */
    List<Activity> f15813a = new ArrayList(1);

    BindingFailedResolveMgr() {
    }

    void a(Activity activity) {
        synchronized (f15812c) {
            try {
                for (Activity activity2 : this.f15813a) {
                    if (activity2 != null && activity2 != activity && !activity2.isFinishing()) {
                        activity2.finish();
                    }
                }
                this.f15813a.add(activity);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void b(Activity activity) {
        synchronized (f15812c) {
            this.f15813a.remove(activity);
        }
    }
}

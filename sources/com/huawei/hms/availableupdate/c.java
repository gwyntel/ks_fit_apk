package com.huawei.hms.availableupdate;

import android.app.Activity;
import com.huawei.hms.support.log.HMSLog;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    public static final c f15869b = new c();

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<Activity> f15870a;

    public boolean a(Activity activity) {
        HMSLog.i("UpdateAdapterMgr", "onActivityCreate");
        Activity activityA = a();
        if (activityA == null || activityA.isFinishing()) {
            this.f15870a = new WeakReference<>(activity);
            return true;
        }
        activity.finish();
        HMSLog.i("UpdateAdapterMgr", "finish one");
        return false;
    }

    public void b(Activity activity) {
        HMSLog.i("UpdateAdapterMgr", "onActivityDestroy");
        Activity activityA = a();
        if (activity == null || !activity.equals(activityA)) {
            return;
        }
        HMSLog.i("UpdateAdapterMgr", "reset");
        this.f15870a = null;
    }

    private Activity a() {
        WeakReference<Activity> weakReference = this.f15870a;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }
}

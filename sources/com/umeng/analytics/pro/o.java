package com.umeng.analytics.pro;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.umeng.commonsdk.debug.UMRTLog;
import java.util.ArrayList;

@TargetApi(14)
/* loaded from: classes4.dex */
public class o implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a, reason: collision with root package name */
    private static o f21845a = new o();

    /* renamed from: b, reason: collision with root package name */
    private final int f21846b = 3000;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21847c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f21848d = true;

    /* renamed from: e, reason: collision with root package name */
    private Handler f21849e = new Handler(Looper.getMainLooper());

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<p> f21850f = new ArrayList<>();

    /* renamed from: g, reason: collision with root package name */
    private a f21851g = new a();

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!o.this.f21847c || !o.this.f21848d) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> still foreground.");
                return;
            }
            o.this.f21847c = false;
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> went background.");
            for (int i2 = 0; i2 < o.this.f21850f.size(); i2++) {
                ((p) o.this.f21850f.get(i2)).n();
            }
        }
    }

    private o() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.f21848d = true;
        a aVar = this.f21851g;
        if (aVar != null) {
            this.f21849e.removeCallbacks(aVar);
            this.f21849e.postDelayed(this.f21851g, 3000L);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.f21848d = false;
        this.f21847c = true;
        a aVar = this.f21851g;
        if (aVar != null) {
            this.f21849e.removeCallbacks(aVar);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public synchronized void b(p pVar) {
        if (pVar != null) {
            for (int i2 = 0; i2 < this.f21850f.size(); i2++) {
                try {
                    if (this.f21850f.get(i2) == pVar) {
                        this.f21850f.remove(i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static void a(Context context) {
        if (context instanceof Application) {
            ((Application) context).registerActivityLifecycleCallbacks(f21845a);
        }
    }

    public static o a() {
        return f21845a;
    }

    public synchronized void a(p pVar) {
        if (pVar != null) {
            this.f21850f.add(pVar);
        }
    }
}

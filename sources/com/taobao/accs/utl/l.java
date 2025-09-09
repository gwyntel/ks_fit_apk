package com.taobao.accs.utl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.agoo.TaobaoRegister;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class l implements Application.ActivityLifecycleCallbacks {
    public static final int STATE_BACK = 0;
    public static final int STATE_FORE = 1;

    /* renamed from: a, reason: collision with root package name */
    private static final String f20396a = "l";

    /* renamed from: b, reason: collision with root package name */
    private static volatile l f20397b = null;

    /* renamed from: c, reason: collision with root package name */
    private static ArrayList<a> f20398c = null;

    /* renamed from: d, reason: collision with root package name */
    private static ArrayList<b> f20399d = null;

    /* renamed from: e, reason: collision with root package name */
    private static Application f20400e = null;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f20401j = false;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20403g;

    /* renamed from: f, reason: collision with root package name */
    private int f20402f = 0;

    /* renamed from: h, reason: collision with root package name */
    private int f20404h = 0;

    /* renamed from: i, reason: collision with root package name */
    private int f20405i = 1;

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private String f20406a;

        /* renamed from: b, reason: collision with root package name */
        private int f20407b;

        /* renamed from: c, reason: collision with root package name */
        private long f20408c = t.a(l.f20400e);

        public a(String str, int i2) {
            this.f20406a = str;
            this.f20407b = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            ALog.e(l.f20396a, "click report", "lastActiveTime", Long.valueOf(this.f20408c), "currentActiveTime", Long.valueOf(jCurrentTimeMillis));
            long j2 = this.f20408c;
            if (j2 == 0 || UtilityImpl.a(j2, jCurrentTimeMillis)) {
                this.f20407b |= 8;
            }
            TaobaoRegister.clickMessage(l.f20400e, this.f20406a, null, this.f20407b, this.f20408c);
        }
    }

    public interface b {
        void a();

        void b();
    }

    private l() {
        f20398c = new ArrayList<>();
        f20399d = new ArrayList<>();
    }

    public static l a() {
        if (f20397b == null) {
            synchronized (l.class) {
                try {
                    if (f20397b == null) {
                        f20397b = new l();
                    }
                } finally {
                }
            }
        }
        return f20397b;
    }

    public void b() {
        ArrayList<a> arrayList = f20398c;
        if (arrayList != null) {
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                ThreadPoolExecutorFactory.getScheduledExecutor().execute(it.next());
            }
            f20398c.clear();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        int i2 = this.f20402f;
        if ((i2 & 1) != 1) {
            this.f20402f = i2 | 3;
        } else if ((i2 & 2) == 2) {
            this.f20402f = i2 & (-3);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (this.f20403g) {
            t.a(f20400e, System.currentTimeMillis());
            if (!f20401j) {
                f20401j = true;
            }
        }
        this.f20403g = false;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        int i2 = this.f20404h;
        this.f20404h = i2 + 1;
        if (i2 == 0) {
            ALog.i(f20396a, "onActivityStarted back to force", new Object[0]);
            this.f20403g = true;
            this.f20405i = 1;
            ThreadPoolExecutorFactory.execute(new m(this));
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i2 = this.f20404h - 1;
        this.f20404h = i2;
        if (i2 == 0) {
            this.f20405i = 0;
            ThreadPoolExecutorFactory.execute(new n(this));
        }
    }

    public void a(b bVar) {
        if (bVar != null) {
            f20399d.add(bVar);
        }
    }
}

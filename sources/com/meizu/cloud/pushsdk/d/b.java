package com.meizu.cloud.pushsdk.d;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.common.util.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
class b implements f {

    /* renamed from: g, reason: collision with root package name */
    private String f19198g;

    /* renamed from: j, reason: collision with root package name */
    private ThreadPoolExecutor f19201j;

    /* renamed from: d, reason: collision with root package name */
    private long f19195d = 60;

    /* renamed from: e, reason: collision with root package name */
    private int f19196e = 10;

    /* renamed from: i, reason: collision with root package name */
    private boolean f19200i = false;

    /* renamed from: a, reason: collision with root package name */
    private final SimpleDateFormat f19192a = new SimpleDateFormat("MM-dd HH:mm:ss");

    /* renamed from: b, reason: collision with root package name */
    private final List<c> f19193b = Collections.synchronizedList(new ArrayList());

    /* renamed from: c, reason: collision with root package name */
    private final Handler f19194c = new Handler(Looper.getMainLooper());

    /* renamed from: f, reason: collision with root package name */
    private final e f19197f = new e();

    /* renamed from: h, reason: collision with root package name */
    private final String f19199h = String.valueOf(Process.myPid());

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            b.this.b(true);
        }
    }

    /* renamed from: com.meizu.cloud.pushsdk.d.b$b, reason: collision with other inner class name */
    class RunnableC0148b implements Runnable {
        RunnableC0148b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList<c> arrayList;
            synchronized (b.this.f19193b) {
                b.this.f19194c.removeCallbacksAndMessages(null);
                arrayList = new ArrayList(b.this.f19193b);
                b.this.f19193b.clear();
            }
            try {
                if (b.this.f19198g != null) {
                    b.this.f19197f.a(b.this.f19198g);
                    for (c cVar : arrayList) {
                        b.this.f19197f.a(cVar.f19204a, cVar.f19205b, cVar.f19206c);
                    }
                }
            } catch (Exception unused) {
            } catch (Throwable th) {
                try {
                    b.this.f19197f.a();
                } catch (Exception unused2) {
                }
                throw th;
            }
            try {
                b.this.f19197f.a();
            } catch (Exception unused3) {
            }
        }
    }

    class c {

        /* renamed from: a, reason: collision with root package name */
        final String f19204a;

        /* renamed from: b, reason: collision with root package name */
        final String f19205b;

        /* renamed from: c, reason: collision with root package name */
        final String f19206c;

        public c(String str, String str2, String str3) {
            this.f19204a = b.this.f19192a.format(new Date()) + " " + b.this.f19199h + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Thread.currentThread().getId() + " " + str + "/";
            this.f19205b = str2;
            this.f19206c = str3;
        }
    }

    public b() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new j().a("log-pool-%d").a());
        this.f19201j = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    private void a(c cVar) {
        try {
            this.f19193b.add(cVar);
        } catch (Exception e2) {
            Log.e(Logger.f16021b, "add logInfo error " + e2.getMessage());
        }
    }

    private void b() {
        if (this.f19193b.size() == this.f19196e) {
            b(true);
        }
    }

    private void c() {
        if (this.f19193b.size() == 0) {
            this.f19194c.postDelayed(new a(), this.f19195d * 1000);
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void d(String str, String str2) {
        if (this.f19200i) {
            Log.w(str, str2);
        }
        synchronized (this.f19193b) {
            c();
            a(new c(ExifInterface.LONGITUDE_WEST, str, str2));
            b();
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str) {
        this.f19198g = str;
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void b(String str, String str2) {
        if (this.f19200i) {
            Log.d(str, str2);
        }
        synchronized (this.f19193b) {
            c();
            a(new c("D", str, str2));
            b();
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void c(String str, String str2) {
        if (this.f19200i) {
            Log.i(str, str2);
        }
        synchronized (this.f19193b) {
            c();
            a(new c("I", str, str2));
            b();
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str, String str2) {
        if (this.f19200i) {
            Log.e(str, str2);
        }
        synchronized (this.f19193b) {
            c();
            a(new c(ExifInterface.LONGITUDE_EAST, str, str2));
            b();
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void b(boolean z2) {
        ThreadPoolExecutor threadPoolExecutor;
        RunnableC0148b runnableC0148b = new RunnableC0148b();
        if (!z2 || (threadPoolExecutor = this.f19201j) == null) {
            runnableC0148b.run();
        } else {
            threadPoolExecutor.execute(runnableC0148b);
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str, String str2, Throwable th) {
        if (this.f19200i) {
            Log.e(str, str2, th);
        }
        synchronized (this.f19193b) {
            c();
            a(new c(ExifInterface.LONGITUDE_EAST, str, str2 + "\n" + Log.getStackTraceString(th)));
            b();
        }
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(boolean z2) {
        this.f19200i = z2;
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public boolean a() {
        return this.f19200i;
    }
}

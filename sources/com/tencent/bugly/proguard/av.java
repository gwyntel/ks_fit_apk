package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.lang.Thread;

/* loaded from: classes4.dex */
public final class av implements Thread.UncaughtExceptionHandler {

    /* renamed from: h, reason: collision with root package name */
    private static String f20887h;

    /* renamed from: i, reason: collision with root package name */
    private static final Object f20888i = new Object();

    /* renamed from: a, reason: collision with root package name */
    protected final Context f20889a;

    /* renamed from: b, reason: collision with root package name */
    protected final as f20890b;

    /* renamed from: c, reason: collision with root package name */
    protected final ac f20891c;

    /* renamed from: d, reason: collision with root package name */
    protected final aa f20892d;

    /* renamed from: e, reason: collision with root package name */
    protected Thread.UncaughtExceptionHandler f20893e;

    /* renamed from: f, reason: collision with root package name */
    protected Thread.UncaughtExceptionHandler f20894f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f20895g = false;

    /* renamed from: j, reason: collision with root package name */
    private int f20896j;

    public av(Context context, as asVar, ac acVar, aa aaVar) {
        this.f20889a = context;
        this.f20890b = asVar;
        this.f20891c = acVar;
        this.f20892d = aaVar;
    }

    private static void c() {
        al.e("current process die", new Object[0]);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    public final synchronized void a() {
        if (this.f20896j >= 10) {
            al.a("java crash handler over %d, no need set.", 10);
            return;
        }
        this.f20895g = true;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            if (av.class.getName().equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                return;
            }
            if ("com.android.internal.os.RuntimeInit$UncaughtHandler".equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                al.a("backup system java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f20894f = defaultUncaughtExceptionHandler;
                this.f20893e = defaultUncaughtExceptionHandler;
            } else {
                al.a("backup java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f20893e = defaultUncaughtExceptionHandler;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f20896j++;
        al.a("registered java monitor: %s", toString());
    }

    public final synchronized void b() {
        this.f20895g = false;
        al.a("close java monitor!", new Object[0]);
        if ("bugly".equals(Thread.getDefaultUncaughtExceptionHandler().getClass().getName())) {
            al.a("Java monitor to unregister: %s", toString());
            Thread.setDefaultUncaughtExceptionHandler(this.f20893e);
            this.f20896j--;
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        synchronized (f20888i) {
            a(thread, th, true, null, null, this.f20892d.Q);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.tencent.bugly.crashreport.crash.CrashDetailBean b(java.lang.Thread r6, java.lang.Throwable r7, boolean r8, java.lang.String r9, byte[] r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.av.b(java.lang.Thread, java.lang.Throwable, boolean, java.lang.String, byte[], boolean):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    private static void a(CrashDetailBean crashDetailBean, Throwable th, boolean z2) {
        String strA;
        String name = th.getClass().getName();
        String strA2 = a(th);
        al.e("stack frame :%d, has cause %b", Integer.valueOf(th.getStackTrace().length), Boolean.valueOf(th.getCause() != null));
        String str = "";
        String string = th.getStackTrace().length > 0 ? th.getStackTrace()[0].toString() : "";
        Throwable cause = th;
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause != null && cause != th) {
            crashDetailBean.f20633n = cause.getClass().getName();
            crashDetailBean.f20634o = a(cause);
            if (cause.getStackTrace().length > 0) {
                crashDetailBean.f20635p = cause.getStackTrace()[0].toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":");
            sb.append(strA2);
            sb.append("\n");
            sb.append(string);
            sb.append("\n......");
            sb.append("\nCaused by:\n");
            sb.append(crashDetailBean.f20633n);
            sb.append(":");
            sb.append(crashDetailBean.f20634o);
            sb.append("\n");
            strA = a(cause, at.f20844h);
            sb.append(strA);
            crashDetailBean.f20636q = sb.toString();
        } else {
            crashDetailBean.f20633n = name;
            if (at.a().i() && z2) {
                al.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
                str = " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]";
            }
            crashDetailBean.f20634o = strA2 + str;
            crashDetailBean.f20635p = string;
            strA = a(th, at.f20844h);
            crashDetailBean.f20636q = strA;
        }
        crashDetailBean.f20640u = ap.c(crashDetailBean.f20636q.getBytes());
        crashDetailBean.f20645z.put(crashDetailBean.B, strA);
    }

    private static boolean a(Thread thread) {
        synchronized (f20888i) {
            try {
                if (f20887h != null && thread.getName().equals(f20887h)) {
                    return true;
                }
                f20887h = thread.getName();
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void a(Thread thread, Throwable th, boolean z2, String str, byte[] bArr, boolean z3) {
        if (z2) {
            al.e("Java Crash Happen cause by %s(%d)", thread.getName(), Long.valueOf(thread.getId()));
            if (a(thread)) {
                al.a("this class has handled this exception", new Object[0]);
                if (this.f20894f != null) {
                    al.a("call system handler", new Object[0]);
                    this.f20894f.uncaughtException(thread, th);
                } else {
                    c();
                }
            }
        } else {
            al.e("Java Catch Happen", new Object[0]);
        }
        try {
            if (!this.f20895g) {
                al.c("Java crash handler is disable. Just return.", new Object[0]);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f20893e;
                    if (uncaughtExceptionHandler != null && a(uncaughtExceptionHandler)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f20893e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f20894f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f20894f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            if (!this.f20891c.b()) {
                al.d("no remote but still store!", new Object[0]);
            }
            if (!this.f20891c.c().f20602f && this.f20891c.b()) {
                al.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                as.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", ap.a(), this.f20892d.f20679d, thread.getName(), ap.a(th), null);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.f20893e;
                    if (uncaughtExceptionHandler2 != null && a(uncaughtExceptionHandler2)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f20893e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f20894f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f20894f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            CrashDetailBean crashDetailBeanB = b(thread, th, z2, str, bArr, z3);
            if (crashDetailBeanB == null) {
                al.e("pkg crash datas fail!", new Object[0]);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.f20893e;
                    if (uncaughtExceptionHandler3 != null && a(uncaughtExceptionHandler3)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f20893e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f20894f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f20894f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            as.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", ap.a(), this.f20892d.f20679d, thread.getName(), ap.a(th), crashDetailBeanB);
            if (!this.f20890b.a(crashDetailBeanB, z2)) {
                this.f20890b.b(crashDetailBeanB, z2);
            }
            if (z2) {
                this.f20890b.a(crashDetailBeanB);
            }
            if (z2) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler4 = this.f20893e;
                if (uncaughtExceptionHandler4 != null && a(uncaughtExceptionHandler4)) {
                    al.e("sys default last handle start!", new Object[0]);
                    this.f20893e.uncaughtException(thread, th);
                    al.e("sys default last handle end!", new Object[0]);
                } else if (this.f20894f != null) {
                    al.e("system handle start!", new Object[0]);
                    this.f20894f.uncaughtException(thread, th);
                    al.e("system handle end!", new Object[0]);
                } else {
                    al.e("crashreport last handle start!", new Object[0]);
                    c();
                    al.e("crashreport last handle end!", new Object[0]);
                }
            }
        } catch (Throwable th2) {
            try {
                if (!al.a(th2)) {
                    th2.printStackTrace();
                }
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler5 = this.f20893e;
                    if (uncaughtExceptionHandler5 != null && a(uncaughtExceptionHandler5)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f20893e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                    } else if (this.f20894f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f20894f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                    }
                }
            } catch (Throwable th3) {
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler6 = this.f20893e;
                    if (uncaughtExceptionHandler6 != null && a(uncaughtExceptionHandler6)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f20893e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                    } else if (this.f20894f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f20894f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                    }
                }
                throw th3;
            }
        }
    }

    private static boolean a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (uncaughtExceptionHandler == null) {
            return true;
        }
        String name = uncaughtExceptionHandler.getClass().getName();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (name.equals(className) && "uncaughtException".equals(methodName)) {
                return false;
            }
        }
        return true;
    }

    public final synchronized void a(StrategyBean strategyBean) {
        if (strategyBean != null) {
            boolean z2 = strategyBean.f20602f;
            if (z2 != this.f20895g) {
                al.a("java changed to %b", Boolean.valueOf(z2));
                if (strategyBean.f20602f) {
                    a();
                    return;
                }
                b();
            }
        }
    }

    private static String a(Throwable th, int i2) {
        if (th == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (th.getStackTrace() != null) {
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    if (i2 > 0 && sb.length() >= i2) {
                        sb.append("\n[Stack over limit size :" + i2 + " , has been cutted !]");
                        return sb.toString();
                    }
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
        } catch (Throwable th2) {
            al.e("gen stack error %s", th2.toString());
        }
        return sb.toString();
    }

    private static String a(Throwable th) {
        String message = th.getMessage();
        if (message == null) {
            return "";
        }
        if (message.length() <= 1000) {
            return message;
        }
        return message.substring(0, 1000) + "\n[Message over limit size:1000, has been cutted!]";
    }
}

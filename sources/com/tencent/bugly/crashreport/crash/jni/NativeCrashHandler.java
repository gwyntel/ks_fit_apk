package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import anetwork.channel.util.RequestConstant;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.ab;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.be;
import com.tencent.bugly.proguard.q;
import com.umeng.message.common.inter.ITagManager;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes4.dex */
public class NativeCrashHandler implements q {

    /* renamed from: a, reason: collision with root package name */
    static String f20660a = null;

    /* renamed from: b, reason: collision with root package name */
    private static NativeCrashHandler f20661b = null;

    /* renamed from: c, reason: collision with root package name */
    private static int f20662c = 1;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f20663n = true;

    /* renamed from: d, reason: collision with root package name */
    private final Context f20664d;

    /* renamed from: e, reason: collision with root package name */
    private final aa f20665e;

    /* renamed from: f, reason: collision with root package name */
    private final ak f20666f;

    /* renamed from: g, reason: collision with root package name */
    private NativeExceptionHandler f20667g;

    /* renamed from: h, reason: collision with root package name */
    private final boolean f20668h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f20669i = false;

    /* renamed from: j, reason: collision with root package name */
    private boolean f20670j = false;

    /* renamed from: k, reason: collision with root package name */
    private boolean f20671k = false;

    /* renamed from: l, reason: collision with root package name */
    private boolean f20672l = false;

    /* renamed from: m, reason: collision with root package name */
    private as f20673m;

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, aa aaVar, as asVar, ak akVar, boolean z2, String str) {
        this.f20664d = ap.a(context);
        if (ap.b(f20660a)) {
            try {
                if (ap.b(str)) {
                    str = context.getDir("bugly", 0).getAbsolutePath();
                }
            } catch (Throwable unused) {
                str = "/data/data/" + aa.a(context).f20678c + "/app_bugly";
            }
            f20660a = str;
        }
        this.f20673m = asVar;
        this.f20665e = aaVar;
        this.f20666f = akVar;
        this.f20668h = z2;
        this.f20667g = new bd(context, aaVar, asVar, ac.a());
    }

    public static synchronized String getDumpFilePath() {
        return f20660a;
    }

    public static synchronized NativeCrashHandler getInstance(Context context, aa aaVar, as asVar, ac acVar, ak akVar, boolean z2, String str) {
        try {
            if (f20661b == null) {
                f20661b = new NativeCrashHandler(context, aaVar, asVar, akVar, z2, str);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f20661b;
    }

    private native String getProperties(String str);

    private native String getSoCpuAbi();

    public static boolean isShouldHandleInJava() {
        return f20663n;
    }

    public static synchronized void setDumpFilePath(String str) {
        f20660a = str;
    }

    public static void setShouldHandleInJava(boolean z2) {
        f20663n = z2;
        NativeCrashHandler nativeCrashHandler = f20661b;
        if (nativeCrashHandler != null) {
            nativeCrashHandler.a(999, String.valueOf(z2));
        }
    }

    @Override // com.tencent.bugly.proguard.q
    public boolean appendLogToNative(String str, String str2, String str3) {
        if ((this.f20669i || this.f20670j) && str != null && str2 != null && str3 != null) {
            try {
                if (this.f20670j) {
                    return appendNativeLog(str, str2, str3);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    protected native boolean appendNativeLog(String str, String str2, String str3);

    protected native boolean appendWholeNativeLog(String str);

    public void checkUploadRecordCrash() {
        this.f20666f.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                int i2;
                if (!ap.a(NativeCrashHandler.this.f20664d, "native_record_lock")) {
                    al.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.f20663n) {
                    NativeCrashHandler.a(NativeCrashHandler.this, RequestConstant.FALSE);
                }
                CrashDetailBean crashDetailBeanA = be.a(NativeCrashHandler.this.f20664d, NativeCrashHandler.f20660a, NativeCrashHandler.this.f20667g);
                if (crashDetailBeanA != null) {
                    al.a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.f20673m.a(crashDetailBeanA, true)) {
                        NativeCrashHandler.this.f20673m.b(crashDetailBeanA, false);
                    }
                    be.a(false, NativeCrashHandler.f20660a);
                }
                final NativeCrashHandler nativeCrashHandler = NativeCrashHandler.this;
                long jB = ap.b() - at.f20846j;
                long jB2 = ap.b() + 86400000;
                File file = new File(NativeCrashHandler.f20660a);
                if (file.exists() && file.isDirectory()) {
                    try {
                        File[] fileArrListFiles = file.listFiles();
                        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                            Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.2
                                @Override // java.util.Comparator
                                public final /* synthetic */ int compare(File file2, File file3) {
                                    return Long.compare(file3.lastModified(), file2.lastModified());
                                }
                            });
                            int length = fileArrListFiles.length;
                            long length2 = 0;
                            int i3 = 0;
                            int i4 = 0;
                            int i5 = 0;
                            while (i3 < length) {
                                File file2 = fileArrListFiles[i3];
                                long jLastModified = file2.lastModified();
                                length2 += file2.length();
                                if (jLastModified < jB || jLastModified >= jB2 || length2 >= at.f20845i) {
                                    i2 = length;
                                    al.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                                    i4++;
                                    if (file2.delete()) {
                                        i5++;
                                    }
                                } else {
                                    i2 = length;
                                }
                                i3++;
                                length = i2;
                            }
                            al.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i4), Integer.valueOf(i5));
                        }
                    } catch (Throwable th) {
                        al.a(th);
                    }
                }
                ap.b(NativeCrashHandler.this.f20664d, "native_record_lock");
            }
        });
    }

    public void disableCatchAnrTrace() {
        f20662c = 1;
    }

    public void dumpAnrNativeStack() {
        a(19, "1");
    }

    public void enableCatchAnrTrace() {
        f20662c |= 2;
    }

    public boolean filterSigabrtSysLog() {
        return a(998, "true");
    }

    @Override // com.tencent.bugly.proguard.q
    public String getLogFromNative() {
        if (!this.f20669i && !this.f20670j) {
            return null;
        }
        try {
            return this.f20670j ? getNativeLog() : (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null);
        } catch (UnsatisfiedLinkError unused) {
            return null;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.f20667g;
    }

    protected native String getNativeKeyValueList();

    protected native String getNativeLog();

    public String getRunningCpuAbi() {
        try {
            return getSoCpuAbi();
        } catch (Throwable unused) {
            al.d("get so cpu abi failed，please upgrade bugly so version", new Object[0]);
            return "";
        }
    }

    public String getSystemProperty(String str) {
        return (this.f20670j || this.f20669i) ? getProperties(str) : ITagManager.FAIL;
    }

    public boolean isEnableCatchAnrTrace() {
        return (f20662c & 2) == 2;
    }

    public synchronized boolean isUserOpened() {
        return this.f20672l;
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        if (strategyBean != null) {
            try {
                boolean z2 = strategyBean.f20602f;
                if (z2 != this.f20671k) {
                    al.d("server native changed to %b", Boolean.valueOf(z2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        boolean z3 = ac.a().c().f20602f && this.f20672l;
        if (z3 != this.f20671k) {
            al.a("native changed to %b", Boolean.valueOf(z3));
            b(z3);
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if ((this.f20669i || this.f20670j) && str != null && str2 != null) {
            try {
                if (this.f20670j) {
                    return putNativeKeyValue(str, str2);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", new Class[]{String.class, String.class}, new Object[]{str, str2});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    protected native boolean putNativeKeyValue(String str, String str2);

    protected native String regist(String str, boolean z2, int i2);

    public void removeEmptyNativeRecordFiles() {
        be.c(f20660a);
    }

    protected native String removeNativeKeyValue(String str);

    public void resendSigquit() {
        a(20, "");
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    protected native void setNativeInfo(int i2, String str);

    @Override // com.tencent.bugly.proguard.q
    public boolean setNativeIsAppForeground(boolean z2) {
        return a(14, z2 ? "true" : RequestConstant.FALSE);
    }

    public boolean setNativeLaunchTime(long j2) {
        try {
            return a(15, String.valueOf(j2));
        } catch (NumberFormatException e2) {
            if (al.a(e2)) {
                return false;
            }
            e2.printStackTrace();
            return false;
        }
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    public synchronized void setUserOpened(boolean z2) {
        try {
            c(z2);
            boolean zIsUserOpened = isUserOpened();
            ac acVarA = ac.a();
            if (acVarA != null) {
                zIsUserOpened = zIsUserOpened && acVarA.c().f20602f;
            }
            if (zIsUserOpened != this.f20671k) {
                al.a("native changed to %b", Boolean.valueOf(zIsUserOpened));
                b(zIsUserOpened);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void startNativeMonitor() {
        try {
            if (!this.f20670j && !this.f20669i) {
                boolean z2 = !ap.b(this.f20665e.f20695t);
                if (at.f20839b) {
                    boolean zA = a(z2 ? this.f20665e.f20695t : "Bugly_Native", z2);
                    this.f20670j = zA;
                    if (!zA && !z2) {
                        this.f20669i = a("NativeRQD", false);
                    }
                } else {
                    String str = "Bugly_Native";
                    aa aaVar = this.f20665e;
                    String str2 = aaVar.f20695t;
                    if (z2) {
                        str = str2;
                    } else {
                        aaVar.getClass();
                    }
                    this.f20670j = a(str, z2);
                }
                if (this.f20670j || this.f20669i) {
                    a(this.f20668h);
                    setNativeAppVersion(this.f20665e.f20690o);
                    setNativeAppChannel(this.f20665e.f20694s);
                    setNativeAppPackage(this.f20665e.f20678c);
                    setNativeUserId(this.f20665e.f());
                    setNativeIsAppForeground(this.f20665e.a());
                    setNativeLaunchTime(this.f20665e.f20676a);
                    return;
                }
                return;
            }
            a(this.f20668h);
        } catch (Throwable th) {
            throw th;
        }
    }

    protected native void testCrash();

    public void testNativeCrash() {
        if (this.f20670j) {
            testCrash();
        } else {
            al.d("[Native] Bugly SO file has not been load.", new Object[0]);
        }
    }

    public void unBlockSigquit(boolean z2) {
        if (z2) {
            a(21, "true");
        } else {
            a(21, RequestConstant.FALSE);
        }
    }

    protected native String unregist();

    private synchronized void c() {
        if (!this.f20671k) {
            al.d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
        } catch (Throwable unused) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
        }
        if (unregist() != null) {
            al.a("[Native] Successfully closed native crash report.", new Object[0]);
            this.f20671k = false;
            return;
        }
        try {
            ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.FALSE});
            this.f20671k = false;
            al.a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable unused2) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
            this.f20670j = false;
            this.f20669i = false;
            return;
        }
    }

    static /* synthetic */ boolean a(NativeCrashHandler nativeCrashHandler, String str) {
        return nativeCrashHandler.a(999, str);
    }

    private synchronized void b(boolean z2) {
        if (z2) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    private synchronized void a(boolean z2) {
        if (this.f20671k) {
            al.d("[Native] Native crash report has already registered.", new Object[0]);
            return;
        }
        if (this.f20670j) {
            try {
                String strRegist = regist(f20660a, z2, f20662c);
                if (strRegist != null) {
                    al.a("[Native] Native Crash Report enable.", new Object[0]);
                    this.f20665e.f20696u = strRegist;
                    String strConcat = Constants.ACCEPT_TIME_SEPARATOR_SERVER.concat(strRegist);
                    if (!at.f20839b && !this.f20665e.f20683h.contains(strConcat)) {
                        aa aaVar = this.f20665e;
                        aaVar.f20683h = aaVar.f20683h.concat(Constants.ACCEPT_TIME_SEPARATOR_SERVER).concat(this.f20665e.f20696u);
                    }
                    al.a("comInfo.sdkVersion %s", this.f20665e.f20683h);
                    this.f20671k = true;
                    String runningCpuAbi = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi)) {
                        this.f20665e.e(runningCpuAbi);
                    }
                    return;
                }
            } catch (Throwable unused) {
                al.c("[Native] Failed to load Bugly SO file.", new Object[0]);
            }
        } else if (this.f20669i) {
            try {
                Class cls = Integer.TYPE;
                String str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", new Class[]{String.class, String.class, cls, cls}, new Object[]{f20660a, ab.d(), Integer.valueOf(z2 ? 1 : 5), 1});
                if (str == null) {
                    String strD = ab.d();
                    aa.b();
                    str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", new Class[]{String.class, String.class, cls}, new Object[]{f20660a, strD, Integer.valueOf(aa.B())});
                }
                if (str != null) {
                    this.f20671k = true;
                    this.f20665e.f20696u = str;
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.TRUE});
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", new Class[]{cls}, new Object[]{Integer.valueOf(z2 ? 1 : 5)});
                    String runningCpuAbi2 = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi2)) {
                        this.f20665e.e(runningCpuAbi2);
                    }
                    return;
                }
            } catch (Throwable unused2) {
            }
        }
        this.f20670j = false;
        this.f20669i = false;
    }

    public static synchronized NativeCrashHandler getInstance() {
        return f20661b;
    }

    public void testNativeCrash(boolean z2, boolean z3, boolean z4) {
        a(16, String.valueOf(z2));
        a(17, String.valueOf(z3));
        a(18, String.valueOf(z4));
        testNativeCrash();
    }

    private synchronized void c(boolean z2) {
        if (this.f20672l != z2) {
            al.a("user change native %b", Boolean.valueOf(z2));
            this.f20672l = z2;
        }
    }

    private static boolean a(String str, boolean z2) {
        boolean z3;
        try {
            al.a("[Native] Trying to load so: %s", str);
            if (z2) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
        } catch (Throwable th) {
            th = th;
            z3 = false;
        }
        try {
            al.a("[Native] Successfully loaded SO: %s", str);
            return true;
        } catch (Throwable th2) {
            th = th2;
            z3 = true;
            al.d(th.getMessage(), new Object[0]);
            al.d("[Native] Failed to load so: %s", str);
            return z3;
        }
    }

    private boolean a(int i2, String str) {
        if (!this.f20670j) {
            return false;
        }
        try {
            setNativeInfo(i2, str);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }
}

package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public final class ao {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f20779a = true;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f20780b = true;

    /* renamed from: c, reason: collision with root package name */
    private static SimpleDateFormat f20781c = null;

    /* renamed from: d, reason: collision with root package name */
    private static int f20782d = 30720;

    /* renamed from: e, reason: collision with root package name */
    private static StringBuilder f20783e = null;

    /* renamed from: f, reason: collision with root package name */
    private static StringBuilder f20784f = null;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f20785g = false;

    /* renamed from: h, reason: collision with root package name */
    private static a f20786h = null;

    /* renamed from: i, reason: collision with root package name */
    private static String f20787i = null;

    /* renamed from: j, reason: collision with root package name */
    private static String f20788j = null;

    /* renamed from: k, reason: collision with root package name */
    private static Context f20789k = null;

    /* renamed from: l, reason: collision with root package name */
    private static String f20790l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f20791m = false;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f20792n = false;

    /* renamed from: o, reason: collision with root package name */
    private static ExecutorService f20793o;

    /* renamed from: p, reason: collision with root package name */
    private static int f20794p;

    /* renamed from: q, reason: collision with root package name */
    private static final Object f20795q = new Object();

    static {
        try {
            f20781c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            al.b(th.getCause());
        }
    }

    public static synchronized void a(Context context) {
        if (f20791m || context == null || !f20780b) {
            return;
        }
        try {
            f20793o = Executors.newSingleThreadExecutor();
            f20784f = new StringBuilder(0);
            f20783e = new StringBuilder(0);
            f20789k = context;
            f20787i = aa.a(context).f20679d;
            f20788j = "";
            f20790l = f20789k.getFilesDir().getPath() + "/buglylog_" + f20787i + OpenAccountUIConstants.UNDER_LINE + f20788j + ".txt";
            f20794p = Process.myPid();
        } catch (Throwable unused) {
        }
        f20791m = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(String str, String str2, String str3) {
        q qVar;
        try {
            aa aaVarB = aa.b();
            if (aaVarB == null || (qVar = aaVarB.N) == null) {
                return false;
            }
            return qVar.appendLogToNative(str, str2, str3);
        } catch (Throwable th) {
            if (al.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void e(String str, String str2, String str3) {
        if (f20779a) {
            f(str, str2, str3);
        } else {
            g(str, str2, str3);
        }
    }

    private static synchronized void f(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (f20795q) {
            try {
                f20784f.append(strA);
            } finally {
                try {
                } catch (Throwable th) {
                }
            }
            if (f20784f.length() >= f20782d) {
                StringBuilder sb = f20784f;
                f20784f = sb.delete(0, sb.indexOf("\u0001\r\n") + 1);
            }
        }
    }

    private static synchronized void g(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (f20795q) {
            try {
                f20784f.append(strA);
            } catch (Throwable unused) {
            }
            if (f20784f.length() <= f20782d) {
                return;
            }
            if (f20785g) {
                return;
            }
            f20785g = true;
            a aVar = f20786h;
            if (aVar == null) {
                f20786h = new a(f20790l);
            } else {
                File file = aVar.f20803b;
                if (file == null || file.length() + f20784f.length() > f20786h.f20804c) {
                    f20786h.a();
                }
            }
            if (f20786h.a(f20784f.toString())) {
                f20784f.setLength(0);
                f20785g = false;
            }
        }
    }

    private static String b() {
        q qVar;
        try {
            aa aaVarB = aa.b();
            if (aaVarB == null || (qVar = aaVarB.N) == null) {
                return null;
            }
            return qVar.getLogFromNative();
        } catch (Throwable th) {
            if (al.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static byte[] c() {
        File file;
        if (!f20780b) {
            return null;
        }
        if (f20792n) {
            al.a("[LogUtil] Get user log from native.", new Object[0]);
            String strB = b();
            if (strB != null) {
                al.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(strB.length()));
                return ap.a(strB, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (f20795q) {
            try {
                a aVar = f20786h;
                if (aVar != null && aVar.f20802a && (file = aVar.f20803b) != null && file.length() > 0) {
                    sb.append(ap.a(f20786h.f20803b, 30720, true));
                }
                StringBuilder sb2 = f20784f;
                if (sb2 != null && sb2.length() > 0) {
                    sb.append(f20784f.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return ap.a(sb.toString(), "BuglyLog.txt");
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        boolean f20802a;

        /* renamed from: b, reason: collision with root package name */
        File f20803b;

        /* renamed from: c, reason: collision with root package name */
        long f20804c = 30720;

        /* renamed from: d, reason: collision with root package name */
        private String f20805d;

        /* renamed from: e, reason: collision with root package name */
        private long f20806e;

        public a(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            this.f20805d = str;
            this.f20802a = a();
        }

        final boolean a() {
            try {
                File file = new File(this.f20805d);
                this.f20803b = file;
                if (file.exists() && !this.f20803b.delete()) {
                    this.f20802a = false;
                    return false;
                }
                if (this.f20803b.createNewFile()) {
                    return true;
                }
                this.f20802a = false;
                return false;
            } catch (Throwable th) {
                al.a(th);
                this.f20802a = false;
                return false;
            }
        }

        public final boolean a(String str) throws IOException {
            FileOutputStream fileOutputStream;
            if (!this.f20802a) {
                return false;
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(this.f20803b, true);
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileOutputStream.write(str.getBytes("UTF-8"));
                fileOutputStream.flush();
                fileOutputStream.close();
                this.f20806e += r10.length;
                this.f20802a = true;
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                try {
                    al.a(th);
                    this.f20802a = false;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th3;
                }
            }
        }
    }

    public static void a(int i2) {
        synchronized (f20795q) {
            try {
                f20782d = i2;
                if (i2 < 0) {
                    f20782d = 0;
                } else if (i2 > 30720) {
                    f20782d = 30720;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th == null) {
            return;
        }
        String message = th.getMessage();
        if (message == null) {
            message = "";
        }
        a(str, str2, message + '\n' + ap.b(th));
    }

    public static synchronized void a(final String str, final String str2, final String str3) {
        if (f20791m && f20780b) {
            try {
                if (f20792n) {
                    f20793o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ao.d(str, str2, str3);
                        }
                    });
                } else {
                    f20793o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ao.e(str, str2, str3);
                        }
                    });
                }
            } catch (Exception e2) {
                al.b(e2);
            }
        }
    }

    private static String a(String str, String str2, String str3, long j2) {
        String string;
        f20783e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = f20781c;
        if (simpleDateFormat != null) {
            string = simpleDateFormat.format(date);
        } else {
            string = date.toString();
        }
        StringBuilder sb = f20783e;
        sb.append(string);
        sb.append(" ");
        sb.append(f20794p);
        sb.append(" ");
        sb.append(j2);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append("\u0001\r\n");
        return f20783e.toString();
    }

    public static byte[] a() {
        if (f20779a) {
            if (f20780b) {
                return ap.a(f20784f.toString(), "BuglyLog.txt");
            }
            return null;
        }
        return c();
    }
}

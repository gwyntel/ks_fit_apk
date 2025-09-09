package com.huawei.hms.common.util;

import android.annotation.SuppressLint;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class Logger {

    /* renamed from: a, reason: collision with root package name */
    public static final boolean f16020a = false;

    /* renamed from: b, reason: collision with root package name */
    public static final String f16021b = "Logger";

    /* renamed from: c, reason: collision with root package name */
    public static final String f16022c = "|";

    /* renamed from: d, reason: collision with root package name */
    public static final int f16023d = 8;

    /* renamed from: e, reason: collision with root package name */
    public static final int f16024e = 20;

    /* renamed from: f, reason: collision with root package name */
    public static final String f16025f = "dynamic-api_";

    public static class b extends Throwable {

        /* renamed from: d, reason: collision with root package name */
        public static final long f16026d = 7129050843360571879L;

        /* renamed from: a, reason: collision with root package name */
        public String f16027a;

        /* renamed from: b, reason: collision with root package name */
        public Throwable f16028b;

        /* renamed from: c, reason: collision with root package name */
        public Throwable f16029c;

        public b(Throwable th) {
            this.f16029c = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            Throwable th = this.f16028b;
            if (th == this) {
                return null;
            }
            return th;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            return this.f16027a;
        }

        @Override // java.lang.Throwable
        public String toString() {
            Throwable th = this.f16029c;
            if (th == null) {
                return "";
            }
            String name = th.getClass().getName();
            if (this.f16027a == null) {
                return name;
            }
            String str = name + ": ";
            if (this.f16027a.startsWith(str)) {
                return this.f16027a;
            }
            return str + this.f16027a;
        }

        public void a(String str) {
            this.f16027a = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Throwable th) {
            this.f16028b = th;
        }
    }

    public static int a(int i2, String str, String str2) {
        return Log.println(i2, a(str), a(str2, 7));
    }

    public static String anonymizeMessage(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (i2 % 2 == 1) {
                charArray[i2] = '*';
            }
        }
        return new String(charArray);
    }

    @SuppressLint({"LogTagMismatch"})
    public static void d(String str, Object obj) {
        println(3, str, obj);
    }

    public static void e(String str, Object obj) {
        println(6, str, obj);
    }

    public static String format(String str, Object... objArr) {
        return str == null ? "" : String.format(Locale.ROOT, str, objArr);
    }

    @SuppressLint({"LogTagMismatch"})
    public static void i(String str, Object obj) {
        println(4, str, obj);
    }

    public static boolean isLoggable(int i2) {
        return Log.isLoggable(f16025f, i2);
    }

    public static void println(int i2, String str, Object obj) {
        if (i2 >= 3 && isLoggable(i2)) {
            a(i2, str, obj == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj.toString());
        }
    }

    public static void v(String str, Object obj) {
        println(2, str, obj);
    }

    public static void w(String str, Object obj) {
        println(5, str, obj);
    }

    public static String a(int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length <= i2) {
            return "";
        }
        StackTraceElement stackTraceElement = stackTrace[i2];
        return Process.myPid() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + Process.myTid() + "|" + stackTraceElement.getFileName() + "|" + stackTraceElement.getClassName() + "|" + stackTraceElement.getMethodName() + "|" + stackTraceElement.getLineNumber();
    }

    @SuppressLint({"LogTagMismatch"})
    public static void d(String str, String str2, Object... objArr) {
        println(3, str, str2, objArr);
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(a(str), a(str2, 5), a(th));
    }

    @SuppressLint({"LogTagMismatch"})
    public static void i(String str, String str2, Object... objArr) {
        println(4, str, str2, objArr);
    }

    public static void println(int i2, String str, String str2, Object... objArr) {
        if (i2 < 3) {
            return;
        }
        if (str2 == null) {
            Log.w(f16021b, "format is null, not log");
            return;
        }
        try {
            if (isLoggable(i2)) {
                a(i2, str, format(str2, objArr));
            }
        } catch (IllegalFormatException e2) {
            w(f16021b, "log format error" + str2, e2);
        }
    }

    public static void v(String str, String str2, Object... objArr) {
        println(2, str, str2, objArr);
    }

    public static void w(String str, String str2, Throwable th) {
        Log.w(a(str), a(str2, 5), a(th));
    }

    public static String a(String str) {
        return f16025f + str;
    }

    public static void e(String str, String str2, Object... objArr) {
        println(6, str, str2, objArr);
    }

    public static void w(String str, String str2, Object... objArr) {
        println(5, str, str2, objArr);
    }

    public static String a(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return a(i2);
        }
        return a(i2) + "|" + str;
    }

    public static Throwable a(Throwable th) {
        if (isLoggable(3)) {
            return th;
        }
        if (th == null) {
            return null;
        }
        int i2 = ((th instanceof IOException) || (th instanceof JSONException)) ? 8 : 20;
        b bVar = new b(th);
        StackTraceElement[] stackTrace = bVar.getStackTrace();
        if (stackTrace.length > i2) {
            bVar.setStackTrace((StackTraceElement[]) Arrays.copyOf(stackTrace, i2));
        } else {
            bVar.setStackTrace(stackTrace);
        }
        bVar.a(anonymizeMessage(th.getMessage()));
        Throwable cause = th.getCause();
        b bVar2 = bVar;
        while (cause != null) {
            b bVar3 = new b(cause);
            bVar3.a(anonymizeMessage(cause.getMessage()));
            bVar2.a(bVar3);
            cause = cause.getCause();
            bVar2 = bVar3;
        }
        return bVar;
    }
}

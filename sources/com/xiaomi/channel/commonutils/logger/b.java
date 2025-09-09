package com.xiaomi.channel.commonutils.logger;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.xiaomi.push.j;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class b {

    /* renamed from: a, reason: collision with root package name */
    private static int f23336a = 2;

    /* renamed from: a, reason: collision with other field name */
    private static Context f76a = null;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f82a = false;

    /* renamed from: b, reason: collision with other field name */
    private static boolean f83b = false;

    /* renamed from: a, reason: collision with other field name */
    private static String f79a = "XMPush-" + Process.myPid();

    /* renamed from: a, reason: collision with other field name */
    private static LoggerInterface f77a = new a();

    /* renamed from: a, reason: collision with other field name */
    private static final HashMap<Integer, Long> f80a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static final HashMap<Integer, String> f23337b = new HashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private static final Integer f78a = -1;

    /* renamed from: a, reason: collision with other field name */
    private static AtomicInteger f81a = new AtomicInteger(1);

    static class a implements LoggerInterface {

        /* renamed from: a, reason: collision with root package name */
        private String f23338a = b.f79a;

        a() {
        }

        @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
        public void log(String str) {
            Log.v(this.f23338a, str);
        }

        @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
        public void setTag(String str) {
            this.f23338a = str;
        }

        @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
        public void log(String str, Throwable th) {
            Log.v(this.f23338a, str, th);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m95a() {
        return false;
    }

    public static void b(String str) {
        if (m96a(0)) {
            a(0, m90a(str));
        }
    }

    public static void c(String str) {
        if (m96a(0)) {
            a(1, m90a(str));
        }
    }

    public static void d(String str) {
        if (m96a(4)) {
            a(4, m90a(str));
        }
    }

    public static void e(String str) {
        if (f82a) {
            m91a(str);
            return;
        }
        Log.w(f79a, m90a(str));
        if (f83b) {
            return;
        }
        m91a(str);
    }

    public static void a(LoggerInterface loggerInterface) {
        f77a = loggerInterface;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static void m97b(String str, String str2) {
        if (m96a(1)) {
            a(1, b(str, str2));
        }
    }

    public static void c(String str, String str2) {
        if (f82a) {
            m92a(str, str2);
            return;
        }
        Log.w(f79a, b(str, str2));
        if (f83b) {
            return;
        }
        m92a(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m96a(int i2) {
        return i2 >= f23336a || m95a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m91a(String str) {
        if (m96a(2)) {
            a(2, m90a(str));
        }
    }

    public static void b(String str, Object... objArr) {
        if (m96a(1)) {
            a(1, a(str, objArr));
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m92a(String str, String str2) {
        if (m96a(2)) {
            a(2, b(str, str2));
        }
    }

    private static String b(String str, String str2) {
        return b() + a(str, str2);
    }

    private static String b() {
        return "[Tid:" + Thread.currentThread().getId() + "] ";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m93a(String str, Object... objArr) {
        if (m96a(2)) {
            a(2, a(str, objArr));
        }
    }

    public static void a(String str, Throwable th) {
        if (m96a(4)) {
            a(4, m90a(str), th);
        }
    }

    public static void a(Throwable th) {
        if (m96a(4)) {
            a(4, th);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m94a(Object... objArr) {
        if (m96a(4)) {
            a(4, a(objArr));
        }
    }

    public static Integer a(String str) {
        if (f23336a <= 1) {
            Integer numValueOf = Integer.valueOf(f81a.incrementAndGet());
            f80a.put(numValueOf, Long.valueOf(System.currentTimeMillis()));
            f23337b.put(numValueOf, str);
            f77a.log(str + " starts");
            return numValueOf;
        }
        return f78a;
    }

    public static void a(Integer num) {
        if (f23336a <= 1) {
            HashMap<Integer, Long> map = f80a;
            if (map.containsKey(num)) {
                long jLongValue = map.remove(num).longValue();
                String strRemove = f23337b.remove(num);
                long jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
                f77a.log(strRemove + " ends in " + jCurrentTimeMillis + " ms");
            }
        }
    }

    public static void a(int i2, String str) {
        if (i2 >= f23336a) {
            f77a.log(str);
            return;
        }
        if (m95a()) {
            Log.d("MyLog", "-->log(" + i2 + "): " + str);
        }
    }

    public static void a(int i2, Throwable th) {
        if (i2 >= f23336a) {
            f77a.log("", th);
            return;
        }
        if (m95a()) {
            Log.w("MyLog", "-->log(" + i2 + "): ", th);
        }
    }

    public static void a(int i2, String str, Throwable th) {
        if (i2 >= f23336a) {
            f77a.log(str, th);
            return;
        }
        if (m95a()) {
            Log.w("MyLog", "-->log(" + i2 + "): " + str, th);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String m90a(String str) {
        return b() + str;
    }

    private static String a(Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Tid:");
        sb.append(Thread.currentThread().getId());
        sb.append("] ");
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                sb.append(obj);
            }
        }
        return sb.toString();
    }

    private static String a(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Tid:");
        sb.append(Thread.currentThread().getId());
        sb.append("] ");
        sb.append("[");
        sb.append(str);
        sb.append("] ");
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                sb.append(obj);
            }
        }
        return sb.toString();
    }

    public static String a(String str, String str2) {
        return "[" + str + "] " + str2;
    }

    public static void a(int i2) {
        if (i2 < 0 || i2 > 5) {
            a(2, "set log level as " + i2);
        }
        f23336a = i2;
    }

    public static int a() {
        return f23336a;
    }

    public static void a(Context context) {
        f76a = context;
        if (j.m550a(context)) {
            f82a = true;
        }
        if (j.m549a()) {
            f83b = true;
        }
    }
}

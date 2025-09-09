package com.xiaomi.push;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes4.dex */
class as implements ar, InvocationHandler {

    /* renamed from: a, reason: collision with root package name */
    private static final String[][] f23446a = {new String[]{"com.bun.supplier.IIdentifierListener", "com.bun.supplier.IdSupplier"}, new String[]{"com.bun.miitmdid.core.IIdentifierListener", "com.bun.miitmdid.supplier.IdSupplier"}};

    /* renamed from: a, reason: collision with other field name */
    private Context f184a;

    /* renamed from: a, reason: collision with other field name */
    private Class f186a = null;

    /* renamed from: b, reason: collision with root package name */
    private Class f23447b = null;

    /* renamed from: a, reason: collision with other field name */
    private Method f188a = null;

    /* renamed from: b, reason: collision with other field name */
    private Method f189b = null;

    /* renamed from: c, reason: collision with root package name */
    private Method f23448c = null;

    /* renamed from: d, reason: collision with root package name */
    private Method f23449d = null;

    /* renamed from: e, reason: collision with root package name */
    private Method f23450e = null;

    /* renamed from: f, reason: collision with root package name */
    private Method f23451f = null;

    /* renamed from: g, reason: collision with root package name */
    private Method f23452g = null;

    /* renamed from: a, reason: collision with other field name */
    private final Object f187a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private volatile int f182a = 0;

    /* renamed from: a, reason: collision with other field name */
    private volatile long f183a = 0;

    /* renamed from: a, reason: collision with other field name */
    private volatile a f185a = null;

    private class a {

        /* renamed from: a, reason: collision with other field name */
        Boolean f190a;

        /* renamed from: a, reason: collision with other field name */
        String f191a;

        /* renamed from: b, reason: collision with root package name */
        String f23454b;

        /* renamed from: c, reason: collision with root package name */
        String f23455c;

        /* renamed from: d, reason: collision with root package name */
        String f23456d;

        private a() {
            this.f190a = null;
            this.f191a = null;
            this.f23454b = null;
            this.f23455c = null;
            this.f23456d = null;
        }

        boolean a() {
            if (!TextUtils.isEmpty(this.f191a) || !TextUtils.isEmpty(this.f23454b) || !TextUtils.isEmpty(this.f23455c) || !TextUtils.isEmpty(this.f23456d)) {
                this.f190a = Boolean.TRUE;
            }
            return this.f190a != null;
        }
    }

    public as(Context context) {
        this.f184a = context.getApplicationContext();
        a(context);
        b(context);
    }

    private void b(Context context) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = -jElapsedRealtime;
        Class cls = this.f23447b;
        if (cls != null) {
            try {
                ClassLoader classLoader = cls.getClassLoader();
                if (classLoader == null) {
                    classLoader = context.getClassLoader();
                }
                a(this.f188a, this.f186a.newInstance(), context, Proxy.newProxyInstance(classLoader, new Class[]{this.f23447b}, this));
            } catch (Throwable th) {
                b("call init sdk error:" + th);
            }
        } else {
            jElapsedRealtime = j2;
        }
        this.f183a = jElapsedRealtime;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public boolean mo180a() {
        a("isSupported");
        return this.f185a != null && Boolean.TRUE.equals(this.f185a.f190a);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        this.f183a = SystemClock.elapsedRealtime();
        if (objArr != null) {
            a aVar = new a();
            int length = objArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Object obj2 = objArr[i2];
                if (obj2 != null && !a(obj2)) {
                    aVar.f23454b = (String) a(this.f23448c, obj2, new Object[0]);
                    aVar.f190a = (Boolean) a(this.f23451f, obj2, new Object[0]);
                    a(this.f23452g, obj2, new Object[0]);
                    if (aVar.a()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("has get succ, check duplicate:");
                        sb.append(this.f185a != null);
                        b(sb.toString());
                        synchronized (as.class) {
                            try {
                                if (this.f185a == null) {
                                    this.f185a = aVar;
                                }
                            } finally {
                            }
                        }
                    }
                }
                i2++;
            }
        }
        a();
        return null;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public String mo179a() {
        a("getOAID");
        if (this.f185a == null) {
            return null;
        }
        return this.f185a.f23454b;
    }

    private void a(Context context) {
        Class<?> clsA = a(context, "com.bun.miitmdid.core.MdidSdk");
        Class<?> cls = null;
        Class<?> cls2 = null;
        int i2 = 0;
        while (true) {
            String[][] strArr = f23446a;
            if (i2 >= strArr.length) {
                break;
            }
            String[] strArr2 = strArr[i2];
            Class<?> clsA2 = a(context, strArr2[0]);
            Class<?> clsA3 = a(context, strArr2[1]);
            if (clsA2 != null && clsA3 != null) {
                b("found class in index " + i2);
                cls2 = clsA3;
                cls = clsA2;
                break;
            }
            i2++;
            cls2 = clsA3;
            cls = clsA2;
        }
        this.f186a = clsA;
        this.f188a = a(clsA, "InitSdk", (Class<?>[]) new Class[]{Context.class, cls});
        this.f23447b = cls;
        this.f23448c = a(cls2, "getOAID", (Class<?>[]) new Class[0]);
        this.f23451f = a(cls2, "isSupported", (Class<?>[]) new Class[0]);
        this.f23452g = a(cls2, "shutDown", (Class<?>[]) new Class[0]);
    }

    private static void b(String str) {
        com.xiaomi.channel.commonutils.logger.b.m91a("mdid:" + str);
    }

    private void a(String str) {
        if (this.f185a != null) {
            return;
        }
        long j2 = this.f183a;
        long jElapsedRealtime = SystemClock.elapsedRealtime() - Math.abs(j2);
        int i2 = this.f182a;
        if (jElapsedRealtime > 3000 && i2 < 3) {
            synchronized (this.f187a) {
                try {
                    if (this.f183a == j2 && this.f182a == i2) {
                        b("retry, current count is " + i2);
                        this.f182a = this.f182a + 1;
                        b(this.f184a);
                        j2 = this.f183a;
                        jElapsedRealtime = SystemClock.elapsedRealtime() - Math.abs(j2);
                    }
                } finally {
                }
            }
        }
        if (this.f185a != null || j2 < 0 || jElapsedRealtime > 3000 || Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        synchronized (this.f187a) {
            if (this.f185a == null) {
                try {
                    b(str + " wait...");
                    this.f187a.wait(3000L);
                } catch (Exception unused) {
                }
            }
        }
    }

    private void a() {
        synchronized (this.f187a) {
            try {
                this.f187a.notifyAll();
            } catch (Exception unused) {
            }
        }
    }

    private static boolean a(Object obj) {
        return (obj instanceof Boolean) || (obj instanceof Character) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double);
    }

    private static Class<?> a(Context context, String str) {
        try {
            return C0472r.a(context, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method a(Class<?> cls, String str, Class<?>... clsArr) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod(str, clsArr);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static <T> T a(Method method, Object obj, Object... objArr) {
        if (method == null) {
            return null;
        }
        try {
            T t2 = (T) method.invoke(obj, objArr);
            if (t2 != null) {
                return t2;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}

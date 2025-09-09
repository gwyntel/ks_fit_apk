package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ig {

    /* renamed from: a, reason: collision with other field name */
    private static ak f568a = new ak(true);

    /* renamed from: a, reason: collision with root package name */
    private static volatile int f23981a = -1;

    /* renamed from: a, reason: collision with other field name */
    private static long f567a = System.currentTimeMillis();

    /* renamed from: a, reason: collision with other field name */
    private static final Object f570a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static List<a> f572a = Collections.synchronizedList(new ArrayList());

    /* renamed from: a, reason: collision with other field name */
    private static String f571a = "";

    /* renamed from: a, reason: collision with other field name */
    private static com.xiaomi.push.providers.a f569a = null;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f23982a;

        /* renamed from: a, reason: collision with other field name */
        public long f573a;

        /* renamed from: a, reason: collision with other field name */
        public String f574a;

        /* renamed from: b, reason: collision with root package name */
        public int f23983b;

        /* renamed from: b, reason: collision with other field name */
        public long f575b;

        /* renamed from: b, reason: collision with other field name */
        public String f576b;

        public a(String str, long j2, int i2, int i3, String str2, long j3) {
            this.f574a = str;
            this.f573a = j2;
            this.f23982a = i2;
            this.f23983b = i3;
            this.f576b = str2;
            this.f575b = j3;
        }

        public boolean a(a aVar) {
            return TextUtils.equals(aVar.f574a, this.f574a) && TextUtils.equals(aVar.f576b, this.f576b) && aVar.f23982a == this.f23982a && aVar.f23983b == this.f23983b && Math.abs(aVar.f573a - this.f573a) <= 5000;
        }
    }

    private static int b(Context context) {
        bj bjVarM201a = bg.m201a();
        if (bjVarM201a == null) {
            return -1;
        }
        return bjVarM201a.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, List<a> list) {
        SQLiteDatabase writableDatabase;
        try {
            synchronized (com.xiaomi.push.providers.a.f937a) {
                try {
                    writableDatabase = m506a(context).getWritableDatabase();
                    writableDatabase.beginTransaction();
                    for (a aVar : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("package_name", aVar.f574a);
                        contentValues.put("message_ts", Long.valueOf(aVar.f573a));
                        contentValues.put(com.umeng.analytics.pro.bc.T, Integer.valueOf(aVar.f23982a));
                        contentValues.put("bytes", Long.valueOf(aVar.f575b));
                        contentValues.put("rcv", Integer.valueOf(aVar.f23983b));
                        contentValues.put(Constants.KEY_IMSI, aVar.f576b);
                        writableDatabase.insert(com.umeng.analytics.pro.f.F, null, contentValues);
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                } finally {
                }
            }
        } catch (Throwable th2) {
            com.xiaomi.channel.commonutils.logger.b.a(th2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m509a(Context context) {
        f23981a = b(context);
    }

    public static int a(Context context) {
        if (f23981a == -1) {
            f23981a = b(context);
        }
        return f23981a;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static synchronized String m507a(Context context) {
        if (!TextUtils.isEmpty(f571a)) {
            return f571a;
        }
        return "";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized void m510a(String str) {
        if (!j.m555d() && !TextUtils.isEmpty(str)) {
            f571a = str;
        }
    }

    public static void a(Context context, String str, long j2, boolean z2, boolean z3, long j3) {
        a(context, str, a(a(context), j2, z2, j3, z3), z2, j3);
    }

    private static void a(Context context, String str, long j2, boolean z2, long j3) {
        int iA;
        boolean zIsEmpty;
        if (context == null || TextUtils.isEmpty(str) || !"com.xiaomi.xmsf".equals(context.getPackageName()) || "com.xiaomi.xmsf".equals(str) || -1 == (iA = a(context))) {
            return;
        }
        synchronized (f570a) {
            try {
                zIsEmpty = f572a.isEmpty();
                a(new a(str, j3, iA, z2 ? 1 : 0, iA == 0 ? m507a(context) : "", j2));
            } catch (Throwable th) {
                throw th;
            }
        }
        if (zIsEmpty) {
            f568a.a(new ih(context), 5000L);
        }
    }

    private static long a(int i2, long j2, boolean z2, long j3, boolean z3) {
        if (z2 && z3) {
            long j4 = f567a;
            f567a = j3;
            if (j3 - j4 > 30000 && j2 > 1024) {
                return j2 * 2;
            }
        }
        return (j2 * (i2 == 0 ? 13 : 11)) / 10;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static com.xiaomi.push.providers.a m506a(Context context) {
        com.xiaomi.push.providers.a aVar = f569a;
        if (aVar != null) {
            return aVar;
        }
        com.xiaomi.push.providers.a aVar2 = new com.xiaomi.push.providers.a(context);
        f569a = aVar2;
        return aVar2;
    }

    public static int a(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes().length;
        }
    }

    private static void a(a aVar) {
        for (a aVar2 : f572a) {
            if (aVar2.a(aVar)) {
                aVar2.f575b += aVar.f575b;
                return;
            }
        }
        f572a.add(aVar);
    }
}

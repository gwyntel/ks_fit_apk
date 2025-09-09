package com.alipay.sdk.m.k;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.m.u.j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: com.alipay.sdk.m.k.a$a, reason: collision with other inner class name */
    public static final class C0044a {

        /* renamed from: a, reason: collision with root package name */
        public static final String f9345a = "RecordPref";

        /* renamed from: b, reason: collision with root package name */
        public static final String f9346b = "alipay_cashier_statistic_record";

        public static synchronized String a(Context context, String str, String str2) {
            try {
                com.alipay.sdk.m.u.e.b(f9345a, "stat append " + str2 + " , " + str);
                if (context != null && !TextUtils.isEmpty(str)) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = UUID.randomUUID().toString();
                    }
                    C0045a c0045aA = a(context);
                    if (c0045aA.f9347a.size() > 20) {
                        c0045aA.f9347a.clear();
                    }
                    c0045aA.f9347a.put(str2, str);
                    a(context, c0045aA);
                    return str2;
                }
                return null;
            } finally {
            }
        }

        public static synchronized String b(Context context) {
            com.alipay.sdk.m.u.e.b(f9345a, "stat peek");
            if (context == null) {
                return null;
            }
            C0045a c0045aA = a(context);
            if (c0045aA.f9347a.isEmpty()) {
                return null;
            }
            try {
                return c0045aA.f9347a.entrySet().iterator().next().getValue();
            } catch (Throwable th) {
                com.alipay.sdk.m.u.e.a(th);
                return null;
            }
        }

        /* renamed from: com.alipay.sdk.m.k.a$a$a, reason: collision with other inner class name */
        public static final class C0045a {

            /* renamed from: a, reason: collision with root package name */
            public final LinkedHashMap<String, String> f9347a = new LinkedHashMap<>();

            public C0045a() {
            }

            public String a() {
                try {
                    JSONArray jSONArray = new JSONArray();
                    for (Map.Entry<String, String> entry : this.f9347a.entrySet()) {
                        JSONArray jSONArray2 = new JSONArray();
                        jSONArray2.put(entry.getKey()).put(entry.getValue());
                        jSONArray.put(jSONArray2);
                    }
                    return jSONArray.toString();
                } catch (Throwable th) {
                    com.alipay.sdk.m.u.e.a(th);
                    return new JSONArray().toString();
                }
            }

            public C0045a(String str) {
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i2);
                        this.f9347a.put(jSONArray2.getString(0), jSONArray2.getString(1));
                    }
                } catch (Throwable th) {
                    com.alipay.sdk.m.u.e.a(th);
                }
            }
        }

        public static synchronized int a(Context context, String str) {
            com.alipay.sdk.m.u.e.b(f9345a, "stat remove " + str);
            if (context != null && !TextUtils.isEmpty(str)) {
                C0045a c0045aA = a(context);
                if (c0045aA.f9347a.isEmpty()) {
                    return 0;
                }
                try {
                    ArrayList arrayList = new ArrayList();
                    for (Map.Entry<String, String> entry : c0045aA.f9347a.entrySet()) {
                        if (str.equals(entry.getValue())) {
                            arrayList.add(entry.getKey());
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        c0045aA.f9347a.remove((String) it.next());
                    }
                    a(context, c0045aA);
                    return arrayList.size();
                } catch (Throwable th) {
                    com.alipay.sdk.m.u.e.a(th);
                    int size = c0045aA.f9347a.size();
                    a(context, new C0045a());
                    return size;
                }
            }
            return 0;
        }

        public static synchronized C0045a a(Context context) {
            try {
                String strA = j.a(null, context, f9346b, null);
                if (TextUtils.isEmpty(strA)) {
                    return new C0045a();
                }
                return new C0045a(strA);
            } catch (Throwable th) {
                com.alipay.sdk.m.u.e.a(th);
                return new C0045a();
            }
        }

        public static synchronized void a(Context context, C0045a c0045a) {
            if (c0045a == null) {
                try {
                    c0045a = new C0045a();
                } finally {
                }
            }
            j.b(null, context, f9346b, c0045a.a());
        }
    }

    public static final class b {

        /* renamed from: com.alipay.sdk.m.k.a$b$a, reason: collision with other inner class name */
        public static class RunnableC0046a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ String f9348a;

            /* renamed from: b, reason: collision with root package name */
            public final /* synthetic */ Context f9349b;

            public RunnableC0046a(String str, Context context) {
                this.f9348a = str;
                this.f9349b = context;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty(this.f9348a) || b.b(this.f9349b, this.f9348a)) {
                    for (int i2 = 0; i2 < 4; i2++) {
                        String strB = C0044a.b(this.f9349b);
                        if (TextUtils.isEmpty(strB) || !b.b(this.f9349b, strB)) {
                            return;
                        }
                    }
                }
            }
        }

        public static synchronized boolean b(Context context, String str) {
            try {
                com.alipay.sdk.m.u.e.b(com.alipay.sdk.m.l.a.f9433z, "stat sub " + str);
                try {
                    if ((com.alipay.sdk.m.m.a.z().e() ? new com.alipay.sdk.m.q.d() : new com.alipay.sdk.m.q.e()).a((com.alipay.sdk.m.s.a) null, context, str) == null) {
                        return false;
                    }
                    C0044a.a(context, str);
                    return true;
                } catch (Throwable th) {
                    com.alipay.sdk.m.u.e.a(th);
                    return false;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }

        public static synchronized void a(Context context, com.alipay.sdk.m.k.b bVar, String str, String str2) {
            if (context == null || bVar == null || str == null) {
                return;
            }
            a(context, bVar.a(str), str2);
        }

        public static synchronized void a(Context context) {
            a(context, null, null);
        }

        public static synchronized void a(Context context, String str, String str2) {
            if (context == null) {
                return;
            }
            try {
                if (!TextUtils.isEmpty(str)) {
                    C0044a.a(context, str, str2);
                }
                new Thread(new RunnableC0046a(str, context)).start();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static final class c {

        /* renamed from: a, reason: collision with root package name */
        public static final String f9350a = "alipay_cashier_ap_seq_v";

        public static synchronized long a(Context context) {
            return d.a(context, f9350a);
        }
    }

    public static final class d {
        public static synchronized long a(Context context, String str) {
            long j2;
            String strA;
            try {
                strA = j.a(null, context, str, null);
            } catch (Throwable unused) {
            }
            j2 = (!TextUtils.isEmpty(strA) ? Long.parseLong(strA) : 0L) + 1;
            try {
                j.b(null, context, str, Long.toString(j2));
            } catch (Throwable unused2) {
            }
            return j2;
        }
    }

    public static final class e {

        /* renamed from: a, reason: collision with root package name */
        public static final String f9351a = "alipay_cashier_statistic_v";

        public static synchronized long a(Context context) {
            return d.a(context, f9351a);
        }
    }

    public static synchronized void a(Context context, com.alipay.sdk.m.s.a aVar, String str, String str2) {
        if (context == null || aVar == null) {
            return;
        }
        try {
            C0044a.a(context, aVar.f9733j.a(str), str2);
        } finally {
        }
    }

    public static synchronized void b(Context context, com.alipay.sdk.m.s.a aVar, String str, String str2) {
        if (context == null || aVar == null) {
            return;
        }
        b.a(context, aVar.f9733j, str, str2);
    }

    public static void b(com.alipay.sdk.m.s.a aVar, String str, String str2, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.f9733j.b(str, str2, str3);
    }

    public static synchronized void a(Context context) {
        b.a(context);
    }

    public static void a(com.alipay.sdk.m.s.a aVar, String str, Throwable th) {
        if (aVar == null || th == null) {
            return;
        }
        aVar.f9733j.a(str, th.getClass().getSimpleName(), th);
    }

    public static void a(com.alipay.sdk.m.s.a aVar, String str, String str2, Throwable th, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.f9733j.a(str, str2, th, str3);
    }

    public static void a(com.alipay.sdk.m.s.a aVar, String str, String str2, Throwable th) {
        if (aVar == null) {
            return;
        }
        aVar.f9733j.a(str, str2, th);
    }

    public static void a(com.alipay.sdk.m.s.a aVar, String str, String str2, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.f9733j.a(str, str2, str3);
    }

    public static void a(com.alipay.sdk.m.s.a aVar, String str, String str2) {
        if (aVar == null) {
            return;
        }
        aVar.f9733j.a(str, str2);
    }
}

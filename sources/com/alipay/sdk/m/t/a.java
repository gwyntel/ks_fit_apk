package com.alipay.sdk.m.t;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.sdk.m.u.e;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: g, reason: collision with root package name */
    public static final String f9739g = "alipay_tid_storage";

    /* renamed from: h, reason: collision with root package name */
    public static final String f9740h = "tidinfo";

    /* renamed from: i, reason: collision with root package name */
    public static final String f9741i = "tid";

    /* renamed from: j, reason: collision with root package name */
    public static final String f9742j = "client_key";

    /* renamed from: k, reason: collision with root package name */
    public static final String f9743k = "timestamp";

    /* renamed from: l, reason: collision with root package name */
    public static final String f9744l = "vimei";

    /* renamed from: m, reason: collision with root package name */
    public static final String f9745m = "vimsi";

    /* renamed from: n, reason: collision with root package name */
    public static Context f9746n;

    /* renamed from: o, reason: collision with root package name */
    public static a f9747o;

    /* renamed from: a, reason: collision with root package name */
    public String f9748a;

    /* renamed from: b, reason: collision with root package name */
    public String f9749b;

    /* renamed from: c, reason: collision with root package name */
    public long f9750c;

    /* renamed from: d, reason: collision with root package name */
    public String f9751d;

    /* renamed from: e, reason: collision with root package name */
    public String f9752e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f9753f = false;

    public static synchronized a a(Context context) {
        try {
            if (f9747o == null) {
                f9747o = new a();
            }
            if (f9746n == null) {
                f9747o.b(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f9747o;
    }

    private void b(Context context) {
        if (context != null) {
            f9746n = context.getApplicationContext();
        }
        if (this.f9753f) {
            return;
        }
        this.f9753f = true;
        l();
    }

    private String k() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void l() {
        /*
            r9 = this;
            java.lang.String r0 = ""
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            r2 = 0
            java.lang.String r3 = "alipay_tid_storage"
            java.lang.String r4 = "tidinfo"
            r5 = 1
            java.lang.String r3 = com.alipay.sdk.m.t.a.C0056a.a(r3, r4, r5)     // Catch: java.lang.Exception -> L51
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Exception -> L51
            if (r4 != 0) goto L55
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: java.lang.Exception -> L51
            r4.<init>(r3)     // Catch: java.lang.Exception -> L51
            java.lang.String r3 = "tid"
            java.lang.String r3 = r4.optString(r3, r0)     // Catch: java.lang.Exception -> L51
            java.lang.String r5 = "client_key"
            java.lang.String r5 = r4.optString(r5, r0)     // Catch: java.lang.Exception -> L4d
            java.lang.String r6 = "timestamp"
            long r7 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> L4a
            long r6 = r4.optLong(r6, r7)     // Catch: java.lang.Exception -> L4a
            java.lang.Long r1 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Exception -> L4a
            java.lang.String r6 = "vimei"
            java.lang.String r6 = r4.optString(r6, r0)     // Catch: java.lang.Exception -> L4a
            java.lang.String r7 = "vimsi"
            java.lang.String r2 = r4.optString(r7, r0)     // Catch: java.lang.Exception -> L48
        L45:
            r0 = r2
            r2 = r3
            goto L5d
        L48:
            r0 = move-exception
            goto L59
        L4a:
            r0 = move-exception
            r6 = r2
            goto L59
        L4d:
            r0 = move-exception
            r5 = r2
        L4f:
            r6 = r5
            goto L59
        L51:
            r0 = move-exception
            r3 = r2
            r5 = r3
            goto L4f
        L55:
            r0 = r2
            r5 = r0
            r6 = r5
            goto L5d
        L59:
            com.alipay.sdk.m.u.e.a(r0)
            goto L45
        L5d:
            java.lang.String r3 = "mspl"
            java.lang.String r4 = "tid_str: load"
            com.alipay.sdk.m.u.e.b(r3, r4)
            boolean r3 = r9.a(r2, r5, r6, r0)
            if (r3 == 0) goto L6e
            r9.m()
            goto L7c
        L6e:
            r9.f9748a = r2
            r9.f9749b = r5
            long r1 = r1.longValue()
            r9.f9750c = r1
            r9.f9751d = r6
            r9.f9752e = r0
        L7c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.t.a.l():void");
    }

    private void m() {
        this.f9748a = "";
        this.f9749b = b();
        this.f9750c = System.currentTimeMillis();
        this.f9751d = k();
        this.f9752e = k();
        C0056a.b(f9739g, f9740h);
    }

    private void n() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("tid", this.f9748a);
            jSONObject.put(f9742j, this.f9749b);
            jSONObject.put(f9743k, this.f9750c);
            jSONObject.put(f9744l, this.f9751d);
            jSONObject.put(f9745m, this.f9752e);
            C0056a.a(f9739g, f9740h, jSONObject.toString(), true);
        } catch (Exception e2) {
            e.a(e2);
        }
    }

    private void o() {
    }

    public String c() {
        return this.f9749b;
    }

    public String d() {
        return this.f9748a;
    }

    public Long e() {
        return Long.valueOf(this.f9750c);
    }

    public String f() {
        return this.f9751d;
    }

    public String g() {
        return this.f9752e;
    }

    public boolean h() {
        return i();
    }

    public boolean i() {
        return TextUtils.isEmpty(this.f9748a) || TextUtils.isEmpty(this.f9749b) || TextUtils.isEmpty(this.f9751d) || TextUtils.isEmpty(this.f9752e);
    }

    /* renamed from: com.alipay.sdk.m.t.a$a, reason: collision with other inner class name */
    public static class C0056a {
        public static boolean a(String str, String str2) {
            if (a.f9746n == null) {
                return false;
            }
            return a.f9746n.getSharedPreferences(str, 0).contains(str2);
        }

        public static void b(String str, String str2) {
            if (a.f9746n == null) {
                return;
            }
            a.f9746n.getSharedPreferences(str, 0).edit().remove(str2).apply();
        }

        public static boolean c(String str, String str2) {
            if (a.f9746n == null) {
                return false;
            }
            return a.f9746n.getSharedPreferences(str, 0).contains(str2);
        }

        public static String d(String str, String str2) {
            return a(str, str2, true);
        }

        public static String a(String str, String str2, boolean z2) {
            if (a.f9746n == null) {
                return null;
            }
            String string = a.f9746n.getSharedPreferences(str, 0).getString(str2, null);
            if (!TextUtils.isEmpty(string) && z2) {
                string = com.alipay.sdk.m.n.e.a(a(), string, string);
                if (TextUtils.isEmpty(string)) {
                    e.b(com.alipay.sdk.m.l.a.f9433z, "tid_str: pref failed");
                }
            }
            e.b(com.alipay.sdk.m.l.a.f9433z, "tid_str: from local");
            return string;
        }

        public static void a(String str, String str2, String str3) {
            a(str, str2, str3, true);
        }

        public static void a(String str, String str2, String str3, boolean z2) {
            if (a.f9746n == null) {
                return;
            }
            SharedPreferences sharedPreferences = a.f9746n.getSharedPreferences(str, 0);
            if (z2) {
                String strA = a();
                String strB = com.alipay.sdk.m.n.e.b(strA, str3, str3);
                if (TextUtils.isEmpty(strB)) {
                    String.format("LocalPreference::putLocalPreferences failed %s，%s", str3, strA);
                }
                str3 = strB;
            }
            sharedPreferences.edit().putString(str2, str3).apply();
        }

        public static String a() {
            String packageName;
            try {
                packageName = a.f9746n.getApplicationContext().getPackageName();
            } catch (Throwable th) {
                e.a(th);
                packageName = "";
            }
            return (packageName + "0000000000000000000000000000").substring(0, 24);
        }
    }

    public String b() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }

    private boolean a(String str, String str2, String str3, String str4) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4);
    }

    public void a() {
        e.b(com.alipay.sdk.m.l.a.f9433z, "tid_str: del");
        m();
    }

    public void a(String str, String str2) throws JSONException {
        e.b(com.alipay.sdk.m.l.a.f9433z, "tid_str: save");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.f9748a = str;
        this.f9749b = str2;
        this.f9750c = System.currentTimeMillis();
        n();
        o();
    }

    private void a(String str, String str2, String str3, String str4, Long l2) throws JSONException {
        if (a(str, str2, str3, str4)) {
            return;
        }
        this.f9748a = str;
        this.f9749b = str2;
        this.f9751d = str3;
        this.f9752e = str4;
        if (l2 == null) {
            this.f9750c = System.currentTimeMillis();
        } else {
            this.f9750c = l2.longValue();
        }
        n();
    }
}

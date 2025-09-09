package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.xiaomi.push.hh;
import com.xiaomi.push.is;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class p {

    /* renamed from: a, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static volatile p f24601a;

    /* renamed from: a, reason: collision with other field name */
    private long f1088a;

    /* renamed from: a, reason: collision with other field name */
    private final Context f1089a;

    /* renamed from: a, reason: collision with other field name */
    private final SharedPreferences f1090a;

    /* renamed from: b, reason: collision with other field name */
    private final boolean f1096b;

    /* renamed from: c, reason: collision with other field name */
    private final boolean f1097c;

    /* renamed from: a, reason: collision with other field name */
    private final AtomicInteger f1092a = new AtomicInteger(0);

    /* renamed from: a, reason: collision with other field name */
    private String f1091a = null;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f1093a = false;

    /* renamed from: b, reason: collision with other field name */
    private String f1094b = null;

    /* renamed from: b, reason: collision with other field name */
    private final AtomicInteger f1095b = new AtomicInteger(0);

    /* renamed from: c, reason: collision with root package name */
    private final AtomicInteger f24603c = new AtomicInteger(0);

    /* renamed from: a, reason: collision with other field name */
    private int f1087a = -1;

    /* renamed from: b, reason: collision with root package name */
    private long f24602b = -1;

    private static class a {
        public static String a() {
            return "support_wifi_digest";
        }

        public static String b(String str) {
            return String.format("HB_dead_time_%s", str);
        }

        public static String c() {
            return "record_hb_count_start";
        }

        public static String d() {
            return "record_short_hb_count";
        }

        public static String e() {
            return "record_long_hb_count";
        }

        public static String f() {
            return "record_hb_change";
        }

        public static String g() {
            return "record_mobile_ptc";
        }

        public static String h() {
            return "record_wifi_ptc";
        }

        public static String i() {
            return "record_ptc_start";
        }

        public static String j() {
            return "keep_short_hb_effective_time";
        }

        public static String a(String str) {
            return String.format("HB_%s", str);
        }

        public static String b() {
            return "record_support_wifi_digest_reported_time";
        }
    }

    private p(Context context) {
        this.f1089a = context;
        this.f1097c = com.xiaomi.push.j.m550a(context);
        this.f1096b = az.a(context).a(is.IntelligentHeartbeatSwitchBoolean.a(), true);
        SharedPreferences sharedPreferences = context.getSharedPreferences("hb_record", 0);
        this.f1090a = sharedPreferences;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (sharedPreferences.getLong(a.c(), -1L) == -1) {
            sharedPreferences.edit().putLong(a.c(), jCurrentTimeMillis).apply();
        }
        long j2 = sharedPreferences.getLong(a.i(), -1L);
        this.f1088a = j2;
        if (j2 == -1) {
            this.f1088a = jCurrentTimeMillis;
            sharedPreferences.edit().putLong(a.i(), jCurrentTimeMillis).apply();
        }
    }

    private void b(String str) {
        if ("WIFI-ID-UNKNOWN".equals(str)) {
            String str2 = this.f1091a;
            if (str2 == null || !str2.startsWith("W-")) {
                this.f1091a = null;
            }
        } else {
            this.f1091a = str;
        }
        int i2 = this.f1090a.getInt(a.a(this.f1091a), -1);
        long j2 = this.f1090a.getLong(a.b(this.f1091a), -1L);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (i2 != -1) {
            if (j2 == -1) {
                this.f1090a.edit().putLong(a.b(this.f1091a), jCurrentTimeMillis + d()).apply();
            } else if (jCurrentTimeMillis > j2) {
                this.f1090a.edit().remove(a.a(this.f1091a)).remove(a.b(this.f1091a)).apply();
            }
        }
        this.f1092a.getAndSet(0);
        if (TextUtils.isEmpty(this.f1091a) || a() != -1) {
            this.f1093a = false;
        } else {
            this.f1093a = true;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[HB] network changed, netid:%s, %s", this.f1091a, Boolean.valueOf(this.f1093a)));
    }

    private void e() {
        if (this.f1090a.getBoolean(a.a(), false)) {
            return;
        }
        this.f1090a.edit().putBoolean(a.a(), true).apply();
    }

    private void f() {
        int i2 = this.f1087a;
        String strH = i2 != 0 ? i2 != 1 ? null : a.h() : a.g();
        if (TextUtils.isEmpty(strH)) {
            return;
        }
        if (this.f1090a.getLong(a.i(), -1L) == -1) {
            this.f1088a = System.currentTimeMillis();
            this.f1090a.edit().putLong(a.i(), this.f1088a).apply();
        }
        this.f1090a.edit().putInt(strH, this.f1090a.getInt(strH, 0) + 1).apply();
    }

    private void g() {
        int i2;
        String[] strArrSplit;
        String[] strArrSplit2;
        if (m780c()) {
            String string = this.f1090a.getString(a.f(), null);
            char c2 = 0;
            if (!TextUtils.isEmpty(string) && (strArrSplit = string.split("###")) != null) {
                int i3 = 0;
                while (i3 < strArrSplit.length) {
                    if (!TextUtils.isEmpty(strArrSplit[i3]) && (strArrSplit2 = strArrSplit[i3].split(":::")) != null && strArrSplit2.length >= 4) {
                        String str = strArrSplit2[c2];
                        String str2 = strArrSplit2[1];
                        String str3 = strArrSplit2[2];
                        String str4 = strArrSplit2[3];
                        HashMap map = new HashMap();
                        map.put(NotificationCompat.CATEGORY_EVENT, "change");
                        map.put("model", Build.MODEL);
                        map.put(HiAnalyticsConstant.BI_KEY_NET_TYPE, str2);
                        map.put("net_name", str);
                        map.put(com.umeng.analytics.pro.bc.ba, str3);
                        map.put(com.alipay.sdk.m.t.a.f9743k, str4);
                        a("category_hb_change", null, map);
                        com.xiaomi.channel.commonutils.logger.b.m91a("[HB] report hb changed events.");
                    }
                    i3++;
                    c2 = 0;
                }
                this.f1090a.edit().remove(a.f()).apply();
            }
            if (this.f1090a.getBoolean(a.a(), false)) {
                long j2 = this.f1090a.getLong(a.b(), 0L);
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - j2 > 1296000000) {
                    HashMap map2 = new HashMap();
                    map2.put(NotificationCompat.CATEGORY_EVENT, "support");
                    map2.put("model", Build.MODEL);
                    map2.put(com.alipay.sdk.m.t.a.f9743k, String.valueOf(System.currentTimeMillis() / 1000));
                    a("category_hb_change", null, map2);
                    com.xiaomi.channel.commonutils.logger.b.m91a("[HB] report support wifi digest events.");
                    this.f1090a.edit().putLong(a.b(), jCurrentTimeMillis).apply();
                }
            }
            if (m782e()) {
                int i4 = this.f1090a.getInt(a.d(), 0);
                int i5 = this.f1090a.getInt(a.e(), 0);
                if (i4 > 0 || i5 > 0) {
                    long j3 = this.f1090a.getLong(a.c(), -1L);
                    String strValueOf = String.valueOf(235000);
                    String strValueOf2 = String.valueOf(j3);
                    String strValueOf3 = String.valueOf(System.currentTimeMillis());
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(com.umeng.analytics.pro.bc.ba, strValueOf);
                        jSONObject.put("c_short", String.valueOf(i4));
                        jSONObject.put("c_long", String.valueOf(i5));
                        jSONObject.put("count", String.valueOf(i4 + i5));
                        jSONObject.put(com.umeng.analytics.pro.f.f21694p, strValueOf2);
                        jSONObject.put(com.umeng.analytics.pro.f.f21695q, strValueOf3);
                        String string2 = jSONObject.toString();
                        HashMap map3 = new HashMap();
                        map3.put(NotificationCompat.CATEGORY_EVENT, "long_and_short_hb_count");
                        a("category_hb_count", string2, map3);
                        com.xiaomi.channel.commonutils.logger.b.m91a("[HB] report short/long hb count events.");
                    } catch (Throwable unused) {
                    }
                }
                this.f1090a.edit().putInt(a.d(), 0).putInt(a.e(), 0).putLong(a.c(), System.currentTimeMillis()).apply();
            }
            if (m783f()) {
                String strValueOf4 = String.valueOf(this.f1088a);
                String strValueOf5 = String.valueOf(System.currentTimeMillis());
                int i6 = this.f1090a.getInt(a.g(), 0);
                if (i6 > 0) {
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(HiAnalyticsConstant.BI_KEY_NET_TYPE, "M");
                        jSONObject2.put("ptc", i6);
                        jSONObject2.put(com.umeng.analytics.pro.f.f21694p, strValueOf4);
                        jSONObject2.put(com.umeng.analytics.pro.f.f21695q, strValueOf5);
                        String string3 = jSONObject2.toString();
                        HashMap map4 = new HashMap();
                        map4.put(NotificationCompat.CATEGORY_EVENT, "ptc_event");
                        a("category_lc_ptc", string3, map4);
                        com.xiaomi.channel.commonutils.logger.b.m91a("[HB] report ping timeout count events of mobile network.");
                        this.f1090a.edit().putInt(a.g(), 0).apply();
                        i2 = 0;
                    } catch (Throwable unused2) {
                        i2 = 0;
                        this.f1090a.edit().putInt(a.g(), 0).apply();
                    }
                } else {
                    i2 = 0;
                }
                int i7 = this.f1090a.getInt(a.h(), i2);
                if (i7 > 0) {
                    try {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put(HiAnalyticsConstant.BI_KEY_NET_TYPE, ExifInterface.LONGITUDE_WEST);
                        jSONObject3.put("ptc", i7);
                        jSONObject3.put(com.umeng.analytics.pro.f.f21694p, strValueOf4);
                        jSONObject3.put(com.umeng.analytics.pro.f.f21695q, strValueOf5);
                        String string4 = jSONObject3.toString();
                        HashMap map5 = new HashMap();
                        map5.put(NotificationCompat.CATEGORY_EVENT, "ptc_event");
                        a("category_lc_ptc", string4, map5);
                        com.xiaomi.channel.commonutils.logger.b.m91a("[HB] report ping timeout count events of wifi network.");
                    } catch (Throwable unused3) {
                    }
                    this.f1090a.edit().putInt(a.h(), 0).apply();
                }
                this.f1088a = System.currentTimeMillis();
                this.f1090a.edit().putLong(a.i(), this.f1088a).apply();
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m785a() {
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m789c() {
        if (m781d()) {
            this.f1094b = this.f1091a;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m790d() {
        if (m781d()) {
            g();
            if (this.f1093a) {
                this.f1092a.getAndSet(0);
            }
        }
    }

    public static p a(Context context) {
        if (f24601a == null) {
            synchronized (p.class) {
                try {
                    if (f24601a == null) {
                        f24601a = new p(context);
                    }
                } finally {
                }
            }
        }
        return f24601a;
    }

    private long c() {
        return this.f1090a.getLong(a.j(), -1L);
    }

    /* renamed from: e, reason: collision with other method in class */
    private boolean m782e() {
        long j2 = this.f1090a.getLong(a.c(), -1L);
        if (j2 == -1) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        return j2 > jCurrentTimeMillis || jCurrentTimeMillis - j2 >= 259200000;
    }

    private void c(String str) {
        if (a(str)) {
            this.f1090a.edit().putInt(a.a(str), 235000).apply();
            this.f1090a.edit().putLong(a.b(this.f1091a), System.currentTimeMillis() + d()).apply();
        }
    }

    private long d() {
        return az.a(this.f1089a).a(is.ShortHeartbeatEffectivePeriodMsLong.a(), 777600000L);
    }

    private void d(String str) {
        String str2;
        String string;
        if (m780c() && !TextUtils.isEmpty(str)) {
            if (str.startsWith("W-")) {
                str2 = ExifInterface.LONGITUDE_WEST;
            } else if (!str.startsWith("M-")) {
                return;
            } else {
                str2 = "M";
            }
            String strValueOf = String.valueOf(235000);
            String strValueOf2 = String.valueOf(System.currentTimeMillis() / 1000);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(":::");
            sb.append(str2);
            sb.append(":::");
            sb.append(strValueOf);
            sb.append(":::");
            sb.append(strValueOf2);
            String string2 = this.f1090a.getString(a.f(), null);
            if (TextUtils.isEmpty(string2)) {
                string = sb.toString();
            } else {
                string = string2 + "###" + sb.toString();
            }
            this.f1090a.edit().putString(a.f(), string).apply();
        }
    }

    public synchronized void a(com.xiaomi.push.bj bjVar) {
        try {
            if (m781d()) {
                String str = null;
                if (bjVar != null) {
                    if (bjVar.a() == 0) {
                        String strM213b = bjVar.m213b();
                        if (!TextUtils.isEmpty(strM213b) && !"UNKNOWN".equalsIgnoreCase(strM213b)) {
                            str = "M-" + strM213b;
                        }
                        b(str);
                        this.f1087a = 0;
                    } else if (bjVar.a() != 1 && bjVar.a() != 6) {
                        b(null);
                        this.f1087a = -1;
                    } else {
                        b("WIFI-ID-UNKNOWN");
                        this.f1087a = 1;
                    }
                } else {
                    b(null);
                    this.f1087a = -1;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    private boolean m780c() {
        return m781d() && az.a(this.f1089a).a(is.IntelligentHeartbeatDataCollectSwitchBoolean.a(), true) && com.xiaomi.push.m.China.name().equals(com.xiaomi.push.service.a.a(this.f1089a).a());
    }

    /* renamed from: f, reason: collision with other method in class */
    private boolean m783f() {
        if (this.f1088a == -1) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = this.f1088a;
        return j2 > jCurrentTimeMillis || jCurrentTimeMillis - j2 >= 259200000;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m788b() {
        if (m781d()) {
            f();
            if (this.f1093a && !TextUtils.isEmpty(this.f1091a) && this.f1091a.equals(this.f1094b)) {
                this.f1092a.getAndIncrement();
                com.xiaomi.channel.commonutils.logger.b.m91a("[HB] ping timeout count:" + this.f1092a);
                if (m779a()) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("[HB] change hb interval for net:" + this.f1091a);
                    c(this.f1091a);
                    this.f1093a = false;
                    this.f1092a.getAndSet(0);
                    d(this.f1091a);
                }
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m786a(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                e();
            }
            if (m781d() && !TextUtils.isEmpty(str)) {
                b("W-" + str);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    private boolean m781d() {
        boolean z2 = c() >= System.currentTimeMillis();
        if (this.f1097c) {
            return this.f1096b || z2;
        }
        return false;
    }

    /* renamed from: b, reason: collision with other method in class */
    public long m787b() {
        return this.f24602b;
    }

    private boolean b() {
        return (TextUtils.isEmpty(this.f1091a) || !this.f1091a.startsWith("M-") || az.a(this.f1089a).a(is.IntelligentHeartbeatUseInMobileNetworkBoolean.a(), false)) ? false : true;
    }

    public void a(int i2) {
        this.f1090a.edit().putLong(a.j(), System.currentTimeMillis() + (i2 * 1000)).apply();
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m779a() {
        return this.f1092a.get() >= Math.max(az.a(this.f1089a).a(is.IntelligentHeartbeatNATCountInt.a(), 5), 3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m784a() {
        int iA;
        long jB = hh.b();
        if (this.f1097c && !b() && ((az.a(this.f1089a).a(is.IntelligentHeartbeatSwitchBoolean.a(), true) || c() >= System.currentTimeMillis()) && (iA = a()) != -1)) {
            jB = iA;
        }
        if (!TextUtils.isEmpty(this.f1091a) && !"WIFI-ID-UNKNOWN".equals(this.f1091a) && this.f1087a == 1) {
            a(jB < 300000);
        }
        this.f24602b = jB;
        com.xiaomi.channel.commonutils.logger.b.m91a("[HB] ping interval:" + jB);
        return jB;
    }

    private int a() {
        if (TextUtils.isEmpty(this.f1091a)) {
            return -1;
        }
        try {
            return this.f1090a.getInt(a.a(this.f1091a), -1);
        } catch (Throwable unused) {
            return -1;
        }
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("W-") || str.startsWith("M-");
    }

    private void a(boolean z2) {
        String strE;
        if (m780c()) {
            int iIncrementAndGet = (z2 ? this.f1095b : this.f24603c).incrementAndGet();
            com.xiaomi.channel.commonutils.logger.b.b(String.format("[HB] %s ping interval count: %s", z2 ? "short" : "long", Integer.valueOf(iIncrementAndGet)));
            if (iIncrementAndGet >= 5) {
                if (z2) {
                    strE = a.d();
                } else {
                    strE = a.e();
                }
                int i2 = this.f1090a.getInt(strE, 0) + iIncrementAndGet;
                this.f1090a.edit().putInt(strE, i2).apply();
                com.xiaomi.channel.commonutils.logger.b.m91a(String.format("[HB] accumulate %s hb count(%s) and write to file. ", z2 ? "short" : "long", Integer.valueOf(i2)));
                if (z2) {
                    this.f1095b.set(0);
                } else {
                    this.f24603c.set(0);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r4, java.lang.String r5, java.util.Map<java.lang.String, java.lang.String> r6) {
        /*
            r3 = this;
            com.xiaomi.push.ir r0 = new com.xiaomi.push.ir
            r0.<init>()
            r0.d(r4)
            java.lang.String r4 = "hb_name"
            r0.c(r4)
            java.lang.String r4 = "hb_channel"
            r0.a(r4)
            r1 = 1
            r0.a(r1)
            r0.b(r5)
            r4 = 0
            r0.a(r4)
            long r1 = java.lang.System.currentTimeMillis()
            r0.b(r1)
            android.content.Context r5 = r3.f1089a
            java.lang.String r5 = r5.getPackageName()
            r0.g(r5)
            java.lang.String r5 = "com.xiaomi.xmsf"
            r0.e(r5)
            if (r6 != 0) goto L3a
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
        L3a:
            android.content.Context r5 = r3.f1089a
            com.xiaomi.push.service.u r5 = com.xiaomi.push.service.v.m802a(r5)
            if (r5 == 0) goto L58
            java.lang.String r1 = r5.f1111a
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L58
            java.lang.String r5 = r5.f1111a
            java.lang.String r1 = "@"
            java.lang.String[] r5 = r5.split(r1)
            int r1 = r5.length
            if (r1 <= 0) goto L58
            r4 = r5[r4]
            goto L59
        L58:
            r4 = 0
        L59:
            java.lang.String r5 = "uuid"
            r6.put(r5, r4)
            java.lang.String r4 = "model"
            java.lang.String r5 = android.os.Build.MODEL
            r6.put(r4, r5)
            android.content.Context r4 = r3.f1089a
            java.lang.String r5 = r4.getPackageName()
            int r4 = com.xiaomi.push.g.a(r4, r5)
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = "avc"
            r6.put(r5, r4)
            r4 = 50602(0xc5aa, float:7.0909E-41)
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = "pvc"
            r6.put(r5, r4)
            r4 = 48
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = "cvc"
            r6.put(r5, r4)
            r0.a(r6)
            android.content.Context r4 = r3.f1089a
            com.xiaomi.push.il r4 = com.xiaomi.push.il.a(r4)
            if (r4 == 0) goto La3
            android.content.Context r5 = r3.f1089a
            java.lang.String r5 = r5.getPackageName()
            r4.a(r0, r5)
        La3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.p.a(java.lang.String, java.lang.String, java.util.Map):void");
    }
}

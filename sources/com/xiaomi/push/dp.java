package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class dp {

    /* renamed from: a, reason: collision with root package name */
    private static int f23604a;

    private static int a(boolean z2) {
        return z2 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void c(Context context, String str, boolean z2, long j2, int i2, long j3, int i3, String str2, int i4) {
        SharedPreferences sharedPreferencesM290a = m290a(context);
        long j4 = sharedPreferencesM290a.getLong("start_time_for_day", 0L);
        if (j4 == 0) {
            dm.a("recordDisconnection not initialized");
            return;
        }
        if (j2 - sharedPreferencesM290a.getLong("last_discnt_time", 0L) < 60000) {
            dm.a("recordDisconnection anti-shake");
            return;
        }
        if (j2 - j4 < 86400000) {
            int i5 = sharedPreferencesM290a.getInt("discnt_count_in_day", 0);
            if (i5 > 100) {
                dm.a("recordDisconnection count > 100 in 24H cycle,abandon.");
                return;
            } else {
                sharedPreferencesM290a.edit().putInt("discnt_count_in_day", i5 + 1).apply();
            }
        } else {
            dm.a("recordDisconnection with the current time exceeds 24H cycle, go on.");
        }
        int i6 = sharedPreferencesM290a.getInt("discnt_count", 0);
        if (i6 == sharedPreferencesM290a.getInt("cnt_count", 0)) {
            a(context, str, a(z2), j2, i2, j3, i3, str2, i4);
            sharedPreferencesM290a.edit().putLong("last_discnt_time", j2).putInt("discnt_count", i6 + 1).apply();
        }
        dm.a("recordDisconnection complete");
    }

    /* renamed from: a, reason: collision with other method in class */
    private static SharedPreferences m290a(Context context) {
        return context.getSharedPreferences("sp_disconnect_stats", 0);
    }

    public static void a(Context context, String str, boolean z2, long j2, int i2, long j3, int i3, String str2, int i4) {
        ah.a(context).a(new dq(context, str, z2, j2, i2, j3, i3, str2, i4));
    }

    private static void b(Context context) {
        dm.a("resetAfterUpload");
        m290a(context).edit().putString("host", null).putString("network_state", null).putString("reason", null).putString("ping_interval", null).putString(com.umeng.analytics.pro.bc.T, null).putString("wifi_digest", null).putString("connected_network_type", null).putString("disconnect_time", null).putString("connected_time", null).putLong("last_discnt_time", 0L).putInt("discnt_count", 0).putInt("cnt_count", 0).putString("xmsf_vc", null).putString("android_vc", null).apply();
    }

    public static void a(Context context, long j2) {
        ah.a(context).a(new dr(context, j2));
    }

    private static void a(Context context, String str, int i2, long j2, int i3, long j3, int i4, String str2, int i5) {
        dm.a(String.format(Locale.US, "recordDisconnectInfo host=%s, netState=%d, currentTimeMillis=%d, reason=%d, pingInterval=%d, netType=%d, wifiDigest=%s, connectedNetType=%d", str, Integer.valueOf(i2), Long.valueOf(j2), Integer.valueOf(i3), Long.valueOf(j3), Integer.valueOf(i4), str2, Integer.valueOf(i5)));
        SharedPreferences sharedPreferencesM290a = m290a(context);
        String string = sharedPreferencesM290a.getString("host", null);
        String string2 = sharedPreferencesM290a.getString("network_state", null);
        String string3 = sharedPreferencesM290a.getString("reason", null);
        String string4 = sharedPreferencesM290a.getString("ping_interval", null);
        String string5 = sharedPreferencesM290a.getString(com.umeng.analytics.pro.bc.T, null);
        String string6 = sharedPreferencesM290a.getString("wifi_digest", null);
        String string7 = sharedPreferencesM290a.getString("connected_network_type", null);
        String string8 = sharedPreferencesM290a.getString("disconnect_time", null);
        String string9 = sharedPreferencesM290a.getString("xmsf_vc", null);
        String string10 = sharedPreferencesM290a.getString("android_vc", null);
        String strA = a(string, str);
        String strA2 = a(string2, i2);
        String strA3 = a(string3, i3);
        String strA4 = a(string4, j3);
        String strA5 = a(string5, i4);
        String strA6 = a(string6, str2);
        String strA7 = a(string7, i5);
        String strA8 = a(string8, j2);
        sharedPreferencesM290a.edit().putString("host", strA).putString("network_state", strA2).putString("reason", strA3).putString("ping_interval", strA4).putString(com.umeng.analytics.pro.bc.T, strA5).putString("wifi_digest", strA6).putString("connected_network_type", strA7).putString("disconnect_time", strA8).putString("xmsf_vc", a(string9, a(context))).putString("android_vc", a(string10, Build.VERSION.SDK_INT)).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void c(Context context, long j2) {
        SharedPreferences sharedPreferencesM290a = m290a(context);
        long j3 = sharedPreferencesM290a.getLong("start_time_for_day", 0L);
        if (j3 == 0) {
            sharedPreferencesM290a.edit().putLong("start_time_for_day", j2).putLong("last_discnt_time", 0L).putInt("discnt_count_in_day", 0).putInt("discnt_count", 0).putInt("cnt_count", 0).apply();
            return;
        }
        int i2 = sharedPreferencesM290a.getInt("discnt_count", 0);
        int i3 = sharedPreferencesM290a.getInt("cnt_count", 0);
        if (i2 > i3) {
            sharedPreferencesM290a.edit().putInt("cnt_count", i3 + 1).putString("connected_time", a(sharedPreferencesM290a.getString("connected_time", null), j2)).apply();
        }
        if (j2 - j3 >= 86400000) {
            sharedPreferencesM290a.edit().putLong("start_time_for_day", j2).putInt("discnt_count_in_day", 0).apply();
            m292a(context);
        } else if (i2 >= 10) {
            m292a(context);
        }
    }

    private static String a(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            str2 = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (str == null || str.length() <= 0) {
            return str2;
        }
        return str + com.alipay.sdk.m.u.i.f9802b + str2;
    }

    private static String a(String str, int i2) {
        return a(str, String.valueOf(i2));
    }

    private static String a(String str, long j2) {
        return a(str, String.valueOf(j2));
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m292a(Context context) {
        dm.a("upload");
        new Cdo().a(context, m291a(context));
        b(context);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static List<dn> m291a(Context context) {
        SharedPreferences sharedPreferencesM290a = m290a(context);
        String[] strArrA = a(sharedPreferencesM290a.getString("host", null));
        if (strArrA != null && strArrA.length > 0) {
            String[] strArrA2 = a(sharedPreferencesM290a.getString("network_state", null));
            String[] strArrA3 = a(sharedPreferencesM290a.getString("reason", null));
            String[] strArrA4 = a(sharedPreferencesM290a.getString("ping_interval", null));
            String[] strArrA5 = a(sharedPreferencesM290a.getString(com.umeng.analytics.pro.bc.T, null));
            String[] strArrA6 = a(sharedPreferencesM290a.getString("wifi_digest", null));
            String[] strArrA7 = a(sharedPreferencesM290a.getString("connected_network_type", null));
            String[] strArrA8 = a(sharedPreferencesM290a.getString("disconnect_time", null));
            String[] strArrA9 = a(sharedPreferencesM290a.getString("connected_time", null));
            String[] strArrA10 = a(sharedPreferencesM290a.getString("xmsf_vc", null));
            String[] strArrA11 = a(sharedPreferencesM290a.getString("android_vc", null));
            if (strArrA2 != null && strArrA3 != null && strArrA4 != null && strArrA5 != null && strArrA6 != null && strArrA7 != null && strArrA8 != null && strArrA9 != null && strArrA10 != null && strArrA11 != null && strArrA.length == strArrA2.length && strArrA.length == strArrA3.length && strArrA.length == strArrA4.length && strArrA.length == strArrA5.length && strArrA.length == strArrA6.length && strArrA.length == strArrA7.length && strArrA.length == strArrA8.length && strArrA.length == strArrA9.length && strArrA.length == strArrA10.length && strArrA.length == strArrA11.length) {
                ArrayList arrayList = new ArrayList(strArrA.length);
                int i2 = 0;
                while (i2 < strArrA.length) {
                    dn dnVar = new dn();
                    dnVar.a(1);
                    dnVar.a(strArrA[i2]);
                    dnVar.b(s.a(strArrA2[i2], -1));
                    dnVar.c(s.a(strArrA3[i2], -1));
                    String[] strArr = strArrA2;
                    String[] strArr2 = strArrA;
                    ArrayList arrayList2 = arrayList;
                    dnVar.a(s.a(strArrA4[i2], -1L));
                    dnVar.d(s.a(strArrA5[i2], -1));
                    dnVar.b(strArrA6[i2]);
                    dnVar.e(s.a(strArrA7[i2], -1));
                    long jA = s.a(strArrA8[i2], -1L);
                    long jA2 = s.a(strArrA9[i2], -1L);
                    dnVar.b(jA2 - jA);
                    dnVar.c(jA);
                    dnVar.d(jA2);
                    dnVar.f(s.a(strArrA10[i2], -1));
                    dnVar.g(s.a(strArrA11[i2], -1));
                    arrayList2.add(dnVar);
                    i2++;
                    strArrA2 = strArr;
                    arrayList = arrayList2;
                    strArrA4 = strArrA4;
                    strArrA3 = strArrA3;
                    strArrA = strArr2;
                    strArrA5 = strArrA5;
                }
                return arrayList;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("DisconnectStatsSP Cached data incorrect,drop.");
            return null;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("DisconnectStatsSP Cached hosts data is empty,drop.");
        return null;
    }

    private static String[] a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        return str.split(com.alipay.sdk.m.u.i.f9802b);
    }

    private static int a(Context context) {
        if (f23604a <= 0) {
            f23604a = j.b(context);
        }
        return f23604a;
    }
}

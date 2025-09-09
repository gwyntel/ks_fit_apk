package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static volatile int f24143a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, m> f650a = null;

    /* renamed from: b, reason: collision with root package name */
    private static int f24144b = -1;

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m549a() {
        return a() == 1;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m553b() {
        return a() == 2;
    }

    /* renamed from: c, reason: collision with other method in class */
    public static boolean m554c() {
        if (f24144b < 0) {
            f24144b = !m556e() ? 1 : 0;
        }
        return f24144b > 0;
    }

    /* renamed from: d, reason: collision with other method in class */
    public static boolean m555d() {
        return !m.China.name().equalsIgnoreCase(a(b()).name());
    }

    /* renamed from: e, reason: collision with other method in class */
    public static boolean m556e() {
        String strA = "";
        try {
            strA = q.a("ro.miui.ui.version.code", "");
        } catch (Exception unused) {
        }
        return !TextUtils.isEmpty(strA);
    }

    public static int a() {
        if (f24143a == 0) {
            try {
                f24143a = (TextUtils.isEmpty(m547a("ro.miui.ui.version.code")) && TextUtils.isEmpty(m547a("ro.miui.ui.version.name"))) ? 2 : 1;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a("get isMIUI failed", th);
                f24143a = 0;
            }
            com.xiaomi.channel.commonutils.logger.b.b("isMIUI's value is: " + f24143a);
        }
        return f24143a;
    }

    public static String b() {
        String strA = q.a("ro.miui.region", "");
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("persist.sys.oppo.region", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("ro.oppo.regionmark", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("ro.vendor.oplus.regionmark", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("ro.hw.country", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("ro.csc.countryiso_code", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = m552b(q.a("ro.product.country.region", ""));
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("gsm.vivo.countrycode", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("persist.sys.oem.region", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("ro.product.locale.region", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = q.a("persist.sys.country", "");
        }
        if (!TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("get region from system, region = " + strA);
        }
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        String country = Locale.getDefault().getCountry();
        com.xiaomi.channel.commonutils.logger.b.m91a("locale.default.country = " + country);
        return country;
    }

    public static String d() {
        return m547a("ro.build.characteristics");
    }

    public static String e() {
        return m547a("ro.product.manufacturer");
    }

    public static String c() {
        return m547a("ro.miui.ui.version.name");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m546a() {
        int iA = C0472r.a();
        if (!m549a() || iA <= 0) {
            return "";
        }
        if (iA < 2) {
            return "alpha";
        }
        if (iA < 3) {
            return "development";
        }
        return "stable";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m547a(String str) {
        try {
            try {
                return (String) bk.a("android.os.SystemProperties", TmpConstant.PROPERTY_IDENTIFIER_GET, str, "");
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d("fail to get property. " + e2);
                return null;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m550a(Context context) {
        return context != null && m551a(context.getPackageName());
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m551a(String str) {
        return "com.xiaomi.xmsf".equals(str);
    }

    public static m a(String str) {
        m mVarB = b(str);
        return mVarB == null ? m.Global : mVarB;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m548a() {
        if (f650a != null) {
            return;
        }
        HashMap map = new HashMap();
        f650a = map;
        map.put("CN", m.China);
        Map<String, m> map2 = f650a;
        m mVar = m.Europe;
        map2.put("FI", mVar);
        f650a.put("SE", mVar);
        f650a.put(SdkConstant.CLOUDAPI_COMMAND_NOTIFY_RESPONSE, mVar);
        f650a.put("FO", mVar);
        f650a.put("EE", mVar);
        f650a.put("LV", mVar);
        f650a.put("LT", mVar);
        f650a.put("BY", mVar);
        f650a.put("MD", mVar);
        f650a.put("UA", mVar);
        f650a.put("PL", mVar);
        f650a.put("CZ", mVar);
        f650a.put("SK", mVar);
        f650a.put("HU", mVar);
        f650a.put("DE", mVar);
        f650a.put("AT", mVar);
        f650a.put("CH", mVar);
        f650a.put("LI", mVar);
        f650a.put("GB", mVar);
        f650a.put("IE", mVar);
        f650a.put("NL", mVar);
        f650a.put("BE", mVar);
        f650a.put("LU", mVar);
        f650a.put("FR", mVar);
        f650a.put(SdkConstant.CLOUDAPI_COMMAND_REGISTER_SUCCESS_RESPONSE, mVar);
        f650a.put("BG", mVar);
        f650a.put("RS", mVar);
        f650a.put("MK", mVar);
        f650a.put("AL", mVar);
        f650a.put("GR", mVar);
        f650a.put("SI", mVar);
        f650a.put("HR", mVar);
        f650a.put("IT", mVar);
        f650a.put("SM", mVar);
        f650a.put("MT", mVar);
        f650a.put("ES", mVar);
        f650a.put("PT", mVar);
        f650a.put("AD", mVar);
        f650a.put("CY", mVar);
        f650a.put("DK", mVar);
        f650a.put("IS", mVar);
        f650a.put("UK", mVar);
        f650a.put("EL", mVar);
        f650a.put("RU", m.Russia);
        f650a.put("IN", m.India);
    }

    private static m b(String str) {
        m548a();
        return f650a.get(str.toUpperCase());
    }

    public static int b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 0).versionCode;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    private static String m552b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String[] strArrSplit = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        return strArrSplit.length > 0 ? strArrSplit[0] : str;
    }

    public static int a(Context context) {
        String strM547a = m547a("ro.miui.ui.version.code");
        if (TextUtils.isEmpty(strM547a) || !TextUtils.isDigitsOnly(strM547a)) {
            return 0;
        }
        return Integer.parseInt(strM547a);
    }

    public static String a(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.toString() + " " + a(intent.getExtras());
    }

    public static String a(Bundle bundle) {
        StringBuilder sb = new StringBuilder("Bundle[");
        if (bundle == null) {
            sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
        } else {
            boolean z2 = true;
            for (String str : bundle.keySet()) {
                if (!z2) {
                    sb.append(", ");
                }
                sb.append(str);
                sb.append(com.alipay.sdk.m.n.a.f9565h);
                Object obj = bundle.get(str);
                if (obj instanceof int[]) {
                    sb.append(Arrays.toString((int[]) obj));
                } else if (obj instanceof byte[]) {
                    sb.append(Arrays.toString((byte[]) obj));
                } else if (obj instanceof boolean[]) {
                    sb.append(Arrays.toString((boolean[]) obj));
                } else if (obj instanceof short[]) {
                    sb.append(Arrays.toString((short[]) obj));
                } else if (obj instanceof long[]) {
                    sb.append(Arrays.toString((long[]) obj));
                } else if (obj instanceof float[]) {
                    sb.append(Arrays.toString((float[]) obj));
                } else if (obj instanceof double[]) {
                    sb.append(Arrays.toString((double[]) obj));
                } else if (obj instanceof String[]) {
                    sb.append(Arrays.toString((String[]) obj));
                } else if (obj instanceof CharSequence[]) {
                    sb.append(Arrays.toString((CharSequence[]) obj));
                } else if (obj instanceof Parcelable[]) {
                    sb.append(Arrays.toString((Parcelable[]) obj));
                } else if (obj instanceof Bundle) {
                    sb.append(a((Bundle) obj));
                } else {
                    sb.append(obj);
                }
                z2 = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

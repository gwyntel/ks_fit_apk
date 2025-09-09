package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.kingsmith.miot.KsProperty;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static String f23966a = null;

    /* renamed from: a, reason: collision with other field name */
    private static final Set<String> f563a;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f564a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f23967b = null;

    /* renamed from: c, reason: collision with root package name */
    private static String f23968c = "";

    /* renamed from: d, reason: collision with root package name */
    private static String f23969d;

    /* renamed from: e, reason: collision with root package name */
    private static String f23970e;

    /* renamed from: f, reason: collision with root package name */
    private static final String f23971f = String.valueOf((char) 2);

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f565a = {"--", "a-", "u-", "v-", "o-", "g-", "d-"};

    static {
        HashSet hashSet = new HashSet();
        f563a = hashSet;
        hashSet.add("com.xiaomi.xmsf");
        hashSet.add("com.xiaomi.finddevice");
        hashSet.add("com.miui.securitycenter");
        f564a = true;
    }

    private static double a(double d2) {
        int i2 = 1;
        while (true) {
            double d3 = i2;
            if (d3 >= d2) {
                return d3;
            }
            i2 <<= 1;
        }
    }

    private static boolean b(String str) {
        if (str == null) {
            return true;
        }
        String strTrim = str.trim();
        return strTrim.length() == 0 || strTrim.equalsIgnoreCase(TmpConstant.GROUP_ROLE_UNKNOWN) || strTrim.equalsIgnoreCase("unknown");
    }

    @Deprecated
    public static String c(Context context) {
        return null;
    }

    @Deprecated
    public static String d(Context context) {
        return null;
    }

    @Deprecated
    public static String e(Context context) {
        return null;
    }

    @Deprecated
    public static String f(Context context) {
        return "";
    }

    public static synchronized String g(Context context) {
        String str = f23970e;
        if (str != null) {
            return str;
        }
        String strB = bp.b(b(context) + a(context));
        f23970e = strB;
        return strB;
    }

    public static synchronized String h(Context context) {
        return bp.b(b(context) + ((String) null));
    }

    public static String i(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    @Deprecated
    private static String j(Context context) {
        return "";
    }

    private static String k(Context context) {
        String string = context.getSharedPreferences(DeviceRequestsHelper.DEVICE_INFO_PARAM, 0).getString(AlcsPalConst.DEFAULT_PLUGIN_ID, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String strL = l(context);
        a(context, strL);
        return strL;
    }

    private static String l(Context context) {
        return bm.a(Build.BRAND + OpenAccountUIConstants.UNDER_LINE + Build.MODEL + OpenAccountUIConstants.UNDER_LINE + Build.VERSION.SDK_INT + OpenAccountUIConstants.UNDER_LINE + Build.VERSION.RELEASE + OpenAccountUIConstants.UNDER_LINE + Build.VERSION.INCREMENTAL + OpenAccountUIConstants.UNDER_LINE + a() + OpenAccountUIConstants.UNDER_LINE + context.getPackageName() + OpenAccountUIConstants.UNDER_LINE + System.currentTimeMillis() + OpenAccountUIConstants.UNDER_LINE + bp.a(16));
    }

    @Deprecated
    public static String a(Context context) {
        return null;
    }

    public static String c() {
        return b() + "KB";
    }

    public static String d() {
        return (a(Environment.getDataDirectory()) / 1024) + "KB";
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String m495a(int i2) {
        if (i2 > 0) {
            String[] strArr = f565a;
            if (i2 < strArr.length) {
                return strArr[i2];
            }
        }
        return f565a[0];
    }

    /* renamed from: c, reason: collision with other method in class */
    private static boolean m501c(Context context) {
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            return true;
        }
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(context.getPackageName(), "com.xiaomi.push.service.XMPushService");
        intent.setComponent(componentName);
        try {
            Bundle bundle = context.getPackageManager().getServiceInfo(componentName, 128).metaData;
            if (bundle != null) {
                String string = bundle.getString("supportGetAndroidID");
                if (TextUtils.isEmpty(string)) {
                    return true;
                }
                return Boolean.parseBoolean(string);
            }
        } catch (Exception unused) {
        }
        return true;
    }

    public static String b(Context context) {
        String str = f23967b;
        if (str != null || !f564a) {
            return str;
        }
        boolean zM501c = m501c(context);
        f564a = zM501c;
        if (!zM501c) {
            return null;
        }
        try {
            f23967b = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.m91a("failure to get androidId: " + th);
        }
        return f23967b;
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int i2 = 0;
        while (true) {
            String[] strArr = f565a;
            if (i2 >= strArr.length) {
                return false;
            }
            if (str.startsWith(strArr[i2])) {
                return true;
            }
            i2++;
        }
    }

    public static synchronized String a(Context context, boolean z2) {
        int i2;
        try {
            if (f23969d == null) {
                String strB = b(context);
                String strC = "";
                if (!j.m555d()) {
                    strC = z2 ? c(context) : j(context);
                }
                String strA = a(context);
                if (Build.VERSION.SDK_INT >= 26 && b(strC) && b(strA)) {
                    String strB2 = ax.a(context).b();
                    if (!TextUtils.isEmpty(strB2)) {
                        strB = strB2 + strB;
                        i2 = 2;
                    } else {
                        String strMo179a = ax.a(context).mo179a();
                        if (!TextUtils.isEmpty(strMo179a) && !strMo179a.startsWith("00000000-0000-0000-0000-000000000000")) {
                            i2 = 4;
                            strB = strMo179a;
                        } else if (TextUtils.isEmpty(strB)) {
                            strB = k(context);
                            i2 = 6;
                        } else {
                            i2 = 5;
                        }
                    }
                } else {
                    strB = strC + strB + strA;
                    i2 = 1;
                }
                com.xiaomi.channel.commonutils.logger.b.b("devid rule select:" + i2);
                if (i2 == 3) {
                    f23969d = strB;
                } else {
                    f23969d = m495a(i2) + bp.b(strB);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23969d;
    }

    public static int b() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        String[] strArrSplit;
        int i2 = 0;
        if (new File("/proc/meminfo").exists()) {
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
                } catch (Exception unused) {
                } catch (Throwable th2) {
                    bufferedReader = null;
                    th = th2;
                }
                try {
                    String line = bufferedReader.readLine();
                    if (!TextUtils.isEmpty(line) && (strArrSplit = line.split("\\s+")) != null && strArrSplit.length >= 2 && TextUtils.isDigitsOnly(strArrSplit[1])) {
                        i2 = Integer.parseInt(strArrSplit[1]);
                    }
                    bufferedReader.close();
                } catch (Exception unused2) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return i2;
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused4) {
            }
        }
        return i2;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static String m499b() {
        return a(((a(Environment.getDataDirectory()) / 1024.0d) / 1024.0d) / 1024.0d) + "GB";
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m500b(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(KsProperty.Power);
        return powerManager == null || powerManager.isScreenOn();
    }

    @TargetApi(17)
    public static int a() {
        Object objA = bk.a("android.os.UserHandle", "myUserId", new Object[0]);
        if (objA == null) {
            return -1;
        }
        return ((Integer) Integer.class.cast(objA)).intValue();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m494a() {
        return a(b()) + "GB";
    }

    private static float a(int i2) {
        float f2 = (((((i2 + 102400) / 524288) + 1) * 524288) / 1024.0f) / 1024.0f;
        double d2 = f2;
        return d2 > 0.5d ? (float) Math.ceil(d2) : f2;
    }

    private static long a(File file) {
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m497a(Context context) {
        Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (intentRegisterReceiver == null) {
            return false;
        }
        int intExtra = intentRegisterReceiver.getIntExtra("status", -1);
        return intExtra == 2 || intExtra == 5;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m496a() {
        return a() <= 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m498a(Context context, String str) {
        ApplicationInfo applicationInfo;
        PackageInfo packageInfo = (PackageInfo) bk.a((Object) context.getPackageManager(), "getPackageInfoAsUser", str, 0, 999);
        return packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null || (applicationInfo.flags & 8388608) != 8388608;
    }

    private static void a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DeviceRequestsHelper.DEVICE_INFO_PARAM, 0);
        if (TextUtils.isEmpty(sharedPreferences.getString(AlcsPalConst.DEFAULT_PLUGIN_ID, null))) {
            sharedPreferences.edit().putString(AlcsPalConst.DEFAULT_PLUGIN_ID, str).apply();
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("default_id exist,do not change it.");
        }
    }
}

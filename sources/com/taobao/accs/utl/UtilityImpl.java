package com.taobao.accs.utl;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.HMacUtil;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class UtilityImpl {
    public static final String NET_TYPE_2G = "2g";
    public static final String NET_TYPE_3G = "3g";
    public static final String NET_TYPE_4G = "4g";
    public static final String NET_TYPE_UNKNOWN = "unknown";
    public static final String NET_TYPE_WIFI = "wifi";

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f20350a = new byte[0];

    public static String a(Context context) {
        String string = context.getSharedPreferences(Constants.SP_FILE_NAME, 4).getString(Constants.KEY_PROXY_HOST, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String strF = f();
        if (TextUtils.isEmpty(strF)) {
            return null;
        }
        return strF;
    }

    public static int b() {
        return -1;
    }

    public static byte[] c() {
        return null;
    }

    public static boolean d(Context context) throws Throwable {
        boolean z2;
        boolean z3;
        if (context == null) {
            return false;
        }
        try {
            try {
            } catch (Throwable th) {
                th = th;
                z2 = context;
            }
        } catch (Exception e2) {
            e = e2;
            z2 = false;
        }
        synchronized (f20350a) {
            try {
                z3 = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getBoolean(Constants.KEY_FOUCE_DISABLE, false);
                return z3;
            } catch (Throwable th2) {
                th = th2;
                z2 = false;
                try {
                    throw th;
                } catch (Exception e3) {
                    e = e3;
                    ALog.e("UtilityImpl", "getFocusDisableStatus", e, new Object[0]);
                    z3 = z2;
                    return z3;
                }
            }
        }
    }

    public static void disableService(Context context) {
        ComponentName componentName = new ComponentName(context, j.channelService);
        PackageManager packageManager = context.getPackageManager();
        try {
            ALog.d("UtilityImpl", "disableService, cn=" + componentName.toString(), new Object[0]);
            if (packageManager.getServiceInfo(componentName, 128).enabled) {
                packageManager.setComponentEnabledSetting(componentName, 2, 1);
                killService(context);
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean e(Context context) {
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.e("UtilityImpl", j.a(e2), new Object[0]);
        }
        return context.getPackageManager().getServiceInfo(new ComponentName(context, j.channelService), 128).enabled;
    }

    public static void enableService(Context context) {
        ComponentName componentName = new ComponentName(context, j.channelService);
        ALog.d("UtilityImpl", "enableService", "comptName", componentName);
        try {
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e2) {
            ALog.w("UtilityImpl", "enableService", e2, new Object[0]);
        }
    }

    public static boolean f(Context context) {
        context.getPackageName();
        ComponentName componentName = new ComponentName(context, com.taobao.accs.client.a.b());
        PackageManager packageManager = context.getPackageManager();
        if (!componentName.getPackageName().equals("!")) {
            return packageManager.getServiceInfo(componentName, 128).enabled;
        }
        ALog.e("UtilityImpl", "getAgooServiceEnabled,exception,comptName.getPackageName()=" + componentName.getPackageName(), new Object[0]);
        return false;
    }

    public static void focusDisableService(Context context) {
        try {
            synchronized (f20350a) {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                editorEdit.putBoolean(Constants.KEY_FOUCE_DISABLE, true);
                editorEdit.apply();
                disableService(context);
            }
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "focusDisableService", th, new Object[0]);
        }
    }

    public static void focusEnableService(Context context) {
        try {
            synchronized (f20350a) {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                editorEdit.putBoolean(Constants.KEY_FOUCE_DISABLE, false);
                editorEdit.apply();
                enableService(context);
            }
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "focusEnableService", th, new Object[0]);
        }
    }

    public static String g(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable unused) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo == null) {
            return "unknown";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        String subtypeName = activeNetworkInfo.getSubtypeName();
        return !TextUtils.isEmpty(subtypeName) ? subtypeName.replaceAll(" ", "") : "";
    }

    public static String h(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "getNetworkTypeExt", th, new Object[0]);
            return null;
        }
        if (activeNetworkInfo == null) {
            return "unknown";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return NET_TYPE_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NET_TYPE_3G;
            case 13:
                return NET_TYPE_4G;
            default:
                String subtypeName = activeNetworkInfo.getSubtypeName();
                if (TextUtils.isEmpty(subtypeName)) {
                    return "unknown";
                }
                if (!subtypeName.equalsIgnoreCase("td-scdma") && !subtypeName.equalsIgnoreCase("td_scdma")) {
                    if (!subtypeName.equalsIgnoreCase("tds_hsdpa")) {
                        return "unknown";
                    }
                }
                return NET_TYPE_3G;
        }
        ALog.e("UtilityImpl", "getNetworkTypeExt", th, new Object[0]);
        return null;
    }

    public static boolean i(Context context) {
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = GlobalClientInfo.getInstance(context).getConnectivityManager().getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isConnected();
                }
            } catch (Throwable th) {
                ALog.e("UtilityImpl", "isNetworkConnected", th, new Object[0]);
            }
        }
        return false;
    }

    public static boolean isMainProcess(Context context) {
        return j.a(context);
    }

    public static String j(Context context) {
        return j.b(context);
    }

    public static long k(Context context) {
        SharedPreferences sharedPreferences;
        long j2;
        long j3 = 0;
        try {
            sharedPreferences = context.getSharedPreferences(Constants.SP_CHANNEL_FILE_NAME, 0);
            long j4 = sharedPreferences.getLong(Constants.SP_KEY_SERVICE_START, 0L);
            j2 = j4 > 0 ? sharedPreferences.getLong(Constants.SP_KEY_SERVICE_END, 0L) - j4 : 0L;
        } catch (Throwable th) {
            th = th;
        }
        try {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putLong(Constants.SP_KEY_SERVICE_START, 0L);
            editorEdit.putLong(Constants.SP_KEY_SERVICE_END, 0L);
            editorEdit.apply();
            return j2;
        } catch (Throwable th2) {
            th = th2;
            j3 = j2;
            ALog.e("UtilityImpl", "getServiceAliveTime:", th, new Object[0]);
            return j3;
        }
    }

    public static void killService(Context context) {
        try {
            int iMyUid = Process.myUid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            if (activityManager == null) {
                return;
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.uid == iMyUid) {
                    if (!TextUtils.isEmpty(com.taobao.accs.client.a.f20082d) && com.taobao.accs.client.a.f20082d.equals(runningAppProcessInfo.processName)) {
                        ALog.e("UtilityImpl", "killService", "processName", runningAppProcessInfo.processName);
                        Process.killProcess(runningAppProcessInfo.pid);
                        return;
                    } else if (runningAppProcessInfo.processName.endsWith(":channel")) {
                        ALog.e("UtilityImpl", "killService", "processName", runningAppProcessInfo.processName);
                        Process.killProcess(runningAppProcessInfo.pid);
                        return;
                    }
                }
            }
            ALog.e("UtilityImpl", "kill nothing", new Object[0]);
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "killService", th, new Object[0]);
        }
    }

    public static String l(Context context) {
        try {
            return GlobalClientInfo.getInstance(context).getPackageInfo().versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String m(Context context) {
        try {
            return context.getSharedPreferences(Constants.SP_COOKIE_FILE_NAME, 4).getString(Constants.SP_KEY_COOKIE_SEC, null);
        } catch (Exception e2) {
            ALog.e("UtilityImpl", "reStoreCookie fail", e2, new Object[0]);
            return null;
        }
    }

    public static void n(Context context) {
        try {
            GlobalClientInfo.f20067c = null;
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_COOKIE_FILE_NAME, 0).edit();
            editorEdit.clear();
            editorEdit.apply();
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "clearCookie fail", th, new Object[0]);
        }
    }

    public static String o(Context context) {
        return j.c(context);
    }

    private static void p(Context context) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            editorEdit.putInt(Constants.KEY_APP_VERSION_CODE, GlobalClientInfo.getInstance(context).getPackageInfo().versionCode);
            editorEdit.putString(Constants.KEY_APP_VERSION_NAME, GlobalClientInfo.getInstance(context).getPackageInfo().versionName);
            editorEdit.apply();
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "saveAppVersion", th, new Object[0]);
        }
    }

    public static int b(Context context) {
        int i2 = context.getSharedPreferences(Constants.SP_FILE_NAME, 4).getInt(Constants.KEY_PROXY_PORT, -1);
        if (i2 > 0) {
            return i2;
        }
        if (a(context) == null) {
            return -1;
        }
        try {
            return g();
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static boolean c(Context context) {
        String str;
        int i2;
        synchronized (f20350a) {
            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME, 0);
                PackageInfo packageInfo = GlobalClientInfo.getInstance(context).getPackageInfo();
                int i3 = sharedPreferences.getInt(Constants.KEY_APP_VERSION_CODE, -1);
                String string = sharedPreferences.getString(Constants.KEY_APP_VERSION_NAME, "");
                if (packageInfo != null) {
                    i2 = packageInfo.versionCode;
                    str = packageInfo.versionName;
                } else {
                    str = null;
                    i2 = 0;
                }
                if (i3 == i2 && string.equals(str)) {
                    return false;
                }
                p(context);
                ALog.i("UtilityImpl", "appVersionChanged", "oldV", Integer.valueOf(i3), "nowV", Integer.valueOf(i2), "oldN", string, "nowN", str);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static String d(String str, Context context) {
        String string;
        try {
            synchronized (f20350a) {
                string = context.getSharedPreferences(str, 0).getString("utdid", j.b(context));
            }
            return string;
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "getUtdid", th, new Object[0]);
            return "";
        }
    }

    public static String i() throws ClassNotFoundException {
        Class<?>[] clsArr = {String.class};
        Object[] objArr = {com.alipay.sdk.m.c.a.f9195a};
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, clsArr).invoke(cls, objArr);
            ALog.d("UtilityImpl", "getEmuiVersion", "result", str);
            return !TextUtils.isEmpty(str) ? str : "";
        } catch (Exception e2) {
            ALog.e("UtilityImpl", "getEmuiVersion", e2, new Object[0]);
            return "";
        }
    }

    public static String a(long j2) {
        try {
            return new SimpleDateFormat(CalendarUtils.yyyy_MM_dd, Locale.US).format(Long.valueOf(j2));
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "formatDay", th, new Object[0]);
            return "";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean b(String str, Context context) {
        String str2;
        boolean z2;
        try {
            try {
            } catch (Throwable th) {
                th = th;
                str2 = str;
            }
        } catch (Throwable th2) {
            th = th2;
            str2 = null;
        }
        synchronized (f20350a) {
            try {
                z2 = !context.getSharedPreferences(str, 0).getString("notification_state", "").equals(j.c(context));
                return z2;
            } catch (Throwable th3) {
                th = th3;
                str2 = null;
                try {
                    throw th;
                } catch (Throwable th4) {
                    th = th4;
                    ALog.e("UtilityImpl", "notificationStateChanged", th, new Object[0]);
                    z2 = str2;
                    return z2;
                }
            }
        }
    }

    public static String f() {
        return System.getProperty("http.proxyHost");
    }

    public static long e() {
        return j.a();
    }

    public static boolean a() {
        try {
            return !GlobalAppRuntimeInfo.isAppBackground();
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "isForeground error ", th, new Object[0]);
            return false;
        }
    }

    public static int g() {
        try {
            return Integer.parseInt(System.getProperty("http.proxyPort"));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static String a(String str, String str2, String str3) {
        String strHmacSha1Hex = null;
        if (TextUtils.isEmpty(str)) {
            ALog.e("UtilityImpl", "getAppsign appkey null", new Object[0]);
            return null;
        }
        try {
            if (!TextUtils.isEmpty(str2)) {
                strHmacSha1Hex = HMacUtil.hmacSha1Hex(str2.getBytes(), (str + str3).getBytes());
            } else {
                ALog.e("UtilityImpl", "getAppsign secret null", new Object[0]);
            }
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "getAppsign", th, new Object[0]);
        }
        return strHmacSha1Hex;
    }

    public static void b(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            GlobalClientInfo.f20067c = str;
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_COOKIE_FILE_NAME, 0).edit();
            editorEdit.putString(Constants.SP_KEY_COOKIE_SEC, str);
            editorEdit.apply();
        } catch (Exception e2) {
            ALog.e("UtilityImpl", "storeCookie fail", e2, new Object[0]);
        }
    }

    public static String d() {
        return TmpConstant.GROUP_ROLE_UNKNOWN;
    }

    public static void c(String str, Context context) {
        try {
            synchronized (f20350a) {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
                editorEdit.putString("utdid", j.b(context));
                editorEdit.apply();
            }
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "saveUtdid", th, new Object[0]);
        }
    }

    public static boolean a(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Throwable unused) {
            ALog.e("UtilityImpl", "package not exist", "pkg", str);
            return false;
        }
    }

    public static String h() {
        String str = f() + ":" + g();
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.d("UtilityImpl", "getProxy:" + str, new Object[0]);
        }
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(String str, Context context) {
        String str2;
        boolean z2;
        try {
            try {
            } catch (Throwable th) {
                th = th;
                str2 = str;
            }
        } catch (Throwable th2) {
            th = th2;
            str2 = null;
        }
        synchronized (f20350a) {
            try {
                z2 = !context.getSharedPreferences(str, 0).getString("utdid", "").equals(j.b(context));
                return z2;
            } catch (Throwable th3) {
                th = th3;
                str2 = null;
                try {
                    throw th;
                } catch (Throwable th4) {
                    th = th4;
                    ALog.e("UtilityImpl", "utdidChanged", th, new Object[0]);
                    z2 = str2;
                    return z2;
                }
            }
        }
    }

    public static String b(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    public static void a(Context context, String str, long j2) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.SP_CHANNEL_FILE_NAME, 0).edit();
            editorEdit.putLong(str, j2);
            editorEdit.apply();
            ALog.d("UtilityImpl", "setServiceTime:" + j2, new Object[0]);
        } catch (Throwable th) {
            ALog.e("UtilityImpl", "setServiceTime:", th, new Object[0]);
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 4).edit();
            editorEdit.putString("notification_state", str2);
            editorEdit.apply();
        } catch (Exception e2) {
            ALog.e("UtilityImpl", "saveNotificationState fail", e2, new Object[0]);
        }
    }

    public static int a(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return str.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String a(Throwable th) {
        return j.a(th);
    }

    public static String a(int i2) {
        try {
            return String.valueOf(i2);
        } catch (Exception e2) {
            ALog.e("UtilityImpl", "int2String", e2, new Object[0]);
            return null;
        }
    }

    public static final Map<String, String> a(Map<String, List<String>> map) {
        HashMap map2 = new HashMap();
        try {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (!TextUtils.isEmpty(key)) {
                    String strA = a(entry.getValue());
                    if (!TextUtils.isEmpty(strA)) {
                        if (!key.startsWith(":")) {
                            key = key.toLowerCase(Locale.US);
                        }
                        map2.put(key, strA);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return map2;
    }

    public static final String a(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            stringBuffer.append(list.get(i2));
            if (i2 < size - 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    public static boolean a(long j2, long j3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(j2));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date(j3));
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }
}

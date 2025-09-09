package anet.channel.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.AwcnConfig;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
class b {

    /* renamed from: t, reason: collision with root package name */
    private static Method f6943t;

    /* renamed from: m, reason: collision with root package name */
    private static String[] f6936m = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};

    /* renamed from: a, reason: collision with root package name */
    static volatile Context f6924a = null;

    /* renamed from: b, reason: collision with root package name */
    static volatile boolean f6925b = false;

    /* renamed from: c, reason: collision with root package name */
    static volatile NetworkStatusHelper.NetworkStatus f6926c = NetworkStatusHelper.NetworkStatus.NONE;

    /* renamed from: d, reason: collision with root package name */
    static volatile String f6927d = "unknown";

    /* renamed from: e, reason: collision with root package name */
    static volatile String f6928e = "";

    /* renamed from: f, reason: collision with root package name */
    static volatile String f6929f = "";

    /* renamed from: g, reason: collision with root package name */
    static volatile String f6930g = "";

    /* renamed from: h, reason: collision with root package name */
    static volatile String f6931h = "unknown";

    /* renamed from: i, reason: collision with root package name */
    static volatile String f6932i = "";

    /* renamed from: j, reason: collision with root package name */
    static volatile Pair<String, Integer> f6933j = null;

    /* renamed from: k, reason: collision with root package name */
    static volatile boolean f6934k = false;

    /* renamed from: l, reason: collision with root package name */
    static volatile List<InetAddress> f6935l = Collections.EMPTY_LIST;

    /* renamed from: n, reason: collision with root package name */
    private static volatile boolean f6937n = false;

    /* renamed from: o, reason: collision with root package name */
    private static volatile boolean f6938o = false;

    /* renamed from: p, reason: collision with root package name */
    private static ConnectivityManager f6939p = null;

    /* renamed from: q, reason: collision with root package name */
    private static TelephonyManager f6940q = null;

    /* renamed from: r, reason: collision with root package name */
    private static WifiManager f6941r = null;

    /* renamed from: s, reason: collision with root package name */
    private static SubscriptionManager f6942s = null;

    /* renamed from: u, reason: collision with root package name */
    private static BroadcastReceiver f6944u = new BroadcastReceiver() { // from class: anet.channel.status.NetworkStatusMonitor$2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.NetworkStatusMonitor", "receiver:" + intent.getAction(), null, new Object[0]);
            }
            ThreadPoolExecutorFactory.submitScheduledTask(new d(this));
        }
    };

    b() {
    }

    static void a() {
        if (f6937n || f6924a == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION);
        try {
            f6924a.registerReceiver(f6944u, intentFilter);
        } catch (Exception unused) {
            ALog.e("awcn.NetworkStatusMonitor", "registerReceiver failed", null, new Object[0]);
        }
        d();
        f6937n = true;
    }

    static void b() {
        if (f6924a != null) {
            f6924a.unregisterReceiver(f6944u);
        }
    }

    static void c() {
        if (Build.VERSION.SDK_INT < 24 || f6938o) {
            return;
        }
        NetworkInfo networkInfoE = e();
        f6925b = networkInfoE != null && networkInfoE.isConnected();
        f6939p.registerDefaultNetworkCallback(new c());
        f6938o = true;
    }

    static void d() {
        boolean z2;
        NetworkInfo networkInfoE;
        WifiInfo wifiInfoI;
        ALog.d("awcn.NetworkStatusMonitor", "[checkNetworkStatus]", null, new Object[0]);
        NetworkStatusHelper.NetworkStatus networkStatus = f6926c;
        String str = f6928e;
        String str2 = f6929f;
        try {
            try {
                networkInfoE = e();
                z2 = false;
            } catch (Exception e2) {
                ALog.e("awcn.NetworkStatusMonitor", "getNetworkInfo exception", null, e2, new Object[0]);
                a(NetworkStatusHelper.NetworkStatus.NONE, "unknown");
                z2 = true;
                networkInfoE = null;
            }
            if (!z2) {
                if (networkInfoE == null || !networkInfoE.isConnected()) {
                    a(NetworkStatusHelper.NetworkStatus.NO, "no network");
                    ALog.i("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "no network");
                } else {
                    ALog.i("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, "info.isConnected", Boolean.valueOf(networkInfoE.isConnected()), "info.isAvailable", Boolean.valueOf(networkInfoE.isAvailable()), "info.getType", Integer.valueOf(networkInfoE.getType()));
                    if (networkInfoE.getType() == 0) {
                        String subtypeName = networkInfoE.getSubtypeName();
                        String strReplace = TextUtils.isEmpty(subtypeName) ? "" : subtypeName.replace(" ", "");
                        a(a(networkInfoE.getSubtype(), strReplace), strReplace);
                        f6928e = a(networkInfoE.getExtraInfo());
                        h();
                    } else if (networkInfoE.getType() == 1) {
                        a(NetworkStatusHelper.NetworkStatus.WIFI, "wifi");
                        if (AwcnConfig.isWifiInfoEnable() && (wifiInfoI = i()) != null && b("android.permission.ACCESS_FINE_LOCATION")) {
                            f6930g = wifiInfoI.getBSSID();
                            f6929f = wifiInfoI.getSSID();
                        }
                        f6931h = "wifi";
                        f6932i = "wifi";
                        f6933j = j();
                    } else {
                        a(NetworkStatusHelper.NetworkStatus.NONE, "unknown");
                    }
                    f6934k = networkInfoE.isRoaming();
                    anet.channel.util.c.e();
                }
            }
            if (f6926c == networkStatus && f6928e.equalsIgnoreCase(str) && f6929f.equalsIgnoreCase(str2)) {
                return;
            }
            if (ALog.isPrintLog(2)) {
                NetworkStatusHelper.printNetworkDetail();
            }
            NetworkStatusHelper.notifyStatusChanged(f6926c);
        } catch (Exception e3) {
            ALog.e("awcn.NetworkStatusMonitor", "checkNetworkStatus", null, e3, new Object[0]);
        }
    }

    static NetworkInfo e() {
        if (f6939p == null) {
            f6939p = (ConnectivityManager) f6924a.getSystemService("connectivity");
        }
        return f6939p.getActiveNetworkInfo();
    }

    static String f() throws NoSuchMethodException, SecurityException {
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class);
            for (String str : f6936m) {
                String str2 = (String) method.invoke(null, str);
                if (!TextUtils.isEmpty(str2)) {
                    return str2;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    static int g() {
        if (f6939p == null || Build.VERSION.SDK_INT < 24) {
            return -1;
        }
        return f6939p.getRestrictBackgroundStatus();
    }

    private static void h() {
        try {
            if (AwcnConfig.isCarrierInfoEnable() && b("android.permission.READ_PHONE_STATE")) {
                if (f6940q == null) {
                    f6940q = (TelephonyManager) f6924a.getSystemService("phone");
                }
                f6932i = f6940q.getSimOperator();
                if (f6942s == null) {
                    SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(f6924a);
                    f6942s = subscriptionManagerFrom;
                    f6943t = subscriptionManagerFrom.getClass().getDeclaredMethod("getDefaultDataSubscriptionInfo", null);
                }
                Method method = f6943t;
                if (method != null) {
                    f6931h = ((SubscriptionInfo) method.invoke(f6942s, null)).getCarrierName().toString();
                }
            }
        } catch (Exception unused) {
        }
    }

    private static WifiInfo i() {
        try {
            if (f6941r == null) {
                f6941r = (WifiManager) f6924a.getSystemService("wifi");
            }
            return f6941r.getConnectionInfo();
        } catch (Throwable th) {
            ALog.e("awcn.NetworkStatusMonitor", "getWifiInfo", null, th, new Object[0]);
            return null;
        }
    }

    private static Pair<String, Integer> j() {
        try {
            String property = System.getProperty("http.proxyHost");
            if (TextUtils.isEmpty(property)) {
                return null;
            }
            return Pair.create(property, Integer.valueOf(Integer.parseInt(System.getProperty("http.proxyPort"))));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static boolean b(String str) {
        return f6924a.checkSelfPermission(str) == 0;
    }

    private static void a(NetworkStatusHelper.NetworkStatus networkStatus, String str) {
        f6926c = networkStatus;
        f6927d = str;
        f6928e = "";
        f6929f = "";
        f6930g = "";
        f6933j = null;
        f6931h = "";
        f6932i = "";
    }

    private static NetworkStatusHelper.NetworkStatus a(int i2, String str) {
        switch (i2) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkStatusHelper.NetworkStatus.G2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkStatusHelper.NetworkStatus.G3;
            case 13:
            case 18:
            case 19:
                return NetworkStatusHelper.NetworkStatus.G4;
            case 20:
                return NetworkStatusHelper.NetworkStatus.G5;
            default:
                if (!str.equalsIgnoreCase("TD-SCDMA") && !str.equalsIgnoreCase("WCDMA") && !str.equalsIgnoreCase("CDMA2000")) {
                    return NetworkStatusHelper.NetworkStatus.NONE;
                }
                return NetworkStatusHelper.NetworkStatus.G3;
        }
    }

    private static String a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String lowerCase = str.toLowerCase(Locale.US);
            if (lowerCase.contains("cmwap")) {
                return "cmwap";
            }
            if (lowerCase.contains("uniwap")) {
                return "uniwap";
            }
            if (lowerCase.contains("3gwap")) {
                return "3gwap";
            }
            if (lowerCase.contains("ctwap")) {
                return "ctwap";
            }
            if (lowerCase.contains("cmnet")) {
                return "cmnet";
            }
            if (lowerCase.contains("uninet")) {
                return "uninet";
            }
            if (lowerCase.contains("3gnet")) {
                return "3gnet";
            }
            if (lowerCase.contains("ctnet")) {
                return "ctnet";
            }
        }
        return "unknown";
    }
}

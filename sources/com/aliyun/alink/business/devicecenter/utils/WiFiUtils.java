package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil;
import com.taobao.accs.utl.BaseMonitor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class WiFiUtils {
    public static final int NO_PASSWORD_WIFI = 0;
    public static final int WEP_CIPHER_WIFI = 1;
    public static final int WPA_CIPHER_WIFI = 2;

    public static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if (str.contains("WEP")) {
            return 1;
        }
        if (str.contains("PSK")) {
            return 2;
        }
        if (str.contains("EAP")) {
            return 3;
        }
        return str.contains("WAPI") ? 4 : 0;
    }

    public static String addQuotes(String str) {
        return "\"" + str + "\"";
    }

    public static int connect(Context context, String str, String str2, String str3, String str4, int i2) {
        WifiManager wifiManager;
        ALog.d("WiFiUtils", "connect() called with: context = [" + context + "], ssid = [" + str + "], psd = [" + str2 + "], bssid = [" + str3 + "], capabilities = [" + str4 + "], lastNetworkId = [" + i2 + "]");
        if (context == null) {
            ALog.e("WiFiUtils", "context is null.");
            return -1;
        }
        try {
            wifiManager = (WifiManager) context.getSystemService("wifi");
            try {
                if (isSameWiFi((ConnectivityManager) context.getSystemService("connectivity"), wifiManager, str, str3)) {
                    ALog.w("WiFiUtils", "connect isSameWifi with current connect is true. return.");
                    return 0;
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return a(wifiManager, str, str2, str3, str4, i2);
            }
        } catch (Exception e3) {
            e = e3;
            wifiManager = null;
        }
        return a(wifiManager, str, str2, str3, str4, i2);
    }

    public static void disconnectAllWifi(WifiManager wifiManager) {
        try {
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            for (int i2 = 0; i2 < configuredNetworks.size(); i2++) {
                disconnectWifi(wifiManager, configuredNetworks.get(i2).networkId);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void disconnectCurrentWifi(WifiManager wifiManager) {
        if (wifiManager == null) {
            return;
        }
        try {
            disconnectWifi(wifiManager, wifiManager.getConnectionInfo().getNetworkId());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void disconnectWifi(WifiManager wifiManager, int i2) {
        try {
            wifiManager.disconnect();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String getBroadcastIp() throws SocketException, UnknownHostException {
        InetAddress broadcast;
        InetAddress ipAddress = WifiManagerUtil.getIpAddress(WifiManagerUtil.NetworkType.WLAN);
        if (ipAddress == null) {
            ALog.w("WiFiUtils", "getBroadcastIp address=null.");
            try {
                ipAddress = InetAddress.getByName(WifiManagerUtil.getWifiIP(ContextHolder.getInstance().getAppContext()));
            } catch (UnknownHostException e2) {
                ALog.w("WiFiUtils", "getBroadcastIp  getByName exception=" + e2);
            }
        }
        if (ipAddress != null) {
            ALog.d("WiFiUtils", "getBroadcastIp address not null, ip=" + ipAddress.getHostAddress());
            try {
                broadcast = WifiManagerUtil.getBroadcast(ipAddress);
            } catch (Exception e3) {
                ALog.w("WiFiUtils", "getBroadcast exception=" + e3);
            }
        } else {
            broadcast = null;
        }
        return broadcast == null ? "255.255.255.255" : broadcast.getHostAddress();
    }

    public static String getCurrentApBssid() {
        WifiInfo connectionInfo;
        Context appContext = ContextHolder.getInstance().getAppContext();
        if (appContext == null || (connectionInfo = ((WifiManager) appContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo()) == null) {
            return null;
        }
        return connectionInfo.getBSSID();
    }

    public static int getKeyManagerType(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.allowedKeyManagement.get(1) ? 2 : 0;
    }

    public static List<ScanResult> getScanResult(Context context) {
        ALog.d("WiFiUtils", "getScanResult called.");
        if (context == null || context.getApplicationContext() == null) {
            ALog.w("WiFiUtils", "context = null.");
            return null;
        }
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            return wifiManager.getScanResults();
        }
        return null;
    }

    public static boolean isNetworkAvaiable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            ALog.w("WiFiUtils", "isNetworkAvaiable ConnectivityManager=null");
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isSameWiFi(ConnectivityManager connectivityManager, WifiManager wifiManager, String str, String str2) {
        WifiInfo connectionInfo;
        NetworkInfo activeNetworkInfo;
        if (wifiManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (connectionInfo = wifiManager.getConnectionInfo()) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || activeNetworkInfo.getState() != NetworkInfo.State.CONNECTED || activeNetworkInfo.getType() != 1 || !str.equals(connectionInfo.getSSID()) || !str2.equals(connectionInfo.getBSSID())) {
            return false;
        }
        ALog.i("WiFiUtils", "isSameWiFi=true ssid=" + str + ", bssid=" + str2);
        return true;
    }

    public static boolean isWiFiConnected(Context context) {
        if (context != null && context.getApplicationContext() != null) {
            try {
                if (((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static boolean startScan(Context context) {
        ALog.d("WiFiUtils", "startScan called.");
        if (context == null || context.getApplicationContext() == null) {
            ALog.w("WiFiUtils", "context = null.");
            return false;
        }
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            return wifiManager.startScan();
        }
        return false;
    }

    public static boolean updateWiFiConfiguration(WifiConfiguration wifiConfiguration, String str, String str2, int i2, String str3) {
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("type=");
        sb.append(i2);
        ALog.d("WiFiUtils", sb.toString());
        if (i2 == 0) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else if (i2 == 1) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (i2 == 2) {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.allowedKeyManagement.set(1);
            boolean z2 = str3 != null && str3.contains("CCMP");
            boolean z3 = str3 != null && str3.contains("TKIP");
            if (z3 && z2) {
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
            } else if (z3) {
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
            } else if (z2) {
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
            }
            wifiConfiguration.status = 0;
        }
        return true;
    }

    public static int a(WifiManager wifiManager, String str, String str2, String str3, String str4, int i2) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        WifiConfiguration next;
        boolean z2;
        boolean z3;
        try {
            Iterator<WifiConfiguration> it = wifiManager.getConfiguredNetworks().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (i2 != -1 && i2 == next.networkId) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("networkId=");
                    sb.append(i2);
                    sb.append(", found match network Id from network configuration.");
                    ALog.i("WiFiUtils", sb.toString());
                    break;
                }
                String str5 = next.SSID;
                if (str5 != null && str5.equals(addQuotes(str))) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("find match ssid ");
                    sb2.append(next.SSID);
                    sb2.append(" from network configuration ");
                    sb2.append(next);
                    ALog.d("WiFiUtils", sb2.toString());
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("keyManagerType=");
                    sb3.append(getKeyManagerType(next));
                    sb3.append(", connectType=");
                    sb3.append(a(str4));
                    sb3.append(", capabilities");
                    sb3.append(str4);
                    ALog.d("WiFiUtils", sb3.toString());
                    if (getKeyManagerType(next) != a(str4) && (!TextUtils.isEmpty(str3) || !AlinkHelper.isValidSoftAp(str, false))) {
                        if (str3 != null && str3.equals(next.BSSID)) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("find match bssid ");
                            sb4.append(next.BSSID);
                            sb4.append(" from network configuration");
                            ALog.i("WiFiUtils", sb4.toString());
                            break;
                        }
                    }
                }
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("wifiConfiguration=");
            sb5.append(next);
            ALog.d("WiFiUtils", sb5.toString());
            if (next != null) {
                ALog.d("WiFiUtils", "use exist configuration.");
                try {
                    Class<?> cls = Class.forName(WifiManager.class.getName());
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("wifiManager:");
                    sb6.append(cls.getName());
                    ALog.i("WiFiUtils", sb6.toString());
                    Method[] methods = cls.getMethods();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= methods.length) {
                            z3 = false;
                            break;
                        }
                        if (methods[i3].getName().equalsIgnoreCase(BaseMonitor.ALARM_POINT_CONNECT) || methods[i3].getName().equalsIgnoreCase("connectNetwork")) {
                            if (methods[i3].getParameterTypes()[0].getName().equals("int")) {
                                z3 = true;
                                break;
                            }
                        }
                        i3++;
                    }
                    ALog.i("WiFiUtils", "hasConnectWithIntParam:" + z3 + ",networkId=" + next.networkId);
                    if (z3) {
                        methods[i3].setAccessible(true);
                        ALog.d("WiFiUtils", "methodName=" + methods[i3].getName());
                        if (methods[i3].getName().equalsIgnoreCase(BaseMonitor.ALARM_POINT_CONNECT)) {
                            if (methods[i3].getParameterTypes().length > 2) {
                                ALog.d("WiFiUtils", "call connect with three parameters.");
                                methods[i3].invoke(wifiManager, null, Integer.valueOf(next.networkId), null);
                                return 1;
                            }
                            ALog.d("WiFiUtils", "call connect with two parameters.");
                            methods[i3].invoke(wifiManager, Integer.valueOf(next.networkId), null);
                            return 1;
                        }
                        ALog.d("WiFiUtils", "call connectNetwork.");
                        methods[i3].invoke(wifiManager, Integer.valueOf(next.networkId));
                        return 1;
                    }
                    ALog.d("WiFiUtils", "call enableNetwork.");
                    wifiManager.enableNetwork(next.networkId, true);
                    return 1;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    ALog.w("WiFiUtils", "connect network exception=" + e2);
                    return 1;
                }
            }
            ALog.d("WiFiUtils", "create new wifi configuration.");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            updateWiFiConfiguration(wifiConfiguration, str, str2, 0, str4);
            int iAddNetwork = wifiManager.addNetwork(wifiConfiguration);
            try {
                Class<?> cls2 = Class.forName(WifiManager.class.getName());
                StringBuilder sb7 = new StringBuilder();
                sb7.append("WifiManager:");
                sb7.append(cls2.getName());
                ALog.i("WiFiUtils", sb7.toString());
                Method[] methods2 = cls2.getMethods();
                int i4 = 0;
                while (true) {
                    if (i4 >= methods2.length) {
                        z2 = false;
                        break;
                    }
                    if (methods2[i4].getName().equalsIgnoreCase(BaseMonitor.ALARM_POINT_CONNECT) || methods2[i4].getName().equalsIgnoreCase("connectNetwork")) {
                        if (methods2[i4].getParameterTypes()[0].getName().contains("int")) {
                            z2 = true;
                            break;
                        }
                    }
                    i4++;
                }
                ALog.i("WiFiUtils", "hasConnectWithIntParam:" + z2 + ",networkId=" + iAddNetwork);
                if (z2) {
                    methods2[i4].setAccessible(true);
                    ALog.d("WiFiUtils", "methodName=" + methods2[i4].getName());
                    if (methods2[i4].getName().equalsIgnoreCase(BaseMonitor.ALARM_POINT_CONNECT)) {
                        if (methods2[i4].getParameterTypes().length > 2) {
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append("call connect with three parameters. id=");
                            sb8.append(iAddNetwork);
                            ALog.d("WiFiUtils", sb8.toString());
                            methods2[i4].invoke(wifiManager, null, Integer.valueOf(iAddNetwork), null);
                            return 1;
                        }
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("call connect with two parameters. id=");
                        sb9.append(iAddNetwork);
                        ALog.d("WiFiUtils", sb9.toString());
                        methods2[i4].invoke(wifiManager, Integer.valueOf(iAddNetwork), null);
                        return 1;
                    }
                    ALog.d("WiFiUtils", "call connectNetwork.");
                    methods2[i4].invoke(wifiManager, wifiConfiguration);
                    return 1;
                }
                ALog.d("WiFiUtils", "call enableNetwork.");
                wifiManager.enableNetwork(iAddNetwork, true);
                return 1;
            } catch (Exception e3) {
                e3.printStackTrace();
                ALog.w("WiFiUtils", "connect network exception=" + e3);
                return 1;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return 1;
        }
        e4.printStackTrace();
        return 1;
    }
}

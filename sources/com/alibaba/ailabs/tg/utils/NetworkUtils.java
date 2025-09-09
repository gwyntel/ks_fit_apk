package com.alibaba.ailabs.tg.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import java.util.List;

/* loaded from: classes2.dex */
public class NetworkUtils {
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_IWLAN = 18;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;

    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static NetworkInfo getActiveNetworkInfo(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE"})
    public static String getCurrentWifiSsid(Context context, boolean z2) {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        if (context == null || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null) {
            return null;
        }
        if ((!isWifiAvailable(context) && z2) || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return null;
        }
        String ssid = connectionInfo.getSSID();
        return ssid != null ? ssid.replaceAll("\"", "") : ssid;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static NetworkType getNetworkType(@NonNull Context context) {
        NetworkType networkType;
        NetworkType networkType2 = NetworkType.NETWORK_NO;
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return networkType2;
        }
        if (activeNetworkInfo.getType() == 1) {
            return NetworkType.NETWORK_WIFI;
        }
        if (activeNetworkInfo.getType() != 0) {
            return NetworkType.NETWORK_UNKNOWN;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                networkType = NetworkType.NETWORK_2G;
                break;
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
                networkType = NetworkType.NETWORK_3G;
                break;
            case 13:
            case 18:
                networkType = NetworkType.NETWORK_4G;
                break;
            default:
                String subtypeName = activeNetworkInfo.getSubtypeName();
                if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                    networkType = NetworkType.NETWORK_UNKNOWN;
                    break;
                } else {
                    networkType = NetworkType.NETWORK_3G;
                    break;
                }
        }
        return networkType;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getWifiFrequency(Context context, String str) {
        String str2;
        List<ScanResult> wifiScanResult = getWifiScanResult(context);
        if (wifiScanResult != null) {
            int size = wifiScanResult.size();
            for (int i2 = 0; i2 < size; i2++) {
                ScanResult scanResult = wifiScanResult.get(i2);
                if (scanResult == null || (str2 = scanResult.SSID) == null) {
                    break;
                }
                if (StringUtils.equals(str2, str)) {
                    if (is5GHz(scanResult.frequency)) {
                        return "5GHz";
                    }
                    if (is24GHz(scanResult.frequency)) {
                        return "24GHz";
                    }
                }
            }
        }
        return "unknown";
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static List<ScanResult> getWifiScanResult(Context context) {
        WifiManager wifiManager;
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) != null && wifiManager.isWifiEnabled()) {
            return wifiManager.getScanResults();
        }
        return null;
    }

    private static boolean is24GHz(int i2) {
        return i2 > 2400 && i2 < 2500;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean is4G(@NonNull Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getSubtype() == 13;
    }

    public static boolean is5GHz(int i2) {
        return i2 > 4900 && i2 < 5900;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isMobileData(@NonNull Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType() == 0;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = getActiveNetworkInfo(context)) == null) {
            return false;
        }
        return activeNetworkInfo.isAvailable();
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo networkInfo;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (networkInfo = connectivityManager.getNetworkInfo(1)) == null) {
            return false;
        }
        return networkInfo.isAvailable() || networkInfo.isConnected();
    }

    public static boolean isWifiEnable(Context context) {
        WifiManager wifiManager;
        return (context == null || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null || !wifiManager.isWifiEnabled()) ? false : true;
    }

    public static void openWirelessSettings(@NonNull Context context) {
        context.startActivity(new Intent("android.settings.WIRELESS_SETTINGS").setFlags(268435456));
    }
}

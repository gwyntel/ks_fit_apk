package com.alibaba.sdk.android.openaccount.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.alibaba.sdk.android.openaccount.network.ConnectType;
import com.alibaba.sdk.android.openaccount.network.MobileNetworkType;

/* loaded from: classes2.dex */
public class NetworkUtils {
    public static ConnectType getConnectType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return ConnectType.CONNECT_TYPE_DISCONNECT;
        }
        int type = activeNetworkInfo.getType();
        return type != 0 ? type != 1 ? ConnectType.CONNECT_TYPE_OTHER : ConnectType.CONNECT_TYPE_WIFI : ConnectType.CONNECT_TYPE_MOBILE;
    }

    public static String getLocalMacAddress(Context context) {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return null;
        }
        return connectionInfo.getMacAddress();
    }

    public static MobileNetworkType getMobileNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.getType() == 0 && activeNetworkInfo.isConnected()) ? getNetWorkType(activeNetworkInfo.getSubtype()) : MobileNetworkType.MOBILE_NETWORK_TYPE_UNKNOWN;
    }

    private static MobileNetworkType getNetWorkType(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return MobileNetworkType.MOBILE_NETWORK_TYPE_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return MobileNetworkType.MOBILE_NETWORK_TYPE_3G;
            case 13:
                return MobileNetworkType.MOBILE_NETWORK_TYPE_4G;
            default:
                return MobileNetworkType.MOBILE_NETWORK_TYPE_UNKNOWN;
        }
    }

    public static boolean isNetworkAvaiable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }
}

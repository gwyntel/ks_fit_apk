package com.alibaba.ailabs.tg.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class DeviceUtils {
    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"HardwareIds"})
    @Deprecated
    public static String getDeviceImei(Context context) {
        TelephonyManager telephonyManager;
        if (context == null || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    private static InetAddress getInetAddress() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement.isUp()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && inetAddressNextElement.getHostAddress().indexOf(58) < 0) {
                            return inetAddressNextElement;
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET"})
    public static String getMacAddress(Context context) throws SocketException {
        String macAddressByWifiInfo = getMacAddressByWifiInfo(context);
        if (!"02:00:00:00:00:00".equals(macAddressByWifiInfo)) {
            return macAddressByWifiInfo;
        }
        String macAddressByNetworkInterface = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddressByNetworkInterface)) {
            return macAddressByNetworkInterface;
        }
        String macAddressByInetAddress = getMacAddressByInetAddress();
        return !"02:00:00:00:00:00".equals(macAddressByInetAddress) ? macAddressByInetAddress : "";
    }

    private static String getMacAddressByInetAddress() {
        NetworkInterface byInetAddress;
        byte[] hardwareAddress;
        try {
            InetAddress inetAddress = getInetAddress();
            if (inetAddress == null || (byInetAddress = NetworkInterface.getByInetAddress(inetAddress)) == null || (hardwareAddress = byInetAddress.getHardwareAddress()) == null || hardwareAddress.length <= 0) {
                return "02:00:00:00:00:00";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b2 : hardwareAddress) {
                sb.append(String.format("%02x:", Byte.valueOf(b2)));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String getMacAddressByNetworkInterface() throws SocketException {
        byte[] hardwareAddress;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (networkInterfaceNextElement != null && networkInterfaceNextElement.getName().equalsIgnoreCase("wlan0") && (hardwareAddress = networkInterfaceNextElement.getHardwareAddress()) != null && hardwareAddress.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : hardwareAddress) {
                        sb.append(String.format("%02x:", Byte.valueOf(b2)));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
            return "02:00:00:00:00:00";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static String getMacAddressByWifiInfo(@NonNull Context context) {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            return (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) ? "02:00:00:00:00:00" : connectionInfo.getMacAddress();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "";
    }

    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }
}

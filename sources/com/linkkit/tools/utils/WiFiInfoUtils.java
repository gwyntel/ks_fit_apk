package com.linkkit.tools.utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.linkkit.tools.a;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* loaded from: classes4.dex */
public class WiFiInfoUtils {
    private static final String TAG = "WiFiInfoUtils";

    private static String bytesToString(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            sb.append(String.format("%02X:", Byte.valueOf(b2)));
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static String callCmd(String str, String str2) throws IOException {
        String str3 = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(str).getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null || line.contains(str2)) {
                    return line;
                }
                str3 = str3 + line;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return str3;
        }
    }

    public static String getGatewayIp(Context context) {
        DhcpInfo dhcpInfo = ((WifiManager) context.getSystemService("wifi")).getDhcpInfo();
        if (dhcpInfo == null) {
            a.b(TAG, "getGatewayIp failed, dhcp info is empty.");
            return null;
        }
        String strIntIp2String = StringUtils.intIp2String(dhcpInfo.gateway);
        a.a(TAG, "getGatewayIp ip = " + strIntIp2String);
        return strIntIp2String;
    }

    private static InetAddress getLocalInetAddress() throws SocketException {
        InetAddress inetAddress;
        SocketException e2;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            inetAddress = null;
            while (networkInterfaces.hasMoreElements()) {
                try {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (true) {
                        if (!inetAddresses.hasMoreElements()) {
                            break;
                        }
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        try {
                            if (!inetAddressNextElement.isLoopbackAddress() && inetAddressNextElement.getHostAddress().indexOf(":") == -1) {
                                inetAddress = inetAddressNextElement;
                                break;
                            }
                            inetAddress = null;
                        } catch (SocketException e3) {
                            e2 = e3;
                            inetAddress = inetAddressNextElement;
                            e2.printStackTrace();
                            return inetAddress;
                        }
                    }
                    if (inetAddress != null) {
                        break;
                    }
                } catch (SocketException e4) {
                    e2 = e4;
                }
            }
        } catch (SocketException e5) {
            inetAddress = null;
            e2 = e5;
        }
        return inetAddress;
    }

    private static String getLocalIpAddress() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress()) {
                        return inetAddressNextElement.getHostAddress().toString();
                    }
                }
            }
            return null;
        } catch (SocketException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String getLocalMacAddressFromBusybox() throws IOException {
        String strCallCmd = callCmd("busybox ifconfig", "HWaddr");
        return strCallCmd == null ? "网络异常" : (strCallCmd.length() <= 0 || !strCallCmd.contains("HWaddr")) ? strCallCmd : strCallCmd.substring(strCallCmd.indexOf("HWaddr") + 6, strCallCmd.length() - 1);
    }

    private static String getLocalMacAddressFromWifiInfo(Context context) {
        return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public static String getMac(Context context) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 24) {
            return getMacAddress(context);
        }
        if (i2 >= 24) {
            return !TextUtils.isEmpty(getMacAddress()) ? getMacAddress() : !TextUtils.isEmpty(getMachineHardwareAddress()) ? getMachineHardwareAddress() : getLocalMacAddressFromBusybox();
        }
        return null;
    }

    private static String getMacAddress(Context context) throws IOException {
        String strTrim;
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address").getInputStream()));
            String line = "";
            while (line != null) {
                line = lineNumberReader.readLine();
                if (line != null) {
                    strTrim = line.trim();
                    break;
                }
            }
        } catch (Exception e2) {
            a.c(TAG, "getMacAddress:" + e2.toString());
        }
        strTrim = "";
        if (strTrim == null || "".equals(strTrim)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception e3) {
                e3.printStackTrace();
                a.c(TAG, "getMacAddress:" + e3.toString());
            }
        }
        return strTrim;
    }

    private static String getMacAddress0(Context context) {
        if (!isAccessWifiStateAuthorized(context)) {
            return "";
        }
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Exception e2) {
            a.c(TAG, "getMacAddress0:" + e2.toString());
            return "";
        }
    }

    private static String getMachineHardwareAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces;
        String strBytesToString = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e2) {
            e2.printStackTrace();
            networkInterfaces = null;
        }
        if (networkInterfaces == null) {
            return null;
        }
        while (networkInterfaces.hasMoreElements()) {
            try {
                strBytesToString = bytesToString(networkInterfaces.nextElement().getHardwareAddress());
            } catch (SocketException e3) {
                e3.printStackTrace();
            }
            if (strBytesToString != null) {
                break;
            }
        }
        return strBytesToString;
    }

    public static String getWifiIP(Context context) {
        a.a(TAG, "getWifiIP context = " + context);
        if (context == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null || !wifiManager.isWifiEnabled()) {
                return null;
            }
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
            return (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
        } catch (Exception e2) {
            a.b(TAG, "getWifiIP exception=" + e2);
            return null;
        }
    }

    private static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return null;
        }
        return wifiManager.getConnectionInfo();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getWifiSsid(android.content.Context r6) {
        /*
            java.lang.String r0 = "UTF-8"
            java.lang.String r1 = "\""
            android.net.wifi.WifiInfo r2 = getWifiInfo(r6)
            java.lang.String r3 = ""
            if (r2 != 0) goto Ld
            return r3
        Ld:
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L38
            java.lang.String r2 = r2.getSSID()     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L38
            java.lang.String r2 = r2.replace(r1, r3)     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L38
            byte[] r2 = r2.getBytes()     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L38
            r4.<init>(r2, r0)     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L38
            java.lang.String r2 = "<unknown ssid>"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Exception -> L2f java.io.UnsupportedEncodingException -> L31
            if (r2 != 0) goto L33
            java.lang.String r2 = "0x"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Exception -> L2f java.io.UnsupportedEncodingException -> L31
            if (r2 == 0) goto L42
            goto L33
        L2f:
            r2 = move-exception
            goto L3b
        L31:
            r2 = move-exception
            goto L3f
        L33:
            r4 = r3
            goto L42
        L35:
            r2 = move-exception
            r4 = r3
            goto L3b
        L38:
            r2 = move-exception
            r4 = r3
            goto L3f
        L3b:
            r2.printStackTrace()
            goto L42
        L3f:
            r2.printStackTrace()
        L42:
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            java.lang.String r5 = "WiFiInfoUtils"
            if (r2 == 0) goto L7f
            java.lang.String r2 = "getWifiSsid(),try CONNECTIVITY_SERVICE"
            com.linkkit.tools.a.a(r5, r2)
            java.lang.String r2 = "connectivity"
            java.lang.Object r6 = r6.getSystemService(r2)
            android.net.ConnectivityManager r6 = (android.net.ConnectivityManager) r6
            if (r6 == 0) goto L5f
            r2 = 1
            android.net.NetworkInfo r6 = r6.getNetworkInfo(r2)
            goto L60
        L5f:
            r6 = 0
        L60:
            if (r6 == 0) goto L7f
            java.lang.String r2 = r6.getExtraInfo()
            if (r2 == 0) goto L7f
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L7b
            java.lang.String r6 = r6.getExtraInfo()     // Catch: java.lang.Exception -> L7b
            java.lang.String r6 = r6.replace(r1, r3)     // Catch: java.lang.Exception -> L7b
            byte[] r6 = r6.getBytes()     // Catch: java.lang.Exception -> L7b
            r2.<init>(r6, r0)     // Catch: java.lang.Exception -> L7b
            r4 = r2
            goto L7f
        L7b:
            r6 = move-exception
            r6.printStackTrace()
        L7f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "getWifiSsid(), result ssid = "
            r6.append(r0)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            com.linkkit.tools.a.a(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linkkit.tools.utils.WiFiInfoUtils.getWifiSsid(android.content.Context):java.lang.String");
    }

    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") != 0) {
            return false;
        }
        a.a(TAG, "isAccessWifiStateAuthorized:access wifi state is enabled");
        return true;
    }

    private static String loadFileAsString(String str) throws Exception {
        FileReader fileReader = new FileReader(str);
        String strLoadReaderAsString = loadReaderAsString(fileReader);
        fileReader.close();
        return strLoadReaderAsString;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder sb = new StringBuilder();
        char[] cArr = new char[4096];
        int i2 = reader.read(cArr);
        while (i2 >= 0) {
            sb.append(cArr, 0, i2);
            i2 = reader.read(cArr);
        }
        return sb.toString();
    }

    private static String getMacAddress() throws SocketException {
        try {
            byte[] hardwareAddress = NetworkInterface.getByInetAddress(getLocalInetAddress()).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i2 = 0; i2 < hardwareAddress.length; i2++) {
                if (i2 != 0) {
                    stringBuffer.append(':');
                }
                String hexString = Integer.toHexString(hardwareAddress[i2] & 255);
                if (hexString.length() == 1) {
                    hexString = 0 + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString().toUpperCase();
        } catch (Exception unused) {
            return null;
        }
    }
}

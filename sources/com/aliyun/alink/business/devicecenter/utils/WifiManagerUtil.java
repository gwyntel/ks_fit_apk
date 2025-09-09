package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.aliyun.alink.business.devicecenter.base.WiFiFreqType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.kingsmith.miot.KsProperty;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class WifiManagerUtil {
    public static int NO_PASSWORD_WIFI = 0;
    public static int WEP_CIPHER_WIFI = 1;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_FAILED = 14;
    public static int WPA_CIPHER_WIFI = 2;

    /* renamed from: a, reason: collision with root package name */
    public Context f10668a;

    /* renamed from: b, reason: collision with root package name */
    public WifiManager.WifiLock f10669b;

    /* renamed from: c, reason: collision with root package name */
    public WifiManager.MulticastLock f10670c;

    /* renamed from: d, reason: collision with root package name */
    public WifiInfo f10671d;

    /* renamed from: e, reason: collision with root package name */
    public ConnectivityManager f10672e;

    /* renamed from: f, reason: collision with root package name */
    public List<WifiConfiguration> f10673f = new LinkedList();
    public WifiManager wifiManager;

    public enum NetworkType {
        WLAN,
        ETHERNET
    }

    public WifiManagerUtil(Context context) {
        this.f10668a = context;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        this.wifiManager = wifiManager;
        this.f10671d = wifiManager.getConnectionInfo();
        this.f10669b = this.wifiManager.createWifiLock("Test");
        this.f10670c = this.wifiManager.createMulticastLock("Alink");
        this.f10672e = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public static InetAddress getBroadcast(InetAddress inetAddress) throws SocketException {
        if (inetAddress == null) {
            return null;
        }
        ALog.d("WifiManagerUtil", "getBroadcast(),inetAddr = " + inetAddress);
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
            if (byInetAddress == null) {
                return null;
            }
            InetAddress broadcast = null;
            for (InterfaceAddress interfaceAddress : byInetAddress.getInterfaceAddresses()) {
                if (interfaceAddress.getAddress() instanceof Inet4Address) {
                    broadcast = interfaceAddress.getBroadcast();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("iAddr=");
            sb.append(broadcast);
            ALog.d("WifiManagerUtil", sb.toString());
            return broadcast;
        } catch (SocketException e2) {
            e2.printStackTrace();
            ALog.w("WifiManagerUtil", "getBroadcast" + e2.getMessage());
            return null;
        } catch (Exception e3) {
            ALog.w("WifiManagerUtil", "getBroadcast" + e3.getMessage());
            return null;
        }
    }

    public static InetAddress getIpAddress(NetworkType networkType) throws SocketException {
        ALog.d("WifiManagerUtil", "getIpAddress()");
        InetAddress inetAddress = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        if (networkInterfaceNextElement.getDisplayName().contains("wlan0") && networkType == NetworkType.WLAN) {
                            return inetAddressNextElement;
                        }
                        if (networkInterfaceNextElement.getDisplayName().contains("eth0") && networkType == NetworkType.ETHERNET) {
                            return inetAddressNextElement;
                        }
                        if (networkInterfaceNextElement.getDisplayName().contains("wlan0") || networkInterfaceNextElement.getDisplayName().contains("eth0") || networkInterfaceNextElement.getDisplayName().contains("ap0")) {
                            inetAddress = inetAddressNextElement;
                        }
                    }
                }
            }
        } catch (SocketException e2) {
            ALog.d("WifiManagerUtil", e2.toString());
        }
        return inetAddress;
    }

    public static String getWifiIP(Context context) {
        ALog.d("WifiManagerUtil", "getWifiIP ");
        if (context == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (!wifiManager.isWifiEnabled()) {
                return null;
            }
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
            return (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
        } catch (Exception e2) {
            ALog.w("WifiManagerUtil", "getWifiIP exception=" + e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a() throws java.lang.NoSuchFieldException, java.lang.SecurityException {
        /*
            r4 = this;
            r0 = 0
            android.net.ConnectivityManager r1 = r4.f10672e     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.Class r1 = r1.getClass()     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.String r2 = "mContext"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            r2 = 1
            r1.setAccessible(r2)     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            android.net.ConnectivityManager r2 = r4.f10672e     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            android.content.Context r1 = (android.content.Context) r1     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.Class r2 = r1.getClass()     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.String r3 = "getOpPackageName"
            java.lang.reflect.Method r2 = r2.getMethod(r3, r0)     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.Object r1 = r2.invoke(r1, r0)     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Exception -> L2a java.lang.reflect.InvocationTargetException -> L2c java.lang.NoSuchMethodException -> L2e java.lang.IllegalAccessException -> L30 java.lang.NoSuchFieldException -> L32
            return r1
        L2a:
            r1 = move-exception
            goto L34
        L2c:
            r1 = move-exception
            goto L38
        L2e:
            r1 = move-exception
            goto L3c
        L30:
            r1 = move-exception
            goto L40
        L32:
            r1 = move-exception
            goto L44
        L34:
            r1.printStackTrace()
            goto L47
        L38:
            r1.printStackTrace()
            goto L47
        L3c:
            r1.printStackTrace()
            goto L47
        L40:
            r1.printStackTrace()
            goto L47
        L44:
            r1.printStackTrace()
        L47:
            android.content.Context r1 = r4.f10668a
            if (r1 == 0) goto L4f
            java.lang.String r0 = r1.getPackageName()
        L4f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil.a():java.lang.String");
    }

    public void acquireMulticastLock() {
        try {
            this.f10670c.acquire();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void acquireWifiLock() {
        this.f10669b.acquire();
    }

    public int closeWiFiAP() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (this.wifiManager == null) {
            ALog.d("WifiManagerUtil", "closeWiFiAP(), wifi manager == null");
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Build.VERSION.SDK_INT=");
        int i2 = Build.VERSION.SDK_INT;
        sb.append(i2);
        ALog.d("WifiManagerUtil", sb.toString());
        if (i2 < 25) {
            if (!isWifiApEnabled()) {
                return 0;
            }
            try {
                Method method = this.wifiManager.getClass().getMethod("getWifiApConfiguration", null);
                method.setAccessible(true);
                this.wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE).invoke(this.wifiManager, (WifiConfiguration) method.invoke(this.wifiManager, null), Boolean.FALSE);
                return 1;
            } catch (Exception e2) {
                ALog.d("WifiManagerUtil", "closeWiFiAP(), error,e= " + e2.toString());
                return -1;
            }
        }
        try {
            Field declaredField = this.f10672e.getClass().getDeclaredField("mService");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this.f10672e);
            Class<?> cls = Class.forName(obj.getClass().getName());
            if (i2 >= 27) {
                cls.getMethod("stopTethering", Integer.TYPE, String.class).invoke(obj, 0, a());
            } else {
                cls.getMethod("stopTethering", Integer.TYPE).invoke(obj, 0);
            }
            return 1;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            ALog.e("WifiManagerUtil", "closeWiFiAP failed " + e3);
            return -1;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            ALog.e("WifiManagerUtil", "closeWiFiAP failed " + e4);
            return -1;
        } catch (NoSuchFieldException e5) {
            e5.printStackTrace();
            ALog.e("WifiManagerUtil", "closeWiFiAP failed " + e5);
            return -1;
        } catch (NoSuchMethodException e6) {
            e6.printStackTrace();
            return -1;
        } catch (InvocationTargetException e7) {
            e7.printStackTrace();
            ALog.e("WifiManagerUtil", "closeWiFiAP failed " + e7);
            return -1;
        } catch (Exception e8) {
            e8.printStackTrace();
            ALog.e("WifiManagerUtil", "closeWiFiAP failed " + e8);
            return -1;
        }
    }

    public WifiConfiguration createWifiConfiguration(String str, String str2, int i2, boolean z2) {
        ALog.d("WifiManagerUtil", "createWifiConfiguration(),SSID = " + str + "## passwd = " + str2 + "## Type = " + i2 + ",isHotSpot=" + z2);
        WifiConfiguration wifiConfigurationIsOpenWifiExist = i2 == NO_PASSWORD_WIFI ? isOpenWifiExist(str) : isWifiExist(str);
        if (wifiConfigurationIsOpenWifiExist != null) {
            removeWifi(wifiConfigurationIsOpenWifiExist.networkId);
        }
        if (wifiConfigurationIsOpenWifiExist == null) {
            wifiConfigurationIsOpenWifiExist = new WifiConfiguration();
        }
        wifiConfigurationIsOpenWifiExist.allowedAuthAlgorithms.clear();
        wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.clear();
        wifiConfigurationIsOpenWifiExist.allowedKeyManagement.clear();
        wifiConfigurationIsOpenWifiExist.allowedPairwiseCiphers.clear();
        wifiConfigurationIsOpenWifiExist.allowedProtocols.clear();
        wifiConfigurationIsOpenWifiExist.SSID = str;
        if (i2 == NO_PASSWORD_WIFI) {
            wifiConfigurationIsOpenWifiExist.allowedKeyManagement.set(0);
        } else if (i2 == WEP_CIPHER_WIFI) {
            wifiConfigurationIsOpenWifiExist.wepKeys[0] = str2;
            wifiConfigurationIsOpenWifiExist.allowedAuthAlgorithms.set(1);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(3);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(2);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(0);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(1);
            wifiConfigurationIsOpenWifiExist.allowedKeyManagement.set(0);
            wifiConfigurationIsOpenWifiExist.wepTxKeyIndex = 0;
        } else if (i2 == WPA_CIPHER_WIFI) {
            wifiConfigurationIsOpenWifiExist.preSharedKey = str2;
            wifiConfigurationIsOpenWifiExist.hiddenSSID = false;
            wifiConfigurationIsOpenWifiExist.allowedAuthAlgorithms.set(0);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(2);
            wifiConfigurationIsOpenWifiExist.allowedKeyManagement.set(1);
            wifiConfigurationIsOpenWifiExist.allowedPairwiseCiphers.set(1);
            wifiConfigurationIsOpenWifiExist.allowedGroupCiphers.set(3);
            wifiConfigurationIsOpenWifiExist.allowedPairwiseCiphers.set(2);
            wifiConfigurationIsOpenWifiExist.status = 2;
        }
        return wifiConfigurationIsOpenWifiExist;
    }

    public void enableWifiBySsid(String str) {
        String str2;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("enableWifiBySsid ssid=");
            sb.append(str);
            ALog.d("WifiManagerUtil", sb.toString());
            if (this.f10673f == null) {
                return;
            }
            for (int i2 = 0; i2 < this.f10673f.size(); i2++) {
                WifiConfiguration wifiConfiguration = this.f10673f.get(i2);
                if (wifiConfiguration != null && (str2 = wifiConfiguration.SSID) != null) {
                    if (str2.equals("\"" + str + "\"")) {
                        this.wifiManager.disconnect();
                        this.wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                        this.wifiManager.reconnect();
                    }
                }
            }
        } catch (Exception e2) {
            ALog.w("WifiManagerUtil", "enableWifiBySsid e=" + e2);
        }
    }

    public int getWifiApState() {
        try {
            return ((Integer) this.wifiManager.getClass().getMethod("getWifiApState", null).invoke(this.wifiManager, null)).intValue();
        } catch (Exception e2) {
            ALog.d("WifiManagerUtil", "getWifiApState(), error = " + e2.toString());
            return 14;
        }
    }

    public int getWifiRssid() {
        WifiInfo wifiInfo = this.f10671d;
        if (wifiInfo == null) {
            return -127;
        }
        return wifiInfo.getRssi();
    }

    public String getWifiType() {
        WifiInfo wifiInfo = this.f10671d;
        if (wifiInfo == null) {
            return WiFiFreqType.WIFI_UNKNOWN.value();
        }
        int frequency = wifiInfo.getFrequency();
        return is5GHz(frequency) ? WiFiFreqType.WIFI_5G.value() : is24GHz(frequency) ? WiFiFreqType.WIFI_2_4G.value() : WiFiFreqType.WIFI_UNKNOWN.value();
    }

    public boolean is24GHz(int i2) {
        return i2 > 2400 && i2 < 2500;
    }

    public boolean is5GHz(int i2) {
        return i2 > 4900 && i2 < 5900;
    }

    public boolean isHTC() {
        try {
            String str = Build.MANUFACTURER;
            StringBuilder sb = new StringBuilder();
            sb.append("isHTC(), manu=");
            sb.append(str);
            ALog.d("WifiManagerUtil", sb.toString());
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return str.trim().toLowerCase().contains("htc");
        } catch (Exception e2) {
            ALog.d("WifiManagerUtil", "isHTC(),error+" + e2);
            return false;
        }
    }

    public WifiConfiguration isOpenWifiExist(String str) {
        ALog.d("WifiManagerUtil", "isOpenWifiExist,ssid=" + str);
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.f10673f.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.f10673f.get(i2);
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID)) {
                if (wifiConfiguration.SSID.equals("\"" + str + "\"") && wifiConfiguration.allowedKeyManagement.get(0)) {
                    ALog.d("WifiManagerUtil", "isOpenWifiExist(),found config");
                    return this.f10673f.get(i2);
                }
            }
        }
        return null;
    }

    public boolean isWifiApEnabled() {
        int wifiApState = getWifiApState();
        ALog.d("WifiManagerUtil", "wifiApState=" + wifiApState);
        return wifiApState == 13;
    }

    public Boolean isWifiAvaiable() {
        ALog.d("WifiManagerUtil", "isWifiAvaiable");
        WifiManager wifiManager = this.wifiManager;
        if (wifiManager == null) {
            ALog.d("WifiManagerUtil", "isWifiAvaiable,wifiManager is null");
            return Boolean.FALSE;
        }
        boolean zIsWifiEnabled = wifiManager.isWifiEnabled();
        ALog.d("WifiManagerUtil", "isWifiAvaiable,enable = " + zIsWifiEnabled);
        return Boolean.valueOf(zIsWifiEnabled);
    }

    public WifiConfiguration isWifiExist(String str) {
        ALog.d("WifiManagerUtil", "isWifiExist");
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.f10673f.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.f10673f.get(i2);
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID)) {
                if (wifiConfiguration.SSID.equals("\"" + str + "\"")) {
                    ALog.d("WifiManagerUtil", "isWifiExist(),found config");
                    return wifiConfiguration;
                }
            }
        }
        return null;
    }

    public void openWifi() {
        if (isWifiAvaiable().booleanValue()) {
            return;
        }
        this.wifiManager.setWifiEnabled(true);
    }

    public void releaseMulticastLock() {
        try {
            if (this.f10670c.isHeld()) {
                this.f10670c.release();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void releaseWifiLock() {
        if (this.f10669b.isHeld()) {
            this.f10669b.acquire();
        }
    }

    public void removeWifi(int i2) {
        this.wifiManager.removeNetwork(i2);
    }

    public boolean setWifiApConfigurationForHTC(WifiConfiguration wifiConfiguration) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        ALog.d("WifiManagerUtil", "setWifiApConfigurationForHTC, call, apConfig = " + wifiConfiguration.toString());
        try {
            Field declaredField = WifiConfiguration.class.getDeclaredField("mWifiApProfile");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(wifiConfiguration);
            declaredField.setAccessible(false);
            if (obj != null) {
                Field declaredField2 = obj.getClass().getDeclaredField("SSID");
                declaredField2.setAccessible(true);
                declaredField2.set(obj, wifiConfiguration.SSID);
                declaredField2.setAccessible(false);
                Field declaredField3 = obj.getClass().getDeclaredField(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID);
                declaredField3.setAccessible(true);
                declaredField3.set(obj, wifiConfiguration.BSSID);
                declaredField3.setAccessible(false);
                Field declaredField4 = obj.getClass().getDeclaredField("dhcpEnable");
                declaredField4.setAccessible(true);
                declaredField4.setInt(obj, 1);
                declaredField4.setAccessible(false);
                Field declaredField5 = obj.getClass().getDeclaredField(KsProperty.Key);
                declaredField5.setAccessible(true);
                declaredField5.set(obj, wifiConfiguration.preSharedKey);
                declaredField5.setAccessible(false);
            }
            return true;
        } catch (Exception e2) {
            ALog.d("WifiManagerUtil", "setWifiApConfigurationForHTC,error, e = " + e2);
            e2.printStackTrace();
            return false;
        }
    }

    public boolean setWifiApEnabled(WifiConfiguration wifiConfiguration, boolean z2) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (this.wifiManager == null) {
            ALog.d("WifiManagerUtil", "setWifiApEnabled(), wifiManager is null");
            return false;
        }
        if (isHTC()) {
            boolean wifiApConfigurationForHTC = setWifiApConfigurationForHTC(wifiConfiguration);
            ALog.d("WifiManagerUtil", "setWifiApEnabled(), success = " + wifiApConfigurationForHTC + ".");
            if (!wifiApConfigurationForHTC) {
                return false;
            }
        }
        if (z2) {
            try {
                boolean wifiEnabled = this.wifiManager.setWifiEnabled(false);
                ALog.d("WifiManagerUtil", "setWifiApEnabled(), success = " + wifiEnabled + ".");
                if (!wifiEnabled) {
                    return false;
                }
            } catch (Exception e2) {
                ALog.d("WifiManagerUtil", " setWifiApEnabled(), e = " + e2);
                e2.printStackTrace();
                return false;
            }
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) this.f10668a.getSystemService("connectivity");
        StringBuilder sb = new StringBuilder();
        sb.append("Build.VERSION.SDK_INT=");
        int i2 = Build.VERSION.SDK_INT;
        sb.append(i2);
        ALog.d("WifiManagerUtil", sb.toString());
        if (i2 < 25) {
            return ((Boolean) this.wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE).invoke(this.wifiManager, wifiConfiguration, Boolean.valueOf(z2))).booleanValue();
        }
        try {
            try {
                try {
                    try {
                        try {
                            Field declaredField = connectivityManager.getClass().getDeclaredField("mService");
                            declaredField.setAccessible(true);
                            Object obj = declaredField.get(connectivityManager);
                            Class<?> cls = Class.forName(obj.getClass().getName());
                            if (i2 >= 27) {
                                cls.getMethod("startTethering", Integer.TYPE, ResultReceiver.class, Boolean.TYPE, String.class).invoke(obj, 0, new ResultReceiver(new Handler()) { // from class: com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil.1
                                    @Override // android.os.ResultReceiver
                                    public void onReceiveResult(int i3, Bundle bundle) {
                                        super.onReceiveResult(i3, bundle);
                                        ALog.d("WifiManagerUtil", "onReceiveResult resultData=" + bundle);
                                    }
                                }, Boolean.TRUE, a());
                            } else {
                                cls.getMethod("startTethering", Integer.TYPE, ResultReceiver.class, Boolean.TYPE).invoke(obj, 0, new ResultReceiver(new Handler()) { // from class: com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil.2
                                    @Override // android.os.ResultReceiver
                                    public void onReceiveResult(int i3, Bundle bundle) {
                                        super.onReceiveResult(i3, bundle);
                                        ALog.d("WifiManagerUtil", "onReceiveResult resultData=" + bundle);
                                    }
                                }, Boolean.TRUE);
                            }
                            return true;
                        } catch (IllegalAccessException e3) {
                            e3.printStackTrace();
                            return false;
                        } catch (NoSuchMethodException e4) {
                            e4.printStackTrace();
                            return false;
                        }
                    } catch (NoSuchFieldException e5) {
                        e5.printStackTrace();
                        return false;
                    }
                } catch (ClassNotFoundException e6) {
                    e6.printStackTrace();
                    return false;
                }
            } catch (InvocationTargetException e7) {
                e7.printStackTrace();
                return false;
            }
        } catch (Exception e8) {
            e8.printStackTrace();
            return false;
        }
    }

    public void updateConfigedWifi() {
        ALog.d("WifiManagerUtil", "updateConfigedWifi()");
        try {
            if (this.wifiManager.getConfiguredNetworks() != null) {
                this.f10673f.clear();
                this.f10673f.addAll(this.wifiManager.getConfiguredNetworks());
            }
        } catch (Exception e2) {
            ALog.w("WifiManagerUtil", "updateConfigedWifi(),error," + e2);
            e2.printStackTrace();
        }
    }
}

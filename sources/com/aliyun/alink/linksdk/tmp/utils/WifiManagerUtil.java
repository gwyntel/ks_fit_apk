package com.aliyun.alink.linksdk.tmp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.aliyun.alink.linksdk.tools.ALog;
import com.kingsmith.miot.KsProperty;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class WifiManagerUtil {
    public static int NO_PASSWORD_WIFI = 0;
    private static final String TAG = "WifiManagerUtil";
    public static int WEP_CIPHER_WIFI = 1;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_FAILED = 14;
    public static int WPA_CIPHER_WIFI = 2;
    private ConnectivityManager connectivityManager;
    private Context context;
    private WifiInfo currWifiInfo;
    private WifiManager.MulticastLock multicastLock;
    private WifiManager.WifiLock wifiLock;
    public WifiManager wifiManager;
    public final String ALINK_SOFT_AP_GATEWAY = "172.31.254.250";
    public final String ALINK_SOFT_AP_STATIC_IP = "172.31.254.153";
    public final String ALINK_SOFT_AP_DNS = "192.192.192.192";
    private List<ScanResult> scanResultList = new LinkedList();
    private List<WifiConfiguration> wifiConfigedList = new LinkedList();

    public enum NetworkType {
        WLAN,
        ETHERNET
    }

    public enum WiFiFreqType {
        WIFI_5G("5GHZ"),
        WIFI_2_4G("2.4GHZ");

        private String name;

        WiFiFreqType(String str) {
            this.name = str;
        }

        public String value() {
            return this.name;
        }
    }

    public WifiManagerUtil(Context context) {
        this.context = context;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        this.wifiManager = wifiManager;
        this.currWifiInfo = wifiManager.getConnectionInfo();
        this.wifiLock = this.wifiManager.createWifiLock("Test");
        this.multicastLock = this.wifiManager.createMulticastLock("Alink");
        this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    }

    private void disconnectAllConfiguredWifi() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            ALog.w(TAG, "disconnectAllConfiguredWifi");
            for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
                if (this.wifiConfigedList.get(i2) != null) {
                    disconnectWifi(this.wifiConfigedList.get(i2).networkId);
                }
            }
        } catch (Exception e2) {
            ALog.w(TAG, "disconnectAllConfiguredWifi e=" + e2);
        }
    }

    public static InetAddress getBroadcast(InetAddress inetAddress) throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
        List<InterfaceAddress> interfaceAddresses;
        if (inetAddress == null) {
            return null;
        }
        ALog.d(TAG, "getBroadcast(),inetAddr = " + inetAddress);
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
            if (byInetAddress != null && (interfaceAddresses = byInetAddress.getInterfaceAddresses()) != null && !interfaceAddresses.isEmpty()) {
                InetAddress broadcast = null;
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    if (interfaceAddress.getAddress() instanceof Inet4Address) {
                        broadcast = interfaceAddress.getBroadcast();
                    }
                }
                ALog.d(TAG, "iAddr=" + broadcast);
                return broadcast;
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.d(TAG, "getBroadcast" + e2.getMessage());
            return null;
        }
    }

    private static Object getDeclaredField(Object obj, String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    private Object getField(Object obj, String str) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        return obj.getClass().getField(str).get(obj);
    }

    public static InetAddress getIpAddress(NetworkType networkType) throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getIpAddress()");
        InetAddress inetAddress = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
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
            }
        } catch (Exception e2) {
            ALog.d(TAG, e2.toString());
        }
        return inetAddress;
    }

    private static void setEnumField(Object obj, String str, String str2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field field = obj.getClass().getField(str2);
        field.set(obj, Enum.valueOf(field.getType(), str));
    }

    private void setStaticIp(WifiConfiguration wifiConfiguration) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InstantiationException, SecurityException, ClassNotFoundException, IllegalArgumentException, UnknownHostException, InvocationTargetException {
        setEnumField(wifiConfiguration, "STATIC", "ipAssignment");
        Object field = getField(wifiConfiguration, "linkProperties");
        if (field == null) {
            return;
        }
        Object objNewInstance = Class.forName("android.net.LinkAddress").getConstructor(InetAddress.class, Integer.TYPE).newInstance(InetAddress.getByName("172.31.254.153"), 24);
        ArrayList arrayList = (ArrayList) getDeclaredField(field, "mLinkAddresses");
        arrayList.clear();
        arrayList.add(objNewInstance);
        Object objNewInstance2 = Class.forName("android.net.RouteInfo").getConstructor(InetAddress.class).newInstance(InetAddress.getByName("172.31.254.250"));
        ArrayList arrayList2 = (ArrayList) getDeclaredField(field, "mRoutes");
        arrayList2.clear();
        arrayList2.add(objNewInstance2);
        ArrayList arrayList3 = (ArrayList) getDeclaredField(field, "mDnses");
        arrayList3.clear();
        arrayList3.add(InetAddress.getByName("192.192.192.192"));
    }

    public static void startScanWifiList(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startScanWifiList()");
        ALog.d(TAG, "startScanWifiList()," + ((WifiManager) context.getSystemService("wifi")).startScan());
    }

    public void acquireMulticastLock() {
        try {
            this.multicastLock.acquire();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void acquireWifiLock() {
        this.wifiLock.acquire();
    }

    public void addWifi(WifiConfiguration wifiConfiguration) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "addWifi()");
        if (wifiConfiguration == null) {
            ALog.d(TAG, "addwifi(),config is null");
            return;
        }
        int iAddNetwork = wifiConfiguration.networkId;
        if (iAddNetwork == -1) {
            ALog.d(TAG, "addWifi(), addNetwork..");
            iAddNetwork = this.wifiManager.addNetwork(wifiConfiguration);
        }
        ALog.d(TAG, "addWifi(),netId = " + iAddNetwork);
        if (iAddNetwork == -1) {
            return;
        }
        try {
            disconnectAllConfiguredWifi();
            ALog.d(TAG, "addWifi(),enable = " + this.wifiManager.enableNetwork(iAddNetwork, true) + " reconnect = " + this.wifiManager.reconnect());
        } catch (Exception e2) {
            ALog.e(TAG, "addWifi(),error,", e2);
        }
    }

    public void closeWiFiAP() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.wifiManager == null) {
            ALog.d(TAG, "closeWiFiAP(), wifi manager == null");
            return;
        }
        if (isWifiApEnabled()) {
            try {
                Method method = this.wifiManager.getClass().getMethod("getWifiApConfiguration", null);
                method.setAccessible(true);
                this.wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE).invoke(this.wifiManager, (WifiConfiguration) method.invoke(this.wifiManager, null), Boolean.FALSE);
                this.wifiManager.setWifiEnabled(true);
            } catch (Exception e2) {
                ALog.d(TAG, "closeWiFiAP(), error,e= " + e2.toString());
            }
        }
    }

    public void closeWifi() {
        if (isWifiAvaiable().booleanValue()) {
            this.wifiManager.setWifiEnabled(false);
        }
    }

    public WifiConfiguration createWifiConfiguration(String str, String str2, int i2, boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "createWifiConfiguration(),SSID = " + str + "## Password = " + str2 + "## Type = " + i2 + ",isHotSpot=" + z2);
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
        try {
            setStaticIp(wifiConfigurationIsOpenWifiExist);
        } catch (Exception e2) {
            ALog.d(TAG, "createWifiConfiguration(), setStaticIP error, e=" + e2);
        }
        return wifiConfigurationIsOpenWifiExist;
    }

    public void disconnectWifi(int i2) {
        this.wifiManager.disableNetwork(i2);
        this.wifiManager.disconnect();
    }

    public void enableWifiBySsid(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str2;
        try {
            ALog.w(TAG, "enableWifiBySsid ssid=" + str);
            if (this.wifiConfigedList == null) {
                return;
            }
            for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
                WifiConfiguration wifiConfiguration = this.wifiConfigedList.get(i2);
                if (wifiConfiguration != null && (str2 = wifiConfiguration.SSID) != null) {
                    if (str2.equals("\"" + str + "\"")) {
                        this.wifiManager.disconnect();
                        this.wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                        this.wifiManager.reconnect();
                    }
                }
            }
        } catch (Exception e2) {
            ALog.w(TAG, "enableWifiBySsid e=" + e2);
        }
    }

    public WifiConfiguration getCurWifiConfig(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getCurWifiConfig(),ssid=" + str);
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.wifiConfigedList.get(i2);
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID)) {
                if (wifiConfiguration.SSID.equals("\"" + str + "\"") && wifiConfiguration.status == 0) {
                    ALog.d(TAG, "getCurWifiConfig(),succ。  networkId=" + wifiConfiguration.networkId + ",ssid=" + wifiConfiguration.SSID + ",config=" + wifiConfiguration.toString());
                    return wifiConfiguration;
                }
            }
        }
        return null;
    }

    public WifiInfo getCurrWifiInfo() {
        return this.currWifiInfo;
    }

    public NetworkInfo getCurrentNetInfo() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getCurrentNetInfo(),call");
        if (this.connectivityManager == null) {
            return null;
        }
        ALog.d(TAG, "getCurrentNetInfo(),connectivityManager != null");
        return this.connectivityManager.getNetworkInfo(1);
    }

    public DhcpInfo getRouterDhcp() {
        return ((WifiManager) this.context.getSystemService("wifi")).getDhcpInfo();
    }

    public int getWifiApState() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            return ((Integer) this.wifiManager.getClass().getMethod("getWifiApState", null).invoke(this.wifiManager, null)).intValue();
        } catch (Exception e2) {
            ALog.d(TAG, "getWifiApState(), error = " + e2.toString());
            return 14;
        }
    }

    public List<WifiConfiguration> getWifiConfiged() {
        List<WifiConfiguration> configuredNetworks = this.wifiManager.getConfiguredNetworks();
        this.wifiConfigedList = configuredNetworks;
        return configuredNetworks;
    }

    public int getWifiRssid() {
        return this.currWifiInfo.getRssi();
    }

    public String getWifiType() {
        return is5GHz(this.currWifiInfo.getFrequency()) ? WiFiFreqType.WIFI_5G.value() : WiFiFreqType.WIFI_2_4G.value();
    }

    public boolean is24GHz(int i2) {
        return i2 > 2400 && i2 < 2500;
    }

    public boolean is5GHz(int i2) {
        return i2 > 4900 && i2 < 5900;
    }

    public Boolean isAPNetworkReady(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isAPNetworkReady(),ssid" + str);
        updateWifi();
        NetworkInfo currentNetInfo = getCurrentNetInfo();
        if (currentNetInfo == null) {
            ALog.d(TAG, "isAPNetworkReady(),false,info is empty");
            return Boolean.FALSE;
        }
        if (currentNetInfo.getType() != 1) {
            ALog.d(TAG, "isAPNetworkReady: false,is not wifi");
            return Boolean.FALSE;
        }
        ALog.d(TAG, "isAPNetworkReady: State = " + currentNetInfo.getState() + ", detailState=" + currentNetInfo.getDetailedState().toString());
        if (!currentNetInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
            ALog.d(TAG, "isAPNetworkReady(),false,state=" + currentNetInfo.getState());
            return Boolean.FALSE;
        }
        if (this.currWifiInfo.getSSID().replace("\"", "").equals(str)) {
            return isWifiAvaiable();
        }
        ALog.d(TAG, "isAPNetworkReady(),false,cur ssid=" + this.currWifiInfo.getSSID());
        return Boolean.FALSE;
    }

    public Boolean isCurrWifiOk() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        updateWifi();
        NetworkInfo currentNetInfo = getCurrentNetInfo();
        return (currentNetInfo != null && currentNetInfo.getType() == 1 && currentNetInfo.getState().equals(NetworkInfo.State.CONNECTED) && isWifiAvaiable().booleanValue()) ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean isHTC() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            String str = Build.MANUFACTURER;
            ALog.d(TAG, "isHTC(), manu=" + str);
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return str.trim().toLowerCase().contains("htc");
        } catch (Exception e2) {
            ALog.d(TAG, "isHTC(),error+" + e2);
            return false;
        }
    }

    public WifiConfiguration isOpenWifiExist(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isOpenWifiExist,ssid=" + str);
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.wifiConfigedList.get(i2);
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID)) {
                if (wifiConfiguration.SSID.equals("\"" + str + "\"") && wifiConfiguration.allowedKeyManagement.get(0)) {
                    ALog.d(TAG, "isOpenWifiExist(),found config");
                    return this.wifiConfigedList.get(i2);
                }
            }
        }
        return null;
    }

    public boolean isWifiApEnabled() {
        return getWifiApState() == 13;
    }

    public Boolean isWifiAvaiable() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isWifiAvaiable");
        WifiManager wifiManager = this.wifiManager;
        if (wifiManager == null) {
            ALog.d(TAG, "isWifiAvaiable,wifiManager is null");
            return Boolean.FALSE;
        }
        boolean zIsWifiEnabled = wifiManager.isWifiEnabled();
        ALog.d(TAG, "isWifiAvaiable,enable = " + zIsWifiEnabled);
        return Boolean.valueOf(zIsWifiEnabled);
    }

    public WifiConfiguration isWifiExist(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isWifiExist");
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.wifiConfigedList.get(i2);
            if (wifiConfiguration != null && !TextUtils.isEmpty(wifiConfiguration.SSID)) {
                if (wifiConfiguration.SSID.equals("\"" + str + "\"")) {
                    ALog.d(TAG, "isWifiExist(),found config");
                    return wifiConfiguration;
                }
            }
        }
        return null;
    }

    public void logWifiConfig() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "logWifiConfig()");
        updateConfigedWifi();
        for (int i2 = 0; i2 < this.wifiConfigedList.size(); i2++) {
            WifiConfiguration wifiConfiguration = this.wifiConfigedList.get(i2);
            ALog.d(TAG, "logWifiConfig(),networkId=" + wifiConfiguration.networkId + ",ssid=" + wifiConfiguration.SSID + ",config=" + wifiConfiguration.toString());
        }
    }

    public void openWifi() {
        if (isWifiAvaiable().booleanValue()) {
            this.wifiManager.setWifiEnabled(true);
        }
    }

    public void releaseMulticastLock() {
        try {
            if (this.multicastLock.isHeld()) {
                this.multicastLock.release();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void releaseWifiLock() {
        if (this.wifiLock.isHeld()) {
            this.wifiLock.acquire();
        }
    }

    public void removeWifi(int i2) {
        this.wifiManager.removeNetwork(i2);
    }

    public boolean setWifiApConfigurationForHTC(WifiConfiguration wifiConfiguration) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setWifiApConfigurationForHTC, call, apConfig = " + wifiConfiguration.toString());
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
            ALog.d(TAG, "setWifiApConfigurationForHTC,error, e = " + e2);
            e2.printStackTrace();
            return false;
        }
    }

    public boolean setWifiApEnabled(WifiConfiguration wifiConfiguration, boolean z2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.wifiManager == null) {
            ALog.d(TAG, "setWifiApEnabled(), wifiManager is null");
            return false;
        }
        if (isHTC()) {
            ALog.d(TAG, "setWifiApEnabled(), isSucc = " + setWifiApConfigurationForHTC(wifiConfiguration));
        }
        if (z2) {
            try {
                this.wifiManager.setWifiEnabled(false);
            } catch (Exception e2) {
                ALog.d(TAG, " setWifiApEnabled(), e = " + e2);
                e2.printStackTrace();
                return false;
            }
        }
        return ((Boolean) this.wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE).invoke(this.wifiManager, wifiConfiguration, Boolean.valueOf(z2))).booleanValue();
    }

    public List<ScanResult> startScanWifi() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startScanWifi()");
        ALog.d(TAG, "startScanWifi()," + this.wifiManager.startScan());
        List<ScanResult> scanResults = this.wifiManager.getScanResults();
        this.scanResultList = scanResults;
        return scanResults;
    }

    public void updateConfigedWifi() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateConfigedWifi()");
        try {
            if (this.wifiManager.getConfiguredNetworks() != null) {
                this.wifiConfigedList.clear();
                this.wifiConfigedList.addAll(this.wifiManager.getConfiguredNetworks());
            }
        } catch (Exception e2) {
            ALog.d(TAG, "updateConfigedWifi(),error," + e2);
            e2.printStackTrace();
        }
    }

    public void updateWifi() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateWifi()");
        this.currWifiInfo = this.wifiManager.getConnectionInfo();
    }
}

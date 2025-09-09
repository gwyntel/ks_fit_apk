package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.breeze.BreezeDeviceDescriptor;
import com.aliyun.iot.breeze.BreezeScanRecord;
import com.aliyun.iot.breeze.mix.MixBleDevice;
import com.kingsmith.miot.KsProperty;
import com.umeng.analytics.pro.f;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class NetworkEnvironmentUtils {
    public static Context a(Context context) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (context == null) {
            context = AppUtils.getContext();
        }
        if (context == null) {
            return null;
        }
        return context.getApplicationContext();
    }

    public static HashMap<String, String> getBleInfo(Context context, IBleInterface.IBleChannelDevice iBleChannelDevice) {
        BreezeDeviceDescriptor descriptor;
        if (iBleChannelDevice == null || !(iBleChannelDevice.getChannelDevice() instanceof MixBleDevice)) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            descriptor = ((MixBleDevice) iBleChannelDevice.getChannelDevice()).getDescriptor();
        } catch (Exception unused) {
        }
        if (descriptor == null) {
            return map;
        }
        map.put("rssi", String.valueOf(descriptor.getRssi()));
        BreezeScanRecord breezeScanRecord = descriptor.getBreezeScanRecord();
        if (breezeScanRecord != null) {
            map.put(AlinkConstants.KEY_MAC, breezeScanRecord.getMac());
            map.put("bleV", BreezeScanRecord.bleVersion2Str(breezeScanRecord.bleVersion()));
            map.put("st", String.valueOf(breezeScanRecord.getSubType()));
            map.put(f.T, String.valueOf(breezeScanRecord.getProtocolVersion()));
            map.put("mi", breezeScanRecord.getModelIdHexStr());
            map.put("fm", String.valueOf(breezeScanRecord.getFmsk()));
        }
        return map;
    }

    public static HashMap<String, String> getPhoneWiFiInfo(Context context) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        Context contextA = a(context);
        if (contextA == null || (wifiManager = (WifiManager) contextA.getSystemService("wifi")) == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            String ssid = connectionInfo.getSSID();
            if (!TextUtils.isEmpty(ssid)) {
                if (!ssid.startsWith("\"") || ssid.length() <= 2) {
                    map.put("ssid", connectionInfo.getSSID());
                } else {
                    map.put("ssid", ssid.substring(1, ssid.length() - 1));
                }
            }
            map.put("bssid", connectionInfo.getBSSID());
            map.put("rssi", String.valueOf(connectionInfo.getRssi()));
            map.put("freq", String.valueOf(connectionInfo.getFrequency()));
            map.put("channel", getWiFiChannel(connectionInfo.getFrequency()));
            map.put(KsProperty.Speed, String.valueOf(connectionInfo.getLinkSpeed()));
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, String.valueOf(NetworkTypeUtils.isMobileNetwork(context)));
            map.put("wifi", String.valueOf(NetworkTypeUtils.isWiFi(context)));
            HashMap<String, String> wiFiConfiguration = getWiFiConfiguration(context, ssid);
            if (wiFiConfiguration != null && !wiFiConfiguration.isEmpty()) {
                map.putAll(wiFiConfiguration);
            }
        } catch (Exception unused) {
        }
        return map;
    }

    public static String getWiFiChannel(int i2) {
        int i3;
        switch (i2) {
            case 2412:
                i3 = 1;
                break;
            case 2417:
                i3 = 2;
                break;
            case 2422:
                i3 = 3;
                break;
            case 2427:
                i3 = 4;
                break;
            case 2432:
                i3 = 5;
                break;
            case 2437:
                i3 = 6;
                break;
            case 2442:
                i3 = 7;
                break;
            case 2447:
                i3 = 8;
                break;
            case 2452:
                i3 = 9;
                break;
            case 2457:
                i3 = 10;
                break;
            case 2462:
                i3 = 11;
                break;
            case 2467:
                i3 = 12;
                break;
            case 2472:
                i3 = 13;
                break;
            case 2484:
                i3 = 14;
                break;
            case 5745:
                i3 = 149;
                break;
            case 5765:
                i3 = 153;
                break;
            case 5785:
                i3 = 157;
                break;
            case 5805:
                i3 = 161;
                break;
            case 5825:
                i3 = 165;
                break;
            default:
                i3 = -1;
                break;
        }
        return String.valueOf(i3);
    }

    public static HashMap<String, String> getWiFiConfiguration(Context context, String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        WifiManager wifiManager;
        List<WifiConfiguration> configuredNetworks;
        Context contextA = a(context);
        if (contextA == null || TextUtils.isEmpty(str) || (wifiManager = (WifiManager) contextA.getSystemService("wifi")) == null || (configuredNetworks = wifiManager.getConfiguredNetworks()) == null || configuredNetworks.isEmpty()) {
            return null;
        }
        int size = configuredNetworks.size();
        HashMap<String, String> map = new HashMap<>();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            WifiConfiguration wifiConfiguration = configuredNetworks.get(i2);
            if (wifiConfiguration != null && str.equals(wifiConfiguration.SSID)) {
                map.put("encType", String.valueOf(wifiConfiguration.allowedKeyManagement));
                break;
            }
            i2++;
        }
        return map;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SimplifyVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r7v17 int, still in use, count: 1, list:
          (r7v17 int) from 0x013b: INVOKE (r7v18 java.lang.String) = (r14v1 java.lang.String), (r15v1 int), (r7v17 int) VIRTUAL call: java.lang.String.substring(int, int):java.lang.String A[Catch: all -> 0x0150, IOException -> 0x0152, MD:(int, int):java.lang.String (c)] (LINE:23)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.utils.InsnRemover.unbindAllArgs(InsnRemover.java:106)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:90)
        	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:174)
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:141)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyArgs(SimplifyVisitor.java:116)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyInsn(SimplifyVisitor.java:132)
        	at jadx.core.dex.visitors.SimplifyVisitor.simplifyBlock(SimplifyVisitor.java:86)
        	at jadx.core.dex.visitors.SimplifyVisitor.visit(SimplifyVisitor.java:71)
        */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.HashMap<java.lang.String, java.lang.String> ping(java.lang.String r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.utils.NetworkEnvironmentUtils.ping(java.lang.String, boolean):java.util.HashMap");
    }

    public static boolean waitFor(Process process, long j2, TimeUnit timeUnit) throws InterruptedException {
        long jNanoTime = System.nanoTime();
        long nanos = timeUnit.toNanos(j2);
        do {
            try {
                process.exitValue();
                return true;
            } catch (IllegalThreadStateException unused) {
                if (nanos > 0) {
                    Thread.sleep(Math.min(TimeUnit.NANOSECONDS.toMillis(nanos) + 1, 100L));
                }
                nanos = timeUnit.toNanos(j2) - (System.nanoTime() - jNanoTime);
            }
        } while (nanos > 0);
        return false;
    }

    public static HashMap<String, String> ping() {
        if (DCEnvHelper.isTgEnv()) {
            return null;
        }
        String defaultHost = IoTAPIClientImpl.getInstance().getDefaultHost();
        if (TextUtils.isEmpty(defaultHost)) {
            return null;
        }
        if (defaultHost.startsWith("https://")) {
            defaultHost = defaultHost.replace("https://", "");
        }
        if (TextUtils.isEmpty(defaultHost)) {
            return null;
        }
        return ping(defaultHost, false);
    }
}

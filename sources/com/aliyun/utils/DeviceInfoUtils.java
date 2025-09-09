package com.aliyun.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import androidx.health.connect.client.records.metadata.DeviceTypes;
import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.cicada.player.utils.NativeUsed;
import com.facebook.internal.AnalyticsEvents;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.bc;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.apache.commons.lang.CharUtils;
import org.json.JSONException;
import org.json.JSONObject;

@NativeUsed
/* loaded from: classes3.dex */
public class DeviceInfoUtils {
    public static File ALIVC_DATA_FILE = null;
    public static final String DATA_DIRECTORY = "AlivcData";
    private static final int MAX_WRITE_COUNT = 10;
    private static final String UUID_FILE = "alivc_data.txt";
    private static final String UUID_PROP = "UUID";
    private static c mCpuTracker;
    private static Context sAppContext;
    private static String sCpuProcessorInfo;
    private static String sDeviceUUID;
    private static String sSessionId;
    private static int sWriteUUIDCount;

    class a extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ File f12119a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f12120b;

        a(File file, String str) {
            this.f12119a = file;
            this.f12120b = str;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            boolean z2 = false;
            try {
                boolean z3 = this.f12119a.exists() || this.f12119a.createNewFile();
                Properties properties = new Properties();
                properties.setProperty(DeviceInfoUtils.UUID_PROP, this.f12120b);
                if (z3) {
                    FileWriter fileWriter = new FileWriter(this.f12119a);
                    properties.store(fileWriter, (String) null);
                    fileWriter.close();
                    z2 = true;
                }
            } catch (Throwable unused) {
            }
            DeviceInfoUtils.access$008();
            if (z2 || DeviceInfoUtils.sWriteUUIDCount >= 10) {
                return;
            }
            DeviceInfoUtils.writeUUIDToFile(this.f12119a, this.f12120b);
        }
    }

    static {
        f.f();
        ALIVC_DATA_FILE = null;
    }

    static /* synthetic */ int access$008() {
        int i2 = sWriteUUIDCount;
        sWriteUUIDCount = i2 + 1;
        return i2;
    }

    private static String canGetContext() {
        return getSDKContext() != null ? "true" : RequestConstant.FALSE;
    }

    public static String generateNewSessionId() {
        String strReplace = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
        sSessionId = strReplace;
        return strReplace;
    }

    public static String getApplicationName() throws PackageManager.NameNotFoundException {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getSDKContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(getSDKContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            packageManager = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public static String getApplicationVersion() {
        try {
            if (getSDKContext() != null) {
                return getSDKContext().getPackageManager().getPackageInfo(getSDKContext().getPackageName(), 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return "";
    }

    public static String getCPUInfo() {
        String str;
        try {
            str = (String) Class.forName("android.os.SystemProperties").getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class, String.class).invoke(null, "ro.board.platform", "");
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        return str.equals("") ? Build.HARDWARE : str;
    }

    public static String getCPUProcessorInfo() throws Throwable {
        if (!TextUtils.isEmpty(sCpuProcessorInfo)) {
            return sCpuProcessorInfo;
        }
        requestCPUInfo();
        return sCpuProcessorInfo;
    }

    public static String getCPUUsageRatio() {
        if (mCpuTracker == null) {
            mCpuTracker = new c();
        }
        return String.valueOf(mCpuTracker.e());
    }

    private static String getCacheDir() {
        File externalCacheDir;
        return (getSDKContext() == null || (externalCacheDir = getSDKContext().getExternalCacheDir()) == null || !externalCacheDir.exists()) ? "" : externalCacheDir.getAbsolutePath();
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    private static String getDeviceFeature() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (sAppContext != null) {
            try {
                jSONObject.put("UIModeType", getUIModeType());
            } catch (Exception unused) {
            }
            putFeature(jSONObject, "android.hardware.audio.low_latency");
            putFeature(jSONObject, "android.hardware.bluetooth");
            putFeature(jSONObject, "android.hardware.bluetooth_le");
            putFeature(jSONObject, "android.hardware.screen.landscape");
            putFeature(jSONObject, "android.hardware.screen.portrait");
            int i2 = Build.VERSION.SDK_INT;
            putFeature(jSONObject, "android.hardware.type.watch");
            putFeature(jSONObject, "android.hardware.audio.output");
            putFeature(jSONObject, "android.software.live_tv");
            putFeature(jSONObject, "android.hardware.opengles.aep");
            putFeature(jSONObject, "android.hardware.audio.pro");
            putFeature(jSONObject, "android.hardware.type.automotive");
            putFeature(jSONObject, "android.hardware.sensor.hifi_sensors");
            putFeature(jSONObject, "android.software.midi");
            if (i2 >= 24) {
                putFeature(jSONObject, "android.software.picture_in_picture");
                putFeature(jSONObject, "android.hardware.vr.high_performance");
                putFeature(jSONObject, "android.hardware.vulkan.level");
                putFeature(jSONObject, "android.hardware.vulkan.version");
            }
            if (i2 >= 27) {
                putFeature(jSONObject, "android.hardware.ram.low");
                putFeature(jSONObject, "android.hardware.ram.normal");
            }
            if (i2 >= 26) {
                putFeature(jSONObject, "android.software.activities_on_secondary_displays");
                putFeature(jSONObject, "android.hardware.type.embedded");
                putFeature(jSONObject, "android.hardware.vr.headtracking");
                putFeature(jSONObject, "android.hardware.vulkan.compute");
            }
            putFeature(jSONObject, "android.hardware.touchscreen");
            putFeature(jSONObject, "android.hardware.faketouch");
            putFeature(jSONObject, "android.hardware.telephony");
            putFeature(jSONObject, "android.hardware.camera");
            putFeature(jSONObject, "android.hardware.nfc");
            putFeature(jSONObject, "android.hardware.location.gps");
            putFeature(jSONObject, "android.hardware.microphone");
            putFeature(jSONObject, "android.hardware.sensor.compass");
        }
        return jSONObject.toString();
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static synchronized String getDeviceUUID() {
        if (!TextUtils.isEmpty(sDeviceUUID)) {
            return sDeviceUUID;
        }
        if (ALIVC_DATA_FILE == null) {
            ALIVC_DATA_FILE = new File(getSDKContext().getCacheDir(), DATA_DIRECTORY);
        }
        File file = new File(ALIVC_DATA_FILE, UUID_FILE);
        try {
            if (ALIVC_DATA_FILE.exists() || ALIVC_DATA_FILE.mkdir()) {
                Properties properties = new Properties();
                FileReader fileReader = new FileReader(file);
                properties.load(fileReader);
                fileReader.close();
                sDeviceUUID = properties.getProperty(UUID_PROP);
            }
        } catch (Throwable unused) {
        }
        if (TextUtils.isEmpty(sDeviceUUID)) {
            sWriteUUIDCount = 0;
            String strReplace = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
            sDeviceUUID = strReplace;
            writeUUIDToFile(file, strReplace);
        }
        return sDeviceUUID;
    }

    public static String getElectricUsageRatio() {
        if (sAppContext == null) {
            return "0";
        }
        try {
            Intent intentRegisterReceiver = sAppContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            return String.valueOf((int) (((intentRegisterReceiver != null ? intentRegisterReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) : -1) / (intentRegisterReceiver != null ? intentRegisterReceiver.getIntExtra("scale", -1) : -1)) * 100.0f));
        } catch (Throwable unused) {
            return "0";
        }
    }

    public static String getGPUInfo() {
        return "";
    }

    public static String getMemoryTotal() {
        try {
            Context context = sAppContext;
            if (context == null) {
                return "0";
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return ((int) (memoryInfo.totalMem / 1048576.0f)) + "";
        } catch (Exception unused) {
            return "0";
        }
    }

    public static String getMemoryUsage() {
        if (((ActivityManager) sAppContext.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getProcessMemoryInfo(new int[]{Process.myPid()}).length == 0) {
            return "0";
        }
        return (r0[0].getTotalPss() / 1024.0f) + "";
    }

    public static String getNetworkDnsInfo() {
        LinkProperties linkProperties;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) sAppContext.getSystemService("connectivity");
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null || (linkProperties = connectivityManager.getLinkProperties(activeNetwork)) == null) {
                return "NoActive";
            }
            List<InetAddress> dnsServers = linkProperties.getDnsServers();
            StringBuilder sb = new StringBuilder();
            for (InetAddress inetAddress : dnsServers) {
                if (inetAddress.getHostAddress() != null) {
                    sb.append(inetAddress.getHostAddress());
                    sb.append(",");
                }
            }
            return sb.length() == 0 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : sb.toString();
        } catch (Exception unused) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getNetworkType() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) sAppContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "NoActive";
            }
            String subtypeName = activeNetworkInfo.getSubtypeName();
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() != 0) {
                return subtypeName;
            }
            int subtype = activeNetworkInfo.getSubtype();
            if (subtype == 20) {
                return "5G";
            }
            switch (subtype) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                        return "Mobile:" + subtypeName;
                    }
                    return "3G";
            }
        } catch (Exception unused) {
            return "Unknow";
        }
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getOpenGLVersion() {
        ConfigurationInfo deviceConfigurationInfo;
        Context context = sAppContext;
        if (context != null) {
            try {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
                if (activityManager != null && (deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo()) != null) {
                    return Integer.toHexString(Integer.parseInt(deviceConfigurationInfo.reqGlEsVersion + ""));
                }
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static Context getSDKContext() {
        return sAppContext;
    }

    public static String getSDKVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getTerminalType() {
        try {
            Context context = sAppContext;
            return (context == null || context.getResources() == null || sAppContext.getResources().getConfiguration() == null) ? "phone" : (sAppContext.getResources().getConfiguration().screenLayout & 15) >= 3 ? "pad" : "phone";
        } catch (Throwable unused) {
            return "phone";
        }
    }

    private static String getUIModeType() throws Exception {
        int currentModeType = ((UiModeManager) sAppContext.getSystemService("uimode")).getCurrentModeType();
        return currentModeType != 6 ? currentModeType != 7 ? currentModeType != 15 ? currentModeType != 1 ? currentModeType != 2 ? currentModeType != 3 ? currentModeType != 4 ? "UNDEFINED" : "TELEVISION" : "CAR" : "DESK" : "NORMAL" : "MASK" : "VR_HEADSET" : DeviceTypes.WATCH;
    }

    public static void loadClass() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @NativeUsed
    private static String native_getDeviceInfo(String str) {
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2133529830:
                if (str.equals(bc.H)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1940306024:
                if (str.equals("network_dns")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1628011446:
                if (str.equals("cpu_usage")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1453464803:
                if (str.equals("terminal_type")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1287148950:
                if (str.equals("application_id")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1182845946:
                if (str.equals("os_name")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1117382559:
                if (str.equals("gpu_info")) {
                    c2 = 6;
                    break;
                }
                break;
            case -1010275000:
                if (str.equals("opengl_version")) {
                    c2 = 7;
                    break;
                }
                break;
            case -977897239:
                if (str.equals("application_version")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -902394881:
                if (str.equals("electric_usage")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -601148322:
                if (str.equals(bc.F)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -591076352:
                if (str.equals("device_model")) {
                    c2 = 11;
                    break;
                }
                break;
            case -553266608:
                if (str.equals("cache_dir")) {
                    c2 = '\f';
                    break;
                }
                break;
            case -379364393:
                if (str.equals("can_get_context")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case -376724013:
                if (str.equals("sdk_version")) {
                    c2 = 14;
                    break;
                }
                break;
            case -19457365:
                if (str.equals(bc.T)) {
                    c2 = 15;
                    break;
                }
                break;
            case 589850:
                if (str.equals("application_name")) {
                    c2 = 16;
                    break;
                }
                break;
            case 3601339:
                if (str.equals(DeviceCommonConstants.KEY_DEVICE_ID)) {
                    c2 = 17;
                    break;
                }
                break;
            case 216651451:
                if (str.equals("cpu_processor")) {
                    c2 = 18;
                    break;
                }
                break;
            case 501310693:
                if (str.equals("cpu_info")) {
                    c2 = 19;
                    break;
                }
                break;
            case 672836989:
                if (str.equals(bc.f21426y)) {
                    c2 = 20;
                    break;
                }
                break;
            case 1000114701:
                if (str.equals("device_feature")) {
                    c2 = 21;
                    break;
                }
                break;
            case 1270116442:
                if (str.equals("mem_total")) {
                    c2 = 22;
                    break;
                }
                break;
            case 1271141047:
                if (str.equals("mem_usage")) {
                    c2 = 23;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return getDeviceManufacturer();
            case 1:
                return getNetworkDnsInfo();
            case 2:
                return getCPUUsageRatio();
            case 3:
                return getTerminalType();
            case 4:
                return getSDKContext().getPackageName();
            case 5:
                return "android";
            case 6:
                return getGPUInfo();
            case 7:
                return getOpenGLVersion();
            case '\b':
                return getApplicationVersion();
            case '\t':
                return getElectricUsageRatio();
            case '\n':
                return getDeviceBrand();
            case 11:
                return getDeviceModel();
            case '\f':
                return getCacheDir();
            case '\r':
                return canGetContext();
            case 14:
                return getSDKVersion();
            case 15:
                return getNetworkType();
            case 16:
                return getApplicationName();
            case 17:
                return getDeviceUUID();
            case 18:
                return getCPUProcessorInfo();
            case 19:
                return getCPUInfo();
            case 20:
                return getOSVersion();
            case 21:
                return getDeviceFeature();
            case 22:
                return getMemoryTotal();
            case 23:
                return getMemoryUsage();
            default:
                return "";
        }
    }

    private static void putFeature(JSONObject jSONObject, String str) throws JSONException {
        try {
            jSONObject.put(str, sAppContext.getPackageManager().hasSystemFeature(str) ? "1" : "0");
        } catch (Exception unused) {
        }
    }

    private static void requestCPUInfo() throws Throwable {
        BufferedReader bufferedReader;
        FileReader fileReader;
        FileReader fileReader2 = null;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            try {
                bufferedReader = new BufferedReader(fileReader);
            } catch (Exception unused) {
                bufferedReader = null;
            } catch (Throwable th) {
                bufferedReader = null;
                fileReader2 = fileReader;
                th = th;
            }
        } catch (Exception unused2) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        try {
            String line = bufferedReader.readLine();
            int i2 = 0;
            while (true) {
                i2++;
                if (i2 >= 30) {
                    break;
                }
                String[] strArrSplit = line.split(":\\s+", 2);
                if (strArrSplit.length > 1 && strArrSplit[0].contains("Processor")) {
                    sCpuProcessorInfo = strArrSplit[1];
                }
                if (TextUtils.isEmpty(sCpuProcessorInfo)) {
                    line = bufferedReader.readLine();
                }
            }
            try {
                fileReader.close();
            } catch (IOException unused3) {
            }
            try {
                bufferedReader.close();
            } catch (IOException unused4) {
            }
            try {
                fileReader.close();
                break;
            } catch (IOException unused5) {
            }
        } catch (Exception unused6) {
            fileReader2 = fileReader;
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                } catch (IOException unused7) {
                }
            }
            if (bufferedReader == null) {
                return;
            }
            bufferedReader.close();
        } catch (Throwable th3) {
            th = th3;
            fileReader2 = fileReader;
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                } catch (IOException unused8) {
                }
            }
            if (bufferedReader == null) {
                throw th;
            }
            try {
                bufferedReader.close();
                throw th;
            } catch (IOException unused9) {
                throw th;
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException unused10) {
        }
    }

    public static void setSDKContext(Context context) {
        if (context != null) {
            context = context.getApplicationContext();
        }
        sAppContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeUUIDToFile(File file, String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return;
        }
        new Timer().schedule(new a(file, str), 3000L);
    }
}

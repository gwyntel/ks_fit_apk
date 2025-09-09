package com.aliyun.alink.sdk.bone.plugins.system;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.alink.sdk.bone.plugins.app.ConvertUtils;
import com.aliyun.alink.sdk.bone.plugins.config.BoneConfig;
import com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper;
import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneMethod;
import com.aliyun.iot.aep.sdk.jsbridge.annotation.BoneService;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.xiaomi.mipush.sdk.Constants;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

@BoneService(name = BoneSystem.API_NAME)
/* loaded from: classes2.dex */
public class BoneSystem extends BaseBoneService {
    public static final String API_NAME = "BoneSystem";

    /* renamed from: a, reason: collision with root package name */
    private Context f11479a;

    /* renamed from: b, reason: collision with root package name */
    private BroadcastReceiver f11480b = null;

    /* renamed from: c, reason: collision with root package name */
    private BroadcastReceiver f11481c = null;

    /* renamed from: d, reason: collision with root package name */
    private KeyBoardListenerHelper f11482d;

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected() || activeNetworkInfo.getTypeName() == null || !"wifi".equalsIgnoreCase(activeNetworkInfo.getTypeName())) {
            return "";
        }
        String ssid = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getSSID();
        return (TextUtils.isEmpty(ssid) || TextUtils.equals("<unknown ssid>", ssid)) ? "" : ssid.replace("\"", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String d(android.content.Context r3) {
        /*
            java.lang.String r0 = "none"
            if (r3 != 0) goto L5
            return r0
        L5:
            java.lang.String r1 = "connectivity"
            java.lang.Object r3 = r3.getSystemService(r1)
            android.net.ConnectivityManager r3 = (android.net.ConnectivityManager) r3
            if (r3 != 0) goto L10
            return r0
        L10:
            android.net.NetworkInfo r3 = r3.getActiveNetworkInfo()
            if (r3 == 0) goto L69
            boolean r1 = r3.isAvailable()
            if (r1 == 0) goto L69
            boolean r1 = r3.isConnected()
            if (r1 == 0) goto L69
            java.lang.String r1 = r3.getTypeName()
            if (r1 != 0) goto L29
            goto L69
        L29:
            java.lang.String r0 = r3.getTypeName()
            java.lang.String r1 = "mobile"
            boolean r2 = r1.equalsIgnoreCase(r0)
            if (r2 == 0) goto L64
            int r0 = r3.getSubtype()
            java.lang.String r3 = r3.getSubtypeName()
            java.lang.String r2 = "3g"
            switch(r0) {
                case 1: goto L62;
                case 2: goto L62;
                case 3: goto L5d;
                case 4: goto L62;
                case 5: goto L5d;
                case 6: goto L5d;
                case 7: goto L62;
                case 8: goto L5d;
                case 9: goto L5d;
                case 10: goto L5d;
                case 11: goto L62;
                case 12: goto L5d;
                case 13: goto L5f;
                case 14: goto L5d;
                case 15: goto L5d;
                default: goto L42;
            }
        L42:
            java.lang.String r0 = "TD-SCDMA"
            boolean r0 = r0.equalsIgnoreCase(r3)
            if (r0 != 0) goto L5d
            java.lang.String r0 = "WCDMA"
            boolean r0 = r0.equalsIgnoreCase(r3)
            if (r0 != 0) goto L5d
            java.lang.String r0 = "CDMA2000"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L5b
            goto L5d
        L5b:
            r0 = r1
            goto L64
        L5d:
            r0 = r2
            goto L64
        L5f:
            java.lang.String r0 = "4g"
            goto L64
        L62:
            java.lang.String r0 = "2g"
        L64:
            java.lang.String r3 = r0.toLowerCase()
            return r3
        L69:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem.d(android.content.Context):java.lang.String");
    }

    @BoneMethod
    public void getContainerInfo(BoneCallback boneCallback) {
        Application application = (Application) this.f11479a.getApplicationContext();
        String packageName = application.getPackageName();
        PackageManager packageManager = application.getPackageManager();
        try {
            String string = packageManager.getApplicationLabel(this.f11479a.getApplicationInfo()).toString();
            String str = packageManager.getPackageInfo(packageName, 0).versionName;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appName", string);
            jSONObject.put("appVersion", str);
            jSONObject.put("runtime", "bone-aep-rn");
            jSONObject.put("apiLevel", 6);
            boneCallback.success(jSONObject);
        } catch (Exception e2) {
            boneCallback.failed("608", e2.getMessage(), "");
        }
    }

    @BoneMethod
    public void getNetworkType(BoneCallback boneCallback) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", d(this.f11479a));
            boneCallback.success(jSONObject);
        } catch (Exception e2) {
            ALog.e("BoneSystemPlugin", "exception happen");
            boneCallback.failed("608", e2.getMessage(), "");
        }
    }

    @BoneMethod
    public void getSystemInfo(BoneCallback boneCallback) {
        Locale locale = Locale.getDefault();
        String str = locale.getLanguage() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + locale.getCountry();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("phone", Build.BOARD + " " + Build.MODEL);
            jSONObject.put(DispatchConstants.PLATFORM, AnalyticsConstants.SDK_TYPE);
            jSONObject.put("systemVersion", String.valueOf(Build.VERSION.SDK_INT));
            jSONObject.put(bc.N, str);
            jSONObject.put("serverEnv", BoneConfig.get("serverEnv"));
            jSONObject.put("pluginEnv", BoneConfig.get("pluginEnv"));
            boneCallback.success(jSONObject);
        } catch (Exception e2) {
            boneCallback.failed("608", e2.getMessage(), "");
        }
    }

    @BoneMethod
    public void getTimeZone(JSONObject jSONObject, BoneCallback boneCallback) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            TimeZone timeZone = TimeZone.getDefault();
            jSONObject2.putOpt("timeZoneId", timeZone.getID());
            jSONObject2.putOpt("displayName", timeZone.getDisplayName());
            jSONObject2.putOpt("rawOffset", Integer.valueOf(timeZone.getRawOffset()));
            boneCallback.success(jSONObject2);
        } catch (Exception e2) {
            ALog.e("BoneSystemPlugin", "exception happen");
            boneCallback.failed("608", e2.getMessage(), "");
        }
    }

    @BoneMethod
    public void isBluetoothEnabled(BoneCallback boneCallback) {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            JSONObject jSONObject = new JSONObject();
            if (defaultAdapter == null) {
                ALog.e("BoneSystemPlugin", "Device does not support Bluetooth");
                jSONObject.put("enable", false);
            } else {
                jSONObject.put("enable", defaultAdapter.isEnabled());
            }
            boneCallback.success(jSONObject);
        } catch (Exception e2) {
            ALog.e("BoneSystemPlugin", "exception happen");
            boneCallback.failed("608", e2.getMessage(), "");
        }
    }

    @BoneMethod
    public void isMobileDataEnable(BoneCallback boneCallback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.f11479a.getSystemService("connectivity");
        Boolean bool = null;
        try {
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", null);
            declaredMethod.setAccessible(true);
            bool = (Boolean) declaredMethod.invoke(connectivityManager, null);
        } catch (Exception e2) {
            boneCallback.failed("608", e2.getMessage(), "");
            e2.printStackTrace();
        }
        if (bool == null) {
            boneCallback.failed(SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION, "Runtime Exception", "");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enable", bool.booleanValue());
            boneCallback.success(jSONObject);
        } catch (Exception e3) {
            ALog.e("BoneSystemPlugin", "exception happen");
            boneCallback.failed("608", e3.getMessage(), "");
        }
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService, com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
    public void onDestroy() {
        BroadcastReceiver broadcastReceiver;
        super.onDestroy();
        Context context = this.f11479a;
        if (context != null && (broadcastReceiver = this.f11480b) != null) {
            context.unregisterReceiver(broadcastReceiver);
            this.f11480b = null;
        }
        BroadcastReceiver broadcastReceiver2 = this.f11481c;
        if (broadcastReceiver2 != null) {
            this.f11479a.unregisterReceiver(broadcastReceiver2);
            this.f11481c = null;
        }
        this.f11479a = null;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService, com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
    public void onInitialize(Context context) {
        super.onInitialize(context);
        this.f11479a = context;
    }

    @BoneMethod
    public void sendBroadcast(JSContext jSContext, String str, JSONObject jSONObject, BoneCallback boneCallback) {
        Activity currentActivity;
        if (jSContext == null || (currentActivity = jSContext.getCurrentActivity()) == null) {
            boneCallback.failed("608", "FAILED_NO_INITIALIZED", "");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            boneCallback.failed("400", "name is required", "");
            return;
        }
        boneCallback.success();
        Intent intent = new Intent(str);
        try {
            intent.putExtras(ConvertUtils.toBundle(jSONObject));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LocalBroadcastManager.getInstance(currentActivity).sendBroadcast(intent);
    }

    @BoneMethod
    public void setStatusBarStyle(JSContext jSContext, int i2, BoneCallback boneCallback) {
        if (jSContext.getCurrentActivity() == null) {
            boneCallback.failed("608", "currentActivity == null", "");
        } else {
            boneCallback.success();
        }
    }

    @BoneMethod
    public void startListenBluetoothStatusChange(final JSContext jSContext, BoneCallback boneCallback) {
        if (jSContext == null) {
            boneCallback.failed("608", "FAILED_NO_INITIALIZED", "");
            return;
        }
        if (this.f11481c == null) {
            this.f11481c = new BroadcastReceiver() { // from class: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (jSContext == null) {
                        return;
                    }
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    String strB = (defaultAdapter == null || !defaultAdapter.isEnabled()) ? "OFF" : "ON";
                    if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                        strB = BoneSystem.b(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0));
                    }
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("state", strB);
                        jSContext.emitter("BoneBluetoothStatusChanged", jSONObject);
                    } catch (Exception unused) {
                        ALog.e("BoneSystemPlugin", "exception happen");
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            this.f11479a.registerReceiver(this.f11481c, intentFilter);
        }
        boneCallback.success();
    }

    @BoneMethod
    public void startListenKeyBoardChange(final JSContext jSContext, BoneCallback boneCallback) {
        Log.d("BoneSystemPlugin", "startListenKeyBoardChange() called with: jsContext = [" + jSContext + "], callback = [" + boneCallback + "]");
        try {
            jSContext.getCurrentActivity().runOnUiThread(new Runnable() { // from class: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem.3
                @Override // java.lang.Runnable
                public void run() {
                    BoneSystem.this.f11482d = new KeyBoardListenerHelper(jSContext.getCurrentActivity());
                    BoneSystem.this.f11482d.setOnKeyBoardChangeListener(new KeyBoardListenerHelper.OnKeyBoardChangeListener() { // from class: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem.3.1
                        @Override // com.aliyun.alink.sdk.bone.plugins.ut.KeyBoardListenerHelper.OnKeyBoardChangeListener
                        public void OnKeyBoardChange(boolean z2, int i2) throws JSONException {
                            Log.d("BoneSystemPlugin", "OnKeyBoardChange() called with: isShow = [" + z2 + "], keyBoardHeight = [" + i2 + "]");
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("isShow", z2);
                                jSONObject.put("keyBoardHeight", i2);
                                jSContext.emitter("onKeyBoardChange", jSONObject);
                            } catch (Exception unused) {
                            }
                        }
                    });
                }
            });
        } catch (Exception unused) {
        }
        boneCallback.success();
    }

    @BoneMethod
    public void startListenNetworkStatusChange(final JSContext jSContext, BoneCallback boneCallback) {
        if (jSContext == null) {
            boneCallback.failed("608", "FAILED_NO_INITIALIZED", "");
            return;
        }
        if (this.f11480b == null) {
            this.f11480b = new BroadcastReceiver() { // from class: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) throws JSONException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (jSContext == null) {
                        return;
                    }
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", BoneSystem.d(context));
                        jSONObject.put("ssid", BoneSystem.c(context));
                        jSContext.emitter("BoneNetworkStatusChanged", jSONObject);
                    } catch (Exception unused) {
                        ALog.e("BoneSystemPlugin", "exception happen");
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION);
            this.f11479a.registerReceiver(this.f11480b, intentFilter);
        }
        boneCallback.success();
    }

    @BoneMethod
    public void stopListenBluetoothStatusChange(BoneCallback boneCallback) {
        Log.d("BoneSystemPlugin", "stopListenBluetoothStatusChange() called with: callback = [" + boneCallback + "]");
        BroadcastReceiver broadcastReceiver = this.f11481c;
        if (broadcastReceiver != null) {
            this.f11479a.unregisterReceiver(broadcastReceiver);
            this.f11481c = null;
        }
        boneCallback.success();
    }

    @BoneMethod
    public void stopListenKeyBoardChange(BoneCallback boneCallback) {
        try {
            this.f11482d.destroy();
            this.f11482d = null;
        } catch (Exception unused) {
        }
        boneCallback.success();
    }

    @BoneMethod
    public void stopListenNetworkStatusChange(BoneCallback boneCallback) {
        BroadcastReceiver broadcastReceiver = this.f11480b;
        if (broadcastReceiver != null) {
            this.f11479a.unregisterReceiver(broadcastReceiver);
            this.f11480b = null;
        }
        boneCallback.success();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(int i2) {
        switch (i2) {
            case 10:
                return "OFF";
            case 11:
                return "TURNING_ON";
            case 12:
                return "ON";
            case 13:
                return "TURNING_OFF";
            default:
                return "unknown (" + i2 + ")";
        }
    }
}

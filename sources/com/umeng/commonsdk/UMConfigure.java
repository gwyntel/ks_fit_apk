package com.umeng.commonsdk;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.facebook.internal.AnalyticsEvents;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.az;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bg;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.vshelper.PageNameMonitor;
import com.umeng.ccg.ActionInfo;
import com.umeng.ccg.CcgAgent;
import com.umeng.ccg.b;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.listener.OnGetOaidListener;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.TagHelper;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.a;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.c;
import com.umeng.commonsdk.utils.d;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.PushAgent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UMConfigure {
    public static final int DEVICE_TYPE_BOX = 2;
    public static final int DEVICE_TYPE_PHONE = 1;
    private static final String KEY_FILE_NAME_APPKEY = "APPKEY";
    private static final String KEY_FILE_NAME_LOG = "LOG";
    private static final String KEY_METHOD_NAME_PUSH_SETCHANNEL = "setMessageChannel";
    private static final String KEY_METHOD_NAME_PUSH_SET_SECRET = "setSecret";
    private static final String KEY_METHOD_NAME_SETAPPKEY = "setAppkey";
    private static final String KEY_METHOD_NAME_SETCHANNEL = "setChannel";
    private static final String KEY_METHOD_NAME_SETDEBUGMODE = "setDebugMode";
    private static final String TAG = "UMConfigure";
    private static final String WRAPER_TYPE_COCOS2DX_X = "Cocos2d-x";
    private static final String WRAPER_TYPE_COCOS2DX_XLUA = "Cocos2d-x_lua";
    private static final String WRAPER_TYPE_FLUTTER = "flutter";
    private static final String WRAPER_TYPE_HYBRID = "hybrid";
    private static final String WRAPER_TYPE_NATIVE = "native";
    private static final String WRAPER_TYPE_PHONEGAP = "phonegap";
    private static final String WRAPER_TYPE_REACTNATIVE = "react-native";
    private static final String WRAPER_TYPE_UNITY = "Unity";
    private static final String WRAPER_TYPE_WEEX = "weex";
    private static boolean debugLog = false;
    private static OnGetOaidListener mOnGetOaidListener;
    public static UMLog umDebugLog = new UMLog();
    private static boolean preInitComplete = false;
    private static Object PreInitLock = new Object();
    public static String sAppkey = "";
    public static String sChannel = "";
    public static boolean isInit = false;
    private static Object lock = new Object();
    private static int preInitInvokedFlag = 0;
    private static int policyGrantInvokedFlag = 0;
    private static int policyGrantResult = 0;
    private static boolean shouldCheckShareSdk = true;
    private static volatile boolean shouldCollectAid = true;
    private static volatile boolean shouldCollectImei = true;
    private static volatile boolean shouldCollectImsi = true;
    private static volatile boolean shouldCollectIccid = true;
    private static volatile boolean shouldCollectWiFiMac = true;
    private static volatile boolean shouldOutputRT = false;
    public static MobclickAgent.PageMode AUTO_ACTIVITY_PAGE_COLLECTION = MobclickAgent.PageMode.AUTO;
    private static final String[] BUSINESS_TYPE = {"apm", "push", "share", "ulink", "uverify", "usms", "urec", "abtest", "game", bc.al};
    private static boolean isFinish = false;
    private static Object lockObject = new Object();

    public enum BS_TYPE {
        APM,
        PUSH,
        SHARE,
        ULINK,
        UVERIFY,
        USMS,
        UREC,
        ABTEST,
        GAME,
        ZID
    }

    private static boolean checkShareSdk(Class<?> cls) {
        try {
            return cls.getDeclaredField("isZyb") != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static void deleteModuleTag(BS_TYPE bs_type, String str) {
        if (bs_type == null) {
            Log.e("UMLog", UMLogCommon.SC_10025);
            return;
        }
        if (TextUtils.isEmpty(str)) {
            Log.e("UMLog", UMLogCommon.SC_10020);
            return;
        }
        try {
            if (str.trim().getBytes().length > 64) {
                Log.e("UMLog", UMLogCommon.SC_10021);
                return;
            }
            int iOrdinal = bs_type.ordinal();
            String[] strArr = BUSINESS_TYPE;
            String str2 = iOrdinal < strArr.length ? strArr[iOrdinal] : "";
            if (TextUtils.isEmpty(str2)) {
                Log.e("UMLog", UMLogCommon.SC_10022);
                return;
            }
            TagHelper.deleteModuleTag(str2 + OpenAccountUIConstants.UNDER_LINE + str);
        } catch (Throwable unused) {
        }
    }

    private static void disableAidCollect() {
        try {
            if (getClass("com.umeng.socialize.UMShareAPI") != null) {
                shouldCollectAid = false;
            }
        } catch (Throwable unused) {
        }
    }

    public static void enableAzxSwitch(boolean z2) {
        b.a(z2);
    }

    public static void enableIccidCollection(boolean z2) {
        shouldCollectIccid = z2;
    }

    public static void enableImeiCollection(boolean z2) {
        shouldCollectImei = z2;
    }

    public static void enableImsiCollection(boolean z2) {
        shouldCollectImsi = z2;
    }

    public static void enableUmcCfgSwitch(boolean z2) {
        b.b(z2);
    }

    public static void enableWiFiMacCollection(boolean z2) {
        shouldCollectWiFiMac = z2;
    }

    private static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static String getCurrentActivityName() {
        return PageNameMonitor.getInstance().getCurrentActivityName();
    }

    private static Object getDecInstanceObject(Class<?> cls) throws NoSuchMethodException, SecurityException {
        Constructor<?> declaredConstructor;
        if (cls == null) {
            return null;
        }
        try {
            declaredConstructor = cls.getDeclaredConstructor(null);
        } catch (NoSuchMethodException unused) {
            declaredConstructor = null;
        }
        if (declaredConstructor == null) {
            return null;
        }
        declaredConstructor.setAccessible(true);
        try {
            return declaredConstructor.newInstance(null);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused2) {
            return null;
        }
    }

    private static Method getDecMethod(Class<?> cls, String str, Class<?>[] clsArr) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = null;
        if (cls != null) {
            try {
                declaredMethod = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException unused) {
            }
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
            }
        }
        return declaredMethod;
    }

    public static boolean getInitStatus() {
        boolean z2;
        synchronized (lockObject) {
            z2 = isFinish;
        }
        return z2;
    }

    private static Map<String, String> getModuleTags() {
        try {
            return TagHelper.getModuleTags();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void getOaid(Context context, OnGetOaidListener onGetOaidListener) {
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "context is null !!!");
            }
        } else {
            final Context applicationContext = context.getApplicationContext();
            mOnGetOaidListener = onGetOaidListener;
            new Thread(new Runnable() { // from class: com.umeng.commonsdk.UMConfigure.3
                @Override // java.lang.Runnable
                public void run() {
                    String strB = az.b(applicationContext);
                    if (UMConfigure.mOnGetOaidListener != null) {
                        UMConfigure.mOnGetOaidListener.onGetOaid(strB);
                    }
                }
            }).start();
        }
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] strArr = new String[2];
        if (context != null) {
            try {
                strArr[0] = DeviceConfig.getDeviceIdForGeneral(context);
                strArr[1] = DeviceConfig.getMac(context);
            } catch (Exception unused) {
            }
        }
        return strArr;
    }

    public static String getUMIDString(Context context) {
        if (context != null) {
            return UMUtils.getUMId(context.getApplicationContext());
        }
        return null;
    }

    public static String getUmengZID(Context context) {
        if (context != null) {
            return UMUtils.getZid(context.getApplicationContext());
        }
        return null;
    }

    private static void ignoreSdkCheck(Context context) {
        shouldCheckShareSdk = false;
    }

    public static void init(Context context, int i2, String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        init(context, null, null, i2, str);
    }

    private static void invoke(Method method, Object obj, Object[] objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method == null || obj == null) {
            return;
        }
        try {
            method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
        }
    }

    public static boolean isDebugLog() {
        return debugLog;
    }

    private static Boolean isInForeground() {
        return Boolean.valueOf(com.umeng.analytics.vshelper.b.d());
    }

    private static boolean isPreInit() {
        boolean z2;
        synchronized (PreInitLock) {
            z2 = preInitComplete;
        }
        return z2;
    }

    private static boolean isSilentMode() {
        boolean zA = c.a();
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> isSilentMode() return: " + zA);
        return zA;
    }

    private static boolean needCheckPolicyResult(Context context) {
        File filesDir = context.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append(bg.f21492m);
        return !new File(sb.toString()).exists();
    }

    public static boolean needSendZcfgEnv(Context context) {
        File filesDir = context.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append(bg.f21491l);
        return !new File(sb.toString()).exists();
    }

    public static void onActEvent(String str, String str2, Bundle bundle) {
        CcgAgent.onActUpEvent(str, str2, bundle);
    }

    public static void preInit(Context context, String str, String str2) {
        synchronized (lock) {
            preInitInvokedFlag = 1;
        }
        preInitInternal(context, str, str2);
    }

    private static void preInitInternal(Context context, String str, String str2) {
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "preInit: context is null, pls check!");
                return;
            }
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if ("1".equals(UMUtils.getSystemProperty("debug.umeng.rtlog", "0"))) {
            shouldOutputRT = true;
        }
        if (TextUtils.isEmpty(str)) {
            str = UMUtils.getAppkeyByXML(applicationContext);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = UMUtils.getChannelByXML(applicationContext);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        sAppkey = str;
        sChannel = str2;
        UMGlobalContext.getInstance(applicationContext);
        n.a(applicationContext);
        if (!needSendZcfgEnv(applicationContext)) {
            FieldManager.a().a(applicationContext);
        }
        synchronized (PreInitLock) {
            preInitComplete = true;
        }
        boolean zA = c.a();
        if (debugLog && zA) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 当前处于静默模式!");
        }
    }

    public static void registerActionInfo(ActionInfo actionInfo) {
        CcgAgent.registerActionInfo(actionInfo);
    }

    public static void resetStorePath() {
        resetStorePrefix("um_");
    }

    public static void resetStorePrefix(String str) {
        ay.b().a(str);
    }

    private static void setCheckDevice(boolean z2) {
        AnalyticsConstants.CHECK_DEVICE = z2;
    }

    public static void setDomain(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("UMLog", "setDomain: Parameter domain is null or empty string, do nothing.");
        } else {
            UMServerURL.DEFAULT_URL = str;
            UMServerURL.SECONDARY_URL = str;
        }
    }

    public static void setEncryptEnabled(boolean z2) {
        com.umeng.commonsdk.statistics.b.a(z2);
    }

    private static void setFile(Class<?> cls, String str, String str2) throws IllegalAccessException, IllegalArgumentException {
        if (cls != null) {
            try {
                cls.getField(str).set(str, str2);
            } catch (Exception unused) {
            }
        }
    }

    private static void setLatencyWindow(long j2) {
        a.f22321c = ((int) j2) * 1000;
    }

    public static void setLogEnabled(boolean z2) {
        try {
            debugLog = z2;
            MLog.DEBUG = z2;
            Class<?> cls = getClass("com.umeng.message.PushAgent");
            Object decInstanceObject = getDecInstanceObject(cls);
            Class cls2 = Boolean.TYPE;
            invoke(getDecMethod(cls, KEY_METHOD_NAME_SETDEBUGMODE, new Class[]{cls2}), decInstanceObject, new Object[]{Boolean.valueOf(z2)});
            setFile(getClass("com.umeng.socialize.Config"), "DEBUG", z2);
            invoke(getDecMethod(getClass("com.umeng.umcrash.UMCrash"), "setDebug", new Class[]{cls2}), new Object[]{Boolean.valueOf(z2)});
        } catch (Exception e2) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + e2);
            }
        } catch (Throwable th) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + th);
            }
        }
    }

    private static void setModuleTag(BS_TYPE bs_type, String str, String str2) {
        try {
            if (bs_type == null) {
                Log.e("UMLog", UMLogCommon.SC_10024);
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (str.trim().getBytes().length > 64) {
                    Log.e("UMLog", UMLogCommon.SC_10017);
                    return;
                }
                if (str2.trim().getBytes().length > 128) {
                    Log.e("UMLog", UMLogCommon.SC_10018);
                    return;
                }
                int iOrdinal = bs_type.ordinal();
                String[] strArr = BUSINESS_TYPE;
                String str3 = iOrdinal < strArr.length ? strArr[iOrdinal] : "";
                if (TextUtils.isEmpty(str3)) {
                    Log.e("UMLog", UMLogCommon.SC_10019);
                    return;
                }
                TagHelper.setModuleTag(str3 + OpenAccountUIConstants.UNDER_LINE + str, str2);
                return;
            }
            Log.e("UMLog", UMLogCommon.SC_10016);
        } catch (Throwable unused) {
        }
    }

    public static void setProcessEvent(boolean z2) {
        AnalyticsConstants.SUB_PROCESS_EVENT = z2;
    }

    private static void setWraperType(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals("native")) {
                com.umeng.commonsdk.stateless.a.f22290a = "native";
                a.f22319a = "native";
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_X)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_COCOS2DX_X;
                a.f22319a = WRAPER_TYPE_COCOS2DX_X;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_XLUA)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_COCOS2DX_XLUA;
                a.f22319a = WRAPER_TYPE_COCOS2DX_XLUA;
            } else if (str.equals(WRAPER_TYPE_UNITY)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_UNITY;
                a.f22319a = WRAPER_TYPE_UNITY;
            } else if (str.equals(WRAPER_TYPE_REACTNATIVE)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_REACTNATIVE;
                a.f22319a = WRAPER_TYPE_REACTNATIVE;
            } else if (str.equals(WRAPER_TYPE_PHONEGAP)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_PHONEGAP;
                a.f22319a = WRAPER_TYPE_PHONEGAP;
            } else if (str.equals(WRAPER_TYPE_WEEX)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_WEEX;
                a.f22319a = WRAPER_TYPE_WEEX;
            } else if (str.equals(WRAPER_TYPE_HYBRID)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_HYBRID;
                a.f22319a = WRAPER_TYPE_HYBRID;
            } else if (str.equals(WRAPER_TYPE_FLUTTER)) {
                com.umeng.commonsdk.stateless.a.f22290a = WRAPER_TYPE_FLUTTER;
                a.f22319a = WRAPER_TYPE_FLUTTER;
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        com.umeng.commonsdk.stateless.a.f22291b = str2;
        a.f22320b = str2;
    }

    public static boolean shouldCollectAid() {
        return shouldCollectAid;
    }

    public static boolean shouldCollectIccid() {
        return shouldCollectIccid;
    }

    public static boolean shouldCollectImei() {
        return shouldCollectImei;
    }

    public static boolean shouldCollectImsi() {
        return shouldCollectImsi;
    }

    public static boolean shouldCollectWifiMac() {
        return shouldCollectWiFiMac;
    }

    public static boolean shouldOutput() {
        return shouldOutputRT;
    }

    public static void submitPolicyGrantResult(Context context, boolean z2) {
        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.B, com.umeng.commonsdk.internal.b.a(context).a(), null);
        synchronized (lock) {
            try {
                policyGrantInvokedFlag = 1;
                if (z2) {
                    policyGrantResult = 1;
                } else {
                    policyGrantResult = 2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.f22262z, com.umeng.commonsdk.internal.b.a(context).a(), Integer.valueOf(policyGrantResult));
    }

    public static void init(Context context, String str, String str2, int i2, String str3) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object objInvoke;
        Method declaredMethod;
        Class<?> cls;
        Method declaredMethod2;
        Method declaredMethod3;
        Object objInvoke2;
        Method declaredMethod4;
        try {
            try {
                if (debugLog) {
                    Log.i(TAG, "common version is 9.7.4.1+000");
                    Log.i(TAG, "common type is " + SdkVersion.SDK_TYPE);
                }
            } catch (Throwable th) {
                if (debugLog) {
                    Log.e(TAG, "init e is " + th);
                }
            }
        } catch (Exception e2) {
            if (debugLog) {
                Log.e(TAG, "init e is " + e2);
            }
        }
        if (context == null) {
            if (debugLog) {
                Log.e(TAG, "context is null !!!");
                return;
            }
            return;
        }
        if (isInit) {
            if (debugLog) {
                Log.e(TAG, "has inited !!!");
                return;
            }
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        UMGlobalContext.getInstance(applicationContext);
        if (c.a()) {
            if (!isPreInit()) {
                preInitInternal(applicationContext, str, str2);
                if (!isPreInit()) {
                    return;
                }
            }
            UMWorkDispatch.sendEvent(applicationContext, com.umeng.commonsdk.internal.a.D, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
            return;
        }
        try {
            Class<?> cls2 = getClass("com.umeng.message.PushAgent");
            if (cls2 != null && !checkShareSdk(cls2)) {
                Log.e("UMLog", UMLogCommon.SC_10015);
                if (shouldCheckShareSdk) {
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused) {
                            }
                        }
                    }.start();
                }
            }
        } catch (Throwable unused) {
        }
        try {
            Class<?> cls3 = getClass("com.umeng.socialize.UMShareAPI");
            if (cls3 != null && !checkShareSdk(cls3)) {
                Log.e("UMLog", UMLogCommon.SC_10015);
                if (shouldCheckShareSdk) {
                    new Thread() { // from class: com.umeng.commonsdk.UMConfigure.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            try {
                                Looper.prepare();
                                Toast.makeText(applicationContext, UMLogCommon.SC_10015, 1).show();
                                Looper.loop();
                            } catch (Throwable unused2) {
                            }
                        }
                    }.start();
                }
            }
        } catch (Throwable unused2) {
        }
        if (!isPreInit()) {
            preInitInternal(applicationContext, str, str2);
            if (!isPreInit()) {
                return;
            }
        }
        UMEnvelopeBuild.registerNetReceiver(applicationContext);
        UMUtils.setAppkey(applicationContext, sAppkey);
        String lastAppkey = UMUtils.getLastAppkey(applicationContext);
        if (!TextUtils.isEmpty(sAppkey) && !sAppkey.equals(lastAppkey)) {
            if (!TextUtils.isEmpty(lastAppkey) && debugLog) {
                UMLog.mutlInfo(UMLogCommon.SC_10008, 2, "");
            }
            UMUtils.setLastAppkey(applicationContext, sAppkey);
        }
        if (debugLog) {
            Log.i(TAG, "current appkey is " + sAppkey + ", last appkey is " + lastAppkey);
        }
        if (debugLog) {
            String appkeyByXML = UMUtils.getAppkeyByXML(applicationContext);
            if (!TextUtils.isEmpty(sAppkey) && !TextUtils.isEmpty(appkeyByXML) && !sAppkey.equals(appkeyByXML)) {
                UMLog.mutlInfo(UMLogCommon.SC_10011, 3, "", new String[]{"@", MqttTopic.MULTI_LEVEL_WILDCARD}, new String[]{sAppkey, appkeyByXML});
            }
        }
        UMUtils.setChannel(applicationContext, sChannel);
        if (debugLog) {
            Log.i(TAG, "channel is " + sChannel);
        }
        try {
            Method declaredMethod5 = MobclickAgent.class.getDeclaredMethod("init", Context.class);
            if (declaredMethod5 != null) {
                declaredMethod5.setAccessible(true);
                declaredMethod5.invoke(MobclickAgent.class, applicationContext);
                if (FieldManager.allow(d.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger enabled.");
                    n.a(applicationContext).b(applicationContext);
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> FirstResumeTrigger disabled.");
                }
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10003, 2, "");
                }
            }
            Class.forName("com.umeng.analytics.game.UMGameAgent");
            Method declaredMethod6 = MobclickAgent.class.getDeclaredMethod("setGameScenarioType", Context.class);
            if (declaredMethod6 != null) {
                declaredMethod6.setAccessible(true);
                declaredMethod6.invoke(MobclickAgent.class, applicationContext);
            }
            if (com.umeng.commonsdk.statistics.b.f22322a.indexOf("e") >= 0 && (declaredMethod4 = MobclickAgent.class.getDeclaredMethod("disableExceptionCatch", null)) != null) {
                declaredMethod4.setAccessible(true);
                declaredMethod4.invoke(MobclickAgent.class, null);
            }
        } catch (Throwable unused3) {
        }
        try {
            int i3 = PushAgent.f22603a;
            Method declaredMethod7 = PushAgent.class.getDeclaredMethod("init", Context.class);
            if (declaredMethod7 != null) {
                declaredMethod7.setAccessible(true);
                declaredMethod7.invoke(PushAgent.class, applicationContext);
            }
        } catch (Throwable unused4) {
        }
        try {
            Method declaredMethod8 = MessageSharedPrefs.class.getDeclaredMethod("getInstance", Context.class);
            if (declaredMethod8 != null && (objInvoke2 = declaredMethod8.invoke(MessageSharedPrefs.class, applicationContext)) != null) {
                Method declaredMethod9 = MessageSharedPrefs.class.getDeclaredMethod("setMessageAppKey", String.class);
                if (declaredMethod9 != null) {
                    declaredMethod9.setAccessible(true);
                    declaredMethod9.invoke(objInvoke2, sAppkey);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10004, 2, "");
                    }
                }
                Method declaredMethod10 = MessageSharedPrefs.class.getDeclaredMethod(KEY_METHOD_NAME_PUSH_SETCHANNEL, String.class);
                if (declaredMethod10 != null) {
                    declaredMethod10.setAccessible(true);
                    declaredMethod10.invoke(objInvoke2, sChannel);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10005, 2, "");
                    }
                }
                if (!TextUtils.isEmpty(str3)) {
                    if (debugLog) {
                        Log.i(TAG, "push secret is " + str3);
                    }
                    Method declaredMethod11 = MessageSharedPrefs.class.getDeclaredMethod("setMessageAppSecret", String.class);
                    if (declaredMethod11 != null) {
                        declaredMethod11.setAccessible(true);
                        declaredMethod11.invoke(objInvoke2, str3);
                        if (debugLog) {
                            UMLog.mutlInfo(UMLogCommon.SC_10009, 2, "");
                        }
                    }
                }
            }
        } catch (Exception unused5) {
        }
        try {
            Class<?> cls4 = getClass("com.umeng.socialize.UMShareAPI");
            setFile(cls4, "APPKEY", sAppkey);
            if (cls4 != null && (declaredMethod3 = cls4.getDeclaredMethod("init", Context.class, String.class)) != null) {
                declaredMethod3.setAccessible(true);
                declaredMethod3.invoke(cls4, applicationContext, sAppkey);
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10006, 2, "");
                }
            }
        } catch (Throwable unused6) {
        }
        AnalyticsConstants.setDeviceType(i2);
        try {
            Class<?> cls5 = Class.forName("com.umeng.error.UMError");
            Method declaredMethod12 = cls5.getDeclaredMethod("init", Context.class);
            if (declaredMethod12 != null) {
                declaredMethod12.setAccessible(true);
                declaredMethod12.invoke(cls5, applicationContext);
                if (debugLog) {
                    UMLog.mutlInfo(UMLogCommon.SC_10010, 2, "");
                }
            }
        } catch (Throwable unused7) {
        }
        try {
            if (getClass("com.umeng.umefs.UMEfs") == null && (cls = getClass("com.umeng.umcrash.UMCrash")) != null) {
                if (SdkVersion.SDK_TYPE == 1 && (declaredMethod2 = cls.getDeclaredMethod("useIntlServices", Boolean.TYPE)) != null) {
                    declaredMethod2.setAccessible(true);
                    declaredMethod2.invoke(cls, Boolean.TRUE);
                }
                Method declaredMethod13 = cls.getDeclaredMethod("init", Context.class, String.class, String.class);
                if (declaredMethod13 != null) {
                    declaredMethod13.setAccessible(true);
                    declaredMethod13.invoke(cls, applicationContext, sAppkey, sChannel);
                    if (debugLog) {
                        UMLog.mutlInfo(UMLogCommon.SC_10014, 2, "");
                    }
                }
            }
        } catch (Throwable unused8) {
        }
        try {
            Method declaredMethod14 = Class.forName("com.umeng.vt.facade.EventFacade").getDeclaredMethod("init", Application.class, String.class, String.class, Integer.TYPE, String.class);
            if (declaredMethod14 != null) {
                declaredMethod14.invoke(null, applicationContext, sAppkey, sChannel, Integer.valueOf(i2), str3);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 EventFacade 成功.");
            }
        } catch (Throwable unused9) {
        }
        try {
            Method declaredMethod15 = Class.forName("com.umeng.vt.common.VTTracker").getDeclaredMethod("init", Application.class, String.class);
            if (declaredMethod15 != null) {
                declaredMethod15.invoke(null, applicationContext, sAppkey);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>>初始化 VTTracker 成功.");
            }
        } catch (Throwable unused10) {
        }
        synchronized (lockObject) {
            isFinish = true;
        }
        if (needCheckPolicyResult(applicationContext)) {
            synchronized (lock) {
                try {
                    if (preInitInvokedFlag == 0) {
                        Log.e(TAG, UMLogCommon.SC_10026);
                    }
                    if (policyGrantResult == 0) {
                        Log.e(TAG, UMLogCommon.SC_10028);
                    }
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(com.umeng.commonsdk.internal.a.J, preInitInvokedFlag);
                        jSONObject.put(com.umeng.commonsdk.internal.a.K, policyGrantInvokedFlag);
                        jSONObject.put("policyGrantResult", policyGrantResult);
                        UMWorkDispatch.sendEvent(applicationContext, com.umeng.commonsdk.internal.a.A, com.umeng.commonsdk.internal.b.a(applicationContext).a(), jSONObject);
                    } catch (Throwable unused11) {
                    }
                } finally {
                }
            }
        }
        if (needSendZcfgEnv(applicationContext)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走零号报文发送逻辑");
            UMWorkDispatch.sendEvent(applicationContext, com.umeng.commonsdk.internal.a.f22252p, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
        } else if (UMUtils.isMainProgress(applicationContext)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 走正常逻辑.");
            if (FieldManager.b()) {
                UMWorkDispatch.sendEvent(applicationContext, com.umeng.commonsdk.internal.a.f22261y, com.umeng.commonsdk.internal.b.a(applicationContext).a(), null);
            }
            if (FieldManager.allow(d.G)) {
                com.umeng.commonsdk.internal.c.a(applicationContext, false);
            }
        }
        isDebugLog();
        try {
            Context applicationContext2 = context.getApplicationContext();
            Class<?> cls6 = Class.forName("com.umeng.cconfig.UMRemoteConfig");
            Method declaredMethod16 = cls6.getDeclaredMethod("getInstance", null);
            if (declaredMethod16 != null && (objInvoke = declaredMethod16.invoke(cls6, null)) != null && (declaredMethod = cls6.getDeclaredMethod("init", Context.class)) != null) {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(objInvoke, applicationContext2);
            }
        } catch (Exception unused12) {
        }
        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.F, com.umeng.commonsdk.internal.b.a(context).a(), null);
        if (!isInit) {
            isInit = true;
        }
        if (debugLog) {
            UMLog.mutlInfo(UMLogCommon.SC_10029, 2, "");
        }
    }

    private static void invoke(Method method, Object[] objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method != null) {
            try {
                method.invoke(null, objArr);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        if (cls != null) {
            try {
                cls.getField(str).set(str, Boolean.valueOf(z2));
            } catch (Exception unused) {
            }
        }
    }
}

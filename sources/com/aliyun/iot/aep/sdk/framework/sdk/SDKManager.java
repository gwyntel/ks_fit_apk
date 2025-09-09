package com.aliyun.iot.aep.sdk.framework.sdk;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.webkit.ProxyConfig;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.iot.aep.sdk.framework.config.AConfigure;
import com.aliyun.iot.aep.sdk.framework.config.SDKConfig;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.vivo.push.PushClientConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class SDKManager {

    /* renamed from: a, reason: collision with root package name */
    private static final ArrayList<SDKConfigure> f11826a = new ArrayList<>();

    /* renamed from: b, reason: collision with root package name */
    private static String f11827b = "";

    public static class InitResultHolder {

        /* renamed from: a, reason: collision with root package name */
        private static final HashMap<String, Result> f11828a = new HashMap<>();

        private static boolean a(String str) {
            return f11828a.containsKey(str);
        }

        public static void dump() {
            ALog.d("SDKManager", "\ndump: --- S ---");
            for (String str : f11828a.keySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(str);
                sb.append("]: ");
                HashMap<String, Result> map = f11828a;
                sb.append(map.get(str).f11829a);
                sb.append(", ");
                sb.append(map.get(str).f11830b);
                ALog.d("SDKManager", sb.toString());
            }
            ALog.d("SDKManager", "dump: --- E --- \n");
        }

        public static Map<String, Result> getDelegates() {
            return (Map) f11828a.clone();
        }

        public static int getInitResultCode(String str) {
            HashMap<String, Result> map = f11828a;
            Result result = map.containsKey(str) ? map.get(str) : null;
            if (result != null) {
                return result.f11830b;
            }
            return Integer.MAX_VALUE;
        }

        public static boolean isInitialized(String str) {
            Result result = a(str) ? f11828a.get(str) : null;
            return result != null && result.f11829a;
        }

        public static boolean updateResult(String str, Result result) {
            if (str == null || str.length() <= 0 || result == null) {
                return false;
            }
            f11828a.put(str, result);
            return true;
        }
    }

    public static class Result {

        /* renamed from: a, reason: collision with root package name */
        boolean f11829a = false;

        /* renamed from: b, reason: collision with root package name */
        int f11830b = Integer.MAX_VALUE;

        /* renamed from: c, reason: collision with root package name */
        String f11831c = null;

        /* renamed from: d, reason: collision with root package name */
        String f11832d = null;

        /* renamed from: e, reason: collision with root package name */
        String f11833e = null;
    }

    @VisibleForTesting
    public static void _initSdkDelegates(Application application, SDKConfigure sDKConfigure, List<ISDKDelegate> list) throws JSONException {
        if (list == null || list.isEmpty()) {
            ALog.w("SDKManager", "SKIP to init " + sDKConfigure.name + ": no delegates");
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ISDKDelegate iSDKDelegate = list.get(i2);
            try {
                String string = sDKConfigure.classFiles.getJSONObject(i2).getString(PushClientConstants.TAG_CLASS_NAME);
                if (InitResultHolder.isInitialized(string)) {
                    ALog.w("SDKManager", string + " shouldn't be initialized twice");
                } else {
                    int iInit = iSDKDelegate.init(application, sDKConfigure, AConfigure.getInstance().getConfig());
                    Result result = new Result();
                    result.f11829a = true;
                    result.f11830b = iInit;
                    result.f11831c = sDKConfigure.name;
                    result.f11832d = sDKConfigure.version;
                    result.f11833e = sDKConfigure.process;
                    InitResultHolder.updateResult(string, result);
                    ALog.d("SDKManager", "Successfully init: " + string);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                ALog.e("SDKManager", "FAILED to init " + sDKConfigure.name + "at delegates[" + i2 + "]");
            }
        }
    }

    @VisibleForTesting(otherwise = 3)
    public static SDKConfigure _parseToSDKConfigure(@NonNull JSONObject jSONObject) {
        ArrayList arrayList;
        String strOptString = jSONObject.optString("name");
        String strOptString2 = jSONObject.optString("version");
        String strOptString3 = jSONObject.optString(TmpConstant.SERVICE_DESC);
        String strOptString4 = jSONObject.optString("doc");
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("classFiles");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("opt");
        String strOptString5 = jSONObject.optString(UMModuleRegister.PROCESS);
        boolean zOptBoolean = jSONObject.optBoolean("needIoTToken");
        JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("submodules");
        if (jSONArrayOptJSONArray2 == null || jSONArrayOptJSONArray2.length() <= 0) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                try {
                    arrayList2.add(_parseToSDKConfigure(jSONArrayOptJSONArray2.getJSONObject(i2)));
                } catch (JSONException e2) {
                    ALog.e("SDKManager", "parse SDK configure", e2);
                    e2.printStackTrace();
                }
            }
            arrayList = arrayList2;
        }
        return new SDKConfigure(strOptString, strOptString2, strOptString3, strOptString4, jSONArrayOptJSONArray, zOptBoolean, jSONObjectOptJSONObject, strOptString5, arrayList);
    }

    @VisibleForTesting
    public static String _prepareSdkDelegateClassName(JSONObject jSONObject) {
        return jSONObject.getString(PushClientConstants.TAG_CLASS_NAME);
    }

    @VisibleForTesting
    public static List<ISDKDelegate> _prepareSdkDelegates(SDKConfigure sDKConfigure) throws ClassNotFoundException {
        if (sDKConfigure == null) {
            return null;
        }
        JSONArray jSONArray = sDKConfigure.classFiles;
        if (jSONArray == null || jSONArray.length() == 0) {
            ALog.w("SDKManager", "SKIP to prepare " + sDKConfigure.name + ": no class files");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < sDKConfigure.classFiles.length(); i2++) {
            try {
                String str_prepareSdkDelegateClassName = _prepareSdkDelegateClassName(sDKConfigure.classFiles.getJSONObject(i2));
                if (str_prepareSdkDelegateClassName != null) {
                    try {
                        Class<?> cls = Class.forName(str_prepareSdkDelegateClassName);
                        if (ISDKDelegate.class.isAssignableFrom(cls)) {
                            arrayList.add((ISDKDelegate) cls.newInstance());
                        }
                    } catch (Exception e2) {
                        ALog.e("SDKManager", "Failed to prepare " + sDKConfigure.name);
                        e2.printStackTrace();
                    }
                }
            } catch (JSONException e3) {
                ALog.e("SDKManager", "Failed to prepare " + sDKConfigure.name);
                e3.printStackTrace();
            }
        }
        return arrayList;
    }

    private static void a(Application application) throws JSONException {
        ArrayList<SDKConfigure> arrayList;
        ArrayList<SDKConfigure> arrayList2 = f11826a;
        if (arrayList2.isEmpty()) {
            ALog.d("SDKManager", "init sdks: configures is null or empty");
            return;
        }
        f11827b = application.getPackageName();
        String strA = a(application, Process.myPid());
        Iterator<SDKConfigure> it = arrayList2.iterator();
        while (it.hasNext()) {
            SDKConfigure next = it.next();
            if (a(strA, next)) {
                _initSdkDelegates(application, next, _prepareSdkDelegates(next));
            }
        }
        Iterator<SDKConfigure> it2 = f11826a.iterator();
        while (it2.hasNext()) {
            SDKConfigure next2 = it2.next();
            if (a(strA, next2) && (arrayList = next2.submodules) != null && !arrayList.isEmpty()) {
                for (int i2 = 0; i2 < next2.submodules.size(); i2++) {
                    SDKConfigure sDKConfigure = next2.submodules.get(i2);
                    _initSdkDelegates(application, sDKConfigure, _prepareSdkDelegates(sDKConfigure));
                }
            }
        }
    }

    private static void b(Application application) throws JSONException {
        if (application == null) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(SDKConfig.sdkConfig);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                ALog.d("SDKManager", jSONObject.toString());
                f11826a.add(_parseToSDKConfigure(jSONObject));
            }
        } catch (Exception e2) {
            ALog.e("SDKManager", "prepare-configure", e2);
            e2.printStackTrace();
        }
    }

    public static ArrayList<SDKConfigure> getSDKConfigures() {
        return (ArrayList) f11826a.clone();
    }

    public static void init(Application application) throws JSONException, IOException {
        AConfigure.getInstance().init(application);
        prepareForInitSdk(application);
        init_underUiThread(application);
    }

    public static void init_underUiThread(Application application) throws JSONException {
        a(application);
    }

    public static boolean isDeviceCenterAvailable() {
        return a("com.aliyun.alink.business.devicecenter.api.discovery.LocalDeviceMgr");
    }

    public static boolean isGatewayConnectAvailable() {
        return a("com.aliyun.alink.linksdk.channel.gateway.api.GatewayConnectConfig");
    }

    public static boolean isJsBridgeAvailable() {
        return a("com.aliyun.alink.sdk.jsbridge.methodexport.BaseBonePlugin");
    }

    public static boolean isOAAvailable() {
        return a("com.aliyun.iot.aep.sdk.login.LoginBusiness");
    }

    public static boolean isPushAvailable() {
        return a("com.aliyun.iot.push.PushManager");
    }

    public static boolean isRNAvailable() {
        return a("com.aliyun.alink.alirn.RNContainer");
    }

    public static boolean isRNLibAvailable() {
        return a("com.aliyun.alink.page.rn.RNActivity");
    }

    public static boolean isRouterExternalLibAvailable() {
        return a("com.aliyun.iot.aep.routerexternal.RouterBoneService");
    }

    public static boolean isTMPAvailable() {
        return a("com.aliyun.alink.linksdk.tmp.TmpSdk");
    }

    public static void prepareForInitSdk(Application application) throws JSONException {
        b(application);
    }

    private static String a(Context context, int i2) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == i2) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }

    private static boolean a(String str, SDKConfigure sDKConfigure) {
        if (str == null || str.isEmpty() || ProxyConfig.MATCH_ALL_SCHEMES.equals(sDKConfigure.process)) {
            return true;
        }
        if (TextUtils.isEmpty(sDKConfigure.process)) {
            return str.equals(f11827b);
        }
        for (String str2 : sDKConfigure.process.split(",")) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}

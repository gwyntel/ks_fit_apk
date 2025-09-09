package com.umeng.ccg;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.media3.exoplayer.ExoPlayer;
import com.umeng.analytics.pro.aq;
import com.umeng.analytics.pro.av;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.MLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class CcgAgent {
    private static Map<String, ArrayList<String>> forbidSdkTable;
    private static Object lock = new Object();
    private static Object configUpdateLock = new Object();
    private static ArrayList<ConfigListener> callbacks = new ArrayList<>();
    private static ArrayList<ConfigUpdateListener> updateCallbacks = new ArrayList<>();
    private static Object actionInfoLock = new Object();
    private static Map<String, ActionInfo> actionInfoTable = new HashMap();

    static {
        HashMap map = new HashMap();
        forbidSdkTable = map;
        map.put(a.f22003e, new ArrayList());
        forbidSdkTable.put(a.f22002d, new ArrayList<>());
        forbidSdkTable.put(a.f22000b, new ArrayList<>());
        forbidSdkTable.put(a.f22001c, new ArrayList<>());
    }

    public static ActionInfo getActionInfo(String str) {
        if (!TextUtils.isEmpty(str)) {
            synchronized (actionInfoLock) {
                try {
                    actionInfo = actionInfoTable.containsKey(str) ? actionInfoTable.get(str) : null;
                } finally {
                }
            }
        }
        return actionInfo;
    }

    public static String[] getCollectItemList() {
        return new String[]{a.f22003e, a.f22002d, a.f22000b, a.f22001c};
    }

    public static void getConfigInfo(ConfigResult configResult) {
        if (configResult != null) {
            configResult.onConfigInfo(d.a().b(UMGlobalContext.getAppContext()));
        }
    }

    public static ArrayList<String> getForbidSdkArray(String str) {
        if (forbidSdkTable.containsKey(str)) {
            return forbidSdkTable.get(str);
        }
        return null;
    }

    public static ArrayList<String> getRegistedModuleList() {
        ArrayList<String> arrayList;
        synchronized (actionInfoLock) {
            arrayList = new ArrayList<>(actionInfoTable.keySet());
        }
        return arrayList;
    }

    public static boolean hasRegistedActionInfo() {
        boolean z2;
        synchronized (actionInfoLock) {
            z2 = actionInfoTable.size() > 0;
        }
        return z2;
    }

    public static void init(Context context) {
        d.a().a(context);
    }

    public static void notifyConfigChanged(JSONObject jSONObject) {
        synchronized (configUpdateLock) {
            try {
                int size = updateCallbacks.size();
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        updateCallbacks.get(i2).onConfigUpdate(jSONObject);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void notifyConfigReady(JSONObject jSONObject) {
        synchronized (lock) {
            try {
                int size = callbacks.size();
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        callbacks.get(i2).onConfigReady(jSONObject);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void onActUpEvent(String str, String str2, Bundle bundle) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "onActUpEvent: type or token agument is empty string, pls check!");
            MLog.e("onActUpEvent: type、token参数不能为null或者空字符串！");
            return;
        }
        String string = "";
        if (bundle != null) {
            try {
                string = bundle.getString("ss");
            } catch (Throwable unused) {
                return;
            }
        }
        JSONObject jSONObjectA = d.a().a(str, str2, string);
        if (jSONObjectA != null) {
            av.a(new aq(aq.f21335b, jSONObjectA), 0L, TimeUnit.SECONDS);
            Thread.sleep(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }

    public static void registerActionInfo(ActionInfo actionInfo) {
        Context appContext = UMGlobalContext.getAppContext();
        if (actionInfo != null) {
            synchronized (actionInfoLock) {
                try {
                    String module = actionInfo.getModule(UMGlobalContext.getAppContext());
                    if (!TextUtils.isEmpty(module) && !actionInfoTable.containsKey(module)) {
                        String[] supportAction = actionInfo.getSupportAction(appContext);
                        if (supportAction != null) {
                            for (String str : supportAction) {
                                boolean switchState = actionInfo.getSwitchState(appContext, str);
                                if (forbidSdkTable.containsKey(str)) {
                                    ArrayList<String> arrayList = forbidSdkTable.get(str);
                                    if (!switchState) {
                                        arrayList.add(module);
                                    }
                                }
                            }
                        }
                        actionInfoTable.put(module, actionInfo);
                    }
                } catch (Throwable unused) {
                }
            }
        }
    }

    public static void registerConfigListener(ConfigListener configListener) {
        if (configListener != null) {
            synchronized (lock) {
                callbacks.add(configListener);
            }
        }
    }

    public static void registerConfigUpdateListener(ConfigUpdateListener configUpdateListener) {
        if (configUpdateListener != null) {
            synchronized (configUpdateLock) {
                updateCallbacks.add(configUpdateListener);
            }
        }
    }
}

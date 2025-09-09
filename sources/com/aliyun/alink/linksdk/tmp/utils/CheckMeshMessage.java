package com.aliyun.alink.linksdk.tmp.utils;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class CheckMeshMessage {
    private static final String TAG = "CheckMeshMessage";
    private static final long UPDATE_DEVICE_TIME = 70000;
    private static IPanelEventCallback iPanelEventCallback;
    private static final HashMap<String, Long> meshControlSeq = new HashMap<>();
    private static final Map<String, Long> scheduledFutureMap = new HashMap();

    public static boolean compareMeshPropertyValue(String str, JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "compareMeshPropertyValue() called with: iotId = [" + str + "], newPropertyValue = [" + jSONObject + "]");
        boolean z2 = false;
        if (jSONObject == null) {
            ALog.w(TAG, "comparePropertyValue newPropertyValue empty false");
            return false;
        }
        String cachedProps = DeviceShadowMgr.getInstance().getCachedProps(str);
        if (TextUtils.isEmpty(cachedProps)) {
            ALog.w(TAG, "comparePropertyValue oldPropertyValue empty true");
            return true;
        }
        JSONObject object = JSON.parseObject(cachedProps);
        if (object == null) {
            ALog.w(TAG, "comparePropertyValue oldPropertyValue empty true");
            return true;
        }
        for (String str2 : object.keySet()) {
            if (!"CloudSequence".equalsIgnoreCase(str2) && !"LocalSequence".equalsIgnoreCase(str2)) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str2);
                JSONObject jSONObject3 = object.getJSONObject(str2);
                if (jSONObject2 != null && jSONObject3 != null && jSONObject2.containsKey("value") && jSONObject3.containsKey("value")) {
                    String string = jSONObject2.getString("value");
                    String string2 = jSONObject3.getString("value");
                    if (TextUtils.isEmpty(string) || !string.equals(string2)) {
                        z2 = true;
                        break;
                    }
                }
            }
        }
        ALog.i("checkMessageEffectiveness", "comparePropertyValue isNeedUpdate:" + z2);
        return z2;
    }

    public static boolean containsMessage(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Long> map = scheduledFutureMap;
        synchronized (map) {
            try {
                if (map.containsKey(str)) {
                    Long l2 = map.get(str);
                    ALog.d(TAG, "containsMessage() called with: notifyIotId = [" + str + "[time ] = " + l2 + "[now]  =" + System.currentTimeMillis());
                    if (l2 != null) {
                        if (l2.longValue() - System.currentTimeMillis() > 0) {
                            return true;
                        }
                        ALog.d(TAG, "containsMessage() called with:  remove time notifyIotId = " + str);
                        map.remove(str);
                    }
                }
                ALog.d(TAG, "containsMessage() called with: notifyIotId = [" + str + "] return flase");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean messageEffectiveness(String str, JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject2;
        Long l2;
        ALog.d("checkMessage", "checkMessageEffectiveness() called with: data = [" + jSONObject.toJSONString() + "]");
        if (containsMessage(str)) {
            return true;
        }
        if (jSONObject.keySet().size() < 2) {
            ALog.d(TAG, "checkMessageEffectiveness() called  数据错误，不作处理");
            return false;
        }
        JSONObject jSONObject3 = jSONObject.containsKey("CloudSequence") ? jSONObject.getJSONObject("CloudSequence") : null;
        if (jSONObject.containsKey("LocalSequence")) {
            jSONObject3 = jSONObject.getJSONObject("LocalSequence");
        }
        if (jSONObject3 == null || (jSONObject2 = jSONObject3.getJSONObject("value")) == null || (l2 = jSONObject2.getLong("SeqNum")) == null) {
            return false;
        }
        HashMap<String, Long> map = meshControlSeq;
        Long l3 = map.get(str);
        if (l3 == null) {
            l3 = 0L;
        }
        boolean z2 = l2.longValue() > l3.longValue() || l2.longValue() < l3.longValue() - 1000;
        if (z2) {
            map.put(str, l2);
        }
        ALog.d(TAG, "messageEffectiveness() called with: iotId = [" + str + "]\n,  items = [" + jSONObject.toJSONString() + "]]\n, 是否处理 = [" + z2 + "]");
        return !z2;
    }

    public static void refreshAppDevice(String str, String str2) {
        refreshAppDevice(str, str2, null);
    }

    public static void removeMessage(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "removeMessage: disconnect device remove");
        Map<String, Long> map = scheduledFutureMap;
        synchronized (map) {
            map.remove(str);
        }
    }

    public static void sendMessage(String str, String str2, JSONObject jSONObject) {
        Log.d(TAG, "sendMessage() called with: iotId = [" + str + "], mqttTopicNotify = [" + str2 + "], paramsJson = [" + jSONObject + "]");
        IPanelEventCallback iPanelEventCallback2 = iPanelEventCallback;
        if (iPanelEventCallback2 != null) {
            iPanelEventCallback2.onNotify(str, str2, jSONObject);
        }
    }

    public static void setiPanelEventCallback(IPanelEventCallback iPanelEventCallback2) {
        iPanelEventCallback = iPanelEventCallback2;
    }

    public static void updateDeviceProperties(String str, String str2, IPanelEventCallback iPanelEventCallback2) {
    }

    public static void refreshAppDevice(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "refreshAppDevice() called with: mIotId = [" + str + "], params = [" + str2 + "], group = [" + str3 + "]");
        if (iPanelEventCallback != null) {
            try {
                JSONObject object = JSON.parseObject(str2);
                String props = DeviceShadowMgr.getInstance().getProps(str);
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                boolean z2 = !SwitchManager.hasSwitch(str2);
                jSONObject.put("iotId", object.getString("iotId"));
                if (TextUtils.isEmpty(props)) {
                    for (String str4 : object.getJSONObject("items").keySet()) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("value", object.getJSONObject("items").get(str4));
                        jSONObject2.put(str4, (Object) jSONObject3);
                    }
                } else {
                    JSONObject jSONObject4 = JSON.parseObject(props).getJSONObject("data");
                    for (String str5 : jSONObject4.keySet()) {
                        JSONObject jSONObject5 = new JSONObject();
                        if (object.getJSONObject("items").containsKey(str5)) {
                            jSONObject5.put("value", object.getJSONObject("items").get(str5));
                        } else {
                            jSONObject5.put("value", (Object) jSONObject4.getJSONObject(str5).getString("value"));
                            if (SwitchManager.hasSwitch(str5) && z2) {
                                jSONObject5.put("value", (Object) "1");
                            }
                        }
                        jSONObject2.put(str5, (Object) jSONObject5);
                    }
                }
                jSONObject.put("items", (Object) jSONObject2);
                jSONObject.put("group", Boolean.valueOf(!TextUtils.isEmpty(str3)));
                ALog.d(TAG, "refreshAppDevice: prop= " + jSONObject2.toJSONString());
                if (TextUtils.isEmpty(str3)) {
                    DeviceShadowMgr.getInstance().optimisticUpdateMeshDevice(str, jSONObject2);
                    scheduledFutureMap.put(str, Long.valueOf(System.currentTimeMillis() + UPDATE_DEVICE_TIME));
                    IPanelEventCallback iPanelEventCallback2 = iPanelEventCallback;
                    if (iPanelEventCallback2 != null) {
                        iPanelEventCallback2.onNotify(str, TmpConstant.MQTT_TOPIC_PROPERTIES, jSONObject);
                        return;
                    }
                    return;
                }
                List<String> meshGroupItem = TgMeshHelper.getMeshGroupItem(str3);
                if (meshGroupItem != null) {
                    for (String str6 : meshGroupItem) {
                        DeviceShadowMgr.getInstance().optimisticUpdateMeshDevice(str6, jSONObject2);
                        ALog.d(TAG, "refreshAppDevice: [ " + str6 + " ] this device was control by group , add 70000l time");
                        scheduledFutureMap.put(str6, Long.valueOf(System.currentTimeMillis() + UPDATE_DEVICE_TIME));
                    }
                }
            } catch (Exception e2) {
                ALog.d(TAG, "refreshAppDevice: " + e2.getLocalizedMessage());
            }
        }
    }
}

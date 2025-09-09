package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class SerializePropCallback implements IPanelEventCallback {
    private static final String TAG = "[Tmp]SerializePropCallback";
    protected IPanelEventCallback mListener;

    public SerializePropCallback(IPanelEventCallback iPanelEventCallback) {
        this.mListener = iPanelEventCallback;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelEventCallback
    public void onNotify(String str, String str2, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject;
        if (this.mListener == null) {
            ALog.e(TAG, "onNotify listener empty return");
            return;
        }
        if (obj == null || TextUtils.isEmpty(str2) || !str2.endsWith(TmpConstant.MQTT_TOPIC_PROPERTIES)) {
            this.mListener.onNotify(str, str2, obj);
            return;
        }
        JSONObject object = JSON.parseObject(obj.toString());
        JSONObject jSONObject2 = object.getJSONObject("items");
        String cachedProps = DeviceShadowMgr.getInstance().getCachedProps(str);
        boolean z2 = false;
        try {
            if (jSONObject2 != null) {
                JSONObject jSONObject3 = cachedProps == null ? new JSONObject() : JSON.parseObject(cachedProps);
                for (String str3 : jSONObject2.keySet()) {
                    JSONObject jSONObject4 = jSONObject2.getJSONObject(str3);
                    JSONObject jSONObject5 = jSONObject3.getJSONObject(str3);
                    if (DeviceShadowMgr.getInstance().comparePropertyValue(jSONObject4, jSONObject5)) {
                        StringBuilder sb = new StringBuilder();
                        jSONObject = jSONObject3;
                        sb.append("onNotify new prop is new, key:");
                        sb.append(str3);
                        sb.append(" newPropertyValue:");
                        sb.append(jSONObject4);
                        sb.append(" oldPropertyValue:");
                        sb.append(jSONObject5);
                        ALog.w(TAG, sb.toString());
                    } else {
                        jSONObject = jSONObject3;
                        ALog.w(TAG, "onNotify new prop is older, key:" + str3 + " newPropertyValue:" + jSONObject4 + " oldPropertyValue:" + jSONObject5);
                        jSONObject2.put(str3, (Object) jSONObject5);
                        z2 = true;
                    }
                    jSONObject3 = jSONObject;
                }
            }
            if (z2) {
                object.put("items", (Object) jSONObject2);
            }
        } catch (Exception e2) {
            ALog.e(TAG, "onNotify error:" + e2.toString());
        }
        this.mListener.onNotify(str, str2, object.toString());
    }
}

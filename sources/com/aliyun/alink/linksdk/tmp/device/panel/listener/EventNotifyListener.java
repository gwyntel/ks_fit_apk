package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class EventNotifyListener extends SubsListenerWrapper {
    private static final String TAG = "[Tmp]EventNotifyListener";

    public EventNotifyListener(String str, IPanelEventCallback iPanelEventCallback) {
        super(str, iPanelEventCallback);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.SubsListenerWrapper, com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onNotify(String str, String str2, AMessage aMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPanelEventCallback iPanelEventCallback = this.mSubListener;
        ALog.d(TAG, "onNotify mIotId:" + this.mIotId + " s:" + str + " s1:" + str2 + " panelEventCallback:" + iPanelEventCallback);
        if (iPanelEventCallback != null) {
            try {
                Object obj = aMessage.data;
                JSONObject jSONObject = JSON.parseObject(obj instanceof byte[] ? new String((byte[]) obj, "UTF-8") : obj.toString()).getJSONObject("params");
                String string = null;
                if (jSONObject.containsKey("value")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("value");
                    if (jSONObject2 != null && jSONObject2.containsKey("iotId")) {
                        string = jSONObject2.getString("iotId");
                    }
                } else if (jSONObject.containsKey("iotId")) {
                    string = jSONObject.getString("iotId");
                }
                if (string == null) {
                    iPanelEventCallback.onNotify(this.mIotId, str2, jSONObject.toString());
                } else if (string.equals(this.mIotId)) {
                    iPanelEventCallback.onNotify(this.mIotId, str2, jSONObject.toString());
                } else {
                    ALog.d(TAG, "onNotify mIotId is not same return");
                }
            } catch (Exception e2) {
                ALog.e(TAG, "onNotify e:" + e2.toString());
            }
        }
    }
}

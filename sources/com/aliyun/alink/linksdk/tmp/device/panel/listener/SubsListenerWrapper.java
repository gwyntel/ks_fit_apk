package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class SubsListenerWrapper implements IConnectNotifyListener {
    private static final String TAG = "SubsListenerWrapper[Tmp]";
    protected String mIotId;
    protected IPanelEventCallback mSubListener;

    public SubsListenerWrapper(String str, IPanelEventCallback iPanelEventCallback) {
        this.mSubListener = iPanelEventCallback;
        this.mIotId = str;
    }

    public String getmIotId() {
        return this.mIotId;
    }

    public IPanelEventCallback getmSubListener() {
        return this.mSubListener;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onConnectStateChange(String str, ConnectState connectState) {
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onNotify(String str, String str2, AMessage aMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPanelEventCallback iPanelEventCallback = this.mSubListener;
        ALog.d(TAG, "onNotify mIotId:" + this.mIotId + " s:" + str + " s1:" + str2 + " panelEventCallback:" + iPanelEventCallback);
        if (iPanelEventCallback != null) {
            String str3 = (String) aMessage.getCachedItem("params");
            String str4 = (String) aMessage.getCachedItem("iotId");
            try {
                if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && !TextUtils.isEmpty(this.mIotId) && this.mIotId.equalsIgnoreCase(str4)) {
                    iPanelEventCallback.onNotify(this.mIotId, str2, str3);
                    return;
                }
                Object obj = aMessage.data;
                JSONObject jSONObject = JSON.parseObject(obj instanceof byte[] ? new String((byte[]) obj, "UTF-8") : obj.toString()).getJSONObject("params");
                String string = jSONObject.getString("iotId");
                if (!TextUtils.isEmpty(string)) {
                    aMessage.putCachedItem("iotId", string);
                }
                aMessage.putCachedItem("params", jSONObject.toString());
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(this.mIotId) || this.mIotId.equalsIgnoreCase(string)) {
                    iPanelEventCallback.onNotify(this.mIotId, str2, jSONObject.toString());
                }
            } catch (Exception e2) {
                ALog.e(TAG, "onNotify e:" + e2.toString());
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public boolean shouldHandle(String str, String str2) {
        return true;
    }
}

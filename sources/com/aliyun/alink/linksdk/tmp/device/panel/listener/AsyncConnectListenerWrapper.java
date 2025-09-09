package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import android.text.TextUtils;
import androidx.media3.common.C;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.connect.entity.cmp.CmpNotifyManager;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AsyncConnectListenerWrapper extends ConnectListenerWrapper implements IConnectNotifyListener {
    private static final String TAG = "[Tmp]AsyncConnectListenerWrapper";
    public static final int TIME_OUT = 15000;
    private String mMessageId;
    private TimeOutRunnable mTimeoutRunnable;

    public static class TimeOutRunnable implements Runnable {
        private WeakReference<AsyncConnectListenerWrapper> mListener;

        public TimeOutRunnable(AsyncConnectListenerWrapper asyncConnectListenerWrapper) {
            this.mListener = new WeakReference<>(asyncConnectListenerWrapper);
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            AsyncConnectListenerWrapper asyncConnectListenerWrapper = this.mListener.get();
            if (asyncConnectListenerWrapper != null) {
                asyncConnectListenerWrapper.onTimeOut();
            }
        }
    }

    public AsyncConnectListenerWrapper(IPanelCallback iPanelCallback) {
        super(iPanelCallback);
        TimeOutRunnable timeOutRunnable = new TimeOutRunnable(this);
        this.mTimeoutRunnable = timeOutRunnable;
        TmpSdk.mHandler.postDelayed(timeOutRunnable, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
        CmpNotifyManager.getInstance().addHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_SERVICE_REPLY, this);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onConnectStateChange(String str, ConnectState connectState) {
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.ConnectListenerWrapper, com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
    public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        removeHandler();
        TmpSdk.mHandler.removeCallbacks(this.mTimeoutRunnable);
        super.onFailure(aRequest, aError);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onNotify(String str, String str2, AMessage aMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object obj = aMessage.data;
            String str3 = obj instanceof byte[] ? new String((byte[]) obj, "UTF-8") : obj.toString();
            ALog.d(TAG, "onNotify s:" + str + " s1:" + str2 + " mMessageId:" + this.mMessageId + " payloadStr:" + str3);
            JSONObject jSONObject = JSON.parseObject(str3).getJSONObject("params");
            String string = jSONObject.getString(TmpConstant.RRESPONSE_MESSAGEID);
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(this.mMessageId) || !this.mMessageId.equalsIgnoreCase(string)) {
                return;
            }
            removeHandler();
            TmpSdk.mHandler.removeCallbacks(this.mTimeoutRunnable);
            JSONObject jSONObject2 = jSONObject.getJSONObject("output").getJSONObject("value");
            CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
            commonResponsePayload.setCode(200);
            commonResponsePayload.setData(jSONObject2);
            AResponse aResponse = new AResponse();
            aResponse.data = JSON.toJSONString(commonResponsePayload);
            super.onResponse(null, aResponse);
        } catch (Exception e2) {
            ALog.e(TAG, "onNotify e:" + e2.toString());
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.panel.listener.ConnectListenerWrapper, com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
    public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj;
        if (aResponse == null || (obj = aResponse.data) == null) {
            ALog.e(TAG, "onResponse error");
            return;
        }
        try {
            this.mMessageId = JSON.parseObject(obj.toString()).getJSONObject("data").getString(TmpConstant.RRESPONSE_MESSAGEID);
        } catch (Exception e2) {
            ALog.e(TAG, "onResponse error : " + e2.toString());
        }
        ALog.d(TAG, "onResponse ignore callback wait notify mMessageId:" + this.mMessageId + " data:" + aResponse.data);
    }

    public void onTimeOut() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(TAG, "onTimeOut");
        AError aError = new AError();
        aError.setCode(300);
        aError.setMsg("timeout");
        onFailure(null, aError);
    }

    public void removeHandler() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        CmpNotifyManager.getInstance().removeHandler(hashCode(), ConnectSDK.getInstance().getPersistentConnectId(), TmpConstant.MQTT_TOPIC_SERVICE_REPLY);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public boolean shouldHandle(String str, String str2) {
        return true;
    }
}

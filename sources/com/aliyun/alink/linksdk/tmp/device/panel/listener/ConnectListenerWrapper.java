package com.aliyun.alink.linksdk.tmp.device.panel.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.service.TmpUTPointEx;
import com.aliyun.alink.linksdk.tmp.service.UTPoint;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ConnectListenerWrapper implements IConnectSendListener {
    protected static final String TAG = "[Tmp]ConnectListenerWrapper";
    protected IPanelCallback mCallback;
    protected TmpUTPointEx mTmpUTPointEx;
    protected UTPoint mUTPoint;

    public ConnectListenerWrapper(UTPoint uTPoint, TmpUTPointEx tmpUTPointEx, IPanelCallback iPanelCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mCallback = iPanelCallback;
        this.mUTPoint = uTPoint;
        if (uTPoint != null) {
            uTPoint.trackStart();
        }
        this.mTmpUTPointEx = tmpUTPointEx;
        if (tmpUTPointEx != null) {
            tmpUTPointEx.trackStart();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
    public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onFailure aError mCallback:" + this.mCallback);
        UTPoint uTPoint = this.mUTPoint;
        if (uTPoint != null) {
            uTPoint.trackEnd();
        }
        TmpUTPointEx tmpUTPointEx = this.mTmpUTPointEx;
        if (tmpUTPointEx != null) {
            tmpUTPointEx.trackEnd(aError == null ? -1 : aError.getCode());
        }
        IPanelCallback iPanelCallback = this.mCallback;
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(false, aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
    public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onResponse aResponse mCallback:" + this.mCallback);
        UTPoint uTPoint = this.mUTPoint;
        if (uTPoint != null) {
            uTPoint.trackEnd();
        }
        TmpUTPointEx tmpUTPointEx = this.mTmpUTPointEx;
        boolean z2 = false;
        if (tmpUTPointEx != null) {
            tmpUTPointEx.trackEnd(0);
        }
        if (aResponse != null) {
            try {
                Object obj = aResponse.data;
                if (obj != null) {
                    JSONObject object = JSON.parseObject(obj.toString());
                    if ((object != null ? object.getIntValue("code") : 0) == 200) {
                        z2 = true;
                    }
                }
            } catch (Exception e2) {
                ALog.d(TAG, "onResponse e:" + e2.toString());
            } catch (Throwable th) {
                ALog.d(TAG, "onResponse t:" + th.toString());
            }
        }
        IPanelCallback iPanelCallback = this.mCallback;
        if (iPanelCallback != null) {
            iPanelCallback.onComplete(z2, aResponse.data);
        }
    }

    public ConnectListenerWrapper(IPanelCallback iPanelCallback) {
        this(null, null, iPanelCallback);
    }
}

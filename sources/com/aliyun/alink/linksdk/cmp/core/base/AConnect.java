package com.aliyun.alink.linksdk.cmp.core.base;

import android.content.Context;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;

/* loaded from: classes2.dex */
public abstract class AConnect {
    protected String connectId = null;
    protected AConnectInfo connectInfo;
    protected AConnectOption connectOption;
    protected ConnectState connectState;
    protected IConnectNotifyListener notifyListener;

    public String getConnectId() {
        return this.connectId;
    }

    public AConnectInfo getConnectInfo() {
        return this.connectInfo;
    }

    public ConnectState getConnectState() {
        return this.connectState;
    }

    public abstract void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener);

    public abstract void onDestroy();

    public abstract void send(ARequest aRequest, IConnectSendListener iConnectSendListener);

    public void setConnectId(String str) {
        this.connectId = str;
    }

    public void setNotifyListener(IConnectNotifyListener iConnectNotifyListener) {
        this.notifyListener = iConnectNotifyListener;
    }

    public abstract void subscribe(ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener);

    @Deprecated
    public void subscribeRrpc(ARequest aRequest, IConnectRrpcListener iConnectRrpcListener) {
        if (iConnectRrpcListener != null) {
            iConnectRrpcListener.onSubscribeFailed(aRequest, CmpError.PARAMS_ERROR());
        }
    }

    public abstract void unsubscribe(ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener);

    public void updateConnectOption(AConnectOption aConnectOption) {
        this.connectOption = aConnectOption;
    }

    public void updateConnectState(ConnectState connectState) {
        if (this.connectState == connectState) {
            return;
        }
        this.connectState = connectState;
        IConnectNotifyListener iConnectNotifyListener = this.notifyListener;
        if (iConnectNotifyListener != null) {
            iConnectNotifyListener.onConnectStateChange(this.connectId, connectState);
        }
    }
}

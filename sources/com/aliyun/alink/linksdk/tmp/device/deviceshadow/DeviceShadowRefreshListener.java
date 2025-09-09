package com.aliyun.alink.linksdk.tmp.device.deviceshadow;

import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;

/* loaded from: classes2.dex */
public class DeviceShadowRefreshListener implements IProcessListener {
    protected IProcessListener mListener;
    protected int mType;

    public DeviceShadowRefreshListener(IProcessListener iProcessListener, int i2) {
        this.mListener = iProcessListener;
        this.mType = i2;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
    public void onFail(ErrorInfo errorInfo) {
        IProcessListener iProcessListener = this.mListener;
        if (iProcessListener != null) {
            iProcessListener.onFail(errorInfo);
            this.mListener = null;
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
    public void onSuccess(Object obj) {
        IProcessListener iProcessListener = this.mListener;
        if (iProcessListener != null) {
            iProcessListener.onSuccess(obj);
            this.mListener = null;
        }
    }
}

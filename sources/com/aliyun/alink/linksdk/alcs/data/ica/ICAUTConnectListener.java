package com.aliyun.alink.linksdk.alcs.data.ica;

import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;

/* loaded from: classes2.dex */
public class ICAUTConnectListener implements ICAConnectListener {
    private ICAConnectListener mICAConnectListener;
    private ICAUTPointEx mICAUTPointEx;

    public ICAUTConnectListener(ICAConnectListener iCAConnectListener, ICAUTPointEx iCAUTPointEx) {
        this.mICAConnectListener = iCAConnectListener;
        this.mICAUTPointEx = iCAUTPointEx;
        if (iCAUTPointEx != null) {
            iCAUTPointEx.trackStart();
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.api.ICAConnectListener
    public void onLoad(int i2, String str, ICADeviceInfo iCADeviceInfo) {
        ICAUTPointEx iCAUTPointEx = this.mICAUTPointEx;
        if (iCAUTPointEx != null) {
            if (i2 == 200) {
                iCAUTPointEx.trackEnd(0);
            } else {
                iCAUTPointEx.trackEnd(i2);
            }
        }
        ICAConnectListener iCAConnectListener = this.mICAConnectListener;
        if (iCAConnectListener != null) {
            iCAConnectListener.onLoad(i2, str, iCADeviceInfo);
        }
    }
}

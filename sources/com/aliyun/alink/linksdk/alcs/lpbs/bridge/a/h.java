package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class h implements ICAConnectListener {

    /* renamed from: b, reason: collision with root package name */
    private static final String f10819b = "[AlcsLPBS]ICAConnectListenerWrapper";

    /* renamed from: a, reason: collision with root package name */
    protected PalConnectListener f10820a;

    public h(PalConnectListener palConnectListener) {
        this.f10820a = palConnectListener;
    }

    @Override // com.aliyun.alink.linksdk.alcs.api.ICAConnectListener
    public void onLoad(int i2, String str, ICADeviceInfo iCADeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10819b, "onLoad errorCode:" + i2 + " deviceInfo:" + iCADeviceInfo);
        if (i2 == 200) {
            i2 = 0;
        }
        PalConnectListener palConnectListener = this.f10820a;
        if (palConnectListener != null) {
            palConnectListener.onLoad(i2, null, new PalDeviceInfo(iCADeviceInfo.productKey, iCADeviceInfo.deviceName));
        }
    }
}

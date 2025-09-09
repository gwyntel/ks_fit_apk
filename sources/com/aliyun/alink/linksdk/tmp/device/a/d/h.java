package com.aliyun.alink.linksdk.tmp.device.a.d;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.config.DefaultServerConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;

/* loaded from: classes2.dex */
public class h extends com.aliyun.alink.linksdk.tmp.device.a.d<h> {

    /* renamed from: n, reason: collision with root package name */
    protected static com.aliyun.alink.linksdk.tmp.resource.a f11320n;

    /* renamed from: o, reason: collision with root package name */
    protected static Object f11321o = new Object();

    public h(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(aVar);
        a(deviceBasicData);
        a(deviceConfig);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public synchronized boolean a() {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        try {
            synchronized (f11321o) {
                try {
                    com.aliyun.alink.linksdk.tmp.resource.a aVar2 = f11320n;
                    if (aVar2 == null) {
                        f11320n = new com.aliyun.alink.linksdk.tmp.resource.a(this.f11298m.getBasicData().getProductKey(), this.f11298m.getBasicData().getDeviceName(), aVar == null ? null : aVar.d());
                    } else {
                        aVar2.a(this.f11298m.getBasicData().getProductKey(), this.f11298m.getBasicData().getDeviceName(), aVar == null ? null : aVar.d());
                    }
                } finally {
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if ((DeviceConfig.DeviceType.SERVER == this.f11298m.getDeviceType() || DeviceConfig.DeviceType.PROVISION_RECEIVER == this.f11298m.getDeviceType()) && DefaultServerConfig.ConnectType.isConnectContainCoap(((DefaultServerConfig) this.f11298m).getConnectType())) {
            com.aliyun.alink.linksdk.tmp.connect.b bVarA = aVar.a();
            ALog.d("[Tmp]DeviceAsyncTask", "regRes METHOD_IDENTIFIER_DEV connect+:" + bVarA);
            if (bVarA != null) {
                aVar.a(bVarA.a(TmpEnum.ConnectType.CONNECT_TYPE_COAP), "dev", false, (ITResRequestHandler) f11320n);
            }
        }
        a((h) null, (Object) null);
        return false;
    }
}

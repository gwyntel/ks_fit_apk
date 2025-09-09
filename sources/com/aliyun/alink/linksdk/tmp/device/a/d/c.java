package com.aliyun.alink.linksdk.tmp.device.a.d;

import com.aliyun.alink.linksdk.cmp.manager.connect.IRegisterConnectListener;
import com.aliyun.alink.linksdk.tmp.config.DefaultServerConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c extends com.aliyun.alink.linksdk.tmp.device.a.d<c> {

    /* renamed from: n, reason: collision with root package name */
    protected static final String f11311n = "[Tmp]CreateMqttConnectTask";

    public c(DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(null, iDevListener);
        a(deviceConfig);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DeviceConfig deviceConfig = this.f11298m;
        if (deviceConfig == null || !(deviceConfig instanceof DefaultServerConfig)) {
            ALog.d(f11311n, "action mConfig null or not server return ");
            a((c) null, (Object) null);
            return true;
        }
        ALog.d(f11311n, "action mConfig");
        DefaultServerConfig defaultServerConfig = (DefaultServerConfig) this.f11298m;
        CloudUtils.registerPersistentConnect(defaultServerConfig.mIotProductKey, defaultServerConfig.mIotDeviceName, defaultServerConfig.mIotSecret, new IRegisterConnectListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.c.1
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onFailure(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(c.f11311n, "action onFailure aError:" + aError);
                c.this.a((c) null, (Object) null);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(c.f11311n, "action onSuccess");
                c.this.a((c) null, (Object) null);
            }
        });
        return true;
    }
}

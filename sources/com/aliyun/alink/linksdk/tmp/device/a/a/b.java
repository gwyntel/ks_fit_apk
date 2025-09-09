package com.aliyun.alink.linksdk.tmp.device.a.a;

import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b extends com.aliyun.alink.linksdk.tmp.device.a.d<b> {

    /* renamed from: n, reason: collision with root package name */
    protected String f11257n;

    public b(String str, IDevListener iDevListener) {
        super(null, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        DeviceShadowMgr.getInstance().updateDeviceNetTypesSupportedByPk(this.f11257n, true, new IProcessListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.a.b.1
            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
            public void onFail(ErrorInfo errorInfo) {
                b.this.a((b) null, (Object) null);
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IProcessListener
            public void onSuccess(Object obj) {
                b.this.a((b) null, (Object) null);
            }
        });
        return true;
    }
}

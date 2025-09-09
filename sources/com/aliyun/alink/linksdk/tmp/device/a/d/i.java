package com.aliyun.alink.linksdk.tmp.device.a.d;

import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.TDeviceShadow;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class i extends com.aliyun.alink.linksdk.tmp.device.a.d<g> implements IDevListener {

    /* renamed from: n, reason: collision with root package name */
    protected WeakReference<TDeviceShadow> f11322n;

    public i(TDeviceShadow tDeviceShadow, IDevListener iDevListener) {
        super(null, iDevListener);
        this.f11322n = new WeakReference<>(tDeviceShadow);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() {
        TDeviceShadow tDeviceShadow = this.f11322n.get();
        if (tDeviceShadow != null) {
            tDeviceShadow.init(this);
            return true;
        }
        onFail(null, new ErrorInfo(300, "param is invalid"));
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onFail(Object obj, ErrorInfo errorInfo) {
        b((i) null, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) {
        a((i) null, (Object) null);
    }
}

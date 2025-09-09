package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* loaded from: classes2.dex */
public class c implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10606a;

    public c(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10606a = softAPConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        if (this.f10606a.provisionHasStopped.get()) {
            return;
        }
        this.f10606a.getSofApProvisionTimeoutErrorInfo();
    }
}

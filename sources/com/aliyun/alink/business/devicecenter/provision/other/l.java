package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* loaded from: classes2.dex */
public class l implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10615a;

    public l(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10615a = softAPConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        if (this.f10615a.provisionHasStopped.get()) {
            return;
        }
        this.f10615a.getSofApProvisionTimeoutErrorInfo();
    }
}

package com.aliyun.alink.business.devicecenter.provision.other;

import com.aliyun.alink.business.devicecenter.biz.ProvisionRepository;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;

/* loaded from: classes2.dex */
public class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10609a;

    public f(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10609a = softAPConfigStrategy;
    }

    @Override // java.lang.Runnable
    public void run() {
        SoftAPConfigStrategy softAPConfigStrategy = this.f10609a;
        softAPConfigStrategy.cancelRequest(softAPConfigStrategy.retryTransitoryClient);
        SoftAPConfigStrategy softAPConfigStrategy2 = this.f10609a;
        softAPConfigStrategy2.retryTransitoryClient = ProvisionRepository.getCipher(softAPConfigStrategy2.mConfigParams.productKey, this.f10609a.mConfigParams.deviceName, this.f10609a.mRandom, null, new e(this));
    }
}

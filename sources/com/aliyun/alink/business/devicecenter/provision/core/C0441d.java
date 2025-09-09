package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigState;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.TimerUtils;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0441d implements TimerUtils.ITimerCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10570a;

    public C0441d(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10570a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.TimerUtils.ITimerCallback
    public void onTimeout() {
        if (this.f10570a.provisionHasStopped.get()) {
            return;
        }
        ALog.d(BreezeConfigStrategy.TAG, "breezeConfigState=" + this.f10570a.breezeConfigState + ", hasNotifiedScanTimeout=" + this.f10570a.hasNotifiedScanTimeout);
        if (this.f10570a.breezeConfigState != BreezeConfigState.BLE_SCANNING || this.f10570a.hasNotifiedScanTimeout.get()) {
            ALog.i(BreezeConfigStrategy.TAG, "startConfig scan->onTimeout breezeConfigState=" + this.f10570a.breezeConfigState);
            return;
        }
        this.f10570a.hasNotifiedScanTimeout.set(true);
        ALog.i(BreezeConfigStrategy.TAG, "startConfig scan->onTimeout scan target device > 10S.");
        ProvisionStatus provisionStatus = ProvisionStatus.BLE_DEVICE_SCAN_NO_RESULT;
        provisionStatus.setMessage("scan target ble device more than 10S, but no result.");
        this.f10570a.provisionStatusCallback(provisionStatus);
    }
}

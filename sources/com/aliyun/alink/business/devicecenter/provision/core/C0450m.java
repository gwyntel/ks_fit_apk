package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.IDataCallback;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.m, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0450m implements IDataCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f10579a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f10580b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10581c;

    public C0450m(BreezeConfigStrategy breezeConfigStrategy, int i2, String str) {
        this.f10581c = breezeConfigStrategy;
        this.f10579a = i2;
        this.f10580b = str;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDataCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResult(boolean z2, String str) {
        ALog.d(BreezeConfigStrategy.TAG, "upload dev wifi log onResult() called with: success = [" + z2 + "], data = [" + str + "]");
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IDataCallback
    public void onState(String str, String str2) {
        ALog.d(BreezeConfigStrategy.TAG, "onState() called with: key = [" + str + "], value = [" + str2 + "]");
        if (this.f10581c.provisionHasStopped.get()) {
            ALog.w(BreezeConfigStrategy.TAG, "uploadData2Oss provision has stopped.");
            return;
        }
        if ("devOssKey".equals(str) || "ignored".equals(str)) {
            this.f10581c.provisionErrorInfo = new DCErrorCode("ProvisionFailFromDevice", DCErrorCode.PF_PROVISION_FAIL_FROM_DEVICE).setSubcode(this.f10579a).setMsg(this.f10580b).setExtra(this.f10581c.getExtraErrorInfo());
            if ("devOssKey".equals(str)) {
                this.f10581c.devWiFiMFromOssObjectName = str2;
            } else {
                this.f10581c.devWiFiMFromOssObjectName = null;
            }
            this.f10581c.provisionResultCallback(null);
            this.f10581c.stopConfig();
        }
    }
}

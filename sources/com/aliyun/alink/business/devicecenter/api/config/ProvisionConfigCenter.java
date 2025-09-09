package com.aliyun.alink.business.devicecenter.api.config;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class ProvisionConfigCenter {

    /* renamed from: a, reason: collision with root package name */
    public ProvisionConfigParams f10065a;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final ProvisionConfigCenter f10066a = new ProvisionConfigCenter();
    }

    public static ProvisionConfigCenter getInstance() {
        return SingletonHolder.f10066a;
    }

    public boolean enableGlobalCloudToken() {
        ProvisionConfigParams provisionConfigParams = this.f10065a;
        return provisionConfigParams != null && provisionConfigParams.enableGlobalCloudToken;
    }

    public ProvisionConfigParams getProvisionConfigParams() {
        ProvisionConfigParams provisionConfigParams = this.f10065a;
        if (provisionConfigParams == null) {
            return null;
        }
        return ProvisionConfigParams.copy(provisionConfigParams);
    }

    public boolean handleBleSubType4Device() {
        ProvisionConfigParams provisionConfigParams = this.f10065a;
        return provisionConfigParams != null && provisionConfigParams.handleBleSubType4Device;
    }

    public boolean ignoreLocationPermissionCheck() {
        ProvisionConfigParams provisionConfigParams = this.f10065a;
        return provisionConfigParams != null && provisionConfigParams.ignoreLocationPermissionCheck;
    }

    public boolean ignoreSoftAPRecoverWiFi() {
        ProvisionConfigParams provisionConfigParams = this.f10065a;
        return provisionConfigParams != null && provisionConfigParams.ignoreSoftAPRecoverWiFi;
    }

    public void init(Context context) {
        if (context == null) {
            ALog.e("ProvisionConfigCenter", "init failed, context is null.");
        } else {
            DeviceCenterBiz.getInstance().setAppContext(context.getApplicationContext());
        }
    }

    public void setProvisionConfiguration(ProvisionConfigParams provisionConfigParams) {
        ALog.d("ProvisionConfigCenter", "setProvisionConfiguration() called with: provisionConfigParams = [" + provisionConfigParams + "]");
        if (provisionConfigParams == null) {
            throw new IllegalArgumentException("invalid method params, return.");
        }
        this.f10065a = ProvisionConfigParams.copy(provisionConfigParams);
    }

    public ProvisionConfigCenter() {
        this.f10065a = null;
    }
}

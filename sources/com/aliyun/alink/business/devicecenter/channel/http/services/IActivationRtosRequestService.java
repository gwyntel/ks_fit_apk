package com.aliyun.alink.business.devicecenter.channel.http.services;

import com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback;
import com.aliyun.alink.business.devicecenter.channel.http.model.request.QrCodeActivateRequest;
import com.aliyun.alink.business.devicecenter.channel.http.mtop.data.BindIotDeviceResult;

/* loaded from: classes2.dex */
public interface IActivationRtosRequestService extends IRequestService {
    public static final String SERVICE_NAME = "rtosBindRequestService";

    void qrCodeActivate(QrCodeActivateRequest qrCodeActivateRequest, IRequestCallback<BindIotDeviceResult> iRequestCallback);
}

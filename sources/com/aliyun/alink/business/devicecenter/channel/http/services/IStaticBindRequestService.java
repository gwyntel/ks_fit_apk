package com.aliyun.alink.business.devicecenter.channel.http.services;

import com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback;
import com.aliyun.alink.business.devicecenter.channel.http.model.request.QrCodeActivateRequest;

/* loaded from: classes2.dex */
public interface IStaticBindRequestService extends IRequestService {
    public static final String SERVICE_NAME = "staticBindRequestService";

    void qrCodeStaticBind(QrCodeActivateRequest qrCodeActivateRequest, IRequestCallback<Object> iRequestCallback);
}

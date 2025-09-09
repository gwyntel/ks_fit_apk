package com.aliyun.iot.aep.sdk.credential.IotCredentialManager;

import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;

/* loaded from: classes3.dex */
public interface IoTCredentialListener {
    void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError);

    void onRefreshIoTCredentialSuccess(IoTCredentialData ioTCredentialData);
}

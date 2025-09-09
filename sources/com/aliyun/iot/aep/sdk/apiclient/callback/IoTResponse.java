package com.aliyun.iot.aep.sdk.apiclient.callback;

/* loaded from: classes3.dex */
public interface IoTResponse {
    int getCode();

    Object getData();

    String getId();

    String getLocalizedMsg();

    String getMessage();

    byte[] getRawData();
}

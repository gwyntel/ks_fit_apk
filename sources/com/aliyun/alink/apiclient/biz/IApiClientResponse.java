package com.aliyun.alink.apiclient.biz;

/* loaded from: classes2.dex */
public interface IApiClientResponse {
    int getCode();

    Object getData();

    String getId();

    String getLocalizedMsg();

    String getMessage();

    byte[] getRawData();
}

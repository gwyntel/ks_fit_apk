package com.aliyun.alink.linksdk.alcs.lpbs.component.cloud;

/* loaded from: classes2.dex */
public interface ILpbsCloudProxyListener {
    public static final int ERROR_CODE_FAILD_UNKNOWN = 1;
    public static final int ERROR_CODE_SUCCESS = 0;

    void onComplete(int i2, Object obj);
}

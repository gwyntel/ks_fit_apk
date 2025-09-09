package com.aliyun.alink.linksdk.tmp.device.payload.cloud;

import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;

/* loaded from: classes2.dex */
public class EncryptGroupAuthInfo {
    public String encryptAccessKey;
    public String encryptAccessToken;
    public String encryptGroupKeyPrefix;
    public String encryptGroupSecret;

    public String toString() {
        return GsonUtils.toJson(this);
    }
}

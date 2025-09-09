package com.aliyun.alink.linksdk.tmp.device.payload.cloud;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;

/* loaded from: classes2.dex */
public class ResponsePayload extends AResponse {
    public int code;
    public int id;

    public ResponsePayload(int i2, int i3) {
        this.id = i2;
        this.code = i3;
    }
}

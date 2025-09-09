package com.aliyun.alink.linksdk.alcs.coap;

/* loaded from: classes2.dex */
public interface IAlcsCoAPReqHandler {
    public static final int SUCCESS = 0;

    void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse);
}

package com.aliyun.alink.business.devicecenter.channel.coap.listener;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;

/* loaded from: classes2.dex */
public interface IAlcsCoAPSend {
    boolean cancelMessage(long j2);

    long sendRequest(AlcsCoAPRequest alcsCoAPRequest, IAlcsCoAPReqHandler iAlcsCoAPReqHandler);

    boolean sendResponse(AlcsCoAPResponse alcsCoAPResponse);
}

package com.aliyun.linksdk.alcs;

import com.aliyun.alink.linksdk.alcs.api.client.AlcsClientConfig;
import com.aliyun.alink.linksdk.alcs.api.client.IDeviceHandler;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;

/* loaded from: classes3.dex */
public interface IAlcsClient {
    void destroy();

    String getDstAddr();

    void init(AlcsClientConfig alcsClientConfig, IDeviceHandler iDeviceHandler);

    boolean isServerOnline();

    boolean sendRequest(boolean z2, PalReqMessage palReqMessage, PalMsgListener palMsgListener);

    boolean sendResponse(boolean z2, AlcsCoAPResponse alcsCoAPResponse);

    void setNotifyListener(IClientNotify iClientNotify);

    void subscribe(boolean z2, PalSubMessage palSubMessage, PalMsgListener palMsgListener);

    void unsubscribe(boolean z2, PalSubMessage palSubMessage, PalMsgListener palMsgListener);
}

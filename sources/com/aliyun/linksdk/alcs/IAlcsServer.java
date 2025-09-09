package com.aliyun.linksdk.alcs;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.resources.AlcsCoAPResource;

/* loaded from: classes3.dex */
public interface IAlcsServer {
    void addSvrAccessKey(String str, String str2);

    boolean publishResoucre(String str, Object obj);

    boolean registerAllResource(boolean z2, AlcsCoAPResource alcsCoAPResource);

    void removeSvrKey(String str);

    boolean sendResponse(boolean z2, AlcsCoAPResponse alcsCoAPResponse);

    void setServerMessageDeliver(IServerMessageDeliver iServerMessageDeliver);

    void startServer();

    void stopServer();

    void unRegisterResource(String str);

    void updateBlackList(String str);

    void updateSvrPrefix(String str);
}

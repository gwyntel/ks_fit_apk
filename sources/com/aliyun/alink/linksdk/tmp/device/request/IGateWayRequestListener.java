package com.aliyun.alink.linksdk.tmp.device.request;

import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public interface IGateWayRequestListener<Request, Response> {
    void onFail(Request request, AError aError);

    void onSuccess(Request request, Response response);
}

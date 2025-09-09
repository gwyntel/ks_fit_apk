package com.aliyun.alink.apiclient.biz;

import com.aliyun.alink.apiclient.CommonPoPRequest;
import com.aliyun.alink.apiclient.CommonRequest;

/* loaded from: classes2.dex */
public class RequestHandlerFactory {
    public IHandler createHandler(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        return "/auth/register/device".equals(commonRequest.getPath()) ? new GetDeviceTriadRequestHAndler() : commonRequest instanceof CommonPoPRequest ? new PoPRequestHandler() : new IoTRequestHandler();
    }
}

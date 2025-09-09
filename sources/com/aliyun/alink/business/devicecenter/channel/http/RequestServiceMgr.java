package com.aliyun.alink.business.devicecenter.channel.http;

import com.aliyun.alink.business.devicecenter.channel.http.services.IRequestService;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class RequestServiceMgr {
    public static final String ACTIVATION_REQUEST_SERVICE = "activationRequestService";
    public static final String ACTIVATION_RTOS_REQUEST_SERVICE = "rtosBindRequestService";
    public static final String STATIC_BIND_REQUEST_SERVICE = "staticBindRequestService";

    /* renamed from: a, reason: collision with root package name */
    public final Map<String, IRequestService> f10309a;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final RequestServiceMgr f10310a = new RequestServiceMgr();
    }

    public static RequestServiceMgr getInstance() {
        return SingletonHolder.f10310a;
    }

    public IRequestService getRequestService(String str) {
        if (StringUtils.isEmpty(str)) {
            ALog.w("RequestServiceMgr", "requestServiceName: " + str);
            throw new IllegalArgumentException("requestServiceName or requestService is invalid");
        }
        if (this.f10309a.containsKey(str)) {
            return this.f10309a.get(str);
        }
        ALog.w("RequestServiceMgr", "requestServiceName: " + str + " not exist.");
        return null;
    }

    public void registerRequestService(String str, IRequestService iRequestService) {
        if (!StringUtils.isEmpty(str) && iRequestService != null) {
            if (this.f10309a.containsKey(str) && this.f10309a.get(str) != null) {
                ALog.w("RequestServiceMgr", String.format("requestServiceName: %s has exist. %s is replaced by %s", str, this.f10309a.get(str).getClass().getName(), iRequestService.getClass().getName()));
            }
            this.f10309a.put(str, iRequestService);
            return;
        }
        ALog.w("RequestServiceMgr", "requestServiceName: " + str + " requestService: " + iRequestService);
        throw new IllegalArgumentException("requestServiceName or requestService is invalid");
    }

    public RequestServiceMgr() {
        this.f10309a = new ConcurrentHashMap();
    }
}

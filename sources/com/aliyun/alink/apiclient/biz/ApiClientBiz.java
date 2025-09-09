package com.aliyun.alink.apiclient.biz;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes2.dex */
public class ApiClientBiz implements IApiClientBiz {
    private static final String TAG = "ApiClientBiz";
    private IApiClientBiz mApiClientBizImpl;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final ApiClientBiz f9968a = new ApiClientBiz();
    }

    public static ApiClientBiz getInstance() {
        return a.f9968a;
    }

    public void setApiClientBiz(IApiClientBiz iApiClientBiz) {
        if (iApiClientBiz == null) {
            throw new IllegalArgumentException("clientBiz cannot be null.");
        }
        this.mApiClientBizImpl = iApiClientBiz;
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientBiz
    public void usageTrack(String str, String str2, String str3, Map map, IApiClientBizCallback iApiClientBizCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "usageTrack() called with: type = [" + str + "], sdkName = [" + str2 + "], event = [" + str3 + "], extraParams = [" + map + "], clientBizCallback = [" + iApiClientBizCallback + "]");
        IApiClientBiz iApiClientBiz = this.mApiClientBizImpl;
        if (iApiClientBiz != null) {
            iApiClientBiz.usageTrack(str, str2, str3, map, iApiClientBizCallback);
        }
    }

    private ApiClientBiz() {
        this.mApiClientBizImpl = null;
        this.mApiClientBizImpl = new com.aliyun.alink.apiclient.biz.a.a();
    }
}

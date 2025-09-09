package com.aliyun.alink.apiclient.biz.a;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.aliyun.alink.apiclient.biz.IApiClientBiz;
import com.aliyun.alink.apiclient.biz.IApiClientBizCallback;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class a implements IApiClientBiz {

    /* renamed from: a, reason: collision with root package name */
    private String f9969a = null;

    public String a(IoTRequest ioTRequest) {
        if (ioTRequest == null) {
            return null;
        }
        return "[schema=" + ioTRequest.getScheme() + ",host=" + ioTRequest.getHost() + ",path=" + ioTRequest.getPath() + ",apiVersion=" + ioTRequest.getAPIVersion() + ",method=" + ioTRequest.getMethod() + ",authType=" + ioTRequest.getAuthType() + ",params=" + ioTRequest.getParams() + "]";
    }

    @Override // com.aliyun.alink.apiclient.biz.IApiClientBiz
    public void usageTrack(String str, String str2, String str3, Map map, final IApiClientBizCallback iApiClientBizCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IoTAPIClient client = new IoTAPIClientFactory().getClient();
        boolean z2 = client instanceof IoTAPIClientImpl;
        if (z2 && !((IoTAPIClientImpl) client).hasInited()) {
            ALog.e("ApiClientBizImpl", "api client has not been inited.");
            if (iApiClientBizCallback != null) {
                iApiClientBizCallback.onFail(new IllegalStateException("ApiClientNotInit"));
                return;
            }
            return;
        }
        HashMap map2 = new HashMap();
        if (TextUtils.isEmpty(str)) {
            map2.put("type", "appSdkTrack");
        } else {
            map2.put("type", str);
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("sdkName cannot be empty.");
        }
        if (TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("event cannot be empty.");
        }
        map2.put("sdk", str2);
        map2.put(NotificationCompat.CATEGORY_EVENT, str3);
        map2.put("system", AnalyticsConstants.SDK_TYPE);
        map2.put("systemVer", com.aliyun.alink.apiclient.biz.b.a.b());
        if (TextUtils.isEmpty(this.f9969a)) {
            this.f9969a = com.aliyun.alink.apiclient.biz.b.a.a(z2 ? ((IoTAPIClientImpl) client).getAppContext() : null);
        }
        map2.put("phoneDescriptor", this.f9969a);
        if (map != null) {
            map2.putAll(map);
        }
        client.send(new IoTRequestBuilder().setApiVersion("1.0.0").setPath("/living/client/data/report/get").setScheme(Scheme.HTTPS).addParam("record", (Map) map2).build(), new IoTCallback() { // from class: com.aliyun.alink.apiclient.biz.a.a.1
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.w("ApiClientBizImpl", "onFailure() called with: ioTRequest = [" + a.this.a(ioTRequest) + "], e = [" + exc + "]");
                IApiClientBizCallback iApiClientBizCallback2 = iApiClientBizCallback;
                if (iApiClientBizCallback2 != null) {
                    iApiClientBizCallback2.onFail(exc);
                }
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                b bVar;
                ALog.d("ApiClientBizImpl", "onResponse() called with: ioTRequest = [" + a.this.a(ioTRequest) + "], ioTResponse = [" + a.this.a(ioTResponse) + "]");
                if (iApiClientBizCallback != null) {
                    if (ioTResponse != null) {
                        bVar = new b();
                        bVar.a(ioTResponse.getCode());
                        bVar.a(ioTResponse.getId());
                        bVar.b(ioTResponse.getMessage());
                        bVar.c(ioTResponse.getLocalizedMsg());
                        bVar.a(ioTResponse.getData());
                        bVar.a(ioTResponse.getRawData());
                    } else {
                        bVar = null;
                    }
                    iApiClientBizCallback.onResponse(bVar);
                }
            }
        });
    }

    public String a(IoTResponse ioTResponse) {
        if (ioTResponse == null) {
            return null;
        }
        return "[requestId=" + ioTResponse.getId() + ",code=" + ioTResponse.getCode() + ",message=" + ioTResponse.getMessage() + ",localizedMsg=" + ioTResponse.getLocalizedMsg() + ",data=" + ioTResponse.getData() + "]";
    }
}

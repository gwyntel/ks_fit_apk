package com.aliyun.alink.linksdk.tmp.connect.mix;

import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.connectsdk.BaseApiRequest;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class MTopAndApiGMixRequest {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11200a = "API_VERSION";

    /* renamed from: b, reason: collision with root package name */
    private static final String f11201b = "API_PATH";

    /* renamed from: c, reason: collision with root package name */
    private static final String f11202c = "API_SCHEME";

    /* renamed from: d, reason: collision with root package name */
    private static final String f11203d = "API_AUTH_TYPE";

    /* renamed from: e, reason: collision with root package name */
    private static final String f11204e = "API_HOST";

    /* renamed from: f, reason: collision with root package name */
    private ApiGatewayRequest f11205f;

    public static class MixApiRequest extends BaseApiRequest {
        private ApiGatewayRequest mApiGatewayRequest;

        public MixApiRequest(ApiGatewayRequest apiGatewayRequest) {
            this.mApiGatewayRequest = apiGatewayRequest;
        }

        @Override // com.aliyun.alink.linksdk.connectsdk.BaseApiRequest
        public Map<String, Object> objectToMap() {
            if (this.mApiGatewayRequest == null) {
                return null;
            }
            HashMap map = new HashMap();
            map.put(MTopAndApiGMixRequest.f11200a, this.mApiGatewayRequest.apiVersion);
            map.put(MTopAndApiGMixRequest.f11201b, this.mApiGatewayRequest.path);
            map.put(MTopAndApiGMixRequest.f11202c, Scheme.HTTP.equals(this.mApiGatewayRequest.scheme) ? "HTTP" : "HTTPS");
            map.put(MTopAndApiGMixRequest.f11203d, this.mApiGatewayRequest.authType);
            map.put(MTopAndApiGMixRequest.f11204e, this.mApiGatewayRequest.host);
            map.putAll(this.mApiGatewayRequest.params);
            return map;
        }
    }

    public MTopAndApiGMixRequest(ApiGatewayRequest apiGatewayRequest) {
        this.f11205f = apiGatewayRequest;
    }

    public BaseApiRequest a() {
        return new MixApiRequest(this.f11205f);
    }

    public ARequest b() {
        return this.f11205f;
    }
}

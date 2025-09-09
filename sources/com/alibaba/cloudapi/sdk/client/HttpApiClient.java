package com.alibaba.cloudapi.sdk.client;

import com.alibaba.cloudapi.sdk.enums.HttpConnectionModel;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;
import com.alibaba.cloudapi.sdk.signature.SignerFactoryManager;
import com.alibaba.cloudapi.sdk.util.ApiRequestMaker;
import com.alibaba.cloudapi.sdk.util.HttpCommonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class HttpApiClient extends BaseApiClient {
    OkHttpClient client;

    protected HttpApiClient() {
    }

    private Request buildRequest(ApiRequest apiRequest) {
        if (apiRequest.getHttpConnectionMode() == HttpConnectionModel.SINGER_CONNECTION) {
            apiRequest.setHost(this.host);
            apiRequest.setScheme(this.scheme);
        }
        ApiRequestMaker.make(apiRequest, this.appKey, this.appSecret);
        return new Request.Builder().method(apiRequest.getMethod().getValue(), (apiRequest.getFormParams() == null || apiRequest.getFormParams().size() <= 0) ? (apiRequest.getBody() == null || apiRequest.getBody().length <= 0) ? null : RequestBody.create(MediaType.parse(apiRequest.getMethod().getRequestContentType()), apiRequest.getBody()) : RequestBody.create(MediaType.parse(apiRequest.getMethod().getRequestContentType()), HttpCommonUtil.buildParamString(apiRequest.getFormParams()))).url(apiRequest.getUrl()).headers(getHeadersFromMap(apiRequest.getHeaders())).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ApiResponse getApiResponse(ApiRequest apiRequest, Response response) throws IOException {
        ApiResponse apiResponse = new ApiResponse(response.code());
        apiResponse.setHeaders(response.headers().toMultimap());
        apiResponse.setBody(response.body().bytes());
        apiResponse.setContentType(response.header("content-type", ""));
        return apiResponse;
    }

    private Headers getHeadersFromMap(Map<String, List<String>> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String str : entry.getValue()) {
                arrayList.add(entry.getKey());
                arrayList.add(str);
            }
        }
        return Headers.of((String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    private Map<String, String> getSimpleMapFromRequest(Headers headers) {
        Map<String, List<String>> multimap = headers.toMultimap();
        HashMap map = new HashMap();
        for (Map.Entry<String, List<String>> entry : multimap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
        return map;
    }

    public void init(HttpClientBuilderParams httpClientBuilderParams) {
        if (httpClientBuilderParams == null) {
            throw new SdkException("buildParam must not be null");
        }
        httpClientBuilderParams.check();
        this.appKey = httpClientBuilderParams.getAppKey();
        this.appSecret = httpClientBuilderParams.getAppSecret();
        this.host = httpClientBuilderParams.getHost();
        this.scheme = httpClientBuilderParams.getScheme();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        long readTimeout = httpClientBuilderParams.getReadTimeout();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        OkHttpClient.Builder builderConnectTimeout = builder.readTimeout(readTimeout, timeUnit).writeTimeout(httpClientBuilderParams.getWriteTimeout(), timeUnit).connectTimeout(httpClientBuilderParams.getConnectionTimeout(), timeUnit);
        if (httpClientBuilderParams.getEventListenerFactory() != null) {
            builderConnectTimeout.eventListenerFactory(httpClientBuilderParams.getEventListenerFactory());
        }
        if (httpClientBuilderParams.getSocketFactory() != null) {
            builderConnectTimeout.socketFactory(httpClientBuilderParams.getSocketFactory());
        }
        builderConnectTimeout.retryOnConnectionFailure(httpClientBuilderParams.isHttpConnectionRetry());
        if (httpClientBuilderParams.getInterceptor() != null) {
            builderConnectTimeout.interceptors().add(httpClientBuilderParams.getInterceptor());
        }
        if (this.scheme == Scheme.HTTPS) {
            builderConnectTimeout.sslSocketFactory(httpClientBuilderParams.getSslSocketFactory(), httpClientBuilderParams.getX509TrustManager()).hostnameVerifier(httpClientBuilderParams.getHostnameVerifier());
        }
        this.client = builderConnectTimeout.build();
        SignerFactoryManager.init();
        this.isInit = true;
    }

    @Override // com.alibaba.cloudapi.sdk.client.BaseApiClient
    protected void sendAsyncRequest(final ApiRequest apiRequest, final ApiCallback apiCallback) {
        checkIsInit();
        this.client.newCall(buildRequest(apiRequest)).enqueue(new Callback() { // from class: com.alibaba.cloudapi.sdk.client.HttpApiClient.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                apiCallback.onFailure(apiRequest, iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                ApiCallback apiCallback2 = apiCallback;
                ApiRequest apiRequest2 = apiRequest;
                apiCallback2.onResponse(apiRequest2, HttpApiClient.this.getApiResponse(apiRequest2, response));
            }
        });
    }

    @Override // com.alibaba.cloudapi.sdk.client.BaseApiClient
    protected ApiResponse sendSyncRequest(ApiRequest apiRequest) {
        checkIsInit();
        try {
            return getApiResponse(apiRequest, this.client.newCall(buildRequest(apiRequest)).execute());
        } catch (IOException e2) {
            return new ApiResponse(500, "Read response occur error", e2);
        }
    }
}

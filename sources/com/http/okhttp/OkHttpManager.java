package com.http.okhttp;

import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.http.helper.HttpCallback;
import com.http.helper.HttpFailCode;
import com.http.helper.HttpUtils;
import com.http.helper.OkHttpInterceptor;
import com.http.utils.LogUtils;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class OkHttpManager implements IHttpClient {
    private static final String DEFAULT_ENCODEING = "UTF-8";
    private static final String TAG = "OkHttpManager";
    private static final MediaType JSON = MediaType.parse(HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON);
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse(OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE);
    private static final MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    private static OkHttpClient defaultOkHttpClient = null;

    private static class SingletonHolder {
        private static final OkHttpManager INSTANCE = new OkHttpManager();

        private SingletonHolder() {
        }
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OkHttpInterceptor.RetryInterceptor(3));
        builder.addInterceptor(new OkHttpInterceptor.ExceptionInterceptor());
        builder.addInterceptor(new OkHttpInterceptor.HttpHeaderInterceptor());
        return builder.build();
    }

    public static OkHttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.http.okhttp.IHttpClient
    public void getAsync(final String str, Map<String, String> map, final HttpCallback<IOException, String> httpCallback) throws IOException {
        if (HttpUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url=null");
        }
        LogUtils.print(TAG, "getAsync url=" + str + ",headers=" + map);
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        if (map != null && map.size() > 0) {
            builder.headers(Headers.of(map));
        }
        getOkHttpClient().newCall(builder.build()).enqueue(new Callback() { // from class: com.http.okhttp.OkHttpManager.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                LogUtils.print(OkHttpManager.TAG, "getAsync onFailure e=" + iOException);
                HttpCallback httpCallback2 = httpCallback;
                if (httpCallback2 != null) {
                    httpCallback2.onFail(call.request().url().toString(), iOException);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.print(OkHttpManager.TAG, "getAsync onResponse call=" + call + ",response=" + response);
                try {
                } catch (IOException e2) {
                    LogUtils.print(OkHttpManager.TAG, "getAsync onResponse e=" + e2);
                }
                if (response == null) {
                    LogUtils.print(OkHttpManager.TAG, "getAsync response=null.");
                    HttpCallback httpCallback2 = httpCallback;
                    if (httpCallback2 != null) {
                        httpCallback2.onFail(str, new IOException("getAsync Reponse Null", new HttpFailCode(-1)));
                        return;
                    }
                    return;
                }
                if (!response.isSuccessful()) {
                    LogUtils.print(OkHttpManager.TAG, "getAsync response is fail.");
                    HttpCallback httpCallback3 = httpCallback;
                    if (httpCallback3 != null) {
                        httpCallback3.onFail(str, new IOException(response.body().string(), new HttpFailCode(response.code())));
                        return;
                    }
                    return;
                }
                if (response.body() != null) {
                    String strString = response.body().string();
                    LogUtils.print(OkHttpManager.TAG, "getAsync result=" + strString);
                    HttpCallback httpCallback4 = httpCallback;
                    if (httpCallback4 != null) {
                        httpCallback4.onSuccess(str, strString);
                        return;
                    }
                    return;
                }
                LogUtils.print(OkHttpManager.TAG, "getAsync onResponse error data.");
                HttpCallback httpCallback5 = httpCallback;
                if (httpCallback5 != null) {
                    httpCallback5.onFail(call.request().url().toString(), new IOException("ResponseDataError"));
                }
            }
        });
    }

    public OkHttpClient getOkHttpClient() {
        return defaultOkHttpClient;
    }

    @Override // com.http.okhttp.IHttpClient
    public String getSync(String str, Map<String, String> map) throws IOException {
        if (HttpUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url=null");
        }
        LogUtils.print(TAG, " getSync url=" + str + ",headers=" + map);
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        if (map != null && map.size() > 0) {
            builder.headers(Headers.of(map));
        }
        Response responseExecute = getOkHttpClient().newCall(builder.build()).execute();
        if (!responseExecute.isSuccessful()) {
            throw new IOException(responseExecute.body().string(), new HttpFailCode(responseExecute.code()));
        }
        String strString = responseExecute.body().string();
        LogUtils.print(TAG, "getSync result=" + strString);
        return strString;
    }

    @Override // com.http.okhttp.IHttpClient
    public void postAsync(final String str, Map<String, String> map, String str2, final HttpCallback<IOException, String> httpCallback) throws IOException {
        if (HttpUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url=null");
        }
        LogUtils.print("postAsync url=" + str + ", headersMap=" + map + ", postData=" + str2);
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        MediaType mediaType = MEDIA_TYPE_NORAML_FORM;
        if (map != null && map.size() > 0) {
            builder.headers(Headers.of(map));
            if (map.get("Content-Type") != null && map.get("Content-Type").contains("application/json")) {
                mediaType = JSON;
            } else if (map.get("Content-Type") != null && map.get("Content-Type").contains(OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE)) {
                mediaType = MEDIA_TYPE_STREAM;
            }
        }
        if (str2 != null) {
            builder.post(RequestBody.create(mediaType, str2));
        }
        getOkHttpClient().newCall(builder.build()).enqueue(new Callback() { // from class: com.http.okhttp.OkHttpManager.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                LogUtils.print(OkHttpManager.TAG, "postAsync onFailure e=" + iOException);
                HttpCallback httpCallback2 = httpCallback;
                if (httpCallback2 != null) {
                    httpCallback2.onFail(call.request().url().toString(), iOException);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.print(OkHttpManager.TAG, "postAsync onResponse call=" + call + ",response=" + response);
                try {
                } catch (IOException e2) {
                    LogUtils.print(OkHttpManager.TAG, "postAsync onResponse e=" + e2);
                }
                if (response == null) {
                    LogUtils.print(OkHttpManager.TAG, "postAsync response=null.");
                    HttpCallback httpCallback2 = httpCallback;
                    if (httpCallback2 != null) {
                        httpCallback2.onFail(str, new IOException("postAsync Reponse Null", new HttpFailCode(-1)));
                        return;
                    }
                    return;
                }
                if (!response.isSuccessful()) {
                    LogUtils.print(OkHttpManager.TAG, "postAsync response is fail.");
                    HttpCallback httpCallback3 = httpCallback;
                    if (httpCallback3 != null) {
                        httpCallback3.onFail(str, new IOException(response.body().string(), new HttpFailCode(response.code())));
                        return;
                    }
                    return;
                }
                if (response.body() != null) {
                    String strString = response.body().string();
                    LogUtils.print(OkHttpManager.TAG, "postAsync url=" + call.request().url().toString());
                    LogUtils.print(OkHttpManager.TAG, "postAsync result=" + strString);
                    HttpCallback httpCallback4 = httpCallback;
                    if (httpCallback4 != null) {
                        httpCallback4.onSuccess(str, strString);
                        return;
                    }
                    return;
                }
                LogUtils.print(OkHttpManager.TAG, "postAsync onResponse error data.");
                HttpCallback httpCallback5 = httpCallback;
                if (httpCallback5 != null) {
                    httpCallback5.onFail(call.request().url().toString(), new IOException("ResponseDataError", new HttpFailCode(-1)));
                }
            }
        });
    }

    @Override // com.http.okhttp.IHttpClient
    public String postSync(String str, Map<String, String> map, String str2) throws IOException {
        if (HttpUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url=null");
        }
        LogUtils.print("postSync url=" + str + ", headersMap=" + map + ", postData=" + str2);
        Request.Builder builder = new Request.Builder();
        builder.url(str);
        MediaType mediaType = MEDIA_TYPE_NORAML_FORM;
        if (map != null && map.size() > 0) {
            builder.headers(Headers.of(map));
            if (map.get("Content-Type") != null && map.get("Content-Type").contains("application/json")) {
                mediaType = JSON;
            } else if (map.get("Content-Type") != null && map.get("Content-Type").contains(OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE)) {
                mediaType = MEDIA_TYPE_STREAM;
            }
        }
        if (str2 != null) {
            builder.post(RequestBody.create(mediaType, str2));
        }
        Response responseExecute = getOkHttpClient().newCall(builder.build()).execute();
        if (!responseExecute.isSuccessful()) {
            throw new IOException(responseExecute.body().string(), new HttpFailCode(responseExecute.code()));
        }
        String strString = responseExecute.body().string();
        LogUtils.print(TAG, "postSync result=" + strString);
        return strString;
    }

    private OkHttpManager() {
        defaultOkHttpClient = createOkHttpClient();
    }
}

package com.alibaba.sdk.android.oss.network;

import java.io.IOException;
import java.io.InputStream;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class NetworkProgressHelper {
    public static ProgressTouchableRequestBody addProgressRequestBody(InputStream inputStream, long j2, String str, ExecutionContext executionContext) {
        return new ProgressTouchableRequestBody(inputStream, j2, str, executionContext);
    }

    public static OkHttpClient addProgressResponseListener(OkHttpClient okHttpClient, final ExecutionContext executionContext) {
        return okHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() { // from class: com.alibaba.sdk.android.oss.network.NetworkProgressHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Response responseProceed = chain.proceed(chain.request());
                return responseProceed.newBuilder().body(new ProgressTouchableResponseBody(responseProceed.body(), executionContext)).build();
            }
        }).build();
    }
}

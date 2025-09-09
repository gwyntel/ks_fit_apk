package com.http.helper;

import com.http.utils.LogUtils;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class OkHttpInterceptor {

    public static class ExceptionInterceptor implements Interceptor {
        private static final String TAG = "ExceptionInterceptor";

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            try {
                return chain.proceed(chain.request());
            } catch (SecurityException e2) {
                LogUtils.print(TAG, "OkHttpException " + e2);
                throw new IOException(e2);
            } catch (Exception e3) {
                LogUtils.print(TAG, "OkHttpException " + e3);
                throw new IOException(e3);
            }
        }
    }

    public static class HttpHeaderInterceptor implements Interceptor {
        private static final String TAG = "ExceptionInterceptor";

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            try {
                chain.request().newBuilder().headers(Headers.of(HttpUtils.getDefaultRequestHeader())).build();
                return chain.proceed(chain.request());
            } catch (SecurityException e2) {
                LogUtils.print(TAG, "OkHttpException " + e2);
                throw new IOException(e2);
            } catch (Exception e3) {
                LogUtils.print(TAG, "OkHttpException " + e3);
                throw new IOException(e3);
            }
        }
    }

    public static class RetryInterceptor implements Interceptor {
        private static final String TAG = "RetryInterceptor";
        private int maxCount;
        private int retryCount = 0;

        public RetryInterceptor(int i2) {
            this.maxCount = i2;
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            int i2;
            Request request = chain.request();
            Response responseProceed = chain.proceed(request);
            while (!responseProceed.isSuccessful() && (i2 = this.retryCount) < this.maxCount) {
                this.retryCount = i2 + 1;
                LogUtils.print(TAG, "retry request: " + request.url().toString() + " count: " + this.retryCount);
                responseProceed = chain.proceed(request);
            }
            return responseProceed;
        }
    }
}

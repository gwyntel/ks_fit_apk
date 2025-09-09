package okhttp3.internal.cache;

import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes5.dex */
public final class CacheInterceptor implements Interceptor {

    /* renamed from: a, reason: collision with root package name */
    final InternalCache f26255a;

    public CacheInterceptor(InternalCache internalCache) {
        this.f26255a = internalCache;
    }

    static boolean a(String str) {
        return "Content-Length".equalsIgnoreCase(str) || "Content-Encoding".equalsIgnoreCase(str) || "Content-Type".equalsIgnoreCase(str);
    }

    static boolean b(String str) {
        return ("Connection".equalsIgnoreCase(str) || HttpHeaders.KEEP_ALIVE.equalsIgnoreCase(str) || "Proxy-Authenticate".equalsIgnoreCase(str) || HttpHeaders.PROXY_AUTHORIZATION.equalsIgnoreCase(str) || HttpHeaders.TE.equalsIgnoreCase(str) || "Trailers".equalsIgnoreCase(str) || HttpHeaders.TRANSFER_ENCODING.equalsIgnoreCase(str) || HttpHeaders.UPGRADE.equalsIgnoreCase(str)) ? false : true;
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        Sink sinkBody;
        if (cacheRequest == null || (sinkBody = cacheRequest.body()) == null) {
            return response;
        }
        final BufferedSource bufferedSourceSource = response.body().source();
        final BufferedSink bufferedSinkBuffer = Okio.buffer(sinkBody);
        return response.newBuilder().body(new RealResponseBody(response.header("Content-Type"), response.body().contentLength(), Okio.buffer(new Source() { // from class: okhttp3.internal.cache.CacheInterceptor.1

            /* renamed from: a, reason: collision with root package name */
            boolean f26256a;

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                if (!this.f26256a && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.f26256a = true;
                    cacheRequest.abort();
                }
                bufferedSourceSource.close();
            }

            @Override // okio.Source
            public long read(Buffer buffer, long j2) throws IOException {
                try {
                    long j3 = bufferedSourceSource.read(buffer, j2);
                    if (j3 != -1) {
                        buffer.copyTo(bufferedSinkBuffer.buffer(), buffer.size() - j3, j3);
                        bufferedSinkBuffer.emitCompleteSegments();
                        return j3;
                    }
                    if (!this.f26256a) {
                        this.f26256a = true;
                        bufferedSinkBuffer.close();
                    }
                    return -1L;
                } catch (IOException e2) {
                    if (!this.f26256a) {
                        this.f26256a = true;
                        cacheRequest.abort();
                    }
                    throw e2;
                }
            }

            @Override // okio.Source
            public Timeout timeout() {
                return bufferedSourceSource.timeout();
            }
        }))).build();
    }

    private static Headers combine(Headers headers, Headers headers2) {
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            String strName = headers.name(i2);
            String strValue = headers.value(i2);
            if ((!HttpHeaders.WARNING.equalsIgnoreCase(strName) || !strValue.startsWith("1")) && (a(strName) || !b(strName) || headers2.get(strName) == null)) {
                Internal.instance.addLenient(builder, strName, strValue);
            }
        }
        int size2 = headers2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            String strName2 = headers2.name(i3);
            if (!a(strName2) && b(strName2)) {
                Internal.instance.addLenient(builder, strName2, headers2.value(i3));
            }
        }
        return builder.build();
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        InternalCache internalCache = this.f26255a;
        Response response = internalCache != null ? internalCache.get(chain.request()) : null;
        CacheStrategy cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), chain.request(), response).get();
        Request request = cacheStrategy.networkRequest;
        Response response2 = cacheStrategy.cacheResponse;
        InternalCache internalCache2 = this.f26255a;
        if (internalCache2 != null) {
            internalCache2.trackResponse(cacheStrategy);
        }
        if (response != null && response2 == null) {
            Util.closeQuietly(response.body());
        }
        if (request == null && response2 == null) {
            return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
        }
        if (request == null) {
            return response2.newBuilder().cacheResponse(stripBody(response2)).build();
        }
        try {
            Response responseProceed = chain.proceed(request);
            if (responseProceed == null && response != null) {
            }
            if (response2 != null) {
                if (responseProceed.code() == 304) {
                    Response responseBuild = response2.newBuilder().headers(combine(response2.headers(), responseProceed.headers())).sentRequestAtMillis(responseProceed.sentRequestAtMillis()).receivedResponseAtMillis(responseProceed.receivedResponseAtMillis()).cacheResponse(stripBody(response2)).networkResponse(stripBody(responseProceed)).build();
                    responseProceed.body().close();
                    this.f26255a.trackConditionalCacheHit();
                    this.f26255a.update(response2, responseBuild);
                    return responseBuild;
                }
                Util.closeQuietly(response2.body());
            }
            Response responseBuild2 = responseProceed.newBuilder().cacheResponse(stripBody(response2)).networkResponse(stripBody(responseProceed)).build();
            if (this.f26255a != null) {
                if (okhttp3.internal.http.HttpHeaders.hasBody(responseBuild2) && CacheStrategy.isCacheable(responseBuild2, request)) {
                    return cacheWritingResponse(this.f26255a.put(responseBuild2), responseBuild2);
                }
                if (HttpMethod.invalidatesCache(request.method())) {
                    try {
                        this.f26255a.remove(request);
                    } catch (IOException unused) {
                    }
                }
            }
            return responseBuild2;
        } finally {
            if (response != null) {
                Util.closeQuietly(response.body());
            }
        }
    }
}

package com.aliyun.iot.aep.sdk.framework.log;

import anet.channel.util.HttpConstant;
import java.io.EOFException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

/* loaded from: classes3.dex */
public final class HttpLoggingInterceptor implements Interceptor {

    /* renamed from: a, reason: collision with root package name */
    private static final Charset f11778a = Charset.forName("UTF-8");

    /* renamed from: b, reason: collision with root package name */
    private final Logger f11779b;

    /* renamed from: c, reason: collision with root package name */
    private volatile Set<String> f11780c;

    /* renamed from: d, reason: collision with root package name */
    private volatile Level f11781d;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        public static final Logger DEFAULT = new Logger() { // from class: com.aliyun.iot.aep.sdk.framework.log.HttpLoggingInterceptor.Logger.1
            @Override // com.aliyun.iot.aep.sdk.framework.log.HttpLoggingInterceptor.Logger
            public void log(String str) {
                Platform.get().log(4, str, null);
            }
        };

        void log(String str);
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    private void a(Headers headers, int i2) {
        String strValue = this.f11780c.contains(headers.name(i2)) ? "██" : headers.value(i2);
        this.f11779b.log(headers.name(i2) + ": " + strValue);
    }

    public Level getLevel() {
        return this.f11781d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v20, types: [java.lang.Long] */
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        long j2;
        char c2;
        String string;
        GzipSource gzipSource;
        Level level = this.f11781d;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean z2 = level == Level.BODY;
        boolean z3 = z2 || level == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        boolean z4 = requestBodyBody != null;
        Connection connection = chain.connection();
        StringBuilder sb = new StringBuilder();
        sb.append("--> ");
        sb.append(request.method());
        sb.append(' ');
        sb.append(request.url());
        sb.append(connection != null ? " " + connection.protocol() : "");
        String string2 = sb.toString();
        if (!z3 && z4) {
            string2 = string2 + " (" + requestBodyBody.contentLength() + "-byte body)";
        }
        this.f11779b.log(string2);
        if (z3) {
            if (z4) {
                if (requestBodyBody.contentType() != null) {
                    this.f11779b.log("Content-Type: " + requestBodyBody.contentType());
                }
                if (requestBodyBody.contentLength() != -1) {
                    this.f11779b.log("Content-Length: " + requestBodyBody.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i2 = 0; i2 < size; i2++) {
                String strName = headers.name(i2);
                if (!"Content-Type".equalsIgnoreCase(strName) && !"Content-Length".equalsIgnoreCase(strName)) {
                    a(headers, i2);
                }
            }
            if (!z2 || !z4) {
                this.f11779b.log("--> END " + request.method());
            } else if (a(request.headers())) {
                this.f11779b.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBodyBody.writeTo(buffer);
                Charset charset = f11778a;
                MediaType mediaTypeContentType = requestBodyBody.contentType();
                if (mediaTypeContentType != null) {
                    charset = mediaTypeContentType.charset(charset);
                }
                this.f11779b.log("");
                if (a(buffer)) {
                    if (charset != null) {
                        this.f11779b.log(buffer.readString(charset));
                    }
                    this.f11779b.log("--> END " + request.method() + " (" + requestBodyBody.contentLength() + "-byte body)");
                } else {
                    this.f11779b.log("--> END " + request.method() + " (binary " + requestBodyBody.contentLength() + "-byte body omitted)");
                }
            }
        }
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
            ResponseBody responseBodyBody = responseProceed.body();
            long jContentLength = responseBodyBody.contentLength();
            String str = jContentLength != -1 ? jContentLength + "-byte" : "unknown-length";
            Logger logger = this.f11779b;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("<-- ");
            sb2.append(responseProceed.code());
            if (responseProceed.message().isEmpty()) {
                string = "";
                j2 = jContentLength;
                c2 = ' ';
            } else {
                StringBuilder sb3 = new StringBuilder();
                j2 = jContentLength;
                c2 = ' ';
                sb3.append(' ');
                sb3.append(responseProceed.message());
                string = sb3.toString();
            }
            sb2.append(string);
            sb2.append(c2);
            sb2.append(responseProceed.request().url());
            sb2.append(" (");
            sb2.append(millis);
            sb2.append("ms");
            sb2.append(z3 ? "" : ", " + str + " body");
            sb2.append(')');
            logger.log(sb2.toString());
            if (z3) {
                Headers headers2 = responseProceed.headers();
                int size2 = headers2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    a(headers2, i3);
                }
                if (!z2 || !HttpHeaders.hasBody(responseProceed)) {
                    this.f11779b.log("<-- END HTTP");
                } else if (a(responseProceed.headers())) {
                    this.f11779b.log("<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource bufferedSourceSource = responseBodyBody.source();
                    bufferedSourceSource.request(Long.MAX_VALUE);
                    Buffer buffer2 = bufferedSourceSource.buffer();
                    GzipSource gzipSource2 = null;
                    if (HttpConstant.GZIP.equalsIgnoreCase(headers2.get("Content-Encoding"))) {
                        ?? ValueOf = Long.valueOf(buffer2.size());
                        try {
                            gzipSource = new GzipSource(buffer2.clone());
                        } catch (Throwable th) {
                            th = th;
                        }
                        try {
                            buffer2 = new Buffer();
                            buffer2.writeAll(gzipSource);
                            gzipSource.close();
                            gzipSource2 = ValueOf;
                        } catch (Throwable th2) {
                            th = th2;
                            gzipSource2 = gzipSource;
                            if (gzipSource2 != null) {
                                gzipSource2.close();
                            }
                            throw th;
                        }
                    }
                    Charset charset2 = f11778a;
                    MediaType mediaTypeContentType2 = responseBodyBody.contentType();
                    if (mediaTypeContentType2 != null) {
                        charset2 = mediaTypeContentType2.charset(charset2);
                    }
                    if (!a(buffer2)) {
                        this.f11779b.log("");
                        this.f11779b.log("<-- END HTTP (binary " + buffer2.size() + "-byte body omitted)");
                        return responseProceed;
                    }
                    if (j2 != 0) {
                        this.f11779b.log("");
                        this.f11779b.log(buffer2.clone().readString(charset2));
                    }
                    if (gzipSource2 != null) {
                        this.f11779b.log("<-- END HTTP (" + buffer2.size() + "-byte, " + gzipSource2 + "-gzipped-byte body)");
                    } else {
                        this.f11779b.log("<-- END HTTP (" + buffer2.size() + "-byte body)");
                    }
                }
            }
            return responseProceed;
        } catch (Exception e2) {
            this.f11779b.log("<-- HTTP FAILED: " + e2);
            throw e2;
        }
    }

    public void redactHeader(String str) {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        treeSet.addAll(this.f11780c);
        treeSet.add(str);
        this.f11780c = treeSet;
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.f11781d = level;
        return this;
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.f11780c = Collections.emptySet();
        this.f11781d = Level.NONE;
        this.f11779b = logger;
    }

    static boolean a(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i2 = 0; i2 < 16; i2++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int utf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(utf8CodePoint) && !Character.isWhitespace(utf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private static boolean a(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity") || str.equalsIgnoreCase(HttpConstant.GZIP)) ? false : true;
    }
}

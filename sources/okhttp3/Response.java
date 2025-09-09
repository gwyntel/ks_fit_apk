package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/* loaded from: classes5.dex */
public final class Response implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    final Request f26222a;

    /* renamed from: b, reason: collision with root package name */
    final Protocol f26223b;

    /* renamed from: c, reason: collision with root package name */
    final int f26224c;

    @Nullable
    private volatile CacheControl cacheControl;

    /* renamed from: d, reason: collision with root package name */
    final String f26225d;

    /* renamed from: e, reason: collision with root package name */
    final Handshake f26226e;

    /* renamed from: f, reason: collision with root package name */
    final Headers f26227f;

    /* renamed from: g, reason: collision with root package name */
    final ResponseBody f26228g;

    /* renamed from: h, reason: collision with root package name */
    final Response f26229h;

    /* renamed from: i, reason: collision with root package name */
    final Response f26230i;

    /* renamed from: j, reason: collision with root package name */
    final Response f26231j;

    /* renamed from: k, reason: collision with root package name */
    final long f26232k;

    /* renamed from: l, reason: collision with root package name */
    final long f26233l;

    Response(Builder builder) {
        this.f26222a = builder.f26234a;
        this.f26223b = builder.f26235b;
        this.f26224c = builder.f26236c;
        this.f26225d = builder.f26237d;
        this.f26226e = builder.f26238e;
        this.f26227f = builder.f26239f.build();
        this.f26228g = builder.f26240g;
        this.f26229h = builder.f26241h;
        this.f26230i = builder.f26242i;
        this.f26231j = builder.f26243j;
        this.f26232k = builder.f26244k;
        this.f26233l = builder.f26245l;
    }

    @Nullable
    public ResponseBody body() {
        return this.f26228g;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl cacheControl2 = CacheControl.parse(this.f26227f);
        this.cacheControl = cacheControl2;
        return cacheControl2;
    }

    @Nullable
    public Response cacheResponse() {
        return this.f26230i;
    }

    public List<Challenge> challenges() {
        String str;
        int i2 = this.f26224c;
        if (i2 == 401) {
            str = "WWW-Authenticate";
        } else {
            if (i2 != 407) {
                return Collections.emptyList();
            }
            str = "Proxy-Authenticate";
        }
        return HttpHeaders.parseChallenges(headers(), str);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        ResponseBody responseBody = this.f26228g;
        if (responseBody == null) {
            throw new IllegalStateException("response is not eligible for a body and must not be closed");
        }
        responseBody.close();
    }

    public int code() {
        return this.f26224c;
    }

    @Nullable
    public Handshake handshake() {
        return this.f26226e;
    }

    @Nullable
    public String header(String str) {
        return header(str, null);
    }

    public List<String> headers(String str) {
        return this.f26227f.values(str);
    }

    public boolean isRedirect() {
        int i2 = this.f26224c;
        if (i2 == 307 || i2 == 308) {
            return true;
        }
        switch (i2) {
            case 300:
            case 301:
            case 302:
            case 303:
                return true;
            default:
                return false;
        }
    }

    public boolean isSuccessful() {
        int i2 = this.f26224c;
        return i2 >= 200 && i2 < 300;
    }

    public String message() {
        return this.f26225d;
    }

    @Nullable
    public Response networkResponse() {
        return this.f26229h;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public ResponseBody peekBody(long j2) throws IOException {
        BufferedSource bufferedSourceSource = this.f26228g.source();
        bufferedSourceSource.request(j2);
        Buffer bufferClone = bufferedSourceSource.buffer().clone();
        if (bufferClone.size() > j2) {
            Buffer buffer = new Buffer();
            buffer.write(bufferClone, j2);
            bufferClone.clear();
            bufferClone = buffer;
        }
        return ResponseBody.create(this.f26228g.contentType(), bufferClone.size(), bufferClone);
    }

    @Nullable
    public Response priorResponse() {
        return this.f26231j;
    }

    public Protocol protocol() {
        return this.f26223b;
    }

    public long receivedResponseAtMillis() {
        return this.f26233l;
    }

    public Request request() {
        return this.f26222a;
    }

    public long sentRequestAtMillis() {
        return this.f26232k;
    }

    public String toString() {
        return "Response{protocol=" + this.f26223b + ", code=" + this.f26224c + ", message=" + this.f26225d + ", url=" + this.f26222a.url() + '}';
    }

    @Nullable
    public String header(String str, @Nullable String str2) {
        String str3 = this.f26227f.get(str);
        return str3 != null ? str3 : str2;
    }

    public Headers headers() {
        return this.f26227f;
    }

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        Request f26234a;

        /* renamed from: b, reason: collision with root package name */
        Protocol f26235b;

        /* renamed from: c, reason: collision with root package name */
        int f26236c;

        /* renamed from: d, reason: collision with root package name */
        String f26237d;

        /* renamed from: e, reason: collision with root package name */
        Handshake f26238e;

        /* renamed from: f, reason: collision with root package name */
        Headers.Builder f26239f;

        /* renamed from: g, reason: collision with root package name */
        ResponseBody f26240g;

        /* renamed from: h, reason: collision with root package name */
        Response f26241h;

        /* renamed from: i, reason: collision with root package name */
        Response f26242i;

        /* renamed from: j, reason: collision with root package name */
        Response f26243j;

        /* renamed from: k, reason: collision with root package name */
        long f26244k;

        /* renamed from: l, reason: collision with root package name */
        long f26245l;

        public Builder() {
            this.f26236c = -1;
            this.f26239f = new Headers.Builder();
        }

        private void checkPriorResponse(Response response) {
            if (response.f26228g != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        private void checkSupportResponse(String str, Response response) {
            if (response.f26228g != null) {
                throw new IllegalArgumentException(str + ".body != null");
            }
            if (response.f26229h != null) {
                throw new IllegalArgumentException(str + ".networkResponse != null");
            }
            if (response.f26230i != null) {
                throw new IllegalArgumentException(str + ".cacheResponse != null");
            }
            if (response.f26231j == null) {
                return;
            }
            throw new IllegalArgumentException(str + ".priorResponse != null");
        }

        public Builder addHeader(String str, String str2) {
            this.f26239f.add(str, str2);
            return this;
        }

        public Builder body(@Nullable ResponseBody responseBody) {
            this.f26240g = responseBody;
            return this;
        }

        public Response build() {
            if (this.f26234a == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.f26235b == null) {
                throw new IllegalStateException("protocol == null");
            }
            if (this.f26236c >= 0) {
                if (this.f26237d != null) {
                    return new Response(this);
                }
                throw new IllegalStateException("message == null");
            }
            throw new IllegalStateException("code < 0: " + this.f26236c);
        }

        public Builder cacheResponse(@Nullable Response response) {
            if (response != null) {
                checkSupportResponse("cacheResponse", response);
            }
            this.f26242i = response;
            return this;
        }

        public Builder code(int i2) {
            this.f26236c = i2;
            return this;
        }

        public Builder handshake(@Nullable Handshake handshake) {
            this.f26238e = handshake;
            return this;
        }

        public Builder header(String str, String str2) {
            this.f26239f.set(str, str2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.f26239f = headers.newBuilder();
            return this;
        }

        public Builder message(String str) {
            this.f26237d = str;
            return this;
        }

        public Builder networkResponse(@Nullable Response response) {
            if (response != null) {
                checkSupportResponse("networkResponse", response);
            }
            this.f26241h = response;
            return this;
        }

        public Builder priorResponse(@Nullable Response response) {
            if (response != null) {
                checkPriorResponse(response);
            }
            this.f26243j = response;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.f26235b = protocol;
            return this;
        }

        public Builder receivedResponseAtMillis(long j2) {
            this.f26245l = j2;
            return this;
        }

        public Builder removeHeader(String str) {
            this.f26239f.removeAll(str);
            return this;
        }

        public Builder request(Request request) {
            this.f26234a = request;
            return this;
        }

        public Builder sentRequestAtMillis(long j2) {
            this.f26244k = j2;
            return this;
        }

        Builder(Response response) {
            this.f26236c = -1;
            this.f26234a = response.f26222a;
            this.f26235b = response.f26223b;
            this.f26236c = response.f26224c;
            this.f26237d = response.f26225d;
            this.f26238e = response.f26226e;
            this.f26239f = response.f26227f.newBuilder();
            this.f26240g = response.f26228g;
            this.f26241h = response.f26229h;
            this.f26242i = response.f26230i;
            this.f26243j = response.f26231j;
            this.f26244k = response.f26232k;
            this.f26245l = response.f26233l;
        }
    }
}

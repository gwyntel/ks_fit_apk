package okhttp3;

import anet.channel.request.Request;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

/* loaded from: classes5.dex */
public final class Request {

    /* renamed from: a, reason: collision with root package name */
    final HttpUrl f26204a;

    /* renamed from: b, reason: collision with root package name */
    final String f26205b;

    /* renamed from: c, reason: collision with root package name */
    final Headers f26206c;

    @Nullable
    private volatile CacheControl cacheControl;

    /* renamed from: d, reason: collision with root package name */
    final RequestBody f26207d;

    /* renamed from: e, reason: collision with root package name */
    final Map f26208e;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        HttpUrl f26209a;

        /* renamed from: b, reason: collision with root package name */
        String f26210b;

        /* renamed from: c, reason: collision with root package name */
        Headers.Builder f26211c;

        /* renamed from: d, reason: collision with root package name */
        RequestBody f26212d;

        /* renamed from: e, reason: collision with root package name */
        Map f26213e;

        public Builder() {
            this.f26213e = Collections.emptyMap();
            this.f26210b = "GET";
            this.f26211c = new Headers.Builder();
        }

        public Builder addHeader(String str, String str2) {
            this.f26211c.add(str, str2);
            return this;
        }

        public Request build() {
            if (this.f26209a != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String string = cacheControl.toString();
            return string.isEmpty() ? removeHeader("Cache-Control") : header("Cache-Control", string);
        }

        public Builder delete(@Nullable RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method(Request.Method.HEAD, null);
        }

        public Builder header(String str, String str2) {
            this.f26211c.set(str, str2);
            return this;
        }

        public Builder headers(Headers headers) {
            this.f26211c = headers.newBuilder();
            return this;
        }

        public Builder method(String str, @Nullable RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("method == null");
            }
            if (str.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            }
            if (requestBody != null && !HttpMethod.permitsRequestBody(str)) {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            }
            if (requestBody != null || !HttpMethod.requiresRequestBody(str)) {
                this.f26210b = str;
                this.f26212d = requestBody;
                return this;
            }
            throw new IllegalArgumentException("method " + str + " must have a request body.");
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            return method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            return method(Request.Method.PUT, requestBody);
        }

        public Builder removeHeader(String str) {
            this.f26211c.removeAll(str);
            return this;
        }

        public Builder tag(@Nullable Object obj) {
            return tag(Object.class, obj);
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl == null) {
                throw new NullPointerException("url == null");
            }
            this.f26209a = httpUrl;
            return this;
        }

        public Builder delete() {
            return delete(Util.EMPTY_REQUEST);
        }

        public <T> Builder tag(Class<? super T> cls, @Nullable T t2) {
            if (cls == null) {
                throw new NullPointerException("type == null");
            }
            if (t2 == null) {
                this.f26213e.remove(cls);
            } else {
                if (this.f26213e.isEmpty()) {
                    this.f26213e = new LinkedHashMap();
                }
                this.f26213e.put(cls, cls.cast(t2));
            }
            return this;
        }

        public Builder url(String str) {
            if (str != null) {
                if (str.regionMatches(true, 0, "ws:", 0, 3)) {
                    str = "http:" + str.substring(3);
                } else if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                    str = "https:" + str.substring(4);
                }
                return url(HttpUrl.get(str));
            }
            throw new NullPointerException("url == null");
        }

        Builder(Request request) {
            Map linkedHashMap;
            this.f26213e = Collections.emptyMap();
            this.f26209a = request.f26204a;
            this.f26210b = request.f26205b;
            this.f26212d = request.f26207d;
            if (request.f26208e.isEmpty()) {
                linkedHashMap = Collections.emptyMap();
            } else {
                linkedHashMap = new LinkedHashMap(request.f26208e);
            }
            this.f26213e = linkedHashMap;
            this.f26211c = request.f26206c.newBuilder();
        }

        public Builder url(URL url) {
            if (url != null) {
                return url(HttpUrl.get(url.toString()));
            }
            throw new NullPointerException("url == null");
        }
    }

    Request(Builder builder) {
        this.f26204a = builder.f26209a;
        this.f26205b = builder.f26210b;
        this.f26206c = builder.f26211c.build();
        this.f26207d = builder.f26212d;
        this.f26208e = Util.immutableMap(builder.f26213e);
    }

    @Nullable
    public RequestBody body() {
        return this.f26207d;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl cacheControl2 = CacheControl.parse(this.f26206c);
        this.cacheControl = cacheControl2;
        return cacheControl2;
    }

    @Nullable
    public String header(String str) {
        return this.f26206c.get(str);
    }

    public Headers headers() {
        return this.f26206c;
    }

    public boolean isHttps() {
        return this.f26204a.isHttps();
    }

    public String method() {
        return this.f26205b;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Nullable
    public Object tag() {
        return tag(Object.class);
    }

    public String toString() {
        return "Request{method=" + this.f26205b + ", url=" + this.f26204a + ", tags=" + this.f26208e + '}';
    }

    public HttpUrl url() {
        return this.f26204a;
    }

    public List<String> headers(String str) {
        return this.f26206c.values(str);
    }

    @Nullable
    public <T> T tag(Class<? extends T> cls) {
        return cls.cast(this.f26208e.get(cls));
    }
}

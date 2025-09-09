package okhttp3;

import com.umeng.analytics.pro.bc;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes5.dex */
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
    static final List C = Util.immutableList(Protocol.HTTP_2, Protocol.HTTP_1_1);
    static final List D = Util.immutableList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);
    final int A;
    final int B;

    /* renamed from: a, reason: collision with root package name */
    final Dispatcher f26145a;

    /* renamed from: b, reason: collision with root package name */
    final Proxy f26146b;

    /* renamed from: c, reason: collision with root package name */
    final List f26147c;

    /* renamed from: d, reason: collision with root package name */
    final List f26148d;

    /* renamed from: e, reason: collision with root package name */
    final List f26149e;

    /* renamed from: f, reason: collision with root package name */
    final List f26150f;

    /* renamed from: g, reason: collision with root package name */
    final EventListener.Factory f26151g;

    /* renamed from: h, reason: collision with root package name */
    final ProxySelector f26152h;

    /* renamed from: i, reason: collision with root package name */
    final CookieJar f26153i;

    /* renamed from: j, reason: collision with root package name */
    final Cache f26154j;

    /* renamed from: k, reason: collision with root package name */
    final InternalCache f26155k;

    /* renamed from: l, reason: collision with root package name */
    final SocketFactory f26156l;

    /* renamed from: m, reason: collision with root package name */
    final SSLSocketFactory f26157m;

    /* renamed from: n, reason: collision with root package name */
    final CertificateChainCleaner f26158n;

    /* renamed from: o, reason: collision with root package name */
    final HostnameVerifier f26159o;

    /* renamed from: p, reason: collision with root package name */
    final CertificatePinner f26160p;

    /* renamed from: q, reason: collision with root package name */
    final Authenticator f26161q;

    /* renamed from: r, reason: collision with root package name */
    final Authenticator f26162r;

    /* renamed from: s, reason: collision with root package name */
    final ConnectionPool f26163s;

    /* renamed from: t, reason: collision with root package name */
    final Dns f26164t;

    /* renamed from: u, reason: collision with root package name */
    final boolean f26165u;

    /* renamed from: v, reason: collision with root package name */
    final boolean f26166v;

    /* renamed from: w, reason: collision with root package name */
    final boolean f26167w;

    /* renamed from: x, reason: collision with root package name */
    final int f26168x;

    /* renamed from: y, reason: collision with root package name */
    final int f26169y;

    /* renamed from: z, reason: collision with root package name */
    final int f26170z;

    public static final class Builder {
        int A;
        int B;

        /* renamed from: a, reason: collision with root package name */
        Dispatcher f26171a;

        /* renamed from: b, reason: collision with root package name */
        Proxy f26172b;

        /* renamed from: c, reason: collision with root package name */
        List f26173c;

        /* renamed from: d, reason: collision with root package name */
        List f26174d;

        /* renamed from: e, reason: collision with root package name */
        final List f26175e;

        /* renamed from: f, reason: collision with root package name */
        final List f26176f;

        /* renamed from: g, reason: collision with root package name */
        EventListener.Factory f26177g;

        /* renamed from: h, reason: collision with root package name */
        ProxySelector f26178h;

        /* renamed from: i, reason: collision with root package name */
        CookieJar f26179i;

        /* renamed from: j, reason: collision with root package name */
        Cache f26180j;

        /* renamed from: k, reason: collision with root package name */
        InternalCache f26181k;

        /* renamed from: l, reason: collision with root package name */
        SocketFactory f26182l;

        /* renamed from: m, reason: collision with root package name */
        SSLSocketFactory f26183m;

        /* renamed from: n, reason: collision with root package name */
        CertificateChainCleaner f26184n;

        /* renamed from: o, reason: collision with root package name */
        HostnameVerifier f26185o;

        /* renamed from: p, reason: collision with root package name */
        CertificatePinner f26186p;

        /* renamed from: q, reason: collision with root package name */
        Authenticator f26187q;

        /* renamed from: r, reason: collision with root package name */
        Authenticator f26188r;

        /* renamed from: s, reason: collision with root package name */
        ConnectionPool f26189s;

        /* renamed from: t, reason: collision with root package name */
        Dns f26190t;

        /* renamed from: u, reason: collision with root package name */
        boolean f26191u;

        /* renamed from: v, reason: collision with root package name */
        boolean f26192v;

        /* renamed from: w, reason: collision with root package name */
        boolean f26193w;

        /* renamed from: x, reason: collision with root package name */
        int f26194x;

        /* renamed from: y, reason: collision with root package name */
        int f26195y;

        /* renamed from: z, reason: collision with root package name */
        int f26196z;

        public Builder() {
            this.f26175e = new ArrayList();
            this.f26176f = new ArrayList();
            this.f26171a = new Dispatcher();
            this.f26173c = OkHttpClient.C;
            this.f26174d = OkHttpClient.D;
            this.f26177g = EventListener.factory(EventListener.NONE);
            ProxySelector proxySelector = ProxySelector.getDefault();
            this.f26178h = proxySelector;
            if (proxySelector == null) {
                this.f26178h = new NullProxySelector();
            }
            this.f26179i = CookieJar.NO_COOKIES;
            this.f26182l = SocketFactory.getDefault();
            this.f26185o = OkHostnameVerifier.INSTANCE;
            this.f26186p = CertificatePinner.DEFAULT;
            Authenticator authenticator = Authenticator.NONE;
            this.f26187q = authenticator;
            this.f26188r = authenticator;
            this.f26189s = new ConnectionPool();
            this.f26190t = Dns.SYSTEM;
            this.f26191u = true;
            this.f26192v = true;
            this.f26193w = true;
            this.f26194x = 0;
            this.f26195y = 10000;
            this.f26196z = 10000;
            this.A = 10000;
            this.B = 0;
        }

        void a(InternalCache internalCache) {
            this.f26181k = internalCache;
            this.f26180j = null;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.f26175e.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new IllegalArgumentException("interceptor == null");
            }
            this.f26176f.add(interceptor);
            return this;
        }

        public Builder authenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("authenticator == null");
            }
            this.f26188r = authenticator;
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient(this);
        }

        public Builder cache(@Nullable Cache cache) {
            this.f26180j = cache;
            this.f26181k = null;
            return this;
        }

        public Builder callTimeout(long j2, TimeUnit timeUnit) {
            this.f26194x = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder certificatePinner(CertificatePinner certificatePinner) {
            if (certificatePinner == null) {
                throw new NullPointerException("certificatePinner == null");
            }
            this.f26186p = certificatePinner;
            return this;
        }

        public Builder connectTimeout(long j2, TimeUnit timeUnit) {
            this.f26195y = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.f26189s = connectionPool;
            return this;
        }

        public Builder connectionSpecs(List<ConnectionSpec> list) {
            this.f26174d = Util.immutableList(list);
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            if (cookieJar == null) {
                throw new NullPointerException("cookieJar == null");
            }
            this.f26179i = cookieJar;
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            if (dispatcher == null) {
                throw new IllegalArgumentException("dispatcher == null");
            }
            this.f26171a = dispatcher;
            return this;
        }

        public Builder dns(Dns dns) {
            if (dns == null) {
                throw new NullPointerException("dns == null");
            }
            this.f26190t = dns;
            return this;
        }

        public Builder eventListener(EventListener eventListener) {
            if (eventListener == null) {
                throw new NullPointerException("eventListener == null");
            }
            this.f26177g = EventListener.factory(eventListener);
            return this;
        }

        public Builder eventListenerFactory(EventListener.Factory factory) {
            if (factory == null) {
                throw new NullPointerException("eventListenerFactory == null");
            }
            this.f26177g = factory;
            return this;
        }

        public Builder followRedirects(boolean z2) {
            this.f26192v = z2;
            return this;
        }

        public Builder followSslRedirects(boolean z2) {
            this.f26191u = z2;
            return this;
        }

        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier == null) {
                throw new NullPointerException("hostnameVerifier == null");
            }
            this.f26185o = hostnameVerifier;
            return this;
        }

        public List<Interceptor> interceptors() {
            return this.f26175e;
        }

        public List<Interceptor> networkInterceptors() {
            return this.f26176f;
        }

        public Builder pingInterval(long j2, TimeUnit timeUnit) {
            this.B = Util.checkDuration(bc.ba, j2, timeUnit);
            return this;
        }

        public Builder protocols(List<Protocol> list) {
            ArrayList arrayList = new ArrayList(list);
            Protocol protocol = Protocol.H2_PRIOR_KNOWLEDGE;
            if (!arrayList.contains(protocol) && !arrayList.contains(Protocol.HTTP_1_1)) {
                throw new IllegalArgumentException("protocols must contain h2_prior_knowledge or http/1.1: " + arrayList);
            }
            if (arrayList.contains(protocol) && arrayList.size() > 1) {
                throw new IllegalArgumentException("protocols containing h2_prior_knowledge cannot use other protocols: " + arrayList);
            }
            if (arrayList.contains(Protocol.HTTP_1_0)) {
                throw new IllegalArgumentException("protocols must not contain http/1.0: " + arrayList);
            }
            if (arrayList.contains(null)) {
                throw new IllegalArgumentException("protocols must not contain null");
            }
            arrayList.remove(Protocol.SPDY_3);
            this.f26173c = Collections.unmodifiableList(arrayList);
            return this;
        }

        public Builder proxy(@Nullable Proxy proxy) {
            this.f26172b = proxy;
            return this;
        }

        public Builder proxyAuthenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("proxyAuthenticator == null");
            }
            this.f26187q = authenticator;
            return this;
        }

        public Builder proxySelector(ProxySelector proxySelector) {
            if (proxySelector == null) {
                throw new NullPointerException("proxySelector == null");
            }
            this.f26178h = proxySelector;
            return this;
        }

        public Builder readTimeout(long j2, TimeUnit timeUnit) {
            this.f26196z = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        public Builder retryOnConnectionFailure(boolean z2) {
            this.f26193w = z2;
            return this;
        }

        public Builder socketFactory(SocketFactory socketFactory) {
            if (socketFactory == null) {
                throw new NullPointerException("socketFactory == null");
            }
            this.f26182l = socketFactory;
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            this.f26183m = sSLSocketFactory;
            this.f26184n = Platform.get().buildCertificateChainCleaner(sSLSocketFactory);
            return this;
        }

        public Builder writeTimeout(long j2, TimeUnit timeUnit) {
            this.A = Util.checkDuration("timeout", j2, timeUnit);
            return this;
        }

        @IgnoreJRERequirement
        public Builder callTimeout(Duration duration) {
            this.f26194x = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder connectTimeout(Duration duration) {
            this.f26195y = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder pingInterval(Duration duration) {
            this.B = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder readTimeout(Duration duration) {
            this.f26196z = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        @IgnoreJRERequirement
        public Builder writeTimeout(Duration duration) {
            this.A = Util.checkDuration("timeout", duration.toMillis(), TimeUnit.MILLISECONDS);
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            if (x509TrustManager != null) {
                this.f26183m = sSLSocketFactory;
                this.f26184n = CertificateChainCleaner.get(x509TrustManager);
                return this;
            }
            throw new NullPointerException("trustManager == null");
        }

        Builder(OkHttpClient okHttpClient) {
            ArrayList arrayList = new ArrayList();
            this.f26175e = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.f26176f = arrayList2;
            this.f26171a = okHttpClient.f26145a;
            this.f26172b = okHttpClient.f26146b;
            this.f26173c = okHttpClient.f26147c;
            this.f26174d = okHttpClient.f26148d;
            arrayList.addAll(okHttpClient.f26149e);
            arrayList2.addAll(okHttpClient.f26150f);
            this.f26177g = okHttpClient.f26151g;
            this.f26178h = okHttpClient.f26152h;
            this.f26179i = okHttpClient.f26153i;
            this.f26181k = okHttpClient.f26155k;
            this.f26180j = okHttpClient.f26154j;
            this.f26182l = okHttpClient.f26156l;
            this.f26183m = okHttpClient.f26157m;
            this.f26184n = okHttpClient.f26158n;
            this.f26185o = okHttpClient.f26159o;
            this.f26186p = okHttpClient.f26160p;
            this.f26187q = okHttpClient.f26161q;
            this.f26188r = okHttpClient.f26162r;
            this.f26189s = okHttpClient.f26163s;
            this.f26190t = okHttpClient.f26164t;
            this.f26191u = okHttpClient.f26165u;
            this.f26192v = okHttpClient.f26166v;
            this.f26193w = okHttpClient.f26167w;
            this.f26194x = okHttpClient.f26168x;
            this.f26195y = okHttpClient.f26169y;
            this.f26196z = okHttpClient.f26170z;
            this.A = okHttpClient.A;
            this.B = okHttpClient.B;
        }
    }

    static {
        Internal.instance = new Internal() { // from class: okhttp3.OkHttpClient.1
            @Override // okhttp3.internal.Internal
            public void addLenient(Headers.Builder builder, String str) {
                builder.a(str);
            }

            @Override // okhttp3.internal.Internal
            public void apply(ConnectionSpec connectionSpec, SSLSocket sSLSocket, boolean z2) {
                connectionSpec.a(sSLSocket, z2);
            }

            @Override // okhttp3.internal.Internal
            public int code(Response.Builder builder) {
                return builder.f26236c;
            }

            @Override // okhttp3.internal.Internal
            public boolean connectionBecameIdle(ConnectionPool connectionPool, RealConnection realConnection) {
                return connectionPool.b(realConnection);
            }

            @Override // okhttp3.internal.Internal
            public Socket deduplicate(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation) {
                return connectionPool.c(address, streamAllocation);
            }

            @Override // okhttp3.internal.Internal
            public boolean equalsNonHost(Address address, Address address2) {
                return address.a(address2);
            }

            @Override // okhttp3.internal.Internal
            public RealConnection get(ConnectionPool connectionPool, Address address, StreamAllocation streamAllocation, Route route) {
                return connectionPool.d(address, streamAllocation, route);
            }

            @Override // okhttp3.internal.Internal
            public boolean isInvalidHttpUrlHost(IllegalArgumentException illegalArgumentException) {
                return illegalArgumentException.getMessage().startsWith("Invalid URL host");
            }

            @Override // okhttp3.internal.Internal
            public Call newWebSocketCall(OkHttpClient okHttpClient, Request request) {
                return RealCall.c(okHttpClient, request, true);
            }

            @Override // okhttp3.internal.Internal
            public void put(ConnectionPool connectionPool, RealConnection realConnection) {
                connectionPool.e(realConnection);
            }

            @Override // okhttp3.internal.Internal
            public RouteDatabase routeDatabase(ConnectionPool connectionPool) {
                return connectionPool.f26110a;
            }

            @Override // okhttp3.internal.Internal
            public void setCache(Builder builder, InternalCache internalCache) {
                builder.a(internalCache);
            }

            @Override // okhttp3.internal.Internal
            public StreamAllocation streamAllocation(Call call) {
                return ((RealCall) call).e();
            }

            @Override // okhttp3.internal.Internal
            @Nullable
            public IOException timeoutExit(Call call, @Nullable IOException iOException) {
                return ((RealCall) call).f(iOException);
            }

            @Override // okhttp3.internal.Internal
            public void addLenient(Headers.Builder builder, String str, String str2) {
                builder.b(str, str2);
            }
        };
    }

    public OkHttpClient() {
        this(new Builder());
    }

    private static SSLSocketFactory newSslSocketFactory(X509TrustManager x509TrustManager) throws KeyManagementException {
        try {
            SSLContext sSLContext = Platform.get().getSSLContext();
            sSLContext.init(null, new TrustManager[]{x509TrustManager}, null);
            return sSLContext.getSocketFactory();
        } catch (GeneralSecurityException e2) {
            throw Util.assertionError("No System TLS", e2);
        }
    }

    InternalCache a() {
        Cache cache = this.f26154j;
        return cache != null ? cache.f26078a : this.f26155k;
    }

    public Authenticator authenticator() {
        return this.f26162r;
    }

    @Nullable
    public Cache cache() {
        return this.f26154j;
    }

    public int callTimeoutMillis() {
        return this.f26168x;
    }

    public CertificatePinner certificatePinner() {
        return this.f26160p;
    }

    public int connectTimeoutMillis() {
        return this.f26169y;
    }

    public ConnectionPool connectionPool() {
        return this.f26163s;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.f26148d;
    }

    public CookieJar cookieJar() {
        return this.f26153i;
    }

    public Dispatcher dispatcher() {
        return this.f26145a;
    }

    public Dns dns() {
        return this.f26164t;
    }

    public EventListener.Factory eventListenerFactory() {
        return this.f26151g;
    }

    public boolean followRedirects() {
        return this.f26166v;
    }

    public boolean followSslRedirects() {
        return this.f26165u;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.f26159o;
    }

    public List<Interceptor> interceptors() {
        return this.f26149e;
    }

    public List<Interceptor> networkInterceptors() {
        return this.f26150f;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override // okhttp3.Call.Factory
    public Call newCall(Request request) {
        return RealCall.c(this, request, false);
    }

    @Override // okhttp3.WebSocket.Factory
    public WebSocket newWebSocket(Request request, WebSocketListener webSocketListener) {
        RealWebSocket realWebSocket = new RealWebSocket(request, webSocketListener, new Random(), this.B);
        realWebSocket.connect(this);
        return realWebSocket;
    }

    public int pingIntervalMillis() {
        return this.B;
    }

    public List<Protocol> protocols() {
        return this.f26147c;
    }

    @Nullable
    public Proxy proxy() {
        return this.f26146b;
    }

    public Authenticator proxyAuthenticator() {
        return this.f26161q;
    }

    public ProxySelector proxySelector() {
        return this.f26152h;
    }

    public int readTimeoutMillis() {
        return this.f26170z;
    }

    public boolean retryOnConnectionFailure() {
        return this.f26167w;
    }

    public SocketFactory socketFactory() {
        return this.f26156l;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.f26157m;
    }

    public int writeTimeoutMillis() {
        return this.A;
    }

    OkHttpClient(Builder builder) {
        boolean z2;
        this.f26145a = builder.f26171a;
        this.f26146b = builder.f26172b;
        this.f26147c = builder.f26173c;
        List list = builder.f26174d;
        this.f26148d = list;
        this.f26149e = Util.immutableList(builder.f26175e);
        this.f26150f = Util.immutableList(builder.f26176f);
        this.f26151g = builder.f26177g;
        this.f26152h = builder.f26178h;
        this.f26153i = builder.f26179i;
        this.f26154j = builder.f26180j;
        this.f26155k = builder.f26181k;
        this.f26156l = builder.f26182l;
        Iterator it = list.iterator();
        loop0: while (true) {
            z2 = false;
            while (it.hasNext()) {
                z2 = (z2 || ((ConnectionSpec) it.next()).isTls()) ? true : z2;
            }
        }
        SSLSocketFactory sSLSocketFactory = builder.f26183m;
        if (sSLSocketFactory == null && z2) {
            X509TrustManager x509TrustManagerPlatformTrustManager = Util.platformTrustManager();
            this.f26157m = newSslSocketFactory(x509TrustManagerPlatformTrustManager);
            this.f26158n = CertificateChainCleaner.get(x509TrustManagerPlatformTrustManager);
        } else {
            this.f26157m = sSLSocketFactory;
            this.f26158n = builder.f26184n;
        }
        if (this.f26157m != null) {
            Platform.get().configureSslSocketFactory(this.f26157m);
        }
        this.f26159o = builder.f26185o;
        this.f26160p = builder.f26186p.d(this.f26158n);
        this.f26161q = builder.f26187q;
        this.f26162r = builder.f26188r;
        this.f26163s = builder.f26189s;
        this.f26164t = builder.f26190t;
        this.f26165u = builder.f26191u;
        this.f26166v = builder.f26192v;
        this.f26167w = builder.f26193w;
        this.f26168x = builder.f26194x;
        this.f26169y = builder.f26195y;
        this.f26170z = builder.f26196z;
        this.A = builder.A;
        this.B = builder.B;
        if (this.f26149e.contains(null)) {
            throw new IllegalStateException("Null interceptor: " + this.f26149e);
        }
        if (this.f26150f.contains(null)) {
            throw new IllegalStateException("Null network interceptor: " + this.f26150f);
        }
    }
}

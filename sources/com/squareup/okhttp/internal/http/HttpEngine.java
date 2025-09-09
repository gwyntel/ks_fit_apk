package com.squareup.okhttp.internal.http;

import anet.channel.request.Request;
import anet.channel.util.HttpConstant;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.http.CacheStrategy;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes4.dex */
public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody() { // from class: com.squareup.okhttp.internal.http.HttpEngine.1
        @Override // com.squareup.okhttp.ResponseBody
        public long contentLength() {
            return 0L;
        }

        @Override // com.squareup.okhttp.ResponseBody
        public MediaType contentType() {
            return null;
        }

        @Override // com.squareup.okhttp.ResponseBody
        public BufferedSource source() {
            return new Buffer();
        }
    };
    public static final int MAX_FOLLOW_UPS = 20;

    /* renamed from: a, reason: collision with root package name */
    final OkHttpClient f19995a;
    private Address address;

    /* renamed from: b, reason: collision with root package name */
    long f19996b = -1;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    private Connection connection;
    private final boolean forWebSocket;
    private Request networkRequest;
    private final Response priorResponse;
    private Sink requestBodyOut;
    private Route route;
    private RouteSelector routeSelector;
    private CacheRequest storeRequest;
    private boolean transparentGzip;
    private Transport transport;
    private final Request userRequest;
    private Response userResponse;

    class NetworkInterceptorChain implements Interceptor.Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int i2, Request request) {
            this.index = i2;
            this.request = request;
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Connection connection() {
            return HttpEngine.this.connection;
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Response proceed(Request request) throws IOException {
            this.calls++;
            if (this.index > 0) {
                Interceptor interceptor = HttpEngine.this.f19995a.networkInterceptors().get(this.index - 1);
                Address address = connection().getRoute().getAddress();
                if (!request.httpUrl().host().equals(address.getUriHost()) || request.httpUrl().port() != address.getUriPort()) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must retain the same host and port");
                }
                if (this.calls > 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                }
            }
            if (this.index < HttpEngine.this.f19995a.networkInterceptors().size()) {
                NetworkInterceptorChain networkInterceptorChain = HttpEngine.this.new NetworkInterceptorChain(this.index + 1, request);
                Interceptor interceptor2 = HttpEngine.this.f19995a.networkInterceptors().get(this.index);
                Response responseIntercept = interceptor2.intercept(networkInterceptorChain);
                if (networkInterceptorChain.calls == 1) {
                    return responseIntercept;
                }
                throw new IllegalStateException("network interceptor " + interceptor2 + " must call proceed() exactly once");
            }
            HttpEngine.this.transport.writeRequestHeaders(request);
            HttpEngine.this.networkRequest = request;
            if (HttpEngine.this.e() && request.body() != null) {
                BufferedSink bufferedSinkBuffer = Okio.buffer(HttpEngine.this.transport.createRequestBody(request, request.body().contentLength()));
                request.body().writeTo(bufferedSinkBuffer);
                bufferedSinkBuffer.close();
            }
            Response networkResponse = HttpEngine.this.readNetworkResponse();
            int iCode = networkResponse.code();
            if ((iCode != 204 && iCode != 205) || networkResponse.body().contentLength() <= 0) {
                return networkResponse;
            }
            throw new ProtocolException("HTTP " + iCode + " had non-zero Content-Length: " + networkResponse.body().contentLength());
        }

        @Override // com.squareup.okhttp.Interceptor.Chain
        public Request request() {
            return this.request;
        }
    }

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean z2, boolean z3, boolean z4, Connection connection, RouteSelector routeSelector, RetryableSink retryableSink, Response response) {
        this.f19995a = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = z2;
        this.callerWritesRequestBody = z3;
        this.forWebSocket = z4;
        this.connection = connection;
        this.routeSelector = routeSelector;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
        if (connection == null) {
            this.route = null;
        } else {
            Internal.instance.setOwner(connection, this);
            this.route = connection.getRoute();
        }
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        Sink sinkBody;
        if (cacheRequest == null || (sinkBody = cacheRequest.body()) == null) {
            return response;
        }
        final BufferedSource bufferedSourceSource = response.body().source();
        final BufferedSink bufferedSinkBuffer = Okio.buffer(sinkBody);
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer(new Source() { // from class: com.squareup.okhttp.internal.http.HttpEngine.2

            /* renamed from: a, reason: collision with root package name */
            boolean f19997a;

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                if (!this.f19997a && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.f19997a = true;
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
                    if (!this.f19997a) {
                        this.f19997a = true;
                        bufferedSinkBuffer.close();
                    }
                    return -1L;
                } catch (IOException e2) {
                    if (!this.f19997a) {
                        this.f19997a = true;
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

    private static Headers combine(Headers headers, Headers headers2) throws IOException {
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            String strName = headers.name(i2);
            String strValue = headers.value(i2);
            if ((!HttpHeaders.WARNING.equalsIgnoreCase(strName) || !strValue.startsWith("1")) && (!OkHeaders.a(strName) || headers2.get(strName) == null)) {
                builder.add(strName, strValue);
            }
        }
        int size2 = headers2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            String strName2 = headers2.name(i3);
            if (!"Content-Length".equalsIgnoreCase(strName2) && OkHeaders.a(strName2)) {
                builder.add(strName2, headers2.value(i3));
            }
        }
        return builder.build();
    }

    private void connect() throws RouteException, RequestException, IOException {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        if (this.routeSelector == null) {
            Address addressCreateAddress = createAddress(this.f19995a, this.networkRequest);
            this.address = addressCreateAddress;
            try {
                this.routeSelector = RouteSelector.get(addressCreateAddress, this.networkRequest, this.f19995a);
            } catch (IOException e2) {
                throw new RequestException(e2);
            }
        }
        Connection connectionCreateNextConnection = createNextConnection();
        this.connection = connectionCreateNextConnection;
        Internal.instance.connectAndSetOwner(this.f19995a, connectionCreateNextConnection, this, this.networkRequest);
        this.route = this.connection.getRoute();
    }

    private void connectFailed(RouteSelector routeSelector, IOException iOException) {
        if (Internal.instance.recycleCount(this.connection) > 0) {
            return;
        }
        routeSelector.connectFailed(this.connection.getRoute(), iOException);
    }

    private static Address createAddress(OkHttpClient okHttpClient, Request request) {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        CertificatePinner certificatePinner;
        if (request.isHttps()) {
            sslSocketFactory = okHttpClient.getSslSocketFactory();
            hostnameVerifier = okHttpClient.getHostnameVerifier();
            certificatePinner = okHttpClient.getCertificatePinner();
        } else {
            sslSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        return new Address(request.httpUrl().host(), request.httpUrl().port(), okHttpClient.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, okHttpClient.getAuthenticator(), okHttpClient.getProxy(), okHttpClient.getProtocols(), okHttpClient.getConnectionSpecs(), okHttpClient.getProxySelector());
    }

    private Connection createNextConnection() throws RouteException, IOException {
        Connection connection;
        ConnectionPool connectionPool = this.f19995a.getConnectionPool();
        while (true) {
            connection = connectionPool.get(this.address);
            if (connection == null) {
                try {
                    return new Connection(connectionPool, this.routeSelector.next());
                } catch (IOException e2) {
                    throw new RouteException(e2);
                }
            }
            if (this.networkRequest.method().equals("GET") || Internal.instance.isReadable(connection)) {
                break;
            }
            Util.closeQuietly(connection.getSocket());
        }
        return connection;
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals(Request.Method.HEAD)) {
            return false;
        }
        int iCode = response.code();
        return (((iCode >= 100 && iCode < 200) || iCode == 204 || iCode == 304) && OkHeaders.contentLength(response) == -1 && !"chunked".equalsIgnoreCase(response.header(HttpHeaders.TRANSFER_ENCODING))) ? false : true;
    }

    private boolean isRecoverable(RouteException routeException) {
        if (!this.f19995a.getRetryOnConnectionFailure()) {
            return false;
        }
        IOException lastConnectException = routeException.getLastConnectException();
        if ((lastConnectException instanceof ProtocolException) || (lastConnectException instanceof InterruptedIOException)) {
            return false;
        }
        return (((lastConnectException instanceof SSLHandshakeException) && (lastConnectException.getCause() instanceof CertificateException)) || (lastConnectException instanceof SSLPeerUnverifiedException)) ? false : true;
    }

    private void maybeCache() throws IOException {
        InternalCache internalCache = Internal.instance.internalCache(this.f19995a);
        if (internalCache == null) {
            return;
        }
        if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
            this.storeRequest = internalCache.put(stripBody(this.userResponse));
        } else if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
            try {
                internalCache.remove(this.networkRequest);
            } catch (IOException unused) {
            }
        }
    }

    private com.squareup.okhttp.Request networkRequest(com.squareup.okhttp.Request request) throws IOException {
        Request.Builder builderNewBuilder = request.newBuilder();
        if (request.header("Host") == null) {
            builderNewBuilder.header("Host", Util.hostHeader(request.httpUrl()));
        }
        Connection connection = this.connection;
        if ((connection == null || connection.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            builderNewBuilder.header("Connection", HttpHeaders.KEEP_ALIVE);
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            builderNewBuilder.header("Accept-Encoding", HttpConstant.GZIP);
        }
        CookieHandler cookieHandler = this.f19995a.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(builderNewBuilder, cookieHandler.get(request.uri(), OkHeaders.toMultimap(builderNewBuilder.build().headers(), null)));
        }
        if (request.header("User-Agent") == null) {
            builderNewBuilder.header("User-Agent", Version.userAgent());
        }
        return builderNewBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Response readNetworkResponse() throws IOException {
        this.transport.finishRequest();
        Response responseBuild = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.f19996b)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            responseBuild = responseBuild.newBuilder().body(this.transport.openResponseBody(responseBuild)).build();
        }
        Internal.instance.setProtocol(this.connection, responseBuild.protocol());
        return responseBuild;
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private Response unzip(Response response) throws IOException {
        if (!this.transparentGzip || !HttpConstant.GZIP.equalsIgnoreCase(this.userResponse.header("Content-Encoding")) || response.body() == null) {
            return response;
        }
        GzipSource gzipSource = new GzipSource(response.body().source());
        Headers headersBuild = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
        return response.newBuilder().headers(headersBuild).body(new RealResponseBody(headersBuild, Okio.buffer(gzipSource))).build();
    }

    private static boolean validate(Response response, Response response2) {
        Date date;
        if (response2.code() == 304) {
            return true;
        }
        Date date2 = response.headers().getDate("Last-Modified");
        return (date2 == null || (date = response2.headers().getDate("Last-Modified")) == null || date.getTime() >= date2.getTime()) ? false : true;
    }

    public Connection close() {
        BufferedSink bufferedSink = this.bufferedRequestBody;
        if (bufferedSink != null) {
            Util.closeQuietly(bufferedSink);
        } else {
            Sink sink = this.requestBodyOut;
            if (sink != null) {
                Util.closeQuietly(sink);
            }
        }
        Response response = this.userResponse;
        if (response == null) {
            Connection connection = this.connection;
            if (connection != null) {
                Util.closeQuietly(connection.getSocket());
            }
            this.connection = null;
            return null;
        }
        Util.closeQuietly(response.body());
        Transport transport = this.transport;
        if (transport != null && this.connection != null && !transport.canReuseConnection()) {
            Util.closeQuietly(this.connection.getSocket());
            this.connection = null;
            return null;
        }
        Connection connection2 = this.connection;
        if (connection2 != null && !Internal.instance.clearOwner(connection2)) {
            this.connection = null;
        }
        Connection connection3 = this.connection;
        this.connection = null;
        return connection3;
    }

    public void disconnect() {
        try {
            Transport transport = this.transport;
            if (transport != null) {
                transport.disconnect(this);
            } else {
                Connection connection = this.connection;
                if (connection != null) {
                    Internal.instance.closeIfOwnedBy(connection, this);
                }
            }
        } catch (IOException unused) {
        }
    }

    boolean e() {
        return HttpMethod.permitsRequestBody(this.userRequest.method());
    }

    public com.squareup.okhttp.Request followUpRequest() throws IOException {
        String strHeader;
        HttpUrl httpUrlResolve;
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        Proxy proxy = getRoute() != null ? getRoute().getProxy() : this.f19995a.getProxy();
        int iCode = this.userResponse.code();
        if (iCode != 307 && iCode != 308) {
            if (iCode != 401) {
                if (iCode != 407) {
                    switch (iCode) {
                        case 300:
                        case 301:
                        case 302:
                        case 303:
                            break;
                        default:
                            return null;
                    }
                } else if (proxy.type() != Proxy.Type.HTTP) {
                    throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                }
            }
            return OkHeaders.processAuthHeader(this.f19995a.getAuthenticator(), this.userResponse, proxy);
        }
        if (!this.userRequest.method().equals("GET") && !this.userRequest.method().equals(Request.Method.HEAD)) {
            return null;
        }
        if (!this.f19995a.getFollowRedirects() || (strHeader = this.userResponse.header("Location")) == null || (httpUrlResolve = this.userRequest.httpUrl().resolve(strHeader)) == null) {
            return null;
        }
        if (!httpUrlResolve.scheme().equals(this.userRequest.httpUrl().scheme()) && !this.f19995a.getFollowSslRedirects()) {
            return null;
        }
        Request.Builder builderNewBuilder = this.userRequest.newBuilder();
        if (HttpMethod.permitsRequestBody(this.userRequest.method())) {
            builderNewBuilder.method("GET", null);
            builderNewBuilder.removeHeader(HttpHeaders.TRANSFER_ENCODING);
            builderNewBuilder.removeHeader("Content-Length");
            builderNewBuilder.removeHeader("Content-Type");
        }
        if (!sameConnection(httpUrlResolve)) {
            builderNewBuilder.removeHeader("Authorization");
        }
        return builderNewBuilder.url(httpUrlResolve).build();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink bufferedSink = this.bufferedRequestBody;
        if (bufferedSink != null) {
            return bufferedSink;
        }
        Sink requestBody = getRequestBody();
        if (requestBody == null) {
            return null;
        }
        BufferedSink bufferedSinkBuffer = Okio.buffer(requestBody);
        this.bufferedRequestBody = bufferedSinkBuffer;
        return bufferedSinkBuffer;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public com.squareup.okhttp.Request getRequest() {
        return this.userRequest;
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public Response getResponse() {
        Response response = this.userResponse;
        if (response != null) {
            return response;
        }
        throw new IllegalStateException();
    }

    public Route getRoute() {
        return this.route;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public void readResponse() throws IOException {
        Response networkResponse;
        if (this.userResponse != null) {
            return;
        }
        com.squareup.okhttp.Request request = this.networkRequest;
        if (request == null && this.cacheResponse == null) {
            throw new IllegalStateException("call sendRequest() first!");
        }
        if (request == null) {
            return;
        }
        if (this.forWebSocket) {
            this.transport.writeRequestHeaders(request);
            networkResponse = readNetworkResponse();
        } else if (this.callerWritesRequestBody) {
            BufferedSink bufferedSink = this.bufferedRequestBody;
            if (bufferedSink != null && bufferedSink.buffer().size() > 0) {
                this.bufferedRequestBody.emit();
            }
            if (this.f19996b == -1) {
                if (OkHeaders.contentLength(this.networkRequest) == -1) {
                    Sink sink = this.requestBodyOut;
                    if (sink instanceof RetryableSink) {
                        this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) sink).contentLength())).build();
                    }
                }
                this.transport.writeRequestHeaders(this.networkRequest);
            }
            Sink sink2 = this.requestBodyOut;
            if (sink2 != null) {
                BufferedSink bufferedSink2 = this.bufferedRequestBody;
                if (bufferedSink2 != null) {
                    bufferedSink2.close();
                } else {
                    sink2.close();
                }
                Sink sink3 = this.requestBodyOut;
                if (sink3 instanceof RetryableSink) {
                    this.transport.writeRequestBody((RetryableSink) sink3);
                }
            }
            networkResponse = readNetworkResponse();
        } else {
            networkResponse = new NetworkInterceptorChain(0, request).proceed(this.networkRequest);
        }
        receiveHeaders(networkResponse.headers());
        Response response = this.cacheResponse;
        if (response != null) {
            if (validate(response, networkResponse)) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                networkResponse.body().close();
                releaseConnection();
                InternalCache internalCache = Internal.instance.internalCache(this.f19995a);
                internalCache.trackConditionalCacheHit();
                internalCache.update(this.cacheResponse, stripBody(this.userResponse));
                this.userResponse = unzip(this.userResponse);
                return;
            }
            Util.closeQuietly(this.cacheResponse.body());
        }
        Response responseBuild = networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
        this.userResponse = responseBuild;
        if (hasBody(responseBuild)) {
            maybeCache();
            this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
        }
    }

    public void receiveHeaders(Headers headers) throws IOException {
        CookieHandler cookieHandler = this.f19995a.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public HttpEngine recover(RouteException routeException) {
        RouteSelector routeSelector = this.routeSelector;
        if (routeSelector != null && this.connection != null) {
            connectFailed(routeSelector, routeException.getLastConnectException());
        }
        RouteSelector routeSelector2 = this.routeSelector;
        if (routeSelector2 == null && this.connection == null) {
            return null;
        }
        if ((routeSelector2 != null && !routeSelector2.hasNext()) || !isRecoverable(routeException)) {
            return null;
        }
        return new HttpEngine(this.f19995a, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) this.requestBodyOut, this.priorResponse);
    }

    public void releaseConnection() throws IOException {
        Transport transport = this.transport;
        if (transport != null && this.connection != null) {
            transport.releaseConnectionOnIdle();
        }
        this.connection = null;
    }

    public boolean sameConnection(HttpUrl httpUrl) {
        HttpUrl httpUrl2 = this.userRequest.httpUrl();
        return httpUrl2.host().equals(httpUrl.host()) && httpUrl2.port() == httpUrl.port() && httpUrl2.scheme().equals(httpUrl.scheme());
    }

    public void sendRequest() throws RouteException, RequestException, IOException {
        if (this.cacheStrategy != null) {
            return;
        }
        if (this.transport != null) {
            throw new IllegalStateException();
        }
        com.squareup.okhttp.Request requestNetworkRequest = networkRequest(this.userRequest);
        InternalCache internalCache = Internal.instance.internalCache(this.f19995a);
        Response response = internalCache != null ? internalCache.get(requestNetworkRequest) : null;
        CacheStrategy cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), requestNetworkRequest, response).get();
        this.cacheStrategy = cacheStrategy;
        this.networkRequest = cacheStrategy.networkRequest;
        this.cacheResponse = cacheStrategy.cacheResponse;
        if (internalCache != null) {
            internalCache.trackResponse(cacheStrategy);
        }
        if (response != null && this.cacheResponse == null) {
            Util.closeQuietly(response.body());
        }
        if (this.networkRequest == null) {
            if (this.connection != null) {
                Internal.instance.recycle(this.f19995a.getConnectionPool(), this.connection);
                this.connection = null;
            }
            Response response2 = this.cacheResponse;
            if (response2 != null) {
                this.userResponse = response2.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
            } else {
                this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
            }
            this.userResponse = unzip(this.userResponse);
            return;
        }
        if (this.connection == null) {
            connect();
        }
        this.transport = Internal.instance.newTransport(this.connection, this);
        if (this.callerWritesRequestBody && e() && this.requestBodyOut == null) {
            long jContentLength = OkHeaders.contentLength(requestNetworkRequest);
            if (!this.bufferRequestBody) {
                this.transport.writeRequestHeaders(this.networkRequest);
                this.requestBodyOut = this.transport.createRequestBody(this.networkRequest, jContentLength);
            } else {
                if (jContentLength > 2147483647L) {
                    throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                }
                if (jContentLength == -1) {
                    this.requestBodyOut = new RetryableSink();
                } else {
                    this.transport.writeRequestHeaders(this.networkRequest);
                    this.requestBodyOut = new RetryableSink((int) jContentLength);
                }
            }
        }
    }

    public void writingRequestHeaders() {
        if (this.f19996b != -1) {
            throw new IllegalStateException();
        }
        this.f19996b = System.currentTimeMillis();
    }

    private boolean isRecoverable(IOException iOException) {
        return (!this.f19995a.getRetryOnConnectionFailure() || (iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) ? false : true;
    }

    public HttpEngine recover(IOException iOException, Sink sink) {
        RouteSelector routeSelector = this.routeSelector;
        if (routeSelector != null && this.connection != null) {
            connectFailed(routeSelector, iOException);
        }
        boolean z2 = sink == null || (sink instanceof RetryableSink);
        RouteSelector routeSelector2 = this.routeSelector;
        if (routeSelector2 == null && this.connection == null) {
            return null;
        }
        if ((routeSelector2 == null || routeSelector2.hasNext()) && isRecoverable(iOException) && z2) {
            return new HttpEngine(this.f19995a, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), this.routeSelector, (RetryableSink) sink, this.priorResponse);
        }
        return null;
    }

    public HttpEngine recover(IOException iOException) {
        return recover(iOException, this.requestBodyOut);
    }
}

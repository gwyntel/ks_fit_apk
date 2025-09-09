package com.squareup.okhttp;

import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.ConnectionSpecSelector;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.http.FramedTransport;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownServiceException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Source;

/* loaded from: classes4.dex */
public final class Connection {
    private FramedConnection framedConnection;
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private boolean connected = false;
    private Protocol protocol = Protocol.HTTP_1_1;

    public Connection(ConnectionPool connectionPool, Route route) {
        this.pool = connectionPool;
        this.route = route;
    }

    private void connectSocket(int i2, int i3, int i4, Request request, ConnectionSpecSelector connectionSpecSelector) throws Throwable {
        this.socket.setSoTimeout(i3);
        Platform.get().connectSocket(this.socket, this.route.getSocketAddress(), i2);
        if (this.route.f19905a.getSslSocketFactory() != null) {
            connectTls(i3, i4, request, connectionSpecSelector);
        }
        Protocol protocol = this.protocol;
        if (protocol != Protocol.SPDY_3 && protocol != Protocol.HTTP_2) {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
            return;
        }
        this.socket.setSoTimeout(0);
        FramedConnection framedConnectionBuild = new FramedConnection.Builder(this.route.f19905a.f19848b, true, this.socket).protocol(this.protocol).build();
        this.framedConnection = framedConnectionBuild;
        framedConnectionBuild.sendConnectionPreface();
    }

    private void connectTls(int i2, int i3, Request request, ConnectionSpecSelector connectionSpecSelector) throws Throwable {
        if (this.route.requiresTunnel()) {
            createTunnel(i2, i3, request);
        }
        Address address = this.route.getAddress();
        SSLSocket sSLSocket = null;
        try {
            try {
                SSLSocket sSLSocket2 = (SSLSocket) address.getSslSocketFactory().createSocket(this.socket, address.getUriHost(), address.getUriPort(), true);
                try {
                    ConnectionSpec connectionSpecConfigureSecureSocket = connectionSpecSelector.configureSecureSocket(sSLSocket2);
                    if (connectionSpecConfigureSecureSocket.supportsTlsExtensions()) {
                        Platform.get().configureTlsExtensions(sSLSocket2, address.getUriHost(), address.getProtocols());
                    }
                    sSLSocket2.startHandshake();
                    Handshake handshake = Handshake.get(sSLSocket2.getSession());
                    if (address.getHostnameVerifier().verify(address.getUriHost(), sSLSocket2.getSession())) {
                        address.getCertificatePinner().check(address.getUriHost(), handshake.peerCertificates());
                        String selectedProtocol = connectionSpecConfigureSecureSocket.supportsTlsExtensions() ? Platform.get().getSelectedProtocol(sSLSocket2) : null;
                        this.protocol = selectedProtocol != null ? Protocol.get(selectedProtocol) : Protocol.HTTP_1_1;
                        this.handshake = handshake;
                        this.socket = sSLSocket2;
                        Platform.get().afterHandshake(sSLSocket2);
                        return;
                    }
                    X509Certificate x509Certificate = (X509Certificate) handshake.peerCertificates().get(0);
                    throw new SSLPeerUnverifiedException("Hostname " + address.getUriHost() + " not verified:\n    certificate: " + CertificatePinner.pin(x509Certificate) + "\n    DN: " + x509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(x509Certificate));
                } catch (AssertionError e2) {
                    e = e2;
                    if (!Util.isAndroidGetsocknameError(e)) {
                        throw e;
                    }
                    throw new IOException(e);
                } catch (Throwable th) {
                    th = th;
                    sSLSocket = sSLSocket2;
                    if (sSLSocket != null) {
                        Platform.get().afterHandshake(sSLSocket);
                    }
                    Util.closeQuietly((Socket) sSLSocket);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (AssertionError e3) {
            e = e3;
        }
    }

    private void createTunnel(int i2, int i3, Request request) throws IOException {
        Request requestCreateTunnelRequest = createTunnelRequest(request);
        HttpConnection httpConnection = new HttpConnection(this.pool, this, this.socket);
        httpConnection.setTimeouts(i2, i3);
        HttpUrl httpUrl = requestCreateTunnelRequest.httpUrl();
        String str = "CONNECT " + httpUrl.host() + ":" + httpUrl.port() + " HTTP/1.1";
        do {
            httpConnection.writeRequest(requestCreateTunnelRequest.headers(), str);
            httpConnection.flush();
            Response responseBuild = httpConnection.readResponse().request(requestCreateTunnelRequest).build();
            long jContentLength = OkHeaders.contentLength(responseBuild);
            if (jContentLength == -1) {
                jContentLength = 0;
            }
            Source sourceNewFixedLengthSource = httpConnection.newFixedLengthSource(jContentLength);
            Util.skipAll(sourceNewFixedLengthSource, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            sourceNewFixedLengthSource.close();
            int iCode = responseBuild.code();
            if (iCode == 200) {
                if (httpConnection.bufferSize() > 0) {
                    throw new IOException("TLS tunnel buffered too many bytes!");
                }
                return;
            } else {
                if (iCode != 407) {
                    throw new IOException("Unexpected response code for CONNECT: " + responseBuild.code());
                }
                requestCreateTunnelRequest = OkHeaders.processAuthHeader(this.route.getAddress().getAuthenticator(), responseBuild, this.route.getProxy());
            }
        } while (requestCreateTunnelRequest != null);
        throw new IOException("Failed to authenticate with proxy");
    }

    private Request createTunnelRequest(Request request) throws IOException {
        HttpUrl httpUrlBuild = new HttpUrl.Builder().scheme("https").host(request.httpUrl().host()).port(request.httpUrl().port()).build();
        Request.Builder builderHeader = new Request.Builder().url(httpUrlBuild).header("Host", Util.hostHeader(httpUrlBuild)).header("Proxy-Connection", HttpHeaders.KEEP_ALIVE);
        String strHeader = request.header("User-Agent");
        if (strHeader != null) {
            builderHeader.header("User-Agent", strHeader);
        }
        String strHeader2 = request.header(HttpHeaders.PROXY_AUTHORIZATION);
        if (strHeader2 != null) {
            builderHeader.header(HttpHeaders.PROXY_AUTHORIZATION, strHeader2);
        }
        return builderHeader.build();
    }

    boolean a() {
        synchronized (this.pool) {
            try {
                if (this.owner == null) {
                    return false;
                }
                this.owner = null;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void b(Object obj) {
        if (i()) {
            throw new IllegalStateException();
        }
        synchronized (this.pool) {
            try {
                if (this.owner != obj) {
                    return;
                }
                this.owner = null;
                Socket socket = this.socket;
                if (socket != null) {
                    socket.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void c(int i2, int i3, int i4, Request request, List list, boolean z2) throws Throwable {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        ConnectionSpecSelector connectionSpecSelector = new ConnectionSpecSelector(list);
        Proxy proxy = this.route.getProxy();
        Address address = this.route.getAddress();
        if (this.route.f19905a.getSslSocketFactory() == null && !list.contains(ConnectionSpec.CLEARTEXT)) {
            throw new RouteException(new UnknownServiceException("CLEARTEXT communication not supported: " + list));
        }
        RouteException routeException = null;
        while (!this.connected) {
            try {
                this.socket = (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.HTTP) ? address.getSocketFactory().createSocket() : new Socket(proxy);
                connectSocket(i2, i3, i4, request, connectionSpecSelector);
                this.connected = true;
            } catch (IOException e2) {
                Util.closeQuietly(this.socket);
                this.socket = null;
                if (routeException == null) {
                    routeException = new RouteException(e2);
                } else {
                    routeException.addConnectException(e2);
                }
                if (!z2) {
                    throw routeException;
                }
                if (!connectionSpecSelector.connectionFailed(e2)) {
                    throw routeException;
                }
            }
        }
    }

    void d(OkHttpClient okHttpClient, Object obj, Request request) {
        q(obj);
        if (!h()) {
            c(okHttpClient.getConnectTimeout(), okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout(), request, this.route.f19905a.getConnectionSpecs(), okHttpClient.getRetryOnConnectionFailure());
            if (i()) {
                okHttpClient.getConnectionPool().d(this);
            }
            okHttpClient.e().connected(getRoute());
        }
        s(okHttpClient.getReadTimeout(), okHttpClient.getWriteTimeout());
    }

    long e() {
        FramedConnection framedConnection = this.framedConnection;
        return framedConnection == null ? this.idleStartTimeNs : framedConnection.getIdleStartTimeNs();
    }

    void f() {
        this.recycleCount++;
    }

    boolean g() {
        return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    boolean h() {
        return this.connected;
    }

    boolean i() {
        return this.framedConnection != null;
    }

    boolean j() {
        FramedConnection framedConnection = this.framedConnection;
        return framedConnection == null || framedConnection.isIdle();
    }

    boolean k() {
        HttpConnection httpConnection = this.httpConnection;
        if (httpConnection != null) {
            return httpConnection.isReadable();
        }
        return true;
    }

    Transport l(HttpEngine httpEngine) {
        return this.framedConnection != null ? new FramedTransport(httpEngine, this.framedConnection) : new HttpTransport(httpEngine, this.httpConnection);
    }

    BufferedSink m() {
        HttpConnection httpConnection = this.httpConnection;
        if (httpConnection != null) {
            return httpConnection.rawSink();
        }
        throw new UnsupportedOperationException();
    }

    BufferedSource n() {
        HttpConnection httpConnection = this.httpConnection;
        if (httpConnection != null) {
            return httpConnection.rawSource();
        }
        throw new UnsupportedOperationException();
    }

    int o() {
        return this.recycleCount;
    }

    void p() {
        if (this.framedConnection != null) {
            throw new IllegalStateException("framedConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    void q(Object obj) {
        if (i()) {
            return;
        }
        synchronized (this.pool) {
            try {
                if (this.owner != null) {
                    throw new IllegalStateException("Connection already has an owner!");
                }
                this.owner = obj;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void r(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    void s(int i2, int i3) throws RouteException, SocketException {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        }
        if (this.httpConnection != null) {
            try {
                this.socket.setSoTimeout(i2);
                this.httpConnection.setTimeouts(i2, i3);
            } catch (IOException e2) {
                throw new RouteException(e2);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.route.f19905a.f19848b);
        sb.append(":");
        sb.append(this.route.f19905a.f19849c);
        sb.append(", proxy=");
        sb.append(this.route.f19906b);
        sb.append(" hostAddress=");
        sb.append(this.route.f19907c.getAddress().getHostAddress());
        sb.append(" cipherSuite=");
        Handshake handshake = this.handshake;
        sb.append(handshake != null ? handshake.cipherSuite() : "none");
        sb.append(" protocol=");
        sb.append(this.protocol);
        sb.append('}');
        return sb.toString();
    }
}

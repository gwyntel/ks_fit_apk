package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.framed.FrameReader;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

/* loaded from: classes4.dex */
public final class FramedConnection implements Closeable {
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp FramedConnection", true));

    /* renamed from: a, reason: collision with root package name */
    final Protocol f19922a;

    /* renamed from: b, reason: collision with root package name */
    final boolean f19923b;

    /* renamed from: c, reason: collision with root package name */
    long f19924c;
    private final Set<Integer> currentPushRequests;

    /* renamed from: d, reason: collision with root package name */
    long f19925d;

    /* renamed from: e, reason: collision with root package name */
    final Settings f19926e;

    /* renamed from: f, reason: collision with root package name */
    final Settings f19927f;

    /* renamed from: g, reason: collision with root package name */
    final Variant f19928g;

    /* renamed from: h, reason: collision with root package name */
    final Socket f19929h;
    private final IncomingStreamHandler handler;
    private final String hostName;

    /* renamed from: i, reason: collision with root package name */
    final FrameWriter f19930i;
    private long idleStartTimeNs;

    /* renamed from: j, reason: collision with root package name */
    final Reader f19931j;
    private int lastGoodStreamId;
    private int nextPingId;
    private int nextStreamId;
    private Map<Integer, Ping> pings;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    private final Map<Integer, FramedStream> streams;

    public static class Builder {
        private boolean client;
        private IncomingStreamHandler handler;
        private String hostName;
        private Protocol protocol;
        private PushObserver pushObserver;
        private Socket socket;

        public Builder(boolean z2, Socket socket) throws IOException {
            this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), z2, socket);
        }

        public FramedConnection build() throws IOException {
            return new FramedConnection(this);
        }

        public Builder handler(IncomingStreamHandler incomingStreamHandler) {
            this.handler = incomingStreamHandler;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }

        public Builder(String str, boolean z2, Socket socket) throws IOException {
            this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
            this.protocol = Protocol.SPDY_3;
            this.pushObserver = PushObserver.CANCEL;
            this.hostName = str;
            this.client = z2;
            this.socket = socket;
        }
    }

    class Reader extends NamedRunnable implements FrameReader.Handler {

        /* renamed from: b, reason: collision with root package name */
        FrameReader f19958b;

        private void ackSettingsLater(final Settings settings) {
            FramedConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{FramedConnection.this.hostName}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.Reader.2
                @Override // com.squareup.okhttp.internal.NamedRunnable
                public void execute() {
                    try {
                        FramedConnection.this.f19930i.ackSettings(settings);
                    } catch (IOException unused) {
                    }
                }
            });
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void ackSettings() {
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void alternateService(int i2, String str, ByteString byteString, String str2, int i3, long j2) {
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void data(boolean z2, int i2, BufferedSource bufferedSource, int i3) throws IOException {
            if (FramedConnection.this.pushedStream(i2)) {
                FramedConnection.this.pushDataLater(i2, bufferedSource, i3, z2);
                return;
            }
            FramedStream framedStreamX = FramedConnection.this.x(i2);
            if (framedStreamX == null) {
                FramedConnection.this.B(i2, ErrorCode.INVALID_STREAM);
                bufferedSource.skip(i3);
            } else {
                framedStreamX.j(bufferedSource, i3);
                if (z2) {
                    framedStreamX.k();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.squareup.okhttp.internal.NamedRunnable
        protected void execute() throws Throwable {
            ErrorCode errorCode;
            ErrorCode errorCode2;
            ErrorCode errorCode3;
            ErrorCode errorCode4 = ErrorCode.INTERNAL_ERROR;
            try {
                try {
                    FramedConnection framedConnection = FramedConnection.this;
                    FrameReader frameReaderNewReader = framedConnection.f19928g.newReader(Okio.buffer(Okio.source(framedConnection.f19929h)), FramedConnection.this.f19923b);
                    this.f19958b = frameReaderNewReader;
                    if (!FramedConnection.this.f19923b) {
                        frameReaderNewReader.readConnectionPreface();
                    }
                    while (this.f19958b.nextFrame(this)) {
                    }
                    errorCode3 = ErrorCode.NO_ERROR;
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    errorCode = errorCode4;
                    try {
                        FramedConnection.this.close(errorCode, errorCode4);
                    } catch (IOException unused2) {
                    }
                    Util.closeQuietly(this.f19958b);
                    throw th;
                }
                try {
                    errorCode4 = ErrorCode.CANCEL;
                    FramedConnection.this.close(errorCode3, errorCode4);
                    errorCode2 = errorCode3;
                } catch (IOException unused3) {
                    errorCode4 = ErrorCode.PROTOCOL_ERROR;
                    FramedConnection framedConnection2 = FramedConnection.this;
                    framedConnection2.close(errorCode4, errorCode4);
                    errorCode2 = framedConnection2;
                    Util.closeQuietly(this.f19958b);
                }
                Util.closeQuietly(this.f19958b);
            } catch (Throwable th2) {
                errorCode = errorCode2;
                th = th2;
                FramedConnection.this.close(errorCode, errorCode4);
                Util.closeQuietly(this.f19958b);
                throw th;
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void goAway(int i2, ErrorCode errorCode, ByteString byteString) {
            FramedStream[] framedStreamArr;
            byteString.size();
            synchronized (FramedConnection.this) {
                framedStreamArr = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                FramedConnection.this.shutdown = true;
            }
            for (FramedStream framedStream : framedStreamArr) {
                if (framedStream.getId() > i2 && framedStream.isLocallyInitiated()) {
                    framedStream.m(ErrorCode.REFUSED_STREAM);
                    FramedConnection.this.y(framedStream.getId());
                }
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void headers(boolean z2, boolean z3, int i2, int i3, List<Header> list, HeadersMode headersMode) {
            if (FramedConnection.this.pushedStream(i2)) {
                FramedConnection.this.pushHeadersLater(i2, list, z3);
                return;
            }
            synchronized (FramedConnection.this) {
                try {
                    if (FramedConnection.this.shutdown) {
                        return;
                    }
                    FramedStream framedStreamX = FramedConnection.this.x(i2);
                    if (framedStreamX != null) {
                        if (headersMode.failIfStreamPresent()) {
                            framedStreamX.closeLater(ErrorCode.PROTOCOL_ERROR);
                            FramedConnection.this.y(i2);
                            return;
                        } else {
                            framedStreamX.l(list, headersMode);
                            if (z3) {
                                framedStreamX.k();
                                return;
                            }
                            return;
                        }
                    }
                    if (headersMode.failIfStreamAbsent()) {
                        FramedConnection.this.B(i2, ErrorCode.INVALID_STREAM);
                        return;
                    }
                    if (i2 <= FramedConnection.this.lastGoodStreamId) {
                        return;
                    }
                    if (i2 % 2 == FramedConnection.this.nextStreamId % 2) {
                        return;
                    }
                    final FramedStream framedStream = new FramedStream(i2, FramedConnection.this, z2, z3, list);
                    FramedConnection.this.lastGoodStreamId = i2;
                    FramedConnection.this.streams.put(Integer.valueOf(i2), framedStream);
                    FramedConnection.executor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{FramedConnection.this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.Reader.1
                        @Override // com.squareup.okhttp.internal.NamedRunnable
                        public void execute() {
                            try {
                                FramedConnection.this.handler.receive(framedStream);
                            } catch (IOException e2) {
                                Internal.logger.log(Level.INFO, "StreamHandler failure for " + FramedConnection.this.hostName, (Throwable) e2);
                                try {
                                    framedStream.close(ErrorCode.PROTOCOL_ERROR);
                                } catch (IOException unused) {
                                }
                            }
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void ping(boolean z2, int i2, int i3) {
            if (!z2) {
                FramedConnection.this.writePingLater(true, i2, i3, null);
                return;
            }
            Ping pingRemovePing = FramedConnection.this.removePing(i2);
            if (pingRemovePing != null) {
                pingRemovePing.b();
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void priority(int i2, int i3, int i4, boolean z2) {
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void pushPromise(int i2, int i3, List<Header> list) {
            FramedConnection.this.pushRequestLater(i3, list);
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void rstStream(int i2, ErrorCode errorCode) {
            if (FramedConnection.this.pushedStream(i2)) {
                FramedConnection.this.pushResetLater(i2, errorCode);
                return;
            }
            FramedStream framedStreamY = FramedConnection.this.y(i2);
            if (framedStreamY != null) {
                framedStreamY.m(errorCode);
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void settings(boolean z2, Settings settings) {
            FramedStream[] framedStreamArr;
            long j2;
            synchronized (FramedConnection.this) {
                try {
                    int iE = FramedConnection.this.f19927f.e(65536);
                    if (z2) {
                        FramedConnection.this.f19927f.a();
                    }
                    FramedConnection.this.f19927f.i(settings);
                    if (FramedConnection.this.getProtocol() == Protocol.HTTP_2) {
                        ackSettingsLater(settings);
                    }
                    int iE2 = FramedConnection.this.f19927f.e(65536);
                    framedStreamArr = null;
                    if (iE2 == -1 || iE2 == iE) {
                        j2 = 0;
                    } else {
                        j2 = iE2 - iE;
                        if (!FramedConnection.this.receivedInitialPeerSettings) {
                            FramedConnection.this.w(j2);
                            FramedConnection.this.receivedInitialPeerSettings = true;
                        }
                        if (!FramedConnection.this.streams.isEmpty()) {
                            framedStreamArr = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                        }
                    }
                } finally {
                }
            }
            if (framedStreamArr == null || j2 == 0) {
                return;
            }
            for (FramedStream framedStream : framedStreamArr) {
                synchronized (framedStream) {
                    framedStream.i(j2);
                }
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader.Handler
        public void windowUpdate(int i2, long j2) {
            if (i2 == 0) {
                synchronized (FramedConnection.this) {
                    FramedConnection framedConnection = FramedConnection.this;
                    framedConnection.f19925d += j2;
                    framedConnection.notifyAll();
                }
                return;
            }
            FramedStream framedStreamX = FramedConnection.this.x(i2);
            if (framedStreamX != null) {
                synchronized (framedStreamX) {
                    framedStreamX.i(j2);
                }
            }
        }

        private Reader() {
            super("OkHttp %s", FramedConnection.this.hostName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushDataLater(final int i2, BufferedSource bufferedSource, final int i3, final boolean z2) throws IOException {
        final Buffer buffer = new Buffer();
        long j2 = i3;
        bufferedSource.require(j2);
        bufferedSource.read(buffer, j2);
        if (buffer.size() == j2) {
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.6
                @Override // com.squareup.okhttp.internal.NamedRunnable
                public void execute() {
                    try {
                        boolean zOnData = FramedConnection.this.pushObserver.onData(i2, buffer, i3, z2);
                        if (zOnData) {
                            FramedConnection.this.f19930i.rstStream(i2, ErrorCode.CANCEL);
                        }
                        if (zOnData || z2) {
                            synchronized (FramedConnection.this) {
                                FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                            }
                        }
                    } catch (IOException unused) {
                    }
                }
            });
            return;
        }
        throw new IOException(buffer.size() + " != " + i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushHeadersLater(final int i2, final List<Header> list, final boolean z2) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.5
            @Override // com.squareup.okhttp.internal.NamedRunnable
            public void execute() {
                boolean zOnHeaders = FramedConnection.this.pushObserver.onHeaders(i2, list, z2);
                if (zOnHeaders) {
                    try {
                        FramedConnection.this.f19930i.rstStream(i2, ErrorCode.CANCEL);
                    } catch (IOException unused) {
                        return;
                    }
                }
                if (zOnHeaders || z2) {
                    synchronized (FramedConnection.this) {
                        FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushRequestLater(final int i2, final List<Header> list) {
        synchronized (this) {
            try {
                if (this.currentPushRequests.contains(Integer.valueOf(i2))) {
                    B(i2, ErrorCode.PROTOCOL_ERROR);
                } else {
                    this.currentPushRequests.add(Integer.valueOf(i2));
                    this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.4
                        @Override // com.squareup.okhttp.internal.NamedRunnable
                        public void execute() {
                            if (FramedConnection.this.pushObserver.onRequest(i2, list)) {
                                try {
                                    FramedConnection.this.f19930i.rstStream(i2, ErrorCode.CANCEL);
                                    synchronized (FramedConnection.this) {
                                        FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                                    }
                                } catch (IOException unused) {
                                }
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushResetLater(final int i2, final ErrorCode errorCode) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.7
            @Override // com.squareup.okhttp.internal.NamedRunnable
            public void execute() {
                FramedConnection.this.pushObserver.onReset(i2, errorCode);
                synchronized (FramedConnection.this) {
                    FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pushedStream(int i2) {
        return this.f19922a == Protocol.HTTP_2 && i2 != 0 && (i2 & 1) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Ping removePing(int i2) {
        Map<Integer, Ping> map;
        map = this.pings;
        return map != null ? map.remove(Integer.valueOf(i2)) : null;
    }

    private synchronized void setIdle(boolean z2) {
        long jNanoTime;
        if (z2) {
            try {
                jNanoTime = System.nanoTime();
            } catch (Throwable th) {
                throw th;
            }
        } else {
            jNanoTime = Long.MAX_VALUE;
        }
        this.idleStartTimeNs = jNanoTime;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePing(boolean z2, int i2, int i3, Ping ping) throws IOException {
        synchronized (this.f19930i) {
            if (ping != null) {
                try {
                    ping.c();
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.f19930i.ping(z2, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePingLater(final boolean z2, final int i2, final int i3, final Ping ping) {
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(i2), Integer.valueOf(i3)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.3
            @Override // com.squareup.okhttp.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.writePing(z2, i2, i3, ping);
                } catch (IOException unused) {
                }
            }
        });
    }

    void A(int i2, ErrorCode errorCode) throws IOException {
        this.f19930i.rstStream(i2, errorCode);
    }

    void B(final int i2, final ErrorCode errorCode) {
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.1
            @Override // com.squareup.okhttp.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.A(i2, errorCode);
                } catch (IOException unused) {
                }
            }
        });
    }

    void C(final int i2, final long j2) {
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(i2)}) { // from class: com.squareup.okhttp.internal.framed.FramedConnection.2
            @Override // com.squareup.okhttp.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.f19930i.windowUpdate(i2, j2);
                } catch (IOException unused) {
                }
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    public void flush() throws IOException {
        this.f19930i.flush();
    }

    public synchronized long getIdleStartTimeNs() {
        return this.idleStartTimeNs;
    }

    public Protocol getProtocol() {
        return this.f19922a;
    }

    public synchronized boolean isIdle() {
        return this.idleStartTimeNs != Long.MAX_VALUE;
    }

    public FramedStream newStream(List<Header> list, boolean z2, boolean z3) throws IOException {
        return newStream(0, list, z2, z3);
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    public Ping ping() throws IOException {
        int i2;
        Ping ping = new Ping();
        synchronized (this) {
            try {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                i2 = this.nextPingId;
                this.nextPingId = i2 + 2;
                if (this.pings == null) {
                    this.pings = new HashMap();
                }
                this.pings.put(Integer.valueOf(i2), ping);
            } catch (Throwable th) {
                throw th;
            }
        }
        writePing(false, i2, 1330343787, ping);
        return ping;
    }

    public FramedStream pushStream(int i2, List<Header> list, boolean z2) throws IOException {
        if (this.f19923b) {
            throw new IllegalStateException("Client cannot push requests.");
        }
        if (this.f19922a == Protocol.HTTP_2) {
            return newStream(i2, list, z2, false);
        }
        throw new IllegalStateException("protocol != HTTP_2");
    }

    public void sendConnectionPreface() throws IOException {
        this.f19930i.connectionPreface();
        this.f19930i.settings(this.f19926e);
        if (this.f19926e.e(65536) != 65536) {
            this.f19930i.windowUpdate(0, r0 - 65536);
        }
    }

    public void shutdown(ErrorCode errorCode) throws IOException {
        synchronized (this.f19930i) {
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                this.f19930i.goAway(this.lastGoodStreamId, errorCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    void w(long j2) {
        this.f19925d += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
    
        r2 = java.lang.Math.min((int) java.lang.Math.min(r12, r4), r8.f19930i.maxDataLength());
        r6 = r2;
        r8.f19925d -= r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeData(int r9, boolean r10, okio.Buffer r11, long r12) throws java.io.IOException {
        /*
            r8 = this;
            r0 = 0
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            r3 = 0
            if (r2 != 0) goto Ld
            com.squareup.okhttp.internal.framed.FrameWriter r12 = r8.f19930i
            r12.data(r10, r9, r11, r3)
            return
        Ld:
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r2 <= 0) goto L60
            monitor-enter(r8)
        L12:
            long r4 = r8.f19925d     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 > 0) goto L32
            java.util.Map<java.lang.Integer, com.squareup.okhttp.internal.framed.FramedStream> r2 = r8.streams     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            boolean r2 = r2.containsKey(r4)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            if (r2 == 0) goto L2a
            r8.wait()     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            goto L12
        L28:
            r9 = move-exception
            goto L5e
        L2a:
            java.io.IOException r9 = new java.io.IOException     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            java.lang.String r10 = "stream closed"
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            throw r9     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
        L32:
            long r4 = java.lang.Math.min(r12, r4)     // Catch: java.lang.Throwable -> L28
            int r2 = (int) r4     // Catch: java.lang.Throwable -> L28
            com.squareup.okhttp.internal.framed.FrameWriter r4 = r8.f19930i     // Catch: java.lang.Throwable -> L28
            int r4 = r4.maxDataLength()     // Catch: java.lang.Throwable -> L28
            int r2 = java.lang.Math.min(r2, r4)     // Catch: java.lang.Throwable -> L28
            long r4 = r8.f19925d     // Catch: java.lang.Throwable -> L28
            long r6 = (long) r2     // Catch: java.lang.Throwable -> L28
            long r4 = r4 - r6
            r8.f19925d = r4     // Catch: java.lang.Throwable -> L28
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L28
            long r12 = r12 - r6
            com.squareup.okhttp.internal.framed.FrameWriter r4 = r8.f19930i
            if (r10 == 0) goto L53
            int r5 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r5 != 0) goto L53
            r5 = 1
            goto L54
        L53:
            r5 = r3
        L54:
            r4.data(r5, r9, r11, r2)
            goto Ld
        L58:
            java.io.InterruptedIOException r9 = new java.io.InterruptedIOException     // Catch: java.lang.Throwable -> L28
            r9.<init>()     // Catch: java.lang.Throwable -> L28
            throw r9     // Catch: java.lang.Throwable -> L28
        L5e:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L28
            throw r9
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedConnection.writeData(int, boolean, okio.Buffer, long):void");
    }

    synchronized FramedStream x(int i2) {
        return this.streams.get(Integer.valueOf(i2));
    }

    synchronized FramedStream y(int i2) {
        FramedStream framedStreamRemove;
        try {
            framedStreamRemove = this.streams.remove(Integer.valueOf(i2));
            if (framedStreamRemove != null && this.streams.isEmpty()) {
                setIdle(true);
            }
            notifyAll();
        } catch (Throwable th) {
            throw th;
        }
        return framedStreamRemove;
    }

    void z(int i2, boolean z2, List list) throws IOException {
        this.f19930i.synReply(z2, i2, list);
    }

    private FramedConnection(Builder builder) throws IOException {
        this.streams = new HashMap();
        this.idleStartTimeNs = System.nanoTime();
        this.f19924c = 0L;
        Settings settings = new Settings();
        this.f19926e = settings;
        Settings settings2 = new Settings();
        this.f19927f = settings2;
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet();
        Protocol protocol = builder.protocol;
        this.f19922a = protocol;
        this.pushObserver = builder.pushObserver;
        boolean z2 = builder.client;
        this.f19923b = z2;
        this.handler = builder.handler;
        this.nextStreamId = builder.client ? 1 : 2;
        if (builder.client && protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        this.nextPingId = builder.client ? 1 : 2;
        if (builder.client) {
            settings.k(7, 0, 16777216);
        }
        String str = builder.hostName;
        this.hostName = str;
        if (protocol == Protocol.HTTP_2) {
            this.f19928g = new Http2();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", str), true));
            settings2.k(7, 0, 65535);
            settings2.k(5, 0, 16384);
        } else {
            if (protocol != Protocol.SPDY_3) {
                throw new AssertionError(protocol);
            }
            this.f19928g = new Spdy3();
            this.pushExecutor = null;
        }
        this.f19925d = settings2.e(65536);
        this.f19929h = builder.socket;
        this.f19930i = this.f19928g.newWriter(Okio.buffer(Okio.sink(builder.socket)), z2);
        Reader reader = new Reader();
        this.f19931j = reader;
        new Thread(reader).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close(ErrorCode errorCode, ErrorCode errorCode2) throws IOException {
        int i2;
        FramedStream[] framedStreamArr;
        Ping[] pingArr = null;
        try {
            shutdown(errorCode);
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        synchronized (this) {
            try {
                if (this.streams.isEmpty()) {
                    framedStreamArr = null;
                } else {
                    framedStreamArr = (FramedStream[]) this.streams.values().toArray(new FramedStream[this.streams.size()]);
                    this.streams.clear();
                    setIdle(false);
                }
                Map<Integer, Ping> map = this.pings;
                if (map != null) {
                    Ping[] pingArr2 = (Ping[]) map.values().toArray(new Ping[this.pings.size()]);
                    this.pings = null;
                    pingArr = pingArr2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (framedStreamArr != null) {
            for (FramedStream framedStream : framedStreamArr) {
                try {
                    framedStream.close(errorCode2);
                } catch (IOException e3) {
                    if (e != null) {
                        e = e3;
                    }
                }
            }
        }
        if (pingArr != null) {
            for (Ping ping : pingArr) {
                ping.a();
            }
        }
        try {
            this.f19930i.close();
        } catch (IOException e4) {
            if (e == null) {
                e = e4;
            }
        }
        try {
            this.f19929h.close();
        } catch (IOException e5) {
            e = e5;
        }
        if (e != null) {
            throw e;
        }
    }

    private FramedStream newStream(int i2, List<Header> list, boolean z2, boolean z3) throws IOException {
        int i3;
        FramedStream framedStream;
        boolean z4 = !z2;
        boolean z5 = !z3;
        synchronized (this.f19930i) {
            try {
                synchronized (this) {
                    try {
                        if (this.shutdown) {
                            throw new IOException("shutdown");
                        }
                        i3 = this.nextStreamId;
                        this.nextStreamId = i3 + 2;
                        framedStream = new FramedStream(i3, this, z4, z5, list);
                        if (framedStream.isOpen()) {
                            this.streams.put(Integer.valueOf(i3), framedStream);
                            setIdle(false);
                        }
                    } finally {
                    }
                }
                if (i2 == 0) {
                    this.f19930i.synStream(z4, z5, i3, i2, list);
                } else {
                    if (this.f19923b) {
                        throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                    }
                    this.f19930i.pushPromise(i2, i3, list);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z2) {
            this.f19930i.flush();
        }
        return framedStream;
    }
}

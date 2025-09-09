package okhttp3.internal.http2;

import androidx.media3.common.C;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Http2Reader;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

/* loaded from: classes5.dex */
public final class Http2Connection implements Closeable {
    private static final ExecutorService listenerExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));

    /* renamed from: a, reason: collision with root package name */
    final boolean f26344a;

    /* renamed from: b, reason: collision with root package name */
    final Listener f26345b;

    /* renamed from: d, reason: collision with root package name */
    final String f26347d;

    /* renamed from: e, reason: collision with root package name */
    int f26348e;

    /* renamed from: f, reason: collision with root package name */
    int f26349f;

    /* renamed from: g, reason: collision with root package name */
    final PushObserver f26350g;

    /* renamed from: i, reason: collision with root package name */
    long f26352i;

    /* renamed from: k, reason: collision with root package name */
    final Settings f26354k;

    /* renamed from: l, reason: collision with root package name */
    final Socket f26355l;

    /* renamed from: m, reason: collision with root package name */
    final Http2Writer f26356m;

    /* renamed from: n, reason: collision with root package name */
    final ReaderRunnable f26357n;

    /* renamed from: o, reason: collision with root package name */
    final Set f26358o;
    private final ExecutorService pushExecutor;
    private boolean shutdown;
    private final ScheduledExecutorService writerExecutor;

    /* renamed from: c, reason: collision with root package name */
    final Map f26346c = new LinkedHashMap();
    private long intervalPingsSent = 0;
    private long intervalPongsReceived = 0;
    private long degradedPingsSent = 0;
    private long degradedPongsReceived = 0;
    private long awaitPingsSent = 0;
    private long awaitPongsReceived = 0;
    private long degradedPongDeadlineNs = 0;

    /* renamed from: h, reason: collision with root package name */
    long f26351h = 0;

    /* renamed from: j, reason: collision with root package name */
    Settings f26353j = new Settings();

    final class IntervalPingRunnable extends NamedRunnable {
        IntervalPingRunnable() {
            super("OkHttp %s ping", Http2Connection.this.f26347d);
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() {
            boolean z2;
            synchronized (Http2Connection.this) {
                if (Http2Connection.this.intervalPongsReceived < Http2Connection.this.intervalPingsSent) {
                    z2 = true;
                } else {
                    Http2Connection.e(Http2Connection.this);
                    z2 = false;
                }
            }
            if (z2) {
                Http2Connection.this.failConnection();
            } else {
                Http2Connection.this.w(false, 1, 0);
            }
        }
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() { // from class: okhttp3.internal.http2.Http2Connection.Listener.1
            @Override // okhttp3.internal.http2.Http2Connection.Listener
            public void onStream(Http2Stream http2Stream) throws IOException {
                http2Stream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public void onSettings(Http2Connection http2Connection) {
        }

        public abstract void onStream(Http2Stream http2Stream) throws IOException;
    }

    final class PingRunnable extends NamedRunnable {

        /* renamed from: b, reason: collision with root package name */
        final boolean f26390b;

        /* renamed from: c, reason: collision with root package name */
        final int f26391c;

        /* renamed from: d, reason: collision with root package name */
        final int f26392d;

        PingRunnable(boolean z2, int i2, int i3) {
            super("OkHttp %s ping %08x%08x", Http2Connection.this.f26347d, Integer.valueOf(i2), Integer.valueOf(i3));
            this.f26390b = z2;
            this.f26391c = i2;
            this.f26392d = i3;
        }

        @Override // okhttp3.internal.NamedRunnable
        public void execute() {
            Http2Connection.this.w(this.f26390b, this.f26391c, this.f26392d);
        }
    }

    class ReaderRunnable extends NamedRunnable implements Http2Reader.Handler {

        /* renamed from: b, reason: collision with root package name */
        final Http2Reader f26394b;

        ReaderRunnable(Http2Reader http2Reader) {
            super("OkHttp %s", Http2Connection.this.f26347d);
            this.f26394b = http2Reader;
        }

        void a(boolean z2, Settings settings) {
            Http2Stream[] http2StreamArr;
            long j2;
            synchronized (Http2Connection.this.f26356m) {
                synchronized (Http2Connection.this) {
                    try {
                        int iD = Http2Connection.this.f26354k.d();
                        if (z2) {
                            Http2Connection.this.f26354k.a();
                        }
                        Http2Connection.this.f26354k.h(settings);
                        int iD2 = Http2Connection.this.f26354k.d();
                        http2StreamArr = null;
                        if (iD2 == -1 || iD2 == iD) {
                            j2 = 0;
                        } else {
                            j2 = iD2 - iD;
                            if (!Http2Connection.this.f26346c.isEmpty()) {
                                http2StreamArr = (Http2Stream[]) Http2Connection.this.f26346c.values().toArray(new Http2Stream[Http2Connection.this.f26346c.size()]);
                            }
                        }
                    } finally {
                    }
                }
                try {
                    Http2Connection http2Connection = Http2Connection.this;
                    http2Connection.f26356m.applyAndAckSettings(http2Connection.f26354k);
                } catch (IOException unused) {
                    Http2Connection.this.failConnection();
                }
            }
            if (http2StreamArr != null) {
                for (Http2Stream http2Stream : http2StreamArr) {
                    synchronized (http2Stream) {
                        http2Stream.c(j2);
                    }
                }
            }
            Http2Connection.listenerExecutor.execute(new NamedRunnable("OkHttp %s settings", Http2Connection.this.f26347d) { // from class: okhttp3.internal.http2.Http2Connection.ReaderRunnable.3
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    Http2Connection http2Connection2 = Http2Connection.this;
                    http2Connection2.f26345b.onSettings(http2Connection2);
                }
            });
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void ackSettings() {
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void alternateService(int i2, String str, ByteString byteString, String str2, int i3, long j2) {
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void data(boolean z2, int i2, BufferedSource bufferedSource, int i3) throws IOException {
            if (Http2Connection.this.r(i2)) {
                Http2Connection.this.n(i2, bufferedSource, i3, z2);
                return;
            }
            Http2Stream http2StreamM = Http2Connection.this.m(i2);
            if (http2StreamM == null) {
                Http2Connection.this.z(i2, ErrorCode.PROTOCOL_ERROR);
                long j2 = i3;
                Http2Connection.this.v(j2);
                bufferedSource.skip(j2);
                return;
            }
            http2StreamM.f(bufferedSource, i3);
            if (z2) {
                http2StreamM.g();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // okhttp3.internal.NamedRunnable
        protected void execute() throws Throwable {
            ErrorCode errorCode;
            ErrorCode errorCode2;
            ErrorCode errorCode3 = ErrorCode.INTERNAL_ERROR;
            try {
                try {
                    this.f26394b.readConnectionPreface(this);
                    while (this.f26394b.nextFrame(false, this)) {
                    }
                    errorCode2 = ErrorCode.NO_ERROR;
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    errorCode = errorCode3;
                    try {
                        Http2Connection.this.l(errorCode, errorCode3);
                    } catch (IOException unused2) {
                    }
                    Util.closeQuietly(this.f26394b);
                    throw th;
                }
                try {
                    errorCode3 = ErrorCode.CANCEL;
                    Http2Connection.this.l(errorCode2, errorCode3);
                    errorCode = errorCode2;
                } catch (IOException unused3) {
                    errorCode3 = ErrorCode.PROTOCOL_ERROR;
                    Http2Connection http2Connection = Http2Connection.this;
                    http2Connection.l(errorCode3, errorCode3);
                    errorCode = http2Connection;
                    Util.closeQuietly(this.f26394b);
                }
                Util.closeQuietly(this.f26394b);
            } catch (Throwable th2) {
                th = th2;
                Http2Connection.this.l(errorCode, errorCode3);
                Util.closeQuietly(this.f26394b);
                throw th;
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void goAway(int i2, ErrorCode errorCode, ByteString byteString) {
            Http2Stream[] http2StreamArr;
            byteString.size();
            synchronized (Http2Connection.this) {
                http2StreamArr = (Http2Stream[]) Http2Connection.this.f26346c.values().toArray(new Http2Stream[Http2Connection.this.f26346c.size()]);
                Http2Connection.this.shutdown = true;
            }
            for (Http2Stream http2Stream : http2StreamArr) {
                if (http2Stream.getId() > i2 && http2Stream.isLocallyInitiated()) {
                    http2Stream.i(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.s(http2Stream.getId());
                }
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void headers(boolean z2, int i2, int i3, List<Header> list) {
            if (Http2Connection.this.r(i2)) {
                Http2Connection.this.o(i2, list, z2);
                return;
            }
            synchronized (Http2Connection.this) {
                try {
                    Http2Stream http2StreamM = Http2Connection.this.m(i2);
                    if (http2StreamM != null) {
                        http2StreamM.h(list);
                        if (z2) {
                            http2StreamM.g();
                            return;
                        }
                        return;
                    }
                    if (Http2Connection.this.shutdown) {
                        return;
                    }
                    Http2Connection http2Connection = Http2Connection.this;
                    if (i2 <= http2Connection.f26348e) {
                        return;
                    }
                    if (i2 % 2 == http2Connection.f26349f % 2) {
                        return;
                    }
                    final Http2Stream http2Stream = new Http2Stream(i2, Http2Connection.this, false, z2, Util.toHeaders(list));
                    Http2Connection http2Connection2 = Http2Connection.this;
                    http2Connection2.f26348e = i2;
                    http2Connection2.f26346c.put(Integer.valueOf(i2), http2Stream);
                    Http2Connection.listenerExecutor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{Http2Connection.this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.ReaderRunnable.1
                        @Override // okhttp3.internal.NamedRunnable
                        public void execute() {
                            try {
                                Http2Connection.this.f26345b.onStream(http2Stream);
                            } catch (IOException e2) {
                                Platform.get().log(4, "Http2Connection.Listener failure for " + Http2Connection.this.f26347d, e2);
                                try {
                                    http2Stream.close(ErrorCode.PROTOCOL_ERROR);
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

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void ping(boolean z2, int i2, int i3) {
            if (!z2) {
                try {
                    Http2Connection.this.writerExecutor.execute(Http2Connection.this.new PingRunnable(true, i2, i3));
                    return;
                } catch (RejectedExecutionException unused) {
                    return;
                }
            }
            synchronized (Http2Connection.this) {
                try {
                    if (i2 == 1) {
                        Http2Connection.c(Http2Connection.this);
                    } else if (i2 == 2) {
                        Http2Connection.j(Http2Connection.this);
                    } else if (i2 == 3) {
                        Http2Connection.k(Http2Connection.this);
                        Http2Connection.this.notifyAll();
                    }
                } finally {
                }
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void priority(int i2, int i3, int i4, boolean z2) {
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void pushPromise(int i2, int i3, List<Header> list) {
            Http2Connection.this.p(i3, list);
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void rstStream(int i2, ErrorCode errorCode) {
            if (Http2Connection.this.r(i2)) {
                Http2Connection.this.q(i2, errorCode);
                return;
            }
            Http2Stream http2StreamS = Http2Connection.this.s(i2);
            if (http2StreamS != null) {
                http2StreamS.i(errorCode);
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void settings(final boolean z2, final Settings settings) {
            try {
                Http2Connection.this.writerExecutor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{Http2Connection.this.f26347d}) { // from class: okhttp3.internal.http2.Http2Connection.ReaderRunnable.2
                    @Override // okhttp3.internal.NamedRunnable
                    public void execute() {
                        ReaderRunnable.this.a(z2, settings);
                    }
                });
            } catch (RejectedExecutionException unused) {
            }
        }

        @Override // okhttp3.internal.http2.Http2Reader.Handler
        public void windowUpdate(int i2, long j2) {
            if (i2 == 0) {
                synchronized (Http2Connection.this) {
                    Http2Connection http2Connection = Http2Connection.this;
                    http2Connection.f26352i += j2;
                    http2Connection.notifyAll();
                }
                return;
            }
            Http2Stream http2StreamM = Http2Connection.this.m(i2);
            if (http2StreamM != null) {
                synchronized (http2StreamM) {
                    http2StreamM.c(j2);
                }
            }
        }
    }

    Http2Connection(Builder builder) {
        Settings settings = new Settings();
        this.f26354k = settings;
        this.f26358o = new LinkedHashSet();
        this.f26350g = builder.f26386f;
        boolean z2 = builder.f26387g;
        this.f26344a = z2;
        this.f26345b = builder.f26385e;
        int i2 = z2 ? 1 : 2;
        this.f26349f = i2;
        if (z2) {
            this.f26349f = i2 + 2;
        }
        if (z2) {
            this.f26353j.i(7, 16777216);
        }
        String str = builder.f26382b;
        this.f26347d = str;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", str), false));
        this.writerExecutor = scheduledThreadPoolExecutor;
        if (builder.f26388h != 0) {
            IntervalPingRunnable intervalPingRunnable = new IntervalPingRunnable();
            int i3 = builder.f26388h;
            scheduledThreadPoolExecutor.scheduleAtFixedRate(intervalPingRunnable, i3, i3, TimeUnit.MILLISECONDS);
        }
        this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", str), true));
        settings.i(7, 65535);
        settings.i(5, 16384);
        this.f26352i = settings.d();
        this.f26355l = builder.f26381a;
        this.f26356m = new Http2Writer(builder.f26384d, z2);
        this.f26357n = new ReaderRunnable(new Http2Reader(builder.f26383c, z2));
    }

    static /* synthetic */ long c(Http2Connection http2Connection) {
        long j2 = http2Connection.intervalPongsReceived;
        http2Connection.intervalPongsReceived = 1 + j2;
        return j2;
    }

    static /* synthetic */ long e(Http2Connection http2Connection) {
        long j2 = http2Connection.intervalPingsSent;
        http2Connection.intervalPingsSent = 1 + j2;
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void failConnection() {
        try {
            ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
            l(errorCode, errorCode);
        } catch (IOException unused) {
        }
    }

    static /* synthetic */ long j(Http2Connection http2Connection) {
        long j2 = http2Connection.degradedPongsReceived;
        http2Connection.degradedPongsReceived = 1 + j2;
        return j2;
    }

    static /* synthetic */ long k(Http2Connection http2Connection) {
        long j2 = http2Connection.awaitPongsReceived;
        http2Connection.awaitPongsReceived = 1 + j2;
        return j2;
    }

    private synchronized void pushExecutorExecute(NamedRunnable namedRunnable) {
        if (!this.shutdown) {
            this.pushExecutor.execute(namedRunnable);
        }
    }

    void A(final int i2, final long j2) {
        try {
            this.writerExecutor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.2
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    try {
                        Http2Connection.this.f26356m.windowUpdate(i2, j2);
                    } catch (IOException unused) {
                        Http2Connection.this.failConnection();
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        l(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    public void flush() throws IOException {
        this.f26356m.flush();
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public synchronized boolean isHealthy(long j2) {
        if (this.shutdown) {
            return false;
        }
        if (this.degradedPongsReceived < this.degradedPingsSent) {
            if (j2 >= this.degradedPongDeadlineNs) {
                return false;
            }
        }
        return true;
    }

    void l(ErrorCode errorCode, ErrorCode errorCode2) throws IOException {
        Http2Stream[] http2StreamArr = null;
        try {
            shutdown(errorCode);
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        synchronized (this) {
            try {
                if (!this.f26346c.isEmpty()) {
                    http2StreamArr = (Http2Stream[]) this.f26346c.values().toArray(new Http2Stream[this.f26346c.size()]);
                    this.f26346c.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (http2StreamArr != null) {
            for (Http2Stream http2Stream : http2StreamArr) {
                try {
                    http2Stream.close(errorCode2);
                } catch (IOException e3) {
                    if (e != null) {
                        e = e3;
                    }
                }
            }
        }
        try {
            this.f26356m.close();
        } catch (IOException e4) {
            if (e == null) {
                e = e4;
            }
        }
        try {
            this.f26355l.close();
        } catch (IOException e5) {
            e = e5;
        }
        this.writerExecutor.shutdown();
        this.pushExecutor.shutdown();
        if (e != null) {
            throw e;
        }
    }

    synchronized Http2Stream m(int i2) {
        return (Http2Stream) this.f26346c.get(Integer.valueOf(i2));
    }

    public synchronized int maxConcurrentStreams() {
        return this.f26354k.e(Integer.MAX_VALUE);
    }

    void n(final int i2, BufferedSource bufferedSource, final int i3, final boolean z2) throws IOException {
        final Buffer buffer = new Buffer();
        long j2 = i3;
        bufferedSource.require(j2);
        bufferedSource.read(buffer, j2);
        if (buffer.size() == j2) {
            pushExecutorExecute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.6
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    try {
                        boolean zOnData = Http2Connection.this.f26350g.onData(i2, buffer, i3, z2);
                        if (zOnData) {
                            Http2Connection.this.f26356m.rstStream(i2, ErrorCode.CANCEL);
                        }
                        if (zOnData || z2) {
                            synchronized (Http2Connection.this) {
                                Http2Connection.this.f26358o.remove(Integer.valueOf(i2));
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

    public Http2Stream newStream(List<Header> list, boolean z2) throws IOException {
        return newStream(0, list, z2);
    }

    void o(final int i2, final List list, final boolean z2) {
        try {
            pushExecutorExecute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.5
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    boolean zOnHeaders = Http2Connection.this.f26350g.onHeaders(i2, list, z2);
                    if (zOnHeaders) {
                        try {
                            Http2Connection.this.f26356m.rstStream(i2, ErrorCode.CANCEL);
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    if (zOnHeaders || z2) {
                        synchronized (Http2Connection.this) {
                            Http2Connection.this.f26358o.remove(Integer.valueOf(i2));
                        }
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    public synchronized int openStreamCount() {
        return this.f26346c.size();
    }

    void p(final int i2, final List list) {
        synchronized (this) {
            try {
                if (this.f26358o.contains(Integer.valueOf(i2))) {
                    z(i2, ErrorCode.PROTOCOL_ERROR);
                    return;
                }
                this.f26358o.add(Integer.valueOf(i2));
                try {
                    pushExecutorExecute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.4
                        @Override // okhttp3.internal.NamedRunnable
                        public void execute() {
                            if (Http2Connection.this.f26350g.onRequest(i2, list)) {
                                try {
                                    Http2Connection.this.f26356m.rstStream(i2, ErrorCode.CANCEL);
                                    synchronized (Http2Connection.this) {
                                        Http2Connection.this.f26358o.remove(Integer.valueOf(i2));
                                    }
                                } catch (IOException unused) {
                                }
                            }
                        }
                    });
                } catch (RejectedExecutionException unused) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Http2Stream pushStream(int i2, List<Header> list, boolean z2) throws IOException {
        if (this.f26344a) {
            throw new IllegalStateException("Client cannot push requests.");
        }
        return newStream(i2, list, z2);
    }

    void q(final int i2, final ErrorCode errorCode) {
        pushExecutorExecute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.7
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                Http2Connection.this.f26350g.onReset(i2, errorCode);
                synchronized (Http2Connection.this) {
                    Http2Connection.this.f26358o.remove(Integer.valueOf(i2));
                }
            }
        });
    }

    boolean r(int i2) {
        return i2 != 0 && (i2 & 1) == 0;
    }

    synchronized Http2Stream s(int i2) {
        Http2Stream http2Stream;
        http2Stream = (Http2Stream) this.f26346c.remove(Integer.valueOf(i2));
        notifyAll();
        return http2Stream;
    }

    public void setSettings(Settings settings) throws IOException {
        synchronized (this.f26356m) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new ConnectionShutdownException();
                }
                this.f26353j.h(settings);
            }
            this.f26356m.settings(settings);
        }
    }

    public void shutdown(ErrorCode errorCode) throws IOException {
        synchronized (this.f26356m) {
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                this.f26356m.goAway(this.f26348e, errorCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    public void start() throws IOException {
        u(true);
    }

    void t() {
        synchronized (this) {
            try {
                long j2 = this.degradedPongsReceived;
                long j3 = this.degradedPingsSent;
                if (j2 < j3) {
                    return;
                }
                this.degradedPingsSent = j3 + 1;
                this.degradedPongDeadlineNs = System.nanoTime() + C.NANOS_PER_SECOND;
                try {
                    this.writerExecutor.execute(new NamedRunnable("OkHttp %s ping", this.f26347d) { // from class: okhttp3.internal.http2.Http2Connection.3
                        @Override // okhttp3.internal.NamedRunnable
                        public void execute() {
                            Http2Connection.this.w(false, 2, 0);
                        }
                    });
                } catch (RejectedExecutionException unused) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void u(boolean z2) throws IOException {
        if (z2) {
            this.f26356m.connectionPreface();
            this.f26356m.settings(this.f26353j);
            if (this.f26353j.d() != 65535) {
                this.f26356m.windowUpdate(0, r5 - 65535);
            }
        }
        new Thread(this.f26357n).start();
    }

    synchronized void v(long j2) {
        long j3 = this.f26351h + j2;
        this.f26351h = j3;
        if (j3 >= this.f26353j.d() / 2) {
            A(0, this.f26351h);
            this.f26351h = 0L;
        }
    }

    void w(boolean z2, int i2, int i3) {
        try {
            this.f26356m.ping(z2, i2, i3);
        } catch (IOException unused) {
            failConnection();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
    
        r2 = java.lang.Math.min((int) java.lang.Math.min(r12, r4), r8.f26356m.maxDataLength());
        r6 = r2;
        r8.f26352i -= r6;
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
            okhttp3.internal.http2.Http2Writer r12 = r8.f26356m
            r12.data(r10, r9, r11, r3)
            return
        Ld:
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r2 <= 0) goto L67
            monitor-enter(r8)
        L12:
            long r4 = r8.f26352i     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 > 0) goto L32
            java.util.Map r2 = r8.f26346c     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            boolean r2 = r2.containsKey(r4)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            if (r2 == 0) goto L2a
            r8.wait()     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            goto L12
        L28:
            r9 = move-exception
            goto L65
        L2a:
            java.io.IOException r9 = new java.io.IOException     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            java.lang.String r10 = "stream closed"
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
            throw r9     // Catch: java.lang.Throwable -> L28 java.lang.InterruptedException -> L58
        L32:
            long r4 = java.lang.Math.min(r12, r4)     // Catch: java.lang.Throwable -> L28
            int r2 = (int) r4     // Catch: java.lang.Throwable -> L28
            okhttp3.internal.http2.Http2Writer r4 = r8.f26356m     // Catch: java.lang.Throwable -> L28
            int r4 = r4.maxDataLength()     // Catch: java.lang.Throwable -> L28
            int r2 = java.lang.Math.min(r2, r4)     // Catch: java.lang.Throwable -> L28
            long r4 = r8.f26352i     // Catch: java.lang.Throwable -> L28
            long r6 = (long) r2     // Catch: java.lang.Throwable -> L28
            long r4 = r4 - r6
            r8.f26352i = r4     // Catch: java.lang.Throwable -> L28
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L28
            long r12 = r12 - r6
            okhttp3.internal.http2.Http2Writer r4 = r8.f26356m
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
            java.lang.Thread r9 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L28
            r9.interrupt()     // Catch: java.lang.Throwable -> L28
            java.io.InterruptedIOException r9 = new java.io.InterruptedIOException     // Catch: java.lang.Throwable -> L28
            r9.<init>()     // Catch: java.lang.Throwable -> L28
            throw r9     // Catch: java.lang.Throwable -> L28
        L65:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L28
            throw r9
        L67:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.writeData(int, boolean, okio.Buffer, long):void");
    }

    void x(int i2, boolean z2, List list) {
        this.f26356m.synReply(z2, i2, list);
    }

    void y(int i2, ErrorCode errorCode) throws IOException {
        this.f26356m.rstStream(i2, errorCode);
    }

    void z(final int i2, final ErrorCode errorCode) {
        try {
            this.writerExecutor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.f26347d, Integer.valueOf(i2)}) { // from class: okhttp3.internal.http2.Http2Connection.1
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    try {
                        Http2Connection.this.y(i2, errorCode);
                    } catch (IOException unused) {
                        Http2Connection.this.failConnection();
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
        }
    }

    private Http2Stream newStream(int i2, List<Header> list, boolean z2) throws IOException {
        int i3;
        Http2Stream http2Stream;
        boolean z3;
        boolean z4 = !z2;
        synchronized (this.f26356m) {
            try {
                synchronized (this) {
                    try {
                        if (this.f26349f > 1073741823) {
                            shutdown(ErrorCode.REFUSED_STREAM);
                        }
                        if (this.shutdown) {
                            throw new ConnectionShutdownException();
                        }
                        i3 = this.f26349f;
                        this.f26349f = i3 + 2;
                        http2Stream = new Http2Stream(i3, this, z4, false, null);
                        z3 = !z2 || this.f26352i == 0 || http2Stream.f26410b == 0;
                        if (http2Stream.isOpen()) {
                            this.f26346c.put(Integer.valueOf(i3), http2Stream);
                        }
                    } finally {
                    }
                }
                if (i2 == 0) {
                    this.f26356m.synStream(z4, i3, i2, list);
                } else {
                    if (this.f26344a) {
                        throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                    }
                    this.f26356m.pushPromise(i2, i3, list);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z3) {
            this.f26356m.flush();
        }
        return http2Stream;
    }

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        Socket f26381a;

        /* renamed from: b, reason: collision with root package name */
        String f26382b;

        /* renamed from: c, reason: collision with root package name */
        BufferedSource f26383c;

        /* renamed from: d, reason: collision with root package name */
        BufferedSink f26384d;

        /* renamed from: e, reason: collision with root package name */
        Listener f26385e = Listener.REFUSE_INCOMING_STREAMS;

        /* renamed from: f, reason: collision with root package name */
        PushObserver f26386f = PushObserver.CANCEL;

        /* renamed from: g, reason: collision with root package name */
        boolean f26387g;

        /* renamed from: h, reason: collision with root package name */
        int f26388h;

        public Builder(boolean z2) {
            this.f26387g = z2;
        }

        public Http2Connection build() {
            return new Http2Connection(this);
        }

        public Builder listener(Listener listener) {
            this.f26385e = listener;
            return this;
        }

        public Builder pingIntervalMillis(int i2) {
            this.f26388h = i2;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.f26386f = pushObserver;
            return this;
        }

        public Builder socket(Socket socket) throws IOException {
            return socket(socket, ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String str, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.f26381a = socket;
            this.f26382b = str;
            this.f26383c = bufferedSource;
            this.f26384d = bufferedSink;
            return this;
        }
    }
}

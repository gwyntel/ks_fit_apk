package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Header;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes5.dex */
public final class Http2Stream {

    /* renamed from: a, reason: collision with root package name */
    long f26409a = 0;

    /* renamed from: b, reason: collision with root package name */
    long f26410b;

    /* renamed from: c, reason: collision with root package name */
    final int f26411c;

    /* renamed from: d, reason: collision with root package name */
    final Http2Connection f26412d;

    /* renamed from: e, reason: collision with root package name */
    final FramingSink f26413e;

    /* renamed from: f, reason: collision with root package name */
    final StreamTimeout f26414f;

    /* renamed from: g, reason: collision with root package name */
    final StreamTimeout f26415g;

    /* renamed from: h, reason: collision with root package name */
    ErrorCode f26416h;
    private boolean hasResponseHeaders;
    private Header.Listener headersListener;
    private final Deque<Headers> headersQueue;
    private final FramingSource source;

    final class FramingSink implements Sink {
        private static final long EMIT_BUFFER_SIZE = 16384;

        /* renamed from: a, reason: collision with root package name */
        boolean f26417a;

        /* renamed from: b, reason: collision with root package name */
        boolean f26418b;
        private final Buffer sendBuffer = new Buffer();

        FramingSink() {
        }

        private void emitFrame(boolean z2) throws IOException {
            Http2Stream http2Stream;
            long jMin;
            Http2Stream http2Stream2;
            synchronized (Http2Stream.this) {
                Http2Stream.this.f26415g.enter();
                while (true) {
                    try {
                        http2Stream = Http2Stream.this;
                        if (http2Stream.f26410b > 0 || this.f26418b || this.f26417a || http2Stream.f26416h != null) {
                            break;
                        } else {
                            http2Stream.j();
                        }
                    } finally {
                        Http2Stream.this.f26415g.exitAndThrowIfTimedOut();
                    }
                }
                http2Stream.f26415g.exitAndThrowIfTimedOut();
                Http2Stream.this.e();
                jMin = Math.min(Http2Stream.this.f26410b, this.sendBuffer.size());
                http2Stream2 = Http2Stream.this;
                http2Stream2.f26410b -= jMin;
            }
            http2Stream2.f26415g.enter();
            try {
                Http2Stream http2Stream3 = Http2Stream.this;
                http2Stream3.f26412d.writeData(http2Stream3.f26411c, z2 && jMin == this.sendBuffer.size(), this.sendBuffer, jMin);
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (Http2Stream.this) {
                try {
                    if (this.f26417a) {
                        return;
                    }
                    if (!Http2Stream.this.f26413e.f26418b) {
                        if (this.sendBuffer.size() > 0) {
                            while (this.sendBuffer.size() > 0) {
                                emitFrame(true);
                            }
                        } else {
                            Http2Stream http2Stream = Http2Stream.this;
                            http2Stream.f26412d.writeData(http2Stream.f26411c, true, null, 0L);
                        }
                    }
                    synchronized (Http2Stream.this) {
                        this.f26417a = true;
                    }
                    Http2Stream.this.f26412d.flush();
                    Http2Stream.this.d();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            synchronized (Http2Stream.this) {
                Http2Stream.this.e();
            }
            while (this.sendBuffer.size() > 0) {
                emitFrame(false);
                Http2Stream.this.f26412d.flush();
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return Http2Stream.this.f26415g;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            this.sendBuffer.write(buffer, j2);
            while (this.sendBuffer.size() >= 16384) {
                emitFrame(false);
            }
        }
    }

    private final class FramingSource implements Source {

        /* renamed from: a, reason: collision with root package name */
        boolean f26420a;

        /* renamed from: b, reason: collision with root package name */
        boolean f26421b;
        private final long maxByteCount;
        private final Buffer receiveBuffer = new Buffer();
        private final Buffer readBuffer = new Buffer();

        FramingSource(long j2) {
            this.maxByteCount = j2;
        }

        private void updateConnectionFlowControl(long j2) {
            Http2Stream.this.f26412d.v(j2);
        }

        void a(BufferedSource bufferedSource, long j2) throws IOException {
            boolean z2;
            boolean z3;
            long size;
            while (j2 > 0) {
                synchronized (Http2Stream.this) {
                    z2 = this.f26421b;
                    z3 = this.readBuffer.size() + j2 > this.maxByteCount;
                }
                if (z3) {
                    bufferedSource.skip(j2);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (z2) {
                    bufferedSource.skip(j2);
                    return;
                }
                long j3 = bufferedSource.read(this.receiveBuffer, j2);
                if (j3 == -1) {
                    throw new EOFException();
                }
                j2 -= j3;
                synchronized (Http2Stream.this) {
                    try {
                        if (this.f26420a) {
                            size = this.receiveBuffer.size();
                            this.receiveBuffer.clear();
                        } else {
                            boolean z4 = this.readBuffer.size() == 0;
                            this.readBuffer.writeAll(this.receiveBuffer);
                            if (z4) {
                                Http2Stream.this.notifyAll();
                            }
                            size = 0;
                        }
                    } finally {
                    }
                }
                if (size > 0) {
                    updateConnectionFlowControl(size);
                }
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            ArrayList arrayList;
            Header.Listener listener;
            synchronized (Http2Stream.this) {
                try {
                    this.f26420a = true;
                    size = this.readBuffer.size();
                    this.readBuffer.clear();
                    if (Http2Stream.this.headersQueue.isEmpty() || Http2Stream.this.headersListener == null) {
                        arrayList = null;
                        listener = null;
                    } else {
                        arrayList = new ArrayList(Http2Stream.this.headersQueue);
                        Http2Stream.this.headersQueue.clear();
                        listener = Http2Stream.this.headersListener;
                    }
                    Http2Stream.this.notifyAll();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (size > 0) {
                updateConnectionFlowControl(size);
            }
            Http2Stream.this.d();
            if (listener != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    listener.onHeaders((Headers) it.next());
                }
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            ErrorCode errorCode;
            long j3;
            Headers headers;
            Header.Listener listener;
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            while (true) {
                synchronized (Http2Stream.this) {
                    try {
                        Http2Stream.this.f26414f.enter();
                        try {
                            Http2Stream http2Stream = Http2Stream.this;
                            errorCode = http2Stream.f26416h;
                            if (errorCode == null) {
                                errorCode = null;
                            }
                            if (!this.f26420a) {
                                if (!http2Stream.headersQueue.isEmpty() && Http2Stream.this.headersListener != null) {
                                    headers = (Headers) Http2Stream.this.headersQueue.removeFirst();
                                    listener = Http2Stream.this.headersListener;
                                } else if (this.readBuffer.size() > 0) {
                                    Buffer buffer2 = this.readBuffer;
                                    j3 = buffer2.read(buffer, Math.min(j2, buffer2.size()));
                                    Http2Stream http2Stream2 = Http2Stream.this;
                                    long j4 = http2Stream2.f26409a + j3;
                                    http2Stream2.f26409a = j4;
                                    if (errorCode == null && j4 >= http2Stream2.f26412d.f26353j.d() / 2) {
                                        Http2Stream http2Stream3 = Http2Stream.this;
                                        http2Stream3.f26412d.A(http2Stream3.f26411c, http2Stream3.f26409a);
                                        Http2Stream.this.f26409a = 0L;
                                    }
                                    headers = null;
                                    listener = null;
                                    if (headers == null || listener == null) {
                                        break;
                                    }
                                    listener.onHeaders(headers);
                                } else if (this.f26421b || errorCode != null) {
                                    headers = null;
                                    listener = null;
                                } else {
                                    Http2Stream.this.j();
                                    Http2Stream.this.f26414f.exitAndThrowIfTimedOut();
                                }
                                j3 = -1;
                                if (headers == null) {
                                    break;
                                }
                                break;
                                break;
                            }
                            throw new IOException("stream closed");
                        } finally {
                            Http2Stream.this.f26414f.exitAndThrowIfTimedOut();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            if (j3 != -1) {
                updateConnectionFlowControl(j3);
                return j3;
            }
            if (errorCode == null) {
                return -1L;
            }
            throw new StreamResetException(errorCode);
        }

        @Override // okio.Source
        public Timeout timeout() {
            return Http2Stream.this.f26414f;
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        @Override // okio.AsyncTimeout
        protected IOException d(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override // okio.AsyncTimeout
        protected void e() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
            Http2Stream.this.f26412d.t();
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw d(null);
            }
        }
    }

    Http2Stream(int i2, Http2Connection http2Connection, boolean z2, boolean z3, Headers headers) {
        ArrayDeque arrayDeque = new ArrayDeque();
        this.headersQueue = arrayDeque;
        this.f26414f = new StreamTimeout();
        this.f26415g = new StreamTimeout();
        this.f26416h = null;
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        }
        this.f26411c = i2;
        this.f26412d = http2Connection;
        this.f26410b = http2Connection.f26354k.d();
        FramingSource framingSource = new FramingSource(http2Connection.f26353j.d());
        this.source = framingSource;
        FramingSink framingSink = new FramingSink();
        this.f26413e = framingSink;
        framingSource.f26421b = z3;
        framingSink.f26418b = z2;
        if (headers != null) {
            arrayDeque.add(headers);
        }
        if (isLocallyInitiated() && headers != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
        }
        if (!isLocallyInitiated() && headers == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            try {
                if (this.f26416h != null) {
                    return false;
                }
                if (this.source.f26421b && this.f26413e.f26418b) {
                    return false;
                }
                this.f26416h = errorCode;
                notifyAll();
                this.f26412d.s(this.f26411c);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void c(long j2) {
        this.f26410b += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (closeInternal(errorCode)) {
            this.f26412d.y(this.f26411c, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.f26412d.z(this.f26411c, errorCode);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void d() throws java.io.IOException {
        /*
            r2 = this;
            monitor-enter(r2)
            okhttp3.internal.http2.Http2Stream$FramingSource r0 = r2.source     // Catch: java.lang.Throwable -> L16
            boolean r1 = r0.f26421b     // Catch: java.lang.Throwable -> L16
            if (r1 != 0) goto L1a
            boolean r0 = r0.f26420a     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L1a
            okhttp3.internal.http2.Http2Stream$FramingSink r0 = r2.f26413e     // Catch: java.lang.Throwable -> L16
            boolean r1 = r0.f26418b     // Catch: java.lang.Throwable -> L16
            if (r1 != 0) goto L18
            boolean r0 = r0.f26417a     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L1a
            goto L18
        L16:
            r0 = move-exception
            goto L32
        L18:
            r0 = 1
            goto L1b
        L1a:
            r0 = 0
        L1b:
            boolean r1 = r2.isOpen()     // Catch: java.lang.Throwable -> L16
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L28
            okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.CANCEL
            r2.close(r0)
            goto L31
        L28:
            if (r1 != 0) goto L31
            okhttp3.internal.http2.Http2Connection r0 = r2.f26412d
            int r1 = r2.f26411c
            r0.s(r1)
        L31:
            return
        L32:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L16
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.d():void");
    }

    void e() throws IOException {
        FramingSink framingSink = this.f26413e;
        if (framingSink.f26417a) {
            throw new IOException("stream closed");
        }
        if (framingSink.f26418b) {
            throw new IOException("stream finished");
        }
        if (this.f26416h != null) {
            throw new StreamResetException(this.f26416h);
        }
    }

    void f(BufferedSource bufferedSource, int i2) throws IOException {
        this.source.a(bufferedSource, i2);
    }

    void g() {
        boolean zIsOpen;
        synchronized (this) {
            this.source.f26421b = true;
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.f26412d.s(this.f26411c);
    }

    public Http2Connection getConnection() {
        return this.f26412d;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.f26416h;
    }

    public int getId() {
        return this.f26411c;
    }

    public Sink getSink() {
        synchronized (this) {
            try {
                if (!this.hasResponseHeaders && !isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.f26413e;
    }

    public Source getSource() {
        return this.source;
    }

    void h(List list) {
        boolean zIsOpen;
        synchronized (this) {
            this.hasResponseHeaders = true;
            this.headersQueue.add(Util.toHeaders(list));
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.f26412d.s(this.f26411c);
    }

    synchronized void i(ErrorCode errorCode) {
        if (this.f26416h == null) {
            this.f26416h = errorCode;
            notifyAll();
        }
    }

    public boolean isLocallyInitiated() {
        return this.f26412d.f26344a == ((this.f26411c & 1) == 1);
    }

    public synchronized boolean isOpen() {
        try {
            if (this.f26416h != null) {
                return false;
            }
            FramingSource framingSource = this.source;
            if (framingSource.f26421b || framingSource.f26420a) {
                FramingSink framingSink = this.f26413e;
                if (framingSink.f26418b || framingSink.f26417a) {
                    if (this.hasResponseHeaders) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    void j() throws InterruptedException, InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    public Timeout readTimeout() {
        return this.f26414f;
    }

    public synchronized void setHeadersListener(Header.Listener listener) {
        this.headersListener = listener;
        if (!this.headersQueue.isEmpty() && listener != null) {
            notifyAll();
        }
    }

    public synchronized Headers takeHeaders() throws IOException {
        this.f26414f.enter();
        while (this.headersQueue.isEmpty() && this.f26416h == null) {
            try {
                j();
            } catch (Throwable th) {
                this.f26414f.exitAndThrowIfTimedOut();
                throw th;
            }
        }
        this.f26414f.exitAndThrowIfTimedOut();
        if (this.headersQueue.isEmpty()) {
            throw new StreamResetException(this.f26416h);
        }
        return this.headersQueue.removeFirst();
    }

    public void writeHeaders(List<Header> list, boolean z2) throws IOException {
        boolean z3;
        boolean z4;
        boolean z5;
        if (list == null) {
            throw new NullPointerException("headers == null");
        }
        synchronized (this) {
            z3 = true;
            try {
                this.hasResponseHeaders = true;
                if (z2) {
                    z4 = false;
                } else {
                    this.f26413e.f26418b = true;
                    z4 = true;
                }
                z5 = z4;
            } finally {
            }
        }
        if (!z4) {
            synchronized (this.f26412d) {
                if (this.f26412d.f26352i != 0) {
                    z3 = false;
                }
            }
            z4 = z3;
        }
        this.f26412d.x(this.f26411c, z5, list);
        if (z4) {
            this.f26412d.flush();
        }
    }

    public Timeout writeTimeout() {
        return this.f26415g;
    }
}

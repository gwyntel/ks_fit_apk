package com.squareup.okhttp.internal.framed;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes4.dex */
public final class FramedStream {

    /* renamed from: b, reason: collision with root package name */
    long f19965b;

    /* renamed from: c, reason: collision with root package name */
    final FramedDataSink f19966c;
    private final FramedConnection connection;
    private final int id;
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    private final FramedDataSource source;

    /* renamed from: a, reason: collision with root package name */
    long f19964a = 0;
    private final StreamTimeout readTimeout = new StreamTimeout();
    private final StreamTimeout writeTimeout = new StreamTimeout();
    private ErrorCode errorCode = null;

    final class FramedDataSink implements Sink {
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer = new Buffer();

        FramedDataSink() {
        }

        private void emitDataFrame(boolean z2) throws IOException {
            long jMin;
            FramedStream framedStream;
            synchronized (FramedStream.this) {
                FramedStream.this.writeTimeout.enter();
                while (true) {
                    try {
                        FramedStream framedStream2 = FramedStream.this;
                        if (framedStream2.f19965b > 0 || this.finished || this.closed || framedStream2.errorCode != null) {
                            break;
                        } else {
                            FramedStream.this.waitForIo();
                        }
                    } finally {
                        FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                }
                FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                FramedStream.this.checkOutNotClosed();
                jMin = Math.min(FramedStream.this.f19965b, this.sendBuffer.size());
                framedStream = FramedStream.this;
                framedStream.f19965b -= jMin;
            }
            framedStream.writeTimeout.enter();
            try {
                FramedStream.this.connection.writeData(FramedStream.this.id, z2 && jMin == this.sendBuffer.size(), this.sendBuffer, jMin);
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (FramedStream.this) {
                try {
                    if (this.closed) {
                        return;
                    }
                    if (!FramedStream.this.f19966c.finished) {
                        if (this.sendBuffer.size() > 0) {
                            while (this.sendBuffer.size() > 0) {
                                emitDataFrame(true);
                            }
                        } else {
                            FramedStream.this.connection.writeData(FramedStream.this.id, true, null, 0L);
                        }
                    }
                    synchronized (FramedStream.this) {
                        this.closed = true;
                    }
                    FramedStream.this.connection.flush();
                    FramedStream.this.cancelStreamIfNecessary();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            synchronized (FramedStream.this) {
                FramedStream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitDataFrame(false);
                FramedStream.this.connection.flush();
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return FramedStream.this.writeTimeout;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            this.sendBuffer.write(buffer, j2);
            while (this.sendBuffer.size() >= 16384) {
                emitDataFrame(false);
            }
        }
    }

    private final class FramedDataSource implements Source {
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            }
            if (FramedStream.this.errorCode == null) {
                return;
            }
            throw new IOException("stream was reset: " + FramedStream.this.errorCode);
        }

        private void waitUntilReadable() throws IOException {
            FramedStream.this.readTimeout.enter();
            while (this.readBuffer.size() == 0 && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                try {
                    FramedStream.this.waitForIo();
                } finally {
                    FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (FramedStream.this) {
                this.closed = true;
                this.readBuffer.clear();
                FramedStream.this.notifyAll();
            }
            FramedStream.this.cancelStreamIfNecessary();
        }

        void d(BufferedSource bufferedSource, long j2) throws IOException {
            boolean z2;
            boolean z3;
            while (j2 > 0) {
                synchronized (FramedStream.this) {
                    z2 = this.finished;
                    z3 = this.readBuffer.size() + j2 > this.maxByteCount;
                }
                if (z3) {
                    bufferedSource.skip(j2);
                    FramedStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
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
                synchronized (FramedStream.this) {
                    try {
                        boolean z4 = this.readBuffer.size() == 0;
                        this.readBuffer.writeAll(this.receiveBuffer);
                        if (z4) {
                            FramedStream.this.notifyAll();
                        }
                    } finally {
                    }
                }
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            synchronized (FramedStream.this) {
                try {
                    waitUntilReadable();
                    checkNotClosed();
                    if (this.readBuffer.size() == 0) {
                        return -1L;
                    }
                    Buffer buffer2 = this.readBuffer;
                    long j3 = buffer2.read(buffer, Math.min(j2, buffer2.size()));
                    FramedStream framedStream = FramedStream.this;
                    long j4 = framedStream.f19964a + j3;
                    framedStream.f19964a = j4;
                    if (j4 >= framedStream.connection.f19926e.e(65536) / 2) {
                        FramedStream.this.connection.C(FramedStream.this.id, FramedStream.this.f19964a);
                        FramedStream.this.f19964a = 0L;
                    }
                    synchronized (FramedStream.this.connection) {
                        try {
                            FramedStream.this.connection.f19924c += j3;
                            if (FramedStream.this.connection.f19924c >= FramedStream.this.connection.f19926e.e(65536) / 2) {
                                FramedStream.this.connection.C(0, FramedStream.this.connection.f19924c);
                                FramedStream.this.connection.f19924c = 0L;
                            }
                        } finally {
                        }
                    }
                    return j3;
                } finally {
                }
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return FramedStream.this.readTimeout;
        }

        private FramedDataSource(long j2) {
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
            this.maxByteCount = j2;
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
            FramedStream.this.closeLater(ErrorCode.CANCEL);
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw d(null);
            }
        }
    }

    FramedStream(int i2, FramedConnection framedConnection, boolean z2, boolean z3, List list) {
        if (framedConnection == null) {
            throw new NullPointerException("connection == null");
        }
        if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        }
        this.id = i2;
        this.connection = framedConnection;
        this.f19965b = framedConnection.f19927f.e(65536);
        FramedDataSource framedDataSource = new FramedDataSource(framedConnection.f19926e.e(65536));
        this.source = framedDataSource;
        FramedDataSink framedDataSink = new FramedDataSink();
        this.f19966c = framedDataSink;
        framedDataSource.finished = z3;
        framedDataSink.finished = z2;
        this.requestHeaders = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelStreamIfNecessary() throws IOException {
        boolean z2;
        boolean zIsOpen;
        synchronized (this) {
            try {
                z2 = !this.source.finished && this.source.closed && (this.f19966c.finished || this.f19966c.closed);
                zIsOpen = isOpen();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            close(ErrorCode.CANCEL);
        } else {
            if (zIsOpen) {
                return;
            }
            this.connection.y(this.id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOutNotClosed() throws IOException {
        if (this.f19966c.closed) {
            throw new IOException("stream closed");
        }
        if (this.f19966c.finished) {
            throw new IOException("stream finished");
        }
        if (this.errorCode == null) {
            return;
        }
        throw new IOException("stream was reset: " + this.errorCode);
    }

    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            try {
                if (this.errorCode != null) {
                    return false;
                }
                if (this.source.finished && this.f19966c.finished) {
                    return false;
                }
                this.errorCode = errorCode;
                notifyAll();
                this.connection.y(this.id);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForIo() throws InterruptedException, InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            throw new InterruptedIOException();
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (closeInternal(errorCode)) {
            this.connection.A(this.id, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.B(this.id, errorCode);
        }
    }

    public FramedConnection getConnection() {
        return this.connection;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public int getId() {
        return this.id;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    public synchronized List<Header> getResponseHeaders() throws IOException {
        List<Header> list;
        try {
            this.readTimeout.enter();
            while (this.responseHeaders == null && this.errorCode == null) {
                try {
                    waitForIo();
                } catch (Throwable th) {
                    this.readTimeout.exitAndThrowIfTimedOut();
                    throw th;
                }
            }
            this.readTimeout.exitAndThrowIfTimedOut();
            list = this.responseHeaders;
            if (list == null) {
                throw new IOException("stream was reset: " + this.errorCode);
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return list;
    }

    public Sink getSink() {
        synchronized (this) {
            try {
                if (this.responseHeaders == null && !isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.f19966c;
    }

    public Source getSource() {
        return this.source;
    }

    void i(long j2) {
        this.f19965b += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }

    public boolean isLocallyInitiated() {
        return this.connection.f19923b == ((this.id & 1) == 1);
    }

    public synchronized boolean isOpen() {
        try {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished || this.source.closed) {
                if (this.f19966c.finished || this.f19966c.closed) {
                    if (this.responseHeaders != null) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    void j(BufferedSource bufferedSource, int i2) {
        this.source.d(bufferedSource, i2);
    }

    void k() {
        boolean zIsOpen;
        synchronized (this) {
            this.source.finished = true;
            zIsOpen = isOpen();
            notifyAll();
        }
        if (zIsOpen) {
            return;
        }
        this.connection.y(this.id);
    }

    void l(List list, HeadersMode headersMode) {
        ErrorCode errorCode;
        boolean zIsOpen;
        synchronized (this) {
            try {
                errorCode = null;
                zIsOpen = true;
                if (this.responseHeaders == null) {
                    if (headersMode.failIfHeadersAbsent()) {
                        errorCode = ErrorCode.PROTOCOL_ERROR;
                    } else {
                        this.responseHeaders = list;
                        zIsOpen = isOpen();
                        notifyAll();
                    }
                } else if (headersMode.failIfHeadersPresent()) {
                    errorCode = ErrorCode.STREAM_IN_USE;
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.responseHeaders);
                    arrayList.addAll(list);
                    this.responseHeaders = arrayList;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (errorCode != null) {
            closeLater(errorCode);
        } else {
            if (zIsOpen) {
                return;
            }
            this.connection.y(this.id);
        }
    }

    synchronized void m(ErrorCode errorCode) {
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public void reply(List<Header> list, boolean z2) throws IOException {
        boolean z3;
        synchronized (this) {
            try {
                if (list == null) {
                    throw new NullPointerException("responseHeaders == null");
                }
                if (this.responseHeaders != null) {
                    throw new IllegalStateException("reply already sent");
                }
                this.responseHeaders = list;
                if (z2) {
                    z3 = false;
                } else {
                    z3 = true;
                    this.f19966c.finished = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.connection.z(this.id, z3, list);
        if (z3) {
            this.connection.flush();
        }
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }
}

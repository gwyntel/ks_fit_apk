package okhttp3.internal.http1;

import android.support.v4.media.session.PlaybackStateCompat;
import com.alipay.sdk.m.u.i;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import org.apache.commons.io.IOUtils;

/* loaded from: classes5.dex */
public final class Http1Codec implements HttpCodec {
    private static final int HEADER_LIMIT = 262144;
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;

    /* renamed from: a, reason: collision with root package name */
    final OkHttpClient f26310a;

    /* renamed from: b, reason: collision with root package name */
    final StreamAllocation f26311b;

    /* renamed from: c, reason: collision with root package name */
    final BufferedSource f26312c;

    /* renamed from: d, reason: collision with root package name */
    final BufferedSink f26313d;

    /* renamed from: e, reason: collision with root package name */
    int f26314e = 0;
    private long headerLimit = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;

    private abstract class AbstractSource implements Source {

        /* renamed from: a, reason: collision with root package name */
        protected final ForwardingTimeout f26315a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f26316b;

        /* renamed from: c, reason: collision with root package name */
        protected long f26317c;

        private AbstractSource() {
            this.f26315a = new ForwardingTimeout(Http1Codec.this.f26312c.timeout());
            this.f26317c = 0L;
        }

        protected final void a(boolean z2, IOException iOException) {
            Http1Codec http1Codec = Http1Codec.this;
            int i2 = http1Codec.f26314e;
            if (i2 == 6) {
                return;
            }
            if (i2 != 5) {
                throw new IllegalStateException("state: " + Http1Codec.this.f26314e);
            }
            http1Codec.a(this.f26315a);
            Http1Codec http1Codec2 = Http1Codec.this;
            http1Codec2.f26314e = 6;
            StreamAllocation streamAllocation = http1Codec2.f26311b;
            if (streamAllocation != null) {
                streamAllocation.streamFinished(!z2, http1Codec2, this.f26317c, iOException);
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            try {
                long j3 = Http1Codec.this.f26312c.read(buffer, j2);
                if (j3 > 0) {
                    this.f26317c += j3;
                }
                return j3;
            } catch (IOException e2) {
                a(false, e2);
                throw e2;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.f26315a;
        }
    }

    private final class ChunkedSink implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1Codec.this.f26313d.timeout());
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            Http1Codec.this.f26313d.writeUtf8("0\r\n\r\n");
            Http1Codec.this.a(this.timeout);
            Http1Codec.this.f26314e = 3;
        }

        @Override // okio.Sink, java.io.Flushable
        public synchronized void flush() throws IOException {
            if (this.closed) {
                return;
            }
            Http1Codec.this.f26313d.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.timeout;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (j2 == 0) {
                return;
            }
            Http1Codec.this.f26313d.writeHexadecimalUnsignedLong(j2);
            Http1Codec.this.f26313d.writeUtf8(IOUtils.LINE_SEPARATOR_WINDOWS);
            Http1Codec.this.f26313d.write(buffer, j2);
            Http1Codec.this.f26313d.writeUtf8(IOUtils.LINE_SEPARATOR_WINDOWS);
        }
    }

    private class ChunkedSource extends AbstractSource {
        private static final long NO_CHUNK_YET = -1;
        private long bytesRemainingInChunk;
        private boolean hasMoreChunks;
        private final HttpUrl url;

        ChunkedSource(HttpUrl httpUrl) {
            super();
            this.bytesRemainingInChunk = -1L;
            this.hasMoreChunks = true;
            this.url = httpUrl;
        }

        private void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != -1) {
                Http1Codec.this.f26312c.readUtf8LineStrict();
            }
            try {
                this.bytesRemainingInChunk = Http1Codec.this.f26312c.readHexadecimalUnsignedLong();
                String strTrim = Http1Codec.this.f26312c.readUtf8LineStrict().trim();
                if (this.bytesRemainingInChunk < 0 || !(strTrim.isEmpty() || strTrim.startsWith(i.f9802b))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.bytesRemainingInChunk + strTrim + "\"");
                }
                if (this.bytesRemainingInChunk == 0) {
                    this.hasMoreChunks = false;
                    HttpHeaders.receiveHeaders(Http1Codec.this.f26310a.cookieJar(), this.url, Http1Codec.this.readHeaders());
                    a(true, null);
                }
            } catch (NumberFormatException e2) {
                throw new ProtocolException(e2.getMessage());
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.f26316b) {
                return;
            }
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                a(false, null);
            }
            this.f26316b = true;
        }

        @Override // okhttp3.internal.http1.Http1Codec.AbstractSource, okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            if (this.f26316b) {
                throw new IllegalStateException("closed");
            }
            if (!this.hasMoreChunks) {
                return -1L;
            }
            long j3 = this.bytesRemainingInChunk;
            if (j3 == 0 || j3 == -1) {
                readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1L;
                }
            }
            long j4 = super.read(buffer, Math.min(j2, this.bytesRemainingInChunk));
            if (j4 != -1) {
                this.bytesRemainingInChunk -= j4;
                return j4;
            }
            ProtocolException protocolException = new ProtocolException("unexpected end of stream");
            a(false, protocolException);
            throw protocolException;
        }
    }

    private final class FixedLengthSink implements Sink {
        private long bytesRemaining;
        private boolean closed;
        private final ForwardingTimeout timeout;

        FixedLengthSink(long j2) {
            this.timeout = new ForwardingTimeout(Http1Codec.this.f26313d.timeout());
            this.bytesRemaining = j2;
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.bytesRemaining > 0) {
                throw new ProtocolException("unexpected end of stream");
            }
            Http1Codec.this.a(this.timeout);
            Http1Codec.this.f26314e = 3;
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.closed) {
                return;
            }
            Http1Codec.this.f26313d.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.timeout;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0L, j2);
            if (j2 <= this.bytesRemaining) {
                Http1Codec.this.f26313d.write(buffer, j2);
                this.bytesRemaining -= j2;
                return;
            }
            throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + j2);
        }
    }

    private class FixedLengthSource extends AbstractSource {
        private long bytesRemaining;

        FixedLengthSource(long j2) {
            super();
            this.bytesRemaining = j2;
            if (j2 == 0) {
                a(true, null);
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.f26316b) {
                return;
            }
            if (this.bytesRemaining != 0 && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                a(false, null);
            }
            this.f26316b = true;
        }

        @Override // okhttp3.internal.http1.Http1Codec.AbstractSource, okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            if (this.f26316b) {
                throw new IllegalStateException("closed");
            }
            long j3 = this.bytesRemaining;
            if (j3 == 0) {
                return -1L;
            }
            long j4 = super.read(buffer, Math.min(j3, j2));
            if (j4 == -1) {
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                a(false, protocolException);
                throw protocolException;
            }
            long j5 = this.bytesRemaining - j4;
            this.bytesRemaining = j5;
            if (j5 == 0) {
                a(true, null);
            }
            return j4;
        }
    }

    private class UnknownLengthSource extends AbstractSource {
        private boolean inputExhausted;

        UnknownLengthSource() {
            super();
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.f26316b) {
                return;
            }
            if (!this.inputExhausted) {
                a(false, null);
            }
            this.f26316b = true;
        }

        @Override // okhttp3.internal.http1.Http1Codec.AbstractSource, okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
            if (this.f26316b) {
                throw new IllegalStateException("closed");
            }
            if (this.inputExhausted) {
                return -1L;
            }
            long j3 = super.read(buffer, j2);
            if (j3 != -1) {
                return j3;
            }
            this.inputExhausted = true;
            a(true, null);
            return -1L;
        }
    }

    public Http1Codec(OkHttpClient okHttpClient, StreamAllocation streamAllocation, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.f26310a = okHttpClient;
        this.f26311b = streamAllocation;
        this.f26312c = bufferedSource;
        this.f26313d = bufferedSink;
    }

    private String readHeaderLine() throws IOException {
        String utf8LineStrict = this.f26312c.readUtf8LineStrict(this.headerLimit);
        this.headerLimit -= utf8LineStrict.length();
        return utf8LineStrict;
    }

    void a(ForwardingTimeout forwardingTimeout) {
        Timeout timeoutDelegate = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        timeoutDelegate.clearDeadline();
        timeoutDelegate.clearTimeout();
    }

    @Override // okhttp3.internal.http.HttpCodec
    public void cancel() throws IOException {
        RealConnection realConnectionConnection = this.f26311b.connection();
        if (realConnectionConnection != null) {
            realConnectionConnection.cancel();
        }
    }

    @Override // okhttp3.internal.http.HttpCodec
    public Sink createRequestBody(Request request, long j2) {
        if ("chunked".equalsIgnoreCase(request.header(com.google.common.net.HttpHeaders.TRANSFER_ENCODING))) {
            return newChunkedSink();
        }
        if (j2 != -1) {
            return newFixedLengthSink(j2);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    @Override // okhttp3.internal.http.HttpCodec
    public void finishRequest() throws IOException {
        this.f26313d.flush();
    }

    @Override // okhttp3.internal.http.HttpCodec
    public void flushRequest() throws IOException {
        this.f26313d.flush();
    }

    public boolean isClosed() {
        return this.f26314e == 6;
    }

    public Sink newChunkedSink() {
        if (this.f26314e == 1) {
            this.f26314e = 2;
            return new ChunkedSink();
        }
        throw new IllegalStateException("state: " + this.f26314e);
    }

    public Source newChunkedSource(HttpUrl httpUrl) throws IOException {
        if (this.f26314e == 4) {
            this.f26314e = 5;
            return new ChunkedSource(httpUrl);
        }
        throw new IllegalStateException("state: " + this.f26314e);
    }

    public Sink newFixedLengthSink(long j2) {
        if (this.f26314e == 1) {
            this.f26314e = 2;
            return new FixedLengthSink(j2);
        }
        throw new IllegalStateException("state: " + this.f26314e);
    }

    public Source newFixedLengthSource(long j2) throws IOException {
        if (this.f26314e == 4) {
            this.f26314e = 5;
            return new FixedLengthSource(j2);
        }
        throw new IllegalStateException("state: " + this.f26314e);
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.f26314e != 4) {
            throw new IllegalStateException("state: " + this.f26314e);
        }
        StreamAllocation streamAllocation = this.f26311b;
        if (streamAllocation == null) {
            throw new IllegalStateException("streamAllocation == null");
        }
        this.f26314e = 5;
        streamAllocation.noNewStreams();
        return new UnknownLengthSource();
    }

    @Override // okhttp3.internal.http.HttpCodec
    public ResponseBody openResponseBody(Response response) throws IOException {
        StreamAllocation streamAllocation = this.f26311b;
        streamAllocation.eventListener.responseBodyStart(streamAllocation.call);
        String strHeader = response.header("Content-Type");
        if (!HttpHeaders.hasBody(response)) {
            return new RealResponseBody(strHeader, 0L, Okio.buffer(newFixedLengthSource(0L)));
        }
        if ("chunked".equalsIgnoreCase(response.header(com.google.common.net.HttpHeaders.TRANSFER_ENCODING))) {
            return new RealResponseBody(strHeader, -1L, Okio.buffer(newChunkedSource(response.request().url())));
        }
        long jContentLength = HttpHeaders.contentLength(response);
        return jContentLength != -1 ? new RealResponseBody(strHeader, jContentLength, Okio.buffer(newFixedLengthSource(jContentLength))) : new RealResponseBody(strHeader, -1L, Okio.buffer(newUnknownLengthSource()));
    }

    public Headers readHeaders() throws IOException {
        Headers.Builder builder = new Headers.Builder();
        while (true) {
            String headerLine = readHeaderLine();
            if (headerLine.length() == 0) {
                return builder.build();
            }
            Internal.instance.addLenient(builder, headerLine);
        }
    }

    @Override // okhttp3.internal.http.HttpCodec
    public Response.Builder readResponseHeaders(boolean z2) throws NumberFormatException, IOException {
        int i2 = this.f26314e;
        if (i2 != 1 && i2 != 3) {
            throw new IllegalStateException("state: " + this.f26314e);
        }
        try {
            StatusLine statusLine = StatusLine.parse(readHeaderLine());
            Response.Builder builderHeaders = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(readHeaders());
            if (z2 && statusLine.code == 100) {
                return null;
            }
            if (statusLine.code == 100) {
                this.f26314e = 3;
                return builderHeaders;
            }
            this.f26314e = 4;
            return builderHeaders;
        } catch (EOFException e2) {
            IOException iOException = new IOException("unexpected end of stream on " + this.f26311b);
            iOException.initCause(e2);
            throw iOException;
        }
    }

    public void writeRequest(Headers headers, String str) throws IOException {
        if (this.f26314e != 0) {
            throw new IllegalStateException("state: " + this.f26314e);
        }
        this.f26313d.writeUtf8(str).writeUtf8(IOUtils.LINE_SEPARATOR_WINDOWS);
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.f26313d.writeUtf8(headers.name(i2)).writeUtf8(": ").writeUtf8(headers.value(i2)).writeUtf8(IOUtils.LINE_SEPARATOR_WINDOWS);
        }
        this.f26313d.writeUtf8(IOUtils.LINE_SEPARATOR_WINDOWS);
        this.f26314e = 1;
    }

    @Override // okhttp3.internal.http.HttpCodec
    public void writeRequestHeaders(Request request) throws IOException {
        writeRequest(request.headers(), RequestLine.get(request, this.f26311b.connection().route().proxy().type()));
    }
}

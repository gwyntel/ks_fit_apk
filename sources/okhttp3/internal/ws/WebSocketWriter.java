package okhttp3.internal.ws;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;

/* loaded from: classes5.dex */
final class WebSocketWriter {

    /* renamed from: a, reason: collision with root package name */
    final boolean f26451a;

    /* renamed from: b, reason: collision with root package name */
    final Random f26452b;

    /* renamed from: c, reason: collision with root package name */
    final BufferedSink f26453c;

    /* renamed from: d, reason: collision with root package name */
    final Buffer f26454d;

    /* renamed from: e, reason: collision with root package name */
    boolean f26455e;

    /* renamed from: f, reason: collision with root package name */
    final Buffer f26456f = new Buffer();

    /* renamed from: g, reason: collision with root package name */
    final FrameSink f26457g = new FrameSink();

    /* renamed from: h, reason: collision with root package name */
    boolean f26458h;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;

    final class FrameSink implements Sink {

        /* renamed from: a, reason: collision with root package name */
        int f26459a;

        /* renamed from: b, reason: collision with root package name */
        long f26460b;

        /* renamed from: c, reason: collision with root package name */
        boolean f26461c;

        /* renamed from: d, reason: collision with root package name */
        boolean f26462d;

        FrameSink() {
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.f26462d) {
                throw new IOException("closed");
            }
            WebSocketWriter webSocketWriter = WebSocketWriter.this;
            webSocketWriter.c(this.f26459a, webSocketWriter.f26456f.size(), this.f26461c, true);
            this.f26462d = true;
            WebSocketWriter.this.f26458h = false;
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.f26462d) {
                throw new IOException("closed");
            }
            WebSocketWriter webSocketWriter = WebSocketWriter.this;
            webSocketWriter.c(this.f26459a, webSocketWriter.f26456f.size(), this.f26461c, false);
            this.f26461c = false;
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return WebSocketWriter.this.f26453c.timeout();
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j2) throws IOException {
            if (this.f26462d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.f26456f.write(buffer, j2);
            boolean z2 = this.f26461c && this.f26460b != -1 && WebSocketWriter.this.f26456f.size() > this.f26460b - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            long jCompleteSegmentByteCount = WebSocketWriter.this.f26456f.completeSegmentByteCount();
            if (jCompleteSegmentByteCount <= 0 || z2) {
                return;
            }
            WebSocketWriter.this.c(this.f26459a, jCompleteSegmentByteCount, this.f26461c, false);
            this.f26461c = false;
        }
    }

    WebSocketWriter(boolean z2, BufferedSink bufferedSink, Random random) {
        if (bufferedSink == null) {
            throw new NullPointerException("sink == null");
        }
        if (random == null) {
            throw new NullPointerException("random == null");
        }
        this.f26451a = z2;
        this.f26453c = bufferedSink;
        this.f26454d = bufferedSink.buffer();
        this.f26452b = random;
        this.maskKey = z2 ? new byte[4] : null;
        this.maskCursor = z2 ? new Buffer.UnsafeCursor() : null;
    }

    private void writeControlFrame(int i2, ByteString byteString) throws IOException {
        if (this.f26455e) {
            throw new IOException("closed");
        }
        int size = byteString.size();
        if (size > 125) {
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        this.f26454d.writeByte(i2 | 128);
        if (this.f26451a) {
            this.f26454d.writeByte(size | 128);
            this.f26452b.nextBytes(this.maskKey);
            this.f26454d.write(this.maskKey);
            if (size > 0) {
                long size2 = this.f26454d.size();
                this.f26454d.write(byteString);
                this.f26454d.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(size2);
                WebSocketProtocol.b(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.f26454d.writeByte(size);
            this.f26454d.write(byteString);
        }
        this.f26453c.flush();
    }

    Sink a(int i2, long j2) {
        if (this.f26458h) {
            throw new IllegalStateException("Another message writer is active. Did you call close()?");
        }
        this.f26458h = true;
        FrameSink frameSink = this.f26457g;
        frameSink.f26459a = i2;
        frameSink.f26460b = j2;
        frameSink.f26461c = true;
        frameSink.f26462d = false;
        return frameSink;
    }

    void b(int i2, ByteString byteString) {
        ByteString byteString2 = ByteString.EMPTY;
        if (i2 != 0 || byteString != null) {
            if (i2 != 0) {
                WebSocketProtocol.c(i2);
            }
            Buffer buffer = new Buffer();
            buffer.writeShort(i2);
            if (byteString != null) {
                buffer.write(byteString);
            }
            byteString2 = buffer.readByteString();
        }
        try {
            writeControlFrame(8, byteString2);
        } finally {
            this.f26455e = true;
        }
    }

    void c(int i2, long j2, boolean z2, boolean z3) throws IOException {
        if (this.f26455e) {
            throw new IOException("closed");
        }
        if (!z2) {
            i2 = 0;
        }
        if (z3) {
            i2 |= 128;
        }
        this.f26454d.writeByte(i2);
        int i3 = this.f26451a ? 128 : 0;
        if (j2 <= 125) {
            this.f26454d.writeByte(((int) j2) | i3);
        } else if (j2 <= 65535) {
            this.f26454d.writeByte(i3 | 126);
            this.f26454d.writeShort((int) j2);
        } else {
            this.f26454d.writeByte(i3 | 127);
            this.f26454d.writeLong(j2);
        }
        if (this.f26451a) {
            this.f26452b.nextBytes(this.maskKey);
            this.f26454d.write(this.maskKey);
            if (j2 > 0) {
                long size = this.f26454d.size();
                this.f26454d.write(this.f26456f, j2);
                this.f26454d.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(size);
                WebSocketProtocol.b(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.f26454d.write(this.f26456f, j2);
        }
        this.f26453c.emit();
    }

    void d(ByteString byteString) throws IOException {
        writeControlFrame(9, byteString);
    }

    void e(ByteString byteString) throws IOException {
        writeControlFrame(10, byteString);
    }
}

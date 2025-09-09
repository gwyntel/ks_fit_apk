package okhttp3.internal.ws;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes5.dex */
final class WebSocketReader {

    /* renamed from: a, reason: collision with root package name */
    final boolean f26443a;

    /* renamed from: b, reason: collision with root package name */
    final BufferedSource f26444b;

    /* renamed from: c, reason: collision with root package name */
    final FrameCallback f26445c;

    /* renamed from: d, reason: collision with root package name */
    boolean f26446d;

    /* renamed from: e, reason: collision with root package name */
    int f26447e;

    /* renamed from: f, reason: collision with root package name */
    long f26448f;

    /* renamed from: g, reason: collision with root package name */
    boolean f26449g;

    /* renamed from: h, reason: collision with root package name */
    boolean f26450h;
    private final Buffer.UnsafeCursor maskCursor;
    private final byte[] maskKey;
    private final Buffer controlFrameBuffer = new Buffer();
    private final Buffer messageFrameBuffer = new Buffer();

    public interface FrameCallback {
        void onReadClose(int i2, String str);

        void onReadMessage(String str) throws IOException;

        void onReadMessage(ByteString byteString) throws IOException;

        void onReadPing(ByteString byteString);

        void onReadPong(ByteString byteString);
    }

    WebSocketReader(boolean z2, BufferedSource bufferedSource, FrameCallback frameCallback) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        }
        this.f26443a = z2;
        this.f26444b = bufferedSource;
        this.f26445c = frameCallback;
        this.maskKey = z2 ? null : new byte[4];
        this.maskCursor = z2 ? null : new Buffer.UnsafeCursor();
    }

    private void readControlFrame() throws IOException {
        short s2;
        String utf8;
        long j2 = this.f26448f;
        if (j2 > 0) {
            this.f26444b.readFully(this.controlFrameBuffer, j2);
            if (!this.f26443a) {
                this.controlFrameBuffer.readAndWriteUnsafe(this.maskCursor);
                this.maskCursor.seek(0L);
                WebSocketProtocol.b(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        }
        switch (this.f26447e) {
            case 8:
                long size = this.controlFrameBuffer.size();
                if (size == 1) {
                    throw new ProtocolException("Malformed close payload length of 1.");
                }
                if (size != 0) {
                    s2 = this.controlFrameBuffer.readShort();
                    utf8 = this.controlFrameBuffer.readUtf8();
                    String strA = WebSocketProtocol.a(s2);
                    if (strA != null) {
                        throw new ProtocolException(strA);
                    }
                } else {
                    s2 = 1005;
                    utf8 = "";
                }
                this.f26445c.onReadClose(s2, utf8);
                this.f26446d = true;
                return;
            case 9:
                this.f26445c.onReadPing(this.controlFrameBuffer.readByteString());
                return;
            case 10:
                this.f26445c.onReadPong(this.controlFrameBuffer.readByteString());
                return;
            default:
                throw new ProtocolException("Unknown control opcode: " + Integer.toHexString(this.f26447e));
        }
    }

    private void readHeader() throws IOException {
        if (this.f26446d) {
            throw new IOException("closed");
        }
        long jTimeoutNanos = this.f26444b.timeout().timeoutNanos();
        this.f26444b.timeout().clearTimeout();
        try {
            byte b2 = this.f26444b.readByte();
            this.f26444b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            this.f26447e = b2 & 15;
            boolean z2 = (b2 & 128) != 0;
            this.f26449g = z2;
            boolean z3 = (b2 & 8) != 0;
            this.f26450h = z3;
            if (z3 && !z2) {
                throw new ProtocolException("Control frames must be final.");
            }
            boolean z4 = (b2 & 64) != 0;
            boolean z5 = (b2 & 32) != 0;
            boolean z6 = (b2 & 16) != 0;
            if (z4 || z5 || z6) {
                throw new ProtocolException("Reserved flags are unsupported.");
            }
            byte b3 = this.f26444b.readByte();
            boolean z7 = (b3 & 128) != 0;
            if (z7 == this.f26443a) {
                throw new ProtocolException(this.f26443a ? "Server-sent frames must not be masked." : "Client-sent frames must be masked.");
            }
            long j2 = b3 & Byte.MAX_VALUE;
            this.f26448f = j2;
            if (j2 == 126) {
                this.f26448f = this.f26444b.readShort() & 65535;
            } else if (j2 == 127) {
                long j3 = this.f26444b.readLong();
                this.f26448f = j3;
                if (j3 < 0) {
                    throw new ProtocolException("Frame length 0x" + Long.toHexString(this.f26448f) + " > 0x7FFFFFFFFFFFFFFF");
                }
            }
            if (this.f26450h && this.f26448f > 125) {
                throw new ProtocolException("Control frame must be less than 125B.");
            }
            if (z7) {
                this.f26444b.readFully(this.maskKey);
            }
        } catch (Throwable th) {
            this.f26444b.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            throw th;
        }
    }

    private void readMessage() throws IOException {
        while (!this.f26446d) {
            long j2 = this.f26448f;
            if (j2 > 0) {
                this.f26444b.readFully(this.messageFrameBuffer, j2);
                if (!this.f26443a) {
                    this.messageFrameBuffer.readAndWriteUnsafe(this.maskCursor);
                    this.maskCursor.seek(this.messageFrameBuffer.size() - this.f26448f);
                    WebSocketProtocol.b(this.maskCursor, this.maskKey);
                    this.maskCursor.close();
                }
            }
            if (this.f26449g) {
                return;
            }
            readUntilNonControlFrame();
            if (this.f26447e != 0) {
                throw new ProtocolException("Expected continuation opcode. Got: " + Integer.toHexString(this.f26447e));
            }
        }
        throw new IOException("closed");
    }

    private void readMessageFrame() throws IOException {
        int i2 = this.f26447e;
        if (i2 != 1 && i2 != 2) {
            throw new ProtocolException("Unknown opcode: " + Integer.toHexString(i2));
        }
        readMessage();
        if (i2 == 1) {
            this.f26445c.onReadMessage(this.messageFrameBuffer.readUtf8());
        } else {
            this.f26445c.onReadMessage(this.messageFrameBuffer.readByteString());
        }
    }

    private void readUntilNonControlFrame() throws IOException {
        while (!this.f26446d) {
            readHeader();
            if (!this.f26450h) {
                return;
            } else {
                readControlFrame();
            }
        }
    }

    void a() throws IOException {
        readHeader();
        if (this.f26450h) {
            readControlFrame();
        } else {
            readMessageFrame();
        }
    }
}

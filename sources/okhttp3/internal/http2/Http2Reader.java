package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.UShort;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Hpack;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* loaded from: classes5.dex */
final class Http2Reader implements Closeable {

    /* renamed from: b, reason: collision with root package name */
    static final Logger f26402b = Logger.getLogger(Http2.class.getName());

    /* renamed from: a, reason: collision with root package name */
    final Hpack.Reader f26403a;
    private final boolean client;
    private final ContinuationSource continuation;
    private final BufferedSource source;

    static final class ContinuationSource implements Source {

        /* renamed from: a, reason: collision with root package name */
        int f26404a;

        /* renamed from: b, reason: collision with root package name */
        byte f26405b;

        /* renamed from: c, reason: collision with root package name */
        int f26406c;

        /* renamed from: d, reason: collision with root package name */
        int f26407d;

        /* renamed from: e, reason: collision with root package name */
        short f26408e;
        private final BufferedSource source;

        ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        private void readContinuationHeader() throws IOException {
            int i2 = this.f26406c;
            int iB = Http2Reader.b(this.source);
            this.f26407d = iB;
            this.f26404a = iB;
            byte b2 = (byte) (this.source.readByte() & 255);
            this.f26405b = (byte) (this.source.readByte() & 255);
            Logger logger = Http2Reader.f26402b;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Http2.b(true, this.f26406c, this.f26404a, b2, this.f26405b));
            }
            int i3 = this.source.readInt() & Integer.MAX_VALUE;
            this.f26406c = i3;
            if (b2 != 9) {
                throw Http2.d("%s != TYPE_CONTINUATION", Byte.valueOf(b2));
            }
            if (i3 != i2) {
                throw Http2.d("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            while (true) {
                int i2 = this.f26407d;
                if (i2 != 0) {
                    long j3 = this.source.read(buffer, Math.min(j2, i2));
                    if (j3 == -1) {
                        return -1L;
                    }
                    this.f26407d = (int) (this.f26407d - j3);
                    return j3;
                }
                this.source.skip(this.f26408e);
                this.f26408e = (short) 0;
                if ((this.f26405b & 4) != 0) {
                    return -1L;
                }
                readContinuationHeader();
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

    interface Handler {
        void ackSettings();

        void alternateService(int i2, String str, ByteString byteString, String str2, int i3, long j2);

        void data(boolean z2, int i2, BufferedSource bufferedSource, int i3) throws IOException;

        void goAway(int i2, ErrorCode errorCode, ByteString byteString);

        void headers(boolean z2, int i2, int i3, List<Header> list);

        void ping(boolean z2, int i2, int i3);

        void priority(int i2, int i3, int i4, boolean z2);

        void pushPromise(int i2, int i3, List<Header> list) throws IOException;

        void rstStream(int i2, ErrorCode errorCode);

        void settings(boolean z2, Settings settings);

        void windowUpdate(int i2, long j2);
    }

    Http2Reader(BufferedSource bufferedSource, boolean z2) {
        this.source = bufferedSource;
        this.client = z2;
        ContinuationSource continuationSource = new ContinuationSource(bufferedSource);
        this.continuation = continuationSource;
        this.f26403a = new Hpack.Reader(4096, continuationSource);
    }

    static int a(int i2, byte b2, short s2) throws IOException {
        if ((b2 & 8) != 0) {
            i2--;
        }
        if (s2 <= i2) {
            return (short) (i2 - s2);
        }
        throw Http2.d("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s2), Integer.valueOf(i2));
    }

    static int b(BufferedSource bufferedSource) {
        return (bufferedSource.readByte() & 255) | ((bufferedSource.readByte() & 255) << 16) | ((bufferedSource.readByte() & 255) << 8);
    }

    private void readData(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i3 == 0) {
            throw Http2.d("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
        }
        boolean z2 = (b2 & 1) != 0;
        if ((b2 & 32) != 0) {
            throw Http2.d("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }
        short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
        handler.data(z2, i3, this.source, a(i2, b2, s2));
        this.source.skip(s2);
    }

    private void readGoAway(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i2 < 8) {
            throw Http2.d("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i2));
        }
        if (i3 != 0) {
            throw Http2.d("TYPE_GOAWAY streamId != 0", new Object[0]);
        }
        int i4 = this.source.readInt();
        int i5 = this.source.readInt();
        int i6 = i2 - 8;
        ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i5);
        if (errorCodeFromHttp2 == null) {
            throw Http2.d("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(i5));
        }
        ByteString byteString = ByteString.EMPTY;
        if (i6 > 0) {
            byteString = this.source.readByteString(i6);
        }
        handler.goAway(i4, errorCodeFromHttp2, byteString);
    }

    private List<Header> readHeaderBlock(int i2, short s2, byte b2, int i3) throws IOException {
        ContinuationSource continuationSource = this.continuation;
        continuationSource.f26407d = i2;
        continuationSource.f26404a = i2;
        continuationSource.f26408e = s2;
        continuationSource.f26405b = b2;
        continuationSource.f26406c = i3;
        this.f26403a.b();
        return this.f26403a.getAndResetHeaderList();
    }

    private void readHeaders(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i3 == 0) {
            throw Http2.d("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }
        boolean z2 = (b2 & 1) != 0;
        short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
        if ((b2 & 32) != 0) {
            readPriority(handler, i3);
            i2 -= 5;
        }
        handler.headers(z2, i3, -1, readHeaderBlock(a(i2, b2, s2), s2, b2, i3));
    }

    private void readPing(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i2 != 8) {
            throw Http2.d("TYPE_PING length != 8: %s", Integer.valueOf(i2));
        }
        if (i3 != 0) {
            throw Http2.d("TYPE_PING streamId != 0", new Object[0]);
        }
        handler.ping((b2 & 1) != 0, this.source.readInt(), this.source.readInt());
    }

    private void readPriority(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i2 != 5) {
            throw Http2.d("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i2));
        }
        if (i3 == 0) {
            throw Http2.d("TYPE_PRIORITY streamId == 0", new Object[0]);
        }
        readPriority(handler, i3);
    }

    private void readPushPromise(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i3 == 0) {
            throw Http2.d("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }
        short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
        handler.pushPromise(i3, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock(a(i2 - 4, b2, s2), s2, b2, i3));
    }

    private void readRstStream(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i2 != 4) {
            throw Http2.d("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i2));
        }
        if (i3 == 0) {
            throw Http2.d("TYPE_RST_STREAM streamId == 0", new Object[0]);
        }
        int i4 = this.source.readInt();
        ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i4);
        if (errorCodeFromHttp2 == null) {
            throw Http2.d("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(i4));
        }
        handler.rstStream(i3, errorCodeFromHttp2);
    }

    private void readSettings(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i3 != 0) {
            throw Http2.d("TYPE_SETTINGS streamId != 0", new Object[0]);
        }
        if ((b2 & 1) != 0) {
            if (i2 != 0) {
                throw Http2.d("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
            handler.ackSettings();
            return;
        }
        if (i2 % 6 != 0) {
            throw Http2.d("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i2));
        }
        Settings settings = new Settings();
        for (int i4 = 0; i4 < i2; i4 += 6) {
            int i5 = this.source.readShort() & UShort.MAX_VALUE;
            int i6 = this.source.readInt();
            if (i5 == 2) {
                if (i6 != 0 && i6 != 1) {
                    throw Http2.d("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                }
            } else if (i5 == 3) {
                i5 = 4;
            } else if (i5 != 4) {
                if (i5 == 5 && (i6 < 16384 || i6 > 16777215)) {
                    throw Http2.d("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(i6));
                }
            } else {
                if (i6 < 0) {
                    throw Http2.d("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                }
                i5 = 7;
            }
            settings.i(i5, i6);
        }
        handler.settings(false, settings);
    }

    private void readWindowUpdate(Handler handler, int i2, byte b2, int i3) throws IOException {
        if (i2 != 4) {
            throw Http2.d("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i2));
        }
        long j2 = this.source.readInt() & 2147483647L;
        if (j2 == 0) {
            throw Http2.d("windowSizeIncrement was 0", Long.valueOf(j2));
        }
        handler.windowUpdate(i3, j2);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.source.close();
    }

    public boolean nextFrame(boolean z2, Handler handler) throws IOException {
        try {
            this.source.require(9L);
            int iB = b(this.source);
            if (iB < 0 || iB > 16384) {
                throw Http2.d("FRAME_SIZE_ERROR: %s", Integer.valueOf(iB));
            }
            byte b2 = (byte) (this.source.readByte() & 255);
            if (z2 && b2 != 4) {
                throw Http2.d("Expected a SETTINGS frame but was %s", Byte.valueOf(b2));
            }
            byte b3 = (byte) (this.source.readByte() & 255);
            int i2 = this.source.readInt() & Integer.MAX_VALUE;
            Logger logger = f26402b;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Http2.b(true, i2, iB, b2, b3));
            }
            switch (b2) {
                case 0:
                    readData(handler, iB, b3, i2);
                    return true;
                case 1:
                    readHeaders(handler, iB, b3, i2);
                    return true;
                case 2:
                    readPriority(handler, iB, b3, i2);
                    return true;
                case 3:
                    readRstStream(handler, iB, b3, i2);
                    return true;
                case 4:
                    readSettings(handler, iB, b3, i2);
                    return true;
                case 5:
                    readPushPromise(handler, iB, b3, i2);
                    return true;
                case 6:
                    readPing(handler, iB, b3, i2);
                    return true;
                case 7:
                    readGoAway(handler, iB, b3, i2);
                    return true;
                case 8:
                    readWindowUpdate(handler, iB, b3, i2);
                    return true;
                default:
                    this.source.skip(iB);
                    return true;
            }
        } catch (IOException unused) {
            return false;
        }
    }

    public void readConnectionPreface(Handler handler) throws IOException {
        if (this.client) {
            if (!nextFrame(true, handler)) {
                throw Http2.d("Required SETTINGS preface not received", new Object[0]);
            }
            return;
        }
        BufferedSource bufferedSource = this.source;
        ByteString byteString = Http2.f26337a;
        ByteString byteString2 = bufferedSource.readByteString(byteString.size());
        Logger logger = f26402b;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Util.format("<< CONNECTION %s", byteString2.hex()));
        }
        if (!byteString.equals(byteString2)) {
            throw Http2.d("Expected a connection header but was %s", byteString2.utf8());
        }
    }

    private void readPriority(Handler handler, int i2) throws IOException {
        int i3 = this.source.readInt();
        handler.priority(i2, i3 & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, (Integer.MIN_VALUE & i3) != 0);
    }
}

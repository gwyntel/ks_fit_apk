package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.framed.FrameReader;
import com.squareup.okhttp.internal.framed.Hpack;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* loaded from: classes4.dex */
public final class Http2 implements Variant {
    private static final Logger logger = Logger.getLogger(FrameLogger.class.getName());
    private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    static final class ContinuationSource implements Source {

        /* renamed from: a, reason: collision with root package name */
        int f19975a;

        /* renamed from: b, reason: collision with root package name */
        byte f19976b;

        /* renamed from: c, reason: collision with root package name */
        int f19977c;

        /* renamed from: d, reason: collision with root package name */
        int f19978d;

        /* renamed from: e, reason: collision with root package name */
        short f19979e;
        private final BufferedSource source;

        public ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        private void readContinuationHeader() throws IOException {
            int i2 = this.f19977c;
            int medium = Http2.readMedium(this.source);
            this.f19978d = medium;
            this.f19975a = medium;
            byte b2 = (byte) (this.source.readByte() & 255);
            this.f19976b = (byte) (this.source.readByte() & 255);
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.b(true, this.f19977c, this.f19975a, b2, this.f19976b));
            }
            int i3 = this.source.readInt() & Integer.MAX_VALUE;
            this.f19977c = i3;
            if (b2 != 9) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(b2));
            }
            if (i3 != i2) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j2) throws IOException {
            while (true) {
                int i2 = this.f19978d;
                if (i2 != 0) {
                    long j3 = this.source.read(buffer, Math.min(j2, i2));
                    if (j3 == -1) {
                        return -1L;
                    }
                    this.f19978d = (int) (this.f19978d - j3);
                    return j3;
                }
                this.source.skip(this.f19979e);
                this.f19979e = (short) 0;
                if ((this.f19976b & 4) != 0) {
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

    static final class FrameLogger {
        private static final String[] TYPES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        private static final String[] FLAGS = new String[64];
        private static final String[] BINARY = new String[256];

        static {
            int i2 = 0;
            int i3 = 0;
            while (true) {
                String[] strArr = BINARY;
                if (i3 >= strArr.length) {
                    break;
                }
                strArr[i3] = String.format("%8s", Integer.toBinaryString(i3)).replace(' ', '0');
                i3++;
            }
            String[] strArr2 = FLAGS;
            strArr2[0] = "";
            strArr2[1] = "END_STREAM";
            int[] iArr = {1};
            strArr2[8] = "PADDED";
            int i4 = iArr[0];
            strArr2[i4 | 8] = strArr2[i4] + "|PADDED";
            strArr2[4] = "END_HEADERS";
            strArr2[32] = "PRIORITY";
            strArr2[36] = "END_HEADERS|PRIORITY";
            int[] iArr2 = {4, 32, 36};
            for (int i5 = 0; i5 < 3; i5++) {
                int i6 = iArr2[i5];
                int i7 = iArr[0];
                String[] strArr3 = FLAGS;
                int i8 = i7 | i6;
                strArr3[i8] = strArr3[i7] + '|' + strArr3[i6];
                strArr3[i8 | 8] = strArr3[i7] + '|' + strArr3[i6] + "|PADDED";
            }
            while (true) {
                String[] strArr4 = FLAGS;
                if (i2 >= strArr4.length) {
                    return;
                }
                if (strArr4[i2] == null) {
                    strArr4[i2] = BINARY[i2];
                }
                i2++;
            }
        }

        static String a(byte b2, byte b3) {
            if (b3 == 0) {
                return "";
            }
            if (b2 != 2 && b2 != 3) {
                if (b2 == 4 || b2 == 6) {
                    return b3 == 1 ? "ACK" : BINARY[b3];
                }
                if (b2 != 7 && b2 != 8) {
                    String[] strArr = FLAGS;
                    String str = b3 < strArr.length ? strArr[b3] : BINARY[b3];
                    return (b2 != 5 || (b3 & 4) == 0) ? (b2 != 0 || (b3 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED") : str.replace("HEADERS", "PUSH_PROMISE");
                }
            }
            return BINARY[b3];
        }

        static String b(boolean z2, int i2, int i3, byte b2, byte b3) {
            String[] strArr = TYPES;
            return String.format("%s 0x%08x %5d %-13s %s", z2 ? "<<" : ">>", Integer.valueOf(i2), Integer.valueOf(i3), b2 < strArr.length ? strArr[b2] : String.format("0x%02x", Byte.valueOf(b2)), a(b2, b3));
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer;
        private final Hpack.Writer hpackWriter;
        private int maxFrameSize;
        private final BufferedSink sink;

        Writer(BufferedSink bufferedSink, boolean z2) {
            this.sink = bufferedSink;
            this.client = z2;
            Buffer buffer = new Buffer();
            this.hpackBuffer = buffer;
            this.hpackWriter = new Hpack.Writer(buffer);
            this.maxFrameSize = 16384;
        }

        private void writeContinuationFrames(int i2, long j2) throws IOException {
            while (j2 > 0) {
                int iMin = (int) Math.min(this.maxFrameSize, j2);
                long j3 = iMin;
                j2 -= j3;
                b(i2, iMin, (byte) 9, j2 == 0 ? (byte) 4 : (byte) 0);
                this.sink.write(this.hpackBuffer, j3);
            }
        }

        void a(int i2, byte b2, Buffer buffer, int i3) throws IOException {
            b(i2, i3, (byte) 0, b2);
            if (i3 > 0) {
                this.sink.write(buffer, i3);
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void ackSettings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.maxFrameSize = settings.f(this.maxFrameSize);
            b(0, 0, (byte) 4, (byte) 1);
            this.sink.flush();
        }

        void b(int i2, int i3, byte b2, byte b3) throws IOException {
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.b(false, i2, i3, b2, b3));
            }
            int i4 = this.maxFrameSize;
            if (i3 > i4) {
                throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(i4), Integer.valueOf(i3));
            }
            if ((Integer.MIN_VALUE & i2) != 0) {
                throw Http2.illegalArgument("reserved bit set: %s", Integer.valueOf(i2));
            }
            Http2.writeMedium(this.sink, i3);
            this.sink.writeByte(b2 & 255);
            this.sink.writeByte(b3 & 255);
            this.sink.writeInt(i2 & Integer.MAX_VALUE);
        }

        void c(boolean z2, int i2, List list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.b(list);
            long size = this.hpackBuffer.size();
            int iMin = (int) Math.min(this.maxFrameSize, size);
            long j2 = iMin;
            byte b2 = size == j2 ? (byte) 4 : (byte) 0;
            if (z2) {
                b2 = (byte) (b2 | 1);
            }
            b(i2, iMin, (byte) 1, b2);
            this.sink.write(this.hpackBuffer, j2);
            if (size > j2) {
                writeContinuationFrames(i2, size - j2);
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            this.closed = true;
            this.sink.close();
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void connectionPreface() throws IOException {
            try {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (this.client) {
                    if (Http2.logger.isLoggable(Level.FINE)) {
                        Http2.logger.fine(String.format(">> CONNECTION %s", Http2.CONNECTION_PREFACE.hex()));
                    }
                    this.sink.write(Http2.CONNECTION_PREFACE.toByteArray());
                    this.sink.flush();
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void data(boolean z2, int i2, Buffer buffer, int i3) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            a(i2, z2 ? (byte) 1 : (byte) 0, buffer, i3);
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void goAway(int i2, ErrorCode errorCode, byte[] bArr) throws IOException {
            try {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (errorCode.httpCode == -1) {
                    throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
                }
                b(0, bArr.length + 8, (byte) 7, (byte) 0);
                this.sink.writeInt(i2);
                this.sink.writeInt(errorCode.httpCode);
                if (bArr.length > 0) {
                    this.sink.write(bArr);
                }
                this.sink.flush();
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void headers(int i2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            c(false, i2, list);
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public int maxDataLength() {
            return this.maxFrameSize;
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void ping(boolean z2, int i2, int i3) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            b(0, 8, (byte) 6, z2 ? (byte) 1 : (byte) 0);
            this.sink.writeInt(i2);
            this.sink.writeInt(i3);
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void pushPromise(int i2, int i3, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.b(list);
            long size = this.hpackBuffer.size();
            int iMin = (int) Math.min(this.maxFrameSize - 4, size);
            long j2 = iMin;
            b(i2, iMin + 4, (byte) 5, size == j2 ? (byte) 4 : (byte) 0);
            this.sink.writeInt(i3 & Integer.MAX_VALUE);
            this.sink.write(this.hpackBuffer, j2);
            if (size > j2) {
                writeContinuationFrames(i2, size - j2);
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void rstStream(int i2, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.httpCode == -1) {
                throw new IllegalArgumentException();
            }
            b(i2, 4, (byte) 3, (byte) 0);
            this.sink.writeInt(errorCode.httpCode);
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void settings(Settings settings) throws IOException {
            try {
                if (this.closed) {
                    throw new IOException("closed");
                }
                int i2 = 0;
                b(0, settings.l() * 6, (byte) 4, (byte) 0);
                while (i2 < 10) {
                    if (settings.h(i2)) {
                        this.sink.writeShort(i2 == 4 ? 3 : i2 == 7 ? 4 : i2);
                        this.sink.writeInt(settings.c(i2));
                    }
                    i2++;
                }
                this.sink.flush();
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void synReply(boolean z2, int i2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            c(z2, i2, list);
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void synStream(boolean z2, boolean z3, int i2, int i3, List<Header> list) throws IOException {
            if (z3) {
                throw new UnsupportedOperationException();
            }
            if (this.closed) {
                throw new IOException("closed");
            }
            c(z2, i2, list);
        }

        @Override // com.squareup.okhttp.internal.framed.FrameWriter
        public synchronized void windowUpdate(int i2, long j2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (j2 == 0 || j2 > 2147483647L) {
                throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j2));
            }
            b(i2, 4, (byte) 8, (byte) 0);
            this.sink.writeInt((int) j2);
            this.sink.flush();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IOException ioException(String str, Object... objArr) throws IOException {
        throw new IOException(String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lengthWithoutPadding(int i2, byte b2, short s2) throws IOException {
        if ((b2 & 8) != 0) {
            i2--;
        }
        if (s2 <= i2) {
            return (short) (i2 - s2);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s2), Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readMedium(BufferedSource bufferedSource) throws IOException {
        return (bufferedSource.readByte() & 255) | ((bufferedSource.readByte() & 255) << 16) | ((bufferedSource.readByte() & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeMedium(BufferedSink bufferedSink, int i2) throws IOException {
        bufferedSink.writeByte((i2 >>> 16) & 255);
        bufferedSink.writeByte((i2 >>> 8) & 255);
        bufferedSink.writeByte(i2 & 255);
    }

    @Override // com.squareup.okhttp.internal.framed.Variant
    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    @Override // com.squareup.okhttp.internal.framed.Variant
    public FrameReader newReader(BufferedSource bufferedSource, boolean z2) {
        return new Reader(bufferedSource, 4096, z2);
    }

    @Override // com.squareup.okhttp.internal.framed.Variant
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean z2) {
        return new Writer(bufferedSink, z2);
    }

    static final class Reader implements FrameReader {

        /* renamed from: a, reason: collision with root package name */
        final Hpack.Reader f19980a;
        private final boolean client;
        private final ContinuationSource continuation;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, int i2, boolean z2) {
            this.source = bufferedSource;
            this.client = z2;
            ContinuationSource continuationSource = new ContinuationSource(bufferedSource);
            this.continuation = continuationSource;
            this.f19980a = new Hpack.Reader(i2, continuationSource);
        }

        private void readData(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            boolean z2 = (b2 & 1) != 0;
            if ((b2 & 32) != 0) {
                throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
            handler.data(z2, i3, this.source, Http2.lengthWithoutPadding(i2, b2, s2));
            this.source.skip(s2);
        }

        private void readGoAway(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i2 < 8) {
                throw Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i2));
            }
            if (i3 != 0) {
                throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
            int i4 = this.source.readInt();
            int i5 = this.source.readInt();
            int i6 = i2 - 8;
            ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i5);
            if (errorCodeFromHttp2 == null) {
                throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(i5));
            }
            ByteString byteString = ByteString.EMPTY;
            if (i6 > 0) {
                byteString = this.source.readByteString(i6);
            }
            handler.goAway(i4, errorCodeFromHttp2, byteString);
        }

        private List<Header> readHeaderBlock(int i2, short s2, byte b2, int i3) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            continuationSource.f19978d = i2;
            continuationSource.f19975a = i2;
            continuationSource.f19979e = s2;
            continuationSource.f19976b = b2;
            continuationSource.f19977c = i3;
            this.f19980a.c();
            return this.f19980a.getAndResetHeaderList();
        }

        private void readHeaders(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i3 == 0) {
                throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean z2 = (b2 & 1) != 0;
            short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
            if ((b2 & 32) != 0) {
                readPriority(handler, i3);
                i2 -= 5;
            }
            handler.headers(false, z2, i3, -1, readHeaderBlock(Http2.lengthWithoutPadding(i2, b2, s2), s2, b2, i3), HeadersMode.HTTP_20_HEADERS);
        }

        private void readPing(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i2 != 8) {
                throw Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(i2));
            }
            if (i3 != 0) {
                throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
            handler.ping((b2 & 1) != 0, this.source.readInt(), this.source.readInt());
        }

        private void readPriority(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i2 != 5) {
                throw Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i2));
            }
            if (i3 == 0) {
                throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
            readPriority(handler, i3);
        }

        private void readPushPromise(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i3 == 0) {
                throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            short s2 = (b2 & 8) != 0 ? (short) (this.source.readByte() & 255) : (short) 0;
            handler.pushPromise(i3, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock(Http2.lengthWithoutPadding(i2 - 4, b2, s2), s2, b2, i3));
        }

        private void readRstStream(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i2 != 4) {
                throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i2));
            }
            if (i3 == 0) {
                throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
            int i4 = this.source.readInt();
            ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(i4);
            if (errorCodeFromHttp2 == null) {
                throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(i4));
            }
            handler.rstStream(i3, errorCodeFromHttp2);
        }

        private void readSettings(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i3 != 0) {
                throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            }
            if ((b2 & 1) != 0) {
                if (i2 != 0) {
                    throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                handler.ackSettings();
                return;
            }
            if (i2 % 6 != 0) {
                throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i2));
            }
            Settings settings = new Settings();
            for (int i4 = 0; i4 < i2; i4 += 6) {
                short s2 = this.source.readShort();
                int i5 = this.source.readInt();
                switch (s2) {
                    case 1:
                    case 6:
                        break;
                    case 2:
                        if (i5 != 0 && i5 != 1) {
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                        }
                        break;
                    case 3:
                        s2 = 4;
                        break;
                    case 4:
                        if (i5 < 0) {
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        }
                        s2 = 7;
                        break;
                    case 5:
                        if (i5 < 16384 || i5 > 16777215) {
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(i5));
                        }
                        break;
                        break;
                    default:
                        throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", Short.valueOf(s2));
                }
                settings.k(s2, 0, i5);
            }
            handler.settings(false, settings);
            if (settings.d() >= 0) {
                this.f19980a.a(settings.d());
            }
        }

        private void readWindowUpdate(FrameReader.Handler handler, int i2, byte b2, int i3) throws IOException {
            if (i2 != 4) {
                throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i2));
            }
            long j2 = this.source.readInt() & 2147483647L;
            if (j2 == 0) {
                throw Http2.ioException("windowSizeIncrement was 0", Long.valueOf(j2));
            }
            handler.windowUpdate(i3, j2);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.source.close();
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader
        public boolean nextFrame(FrameReader.Handler handler) throws IOException {
            try {
                this.source.require(9L);
                int medium = Http2.readMedium(this.source);
                if (medium < 0 || medium > 16384) {
                    throw Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(medium));
                }
                byte b2 = (byte) (this.source.readByte() & 255);
                byte b3 = (byte) (this.source.readByte() & 255);
                int i2 = this.source.readInt() & Integer.MAX_VALUE;
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(FrameLogger.b(true, i2, medium, b2, b3));
                }
                switch (b2) {
                    case 0:
                        readData(handler, medium, b3, i2);
                        return true;
                    case 1:
                        readHeaders(handler, medium, b3, i2);
                        return true;
                    case 2:
                        readPriority(handler, medium, b3, i2);
                        return true;
                    case 3:
                        readRstStream(handler, medium, b3, i2);
                        return true;
                    case 4:
                        readSettings(handler, medium, b3, i2);
                        return true;
                    case 5:
                        readPushPromise(handler, medium, b3, i2);
                        return true;
                    case 6:
                        readPing(handler, medium, b3, i2);
                        return true;
                    case 7:
                        readGoAway(handler, medium, b3, i2);
                        return true;
                    case 8:
                        readWindowUpdate(handler, medium, b3, i2);
                        return true;
                    default:
                        this.source.skip(medium);
                        return true;
                }
            } catch (IOException unused) {
                return false;
            }
        }

        @Override // com.squareup.okhttp.internal.framed.FrameReader
        public void readConnectionPreface() throws IOException {
            if (this.client) {
                return;
            }
            ByteString byteString = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(String.format("<< CONNECTION %s", byteString.hex()));
            }
            if (!Http2.CONNECTION_PREFACE.equals(byteString)) {
                throw Http2.ioException("Expected a connection header but was %s", byteString.utf8());
            }
        }

        private void readPriority(FrameReader.Handler handler, int i2) throws IOException {
            int i3 = this.source.readInt();
            handler.priority(i2, i3 & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, (Integer.MIN_VALUE & i3) != 0);
        }
    }
}

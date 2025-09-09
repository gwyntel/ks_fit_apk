package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.media3.common.C;
import androidx.media3.exoplayer.MediaPeriodQueue;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.google.common.base.Ascii;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;

/* loaded from: classes5.dex */
public final class Buffer implements BufferedSource, BufferedSink, Cloneable, ByteChannel {
    private static final byte[] DIGITS = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: a, reason: collision with root package name */
    Segment f26469a;

    /* renamed from: b, reason: collision with root package name */
    long f26470b;

    public static final class UnsafeCursor implements Closeable {
        public Buffer buffer;
        public byte[] data;
        public boolean readWrite;
        private Segment segment;
        public long offset = -1;
        public int start = -1;
        public int end = -1;

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.buffer = null;
            this.segment = null;
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
        }

        public final long expandBuffer(int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("minByteCount <= 0: " + i2);
            }
            if (i2 > 8192) {
                throw new IllegalArgumentException("minByteCount > Segment.SIZE: " + i2);
            }
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
            }
            long j2 = buffer.f26470b;
            Segment segmentD = buffer.d(i2);
            int i3 = 8192 - segmentD.f26495c;
            segmentD.f26495c = 8192;
            long j3 = i3;
            this.buffer.f26470b = j2 + j3;
            this.segment = segmentD;
            this.offset = j2;
            this.data = segmentD.f26493a;
            this.start = 8192 - i3;
            this.end = 8192;
            return j3;
        }

        public final int next() {
            long j2 = this.offset;
            if (j2 != this.buffer.f26470b) {
                return j2 == -1 ? seek(0L) : seek(j2 + (this.end - this.start));
            }
            throw new IllegalStateException();
        }

        public final long resizeBuffer(long j2) {
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            if (!this.readWrite) {
                throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
            }
            long j3 = buffer.f26470b;
            if (j2 <= j3) {
                if (j2 < 0) {
                    throw new IllegalArgumentException("newSize < 0: " + j2);
                }
                long j4 = j3 - j2;
                while (true) {
                    if (j4 <= 0) {
                        break;
                    }
                    Buffer buffer2 = this.buffer;
                    Segment segment = buffer2.f26469a.f26499g;
                    int i2 = segment.f26495c;
                    long j5 = i2 - segment.f26494b;
                    if (j5 > j4) {
                        segment.f26495c = (int) (i2 - j4);
                        break;
                    }
                    buffer2.f26469a = segment.pop();
                    SegmentPool.a(segment);
                    j4 -= j5;
                }
                this.segment = null;
                this.offset = j2;
                this.data = null;
                this.start = -1;
                this.end = -1;
            } else if (j2 > j3) {
                long j6 = j2 - j3;
                boolean z2 = true;
                while (j6 > 0) {
                    Segment segmentD = this.buffer.d(1);
                    int iMin = (int) Math.min(j6, 8192 - segmentD.f26495c);
                    int i3 = segmentD.f26495c + iMin;
                    segmentD.f26495c = i3;
                    j6 -= iMin;
                    if (z2) {
                        this.segment = segmentD;
                        this.offset = j3;
                        this.data = segmentD.f26493a;
                        this.start = i3 - iMin;
                        this.end = i3;
                        z2 = false;
                    }
                }
            }
            this.buffer.f26470b = j2;
            return j3;
        }

        public final int seek(long j2) {
            if (j2 >= -1) {
                Buffer buffer = this.buffer;
                long j3 = buffer.f26470b;
                if (j2 <= j3) {
                    if (j2 == -1 || j2 == j3) {
                        this.segment = null;
                        this.offset = j2;
                        this.data = null;
                        this.start = -1;
                        this.end = -1;
                        return -1;
                    }
                    Segment segment = buffer.f26469a;
                    Segment segmentPush = this.segment;
                    long j4 = 0;
                    if (segmentPush != null) {
                        long j5 = this.offset - (this.start - segmentPush.f26494b);
                        if (j5 > j2) {
                            j3 = j5;
                            segmentPush = segment;
                            segment = segmentPush;
                        } else {
                            j4 = j5;
                        }
                    } else {
                        segmentPush = segment;
                    }
                    if (j3 - j2 > j2 - j4) {
                        while (true) {
                            int i2 = segmentPush.f26495c;
                            int i3 = segmentPush.f26494b;
                            if (j2 < (i2 - i3) + j4) {
                                break;
                            }
                            j4 += i2 - i3;
                            segmentPush = segmentPush.f26498f;
                        }
                    } else {
                        while (j3 > j2) {
                            segment = segment.f26499g;
                            j3 -= segment.f26495c - segment.f26494b;
                        }
                        segmentPush = segment;
                        j4 = j3;
                    }
                    if (this.readWrite && segmentPush.f26496d) {
                        Segment segmentB = segmentPush.b();
                        Buffer buffer2 = this.buffer;
                        if (buffer2.f26469a == segmentPush) {
                            buffer2.f26469a = segmentB;
                        }
                        segmentPush = segmentPush.push(segmentB);
                        segmentPush.f26499g.pop();
                    }
                    this.segment = segmentPush;
                    this.offset = j2;
                    this.data = segmentPush.f26493a;
                    int i4 = segmentPush.f26494b + ((int) (j2 - j4));
                    this.start = i4;
                    int i5 = segmentPush.f26495c;
                    this.end = i5;
                    return i5 - i4;
                }
            }
            throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", Long.valueOf(j2), Long.valueOf(this.buffer.f26470b)));
        }
    }

    private ByteString digest(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(str);
            Segment segment = this.f26469a;
            if (segment != null) {
                byte[] bArr = segment.f26493a;
                int i2 = segment.f26494b;
                messageDigest.update(bArr, i2, segment.f26495c - i2);
                Segment segment2 = this.f26469a;
                while (true) {
                    segment2 = segment2.f26498f;
                    if (segment2 == this.f26469a) {
                        break;
                    }
                    byte[] bArr2 = segment2.f26493a;
                    int i3 = segment2.f26494b;
                    messageDigest.update(bArr2, i3, segment2.f26495c - i3);
                }
            }
            return ByteString.of(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private ByteString hmac(String str, ByteString byteString) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac mac = Mac.getInstance(str);
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            Segment segment = this.f26469a;
            if (segment != null) {
                byte[] bArr = segment.f26493a;
                int i2 = segment.f26494b;
                mac.update(bArr, i2, segment.f26495c - i2);
                Segment segment2 = this.f26469a;
                while (true) {
                    segment2 = segment2.f26498f;
                    if (segment2 == this.f26469a) {
                        break;
                    }
                    byte[] bArr2 = segment2.f26493a;
                    int i3 = segment2.f26494b;
                    mac.update(bArr2, i3, segment2.f26495c - i3);
                }
            }
            return ByteString.of(mac.doFinal());
        } catch (InvalidKeyException e2) {
            throw new IllegalArgumentException(e2);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    String b(long j2) throws EOFException {
        if (j2 > 0) {
            long j3 = j2 - 1;
            if (getByte(j3) == 13) {
                String utf8 = readUtf8(j3);
                skip(2L);
                return utf8;
            }
        }
        String utf82 = readUtf8(j2);
        skip(1L);
        return utf82;
    }

    @Override // okio.BufferedSource, okio.BufferedSink
    public Buffer buffer() {
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0055, code lost:
    
        if (r19 == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0058, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int c(okio.Options r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 158
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.c(okio.Options, boolean):int");
    }

    public final void clear() {
        try {
            skip(this.f26470b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public final long completeSegmentByteCount() {
        long j2 = this.f26470b;
        if (j2 == 0) {
            return 0L;
        }
        Segment segment = this.f26469a.f26499g;
        return (segment.f26495c >= 8192 || !segment.f26497e) ? j2 : j2 - (r3 - segment.f26494b);
    }

    public final Buffer copyTo(OutputStream outputStream) throws IOException {
        return copyTo(outputStream, 0L, this.f26470b);
    }

    Segment d(int i2) {
        if (i2 < 1 || i2 > 8192) {
            throw new IllegalArgumentException();
        }
        Segment segment = this.f26469a;
        if (segment != null) {
            Segment segment2 = segment.f26499g;
            return (segment2.f26495c + i2 > 8192 || !segment2.f26497e) ? segment2.push(SegmentPool.b()) : segment2;
        }
        Segment segmentB = SegmentPool.b();
        this.f26469a = segmentB;
        segmentB.f26499g = segmentB;
        segmentB.f26498f = segmentB;
        return segmentB;
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() {
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer emitCompleteSegments() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Buffer)) {
            return false;
        }
        Buffer buffer = (Buffer) obj;
        long j2 = this.f26470b;
        if (j2 != buffer.f26470b) {
            return false;
        }
        long j3 = 0;
        if (j2 == 0) {
            return true;
        }
        Segment segment = this.f26469a;
        Segment segment2 = buffer.f26469a;
        int i2 = segment.f26494b;
        int i3 = segment2.f26494b;
        while (j3 < this.f26470b) {
            long jMin = Math.min(segment.f26495c - i2, segment2.f26495c - i3);
            int i4 = 0;
            while (i4 < jMin) {
                int i5 = i2 + 1;
                int i6 = i3 + 1;
                if (segment.f26493a[i2] != segment2.f26493a[i3]) {
                    return false;
                }
                i4++;
                i2 = i5;
                i3 = i6;
            }
            if (i2 == segment.f26495c) {
                segment = segment.f26498f;
                i2 = segment.f26494b;
            }
            if (i3 == segment2.f26495c) {
                segment2 = segment2.f26498f;
                i3 = segment2.f26494b;
            }
            j3 += jMin;
        }
        return true;
    }

    @Override // okio.BufferedSource
    public boolean exhausted() {
        return this.f26470b == 0;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() {
    }

    public final byte getByte(long j2) {
        int i2;
        Util.checkOffsetAndCount(this.f26470b, j2, 1L);
        long j3 = this.f26470b;
        if (j3 - j2 <= j2) {
            long j4 = j2 - j3;
            Segment segment = this.f26469a;
            do {
                segment = segment.f26499g;
                int i3 = segment.f26495c;
                i2 = segment.f26494b;
                j4 += i3 - i2;
            } while (j4 < 0);
            return segment.f26493a[i2 + ((int) j4)];
        }
        Segment segment2 = this.f26469a;
        while (true) {
            int i4 = segment2.f26495c;
            int i5 = segment2.f26494b;
            long j5 = i4 - i5;
            if (j2 < j5) {
                return segment2.f26493a[i5 + ((int) j2)];
            }
            j2 -= j5;
            segment2 = segment2.f26498f;
        }
    }

    public int hashCode() {
        Segment segment = this.f26469a;
        if (segment == null) {
            return 0;
        }
        int i2 = 1;
        do {
            int i3 = segment.f26495c;
            for (int i4 = segment.f26494b; i4 < i3; i4++) {
                i2 = (i2 * 31) + segment.f26493a[i4];
            }
            segment = segment.f26498f;
        } while (segment != this.f26469a);
        return i2;
    }

    public final ByteString hmacSha1(ByteString byteString) {
        return hmac("HmacSHA1", byteString);
    }

    public final ByteString hmacSha256(ByteString byteString) {
        return hmac("HmacSHA256", byteString);
    }

    public final ByteString hmacSha512(ByteString byteString) {
        return hmac("HmacSHA512", byteString);
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2) {
        return indexOf(b2, 0L, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString) {
        return indexOfElement(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: okio.Buffer.2
            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(Buffer.this.f26470b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() {
                Buffer buffer = Buffer.this;
                if (buffer.f26470b > 0) {
                    return buffer.readByte() & 255;
                }
                return -1;
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i2, int i3) {
                return Buffer.this.read(bArr, i2, i3);
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return true;
    }

    public final ByteString md5() {
        return digest(Utils.MD5);
    }

    @Override // okio.BufferedSink
    public OutputStream outputStream() {
        return new OutputStream() { // from class: okio.Buffer.1
            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() {
            }

            public String toString() {
                return Buffer.this + ".outputStream()";
            }

            @Override // java.io.OutputStream
            public void write(int i2) {
                Buffer.this.writeByte((int) ((byte) i2));
            }

            @Override // java.io.OutputStream
            public void write(byte[] bArr, int i2, int i3) {
                Buffer.this.write(bArr, i2, i3);
            }
        };
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j2, ByteString byteString) {
        return rangeEquals(j2, byteString, 0, byteString.size());
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        long j2 = this.f26470b;
        if (j2 > 0) {
            sink.write(this, j2);
        }
        return j2;
    }

    public final UnsafeCursor readAndWriteUnsafe() {
        return readAndWriteUnsafe(new UnsafeCursor());
    }

    @Override // okio.BufferedSource
    public byte readByte() {
        long j2 = this.f26470b;
        if (j2 == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.f26469a;
        int i2 = segment.f26494b;
        int i3 = segment.f26495c;
        int i4 = i2 + 1;
        byte b2 = segment.f26493a[i2];
        this.f26470b = j2 - 1;
        if (i4 == i3) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.f26494b = i4;
        }
        return b2;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() {
        try {
            return readByteArray(this.f26470b);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // okio.BufferedSource
    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ac A[EDGE_INSN: B:48:0x00ac->B:38:0x00ac BREAK  A[LOOP:0: B:5:0x000f->B:50:?], SYNTHETIC] */
    @Override // okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long readDecimalLong() {
        /*
            r15 = this;
            long r0 = r15.f26470b
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto Lb7
            r0 = 0
            r4 = -7
            r1 = r0
            r5 = r4
            r3 = r2
            r2 = r1
        Lf:
            okio.Segment r7 = r15.f26469a
            byte[] r8 = r7.f26493a
            int r9 = r7.f26494b
            int r10 = r7.f26495c
        L17:
            if (r9 >= r10) goto L98
            r11 = r8[r9]
            r12 = 48
            if (r11 < r12) goto L69
            r12 = 57
            if (r11 > r12) goto L69
            int r12 = 48 - r11
            r13 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r13 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r13 < 0) goto L3c
            if (r13 != 0) goto L36
            long r13 = (long) r12
            int r13 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r13 >= 0) goto L36
            goto L3c
        L36:
            r13 = 10
            long r3 = r3 * r13
            long r11 = (long) r12
            long r3 = r3 + r11
            goto L74
        L3c:
            okio.Buffer r0 = new okio.Buffer
            r0.<init>()
            okio.Buffer r0 = r0.writeDecimalLong(r3)
            okio.Buffer r0 = r0.writeByte(r11)
            if (r1 != 0) goto L4e
            r0.readByte()
        L4e:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Number too large: "
            r2.append(r3)
            java.lang.String r0 = r0.readUtf8()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L69:
            r12 = 45
            r13 = 1
            if (r11 != r12) goto L79
            if (r0 != 0) goto L79
            r11 = 1
            long r5 = r5 - r11
            r1 = r13
        L74:
            int r9 = r9 + 1
            int r0 = r0 + 1
            goto L17
        L79:
            if (r0 == 0) goto L7d
            r2 = r13
            goto L98
        L7d:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Expected leading [0-9] or '-' character but was 0x"
            r1.append(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r11)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L98:
            if (r9 != r10) goto La4
            okio.Segment r8 = r7.pop()
            r15.f26469a = r8
            okio.SegmentPool.a(r7)
            goto La6
        La4:
            r7.f26494b = r9
        La6:
            if (r2 != 0) goto Lac
            okio.Segment r7 = r15.f26469a
            if (r7 != 0) goto Lf
        Lac:
            long r5 = r15.f26470b
            long r7 = (long) r0
            long r5 = r5 - r7
            r15.f26470b = r5
            if (r1 == 0) goto Lb5
            goto Lb6
        Lb5:
            long r3 = -r3
        Lb6:
            return r3
        Lb7:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    public final Buffer readFrom(InputStream inputStream) throws IOException {
        readFrom(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer buffer, long j2) throws EOFException {
        long j3 = this.f26470b;
        if (j3 >= j2) {
            buffer.write(this, j2);
        } else {
            buffer.write(this, j3);
            throw new EOFException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a2 A[EDGE_INSN: B:43:0x00a2->B:37:0x00a2 BREAK  A[LOOP:0: B:5:0x000b->B:45:?], SYNTHETIC] */
    @Override // okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long readHexadecimalUnsignedLong() {
        /*
            r14 = this;
            long r0 = r14.f26470b
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto La9
            r0 = 0
            r1 = r0
            r4 = r2
        Lb:
            okio.Segment r6 = r14.f26469a
            byte[] r7 = r6.f26493a
            int r8 = r6.f26494b
            int r9 = r6.f26495c
        L13:
            if (r8 >= r9) goto L8e
            r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L22
            r11 = 57
            if (r10 > r11) goto L22
            int r11 = r10 + (-48)
            goto L37
        L22:
            r11 = 97
            if (r10 < r11) goto L2d
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L2d
            int r11 = r10 + (-87)
            goto L37
        L2d:
            r11 = 65
            if (r10 < r11) goto L6f
            r11 = 70
            if (r10 > r11) goto L6f
            int r11 = r10 + (-55)
        L37:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 != 0) goto L47
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L13
        L47:
            okio.Buffer r0 = new okio.Buffer
            r0.<init>()
            okio.Buffer r0 = r0.writeHexadecimalUnsignedLong(r4)
            okio.Buffer r0 = r0.writeByte(r10)
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Number too large: "
            r2.append(r3)
            java.lang.String r0 = r0.readUtf8()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L6f:
            if (r0 == 0) goto L73
            r1 = 1
            goto L8e
        L73:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Expected leading [0-9a-fA-F] character but was 0x"
            r1.append(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r10)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L8e:
            if (r8 != r9) goto L9a
            okio.Segment r7 = r6.pop()
            r14.f26469a = r7
            okio.SegmentPool.a(r6)
            goto L9c
        L9a:
            r6.f26494b = r8
        L9c:
            if (r1 != 0) goto La2
            okio.Segment r6 = r14.f26469a
            if (r6 != 0) goto Lb
        La2:
            long r1 = r14.f26470b
            long r6 = (long) r0
            long r1 = r1 - r6
            r14.f26470b = r1
            return r4
        La9:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readHexadecimalUnsignedLong():long");
    }

    @Override // okio.BufferedSource
    public int readInt() {
        long j2 = this.f26470b;
        if (j2 < 4) {
            throw new IllegalStateException("size < 4: " + this.f26470b);
        }
        Segment segment = this.f26469a;
        int i2 = segment.f26494b;
        int i3 = segment.f26495c;
        if (i3 - i2 < 4) {
            return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
        }
        byte[] bArr = segment.f26493a;
        int i4 = i2 + 3;
        int i5 = ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 2] & 255) << 8);
        int i6 = i2 + 4;
        int i7 = (bArr[i4] & 255) | i5;
        this.f26470b = j2 - 4;
        if (i6 == i3) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.f26494b = i6;
        }
        return i7;
    }

    @Override // okio.BufferedSource
    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    @Override // okio.BufferedSource
    public long readLong() {
        long j2 = this.f26470b;
        if (j2 < 8) {
            throw new IllegalStateException("size < 8: " + this.f26470b);
        }
        Segment segment = this.f26469a;
        int i2 = segment.f26494b;
        int i3 = segment.f26495c;
        if (i3 - i2 < 8) {
            return ((readInt() & 4294967295L) << 32) | (4294967295L & readInt());
        }
        byte[] bArr = segment.f26493a;
        int i4 = i2 + 7;
        long j3 = ((bArr[i2 + 1] & 255) << 48) | ((bArr[i2] & 255) << 56) | ((bArr[i2 + 2] & 255) << 40) | ((bArr[i2 + 3] & 255) << 32) | ((bArr[i2 + 4] & 255) << 24) | ((bArr[i2 + 5] & 255) << 16) | ((bArr[i2 + 6] & 255) << 8);
        int i5 = i2 + 8;
        long j4 = j3 | (bArr[i4] & 255);
        this.f26470b = j2 - 8;
        if (i5 == i3) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.f26494b = i5;
        }
        return j4;
    }

    @Override // okio.BufferedSource
    public long readLongLe() {
        return Util.reverseBytesLong(readLong());
    }

    @Override // okio.BufferedSource
    public short readShort() {
        long j2 = this.f26470b;
        if (j2 < 2) {
            throw new IllegalStateException("size < 2: " + this.f26470b);
        }
        Segment segment = this.f26469a;
        int i2 = segment.f26494b;
        int i3 = segment.f26495c;
        if (i3 - i2 < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] bArr = segment.f26493a;
        int i4 = i2 + 1;
        int i5 = (bArr[i2] & 255) << 8;
        int i6 = i2 + 2;
        int i7 = (bArr[i4] & 255) | i5;
        this.f26470b = j2 - 2;
        if (i6 == i3) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        } else {
            segment.f26494b = i6;
        }
        return (short) i7;
    }

    @Override // okio.BufferedSource
    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    @Override // okio.BufferedSource
    public String readString(Charset charset) {
        try {
            return readString(this.f26470b, charset);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public final UnsafeCursor readUnsafe() {
        return readUnsafe(new UnsafeCursor());
    }

    @Override // okio.BufferedSource
    public String readUtf8() {
        try {
            return readString(this.f26470b, Util.UTF_8);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // okio.BufferedSource
    public int readUtf8CodePoint() throws EOFException {
        int i2;
        int i3;
        int i4;
        if (this.f26470b == 0) {
            throw new EOFException();
        }
        byte b2 = getByte(0L);
        if ((b2 & 128) == 0) {
            i2 = b2 & Byte.MAX_VALUE;
            i4 = 0;
            i3 = 1;
        } else if ((b2 & 224) == 192) {
            i2 = b2 & Ascii.US;
            i3 = 2;
            i4 = 128;
        } else if ((b2 & 240) == 224) {
            i2 = b2 & 15;
            i3 = 3;
            i4 = 2048;
        } else {
            if ((b2 & 248) != 240) {
                skip(1L);
                return 65533;
            }
            i2 = b2 & 7;
            i3 = 4;
            i4 = 65536;
        }
        long j2 = i3;
        if (this.f26470b < j2) {
            throw new EOFException("size < " + i3 + ": " + this.f26470b + " (to read code point prefixed 0x" + Integer.toHexString(b2) + ")");
        }
        for (int i5 = 1; i5 < i3; i5++) {
            long j3 = i5;
            byte b3 = getByte(j3);
            if ((b3 & 192) != 128) {
                skip(j3);
                return 65533;
            }
            i2 = (i2 << 6) | (b3 & 63);
        }
        skip(j2);
        if (i2 > 1114111) {
            return 65533;
        }
        if ((i2 < 55296 || i2 > 57343) && i2 >= i4) {
            return i2;
        }
        return 65533;
    }

    @Override // okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws EOFException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return b(jIndexOf);
        }
        long j2 = this.f26470b;
        if (j2 != 0) {
            return readUtf8(j2);
        }
        return null;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() throws EOFException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public boolean request(long j2) {
        return this.f26470b >= j2;
    }

    @Override // okio.BufferedSource
    public void require(long j2) throws EOFException {
        if (this.f26470b < j2) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public int select(Options options) {
        int iC = c(options, false);
        if (iC == -1) {
            return -1;
        }
        try {
            skip(options.f26479a[iC].size());
            return iC;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    public final ByteString sha1() {
        return digest("SHA-1");
    }

    public final ByteString sha256() {
        return digest("SHA-256");
    }

    public final ByteString sha512() {
        return digest("SHA-512");
    }

    public final long size() {
        return this.f26470b;
    }

    @Override // okio.BufferedSource
    public void skip(long j2) throws EOFException {
        while (j2 > 0) {
            if (this.f26469a == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j2, r0.f26495c - r0.f26494b);
            long j3 = iMin;
            this.f26470b -= j3;
            j2 -= j3;
            Segment segment = this.f26469a;
            int i2 = segment.f26494b + iMin;
            segment.f26494b = i2;
            if (i2 == segment.f26495c) {
                this.f26469a = segment.pop();
                SegmentPool.a(segment);
            }
        }
    }

    public final ByteString snapshot() {
        long j2 = this.f26470b;
        if (j2 <= 2147483647L) {
            return snapshot((int) j2);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.f26470b);
    }

    @Override // okio.Source
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        return snapshot().toString();
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long j3 = source.read(this, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j3 == -1) {
                return j2;
            }
            j2 += j3;
        }
    }

    public final Buffer writeTo(OutputStream outputStream) throws IOException {
        return writeTo(outputStream, this.f26470b);
    }

    public Buffer clone() {
        Buffer buffer = new Buffer();
        if (this.f26470b == 0) {
            return buffer;
        }
        Segment segmentA = this.f26469a.a();
        buffer.f26469a = segmentA;
        segmentA.f26499g = segmentA;
        segmentA.f26498f = segmentA;
        Segment segment = this.f26469a;
        while (true) {
            segment = segment.f26498f;
            if (segment == this.f26469a) {
                buffer.f26470b = this.f26470b;
                return buffer;
            }
            buffer.f26469a.f26499g.push(segment.a());
        }
    }

    public final Buffer copyTo(OutputStream outputStream, long j2, long j3) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.f26470b, j2, j3);
        if (j3 == 0) {
            return this;
        }
        Segment segment = this.f26469a;
        while (true) {
            int i2 = segment.f26495c;
            int i3 = segment.f26494b;
            if (j2 < i2 - i3) {
                break;
            }
            j2 -= i2 - i3;
            segment = segment.f26498f;
        }
        while (j3 > 0) {
            int iMin = (int) Math.min(segment.f26495c - r9, j3);
            outputStream.write(segment.f26493a, (int) (segment.f26494b + j2), iMin);
            j3 -= iMin;
            segment = segment.f26498f;
            j2 = 0;
        }
        return this;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j2) {
        return indexOf(b2, j2, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j2) {
        int i2;
        int i3;
        long j3 = 0;
        if (j2 < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment segment = this.f26469a;
        if (segment == null) {
            return -1L;
        }
        long j4 = this.f26470b;
        if (j4 - j2 < j2) {
            while (j4 > j2) {
                segment = segment.f26499g;
                j4 -= segment.f26495c - segment.f26494b;
            }
        } else {
            while (true) {
                long j5 = (segment.f26495c - segment.f26494b) + j3;
                if (j5 >= j2) {
                    break;
                }
                segment = segment.f26498f;
                j3 = j5;
            }
            j4 = j3;
        }
        if (byteString.size() == 2) {
            byte b2 = byteString.getByte(0);
            byte b3 = byteString.getByte(1);
            while (j4 < this.f26470b) {
                byte[] bArr = segment.f26493a;
                i2 = (int) ((segment.f26494b + j2) - j4);
                int i4 = segment.f26495c;
                while (i2 < i4) {
                    byte b4 = bArr[i2];
                    if (b4 == b2 || b4 == b3) {
                        i3 = segment.f26494b;
                        return (i2 - i3) + j4;
                    }
                    i2++;
                }
                j4 += segment.f26495c - segment.f26494b;
                segment = segment.f26498f;
                j2 = j4;
            }
            return -1L;
        }
        byte[] bArrInternalArray = byteString.internalArray();
        while (j4 < this.f26470b) {
            byte[] bArr2 = segment.f26493a;
            i2 = (int) ((segment.f26494b + j2) - j4);
            int i5 = segment.f26495c;
            while (i2 < i5) {
                byte b5 = bArr2[i2];
                for (byte b6 : bArrInternalArray) {
                    if (b5 == b6) {
                        i3 = segment.f26494b;
                        return (i2 - i3) + j4;
                    }
                }
                i2++;
            }
            j4 += segment.f26495c - segment.f26494b;
            segment = segment.f26498f;
            j2 = j4;
        }
        return -1L;
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j2, ByteString byteString, int i2, int i3) {
        if (j2 < 0 || i2 < 0 || i3 < 0 || this.f26470b - j2 < i3 || byteString.size() - i2 < i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (getByte(i4 + j2) != byteString.getByte(i2 + i4)) {
                return false;
            }
        }
        return true;
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr, int i2, int i3) {
        Util.checkOffsetAndCount(bArr.length, i2, i3);
        Segment segment = this.f26469a;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(i3, segment.f26495c - segment.f26494b);
        System.arraycopy(segment.f26493a, segment.f26494b, bArr, i2, iMin);
        int i4 = segment.f26494b + iMin;
        segment.f26494b = i4;
        this.f26470b -= iMin;
        if (i4 == segment.f26495c) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        }
        return iMin;
    }

    public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = true;
        return unsafeCursor;
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long j2) throws EOFException {
        return new ByteString(readByteArray(j2));
    }

    public final Buffer readFrom(InputStream inputStream, long j2) throws IOException {
        if (j2 >= 0) {
            readFrom(inputStream, j2, false);
            return this;
        }
        throw new IllegalArgumentException("byteCount < 0: " + j2);
    }

    public final UnsafeCursor readUnsafe(UnsafeCursor unsafeCursor) {
        if (unsafeCursor.buffer != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        unsafeCursor.buffer = this;
        unsafeCursor.readWrite = false;
        return unsafeCursor;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict(long j2) throws EOFException {
        if (j2 < 0) {
            throw new IllegalArgumentException("limit < 0: " + j2);
        }
        long j3 = j2 != Long.MAX_VALUE ? j2 + 1 : Long.MAX_VALUE;
        long jIndexOf = indexOf((byte) 10, 0L, j3);
        if (jIndexOf != -1) {
            return b(jIndexOf);
        }
        if (j3 < size() && getByte(j3 - 1) == 13 && getByte(j3) == 10) {
            return b(j3);
        }
        Buffer buffer = new Buffer();
        copyTo(buffer, 0L, Math.min(32L, size()));
        throw new EOFException("\\n not found: limit=" + Math.min(size(), j2) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
    }

    @Override // okio.BufferedSink
    public Buffer writeByte(int i2) {
        Segment segmentD = d(1);
        byte[] bArr = segmentD.f26493a;
        int i3 = segmentD.f26495c;
        segmentD.f26495c = i3 + 1;
        bArr[i3] = (byte) i2;
        this.f26470b++;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeDecimalLong(long j2) {
        boolean z2;
        if (j2 == 0) {
            return writeByte(48);
        }
        int i2 = 1;
        if (j2 < 0) {
            j2 = -j2;
            if (j2 < 0) {
                return writeUtf8("-9223372036854775808");
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (j2 >= 100000000) {
            i2 = j2 < MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US ? j2 < 10000000000L ? j2 < C.NANOS_PER_SECOND ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        } else if (j2 >= 10000) {
            i2 = j2 < 1000000 ? j2 < SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US ? 5 : 6 : j2 < 10000000 ? 7 : 8;
        } else if (j2 >= 100) {
            i2 = j2 < 1000 ? 3 : 4;
        } else if (j2 >= 10) {
            i2 = 2;
        }
        if (z2) {
            i2++;
        }
        Segment segmentD = d(i2);
        byte[] bArr = segmentD.f26493a;
        int i3 = segmentD.f26495c + i2;
        while (j2 != 0) {
            i3--;
            bArr[i3] = DIGITS[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z2) {
            bArr[i3 - 1] = 45;
        }
        segmentD.f26495c += i2;
        this.f26470b += i2;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeHexadecimalUnsignedLong(long j2) {
        if (j2 == 0) {
            return writeByte(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j2)) / 4) + 1;
        Segment segmentD = d(iNumberOfTrailingZeros);
        byte[] bArr = segmentD.f26493a;
        int i2 = segmentD.f26495c;
        for (int i3 = (i2 + iNumberOfTrailingZeros) - 1; i3 >= i2; i3--) {
            bArr[i3] = DIGITS[(int) (15 & j2)];
            j2 >>>= 4;
        }
        segmentD.f26495c += iNumberOfTrailingZeros;
        this.f26470b += iNumberOfTrailingZeros;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeInt(int i2) {
        Segment segmentD = d(4);
        byte[] bArr = segmentD.f26493a;
        int i3 = segmentD.f26495c;
        bArr[i3] = (byte) ((i2 >>> 24) & 255);
        bArr[i3 + 1] = (byte) ((i2 >>> 16) & 255);
        bArr[i3 + 2] = (byte) ((i2 >>> 8) & 255);
        bArr[i3 + 3] = (byte) (i2 & 255);
        segmentD.f26495c = i3 + 4;
        this.f26470b += 4;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeIntLe(int i2) {
        return writeInt(Util.reverseBytesInt(i2));
    }

    @Override // okio.BufferedSink
    public Buffer writeLong(long j2) {
        Segment segmentD = d(8);
        byte[] bArr = segmentD.f26493a;
        int i2 = segmentD.f26495c;
        bArr[i2] = (byte) ((j2 >>> 56) & 255);
        bArr[i2 + 1] = (byte) ((j2 >>> 48) & 255);
        bArr[i2 + 2] = (byte) ((j2 >>> 40) & 255);
        bArr[i2 + 3] = (byte) ((j2 >>> 32) & 255);
        bArr[i2 + 4] = (byte) ((j2 >>> 24) & 255);
        bArr[i2 + 5] = (byte) ((j2 >>> 16) & 255);
        bArr[i2 + 6] = (byte) ((j2 >>> 8) & 255);
        bArr[i2 + 7] = (byte) (j2 & 255);
        segmentD.f26495c = i2 + 8;
        this.f26470b += 8;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeLongLe(long j2) {
        return writeLong(Util.reverseBytesLong(j2));
    }

    @Override // okio.BufferedSink
    public Buffer writeShort(int i2) {
        Segment segmentD = d(2);
        byte[] bArr = segmentD.f26493a;
        int i3 = segmentD.f26495c;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        bArr[i3 + 1] = (byte) (i2 & 255);
        segmentD.f26495c = i3 + 2;
        this.f26470b += 2;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeShortLe(int i2) {
        return writeShort((int) Util.reverseBytesShort((short) i2));
    }

    public final Buffer writeTo(OutputStream outputStream, long j2) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.f26470b, 0L, j2);
        Segment segment = this.f26469a;
        while (j2 > 0) {
            int iMin = (int) Math.min(j2, segment.f26495c - segment.f26494b);
            outputStream.write(segment.f26493a, segment.f26494b, iMin);
            int i2 = segment.f26494b + iMin;
            segment.f26494b = i2;
            long j3 = iMin;
            this.f26470b -= j3;
            j2 -= j3;
            if (i2 == segment.f26495c) {
                Segment segmentPop = segment.pop();
                this.f26469a = segmentPop;
                SegmentPool.a(segment);
                segment = segmentPop;
            }
        }
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8CodePoint(int i2) {
        if (i2 < 128) {
            writeByte(i2);
        } else if (i2 < 2048) {
            writeByte((i2 >> 6) | 192);
            writeByte((i2 & 63) | 128);
        } else if (i2 < 65536) {
            if (i2 < 55296 || i2 > 57343) {
                writeByte((i2 >> 12) | 224);
                writeByte(((i2 >> 6) & 63) | 128);
                writeByte((i2 & 63) | 128);
            } else {
                writeByte(63);
            }
        } else {
            if (i2 > 1114111) {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
            }
            writeByte((i2 >> 18) | 240);
            writeByte(((i2 >> 12) & 63) | 128);
            writeByte(((i2 >> 6) & 63) | 128);
            writeByte((i2 & 63) | 128);
        }
        return this;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j2, long j3) {
        Segment segment;
        long j4 = 0;
        if (j2 >= 0 && j3 >= j2) {
            long j5 = this.f26470b;
            long j6 = j3 > j5 ? j5 : j3;
            if (j2 == j6 || (segment = this.f26469a) == null) {
                return -1L;
            }
            if (j5 - j2 < j2) {
                while (j5 > j2) {
                    segment = segment.f26499g;
                    j5 -= segment.f26495c - segment.f26494b;
                }
            } else {
                while (true) {
                    long j7 = (segment.f26495c - segment.f26494b) + j4;
                    if (j7 >= j2) {
                        break;
                    }
                    segment = segment.f26498f;
                    j4 = j7;
                }
                j5 = j4;
            }
            long j8 = j2;
            while (j5 < j6) {
                byte[] bArr = segment.f26493a;
                int iMin = (int) Math.min(segment.f26495c, (segment.f26494b + j6) - j5);
                for (int i2 = (int) ((segment.f26494b + j8) - j5); i2 < iMin; i2++) {
                    if (bArr[i2] == b2) {
                        return (i2 - segment.f26494b) + j5;
                    }
                }
                j5 += segment.f26495c - segment.f26494b;
                segment = segment.f26498f;
                j8 = j5;
            }
            return -1L;
        }
        throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", Long.valueOf(this.f26470b), Long.valueOf(j2), Long.valueOf(j3)));
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long j2) throws EOFException {
        Util.checkOffsetAndCount(this.f26470b, 0L, j2);
        if (j2 <= 2147483647L) {
            byte[] bArr = new byte[(int) j2];
            readFully(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
    }

    @Override // okio.BufferedSource
    public String readString(long j2, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.f26470b, 0L, j2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        }
        if (j2 == 0) {
            return "";
        }
        Segment segment = this.f26469a;
        int i2 = segment.f26494b;
        if (i2 + j2 > segment.f26495c) {
            return new String(readByteArray(j2), charset);
        }
        String str = new String(segment.f26493a, i2, (int) j2, charset);
        int i3 = (int) (segment.f26494b + j2);
        segment.f26494b = i3;
        this.f26470b -= j2;
        if (i3 == segment.f26495c) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        }
        return str;
    }

    @Override // okio.BufferedSource
    public String readUtf8(long j2) throws EOFException {
        return readString(j2, Util.UTF_8);
    }

    @Override // okio.BufferedSink
    public Buffer writeString(String str, Charset charset) {
        return writeString(str, 0, str.length(), charset);
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str) {
        return writeUtf8(str, 0, str.length());
    }

    private void readFrom(InputStream inputStream, long j2, boolean z2) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (j2 <= 0 && !z2) {
                return;
            }
            Segment segmentD = d(1);
            int i2 = inputStream.read(segmentD.f26493a, segmentD.f26495c, (int) Math.min(j2, 8192 - segmentD.f26495c));
            if (i2 == -1) {
                if (!z2) {
                    throw new EOFException();
                }
                return;
            } else {
                segmentD.f26495c += i2;
                long j3 = i2;
                this.f26470b += j3;
                j2 -= j3;
            }
        }
    }

    public final ByteString snapshot(int i2) {
        if (i2 == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, i2);
    }

    @Override // okio.BufferedSink
    public Buffer write(ByteString byteString) {
        if (byteString != null) {
            byteString.write(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    @Override // okio.BufferedSink
    public Buffer writeString(String str, int i2, int i3, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i2);
        }
        if (i3 >= i2) {
            if (i3 <= str.length()) {
                if (charset != null) {
                    if (charset.equals(Util.UTF_8)) {
                        return writeUtf8(str, i2, i3);
                    }
                    byte[] bytes = str.substring(i2, i3).getBytes(charset);
                    return write(bytes, 0, bytes.length);
                }
                throw new IllegalArgumentException("charset == null");
            }
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        }
        throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String str, int i2, int i3) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("beginIndex < 0: " + i2);
        }
        if (i3 >= i2) {
            if (i3 > str.length()) {
                throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
            }
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt < 128) {
                    Segment segmentD = d(1);
                    byte[] bArr = segmentD.f26493a;
                    int i4 = segmentD.f26495c - i2;
                    int iMin = Math.min(i3, 8192 - i4);
                    int i5 = i2 + 1;
                    bArr[i2 + i4] = (byte) cCharAt;
                    while (i5 < iMin) {
                        char cCharAt2 = str.charAt(i5);
                        if (cCharAt2 >= 128) {
                            break;
                        }
                        bArr[i5 + i4] = (byte) cCharAt2;
                        i5++;
                    }
                    int i6 = segmentD.f26495c;
                    int i7 = (i4 + i5) - i6;
                    segmentD.f26495c = i6 + i7;
                    this.f26470b += i7;
                    i2 = i5;
                } else {
                    if (cCharAt < 2048) {
                        writeByte((cCharAt >> 6) | 192);
                        writeByte((cCharAt & '?') | 128);
                    } else if (cCharAt >= 55296 && cCharAt <= 57343) {
                        int i8 = i2 + 1;
                        char cCharAt3 = i8 < i3 ? str.charAt(i8) : (char) 0;
                        if (cCharAt <= 56319 && cCharAt3 >= 56320 && cCharAt3 <= 57343) {
                            int i9 = (((cCharAt & 10239) << 10) | (9215 & cCharAt3)) + 65536;
                            writeByte((i9 >> 18) | 240);
                            writeByte(((i9 >> 12) & 63) | 128);
                            writeByte(((i9 >> 6) & 63) | 128);
                            writeByte((i9 & 63) | 128);
                            i2 += 2;
                        } else {
                            writeByte(63);
                            i2 = i8;
                        }
                    } else {
                        writeByte((cCharAt >> '\f') | 224);
                        writeByte(((cCharAt >> 6) & 63) | 128);
                        writeByte((cCharAt & '?') | 128);
                    }
                    i2++;
                }
            }
            return this;
        }
        throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
    }

    private boolean rangeEquals(Segment segment, int i2, ByteString byteString, int i3, int i4) {
        int i5 = segment.f26495c;
        byte[] bArr = segment.f26493a;
        while (i3 < i4) {
            if (i2 == i5) {
                segment = segment.f26498f;
                byte[] bArr2 = segment.f26493a;
                bArr = bArr2;
                i2 = segment.f26494b;
                i5 = segment.f26495c;
            }
            if (bArr[i2] != byteString.getByte(i3)) {
                return false;
            }
            i2++;
            i3++;
        }
        return true;
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] bArr) throws EOFException {
        int i2 = 0;
        while (i2 < bArr.length) {
            int i3 = read(bArr, i2, bArr.length - i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 += i3;
        }
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] bArr) {
        if (bArr != null) {
            return write(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            long j2 = i3;
            Util.checkOffsetAndCount(bArr.length, i2, j2);
            int i4 = i3 + i2;
            while (i2 < i4) {
                Segment segmentD = d(1);
                int iMin = Math.min(i4 - i2, 8192 - segmentD.f26495c);
                System.arraycopy(bArr, i2, segmentD.f26493a, segmentD.f26495c, iMin);
                i2 += iMin;
                segmentD.f26495c += iMin;
            }
            this.f26470b += j2;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final Buffer copyTo(Buffer buffer, long j2, long j3) {
        if (buffer != null) {
            Util.checkOffsetAndCount(this.f26470b, j2, j3);
            if (j3 == 0) {
                return this;
            }
            buffer.f26470b += j3;
            Segment segment = this.f26469a;
            while (true) {
                int i2 = segment.f26495c;
                int i3 = segment.f26494b;
                if (j2 < i2 - i3) {
                    break;
                }
                j2 -= i2 - i3;
                segment = segment.f26498f;
            }
            while (j3 > 0) {
                Segment segmentA = segment.a();
                int i4 = (int) (segmentA.f26494b + j2);
                segmentA.f26494b = i4;
                segmentA.f26495c = Math.min(i4 + ((int) j3), segmentA.f26495c);
                Segment segment2 = buffer.f26469a;
                if (segment2 == null) {
                    segmentA.f26499g = segmentA;
                    segmentA.f26498f = segmentA;
                    buffer.f26469a = segmentA;
                } else {
                    segment2.f26499g.push(segmentA);
                }
                j3 -= segmentA.f26495c - segmentA.f26494b;
                segment = segment.f26498f;
                j2 = 0;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        Segment segment = this.f26469a;
        if (segment == null) {
            return -1;
        }
        int iMin = Math.min(byteBuffer.remaining(), segment.f26495c - segment.f26494b);
        byteBuffer.put(segment.f26493a, segment.f26494b, iMin);
        int i2 = segment.f26494b + iMin;
        segment.f26494b = i2;
        this.f26470b -= iMin;
        if (i2 == segment.f26495c) {
            this.f26469a = segment.pop();
            SegmentPool.a(segment);
        }
        return iMin;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer != null) {
            int iRemaining = byteBuffer.remaining();
            int i2 = iRemaining;
            while (i2 > 0) {
                Segment segmentD = d(1);
                int iMin = Math.min(i2, 8192 - segmentD.f26495c);
                byteBuffer.get(segmentD.f26493a, segmentD.f26495c, iMin);
                i2 -= iMin;
                segmentD.f26495c += iMin;
            }
            this.f26470b += iRemaining;
            return iRemaining;
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString, long j2) throws IOException {
        byte[] bArr;
        if (byteString.size() == 0) {
            throw new IllegalArgumentException("bytes is empty");
        }
        long j3 = 0;
        if (j2 >= 0) {
            Segment segment = this.f26469a;
            long j4 = -1;
            if (segment == null) {
                return -1L;
            }
            long j5 = this.f26470b;
            if (j5 - j2 < j2) {
                while (j5 > j2) {
                    segment = segment.f26499g;
                    j5 -= segment.f26495c - segment.f26494b;
                }
            } else {
                while (true) {
                    long j6 = (segment.f26495c - segment.f26494b) + j3;
                    if (j6 >= j2) {
                        break;
                    }
                    segment = segment.f26498f;
                    j3 = j6;
                }
                j5 = j3;
            }
            byte b2 = byteString.getByte(0);
            int size = byteString.size();
            long j7 = 1 + (this.f26470b - size);
            long j8 = j2;
            Segment segment2 = segment;
            long j9 = j5;
            while (j9 < j7) {
                byte[] bArr2 = segment2.f26493a;
                int iMin = (int) Math.min(segment2.f26495c, (segment2.f26494b + j7) - j9);
                int i2 = (int) ((segment2.f26494b + j8) - j9);
                while (i2 < iMin) {
                    if (bArr2[i2] == b2) {
                        bArr = bArr2;
                        if (rangeEquals(segment2, i2 + 1, byteString, 1, size)) {
                            return (i2 - segment2.f26494b) + j9;
                        }
                    } else {
                        bArr = bArr2;
                    }
                    i2++;
                    bArr2 = bArr;
                }
                j9 += segment2.f26495c - segment2.f26494b;
                segment2 = segment2.f26498f;
                j8 = j9;
                j4 = -1;
            }
            return j4;
        }
        throw new IllegalArgumentException("fromIndex < 0");
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 >= 0) {
            long j3 = this.f26470b;
            if (j3 == 0) {
                return -1L;
            }
            if (j2 > j3) {
                j2 = j3;
            }
            buffer.write(this, j2);
            return j2;
        }
        throw new IllegalArgumentException("byteCount < 0: " + j2);
    }

    @Override // okio.BufferedSink
    public BufferedSink write(Source source, long j2) throws IOException {
        while (j2 > 0) {
            long j3 = source.read(this, j2);
            if (j3 == -1) {
                throw new EOFException();
            }
            j2 -= j3;
        }
        return this;
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (buffer != this) {
            Util.checkOffsetAndCount(buffer.f26470b, 0L, j2);
            while (j2 > 0) {
                Segment segment = buffer.f26469a;
                if (j2 < segment.f26495c - segment.f26494b) {
                    Segment segment2 = this.f26469a;
                    Segment segment3 = segment2 != null ? segment2.f26499g : null;
                    if (segment3 != null && segment3.f26497e) {
                        if ((segment3.f26495c + j2) - (segment3.f26496d ? 0 : segment3.f26494b) <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                            segment.writeTo(segment3, (int) j2);
                            buffer.f26470b -= j2;
                            this.f26470b += j2;
                            return;
                        }
                    }
                    buffer.f26469a = segment.split((int) j2);
                }
                Segment segment4 = buffer.f26469a;
                long j3 = segment4.f26495c - segment4.f26494b;
                buffer.f26469a = segment4.pop();
                Segment segment5 = this.f26469a;
                if (segment5 == null) {
                    this.f26469a = segment4;
                    segment4.f26499g = segment4;
                    segment4.f26498f = segment4;
                } else {
                    segment5.f26499g.push(segment4).compact();
                }
                buffer.f26470b -= j3;
                this.f26470b += j3;
                j2 -= j3;
            }
            return;
        }
        throw new IllegalArgumentException("source == this");
    }
}

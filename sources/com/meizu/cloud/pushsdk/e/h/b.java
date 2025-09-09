package com.meizu.cloud.pushsdk.e.h;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.media3.common.C;
import androidx.media3.exoplayer.MediaPeriodQueue;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public final class b implements d, c, Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f19485a = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: b, reason: collision with root package name */
    j f19486b;

    /* renamed from: c, reason: collision with root package name */
    long f19487c;

    class a extends InputStream {
        a() {
        }

        @Override // java.io.InputStream
        public int available() {
            return (int) Math.min(b.this.f19487c, 2147483647L);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.InputStream
        public int read() {
            b bVar = b.this;
            if (bVar.f19487c > 0) {
                return bVar.i() & 255;
            }
            return -1;
        }

        public String toString() {
            return b.this + ".inputStream()";
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            return b.this.b(bArr, i2, i3);
        }
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public long a(m mVar) throws IOException {
        if (mVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long jB = mVar.b(this, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
            if (jB == -1) {
                return j2;
            }
            j2 += jB;
        }
    }

    public int b(byte[] bArr, int i2, int i3) {
        o.a(bArr.length, i2, i3);
        j jVar = this.f19486b;
        if (jVar == null) {
            return -1;
        }
        int iMin = Math.min(i3, jVar.f19509c - jVar.f19508b);
        System.arraycopy(jVar.f19507a, jVar.f19508b, bArr, i2, iMin);
        int i4 = jVar.f19508b + iMin;
        jVar.f19508b = i4;
        this.f19487c -= iMin;
        if (i4 == jVar.f19509c) {
            this.f19486b = jVar.b();
            k.a(jVar);
        }
        return iMin;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    public b c() {
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.m, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.e.h.l
    public void close() {
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public b a(long j2) {
        boolean z2;
        if (j2 == 0) {
            return b(48);
        }
        int i2 = 1;
        if (j2 < 0) {
            j2 = -j2;
            if (j2 < 0) {
                return a("-9223372036854775808");
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
        j jVarA = a(i2);
        byte[] bArr = jVarA.f19507a;
        int i3 = jVarA.f19509c + i2;
        while (j2 != 0) {
            i3--;
            bArr[i3] = f19485a[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z2) {
            bArr[i3 - 1] = 45;
        }
        jVarA.f19509c += i2;
        this.f19487c += i2;
        return this;
    }

    public b e(long j2) {
        if (j2 == 0) {
            return b(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j2)) / 4) + 1;
        j jVarA = a(iNumberOfTrailingZeros);
        byte[] bArr = jVarA.f19507a;
        int i2 = jVarA.f19509c;
        for (int i3 = (i2 + iNumberOfTrailingZeros) - 1; i3 >= i2; i3--) {
            bArr[i3] = f19485a[(int) (15 & j2)];
            j2 >>>= 4;
        }
        jVarA.f19509c += iNumberOfTrailingZeros;
        this.f19487c += iNumberOfTrailingZeros;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        long j2 = this.f19487c;
        if (j2 != bVar.f19487c) {
            return false;
        }
        long j3 = 0;
        if (j2 == 0) {
            return true;
        }
        j jVar = this.f19486b;
        j jVar2 = bVar.f19486b;
        int i2 = jVar.f19508b;
        int i3 = jVar2.f19508b;
        while (j3 < this.f19487c) {
            long jMin = Math.min(jVar.f19509c - i2, jVar2.f19509c - i3);
            int i4 = 0;
            while (i4 < jMin) {
                int i5 = i2 + 1;
                int i6 = i3 + 1;
                if (jVar.f19507a[i2] != jVar2.f19507a[i3]) {
                    return false;
                }
                i4++;
                i2 = i5;
                i3 = i6;
            }
            if (i2 == jVar.f19509c) {
                jVar = jVar.f19512f;
                i2 = jVar.f19508b;
            }
            if (i3 == jVar2.f19509c) {
                jVar2 = jVar2.f19512f;
                i3 = jVar2.f19508b;
            }
            j3 += jMin;
        }
        return true;
    }

    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public b clone() {
        b bVar = new b();
        if (this.f19487c == 0) {
            return bVar;
        }
        j jVar = new j(this.f19486b);
        bVar.f19486b = jVar;
        jVar.f19513g = jVar;
        jVar.f19512f = jVar;
        j jVar2 = this.f19486b;
        while (true) {
            jVar2 = jVar2.f19512f;
            if (jVar2 == this.f19486b) {
                bVar.f19487c = this.f19487c;
                return bVar;
            }
            bVar.f19486b.f19513g.a(new j(jVar2));
        }
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l, java.io.Flushable
    public void flush() {
    }

    public long g() {
        long j2 = this.f19487c;
        if (j2 == 0) {
            return 0L;
        }
        j jVar = this.f19486b.f19513g;
        return (jVar.f19509c >= 2048 || !jVar.f19511e) ? j2 : j2 - (r3 - jVar.f19508b);
    }

    public boolean h() {
        return this.f19487c == 0;
    }

    public int hashCode() {
        j jVar = this.f19486b;
        if (jVar == null) {
            return 0;
        }
        int i2 = 1;
        do {
            int i3 = jVar.f19509c;
            for (int i4 = jVar.f19508b; i4 < i3; i4++) {
                i2 = (i2 * 31) + jVar.f19507a[i4];
            }
            jVar = jVar.f19512f;
        } while (jVar != this.f19486b);
        return i2;
    }

    public byte i() {
        long j2 = this.f19487c;
        if (j2 == 0) {
            throw new IllegalStateException("size == 0");
        }
        j jVar = this.f19486b;
        int i2 = jVar.f19508b;
        int i3 = jVar.f19509c;
        int i4 = i2 + 1;
        byte b2 = jVar.f19507a[i2];
        this.f19487c = j2 - 1;
        if (i4 == i3) {
            this.f19486b = jVar.b();
            k.a(jVar);
        } else {
            jVar.f19508b = i4;
        }
        return b2;
    }

    public e j() {
        return new e(b());
    }

    public long k() {
        return this.f19487c;
    }

    public String toString() throws NoSuchAlgorithmException {
        long j2 = this.f19487c;
        if (j2 == 0) {
            return "Buffer[size=0]";
        }
        if (j2 <= 16) {
            return String.format("Buffer[size=%s data=%s]", Long.valueOf(this.f19487c), clone().j().a());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            j jVar = this.f19486b;
            byte[] bArr = jVar.f19507a;
            int i2 = jVar.f19508b;
            messageDigest.update(bArr, i2, jVar.f19509c - i2);
            j jVar2 = this.f19486b;
            while (true) {
                jVar2 = jVar2.f19512f;
                if (jVar2 == this.f19486b) {
                    return String.format("Buffer[size=%s md5=%s]", Long.valueOf(this.f19487c), e.a(messageDigest.digest()).a());
                }
                byte[] bArr2 = jVar2.f19507a;
                int i3 = jVar2.f19508b;
                messageDigest.update(bArr2, i3, jVar2.f19509c - i3);
            }
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public b a(String str, int i2, int i3) {
        char cCharAt;
        int i4;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i2);
        }
        if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        }
        if (i3 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        }
        while (i2 < i3) {
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 < 128) {
                j jVarA = a(1);
                byte[] bArr = jVarA.f19507a;
                int i5 = jVarA.f19509c - i2;
                int iMin = Math.min(i3, 2048 - i5);
                int i6 = i2 + 1;
                bArr[i2 + i5] = (byte) cCharAt2;
                while (true) {
                    i2 = i6;
                    if (i2 >= iMin || (cCharAt = str.charAt(i2)) >= 128) {
                        break;
                    }
                    i6 = i2 + 1;
                    bArr[i2 + i5] = (byte) cCharAt;
                }
                int i7 = jVarA.f19509c;
                int i8 = (i5 + i2) - i7;
                jVarA.f19509c = i7 + i8;
                this.f19487c += i8;
            } else {
                if (cCharAt2 < 2048) {
                    i4 = (cCharAt2 >> 6) | 192;
                } else if (cCharAt2 < 55296 || cCharAt2 > 57343) {
                    b((cCharAt2 >> '\f') | 224);
                    i4 = ((cCharAt2 >> 6) & 63) | 128;
                } else {
                    int i9 = i2 + 1;
                    char cCharAt3 = i9 < i3 ? str.charAt(i9) : (char) 0;
                    if (cCharAt2 > 56319 || cCharAt3 < 56320 || cCharAt3 > 57343) {
                        b(63);
                        i2 = i9;
                    } else {
                        int i10 = (((cCharAt2 & 10239) << 10) | (9215 & cCharAt3)) + 65536;
                        b((i10 >> 18) | 240);
                        b(((i10 >> 12) & 63) | 128);
                        b(((i10 >> 6) & 63) | 128);
                        b((i10 & 63) | 128);
                        i2 += 2;
                    }
                }
                b(i4);
                b((cCharAt2 & '?') | 128);
                i2++;
            }
        }
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.m
    public long b(b bVar, long j2) {
        if (bVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        long j3 = this.f19487c;
        if (j3 == 0) {
            return -1L;
        }
        if (j2 > j3) {
            j2 = j3;
        }
        bVar.a(this, j2);
        return j2;
    }

    public b c(int i2) {
        int i3;
        int i4;
        if (i2 >= 128) {
            if (i2 < 2048) {
                i4 = (i2 >> 6) | 192;
            } else {
                if (i2 < 65536) {
                    if (i2 >= 55296 && i2 <= 57343) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
                    }
                    i3 = (i2 >> 12) | 224;
                } else {
                    if (i2 > 1114111) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
                    }
                    b((i2 >> 18) | 240);
                    i3 = ((i2 >> 12) & 63) | 128;
                }
                b(i3);
                i4 = ((i2 >> 6) & 63) | 128;
            }
            b(i4);
            i2 = (i2 & 63) | 128;
        }
        b(i2);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public InputStream d() {
        return new a();
    }

    public void e() {
        try {
            c(this.f19487c);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public b b(int i2) {
        j jVarA = a(1);
        byte[] bArr = jVarA.f19507a;
        int i3 = jVarA.f19509c;
        jVarA.f19509c = i3 + 1;
        bArr[i3] = (byte) i2;
        this.f19487c++;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public b a(byte[] bArr) {
        if (bArr != null) {
            return a(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public b a(e eVar) {
        if (eVar == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        eVar.a(this);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public b a(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = i3;
        o.a(bArr.length, i2, j2);
        int i4 = i3 + i2;
        while (i2 < i4) {
            j jVarA = a(1);
            int iMin = Math.min(i4 - i2, 2048 - jVarA.f19509c);
            System.arraycopy(bArr, i2, jVarA.f19507a, jVarA.f19509c, iMin);
            i2 += iMin;
            jVarA.f19509c += iMin;
        }
        this.f19487c += j2;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.c
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public b a(String str) {
        return a(str, 0, str.length());
    }

    public void c(long j2) throws EOFException {
        while (j2 > 0) {
            if (this.f19486b == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j2, r0.f19509c - r0.f19508b);
            long j3 = iMin;
            this.f19487c -= j3;
            j2 -= j3;
            j jVar = this.f19486b;
            int i2 = jVar.f19508b + iMin;
            jVar.f19508b = i2;
            if (i2 == jVar.f19509c) {
                this.f19486b = jVar.b();
                k.a(jVar);
            }
        }
    }

    public void b(byte[] bArr) throws EOFException {
        int i2 = 0;
        while (i2 < bArr.length) {
            int iB = b(bArr, i2, bArr.length - i2);
            if (iB == -1) {
                throw new EOFException();
            }
            i2 += iB;
        }
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public byte[] b() {
        try {
            return b(this.f19487c);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    j a(int i2) {
        if (i2 < 1 || i2 > 2048) {
            throw new IllegalArgumentException();
        }
        j jVar = this.f19486b;
        if (jVar != null) {
            j jVar2 = jVar.f19513g;
            return (jVar2.f19509c + i2 > 2048 || !jVar2.f19511e) ? jVar2.a(k.a()) : jVar2;
        }
        j jVarA = k.a();
        this.f19486b = jVarA;
        jVarA.f19513g = jVarA;
        jVarA.f19512f = jVarA;
        return jVarA;
    }

    public byte[] b(long j2) throws EOFException {
        o.a(this.f19487c, 0L, j2);
        if (j2 <= 2147483647L) {
            byte[] bArr = new byte[(int) j2];
            b(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
    }

    @Override // com.meizu.cloud.pushsdk.e.h.d
    public String a() {
        try {
            return a(this.f19487c, o.f19519a);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public String a(long j2, Charset charset) throws EOFException {
        o.a(this.f19487c, 0L, j2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        }
        if (j2 == 0) {
            return "";
        }
        j jVar = this.f19486b;
        int i2 = jVar.f19508b;
        if (i2 + j2 > jVar.f19509c) {
            return new String(b(j2), charset);
        }
        String str = new String(jVar.f19507a, i2, (int) j2, charset);
        int i3 = (int) (jVar.f19508b + j2);
        jVar.f19508b = i3;
        this.f19487c -= j2;
        if (i3 == jVar.f19509c) {
            this.f19486b = jVar.b();
            k.a(jVar);
        }
        return str;
    }

    @Override // com.meizu.cloud.pushsdk.e.h.l
    public void a(b bVar, long j2) {
        if (bVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (bVar == this) {
            throw new IllegalArgumentException("source == this");
        }
        o.a(bVar.f19487c, 0L, j2);
        while (j2 > 0) {
            j jVar = bVar.f19486b;
            if (j2 < jVar.f19509c - jVar.f19508b) {
                j jVar2 = this.f19486b;
                j jVar3 = jVar2 != null ? jVar2.f19513g : null;
                if (jVar3 != null && jVar3.f19511e) {
                    if ((jVar3.f19509c + j2) - (jVar3.f19510d ? 0 : jVar3.f19508b) <= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) {
                        jVar.a(jVar3, (int) j2);
                        bVar.f19487c -= j2;
                        this.f19487c += j2;
                        return;
                    }
                }
                bVar.f19486b = jVar.a((int) j2);
            }
            j jVar4 = bVar.f19486b;
            long j3 = jVar4.f19509c - jVar4.f19508b;
            bVar.f19486b = jVar4.b();
            j jVar5 = this.f19486b;
            if (jVar5 == null) {
                this.f19486b = jVar4;
                jVar4.f19513g = jVar4;
                jVar4.f19512f = jVar4;
            } else {
                jVar5.f19513g.a(jVar4).a();
            }
            bVar.f19487c -= j3;
            this.f19487c += j3;
            j2 -= j3;
        }
    }
}

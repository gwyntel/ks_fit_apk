package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/* loaded from: classes5.dex */
final class SegmentedByteString extends ByteString {
    final transient int[] directory;
    final transient byte[][] segments;

    SegmentedByteString(Buffer buffer, int i2) {
        super(null);
        Util.checkOffsetAndCount(buffer.f26470b, 0L, i2);
        Segment segment = buffer.f26469a;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = segment.f26495c;
            int i7 = segment.f26494b;
            if (i6 == i7) {
                throw new AssertionError("s.limit == s.pos");
            }
            i4 += i6 - i7;
            i5++;
            segment = segment.f26498f;
        }
        this.segments = new byte[i5][];
        this.directory = new int[i5 * 2];
        Segment segment2 = buffer.f26469a;
        int i8 = 0;
        while (i3 < i2) {
            byte[][] bArr = this.segments;
            bArr[i8] = segment2.f26493a;
            int i9 = segment2.f26495c;
            int i10 = segment2.f26494b;
            i3 += i9 - i10;
            if (i3 > i2) {
                i3 = i2;
            }
            int[] iArr = this.directory;
            iArr[i8] = i3;
            iArr[bArr.length + i8] = i10;
            segment2.f26496d = true;
            i8++;
            segment2 = segment2.f26498f;
        }
    }

    private int segment(int i2) {
        int iBinarySearch = Arrays.binarySearch(this.directory, 0, this.segments.length, i2 + 1);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }

    private ByteString toByteString() {
        return new ByteString(toByteArray());
    }

    private Object writeReplace() {
        return toByteString();
    }

    @Override // okio.ByteString
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // okio.ByteString
    public String base64() {
        return toByteString().base64();
    }

    @Override // okio.ByteString
    public String base64Url() {
        return toByteString().base64Url();
    }

    @Override // okio.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(0, byteString, 0, size())) {
                return true;
            }
        }
        return false;
    }

    @Override // okio.ByteString
    public byte getByte(int i2) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], i2, 1L);
        int iSegment = segment(i2);
        int i3 = iSegment == 0 ? 0 : this.directory[iSegment - 1];
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        return bArr[iSegment][(i2 - i3) + iArr[bArr.length + iSegment]];
    }

    @Override // okio.ByteString
    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int length = this.segments.length;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i3 < length) {
            byte[] bArr = this.segments[i3];
            int[] iArr = this.directory;
            int i6 = iArr[length + i3];
            int i7 = iArr[i3];
            int i8 = (i7 - i5) + i6;
            while (i6 < i8) {
                i4 = (i4 * 31) + bArr[i6];
                i6++;
            }
            i3++;
            i5 = i7;
        }
        this.hashCode = i4;
        return i4;
    }

    @Override // okio.ByteString
    public String hex() {
        return toByteString().hex();
    }

    @Override // okio.ByteString
    public ByteString hmacSha1(ByteString byteString) {
        return toByteString().hmacSha1(byteString);
    }

    @Override // okio.ByteString
    public ByteString hmacSha256(ByteString byteString) {
        return toByteString().hmacSha256(byteString);
    }

    @Override // okio.ByteString
    public int indexOf(byte[] bArr, int i2) {
        return toByteString().indexOf(bArr, i2);
    }

    @Override // okio.ByteString
    byte[] internalArray() {
        return toByteArray();
    }

    @Override // okio.ByteString
    public int lastIndexOf(byte[] bArr, int i2) {
        return toByteString().lastIndexOf(bArr, i2);
    }

    @Override // okio.ByteString
    public ByteString md5() {
        return toByteString().md5();
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i2, ByteString byteString, int i3, int i4) {
        if (i2 < 0 || i2 > size() - i4) {
            return false;
        }
        int iSegment = segment(i2);
        while (i4 > 0) {
            int i5 = iSegment == 0 ? 0 : this.directory[iSegment - 1];
            int iMin = Math.min(i4, ((this.directory[iSegment] - i5) + i5) - i2);
            int[] iArr = this.directory;
            byte[][] bArr = this.segments;
            if (!byteString.rangeEquals(i3, bArr[iSegment], (i2 - i5) + iArr[bArr.length + iSegment], iMin)) {
                return false;
            }
            i2 += iMin;
            i3 += iMin;
            i4 -= iMin;
            iSegment++;
        }
        return true;
    }

    @Override // okio.ByteString
    public ByteString sha1() {
        return toByteString().sha1();
    }

    @Override // okio.ByteString
    public ByteString sha256() {
        return toByteString().sha256();
    }

    @Override // okio.ByteString
    public int size() {
        return this.directory[this.segments.length - 1];
    }

    @Override // okio.ByteString
    public String string(Charset charset) {
        return toByteString().string(charset);
    }

    @Override // okio.ByteString
    public ByteString substring(int i2) {
        return toByteString().substring(i2);
    }

    @Override // okio.ByteString
    public ByteString toAsciiLowercase() {
        return toByteString().toAsciiLowercase();
    }

    @Override // okio.ByteString
    public ByteString toAsciiUppercase() {
        return toByteString().toAsciiUppercase();
    }

    @Override // okio.ByteString
    public byte[] toByteArray() {
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        byte[] bArr2 = new byte[iArr[bArr.length - 1]];
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr2 = this.directory;
            int i4 = iArr2[length + i2];
            int i5 = iArr2[i2];
            System.arraycopy(this.segments[i2], i4, bArr2, i3, i5 - i3);
            i2++;
            i3 = i5;
        }
        return bArr2;
    }

    @Override // okio.ByteString
    public String toString() {
        return toByteString().toString();
    }

    @Override // okio.ByteString
    public String utf8() {
        return toByteString().utf8();
    }

    @Override // okio.ByteString
    public void write(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        int length = this.segments.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr = this.directory;
            int i4 = iArr[length + i2];
            int i5 = iArr[i2];
            outputStream.write(this.segments[i2], i4, i5 - i3);
            i2++;
            i3 = i5;
        }
    }

    @Override // okio.ByteString
    public ByteString substring(int i2, int i3) {
        return toByteString().substring(i2, i3);
    }

    @Override // okio.ByteString
    void write(Buffer buffer) {
        int length = this.segments.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr = this.directory;
            int i4 = iArr[length + i2];
            int i5 = iArr[i2];
            Segment segment = new Segment(this.segments[i2], i4, (i4 + i5) - i3, true, false);
            Segment segment2 = buffer.f26469a;
            if (segment2 == null) {
                segment.f26499g = segment;
                segment.f26498f = segment;
                buffer.f26469a = segment;
            } else {
                segment2.f26499g.push(segment);
            }
            i2++;
            i3 = i5;
        }
        buffer.f26470b += i3;
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int i2, byte[] bArr, int i3, int i4) {
        if (i2 < 0 || i2 > size() - i4 || i3 < 0 || i3 > bArr.length - i4) {
            return false;
        }
        int iSegment = segment(i2);
        while (i4 > 0) {
            int i5 = iSegment == 0 ? 0 : this.directory[iSegment - 1];
            int iMin = Math.min(i4, ((this.directory[iSegment] - i5) + i5) - i2);
            int[] iArr = this.directory;
            byte[][] bArr2 = this.segments;
            if (!Util.arrayRangeEquals(bArr2[iSegment], (i2 - i5) + iArr[bArr2.length + iSegment], bArr, i3, iMin)) {
                return false;
            }
            i2 += iMin;
            i3 += iMin;
            i4 -= iMin;
            iSegment++;
        }
        return true;
    }
}

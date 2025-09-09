package androidx.media3.common.util;

import androidx.annotation.Nullable;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Chars;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CheckReturnValue;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.commons.lang.CharUtils;

@CheckReturnValue
@UnstableApi
/* loaded from: classes.dex */
public final class ParsableByteArray {
    private static final char[] CR_AND_LF = {CharUtils.CR, '\n'};
    private static final char[] LF = {'\n'};
    private static final ImmutableSet<Charset> SUPPORTED_CHARSETS_FOR_READLINE = ImmutableSet.of(Charsets.US_ASCII, Charsets.UTF_8, Charsets.UTF_16, Charsets.UTF_16BE, Charsets.UTF_16LE);
    private byte[] data;
    private int limit;
    private int position;

    public ParsableByteArray() {
        this.data = Util.EMPTY_BYTE_ARRAY;
    }

    private int findNextLineTerminator(Charset charset) {
        int i2;
        if (charset.equals(Charsets.UTF_8) || charset.equals(Charsets.US_ASCII)) {
            i2 = 1;
        } else {
            if (!charset.equals(Charsets.UTF_16) && !charset.equals(Charsets.UTF_16LE) && !charset.equals(Charsets.UTF_16BE)) {
                throw new IllegalArgumentException("Unsupported charset: " + charset);
            }
            i2 = 2;
        }
        int i3 = this.position;
        while (true) {
            int i4 = this.limit;
            if (i3 >= i4 - (i2 - 1)) {
                return i4;
            }
            if ((charset.equals(Charsets.UTF_8) || charset.equals(Charsets.US_ASCII)) && Util.isLinebreak(this.data[i3])) {
                return i3;
            }
            if (charset.equals(Charsets.UTF_16) || charset.equals(Charsets.UTF_16BE)) {
                byte[] bArr = this.data;
                if (bArr[i3] == 0 && Util.isLinebreak(bArr[i3 + 1])) {
                    return i3;
                }
            }
            if (charset.equals(Charsets.UTF_16LE)) {
                byte[] bArr2 = this.data;
                if (bArr2[i3 + 1] == 0 && Util.isLinebreak(bArr2[i3])) {
                    return i3;
                }
            }
            i3 += i2;
        }
    }

    private int peekCharacterAndSize(Charset charset) {
        byte bCheckedCast;
        char cFromBytes;
        int i2 = 1;
        if ((charset.equals(Charsets.UTF_8) || charset.equals(Charsets.US_ASCII)) && bytesLeft() >= 1) {
            bCheckedCast = (byte) Chars.checkedCast(UnsignedBytes.toInt(this.data[this.position]));
        } else {
            if ((charset.equals(Charsets.UTF_16) || charset.equals(Charsets.UTF_16BE)) && bytesLeft() >= 2) {
                byte[] bArr = this.data;
                int i3 = this.position;
                cFromBytes = Chars.fromBytes(bArr[i3], bArr[i3 + 1]);
            } else {
                if (!charset.equals(Charsets.UTF_16LE) || bytesLeft() < 2) {
                    return 0;
                }
                byte[] bArr2 = this.data;
                int i4 = this.position;
                cFromBytes = Chars.fromBytes(bArr2[i4 + 1], bArr2[i4]);
            }
            bCheckedCast = (byte) cFromBytes;
            i2 = 2;
        }
        return (Chars.checkedCast(bCheckedCast) << 16) + i2;
    }

    private char readCharacterIfInList(Charset charset, char[] cArr) {
        int iPeekCharacterAndSize = peekCharacterAndSize(charset);
        if (iPeekCharacterAndSize == 0) {
            return (char) 0;
        }
        char c2 = (char) (iPeekCharacterAndSize >> 16);
        if (!Chars.contains(cArr, c2)) {
            return (char) 0;
        }
        this.position += iPeekCharacterAndSize & 65535;
        return c2;
    }

    private void skipLineTerminator(Charset charset) {
        if (readCharacterIfInList(charset, CR_AND_LF) == '\r') {
            readCharacterIfInList(charset, LF);
        }
    }

    public int bytesLeft() {
        return this.limit - this.position;
    }

    public int capacity() {
        return this.data.length;
    }

    public void ensureCapacity(int i2) {
        if (i2 > capacity()) {
            this.data = Arrays.copyOf(this.data, i2);
        }
    }

    public byte[] getData() {
        return this.data;
    }

    public int getPosition() {
        return this.position;
    }

    public int limit() {
        return this.limit;
    }

    public char peekChar() {
        byte[] bArr = this.data;
        int i2 = this.position;
        return (char) ((bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8));
    }

    public int peekUnsignedByte() {
        return this.data[this.position] & 255;
    }

    public void readBytes(ParsableBitArray parsableBitArray, int i2) {
        readBytes(parsableBitArray.data, 0, i2);
        parsableBitArray.setPosition(0);
    }

    @Nullable
    public String readDelimiterTerminatedString(char c2) {
        if (bytesLeft() == 0) {
            return null;
        }
        int i2 = this.position;
        while (i2 < this.limit && this.data[i2] != c2) {
            i2++;
        }
        byte[] bArr = this.data;
        int i3 = this.position;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(bArr, i3, i2 - i3);
        this.position = i2;
        if (i2 < this.limit) {
            this.position = i2 + 1;
        }
        return strFromUtf8Bytes;
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public int readInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = (bArr[i2] & 255) << 24;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 16) | i4;
        int i7 = i2 + 3;
        this.position = i7;
        int i8 = i6 | ((bArr[i5] & 255) << 8);
        this.position = i2 + 4;
        return (bArr[i7] & 255) | i8;
    }

    public int readInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = ((bArr[i2] & 255) << 24) >> 8;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 8) | i4;
        this.position = i2 + 3;
        return (bArr[i5] & 255) | i6;
    }

    @Nullable
    public String readLine() {
        return readLine(Charsets.UTF_8);
    }

    public int readLittleEndianInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = bArr[i2] & 255;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 8) | i4;
        int i7 = i2 + 3;
        this.position = i7;
        int i8 = i6 | ((bArr[i5] & 255) << 16);
        this.position = i2 + 4;
        return ((bArr[i7] & 255) << 24) | i8;
    }

    public int readLittleEndianInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = bArr[i2] & 255;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 8) | i4;
        this.position = i2 + 3;
        return ((bArr[i5] & 255) << 16) | i6;
    }

    public long readLittleEndianLong() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        this.position = i2 + 2;
        this.position = i2 + 3;
        long j2 = (bArr[i2] & 255) | ((bArr[r2] & 255) << 8) | ((bArr[r7] & 255) << 16);
        this.position = i2 + 4;
        long j3 = j2 | ((bArr[r8] & 255) << 24);
        this.position = i2 + 5;
        long j4 = j3 | ((bArr[r7] & 255) << 32);
        this.position = i2 + 6;
        long j5 = j4 | ((bArr[r8] & 255) << 40);
        this.position = i2 + 7;
        long j6 = j5 | ((bArr[r7] & 255) << 48);
        this.position = i2 + 8;
        return ((bArr[r8] & 255) << 56) | j6;
    }

    public short readLittleEndianShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = bArr[i2] & 255;
        this.position = i2 + 2;
        return (short) (((bArr[i3] & 255) << 8) | i4);
    }

    public long readLittleEndianUnsignedInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        this.position = i2 + 2;
        this.position = i2 + 3;
        long j2 = (bArr[i2] & 255) | ((bArr[r2] & 255) << 8) | ((bArr[r7] & 255) << 16);
        this.position = i2 + 4;
        return ((bArr[r4] & 255) << 24) | j2;
    }

    public int readLittleEndianUnsignedInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = bArr[i2] & 255;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 8) | i4;
        this.position = i2 + 3;
        return ((bArr[i5] & 255) << 16) | i6;
    }

    public int readLittleEndianUnsignedIntToInt() {
        int littleEndianInt = readLittleEndianInt();
        if (littleEndianInt >= 0) {
            return littleEndianInt;
        }
        throw new IllegalStateException("Top bit not zero: " + littleEndianInt);
    }

    public int readLittleEndianUnsignedShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = bArr[i2] & 255;
        this.position = i2 + 2;
        return ((bArr[i3] & 255) << 8) | i4;
    }

    public long readLong() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        this.position = i2 + 2;
        this.position = i2 + 3;
        long j2 = ((bArr[i2] & 255) << 56) | ((bArr[r2] & 255) << 48) | ((bArr[r7] & 255) << 40);
        this.position = i2 + 4;
        long j3 = j2 | ((bArr[r4] & 255) << 32);
        this.position = i2 + 5;
        long j4 = j3 | ((bArr[r7] & 255) << 24);
        this.position = i2 + 6;
        long j5 = j4 | ((bArr[r4] & 255) << 16);
        this.position = i2 + 7;
        long j6 = j5 | ((bArr[r7] & 255) << 8);
        this.position = i2 + 8;
        return (bArr[r4] & 255) | j6;
    }

    public String readNullTerminatedString(int i2) {
        if (i2 == 0) {
            return "";
        }
        int i3 = this.position;
        int i4 = (i3 + i2) - 1;
        String strFromUtf8Bytes = Util.fromUtf8Bytes(this.data, i3, (i4 >= this.limit || this.data[i4] != 0) ? i2 : i2 - 1);
        this.position += i2;
        return strFromUtf8Bytes;
    }

    public short readShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = (bArr[i2] & 255) << 8;
        this.position = i2 + 2;
        return (short) ((bArr[i3] & 255) | i4);
    }

    public String readString(int i2) {
        return readString(i2, Charsets.UTF_8);
    }

    public int readSynchSafeInt() {
        return (readUnsignedByte() << 21) | (readUnsignedByte() << 14) | (readUnsignedByte() << 7) | readUnsignedByte();
    }

    public int readUnsignedByte() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        return bArr[i2] & 255;
    }

    public int readUnsignedFixedPoint1616() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = (bArr[i2] & 255) << 8;
        this.position = i2 + 2;
        int i5 = (bArr[i3] & 255) | i4;
        this.position = i2 + 4;
        return i5;
    }

    public long readUnsignedInt() {
        byte[] bArr = this.data;
        int i2 = this.position;
        this.position = i2 + 1;
        this.position = i2 + 2;
        this.position = i2 + 3;
        long j2 = ((bArr[i2] & 255) << 24) | ((bArr[r2] & 255) << 16) | ((bArr[r7] & 255) << 8);
        this.position = i2 + 4;
        return (bArr[r4] & 255) | j2;
    }

    public int readUnsignedInt24() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = (bArr[i2] & 255) << 16;
        int i5 = i2 + 2;
        this.position = i5;
        int i6 = ((bArr[i3] & 255) << 8) | i4;
        this.position = i2 + 3;
        return (bArr[i5] & 255) | i6;
    }

    public int readUnsignedIntToInt() {
        int i2 = readInt();
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalStateException("Top bit not zero: " + i2);
    }

    public long readUnsignedLongToLong() {
        long j2 = readLong();
        if (j2 >= 0) {
            return j2;
        }
        throw new IllegalStateException("Top bit not zero: " + j2);
    }

    public int readUnsignedShort() {
        byte[] bArr = this.data;
        int i2 = this.position;
        int i3 = i2 + 1;
        this.position = i3;
        int i4 = (bArr[i2] & 255) << 8;
        this.position = i2 + 2;
        return (bArr[i3] & 255) | i4;
    }

    public long readUtf8EncodedLong() {
        int i2;
        int i3;
        long j2 = this.data[this.position];
        int i4 = 7;
        while (true) {
            if (i4 < 0) {
                break;
            }
            if (((1 << i4) & j2) != 0) {
                i4--;
            } else if (i4 < 6) {
                j2 &= r6 - 1;
                i3 = 7 - i4;
            } else if (i4 == 7) {
                i3 = 1;
            }
        }
        i3 = 0;
        if (i3 == 0) {
            throw new NumberFormatException("Invalid UTF-8 sequence first byte: " + j2);
        }
        for (i2 = 1; i2 < i3; i2++) {
            if ((this.data[this.position + i2] & 192) != 128) {
                throw new NumberFormatException("Invalid UTF-8 sequence continuation byte: " + j2);
            }
            j2 = (j2 << 6) | (r3 & 63);
        }
        this.position += i3;
        return j2;
    }

    @Nullable
    public Charset readUtfCharsetFromBom() {
        if (bytesLeft() >= 3) {
            byte[] bArr = this.data;
            int i2 = this.position;
            if (bArr[i2] == -17 && bArr[i2 + 1] == -69 && bArr[i2 + 2] == -65) {
                this.position = i2 + 3;
                return Charsets.UTF_8;
            }
        }
        if (bytesLeft() < 2) {
            return null;
        }
        byte[] bArr2 = this.data;
        int i3 = this.position;
        byte b2 = bArr2[i3];
        if (b2 == -2 && bArr2[i3 + 1] == -1) {
            this.position = i3 + 2;
            return Charsets.UTF_16BE;
        }
        if (b2 != -1 || bArr2[i3 + 1] != -2) {
            return null;
        }
        this.position = i3 + 2;
        return Charsets.UTF_16LE;
    }

    public void reset(int i2) {
        reset(capacity() < i2 ? new byte[i2] : this.data, i2);
    }

    public void setLimit(int i2) {
        Assertions.checkArgument(i2 >= 0 && i2 <= this.data.length);
        this.limit = i2;
    }

    public void setPosition(int i2) {
        Assertions.checkArgument(i2 >= 0 && i2 <= this.limit);
        this.position = i2;
    }

    public void skipBytes(int i2) {
        setPosition(this.position + i2);
    }

    public char peekChar(Charset charset) {
        Assertions.checkArgument(SUPPORTED_CHARSETS_FOR_READLINE.contains(charset), "Unsupported charset: " + charset);
        return (char) (peekCharacterAndSize(charset) >> 16);
    }

    @Nullable
    public String readLine(Charset charset) {
        Assertions.checkArgument(SUPPORTED_CHARSETS_FOR_READLINE.contains(charset), "Unsupported charset: " + charset);
        if (bytesLeft() == 0) {
            return null;
        }
        if (!charset.equals(Charsets.US_ASCII)) {
            readUtfCharsetFromBom();
        }
        String string = readString(findNextLineTerminator(charset) - this.position, charset);
        if (this.position == this.limit) {
            return string;
        }
        skipLineTerminator(charset);
        return string;
    }

    public String readString(int i2, Charset charset) {
        String str = new String(this.data, this.position, i2, charset);
        this.position += i2;
        return str;
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public ParsableByteArray(int i2) {
        this.data = new byte[i2];
        this.limit = i2;
    }

    public void readBytes(byte[] bArr, int i2, int i3) {
        System.arraycopy(this.data, this.position, bArr, i2, i3);
        this.position += i3;
    }

    public void reset(byte[] bArr, int i2) {
        this.data = bArr;
        this.limit = i2;
        this.position = 0;
    }

    public void readBytes(ByteBuffer byteBuffer, int i2) {
        byteBuffer.put(this.data, this.position, i2);
        this.position += i2;
    }

    public ParsableByteArray(byte[] bArr) {
        this.data = bArr;
        this.limit = bArr.length;
    }

    @Nullable
    public String readNullTerminatedString() {
        return readDelimiterTerminatedString((char) 0);
    }

    public ParsableByteArray(byte[] bArr, int i2) {
        this.data = bArr;
        this.limit = i2;
    }
}

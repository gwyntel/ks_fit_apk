package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.CheckForNull;

@Immutable
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Murmur3_32HashFunction extends AbstractHashFunction implements Serializable {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final int CHUNK_SIZE = 4;
    private static final long serialVersionUID = 0;
    private final int seed;
    private final boolean supplementaryPlaneFix;
    static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0, false);
    static final HashFunction MURMUR3_32_FIXED = new Murmur3_32HashFunction(0, true);
    static final HashFunction GOOD_FAST_HASH_32 = new Murmur3_32HashFunction(Hashing.f14613a, true);

    private static final class Murmur3_32Hasher extends AbstractHasher {
        private long buffer;
        private int h1;
        private int shift;
        private int length = 0;
        private boolean isDone = false;

        Murmur3_32Hasher(int i2) {
            this.h1 = i2;
        }

        private void update(int i2, long j2) {
            long j3 = this.buffer;
            int i3 = this.shift;
            long j4 = ((j2 & 4294967295L) << i3) | j3;
            this.buffer = j4;
            int i4 = i3 + (i2 * 8);
            this.shift = i4;
            this.length += i2;
            if (i4 >= 32) {
                this.h1 = Murmur3_32HashFunction.mixH1(this.h1, Murmur3_32HashFunction.mixK1((int) j4));
                this.buffer >>>= 32;
                this.shift -= 32;
            }
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            Preconditions.checkState(!this.isDone);
            this.isDone = true;
            int iMixK1 = this.h1 ^ Murmur3_32HashFunction.mixK1((int) this.buffer);
            this.h1 = iMixK1;
            return Murmur3_32HashFunction.fmix(iMixK1, this.length);
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putByte(byte b2) {
            update(1, b2 & 255);
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putChar(char c2) {
            update(2, c2);
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putInt(int i2) {
            update(4, i2);
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putLong(long j2) {
            update(4, (int) j2);
            update(4, j2 >>> 32);
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putString(CharSequence charSequence, Charset charset) {
            if (!StandardCharsets.UTF_8.equals(charset)) {
                return super.putString(charSequence, charset);
            }
            int length = charSequence.length();
            int i2 = 0;
            while (true) {
                int i3 = i2 + 4;
                if (i3 > length) {
                    break;
                }
                char cCharAt = charSequence.charAt(i2);
                char cCharAt2 = charSequence.charAt(i2 + 1);
                char cCharAt3 = charSequence.charAt(i2 + 2);
                char cCharAt4 = charSequence.charAt(i2 + 3);
                if (cCharAt >= 128 || cCharAt2 >= 128 || cCharAt3 >= 128 || cCharAt4 >= 128) {
                    break;
                }
                update(4, (cCharAt2 << '\b') | cCharAt | (cCharAt3 << 16) | (cCharAt4 << 24));
                i2 = i3;
            }
            while (i2 < length) {
                char cCharAt5 = charSequence.charAt(i2);
                if (cCharAt5 < 128) {
                    update(1, cCharAt5);
                } else if (cCharAt5 < 2048) {
                    update(2, Murmur3_32HashFunction.charToTwoUtf8Bytes(cCharAt5));
                } else if (cCharAt5 < 55296 || cCharAt5 > 57343) {
                    update(3, Murmur3_32HashFunction.charToThreeUtf8Bytes(cCharAt5));
                } else {
                    int iCodePointAt = Character.codePointAt(charSequence, i2);
                    if (iCodePointAt == cCharAt5) {
                        putBytes(charSequence.subSequence(i2, length).toString().getBytes(charset));
                        return this;
                    }
                    i2++;
                    update(4, Murmur3_32HashFunction.codePointToFourUtf8Bytes(iCodePointAt));
                }
                i2++;
            }
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putBytes(byte[] bArr, int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
            int i4 = 0;
            while (true) {
                int i5 = i4 + 4;
                if (i5 > i3) {
                    break;
                }
                update(4, Murmur3_32HashFunction.getIntLittleEndian(bArr, i4 + i2));
                i4 = i5;
            }
            while (i4 < i3) {
                putByte(bArr[i2 + i4]);
                i4++;
            }
            return this;
        }

        @Override // com.google.common.hash.AbstractHasher, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        @CanIgnoreReturnValue
        public Hasher putBytes(ByteBuffer byteBuffer) {
            ByteOrder byteOrderOrder = byteBuffer.order();
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (byteBuffer.remaining() >= 4) {
                putInt(byteBuffer.getInt());
            }
            while (byteBuffer.hasRemaining()) {
                putByte(byteBuffer.get());
            }
            byteBuffer.order(byteOrderOrder);
            return this;
        }
    }

    Murmur3_32HashFunction(int i2, boolean z2) {
        this.seed = i2;
        this.supplementaryPlaneFix = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long charToThreeUtf8Bytes(char c2) {
        return (c2 >>> '\f') | 224 | ((((c2 >>> 6) & 63) | 128) << 8) | (((c2 & '?') | 128) << 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long charToTwoUtf8Bytes(char c2) {
        return (c2 >>> 6) | 192 | (((c2 & '?') | 128) << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long codePointToFourUtf8Bytes(int i2) {
        return (i2 >>> 18) | 240 | ((((i2 >>> 12) & 63) | 128) << 8) | ((((i2 >>> 6) & 63) | 128) << 16) | (((i2 & 63) | 128) << 24);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashCode fmix(int i2, int i3) {
        int i4 = i2 ^ i3;
        int i5 = (i4 ^ (i4 >>> 16)) * (-2048144789);
        int i6 = (i5 ^ (i5 >>> 13)) * (-1028477387);
        return HashCode.fromInt(i6 ^ (i6 >>> 16));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getIntLittleEndian(byte[] bArr, int i2) {
        return Ints.fromBytes(bArr[i2 + 3], bArr[i2 + 2], bArr[i2 + 1], bArr[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mixH1(int i2, int i3) {
        return (Integer.rotateLeft(i2 ^ i3, 13) * 5) - 430675100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mixK1(int i2) {
        return Integer.rotateLeft(i2 * C1, 15) * C2;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 32;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Murmur3_32HashFunction)) {
            return false;
        }
        Murmur3_32HashFunction murmur3_32HashFunction = (Murmur3_32HashFunction) obj;
        return this.seed == murmur3_32HashFunction.seed && this.supplementaryPlaneFix == murmur3_32HashFunction.supplementaryPlaneFix;
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, bArr.length);
        int iMixH1 = this.seed;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = i5 + 4;
            if (i6 > i3) {
                break;
            }
            iMixH1 = mixH1(iMixH1, mixK1(getIntLittleEndian(bArr, i5 + i2)));
            i5 = i6;
        }
        int i7 = i5;
        int i8 = 0;
        while (i7 < i3) {
            i4 ^= UnsignedBytes.toInt(bArr[i2 + i7]) << i8;
            i7++;
            i8 += 8;
        }
        return fmix(mixK1(i4) ^ iMixH1, i3);
    }

    public int hashCode() {
        return Murmur3_32HashFunction.class.hashCode() ^ this.seed;
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashInt(int i2) {
        return fmix(mixH1(this.seed, mixK1(i2)), 4);
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashLong(long j2) {
        return fmix(mixH1(mixH1(this.seed, mixK1((int) j2)), mixK1((int) (j2 >>> 32))), 8);
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        if (!StandardCharsets.UTF_8.equals(charset)) {
            return hashBytes(charSequence.toString().getBytes(charset));
        }
        int length = charSequence.length();
        int iMixH1 = this.seed;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i3 + 4;
            if (i5 > length) {
                break;
            }
            char cCharAt = charSequence.charAt(i3);
            char cCharAt2 = charSequence.charAt(i3 + 1);
            char cCharAt3 = charSequence.charAt(i3 + 2);
            char cCharAt4 = charSequence.charAt(i3 + 3);
            if (cCharAt >= 128 || cCharAt2 >= 128 || cCharAt3 >= 128 || cCharAt4 >= 128) {
                break;
            }
            iMixH1 = mixH1(iMixH1, mixK1((cCharAt2 << '\b') | cCharAt | (cCharAt3 << 16) | (cCharAt4 << 24)));
            i4 += 4;
            i3 = i5;
        }
        long jCharToThreeUtf8Bytes = 0;
        while (i3 < length) {
            char cCharAt5 = charSequence.charAt(i3);
            if (cCharAt5 < 128) {
                jCharToThreeUtf8Bytes |= cCharAt5 << i2;
                i2 += 8;
                i4++;
            } else if (cCharAt5 < 2048) {
                jCharToThreeUtf8Bytes |= charToTwoUtf8Bytes(cCharAt5) << i2;
                i2 += 16;
                i4 += 2;
            } else if (cCharAt5 < 55296 || cCharAt5 > 57343) {
                jCharToThreeUtf8Bytes |= charToThreeUtf8Bytes(cCharAt5) << i2;
                i2 += 24;
                i4 += 3;
            } else {
                int iCodePointAt = Character.codePointAt(charSequence, i3);
                if (iCodePointAt == cCharAt5) {
                    return hashBytes(charSequence.toString().getBytes(charset));
                }
                i3++;
                jCharToThreeUtf8Bytes |= codePointToFourUtf8Bytes(iCodePointAt) << i2;
                if (this.supplementaryPlaneFix) {
                    i2 += 32;
                }
                i4 += 4;
            }
            if (i2 >= 32) {
                iMixH1 = mixH1(iMixH1, mixK1((int) jCharToThreeUtf8Bytes));
                jCharToThreeUtf8Bytes >>>= 32;
                i2 -= 32;
            }
            i3++;
        }
        return fmix(mixK1((int) jCharToThreeUtf8Bytes) ^ iMixH1, i4);
    }

    @Override // com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int iMixK1 = this.seed;
        for (int i2 = 1; i2 < charSequence.length(); i2 += 2) {
            iMixK1 = mixH1(iMixK1, mixK1(charSequence.charAt(i2 - 1) | (charSequence.charAt(i2) << 16)));
        }
        if ((charSequence.length() & 1) == 1) {
            iMixK1 ^= mixK1(charSequence.charAt(charSequence.length() - 1));
        }
        return fmix(iMixK1, charSequence.length() * 2);
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_32(" + this.seed + ")";
    }
}

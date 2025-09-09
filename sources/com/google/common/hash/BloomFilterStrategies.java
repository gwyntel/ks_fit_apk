package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
enum BloomFilterStrategies implements BloomFilter.Strategy {
    MURMUR128_MITZ_32 { // from class: com.google.common.hash.BloomFilterStrategies.1
        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, LockFreeBitArray lockFreeBitArray) {
            long jB = lockFreeBitArray.b();
            long jAsLong = Hashing.murmur3_128().hashObject(t2, funnel).asLong();
            int i3 = (int) jAsLong;
            int i4 = (int) (jAsLong >>> 32);
            for (int i5 = 1; i5 <= i2; i5++) {
                int i6 = (i5 * i4) + i3;
                if (i6 < 0) {
                    i6 = ~i6;
                }
                if (!lockFreeBitArray.d(i6 % jB)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, LockFreeBitArray lockFreeBitArray) {
            long jB = lockFreeBitArray.b();
            long jAsLong = Hashing.murmur3_128().hashObject(t2, funnel).asLong();
            int i3 = (int) jAsLong;
            int i4 = (int) (jAsLong >>> 32);
            boolean zG = false;
            for (int i5 = 1; i5 <= i2; i5++) {
                int i6 = (i5 * i4) + i3;
                if (i6 < 0) {
                    i6 = ~i6;
                }
                zG |= lockFreeBitArray.g(i6 % jB);
            }
            return zG;
        }
    },
    MURMUR128_MITZ_64 { // from class: com.google.common.hash.BloomFilterStrategies.2
        private long lowerEight(byte[] bArr) {
            return Longs.fromBytes(bArr[7], bArr[6], bArr[5], bArr[4], bArr[3], bArr[2], bArr[1], bArr[0]);
        }

        private long upperEight(byte[] bArr) {
            return Longs.fromBytes(bArr[15], bArr[14], bArr[13], bArr[12], bArr[11], bArr[10], bArr[9], bArr[8]);
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, LockFreeBitArray lockFreeBitArray) {
            long jB = lockFreeBitArray.b();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(t2, funnel).getBytesInternal();
            long jLowerEight = lowerEight(bytesInternal);
            long jUpperEight = upperEight(bytesInternal);
            for (int i3 = 0; i3 < i2; i3++) {
                if (!lockFreeBitArray.d((Long.MAX_VALUE & jLowerEight) % jB)) {
                    return false;
                }
                jLowerEight += jUpperEight;
            }
            return true;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, LockFreeBitArray lockFreeBitArray) {
            long jB = lockFreeBitArray.b();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(t2, funnel).getBytesInternal();
            long jLowerEight = lowerEight(bytesInternal);
            long jUpperEight = upperEight(bytesInternal);
            boolean zG = false;
            for (int i3 = 0; i3 < i2; i3++) {
                zG |= lockFreeBitArray.g((Long.MAX_VALUE & jLowerEight) % jB);
                jLowerEight += jUpperEight;
            }
            return zG;
        }
    };

    static final class LockFreeBitArray {
        private static final int LONG_ADDRESSABLE_BITS = 6;

        /* renamed from: a, reason: collision with root package name */
        final AtomicLongArray f14605a;
        private final LongAddable bitCount;

        LockFreeBitArray(long j2) {
            Preconditions.checkArgument(j2 > 0, "data length is zero!");
            this.f14605a = new AtomicLongArray(Ints.checkedCast(LongMath.divide(j2, 64L, RoundingMode.CEILING)));
            this.bitCount = LongAddables.create();
        }

        public static long[] toPlainArray(AtomicLongArray atomicLongArray) {
            int length = atomicLongArray.length();
            long[] jArr = new long[length];
            for (int i2 = 0; i2 < length; i2++) {
                jArr[i2] = atomicLongArray.get(i2);
            }
            return jArr;
        }

        long a() {
            return this.bitCount.sum();
        }

        long b() {
            return this.f14605a.length() * 64;
        }

        LockFreeBitArray c() {
            return new LockFreeBitArray(toPlainArray(this.f14605a));
        }

        boolean d(long j2) {
            return ((1 << ((int) j2)) & this.f14605a.get((int) (j2 >>> 6))) != 0;
        }

        void e(LockFreeBitArray lockFreeBitArray) {
            Preconditions.checkArgument(this.f14605a.length() == lockFreeBitArray.f14605a.length(), "BitArrays must be of equal length (%s != %s)", this.f14605a.length(), lockFreeBitArray.f14605a.length());
            for (int i2 = 0; i2 < this.f14605a.length(); i2++) {
                f(i2, lockFreeBitArray.f14605a.get(i2));
            }
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof LockFreeBitArray) {
                return Arrays.equals(toPlainArray(this.f14605a), toPlainArray(((LockFreeBitArray) obj).f14605a));
            }
            return false;
        }

        void f(int i2, long j2) {
            long j3;
            long j4;
            do {
                j3 = this.f14605a.get(i2);
                j4 = j3 | j2;
                if (j3 == j4) {
                    return;
                }
            } while (!this.f14605a.compareAndSet(i2, j3, j4));
            this.bitCount.add(Long.bitCount(j4) - Long.bitCount(j3));
        }

        boolean g(long j2) {
            long j3;
            long j4;
            if (d(j2)) {
                return false;
            }
            int i2 = (int) (j2 >>> 6);
            long j5 = 1 << ((int) j2);
            do {
                j3 = this.f14605a.get(i2);
                j4 = j3 | j5;
                if (j3 == j4) {
                    return false;
                }
            } while (!this.f14605a.compareAndSet(i2, j3, j4));
            this.bitCount.increment();
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(toPlainArray(this.f14605a));
        }

        LockFreeBitArray(long[] jArr) {
            Preconditions.checkArgument(jArr.length > 0, "data length is zero!");
            this.f14605a = new AtomicLongArray(jArr);
            this.bitCount = LongAddables.create();
            long jBitCount = 0;
            for (long j2 : jArr) {
                jBitCount += Long.bitCount(j2);
            }
            this.bitCount.add(jBitCount);
        }
    }
}

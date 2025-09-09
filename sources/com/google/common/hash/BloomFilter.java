package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.BloomFilterStrategies;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import javax.annotation.CheckForNull;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 912559;
    private final BloomFilterStrategies.LockFreeBitArray bits;
    private final Funnel<? super T> funnel;
    private final int numHashFunctions;
    private final Strategy strategy;

    private static class SerialForm<T> implements Serializable {
        private static final long serialVersionUID = 1;
        final long[] data;
        final Funnel<? super T> funnel;
        final int numHashFunctions;
        final Strategy strategy;

        SerialForm(BloomFilter<T> bloomFilter) {
            this.data = BloomFilterStrategies.LockFreeBitArray.toPlainArray(((BloomFilter) bloomFilter).bits.f14605a);
            this.numHashFunctions = ((BloomFilter) bloomFilter).numHashFunctions;
            this.funnel = ((BloomFilter) bloomFilter).funnel;
            this.strategy = ((BloomFilter) bloomFilter).strategy;
        }

        Object readResolve() {
            return new BloomFilter(new BloomFilterStrategies.LockFreeBitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
        }
    }

    interface Strategy extends Serializable {
        <T> boolean mightContain(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, BloomFilterStrategies.LockFreeBitArray lockFreeBitArray);

        int ordinal();

        <T> boolean put(@ParametricNullness T t2, Funnel<? super T> funnel, int i2, BloomFilterStrategies.LockFreeBitArray lockFreeBitArray);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i2, double d2) {
        return create(funnel, i2, d2);
    }

    @VisibleForTesting
    static long optimalNumOfBits(long j2, double d2) {
        if (d2 == 0.0d) {
            d2 = Double.MIN_VALUE;
        }
        return (long) (((-j2) * Math.log(d2)) / (Math.log(2.0d) * Math.log(2.0d)));
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long j2, long j3) {
        return Math.max(1, (int) Math.round((j3 / j2) * Math.log(2.0d)));
    }

    public static <T> BloomFilter<T> readFrom(InputStream inputStream, Funnel<? super T> funnel) throws IOException {
        int i2;
        int i3;
        DataInputStream dataInputStream;
        byte b2;
        Preconditions.checkNotNull(inputStream, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        int i4 = -1;
        try {
            try {
                dataInputStream = new DataInputStream(inputStream);
                b2 = dataInputStream.readByte();
                try {
                    i3 = UnsignedBytes.toInt(dataInputStream.readByte());
                } catch (Exception e2) {
                    e = e2;
                    i3 = -1;
                    i4 = b2;
                    i2 = -1;
                }
            } catch (Exception e3) {
                e = e3;
                i2 = -1;
                i3 = -1;
            }
            try {
                i4 = dataInputStream.readInt();
                BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.values()[b2];
                BloomFilterStrategies.LockFreeBitArray lockFreeBitArray = new BloomFilterStrategies.LockFreeBitArray(LongMath.checkedMultiply(i4, 64L));
                for (int i5 = 0; i5 < i4; i5++) {
                    lockFreeBitArray.f(i5, dataInputStream.readLong());
                }
                return new BloomFilter<>(lockFreeBitArray, i3, funnel, bloomFilterStrategies);
            } catch (Exception e4) {
                e = e4;
                int i6 = i4;
                i4 = b2;
                i2 = i6;
                throw new IOException("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: " + i4 + " numHashFunctions: " + i3 + " dataLength: " + i2, e);
            }
        } catch (IOException e5) {
            throw e5;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    public boolean apply(@ParametricNullness T t2) {
        return mightContain(t2);
    }

    public long approximateElementCount() {
        double dB = this.bits.b();
        return DoubleMath.roundToLong(((-Math.log1p(-(this.bits.a() / dB))) * dB) / this.numHashFunctions, RoundingMode.HALF_UP);
    }

    @VisibleForTesting
    long bitSize() {
        return this.bits.b();
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(this.bits.c(), this.numHashFunctions, this.funnel, this.strategy);
    }

    @Override // com.google.common.base.Predicate
    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BloomFilter)) {
            return false;
        }
        BloomFilter bloomFilter = (BloomFilter) obj;
        return this.numHashFunctions == bloomFilter.numHashFunctions && this.funnel.equals(bloomFilter.funnel) && this.bits.equals(bloomFilter.bits) && this.strategy.equals(bloomFilter.strategy);
    }

    public double expectedFpp() {
        return Math.pow(this.bits.a() / bitSize(), this.numHashFunctions);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.numHashFunctions), this.funnel, this.strategy, this.bits);
    }

    public boolean isCompatible(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        return this != bloomFilter && this.numHashFunctions == bloomFilter.numHashFunctions && bitSize() == bloomFilter.bitSize() && this.strategy.equals(bloomFilter.strategy) && this.funnel.equals(bloomFilter.funnel);
    }

    public boolean mightContain(@ParametricNullness T t2) {
        return this.strategy.mightContain(t2, this.funnel, this.numHashFunctions, this.bits);
    }

    @CanIgnoreReturnValue
    public boolean put(@ParametricNullness T t2) {
        return this.strategy.put(t2, this.funnel, this.numHashFunctions, this.bits);
    }

    public void putAll(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        Preconditions.checkArgument(this != bloomFilter, "Cannot combine a BloomFilter with itself.");
        int i2 = this.numHashFunctions;
        int i3 = bloomFilter.numHashFunctions;
        Preconditions.checkArgument(i2 == i3, "BloomFilters must have the same number of hash functions (%s != %s)", i2, i3);
        Preconditions.checkArgument(bitSize() == bloomFilter.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", bitSize(), bloomFilter.bitSize());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.e(bloomFilter.bits);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(SignedBytes.checkedCast(this.strategy.ordinal()));
        dataOutputStream.writeByte(UnsignedBytes.checkedCast(this.numHashFunctions));
        dataOutputStream.writeInt(this.bits.f14605a.length());
        for (int i2 = 0; i2 < this.bits.f14605a.length(); i2++) {
            dataOutputStream.writeLong(this.bits.f14605a.get(i2));
        }
    }

    private BloomFilter(BloomFilterStrategies.LockFreeBitArray lockFreeBitArray, int i2, Funnel<? super T> funnel, Strategy strategy) {
        Preconditions.checkArgument(i2 > 0, "numHashFunctions (%s) must be > 0", i2);
        Preconditions.checkArgument(i2 <= 255, "numHashFunctions (%s) must be <= 255", i2);
        this.bits = (BloomFilterStrategies.LockFreeBitArray) Preconditions.checkNotNull(lockFreeBitArray);
        this.numHashFunctions = i2;
        this.funnel = (Funnel) Preconditions.checkNotNull(funnel);
        this.strategy = (Strategy) Preconditions.checkNotNull(strategy);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j2, double d2) {
        return create(funnel, j2, d2, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j2, double d2, Strategy strategy) {
        Preconditions.checkNotNull(funnel);
        Preconditions.checkArgument(j2 >= 0, "Expected insertions (%s) must be >= 0", j2);
        Preconditions.checkArgument(d2 > 0.0d, "False positive probability (%s) must be > 0.0", Double.valueOf(d2));
        Preconditions.checkArgument(d2 < 1.0d, "False positive probability (%s) must be < 1.0", Double.valueOf(d2));
        Preconditions.checkNotNull(strategy);
        if (j2 == 0) {
            j2 = 1;
        }
        long jOptimalNumOfBits = optimalNumOfBits(j2, d2);
        try {
            return new BloomFilter<>(new BloomFilterStrategies.LockFreeBitArray(jOptimalNumOfBits), optimalNumOfHashFunctions(j2, jOptimalNumOfBits), funnel, strategy);
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + jOptimalNumOfBits + " bits", e2);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i2) {
        return create(funnel, i2);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j2) {
        return create(funnel, j2, 0.03d);
    }
}

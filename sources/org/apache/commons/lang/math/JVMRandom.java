package org.apache.commons.lang.math;

import java.util.Random;

/* loaded from: classes5.dex */
public final class JVMRandom extends Random {
    private static final Random SHARED_RANDOM = new Random();
    private static final long serialVersionUID = 1;
    private boolean constructed = true;

    private static int bitsRequired(long j2) {
        int i2 = 0;
        long j3 = j2;
        while (j2 >= 0) {
            if (j3 == 0) {
                return i2;
            }
            i2++;
            j2 <<= 1;
            j3 >>= 1;
        }
        return 64 - i2;
    }

    private static long next63bits() {
        return SHARED_RANDOM.nextLong() & Long.MAX_VALUE;
    }

    @Override // java.util.Random
    public boolean nextBoolean() {
        return SHARED_RANDOM.nextBoolean();
    }

    @Override // java.util.Random
    public void nextBytes(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Random
    public double nextDouble() {
        return SHARED_RANDOM.nextDouble();
    }

    @Override // java.util.Random
    public float nextFloat() {
        return SHARED_RANDOM.nextFloat();
    }

    @Override // java.util.Random
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Random
    public int nextInt() {
        return nextInt(Integer.MAX_VALUE);
    }

    @Override // java.util.Random
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }

    @Override // java.util.Random
    public synchronized void setSeed(long j2) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    public static long nextLong(long j2) {
        long jNext63bits;
        long j3;
        if (j2 <= 0) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        }
        if (((-j2) & j2) == j2) {
            return next63bits() >> (63 - bitsRequired(j2 - 1));
        }
        do {
            jNext63bits = next63bits();
            j3 = jNext63bits % j2;
        } while ((jNext63bits - j3) + (j2 - 1) < 0);
        return j3;
    }

    @Override // java.util.Random
    public int nextInt(int i2) {
        return SHARED_RANDOM.nextInt(i2);
    }
}

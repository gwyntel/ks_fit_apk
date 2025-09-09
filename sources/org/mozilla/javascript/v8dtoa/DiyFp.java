package org.mozilla.javascript.v8dtoa;

/* loaded from: classes5.dex */
class DiyFp {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kSignificandSize = 64;
    static final long kUint64MSB = Long.MIN_VALUE;

    /* renamed from: e, reason: collision with root package name */
    private int f26769e;

    /* renamed from: f, reason: collision with root package name */
    private long f26770f;

    DiyFp() {
        this.f26770f = 0L;
        this.f26769e = 0;
    }

    static DiyFp minus(DiyFp diyFp, DiyFp diyFp2) {
        DiyFp diyFp3 = new DiyFp(diyFp.f26770f, diyFp.f26769e);
        diyFp3.subtract(diyFp2);
        return diyFp3;
    }

    static DiyFp times(DiyFp diyFp, DiyFp diyFp2) {
        DiyFp diyFp3 = new DiyFp(diyFp.f26770f, diyFp.f26769e);
        diyFp3.multiply(diyFp2);
        return diyFp3;
    }

    private static boolean uint64_gte(long j2, long j3) {
        if (j2 == j3) {
            return true;
        }
        return (((j2 > 0L ? 1 : (j2 == 0L ? 0 : -1)) < 0) ^ ((j2 > j3 ? 1 : (j2 == j3 ? 0 : -1)) > 0)) ^ ((j3 > 0L ? 1 : (j3 == 0L ? 0 : -1)) < 0);
    }

    int e() {
        return this.f26769e;
    }

    long f() {
        return this.f26770f;
    }

    void multiply(DiyFp diyFp) {
        long j2 = this.f26770f;
        long j3 = j2 >>> 32;
        long j4 = j2 & 4294967295L;
        long j5 = diyFp.f26770f;
        long j6 = j5 >>> 32;
        long j7 = j5 & 4294967295L;
        long j8 = j3 * j6;
        long j9 = j6 * j4;
        long j10 = j3 * j7;
        this.f26769e += diyFp.f26769e + 64;
        this.f26770f = j8 + (j10 >>> 32) + (j9 >>> 32) + ((((((j4 * j7) >>> 32) + (j10 & 4294967295L)) + (4294967295L & j9)) + 2147483648L) >>> 32);
    }

    void normalize() {
        long j2 = this.f26770f;
        int i2 = this.f26769e;
        while (((-18014398509481984L) & j2) == 0) {
            j2 <<= 10;
            i2 -= 10;
        }
        while ((Long.MIN_VALUE & j2) == 0) {
            j2 <<= 1;
            i2--;
        }
        this.f26770f = j2;
        this.f26769e = i2;
    }

    void setE(int i2) {
        this.f26769e = i2;
    }

    void setF(long j2) {
        this.f26770f = j2;
    }

    void subtract(DiyFp diyFp) {
        this.f26770f -= diyFp.f26770f;
    }

    public String toString() {
        return "[DiyFp f:" + this.f26770f + ", e:" + this.f26769e + "]";
    }

    DiyFp(long j2, int i2) {
        this.f26770f = j2;
        this.f26769e = i2;
    }

    static DiyFp normalize(DiyFp diyFp) {
        DiyFp diyFp2 = new DiyFp(diyFp.f26770f, diyFp.f26769e);
        diyFp2.normalize();
        return diyFp2;
    }
}

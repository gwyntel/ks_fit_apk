package kotlin;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class d {
    public static /* synthetic */ long a(long j2, long j3) {
        if (j3 < 0) {
            return (j2 ^ Long.MIN_VALUE) < (j3 ^ Long.MIN_VALUE) ? 0L : 1L;
        }
        if (j2 >= 0) {
            return j2 / j3;
        }
        long j4 = ((j2 >>> 1) / j3) << 1;
        return j4 + (((j2 - (j4 * j3)) ^ Long.MIN_VALUE) < (j3 ^ Long.MIN_VALUE) ? 0 : 1);
    }
}

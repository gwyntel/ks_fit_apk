package kotlin;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class c {
    public static /* synthetic */ long a(long j2, long j3) {
        if (j3 < 0) {
            return (j2 ^ Long.MIN_VALUE) < (j3 ^ Long.MIN_VALUE) ? j2 : j2 - j3;
        }
        if (j2 >= 0) {
            return j2 % j3;
        }
        long j4 = j2 - ((((j2 >>> 1) / j3) << 1) * j3);
        if ((j4 ^ Long.MIN_VALUE) < (j3 ^ Long.MIN_VALUE)) {
            j3 = 0;
        }
        return j4 - j3;
    }
}

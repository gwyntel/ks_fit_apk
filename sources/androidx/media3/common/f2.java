package androidx.media3.common;

import android.os.SystemClock;
import androidx.media3.common.SimpleBasePlayer;

/* loaded from: classes.dex */
public abstract /* synthetic */ class f2 {
    static {
        SimpleBasePlayer.PositionSupplier positionSupplier = SimpleBasePlayer.PositionSupplier.ZERO;
    }

    public static SimpleBasePlayer.PositionSupplier a(final long j2) {
        return new SimpleBasePlayer.PositionSupplier() { // from class: androidx.media3.common.e2
            @Override // androidx.media3.common.SimpleBasePlayer.PositionSupplier
            public final long get() {
                return f2.c(j2);
            }
        };
    }

    public static SimpleBasePlayer.PositionSupplier b(final long j2, final float f2) {
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        return new SimpleBasePlayer.PositionSupplier() { // from class: androidx.media3.common.d2
            @Override // androidx.media3.common.SimpleBasePlayer.PositionSupplier
            public final long get() {
                return f2.d(j2, jElapsedRealtime, f2);
            }
        };
    }

    public static /* synthetic */ long d(long j2, long j3, float f2) {
        return j2 + ((long) ((SystemClock.elapsedRealtime() - j3) * f2));
    }

    public static /* synthetic */ long c(long j2) {
        return j2;
    }
}

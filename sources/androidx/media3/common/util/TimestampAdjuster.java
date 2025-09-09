package androidx.media3.common.util;

import androidx.annotation.GuardedBy;
import androidx.media3.common.C;
import java.util.concurrent.TimeoutException;
import org.mozilla.javascript.typedarrays.Conversions;

@UnstableApi
/* loaded from: classes.dex */
public final class TimestampAdjuster {
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    public static final long MODE_NO_OFFSET = Long.MAX_VALUE;
    public static final long MODE_SHARED = 9223372036854775806L;

    @GuardedBy("this")
    private long firstSampleTimestampUs;

    @GuardedBy("this")
    private long lastUnadjustedTimestampUs;
    private final ThreadLocal<Long> nextSampleTimestampUs = new ThreadLocal<>();

    @GuardedBy("this")
    private long timestampOffsetUs;

    public TimestampAdjuster(long j2) {
        reset(j2);
    }

    public static long ptsToUs(long j2) {
        return (j2 * 1000000) / 90000;
    }

    public static long usToNonWrappedPts(long j2) {
        return (j2 * 90000) / 1000000;
    }

    public static long usToWrappedPts(long j2) {
        return usToNonWrappedPts(j2) % MAX_PTS_PLUS_ONE;
    }

    public synchronized long adjustSampleTimestamp(long j2) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        try {
            if (!isInitialized()) {
                long jLongValue = this.firstSampleTimestampUs;
                if (jLongValue == MODE_SHARED) {
                    jLongValue = ((Long) Assertions.checkNotNull(this.nextSampleTimestampUs.get())).longValue();
                }
                this.timestampOffsetUs = jLongValue - j2;
                notifyAll();
            }
            this.lastUnadjustedTimestampUs = j2;
            return j2 + this.timestampOffsetUs;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized long adjustTsTimestamp(long j2) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        try {
            long j3 = this.lastUnadjustedTimestampUs;
            if (j3 != C.TIME_UNSET) {
                long jUsToNonWrappedPts = usToNonWrappedPts(j3);
                long j4 = (Conversions.THIRTYTWO_BIT + jUsToNonWrappedPts) / MAX_PTS_PLUS_ONE;
                long j5 = ((j4 - 1) * MAX_PTS_PLUS_ONE) + j2;
                j2 += j4 * MAX_PTS_PLUS_ONE;
                if (Math.abs(j5 - jUsToNonWrappedPts) < Math.abs(j2 - jUsToNonWrappedPts)) {
                    j2 = j5;
                }
            }
            return adjustSampleTimestamp(ptsToUs(j2));
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized long adjustTsTimestampGreaterThanPreviousTimestamp(long j2) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long j3 = this.lastUnadjustedTimestampUs;
        if (j3 != C.TIME_UNSET) {
            long jUsToNonWrappedPts = usToNonWrappedPts(j3);
            long j4 = jUsToNonWrappedPts / MAX_PTS_PLUS_ONE;
            Long.signum(j4);
            long j5 = (j4 * MAX_PTS_PLUS_ONE) + j2;
            j2 += (j4 + 1) * MAX_PTS_PLUS_ONE;
            if (j5 >= jUsToNonWrappedPts) {
                j2 = j5;
            }
        }
        return adjustSampleTimestamp(ptsToUs(j2));
    }

    public synchronized long getFirstSampleTimestampUs() {
        long j2;
        j2 = this.firstSampleTimestampUs;
        if (j2 == Long.MAX_VALUE || j2 == MODE_SHARED) {
            j2 = C.TIME_UNSET;
        }
        return j2;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long j2;
        try {
            j2 = this.lastUnadjustedTimestampUs;
        } catch (Throwable th) {
            throw th;
        }
        return j2 != C.TIME_UNSET ? j2 + this.timestampOffsetUs : getFirstSampleTimestampUs();
    }

    public synchronized long getTimestampOffsetUs() {
        return this.timestampOffsetUs;
    }

    public synchronized boolean isInitialized() {
        return this.timestampOffsetUs != C.TIME_UNSET;
    }

    public synchronized void reset(long j2) {
        this.firstSampleTimestampUs = j2;
        this.timestampOffsetUs = j2 == Long.MAX_VALUE ? 0L : -9223372036854775807L;
        this.lastUnadjustedTimestampUs = C.TIME_UNSET;
    }

    public synchronized void sharedInitializeOrWait(boolean z2, long j2, long j3) throws InterruptedException, TimeoutException {
        try {
            Assertions.checkState(this.firstSampleTimestampUs == MODE_SHARED);
            if (isInitialized()) {
                return;
            }
            if (z2) {
                this.nextSampleTimestampUs.set(Long.valueOf(j2));
            } else {
                long jElapsedRealtime = 0;
                long j4 = j3;
                while (!isInitialized()) {
                    if (j3 == 0) {
                        wait();
                    } else {
                        Assertions.checkState(j4 > 0);
                        long jElapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                        wait(j4);
                        jElapsedRealtime += android.os.SystemClock.elapsedRealtime() - jElapsedRealtime2;
                        if (jElapsedRealtime >= j3 && !isInitialized()) {
                            throw new TimeoutException("TimestampAdjuster failed to initialize in " + j3 + " milliseconds");
                        }
                        j4 = j3 - jElapsedRealtime;
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}

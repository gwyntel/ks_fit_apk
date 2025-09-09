package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
public final class CacheStats {
    private final long evictionCount;
    private final long hitCount;
    private final long loadExceptionCount;
    private final long loadSuccessCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(long j2, long j3, long j4, long j5, long j6, long j7) {
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 >= 0);
        Preconditions.checkArgument(j7 >= 0);
        this.hitCount = j2;
        this.missCount = j3;
        this.loadSuccessCount = j4;
        this.loadExceptionCount = j5;
        this.totalLoadTime = j6;
        this.evictionCount = j7;
    }

    public double averageLoadPenalty() {
        long jSaturatedAdd = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (jSaturatedAdd == 0) {
            return 0.0d;
        }
        return this.totalLoadTime / jSaturatedAdd;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof CacheStats)) {
            return false;
        }
        CacheStats cacheStats = (CacheStats) obj;
        return this.hitCount == cacheStats.hitCount && this.missCount == cacheStats.missCount && this.loadSuccessCount == cacheStats.loadSuccessCount && this.loadExceptionCount == cacheStats.loadExceptionCount && this.totalLoadTime == cacheStats.totalLoadTime && this.evictionCount == cacheStats.evictionCount;
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount));
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long jRequestCount = requestCount();
        if (jRequestCount == 0) {
            return 1.0d;
        }
        return this.hitCount / jRequestCount;
    }

    public long loadCount() {
        return LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long jSaturatedAdd = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (jSaturatedAdd == 0) {
            return 0.0d;
        }
        return this.loadExceptionCount / jSaturatedAdd;
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public CacheStats minus(CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, LongMath.saturatedSubtract(this.hitCount, cacheStats.hitCount)), Math.max(0L, LongMath.saturatedSubtract(this.missCount, cacheStats.missCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadSuccessCount, cacheStats.loadSuccessCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadExceptionCount, cacheStats.loadExceptionCount)), Math.max(0L, LongMath.saturatedSubtract(this.totalLoadTime, cacheStats.totalLoadTime)), Math.max(0L, LongMath.saturatedSubtract(this.evictionCount, cacheStats.evictionCount)));
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long jRequestCount = requestCount();
        if (jRequestCount == 0) {
            return 0.0d;
        }
        return this.missCount / jRequestCount;
    }

    public CacheStats plus(CacheStats cacheStats) {
        return new CacheStats(LongMath.saturatedAdd(this.hitCount, cacheStats.hitCount), LongMath.saturatedAdd(this.missCount, cacheStats.missCount), LongMath.saturatedAdd(this.loadSuccessCount, cacheStats.loadSuccessCount), LongMath.saturatedAdd(this.loadExceptionCount, cacheStats.loadExceptionCount), LongMath.saturatedAdd(this.totalLoadTime, cacheStats.totalLoadTime), LongMath.saturatedAdd(this.evictionCount, cacheStats.evictionCount));
    }

    public long requestCount() {
        return LongMath.saturatedAdd(this.hitCount, this.missCount);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }
}

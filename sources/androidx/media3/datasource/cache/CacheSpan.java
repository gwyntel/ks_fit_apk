package androidx.media3.datasource.cache;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.util.UnstableApi;
import java.io.File;

@UnstableApi
/* loaded from: classes.dex */
public class CacheSpan implements Comparable<CacheSpan> {

    @Nullable
    public final File file;
    public final boolean isCached;
    public final String key;
    public final long lastTouchTimestamp;
    public final long length;
    public final long position;

    public CacheSpan(String str, long j2, long j3) {
        this(str, j2, j3, C.TIME_UNSET, null);
    }

    public boolean isHoleSpan() {
        return !this.isCached;
    }

    public boolean isOpenEnded() {
        return this.length == -1;
    }

    public String toString() {
        return "[" + this.position + ", " + this.length + "]";
    }

    public CacheSpan(String str, long j2, long j3, long j4, @Nullable File file) {
        this.key = str;
        this.position = j2;
        this.length = j3;
        this.isCached = file != null;
        this.file = file;
        this.lastTouchTimestamp = j4;
    }

    @Override // java.lang.Comparable
    public int compareTo(CacheSpan cacheSpan) {
        if (!this.key.equals(cacheSpan.key)) {
            return this.key.compareTo(cacheSpan.key);
        }
        long j2 = this.position - cacheSpan.position;
        if (j2 == 0) {
            return 0;
        }
        return j2 < 0 ? -1 : 1;
    }
}

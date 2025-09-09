package androidx.media3.datasource.cache;

/* loaded from: classes.dex */
final class CacheFileMetadata {
    public final long lastTouchTimestamp;
    public final long length;

    public CacheFileMetadata(long j2, long j3) {
        this.length = j2;
        this.lastTouchTimestamp = j3;
    }
}

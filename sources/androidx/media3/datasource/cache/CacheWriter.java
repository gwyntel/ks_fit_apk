package androidx.media3.datasource.cache;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSourceUtil;
import androidx.media3.datasource.DataSpec;
import java.io.IOException;
import java.io.InterruptedIOException;

@UnstableApi
/* loaded from: classes.dex */
public final class CacheWriter {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    private long bytesCached;
    private final Cache cache;
    private final String cacheKey;
    private final CacheDataSource dataSource;
    private final DataSpec dataSpec;
    private long endPosition;
    private volatile boolean isCanceled;
    private long nextPosition;

    @Nullable
    private final ProgressListener progressListener;
    private final byte[] temporaryBuffer;

    public interface ProgressListener {
        void onProgress(long j2, long j3, long j4);
    }

    public CacheWriter(CacheDataSource cacheDataSource, DataSpec dataSpec, @Nullable byte[] bArr, @Nullable ProgressListener progressListener) {
        this.dataSource = cacheDataSource;
        this.cache = cacheDataSource.getCache();
        this.dataSpec = dataSpec;
        this.temporaryBuffer = bArr == null ? new byte[131072] : bArr;
        this.progressListener = progressListener;
        this.cacheKey = cacheDataSource.getCacheKeyFactory().buildCacheKey(dataSpec);
        this.nextPosition = dataSpec.position;
    }

    private long getLength() {
        long j2 = this.endPosition;
        if (j2 == -1) {
            return -1L;
        }
        return j2 - this.dataSpec.position;
    }

    private void onNewBytesCached(long j2) {
        this.bytesCached += j2;
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(getLength(), this.bytesCached, j2);
        }
    }

    private void onRequestEndPosition(long j2) {
        if (this.endPosition == j2) {
            return;
        }
        this.endPosition = j2;
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(getLength(), this.bytesCached, 0L);
        }
    }

    private long readBlockToCache(long j2, long j3) throws IOException {
        long jOpen;
        boolean z2 = true;
        boolean z3 = j2 + j3 == this.endPosition || j3 == -1;
        if (j3 != -1) {
            try {
                jOpen = this.dataSource.open(this.dataSpec.buildUpon().setPosition(j2).setLength(j3).build());
            } catch (IOException unused) {
                DataSourceUtil.closeQuietly(this.dataSource);
            }
        } else {
            z2 = false;
            jOpen = -1;
        }
        if (!z2) {
            throwIfCanceled();
            try {
                jOpen = this.dataSource.open(this.dataSpec.buildUpon().setPosition(j2).setLength(-1L).build());
            } catch (IOException e2) {
                DataSourceUtil.closeQuietly(this.dataSource);
                throw e2;
            }
        }
        if (z3 && jOpen != -1) {
            try {
                onRequestEndPosition(jOpen + j2);
            } catch (IOException e3) {
                DataSourceUtil.closeQuietly(this.dataSource);
                throw e3;
            }
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 != -1) {
            throwIfCanceled();
            CacheDataSource cacheDataSource = this.dataSource;
            byte[] bArr = this.temporaryBuffer;
            i2 = cacheDataSource.read(bArr, 0, bArr.length);
            if (i2 != -1) {
                onNewBytesCached(i2);
                i3 += i2;
            }
        }
        if (z3) {
            onRequestEndPosition(j2 + i3);
        }
        this.dataSource.close();
        return i3;
    }

    private void throwIfCanceled() throws InterruptedIOException {
        if (this.isCanceled) {
            throw new InterruptedIOException();
        }
    }

    @WorkerThread
    public void cache() throws IOException {
        throwIfCanceled();
        Cache cache = this.cache;
        String str = this.cacheKey;
        DataSpec dataSpec = this.dataSpec;
        this.bytesCached = cache.getCachedBytes(str, dataSpec.position, dataSpec.length);
        DataSpec dataSpec2 = this.dataSpec;
        long j2 = dataSpec2.length;
        if (j2 != -1) {
            this.endPosition = dataSpec2.position + j2;
        } else {
            long jA = c.a(this.cache.getContentMetadata(this.cacheKey));
            if (jA == -1) {
                jA = -1;
            }
            this.endPosition = jA;
        }
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(getLength(), this.bytesCached, 0L);
        }
        while (true) {
            long j3 = this.endPosition;
            if (j3 != -1 && this.nextPosition >= j3) {
                return;
            }
            throwIfCanceled();
            long j4 = this.endPosition;
            long cachedLength = this.cache.getCachedLength(this.cacheKey, this.nextPosition, j4 == -1 ? Long.MAX_VALUE : j4 - this.nextPosition);
            if (cachedLength > 0) {
                this.nextPosition += cachedLength;
            } else {
                long j5 = -cachedLength;
                if (j5 == Long.MAX_VALUE) {
                    j5 = -1;
                }
                long j6 = this.nextPosition;
                this.nextPosition = j6 + readBlockToCache(j6, j5);
            }
        }
    }

    public void cancel() {
        this.isCanceled = true;
    }
}

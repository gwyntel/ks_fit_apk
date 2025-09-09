package androidx.media3.exoplayer.upstream;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.CacheSpan;
import androidx.media3.extractor.ChunkIndex;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

@UnstableApi
/* loaded from: classes2.dex */
public final class CachedRegionTracker implements Cache.Listener {
    public static final int CACHED_TO_END = -2;
    public static final int NOT_CACHED = -1;
    private static final String TAG = "CachedRegionTracker";
    private final Cache cache;
    private final String cacheKey;
    private final ChunkIndex chunkIndex;
    private final TreeSet<Region> regions = new TreeSet<>();
    private final Region lookupRegion = new Region(0, 0);

    private static class Region implements Comparable<Region> {
        public long endOffset;
        public int endOffsetIndex;
        public long startOffset;

        public Region(long j2, long j3) {
            this.startOffset = j2;
            this.endOffset = j3;
        }

        @Override // java.lang.Comparable
        public int compareTo(Region region) {
            return Util.compareLong(this.startOffset, region.startOffset);
        }
    }

    public CachedRegionTracker(Cache cache, String str, ChunkIndex chunkIndex) {
        this.cache = cache;
        this.cacheKey = str;
        this.chunkIndex = chunkIndex;
        synchronized (this) {
            try {
                Iterator<CacheSpan> itDescendingIterator = cache.addListener(str, this).descendingIterator();
                while (itDescendingIterator.hasNext()) {
                    mergeSpan(itDescendingIterator.next());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void mergeSpan(CacheSpan cacheSpan) {
        long j2 = cacheSpan.position;
        Region region = new Region(j2, cacheSpan.length + j2);
        Region regionFloor = this.regions.floor(region);
        Region regionCeiling = this.regions.ceiling(region);
        boolean zRegionsConnect = regionsConnect(regionFloor, region);
        if (regionsConnect(region, regionCeiling)) {
            if (zRegionsConnect) {
                regionFloor.endOffset = regionCeiling.endOffset;
                regionFloor.endOffsetIndex = regionCeiling.endOffsetIndex;
            } else {
                region.endOffset = regionCeiling.endOffset;
                region.endOffsetIndex = regionCeiling.endOffsetIndex;
                this.regions.add(region);
            }
            this.regions.remove(regionCeiling);
            return;
        }
        if (!zRegionsConnect) {
            int iBinarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region.endOffset);
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 2;
            }
            region.endOffsetIndex = iBinarySearch;
            this.regions.add(region);
            return;
        }
        regionFloor.endOffset = region.endOffset;
        int i2 = regionFloor.endOffsetIndex;
        while (true) {
            ChunkIndex chunkIndex = this.chunkIndex;
            if (i2 >= chunkIndex.length - 1) {
                break;
            }
            int i3 = i2 + 1;
            if (chunkIndex.offsets[i3] > regionFloor.endOffset) {
                break;
            } else {
                i2 = i3;
            }
        }
        regionFloor.endOffsetIndex = i2;
    }

    private boolean regionsConnect(@Nullable Region region, @Nullable Region region2) {
        return (region == null || region2 == null || region.endOffset != region2.startOffset) ? false : true;
    }

    public synchronized int getRegionEndTimeMs(long j2) {
        int i2;
        Region region = this.lookupRegion;
        region.startOffset = j2;
        Region regionFloor = this.regions.floor(region);
        if (regionFloor != null) {
            long j3 = regionFloor.endOffset;
            if (j2 <= j3 && (i2 = regionFloor.endOffsetIndex) != -1) {
                ChunkIndex chunkIndex = this.chunkIndex;
                if (i2 == chunkIndex.length - 1) {
                    if (j3 == chunkIndex.offsets[i2] + chunkIndex.sizes[i2]) {
                        return -2;
                    }
                }
                return (int) ((chunkIndex.timesUs[i2] + ((chunkIndex.durationsUs[i2] * (j3 - chunkIndex.offsets[i2])) / chunkIndex.sizes[i2])) / 1000);
            }
        }
        return -1;
    }

    @Override // androidx.media3.datasource.cache.Cache.Listener
    public synchronized void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        mergeSpan(cacheSpan);
    }

    @Override // androidx.media3.datasource.cache.Cache.Listener
    public synchronized void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        long j2 = cacheSpan.position;
        Region region = new Region(j2, cacheSpan.length + j2);
        Region regionFloor = this.regions.floor(region);
        if (regionFloor == null) {
            Log.e(TAG, "Removed a span we were not aware of");
            return;
        }
        this.regions.remove(regionFloor);
        long j3 = regionFloor.startOffset;
        long j4 = region.startOffset;
        if (j3 < j4) {
            Region region2 = new Region(j3, j4);
            int iBinarySearch = Arrays.binarySearch(this.chunkIndex.offsets, region2.endOffset);
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 2;
            }
            region2.endOffsetIndex = iBinarySearch;
            this.regions.add(region2);
        }
        long j5 = regionFloor.endOffset;
        long j6 = region.endOffset;
        if (j5 > j6) {
            Region region3 = new Region(j6 + 1, j5);
            region3.endOffsetIndex = regionFloor.endOffsetIndex;
            this.regions.add(region3);
        }
    }

    @Override // androidx.media3.datasource.cache.Cache.Listener
    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
    }

    public void release() {
        this.cache.removeListener(this.cacheKey, this);
    }
}

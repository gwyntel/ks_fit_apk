package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public class LruBitmapPool implements BitmapPool {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private final Set<Bitmap.Config> allowedConfigs;
    private long currentSize;
    private int evictions;
    private int hits;
    private final long initialMaxSize;
    private long maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    private interface BitmapTracker {
        void add(Bitmap bitmap);

        void remove(Bitmap bitmap);
    }

    private static final class NullBitmapTracker implements BitmapTracker {
        NullBitmapTracker() {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void add(Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void remove(Bitmap bitmap) {
        }
    }

    private static class ThrowingBitmapTracker implements BitmapTracker {
        private final Set<Bitmap> bitmaps = Collections.synchronizedSet(new HashSet());

        private ThrowingBitmapTracker() {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void add(Bitmap bitmap) {
            if (!this.bitmaps.contains(bitmap)) {
                this.bitmaps.add(bitmap);
                return;
            }
            throw new IllegalStateException("Can't add already added bitmap: " + bitmap + " [" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]");
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void remove(Bitmap bitmap) {
            if (!this.bitmaps.contains(bitmap)) {
                throw new IllegalStateException("Cannot remove bitmap not in tracker");
            }
            this.bitmaps.remove(bitmap);
        }
    }

    LruBitmapPool(long j2, LruPoolStrategy lruPoolStrategy, Set set) {
        this.initialMaxSize = j2;
        this.maxSize = j2;
        this.strategy = lruPoolStrategy;
        this.allowedConfigs = set;
        this.tracker = new NullBitmapTracker();
    }

    @TargetApi(26)
    private static void assertNotHardwareConfig(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            throw new IllegalArgumentException("Cannot create a mutable Bitmap with config: " + config + ". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
        }
    }

    @NonNull
    private static Bitmap createBitmap(int i2, int i3, @Nullable Bitmap.Config config) {
        if (config == null) {
            config = DEFAULT_CONFIG;
        }
        return Bitmap.createBitmap(i2, i3, config);
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            dumpUnchecked();
        }
    }

    private void dumpUnchecked() {
        Log.v(TAG, "Hits=" + this.hits + ", misses=" + this.misses + ", puts=" + this.puts + ", evictions=" + this.evictions + ", currentSize=" + this.currentSize + ", maxSize=" + this.maxSize + "\nStrategy=" + this.strategy);
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> getDefaultAllowedConfigs() {
        HashSet hashSet = new HashSet(Arrays.asList(Bitmap.Config.values()));
        int i2 = Build.VERSION.SDK_INT;
        hashSet.add(null);
        if (i2 >= 26) {
            hashSet.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static LruPoolStrategy getDefaultStrategy() {
        return new SizeConfigStrategy();
    }

    @Nullable
    private synchronized Bitmap getDirtyOrNull(int i2, int i3, @Nullable Bitmap.Config config) {
        Bitmap bitmap;
        try {
            assertNotHardwareConfig(config);
            bitmap = this.strategy.get(i2, i3, config != null ? config : DEFAULT_CONFIG);
            if (bitmap == null) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Missing bitmap=" + this.strategy.logBitmap(i2, i3, config));
                }
                this.misses++;
            } else {
                this.hits++;
                this.currentSize -= this.strategy.getSize(bitmap);
                this.tracker.remove(bitmap);
                normalize(bitmap);
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Get bitmap=" + this.strategy.logBitmap(i2, i3, config));
            }
            dump();
        } catch (Throwable th) {
            throw th;
        }
        return bitmap;
    }

    @TargetApi(19)
    private static void maybeSetPreMultiplied(Bitmap bitmap) {
        bitmap.setPremultiplied(true);
    }

    private static void normalize(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        maybeSetPreMultiplied(bitmap);
    }

    private synchronized void trimToSize(long j2) {
        while (this.currentSize > j2) {
            try {
                Bitmap bitmapRemoveLast = this.strategy.removeLast();
                if (bitmapRemoveLast == null) {
                    if (Log.isLoggable(TAG, 5)) {
                        Log.w(TAG, "Size mismatch, resetting");
                        dumpUnchecked();
                    }
                    this.currentSize = 0L;
                    return;
                }
                this.tracker.remove(bitmapRemoveLast);
                this.currentSize -= this.strategy.getSize(bitmapRemoveLast);
                this.evictions++;
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Evicting bitmap=" + this.strategy.logBitmap(bitmapRemoveLast));
                }
                dump();
                bitmapRemoveLast.recycle();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public void clearMemory() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        trimToSize(0L);
    }

    public long evictionCount() {
        return this.evictions;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @NonNull
    public Bitmap get(int i2, int i3, Bitmap.Config config) {
        Bitmap dirtyOrNull = getDirtyOrNull(i2, i3, config);
        if (dirtyOrNull == null) {
            return createBitmap(i2, i3, config);
        }
        dirtyOrNull.eraseColor(0);
        return dirtyOrNull;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @NonNull
    public Bitmap getDirty(int i2, int i3, Bitmap.Config config) {
        Bitmap dirtyOrNull = getDirtyOrNull(i2, i3, config);
        return dirtyOrNull == null ? createBitmap(i2, i3, config) : dirtyOrNull;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public long getMaxSize() {
        return this.maxSize;
    }

    public long hitCount() {
        return this.hits;
    }

    public long missCount() {
        return this.misses;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized void put(Bitmap bitmap) {
        try {
            if (bitmap == null) {
                throw new NullPointerException("Bitmap must not be null");
            }
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Cannot pool recycled bitmap");
            }
            if (bitmap.isMutable() && this.strategy.getSize(bitmap) <= this.maxSize && this.allowedConfigs.contains(bitmap.getConfig())) {
                int size = this.strategy.getSize(bitmap);
                this.strategy.put(bitmap);
                this.tracker.add(bitmap);
                this.puts++;
                this.currentSize += size;
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Put bitmap in pool=" + this.strategy.logBitmap(bitmap));
                }
                dump();
                evict();
                return;
            }
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Reject bitmap from pool, bitmap: " + this.strategy.logBitmap(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.allowedConfigs.contains(bitmap.getConfig()));
            }
            bitmap.recycle();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized void setSizeMultiplier(float f2) {
        this.maxSize = Math.round(this.initialMaxSize * f2);
        evict();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i2) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "trimMemory, level=" + i2);
        }
        if (i2 >= 40 || i2 >= 20) {
            clearMemory();
        } else if (i2 >= 20 || i2 == 15) {
            trimToSize(getMaxSize() / 2);
        }
    }

    public LruBitmapPool(long j2) {
        this(j2, getDefaultStrategy(), getDefaultAllowedConfigs());
    }

    public LruBitmapPool(long j2, Set<Bitmap.Config> set) {
        this(j2, getDefaultStrategy(), set);
    }
}

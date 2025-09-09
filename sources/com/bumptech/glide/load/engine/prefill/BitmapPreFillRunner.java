package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
final class BitmapPreFillRunner implements Runnable {
    private static final Clock DEFAULT_CLOCK = new Clock();

    /* renamed from: a, reason: collision with root package name */
    static final long f12375a = TimeUnit.SECONDS.toMillis(1);
    private final BitmapPool bitmapPool;
    private final Clock clock;
    private long currentDelay;
    private final Handler handler;
    private boolean isCancelled;
    private final MemoryCache memoryCache;
    private final Set<PreFillType> seenTypes;
    private final PreFillQueue toPrefill;

    @VisibleForTesting
    static class Clock {
        Clock() {
        }

        long a() {
            return SystemClock.currentThreadTimeMillis();
        }
    }

    private static final class UniqueKey implements Key {
        UniqueKey() {
        }

        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    public BitmapPreFillRunner(BitmapPool bitmapPool, MemoryCache memoryCache, PreFillQueue preFillQueue) {
        this(bitmapPool, memoryCache, preFillQueue, DEFAULT_CLOCK, new Handler(Looper.getMainLooper()));
    }

    private long getFreeMemoryCacheBytes() {
        return this.memoryCache.getMaxSize() - this.memoryCache.getCurrentSize();
    }

    private long getNextDelay() {
        long j2 = this.currentDelay;
        this.currentDelay = Math.min(4 * j2, f12375a);
        return j2;
    }

    private boolean isGcDetected(long j2) {
        return this.clock.a() - j2 >= 32;
    }

    boolean a() {
        Bitmap bitmapCreateBitmap;
        long jA = this.clock.a();
        while (!this.toPrefill.isEmpty() && !isGcDetected(jA)) {
            PreFillType preFillTypeRemove = this.toPrefill.remove();
            if (this.seenTypes.contains(preFillTypeRemove)) {
                bitmapCreateBitmap = Bitmap.createBitmap(preFillTypeRemove.d(), preFillTypeRemove.b(), preFillTypeRemove.a());
            } else {
                this.seenTypes.add(preFillTypeRemove);
                bitmapCreateBitmap = this.bitmapPool.getDirty(preFillTypeRemove.d(), preFillTypeRemove.b(), preFillTypeRemove.a());
            }
            int bitmapByteSize = Util.getBitmapByteSize(bitmapCreateBitmap);
            if (getFreeMemoryCacheBytes() >= bitmapByteSize) {
                this.memoryCache.put(new UniqueKey(), BitmapResource.obtain(bitmapCreateBitmap, this.bitmapPool));
            } else {
                this.bitmapPool.put(bitmapCreateBitmap);
            }
            if (Log.isLoggable("PreFillRunner", 3)) {
                Log.d("PreFillRunner", "allocated [" + preFillTypeRemove.d() + "x" + preFillTypeRemove.b() + "] " + preFillTypeRemove.a() + " size: " + bitmapByteSize);
            }
        }
        return (this.isCancelled || this.toPrefill.isEmpty()) ? false : true;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (a()) {
            this.handler.postDelayed(this, getNextDelay());
        }
    }

    BitmapPreFillRunner(BitmapPool bitmapPool, MemoryCache memoryCache, PreFillQueue preFillQueue, Clock clock, Handler handler) {
        this.seenTypes = new HashSet();
        this.currentDelay = 40L;
        this.bitmapPool = bitmapPool;
        this.memoryCache = memoryCache;
        this.toPrefill = preFillQueue;
        this.clock = clock;
        this.handler = handler;
    }
}

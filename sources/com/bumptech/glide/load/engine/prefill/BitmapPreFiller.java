package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.bumptech.glide.util.Util;
import java.util.HashMap;

/* loaded from: classes3.dex */
public final class BitmapPreFiller {
    private final BitmapPool bitmapPool;
    private BitmapPreFillRunner current;
    private final DecodeFormat defaultFormat;
    private final MemoryCache memoryCache;

    public BitmapPreFiller(MemoryCache memoryCache, BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this.memoryCache = memoryCache;
        this.bitmapPool = bitmapPool;
        this.defaultFormat = decodeFormat;
    }

    private static int getSizeInBytes(PreFillType preFillType) {
        return Util.getBitmapByteSize(preFillType.d(), preFillType.b(), preFillType.a());
    }

    PreFillQueue a(PreFillType... preFillTypeArr) {
        long maxSize = (this.memoryCache.getMaxSize() - this.memoryCache.getCurrentSize()) + this.bitmapPool.getMaxSize();
        int iC = 0;
        for (PreFillType preFillType : preFillTypeArr) {
            iC += preFillType.c();
        }
        float f2 = maxSize / iC;
        HashMap map = new HashMap();
        for (PreFillType preFillType2 : preFillTypeArr) {
            map.put(preFillType2, Integer.valueOf(Math.round(preFillType2.c() * f2) / getSizeInBytes(preFillType2)));
        }
        return new PreFillQueue(map);
    }

    public void preFill(PreFillType.Builder... builderArr) {
        BitmapPreFillRunner bitmapPreFillRunner = this.current;
        if (bitmapPreFillRunner != null) {
            bitmapPreFillRunner.cancel();
        }
        PreFillType[] preFillTypeArr = new PreFillType[builderArr.length];
        for (int i2 = 0; i2 < builderArr.length; i2++) {
            PreFillType.Builder builder = builderArr[i2];
            if (builder.b() == null) {
                builder.setConfig(this.defaultFormat == DecodeFormat.PREFER_ARGB_8888 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            preFillTypeArr[i2] = builder.a();
        }
        BitmapPreFillRunner bitmapPreFillRunner2 = new BitmapPreFillRunner(this.bitmapPool, this.memoryCache, a(preFillTypeArr));
        this.current = bitmapPreFillRunner2;
        Util.postOnUiThread(bitmapPreFillRunner2);
    }
}

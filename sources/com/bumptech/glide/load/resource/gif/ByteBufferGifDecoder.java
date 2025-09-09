package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

/* loaded from: classes3.dex */
public class ByteBufferGifDecoder implements ResourceDecoder<ByteBuffer, GifDrawable> {
    private static final GifDecoderFactory GIF_DECODER_FACTORY = new GifDecoderFactory();
    private static final GifHeaderParserPool PARSER_POOL = new GifHeaderParserPool();
    private static final String TAG = "BufferGifDecoder";
    private final Context context;
    private final GifDecoderFactory gifDecoderFactory;
    private final GifHeaderParserPool parserPool;
    private final List<ImageHeaderParser> parsers;
    private final GifBitmapProvider provider;

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        GifDecoder a(GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i2) {
            return new StandardGifDecoder(bitmapProvider, gifHeader, byteBuffer, i2);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<GifHeaderParser> pool = Util.createQueue(0);

        GifHeaderParserPool() {
        }

        synchronized GifHeaderParser a(ByteBuffer byteBuffer) {
            GifHeaderParser gifHeaderParserPoll;
            try {
                gifHeaderParserPoll = this.pool.poll();
                if (gifHeaderParserPoll == null) {
                    gifHeaderParserPoll = new GifHeaderParser();
                }
            } catch (Throwable th) {
                throw th;
            }
            return gifHeaderParserPoll.setData(byteBuffer);
        }

        synchronized void b(GifHeaderParser gifHeaderParser) {
            gifHeaderParser.clear();
            this.pool.offer(gifHeaderParser);
        }
    }

    public ByteBufferGifDecoder(Context context) {
        this(context, Glide.get(context).getRegistry().getImageHeaderParsers(), Glide.get(context).getBitmapPool(), Glide.get(context).getArrayPool());
    }

    private static int getSampleSize(GifHeader gifHeader, int i2, int i3) {
        int iMin = Math.min(gifHeader.getHeight() / i3, gifHeader.getWidth() / i2);
        int iMax = Math.max(1, iMin == 0 ? 0 : Integer.highestOneBit(iMin));
        if (Log.isLoggable(TAG, 2) && iMax > 1) {
            Log.v(TAG, "Downsampling GIF, sampleSize: " + iMax + ", target dimens: [" + i2 + "x" + i3 + "], actual dimens: [" + gifHeader.getWidth() + "x" + gifHeader.getHeight() + "]");
        }
        return iMax;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public GifDrawableResource decode(@NonNull ByteBuffer byteBuffer, int i2, int i3, @NonNull Options options) {
        GifHeaderParser gifHeaderParserA = this.parserPool.a(byteBuffer);
        try {
            return decode(byteBuffer, i2, i3, gifHeaderParserA, options);
        } finally {
            this.parserPool.b(gifHeaderParserA);
        }
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public boolean handles(@NonNull ByteBuffer byteBuffer, @NonNull Options options) throws IOException {
        return !((Boolean) options.get(GifOptions.DISABLE_ANIMATION)).booleanValue() && ImageHeaderParserUtils.getType(this.parsers, byteBuffer) == ImageHeaderParser.ImageType.GIF;
    }

    public ByteBufferGifDecoder(Context context, List<ImageHeaderParser> list, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this(context, list, bitmapPool, arrayPool, PARSER_POOL, GIF_DECODER_FACTORY);
    }

    @Nullable
    private GifDrawableResource decode(ByteBuffer byteBuffer, int i2, int i3, GifHeaderParser gifHeaderParser, Options options) {
        Bitmap.Config config;
        long logTime = LogTime.getLogTime();
        try {
            GifHeader header = gifHeaderParser.parseHeader();
            if (header.getNumFrames() > 0 && header.getStatus() == 0) {
                if (options.get(GifOptions.DECODE_FORMAT) == DecodeFormat.PREFER_RGB_565) {
                    config = Bitmap.Config.RGB_565;
                } else {
                    config = Bitmap.Config.ARGB_8888;
                }
                GifDecoder gifDecoderA = this.gifDecoderFactory.a(this.provider, header, byteBuffer, getSampleSize(header, i2, i3));
                gifDecoderA.setDefaultBitmapConfig(config);
                gifDecoderA.advance();
                Bitmap nextFrame = gifDecoderA.getNextFrame();
                if (nextFrame == null) {
                    if (Log.isLoggable(TAG, 2)) {
                        Log.v(TAG, "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
                    }
                    return null;
                }
                GifDrawableResource gifDrawableResource = new GifDrawableResource(new GifDrawable(this.context, gifDecoderA, UnitTransformation.get(), i2, i3, nextFrame));
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(TAG, "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
                }
                return gifDrawableResource;
            }
            return null;
        } finally {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Decoded GIF from stream in " + LogTime.getElapsedMillis(logTime));
            }
        }
    }

    ByteBufferGifDecoder(Context context, List list, BitmapPool bitmapPool, ArrayPool arrayPool, GifHeaderParserPool gifHeaderParserPool, GifDecoderFactory gifDecoderFactory) {
        this.context = context.getApplicationContext();
        this.parsers = list;
        this.gifDecoderFactory = gifDecoderFactory;
        this.provider = new GifBitmapProvider(bitmapPool, arrayPool);
        this.parserPool = gifHeaderParserPool;
    }
}

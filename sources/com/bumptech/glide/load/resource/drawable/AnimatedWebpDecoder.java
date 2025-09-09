package com.bumptech.glide.load.resource.drawable;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.DefaultOnHeaderDecodedListener;
import com.bumptech.glide.util.ByteBufferUtil;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(28)
/* loaded from: classes3.dex */
public final class AnimatedWebpDecoder {
    private final ArrayPool arrayPool;
    private final List<ImageHeaderParser> imageHeaderParsers;

    private static final class AnimatedImageDrawableResource implements Resource<Drawable> {
        private static final int ESTIMATED_NUMBER_OF_FRAMES = 2;
        private final AnimatedImageDrawable imageDrawable;

        AnimatedImageDrawableResource(AnimatedImageDrawable animatedImageDrawable) {
            this.imageDrawable = animatedImageDrawable;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        @NonNull
        public Class<Drawable> getResourceClass() {
            return Drawable.class;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public int getSize() {
            return this.imageDrawable.getIntrinsicWidth() * this.imageDrawable.getIntrinsicHeight() * Util.getBytesPerPixel(Bitmap.Config.ARGB_8888) * 2;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public void recycle() {
            this.imageDrawable.stop();
            this.imageDrawable.clearAnimationCallbacks();
        }

        @Override // com.bumptech.glide.load.engine.Resource
        @NonNull
        public Drawable get() {
            return this.imageDrawable;
        }
    }

    private static final class ByteBufferAnimatedWebpDecoder implements ResourceDecoder<ByteBuffer, Drawable> {
        private final AnimatedWebpDecoder delegate;

        ByteBufferAnimatedWebpDecoder(AnimatedWebpDecoder animatedWebpDecoder) {
            this.delegate = animatedWebpDecoder;
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public Resource<Drawable> decode(@NonNull ByteBuffer byteBuffer, int i2, int i3, @NonNull Options options) throws IOException {
            return this.delegate.a(ImageDecoder.createSource(byteBuffer), i2, i3, options);
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public boolean handles(@NonNull ByteBuffer byteBuffer, @NonNull Options options) throws IOException {
            return this.delegate.c(byteBuffer);
        }
    }

    private static final class StreamAnimatedWebpDecoder implements ResourceDecoder<InputStream, Drawable> {
        private final AnimatedWebpDecoder delegate;

        StreamAnimatedWebpDecoder(AnimatedWebpDecoder animatedWebpDecoder) {
            this.delegate = animatedWebpDecoder;
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public Resource<Drawable> decode(@NonNull InputStream inputStream, int i2, int i3, @NonNull Options options) throws IOException {
            return this.delegate.a(ImageDecoder.createSource(ByteBufferUtil.fromStream(inputStream)), i2, i3, options);
        }

        @Override // com.bumptech.glide.load.ResourceDecoder
        public boolean handles(@NonNull InputStream inputStream, @NonNull Options options) throws IOException {
            return this.delegate.b(inputStream);
        }
    }

    private AnimatedWebpDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        this.imageHeaderParsers = list;
        this.arrayPool = arrayPool;
    }

    public static ResourceDecoder<ByteBuffer, Drawable> byteBufferDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        return new ByteBufferAnimatedWebpDecoder(new AnimatedWebpDecoder(list, arrayPool));
    }

    private boolean isHandled(ImageHeaderParser.ImageType imageType) {
        return imageType == ImageHeaderParser.ImageType.ANIMATED_WEBP;
    }

    public static ResourceDecoder<InputStream, Drawable> streamDecoder(List<ImageHeaderParser> list, ArrayPool arrayPool) {
        return new StreamAnimatedWebpDecoder(new AnimatedWebpDecoder(list, arrayPool));
    }

    Resource a(ImageDecoder.Source source, int i2, int i3, Options options) throws IOException {
        Drawable drawableDecodeDrawable = ImageDecoder.decodeDrawable(source, new DefaultOnHeaderDecodedListener(i2, i3, options));
        if (a.a(drawableDecodeDrawable)) {
            return new AnimatedImageDrawableResource(b.a(drawableDecodeDrawable));
        }
        throw new IOException("Received unexpected drawable type for animated webp, failing: " + drawableDecodeDrawable);
    }

    boolean b(InputStream inputStream) {
        return isHandled(ImageHeaderParserUtils.getType(this.imageHeaderParsers, inputStream, this.arrayPool));
    }

    boolean c(ByteBuffer byteBuffer) {
        return isHandled(ImageHeaderParserUtils.getType(this.imageHeaderParsers, byteBuffer));
    }
}

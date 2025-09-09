package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public abstract class ThumbnailImageViewTarget<T> extends ImageViewTarget<T> {
    public ThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    @Override // com.bumptech.glide.request.target.ImageViewTarget
    protected void c(Object obj) {
        ViewGroup.LayoutParams layoutParams = ((ImageView) this.f12430a).getLayoutParams();
        Drawable drawableD = d(obj);
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            drawableD = new FixedSizeDrawable(drawableD, layoutParams.width, layoutParams.height);
        }
        ((ImageView) this.f12430a).setImageDrawable(drawableD);
    }

    protected abstract Drawable d(Object obj);

    @Deprecated
    public ThumbnailImageViewTarget(ImageView imageView, boolean z2) {
        super(imageView, z2);
    }
}

package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class DrawableImageViewTarget extends ImageViewTarget<Drawable> {
    public DrawableImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.request.target.ImageViewTarget
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public void c(Drawable drawable) {
        ((ImageView) this.f12430a).setImageDrawable(drawable);
    }

    @Deprecated
    public DrawableImageViewTarget(ImageView imageView, boolean z2) {
        super(imageView, z2);
    }
}

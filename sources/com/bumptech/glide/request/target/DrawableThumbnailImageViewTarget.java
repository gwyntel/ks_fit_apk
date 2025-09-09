package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class DrawableThumbnailImageViewTarget extends ThumbnailImageViewTarget<Drawable> {
    public DrawableThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.request.target.ThumbnailImageViewTarget
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public Drawable d(Drawable drawable) {
        return drawable;
    }

    @Deprecated
    public DrawableThumbnailImageViewTarget(ImageView imageView, boolean z2) {
        super(imageView, z2);
    }
}

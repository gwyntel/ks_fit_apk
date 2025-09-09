package com.bumptech.glide.request.target;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class BitmapThumbnailImageViewTarget extends ThumbnailImageViewTarget<Bitmap> {
    public BitmapThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.request.target.ThumbnailImageViewTarget
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public Drawable d(Bitmap bitmap) {
        return new BitmapDrawable(((ImageView) this.f12430a).getResources(), bitmap);
    }

    @Deprecated
    public BitmapThumbnailImageViewTarget(ImageView imageView, boolean z2) {
        super(imageView, z2);
    }
}

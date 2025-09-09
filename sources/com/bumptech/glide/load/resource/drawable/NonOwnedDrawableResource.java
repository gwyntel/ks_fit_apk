package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.Resource;

/* loaded from: classes3.dex */
final class NonOwnedDrawableResource extends DrawableResource<Drawable> {
    private NonOwnedDrawableResource(Drawable drawable) {
        super(drawable);
    }

    static Resource a(Drawable drawable) {
        if (drawable != null) {
            return new NonOwnedDrawableResource(drawable);
        }
        return null;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<Drawable> getResourceClass() {
        return this.f12395a.getClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return Math.max(1, this.f12395a.getIntrinsicWidth() * this.f12395a.getIntrinsicHeight() * 4);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
    }
}

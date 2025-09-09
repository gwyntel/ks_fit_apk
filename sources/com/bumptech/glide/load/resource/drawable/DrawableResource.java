package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes3.dex */
public abstract class DrawableResource<T extends Drawable> implements Resource<T>, Initializable {

    /* renamed from: a, reason: collision with root package name */
    protected final Drawable f12395a;

    public DrawableResource(T t2) {
        this.f12395a = (Drawable) Preconditions.checkNotNull(t2);
    }

    public void initialize() {
        Drawable drawable = this.f12395a;
        if (drawable instanceof BitmapDrawable) {
            ((BitmapDrawable) drawable).getBitmap().prepareToDraw();
        } else if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).getFirstFrame().prepareToDraw();
        }
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public final T get() {
        Drawable.ConstantState constantState = this.f12395a.getConstantState();
        return constantState == null ? (T) this.f12395a : (T) constantState.newDrawable();
    }
}

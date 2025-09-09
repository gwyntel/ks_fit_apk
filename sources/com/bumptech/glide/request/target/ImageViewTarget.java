package com.bumptech.glide.request.target;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes3.dex */
public abstract class ImageViewTarget<Z> extends ViewTarget<ImageView, Z> implements Transition.ViewAdapter {

    @Nullable
    private Animatable animatable;

    public ImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    private void maybeUpdateAnimatable(@Nullable Z z2) {
        if (!(z2 instanceof Animatable)) {
            this.animatable = null;
            return;
        }
        Animatable animatable = (Animatable) z2;
        this.animatable = animatable;
        animatable.start();
    }

    private void setResourceInternal(@Nullable Z z2) {
        c(z2);
        maybeUpdateAnimatable(z2);
    }

    protected abstract void c(Object obj);

    @Override // com.bumptech.glide.request.transition.Transition.ViewAdapter
    @Nullable
    public Drawable getCurrentDrawable() {
        return ((ImageView) this.f12430a).getDrawable();
    }

    @Override // com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
        super.onLoadCleared(drawable);
        Animatable animatable = this.animatable;
        if (animatable != null) {
            animatable.stop();
        }
        setResourceInternal(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadFailed(@Nullable Drawable drawable) {
        super.onLoadFailed(drawable);
        setResourceInternal(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadStarted(@Nullable Drawable drawable) {
        super.onLoadStarted(drawable);
        setResourceInternal(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onResourceReady(@NonNull Z z2, @Nullable Transition<? super Z> transition) {
        if (transition == null || !transition.transition(z2, this)) {
            setResourceInternal(z2);
        } else {
            maybeUpdateAnimatable(z2);
        }
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        Animatable animatable = this.animatable;
        if (animatable != null) {
            animatable.start();
        }
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        Animatable animatable = this.animatable;
        if (animatable != null) {
            animatable.stop();
        }
    }

    @Override // com.bumptech.glide.request.transition.Transition.ViewAdapter
    public void setDrawable(Drawable drawable) {
        ((ImageView) this.f12430a).setImageDrawable(drawable);
    }

    @Deprecated
    public ImageViewTarget(ImageView imageView, boolean z2) {
        super(imageView, z2);
    }
}

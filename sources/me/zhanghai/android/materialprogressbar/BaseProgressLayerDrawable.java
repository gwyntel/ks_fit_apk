package me.zhanghai.android.materialprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable;
import me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable;
import me.zhanghai.android.materialprogressbar.TintableDrawable;
import me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

/* loaded from: classes5.dex */
class BaseProgressLayerDrawable<ProgressDrawableType extends IntrinsicPaddingDrawable & ShowBackgroundDrawable & TintableDrawable, BackgroundDrawableType extends IntrinsicPaddingDrawable & ShowBackgroundDrawable & TintableDrawable> extends LayerDrawable implements IntrinsicPaddingDrawable, MaterialProgressDrawable, ShowBackgroundDrawable, TintableDrawable {
    private float mBackgroundAlpha;
    private BackgroundDrawableType mBackgroundDrawable;
    private ProgressDrawableType mProgressDrawable;
    private ProgressDrawableType mSecondaryProgressDrawable;

    public BaseProgressLayerDrawable(Drawable[] drawableArr, Context context) {
        super(drawableArr);
        this.mBackgroundAlpha = ThemeUtils.getFloatFromAttrRes(android.R.attr.disabledAlpha, 0.0f, context);
        setId(0, android.R.id.background);
        this.mBackgroundDrawable = (BackgroundDrawableType) ((IntrinsicPaddingDrawable) getDrawable(0));
        setId(1, android.R.id.secondaryProgress);
        this.mSecondaryProgressDrawable = (ProgressDrawableType) ((IntrinsicPaddingDrawable) getDrawable(1));
        setId(2, android.R.id.progress);
        this.mProgressDrawable = (ProgressDrawableType) ((IntrinsicPaddingDrawable) getDrawable(2));
        setTint(ThemeUtils.getColorFromAttrRes(R.attr.colorControlActivated, ViewCompat.MEASURED_STATE_MASK, context));
    }

    @Override // me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public boolean getShowBackground() {
        return this.mBackgroundDrawable.getShowBackground();
    }

    @Override // me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public boolean getUseIntrinsicPadding() {
        return this.mBackgroundDrawable.getUseIntrinsicPadding();
    }

    @Override // me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public void setShowBackground(boolean z2) {
        if (this.mBackgroundDrawable.getShowBackground() != z2) {
            this.mBackgroundDrawable.setShowBackground(z2);
            this.mSecondaryProgressDrawable.setShowBackground(!z2);
        }
    }

    @Override // android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public void setTint(@ColorInt int i2) {
        int alphaComponent = ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * this.mBackgroundAlpha));
        this.mBackgroundDrawable.setTint(alphaComponent);
        this.mSecondaryProgressDrawable.setTint(alphaComponent);
        this.mProgressDrawable.setTint(i2);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public void setTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList colorStateListWithAlpha;
        if (colorStateList != null) {
            if (!colorStateList.isOpaque()) {
                Log.w(getClass().getSimpleName(), "setTintList() called with a non-opaque ColorStateList, its original alpha will be discarded");
            }
            colorStateListWithAlpha = colorStateList.withAlpha(Math.round(this.mBackgroundAlpha * 255.0f));
        } else {
            colorStateListWithAlpha = null;
        }
        this.mBackgroundDrawable.setTintList(colorStateListWithAlpha);
        this.mSecondaryProgressDrawable.setTintList(colorStateListWithAlpha);
        this.mProgressDrawable.setTintList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mBackgroundDrawable.setTintMode(mode);
        this.mSecondaryProgressDrawable.setTintMode(mode);
        this.mProgressDrawable.setTintMode(mode);
    }

    @Override // me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public void setUseIntrinsicPadding(boolean z2) {
        this.mBackgroundDrawable.setUseIntrinsicPadding(z2);
        this.mSecondaryProgressDrawable.setUseIntrinsicPadding(z2);
        this.mProgressDrawable.setUseIntrinsicPadding(z2);
    }
}

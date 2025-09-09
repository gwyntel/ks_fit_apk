package me.zhanghai.android.materialprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes5.dex */
public class CircularProgressDrawable extends BaseProgressLayerDrawable<SingleCircularProgressDrawable, CircularProgressBackgroundDrawable> {
    public CircularProgressDrawable(int i2, Context context) {
        super(new Drawable[]{new CircularProgressBackgroundDrawable(), new SingleCircularProgressDrawable(i2), new SingleCircularProgressDrawable(i2)}, context);
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public /* bridge */ /* synthetic */ boolean getShowBackground() {
        return super.getShowBackground();
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public /* bridge */ /* synthetic */ boolean getUseIntrinsicPadding() {
        return super.getUseIntrinsicPadding();
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public /* bridge */ /* synthetic */ void setShowBackground(boolean z2) {
        super.setShowBackground(z2);
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public /* bridge */ /* synthetic */ void setTint(@ColorInt int i2) {
        super.setTint(i2);
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public /* bridge */ /* synthetic */ void setTintList(@Nullable ColorStateList colorStateList) {
        super.setTintList(colorStateList);
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, android.graphics.drawable.Drawable, me.zhanghai.android.materialprogressbar.TintableDrawable
    @SuppressLint({"NewApi"})
    public /* bridge */ /* synthetic */ void setTintMode(@NonNull PorterDuff.Mode mode) {
        super.setTintMode(mode);
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseProgressLayerDrawable, me.zhanghai.android.materialprogressbar.IntrinsicPaddingDrawable
    public /* bridge */ /* synthetic */ void setUseIntrinsicPadding(boolean z2) {
        super.setUseIntrinsicPadding(z2);
    }
}

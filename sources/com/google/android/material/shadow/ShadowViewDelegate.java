package com.google.android.material.shadow;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public interface ShadowViewDelegate {
    float getRadius();

    boolean isCompatPaddingEnabled();

    void setBackgroundDrawable(Drawable drawable);

    void setShadowPadding(int i2, int i3, int i4, int i5);
}

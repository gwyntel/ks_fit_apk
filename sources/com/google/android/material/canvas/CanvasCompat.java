package com.google.android.material.canvas;

import android.graphics.Canvas;
import android.graphics.RectF;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class CanvasCompat {
    private CanvasCompat() {
    }

    public static int saveLayerAlpha(Canvas canvas, RectF rectF, int i2) {
        return canvas.saveLayerAlpha(rectF, i2);
    }

    public static int saveLayerAlpha(Canvas canvas, float f2, float f3, float f4, float f5, int i2) {
        return canvas.saveLayerAlpha(f2, f3, f4, f5, i2);
    }
}

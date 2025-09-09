package me.zhanghai.android.materialprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.view.ViewCompat;

/* loaded from: classes5.dex */
abstract class BasePaintDrawable extends BaseDrawable {
    private Paint mPaint;

    BasePaintDrawable() {
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseDrawable
    protected final void onDraw(Canvas canvas, int i2, int i3) {
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            onPreparePaint(this.mPaint);
        }
        this.mPaint.setAlpha(this.mAlpha);
        this.mPaint.setColorFilter(getColorFilterForDrawing());
        onDraw(canvas, i2, i3, this.mPaint);
    }

    protected abstract void onDraw(Canvas canvas, int i2, int i3, Paint paint);

    protected abstract void onPreparePaint(Paint paint);
}

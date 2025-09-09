package me.zhanghai.android.materialprogressbar;

import android.graphics.Canvas;
import android.graphics.Paint;

/* loaded from: classes5.dex */
class SingleCircularProgressDrawable extends BaseSingleCircularProgressDrawable implements ShowBackgroundDrawable {
    private static final int LEVEL_MAX = 10000;
    private static final float START_ANGLE_MAX_DYNAMIC = 360.0f;
    private static final float START_ANGLE_MAX_NORMAL = 0.0f;
    private static final float SWEEP_ANGLE_MAX = 360.0f;
    private boolean mShowBackground;
    private final float mStartAngleMax;

    SingleCircularProgressDrawable(int i2) {
        if (i2 == 0) {
            this.mStartAngleMax = 0.0f;
        } else {
            if (i2 != 1) {
                throw new IllegalArgumentException("Invalid value for style");
            }
            this.mStartAngleMax = 360.0f;
        }
    }

    @Override // me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public boolean getShowBackground() {
        return this.mShowBackground;
    }

    @Override // me.zhanghai.android.materialprogressbar.BaseSingleCircularProgressDrawable
    protected void onDrawRing(Canvas canvas, Paint paint) {
        int level = getLevel();
        if (level == 0) {
            return;
        }
        float f2 = level / 10000.0f;
        float f3 = this.mStartAngleMax * f2;
        float f4 = f2 * 360.0f;
        drawRing(canvas, paint, f3, f4);
        if (this.mShowBackground) {
            drawRing(canvas, paint, f3, f4);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i2) {
        invalidateSelf();
        return true;
    }

    @Override // me.zhanghai.android.materialprogressbar.ShowBackgroundDrawable
    public void setShowBackground(boolean z2) {
        if (this.mShowBackground != z2) {
            this.mShowBackground = z2;
            invalidateSelf();
        }
    }
}

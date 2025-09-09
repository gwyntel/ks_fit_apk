package com.luck.picture.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class RoundCornerRelativeLayout extends RelativeLayout {
    private final float cornerSize;
    private final boolean isBottomNormal;
    private final boolean isTopNormal;
    private final RectF mRect;
    private final Path path;

    public RoundCornerRelativeLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(this.path);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.path.reset();
        RectF rectF = this.mRect;
        rectF.right = i2;
        rectF.bottom = i3;
        boolean z2 = this.isTopNormal;
        if (!z2 && !this.isBottomNormal) {
            Path path = this.path;
            float f2 = this.cornerSize;
            path.addRoundRect(rectF, f2, f2, Path.Direction.CW);
            return;
        }
        if (z2) {
            float f3 = this.cornerSize;
            this.path.addRoundRect(rectF, new float[]{0.0f, 0.0f, 0.0f, 0.0f, f3, f3, f3, f3}, Path.Direction.CW);
        }
        if (this.isBottomNormal) {
            float f4 = this.cornerSize;
            this.path.addRoundRect(this.mRect, new float[]{f4, f4, f4, f4, 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CW);
        }
    }

    public RoundCornerRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerRelativeLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mRect = new RectF();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PictureRoundCornerRelativeLayout, i2, 0);
        this.cornerSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.PictureRoundCornerRelativeLayout_psCorners, 0.0f);
        this.isTopNormal = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PictureRoundCornerRelativeLayout_psTopNormal, false);
        this.isBottomNormal = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PictureRoundCornerRelativeLayout_psBottomNormal, false);
        typedArrayObtainStyledAttributes.recycle();
        this.path = new Path();
    }
}

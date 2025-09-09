package com.luck.picture.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class MediumBoldTextView extends AppCompatTextView {
    private float mStrokeWidth;

    public MediumBoldTextView(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        float strokeWidth = paint.getStrokeWidth();
        float f2 = this.mStrokeWidth;
        if (strokeWidth != f2) {
            paint.setStrokeWidth(f2);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        super.onDraw(canvas);
    }

    public void setStrokeWidth(float f2) {
        this.mStrokeWidth = f2;
        invalidate();
    }

    public MediumBoldTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MediumBoldTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mStrokeWidth = 0.6f;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PictureMediumBoldTextView, i2, 0);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getFloat(R.styleable.PictureMediumBoldTextView_stroke_Width, this.mStrokeWidth);
        typedArrayObtainStyledAttributes.recycle();
    }
}

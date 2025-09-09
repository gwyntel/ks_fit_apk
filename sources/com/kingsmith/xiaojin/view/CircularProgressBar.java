package com.kingsmith.xiaojin.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.kingsmith.xiaojin.R;

/* loaded from: classes4.dex */
public class CircularProgressBar extends View {
    private boolean isAnimation;
    private float mCurrentAngle;
    private float mCurrentProgress;
    private long mDuration;
    private int mEndAngle;
    private float mMaxProgress;
    private int mProgressColor;
    private int mProgressbarBgColor;
    private int mRadius;
    private int mStartAngle;
    private int mStrokeWidth;
    private String mText;
    private int mTextColor;
    private float mTextSize;

    public CircularProgressBar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mStartAngle = 0;
        this.mCurrentAngle = 0.0f;
        this.mEndAngle = 360;
        this.mDuration = 1000L;
        this.isAnimation = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CircularProgressBar);
        this.mRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CircularProgressBar_radius, 80);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CircularProgressBar_strokeWidth, 8);
        this.mProgressbarBgColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CircularProgressBar_progressbarBackgroundColor, ContextCompat.getColor(context, R.color.mProgressbarBgColor));
        this.mProgressColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CircularProgressBar_progressbarColor, ContextCompat.getColor(context, R.color.mProgressColor));
        this.mMaxProgress = typedArrayObtainStyledAttributes.getInt(R.styleable.CircularProgressBar_maxProgress, 100);
        this.mCurrentProgress = typedArrayObtainStyledAttributes.getInt(R.styleable.CircularProgressBar_progress, 0);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.CircularProgressBar_text);
        this.mText = string == null ? "" : string;
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CircularProgressBar_textColor, ContextCompat.getColor(context, R.color.mProgressTextColor));
        this.mTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CircularProgressBar_textSize, (int) TypedValue.applyDimension(2, 14.0f, getResources().getDisplayMetrics()));
        typedArrayObtainStyledAttributes.recycle();
    }

    private void drawCenterText(Canvas canvas, int i2) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.mTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(this.mTextSize);
        Rect rect = new Rect();
        String str = this.mText;
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(this.mText, i2, (rect.height() / 2) + (getHeight() / 2), paint);
    }

    private void drawProgress(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.mStrokeWidth);
        paint.setColor(this.mProgressColor);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        if (!this.isAnimation) {
            this.mCurrentAngle = (this.mCurrentProgress / this.mMaxProgress) * 360.0f;
        }
        canvas.drawArc(rectF, this.mStartAngle, this.mCurrentAngle, false, paint);
    }

    private void drawProgressbarBg(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.mStrokeWidth);
        paint.setAntiAlias(true);
        paint.setColor(this.mProgressbarBgColor);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, this.mStartAngle, this.mEndAngle, false, paint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAnimator$0(ValueAnimator valueAnimator) {
        this.mCurrentAngle = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private void setAnimator(float f2, float f3) {
        this.isAnimation = true;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f3);
        valueAnimatorOfFloat.setDuration(this.mDuration);
        valueAnimatorOfFloat.setTarget(Float.valueOf(this.mCurrentAngle));
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: h0.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f25024a.lambda$setAnimator$0(valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        RectF rectF = new RectF();
        int i2 = this.mStrokeWidth;
        rectF.left = i2;
        rectF.top = i2;
        int i3 = width * 2;
        rectF.right = i3 - i2;
        rectF.bottom = i3 - i2;
        drawProgressbarBg(canvas, rectF);
        drawProgress(canvas, rectF);
        drawCenterText(canvas, width);
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i2);
        int size = (mode == Integer.MIN_VALUE || mode == 0) ? this.mRadius * 2 : mode != 1073741824 ? 0 : View.MeasureSpec.getSize(i2);
        setMeasuredDimension(size, size);
    }

    public void setProgress(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Progress value can not be less than 0");
        }
        float f3 = this.mMaxProgress;
        if (f2 > f3) {
            f2 = f3;
        }
        this.mCurrentProgress = f2;
        float f4 = (f2 / f3) * 360.0f;
        this.mCurrentAngle = f4;
        setAnimator(0.0f, f4);
    }

    public void setText(String str) {
        this.mText = str;
    }

    public void setTextColor(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("Color value can not be less than 0");
        }
        this.mTextColor = i2;
    }

    public void setTextSize(float f2) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("textSize can not be less than 0");
        }
        this.mTextSize = f2;
    }
}

package com.aliyun.iot.link.ui.component.progress_dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import com.aliyun.iot.link.ui.component.R;

/* loaded from: classes3.dex */
public class BGAProgressBar extends ProgressBar {
    private static final int DEFAULT_RADIUS = 16;
    private static final String DEFAULT_REACHED_COLOR = "#70A800";
    private static final int DEFAULT_REACHED_HEIGHT = 2;
    private static final int DEFAULT_START_ANGLE = 270;
    private static final String DEFAULT_TEXT_COLOR = "#70A800";
    private static final int DEFAULT_TEXT_MARGIN = 4;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final String DEFAULT_UNREACHED_COLOR = "#CCCCCC";
    private static final int DEFAULT_UNREACHED_HEIGHT = 2;
    private static final String TAG = "BGAProgressBar";
    private RectF mArcRectF;
    private boolean mIsCapRounded;
    private boolean mIsHiddenText;
    private int mMaxStrokeWidth;
    private int mMaxUnReachedEndX;
    private Mode mMode;
    private Paint mPaint;
    private int mRadius;
    private int mReachedColor;
    private int mReachedHeight;
    private int mStartAngle;
    private String mText;
    private int mTextColor;
    private int mTextHeight;
    private int mTextMargin;
    private Rect mTextRect;
    private int mTextSize;
    private int mTextWidth;
    private int mUnReachedColor;
    private int mUnReachedHeight;

    public enum Mode {
        System,
        Horizontal,
        Circle
    }

    public BGAProgressBar(Context context) {
        this(context, null);
    }

    private void calculateTextWidthAndHeight() {
        this.mText = String.format("%d", Integer.valueOf((int) (((getProgress() * 1.0f) / getMax()) * 100.0f))) + "%";
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setStyle(Paint.Style.FILL);
        Paint paint = this.mPaint;
        String str = this.mText;
        paint.getTextBounds(str, 0, str.length(), this.mTextRect);
        this.mTextWidth = this.mTextRect.width();
        this.mTextHeight = this.mTextRect.height();
    }

    public static int dp2px(Context context, float f2) {
        return (int) TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    private void initCustomAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BGAProgressBar);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            initAttr(typedArrayObtainStyledAttributes.getIndex(i2), typedArrayObtainStyledAttributes);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initDefaultAttrs(Context context) {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMode = Mode.System;
        this.mTextColor = Color.parseColor("#70A800");
        this.mTextSize = sp2px(context, 10.0f);
        this.mTextMargin = dp2px(context, 4.0f);
        this.mReachedColor = Color.parseColor("#70A800");
        this.mReachedHeight = dp2px(context, 2.0f);
        this.mUnReachedColor = Color.parseColor(DEFAULT_UNREACHED_COLOR);
        this.mUnReachedHeight = dp2px(context, 2.0f);
        this.mIsCapRounded = false;
        this.mIsHiddenText = false;
        this.mStartAngle = 270;
        this.mRadius = dp2px(context, 16.0f);
    }

    private void onDrawCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft() + (this.mMaxStrokeWidth / 2), getPaddingTop() + (this.mMaxStrokeWidth / 2));
        Paint paint = this.mPaint;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        this.mPaint.setColor(this.mUnReachedColor);
        this.mPaint.setStrokeWidth(this.mUnReachedHeight);
        int i2 = this.mRadius;
        canvas.drawCircle(i2, i2, i2, this.mPaint);
        this.mPaint.setStyle(style);
        this.mPaint.setColor(this.mReachedColor);
        this.mPaint.setStrokeWidth(this.mReachedHeight);
        canvas.drawArc(this.mArcRectF, this.mStartAngle, ((getProgress() * 1.0f) / getMax()) * 360.0f, false, this.mPaint);
        if (!this.mIsHiddenText) {
            calculateTextWidthAndHeight();
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mTextColor);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(this.mText, this.mRadius, r1 + (this.mTextHeight / 2), this.mPaint);
        }
        canvas.restore();
    }

    private void onDrawHorizontal(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getMeasuredHeight() / 2);
        int i2 = this.mMaxUnReachedEndX;
        float progress = ((getProgress() * 1.0f) / getMax()) * i2;
        if (this.mIsHiddenText) {
            if (progress > i2) {
                progress = i2;
            }
            if (progress > 0.0f) {
                this.mPaint.setColor(this.mReachedColor);
                this.mPaint.setStrokeWidth(this.mReachedHeight);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(0.0f, 0.0f, progress, 0.0f, this.mPaint);
            }
            if (this.mIsCapRounded) {
                progress += ((this.mReachedHeight + this.mUnReachedHeight) * 1.0f) / 2.0f;
            }
            float f2 = progress;
            if (f2 < this.mMaxUnReachedEndX) {
                this.mPaint.setColor(this.mUnReachedColor);
                this.mPaint.setStrokeWidth(this.mUnReachedHeight);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(f2, 0.0f, this.mMaxUnReachedEndX, 0.0f, this.mPaint);
            }
        } else {
            calculateTextWidthAndHeight();
            float f3 = (this.mMaxUnReachedEndX - this.mTextWidth) - this.mTextMargin;
            if (progress > f3) {
                progress = f3;
            }
            if (progress > 0.0f) {
                this.mPaint.setColor(this.mReachedColor);
                this.mPaint.setStrokeWidth(this.mReachedHeight);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(0.0f, 0.0f, progress, 0.0f, this.mPaint);
            }
            this.mPaint.setTextAlign(Paint.Align.LEFT);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mTextColor);
            if (progress > 0.0f) {
                progress += this.mTextMargin;
            }
            canvas.drawText(this.mText, progress, this.mTextHeight / 2, this.mPaint);
            float f4 = progress + this.mTextWidth + this.mTextMargin;
            if (f4 < this.mMaxUnReachedEndX) {
                this.mPaint.setColor(this.mUnReachedColor);
                this.mPaint.setStrokeWidth(this.mUnReachedHeight);
                this.mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(f4, 0.0f, this.mMaxUnReachedEndX, 0.0f, this.mPaint);
            }
        }
        canvas.restore();
    }

    public static int sp2px(Context context, float f2) {
        return (int) TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    protected void initAttr(int i2, TypedArray typedArray) {
        if (i2 == R.styleable.BGAProgressBar_bga_pb_mode) {
            this.mMode = Mode.values()[typedArray.getInt(i2, Mode.System.ordinal())];
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_textColor) {
            this.mTextColor = typedArray.getColor(i2, this.mTextColor);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_textSize) {
            this.mTextSize = typedArray.getDimensionPixelOffset(i2, this.mTextSize);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_textMargin) {
            this.mTextMargin = typedArray.getDimensionPixelOffset(i2, this.mTextMargin);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_reachedColor) {
            this.mReachedColor = typedArray.getColor(i2, this.mReachedColor);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_reachedHeight) {
            this.mReachedHeight = typedArray.getDimensionPixelOffset(i2, this.mReachedHeight);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_unReachedColor) {
            this.mUnReachedColor = typedArray.getColor(i2, this.mUnReachedColor);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_unReachedHeight) {
            this.mUnReachedHeight = typedArray.getDimensionPixelOffset(i2, this.mUnReachedHeight);
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_isCapRounded) {
            boolean z2 = typedArray.getBoolean(i2, this.mIsCapRounded);
            this.mIsCapRounded = z2;
            if (z2) {
                this.mPaint.setStrokeCap(Paint.Cap.ROUND);
                return;
            }
            return;
        }
        if (i2 == R.styleable.BGAProgressBar_bga_pb_isHiddenText) {
            this.mIsHiddenText = typedArray.getBoolean(i2, this.mIsHiddenText);
        } else if (i2 == R.styleable.BGAProgressBar_bga_pb_radius) {
            this.mRadius = typedArray.getDimensionPixelOffset(i2, this.mRadius);
        } else if (i2 == R.styleable.BGAProgressBar_bga_pb_startAngle) {
            this.mStartAngle = typedArray.getInt(i2, this.mStartAngle);
        }
    }

    public void isCapRounded(boolean z2) {
        this.mIsCapRounded = z2;
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        try {
            Mode mode = this.mMode;
            if (mode == Mode.System) {
                super.onDraw(canvas);
            } else if (mode == Mode.Horizontal) {
                onDrawHorizontal(canvas);
            } else if (mode == Mode.Circle) {
                onDrawCircle(canvas);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i2, int i3) {
        try {
            Mode mode = this.mMode;
            if (mode == Mode.System) {
                super.onMeasure(i2, i3);
            } else if (mode == Mode.Horizontal) {
                calculateTextWidthAndHeight();
                setMeasuredDimension(View.MeasureSpec.getSize(i2), View.resolveSize(getPaddingTop() + getPaddingBottom() + (this.mIsHiddenText ? Math.max(this.mReachedHeight, this.mUnReachedHeight) : Math.max(this.mTextHeight, Math.max(this.mReachedHeight, this.mUnReachedHeight))), i3));
                this.mMaxUnReachedEndX = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            } else if (mode == Mode.Circle) {
                int paddingLeft = (this.mRadius * 2) + this.mMaxStrokeWidth + getPaddingLeft() + getPaddingRight();
                int iMin = Math.min(View.resolveSize(paddingLeft, i2), View.resolveSize(paddingLeft, i3));
                this.mRadius = (((iMin - getPaddingLeft()) - getPaddingRight()) - this.mMaxStrokeWidth) / 2;
                if (this.mArcRectF == null) {
                    this.mArcRectF = new RectF();
                }
                RectF rectF = this.mArcRectF;
                int i4 = this.mRadius;
                rectF.set(0.0f, 0.0f, i4 * 2, i4 * 2);
                setMeasuredDimension(iMin, iMin);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void setMode(Mode mode) {
        this.mMode = mode;
    }

    public void setReachedColor(String str) {
        this.mUnReachedColor = Color.parseColor(str);
    }

    public void setReachedWidth(int i2) {
        this.mReachedHeight = i2;
    }

    public void setStartAngle(int i2) {
        this.mStartAngle = i2;
    }

    public void setTextColor(String str) {
        this.mTextColor = Color.parseColor(str);
    }

    public void setTextMargin(int i2) {
        this.mTextMargin = i2;
    }

    public void setTextSize(int i2) {
        this.mTextSize = i2;
    }

    public void setUnReachedColor(String str) {
        this.mUnReachedColor = Color.parseColor(str);
    }

    public void setUnReachedWidth(int i2) {
        this.mUnReachedHeight = i2;
    }

    public BGAProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.progressBarStyle);
    }

    public BGAProgressBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTextRect = new Rect();
        initDefaultAttrs(context);
        initCustomAttrs(context, attributeSet);
        this.mMaxStrokeWidth = Math.max(this.mReachedHeight, this.mUnReachedHeight);
    }
}

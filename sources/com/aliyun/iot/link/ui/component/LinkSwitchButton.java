package com.aliyun.iot.link.ui.component;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import androidx.annotation.FloatRange;
import androidx.annotation.RequiresApi;
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord;
import com.aliyun.iot.link.ui.component.wheelview.DimensionUtil;

/* loaded from: classes3.dex */
public class LinkSwitchButton extends CompoundButton {
    private static final int DEFAULT_ANIMATION_DURATION = 250;
    private static final String TAG = "SwitchButton";
    private long mAnimationDuration;
    private ObjectAnimator mAnimator;
    private Drawable mBackgroundDrawable;
    private Drawable mCurrentBackDrawable;
    private Drawable mNextBackDrawable;
    private CharSequence mOffText;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    private CharSequence mOnText;
    private Paint mPaint;
    private float mProgress;
    private RectF mThumbDrawRectF;
    private Drawable mThumbDrawable;
    private int mThumbPadding;
    private RectF mThumbRectF;
    private long mTouchDownTime;
    private int mTouchDownX;
    private static int[] STATE_ON = {android.R.attr.state_checked, android.R.attr.state_enabled, android.R.attr.state_pressed};
    private static int[] STATE_OFF = {-16842912, android.R.attr.state_enabled, android.R.attr.state_pressed};

    public LinkSwitchButton(Context context) {
        this(context, null);
    }

    public static float dip2px(float f2) {
        return TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics());
    }

    private int getMeasuredSize(int i2, int i3) {
        return View.MeasureSpec.getMode(i3) != 1073741824 ? i2 : View.MeasureSpec.getSize(i3);
    }

    private void init() {
        this.mThumbRectF = new RectF();
        this.mThumbDrawRectF = new RectF();
        ObjectAnimator duration = ObjectAnimator.ofFloat(this, "progress", 0.0f, 1.0f).setDuration(this.mAnimationDuration);
        this.mAnimator = duration;
        duration.setDuration(this.mAnimationDuration);
        this.mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    private void obtainAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinkSwitchButton);
        this.mThumbPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinkSwitchButton_switchThumbPadding, (int) dip2px(2.5f));
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.LinkSwitchButton_switchBackground);
        this.mBackgroundDrawable = drawable;
        if (drawable == null) {
            this.mBackgroundDrawable = new ColorDrawable(Color.parseColor("#4cd964"));
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.LinkSwitchButton_switchThumb);
        this.mThumbDrawable = drawable2;
        if (drawable2 == null) {
            this.mThumbDrawable = new ColorDrawable(-1);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void resizeDrawableBounds() {
        this.mBackgroundDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        RectF rectF = this.mThumbRectF;
        int i2 = this.mThumbPadding;
        rectF.set(i2, i2, getMeasuredHeight() - this.mThumbPadding, getMeasuredHeight() - this.mThumbPadding);
        Drawable drawable = this.mThumbDrawable;
        RectF rectF2 = this.mThumbRectF;
        drawable.setBounds((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    public void animateToState(boolean z2) {
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (z2) {
            this.mAnimator.setFloatValues(this.mProgress, 1.0f);
        } else {
            this.mAnimator.setFloatValues(this.mProgress, 0.0f);
        }
        this.mAnimator.start();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] iArr = isChecked() ? STATE_OFF : STATE_ON;
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof StateListDrawable) {
            drawable.setState(iArr);
            this.mNextBackDrawable = this.mBackgroundDrawable.getCurrent().mutate();
            setDrawableState(this.mBackgroundDrawable);
            this.mCurrentBackDrawable = this.mBackgroundDrawable.getCurrent().mutate();
        }
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public CharSequence getOffText() {
        return this.mOffText;
    }

    public CharSequence getOnText() {
        return this.mOnText;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public int getThumbPadding() {
        return this.mThumbPadding;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    @RequiresApi(api = 21)
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof ColorDrawable) {
            this.mPaint.setColor(((ColorDrawable) drawable).getColor());
            canvas.drawRoundRect(0.0f, 0.0f, getWidth(), getHeight(), getHeight() >> 1, getHeight() >> 1, this.mPaint);
        } else if (drawable instanceof StateListDrawable) {
            int progress = (int) ((isChecked() ? getProgress() : 1.0f - getProgress()) * 255.0f);
            this.mCurrentBackDrawable.setAlpha(progress);
            this.mCurrentBackDrawable.draw(canvas);
            this.mNextBackDrawable.setAlpha(255 - progress);
            this.mNextBackDrawable.draw(canvas);
        }
        this.mThumbDrawRectF.set(this.mThumbRectF);
        this.mThumbDrawRectF.offset(this.mProgress * (getWidth() - getHeight()), 0.0f);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 instanceof ColorDrawable) {
            this.mPaint.setColor(((ColorDrawable) drawable2).getColor());
            RectF rectF = this.mThumbDrawRectF;
            canvas.drawRoundRect(rectF, rectF.height() / 2.0f, this.mThumbDrawRectF.height() / 2.0f, this.mPaint);
        } else {
            RectF rectF2 = this.mThumbDrawRectF;
            drawable2.setBounds((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
            this.mThumbDrawable.draw(canvas);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i2, int i3) {
        setMeasuredDimension(getMeasuredSize((int) DimensionUtil.dip2px(40.0f), i2), getMeasuredSize((int) DimensionUtil.dip2px(24.0f), i3));
        resizeDrawableBounds();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        resizeDrawableBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002c  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getAction()
            r1 = 1
            if (r0 == 0) goto L6a
            if (r0 == r1) goto L2c
            r2 = 2
            if (r0 == r2) goto L10
            r7 = 3
            if (r0 == r7) goto L2c
            goto L77
        L10:
            float r0 = r6.getProgress()
            float r7 = r7.getX()
            int r2 = r6.mTouchDownX
            float r2 = (float) r2
            float r7 = r7 - r2
            int r2 = r6.getWidth()
            int r3 = r6.getHeight()
            int r2 = r2 - r3
            float r2 = (float) r2
            float r7 = r7 / r2
            float r0 = r0 + r7
            r6.setProgress(r0)
            goto L77
        L2c:
            long r2 = java.lang.System.currentTimeMillis()
            long r4 = r6.mTouchDownTime
            long r2 = r2 - r4
            int r7 = android.view.ViewConfiguration.getTapTimeout()
            long r4 = (long) r7
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 >= 0) goto L4c
            boolean r7 = r6.isChecked()
            r7 = r7 ^ r1
            r6.setChecked(r7)
            boolean r7 = r6.isChecked()
            r6.animateToState(r7)
            goto L77
        L4c:
            float r7 = r6.getProgress()
            r0 = 1056964608(0x3f000000, float:0.5)
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            r2 = 0
            if (r7 <= 0) goto L59
            r7 = r1
            goto L5a
        L59:
            r7 = r2
        L5a:
            r6.animateToState(r7)
            float r7 = r6.getProgress()
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 <= 0) goto L66
            r2 = r1
        L66:
            r6.setChecked(r2)
            goto L77
        L6a:
            long r2 = java.lang.System.currentTimeMillis()
            r6.mTouchDownTime = r2
            float r7 = r7.getX()
            int r7 = (int) r7
            r6.mTouchDownX = r7
        L77:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.link.ui.component.LinkSwitchButton.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setAnimationDuration(long j2) {
        this.mAnimationDuration = j2;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
        setChecked(z2, false, true);
    }

    public void setOffText(CharSequence charSequence) {
        this.mOffText = charSequence;
    }

    @Override // android.widget.CompoundButton
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnText(CharSequence charSequence) {
        this.mOnText = charSequence;
    }

    public void setProgress(@FloatRange(from = 0.0d, to = HeartRateVariabilityRmssdRecord.MIN_HRV_RMSSD) float f2) {
        if (f2 > 1.0f) {
            this.mProgress = 1.0f;
        } else if (f2 < 0.0f) {
            this.mProgress = 0.0f;
        } else {
            this.mProgress = f2;
        }
        invalidate();
    }

    public void setThumbPadding(int i2) {
        this.mThumbPadding = i2;
    }

    public LinkSwitchButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setChecked(boolean z2, boolean z3, boolean z4) {
        if (isChecked() == z2) {
            return;
        }
        if (!z4) {
            super.setOnCheckedChangeListener(null);
        }
        super.setChecked(z2);
        super.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        if (!z3) {
            animateToState(z2);
            return;
        }
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        setProgress(z2 ? 1.0f : 0.0f);
        invalidate();
    }

    public LinkSwitchButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mAnimationDuration = 250L;
        this.mPaint = new Paint(1);
        obtainAttrs(context, attributeSet);
        init();
    }
}

package com.aliyun.iot.link.ui.component.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.view.ViewCompat;
import com.aliyun.iot.link.ui.component.R;
import com.aliyun.iot.link.ui.component.wheelview.source.DataHolder;
import com.aliyun.iot.link.ui.component.wheelview.source.ListDataHolder;
import com.aliyun.iot.link.ui.component.wheelview.source.NumberDataHolder;
import com.aliyun.iot.link.ui.component.wheelview.source.WheelDataSource;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class ILopWheelView<T> extends View implements Runnable {
    private static final float DEFAULT_FRICTION = 0.06f;
    private static final String DEFAULT_INTEGER_FORMAT = "%d";
    private static final int DEFAULT_NORMAL_TEXT_COLOR = -7829368;
    private static final int DEFAULT_RESERVED_DECIMAL_DIGITS = 2;
    private static final int DEFAULT_SELECTED_TEXT_COLOR = -16777216;
    private static final int DEFAULT_VISIBLE_ITEM = 5;
    private static final String TAG = "WheelView";
    private float _normalTextSizeOrigin;
    private float _selectTextSizeOrigin;
    private boolean mAutoAdjustTextSize;
    private long mClickTimeout;
    private int mCurrentScrollPosition;

    @NonNull
    private DataHolder<T> mDataHolder;
    private int mDecimalDigitNumber;
    private DecimalFormat mDecimalFormat;
    private int mDividerColor;
    private float mDividerHeight;
    private Rect mDrawRect;
    private float mFriction;
    private boolean mFroze;
    private String mIntegerFormat;
    private boolean mIsCyclic;
    private boolean mIsDragging;
    private boolean mIsFlingScroll;
    private int mItemHeight;
    private int mMaxScrollY;
    private int mMaximumVelocity;
    private int mMinScrollY;
    private int mMinimumVelocity;
    private int mNormalTextColor;
    private float mNormalTextSize;
    private OnItemSelectedListener<T> mOnItemSelectedListener;
    private Paint mPaint;
    private int mScrollOffsetY;
    private Scroller mScroller;
    private int mSelectedItemPosition;
    private int mSelectedItemTextColor;
    private float mSelectedItemTextSize;
    private int mSelectedRectColor;
    private SoundPlayer mSoundPlayer;
    private boolean mSpringBackEffect;
    private long mTouchDownTime;
    private float mTouchY;
    private VelocityTracker mVelocityTracker;
    private int mVisibleItemNum;
    private List<WheelLayer> mWheelLayers;
    private static final float DEFAULT_SELECTED_TEXT_SIZE = DimensionUtil.sp2px(18.0f);
    private static final float DEFAULT_NORMAL_TEXT_SIZE = DimensionUtil.sp2px(14.0f);
    private static final float DEFAULT_DIVIDER_HEIGHT = DimensionUtil.dip2px(1.0f);
    public static boolean mEnableLog = false;

    public interface OnItemSelectedListener<T> {
        void onItemSelected(T t2, int i2);

        void onWheelSelecting(T t2, int i2);
    }

    private static class SoundPlayer {
        private int mSoundId;
        private SoundPool mSoundPool = new SoundPool.Builder().build();

        public void load(Context context, @RawRes int i2) {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool != null) {
                this.mSoundId = soundPool.load(context, i2, 1);
            }
        }

        public void play() {
            int i2;
            SoundPool soundPool = this.mSoundPool;
            if (soundPool == null || (i2 = this.mSoundId) == 0) {
                return;
            }
            soundPool.play(i2, 1.0f, 1.0f, 1, 0, 1.0f);
        }

        public void release() {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool != null) {
                soundPool.release();
            }
        }
    }

    public ILopWheelView(Context context) {
        this(context, null);
    }

    private void Log(String str) {
        if (mEnableLog) {
            Log.d(TAG, str);
        }
    }

    private int adjustVisibleItemNum(int i2) {
        return ((i2 / 2) * 2) + 1;
    }

    private int calculateDistanceNeedToScroll(int i2) {
        int iAbs = Math.abs(i2);
        int i3 = this.mItemHeight;
        return iAbs > i3 / 2 ? this.mScrollOffsetY < 0 ? (-i3) - i2 : i3 - i2 : -i2;
    }

    private void checkIfSelectItemChange() {
        int currentPosition = getCurrentPosition();
        if (this.mCurrentScrollPosition != currentPosition) {
            OnItemSelectedListener<T> onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onWheelSelecting(this.mDataHolder.get(currentPosition), currentPosition);
            }
            playSoundEffect();
            this.mCurrentScrollPosition = currentPosition;
        }
    }

    private void drawDivider(Canvas canvas) {
        int i2 = this.mDividerColor;
        if (i2 == 0) {
            return;
        }
        this.mPaint.setColor(i2);
        this.mPaint.setStrokeWidth(this.mDividerHeight);
        Rect rect = this.mDrawRect;
        float f2 = rect.left;
        float fCenterY = rect.centerY() - (this.mItemHeight >> 1);
        Rect rect2 = this.mDrawRect;
        canvas.drawLine(f2, fCenterY, rect2.right, rect2.centerY() - (this.mItemHeight >> 1), this.mPaint);
        Rect rect3 = this.mDrawRect;
        float f3 = rect3.left;
        float fCenterY2 = rect3.centerY() + (this.mItemHeight >> 1);
        Rect rect4 = this.mDrawRect;
        canvas.drawLine(f3, fCenterY2, rect4.right, rect4.centerY() + (this.mItemHeight >> 1), this.mPaint);
    }

    private void drawItem(Canvas canvas, int i2, int i3) {
        int i4;
        float f2;
        float f3;
        String textByIndex = getTextByIndex(i2);
        if (textByIndex == null) {
            return;
        }
        if (this.mFroze) {
            i4 = (i2 - this.mSelectedItemPosition) * this.mItemHeight;
        } else {
            int i5 = this.mScrollOffsetY;
            int i6 = this.mItemHeight;
            i4 = ((i2 - (i5 / i6)) * i6) - i3;
        }
        int iAbs = Math.abs(i4);
        int i7 = this.mItemHeight;
        if (iAbs < i7) {
            float fAbs = this.mNormalTextSize + (((i7 - Math.abs(i4)) / (this.mItemHeight * 1.0f)) * (this.mSelectedItemTextSize - this.mNormalTextSize));
            int iEvaluate = evaluate((r3 - Math.abs(i4)) / (this.mItemHeight * 1.0f), this.mNormalTextColor, this.mSelectedItemTextColor);
            this.mPaint.setTextSize(fAbs);
            this.mPaint.setColor(iEvaluate);
            f2 = this.mPaint.getFontMetrics().descent;
            f3 = this.mPaint.getFontMetrics().ascent;
        } else {
            this.mPaint.setTextSize(this.mNormalTextSize);
            this.mPaint.setColor(this.mNormalTextColor);
            f2 = this.mPaint.getFontMetrics().descent;
            f3 = this.mPaint.getFontMetrics().ascent;
        }
        canvas.drawText(textByIndex, (int) ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - this.mPaint.measureText(textByIndex)) / 2.0f), (this.mDrawRect.centerY() + i4) - ((int) ((f2 + f3) / 2.0f)), this.mPaint);
    }

    private void drawSelectedItemBackground(Canvas canvas) {
        int i2 = this.mSelectedRectColor;
        if (i2 != 0) {
            this.mPaint.setColor(i2);
            Rect rect = this.mDrawRect;
            float f2 = rect.left;
            float fCenterY = rect.centerY() - (this.mItemHeight >> 1);
            Rect rect2 = this.mDrawRect;
            canvas.drawRect(f2, fCenterY, rect2.right, rect2.centerY() + (this.mItemHeight >> 1), this.mPaint);
        }
    }

    private void fixBounchEffect() {
        int finalY = this.mScroller.getFinalY();
        int iAbs = Math.abs(finalY % this.mItemHeight);
        int i2 = this.mItemHeight;
        this.mScroller.setFinalY(iAbs > (i2 >> 1) ? finalY < 0 ? ((finalY / i2) * i2) - i2 : ((finalY / i2) * i2) + i2 : (finalY / i2) * i2);
    }

    private int getCurrentPosition() {
        int i2;
        if (this.mItemHeight == 0 || this.mDataHolder.isEmpty()) {
            return 0;
        }
        int i3 = this.mScrollOffsetY;
        if (i3 < 0) {
            int i4 = this.mItemHeight;
            i2 = (i3 - (i4 / 2)) / i4;
        } else {
            int i5 = this.mItemHeight;
            i2 = (i3 + (i5 / 2)) / i5;
        }
        int size = i2 % this.mDataHolder.size();
        return size < 0 ? size + this.mDataHolder.size() : size;
    }

    private String getTextByIndex(int i2) {
        if (this.mDataHolder.isEmpty()) {
            Log("data is empty");
            return null;
        }
        if (!this.mIsCyclic && (i2 < 0 || i2 >= this.mDataHolder.size())) {
            return null;
        }
        int size = i2 % this.mDataHolder.size();
        if (size < 0) {
            size += this.mDataHolder.size();
        }
        return getItemDisplayText(this.mDataHolder.get(size));
    }

    private void init(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mClickTimeout = ViewConfiguration.getTapTimeout();
        Scroller scroller = new Scroller(context);
        this.mScroller = scroller;
        scroller.setFriction(this.mFriction);
        this.mDrawRect = new Rect();
        if (isInEditMode()) {
            return;
        }
        this.mSoundPlayer = new SoundPlayer();
    }

    private void initDecimalFormat() {
        StringBuilder sb = new StringBuilder("0.");
        for (int i2 = 0; i2 < this.mDecimalDigitNumber; i2++) {
            sb.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(sb.toString());
        this.mDecimalFormat = decimalFormat;
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        this.mDecimalFormat.setGroupingSize(3);
        this.mDecimalFormat.setGroupingUsed(true);
    }

    private void initVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    private void invalidateAndCheckItemChange() {
        invalidate();
        checkIfSelectItemChange();
    }

    public static boolean isEnableLog() {
        return mEnableLog;
    }

    private void obtainAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ILopWheelView);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.ILopWheelView_selectedTextSize, DEFAULT_SELECTED_TEXT_SIZE);
        this.mSelectedItemTextSize = dimension;
        this._selectTextSizeOrigin = dimension;
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.ILopWheelView_normalTextSize, DEFAULT_NORMAL_TEXT_SIZE);
        this.mNormalTextSize = dimension2;
        this._normalTextSizeOrigin = dimension2;
        this.mAutoAdjustTextSize = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ILopWheelView_autoAdjustTextSize, false);
        this.mNormalTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ILopWheelView_normalTextColor, DEFAULT_NORMAL_TEXT_COLOR);
        this.mSelectedItemTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ILopWheelView_selectedTextColor, -16777216);
        this.mDecimalDigitNumber = typedArrayObtainStyledAttributes.getInt(R.styleable.ILopWheelView_decimalDigitsNumber, 2);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.ILopWheelView_integerFormat);
        this.mIntegerFormat = string;
        if (TextUtils.isEmpty(string)) {
            this.mIntegerFormat = DEFAULT_INTEGER_FORMAT;
        }
        this.mVisibleItemNum = adjustVisibleItemNum(typedArrayObtainStyledAttributes.getInt(R.styleable.ILopWheelView_visibleItemNum, 5));
        this.mIsCyclic = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ILopWheelView_cyclic, false);
        this.mDividerHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.ILopWheelView_dividerHeight, DEFAULT_DIVIDER_HEIGHT);
        this.mDividerColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ILopWheelView_dividerColor, 0);
        this.mSelectedRectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ILopWheelView_selectedItemBackgroundColor, 0);
        this.mFriction = typedArrayObtainStyledAttributes.getFloat(R.styleable.ILopWheelView_friction, DEFAULT_FRICTION);
        this.mSpringBackEffect = !typedArrayObtainStyledAttributes.getBoolean(R.styleable.ILopWheelView_fixSpringBack, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void reset() {
        setBoundary();
        this.mScrollOffsetY = 0;
        this.mSelectedItemPosition = 0;
        this.mCurrentScrollPosition = 0;
        invalidateAndCheckItemChange();
    }

    private void resizeTextSize(int i2) {
        float f2 = this._selectTextSizeOrigin;
        this.mSelectedItemTextSize = f2;
        float f3 = this._normalTextSizeOrigin;
        this.mNormalTextSize = f3;
        float f4 = i2 * 0.9f;
        if (f2 > f4) {
            this.mSelectedItemTextSize = f4;
            this.mNormalTextSize = (f4 * f3) / f2;
        }
    }

    private void scroll(int i2) {
        int i3 = this.mScrollOffsetY + i2;
        this.mScrollOffsetY = i3;
        if (!this.mIsCyclic) {
            this.mScrollOffsetY = Math.min(this.mMaxScrollY, Math.max(this.mMinScrollY, i3));
        }
        invalidateAndCheckItemChange();
    }

    private void setBoundary() {
        boolean z2 = this.mIsCyclic;
        this.mMinScrollY = z2 ? Integer.MIN_VALUE : 0;
        this.mMaxScrollY = z2 ? Integer.MAX_VALUE : (this.mDataHolder.size() - 1) * this.mItemHeight;
    }

    public static void setEnableLog(boolean z2) {
        mEnableLog = z2;
    }

    private void updateAfterStop() {
        int i2 = this.mSelectedItemPosition;
        if (i2 < 0) {
            this.mSelectedItemPosition = (i2 % this.mDataHolder.size()) + this.mDataHolder.size();
        }
        if (this.mSelectedItemPosition >= this.mDataHolder.size()) {
            this.mSelectedItemPosition %= this.mDataHolder.size();
        }
        Log("stop position:" + this.mSelectedItemPosition);
        this.mScrollOffsetY = this.mItemHeight * this.mSelectedItemPosition;
        this.mIsFlingScroll = false;
        invalidateAndCheckItemChange();
    }

    public void addWheelLayer(WheelLayer wheelLayer) {
        this.mWheelLayers.add(wheelLayer);
    }

    public int evaluate(float f2, int i2, int i3) {
        float f3 = ((i2 >> 24) & 255) / 255.0f;
        float fPow = (float) Math.pow(((i2 >> 16) & 255) / 255.0f, 2.2d);
        float fPow2 = (float) Math.pow(((i2 >> 8) & 255) / 255.0f, 2.2d);
        float fPow3 = (float) Math.pow((i2 & 255) / 255.0f, 2.2d);
        float fPow4 = (float) Math.pow(((i3 >> 16) & 255) / 255.0f, 2.2d);
        float f4 = f3 + (((((i3 >> 24) & 255) / 255.0f) - f3) * f2);
        float fPow5 = fPow2 + ((((float) Math.pow(((i3 >> 8) & 255) / 255.0f, 2.2d)) - fPow2) * f2);
        float fPow6 = fPow3 + (f2 * (((float) Math.pow((i3 & 255) / 255.0f, 2.2d)) - fPow3));
        return (Math.round(((float) Math.pow(fPow + ((fPow4 - fPow) * f2), 0.45454545454545453d)) * 255.0f) << 16) | (Math.round(f4 * 255.0f) << 24) | (Math.round(((float) Math.pow(fPow5, 0.45454545454545453d)) * 255.0f) << 8) | Math.round(((float) Math.pow(fPow6, 0.45454545454545453d)) * 255.0f);
    }

    public void finishScroll() {
        if (this.mScroller.isFinished()) {
            return;
        }
        this.mScroller.abortAnimation();
        this.mSelectedItemPosition = (int) ((this.mScroller.getFinalY() / this.mItemHeight) + (this.mScrollOffsetY > 0 ? 0.5f : -0.5f));
        updateAfterStop();
    }

    public List<T> getData() {
        return this.mDataHolder.toList();
    }

    public int getDecimalDigitNumber() {
        return this.mDecimalDigitNumber;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public float getDividerHeight() {
        return this.mDividerHeight;
    }

    public float getFriction() {
        return this.mFriction;
    }

    public String getIntegerFormat() {
        return this.mIntegerFormat;
    }

    public String getItemDisplayText(T t2) {
        if (t2 == null) {
            return "";
        }
        if (t2 instanceof WheelDataSource) {
            return ((WheelDataSource) t2).getDisplayText();
        }
        if (t2 instanceof Integer) {
            return !TextUtils.isEmpty(this.mIntegerFormat) ? String.format(Locale.getDefault(), this.mIntegerFormat, t2) : String.valueOf(t2);
        }
        if (!(t2 instanceof Float) && !(t2 instanceof Double)) {
            return t2.toString();
        }
        if (this.mDecimalFormat == null) {
            initDecimalFormat();
        }
        return this.mDecimalFormat.format(t2);
    }

    public float getNormalTextSize() {
        return this.mNormalTextSize;
    }

    public OnItemSelectedListener<T> getOnItemSelectedListener() {
        return this.mOnItemSelectedListener;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public T getSelectedItemData() {
        return this.mDataHolder.get(this.mSelectedItemPosition);
    }

    public int getSelectedItemPosition() {
        return this.mSelectedItemPosition;
    }

    public float getSelectedItemTextSize() {
        return this.mSelectedItemTextSize;
    }

    public int getSelectedRectColor() {
        return this.mSelectedRectColor;
    }

    public int getVisibleItemNum() {
        return this.mVisibleItemNum;
    }

    public List<WheelLayer> getWheelLayers() {
        return this.mWheelLayers;
    }

    public boolean isAutoAdjustTextSize() {
        return this.mAutoAdjustTextSize;
    }

    public boolean isCyclic() {
        return this.mIsCyclic;
    }

    public boolean isFroze() {
        return this.mFroze;
    }

    public boolean isSpringBackEffect() {
        return this.mSpringBackEffect;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SoundPlayer soundPlayer = this.mSoundPlayer;
        if (soundPlayer != null) {
            soundPlayer.release();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int i2;
        int i3;
        super.onDraw(canvas);
        drawSelectedItemBackground(canvas);
        drawDivider(canvas);
        if (this.mFroze) {
            i3 = this.mSelectedItemPosition;
            i2 = 0;
        } else {
            int i4 = this.mScrollOffsetY;
            int i5 = this.mItemHeight;
            int i6 = i4 / i5;
            i2 = i4 % i5;
            i3 = i6;
        }
        int i7 = (this.mVisibleItemNum + 1) / 2;
        int i8 = i3 + i7;
        for (int i9 = (i3 - i7) + (this.mScrollOffsetY > 0 ? 1 : 0); i9 <= i8; i9++) {
            drawItem(canvas, i9, i2);
        }
        Iterator<WheelLayer> it = this.mWheelLayers.iterator();
        while (it.hasNext()) {
            it.next().onDraw(this, canvas, this.mDrawRect);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.mItemHeight = Math.max(View.MeasureSpec.getSize(i3) / this.mVisibleItemNum, 1);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.mItemHeight = Math.max(1, i3 / this.mVisibleItemNum);
        this.mDrawRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        setBoundary();
        if (this.mAutoAdjustTextSize) {
            resizeTextSize(this.mItemHeight);
        }
        this.mScrollOffsetY = this.mSelectedItemPosition * this.mItemHeight;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mFroze) {
            Log("frozen.  skip touch events");
            return false;
        }
        initVelocityTracker(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.forceFinished(true);
            }
            this.mIsDragging = true;
            this.mTouchY = motionEvent.getY();
            this.mTouchDownTime = System.currentTimeMillis();
        } else if (actionMasked == 1) {
            this.mIsDragging = false;
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            float yVelocity = this.mVelocityTracker.getYVelocity();
            if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                this.mScroller.forceFinished(true);
                this.mIsFlingScroll = true;
                this.mScroller.fling(0, this.mScrollOffsetY, 0, (int) (-yVelocity), 0, 0, this.mMinScrollY, this.mMaxScrollY);
                if (!this.mSpringBackEffect) {
                    fixBounchEffect();
                }
            } else {
                int y2 = System.currentTimeMillis() - this.mTouchDownTime <= this.mClickTimeout ? (int) (motionEvent.getY() - this.mDrawRect.centerY()) : 0;
                int iCalculateDistanceNeedToScroll = y2 + calculateDistanceNeedToScroll((this.mScrollOffsetY + y2) % this.mItemHeight);
                if (!this.mIsCyclic) {
                    iCalculateDistanceNeedToScroll = iCalculateDistanceNeedToScroll <= 0 ? Math.max(iCalculateDistanceNeedToScroll, -this.mScrollOffsetY) : Math.min(iCalculateDistanceNeedToScroll, this.mMaxScrollY - this.mScrollOffsetY);
                }
                this.mScroller.startScroll(0, this.mScrollOffsetY, 0, iCalculateDistanceNeedToScroll);
            }
            invalidateAndCheckItemChange();
            ViewCompat.postOnAnimation(this, this);
            recycleVelocityTracker();
        } else if (actionMasked == 2) {
            float y3 = motionEvent.getY();
            float f2 = y3 - this.mTouchY;
            if (Math.abs(f2) >= 1.0f) {
                scroll((int) (-f2));
                this.mTouchY = y3;
            }
        } else if (actionMasked == 3) {
            recycleVelocityTracker();
        }
        return true;
    }

    public void playSoundEffect() {
        SoundPlayer soundPlayer = this.mSoundPlayer;
        if (soundPlayer != null) {
            soundPlayer.play();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.mScroller.isFinished() && !this.mIsDragging && !this.mIsFlingScroll) {
            int currentPosition = getCurrentPosition();
            if (currentPosition == this.mSelectedItemPosition) {
                return;
            }
            this.mSelectedItemPosition = currentPosition;
            this.mCurrentScrollPosition = currentPosition;
            OnItemSelectedListener<T> onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(this.mDataHolder.get(currentPosition), this.mSelectedItemPosition);
            }
        }
        if (this.mScroller.computeScrollOffset()) {
            this.mScrollOffsetY = this.mScroller.getCurrY();
            invalidateAndCheckItemChange();
            ViewCompat.postOnAnimation(this, this);
        } else if (this.mIsFlingScroll) {
            this.mIsFlingScroll = false;
            Scroller scroller = this.mScroller;
            int i2 = this.mScrollOffsetY;
            scroller.startScroll(0, i2, 0, calculateDistanceNeedToScroll(i2 % this.mItemHeight));
            invalidateAndCheckItemChange();
            ViewCompat.postOnAnimation(this, this);
        }
    }

    public void setAutoAdjustTextSize(boolean z2) {
        this.mAutoAdjustTextSize = z2;
        requestLayout();
        invalidate();
    }

    public void setCyclic(boolean z2) {
        if (this.mIsCyclic == z2) {
            return;
        }
        this.mIsCyclic = z2;
        setBoundary();
        invalidate();
    }

    public void setData(List<T> list) {
        if (list == null) {
            return;
        }
        setDataSource(new ListDataHolder(list));
    }

    public void setDataInRange(Number number, Number number2, Number number3, boolean z2) {
        setDataSource(new NumberDataHolder(number, number2, number3, z2));
    }

    public void setDataSource(DataHolder<T> dataHolder) {
        this.mScroller.forceFinished(true);
        this.mDataHolder = dataHolder;
        reset();
    }

    public void setDecimalDigitNumber(int i2) {
        this.mDecimalDigitNumber = i2;
        invalidate();
    }

    public void setDefault(int i2) {
        if (i2 < 0) {
            Log("index not in range:0");
            i2 = 0;
        } else if (i2 > this.mDataHolder.size() - 1) {
            i2 = this.mDataHolder.size() - 1;
            Log("index not in range:" + i2);
        }
        int i3 = (this.mItemHeight * i2) - this.mScrollOffsetY;
        if (i3 == 0) {
            return;
        }
        finishScroll();
        scroll(i3);
        this.mSelectedItemPosition = i2;
        OnItemSelectedListener<T> onItemSelectedListener = this.mOnItemSelectedListener;
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(this.mDataHolder.get(i2), this.mSelectedItemPosition);
        }
    }

    public void setDividerColor(@ColorInt int i2) {
        if (this.mDividerColor == i2) {
            return;
        }
        this.mDividerColor = i2;
        invalidate();
    }

    public void setDividerHeight(float f2) {
        float fDip2px = DimensionUtil.dip2px(f2);
        if (fDip2px == this.mDividerHeight) {
            return;
        }
        this.mDividerHeight = fDip2px;
        invalidate();
    }

    public void setFriction(float f2) {
        this.mFriction = f2;
        this.mScroller.setFriction(f2);
    }

    public void setFroze(boolean z2) {
        this.mFroze = z2;
    }

    public void setIntegerFormat(String str) {
        if (TextUtils.isEmpty(str) || str.equals(this.mIntegerFormat)) {
            return;
        }
        this.mIntegerFormat = str;
        invalidate();
    }

    public void setNormalItemTextColor(@ColorInt int i2) {
        if (this.mNormalTextColor == i2) {
            return;
        }
        this.mNormalTextColor = i2;
        invalidate();
    }

    public void setNormalTextSize(float f2) {
        this.mNormalTextSize = f2;
        invalidate();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setSelectedItemTextColor(@ColorInt int i2) {
        if (this.mSelectedItemTextColor == i2) {
            return;
        }
        this.mSelectedItemTextColor = i2;
        invalidate();
    }

    public void setSelectedRectColor(@ColorInt int i2) {
        if (this.mSelectedRectColor == i2) {
            return;
        }
        this.mSelectedRectColor = i2;
        invalidate();
    }

    public void setSelectedTextSize(float f2) {
        float fSp2px = DimensionUtil.sp2px(f2);
        if (fSp2px == this.mSelectedItemTextSize) {
            return;
        }
        this.mSelectedItemTextSize = fSp2px;
        if (isAutoAdjustTextSize()) {
            requestLayout();
        }
        invalidate();
    }

    public void setSoundEffectResource(@RawRes int i2) {
        SoundPlayer soundPlayer = this.mSoundPlayer;
        if (soundPlayer != null) {
            soundPlayer.load(getContext(), i2);
        }
    }

    public void setSpringBackEffect(boolean z2) {
        this.mSpringBackEffect = z2;
    }

    public void setVisibleItemNum(int i2) {
        if (this.mVisibleItemNum == i2) {
            return;
        }
        this.mVisibleItemNum = adjustVisibleItemNum(i2);
        this.mScrollOffsetY = 0;
        invalidate();
    }

    public void stopScroll() {
        if (this.mScroller.isFinished()) {
            return;
        }
        this.mScroller.forceFinished(true);
        this.mSelectedItemPosition = (int) ((r0 / this.mItemHeight) + (this.mScrollOffsetY > 0 ? 0.5f : -0.5f));
        updateAfterStop();
    }

    public ILopWheelView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ILopWheelView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPaint = new Paint(1);
        this.mItemHeight = 1;
        this.mDataHolder = new DataHolder.EmptyHolder();
        this.mFriction = DEFAULT_FRICTION;
        this.mIsDragging = false;
        this.mIsFlingScroll = false;
        this.mFroze = false;
        this.mWheelLayers = new ArrayList();
        obtainAttrs(context, attributeSet);
        init(context);
    }
}

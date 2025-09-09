package com.yalantis.ucrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yalantis.ucrop.R;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.task.BitmapCropTask;
import com.yalantis.ucrop.util.CubicEasing;
import com.yalantis.ucrop.util.RectUtils;
import com.yalantis.ucrop.view.TransformImageView;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class CropImageView extends TransformImageView {
    public static final float DEFAULT_ASPECT_RATIO = 0.0f;
    public static final int DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION = 500;
    public static final int DEFAULT_MAX_BITMAP_SIZE = 0;
    public static final float DEFAULT_MAX_SCALE_MULTIPLIER = 10.0f;
    public static final float SOURCE_IMAGE_ASPECT_RATIO = 0.0f;
    private CropBoundsChangeListener mCropBoundsChangeListener;
    private final RectF mCropRect;
    private long mImageToWrapCropBoundsAnimDuration;
    private int mMaxResultImageSizeX;
    private int mMaxResultImageSizeY;
    private float mMaxScale;
    private float mMaxScaleMultiplier;
    private float mMinScale;
    private float mTargetAspectRatio;
    private final Matrix mTempMatrix;
    private Runnable mWrapCropBoundsRunnable;
    private Runnable mZoomImageToPositionRunnable;

    private static class WrapCropBoundsRunnable implements Runnable {
        private final float mCenterDiffX;
        private final float mCenterDiffY;
        private final WeakReference<CropImageView> mCropImageView;
        private final float mDeltaScale;
        private final long mDurationMs;
        private final float mOldScale;
        private final float mOldX;
        private final float mOldY;
        private final long mStartTime = System.currentTimeMillis();
        private final boolean mWillBeImageInBoundsAfterTranslate;

        public WrapCropBoundsRunnable(CropImageView cropImageView, long j2, float f2, float f3, float f4, float f5, float f6, float f7, boolean z2) {
            this.mCropImageView = new WeakReference<>(cropImageView);
            this.mDurationMs = j2;
            this.mOldX = f2;
            this.mOldY = f3;
            this.mCenterDiffX = f4;
            this.mCenterDiffY = f5;
            this.mOldScale = f6;
            this.mDeltaScale = f7;
            this.mWillBeImageInBoundsAfterTranslate = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            CropImageView cropImageView = this.mCropImageView.get();
            if (cropImageView == null) {
                return;
            }
            float fMin = Math.min(this.mDurationMs, System.currentTimeMillis() - this.mStartTime);
            float fEaseOut = CubicEasing.easeOut(fMin, 0.0f, this.mCenterDiffX, this.mDurationMs);
            float fEaseOut2 = CubicEasing.easeOut(fMin, 0.0f, this.mCenterDiffY, this.mDurationMs);
            float fEaseInOut = CubicEasing.easeInOut(fMin, 0.0f, this.mDeltaScale, this.mDurationMs);
            if (fMin < this.mDurationMs) {
                float[] fArr = cropImageView.f24672b;
                cropImageView.postTranslate(fEaseOut - (fArr[0] - this.mOldX), fEaseOut2 - (fArr[1] - this.mOldY));
                if (!this.mWillBeImageInBoundsAfterTranslate) {
                    cropImageView.zoomInImage(this.mOldScale + fEaseInOut, cropImageView.mCropRect.centerX(), cropImageView.mCropRect.centerY());
                }
                if (cropImageView.i()) {
                    return;
                }
                cropImageView.post(this);
            }
        }
    }

    private static class ZoomImageToPosition implements Runnable {
        private final WeakReference<CropImageView> mCropImageView;
        private final float mDeltaScale;
        private final float mDestX;
        private final float mDestY;
        private final long mDurationMs;
        private final float mOldScale;
        private final long mStartTime = System.currentTimeMillis();

        public ZoomImageToPosition(CropImageView cropImageView, long j2, float f2, float f3, float f4, float f5) {
            this.mCropImageView = new WeakReference<>(cropImageView);
            this.mDurationMs = j2;
            this.mOldScale = f2;
            this.mDeltaScale = f3;
            this.mDestX = f4;
            this.mDestY = f5;
        }

        @Override // java.lang.Runnable
        public void run() {
            CropImageView cropImageView = this.mCropImageView.get();
            if (cropImageView == null) {
                return;
            }
            float fMin = Math.min(this.mDurationMs, System.currentTimeMillis() - this.mStartTime);
            float fEaseInOut = CubicEasing.easeInOut(fMin, 0.0f, this.mDeltaScale, this.mDurationMs);
            if (fMin >= this.mDurationMs) {
                cropImageView.setImageToWrapCropBounds();
            } else {
                cropImageView.zoomInImage(this.mOldScale + fEaseInOut, this.mDestX, this.mDestY);
                cropImageView.post(this);
            }
        }
    }

    public CropImageView(Context context) {
        this(context, null);
    }

    private float[] calculateImageIndents() {
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(-getCurrentAngle());
        float[] fArr = this.f24671a;
        float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
        float[] cornersFromRect = RectUtils.getCornersFromRect(this.mCropRect);
        this.mTempMatrix.mapPoints(fArrCopyOf);
        this.mTempMatrix.mapPoints(cornersFromRect);
        RectF rectFTrapToRect = RectUtils.trapToRect(fArrCopyOf);
        RectF rectFTrapToRect2 = RectUtils.trapToRect(cornersFromRect);
        float f2 = rectFTrapToRect.left - rectFTrapToRect2.left;
        float f3 = rectFTrapToRect.top - rectFTrapToRect2.top;
        float f4 = rectFTrapToRect.right - rectFTrapToRect2.right;
        float f5 = rectFTrapToRect.bottom - rectFTrapToRect2.bottom;
        if (f2 <= 0.0f) {
            f2 = 0.0f;
        }
        if (f3 <= 0.0f) {
            f3 = 0.0f;
        }
        if (f4 >= 0.0f) {
            f4 = 0.0f;
        }
        if (f5 >= 0.0f) {
            f5 = 0.0f;
        }
        float[] fArr2 = {f2, f3, f4, f5};
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(getCurrentAngle());
        this.mTempMatrix.mapPoints(fArr2);
        return fArr2;
    }

    private void calculateImageScaleBounds() {
        if (getDrawable() == null) {
            return;
        }
        calculateImageScaleBounds(r0.getIntrinsicWidth(), r0.getIntrinsicHeight());
    }

    private void setupInitialImagePosition(float f2, float f3) {
        float fWidth = this.mCropRect.width();
        float fHeight = this.mCropRect.height();
        float fMax = Math.max(this.mCropRect.width() / f2, this.mCropRect.height() / f3);
        RectF rectF = this.mCropRect;
        float f4 = ((fWidth - (f2 * fMax)) / 2.0f) + rectF.left;
        float f5 = ((fHeight - (f3 * fMax)) / 2.0f) + rectF.top;
        this.f24673c.reset();
        this.f24673c.postScale(fMax, fMax);
        this.f24673c.postTranslate(f4, f5);
        setImageMatrix(this.f24673c);
    }

    public void cancelAllAnimations() {
        removeCallbacks(this.mWrapCropBoundsRunnable);
        removeCallbacks(this.mZoomImageToPositionRunnable);
    }

    public void cropAndSaveImage(@NonNull Bitmap.CompressFormat compressFormat, int i2, @Nullable BitmapCropCallback bitmapCropCallback) {
        cancelAllAnimations();
        setImageToWrapCropBounds(false);
        ImageState imageState = new ImageState(this.mCropRect, RectUtils.trapToRect(this.f24671a), getCurrentScale(), getCurrentAngle());
        CropParameters cropParameters = new CropParameters(this.mMaxResultImageSizeX, this.mMaxResultImageSizeY, compressFormat, i2, getImageInputPath(), getImageOutputPath(), getExifInfo());
        cropParameters.setContentImageInputUri(getImageInputUri());
        cropParameters.setContentImageOutputUri(getImageOutputUri());
        new BitmapCropTask(getContext(), getViewBitmap(), imageState, cropParameters, bitmapCropCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.yalantis.ucrop.view.TransformImageView
    protected void g() {
        super.g();
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        if (this.mTargetAspectRatio == 0.0f) {
            this.mTargetAspectRatio = intrinsicWidth / intrinsicHeight;
        }
        int i2 = this.f24674d;
        float f2 = this.mTargetAspectRatio;
        int i3 = (int) (i2 / f2);
        int i4 = this.f24675e;
        if (i3 > i4) {
            this.mCropRect.set((i2 - ((int) (i4 * f2))) / 2, 0.0f, r4 + r2, i4);
        } else {
            this.mCropRect.set(0.0f, (i4 - i3) / 2, i2, i3 + r6);
        }
        calculateImageScaleBounds(intrinsicWidth, intrinsicHeight);
        setupInitialImagePosition(intrinsicWidth, intrinsicHeight);
        CropBoundsChangeListener cropBoundsChangeListener = this.mCropBoundsChangeListener;
        if (cropBoundsChangeListener != null) {
            cropBoundsChangeListener.onCropAspectRatioChanged(this.mTargetAspectRatio);
        }
        TransformImageView.TransformImageListener transformImageListener = this.f24676f;
        if (transformImageListener != null) {
            transformImageListener.onScale(getCurrentScale());
            this.f24676f.onRotate(getCurrentAngle());
        }
    }

    @Nullable
    public CropBoundsChangeListener getCropBoundsChangeListener() {
        return this.mCropBoundsChangeListener;
    }

    public float getMaxScale() {
        return this.mMaxScale;
    }

    public float getMinScale() {
        return this.mMinScale;
    }

    public float getTargetAspectRatio() {
        return this.mTargetAspectRatio;
    }

    protected boolean i() {
        return j(this.f24671a);
    }

    protected boolean j(float[] fArr) {
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(-getCurrentAngle());
        float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
        this.mTempMatrix.mapPoints(fArrCopyOf);
        float[] cornersFromRect = RectUtils.getCornersFromRect(this.mCropRect);
        this.mTempMatrix.mapPoints(cornersFromRect);
        return RectUtils.trapToRect(fArrCopyOf).contains(RectUtils.trapToRect(cornersFromRect));
    }

    protected void k(TypedArray typedArray) {
        float fAbs = Math.abs(typedArray.getFloat(R.styleable.ucrop_UCropView_ucrop_aspect_ratio_x, 0.0f));
        float fAbs2 = Math.abs(typedArray.getFloat(R.styleable.ucrop_UCropView_ucrop_aspect_ratio_y, 0.0f));
        if (fAbs == 0.0f || fAbs2 == 0.0f) {
            this.mTargetAspectRatio = 0.0f;
        } else {
            this.mTargetAspectRatio = fAbs / fAbs2;
        }
    }

    protected void l(float f2, float f3, float f4, long j2) {
        if (f2 > getMaxScale()) {
            f2 = getMaxScale();
        }
        float currentScale = getCurrentScale();
        ZoomImageToPosition zoomImageToPosition = new ZoomImageToPosition(this, j2, currentScale, f2 - currentScale, f3, f4);
        this.mZoomImageToPositionRunnable = zoomImageToPosition;
        post(zoomImageToPosition);
    }

    public void postRotate(float f2) {
        postRotate(f2, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    @Override // com.yalantis.ucrop.view.TransformImageView
    public void postScale(float f2, float f3, float f4) {
        if (f2 > 1.0f && getCurrentScale() * f2 <= getMaxScale()) {
            super.postScale(f2, f3, f4);
        } else {
            if (f2 >= 1.0f || getCurrentScale() * f2 < getMinScale()) {
                return;
            }
            super.postScale(f2, f3, f4);
        }
    }

    public void setCropBoundsChangeListener(@Nullable CropBoundsChangeListener cropBoundsChangeListener) {
        this.mCropBoundsChangeListener = cropBoundsChangeListener;
    }

    public void setCropRect(RectF rectF) {
        this.mTargetAspectRatio = rectF.width() / rectF.height();
        this.mCropRect.set(rectF.left - getPaddingLeft(), rectF.top - getPaddingTop(), rectF.right - getPaddingRight(), rectF.bottom - getPaddingBottom());
        calculateImageScaleBounds();
        setImageToWrapCropBounds();
    }

    public void setImageToWrapCropBounds() {
        setImageToWrapCropBounds(true);
    }

    public void setImageToWrapCropBoundsAnimDuration(@IntRange(from = 100) long j2) {
        if (j2 <= 0) {
            throw new IllegalArgumentException("Animation duration cannot be negative value.");
        }
        this.mImageToWrapCropBoundsAnimDuration = j2;
    }

    public void setMaxResultImageSizeX(@IntRange(from = 10) int i2) {
        this.mMaxResultImageSizeX = i2;
    }

    public void setMaxResultImageSizeY(@IntRange(from = 10) int i2) {
        this.mMaxResultImageSizeY = i2;
    }

    public void setMaxScaleMultiplier(float f2) {
        this.mMaxScaleMultiplier = f2;
    }

    public void setTargetAspectRatio(float f2) {
        if (getDrawable() == null) {
            this.mTargetAspectRatio = f2;
            return;
        }
        if (f2 == 0.0f) {
            this.mTargetAspectRatio = r0.getIntrinsicWidth() / r0.getIntrinsicHeight();
        } else {
            this.mTargetAspectRatio = f2;
        }
        CropBoundsChangeListener cropBoundsChangeListener = this.mCropBoundsChangeListener;
        if (cropBoundsChangeListener != null) {
            cropBoundsChangeListener.onCropAspectRatioChanged(this.mTargetAspectRatio);
        }
    }

    public void zoomInImage(float f2) {
        zoomInImage(f2, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    public void zoomOutImage(float f2) {
        zoomOutImage(f2, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setImageToWrapCropBounds(boolean z2) {
        float f2;
        float fMax;
        float f3;
        if (!this.f24678h || i()) {
            return;
        }
        float[] fArr = this.f24672b;
        float f4 = fArr[0];
        float f5 = fArr[1];
        float currentScale = getCurrentScale();
        float fCenterX = this.mCropRect.centerX() - f4;
        float fCenterY = this.mCropRect.centerY() - f5;
        this.mTempMatrix.reset();
        this.mTempMatrix.setTranslate(fCenterX, fCenterY);
        float[] fArr2 = this.f24671a;
        float[] fArrCopyOf = Arrays.copyOf(fArr2, fArr2.length);
        this.mTempMatrix.mapPoints(fArrCopyOf);
        boolean zJ = j(fArrCopyOf);
        if (zJ) {
            float[] fArrCalculateImageIndents = calculateImageIndents();
            float f6 = -(fArrCalculateImageIndents[0] + fArrCalculateImageIndents[2]);
            f3 = -(fArrCalculateImageIndents[1] + fArrCalculateImageIndents[3]);
            f2 = f6;
            fMax = 0.0f;
        } else {
            RectF rectF = new RectF(this.mCropRect);
            this.mTempMatrix.reset();
            this.mTempMatrix.setRotate(getCurrentAngle());
            this.mTempMatrix.mapRect(rectF);
            float[] rectSidesFromCorners = RectUtils.getRectSidesFromCorners(this.f24671a);
            f2 = fCenterX;
            fMax = (Math.max(rectF.width() / rectSidesFromCorners[0], rectF.height() / rectSidesFromCorners[1]) * currentScale) - currentScale;
            f3 = fCenterY;
        }
        if (z2) {
            WrapCropBoundsRunnable wrapCropBoundsRunnable = new WrapCropBoundsRunnable(this, this.mImageToWrapCropBoundsAnimDuration, f4, f5, f2, f3, currentScale, fMax, zJ);
            this.mWrapCropBoundsRunnable = wrapCropBoundsRunnable;
            post(wrapCropBoundsRunnable);
        } else {
            postTranslate(f2, f3);
            if (zJ) {
                return;
            }
            zoomInImage(currentScale + fMax, this.mCropRect.centerX(), this.mCropRect.centerY());
        }
    }

    public void zoomInImage(float f2, float f3, float f4) {
        if (f2 <= getMaxScale()) {
            postScale(f2 / getCurrentScale(), f3, f4);
        }
    }

    public void zoomOutImage(float f2, float f3, float f4) {
        if (f2 >= getMinScale()) {
            postScale(f2 / getCurrentScale(), f3, f4);
        }
    }

    public CropImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mCropRect = new RectF();
        this.mTempMatrix = new Matrix();
        this.mMaxScaleMultiplier = 10.0f;
        this.mZoomImageToPositionRunnable = null;
        this.mMaxResultImageSizeX = 0;
        this.mMaxResultImageSizeY = 0;
        this.mImageToWrapCropBoundsAnimDuration = 500L;
    }

    private void calculateImageScaleBounds(float f2, float f3) {
        float fMin = Math.min(Math.min(this.mCropRect.width() / f2, this.mCropRect.width() / f3), Math.min(this.mCropRect.height() / f3, this.mCropRect.height() / f2));
        this.mMinScale = fMin;
        this.mMaxScale = fMin * this.mMaxScaleMultiplier;
    }
}

package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class ImageFilterView extends AppCompatImageView {

    /* renamed from: a, reason: collision with root package name */
    ViewOutlineProvider f3170a;

    /* renamed from: b, reason: collision with root package name */
    RectF f3171b;

    /* renamed from: c, reason: collision with root package name */
    Drawable[] f3172c;

    /* renamed from: d, reason: collision with root package name */
    LayerDrawable f3173d;

    /* renamed from: e, reason: collision with root package name */
    float f3174e;

    /* renamed from: f, reason: collision with root package name */
    float f3175f;

    /* renamed from: g, reason: collision with root package name */
    float f3176g;

    /* renamed from: h, reason: collision with root package name */
    float f3177h;
    private Drawable mAltDrawable;
    private float mCrossfade;
    private Drawable mDrawable;
    private ImageMatrix mImageMatrix;
    private boolean mOverlay;
    private Path mPath;
    private float mRound;
    private float mRoundPercent;

    static class ImageMatrix {

        /* renamed from: a, reason: collision with root package name */
        float[] f3180a = new float[20];

        /* renamed from: b, reason: collision with root package name */
        ColorMatrix f3181b = new ColorMatrix();

        /* renamed from: c, reason: collision with root package name */
        ColorMatrix f3182c = new ColorMatrix();

        /* renamed from: d, reason: collision with root package name */
        float f3183d = 1.0f;

        /* renamed from: e, reason: collision with root package name */
        float f3184e = 1.0f;

        /* renamed from: f, reason: collision with root package name */
        float f3185f = 1.0f;

        /* renamed from: g, reason: collision with root package name */
        float f3186g = 1.0f;

        ImageMatrix() {
        }

        private void brightness(float f2) {
            float[] fArr = this.f3180a;
            fArr[0] = f2;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = f2;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = f2;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void saturation(float f2) {
            float f3 = 1.0f - f2;
            float f4 = 0.2999f * f3;
            float f5 = 0.587f * f3;
            float f6 = f3 * 0.114f;
            float[] fArr = this.f3180a;
            fArr[0] = f4 + f2;
            fArr[1] = f5;
            fArr[2] = f6;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = f4;
            fArr[6] = f5 + f2;
            fArr[7] = f6;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = f4;
            fArr[11] = f5;
            fArr[12] = f6 + f2;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void warmth(float f2) {
            float fLog;
            float fPow;
            if (f2 <= 0.0f) {
                f2 = 0.01f;
            }
            float f3 = (5000.0f / f2) / 100.0f;
            if (f3 > 66.0f) {
                double d2 = f3 - 60.0f;
                fPow = ((float) Math.pow(d2, -0.13320475816726685d)) * 329.69873f;
                fLog = ((float) Math.pow(d2, 0.07551484555006027d)) * 288.12216f;
            } else {
                fLog = (((float) Math.log(f3)) * 99.4708f) - 161.11957f;
                fPow = 255.0f;
            }
            float fLog2 = f3 < 66.0f ? f3 > 19.0f ? (((float) Math.log(f3 - 10.0f)) * 138.51773f) - 305.0448f : 0.0f : 255.0f;
            float fMin = Math.min(255.0f, Math.max(fPow, 0.0f));
            float fMin2 = Math.min(255.0f, Math.max(fLog, 0.0f));
            float fMin3 = Math.min(255.0f, Math.max(fLog2, 0.0f));
            float fLog3 = (((float) Math.log(50.0f)) * 99.4708f) - 161.11957f;
            float fLog4 = (((float) Math.log(40.0f)) * 138.51773f) - 305.0448f;
            float fMin4 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            float fMin5 = Math.min(255.0f, Math.max(fLog3, 0.0f));
            float fMin6 = fMin3 / Math.min(255.0f, Math.max(fLog4, 0.0f));
            float[] fArr = this.f3180a;
            fArr[0] = fMin / fMin4;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = fMin2 / fMin5;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = fMin6;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        void a(ImageView imageView) {
            boolean z2;
            this.f3181b.reset();
            float f2 = this.f3184e;
            boolean z3 = true;
            if (f2 != 1.0f) {
                saturation(f2);
                this.f3181b.set(this.f3180a);
                z2 = true;
            } else {
                z2 = false;
            }
            float f3 = this.f3185f;
            if (f3 != 1.0f) {
                this.f3182c.setScale(f3, f3, f3, 1.0f);
                this.f3181b.postConcat(this.f3182c);
                z2 = true;
            }
            float f4 = this.f3186g;
            if (f4 != 1.0f) {
                warmth(f4);
                this.f3182c.set(this.f3180a);
                this.f3181b.postConcat(this.f3182c);
                z2 = true;
            }
            float f5 = this.f3183d;
            if (f5 != 1.0f) {
                brightness(f5);
                this.f3182c.set(this.f3180a);
                this.f3181b.postConcat(this.f3182c);
            } else {
                z3 = z2;
            }
            if (z3) {
                imageView.setColorFilter(new ColorMatrixColorFilter(this.f3181b));
            } else {
                imageView.clearColorFilter();
            }
        }
    }

    public ImageFilterView(Context context) {
        super(context);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.f3172c = new Drawable[2];
        this.f3174e = Float.NaN;
        this.f3175f = Float.NaN;
        this.f3176g = Float.NaN;
        this.f3177h = Float.NaN;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ImageFilterView);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            this.mAltDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ImageFilterView_altSrc);
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ImageFilterView_crossfade) {
                    this.mCrossfade = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                } else if (index == R.styleable.ImageFilterView_warmth) {
                    setWarmth(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_saturation) {
                    setSaturation(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_contrast) {
                    setContrast(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_brightness) {
                    setBrightness(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_round) {
                    setRound(typedArrayObtainStyledAttributes.getDimension(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_roundPercent) {
                    setRoundPercent(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_overlay) {
                    setOverlay(typedArrayObtainStyledAttributes.getBoolean(index, this.mOverlay));
                } else if (index == R.styleable.ImageFilterView_imagePanX) {
                    setImagePanX(typedArrayObtainStyledAttributes.getFloat(index, this.f3174e));
                } else if (index == R.styleable.ImageFilterView_imagePanY) {
                    setImagePanY(typedArrayObtainStyledAttributes.getFloat(index, this.f3175f));
                } else if (index == R.styleable.ImageFilterView_imageRotate) {
                    setImageRotate(typedArrayObtainStyledAttributes.getFloat(index, this.f3177h));
                } else if (index == R.styleable.ImageFilterView_imageZoom) {
                    setImageZoom(typedArrayObtainStyledAttributes.getFloat(index, this.f3176g));
                }
            }
            typedArrayObtainStyledAttributes.recycle();
            Drawable drawable = getDrawable();
            this.mDrawable = drawable;
            if (this.mAltDrawable == null || drawable == null) {
                Drawable drawable2 = getDrawable();
                this.mDrawable = drawable2;
                if (drawable2 != null) {
                    Drawable[] drawableArr = this.f3172c;
                    Drawable drawableMutate = drawable2.mutate();
                    this.mDrawable = drawableMutate;
                    drawableArr[0] = drawableMutate;
                    return;
                }
                return;
            }
            Drawable[] drawableArr2 = this.f3172c;
            Drawable drawableMutate2 = getDrawable().mutate();
            this.mDrawable = drawableMutate2;
            drawableArr2[0] = drawableMutate2;
            this.f3172c[1] = this.mAltDrawable.mutate();
            LayerDrawable layerDrawable = new LayerDrawable(this.f3172c);
            this.f3173d = layerDrawable;
            layerDrawable.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            if (!this.mOverlay) {
                this.f3173d.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            super.setImageDrawable(this.f3173d);
        }
    }

    private void setMatrix() {
        if (Float.isNaN(this.f3174e) && Float.isNaN(this.f3175f) && Float.isNaN(this.f3176g) && Float.isNaN(this.f3177h)) {
            return;
        }
        float f2 = Float.isNaN(this.f3174e) ? 0.0f : this.f3174e;
        float f3 = Float.isNaN(this.f3175f) ? 0.0f : this.f3175f;
        float f4 = Float.isNaN(this.f3176g) ? 1.0f : this.f3176g;
        float f5 = Float.isNaN(this.f3177h) ? 0.0f : this.f3177h;
        Matrix matrix = new Matrix();
        matrix.reset();
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float width = getWidth();
        float height = getHeight();
        float f6 = f4 * (intrinsicWidth * height < intrinsicHeight * width ? width / intrinsicWidth : height / intrinsicHeight);
        matrix.postScale(f6, f6);
        float f7 = intrinsicWidth * f6;
        float f8 = f6 * intrinsicHeight;
        matrix.postTranslate((((f2 * (width - f7)) + width) - f7) * 0.5f, (((f3 * (height - f8)) + height) - f8) * 0.5f);
        matrix.postRotate(f5, width / 2.0f, height / 2.0f);
        setImageMatrix(matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void setOverlay(boolean z2) {
        this.mOverlay = z2;
    }

    private void updateViewMatrix() {
        if (Float.isNaN(this.f3174e) && Float.isNaN(this.f3175f) && Float.isNaN(this.f3176g) && Float.isNaN(this.f3177h)) {
            setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            setMatrix();
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public float getBrightness() {
        return this.mImageMatrix.f3183d;
    }

    public float getContrast() {
        return this.mImageMatrix.f3185f;
    }

    public float getCrossfade() {
        return this.mCrossfade;
    }

    public float getImagePanX() {
        return this.f3174e;
    }

    public float getImagePanY() {
        return this.f3175f;
    }

    public float getImageRotate() {
        return this.f3177h;
    }

    public float getImageZoom() {
        return this.f3176g;
    }

    public float getRound() {
        return this.mRound;
    }

    public float getRoundPercent() {
        return this.mRoundPercent;
    }

    public float getSaturation() {
        return this.mImageMatrix.f3184e;
    }

    public float getWarmth() {
        return this.mImageMatrix.f3186g;
    }

    @Override // android.view.View
    public void layout(int i2, int i3, int i4, int i5) {
        super.layout(i2, i3, i4, i5);
        setMatrix();
    }

    public void setAltImageResource(int i2) {
        Drawable drawableMutate = AppCompatResources.getDrawable(getContext(), i2).mutate();
        this.mAltDrawable = drawableMutate;
        Drawable[] drawableArr = this.f3172c;
        drawableArr[0] = this.mDrawable;
        drawableArr[1] = drawableMutate;
        LayerDrawable layerDrawable = new LayerDrawable(this.f3172c);
        this.f3173d = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setBrightness(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.f3183d = f2;
        imageMatrix.a(this);
    }

    public void setContrast(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.f3185f = f2;
        imageMatrix.a(this);
    }

    public void setCrossfade(float f2) {
        this.mCrossfade = f2;
        if (this.f3172c != null) {
            if (!this.mOverlay) {
                this.f3173d.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            this.f3173d.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            super.setImageDrawable(this.f3173d);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (this.mAltDrawable == null || drawable == null) {
            super.setImageDrawable(drawable);
            return;
        }
        Drawable drawableMutate = drawable.mutate();
        this.mDrawable = drawableMutate;
        Drawable[] drawableArr = this.f3172c;
        drawableArr[0] = drawableMutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.f3172c);
        this.f3173d = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setImagePanX(float f2) {
        this.f3174e = f2;
        updateViewMatrix();
    }

    public void setImagePanY(float f2) {
        this.f3175f = f2;
        updateViewMatrix();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i2) {
        if (this.mAltDrawable == null) {
            super.setImageResource(i2);
            return;
        }
        Drawable drawableMutate = AppCompatResources.getDrawable(getContext(), i2).mutate();
        this.mDrawable = drawableMutate;
        Drawable[] drawableArr = this.f3172c;
        drawableArr[0] = drawableMutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.f3172c);
        this.f3173d = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setImageRotate(float f2) {
        this.f3177h = f2;
        updateViewMatrix();
    }

    public void setImageZoom(float f2) {
        this.f3176g = f2;
        updateViewMatrix();
    }

    @RequiresApi(21)
    public void setRound(float f2) {
        if (Float.isNaN(f2)) {
            this.mRound = f2;
            float f3 = this.mRoundPercent;
            this.mRoundPercent = -1.0f;
            setRoundPercent(f3);
            return;
        }
        boolean z2 = this.mRound != f2;
        this.mRound = f2;
        if (f2 != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.f3171b == null) {
                this.f3171b = new RectF();
            }
            if (this.f3170a == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.2
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
                    }
                };
                this.f3170a = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            this.f3171b.set(0.0f, 0.0f, getWidth(), getHeight());
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.f3171b;
            float f4 = this.mRound;
            path.addRoundRect(rectF, f4, f4, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    @RequiresApi(21)
    public void setRoundPercent(float f2) {
        boolean z2 = this.mRoundPercent != f2;
        this.mRoundPercent = f2;
        if (f2 != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.f3171b == null) {
                this.f3171b = new RectF();
            }
            if (this.f3170a == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), (Math.min(r3, r4) * ImageFilterView.this.mRoundPercent) / 2.0f);
                    }
                };
                this.f3170a = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            int width = getWidth();
            int height = getHeight();
            float fMin = (Math.min(width, height) * this.mRoundPercent) / 2.0f;
            this.f3171b.set(0.0f, 0.0f, width, height);
            this.mPath.reset();
            this.mPath.addRoundRect(this.f3171b, fMin, fMin, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    public void setSaturation(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.f3184e = f2;
        imageMatrix.a(this);
    }

    public void setWarmth(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.f3186g = f2;
        imageMatrix.a(this);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.f3172c = new Drawable[2];
        this.f3174e = Float.NaN;
        this.f3175f = Float.NaN;
        this.f3176g = Float.NaN;
        this.f3177h = Float.NaN;
        init(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.f3172c = new Drawable[2];
        this.f3174e = Float.NaN;
        this.f3175f = Float.NaN;
        this.f3176g = Float.NaN;
        this.f3177h = Float.NaN;
        init(context, attributeSet);
    }
}

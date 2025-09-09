package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;

    /* renamed from: a, reason: collision with root package name */
    final Bitmap f3576a;
    private int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    private boolean mIsCircular;
    private int mTargetDensity;
    private int mGravity = 119;
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();

    /* renamed from: b, reason: collision with root package name */
    final Rect f3577b = new Rect();
    private final RectF mDstRectF = new RectF();
    private boolean mApplyGravity = true;

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.mTargetDensity = 160;
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.f3576a = bitmap;
        if (bitmap != null) {
            computeBitmapSize();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mBitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
            this.mBitmapShader = null;
        }
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.f3576a.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.f3576a.getScaledHeight(this.mTargetDensity);
    }

    private static boolean isGreaterThanZero(float f2) {
        return f2 > 0.05f;
    }

    private void updateCircularCornerRadius() {
        this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2;
    }

    void a(int i2, int i3, int i4, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    void b() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int iMin = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                a(this.mGravity, iMin, iMin, getBounds(), this.f3577b);
                int iMin2 = Math.min(this.f3577b.width(), this.f3577b.height());
                this.f3577b.inset(Math.max(0, (this.f3577b.width() - iMin2) / 2), Math.max(0, (this.f3577b.height() - iMin2) / 2));
                this.mCornerRadius = iMin2 * 0.5f;
            } else {
                a(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.f3577b);
            }
            this.mDstRectF.set(this.f3577b);
            if (this.mBitmapShader != null) {
                Matrix matrix = this.mShaderMatrix;
                RectF rectF = this.mDstRectF;
                matrix.setTranslate(rectF.left, rectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / this.f3576a.getWidth(), this.mDstRectF.height() / this.f3576a.getHeight());
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.f3576a;
        if (bitmap == null) {
            return;
        }
        b();
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.f3577b, this.mPaint);
            return;
        }
        RectF rectF = this.mDstRectF;
        float f2 = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.f3576a;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getGravity() {
        return this.mGravity;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        return (this.mGravity != 119 || this.mIsCircular || (bitmap = this.f3576a) == null || bitmap.hasAlpha() || this.mPaint.getAlpha() < 255 || isGreaterThanZero(this.mCornerRadius)) ? -3 : -1;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public boolean isCircular() {
        return this.mIsCircular;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        if (i2 != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i2);
            invalidateSelf();
        }
    }

    public void setAntiAlias(boolean z2) {
        this.mPaint.setAntiAlias(z2);
        invalidateSelf();
    }

    public void setCircular(boolean z2) {
        this.mIsCircular = z2;
        this.mApplyGravity = true;
        if (!z2) {
            setCornerRadius(0.0f);
            return;
        }
        updateCircularCornerRadius();
        this.mPaint.setShader(this.mBitmapShader);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setCornerRadius(float f2) {
        if (this.mCornerRadius == f2) {
            return;
        }
        this.mIsCircular = false;
        if (isGreaterThanZero(f2)) {
            this.mPaint.setShader(this.mBitmapShader);
        } else {
            this.mPaint.setShader(null);
        }
        this.mCornerRadius = f2;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z2) {
        this.mPaint.setDither(z2);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z2) {
        this.mPaint.setFilterBitmap(z2);
        invalidateSelf();
    }

    public void setGravity(int i2) {
        if (this.mGravity != i2) {
            this.mGravity = i2;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z2) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i2) {
        if (this.mTargetDensity != i2) {
            if (i2 == 0) {
                i2 = 160;
            }
            this.mTargetDensity = i2;
            if (this.f3576a != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }
}

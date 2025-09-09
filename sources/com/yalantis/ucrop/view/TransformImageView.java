package com.yalantis.ucrop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FastBitmapDrawable;
import com.yalantis.ucrop.util.RectUtils;

/* loaded from: classes4.dex */
public class TransformImageView extends AppCompatImageView {
    private static final int MATRIX_VALUES_COUNT = 9;
    private static final int RECT_CENTER_POINT_COORDS = 2;
    private static final int RECT_CORNER_POINTS_COORDS = 8;
    private static final String TAG = "TransformImageView";

    /* renamed from: a, reason: collision with root package name */
    protected final float[] f24671a;

    /* renamed from: b, reason: collision with root package name */
    protected final float[] f24672b;

    /* renamed from: c, reason: collision with root package name */
    protected Matrix f24673c;

    /* renamed from: d, reason: collision with root package name */
    protected int f24674d;

    /* renamed from: e, reason: collision with root package name */
    protected int f24675e;

    /* renamed from: f, reason: collision with root package name */
    protected TransformImageListener f24676f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f24677g;

    /* renamed from: h, reason: collision with root package name */
    protected boolean f24678h;
    private ExifInfo mExifInfo;
    private String mImageInputPath;
    private Uri mImageInputUri;
    private String mImageOutputPath;
    private Uri mImageOutputUri;
    private float[] mInitialImageCenter;
    private float[] mInitialImageCorners;
    private final float[] mMatrixValues;
    private int mMaxBitmapSize;

    public interface TransformImageListener {
        void onLoadComplete();

        void onLoadFailure(@NonNull Exception exc);

        void onRotate(float f2);

        void onScale(float f2);
    }

    public TransformImageView(Context context) {
        this(context, null);
    }

    private void updateCurrentImagePoints() {
        this.f24673c.mapPoints(this.f24671a, this.mInitialImageCorners);
        this.f24673c.mapPoints(this.f24672b, this.mInitialImageCenter);
    }

    protected float f(Matrix matrix, int i2) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i2];
    }

    protected void g() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        Log.d(TAG, String.format("Image size: [%d:%d]", Integer.valueOf((int) intrinsicWidth), Integer.valueOf((int) intrinsicHeight)));
        RectF rectF = new RectF(0.0f, 0.0f, intrinsicWidth, intrinsicHeight);
        this.mInitialImageCorners = RectUtils.getCornersFromRect(rectF);
        this.mInitialImageCenter = RectUtils.getCenterFromRect(rectF);
        this.f24678h = true;
        TransformImageListener transformImageListener = this.f24676f;
        if (transformImageListener != null) {
            transformImageListener.onLoadComplete();
        }
    }

    public float getCurrentAngle() {
        return getMatrixAngle(this.f24673c);
    }

    public float getCurrentScale() {
        return getMatrixScale(this.f24673c);
    }

    public ExifInfo getExifInfo() {
        return this.mExifInfo;
    }

    public String getImageInputPath() {
        return this.mImageInputPath;
    }

    public Uri getImageInputUri() {
        return this.mImageInputUri;
    }

    public String getImageOutputPath() {
        return this.mImageOutputPath;
    }

    public Uri getImageOutputUri() {
        return this.mImageOutputUri;
    }

    public float getMatrixAngle(@NonNull Matrix matrix) {
        return (float) (-(Math.atan2(f(matrix, 1), f(matrix, 0)) * 57.29577951308232d));
    }

    public float getMatrixScale(@NonNull Matrix matrix) {
        return (float) Math.sqrt(Math.pow(f(matrix, 0), 2.0d) + Math.pow(f(matrix, 3), 2.0d));
    }

    public int getMaxBitmapSize() {
        if (this.mMaxBitmapSize <= 0) {
            this.mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(getContext());
        }
        return this.mMaxBitmapSize;
    }

    @Nullable
    public Bitmap getViewBitmap() {
        if (getDrawable() == null || !(getDrawable() instanceof FastBitmapDrawable)) {
            return null;
        }
        return ((FastBitmapDrawable) getDrawable()).getBitmap();
    }

    protected void init() {
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    @Override // android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (z2 || (this.f24677g && !this.f24678h)) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            this.f24674d = width - paddingLeft;
            this.f24675e = height - paddingTop;
            g();
        }
    }

    public void postRotate(float f2, float f3, float f4) {
        if (f2 != 0.0f) {
            this.f24673c.postRotate(f2, f3, f4);
            setImageMatrix(this.f24673c);
            TransformImageListener transformImageListener = this.f24676f;
            if (transformImageListener != null) {
                transformImageListener.onRotate(getMatrixAngle(this.f24673c));
            }
        }
    }

    public void postScale(float f2, float f3, float f4) {
        if (f2 != 0.0f) {
            this.f24673c.postScale(f2, f2, f3, f4);
            setImageMatrix(this.f24673c);
            TransformImageListener transformImageListener = this.f24676f;
            if (transformImageListener != null) {
                transformImageListener.onScale(getMatrixScale(this.f24673c));
            }
        }
    }

    public void postTranslate(float f2, float f3) {
        if (f2 == 0.0f && f3 == 0.0f) {
            return;
        }
        this.f24673c.postTranslate(f2, f3);
        setImageMatrix(this.f24673c);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        setImageDrawable(new FastBitmapDrawable(bitmap));
    }

    @Override // android.widget.ImageView
    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        this.f24673c.set(matrix);
        updateCurrentImagePoints();
    }

    public void setImageUri(@NonNull Uri uri, @Nullable Uri uri2) throws Exception {
        int maxBitmapSize = getMaxBitmapSize();
        BitmapLoadUtils.decodeBitmapInBackground(getContext(), uri, uri2, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() { // from class: com.yalantis.ucrop.view.TransformImageView.1
            @Override // com.yalantis.ucrop.callback.BitmapLoadCallback
            public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull Uri uri3, @Nullable Uri uri4) {
                TransformImageView.this.mImageInputUri = uri3;
                TransformImageView.this.mImageOutputUri = uri4;
                TransformImageView.this.mImageInputPath = uri3.getPath();
                TransformImageView.this.mImageOutputPath = uri4 != null ? uri4.getPath() : null;
                TransformImageView.this.mExifInfo = exifInfo;
                TransformImageView transformImageView = TransformImageView.this;
                transformImageView.f24677g = true;
                transformImageView.setImageBitmap(bitmap);
            }

            @Override // com.yalantis.ucrop.callback.BitmapLoadCallback
            public void onFailure(@NonNull Exception exc) {
                Log.e(TransformImageView.TAG, "onFailure: setImageUri", exc);
                TransformImageListener transformImageListener = TransformImageView.this.f24676f;
                if (transformImageListener != null) {
                    transformImageListener.onLoadFailure(exc);
                }
            }
        });
    }

    public void setMaxBitmapSize(int i2) {
        this.mMaxBitmapSize = i2;
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.MATRIX) {
            super.setScaleType(scaleType);
        } else {
            Log.w(TAG, "Invalid ScaleType. Only ScaleType.MATRIX can be used");
        }
    }

    public void setTransformImageListener(TransformImageListener transformImageListener) {
        this.f24676f = transformImageListener;
    }

    public TransformImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TransformImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f24671a = new float[8];
        this.f24672b = new float[2];
        this.mMatrixValues = new float[9];
        this.f24673c = new Matrix();
        this.f24677g = false;
        this.f24678h = false;
        this.mMaxBitmapSize = 0;
        init();
    }
}

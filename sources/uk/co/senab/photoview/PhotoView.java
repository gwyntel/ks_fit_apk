package uk.co.senab.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

/* loaded from: classes5.dex */
public class PhotoView extends ImageView implements IPhotoView {
    private PhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    protected void a() {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher == null || photoViewAttacher.getImageView() == null) {
            this.mAttacher = new PhotoViewAttacher(this);
        }
        ImageView.ScaleType scaleType = this.mPendingScaleType;
        if (scaleType != null) {
            setScaleType(scaleType);
            this.mPendingScaleType = null;
        }
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public Matrix getDisplayMatrix() {
        return this.mAttacher.getDisplayMatrix();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public IPhotoView getIPhotoViewImplementation() {
        return this.mAttacher;
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public float getMaxScale() {
        return getMaximumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public float getMidScale() {
        return getMediumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public float getMinScale() {
        return getMinimumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public float getMinimumScale() {
        return this.mAttacher.getMinimumScale();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public float getScale() {
        return this.mAttacher.getScale();
    }

    @Override // android.widget.ImageView, uk.co.senab.photoview.IPhotoView
    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public Bitmap getVisibleRectangleBitmap() {
        return this.mAttacher.getVisibleRectangleBitmap();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        a();
        super.onAttachedToWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setAllowParentInterceptOnEdge(boolean z2) {
        this.mAttacher.setAllowParentInterceptOnEdge(z2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public boolean setDisplayMatrix(Matrix matrix) {
        return this.mAttacher.setDisplayMatrix(matrix);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i2) {
        super.setImageResource(i2);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.update();
        }
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public void setMaxScale(float f2) {
        setMaximumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setMaximumScale(float f2) {
        this.mAttacher.setMaximumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setMediumScale(float f2) {
        this.mAttacher.setMediumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public void setMidScale(float f2) {
        setMediumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    @Deprecated
    public void setMinScale(float f2) {
        setMinimumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setMinimumScale(float f2) {
        this.mAttacher.setMinimumScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mAttacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    @Override // android.view.View, uk.co.senab.photoview.IPhotoView
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mAttacher.setOnLongClickListener(onLongClickListener);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener onMatrixChangedListener) {
        this.mAttacher.setOnMatrixChangeListener(onMatrixChangedListener);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener) {
        this.mAttacher.setOnPhotoTapListener(onPhotoTapListener);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener onScaleChangeListener) {
        this.mAttacher.setOnScaleChangeListener(onScaleChangeListener);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener onViewTapListener) {
        this.mAttacher.setOnViewTapListener(onViewTapListener);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setPhotoViewRotation(float f2) {
        this.mAttacher.setRotationTo(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setRotationBy(float f2) {
        this.mAttacher.setRotationBy(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setRotationTo(float f2) {
        this.mAttacher.setRotationTo(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setScale(float f2) {
        this.mAttacher.setScale(f2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setScaleLevels(float f2, float f3, float f4) {
        this.mAttacher.setScaleLevels(f2, f3, f4);
    }

    @Override // android.widget.ImageView, uk.co.senab.photoview.IPhotoView
    public void setScaleType(ImageView.ScaleType scaleType) {
        PhotoViewAttacher photoViewAttacher = this.mAttacher;
        if (photoViewAttacher != null) {
            photoViewAttacher.setScaleType(scaleType);
        } else {
            this.mPendingScaleType = scaleType;
        }
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setZoomTransitionDuration(int i2) {
        this.mAttacher.setZoomTransitionDuration(i2);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setZoomable(boolean z2) {
        this.mAttacher.setZoomable(z2);
    }

    public PhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setScale(float f2, boolean z2) {
        this.mAttacher.setScale(f2, z2);
    }

    public PhotoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        a();
    }

    @Override // uk.co.senab.photoview.IPhotoView
    public void setScale(float f2, float f3, float f4, boolean z2) {
        this.mAttacher.setScale(f2, f3, f4, z2);
    }
}

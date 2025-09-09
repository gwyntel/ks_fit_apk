package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class Layer extends ConstraintHelper {
    private static final String TAG = "Layer";

    /* renamed from: i, reason: collision with root package name */
    ConstraintLayout f2969i;

    /* renamed from: j, reason: collision with root package name */
    protected float f2970j;

    /* renamed from: k, reason: collision with root package name */
    protected float f2971k;

    /* renamed from: l, reason: collision with root package name */
    protected float f2972l;

    /* renamed from: m, reason: collision with root package name */
    protected float f2973m;
    private boolean mApplyElevationOnAttach;
    private boolean mApplyVisibilityOnAttach;
    private float mGroupRotateAngle;
    private float mRotationCenterX;
    private float mRotationCenterY;
    private float mScaleX;
    private float mScaleY;
    private float mShiftX;
    private float mShiftY;

    /* renamed from: n, reason: collision with root package name */
    protected float f2974n;

    /* renamed from: o, reason: collision with root package name */
    protected float f2975o;

    /* renamed from: p, reason: collision with root package name */
    boolean f2976p;

    /* renamed from: q, reason: collision with root package name */
    View[] f2977q;

    public Layer(Context context) {
        super(context);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.f2970j = Float.NaN;
        this.f2971k = Float.NaN;
        this.f2972l = Float.NaN;
        this.f2973m = Float.NaN;
        this.f2974n = Float.NaN;
        this.f2975o = Float.NaN;
        this.f2976p = true;
        this.f2977q = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }

    private void reCacheViews() {
        int i2;
        if (this.f2969i == null || (i2 = this.f3219b) == 0) {
            return;
        }
        View[] viewArr = this.f2977q;
        if (viewArr == null || viewArr.length != i2) {
            this.f2977q = new View[i2];
        }
        for (int i3 = 0; i3 < this.f3219b; i3++) {
            this.f2977q[i3] = this.f2969i.getViewById(this.f3218a[i3]);
        }
    }

    private void transform() {
        if (this.f2969i == null) {
            return;
        }
        if (this.f2977q == null) {
            reCacheViews();
        }
        f();
        double radians = Float.isNaN(this.mGroupRotateAngle) ? 0.0d : Math.toRadians(this.mGroupRotateAngle);
        float fSin = (float) Math.sin(radians);
        float fCos = (float) Math.cos(radians);
        float f2 = this.mScaleX;
        float f3 = f2 * fCos;
        float f4 = this.mScaleY;
        float f5 = (-f4) * fSin;
        float f6 = f2 * fSin;
        float f7 = f4 * fCos;
        for (int i2 = 0; i2 < this.f3219b; i2++) {
            View view = this.f2977q[i2];
            int left = (view.getLeft() + view.getRight()) / 2;
            int top2 = (view.getTop() + view.getBottom()) / 2;
            float f8 = left - this.f2970j;
            float f9 = top2 - this.f2971k;
            float f10 = (((f3 * f8) + (f5 * f9)) - f8) + this.mShiftX;
            float f11 = (((f8 * f6) + (f7 * f9)) - f9) + this.mShiftY;
            view.setTranslationX(f10);
            view.setTranslationY(f11);
            view.setScaleY(this.mScaleY);
            view.setScaleX(this.mScaleX);
            if (!Float.isNaN(this.mGroupRotateAngle)) {
                view.setRotation(this.mGroupRotateAngle);
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    protected void c(ConstraintLayout constraintLayout) {
        b(constraintLayout);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    protected void e(AttributeSet attributeSet) {
        super.e(attributeSet);
        this.f3222e = false;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_android_visibility) {
                    this.mApplyVisibilityOnAttach = true;
                } else if (index == R.styleable.ConstraintLayout_Layout_android_elevation) {
                    this.mApplyElevationOnAttach = true;
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    protected void f() {
        if (this.f2969i == null) {
            return;
        }
        if (this.f2976p || Float.isNaN(this.f2970j) || Float.isNaN(this.f2971k)) {
            if (!Float.isNaN(this.mRotationCenterX) && !Float.isNaN(this.mRotationCenterY)) {
                this.f2971k = this.mRotationCenterY;
                this.f2970j = this.mRotationCenterX;
                return;
            }
            View[] viewArrD = d(this.f2969i);
            int left = viewArrD[0].getLeft();
            int top2 = viewArrD[0].getTop();
            int right = viewArrD[0].getRight();
            int bottom = viewArrD[0].getBottom();
            for (int i2 = 0; i2 < this.f3219b; i2++) {
                View view = viewArrD[i2];
                left = Math.min(left, view.getLeft());
                top2 = Math.min(top2, view.getTop());
                right = Math.max(right, view.getRight());
                bottom = Math.max(bottom, view.getBottom());
            }
            this.f2972l = right;
            this.f2973m = bottom;
            this.f2974n = left;
            this.f2975o = top2;
            if (Float.isNaN(this.mRotationCenterX)) {
                this.f2970j = (left + right) / 2;
            } else {
                this.f2970j = this.mRotationCenterX;
            }
            if (Float.isNaN(this.mRotationCenterY)) {
                this.f2971k = (top2 + bottom) / 2;
            } else {
                this.f2971k = this.mRotationCenterY;
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    protected void onAttachedToWindow() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        super.onAttachedToWindow();
        this.f2969i = (ConstraintLayout) getParent();
        if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
            int visibility = getVisibility();
            float elevation = getElevation();
            for (int i2 = 0; i2 < this.f3219b; i2++) {
                View viewById = this.f2969i.getViewById(this.f3218a[i2]);
                if (viewById != null) {
                    if (this.mApplyVisibilityOnAttach) {
                        viewById.setVisibility(visibility);
                    }
                    if (this.mApplyElevationOnAttach && elevation > 0.0f) {
                        viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public void setElevation(float f2) {
        super.setElevation(f2);
        a();
    }

    @Override // android.view.View
    public void setPivotX(float f2) {
        this.mRotationCenterX = f2;
        transform();
    }

    @Override // android.view.View
    public void setPivotY(float f2) {
        this.mRotationCenterY = f2;
        transform();
    }

    @Override // android.view.View
    public void setRotation(float f2) {
        this.mGroupRotateAngle = f2;
        transform();
    }

    @Override // android.view.View
    public void setScaleX(float f2) {
        this.mScaleX = f2;
        transform();
    }

    @Override // android.view.View
    public void setScaleY(float f2) {
        this.mScaleY = f2;
        transform();
    }

    @Override // android.view.View
    public void setTranslationX(float f2) {
        this.mShiftX = f2;
        transform();
    }

    @Override // android.view.View
    public void setTranslationY(float f2) {
        this.mShiftY = f2;
        transform();
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        a();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePostLayout(ConstraintLayout constraintLayout) {
        reCacheViews();
        this.f2970j = Float.NaN;
        this.f2971k = Float.NaN;
        ConstraintWidget constraintWidget = ((ConstraintLayout.LayoutParams) getLayoutParams()).getConstraintWidget();
        constraintWidget.setWidth(0);
        constraintWidget.setHeight(0);
        f();
        layout(((int) this.f2974n) - getPaddingLeft(), ((int) this.f2975o) - getPaddingTop(), ((int) this.f2972l) + getPaddingRight(), ((int) this.f2973m) + getPaddingBottom());
        transform();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePreDraw(ConstraintLayout constraintLayout) {
        this.f2969i = constraintLayout;
        float rotation = getRotation();
        if (rotation != 0.0f) {
            this.mGroupRotateAngle = rotation;
        } else {
            if (Float.isNaN(this.mGroupRotateAngle)) {
                return;
            }
            this.mGroupRotateAngle = rotation;
        }
    }

    public Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.f2970j = Float.NaN;
        this.f2971k = Float.NaN;
        this.f2972l = Float.NaN;
        this.f2973m = Float.NaN;
        this.f2974n = Float.NaN;
        this.f2975o = Float.NaN;
        this.f2976p = true;
        this.f2977q = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }

    public Layer(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.f2970j = Float.NaN;
        this.f2971k = Float.NaN;
        this.f2972l = Float.NaN;
        this.f2973m = Float.NaN;
        this.f2974n = Float.NaN;
        this.f2975o = Float.NaN;
        this.f2976p = true;
        this.f2977q = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }
}

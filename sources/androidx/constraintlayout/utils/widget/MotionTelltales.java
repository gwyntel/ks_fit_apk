package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class MotionTelltales extends MockView {
    private static final String TAG = "MotionTelltales";

    /* renamed from: b, reason: collision with root package name */
    MotionLayout f3209b;

    /* renamed from: c, reason: collision with root package name */
    float[] f3210c;

    /* renamed from: d, reason: collision with root package name */
    Matrix f3211d;

    /* renamed from: e, reason: collision with root package name */
    int f3212e;

    /* renamed from: f, reason: collision with root package name */
    int f3213f;

    /* renamed from: g, reason: collision with root package name */
    float f3214g;
    private Paint mPaintTelltales;

    public MotionTelltales(Context context) {
        super(context);
        this.mPaintTelltales = new Paint();
        this.f3210c = new float[2];
        this.f3211d = new Matrix();
        this.f3212e = 0;
        this.f3213f = -65281;
        this.f3214g = 0.25f;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MotionTelltales);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.MotionTelltales_telltales_tailColor) {
                    this.f3213f = typedArrayObtainStyledAttributes.getColor(index, this.f3213f);
                } else if (index == R.styleable.MotionTelltales_telltales_velocityMode) {
                    this.f3212e = typedArrayObtainStyledAttributes.getInt(index, this.f3212e);
                } else if (index == R.styleable.MotionTelltales_telltales_tailScale) {
                    this.f3214g = typedArrayObtainStyledAttributes.getFloat(index, this.f3214g);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        this.mPaintTelltales.setColor(this.f3213f);
        this.mPaintTelltales.setStrokeWidth(5.0f);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // androidx.constraintlayout.utils.widget.MockView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMatrix().invert(this.f3211d);
        if (this.f3209b == null) {
            ViewParent parent = getParent();
            if (parent instanceof MotionLayout) {
                this.f3209b = (MotionLayout) parent;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float[] fArr = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        for (int i2 = 0; i2 < 5; i2++) {
            float f2 = fArr[i2];
            for (int i3 = 0; i3 < 5; i3++) {
                float f3 = fArr[i3];
                this.f3209b.getViewVelocity(this, f3, f2, this.f3210c, this.f3212e);
                this.f3211d.mapVectors(this.f3210c);
                float f4 = width * f3;
                float f5 = height * f2;
                float[] fArr2 = this.f3210c;
                float f6 = fArr2[0];
                float f7 = this.f3214g;
                float f8 = f5 - (fArr2[1] * f7);
                this.f3211d.mapVectors(fArr2);
                canvas.drawLine(f4, f5, f4 - (f6 * f7), f8, this.mPaintTelltales);
            }
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        postInvalidate();
    }

    public void setText(CharSequence charSequence) {
        this.f3187a = charSequence.toString();
        requestLayout();
    }

    public MotionTelltales(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaintTelltales = new Paint();
        this.f3210c = new float[2];
        this.f3211d = new Matrix();
        this.f3212e = 0;
        this.f3213f = -65281;
        this.f3214g = 0.25f;
        init(context, attributeSet);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPaintTelltales = new Paint();
        this.f3210c = new float[2];
        this.f3211d = new Matrix();
        this.f3212e = 0;
        this.f3213f = -65281;
        this.f3214g = 0.25f;
        init(context, attributeSet);
    }
}

package com.huawei.hms.scankit;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.core.view.ViewCompat;
import com.huawei.hms.scankit.p.u6;

/* loaded from: classes4.dex */
public final class ViewfinderView extends View {
    private int[] A;
    private float[] B;
    private Rect C;
    private boolean D;
    Point E;
    private boolean F;

    /* renamed from: a, reason: collision with root package name */
    private Paint f16743a;

    /* renamed from: b, reason: collision with root package name */
    private TextPaint f16744b;

    /* renamed from: c, reason: collision with root package name */
    private int f16745c;

    /* renamed from: d, reason: collision with root package name */
    private int f16746d;

    /* renamed from: e, reason: collision with root package name */
    private int f16747e;

    /* renamed from: f, reason: collision with root package name */
    private int f16748f;

    /* renamed from: g, reason: collision with root package name */
    private int f16749g;

    /* renamed from: h, reason: collision with root package name */
    private float f16750h;

    /* renamed from: i, reason: collision with root package name */
    private c f16751i;

    /* renamed from: j, reason: collision with root package name */
    private String f16752j;

    /* renamed from: k, reason: collision with root package name */
    private int f16753k;

    /* renamed from: l, reason: collision with root package name */
    private float f16754l;

    /* renamed from: m, reason: collision with root package name */
    public int f16755m;

    /* renamed from: n, reason: collision with root package name */
    public int f16756n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f16757o;

    /* renamed from: p, reason: collision with root package name */
    private int f16758p;

    /* renamed from: q, reason: collision with root package name */
    private int f16759q;

    /* renamed from: r, reason: collision with root package name */
    private int f16760r;

    /* renamed from: s, reason: collision with root package name */
    private int f16761s;

    /* renamed from: t, reason: collision with root package name */
    private b f16762t;

    /* renamed from: u, reason: collision with root package name */
    private int f16763u;

    /* renamed from: v, reason: collision with root package name */
    private int f16764v;

    /* renamed from: w, reason: collision with root package name */
    private Rect f16765w;

    /* renamed from: x, reason: collision with root package name */
    private int f16766x;

    /* renamed from: y, reason: collision with root package name */
    private ValueAnimator f16767y;

    /* renamed from: z, reason: collision with root package name */
    Paint f16768z;

    class a implements ValueAnimator.AnimatorUpdateListener {
        a() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ViewfinderView.this.f16755m = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            ViewfinderView.this.invalidate();
        }
    }

    public enum b {
        NONE(0),
        LINE(1),
        GRID(2);


        /* renamed from: a, reason: collision with root package name */
        private int f16774a;

        b(int i2) {
            this.f16774a = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static b b(int i2) {
            for (b bVar : values()) {
                if (bVar.f16774a == i2) {
                    return bVar;
                }
            }
            return LINE;
        }
    }

    public enum c {
        TOP(0),
        BOTTOM(1);


        /* renamed from: a, reason: collision with root package name */
        private int f16778a;

        c(int i2) {
            this.f16778a = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static c b(int i2) {
            for (c cVar : values()) {
                if (cVar.f16778a == i2) {
                    return cVar;
                }
            }
            return TOP;
        }
    }

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public static int b(Context context, int i2) {
        try {
            return context.getColor(i2);
        } catch (Resources.NotFoundException unused) {
            Log.e("ViewfinderView", "getColor: Resources.NotFoundException");
            return ViewCompat.MEASURED_SIZE_MASK;
        } catch (Exception unused2) {
            Log.e("ViewfinderView", "getColor: Exception");
            return ViewCompat.MEASURED_SIZE_MASK;
        }
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public void a(u6 u6Var) {
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.f16767y;
        if (valueAnimator != null) {
            valueAnimator.pause();
            this.f16767y.removeAllListeners();
            this.f16767y.cancel();
        }
    }

    @Override // android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        if (this.F) {
            canvas.save();
            String str = Build.DEVICE;
            a(canvas, "HWTAH".equals(str) || str.equals("HWTAH-C"));
            canvas.restore();
        }
        a(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.f16758p = i2;
        this.f16759q = i3;
        a();
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewfinderView);
        this.f16745c = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_maskColor, b(context, R.color.scankit_viewfinder_mask));
        this.f16746d = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_frameColor, b(context, R.color.scankit_viewfinder_frame));
        this.f16748f = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_cornerColor, b(context, R.color.scankit_viewfinder_corner));
        this.f16747e = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_laserColor, b(context, R.color.scankit_viewfinder_lasers));
        this.f16749g = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_resultPointColor, b(context, R.color.scankit_viewfinder_result_point_color));
        this.f16752j = typedArrayObtainStyledAttributes.getString(R.styleable.ViewfinderView_scankit_labelText);
        this.f16753k = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_scankit_labelTextColor, b(context, R.color.scankit_viewfinder_text_color));
        this.f16754l = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_scankit_labelTextSize, TypedValue.applyDimension(2, 14.0f, getResources().getDisplayMetrics()));
        this.f16750h = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_scankit_labelTextPadding, TypedValue.applyDimension(1, 24.0f, getResources().getDisplayMetrics()));
        this.f16751i = c.b(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_scankit_labelTextLocation, 0));
        this.f16757o = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_scankit_showResultPoint, false);
        this.f16760r = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_scankit_frameWidth, 0);
        this.f16761s = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_scankit_frameHeight, 0);
        this.f16762t = b.b(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_scankit_laserStyle, b.LINE.f16774a));
        this.f16763u = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_scankit_gridColumn, 20);
        this.f16764v = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_scankit_gridHeight, TypedValue.applyDimension(1, 40.0f, getResources().getDisplayMetrics()));
        this.F = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_scankit_line_anim, true);
        typedArrayObtainStyledAttributes.recycle();
        this.f16743a = new Paint(1);
        this.f16744b = new TextPaint(1);
        this.f16766x = a(context, 136);
        this.f16759q = getDisplayMetrics().heightPixels;
        this.f16758p = getDisplayMetrics().widthPixels;
    }

    public ViewfinderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f16755m = 0;
        this.f16756n = 0;
        this.f16765w = new Rect();
        this.f16768z = new Paint();
        this.A = new int[]{Color.parseColor("#FFFFFFFF"), Color.parseColor("#72FFFFFF"), Color.parseColor("#58FFFFFF"), Color.parseColor("#40FFFFFF"), Color.parseColor("#28FFFFFF"), Color.parseColor("#13FFFFFF"), Color.parseColor("#00FFFFFF")};
        this.B = new float[]{0.0f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        this.D = true;
        this.F = true;
        a(context, attributeSet);
    }

    private void a(Canvas canvas, boolean z2) {
        this.f16743a.setStyle(Paint.Style.FILL);
        this.f16743a.setColor(this.f16747e);
        if (!e.f16900z && !z2) {
            Rect rect = this.f16765w;
            rect.left = 0;
            int i2 = this.f16755m;
            rect.top = i2;
            rect.bottom = i2 + this.f16766x;
            rect.right = this.f16758p;
        } else {
            Rect rect2 = this.f16765w;
            int i3 = this.f16758p / 2;
            rect2.left = i3 - 540;
            int i4 = this.f16755m;
            rect2.top = i4;
            rect2.bottom = i4 + this.f16766x;
            rect2.right = i3 + 540;
        }
        float f2 = this.f16758p / 2;
        float f3 = this.f16765w.bottom + 500;
        this.f16743a.setShader(new RadialGradient(f2, f3, 690, this.A, this.B, Shader.TileMode.CLAMP));
        this.f16743a.setStrokeWidth(10.0f);
        Rect rect3 = this.f16765w;
        float f4 = rect3.left;
        float f5 = rect3.bottom;
        canvas.drawLine(f4, f5, rect3.right, f5, this.f16743a);
        canvas.clipRect(this.f16765w);
        canvas.drawCircle(f2, f3, r12 + 200, this.f16743a);
    }

    public void a() {
        if (e.f16900z) {
            this.f16756n = this.f16759q;
        } else {
            this.f16756n = this.f16759q - a(getContext(), 139);
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, this.f16756n - this.f16766x);
        this.f16767y = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(3000L);
        this.f16767y.setInterpolator(new AccelerateDecelerateInterpolator());
        this.f16767y.setRepeatMode(1);
        this.f16767y.setRepeatCount(-1);
        this.f16767y.addUpdateListener(new a());
        this.f16767y.start();
    }

    public void a(Rect rect, boolean z2, Point point) {
        this.D = z2;
        this.E = point;
        if (this.C == null) {
            this.C = rect;
            invalidate();
        }
    }

    public static int a(Context context, int i2) {
        return (int) ((i2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void a(Canvas canvas) {
        Point point;
        int i2;
        int i3;
        int i4;
        if (this.C == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (this.D) {
            Point point2 = this.E;
            point = new Point(point2.y, point2.x);
        } else {
            Point point3 = this.E;
            point = new Point(point3.x, point3.y);
        }
        int i5 = point.x;
        float f2 = width / i5;
        int i6 = point.y;
        float f3 = height / i6;
        int i7 = (int) (i6 * 0.1d);
        int i8 = (int) ((i5 * 0.15d) / 2.0d);
        RectF rectF = new RectF();
        if (this.D) {
            if (f2 > f3) {
                i3 = (int) (point.y * f2);
                canvas.translate(0.0f, (height / 2) - (i3 / 2));
                i4 = width;
            } else {
                i2 = (int) (point.x * f3);
                canvas.translate((width / 2) - (i2 / 2), 0.0f);
                i4 = i2;
                i3 = height;
            }
        } else if (f2 > f3) {
            i3 = (int) (point.y * f2);
            canvas.translate(0.0f, (height / 2) - (i3 / 2));
            i4 = width;
        } else {
            i2 = (int) (point.x * f3);
            canvas.translate((width / 2) - (i2 / 2), 0.0f);
            i4 = i2;
            i3 = height;
        }
        Rect rect = this.C;
        float f4 = rect.left + i8;
        float f5 = point.x;
        float f6 = rect.top + i7;
        float f7 = point.y;
        float f8 = f6 / f7;
        float f9 = (rect.bottom + i7) / f7;
        float f10 = i4;
        float f11 = (f4 / f5) * f10;
        rectF.left = f11;
        float f12 = ((rect.right + i8) / f5) * f10;
        rectF.right = f12;
        float f13 = i3;
        float f14 = f8 * f13;
        rectF.top = f14;
        float f15 = f9 * f13;
        rectF.bottom = f15;
        float f16 = (f11 + f12) / 2.0f;
        float f17 = (f14 + f15) / 2.0f;
        this.f16768z.setStyle(Paint.Style.FILL);
        this.f16768z.setColor(-1);
        canvas.drawCircle(f16, f17, ((int) ((getDisplayMetrics().density * 24.0f) + 0.5d)) / 2, this.f16768z);
        this.f16768z.setColor(Color.parseColor("#007DFF"));
        canvas.drawCircle(f16, f17, ((int) ((getDisplayMetrics().density * 22.0f) + 0.5d)) / 2, this.f16768z);
        if (this.D) {
            if (f2 > f3) {
                canvas.translate(0.0f, (i3 / 2) - (height / 2));
                return;
            } else {
                canvas.translate((i4 / 2) - (width / 2), 0.0f);
                return;
            }
        }
        if (f2 > f3) {
            canvas.translate(0.0f, (i3 / 2) - (height / 2));
        } else {
            canvas.translate((i4 / 2) - (width / 2), 0.0f);
        }
    }
}

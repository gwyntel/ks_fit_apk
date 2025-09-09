package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class CircularProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 7.5f;
    private static final float CENTER_RADIUS_LARGE = 11.0f;
    private static final float COLOR_CHANGE_OFFSET = 0.75f;
    public static final int DEFAULT = 1;
    private static final float GROUP_FULL_ROTATION = 216.0f;
    public static final int LARGE = 0;
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float MIN_PROGRESS_ARC = 0.01f;
    private static final float RING_ROTATION = 0.20999998f;
    private static final float SHRINK_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;

    /* renamed from: a, reason: collision with root package name */
    float f6205a;

    /* renamed from: b, reason: collision with root package name */
    boolean f6206b;
    private Animator mAnimator;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int[] COLORS = {ViewCompat.MEASURED_STATE_MASK};

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ProgressDrawableSize {
    }

    private static class Ring {

        /* renamed from: a, reason: collision with root package name */
        final RectF f6211a = new RectF();

        /* renamed from: b, reason: collision with root package name */
        final Paint f6212b;

        /* renamed from: c, reason: collision with root package name */
        final Paint f6213c;

        /* renamed from: d, reason: collision with root package name */
        final Paint f6214d;

        /* renamed from: e, reason: collision with root package name */
        float f6215e;

        /* renamed from: f, reason: collision with root package name */
        float f6216f;

        /* renamed from: g, reason: collision with root package name */
        float f6217g;

        /* renamed from: h, reason: collision with root package name */
        float f6218h;

        /* renamed from: i, reason: collision with root package name */
        int[] f6219i;

        /* renamed from: j, reason: collision with root package name */
        int f6220j;

        /* renamed from: k, reason: collision with root package name */
        float f6221k;

        /* renamed from: l, reason: collision with root package name */
        float f6222l;

        /* renamed from: m, reason: collision with root package name */
        float f6223m;

        /* renamed from: n, reason: collision with root package name */
        boolean f6224n;

        /* renamed from: o, reason: collision with root package name */
        Path f6225o;

        /* renamed from: p, reason: collision with root package name */
        float f6226p;

        /* renamed from: q, reason: collision with root package name */
        float f6227q;

        /* renamed from: r, reason: collision with root package name */
        int f6228r;

        /* renamed from: s, reason: collision with root package name */
        int f6229s;

        /* renamed from: t, reason: collision with root package name */
        int f6230t;

        /* renamed from: u, reason: collision with root package name */
        int f6231u;

        Ring() {
            Paint paint = new Paint();
            this.f6212b = paint;
            Paint paint2 = new Paint();
            this.f6213c = paint2;
            Paint paint3 = new Paint();
            this.f6214d = paint3;
            this.f6215e = 0.0f;
            this.f6216f = 0.0f;
            this.f6217g = 0.0f;
            this.f6218h = 5.0f;
            this.f6226p = 1.0f;
            this.f6230t = 255;
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAntiAlias(true);
            paint3.setColor(0);
        }

        void A(int i2) {
            this.f6214d.setColor(i2);
        }

        void B(float f2) {
            this.f6227q = f2;
        }

        void C(int i2) {
            this.f6231u = i2;
        }

        void D(ColorFilter colorFilter) {
            this.f6212b.setColorFilter(colorFilter);
        }

        void E(int i2) {
            this.f6220j = i2;
            this.f6231u = this.f6219i[i2];
        }

        void F(int[] iArr) {
            this.f6219i = iArr;
            E(0);
        }

        void G(float f2) {
            this.f6216f = f2;
        }

        void H(float f2) {
            this.f6217g = f2;
        }

        void I(boolean z2) {
            if (this.f6224n != z2) {
                this.f6224n = z2;
            }
        }

        void J(float f2) {
            this.f6215e = f2;
        }

        void K(Paint.Cap cap) {
            this.f6212b.setStrokeCap(cap);
        }

        void L(float f2) {
            this.f6218h = f2;
            this.f6212b.setStrokeWidth(f2);
        }

        void M() {
            this.f6221k = this.f6215e;
            this.f6222l = this.f6216f;
            this.f6223m = this.f6217g;
        }

        void a(Canvas canvas, Rect rect) {
            RectF rectF = this.f6211a;
            float f2 = this.f6227q;
            float fMin = (this.f6218h / 2.0f) + f2;
            if (f2 <= 0.0f) {
                fMin = (Math.min(rect.width(), rect.height()) / 2.0f) - Math.max((this.f6228r * this.f6226p) / 2.0f, this.f6218h / 2.0f);
            }
            rectF.set(rect.centerX() - fMin, rect.centerY() - fMin, rect.centerX() + fMin, rect.centerY() + fMin);
            float f3 = this.f6215e;
            float f4 = this.f6217g;
            float f5 = (f3 + f4) * 360.0f;
            float f6 = ((this.f6216f + f4) * 360.0f) - f5;
            this.f6212b.setColor(this.f6231u);
            this.f6212b.setAlpha(this.f6230t);
            float f7 = this.f6218h / 2.0f;
            rectF.inset(f7, f7);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.f6214d);
            float f8 = -f7;
            rectF.inset(f8, f8);
            canvas.drawArc(rectF, f5, f6, false, this.f6212b);
            b(canvas, f5, f6, rectF);
        }

        void b(Canvas canvas, float f2, float f3, RectF rectF) {
            if (this.f6224n) {
                Path path = this.f6225o;
                if (path == null) {
                    Path path2 = new Path();
                    this.f6225o = path2;
                    path2.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                float fMin = Math.min(rectF.width(), rectF.height()) / 2.0f;
                float f4 = (this.f6228r * this.f6226p) / 2.0f;
                this.f6225o.moveTo(0.0f, 0.0f);
                this.f6225o.lineTo(this.f6228r * this.f6226p, 0.0f);
                Path path3 = this.f6225o;
                float f5 = this.f6228r;
                float f6 = this.f6226p;
                path3.lineTo((f5 * f6) / 2.0f, this.f6229s * f6);
                this.f6225o.offset((fMin + rectF.centerX()) - f4, rectF.centerY() + (this.f6218h / 2.0f));
                this.f6225o.close();
                this.f6213c.setColor(this.f6231u);
                this.f6213c.setAlpha(this.f6230t);
                canvas.save();
                canvas.rotate(f2 + f3, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.f6225o, this.f6213c);
                canvas.restore();
            }
        }

        int c() {
            return this.f6230t;
        }

        float d() {
            return this.f6229s;
        }

        float e() {
            return this.f6226p;
        }

        float f() {
            return this.f6228r;
        }

        int g() {
            return this.f6214d.getColor();
        }

        float h() {
            return this.f6227q;
        }

        int[] i() {
            return this.f6219i;
        }

        float j() {
            return this.f6216f;
        }

        int k() {
            return this.f6219i[l()];
        }

        int l() {
            return (this.f6220j + 1) % this.f6219i.length;
        }

        float m() {
            return this.f6217g;
        }

        boolean n() {
            return this.f6224n;
        }

        float o() {
            return this.f6215e;
        }

        int p() {
            return this.f6219i[this.f6220j];
        }

        float q() {
            return this.f6222l;
        }

        float r() {
            return this.f6223m;
        }

        float s() {
            return this.f6221k;
        }

        Paint.Cap t() {
            return this.f6212b.getStrokeCap();
        }

        float u() {
            return this.f6218h;
        }

        void v() {
            E(l());
        }

        void w() {
            this.f6221k = 0.0f;
            this.f6222l = 0.0f;
            this.f6223m = 0.0f;
            J(0.0f);
            G(0.0f);
            H(0.0f);
        }

        void x(int i2) {
            this.f6230t = i2;
        }

        void y(float f2, float f3) {
            this.f6228r = (int) f2;
            this.f6229s = (int) f3;
        }

        void z(float f2) {
            if (f2 != this.f6226p) {
                this.f6226p = f2;
            }
        }
    }

    public CircularProgressDrawable(@NonNull Context context) {
        this.mResources = ((Context) Preconditions.checkNotNull(context)).getResources();
        Ring ring = new Ring();
        this.mRing = ring;
        ring.F(COLORS);
        setStrokeWidth(STROKE_WIDTH);
        setupAnimators();
    }

    private void applyFinishTranslation(float f2, Ring ring) {
        b(f2, ring);
        float fFloor = (float) (Math.floor(ring.r() / MAX_PROGRESS_ARC) + 1.0d);
        ring.J(ring.s() + (((ring.q() - MIN_PROGRESS_ARC) - ring.s()) * f2));
        ring.G(ring.q());
        ring.H(ring.r() + ((fFloor - ring.r()) * f2));
    }

    private int evaluateColorChange(float f2, int i2, int i3) {
        return ((((i2 >> 24) & 255) + ((int) ((((i3 >> 24) & 255) - r0) * f2))) << 24) | ((((i2 >> 16) & 255) + ((int) ((((i3 >> 16) & 255) - r1) * f2))) << 16) | ((((i2 >> 8) & 255) + ((int) ((((i3 >> 8) & 255) - r2) * f2))) << 8) | ((i2 & 255) + ((int) (f2 * ((i3 & 255) - r8))));
    }

    private float getRotation() {
        return this.mRotation;
    }

    private void setRotation(float f2) {
        this.mRotation = f2;
    }

    private void setSizeParameters(float f2, float f3, float f4, float f5) {
        Ring ring = this.mRing;
        float f6 = this.mResources.getDisplayMetrics().density;
        ring.L(f3 * f6);
        ring.B(f2 * f6);
        ring.E(0);
        ring.y(f4 * f6, f5 * f6);
    }

    private void setupAnimators() {
        final Ring ring = this.mRing;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircularProgressDrawable.this.b(fFloatValue, ring);
                CircularProgressDrawable.this.a(fFloatValue, ring, false);
                CircularProgressDrawable.this.invalidateSelf();
            }
        });
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setRepeatMode(1);
        valueAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR);
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: androidx.swiperefreshlayout.widget.CircularProgressDrawable.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                CircularProgressDrawable.this.a(1.0f, ring, true);
                ring.M();
                ring.v();
                CircularProgressDrawable circularProgressDrawable = CircularProgressDrawable.this;
                if (!circularProgressDrawable.f6206b) {
                    circularProgressDrawable.f6205a += 1.0f;
                    return;
                }
                circularProgressDrawable.f6206b = false;
                animator.cancel();
                animator.setDuration(1332L);
                animator.start();
                ring.I(false);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                CircularProgressDrawable.this.f6205a = 0.0f;
            }
        });
        this.mAnimator = valueAnimatorOfFloat;
    }

    void a(float f2, Ring ring, boolean z2) {
        float interpolation;
        float interpolation2;
        if (this.f6206b) {
            applyFinishTranslation(f2, ring);
            return;
        }
        if (f2 != 1.0f || z2) {
            float fR = ring.r();
            if (f2 < 0.5f) {
                interpolation = ring.s();
                interpolation2 = (MATERIAL_INTERPOLATOR.getInterpolation(f2 / 0.5f) * 0.79f) + MIN_PROGRESS_ARC + interpolation;
            } else {
                float fS = ring.s() + 0.79f;
                interpolation = fS - (((1.0f - MATERIAL_INTERPOLATOR.getInterpolation((f2 - 0.5f) / 0.5f)) * 0.79f) + MIN_PROGRESS_ARC);
                interpolation2 = fS;
            }
            float f3 = fR + (RING_ROTATION * f2);
            float f4 = (f2 + this.f6205a) * GROUP_FULL_ROTATION;
            ring.J(interpolation);
            ring.G(interpolation2);
            ring.H(f3);
            setRotation(f4);
        }
    }

    void b(float f2, Ring ring) {
        if (f2 > 0.75f) {
            ring.C(evaluateColorChange((f2 - 0.75f) / 0.25f, ring.p(), ring.k()));
        } else {
            ring.C(ring.p());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        this.mRing.a(canvas, bounds);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mRing.c();
    }

    public boolean getArrowEnabled() {
        return this.mRing.n();
    }

    public float getArrowHeight() {
        return this.mRing.d();
    }

    public float getArrowScale() {
        return this.mRing.e();
    }

    public float getArrowWidth() {
        return this.mRing.f();
    }

    public int getBackgroundColor() {
        return this.mRing.g();
    }

    public float getCenterRadius() {
        return this.mRing.h();
    }

    @NonNull
    public int[] getColorSchemeColors() {
        return this.mRing.i();
    }

    public float getEndTrim() {
        return this.mRing.j();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getProgressRotation() {
        return this.mRing.m();
    }

    public float getStartTrim() {
        return this.mRing.o();
    }

    @NonNull
    public Paint.Cap getStrokeCap() {
        return this.mRing.t();
    }

    public float getStrokeWidth() {
        return this.mRing.u();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mAnimator.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mRing.x(i2);
        invalidateSelf();
    }

    public void setArrowDimensions(float f2, float f3) {
        this.mRing.y(f2, f3);
        invalidateSelf();
    }

    public void setArrowEnabled(boolean z2) {
        this.mRing.I(z2);
        invalidateSelf();
    }

    public void setArrowScale(float f2) {
        this.mRing.z(f2);
        invalidateSelf();
    }

    public void setBackgroundColor(int i2) {
        this.mRing.A(i2);
        invalidateSelf();
    }

    public void setCenterRadius(float f2) {
        this.mRing.B(f2);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mRing.D(colorFilter);
        invalidateSelf();
    }

    public void setColorSchemeColors(@NonNull int... iArr) {
        this.mRing.F(iArr);
        this.mRing.E(0);
        invalidateSelf();
    }

    public void setProgressRotation(float f2) {
        this.mRing.H(f2);
        invalidateSelf();
    }

    public void setStartEndTrim(float f2, float f3) {
        this.mRing.J(f2);
        this.mRing.G(f3);
        invalidateSelf();
    }

    public void setStrokeCap(@NonNull Paint.Cap cap) {
        this.mRing.K(cap);
        invalidateSelf();
    }

    public void setStrokeWidth(float f2) {
        this.mRing.L(f2);
        invalidateSelf();
    }

    public void setStyle(int i2) {
        if (i2 == 0) {
            setSizeParameters(CENTER_RADIUS_LARGE, 3.0f, 12.0f, 6.0f);
        } else {
            setSizeParameters(CENTER_RADIUS, STROKE_WIDTH, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.mAnimator.cancel();
        this.mRing.M();
        if (this.mRing.j() != this.mRing.o()) {
            this.f6206b = true;
            this.mAnimator.setDuration(666L);
            this.mAnimator.start();
        } else {
            this.mRing.E(0);
            this.mRing.w();
            this.mAnimator.setDuration(1332L);
            this.mAnimator.start();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimator.cancel();
        setRotation(0.0f);
        this.mRing.I(false);
        this.mRing.E(0);
        this.mRing.w();
        invalidateSelf();
    }
}

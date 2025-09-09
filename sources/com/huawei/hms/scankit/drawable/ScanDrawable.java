package com.huawei.hms.scankit.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.media3.exoplayer.ExoPlayer;
import com.huawei.hms.scankit.R;
import com.huawei.hms.scankit.p.b1;
import com.huawei.hms.scankit.p.b6;
import com.huawei.hms.scankit.p.n6;
import com.huawei.hms.scankit.p.y5;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class ScanDrawable extends Drawable implements Animatable {

    /* renamed from: a, reason: collision with root package name */
    private final ValueAnimator f16873a;

    /* renamed from: b, reason: collision with root package name */
    private final ValueAnimator f16874b;

    /* renamed from: c, reason: collision with root package name */
    private final Matrix f16875c;

    /* renamed from: d, reason: collision with root package name */
    private final Paint f16876d;

    /* renamed from: e, reason: collision with root package name */
    private final Paint f16877e;

    /* renamed from: f, reason: collision with root package name */
    private final ColorMatrix f16878f;

    /* renamed from: g, reason: collision with root package name */
    private final Matrix f16879g;

    /* renamed from: h, reason: collision with root package name */
    private final Rect f16880h;

    /* renamed from: i, reason: collision with root package name */
    private final Rect f16881i;

    /* renamed from: j, reason: collision with root package name */
    private final Rect f16882j;

    /* renamed from: k, reason: collision with root package name */
    private final Rect f16883k;

    /* renamed from: l, reason: collision with root package name */
    private int f16884l;

    /* renamed from: m, reason: collision with root package name */
    private int f16885m;

    /* renamed from: n, reason: collision with root package name */
    private float f16886n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f16887o;

    /* renamed from: p, reason: collision with root package name */
    private float f16888p;

    /* renamed from: q, reason: collision with root package name */
    private int f16889q;

    /* renamed from: r, reason: collision with root package name */
    private y5 f16890r;

    /* renamed from: s, reason: collision with root package name */
    private float f16891s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f16892t;

    /* renamed from: u, reason: collision with root package name */
    private Bitmap f16893u;

    /* renamed from: v, reason: collision with root package name */
    private Bitmap f16894v;

    /* renamed from: w, reason: collision with root package name */
    private AnimatorSet f16895w;

    /* renamed from: x, reason: collision with root package name */
    private static final int[] f16870x = {13625597, 357325};

    /* renamed from: y, reason: collision with root package name */
    private static final Interpolator f16871y = new b1(0.4f, 0.0f, 0.4f, 1.0f);

    /* renamed from: z, reason: collision with root package name */
    private static final Interpolator f16872z = new b1(0.4f, 0.0f, 0.7f, 1.0f);
    private static final Interpolator A = new b1(0.25f, 0.0f, 0.4f, 1.0f);

    class a implements ValueAnimator.AnimatorUpdateListener {
        a() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) ScanDrawable.this.f16874b.getAnimatedValue()).floatValue();
            ScanDrawable scanDrawable = ScanDrawable.this;
            scanDrawable.f16889q = scanDrawable.f16883k.top + ((int) (ScanDrawable.this.f16883k.height() * ScanDrawable.f16871y.getInterpolation(fFloatValue)));
            if (fFloatValue < 0.389f) {
                ScanDrawable.this.f16888p = ScanDrawable.f16872z.getInterpolation(fFloatValue / 0.389f);
            } else {
                ScanDrawable.this.f16888p = 1.0f - ScanDrawable.A.getInterpolation((fFloatValue - 0.389f) / 0.611f);
            }
            ScanDrawable.this.invalidateSelf();
        }
    }

    class b extends AnimatorListenerAdapter {
        b() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
            super.onAnimationRepeat(animator);
            ScanDrawable.this.f16887o = !r2.f16887o;
        }
    }

    class c extends AnimatorListenerAdapter {
        c() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
            super.onAnimationRepeat(animator);
            float fAbs = Math.abs(((Float) ScanDrawable.this.f16874b.getAnimatedValue()).floatValue() - 0.5f);
            ScanDrawable.this.f16892t = !r1.f16892t;
            if (ScanDrawable.this.f16892t) {
                if (fAbs > 0.35f) {
                    ScanDrawable.this.f16886n = 0.0f;
                } else {
                    ScanDrawable.this.f16886n = n6.a(0.5f);
                }
            }
        }
    }

    public ScanDrawable() {
        this.f16873a = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.f16874b = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.f16875c = new Matrix();
        this.f16876d = new Paint();
        this.f16877e = new Paint();
        this.f16878f = new ColorMatrix();
        this.f16879g = new Matrix();
        this.f16880h = new Rect();
        this.f16881i = new Rect();
        this.f16882j = new Rect();
        this.f16883k = new Rect();
        this.f16886n = 0.5f;
        this.f16887o = false;
        this.f16888p = 0.0f;
        this.f16892t = true;
        this.f16895w = new AnimatorSet();
        d();
    }

    private void e() {
        this.f16873a.setInterpolator(new LinearInterpolator());
        this.f16873a.setRepeatMode(2);
        this.f16873a.setRepeatCount(-1);
        this.f16873a.setDuration(500L);
        this.f16873a.setStartDelay(200L);
        this.f16873a.addListener(new c());
    }

    private void f() {
        this.f16874b.setDuration(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        this.f16874b.setInterpolator(new LinearInterpolator());
        this.f16874b.setRepeatCount(-1);
        this.f16874b.setRepeatMode(2);
        this.f16874b.addUpdateListener(new a());
        this.f16874b.addListener(new b());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!isRunning() || canvas == null) {
            Log.w("ScanDrawable", "animator is not running or canvas is null.");
            return;
        }
        if (this.f16887o) {
            int i2 = this.f16889q;
            this.f16881i.set(0, i2, getBounds().right, ((int) (this.f16885m * this.f16888p * 0.5f)) + i2);
            int i3 = this.f16889q;
            this.f16882j.set(0, i3, getBounds().right, ((int) (this.f16885m * this.f16888p)) + i3);
        } else {
            int i4 = this.f16889q;
            this.f16881i.set(0, i4, getBounds().right, i4 - ((int) ((this.f16885m * this.f16888p) * 0.5f)));
            int i5 = this.f16889q;
            this.f16882j.set(0, i5, getBounds().right, i5 - ((int) (this.f16885m * this.f16888p)));
        }
        a(canvas, this.f16882j);
        b(canvas);
        a(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (resources == null || xmlPullParser == null || attributeSet == null) {
            Log.e("ScanDrawable", "resources, xmlPullParser or attributeSet is null when inflating drawable");
        } else {
            a(resources);
            super.inflate(resources, xmlPullParser, attributeSet, theme);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.f16895w.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (rect == null) {
            Log.e("ScanDrawable", "on bounds change: bounds is null!");
        } else {
            super.onBoundsChange(rect);
            a(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (isRunning()) {
            Log.i("ScanDrawable", "start failed, animator is running");
            return;
        }
        this.f16887o = false;
        this.f16892t = true;
        a(getBounds());
        this.f16895w.start();
        Log.i("ScanDrawable", "start scan animator success");
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (!isRunning()) {
            Log.i("ScanDrawable", "stop failed, animator is not running");
            return;
        }
        this.f16895w.end();
        this.f16890r = null;
        Log.i("ScanDrawable", "stop scan animator success");
    }

    private void d() {
        f();
        e();
        AnimatorSet animatorSet = new AnimatorSet();
        this.f16895w = animatorSet;
        animatorSet.playTogether(this.f16874b, this.f16873a);
    }

    private void b(Canvas canvas) {
        y5 y5Var = this.f16890r;
        if (y5Var == null) {
            Log.e("ScanDrawable", "drawParticle failed, mParticle is null");
        } else {
            y5Var.a(canvas, this.f16881i);
        }
    }

    private void a(Resources resources) {
        if (resources == null) {
            Log.e("ScanDrawable", "resources is null when init drawable");
            return;
        }
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(resources, R.drawable.scankit_scan_light);
        this.f16894v = Bitmap.createBitmap(bitmapDecodeResource.getWidth() * 2, bitmapDecodeResource.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(this.f16894v);
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.MIRROR;
        paint.setShader(new BitmapShader(bitmapDecodeResource, tileMode, tileMode));
        canvas.drawRect(0.0f, 0.0f, bitmapDecodeResource.getWidth() * 2, bitmapDecodeResource.getHeight() * 2, paint);
        this.f16893u = BitmapFactory.decodeResource(resources, R.drawable.scankit_scan_tail);
        this.f16891s = resources.getDisplayMetrics().density;
    }

    private void a(Rect rect) {
        if (rect.height() == 0) {
            Log.d("ScanDrawable", "initBounds bounds is null");
            return;
        }
        this.f16883k.set(rect);
        this.f16883k.inset(0, (int) (rect.height() * 0.1f));
        this.f16884l = (int) (rect.height() * 0.18f);
        this.f16885m = (int) (rect.height() * 0.36f);
        Rect rect2 = new Rect(rect);
        rect2.inset((int) (rect.width() * 0.2f), 0);
        float f2 = this.f16891s;
        int iWidth = (int) ((f2 != 0.0f ? 0.001f / (f2 * f2) : 0.001f) * rect2.width() * rect2.height());
        this.f16890r = new y5(new b6(iWidth, 500L).b(0.33f, 1.0f).a(0, -1, 0L, 100L, new LinearInterpolator()).a(-1, 0, 400L, 500L, new LinearInterpolator()), rect2, iWidth, this.f16891s * 2.0f, f16870x);
    }

    public ScanDrawable(Resources resources) {
        this();
        a(resources);
    }

    private void a(Canvas canvas, Rect rect) {
        Bitmap bitmap = this.f16893u;
        if (bitmap != null && bitmap.getWidth() != 0 && this.f16893u.getHeight() != 0) {
            this.f16875c.setScale(rect.width() / this.f16893u.getWidth(), rect.height() / this.f16893u.getHeight());
            this.f16875c.postTranslate(rect.left, rect.top);
            canvas.drawBitmap(this.f16893u, this.f16875c, this.f16876d);
            this.f16875c.reset();
            return;
        }
        Log.e("ScanDrawable", "dawTail failed, input bitmap is null");
    }

    private void a(Canvas canvas) {
        Bitmap bitmap = this.f16894v;
        if (bitmap != null && bitmap.getWidth() != 0 && this.f16894v.getHeight() != 0) {
            float fFloatValue = (this.f16888p * 0.5f) + (((Float) this.f16873a.getAnimatedValue()).floatValue() * this.f16886n);
            float f2 = (1.5f - fFloatValue) * 0.05f;
            float f3 = f2 + 1.0f;
            this.f16878f.set(new float[]{1.0f, f2, f2, f2, 0.0f, f2, f3, f2, f2, 0.0f, f2, f2, f3, f2, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
            this.f16877e.setColorFilter(new ColorMatrixColorFilter(this.f16878f));
            int i2 = (int) (this.f16884l * ((fFloatValue * 0.2f) + 0.4f));
            if (this.f16887o) {
                int i3 = this.f16889q;
                this.f16880h.set(0, i3 + i2, getBounds().right, i3 - i2);
            } else {
                int i4 = this.f16889q;
                this.f16880h.set(0, i4 - i2, getBounds().right, i4 + i2);
            }
            this.f16879g.setScale(this.f16880h.width() / this.f16894v.getWidth(), this.f16880h.height() / this.f16894v.getHeight());
            Matrix matrix = this.f16879g;
            Rect rect = this.f16880h;
            matrix.postTranslate(rect.left, rect.top);
            canvas.drawBitmap(this.f16894v, this.f16879g, this.f16877e);
            this.f16879g.reset();
            return;
        }
        Log.e("ScanDrawable", "drawLight failed, light bitmap is null");
    }
}

package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

/* loaded from: classes3.dex */
public class ShadowDrawableWrapper extends DrawableWrapper {

    /* renamed from: j, reason: collision with root package name */
    static final double f13518j = Math.cos(Math.toRadians(45.0d));

    /* renamed from: a, reason: collision with root package name */
    final Paint f13519a;
    private boolean addPaddingForCorners;

    /* renamed from: b, reason: collision with root package name */
    final Paint f13520b;

    /* renamed from: c, reason: collision with root package name */
    final RectF f13521c;

    /* renamed from: d, reason: collision with root package name */
    float f13522d;
    private boolean dirty;

    /* renamed from: e, reason: collision with root package name */
    Path f13523e;

    /* renamed from: f, reason: collision with root package name */
    float f13524f;

    /* renamed from: g, reason: collision with root package name */
    float f13525g;

    /* renamed from: h, reason: collision with root package name */
    float f13526h;

    /* renamed from: i, reason: collision with root package name */
    float f13527i;
    private boolean printedShadowClipWarning;
    private float rotation;
    private final int shadowEndColor;
    private final int shadowMiddleColor;
    private final int shadowStartColor;

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f2, float f3, float f4) {
        super(drawable);
        this.dirty = true;
        this.addPaddingForCorners = true;
        this.printedShadowClipWarning = false;
        this.shadowStartColor = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.shadowMiddleColor = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.shadowEndColor = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        Paint paint = new Paint(5);
        this.f13519a = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f13522d = Math.round(f2);
        this.f13521c = new RectF();
        Paint paint2 = new Paint(paint);
        this.f13520b = paint2;
        paint2.setAntiAlias(false);
        setShadowSize(f3, f4);
    }

    private void buildComponents(Rect rect) {
        float f2 = this.f13525g;
        float f3 = 1.5f * f2;
        this.f13521c.set(rect.left + f2, rect.top + f3, rect.right - f2, rect.bottom - f3);
        Drawable wrappedDrawable = getWrappedDrawable();
        RectF rectF = this.f13521c;
        wrappedDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        buildShadowCorners();
    }

    private void buildShadowCorners() {
        float f2 = this.f13522d;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        float f3 = this.f13526h;
        rectF2.inset(-f3, -f3);
        Path path = this.f13523e;
        if (path == null) {
            this.f13523e = new Path();
        } else {
            path.reset();
        }
        this.f13523e.setFillType(Path.FillType.EVEN_ODD);
        this.f13523e.moveTo(-this.f13522d, 0.0f);
        this.f13523e.rLineTo(-this.f13526h, 0.0f);
        this.f13523e.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f13523e.arcTo(rectF, 270.0f, -90.0f, false);
        this.f13523e.close();
        float f4 = -rectF2.top;
        if (f4 > 0.0f) {
            float f5 = this.f13522d / f4;
            this.f13519a.setShader(new RadialGradient(0.0f, 0.0f, f4, new int[]{0, this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, f5, ((1.0f - f5) / 2.0f) + f5, 1.0f}, Shader.TileMode.CLAMP));
        }
        this.f13520b.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, new int[]{this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.f13520b.setAntiAlias(false);
    }

    public static float calculateHorizontalPadding(float f2, float f3, boolean z2) {
        return z2 ? (float) (f2 + ((1.0d - f13518j) * f3)) : f2;
    }

    public static float calculateVerticalPadding(float f2, float f3, boolean z2) {
        return z2 ? (float) ((f2 * 1.5f) + ((1.0d - f13518j) * f3)) : f2 * 1.5f;
    }

    private void drawShadow(Canvas canvas) {
        int i2;
        float f2;
        int i3;
        float f3;
        float f4;
        float f5;
        int iSave = canvas.save();
        canvas.rotate(this.rotation, this.f13521c.centerX(), this.f13521c.centerY());
        float f6 = this.f13522d;
        float f7 = (-f6) - this.f13526h;
        float f8 = f6 * 2.0f;
        boolean z2 = this.f13521c.width() - f8 > 0.0f;
        boolean z3 = this.f13521c.height() - f8 > 0.0f;
        float f9 = this.f13527i;
        float f10 = f6 / ((f9 - (0.5f * f9)) + f6);
        float f11 = f6 / ((f9 - (0.25f * f9)) + f6);
        float f12 = f6 / ((f9 - (f9 * 1.0f)) + f6);
        int iSave2 = canvas.save();
        RectF rectF = this.f13521c;
        canvas.translate(rectF.left + f6, rectF.top + f6);
        canvas.scale(f10, f11);
        canvas.drawPath(this.f13523e, this.f13519a);
        if (z2) {
            canvas.scale(1.0f / f10, 1.0f);
            i2 = iSave2;
            f2 = f12;
            i3 = iSave;
            f3 = f11;
            canvas.drawRect(0.0f, f7, this.f13521c.width() - f8, -this.f13522d, this.f13520b);
        } else {
            i2 = iSave2;
            f2 = f12;
            i3 = iSave;
            f3 = f11;
        }
        canvas.restoreToCount(i2);
        int iSave3 = canvas.save();
        RectF rectF2 = this.f13521c;
        canvas.translate(rectF2.right - f6, rectF2.bottom - f6);
        float f13 = f2;
        canvas.scale(f10, f13);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f13523e, this.f13519a);
        if (z2) {
            canvas.scale(1.0f / f10, 1.0f);
            f4 = f3;
            f5 = f13;
            canvas.drawRect(0.0f, f7, this.f13521c.width() - f8, (-this.f13522d) + this.f13526h, this.f13520b);
        } else {
            f4 = f3;
            f5 = f13;
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        RectF rectF3 = this.f13521c;
        canvas.translate(rectF3.left + f6, rectF3.bottom - f6);
        canvas.scale(f10, f5);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f13523e, this.f13519a);
        if (z3) {
            canvas.scale(1.0f / f5, 1.0f);
            canvas.drawRect(0.0f, f7, this.f13521c.height() - f8, -this.f13522d, this.f13520b);
        }
        canvas.restoreToCount(iSave4);
        int iSave5 = canvas.save();
        RectF rectF4 = this.f13521c;
        canvas.translate(rectF4.right - f6, rectF4.top + f6);
        float f14 = f4;
        canvas.scale(f10, f14);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f13523e, this.f13519a);
        if (z3) {
            canvas.scale(1.0f / f14, 1.0f);
            canvas.drawRect(0.0f, f7, this.f13521c.height() - f8, -this.f13522d, this.f13520b);
        }
        canvas.restoreToCount(iSave5);
        canvas.restoreToCount(i3);
    }

    private static int toEven(float f2) {
        int iRound = Math.round(f2);
        return iRound % 2 == 1 ? iRound - 1 : iRound;
    }

    public void draw(Canvas canvas) {
        if (this.dirty) {
            buildComponents(getBounds());
            this.dirty = false;
        }
        drawShadow(canvas);
        super.draw(canvas);
    }

    public float getCornerRadius() {
        return this.f13522d;
    }

    public float getMaxShadowSize() {
        return this.f13525g;
    }

    public float getMinHeight() {
        float f2 = this.f13525g;
        return (Math.max(f2, this.f13522d + ((f2 * 1.5f) / 2.0f)) * 2.0f) + (this.f13525g * 1.5f * 2.0f);
    }

    public float getMinWidth() {
        float f2 = this.f13525g;
        return (Math.max(f2, this.f13522d + (f2 / 2.0f)) * 2.0f) + (this.f13525g * 2.0f);
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int iCeil = (int) Math.ceil(calculateVerticalPadding(this.f13525g, this.f13522d, this.addPaddingForCorners));
        int iCeil2 = (int) Math.ceil(calculateHorizontalPadding(this.f13525g, this.f13522d, this.addPaddingForCorners));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    public float getShadowSize() {
        return this.f13527i;
    }

    public void setAddPaddingForCorners(boolean z2) {
        this.addPaddingForCorners = z2;
        invalidateSelf();
    }

    public void setAlpha(int i2) {
        super.setAlpha(i2);
        this.f13519a.setAlpha(i2);
        this.f13520b.setAlpha(i2);
    }

    public void setCornerRadius(float f2) {
        float fRound = Math.round(f2);
        if (this.f13522d == fRound) {
            return;
        }
        this.f13522d = fRound;
        this.dirty = true;
        invalidateSelf();
    }

    public void setMaxShadowSize(float f2) {
        setShadowSize(this.f13527i, f2);
    }

    public final void setRotation(float f2) {
        if (this.rotation != f2) {
            this.rotation = f2;
            invalidateSelf();
        }
    }

    public void setShadowSize(float f2, float f3) {
        if (f2 < 0.0f || f3 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float even = toEven(f2);
        float even2 = toEven(f3);
        if (even > even2) {
            if (!this.printedShadowClipWarning) {
                this.printedShadowClipWarning = true;
            }
            even = even2;
        }
        if (this.f13527i == even && this.f13525g == even2) {
            return;
        }
        this.f13527i = even;
        this.f13525g = even2;
        this.f13526h = Math.round(even * 1.5f);
        this.f13524f = even2;
        this.dirty = true;
        invalidateSelf();
    }

    public void setShadowSize(float f2) {
        setShadowSize(f2, this.f13525g);
    }
}

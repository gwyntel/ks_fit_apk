package com.google.android.material.bottomappbar;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* loaded from: classes3.dex */
public class BottomAppBarTopEdgeTreatment extends EdgeTreatment {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private float cradleVerticalOffset;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f2, float f3, float f4) {
        this.fabMargin = f2;
        this.roundedCornerRadius = f3;
        this.cradleVerticalOffset = f4;
        if (f4 < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.horizontalOffset = 0.0f;
    }

    float a() {
        return this.cradleVerticalOffset;
    }

    float b() {
        return this.fabMargin;
    }

    float c() {
        return this.roundedCornerRadius;
    }

    float d() {
        return this.fabDiameter;
    }

    float e() {
        return this.horizontalOffset;
    }

    void f(float f2) {
        this.cradleVerticalOffset = f2;
    }

    void g(float f2) {
        this.fabMargin = f2;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f2, float f3, ShapePath shapePath) {
        float f4 = this.fabDiameter;
        if (f4 == 0.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f5 = ((this.fabMargin * 2.0f) + f4) / 2.0f;
        float f6 = f3 * this.roundedCornerRadius;
        float f7 = (f2 / 2.0f) + this.horizontalOffset;
        float f8 = (this.cradleVerticalOffset * f3) + ((1.0f - f3) * f5);
        if (f8 / f5 >= 1.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f9 = f5 + f6;
        float f10 = f8 + f6;
        float fSqrt = (float) Math.sqrt((f9 * f9) - (f10 * f10));
        float f11 = f7 - fSqrt;
        float f12 = f7 + fSqrt;
        float degrees = (float) Math.toDegrees(Math.atan(fSqrt / f10));
        float f13 = 90.0f - degrees;
        float f14 = f11 - f6;
        shapePath.lineTo(f14, 0.0f);
        float f15 = f6 * 2.0f;
        shapePath.addArc(f14, 0.0f, f11 + f6, f15, 270.0f, degrees);
        shapePath.addArc(f7 - f5, (-f5) - f8, f7 + f5, f5 - f8, 180.0f - f13, (f13 * 2.0f) - 180.0f);
        shapePath.addArc(f12 - f6, 0.0f, f12 + f6, f15, 270.0f - degrees, degrees);
        shapePath.lineTo(f2, 0.0f);
    }

    void h(float f2) {
        this.roundedCornerRadius = f2;
    }

    void i(float f2) {
        this.fabDiameter = f2;
    }

    void j(float f2) {
        this.horizontalOffset = f2;
    }
}

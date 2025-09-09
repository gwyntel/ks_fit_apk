package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class VelocityMatrix {
    private static String TAG = "VelocityMatrix";

    /* renamed from: a, reason: collision with root package name */
    float f2775a;

    /* renamed from: b, reason: collision with root package name */
    float f2776b;

    /* renamed from: c, reason: collision with root package name */
    float f2777c;

    /* renamed from: d, reason: collision with root package name */
    float f2778d;

    /* renamed from: e, reason: collision with root package name */
    float f2779e;

    /* renamed from: f, reason: collision with root package name */
    float f2780f;

    public void applyTransform(float f2, float f3, int i2, int i3, float[] fArr) {
        float f4 = fArr[0];
        float f5 = fArr[1];
        float f6 = (f3 - 0.5f) * 2.0f;
        float f7 = f4 + this.f2777c;
        float f8 = f5 + this.f2778d;
        float f9 = f7 + (this.f2775a * (f2 - 0.5f) * 2.0f);
        float f10 = f8 + (this.f2776b * f6);
        float radians = (float) Math.toRadians(this.f2780f);
        float radians2 = (float) Math.toRadians(this.f2779e);
        double d2 = radians;
        double d3 = i3 * f6;
        float fSin = f9 + (((float) ((((-i2) * r7) * Math.sin(d2)) - (Math.cos(d2) * d3))) * radians2);
        float fCos = f10 + (radians2 * ((float) (((i2 * r7) * Math.cos(d2)) - (d3 * Math.sin(d2)))));
        fArr[0] = fSin;
        fArr[1] = fCos;
    }

    public void clear() {
        this.f2779e = 0.0f;
        this.f2778d = 0.0f;
        this.f2777c = 0.0f;
        this.f2776b = 0.0f;
        this.f2775a = 0.0f;
    }

    public void setRotationVelocity(SplineSet splineSet, float f2) {
        if (splineSet != null) {
            this.f2779e = splineSet.getSlope(f2);
            this.f2780f = splineSet.get(f2);
        }
    }

    public void setScaleVelocity(SplineSet splineSet, SplineSet splineSet2, float f2) {
        if (splineSet != null) {
            this.f2775a = splineSet.getSlope(f2);
        }
        if (splineSet2 != null) {
            this.f2776b = splineSet2.getSlope(f2);
        }
    }

    public void setTranslationVelocity(SplineSet splineSet, SplineSet splineSet2, float f2) {
        if (splineSet != null) {
            this.f2777c = splineSet.getSlope(f2);
        }
        if (splineSet2 != null) {
            this.f2778d = splineSet2.getSlope(f2);
        }
    }

    public void setRotationVelocity(KeyCycleOscillator keyCycleOscillator, float f2) {
        if (keyCycleOscillator != null) {
            this.f2779e = keyCycleOscillator.getSlope(f2);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f2) {
        if (keyCycleOscillator != null) {
            this.f2775a = keyCycleOscillator.getSlope(f2);
        }
        if (keyCycleOscillator2 != null) {
            this.f2776b = keyCycleOscillator2.getSlope(f2);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f2) {
        if (keyCycleOscillator != null) {
            this.f2777c = keyCycleOscillator.getSlope(f2);
        }
        if (keyCycleOscillator2 != null) {
            this.f2778d = keyCycleOscillator2.getSlope(f2);
        }
    }
}

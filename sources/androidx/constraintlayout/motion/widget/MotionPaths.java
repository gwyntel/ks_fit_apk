package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
class MotionPaths implements Comparable<MotionPaths> {
    public static final boolean DEBUG = false;
    public static final boolean OLD_WAY = false;
    public static final String TAG = "MotionPaths";

    /* renamed from: t, reason: collision with root package name */
    static String[] f3106t = {RequestParameters.POSITION, "x", "y", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "pathRotate"};

    /* renamed from: a, reason: collision with root package name */
    Easing f3107a;

    /* renamed from: c, reason: collision with root package name */
    float f3109c;

    /* renamed from: d, reason: collision with root package name */
    float f3110d;

    /* renamed from: e, reason: collision with root package name */
    float f3111e;

    /* renamed from: f, reason: collision with root package name */
    float f3112f;

    /* renamed from: g, reason: collision with root package name */
    float f3113g;

    /* renamed from: h, reason: collision with root package name */
    float f3114h;

    /* renamed from: k, reason: collision with root package name */
    int f3117k;

    /* renamed from: l, reason: collision with root package name */
    int f3118l;

    /* renamed from: m, reason: collision with root package name */
    float f3119m;

    /* renamed from: n, reason: collision with root package name */
    MotionController f3120n;

    /* renamed from: o, reason: collision with root package name */
    LinkedHashMap f3121o;

    /* renamed from: p, reason: collision with root package name */
    int f3122p;

    /* renamed from: q, reason: collision with root package name */
    int f3123q;

    /* renamed from: r, reason: collision with root package name */
    double[] f3124r;

    /* renamed from: s, reason: collision with root package name */
    double[] f3125s;

    /* renamed from: b, reason: collision with root package name */
    int f3108b = 0;

    /* renamed from: i, reason: collision with root package name */
    float f3115i = Float.NaN;

    /* renamed from: j, reason: collision with root package name */
    float f3116j = Float.NaN;

    public MotionPaths() {
        int i2 = Key.UNSET;
        this.f3117k = i2;
        this.f3118l = i2;
        this.f3119m = Float.NaN;
        this.f3120n = null;
        this.f3121o = new LinkedHashMap();
        this.f3122p = 0;
        this.f3124r = new double[18];
        this.f3125s = new double[18];
    }

    private boolean diff(float f2, float f3) {
        return (Float.isNaN(f2) || Float.isNaN(f3)) ? Float.isNaN(f2) != Float.isNaN(f3) : Math.abs(f2 - f3) > 1.0E-6f;
    }

    private static final float xRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return (((f6 - f4) * f3) - ((f7 - f5) * f2)) + f4;
    }

    private static final float yRotate(float f2, float f3, float f4, float f5, float f6, float f7) {
        return ((f6 - f4) * f2) + ((f7 - f5) * f3) + f5;
    }

    void a(MotionPaths motionPaths, boolean[] zArr, String[] strArr, boolean z2) {
        boolean zDiff = diff(this.f3111e, motionPaths.f3111e);
        boolean zDiff2 = diff(this.f3112f, motionPaths.f3112f);
        zArr[0] = zArr[0] | diff(this.f3110d, motionPaths.f3110d);
        boolean z3 = zDiff | zDiff2 | z2;
        zArr[1] = zArr[1] | z3;
        zArr[2] = z3 | zArr[2];
        zArr[3] = zArr[3] | diff(this.f3113g, motionPaths.f3113g);
        zArr[4] = diff(this.f3114h, motionPaths.f3114h) | zArr[4];
    }

    public void applyParameters(ConstraintSet.Constraint constraint) {
        this.f3107a = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        ConstraintSet.Motion motion = constraint.motion;
        this.f3117k = motion.mPathMotionArc;
        this.f3118l = motion.mAnimateRelativeTo;
        this.f3115i = motion.mPathRotate;
        this.f3108b = motion.mDrawPath;
        this.f3123q = motion.mAnimateCircleAngleTo;
        this.f3116j = constraint.propertySet.mProgress;
        this.f3119m = constraint.layout.circleAngle;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute != null && constraintAttribute.isContinuous()) {
                this.f3121o.put(str, constraintAttribute);
            }
        }
    }

    void b(double[] dArr, int[] iArr) {
        float[] fArr = {this.f3110d, this.f3111e, this.f3112f, this.f3113g, this.f3114h, this.f3115i};
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 < 6) {
                dArr[i2] = fArr[r2];
                i2++;
            }
        }
    }

    void c(double d2, int[] iArr, double[] dArr, float[] fArr, int i2) {
        float fSin = this.f3111e;
        float fCos = this.f3112f;
        float f2 = this.f3113g;
        float f3 = this.f3114h;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f4 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                fSin = f4;
            } else if (i4 == 2) {
                fCos = f4;
            } else if (i4 == 3) {
                f2 = f4;
            } else if (i4 == 4) {
                f3 = f4;
            }
        }
        MotionController motionController = this.f3120n;
        if (motionController != null) {
            float[] fArr2 = new float[2];
            motionController.getCenter(d2, fArr2, new float[2]);
            float f5 = fArr2[0];
            float f6 = fArr2[1];
            double d3 = f5;
            double d4 = fSin;
            double d5 = fCos;
            fSin = (float) ((d3 + (Math.sin(d5) * d4)) - (f2 / 2.0f));
            fCos = (float) ((f6 - (d4 * Math.cos(d5))) - (f3 / 2.0f));
        }
        fArr[i2] = fSin + (f2 / 2.0f) + 0.0f;
        fArr[i2 + 1] = fCos + (f3 / 2.0f) + 0.0f;
    }

    public void configureRelativeTo(MotionController motionController) {
        motionController.k(this.f3116j);
    }

    void d(double d2, int[] iArr, double[] dArr, float[] fArr, double[] dArr2, float[] fArr2) {
        float f2;
        float f3 = this.f3111e;
        float f4 = this.f3112f;
        float f5 = this.f3113g;
        float f6 = this.f3114h;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f11 = (float) dArr[i2];
            float f12 = (float) dArr2[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f3 = f11;
                f7 = f12;
            } else if (i3 == 2) {
                f4 = f11;
                f9 = f12;
            } else if (i3 == 3) {
                f5 = f11;
                f8 = f12;
            } else if (i3 == 4) {
                f6 = f11;
                f10 = f12;
            }
        }
        float f13 = 2.0f;
        float f14 = (f8 / 2.0f) + f7;
        float fCos = (f10 / 2.0f) + f9;
        MotionController motionController = this.f3120n;
        if (motionController != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motionController.getCenter(d2, fArr3, fArr4);
            float f15 = fArr3[0];
            float f16 = fArr3[1];
            float f17 = fArr4[0];
            float f18 = fArr4[1];
            double d3 = f3;
            double d4 = f4;
            f2 = f5;
            float fSin = (float) ((f15 + (Math.sin(d4) * d3)) - (f5 / 2.0f));
            float fCos2 = (float) ((f16 - (d3 * Math.cos(d4))) - (f6 / 2.0f));
            double d5 = f7;
            double d6 = f9;
            float fSin2 = (float) (f17 + (Math.sin(d4) * d5) + (Math.cos(d4) * d6));
            fCos = (float) ((f18 - (d5 * Math.cos(d4))) + (Math.sin(d4) * d6));
            f14 = fSin2;
            f3 = fSin;
            f4 = fCos2;
            f13 = 2.0f;
        } else {
            f2 = f5;
        }
        fArr[0] = f3 + (f2 / f13) + 0.0f;
        fArr[1] = f4 + (f6 / f13) + 0.0f;
        fArr2[0] = f14;
        fArr2[1] = fCos;
    }

    int e(String str, double[] dArr, int i2) {
        ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.f3121o.get(str);
        int i3 = 0;
        if (constraintAttribute == null) {
            return 0;
        }
        if (constraintAttribute.numberOfInterpolatedValues() == 1) {
            dArr[i2] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = constraintAttribute.numberOfInterpolatedValues();
        constraintAttribute.getValuesToInterpolate(new float[iNumberOfInterpolatedValues]);
        while (i3 < iNumberOfInterpolatedValues) {
            dArr[i2] = r2[i3];
            i3++;
            i2++;
        }
        return iNumberOfInterpolatedValues;
    }

    int f(String str) {
        ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.f3121o.get(str);
        if (constraintAttribute == null) {
            return 0;
        }
        return constraintAttribute.numberOfInterpolatedValues();
    }

    void g(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.f3111e;
        float fCos = this.f3112f;
        float f3 = this.f3113g;
        float f4 = this.f3114h;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            float f5 = (float) dArr[i3];
            int i4 = iArr[i3];
            if (i4 == 1) {
                f2 = f5;
            } else if (i4 == 2) {
                fCos = f5;
            } else if (i4 == 3) {
                f3 = f5;
            } else if (i4 == 4) {
                f4 = f5;
            }
        }
        MotionController motionController = this.f3120n;
        if (motionController != null) {
            float centerX = motionController.getCenterX();
            float centerY = this.f3120n.getCenterY();
            double d2 = f2;
            double d3 = fCos;
            float fSin = (float) ((centerX + (Math.sin(d3) * d2)) - (f3 / 2.0f));
            fCos = (float) ((centerY - (d2 * Math.cos(d3))) - (f4 / 2.0f));
            f2 = fSin;
        }
        float f6 = f3 + f2;
        float f7 = f4 + fCos;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        fArr[i2] = f2 + 0.0f;
        fArr[i2 + 1] = fCos + 0.0f;
        fArr[i2 + 2] = f6 + 0.0f;
        fArr[i2 + 3] = fCos + 0.0f;
        fArr[i2 + 4] = f6 + 0.0f;
        fArr[i2 + 5] = f7 + 0.0f;
        fArr[i2 + 6] = f2 + 0.0f;
        fArr[i2 + 7] = f7 + 0.0f;
    }

    boolean h(String str) {
        return this.f3121o.containsKey(str);
    }

    void i(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.f2997a / 100.0f;
        this.f3109c = f2;
        this.f3108b = keyPosition.f3005i;
        float f3 = Float.isNaN(keyPosition.f3006j) ? f2 : keyPosition.f3006j;
        float f4 = Float.isNaN(keyPosition.f3007k) ? f2 : keyPosition.f3007k;
        float f5 = motionPaths2.f3113g;
        float f6 = motionPaths.f3113g;
        float f7 = motionPaths2.f3114h;
        float f8 = motionPaths.f3114h;
        this.f3110d = this.f3109c;
        float f9 = motionPaths.f3111e;
        float f10 = motionPaths.f3112f;
        float f11 = (motionPaths2.f3111e + (f5 / 2.0f)) - ((f6 / 2.0f) + f9);
        float f12 = (motionPaths2.f3112f + (f7 / 2.0f)) - (f10 + (f8 / 2.0f));
        float f13 = ((f5 - f6) * f3) / 2.0f;
        this.f3111e = (int) ((f9 + (f11 * f2)) - f13);
        float f14 = ((f7 - f8) * f4) / 2.0f;
        this.f3112f = (int) ((f10 + (f12 * f2)) - f14);
        this.f3113g = (int) (f6 + r9);
        this.f3114h = (int) (f8 + r12);
        float f15 = Float.isNaN(keyPosition.f3008l) ? f2 : keyPosition.f3008l;
        float f16 = Float.isNaN(keyPosition.f3011o) ? 0.0f : keyPosition.f3011o;
        if (!Float.isNaN(keyPosition.f3009m)) {
            f2 = keyPosition.f3009m;
        }
        float f17 = Float.isNaN(keyPosition.f3010n) ? 0.0f : keyPosition.f3010n;
        this.f3122p = 0;
        this.f3111e = (int) (((motionPaths.f3111e + (f15 * f11)) + (f17 * f12)) - f13);
        this.f3112f = (int) (((motionPaths.f3112f + (f11 * f16)) + (f12 * f2)) - f14);
        this.f3107a = Easing.getInterpolator(keyPosition.f3003g);
        this.f3117k = keyPosition.f3004h;
    }

    void j(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.f2997a / 100.0f;
        this.f3109c = f2;
        this.f3108b = keyPosition.f3005i;
        float f3 = Float.isNaN(keyPosition.f3006j) ? f2 : keyPosition.f3006j;
        float f4 = Float.isNaN(keyPosition.f3007k) ? f2 : keyPosition.f3007k;
        float f5 = motionPaths2.f3113g - motionPaths.f3113g;
        float f6 = motionPaths2.f3114h - motionPaths.f3114h;
        this.f3110d = this.f3109c;
        if (!Float.isNaN(keyPosition.f3008l)) {
            f2 = keyPosition.f3008l;
        }
        float f7 = motionPaths.f3111e;
        float f8 = motionPaths.f3113g;
        float f9 = motionPaths.f3112f;
        float f10 = motionPaths.f3114h;
        float f11 = (motionPaths2.f3111e + (motionPaths2.f3113g / 2.0f)) - ((f8 / 2.0f) + f7);
        float f12 = (motionPaths2.f3112f + (motionPaths2.f3114h / 2.0f)) - ((f10 / 2.0f) + f9);
        float f13 = f11 * f2;
        float f14 = (f5 * f3) / 2.0f;
        this.f3111e = (int) ((f7 + f13) - f14);
        float f15 = f2 * f12;
        float f16 = (f6 * f4) / 2.0f;
        this.f3112f = (int) ((f9 + f15) - f16);
        this.f3113g = (int) (f8 + r7);
        this.f3114h = (int) (f10 + r8);
        float f17 = Float.isNaN(keyPosition.f3009m) ? 0.0f : keyPosition.f3009m;
        this.f3122p = 1;
        float f18 = (int) ((motionPaths.f3111e + f13) - f14);
        float f19 = (int) ((motionPaths.f3112f + f15) - f16);
        this.f3111e = f18 + ((-f12) * f17);
        this.f3112f = f19 + (f11 * f17);
        this.f3118l = this.f3118l;
        this.f3107a = Easing.getInterpolator(keyPosition.f3003g);
        this.f3117k = keyPosition.f3004h;
    }

    void k(int i2, int i3, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float fMin;
        float f2;
        float f3 = keyPosition.f2997a / 100.0f;
        this.f3109c = f3;
        this.f3108b = keyPosition.f3005i;
        this.f3122p = keyPosition.f3012p;
        float f4 = Float.isNaN(keyPosition.f3006j) ? f3 : keyPosition.f3006j;
        float f5 = Float.isNaN(keyPosition.f3007k) ? f3 : keyPosition.f3007k;
        float f6 = motionPaths2.f3113g;
        float f7 = motionPaths.f3113g;
        float f8 = motionPaths2.f3114h;
        float f9 = motionPaths.f3114h;
        this.f3110d = this.f3109c;
        this.f3113g = (int) (f7 + ((f6 - f7) * f4));
        this.f3114h = (int) (f9 + ((f8 - f9) * f5));
        int i4 = keyPosition.f3012p;
        if (i4 == 1) {
            float f10 = Float.isNaN(keyPosition.f3008l) ? f3 : keyPosition.f3008l;
            float f11 = motionPaths2.f3111e;
            float f12 = motionPaths.f3111e;
            this.f3111e = (f10 * (f11 - f12)) + f12;
            if (!Float.isNaN(keyPosition.f3009m)) {
                f3 = keyPosition.f3009m;
            }
            float f13 = motionPaths2.f3112f;
            float f14 = motionPaths.f3112f;
            this.f3112f = (f3 * (f13 - f14)) + f14;
        } else if (i4 != 2) {
            float f15 = Float.isNaN(keyPosition.f3008l) ? f3 : keyPosition.f3008l;
            float f16 = motionPaths2.f3111e;
            float f17 = motionPaths.f3111e;
            this.f3111e = (f15 * (f16 - f17)) + f17;
            if (!Float.isNaN(keyPosition.f3009m)) {
                f3 = keyPosition.f3009m;
            }
            float f18 = motionPaths2.f3112f;
            float f19 = motionPaths.f3112f;
            this.f3112f = (f3 * (f18 - f19)) + f19;
        } else {
            if (Float.isNaN(keyPosition.f3008l)) {
                float f20 = motionPaths2.f3111e;
                float f21 = motionPaths.f3111e;
                fMin = ((f20 - f21) * f3) + f21;
            } else {
                fMin = Math.min(f5, f4) * keyPosition.f3008l;
            }
            this.f3111e = fMin;
            if (Float.isNaN(keyPosition.f3009m)) {
                float f22 = motionPaths2.f3112f;
                float f23 = motionPaths.f3112f;
                f2 = (f3 * (f22 - f23)) + f23;
            } else {
                f2 = keyPosition.f3009m;
            }
            this.f3112f = f2;
        }
        this.f3118l = motionPaths.f3118l;
        this.f3107a = Easing.getInterpolator(keyPosition.f3003g);
        this.f3117k = keyPosition.f3004h;
    }

    void l(int i2, int i3, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = keyPosition.f2997a / 100.0f;
        this.f3109c = f2;
        this.f3108b = keyPosition.f3005i;
        float f3 = Float.isNaN(keyPosition.f3006j) ? f2 : keyPosition.f3006j;
        float f4 = Float.isNaN(keyPosition.f3007k) ? f2 : keyPosition.f3007k;
        float f5 = motionPaths2.f3113g;
        float f6 = motionPaths.f3113g;
        float f7 = motionPaths2.f3114h;
        float f8 = motionPaths.f3114h;
        this.f3110d = this.f3109c;
        float f9 = motionPaths.f3111e;
        float f10 = motionPaths.f3112f;
        float f11 = motionPaths2.f3111e + (f5 / 2.0f);
        float f12 = motionPaths2.f3112f + (f7 / 2.0f);
        float f13 = (f5 - f6) * f3;
        this.f3111e = (int) ((f9 + ((f11 - ((f6 / 2.0f) + f9)) * f2)) - (f13 / 2.0f));
        float f14 = (f7 - f8) * f4;
        this.f3112f = (int) ((f10 + ((f12 - (f10 + (f8 / 2.0f))) * f2)) - (f14 / 2.0f));
        this.f3113g = (int) (f6 + f13);
        this.f3114h = (int) (f8 + f14);
        this.f3122p = 2;
        if (!Float.isNaN(keyPosition.f3008l)) {
            this.f3111e = (int) (keyPosition.f3008l * ((int) (i2 - this.f3113g)));
        }
        if (!Float.isNaN(keyPosition.f3009m)) {
            this.f3112f = (int) (keyPosition.f3009m * ((int) (i3 - this.f3114h)));
        }
        this.f3118l = this.f3118l;
        this.f3107a = Easing.getInterpolator(keyPosition.f3003g);
        this.f3117k = keyPosition.f3004h;
    }

    void m(float f2, float f3, float f4, float f5) {
        this.f3111e = f2;
        this.f3112f = f3;
        this.f3113g = f4;
        this.f3114h = f5;
    }

    void n(float f2, float f3, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f8 = (float) dArr[i2];
            double d2 = dArr2[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                f4 = f8;
            } else if (i3 == 2) {
                f6 = f8;
            } else if (i3 == 3) {
                f5 = f8;
            } else if (i3 == 4) {
                f7 = f8;
            }
        }
        float f9 = f4 - ((0.0f * f5) / 2.0f);
        float f10 = f6 - ((0.0f * f7) / 2.0f);
        fArr[0] = (f9 * (1.0f - f2)) + (((f5 * 1.0f) + f9) * f2) + 0.0f;
        fArr[1] = (f10 * (1.0f - f3)) + (((f7 * 1.0f) + f10) * f3) + 0.0f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void o(float f2, View view, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3, boolean z2) {
        float f3;
        float f4;
        float f5 = this.f3111e;
        float f6 = this.f3112f;
        float f7 = this.f3113g;
        float f8 = this.f3114h;
        if (iArr.length != 0 && this.f3124r.length <= iArr[iArr.length - 1]) {
            int i2 = iArr[iArr.length - 1] + 1;
            this.f3124r = new double[i2];
            this.f3125s = new double[i2];
        }
        Arrays.fill(this.f3124r, Double.NaN);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            double[] dArr4 = this.f3124r;
            int i4 = iArr[i3];
            dArr4[i4] = dArr[i3];
            this.f3125s[i4] = dArr2[i3];
        }
        float f9 = Float.NaN;
        int i5 = 0;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        while (true) {
            double[] dArr5 = this.f3124r;
            if (i5 >= dArr5.length) {
                break;
            }
            if (Double.isNaN(dArr5[i5]) && (dArr3 == null || dArr3[i5] == 0.0d)) {
                f4 = f9;
            } else {
                double d2 = dArr3 != null ? dArr3[i5] : 0.0d;
                if (!Double.isNaN(this.f3124r[i5])) {
                    d2 = this.f3124r[i5] + d2;
                }
                f4 = f9;
                float f14 = (float) d2;
                float f15 = (float) this.f3125s[i5];
                if (i5 == 1) {
                    f9 = f4;
                    f10 = f15;
                    f5 = f14;
                } else if (i5 == 2) {
                    f9 = f4;
                    f11 = f15;
                    f6 = f14;
                } else if (i5 == 3) {
                    f9 = f4;
                    f12 = f15;
                    f7 = f14;
                } else if (i5 == 4) {
                    f9 = f4;
                    f13 = f15;
                    f8 = f14;
                } else if (i5 == 5) {
                    f9 = f14;
                }
                i5++;
            }
            f9 = f4;
            i5++;
        }
        float f16 = f9;
        MotionController motionController = this.f3120n;
        if (motionController != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motionController.getCenter(f2, fArr, fArr2);
            float f17 = fArr[0];
            float f18 = fArr[1];
            float f19 = fArr2[0];
            float f20 = fArr2[1];
            double d3 = f5;
            double d4 = f6;
            float fSin = (float) ((f17 + (Math.sin(d4) * d3)) - (f7 / 2.0f));
            f3 = f8;
            float fCos = (float) ((f18 - (Math.cos(d4) * d3)) - (f8 / 2.0f));
            double d5 = f10;
            double d6 = f11;
            float fSin2 = (float) (f19 + (Math.sin(d4) * d5) + (Math.cos(d4) * d3 * d6));
            float fCos2 = (float) ((f20 - (d5 * Math.cos(d4))) + (d3 * Math.sin(d4) * d6));
            if (dArr2.length >= 2) {
                dArr2[0] = fSin2;
                dArr2[1] = fCos2;
            }
            if (!Float.isNaN(f16)) {
                view.setRotation((float) (f16 + Math.toDegrees(Math.atan2(fCos2, fSin2))));
            }
            f5 = fSin;
            f6 = fCos;
        } else {
            f3 = f8;
            if (!Float.isNaN(f16)) {
                view.setRotation((float) (0.0f + f16 + Math.toDegrees(Math.atan2(f11 + (f13 / 2.0f), f10 + (f12 / 2.0f)))));
            }
        }
        if (view instanceof FloatLayout) {
            ((FloatLayout) view).layout(f5, f6, f7 + f5, f6 + f3);
            return;
        }
        float f21 = f5 + 0.5f;
        int i6 = (int) f21;
        float f22 = f6 + 0.5f;
        int i7 = (int) f22;
        int i8 = (int) (f21 + f7);
        int i9 = (int) (f22 + f3);
        int i10 = i8 - i6;
        int i11 = i9 - i7;
        if (i10 != view.getMeasuredWidth() || i11 != view.getMeasuredHeight() || z2) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), View.MeasureSpec.makeMeasureSpec(i11, 1073741824));
        }
        view.layout(i6, i7, i8, i9);
    }

    public void setupRelative(MotionController motionController, MotionPaths motionPaths) {
        double d2 = ((this.f3111e + (this.f3113g / 2.0f)) - motionPaths.f3111e) - (motionPaths.f3113g / 2.0f);
        double d3 = ((this.f3112f + (this.f3114h / 2.0f)) - motionPaths.f3112f) - (motionPaths.f3114h / 2.0f);
        this.f3120n = motionController;
        this.f3111e = (float) Math.hypot(d3, d2);
        if (Float.isNaN(this.f3119m)) {
            this.f3112f = (float) (Math.atan2(d3, d2) + 1.5707963267948966d);
        } else {
            this.f3112f = (float) Math.toRadians(this.f3119m);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull MotionPaths motionPaths) {
        return Float.compare(this.f3110d, motionPaths.f3110d);
    }

    public MotionPaths(int i2, int i3, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        int i4 = Key.UNSET;
        this.f3117k = i4;
        this.f3118l = i4;
        this.f3119m = Float.NaN;
        this.f3120n = null;
        this.f3121o = new LinkedHashMap();
        this.f3122p = 0;
        this.f3124r = new double[18];
        this.f3125s = new double[18];
        if (motionPaths.f3118l != Key.UNSET) {
            k(i2, i3, keyPosition, motionPaths, motionPaths2);
            return;
        }
        int i5 = keyPosition.f3012p;
        if (i5 == 1) {
            j(keyPosition, motionPaths, motionPaths2);
        } else if (i5 != 2) {
            i(keyPosition, motionPaths, motionPaths2);
        } else {
            l(i2, i3, keyPosition, motionPaths, motionPaths2);
        }
    }
}

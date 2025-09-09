package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class MotionPaths implements Comparable<MotionPaths> {
    public static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    public static final boolean OLD_WAY = false;
    public static final int PERPENDICULAR = 1;
    public static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";

    /* renamed from: t, reason: collision with root package name */
    static String[] f2613t = {RequestParameters.POSITION, "x", "y", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "pathRotate"};

    /* renamed from: a, reason: collision with root package name */
    Easing f2614a;

    /* renamed from: b, reason: collision with root package name */
    int f2615b;

    /* renamed from: c, reason: collision with root package name */
    float f2616c;

    /* renamed from: d, reason: collision with root package name */
    float f2617d;

    /* renamed from: e, reason: collision with root package name */
    float f2618e;

    /* renamed from: f, reason: collision with root package name */
    float f2619f;

    /* renamed from: g, reason: collision with root package name */
    float f2620g;

    /* renamed from: h, reason: collision with root package name */
    float f2621h;

    /* renamed from: i, reason: collision with root package name */
    float f2622i;

    /* renamed from: j, reason: collision with root package name */
    float f2623j;

    /* renamed from: k, reason: collision with root package name */
    int f2624k;

    /* renamed from: l, reason: collision with root package name */
    int f2625l;

    /* renamed from: m, reason: collision with root package name */
    float f2626m;

    /* renamed from: n, reason: collision with root package name */
    Motion f2627n;

    /* renamed from: o, reason: collision with root package name */
    HashMap f2628o;

    /* renamed from: p, reason: collision with root package name */
    int f2629p;

    /* renamed from: q, reason: collision with root package name */
    int f2630q;

    /* renamed from: r, reason: collision with root package name */
    double[] f2631r;

    /* renamed from: s, reason: collision with root package name */
    double[] f2632s;

    public MotionPaths() {
        this.f2615b = 0;
        this.f2622i = Float.NaN;
        this.f2623j = Float.NaN;
        this.f2624k = -1;
        this.f2625l = -1;
        this.f2626m = Float.NaN;
        this.f2627n = null;
        this.f2628o = new HashMap();
        this.f2629p = 0;
        this.f2631r = new double[18];
        this.f2632s = new double[18];
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
        boolean zDiff = diff(this.f2618e, motionPaths.f2618e);
        boolean zDiff2 = diff(this.f2619f, motionPaths.f2619f);
        zArr[0] = zArr[0] | diff(this.f2617d, motionPaths.f2617d);
        boolean z3 = zDiff | zDiff2 | z2;
        zArr[1] = zArr[1] | z3;
        zArr[2] = z3 | zArr[2];
        zArr[3] = zArr[3] | diff(this.f2620g, motionPaths.f2620g);
        zArr[4] = diff(this.f2621h, motionPaths.f2621h) | zArr[4];
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.f2614a = Easing.getInterpolator(motionWidget.f2634b.mTransitionEasing);
        MotionWidget.Motion motion = motionWidget.f2634b;
        this.f2624k = motion.mPathMotionArc;
        this.f2625l = motion.mAnimateRelativeTo;
        this.f2622i = motion.mPathRotate;
        this.f2615b = motion.mDrawPath;
        this.f2630q = motion.mAnimateCircleAngleTo;
        this.f2623j = motionWidget.f2635c.mProgress;
        this.f2626m = 0.0f;
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.f2628o.put(str, customAttribute);
            }
        }
    }

    void b(double[] dArr, int[] iArr) {
        float[] fArr = {this.f2617d, this.f2618e, this.f2619f, this.f2620g, this.f2621h, this.f2622i};
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 < 6) {
                dArr[i2] = fArr[r2];
                i2++;
            }
        }
    }

    void c(double d2, int[] iArr, double[] dArr, float[] fArr, int i2) {
        float fSin = this.f2618e;
        float fCos = this.f2619f;
        float f2 = this.f2620g;
        float f3 = this.f2621h;
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
        Motion motion = this.f2627n;
        if (motion != null) {
            float[] fArr2 = new float[2];
            motion.getCenter(d2, fArr2, new float[2]);
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

    public void configureRelativeTo(Motion motion) {
        motion.a(this.f2623j);
    }

    void d(double d2, int[] iArr, double[] dArr, float[] fArr, double[] dArr2, float[] fArr2) {
        float f2;
        float f3 = this.f2618e;
        float f4 = this.f2619f;
        float f5 = this.f2620g;
        float f6 = this.f2621h;
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
        Motion motion = this.f2627n;
        if (motion != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            motion.getCenter(d2, fArr3, fArr4);
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
        CustomVariable customVariable = (CustomVariable) this.f2628o.get(str);
        int i3 = 0;
        if (customVariable == null) {
            return 0;
        }
        if (customVariable.numberOfInterpolatedValues() == 1) {
            dArr[i2] = customVariable.getValueToInterpolate();
            return 1;
        }
        int iNumberOfInterpolatedValues = customVariable.numberOfInterpolatedValues();
        customVariable.getValuesToInterpolate(new float[iNumberOfInterpolatedValues]);
        while (i3 < iNumberOfInterpolatedValues) {
            dArr[i2] = r2[i3];
            i3++;
            i2++;
        }
        return iNumberOfInterpolatedValues;
    }

    int f(String str) {
        CustomVariable customVariable = (CustomVariable) this.f2628o.get(str);
        if (customVariable == null) {
            return 0;
        }
        return customVariable.numberOfInterpolatedValues();
    }

    void g(int[] iArr, double[] dArr, float[] fArr, int i2) {
        float f2 = this.f2618e;
        float fCos = this.f2619f;
        float f3 = this.f2620g;
        float f4 = this.f2621h;
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
        Motion motion = this.f2627n;
        if (motion != null) {
            float centerX = motion.getCenterX();
            float centerY = this.f2627n.getCenterY();
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
        return this.f2628o.containsKey(str);
    }

    void i(MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.f2616c = f2;
        this.f2615b = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.f2620g;
        float f6 = motionPaths.f2620g;
        float f7 = motionPaths2.f2621h;
        float f8 = motionPaths.f2621h;
        this.f2617d = this.f2616c;
        float f9 = motionPaths.f2618e;
        float f10 = motionPaths.f2619f;
        float f11 = (motionPaths2.f2618e + (f5 / 2.0f)) - ((f6 / 2.0f) + f9);
        float f12 = (motionPaths2.f2619f + (f7 / 2.0f)) - (f10 + (f8 / 2.0f));
        float f13 = ((f5 - f6) * f3) / 2.0f;
        this.f2618e = (int) ((f9 + (f11 * f2)) - f13);
        float f14 = ((f7 - f8) * f4) / 2.0f;
        this.f2619f = (int) ((f10 + (f12 * f2)) - f14);
        this.f2620g = (int) (f6 + r9);
        this.f2621h = (int) (f8 + r12);
        float f15 = Float.isNaN(motionKeyPosition.mPercentX) ? f2 : motionKeyPosition.mPercentX;
        float f16 = Float.isNaN(motionKeyPosition.mAltPercentY) ? 0.0f : motionKeyPosition.mAltPercentY;
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            f2 = motionKeyPosition.mPercentY;
        }
        float f17 = Float.isNaN(motionKeyPosition.mAltPercentX) ? 0.0f : motionKeyPosition.mAltPercentX;
        this.f2629p = 0;
        this.f2618e = (int) (((motionPaths.f2618e + (f15 * f11)) + (f17 * f12)) - f13);
        this.f2619f = (int) (((motionPaths.f2619f + (f11 * f16)) + (f12 * f2)) - f14);
        this.f2614a = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.f2624k = motionKeyPosition.mPathMotionArc;
    }

    void j(MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.f2616c = f2;
        this.f2615b = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.f2620g - motionPaths.f2620g;
        float f6 = motionPaths2.f2621h - motionPaths.f2621h;
        this.f2617d = this.f2616c;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            f2 = motionKeyPosition.mPercentX;
        }
        float f7 = motionPaths.f2618e;
        float f8 = motionPaths.f2620g;
        float f9 = motionPaths.f2619f;
        float f10 = motionPaths.f2621h;
        float f11 = (motionPaths2.f2618e + (motionPaths2.f2620g / 2.0f)) - ((f8 / 2.0f) + f7);
        float f12 = (motionPaths2.f2619f + (motionPaths2.f2621h / 2.0f)) - ((f10 / 2.0f) + f9);
        float f13 = f11 * f2;
        float f14 = (f5 * f3) / 2.0f;
        this.f2618e = (int) ((f7 + f13) - f14);
        float f15 = f2 * f12;
        float f16 = (f6 * f4) / 2.0f;
        this.f2619f = (int) ((f9 + f15) - f16);
        this.f2620g = (int) (f8 + r7);
        this.f2621h = (int) (f10 + r8);
        float f17 = Float.isNaN(motionKeyPosition.mPercentY) ? 0.0f : motionKeyPosition.mPercentY;
        this.f2629p = 1;
        float f18 = (int) ((motionPaths.f2618e + f13) - f14);
        float f19 = (int) ((motionPaths.f2619f + f15) - f16);
        this.f2618e = f18 + ((-f12) * f17);
        this.f2619f = f19 + (f11 * f17);
        this.f2625l = this.f2625l;
        this.f2614a = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.f2624k = motionKeyPosition.mPathMotionArc;
    }

    void k(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float fMin;
        float f2;
        float f3 = motionKeyPosition.mFramePosition / 100.0f;
        this.f2616c = f3;
        this.f2615b = motionKeyPosition.mDrawPath;
        this.f2629p = motionKeyPosition.mPositionType;
        float f4 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f3 : motionKeyPosition.mPercentWidth;
        float f5 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f3 : motionKeyPosition.mPercentHeight;
        float f6 = motionPaths2.f2620g;
        float f7 = motionPaths.f2620g;
        float f8 = motionPaths2.f2621h;
        float f9 = motionPaths.f2621h;
        this.f2617d = this.f2616c;
        this.f2620g = (int) (f7 + ((f6 - f7) * f4));
        this.f2621h = (int) (f9 + ((f8 - f9) * f5));
        int i4 = motionKeyPosition.mPositionType;
        if (i4 == 1) {
            float f10 = Float.isNaN(motionKeyPosition.mPercentX) ? f3 : motionKeyPosition.mPercentX;
            float f11 = motionPaths2.f2618e;
            float f12 = motionPaths.f2618e;
            this.f2618e = (f10 * (f11 - f12)) + f12;
            if (!Float.isNaN(motionKeyPosition.mPercentY)) {
                f3 = motionKeyPosition.mPercentY;
            }
            float f13 = motionPaths2.f2619f;
            float f14 = motionPaths.f2619f;
            this.f2619f = (f3 * (f13 - f14)) + f14;
        } else if (i4 != 2) {
            float f15 = Float.isNaN(motionKeyPosition.mPercentX) ? f3 : motionKeyPosition.mPercentX;
            float f16 = motionPaths2.f2618e;
            float f17 = motionPaths.f2618e;
            this.f2618e = (f15 * (f16 - f17)) + f17;
            if (!Float.isNaN(motionKeyPosition.mPercentY)) {
                f3 = motionKeyPosition.mPercentY;
            }
            float f18 = motionPaths2.f2619f;
            float f19 = motionPaths.f2619f;
            this.f2619f = (f3 * (f18 - f19)) + f19;
        } else {
            if (Float.isNaN(motionKeyPosition.mPercentX)) {
                float f20 = motionPaths2.f2618e;
                float f21 = motionPaths.f2618e;
                fMin = ((f20 - f21) * f3) + f21;
            } else {
                fMin = Math.min(f5, f4) * motionKeyPosition.mPercentX;
            }
            this.f2618e = fMin;
            if (Float.isNaN(motionKeyPosition.mPercentY)) {
                float f22 = motionPaths2.f2619f;
                float f23 = motionPaths.f2619f;
                f2 = (f3 * (f22 - f23)) + f23;
            } else {
                f2 = motionKeyPosition.mPercentY;
            }
            this.f2619f = f2;
        }
        this.f2625l = motionPaths.f2625l;
        this.f2614a = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.f2624k = motionKeyPosition.mPathMotionArc;
    }

    void l(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f2 = motionKeyPosition.mFramePosition / 100.0f;
        this.f2616c = f2;
        this.f2615b = motionKeyPosition.mDrawPath;
        float f3 = Float.isNaN(motionKeyPosition.mPercentWidth) ? f2 : motionKeyPosition.mPercentWidth;
        float f4 = Float.isNaN(motionKeyPosition.mPercentHeight) ? f2 : motionKeyPosition.mPercentHeight;
        float f5 = motionPaths2.f2620g;
        float f6 = motionPaths.f2620g;
        float f7 = motionPaths2.f2621h;
        float f8 = motionPaths.f2621h;
        this.f2617d = this.f2616c;
        float f9 = motionPaths.f2618e;
        float f10 = motionPaths.f2619f;
        float f11 = motionPaths2.f2618e + (f5 / 2.0f);
        float f12 = motionPaths2.f2619f + (f7 / 2.0f);
        float f13 = (f5 - f6) * f3;
        this.f2618e = (int) ((f9 + ((f11 - ((f6 / 2.0f) + f9)) * f2)) - (f13 / 2.0f));
        float f14 = (f7 - f8) * f4;
        this.f2619f = (int) ((f10 + ((f12 - (f10 + (f8 / 2.0f))) * f2)) - (f14 / 2.0f));
        this.f2620g = (int) (f6 + f13);
        this.f2621h = (int) (f8 + f14);
        this.f2629p = 2;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            this.f2618e = (int) (motionKeyPosition.mPercentX * ((int) (i2 - this.f2620g)));
        }
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            this.f2619f = (int) (motionKeyPosition.mPercentY * ((int) (i3 - this.f2621h)));
        }
        this.f2625l = this.f2625l;
        this.f2614a = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.f2624k = motionKeyPosition.mPathMotionArc;
    }

    void m(float f2, float f3, float f4, float f5) {
        this.f2618e = f2;
        this.f2619f = f3;
        this.f2620g = f4;
        this.f2621h = f5;
    }

    void n(float f2, MotionWidget motionWidget, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3) {
        float f3;
        float f4;
        float f5 = this.f2618e;
        float f6 = this.f2619f;
        float f7 = this.f2620g;
        float f8 = this.f2621h;
        if (iArr.length != 0 && this.f2631r.length <= iArr[iArr.length - 1]) {
            int i2 = iArr[iArr.length - 1] + 1;
            this.f2631r = new double[i2];
            this.f2632s = new double[i2];
        }
        Arrays.fill(this.f2631r, Double.NaN);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            double[] dArr4 = this.f2631r;
            int i4 = iArr[i3];
            dArr4[i4] = dArr[i3];
            this.f2632s[i4] = dArr2[i3];
        }
        float f9 = Float.NaN;
        int i5 = 0;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        while (true) {
            double[] dArr5 = this.f2631r;
            if (i5 >= dArr5.length) {
                break;
            }
            if (Double.isNaN(dArr5[i5]) && (dArr3 == null || dArr3[i5] == 0.0d)) {
                f4 = f9;
            } else {
                double d2 = dArr3 != null ? dArr3[i5] : 0.0d;
                if (!Double.isNaN(this.f2631r[i5])) {
                    d2 = this.f2631r[i5] + d2;
                }
                f4 = f9;
                float f14 = (float) d2;
                float f15 = (float) this.f2632s[i5];
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
        Motion motion = this.f2627n;
        if (motion != null) {
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            motion.getCenter(f2, fArr, fArr2);
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
                motionWidget.setRotationZ((float) (f16 + Math.toDegrees(Math.atan2(fCos2, fSin2))));
            }
            f5 = fSin;
            f6 = fCos;
        } else {
            f3 = f8;
            if (!Float.isNaN(f16)) {
                motionWidget.setRotationZ((float) (0.0f + f16 + Math.toDegrees(Math.atan2(f11 + (f13 / 2.0f), f10 + (f12 / 2.0f)))));
            }
        }
        float f21 = f5 + 0.5f;
        float f22 = f6 + 0.5f;
        motionWidget.layout((int) f21, (int) f22, (int) (f21 + f7), (int) (f22 + f3));
    }

    public void setupRelative(Motion motion, MotionPaths motionPaths) {
        double d2 = ((this.f2618e + (this.f2620g / 2.0f)) - motionPaths.f2618e) - (motionPaths.f2620g / 2.0f);
        double d3 = ((this.f2619f + (this.f2621h / 2.0f)) - motionPaths.f2619f) - (motionPaths.f2621h / 2.0f);
        this.f2627n = motion;
        this.f2618e = (float) Math.hypot(d3, d2);
        if (Float.isNaN(this.f2626m)) {
            this.f2619f = (float) (Math.atan2(d3, d2) + 1.5707963267948966d);
        } else {
            this.f2619f = (float) Math.toRadians(this.f2626m);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.f2617d, motionPaths.f2617d);
    }

    public MotionPaths(int i2, int i3, MotionKeyPosition motionKeyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        this.f2615b = 0;
        this.f2622i = Float.NaN;
        this.f2623j = Float.NaN;
        this.f2624k = -1;
        this.f2625l = -1;
        this.f2626m = Float.NaN;
        this.f2627n = null;
        this.f2628o = new HashMap();
        this.f2629p = 0;
        this.f2631r = new double[18];
        this.f2632s = new double[18];
        if (motionPaths.f2625l != -1) {
            k(i2, i3, motionKeyPosition, motionPaths, motionPaths2);
            return;
        }
        int i4 = motionKeyPosition.mPositionType;
        if (i4 == 1) {
            j(motionKeyPosition, motionPaths, motionPaths2);
        } else if (i4 != 2) {
            i(motionKeyPosition, motionPaths, motionPaths2);
        } else {
            l(i2, i3, motionKeyPosition, motionPaths, motionPaths2);
        }
    }
}

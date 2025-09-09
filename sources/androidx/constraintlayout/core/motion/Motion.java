package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.ViewState;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Motion implements TypedValues {
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;

    /* renamed from: b, reason: collision with root package name */
    MotionWidget f2596b;

    /* renamed from: f, reason: collision with root package name */
    float f2600f;

    /* renamed from: g, reason: collision with root package name */
    float f2601g;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private MotionKeyTrigger[] mKeyTriggers;
    private CurveFit[] mSpline;
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;

    /* renamed from: a, reason: collision with root package name */
    Rect f2595a = new Rect();
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();

    /* renamed from: c, reason: collision with root package name */
    float f2597c = Float.NaN;

    /* renamed from: d, reason: collision with root package name */
    float f2598d = 0.0f;

    /* renamed from: e, reason: collision with root package name */
    float f2599e = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<MotionKey> mKeyList = new ArrayList<>();
    private int mPathMotionArc = -1;
    private int mTransformPivotTarget = -1;
    private MotionWidget mTransformPivotView = null;
    private int mQuantizeMotionSteps = -1;
    private float mQuantizeMotionPhase = Float.NaN;
    private DifferentialInterpolator mQuantizeMotionInterpolator = null;
    private boolean mNoMovement = false;

    public Motion(MotionWidget motionWidget) {
        setView(motionWidget);
    }

    private float getAdjustedPosition(float f2, float[] fArr) {
        float f3 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f4 = this.f2599e;
            if (f4 != 1.0d) {
                float f5 = this.f2598d;
                if (f2 < f5) {
                    f2 = 0.0f;
                }
                if (f2 > f5 && f2 < 1.0d) {
                    f2 = Math.min((f2 - f5) * f4, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.f2614a;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        float f6 = Float.NaN;
        while (it.hasNext()) {
            MotionPaths next = it.next();
            Easing easing2 = next.f2614a;
            if (easing2 != null) {
                float f7 = next.f2616c;
                if (f7 < f2) {
                    easing = easing2;
                    f3 = f7;
                } else if (Float.isNaN(f6)) {
                    f6 = next.f2616c;
                }
            }
        }
        if (easing != null) {
            float f8 = (Float.isNaN(f6) ? 1.0f : f6) - f3;
            double d2 = (f2 - f3) / f8;
            f2 = (((float) easing.get(d2)) * f8) + f3;
            if (fArr != null) {
                fArr[0] = (float) easing.getDiff(d2);
            }
        }
        return f2;
    }

    private static DifferentialInterpolator getInterpolator(int i2, String str, int i3) {
        if (i2 != -1) {
            return null;
        }
        final Easing interpolator = Easing.getInterpolator(str);
        return new DifferentialInterpolator() { // from class: androidx.constraintlayout.core.motion.Motion.1

            /* renamed from: a, reason: collision with root package name */
            float f2602a;

            @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
            public float getInterpolation(float f2) {
                this.f2602a = f2;
                return (float) interpolator.get(f2);
            }

            @Override // androidx.constraintlayout.core.motion.utils.DifferentialInterpolator
            public float getVelocity() {
                return (float) interpolator.getDiff(this.f2602a);
            }
        };
    }

    private float getPreCycleDistance() {
        char c2;
        float fHypot;
        float[] fArr = new float[2];
        float f2 = 1.0f / 99;
        double d2 = 0.0d;
        double d3 = 0.0d;
        float f3 = 0.0f;
        int i2 = 0;
        while (i2 < 100) {
            float f4 = i2 * f2;
            double d4 = f4;
            Easing easing = this.mStartMotionPath.f2614a;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f5 = Float.NaN;
            float f6 = 0.0f;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.f2614a;
                if (easing2 != null) {
                    float f7 = next.f2616c;
                    if (f7 < f4) {
                        easing = easing2;
                        f6 = f7;
                    } else if (Float.isNaN(f5)) {
                        f5 = next.f2616c;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f5)) {
                    f5 = 1.0f;
                }
                d4 = (((float) easing.get((f4 - f6) / r17)) * (f5 - f6)) + f6;
            }
            this.mSpline[0].getPos(d4, this.mInterpolateData);
            float f8 = f3;
            int i3 = i2;
            this.mStartMotionPath.c(d4, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
            if (i3 > 0) {
                c2 = 0;
                fHypot = (float) (f8 + Math.hypot(d3 - fArr[1], d2 - fArr[0]));
            } else {
                c2 = 0;
                fHypot = f8;
            }
            d2 = fArr[c2];
            i2 = i3 + 1;
            f3 = fHypot;
            d3 = fArr[1];
        }
        return f3;
    }

    private void insertKey(MotionPaths motionPaths) {
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        MotionPaths motionPaths2 = null;
        while (it.hasNext()) {
            MotionPaths next = it.next();
            if (motionPaths.f2617d == next.f2617d) {
                motionPaths2 = next;
            }
        }
        if (motionPaths2 != null) {
            this.mMotionPaths.remove(motionPaths2);
        }
        if (Collections.binarySearch(this.mMotionPaths, motionPaths) == 0) {
            Utils.loge(TAG, " KeyPath position \"" + motionPaths.f2617d + "\" outside of range");
        }
        this.mMotionPaths.add((-r0) - 1, motionPaths);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.m(this.f2596b.getX(), this.f2596b.getY(), this.f2596b.getWidth(), this.f2596b.getHeight());
    }

    double[] a(double d2) {
        this.mSpline[0].getPos(d2, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(d2, dArr);
            }
        }
        return this.mInterpolateData;
    }

    public void addKey(MotionKey motionKey) {
        this.mKeyList.add(motionKey);
    }

    public int buildKeyFrames(float[] fArr, int[] iArr, int[] iArr2) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                iArr[i2] = it.next().f2629p;
                i2++;
            }
        }
        if (iArr2 != null) {
            Iterator<MotionPaths> it2 = this.mMotionPaths.iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                iArr2[i3] = (int) (it2.next().f2617d * 100.0f);
                i3++;
            }
        }
        int i4 = 0;
        for (int i5 = 0; i5 < timePoints.length; i5++) {
            this.mSpline[0].getPos(timePoints[i5], this.mInterpolateData);
            this.mStartMotionPath.c(timePoints[i5], this.mInterpolateVariables, this.mInterpolateData, fArr, i4);
            i4 += 2;
        }
        return i4 / 2;
    }

    public void buildPath(float[] fArr, int i2) {
        double d2;
        float f2 = 1.0f;
        float f3 = 1.0f / (i2 - 1);
        HashMap<String, SplineSet> map = this.mAttributesMap;
        SplineSet splineSet = map == null ? null : map.get("translationX");
        HashMap<String, SplineSet> map2 = this.mAttributesMap;
        SplineSet splineSet2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, KeyCycleOscillator> map3 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, KeyCycleOscillator> map4 = this.mCycleMap;
        KeyCycleOscillator keyCycleOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i3 = 0;
        while (i3 < i2) {
            float fMin = i3 * f3;
            float f4 = this.f2599e;
            float f5 = 0.0f;
            if (f4 != f2) {
                float f6 = this.f2598d;
                if (fMin < f6) {
                    fMin = 0.0f;
                }
                if (fMin > f6 && fMin < 1.0d) {
                    fMin = Math.min((fMin - f6) * f4, f2);
                }
            }
            float f7 = fMin;
            double d3 = f7;
            Easing easing = this.mStartMotionPath.f2614a;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f8 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.f2614a;
                double d4 = d3;
                if (easing2 != null) {
                    float f9 = next.f2616c;
                    if (f9 < f7) {
                        f5 = f9;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.f2616c;
                    }
                }
                d3 = d4;
            }
            double d5 = d3;
            if (easing != null) {
                if (Float.isNaN(f8)) {
                    f8 = 1.0f;
                }
                d2 = (((float) easing.get((f7 - f5) / r16)) * (f8 - f5)) + f5;
            } else {
                d2 = d5;
            }
            this.mSpline[0].getPos(d2, this.mInterpolateData);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d2, dArr);
                }
            }
            int i4 = i3 * 2;
            int i5 = i3;
            this.mStartMotionPath.c(d2, this.mInterpolateVariables, this.mInterpolateData, fArr, i4);
            if (keyCycleOscillator != null) {
                fArr[i4] = fArr[i4] + keyCycleOscillator.get(f7);
            } else if (splineSet != null) {
                fArr[i4] = fArr[i4] + splineSet.get(f7);
            }
            if (keyCycleOscillator2 != null) {
                int i6 = i4 + 1;
                fArr[i6] = fArr[i6] + keyCycleOscillator2.get(f7);
            } else if (splineSet2 != null) {
                int i7 = i4 + 1;
                fArr[i7] = fArr[i7] + splineSet2.get(f7);
            }
            i3 = i5 + 1;
            f2 = 1.0f;
        }
    }

    public void buildRect(float f2, float[] fArr, int i2) {
        this.mSpline[0].getPos(getAdjustedPosition(f2, null), this.mInterpolateData);
        this.mStartMotionPath.g(this.mInterpolateVariables, this.mInterpolateData, fArr, i2);
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.f2625l;
    }

    public void getCenter(double d2, float[] fArr, float[] fArr2) {
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.mSpline[0].getPos(d2, dArr);
        this.mSpline[0].getSlope(d2, dArr2);
        Arrays.fill(fArr2, 0.0f);
        this.mStartMotionPath.d(d2, this.mInterpolateVariables, dArr, fArr, dArr2, fArr2);
    }

    public float getCenterX() {
        return this.f2600f;
    }

    public float getCenterY() {
        return this.f2601g;
    }

    public int getDrawPath() {
        int iMax = this.mStartMotionPath.f2615b;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().f2615b);
        }
        return Math.max(iMax, this.mEndMotionPath.f2615b);
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.f2621h;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.f2620g;
    }

    public float getFinalX() {
        return this.mEndMotionPath.f2618e;
    }

    public float getFinalY() {
        return this.mEndMotionPath.f2619f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public int getId(String str) {
        return 0;
    }

    public MotionPaths getKeyFrame(int i2) {
        return this.mMotionPaths.get(i2);
    }

    public int getKeyFrameInfo(int i2, int[] iArr) {
        float[] fArr = new float[2];
        Iterator<MotionKey> it = this.mKeyList.iterator();
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            MotionKey next = it.next();
            int i5 = next.mType;
            if (i5 == i2 || i2 != -1) {
                iArr[i4] = 0;
                iArr[i4 + 1] = i5;
                int i6 = next.mFramePosition;
                iArr[i4 + 2] = i6;
                double d2 = i6 / 100.0f;
                this.mSpline[0].getPos(d2, this.mInterpolateData);
                this.mStartMotionPath.c(d2, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                iArr[i4 + 3] = Float.floatToIntBits(fArr[0]);
                int i7 = i4 + 4;
                iArr[i7] = Float.floatToIntBits(fArr[1]);
                if (next instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) next;
                    iArr[i4 + 5] = motionKeyPosition.mPositionType;
                    iArr[i4 + 6] = Float.floatToIntBits(motionKeyPosition.mPercentX);
                    i7 = i4 + 7;
                    iArr[i7] = Float.floatToIntBits(motionKeyPosition.mPercentY);
                }
                int i8 = i7 + 1;
                iArr[i4] = i8 - i4;
                i3++;
                i4 = i8;
            }
        }
        return i3;
    }

    public int getKeyFramePositions(int[] iArr, float[] fArr) {
        Iterator<MotionKey> it = this.mKeyList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            MotionKey next = it.next();
            int i4 = next.mFramePosition;
            iArr[i2] = (next.mType * 1000) + i4;
            double d2 = i4 / 100.0f;
            this.mSpline[0].getPos(d2, this.mInterpolateData);
            this.mStartMotionPath.c(d2, this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
            i2++;
        }
        return i2;
    }

    public float getStartHeight() {
        return this.mStartMotionPath.f2621h;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.f2620g;
    }

    public float getStartX() {
        return this.mStartMotionPath.f2618e;
    }

    public float getStartY() {
        return this.mStartMotionPath.f2619f;
    }

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public MotionWidget getView() {
        return this.f2596b;
    }

    public boolean interpolate(MotionWidget motionWidget, float f2, long j2, KeyCache keyCache) {
        double d2;
        float adjustedPosition = getAdjustedPosition(f2, null);
        int i2 = this.mQuantizeMotionSteps;
        if (i2 != -1) {
            float f3 = 1.0f / i2;
            float fFloor = ((float) Math.floor(adjustedPosition / f3)) * f3;
            float f4 = (adjustedPosition % f3) / f3;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f4 = (f4 + this.mQuantizeMotionPhase) % 1.0f;
            }
            DifferentialInterpolator differentialInterpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((differentialInterpolator != null ? differentialInterpolator.getInterpolation(f4) : ((double) f4) > 0.5d ? 1.0f : 0.0f) * f3) + fFloor;
        }
        float f5 = adjustedPosition;
        HashMap<String, SplineSet> map = this.mAttributesMap;
        if (map != null) {
            Iterator<SplineSet> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(motionWidget, f5);
            }
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            double d3 = f5;
            curveFitArr[0].getPos(d3, this.mInterpolateData);
            this.mSpline[0].getSlope(d3, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(d3, dArr);
                    this.mArcSpline.getSlope(d3, this.mInterpolateVelocity);
                }
            }
            if (this.mNoMovement) {
                d2 = d3;
            } else {
                d2 = d3;
                this.mStartMotionPath.n(f5, motionWidget, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null);
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = motionWidget.getParent().findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float top2 = (r1.getTop() + this.mTransformPivotView.getBottom()) / 2.0f;
                    float left = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0f;
                    if (motionWidget.getRight() - motionWidget.getLeft() > 0 && motionWidget.getBottom() - motionWidget.getTop() > 0) {
                        motionWidget.setPivotX(left - motionWidget.getLeft());
                        motionWidget.setPivotY(top2 - motionWidget.getTop());
                    }
                }
            }
            int i3 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i3 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i3].getPos(d2, this.mValuesBuff);
                ((CustomVariable) this.mStartMotionPath.f2628o.get(this.mAttributeNames[i3 - 1])).setInterpolatedValue(motionWidget, this.mValuesBuff);
                i3++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.f2605a == 0) {
                if (f5 <= 0.0f) {
                    motionWidget.setVisibility(motionConstrainedPoint.f2606b);
                } else if (f5 >= 1.0f) {
                    motionWidget.setVisibility(this.mEndPoint.f2606b);
                } else if (this.mEndPoint.f2606b != motionConstrainedPoint.f2606b) {
                    motionWidget.setVisibility(4);
                }
            }
            if (this.mKeyTriggers != null) {
                int i4 = 0;
                while (true) {
                    MotionKeyTrigger[] motionKeyTriggerArr = this.mKeyTriggers;
                    if (i4 >= motionKeyTriggerArr.length) {
                        break;
                    }
                    motionKeyTriggerArr[i4].conditionallyFire(f5, motionWidget);
                    i4++;
                }
            }
        } else {
            MotionPaths motionPaths = this.mStartMotionPath;
            float f6 = motionPaths.f2618e;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float f7 = f6 + ((motionPaths2.f2618e - f6) * f5);
            float f8 = motionPaths.f2619f;
            float f9 = f8 + ((motionPaths2.f2619f - f8) * f5);
            float f10 = motionPaths.f2620g;
            float f11 = f10 + ((motionPaths2.f2620g - f10) * f5);
            float f12 = motionPaths.f2621h;
            float f13 = f7 + 0.5f;
            float f14 = f9 + 0.5f;
            motionWidget.layout((int) f13, (int) f14, (int) (f13 + f11), (int) (f14 + f12 + ((motionPaths2.f2621h - f12) * f5)));
        }
        HashMap<String, KeyCycleOscillator> map2 = this.mCycleMap;
        if (map2 == null) {
            return false;
        }
        for (KeyCycleOscillator keyCycleOscillator : map2.values()) {
            if (keyCycleOscillator instanceof KeyCycleOscillator.PathRotateSet) {
                double[] dArr2 = this.mInterpolateVelocity;
                ((KeyCycleOscillator.PathRotateSet) keyCycleOscillator).setPathRotate(motionWidget, f5, dArr2[0], dArr2[1]);
            } else {
                keyCycleOscillator.setProperty(motionWidget, f5);
            }
        }
        return false;
    }

    public void setDrawPath(int i2) {
        this.mStartMotionPath.f2615b = i2;
    }

    public void setEnd(MotionWidget motionWidget) {
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.f2616c = 1.0f;
        motionPaths.f2617d = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.m(motionWidget.getLeft(), motionWidget.getTop(), motionWidget.getWidth(), motionWidget.getHeight());
        this.mEndMotionPath.applyParameters(motionWidget);
        this.mEndPoint.setState(motionWidget);
    }

    public void setPathMotionArc(int i2) {
        this.mPathMotionArc = i2;
    }

    public void setStart(MotionWidget motionWidget) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f2616c = 0.0f;
        motionPaths.f2617d = 0.0f;
        motionPaths.m(motionWidget.getX(), motionWidget.getY(), motionWidget.getWidth(), motionWidget.getHeight());
        this.mStartMotionPath.applyParameters(motionWidget);
        this.mStartPoint.setState(motionWidget);
    }

    public void setStartState(ViewState viewState, MotionWidget motionWidget, int i2, int i3, int i4) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f2616c = 0.0f;
        motionPaths.f2617d = 0.0f;
        Rect rect = new Rect();
        if (i2 == 1) {
            int i5 = viewState.left + viewState.right;
            rect.left = ((viewState.f2781top + viewState.bottom) - viewState.width()) / 2;
            rect.f2725top = i3 - ((i5 + viewState.height()) / 2);
            rect.right = rect.left + viewState.width();
            rect.bottom = rect.f2725top + viewState.height();
        } else if (i2 == 2) {
            int i6 = viewState.left + viewState.right;
            rect.left = i4 - (((viewState.f2781top + viewState.bottom) + viewState.width()) / 2);
            rect.f2725top = (i6 - viewState.height()) / 2;
            rect.right = rect.left + viewState.width();
            rect.bottom = rect.f2725top + viewState.height();
        }
        this.mStartMotionPath.m(rect.left, rect.f2725top, rect.width(), rect.height());
        this.mStartPoint.setState(rect, motionWidget, i2, viewState.rotation);
    }

    public void setTransformPivotTarget(int i2) {
        this.mTransformPivotTarget = i2;
        this.mTransformPivotView = null;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        return false;
    }

    public void setView(MotionWidget motionWidget) {
        this.f2596b = motionWidget;
    }

    public void setup(int i2, int i3, float f2, long j2) {
        ArrayList arrayList;
        String[] strArr;
        int i4;
        int i5;
        CustomVariable customVariable;
        SplineSet splineSetMakeSpline;
        CustomVariable customVariable2;
        Integer num;
        SplineSet splineSetMakeSpline2;
        CustomVariable customVariable3;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        int i6 = this.mPathMotionArc;
        if (i6 != -1) {
            this.mStartMotionPath.f2624k = i6;
        }
        this.mStartPoint.a(this.mEndPoint, hashSet2);
        ArrayList<MotionKey> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            Iterator<MotionKey> it = arrayList2.iterator();
            arrayList = null;
            while (it.hasNext()) {
                MotionKey next = it.next();
                if (next instanceof MotionKeyPosition) {
                    MotionKeyPosition motionKeyPosition = (MotionKeyPosition) next;
                    insertKey(new MotionPaths(i2, i3, motionKeyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i7 = motionKeyPosition.mCurveFit;
                    if (i7 != -1) {
                        this.mCurveFitType = i7;
                    }
                } else if (next instanceof MotionKeyCycle) {
                    next.getAttributeNames(hashSet3);
                } else if (next instanceof MotionKeyTimeCycle) {
                    next.getAttributeNames(hashSet);
                } else if (next instanceof MotionKeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((MotionKeyTrigger) next);
                } else {
                    next.setInterpolation(map);
                    next.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.mKeyTriggers = (MotionKeyTrigger[]) arrayList.toArray(new MotionKeyTrigger[0]);
        }
        char c2 = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator<String> it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (next2.startsWith("CUSTOM,")) {
                    KeyFrameArray.CustomVar customVar = new KeyFrameArray.CustomVar();
                    String str = next2.split(",")[c2];
                    Iterator<MotionKey> it3 = this.mKeyList.iterator();
                    while (it3.hasNext()) {
                        MotionKey next3 = it3.next();
                        HashMap<String, CustomVariable> map2 = next3.mCustom;
                        if (map2 != null && (customVariable3 = map2.get(str)) != null) {
                            customVar.append(next3.mFramePosition, customVariable3);
                        }
                    }
                    splineSetMakeSpline2 = SplineSet.makeCustomSplineSet(next2, customVar);
                } else {
                    splineSetMakeSpline2 = SplineSet.makeSpline(next2, j2);
                }
                if (splineSetMakeSpline2 != null) {
                    splineSetMakeSpline2.setType(next2);
                    this.mAttributesMap.put(next2, splineSetMakeSpline2);
                }
                c2 = 1;
            }
            ArrayList<MotionKey> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                Iterator<MotionKey> it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    MotionKey next4 = it4.next();
                    if (next4 instanceof MotionKeyAttributes) {
                        next4.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str2 : this.mAttributesMap.keySet()) {
                int iIntValue = (!map.containsKey(str2) || (num = map.get(str2)) == null) ? 0 : num.intValue();
                SplineSet splineSet = this.mAttributesMap.get(str2);
                if (splineSet != null) {
                    splineSet.setup(iIntValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator<String> it5 = hashSet.iterator();
            while (it5.hasNext()) {
                String next5 = it5.next();
                if (!this.mTimeCycleAttributesMap.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        KeyFrameArray.CustomVar customVar2 = new KeyFrameArray.CustomVar();
                        String str3 = next5.split(",")[1];
                        Iterator<MotionKey> it6 = this.mKeyList.iterator();
                        while (it6.hasNext()) {
                            MotionKey next6 = it6.next();
                            HashMap<String, CustomVariable> map3 = next6.mCustom;
                            if (map3 != null && (customVariable2 = map3.get(str3)) != null) {
                                customVar2.append(next6.mFramePosition, customVariable2);
                            }
                        }
                        splineSetMakeSpline = SplineSet.makeCustomSplineSet(next5, customVar2);
                    } else {
                        splineSetMakeSpline = SplineSet.makeSpline(next5, j2);
                    }
                    if (splineSetMakeSpline != null) {
                        splineSetMakeSpline.setType(next5);
                    }
                }
            }
            ArrayList<MotionKey> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                Iterator<MotionKey> it7 = arrayList4.iterator();
                while (it7.hasNext()) {
                    MotionKey next7 = it7.next();
                    if (next7 instanceof MotionKeyTimeCycle) {
                        ((MotionKeyTimeCycle) next7).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str4 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str4).setup(map.containsKey(str4) ? map.get(str4).intValue() : 0);
            }
        }
        int size = this.mMotionPaths.size();
        int i8 = size + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[i8];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size + 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == MotionKey.UNSET) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it8 = this.mMotionPaths.iterator();
        int i9 = 1;
        while (it8.hasNext()) {
            motionPathsArr[i9] = it8.next();
            i9++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str5 : this.mEndMotionPath.f2628o.keySet()) {
            if (this.mStartMotionPath.f2628o.containsKey(str5)) {
                if (!hashSet2.contains("CUSTOM," + str5)) {
                    hashSet4.add(str5);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i10 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i10 >= strArr.length) {
                break;
            }
            String str6 = strArr[i10];
            this.mAttributeInterpolatorCount[i10] = 0;
            int i11 = 0;
            while (true) {
                if (i11 >= i8) {
                    break;
                }
                if (motionPathsArr[i11].f2628o.containsKey(str6) && (customVariable = (CustomVariable) motionPathsArr[i11].f2628o.get(str6)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i10] = iArr[i10] + customVariable.numberOfInterpolatedValues();
                    break;
                }
                i11++;
            }
            i10++;
        }
        boolean z2 = motionPathsArr[0].f2624k != -1;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i12 = 1; i12 < i8; i12++) {
            motionPathsArr[i12].a(motionPathsArr[i12 - 1], zArr, this.mAttributeNames, z2);
        }
        int i13 = 0;
        for (int i14 = 1; i14 < length; i14++) {
            if (zArr[i14]) {
                i13++;
            }
        }
        this.mInterpolateVariables = new int[i13];
        int i15 = 2;
        int iMax = Math.max(2, i13);
        this.mInterpolateData = new double[iMax];
        this.mInterpolateVelocity = new double[iMax];
        int i16 = 0;
        for (int i17 = 1; i17 < length; i17++) {
            if (zArr[i17]) {
                this.mInterpolateVariables[i16] = i17;
                i16++;
            }
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i8, this.mInterpolateVariables.length);
        double[] dArr2 = new double[i8];
        for (int i18 = 0; i18 < i8; i18++) {
            motionPathsArr[i18].b(dArr[i18], this.mInterpolateVariables);
            dArr2[i18] = motionPathsArr[i18].f2616c;
        }
        int i19 = 0;
        while (true) {
            int[] iArr2 = this.mInterpolateVariables;
            if (i19 >= iArr2.length) {
                break;
            }
            if (iArr2[i19] < MotionPaths.f2613t.length) {
                String str7 = MotionPaths.f2613t[this.mInterpolateVariables[i19]] + " [";
                for (int i20 = 0; i20 < i8; i20++) {
                    str7 = str7 + dArr[i20][i19];
                }
            }
            i19++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i21 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i21 >= strArr3.length) {
                break;
            }
            String str8 = strArr3[i21];
            int i22 = 0;
            int i23 = 0;
            double[] dArr3 = null;
            double[][] dArr4 = null;
            while (i22 < i8) {
                if (motionPathsArr[i22].h(str8)) {
                    if (dArr4 == null) {
                        dArr3 = new double[i8];
                        int[] iArr3 = new int[i15];
                        iArr3[1] = motionPathsArr[i22].f(str8);
                        i5 = 0;
                        iArr3[0] = i8;
                        dArr4 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iArr3);
                    } else {
                        i5 = 0;
                    }
                    MotionPaths motionPaths = motionPathsArr[i22];
                    i4 = i8;
                    dArr3[i23] = motionPaths.f2616c;
                    motionPaths.e(str8, dArr4[i23], i5);
                    i23++;
                } else {
                    i4 = i8;
                }
                i22++;
                i8 = i4;
                i15 = 2;
            }
            i21++;
            this.mSpline[i21] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr3, i23), (double[][]) Arrays.copyOf(dArr4, i23));
            i8 = i8;
            i15 = 2;
        }
        int i24 = i8;
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr2, dArr);
        if (motionPathsArr[0].f2624k != -1) {
            int[] iArr4 = new int[i24];
            double[] dArr5 = new double[i24];
            double[][] dArr6 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i24, 2);
            for (int i25 = 0; i25 < i24; i25++) {
                iArr4[i25] = motionPathsArr[i25].f2624k;
                dArr5[i25] = r7.f2616c;
                double[] dArr7 = dArr6[i25];
                dArr7[0] = r7.f2618e;
                dArr7[1] = r7.f2619f;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr5, dArr6);
        }
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator<String> it9 = hashSet3.iterator();
            float preCycleDistance = Float.NaN;
            while (it9.hasNext()) {
                String next8 = it9.next();
                KeyCycleOscillator keyCycleOscillatorMakeWidgetCycle = KeyCycleOscillator.makeWidgetCycle(next8);
                if (keyCycleOscillatorMakeWidgetCycle != null) {
                    if (keyCycleOscillatorMakeWidgetCycle.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    keyCycleOscillatorMakeWidgetCycle.setType(next8);
                    this.mCycleMap.put(next8, keyCycleOscillatorMakeWidgetCycle);
                }
            }
            Iterator<MotionKey> it10 = this.mKeyList.iterator();
            while (it10.hasNext()) {
                MotionKey next9 = it10.next();
                if (next9 instanceof MotionKeyCycle) {
                    ((MotionKeyCycle) next9).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<KeyCycleOscillator> it11 = this.mCycleMap.values().iterator();
            while (it11.hasNext()) {
                it11.next().setup(preCycleDistance);
            }
        }
    }

    public void setupRelative(Motion motion) {
        this.mStartMotionPath.setupRelative(motion, motion.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motion, motion.mEndMotionPath);
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.f2618e + " y: " + this.mStartMotionPath.f2619f + " end: x: " + this.mEndMotionPath.f2618e + " y: " + this.mEndMotionPath.f2619f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, boolean z2) {
        return false;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        if (i2 != 509) {
            return i2 == 704;
        }
        setPathMotionArc(i3);
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (705 == i2) {
            System.out.println("TYPE_INTERPOLATOR  " + str);
            this.mQuantizeMotionInterpolator = getInterpolator(-1, str, 0);
        }
        return false;
    }
}

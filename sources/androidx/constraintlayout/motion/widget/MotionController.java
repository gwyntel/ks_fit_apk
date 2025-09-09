package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionController {
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
    View f3031b;

    /* renamed from: c, reason: collision with root package name */
    int f3032c;

    /* renamed from: e, reason: collision with root package name */
    String f3034e;

    /* renamed from: i, reason: collision with root package name */
    float f3038i;

    /* renamed from: j, reason: collision with root package name */
    float f3039j;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, ViewSpline> mAttributesMap;
    private HashMap<String, ViewOscillator> mCycleMap;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private KeyTrigger[] mKeyTriggers;
    private boolean mNoMovement;
    private int mPathMotionArc;
    private Interpolator mQuantizeMotionInterpolator;
    private float mQuantizeMotionPhase;
    private int mQuantizeMotionSteps;
    private CurveFit[] mSpline;
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    private int mTransformPivotTarget;
    private View mTransformPivotView;

    /* renamed from: a, reason: collision with root package name */
    Rect f3030a = new Rect();

    /* renamed from: d, reason: collision with root package name */
    boolean f3033d = false;
    private int mCurveFitType = -1;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();

    /* renamed from: f, reason: collision with root package name */
    float f3035f = Float.NaN;

    /* renamed from: g, reason: collision with root package name */
    float f3036g = 0.0f;

    /* renamed from: h, reason: collision with root package name */
    float f3037h = 1.0f;
    private int MAX_DIMENSION = 4;
    private float[] mValuesBuff = new float[4];
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    private float[] mVelocity = new float[1];
    private ArrayList<Key> mKeyList = new ArrayList<>();

    MotionController(View view) {
        int i2 = Key.UNSET;
        this.mPathMotionArc = i2;
        this.mTransformPivotTarget = i2;
        this.mTransformPivotView = null;
        this.mQuantizeMotionSteps = i2;
        this.mQuantizeMotionPhase = Float.NaN;
        this.mQuantizeMotionInterpolator = null;
        this.mNoMovement = false;
        setView(view);
    }

    private float getAdjustedPosition(float f2, float[] fArr) {
        float f3 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f4 = this.f3037h;
            if (f4 != 1.0d) {
                float f5 = this.f3036g;
                if (f2 < f5) {
                    f2 = 0.0f;
                }
                if (f2 > f5 && f2 < 1.0d) {
                    f2 = Math.min((f2 - f5) * f4, 1.0f);
                }
            }
        }
        Easing easing = this.mStartMotionPath.f3107a;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        float f6 = Float.NaN;
        while (it.hasNext()) {
            MotionPaths next = it.next();
            Easing easing2 = next.f3107a;
            if (easing2 != null) {
                float f7 = next.f3109c;
                if (f7 < f2) {
                    easing = easing2;
                    f3 = f7;
                } else if (Float.isNaN(f6)) {
                    f6 = next.f3109c;
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

    private static Interpolator getInterpolator(Context context, int i2, String str, int i3) {
        if (i2 == -2) {
            return AnimationUtils.loadInterpolator(context, i3);
        }
        if (i2 == -1) {
            final Easing interpolator = Easing.getInterpolator(str);
            return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionController.1
                @Override // android.animation.TimeInterpolator
                public float getInterpolation(float f2) {
                    return (float) interpolator.get(f2);
                }
            };
        }
        if (i2 == 0) {
            return new AccelerateDecelerateInterpolator();
        }
        if (i2 == 1) {
            return new AccelerateInterpolator();
        }
        if (i2 == 2) {
            return new DecelerateInterpolator();
        }
        if (i2 == 4) {
            return new BounceInterpolator();
        }
        if (i2 != 5) {
            return null;
        }
        return new OvershootInterpolator();
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
            Easing easing = this.mStartMotionPath.f3107a;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f5 = Float.NaN;
            float f6 = 0.0f;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.f3107a;
                if (easing2 != null) {
                    float f7 = next.f3109c;
                    if (f7 < f4) {
                        easing = easing2;
                        f6 = f7;
                    } else if (Float.isNaN(f5)) {
                        f5 = next.f3109c;
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
        if (Collections.binarySearch(this.mMotionPaths, motionPaths) == 0) {
            Log.e(TAG, " KeyPath position \"" + motionPaths.f3110d + "\" outside of range");
        }
        this.mMotionPaths.add((-r0) - 1, motionPaths);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.m((int) this.f3031b.getX(), (int) this.f3031b.getY(), this.f3031b.getWidth(), this.f3031b.getHeight());
    }

    void a(ArrayList arrayList) {
        this.mKeyList.addAll(arrayList);
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    int b(float[] fArr, int[] iArr) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.mSpline[0].getTimePoints();
        if (iArr != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                iArr[i2] = it.next().f3122p;
                i2++;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < timePoints.length; i4++) {
            this.mSpline[0].getPos(timePoints[i4], this.mInterpolateData);
            this.mStartMotionPath.c(timePoints[i4], this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
        }
        return i3 / 2;
    }

    void c(float[] fArr, int i2) {
        double d2;
        float f2 = 1.0f;
        float f3 = 1.0f / (i2 - 1);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewOscillator> map3 = this.mCycleMap;
        ViewOscillator viewOscillator = map3 == null ? null : map3.get("translationX");
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map4 != null ? map4.get("translationY") : null;
        int i3 = 0;
        while (i3 < i2) {
            float fMin = i3 * f3;
            float f4 = this.f3037h;
            float f5 = 0.0f;
            if (f4 != f2) {
                float f6 = this.f3036g;
                if (fMin < f6) {
                    fMin = 0.0f;
                }
                if (fMin > f6 && fMin < 1.0d) {
                    fMin = Math.min((fMin - f6) * f4, f2);
                }
            }
            float f7 = fMin;
            double d3 = f7;
            Easing easing = this.mStartMotionPath.f3107a;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float f8 = Float.NaN;
            while (it.hasNext()) {
                MotionPaths next = it.next();
                Easing easing2 = next.f3107a;
                double d4 = d3;
                if (easing2 != null) {
                    float f9 = next.f3109c;
                    if (f9 < f7) {
                        f5 = f9;
                        easing = easing2;
                    } else if (Float.isNaN(f8)) {
                        f8 = next.f3109c;
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
            if (viewOscillator != null) {
                fArr[i4] = fArr[i4] + viewOscillator.get(f7);
            } else if (viewSpline != null) {
                fArr[i4] = fArr[i4] + viewSpline.get(f7);
            }
            if (viewOscillator2 != null) {
                int i6 = i4 + 1;
                fArr[i6] = fArr[i6] + viewOscillator2.get(f7);
            } else if (viewSpline2 != null) {
                int i7 = i4 + 1;
                fArr[i7] = fArr[i7] + viewSpline2.get(f7);
            }
            i3 = i5 + 1;
            f2 = 1.0f;
        }
    }

    void d(float f2, float[] fArr, int i2) {
        this.mSpline[0].getPos(getAdjustedPosition(f2, null), this.mInterpolateData);
        this.mStartMotionPath.g(this.mInterpolateVariables, this.mInterpolateData, fArr, i2);
    }

    void e(float[] fArr, int i2) {
        float f2 = 1.0f / (i2 - 1);
        for (int i3 = 0; i3 < i2; i3++) {
            this.mSpline[0].getPos(getAdjustedPosition(i3 * f2, null), this.mInterpolateData);
            this.mStartMotionPath.g(this.mInterpolateVariables, this.mInterpolateData, fArr, i3 * 8);
        }
    }

    void f(boolean z2) {
        if (!"button".equals(Debug.getName(this.f3031b)) || this.mKeyTriggers == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
            if (i2 >= keyTriggerArr.length) {
                return;
            }
            keyTriggerArr[i2].conditionallyFire(z2 ? -100.0f : 100.0f, this.f3031b);
            i2++;
        }
    }

    int g(String str, float[] fArr, int i2) {
        ViewSpline viewSpline = this.mAttributesMap.get(str);
        if (viewSpline == null) {
            return -1;
        }
        for (int i3 = 0; i3 < fArr.length; i3++) {
            fArr[i3] = viewSpline.get(i3 / (fArr.length - 1));
        }
        return fArr.length;
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.f3118l;
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
        return this.f3038i;
    }

    public float getCenterY() {
        return this.f3039j;
    }

    public int getDrawPath() {
        int iMax = this.mStartMotionPath.f3108b;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().f3108b);
        }
        return Math.max(iMax, this.mEndMotionPath.f3108b);
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.f3114h;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.f3113g;
    }

    public float getFinalX() {
        return this.mEndMotionPath.f3111e;
    }

    public float getFinalY() {
        return this.mEndMotionPath.f3112f;
    }

    public int getKeyFrameInfo(int i2, int[] iArr) {
        float[] fArr = new float[2];
        Iterator<Key> it = this.mKeyList.iterator();
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            int i5 = next.f3000d;
            if (i5 == i2 || i2 != -1) {
                iArr[i4] = 0;
                iArr[i4 + 1] = i5;
                int i6 = next.f2997a;
                iArr[i4 + 2] = i6;
                double d2 = i6 / 100.0f;
                this.mSpline[0].getPos(d2, this.mInterpolateData);
                this.mStartMotionPath.c(d2, this.mInterpolateVariables, this.mInterpolateData, fArr, 0);
                iArr[i4 + 3] = Float.floatToIntBits(fArr[0]);
                int i7 = i4 + 4;
                iArr[i7] = Float.floatToIntBits(fArr[1]);
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    iArr[i4 + 5] = keyPosition.f3012p;
                    iArr[i4 + 6] = Float.floatToIntBits(keyPosition.f3008l);
                    i7 = i4 + 7;
                    iArr[i7] = Float.floatToIntBits(keyPosition.f3009m);
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
        Iterator<Key> it = this.mKeyList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            int i4 = next.f2997a;
            iArr[i2] = (next.f3000d * 1000) + i4;
            double d2 = i4 / 100.0f;
            this.mSpline[0].getPos(d2, this.mInterpolateData);
            this.mStartMotionPath.c(d2, this.mInterpolateVariables, this.mInterpolateData, fArr, i3);
            i3 += 2;
            i2++;
        }
        return i2;
    }

    public float getStartHeight() {
        return this.mStartMotionPath.f3114h;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.f3113g;
    }

    public float getStartX() {
        return this.mStartMotionPath.f3111e;
    }

    public float getStartY() {
        return this.mStartMotionPath.f3112f;
    }

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public View getView() {
        return this.f3031b;
    }

    void h(float f2, float f3, float f4, float[] fArr) {
        double[] dArr;
        float adjustedPosition = getAdjustedPosition(f2, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        int i2 = 0;
        if (curveFitArr == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f5 = motionPaths.f3111e;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f6 = f5 - motionPaths2.f3111e;
            float f7 = motionPaths.f3112f - motionPaths2.f3112f;
            float f8 = (motionPaths.f3113g - motionPaths2.f3113g) + f6;
            float f9 = (motionPaths.f3114h - motionPaths2.f3114h) + f7;
            fArr[0] = (f6 * (1.0f - f3)) + (f8 * f3);
            fArr[1] = (f7 * (1.0f - f4)) + (f9 * f4);
            return;
        }
        double d2 = adjustedPosition;
        curveFitArr[0].getSlope(d2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d2, this.mInterpolateData);
        float f10 = this.mVelocity[0];
        while (true) {
            dArr = this.mInterpolateVelocity;
            if (i2 >= dArr.length) {
                break;
            }
            dArr[i2] = dArr[i2] * f10;
            i2++;
        }
        CurveFit curveFit = this.mArcSpline;
        if (curveFit == null) {
            this.mStartMotionPath.n(f3, f4, fArr, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        double[] dArr2 = this.mInterpolateData;
        if (dArr2.length > 0) {
            curveFit.getPos(d2, dArr2);
            this.mArcSpline.getSlope(d2, this.mInterpolateVelocity);
            this.mStartMotionPath.n(f3, f4, fArr, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
    }

    MotionPaths i(int i2) {
        return this.mMotionPaths.get(i2);
    }

    float j(int i2, float f2, float f3) {
        MotionPaths motionPaths = this.mEndMotionPath;
        float f4 = motionPaths.f3111e;
        MotionPaths motionPaths2 = this.mStartMotionPath;
        float f5 = motionPaths2.f3111e;
        float f6 = f4 - f5;
        float f7 = motionPaths.f3112f;
        float f8 = motionPaths2.f3112f;
        float f9 = f7 - f8;
        float f10 = f5 + (motionPaths2.f3113g / 2.0f);
        float f11 = f8 + (motionPaths2.f3114h / 2.0f);
        float fHypot = (float) Math.hypot(f6, f9);
        if (fHypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f12 = f2 - f10;
        float f13 = f3 - f11;
        if (((float) Math.hypot(f12, f13)) == 0.0f) {
            return 0.0f;
        }
        float f14 = (f12 * f6) + (f13 * f9);
        if (i2 == 0) {
            return f14 / fHypot;
        }
        if (i2 == 1) {
            return (float) Math.sqrt((fHypot * fHypot) - (f14 * f14));
        }
        if (i2 == 2) {
            return f12 / f6;
        }
        if (i2 == 3) {
            return f13 / f6;
        }
        if (i2 == 4) {
            return f12 / f9;
        }
        if (i2 != 5) {
            return 0.0f;
        }
        return f13 / f9;
    }

    double[] k(double d2) {
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

    KeyPositionBase l(int i2, int i3, float f2, float f3) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f4 = motionPaths.f3111e;
        rectF.left = f4;
        float f5 = motionPaths.f3112f;
        rectF.top = f5;
        rectF.right = f4 + motionPaths.f3113g;
        rectF.bottom = f5 + motionPaths.f3114h;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f6 = motionPaths2.f3111e;
        rectF2.left = f6;
        float f7 = motionPaths2.f3112f;
        rectF2.top = f7;
        rectF2.right = f6 + motionPaths2.f3113g;
        rectF2.bottom = f7 + motionPaths2.f3114h;
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key next = it.next();
            if (next instanceof KeyPositionBase) {
                KeyPositionBase keyPositionBase = (KeyPositionBase) next;
                if (keyPositionBase.intersects(i2, i3, rectF, rectF2, f2, f3)) {
                    return keyPositionBase;
                }
            }
        }
        return null;
    }

    void m(float f2, int i2, int i3, float f3, float f4, float[] fArr) {
        float adjustedPosition = getAdjustedPosition(f2, this.mVelocity);
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        ViewSpline viewSpline = map == null ? null : map.get("translationX");
        HashMap<String, ViewSpline> map2 = this.mAttributesMap;
        ViewSpline viewSpline2 = map2 == null ? null : map2.get("translationY");
        HashMap<String, ViewSpline> map3 = this.mAttributesMap;
        ViewSpline viewSpline3 = map3 == null ? null : map3.get(Key.ROTATION);
        HashMap<String, ViewSpline> map4 = this.mAttributesMap;
        ViewSpline viewSpline4 = map4 == null ? null : map4.get("scaleX");
        HashMap<String, ViewSpline> map5 = this.mAttributesMap;
        ViewSpline viewSpline5 = map5 == null ? null : map5.get("scaleY");
        HashMap<String, ViewOscillator> map6 = this.mCycleMap;
        ViewOscillator viewOscillator = map6 == null ? null : map6.get("translationX");
        HashMap<String, ViewOscillator> map7 = this.mCycleMap;
        ViewOscillator viewOscillator2 = map7 == null ? null : map7.get("translationY");
        HashMap<String, ViewOscillator> map8 = this.mCycleMap;
        ViewOscillator viewOscillator3 = map8 == null ? null : map8.get(Key.ROTATION);
        HashMap<String, ViewOscillator> map9 = this.mCycleMap;
        ViewOscillator viewOscillator4 = map9 == null ? null : map9.get("scaleX");
        HashMap<String, ViewOscillator> map10 = this.mCycleMap;
        ViewOscillator viewOscillator5 = map10 != null ? map10.get("scaleY") : null;
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
        velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
        velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
        velocityMatrix.setScaleVelocity(viewOscillator4, viewOscillator5, adjustedPosition);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                double d2 = adjustedPosition;
                curveFit.getPos(d2, dArr);
                this.mArcSpline.getSlope(d2, this.mInterpolateVelocity);
                this.mStartMotionPath.n(f3, f4, fArr, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
            velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
            return;
        }
        int i4 = 0;
        if (this.mSpline == null) {
            MotionPaths motionPaths = this.mEndMotionPath;
            float f5 = motionPaths.f3111e;
            MotionPaths motionPaths2 = this.mStartMotionPath;
            float f6 = f5 - motionPaths2.f3111e;
            ViewOscillator viewOscillator6 = viewOscillator5;
            float f7 = motionPaths.f3112f - motionPaths2.f3112f;
            ViewOscillator viewOscillator7 = viewOscillator4;
            float f8 = (motionPaths.f3113g - motionPaths2.f3113g) + f6;
            float f9 = (motionPaths.f3114h - motionPaths2.f3114h) + f7;
            fArr[0] = (f6 * (1.0f - f3)) + (f8 * f3);
            fArr[1] = (f7 * (1.0f - f4)) + (f9 * f4);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(viewSpline3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewSpline, viewSpline2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewSpline4, viewSpline5, adjustedPosition);
            velocityMatrix.setRotationVelocity(viewOscillator3, adjustedPosition);
            velocityMatrix.setTranslationVelocity(viewOscillator, viewOscillator2, adjustedPosition);
            velocityMatrix.setScaleVelocity(viewOscillator7, viewOscillator6, adjustedPosition);
            velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
            return;
        }
        double adjustedPosition2 = getAdjustedPosition(adjustedPosition, this.mVelocity);
        this.mSpline[0].getSlope(adjustedPosition2, this.mInterpolateVelocity);
        this.mSpline[0].getPos(adjustedPosition2, this.mInterpolateData);
        float f10 = this.mVelocity[0];
        while (true) {
            double[] dArr2 = this.mInterpolateVelocity;
            if (i4 >= dArr2.length) {
                this.mStartMotionPath.n(f3, f4, fArr, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                velocityMatrix.applyTransform(f3, f4, i2, i3, fArr);
                return;
            } else {
                dArr2[i4] = dArr2[i4] * f10;
                i4++;
            }
        }
    }

    boolean n(View view, float f2, long j2, KeyCache keyCache) {
        ViewTimeCycle.PathRotate pathRotate;
        boolean pathRotate2;
        int i2;
        double d2;
        float adjustedPosition = getAdjustedPosition(f2, null);
        int i3 = this.mQuantizeMotionSteps;
        if (i3 != Key.UNSET) {
            float f3 = 1.0f / i3;
            float fFloor = ((float) Math.floor(adjustedPosition / f3)) * f3;
            float f4 = (adjustedPosition % f3) / f3;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                f4 = (f4 + this.mQuantizeMotionPhase) % 1.0f;
            }
            Interpolator interpolator = this.mQuantizeMotionInterpolator;
            adjustedPosition = ((interpolator != null ? interpolator.getInterpolation(f4) : ((double) f4) > 0.5d ? 1.0f : 0.0f) * f3) + fFloor;
        }
        float f5 = adjustedPosition;
        HashMap<String, ViewSpline> map = this.mAttributesMap;
        if (map != null) {
            Iterator<ViewSpline> it = map.values().iterator();
            while (it.hasNext()) {
                it.next().setProperty(view, f5);
            }
        }
        HashMap<String, ViewTimeCycle> map2 = this.mTimeCycleAttributesMap;
        if (map2 != null) {
            ViewTimeCycle.PathRotate pathRotate3 = null;
            boolean property = false;
            for (ViewTimeCycle viewTimeCycle : map2.values()) {
                if (viewTimeCycle instanceof ViewTimeCycle.PathRotate) {
                    pathRotate3 = (ViewTimeCycle.PathRotate) viewTimeCycle;
                } else {
                    property |= viewTimeCycle.setProperty(view, f5, j2, keyCache);
                }
            }
            pathRotate2 = property;
            pathRotate = pathRotate3;
        } else {
            pathRotate = null;
            pathRotate2 = false;
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
                this.mStartMotionPath.o(f5, view, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null, this.f3033d);
                this.f3033d = false;
            }
            if (this.mTransformPivotTarget != Key.UNSET) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = ((View) view.getParent()).findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float top2 = (r1.getTop() + this.mTransformPivotView.getBottom()) / 2.0f;
                    float left = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0f;
                    if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                        view.setPivotX(left - view.getLeft());
                        view.setPivotY(top2 - view.getTop());
                    }
                }
            }
            HashMap<String, ViewSpline> map3 = this.mAttributesMap;
            if (map3 != null) {
                for (ViewSpline viewSpline : map3.values()) {
                    if (viewSpline instanceof ViewSpline.PathRotate) {
                        double[] dArr2 = this.mInterpolateVelocity;
                        if (dArr2.length > 1) {
                            ((ViewSpline.PathRotate) viewSpline).setPathRotate(view, f5, dArr2[0], dArr2[1]);
                        }
                    }
                }
            }
            if (pathRotate != null) {
                double[] dArr3 = this.mInterpolateVelocity;
                i2 = 1;
                pathRotate2 |= pathRotate.setPathRotate(view, keyCache, f5, j2, dArr3[0], dArr3[1]);
            } else {
                i2 = 1;
            }
            int i4 = i2;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i4 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i4].getPos(d2, this.mValuesBuff);
                CustomSupport.setInterpolatedValue((ConstraintAttribute) this.mStartMotionPath.f3121o.get(this.mAttributeNames[i4 - 1]), view, this.mValuesBuff);
                i4++;
            }
            MotionConstrainedPoint motionConstrainedPoint = this.mStartPoint;
            if (motionConstrainedPoint.f3022a == 0) {
                if (f5 <= 0.0f) {
                    view.setVisibility(motionConstrainedPoint.f3023b);
                } else if (f5 >= 1.0f) {
                    view.setVisibility(this.mEndPoint.f3023b);
                } else if (this.mEndPoint.f3023b != motionConstrainedPoint.f3023b) {
                    view.setVisibility(0);
                }
            }
            if (this.mKeyTriggers != null) {
                int i5 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.mKeyTriggers;
                    if (i5 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i5].conditionallyFire(f5, view);
                    i5++;
                }
            }
        } else {
            i2 = 1;
            MotionPaths motionPaths = this.mStartMotionPath;
            float f6 = motionPaths.f3111e;
            MotionPaths motionPaths2 = this.mEndMotionPath;
            float f7 = f6 + ((motionPaths2.f3111e - f6) * f5);
            float f8 = motionPaths.f3112f;
            float f9 = f8 + ((motionPaths2.f3112f - f8) * f5);
            float f10 = motionPaths.f3113g;
            float f11 = motionPaths2.f3113g;
            float f12 = motionPaths.f3114h;
            float f13 = motionPaths2.f3114h;
            float f14 = f7 + 0.5f;
            int i6 = (int) f14;
            float f15 = f9 + 0.5f;
            int i7 = (int) f15;
            int i8 = (int) (f14 + ((f11 - f10) * f5) + f10);
            int i9 = (int) (f15 + ((f13 - f12) * f5) + f12);
            int i10 = i8 - i6;
            int i11 = i9 - i7;
            if (f11 != f10 || f13 != f12 || this.f3033d) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i10, 1073741824), View.MeasureSpec.makeMeasureSpec(i11, 1073741824));
                this.f3033d = false;
            }
            view.layout(i6, i7, i8, i9);
        }
        HashMap<String, ViewOscillator> map4 = this.mCycleMap;
        if (map4 != null) {
            for (ViewOscillator viewOscillator : map4.values()) {
                if (viewOscillator instanceof ViewOscillator.PathRotateSet) {
                    double[] dArr4 = this.mInterpolateVelocity;
                    ((ViewOscillator.PathRotateSet) viewOscillator).setPathRotate(view, f5, dArr4[0], dArr4[i2]);
                } else {
                    viewOscillator.setProperty(view, f5);
                }
            }
        }
        return pathRotate2;
    }

    void o(View view, KeyPositionBase keyPositionBase, float f2, float f3, String[] strArr, float[] fArr) {
        RectF rectF = new RectF();
        MotionPaths motionPaths = this.mStartMotionPath;
        float f4 = motionPaths.f3111e;
        rectF.left = f4;
        float f5 = motionPaths.f3112f;
        rectF.top = f5;
        rectF.right = f4 + motionPaths.f3113g;
        rectF.bottom = f5 + motionPaths.f3114h;
        RectF rectF2 = new RectF();
        MotionPaths motionPaths2 = this.mEndMotionPath;
        float f6 = motionPaths2.f3111e;
        rectF2.left = f6;
        float f7 = motionPaths2.f3112f;
        rectF2.top = f7;
        rectF2.right = f6 + motionPaths2.f3113g;
        rectF2.bottom = f7 + motionPaths2.f3114h;
        keyPositionBase.positionAttributes(view, rectF, rectF2, f2, f3, strArr, fArr);
    }

    void p(Rect rect, Rect rect2, int i2, int i3, int i4) {
        if (i2 == 1) {
            int i5 = rect.left + rect.right;
            rect2.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            rect2.top = i4 - ((i5 + rect.height()) / 2);
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
            return;
        }
        if (i2 == 2) {
            int i6 = rect.left + rect.right;
            rect2.left = i3 - (((rect.top + rect.bottom) + rect.width()) / 2);
            rect2.top = (i6 - rect.height()) / 2;
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
            return;
        }
        if (i2 == 3) {
            int i7 = rect.left + rect.right;
            rect2.left = ((rect.height() / 2) + rect.top) - (i7 / 2);
            rect2.top = i4 - ((i7 + rect.height()) / 2);
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
            return;
        }
        if (i2 != 4) {
            return;
        }
        int i8 = rect.left + rect.right;
        rect2.left = i3 - (((rect.bottom + rect.top) + rect.width()) / 2);
        rect2.top = (i8 - rect.height()) / 2;
        rect2.right = rect2.left + rect.width();
        rect2.bottom = rect2.top + rect.height();
    }

    void q(View view) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f3109c = 0.0f;
        motionPaths.f3110d = 0.0f;
        this.mNoMovement = true;
        motionPaths.m(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mEndMotionPath.m(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mStartPoint.setState(view);
        this.mEndPoint.setState(view);
    }

    void r(Rect rect, ConstraintSet constraintSet, int i2, int i3) {
        int i4 = constraintSet.mRotate;
        if (i4 != 0) {
            p(rect, this.f3030a, i4, i2, i3);
            rect = this.f3030a;
        }
        MotionPaths motionPaths = this.mEndMotionPath;
        motionPaths.f3109c = 1.0f;
        motionPaths.f3110d = 1.0f;
        readView(motionPaths);
        this.mEndMotionPath.m(rect.left, rect.top, rect.width(), rect.height());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.f3032c));
        this.mEndPoint.setState(rect, constraintSet, i4, this.f3032c);
    }

    public void remeasure() {
        this.f3033d = true;
    }

    void s(View view) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f3109c = 0.0f;
        motionPaths.f3110d = 0.0f;
        motionPaths.m(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mStartPoint.setState(view);
    }

    public void setDrawPath(int i2) {
        this.mStartMotionPath.f3108b = i2;
    }

    public void setPathMotionArc(int i2) {
        this.mPathMotionArc = i2;
    }

    public void setStartState(ViewState viewState, View view, int i2, int i3, int i4) {
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f3109c = 0.0f;
        motionPaths.f3110d = 0.0f;
        Rect rect = new Rect();
        if (i2 == 1) {
            int i5 = viewState.left + viewState.right;
            rect.left = ((viewState.f2988top + viewState.bottom) - viewState.width()) / 2;
            rect.top = i3 - ((i5 + viewState.height()) / 2);
            rect.right = rect.left + viewState.width();
            rect.bottom = rect.top + viewState.height();
        } else if (i2 == 2) {
            int i6 = viewState.left + viewState.right;
            rect.left = i4 - (((viewState.f2988top + viewState.bottom) + viewState.width()) / 2);
            rect.top = (i6 - viewState.height()) / 2;
            rect.right = rect.left + viewState.width();
            rect.bottom = rect.top + viewState.height();
        }
        this.mStartMotionPath.m(rect.left, rect.top, rect.width(), rect.height());
        this.mStartPoint.setState(rect, view, i2, viewState.rotation);
    }

    public void setTransformPivotTarget(int i2) {
        this.mTransformPivotTarget = i2;
        this.mTransformPivotView = null;
    }

    public void setView(View view) {
        this.f3031b = view;
        this.f3032c = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            this.f3034e = ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void setup(int i2, int i3, float f2, long j2) {
        ArrayList arrayList;
        String[] strArr;
        double[][] dArr;
        ConstraintAttribute constraintAttribute;
        ViewTimeCycle viewTimeCycleMakeSpline;
        ConstraintAttribute constraintAttribute2;
        Integer num;
        ViewSpline viewSplineMakeSpline;
        ConstraintAttribute constraintAttribute3;
        new HashSet();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        HashMap<String, Integer> map = new HashMap<>();
        int i4 = this.mPathMotionArc;
        if (i4 != Key.UNSET) {
            this.mStartMotionPath.f3117k = i4;
        }
        this.mStartPoint.a(this.mEndPoint, hashSet2);
        ArrayList<Key> arrayList2 = this.mKeyList;
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            arrayList = null;
            while (it.hasNext()) {
                Key next = it.next();
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    insertKey(new MotionPaths(i2, i3, keyPosition, this.mStartMotionPath, this.mEndMotionPath));
                    int i5 = keyPosition.f3013f;
                    if (i5 != Key.UNSET) {
                        this.mCurveFitType = i5;
                    }
                } else if (next instanceof KeyCycle) {
                    next.getAttributeNames(hashSet3);
                } else if (next instanceof KeyTimeCycle) {
                    next.getAttributeNames(hashSet);
                } else if (next instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) next);
                } else {
                    next.setInterpolation(map);
                    next.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.mKeyTriggers = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        char c2 = 1;
        if (!hashSet2.isEmpty()) {
            this.mAttributesMap = new HashMap<>();
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                if (str.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str2 = str.split(",")[1];
                    Iterator<Key> it3 = this.mKeyList.iterator();
                    while (it3.hasNext()) {
                        Key next2 = it3.next();
                        HashMap map2 = next2.f3001e;
                        if (map2 != null && (constraintAttribute3 = (ConstraintAttribute) map2.get(str2)) != null) {
                            sparseArray.append(next2.f2997a, constraintAttribute3);
                        }
                    }
                    viewSplineMakeSpline = ViewSpline.makeCustomSpline(str, (SparseArray<ConstraintAttribute>) sparseArray);
                } else {
                    viewSplineMakeSpline = ViewSpline.makeSpline(str);
                }
                if (viewSplineMakeSpline != null) {
                    viewSplineMakeSpline.setType(str);
                    this.mAttributesMap.put(str, viewSplineMakeSpline);
                }
            }
            ArrayList<Key> arrayList3 = this.mKeyList;
            if (arrayList3 != null) {
                Iterator<Key> it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    Key next3 = it4.next();
                    if (next3 instanceof KeyAttributes) {
                        next3.addValues(this.mAttributesMap);
                    }
                }
            }
            this.mStartPoint.addValues(this.mAttributesMap, 0);
            this.mEndPoint.addValues(this.mAttributesMap, 100);
            for (String str3 : this.mAttributesMap.keySet()) {
                int iIntValue = (!map.containsKey(str3) || (num = map.get(str3)) == null) ? 0 : num.intValue();
                ViewSpline viewSpline = this.mAttributesMap.get(str3);
                if (viewSpline != null) {
                    viewSpline.setup(iIntValue);
                }
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap<>();
            }
            Iterator it5 = hashSet.iterator();
            while (it5.hasNext()) {
                String str4 = (String) it5.next();
                if (!this.mTimeCycleAttributesMap.containsKey(str4)) {
                    if (str4.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str5 = str4.split(",")[1];
                        Iterator<Key> it6 = this.mKeyList.iterator();
                        while (it6.hasNext()) {
                            Key next4 = it6.next();
                            HashMap map3 = next4.f3001e;
                            if (map3 != null && (constraintAttribute2 = (ConstraintAttribute) map3.get(str5)) != null) {
                                sparseArray2.append(next4.f2997a, constraintAttribute2);
                            }
                        }
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeCustomSpline(str4, sparseArray2);
                    } else {
                        viewTimeCycleMakeSpline = ViewTimeCycle.makeSpline(str4, j2);
                    }
                    if (viewTimeCycleMakeSpline != null) {
                        viewTimeCycleMakeSpline.setType(str4);
                        this.mTimeCycleAttributesMap.put(str4, viewTimeCycleMakeSpline);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.mKeyList;
            if (arrayList4 != null) {
                Iterator<Key> it7 = arrayList4.iterator();
                while (it7.hasNext()) {
                    Key next5 = it7.next();
                    if (next5 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) next5).addTimeValues(this.mTimeCycleAttributesMap);
                    }
                }
            }
            for (String str6 : this.mTimeCycleAttributesMap.keySet()) {
                this.mTimeCycleAttributesMap.get(str6).setup(map.containsKey(str6) ? map.get(str6).intValue() : 0);
            }
        }
        int size = this.mMotionPaths.size();
        int i6 = size + 2;
        MotionPaths[] motionPathsArr = new MotionPaths[i6];
        motionPathsArr[0] = this.mStartMotionPath;
        motionPathsArr[size + 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> it8 = this.mMotionPaths.iterator();
        int i7 = 1;
        while (it8.hasNext()) {
            motionPathsArr[i7] = it8.next();
            i7++;
        }
        HashSet hashSet4 = new HashSet();
        for (String str7 : this.mEndMotionPath.f3121o.keySet()) {
            if (this.mStartMotionPath.f3121o.containsKey(str7)) {
                if (!hashSet2.contains("CUSTOM," + str7)) {
                    hashSet4.add(str7);
                }
            }
        }
        String[] strArr2 = (String[]) hashSet4.toArray(new String[0]);
        this.mAttributeNames = strArr2;
        this.mAttributeInterpolatorCount = new int[strArr2.length];
        int i8 = 0;
        while (true) {
            strArr = this.mAttributeNames;
            if (i8 >= strArr.length) {
                break;
            }
            String str8 = strArr[i8];
            this.mAttributeInterpolatorCount[i8] = 0;
            int i9 = 0;
            while (true) {
                if (i9 >= i6) {
                    break;
                }
                if (motionPathsArr[i9].f3121o.containsKey(str8) && (constraintAttribute = (ConstraintAttribute) motionPathsArr[i9].f3121o.get(str8)) != null) {
                    int[] iArr = this.mAttributeInterpolatorCount;
                    iArr[i8] = iArr[i8] + constraintAttribute.numberOfInterpolatedValues();
                    break;
                }
                i9++;
            }
            i8++;
        }
        boolean z2 = motionPathsArr[0].f3117k != Key.UNSET;
        int length = 18 + strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i10 = 1; i10 < i6; i10++) {
            motionPathsArr[i10].a(motionPathsArr[i10 - 1], zArr, this.mAttributeNames, z2);
        }
        int i11 = 0;
        for (int i12 = 1; i12 < length; i12++) {
            if (zArr[i12]) {
                i11++;
            }
        }
        this.mInterpolateVariables = new int[i11];
        int i13 = 2;
        int iMax = Math.max(2, i11);
        this.mInterpolateData = new double[iMax];
        this.mInterpolateVelocity = new double[iMax];
        int i14 = 0;
        for (int i15 = 1; i15 < length; i15++) {
            if (zArr[i15]) {
                this.mInterpolateVariables[i14] = i15;
                i14++;
            }
        }
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i6, this.mInterpolateVariables.length);
        double[] dArr3 = new double[i6];
        for (int i16 = 0; i16 < i6; i16++) {
            motionPathsArr[i16].b(dArr2[i16], this.mInterpolateVariables);
            dArr3[i16] = motionPathsArr[i16].f3109c;
        }
        int i17 = 0;
        while (true) {
            int[] iArr2 = this.mInterpolateVariables;
            if (i17 >= iArr2.length) {
                break;
            }
            if (iArr2[i17] < MotionPaths.f3106t.length) {
                String str9 = MotionPaths.f3106t[this.mInterpolateVariables[i17]] + " [";
                for (int i18 = 0; i18 < i6; i18++) {
                    str9 = str9 + dArr2[i18][i17];
                }
            }
            i17++;
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        int i19 = 0;
        while (true) {
            String[] strArr3 = this.mAttributeNames;
            if (i19 >= strArr3.length) {
                break;
            }
            String str10 = strArr3[i19];
            int i20 = 0;
            int i21 = 0;
            double[] dArr4 = null;
            double[][] dArr5 = null;
            while (i20 < i6) {
                if (motionPathsArr[i20].h(str10)) {
                    if (dArr5 == null) {
                        dArr4 = new double[i6];
                        int[] iArr3 = new int[i13];
                        iArr3[c2] = motionPathsArr[i20].f(str10);
                        iArr3[0] = i6;
                        dArr5 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iArr3);
                    }
                    MotionPaths motionPaths = motionPathsArr[i20];
                    dArr = dArr2;
                    dArr4[i21] = motionPaths.f3109c;
                    motionPaths.e(str10, dArr5[i21], 0);
                    i21++;
                } else {
                    dArr = dArr2;
                }
                i20++;
                dArr2 = dArr;
                i13 = 2;
                c2 = 1;
            }
            i19++;
            this.mSpline[i19] = CurveFit.get(this.mCurveFitType, Arrays.copyOf(dArr4, i21), (double[][]) Arrays.copyOf(dArr5, i21));
            dArr2 = dArr2;
            i13 = 2;
            c2 = 1;
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, dArr3, dArr2);
        if (motionPathsArr[0].f3117k != Key.UNSET) {
            int[] iArr4 = new int[i6];
            double[] dArr6 = new double[i6];
            double[][] dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i6, 2);
            for (int i22 = 0; i22 < i6; i22++) {
                iArr4[i22] = motionPathsArr[i22].f3117k;
                dArr6[i22] = r9.f3109c;
                double[] dArr8 = dArr7[i22];
                dArr8[0] = r9.f3111e;
                dArr8[1] = r9.f3112f;
            }
            this.mArcSpline = CurveFit.getArc(iArr4, dArr6, dArr7);
        }
        this.mCycleMap = new HashMap<>();
        if (this.mKeyList != null) {
            Iterator it9 = hashSet3.iterator();
            float preCycleDistance = Float.NaN;
            while (it9.hasNext()) {
                String str11 = (String) it9.next();
                ViewOscillator viewOscillatorMakeSpline = ViewOscillator.makeSpline(str11);
                if (viewOscillatorMakeSpline != null) {
                    if (viewOscillatorMakeSpline.variesByPath() && Float.isNaN(preCycleDistance)) {
                        preCycleDistance = getPreCycleDistance();
                    }
                    viewOscillatorMakeSpline.setType(str11);
                    this.mCycleMap.put(str11, viewOscillatorMakeSpline);
                }
            }
            Iterator<Key> it10 = this.mKeyList.iterator();
            while (it10.hasNext()) {
                Key next6 = it10.next();
                if (next6 instanceof KeyCycle) {
                    ((KeyCycle) next6).addCycleValues(this.mCycleMap);
                }
            }
            Iterator<ViewOscillator> it11 = this.mCycleMap.values().iterator();
            while (it11.hasNext()) {
                it11.next().setup(preCycleDistance);
            }
        }
    }

    public void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    void t(Rect rect, ConstraintSet constraintSet, int i2, int i3) {
        int i4 = constraintSet.mRotate;
        if (i4 != 0) {
            p(rect, this.f3030a, i4, i2, i3);
        }
        MotionPaths motionPaths = this.mStartMotionPath;
        motionPaths.f3109c = 0.0f;
        motionPaths.f3110d = 0.0f;
        readView(motionPaths);
        this.mStartMotionPath.m(rect.left, rect.top, rect.width(), rect.height());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.f3032c);
        this.mStartMotionPath.applyParameters(parameters);
        this.f3035f = parameters.motion.mMotionStagger;
        this.mStartPoint.setState(rect, constraintSet, i4, this.f3032c);
        this.mTransformPivotTarget = parameters.transform.transformPivotTarget;
        ConstraintSet.Motion motion = parameters.motion;
        this.mQuantizeMotionSteps = motion.mQuantizeMotionSteps;
        this.mQuantizeMotionPhase = motion.mQuantizeMotionPhase;
        Context context = this.f3031b.getContext();
        ConstraintSet.Motion motion2 = parameters.motion;
        this.mQuantizeMotionInterpolator = getInterpolator(context, motion2.mQuantizeInterpolatorType, motion2.mQuantizeInterpolatorString, motion2.mQuantizeInterpolatorID);
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.f3111e + " y: " + this.mStartMotionPath.f3112f + " end: x: " + this.mEndMotionPath.f3111e + " y: " + this.mEndMotionPath.f3112f;
    }
}

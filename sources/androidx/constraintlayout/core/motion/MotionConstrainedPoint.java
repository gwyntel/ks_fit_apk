package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.Utils;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    public static final boolean DEBUG = false;
    public static final String TAG = "MotionPaths";

    /* renamed from: g, reason: collision with root package name */
    static String[] f2604g = {RequestParameters.POSITION, "x", "y", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "pathRotate"};

    /* renamed from: b, reason: collision with root package name */
    int f2606b;
    private float height;
    private Easing mKeyFrameEasing;
    private float position;
    private float width;

    /* renamed from: x, reason: collision with root package name */
    private float f2611x;

    /* renamed from: y, reason: collision with root package name */
    private float f2612y;
    private float alpha = 1.0f;

    /* renamed from: a, reason: collision with root package name */
    int f2605a = 0;
    private boolean applyElevation = false;
    private float elevation = 0.0f;
    private float rotation = 0.0f;
    private float rotationX = 0.0f;
    public float rotationY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float translationZ = 0.0f;
    private int mDrawPath = 0;
    private float mPathRotate = Float.NaN;
    private float mProgress = Float.NaN;
    private int mAnimateRelativeTo = -1;

    /* renamed from: c, reason: collision with root package name */
    LinkedHashMap f2607c = new LinkedHashMap();

    /* renamed from: d, reason: collision with root package name */
    int f2608d = 0;

    /* renamed from: e, reason: collision with root package name */
    double[] f2609e = new double[18];

    /* renamed from: f, reason: collision with root package name */
    double[] f2610f = new double[18];

    private boolean diff(float f2, float f3) {
        return (Float.isNaN(f2) || Float.isNaN(f3)) ? Float.isNaN(f2) != Float.isNaN(f3) : Math.abs(f2 - f3) > 1.0E-6f;
    }

    void a(MotionConstrainedPoint motionConstrainedPoint, HashSet hashSet) {
        if (diff(this.alpha, motionConstrainedPoint.alpha)) {
            hashSet.add("alpha");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("translationZ");
        }
        int i2 = this.f2606b;
        int i3 = motionConstrainedPoint.f2606b;
        if (i2 != i3 && this.f2605a == 0 && (i2 == 4 || i3 == 4)) {
            hashSet.add("alpha");
        }
        if (diff(this.rotation, motionConstrainedPoint.rotation)) {
            hashSet.add("rotationZ");
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(motionConstrainedPoint.mPathRotate)) {
            hashSet.add("pathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(motionConstrainedPoint.mProgress)) {
            hashSet.add("progress");
        }
        if (diff(this.rotationX, motionConstrainedPoint.rotationX)) {
            hashSet.add("rotationX");
        }
        if (diff(this.rotationY, motionConstrainedPoint.rotationY)) {
            hashSet.add("rotationY");
        }
        if (diff(this.mPivotX, motionConstrainedPoint.mPivotX)) {
            hashSet.add("pivotX");
        }
        if (diff(this.mPivotY, motionConstrainedPoint.mPivotY)) {
            hashSet.add("pivotY");
        }
        if (diff(this.scaleX, motionConstrainedPoint.scaleX)) {
            hashSet.add("scaleX");
        }
        if (diff(this.scaleY, motionConstrainedPoint.scaleY)) {
            hashSet.add("scaleY");
        }
        if (diff(this.translationX, motionConstrainedPoint.translationX)) {
            hashSet.add("translationX");
        }
        if (diff(this.translationY, motionConstrainedPoint.translationY)) {
            hashSet.add("translationY");
        }
        if (diff(this.translationZ, motionConstrainedPoint.translationZ)) {
            hashSet.add("translationZ");
        }
        if (diff(this.elevation, motionConstrainedPoint.elevation)) {
            hashSet.add("elevation");
        }
    }

    public void addValues(HashMap<String, SplineSet> map, int i2) {
        SplineSet splineSet;
        for (String str : map.keySet()) {
            splineSet = map.get(str);
            str.hashCode();
            switch (str) {
                case "rotationX":
                    splineSet.setPoint(i2, Float.isNaN(this.rotationX) ? 0.0f : this.rotationX);
                    break;
                case "rotationY":
                    splineSet.setPoint(i2, Float.isNaN(this.rotationY) ? 0.0f : this.rotationY);
                    break;
                case "rotationZ":
                    splineSet.setPoint(i2, Float.isNaN(this.rotation) ? 0.0f : this.rotation);
                    break;
                case "translationX":
                    splineSet.setPoint(i2, Float.isNaN(this.translationX) ? 0.0f : this.translationX);
                    break;
                case "translationY":
                    splineSet.setPoint(i2, Float.isNaN(this.translationY) ? 0.0f : this.translationY);
                    break;
                case "translationZ":
                    splineSet.setPoint(i2, Float.isNaN(this.translationZ) ? 0.0f : this.translationZ);
                    break;
                case "progress":
                    splineSet.setPoint(i2, Float.isNaN(this.mProgress) ? 0.0f : this.mProgress);
                    break;
                case "pivotX":
                    splineSet.setPoint(i2, Float.isNaN(this.mPivotX) ? 0.0f : this.mPivotX);
                    break;
                case "pivotY":
                    splineSet.setPoint(i2, Float.isNaN(this.mPivotY) ? 0.0f : this.mPivotY);
                    break;
                case "scaleX":
                    splineSet.setPoint(i2, Float.isNaN(this.scaleX) ? 1.0f : this.scaleX);
                    break;
                case "scaleY":
                    splineSet.setPoint(i2, Float.isNaN(this.scaleY) ? 1.0f : this.scaleY);
                    break;
                case "alpha":
                    splineSet.setPoint(i2, Float.isNaN(this.alpha) ? 1.0f : this.alpha);
                    break;
                case "pathRotate":
                    splineSet.setPoint(i2, Float.isNaN(this.mPathRotate) ? 0.0f : this.mPathRotate);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        if (this.f2607c.containsKey(str2)) {
                            CustomVariable customVariable = (CustomVariable) this.f2607c.get(str2);
                            if (splineSet instanceof SplineSet.CustomSpline) {
                                ((SplineSet.CustomSpline) splineSet).setPoint(i2, customVariable);
                                break;
                            } else {
                                Utils.loge("MotionPaths", str + " ViewSpline not a CustomSet frame = " + i2 + ", value" + customVariable.getValueToInterpolate() + splineSet);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        Utils.loge("MotionPaths", "UNKNOWN spline " + str);
                        break;
                    }
            }
        }
    }

    public void applyParameters(MotionWidget motionWidget) {
        this.f2606b = motionWidget.getVisibility();
        this.alpha = motionWidget.getVisibility() != 4 ? 0.0f : motionWidget.getAlpha();
        this.applyElevation = false;
        this.rotation = motionWidget.getRotationZ();
        this.rotationX = motionWidget.getRotationX();
        this.rotationY = motionWidget.getRotationY();
        this.scaleX = motionWidget.getScaleX();
        this.scaleY = motionWidget.getScaleY();
        this.mPivotX = motionWidget.getPivotX();
        this.mPivotY = motionWidget.getPivotY();
        this.translationX = motionWidget.getTranslationX();
        this.translationY = motionWidget.getTranslationY();
        this.translationZ = motionWidget.getTranslationZ();
        for (String str : motionWidget.getCustomAttributeNames()) {
            CustomVariable customAttribute = motionWidget.getCustomAttribute(str);
            if (customAttribute != null && customAttribute.isContinuous()) {
                this.f2607c.put(str, customAttribute);
            }
        }
    }

    void b(float f2, float f3, float f4, float f5) {
        this.f2611x = f2;
        this.f2612y = f3;
        this.width = f4;
        this.height = f5;
    }

    public void setState(MotionWidget motionWidget) {
        b(motionWidget.getX(), motionWidget.getY(), motionWidget.getWidth(), motionWidget.getHeight());
        applyParameters(motionWidget);
    }

    @Override // java.lang.Comparable
    public int compareTo(MotionConstrainedPoint motionConstrainedPoint) {
        return Float.compare(this.position, motionConstrainedPoint.position);
    }

    public void setState(Rect rect, MotionWidget motionWidget, int i2, float f2) {
        b(rect.left, rect.f2725top, rect.width(), rect.height());
        applyParameters(motionWidget);
        this.mPivotX = Float.NaN;
        this.mPivotY = Float.NaN;
        if (i2 == 1) {
            this.rotation = f2 - 90.0f;
        } else {
            if (i2 != 2) {
                return;
            }
            this.rotation = f2 + 90.0f;
        }
    }
}

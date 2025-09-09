package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public abstract class ViewTimeCycle extends TimeCycleSplineSet {
    private static final String TAG = "ViewTimeCycle";

    static class AlphaSet extends ViewTimeCycle {
        AlphaSet() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setAlpha(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    public static class CustomSet extends ViewTimeCycle {

        /* renamed from: l, reason: collision with root package name */
        String f2989l;

        /* renamed from: m, reason: collision with root package name */
        SparseArray f2990m;

        /* renamed from: n, reason: collision with root package name */
        SparseArray f2991n = new SparseArray();

        /* renamed from: o, reason: collision with root package name */
        float[] f2992o;

        /* renamed from: p, reason: collision with root package name */
        float[] f2993p;

        public CustomSet(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.f2989l = str.split(",")[1];
            this.f2990m = sparseArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            this.f2742a.getPos(f2, this.f2992o);
            float[] fArr = this.f2992o;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            long j3 = j2 - this.f2750i;
            if (Float.isNaN(this.f2751j)) {
                float floatValue = keyCache.getFloatValue(view, this.f2989l, 0);
                this.f2751j = floatValue;
                if (Float.isNaN(floatValue)) {
                    this.f2751j = 0.0f;
                }
            }
            float f5 = (float) ((this.f2751j + ((j3 * 1.0E-9d) * f3)) % 1.0d);
            this.f2751j = f5;
            this.f2750i = j2;
            float fA = a(f5);
            this.f2749h = false;
            int i2 = 0;
            while (true) {
                float[] fArr2 = this.f2993p;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.f2749h;
                float f6 = this.f2992o[i2];
                this.f2749h = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fA) + f4;
                i2++;
            }
            CustomSupport.setInterpolatedValue((ConstraintAttribute) this.f2990m.valueAt(0), view, this.f2993p);
            if (f3 != 0.0f) {
                this.f2749h = true;
            }
            return this.f2749h;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.f2990m.size();
            int iNumberOfInterpolatedValues = ((ConstraintAttribute) this.f2990m.valueAt(0)).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i3 = iNumberOfInterpolatedValues + 2;
            this.f2992o = new float[i3];
            this.f2993p = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.f2990m.keyAt(i4);
                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.f2990m.valueAt(i4);
                float[] fArr = (float[]) this.f2991n.valueAt(i4);
                dArr[i4] = iKeyAt * 0.01d;
                constraintAttribute.getValuesToInterpolate(this.f2992o);
                int i5 = 0;
                while (true) {
                    if (i5 < this.f2992o.length) {
                        dArr2[i4][i5] = r8[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNumberOfInterpolatedValues] = fArr[0];
                dArr3[iNumberOfInterpolatedValues + 1] = fArr[1];
            }
            this.f2742a = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, ConstraintAttribute constraintAttribute, float f2, int i3, float f3) {
            this.f2990m.append(i2, constraintAttribute);
            this.f2991n.append(i2, new float[]{f2, f3});
            this.f2743b = Math.max(this.f2743b, i3);
        }
    }

    static class ElevationSet extends ViewTimeCycle {
        ElevationSet() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setElevation(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    public static class PathRotate extends ViewTimeCycle {
        public boolean setPathRotate(View view, KeyCache keyCache, float f2, long j2, double d2, double d3) {
            view.setRotation(get(f2, j2, view, keyCache) + ((float) Math.toDegrees(Math.atan2(d3, d2))));
            return this.f2749h;
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            return this.f2749h;
        }
    }

    static class ProgressSet extends ViewTimeCycle {

        /* renamed from: l, reason: collision with root package name */
        boolean f2994l = false;

        ProgressSet() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Method method;
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f2, j2, view, keyCache));
            } else {
                if (this.f2994l) {
                    return false;
                }
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.f2994l = true;
                    method = null;
                }
                Method method2 = method;
                if (method2 != null) {
                    try {
                        method2.invoke(view, Float.valueOf(get(f2, j2, view, keyCache)));
                    } catch (IllegalAccessException e2) {
                        Log.e(ViewTimeCycle.TAG, "unable to setProgress", e2);
                    } catch (InvocationTargetException e3) {
                        Log.e(ViewTimeCycle.TAG, "unable to setProgress", e3);
                    }
                }
            }
            return this.f2749h;
        }
    }

    static class RotationSet extends ViewTimeCycle {
        RotationSet() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotation(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class RotationXset extends ViewTimeCycle {
        RotationXset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotationX(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class RotationYset extends ViewTimeCycle {
        RotationYset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setRotationY(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class ScaleXset extends ViewTimeCycle {
        ScaleXset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setScaleX(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class ScaleYset extends ViewTimeCycle {
        ScaleYset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setScaleY(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class TranslationXset extends ViewTimeCycle {
        TranslationXset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationX(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class TranslationYset extends ViewTimeCycle {
        TranslationYset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationY(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    static class TranslationZset extends ViewTimeCycle {
        TranslationZset() {
        }

        @Override // androidx.constraintlayout.motion.utils.ViewTimeCycle
        public boolean setProperty(View view, float f2, long j2, KeyCache keyCache) {
            view.setTranslationZ(get(f2, j2, view, keyCache));
            return this.f2749h;
        }
    }

    public static ViewTimeCycle makeCustomSpline(String str, SparseArray<ConstraintAttribute> sparseArray) {
        return new CustomSet(str, sparseArray);
    }

    public static ViewTimeCycle makeSpline(String str, long j2) {
        ViewTimeCycle rotationXset;
        str.hashCode();
        switch (str) {
            case "rotationX":
                rotationXset = new RotationXset();
                break;
            case "rotationY":
                rotationXset = new RotationYset();
                break;
            case "translationX":
                rotationXset = new TranslationXset();
                break;
            case "translationY":
                rotationXset = new TranslationYset();
                break;
            case "translationZ":
                rotationXset = new TranslationZset();
                break;
            case "progress":
                rotationXset = new ProgressSet();
                break;
            case "scaleX":
                rotationXset = new ScaleXset();
                break;
            case "scaleY":
                rotationXset = new ScaleYset();
                break;
            case "rotation":
                rotationXset = new RotationSet();
                break;
            case "elevation":
                rotationXset = new ElevationSet();
                break;
            case "transitionPathRotate":
                rotationXset = new PathRotate();
                break;
            case "alpha":
                rotationXset = new AlphaSet();
                break;
            default:
                return null;
        }
        rotationXset.b(j2);
        return rotationXset;
    }

    public float get(float f2, long j2, View view, KeyCache keyCache) {
        this.f2742a.getPos(f2, this.f2748g);
        float[] fArr = this.f2748g;
        float f3 = fArr[1];
        if (f3 == 0.0f) {
            this.f2749h = false;
            return fArr[2];
        }
        if (Float.isNaN(this.f2751j)) {
            float floatValue = keyCache.getFloatValue(view, this.f2747f, 0);
            this.f2751j = floatValue;
            if (Float.isNaN(floatValue)) {
                this.f2751j = 0.0f;
            }
        }
        float f4 = (float) ((this.f2751j + (((j2 - this.f2750i) * 1.0E-9d) * f3)) % 1.0d);
        this.f2751j = f4;
        keyCache.setFloatValue(view, this.f2747f, 0, f4);
        this.f2750i = j2;
        float f5 = this.f2748g[0];
        float fA = (a(this.f2751j) * f5) + this.f2748g[2];
        this.f2749h = (f5 == 0.0f && f3 == 0.0f) ? false : true;
        return fA;
    }

    public abstract boolean setProperty(View view, float f2, long j2, KeyCache keyCache);
}

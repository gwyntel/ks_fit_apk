package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import androidx.constraintlayout.core.state.WidgetFrame;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SplineSet {
    private static final String TAG = "SplineSet";

    /* renamed from: a, reason: collision with root package name */
    protected CurveFit f2728a;

    /* renamed from: b, reason: collision with root package name */
    protected int[] f2729b = new int[10];

    /* renamed from: c, reason: collision with root package name */
    protected float[] f2730c = new float[10];
    private int count;
    private String mType;

    private static class CoreSpline extends SplineSet {

        /* renamed from: d, reason: collision with root package name */
        String f2731d;

        /* renamed from: e, reason: collision with root package name */
        long f2732e;

        public CoreSpline(String str, long j2) {
            this.f2731d = str;
            this.f2732e = j2;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setProperty(TypedValues typedValues, float f2) {
            typedValues.setValue(typedValues.getId(this.f2731d), get(f2));
        }
    }

    public static class CustomSet extends SplineSet {

        /* renamed from: d, reason: collision with root package name */
        String f2733d;

        /* renamed from: e, reason: collision with root package name */
        KeyFrameArray.CustomArray f2734e;

        /* renamed from: f, reason: collision with root package name */
        float[] f2735f;

        public CustomSet(String str, KeyFrameArray.CustomArray customArray) {
            this.f2733d = str.split(",")[1];
            this.f2734e = customArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setPoint(int i2, float f2) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        public void setProperty(WidgetFrame widgetFrame, float f2) {
            this.f2728a.getPos(f2, this.f2735f);
            widgetFrame.setCustomValue(this.f2734e.valueAt(0), this.f2735f);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setup(int i2) {
            int size = this.f2734e.size();
            int iNumberOfInterpolatedValues = this.f2734e.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.f2735f = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, iNumberOfInterpolatedValues);
            for (int i3 = 0; i3 < size; i3++) {
                int iKeyAt = this.f2734e.keyAt(i3);
                CustomAttribute customAttributeValueAt = this.f2734e.valueAt(i3);
                dArr[i3] = iKeyAt * 0.01d;
                customAttributeValueAt.getValuesToInterpolate(this.f2735f);
                int i4 = 0;
                while (true) {
                    if (i4 < this.f2735f.length) {
                        dArr2[i3][i4] = r6[i4];
                        i4++;
                    }
                }
            }
            this.f2728a = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomAttribute customAttribute) {
            this.f2734e.append(i2, customAttribute);
        }
    }

    public static class CustomSpline extends SplineSet {

        /* renamed from: d, reason: collision with root package name */
        String f2736d;

        /* renamed from: e, reason: collision with root package name */
        KeyFrameArray.CustomVar f2737e;

        /* renamed from: f, reason: collision with root package name */
        float[] f2738f;

        public CustomSpline(String str, KeyFrameArray.CustomVar customVar) {
            this.f2736d = str.split(",")[1];
            this.f2737e = customVar;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setPoint(int i2, float f2) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setProperty(TypedValues typedValues, float f2) {
            setProperty((MotionWidget) typedValues, f2);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setup(int i2) {
            int size = this.f2737e.size();
            int iNumberOfInterpolatedValues = this.f2737e.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.f2738f = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, iNumberOfInterpolatedValues);
            for (int i3 = 0; i3 < size; i3++) {
                int iKeyAt = this.f2737e.keyAt(i3);
                CustomVariable customVariableValueAt = this.f2737e.valueAt(i3);
                dArr[i3] = iKeyAt * 0.01d;
                customVariableValueAt.getValuesToInterpolate(this.f2738f);
                int i4 = 0;
                while (true) {
                    if (i4 < this.f2738f.length) {
                        dArr2[i3][i4] = r6[i4];
                        i4++;
                    }
                }
            }
            this.f2728a = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomVariable customVariable) {
            this.f2737e.append(i2, customVariable);
        }

        public void setProperty(MotionWidget motionWidget, float f2) {
            this.f2728a.getPos(f2, this.f2738f);
            this.f2737e.valueAt(0).setInterpolatedValue(motionWidget, this.f2738f);
        }
    }

    private static class Sort {
        private Sort() {
        }

        static void a(int[] iArr, float[] fArr, int i2, int i3) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i3;
            iArr2[1] = i2;
            int i4 = 2;
            while (i4 > 0) {
                int i5 = iArr2[i4 - 1];
                int i6 = i4 - 2;
                int i7 = iArr2[i6];
                if (i5 < i7) {
                    int iPartition = partition(iArr, fArr, i5, i7);
                    iArr2[i6] = iPartition - 1;
                    iArr2[i4 - 1] = i5;
                    int i8 = i4 + 1;
                    iArr2[i4] = i7;
                    i4 += 2;
                    iArr2[i8] = iPartition + 1;
                } else {
                    i4 = i6;
                }
            }
        }

        private static int partition(int[] iArr, float[] fArr, int i2, int i3) {
            int i4 = iArr[i3];
            int i5 = i2;
            while (i2 < i3) {
                if (iArr[i2] <= i4) {
                    swap(iArr, fArr, i5, i2);
                    i5++;
                }
                i2++;
            }
            swap(iArr, fArr, i5, i3);
            return i5;
        }

        private static void swap(int[] iArr, float[] fArr, int i2, int i3) {
            int i4 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i4;
            float f2 = fArr[i2];
            fArr[i2] = fArr[i3];
            fArr[i3] = f2;
        }
    }

    public static SplineSet makeCustomSpline(String str, KeyFrameArray.CustomArray customArray) {
        return new CustomSet(str, customArray);
    }

    public static SplineSet makeCustomSplineSet(String str, KeyFrameArray.CustomVar customVar) {
        return new CustomSpline(str, customVar);
    }

    public static SplineSet makeSpline(String str, long j2) {
        return new CoreSpline(str, j2);
    }

    public float get(float f2) {
        return (float) this.f2728a.getPos(f2, 0);
    }

    public CurveFit getCurveFit() {
        return this.f2728a;
    }

    public float getSlope(float f2) {
        return (float) this.f2728a.getSlope(f2, 0);
    }

    public void setPoint(int i2, float f2) {
        int[] iArr = this.f2729b;
        if (iArr.length < this.count + 1) {
            this.f2729b = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.f2730c;
            this.f2730c = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.f2729b;
        int i3 = this.count;
        iArr2[i3] = i2;
        this.f2730c[i3] = f2;
        this.count = i3 + 1;
    }

    public void setProperty(TypedValues typedValues, float f2) {
        typedValues.setValue(a.a(this.mType), get(f2));
    }

    public void setType(String str) {
        this.mType = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setup(int r10) {
        /*
            r9 = this;
            int r0 = r9.count
            if (r0 != 0) goto L5
            return
        L5:
            int[] r1 = r9.f2729b
            float[] r2 = r9.f2730c
            r3 = 1
            int r0 = r0 - r3
            r4 = 0
            androidx.constraintlayout.core.motion.utils.SplineSet.Sort.a(r1, r2, r4, r0)
            r0 = r3
            r1 = r0
        L11:
            int r2 = r9.count
            if (r0 >= r2) goto L24
            int[] r2 = r9.f2729b
            int r5 = r0 + (-1)
            r5 = r2[r5]
            r2 = r2[r0]
            if (r5 == r2) goto L21
            int r1 = r1 + 1
        L21:
            int r0 = r0 + 1
            goto L11
        L24:
            double[] r0 = new double[r1]
            r2 = 2
            int[] r2 = new int[r2]
            r2[r3] = r3
            r2[r4] = r1
            java.lang.Class r1 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r1, r2)
            double[][] r1 = (double[][]) r1
            r2 = r4
            r3 = r2
        L37:
            int r5 = r9.count
            if (r2 >= r5) goto L63
            if (r2 <= 0) goto L48
            int[] r5 = r9.f2729b
            r6 = r5[r2]
            int r7 = r2 + (-1)
            r5 = r5[r7]
            if (r6 != r5) goto L48
            goto L60
        L48:
            int[] r5 = r9.f2729b
            r5 = r5[r2]
            double r5 = (double) r5
            r7 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            double r5 = r5 * r7
            r0[r3] = r5
            r5 = r1[r3]
            float[] r6 = r9.f2730c
            r6 = r6[r2]
            double r6 = (double) r6
            r5[r4] = r6
            int r3 = r3 + 1
        L60:
            int r2 = r2 + 1
            goto L37
        L63:
            androidx.constraintlayout.core.motion.utils.CurveFit r10 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r10, r0, r1)
            r9.f2728a = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.SplineSet.setup(int):void");
    }

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.count; i2++) {
            str = str + "[" + this.f2729b[i2] + " , " + decimalFormat.format(this.f2730c[i2]) + "] ";
        }
        return str;
    }
}

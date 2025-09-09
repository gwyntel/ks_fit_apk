package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    private static final String TAG = "SplineSet";

    /* renamed from: k, reason: collision with root package name */
    protected static float f2741k = 6.2831855f;

    /* renamed from: a, reason: collision with root package name */
    protected CurveFit f2742a;

    /* renamed from: e, reason: collision with root package name */
    protected int f2746e;

    /* renamed from: f, reason: collision with root package name */
    protected String f2747f;

    /* renamed from: i, reason: collision with root package name */
    protected long f2750i;

    /* renamed from: b, reason: collision with root package name */
    protected int f2743b = 0;

    /* renamed from: c, reason: collision with root package name */
    protected int[] f2744c = new int[10];

    /* renamed from: d, reason: collision with root package name */
    protected float[][] f2745d = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);

    /* renamed from: g, reason: collision with root package name */
    protected float[] f2748g = new float[3];

    /* renamed from: h, reason: collision with root package name */
    protected boolean f2749h = false;

    /* renamed from: j, reason: collision with root package name */
    protected float f2751j = Float.NaN;

    public static class CustomSet extends TimeCycleSplineSet {

        /* renamed from: l, reason: collision with root package name */
        String f2752l;

        /* renamed from: m, reason: collision with root package name */
        KeyFrameArray.CustomArray f2753m;

        /* renamed from: n, reason: collision with root package name */
        KeyFrameArray.FloatArray f2754n = new KeyFrameArray.FloatArray();

        /* renamed from: o, reason: collision with root package name */
        float[] f2755o;

        /* renamed from: p, reason: collision with root package name */
        float[] f2756p;

        public CustomSet(String str, KeyFrameArray.CustomArray customArray) {
            this.f2752l = str.split(",")[1];
            this.f2753m = customArray;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public boolean setProperty(MotionWidget motionWidget, float f2, long j2, KeyCache keyCache) {
            this.f2742a.getPos(f2, this.f2755o);
            float[] fArr = this.f2755o;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            long j3 = j2 - this.f2750i;
            if (Float.isNaN(this.f2751j)) {
                float floatValue = keyCache.getFloatValue(motionWidget, this.f2752l, 0);
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
                float[] fArr2 = this.f2756p;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.f2749h;
                float f6 = this.f2755o[i2];
                this.f2749h = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fA) + f4;
                i2++;
            }
            motionWidget.setInterpolatedValue(this.f2753m.valueAt(0), this.f2756p);
            if (f3 != 0.0f) {
                this.f2749h = true;
            }
            return this.f2749h;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.f2753m.size();
            int iNumberOfInterpolatedValues = this.f2753m.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i3 = iNumberOfInterpolatedValues + 2;
            this.f2755o = new float[i3];
            this.f2756p = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.f2753m.keyAt(i4);
                CustomAttribute customAttributeValueAt = this.f2753m.valueAt(i4);
                float[] fArrValueAt = this.f2754n.valueAt(i4);
                dArr[i4] = iKeyAt * 0.01d;
                customAttributeValueAt.getValuesToInterpolate(this.f2755o);
                int i5 = 0;
                while (true) {
                    if (i5 < this.f2755o.length) {
                        dArr2[i4][i5] = r8[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNumberOfInterpolatedValues] = fArrValueAt[0];
                dArr3[iNumberOfInterpolatedValues + 1] = fArrValueAt[1];
            }
            this.f2742a = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomAttribute customAttribute, float f2, int i3, float f3) {
            this.f2753m.append(i2, customAttribute);
            this.f2754n.append(i2, new float[]{f2, f3});
            this.f2743b = Math.max(this.f2743b, i3);
        }
    }

    public static class CustomVarSet extends TimeCycleSplineSet {

        /* renamed from: l, reason: collision with root package name */
        String f2757l;

        /* renamed from: m, reason: collision with root package name */
        KeyFrameArray.CustomVar f2758m;

        /* renamed from: n, reason: collision with root package name */
        KeyFrameArray.FloatArray f2759n = new KeyFrameArray.FloatArray();

        /* renamed from: o, reason: collision with root package name */
        float[] f2760o;

        /* renamed from: p, reason: collision with root package name */
        float[] f2761p;

        public CustomVarSet(String str, KeyFrameArray.CustomVar customVar) {
            this.f2757l = str.split(",")[1];
            this.f2758m = customVar;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setPoint(int i2, float f2, float f3, int i3, float f4) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public boolean setProperty(MotionWidget motionWidget, float f2, long j2, KeyCache keyCache) {
            this.f2742a.getPos(f2, this.f2760o);
            float[] fArr = this.f2760o;
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            long j3 = j2 - this.f2750i;
            if (Float.isNaN(this.f2751j)) {
                float floatValue = keyCache.getFloatValue(motionWidget, this.f2757l, 0);
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
                float[] fArr2 = this.f2761p;
                if (i2 >= fArr2.length) {
                    break;
                }
                boolean z2 = this.f2749h;
                float f6 = this.f2760o[i2];
                this.f2749h = z2 | (((double) f6) != 0.0d);
                fArr2[i2] = (f6 * fA) + f4;
                i2++;
            }
            this.f2758m.valueAt(0).setInterpolatedValue(motionWidget, this.f2761p);
            if (f3 != 0.0f) {
                this.f2749h = true;
            }
            return this.f2749h;
        }

        @Override // androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet
        public void setup(int i2) {
            int size = this.f2758m.size();
            int iNumberOfInterpolatedValues = this.f2758m.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            int i3 = iNumberOfInterpolatedValues + 2;
            this.f2760o = new float[i3];
            this.f2761p = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i3);
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = this.f2758m.keyAt(i4);
                CustomVariable customVariableValueAt = this.f2758m.valueAt(i4);
                float[] fArrValueAt = this.f2759n.valueAt(i4);
                dArr[i4] = iKeyAt * 0.01d;
                customVariableValueAt.getValuesToInterpolate(this.f2760o);
                int i5 = 0;
                while (true) {
                    if (i5 < this.f2760o.length) {
                        dArr2[i4][i5] = r8[i5];
                        i5++;
                    }
                }
                double[] dArr3 = dArr2[i4];
                dArr3[iNumberOfInterpolatedValues] = fArrValueAt[0];
                dArr3[iNumberOfInterpolatedValues + 1] = fArrValueAt[1];
            }
            this.f2742a = CurveFit.get(i2, dArr, dArr2);
        }

        public void setPoint(int i2, CustomVariable customVariable, float f2, int i3, float f3) {
            this.f2758m.append(i2, customVariable);
            this.f2759n.append(i2, new float[]{f2, f3});
            this.f2743b = Math.max(this.f2743b, i3);
        }
    }

    protected static class Sort {
        static void a(int[] iArr, float[][] fArr, int i2, int i3) {
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

        private static int partition(int[] iArr, float[][] fArr, int i2, int i3) {
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

        private static void swap(int[] iArr, float[][] fArr, int i2, int i3) {
            int i4 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i4;
            float[] fArr2 = fArr[i2];
            fArr[i2] = fArr[i3];
            fArr[i3] = fArr2;
        }
    }

    protected float a(float f2) {
        float fAbs;
        switch (this.f2743b) {
            case 1:
                return Math.signum(f2 * f2741k);
            case 2:
                fAbs = Math.abs(f2);
                break;
            case 3:
                return (((f2 * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                fAbs = ((f2 * 2.0f) + 1.0f) % 2.0f;
                break;
            case 5:
                return (float) Math.cos(f2 * f2741k);
            case 6:
                float fAbs2 = 1.0f - Math.abs(((f2 * 4.0f) % 4.0f) - 2.0f);
                fAbs = fAbs2 * fAbs2;
                break;
            default:
                return (float) Math.sin(f2 * f2741k);
        }
        return 1.0f - fAbs;
    }

    protected void b(long j2) {
        this.f2750i = j2;
    }

    public CurveFit getCurveFit() {
        return this.f2742a;
    }

    public void setPoint(int i2, float f2, float f3, int i3, float f4) {
        int[] iArr = this.f2744c;
        int i4 = this.f2746e;
        iArr[i4] = i2;
        float[] fArr = this.f2745d[i4];
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[2] = f4;
        this.f2743b = Math.max(this.f2743b, i3);
        this.f2746e++;
    }

    public void setType(String str) {
        this.f2747f = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setup(int r12) {
        /*
            r11 = this;
            int r0 = r11.f2746e
            if (r0 != 0) goto L1d
            java.io.PrintStream r12 = java.lang.System.err
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Error no points added to "
            r0.append(r1)
            java.lang.String r1 = r11.f2747f
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r12.println(r0)
            return
        L1d:
            int[] r1 = r11.f2744c
            float[][] r2 = r11.f2745d
            r3 = 1
            int r0 = r0 - r3
            r4 = 0
            androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.Sort.a(r1, r2, r4, r0)
            r0 = r3
            r1 = r4
        L29:
            int[] r2 = r11.f2744c
            int r5 = r2.length
            if (r0 >= r5) goto L3b
            r5 = r2[r0]
            int r6 = r0 + (-1)
            r2 = r2[r6]
            if (r5 == r2) goto L38
            int r1 = r1 + 1
        L38:
            int r0 = r0 + 1
            goto L29
        L3b:
            if (r1 != 0) goto L3e
            r1 = r3
        L3e:
            double[] r0 = new double[r1]
            r2 = 2
            int[] r5 = new int[r2]
            r6 = 3
            r5[r3] = r6
            r5[r4] = r1
            java.lang.Class r1 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r1, r5)
            double[][] r1 = (double[][]) r1
            r5 = r4
            r6 = r5
        L52:
            int r7 = r11.f2746e
            if (r5 >= r7) goto L8a
            if (r5 <= 0) goto L63
            int[] r7 = r11.f2744c
            r8 = r7[r5]
            int r9 = r5 + (-1)
            r7 = r7[r9]
            if (r8 != r7) goto L63
            goto L87
        L63:
            int[] r7 = r11.f2744c
            r7 = r7[r5]
            double r7 = (double) r7
            r9 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            double r7 = r7 * r9
            r0[r6] = r7
            r7 = r1[r6]
            float[][] r8 = r11.f2745d
            r8 = r8[r5]
            r9 = r8[r4]
            double r9 = (double) r9
            r7[r4] = r9
            r9 = r8[r3]
            double r9 = (double) r9
            r7[r3] = r9
            r8 = r8[r2]
            double r8 = (double) r8
            r7[r2] = r8
            int r6 = r6 + 1
        L87:
            int r5 = r5 + 1
            goto L52
        L8a:
            androidx.constraintlayout.core.motion.utils.CurveFit r12 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r12, r0, r1)
            r11.f2742a = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.setup(int):void");
    }

    public String toString() {
        String str = this.f2747f;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.f2746e; i2++) {
            str = str + "[" + this.f2744c[i2] + " , " + decimalFormat.format(this.f2745d[i2]) + "] ";
        }
        return str;
    }
}

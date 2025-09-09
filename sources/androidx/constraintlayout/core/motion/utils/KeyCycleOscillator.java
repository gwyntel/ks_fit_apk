package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    private static final String TAG = "KeyCycleOscillator";
    private CurveFit mCurveFit;
    private CycleOscillator mCycleOscillator;
    private String mType;
    private int mWaveShape = 0;
    private String mWaveString = null;
    public int mVariesBy = 0;

    /* renamed from: a, reason: collision with root package name */
    ArrayList f2684a = new ArrayList();

    private static class CoreSpline extends KeyCycleOscillator {

        /* renamed from: b, reason: collision with root package name */
        String f2686b;

        /* renamed from: c, reason: collision with root package name */
        int f2687c;

        public CoreSpline(String str) {
            this.f2686b = str;
            this.f2687c = c.a(str);
        }

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public void setProperty(MotionWidget motionWidget, float f2) {
            motionWidget.setValue(this.f2687c, get(f2));
        }
    }

    static class CycleOscillator {
        private static final String TAG = "CycleOscillator";
        private final int OFFST;
        private final int PHASE;
        private final int VALUE;

        /* renamed from: a, reason: collision with root package name */
        Oscillator f2688a;

        /* renamed from: b, reason: collision with root package name */
        float[] f2689b;

        /* renamed from: c, reason: collision with root package name */
        double[] f2690c;

        /* renamed from: d, reason: collision with root package name */
        float[] f2691d;

        /* renamed from: e, reason: collision with root package name */
        float[] f2692e;

        /* renamed from: f, reason: collision with root package name */
        float[] f2693f;

        /* renamed from: g, reason: collision with root package name */
        float[] f2694g;

        /* renamed from: h, reason: collision with root package name */
        int f2695h;

        /* renamed from: i, reason: collision with root package name */
        CurveFit f2696i;

        /* renamed from: j, reason: collision with root package name */
        double[] f2697j;

        /* renamed from: k, reason: collision with root package name */
        double[] f2698k;

        /* renamed from: l, reason: collision with root package name */
        float f2699l;
        private final int mVariesBy;

        CycleOscillator(int i2, String str, int i3, int i4) {
            Oscillator oscillator = new Oscillator();
            this.f2688a = oscillator;
            this.OFFST = 0;
            this.PHASE = 1;
            this.VALUE = 2;
            this.f2695h = i2;
            this.mVariesBy = i3;
            oscillator.setType(i2, str);
            this.f2689b = new float[i4];
            this.f2690c = new double[i4];
            this.f2691d = new float[i4];
            this.f2692e = new float[i4];
            this.f2693f = new float[i4];
            this.f2694g = new float[i4];
        }

        public double getLastPhase() {
            return this.f2697j[1];
        }

        public double getSlope(float f2) {
            CurveFit curveFit = this.f2696i;
            if (curveFit != null) {
                double d2 = f2;
                curveFit.getSlope(d2, this.f2698k);
                this.f2696i.getPos(d2, this.f2697j);
            } else {
                double[] dArr = this.f2698k;
                dArr[0] = 0.0d;
                dArr[1] = 0.0d;
                dArr[2] = 0.0d;
            }
            double d3 = f2;
            double value = this.f2688a.getValue(d3, this.f2697j[1]);
            double slope = this.f2688a.getSlope(d3, this.f2697j[1], this.f2698k[1]);
            double[] dArr2 = this.f2698k;
            return dArr2[0] + (value * dArr2[2]) + (slope * this.f2697j[2]);
        }

        public double getValues(float f2) {
            CurveFit curveFit = this.f2696i;
            if (curveFit != null) {
                curveFit.getPos(f2, this.f2697j);
            } else {
                double[] dArr = this.f2697j;
                dArr[0] = this.f2692e[0];
                dArr[1] = this.f2693f[0];
                dArr[2] = this.f2689b[0];
            }
            double[] dArr2 = this.f2697j;
            return dArr2[0] + (this.f2688a.getValue(f2, dArr2[1]) * this.f2697j[2]);
        }

        public void setPoint(int i2, int i3, float f2, float f3, float f4, float f5) {
            this.f2690c[i2] = i3 / 100.0d;
            this.f2691d[i2] = f2;
            this.f2692e[i2] = f3;
            this.f2693f[i2] = f4;
            this.f2689b[i2] = f5;
        }

        public void setup(float f2) {
            this.f2699l = f2;
            double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, this.f2690c.length, 3);
            float[] fArr = this.f2689b;
            this.f2697j = new double[fArr.length + 2];
            this.f2698k = new double[fArr.length + 2];
            if (this.f2690c[0] > 0.0d) {
                this.f2688a.addPoint(0.0d, this.f2691d[0]);
            }
            double[] dArr2 = this.f2690c;
            int length = dArr2.length - 1;
            if (dArr2[length] < 1.0d) {
                this.f2688a.addPoint(1.0d, this.f2691d[length]);
            }
            for (int i2 = 0; i2 < dArr.length; i2++) {
                double[] dArr3 = dArr[i2];
                dArr3[0] = this.f2692e[i2];
                dArr3[1] = this.f2693f[i2];
                dArr3[2] = this.f2689b[i2];
                this.f2688a.addPoint(this.f2690c[i2], this.f2691d[i2]);
            }
            this.f2688a.normalize();
            double[] dArr4 = this.f2690c;
            if (dArr4.length > 1) {
                this.f2696i = CurveFit.get(0, dArr4, dArr);
            } else {
                this.f2696i = null;
            }
        }
    }

    private static class IntDoubleSort {
        private IntDoubleSort() {
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

    private static class IntFloatFloatSort {
        private IntFloatFloatSort() {
        }

        private static int partition(int[] iArr, float[] fArr, float[] fArr2, int i2, int i3) {
            int i4 = iArr[i3];
            int i5 = i2;
            while (i2 < i3) {
                if (iArr[i2] <= i4) {
                    swap(iArr, fArr, fArr2, i5, i2);
                    i5++;
                }
                i2++;
            }
            swap(iArr, fArr, fArr2, i5, i3);
            return i5;
        }

        private static void swap(int[] iArr, float[] fArr, float[] fArr2, int i2, int i3) {
            int i4 = iArr[i2];
            iArr[i2] = iArr[i3];
            iArr[i3] = i4;
            float f2 = fArr[i2];
            fArr[i2] = fArr[i3];
            fArr[i3] = f2;
            float f3 = fArr2[i2];
            fArr2[i2] = fArr2[i3];
            fArr2[i3] = f3;
        }
    }

    public static class PathRotateSet extends KeyCycleOscillator {

        /* renamed from: b, reason: collision with root package name */
        String f2700b;

        /* renamed from: c, reason: collision with root package name */
        int f2701c;

        public PathRotateSet(String str) {
            this.f2700b = str;
            this.f2701c = c.a(str);
        }

        public void setPathRotate(MotionWidget motionWidget, float f2, double d2, double d3) {
            motionWidget.setRotationZ(get(f2) + ((float) Math.toDegrees(Math.atan2(d3, d2))));
        }

        @Override // androidx.constraintlayout.core.motion.utils.KeyCycleOscillator
        public void setProperty(MotionWidget motionWidget, float f2) {
            motionWidget.setValue(this.f2701c, get(f2));
        }
    }

    static class WavePoint {

        /* renamed from: a, reason: collision with root package name */
        int f2702a;

        /* renamed from: b, reason: collision with root package name */
        float f2703b;

        /* renamed from: c, reason: collision with root package name */
        float f2704c;

        /* renamed from: d, reason: collision with root package name */
        float f2705d;

        /* renamed from: e, reason: collision with root package name */
        float f2706e;

        public WavePoint(int i2, float f2, float f3, float f4, float f5) {
            this.f2702a = i2;
            this.f2703b = f5;
            this.f2704c = f3;
            this.f2705d = f2;
            this.f2706e = f4;
        }
    }

    public static KeyCycleOscillator makeWidgetCycle(String str) {
        return str.equals("pathRotate") ? new PathRotateSet(str) : new CoreSpline(str);
    }

    protected void a(Object obj) {
    }

    public float get(float f2) {
        return (float) this.mCycleOscillator.getValues(f2);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public float getSlope(float f2) {
        return (float) this.mCycleOscillator.getSlope(f2);
    }

    public void setPoint(int i2, int i3, String str, int i4, float f2, float f3, float f4, float f5, Object obj) {
        this.f2684a.add(new WavePoint(i2, f2, f3, f4, f5));
        if (i4 != -1) {
            this.mVariesBy = i4;
        }
        this.mWaveShape = i3;
        a(obj);
        this.mWaveString = str;
    }

    public void setProperty(MotionWidget motionWidget, float f2) {
    }

    public void setType(String str) {
        this.mType = str;
    }

    public void setup(float f2) {
        int size = this.f2684a.size();
        if (size == 0) {
            return;
        }
        Collections.sort(this.f2684a, new Comparator<WavePoint>() { // from class: androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.1
            @Override // java.util.Comparator
            public int compare(WavePoint wavePoint, WavePoint wavePoint2) {
                return Integer.compare(wavePoint.f2702a, wavePoint2.f2702a);
            }
        });
        double[] dArr = new double[size];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 3);
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mWaveString, this.mVariesBy, size);
        Iterator it = this.f2684a.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            WavePoint wavePoint = (WavePoint) it.next();
            float f3 = wavePoint.f2705d;
            dArr[i2] = f3 * 0.01d;
            double[] dArr3 = dArr2[i2];
            float f4 = wavePoint.f2703b;
            dArr3[0] = f4;
            float f5 = wavePoint.f2704c;
            dArr3[1] = f5;
            float f6 = wavePoint.f2706e;
            dArr3[2] = f6;
            this.mCycleOscillator.setPoint(i2, wavePoint.f2702a, f3, f5, f6, f4);
            i2++;
            dArr2 = dArr2;
        }
        this.mCycleOscillator.setup(f2);
        this.mCurveFit = CurveFit.get(0, dArr, dArr2);
    }

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator it = this.f2684a.iterator();
        while (it.hasNext()) {
            str = str + "[" + ((WavePoint) it.next()).f2702a + " , " + decimalFormat.format(r3.f2703b) + "] ";
        }
        return str;
    }

    public boolean variesByPath() {
        return this.mVariesBy == 1;
    }

    public void setPoint(int i2, int i3, String str, int i4, float f2, float f3, float f4, float f5) {
        this.f2684a.add(new WavePoint(i2, f2, f3, f4, f5));
        if (i4 != -1) {
            this.mVariesBy = i4;
        }
        this.mWaveShape = i3;
        this.mWaveString = str;
    }
}

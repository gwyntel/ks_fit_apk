package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class ArcCurveFit extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;

    /* renamed from: a, reason: collision with root package name */
    Arc[] f2645a;
    private boolean mExtrapolate = true;
    private final double[] mTime;

    private static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];

        /* renamed from: a, reason: collision with root package name */
        double[] f2646a;

        /* renamed from: b, reason: collision with root package name */
        double f2647b;

        /* renamed from: c, reason: collision with root package name */
        double f2648c;

        /* renamed from: d, reason: collision with root package name */
        double f2649d;

        /* renamed from: e, reason: collision with root package name */
        double f2650e;

        /* renamed from: f, reason: collision with root package name */
        double f2651f;

        /* renamed from: g, reason: collision with root package name */
        double f2652g;

        /* renamed from: h, reason: collision with root package name */
        double f2653h;

        /* renamed from: i, reason: collision with root package name */
        double f2654i;

        /* renamed from: j, reason: collision with root package name */
        double f2655j;

        /* renamed from: k, reason: collision with root package name */
        double f2656k;

        /* renamed from: l, reason: collision with root package name */
        double f2657l;

        /* renamed from: m, reason: collision with root package name */
        double f2658m;

        /* renamed from: n, reason: collision with root package name */
        double f2659n;

        /* renamed from: o, reason: collision with root package name */
        double f2660o;

        /* renamed from: p, reason: collision with root package name */
        double f2661p;

        /* renamed from: q, reason: collision with root package name */
        boolean f2662q;

        /* renamed from: r, reason: collision with root package name */
        boolean f2663r;

        Arc(int i2, double d2, double d3, double d4, double d5, double d6, double d7) {
            this.f2663r = false;
            this.f2662q = i2 == 1;
            this.f2648c = d2;
            this.f2649d = d3;
            this.f2654i = 1.0d / (d3 - d2);
            if (3 == i2) {
                this.f2663r = true;
            }
            double d8 = d6 - d4;
            double d9 = d7 - d5;
            if (!this.f2663r && Math.abs(d8) >= EPSILON && Math.abs(d9) >= EPSILON) {
                this.f2646a = new double[101];
                boolean z2 = this.f2662q;
                this.f2655j = d8 * (z2 ? -1 : 1);
                this.f2656k = d9 * (z2 ? 1 : -1);
                this.f2657l = z2 ? d6 : d4;
                this.f2658m = z2 ? d5 : d7;
                buildTable(d4, d5, d6, d7);
                this.f2659n = this.f2647b * this.f2654i;
                return;
            }
            this.f2663r = true;
            this.f2650e = d4;
            this.f2651f = d6;
            this.f2652g = d5;
            this.f2653h = d7;
            double dHypot = Math.hypot(d9, d8);
            this.f2647b = dHypot;
            this.f2659n = dHypot * this.f2654i;
            double d10 = this.f2649d;
            double d11 = this.f2648c;
            this.f2657l = d8 / (d10 - d11);
            this.f2658m = d9 / (d10 - d11);
        }

        private void buildTable(double d2, double d3, double d4, double d5) {
            double dHypot;
            double d6 = d4 - d2;
            double d7 = d3 - d5;
            int i2 = 0;
            double d8 = 0.0d;
            double d9 = 0.0d;
            double d10 = 0.0d;
            while (true) {
                if (i2 >= ourPercent.length) {
                    break;
                }
                double d11 = d8;
                double radians = Math.toRadians((i2 * 90.0d) / (r15.length - 1));
                double dSin = Math.sin(radians) * d6;
                double dCos = Math.cos(radians) * d7;
                if (i2 > 0) {
                    dHypot = Math.hypot(dSin - d9, dCos - d10) + d11;
                    ourPercent[i2] = dHypot;
                } else {
                    dHypot = d11;
                }
                i2++;
                d10 = dCos;
                d8 = dHypot;
                d9 = dSin;
            }
            double d12 = d8;
            this.f2647b = d12;
            int i3 = 0;
            while (true) {
                double[] dArr = ourPercent;
                if (i3 >= dArr.length) {
                    break;
                }
                dArr[i3] = dArr[i3] / d12;
                i3++;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= this.f2646a.length) {
                    return;
                }
                double length = i4 / (r1.length - 1);
                int iBinarySearch = Arrays.binarySearch(ourPercent, length);
                if (iBinarySearch >= 0) {
                    this.f2646a[i4] = iBinarySearch / (ourPercent.length - 1);
                } else if (iBinarySearch == -1) {
                    this.f2646a[i4] = 0.0d;
                } else {
                    int i5 = -iBinarySearch;
                    int i6 = i5 - 2;
                    double[] dArr2 = ourPercent;
                    double d13 = dArr2[i6];
                    this.f2646a[i4] = (i6 + ((length - d13) / (dArr2[i5 - 1] - d13))) / (dArr2.length - 1);
                }
                i4++;
            }
        }

        double a() {
            double d2 = this.f2655j * this.f2661p;
            double dHypot = this.f2659n / Math.hypot(d2, (-this.f2656k) * this.f2660o);
            if (this.f2662q) {
                d2 = -d2;
            }
            return d2 * dHypot;
        }

        double b() {
            double d2 = this.f2655j * this.f2661p;
            double d3 = (-this.f2656k) * this.f2660o;
            double dHypot = this.f2659n / Math.hypot(d2, d3);
            return this.f2662q ? (-d3) * dHypot : d3 * dHypot;
        }

        double c() {
            return this.f2657l + (this.f2655j * this.f2660o);
        }

        double d() {
            return this.f2658m + (this.f2656k * this.f2661p);
        }

        double e(double d2) {
            if (d2 <= 0.0d) {
                return 0.0d;
            }
            if (d2 >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.f2646a;
            double length = d2 * (dArr.length - 1);
            int i2 = (int) length;
            double d3 = length - i2;
            double d4 = dArr[i2];
            return d4 + (d3 * (dArr[i2 + 1] - d4));
        }

        void f(double d2) {
            double dE = e((this.f2662q ? this.f2649d - d2 : d2 - this.f2648c) * this.f2654i) * 1.5707963267948966d;
            this.f2660o = Math.sin(dE);
            this.f2661p = Math.cos(dE);
        }

        public double getLinearDX(double d2) {
            return this.f2657l;
        }

        public double getLinearDY(double d2) {
            return this.f2658m;
        }

        public double getLinearX(double d2) {
            double d3 = (d2 - this.f2648c) * this.f2654i;
            double d4 = this.f2650e;
            return d4 + (d3 * (this.f2651f - d4));
        }

        public double getLinearY(double d2) {
            double d3 = (d2 - this.f2648c) * this.f2654i;
            double d4 = this.f2652g;
            return d4 + (d3 * (this.f2653h - d4));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ArcCurveFit(int[] r25, double[] r26, double[][] r27) {
        /*
            r24 = this;
            r0 = r24
            r1 = r26
            r24.<init>()
            r2 = 1
            r0.mExtrapolate = r2
            r0.mTime = r1
            int r3 = r1.length
            int r3 = r3 - r2
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc[] r3 = new androidx.constraintlayout.core.motion.utils.ArcCurveFit.Arc[r3]
            r0.f2645a = r3
            r3 = 0
            r5 = r2
            r6 = r5
            r4 = r3
        L16:
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc[] r7 = r0.f2645a
            int r8 = r7.length
            if (r4 >= r8) goto L51
            r8 = r25[r4]
            r9 = 3
            if (r8 == 0) goto L31
            if (r8 == r2) goto L2f
            r10 = 2
            if (r8 == r10) goto L2d
            if (r8 == r9) goto L28
            goto L32
        L28:
            if (r5 != r2) goto L2f
            goto L2d
        L2b:
            r6 = r5
            goto L32
        L2d:
            r5 = r10
            goto L2b
        L2f:
            r5 = r2
            goto L2b
        L31:
            r6 = r9
        L32:
            androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc r22 = new androidx.constraintlayout.core.motion.utils.ArcCurveFit$Arc
            r10 = r1[r4]
            int r23 = r4 + 1
            r12 = r1[r23]
            r8 = r27[r4]
            r14 = r8[r3]
            r16 = r8[r2]
            r8 = r27[r23]
            r18 = r8[r3]
            r20 = r8[r2]
            r8 = r22
            r9 = r6
            r8.<init>(r9, r10, r12, r14, r16, r18, r20)
            r7[r4] = r22
            r4 = r23
            goto L16
        L51:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.ArcCurveFit.<init>(int[], double[], double[][]):void");
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d2, double[] dArr) {
        if (this.mExtrapolate) {
            Arc[] arcArr = this.f2645a;
            Arc arc = arcArr[0];
            double d3 = arc.f2648c;
            if (d2 < d3) {
                double d4 = d2 - d3;
                if (arc.f2663r) {
                    dArr[0] = arc.getLinearX(d3) + (this.f2645a[0].getLinearDX(d3) * d4);
                    dArr[1] = this.f2645a[0].getLinearY(d3) + (d4 * this.f2645a[0].getLinearDY(d3));
                    return;
                } else {
                    arc.f(d3);
                    dArr[0] = this.f2645a[0].c() + (this.f2645a[0].a() * d4);
                    dArr[1] = this.f2645a[0].d() + (d4 * this.f2645a[0].b());
                    return;
                }
            }
            if (d2 > arcArr[arcArr.length - 1].f2649d) {
                double d5 = arcArr[arcArr.length - 1].f2649d;
                double d6 = d2 - d5;
                int length = arcArr.length - 1;
                Arc arc2 = arcArr[length];
                if (arc2.f2663r) {
                    dArr[0] = arc2.getLinearX(d5) + (this.f2645a[length].getLinearDX(d5) * d6);
                    dArr[1] = this.f2645a[length].getLinearY(d5) + (d6 * this.f2645a[length].getLinearDY(d5));
                    return;
                } else {
                    arc2.f(d2);
                    dArr[0] = this.f2645a[length].c() + (this.f2645a[length].a() * d6);
                    dArr[1] = this.f2645a[length].d() + (d6 * this.f2645a[length].b());
                    return;
                }
            }
        } else {
            Arc[] arcArr2 = this.f2645a;
            double d7 = arcArr2[0].f2648c;
            if (d2 < d7) {
                d2 = d7;
            }
            if (d2 > arcArr2[arcArr2.length - 1].f2649d) {
                d2 = arcArr2[arcArr2.length - 1].f2649d;
            }
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr3 = this.f2645a;
            if (i2 >= arcArr3.length) {
                return;
            }
            Arc arc3 = arcArr3[i2];
            if (d2 <= arc3.f2649d) {
                if (arc3.f2663r) {
                    dArr[0] = arc3.getLinearX(d2);
                    dArr[1] = this.f2645a[i2].getLinearY(d2);
                    return;
                } else {
                    arc3.f(d2);
                    dArr[0] = this.f2645a[i2].c();
                    dArr[1] = this.f2645a[i2].d();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getSlope(double d2, double[] dArr) {
        Arc[] arcArr = this.f2645a;
        double d3 = arcArr[0].f2648c;
        if (d2 < d3) {
            d2 = d3;
        } else if (d2 > arcArr[arcArr.length - 1].f2649d) {
            d2 = arcArr[arcArr.length - 1].f2649d;
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr2 = this.f2645a;
            if (i2 >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i2];
            if (d2 <= arc.f2649d) {
                if (arc.f2663r) {
                    dArr[0] = arc.getLinearDX(d2);
                    dArr[1] = this.f2645a[i2].getLinearDY(d2);
                    return;
                } else {
                    arc.f(d2);
                    dArr[0] = this.f2645a[i2].a();
                    dArr[1] = this.f2645a[i2].b();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mTime;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getSlope(double d2, int i2) {
        Arc[] arcArr = this.f2645a;
        int i3 = 0;
        double d3 = arcArr[0].f2648c;
        if (d2 < d3) {
            d2 = d3;
        }
        if (d2 > arcArr[arcArr.length - 1].f2649d) {
            d2 = arcArr[arcArr.length - 1].f2649d;
        }
        while (true) {
            Arc[] arcArr2 = this.f2645a;
            if (i3 >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc = arcArr2[i3];
            if (d2 <= arc.f2649d) {
                if (arc.f2663r) {
                    if (i2 == 0) {
                        return arc.getLinearDX(d2);
                    }
                    return arc.getLinearDY(d2);
                }
                arc.f(d2);
                if (i2 == 0) {
                    return this.f2645a[i3].a();
                }
                return this.f2645a[i3].b();
            }
            i3++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d2, float[] fArr) {
        if (this.mExtrapolate) {
            Arc[] arcArr = this.f2645a;
            Arc arc = arcArr[0];
            double d3 = arc.f2648c;
            if (d2 < d3) {
                double d4 = d2 - d3;
                if (arc.f2663r) {
                    fArr[0] = (float) (arc.getLinearX(d3) + (this.f2645a[0].getLinearDX(d3) * d4));
                    fArr[1] = (float) (this.f2645a[0].getLinearY(d3) + (d4 * this.f2645a[0].getLinearDY(d3)));
                    return;
                } else {
                    arc.f(d3);
                    fArr[0] = (float) (this.f2645a[0].c() + (this.f2645a[0].a() * d4));
                    fArr[1] = (float) (this.f2645a[0].d() + (d4 * this.f2645a[0].b()));
                    return;
                }
            }
            if (d2 > arcArr[arcArr.length - 1].f2649d) {
                double d5 = arcArr[arcArr.length - 1].f2649d;
                double d6 = d2 - d5;
                int length = arcArr.length - 1;
                Arc arc2 = arcArr[length];
                if (arc2.f2663r) {
                    fArr[0] = (float) (arc2.getLinearX(d5) + (this.f2645a[length].getLinearDX(d5) * d6));
                    fArr[1] = (float) (this.f2645a[length].getLinearY(d5) + (d6 * this.f2645a[length].getLinearDY(d5)));
                    return;
                } else {
                    arc2.f(d2);
                    fArr[0] = (float) this.f2645a[length].c();
                    fArr[1] = (float) this.f2645a[length].d();
                    return;
                }
            }
        } else {
            Arc[] arcArr2 = this.f2645a;
            double d7 = arcArr2[0].f2648c;
            if (d2 < d7) {
                d2 = d7;
            } else if (d2 > arcArr2[arcArr2.length - 1].f2649d) {
                d2 = arcArr2[arcArr2.length - 1].f2649d;
            }
        }
        int i2 = 0;
        while (true) {
            Arc[] arcArr3 = this.f2645a;
            if (i2 >= arcArr3.length) {
                return;
            }
            Arc arc3 = arcArr3[i2];
            if (d2 <= arc3.f2649d) {
                if (arc3.f2663r) {
                    fArr[0] = (float) arc3.getLinearX(d2);
                    fArr[1] = (float) this.f2645a[i2].getLinearY(d2);
                    return;
                } else {
                    arc3.f(d2);
                    fArr[0] = (float) this.f2645a[i2].c();
                    fArr[1] = (float) this.f2645a[i2].d();
                    return;
                }
            }
            i2++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getPos(double d2, int i2) {
        double linearY;
        double linearDY;
        double d3;
        double dB;
        double linearY2;
        double linearDY2;
        int i3 = 0;
        if (this.mExtrapolate) {
            Arc[] arcArr = this.f2645a;
            Arc arc = arcArr[0];
            double d4 = arc.f2648c;
            if (d2 < d4) {
                double d5 = d2 - d4;
                if (arc.f2663r) {
                    if (i2 == 0) {
                        linearY2 = arc.getLinearX(d4);
                        linearDY2 = this.f2645a[0].getLinearDX(d4);
                    } else {
                        linearY2 = arc.getLinearY(d4);
                        linearDY2 = this.f2645a[0].getLinearDY(d4);
                    }
                    return linearY2 + (d5 * linearDY2);
                }
                arc.f(d4);
                if (i2 == 0) {
                    d3 = this.f2645a[0].c();
                    dB = this.f2645a[0].a();
                } else {
                    d3 = this.f2645a[0].d();
                    dB = this.f2645a[0].b();
                }
                return d3 + (d5 * dB);
            }
            if (d2 > arcArr[arcArr.length - 1].f2649d) {
                double d6 = arcArr[arcArr.length - 1].f2649d;
                double d7 = d2 - d6;
                int length = arcArr.length - 1;
                if (i2 == 0) {
                    linearY = arcArr[length].getLinearX(d6);
                    linearDY = this.f2645a[length].getLinearDX(d6);
                } else {
                    linearY = arcArr[length].getLinearY(d6);
                    linearDY = this.f2645a[length].getLinearDY(d6);
                }
                return linearY + (d7 * linearDY);
            }
        } else {
            Arc[] arcArr2 = this.f2645a;
            double d8 = arcArr2[0].f2648c;
            if (d2 < d8) {
                d2 = d8;
            } else if (d2 > arcArr2[arcArr2.length - 1].f2649d) {
                d2 = arcArr2[arcArr2.length - 1].f2649d;
            }
        }
        while (true) {
            Arc[] arcArr3 = this.f2645a;
            if (i3 >= arcArr3.length) {
                return Double.NaN;
            }
            Arc arc2 = arcArr3[i3];
            if (d2 <= arc2.f2649d) {
                if (arc2.f2663r) {
                    if (i2 == 0) {
                        return arc2.getLinearX(d2);
                    }
                    return arc2.getLinearY(d2);
                }
                arc2.f(d2);
                if (i2 == 0) {
                    return this.f2645a[i3].c();
                }
                return this.f2645a[i3].d();
            }
            i3++;
        }
    }
}

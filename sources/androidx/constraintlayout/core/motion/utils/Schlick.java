package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public class Schlick extends Easing {
    private static final boolean DEBUG = false;

    /* renamed from: c, reason: collision with root package name */
    double f2726c;

    /* renamed from: d, reason: collision with root package name */
    double f2727d;

    Schlick(String str) {
        this.f2667a = str;
        int iIndexOf = str.indexOf(40);
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        this.f2726c = Double.parseDouble(str.substring(iIndexOf + 1, iIndexOf2).trim());
        int i2 = iIndexOf2 + 1;
        this.f2727d = Double.parseDouble(str.substring(i2, str.indexOf(44, i2)).trim());
    }

    private double dfunc(double d2) {
        double d3 = this.f2727d;
        if (d2 < d3) {
            double d4 = this.f2726c;
            return ((d4 * d3) * d3) / ((((d3 - d2) * d4) + d2) * ((d4 * (d3 - d2)) + d2));
        }
        double d5 = this.f2726c;
        return (((d3 - 1.0d) * d5) * (d3 - 1.0d)) / (((((-d5) * (d3 - d2)) - d2) + 1.0d) * ((((-d5) * (d3 - d2)) - d2) + 1.0d));
    }

    private double func(double d2) {
        double d3 = this.f2727d;
        return d2 < d3 ? (d3 * d2) / (d2 + (this.f2726c * (d3 - d2))) : ((1.0d - d3) * (d2 - 1.0d)) / ((1.0d - d2) - (this.f2726c * (d3 - d2)));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double get(double d2) {
        return func(d2);
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double getDiff(double d2) {
        return dfunc(d2);
    }
}

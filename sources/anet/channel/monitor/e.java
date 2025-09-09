package anet.channel.monitor;

/* loaded from: classes2.dex */
class e {

    /* renamed from: b, reason: collision with root package name */
    private double f6836b;

    /* renamed from: c, reason: collision with root package name */
    private double f6837c;

    /* renamed from: d, reason: collision with root package name */
    private double f6838d;

    /* renamed from: e, reason: collision with root package name */
    private double f6839e;

    /* renamed from: f, reason: collision with root package name */
    private double f6840f;

    /* renamed from: g, reason: collision with root package name */
    private double f6841g;

    /* renamed from: h, reason: collision with root package name */
    private double f6842h;

    /* renamed from: a, reason: collision with root package name */
    private long f6835a = 0;

    /* renamed from: i, reason: collision with root package name */
    private double f6843i = 0.0d;

    /* renamed from: j, reason: collision with root package name */
    private double f6844j = 0.0d;

    /* renamed from: k, reason: collision with root package name */
    private double f6845k = 0.0d;

    e() {
    }

    public double a(double d2, double d3) {
        char c2;
        double d4 = d2 / d3;
        if (d4 < 8.0d) {
            if (this.f6835a != 0) {
                return this.f6845k;
            }
            this.f6845k = d4;
            return d4;
        }
        long j2 = this.f6835a;
        if (j2 == 0) {
            this.f6843i = d4;
            this.f6842h = d4;
            this.f6838d = d4 * 0.1d;
            this.f6837c = 0.02d * d4;
            this.f6839e = 0.1d * d4 * d4;
        } else if (j2 == 1) {
            this.f6844j = d4;
            this.f6842h = d4;
        } else {
            double d5 = this.f6844j;
            double d6 = d4 - d5;
            this.f6843i = d5;
            this.f6844j = d4;
            double d7 = d4 / 0.95d;
            this.f6836b = d7;
            this.f6841g = d7 - (this.f6842h * 0.95d);
            double dSqrt = Math.sqrt(this.f6838d);
            double d8 = this.f6841g;
            if (d8 >= 4.0d * dSqrt) {
                this.f6841g = (d8 * 0.75d) + (dSqrt * 2.0d);
                c2 = 1;
            } else if (d8 <= (-4.0d) * dSqrt) {
                this.f6841g = (dSqrt * (-1.0d)) + (d8 * 0.75d);
                c2 = 2;
            } else {
                c2 = 0;
            }
            double d9 = this.f6838d * 1.05d;
            double d10 = this.f6841g;
            double dMin = Math.min(Math.max(Math.abs(d9 - ((0.0025d * d10) * d10)), this.f6838d * 0.8d), this.f6838d * 1.25d);
            this.f6838d = dMin;
            double d11 = this.f6839e;
            double d12 = d11 / ((0.9025d * d11) + dMin);
            this.f6840f = d12;
            double d13 = this.f6842h + (1.0526315789473684d * d6) + (d12 * this.f6841g);
            this.f6842h = d13;
            if (c2 == 1) {
                this.f6842h = Math.min(d13, this.f6836b);
            } else if (c2 == 2) {
                this.f6842h = Math.max(d13, this.f6836b);
            }
            this.f6839e = (1.0d - (0.95d * this.f6840f)) * (this.f6839e + this.f6837c);
        }
        double d14 = this.f6842h;
        if (d14 < 0.0d) {
            double d15 = this.f6844j * 0.7d;
            this.f6845k = d15;
            this.f6842h = d15;
        } else {
            this.f6845k = d14;
        }
        return this.f6845k;
    }

    public void a() {
        this.f6835a = 0L;
        this.f6845k = 0.0d;
    }
}

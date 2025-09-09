package anet.channel.monitor;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: b, reason: collision with root package name */
    protected long f6847b;

    /* renamed from: c, reason: collision with root package name */
    private final double f6848c = 40.0d;

    /* renamed from: a, reason: collision with root package name */
    boolean f6846a = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f6849d = true;

    public int a() {
        return 0;
    }

    protected final boolean b() {
        if (!this.f6849d) {
            return false;
        }
        if (System.currentTimeMillis() - this.f6847b <= a() * 1000) {
            return true;
        }
        this.f6849d = false;
        return false;
    }

    public boolean a(double d2) {
        return d2 < 40.0d;
    }
}

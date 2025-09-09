package anet.channel.monitor;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    static int f6815a = 0;

    /* renamed from: b, reason: collision with root package name */
    static long f6816b = 0;

    /* renamed from: c, reason: collision with root package name */
    static long f6817c = 0;

    /* renamed from: d, reason: collision with root package name */
    static long f6818d = 0;

    /* renamed from: e, reason: collision with root package name */
    static long f6819e = 0;

    /* renamed from: f, reason: collision with root package name */
    static long f6820f = 0;

    /* renamed from: g, reason: collision with root package name */
    static double f6821g = 0.0d;

    /* renamed from: h, reason: collision with root package name */
    static double f6822h = 0.0d;

    /* renamed from: i, reason: collision with root package name */
    static double f6823i = 0.0d;

    /* renamed from: j, reason: collision with root package name */
    static double f6824j = 40.0d;

    /* renamed from: k, reason: collision with root package name */
    private static volatile boolean f6825k = false;

    /* renamed from: l, reason: collision with root package name */
    private int f6826l;

    /* renamed from: m, reason: collision with root package name */
    private int f6827m;

    /* renamed from: n, reason: collision with root package name */
    private e f6828n;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        static b f6829a = new b(null);

        a() {
        }
    }

    /* synthetic */ b(c cVar) {
        this();
    }

    static /* synthetic */ int b(b bVar) {
        int i2 = bVar.f6827m;
        bVar.f6827m = i2 + 1;
        return i2;
    }

    public synchronized void d() {
        try {
            ALog.i("awcn.BandWidthSampler", "[startNetworkMeter]", null, "NetworkStatus", NetworkStatusHelper.getStatus());
        } catch (Exception e2) {
            ALog.w("awcn.BandWidthSampler", "startNetworkMeter fail.", null, e2, new Object[0]);
        }
        if (NetworkStatusHelper.getStatus() == NetworkStatusHelper.NetworkStatus.G2) {
            f6825k = false;
        } else {
            f6825k = true;
        }
    }

    public void e() {
        f6825k = false;
    }

    private b() {
        this.f6826l = 5;
        this.f6827m = 0;
        this.f6828n = new e();
        NetworkStatusHelper.addStatusChangeListener(new c(this));
    }

    public double c() {
        return f6823i;
    }

    public static b a() {
        return a.f6829a;
    }

    public int b() {
        if (NetworkStatusHelper.getStatus() == NetworkStatusHelper.NetworkStatus.G2) {
            return 1;
        }
        return this.f6826l;
    }

    public void a(long j2, long j3, long j4) {
        if (f6825k) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.BandWidthSampler", "onDataReceived", null, "mRequestStartTime", Long.valueOf(j2), "mRequestFinishedTime", Long.valueOf(j3), "mRequestDataSize", Long.valueOf(j4));
            }
            if (j4 <= 3000 || j2 >= j3) {
                return;
            }
            ThreadPoolExecutorFactory.submitScheduledTask(new d(this, j4, j3, j2));
        }
    }
}

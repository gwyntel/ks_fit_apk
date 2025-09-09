package anet.channel.detect;

import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static d f6743a = new d();

    /* renamed from: b, reason: collision with root package name */
    private static ExceptionDetector f6744b = new ExceptionDetector();

    /* renamed from: c, reason: collision with root package name */
    private static k f6745c = new k();

    /* renamed from: d, reason: collision with root package name */
    private static AtomicBoolean f6746d = new AtomicBoolean(false);

    public static void a() {
        try {
            if (f6746d.compareAndSet(false, true)) {
                ALog.i("awcn.NetworkDetector", "registerListener", null, new Object[0]);
                f6743a.b();
                f6744b.a();
                f6745c.a();
            }
        } catch (Exception e2) {
            ALog.e("awcn.NetworkDetector", "[registerListener]error", null, e2, new Object[0]);
        }
    }

    public static void a(RequestStatistic requestStatistic) {
        if (f6746d.get()) {
            f6744b.a(requestStatistic);
        }
    }
}

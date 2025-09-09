package anet.channel.monitor;

import anet.channel.util.ALog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f6812a;

    /* renamed from: b, reason: collision with root package name */
    private Map<INetworkQualityChangeListener, f> f6813b = new ConcurrentHashMap();

    /* renamed from: c, reason: collision with root package name */
    private f f6814c = new f();

    private a() {
    }

    public static a a() {
        if (f6812a == null) {
            synchronized (a.class) {
                try {
                    if (f6812a == null) {
                        f6812a = new a();
                    }
                } finally {
                }
            }
        }
        return f6812a;
    }

    public void a(INetworkQualityChangeListener iNetworkQualityChangeListener, f fVar) {
        if (iNetworkQualityChangeListener == null) {
            ALog.e("BandWidthListenerHelp", "listener is null", null, new Object[0]);
            return;
        }
        if (fVar == null) {
            this.f6814c.f6847b = System.currentTimeMillis();
            this.f6813b.put(iNetworkQualityChangeListener, this.f6814c);
        } else {
            fVar.f6847b = System.currentTimeMillis();
            this.f6813b.put(iNetworkQualityChangeListener, fVar);
        }
    }

    public void a(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        this.f6813b.remove(iNetworkQualityChangeListener);
    }

    public void a(double d2) {
        boolean zA;
        for (Map.Entry<INetworkQualityChangeListener, f> entry : this.f6813b.entrySet()) {
            INetworkQualityChangeListener key = entry.getKey();
            f value = entry.getValue();
            if (key != null && value != null && !value.b() && value.f6846a != (zA = value.a(d2))) {
                value.f6846a = zA;
                key.onNetworkQualityChanged(zA ? NetworkSpeed.Slow : NetworkSpeed.Fast);
            }
        }
    }
}

package anetwork.channel.monitor;

import android.content.Context;
import anet.channel.monitor.INetworkQualityChangeListener;
import anet.channel.monitor.NetworkSpeed;
import anet.channel.monitor.a;
import anet.channel.monitor.b;
import anet.channel.monitor.f;
import anet.channel.util.ALog;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class Monitor {
    private static final String TAG = "anet.Monitor";
    static AtomicBoolean isInit = new AtomicBoolean(false);

    public static void addListener(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        addListener(iNetworkQualityChangeListener, null);
    }

    public static NetworkSpeed getNetSpeed() {
        NetworkSpeed networkSpeed = NetworkSpeed.Fast;
        try {
            return NetworkSpeed.valueOfCode(b.a().b());
        } catch (Throwable th) {
            ALog.e(TAG, "getNetworkSpeed failed", null, th, new Object[0]);
            return networkSpeed;
        }
    }

    public static double getNetSpeedValue() {
        return b.a().c();
    }

    @Deprecated
    public static anetwork.channel.monitor.speed.NetworkSpeed getNetworkSpeed() {
        return anetwork.channel.monitor.speed.NetworkSpeed.valueOfCode(getNetSpeed().getCode());
    }

    public static synchronized void init() {
        if (isInit.compareAndSet(false, true)) {
            b.a().d();
        }
    }

    public static void removeListener(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        a.a().a(iNetworkQualityChangeListener);
    }

    public static void start() {
        try {
            b.a().d();
        } catch (Throwable th) {
            ALog.e(TAG, "start failed", null, th, new Object[0]);
        }
    }

    public static void stop() {
        try {
            b.a().e();
        } catch (Throwable th) {
            ALog.e(TAG, "stop failed", null, th, new Object[0]);
        }
    }

    public static void addListener(INetworkQualityChangeListener iNetworkQualityChangeListener, f fVar) {
        a.a().a(iNetworkQualityChangeListener, fVar);
    }

    @Deprecated
    public static synchronized void init(Context context) {
        init();
    }
}

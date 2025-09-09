package anetwork.channel.aidl.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Looper;
import anet.channel.util.ALog;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.NetworkService;
import anetwork.channel.config.NetworkConfigCenter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    static volatile IRemoteNetworkGetter f7129a = null;

    /* renamed from: b, reason: collision with root package name */
    static volatile boolean f7130b = false;

    /* renamed from: c, reason: collision with root package name */
    static volatile boolean f7131c = false;

    /* renamed from: d, reason: collision with root package name */
    static volatile CountDownLatch f7132d;

    /* renamed from: e, reason: collision with root package name */
    static Handler f7133e = new Handler(Looper.getMainLooper());

    /* renamed from: f, reason: collision with root package name */
    private static ServiceConnection f7134f = new e();

    public static void a(Context context, boolean z2) {
        if (f7129a == null && !f7130b) {
            a(context);
            if (f7130b || !z2) {
                return;
            }
            try {
                synchronized (d.class) {
                    try {
                        if (f7129a != null) {
                            return;
                        }
                        if (f7132d == null) {
                            f7132d = new CountDownLatch(1);
                        }
                        ALog.i("anet.RemoteGetter", "[initRemoteGetterAndWait]begin to wait", null, new Object[0]);
                        if (f7132d.await(NetworkConfigCenter.getServiceBindWaitTime(), TimeUnit.SECONDS)) {
                            ALog.i("anet.RemoteGetter", "mServiceBindLock count down to 0", null, new Object[0]);
                        } else {
                            ALog.i("anet.RemoteGetter", "mServiceBindLock wait timeout", null, new Object[0]);
                        }
                    } finally {
                    }
                }
            } catch (InterruptedException unused) {
                ALog.e("anet.RemoteGetter", "mServiceBindLock wait interrupt", null, new Object[0]);
            }
        }
    }

    public static IRemoteNetworkGetter a() {
        return f7129a;
    }

    private static void a(Context context) {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.RemoteGetter", "[asyncBindService] mContext:" + context + " bBindFailed:" + f7130b + " bBinding:" + f7131c, null, new Object[0]);
        }
        if (context == null || f7130b || f7131c) {
            return;
        }
        f7131c = true;
        Intent intent = new Intent(context, (Class<?>) NetworkService.class);
        intent.setAction(IRemoteNetworkGetter.class.getName());
        intent.addCategory("android.intent.category.DEFAULT");
        f7130b = !context.bindService(intent, f7134f, 1);
        if (f7130b) {
            f7131c = false;
            ALog.e("anet.RemoteGetter", "[asyncBindService]ANet_Service start not success. ANet run with local mode!", null, new Object[0]);
        }
        f7133e.postDelayed(new f(), 10000L);
    }
}

package com.vivo.push.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.media3.exoplayer.ExoPlayer;
import com.vivo.push.PushClient;
import com.vivo.push.cache.ClientConfigManagerImpl;
import com.vivo.push.e;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.VivoPushException;
import com.vivo.push.util.p;
import com.vivo.push.util.r;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes4.dex */
public class PushServiceReceiver extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private static HandlerThread f23202a;

    /* renamed from: b, reason: collision with root package name */
    private static Handler f23203b;

    /* renamed from: c, reason: collision with root package name */
    private static a f23204c = new a();

    static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f23205a;

        /* renamed from: b, reason: collision with root package name */
        private String f23206b;

        a() {
        }

        static /* synthetic */ void a(a aVar, Context context, String str) {
            aVar.f23205a = ContextDelegate.getContext(context);
            aVar.f23206b = str;
        }

        @Override // java.lang.Runnable
        public final void run() throws PackageManager.NameNotFoundException {
            NetworkInfo networkInfoA = r.a(this.f23205a);
            if (!(networkInfoA != null ? networkInfoA.isConnectedOrConnecting() : false)) {
                p.d("PushServiceReceiver", this.f23205a.getPackageName() + ": 无网络  by " + this.f23206b);
                p.a(this.f23205a, "触发静态广播:无网络(" + this.f23206b + "," + this.f23205a.getPackageName() + ")");
                return;
            }
            p.d("PushServiceReceiver", this.f23205a.getPackageName() + ": 执行开始出发动作: " + this.f23206b);
            p.a(this.f23205a, "触发静态广播(" + this.f23206b + "," + this.f23205a.getPackageName() + ")");
            e.a().a(this.f23205a);
            if (ClientConfigManagerImpl.getInstance(this.f23205a).isCancleBroadcastReceiver()) {
                return;
            }
            try {
                PushClient.getInstance(this.f23205a).initialize();
            } catch (VivoPushException e2) {
                e2.printStackTrace();
                p.a(this.f23205a, " 初始化异常 error= " + e2.getMessage());
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Context context2 = ContextDelegate.getContext(context);
        String action = intent.getAction();
        if (ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION.equals(action) || "android.intent.action.ACTION_POWER_CONNECTED".equals(action) || "android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
            if (f23202a == null) {
                HandlerThread handlerThread = new HandlerThread("PushServiceReceiver");
                f23202a = handlerThread;
                handlerThread.start();
                f23203b = new Handler(f23202a.getLooper());
            }
            p.d("PushServiceReceiver", context2.getPackageName() + ": start PushSerevice for by " + action + "  ; handler : " + f23203b);
            a.a(f23204c, context2, action);
            f23203b.removeCallbacks(f23204c);
            f23203b.postDelayed(f23204c, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        }
    }
}

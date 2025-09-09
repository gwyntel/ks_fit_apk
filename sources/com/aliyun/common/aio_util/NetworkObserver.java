package com.aliyun.common.aio_util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.aliyun.aio.keep.CalledByNative;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;

/* loaded from: classes2.dex */
class NetworkObserver extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    private Context f11521a;

    /* renamed from: b, reason: collision with root package name */
    private volatile String f11522b = "";

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f11523c = false;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final NetworkObserver f11524a = new NetworkObserver();
    }

    NetworkObserver() {
    }

    private static NetworkObserver a() {
        return a.f11524a;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(android.content.Context r4) {
        /*
            r3 = this;
            java.lang.String r0 = "connectivity"
            java.lang.Object r4 = r4.getSystemService(r0)     // Catch: java.lang.Throwable -> L13
            android.net.ConnectivityManager r4 = (android.net.ConnectivityManager) r4     // Catch: java.lang.Throwable -> L13
            android.net.NetworkInfo r4 = r4.getActiveNetworkInfo()     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L16
            java.lang.String r4 = "NoActive"
            r3.f11522b = r4     // Catch: java.lang.Throwable -> L13
            return
        L13:
            r4 = move-exception
            goto L8e
        L16:
            java.lang.String r0 = r4.getSubtypeName()     // Catch: java.lang.Throwable -> L13
            int r1 = r4.getType()     // Catch: java.lang.Throwable -> L13
            r2 = 1
            if (r1 != r2) goto L25
            java.lang.String r0 = "WIFI"
            goto L8b
        L25:
            int r1 = r4.getType()     // Catch: java.lang.Throwable -> L13
            if (r1 != 0) goto L6c
            int r4 = r4.getSubtype()     // Catch: java.lang.Throwable -> L13
            switch(r4) {
                case 1: goto L69;
                case 2: goto L69;
                case 3: goto L66;
                case 4: goto L69;
                case 5: goto L66;
                case 6: goto L66;
                case 7: goto L69;
                case 8: goto L66;
                case 9: goto L66;
                case 10: goto L66;
                case 11: goto L69;
                case 12: goto L66;
                case 13: goto L63;
                case 14: goto L66;
                case 15: goto L66;
                case 16: goto L69;
                case 17: goto L66;
                case 18: goto L60;
                case 19: goto L63;
                case 20: goto L5d;
                default: goto L32;
            }     // Catch: java.lang.Throwable -> L13
        L32:
            java.lang.String r4 = "TD-SCDMA"
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L66
            java.lang.String r4 = "WCDMA"
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch: java.lang.Throwable -> L13
            if (r4 != 0) goto L66
            java.lang.String r4 = "CDMA2000"
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch: java.lang.Throwable -> L13
            if (r4 == 0) goto L4b
            goto L66
        L4b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L13
            r4.<init>()     // Catch: java.lang.Throwable -> L13
            java.lang.String r1 = "Mobile:"
            r4.append(r1)     // Catch: java.lang.Throwable -> L13
            r4.append(r0)     // Catch: java.lang.Throwable -> L13
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L13
            goto L8b
        L5d:
            java.lang.String r0 = "5G"
            goto L8b
        L60:
            java.lang.String r0 = "IWLAN"
            goto L8b
        L63:
            java.lang.String r0 = "4G"
            goto L8b
        L66:
            java.lang.String r0 = "3G"
            goto L8b
        L69:
            java.lang.String r0 = "2G"
            goto L8b
        L6c:
            int r1 = r4.getType()     // Catch: java.lang.Throwable -> L13
            r2 = 7
            if (r1 != r2) goto L76
            java.lang.String r0 = "BlueTooth"
            goto L8b
        L76:
            int r1 = r4.getType()     // Catch: java.lang.Throwable -> L13
            r2 = 9
            if (r1 != r2) goto L81
            java.lang.String r0 = "EtherNet"
            goto L8b
        L81:
            int r4 = r4.getType()     // Catch: java.lang.Throwable -> L13
            r1 = 17
            if (r4 != r1) goto L8b
            java.lang.String r0 = "VPN"
        L8b:
            r3.f11522b = r0     // Catch: java.lang.Throwable -> L13
            goto L91
        L8e:
            r4.printStackTrace()
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.common.aio_util.NetworkObserver.b(android.content.Context):void");
    }

    @CalledByNative
    public static String getNetworkType(Context context) {
        NetworkObserver networkObserverA = a();
        if (!networkObserverA.f11523c && context != null) {
            networkObserverA.a(context);
            networkObserverA.b(context);
        }
        return networkObserverA.f11522b;
    }

    @CalledByNative
    public static boolean isNetworkAvailable(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnectedOrConnecting()) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        b(this.f11521a);
    }

    private synchronized void a(Context context) {
        if (!this.f11523c) {
            this.f11521a = context;
            try {
                context.registerReceiver(this, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
            } catch (Throwable th) {
                th.printStackTrace();
            }
            this.f11523c = true;
        }
    }
}

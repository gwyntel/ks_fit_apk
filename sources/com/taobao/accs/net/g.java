package com.taobao.accs.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.internal.AccsJobService;
import com.taobao.accs.utl.ALog;
import com.yc.utesdk.ble.close.KeyType;

/* loaded from: classes4.dex */
public abstract class g {

    /* renamed from: b, reason: collision with root package name */
    protected static volatile g f20217b;

    /* renamed from: c, reason: collision with root package name */
    private static final int[] f20218c = {KeyType.QUERY_BRIGHT_SCREEN_PARAM, 360, 480};

    /* renamed from: a, reason: collision with root package name */
    protected Context f20219a;

    /* renamed from: d, reason: collision with root package name */
    private int f20220d;

    /* renamed from: e, reason: collision with root package name */
    private long f20221e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f20222f = false;

    /* renamed from: g, reason: collision with root package name */
    private int[] f20223g = {0, 0, 0};

    /* renamed from: h, reason: collision with root package name */
    private boolean f20224h;

    protected g(Context context) {
        this.f20224h = true;
        try {
            this.f20219a = context;
            this.f20220d = 0;
            this.f20221e = System.currentTimeMillis();
            this.f20224h = com.taobao.accs.utl.t.a();
        } catch (Throwable th) {
            ALog.e("HeartbeatManager", "HeartbeatManager", th, new Object[0]);
        }
    }

    public static g a(Context context) {
        if (f20217b == null) {
            synchronized (g.class) {
                try {
                    if (f20217b == null) {
                        if (GlobalConfig.isJobHeartbeatEnable() && b(context)) {
                            ALog.i("HeartbeatManager", "hb use job", new Object[0]);
                            f20217b = new f(context);
                        } else {
                            ALog.i("HeartbeatManager", "hb use alarm", new Object[0]);
                            f20217b = new e(context);
                        }
                    }
                } finally {
                }
            }
        }
        return f20217b;
    }

    private static boolean b(Context context) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context.getPackageName(), AccsJobService.class.getName()), 0);
            if (serviceInfo != null) {
                return serviceInfo.isEnabled();
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    protected abstract void a(int i2);

    public void c() {
        this.f20221e = -1L;
        if (this.f20222f) {
            int[] iArr = this.f20223g;
            int i2 = this.f20220d;
            iArr[i2] = iArr[i2] + 1;
        }
        int i3 = this.f20220d;
        this.f20220d = i3 > 0 ? i3 - 1 : 0;
        ALog.d("HeartbeatManager", "onNetworkTimeout", new Object[0]);
    }

    public void d() {
        this.f20221e = -1L;
        ALog.d("HeartbeatManager", "onNetworkFail", new Object[0]);
    }

    public void e() {
        ALog.d("HeartbeatManager", "onHeartbeatSucc", new Object[0]);
        if (System.currentTimeMillis() - this.f20221e <= 7199000) {
            this.f20222f = false;
            this.f20223g[this.f20220d] = 0;
            return;
        }
        int i2 = this.f20220d;
        if (i2 >= f20218c.length - 1 || this.f20223g[i2] > 2) {
            return;
        }
        ALog.d("HeartbeatManager", "upgrade", new Object[0]);
        this.f20220d++;
        this.f20222f = true;
        this.f20221e = System.currentTimeMillis();
    }

    public void f() {
        this.f20220d = 0;
        this.f20221e = System.currentTimeMillis();
        ALog.d("HeartbeatManager", "resetLevel", new Object[0]);
    }

    public int b() {
        int i2 = this.f20224h ? f20218c[this.f20220d] : KeyType.QUERY_BRIGHT_SCREEN_PARAM;
        this.f20224h = com.taobao.accs.utl.t.a();
        return i2;
    }

    public synchronized void a() {
        try {
            if (this.f20221e < 0) {
                this.f20221e = System.currentTimeMillis();
            }
            int iB = b();
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.d("HeartbeatManager", "set " + iB, new Object[0]);
            }
            a(iB);
        } finally {
        }
    }
}

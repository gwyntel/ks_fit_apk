package com.hihonor.push.sdk;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.hihonor.push.framework.aidl.IPushInvoke;
import com.hihonor.push.sdk.b0;
import com.hihonor.push.sdk.bean.RemoteServiceBean;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import com.hihonor.push.sdk.z;

/* loaded from: classes3.dex */
public class f0 implements ServiceConnection {

    /* renamed from: e, reason: collision with root package name */
    public static final Object f15483e = new Object();

    /* renamed from: a, reason: collision with root package name */
    public final RemoteServiceBean f15484a;

    /* renamed from: b, reason: collision with root package name */
    public a f15485b;

    /* renamed from: c, reason: collision with root package name */
    public Handler f15486c = null;

    /* renamed from: d, reason: collision with root package name */
    public boolean f15487d = false;

    public interface a {
    }

    public f0(RemoteServiceBean remoteServiceBean) {
        this.f15484a = remoteServiceBean;
    }

    public final void a(int i2) {
        a aVar = this.f15485b;
        if (aVar != null) {
            c0 c0Var = (c0) aVar;
            c0Var.f15470a.f15473a.set(i2 == HonorPushErrorEnum.ERROR_SERVICE_TIME_OUT.statusCode ? 2 : 1);
            c0Var.f15470a.a(i2);
            c0Var.f15470a.f15474b = null;
        }
    }

    public void b() {
        try {
            Log.i("AIDLSrvConnection", "trying to unbind service from " + this);
            l.f15511e.a().unbindService(this);
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        Log.i("AIDLSrvConnection", "enter onNullBinding, than unBind.");
        if (this.f15487d) {
            this.f15487d = false;
            return;
        }
        b();
        a();
        a aVar = this.f15485b;
        if (aVar != null) {
            c0 c0Var = (c0) aVar;
            c0Var.f15470a.f15473a.set(1);
            c0Var.f15470a.a(8002005);
            c0Var.f15470a.f15474b = null;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i("AIDLSrvConnection", "enter onServiceConnected.");
        a();
        a aVar = this.f15485b;
        if (aVar != null) {
            c0 c0Var = (c0) aVar;
            c0Var.f15470a.f15474b = IPushInvoke.Stub.asInterface(iBinder);
            if (c0Var.f15470a.f15474b == null) {
                c0Var.f15470a.f15476d.b();
                c0Var.f15470a.f15473a.set(1);
                c0Var.f15470a.a(8002001);
                return;
            }
            c0Var.f15470a.f15473a.set(3);
            b0.a aVar2 = c0Var.f15470a.f15475c;
            if (aVar2 != null) {
                z.a aVar3 = (z.a) aVar2;
                if (Looper.myLooper() == z.this.f15570a.getLooper()) {
                    aVar3.b();
                } else {
                    z.this.f15570a.post(new x(aVar3));
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.i("AIDLSrvConnection", "enter onServiceDisconnected.");
        a aVar = this.f15485b;
        if (aVar != null) {
            c0 c0Var = (c0) aVar;
            c0Var.f15470a.f15473a.set(1);
            c0Var.f15470a.a(8002002);
            c0Var.f15470a.f15474b = null;
        }
    }

    public final void a() {
        synchronized (f15483e) {
            try {
                Handler handler = this.f15486c;
                if (handler != null) {
                    handler.removeMessages(1001);
                    this.f15486c = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}

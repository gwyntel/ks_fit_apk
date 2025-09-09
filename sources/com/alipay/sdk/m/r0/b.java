package com.alipay.sdk.m.r0;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.alipay.sdk.m.q0.a;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: e, reason: collision with root package name */
    public static String f9701e = "OpenDeviceId library";

    /* renamed from: f, reason: collision with root package name */
    public static boolean f9702f = false;

    /* renamed from: b, reason: collision with root package name */
    public com.alipay.sdk.m.q0.a f9704b;

    /* renamed from: c, reason: collision with root package name */
    public ServiceConnection f9705c;

    /* renamed from: a, reason: collision with root package name */
    public Context f9703a = null;

    /* renamed from: d, reason: collision with root package name */
    public InterfaceC0054b f9706d = null;

    public class a implements ServiceConnection {
        public a() {
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                b.this.f9704b = a.AbstractBinderC0052a.a(iBinder);
                if (b.this.f9706d != null) {
                    b.this.f9706d.a("Deviceid Service Connected", b.this);
                }
                b.this.b("Service onServiceConnected");
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            b.this.f9704b = null;
            b.this.b("Service onServiceDisconnected");
        }
    }

    /* renamed from: com.alipay.sdk.m.r0.b$b, reason: collision with other inner class name */
    public interface InterfaceC0054b<T> {
        void a(T t2, b bVar);
    }

    public String b() {
        if (this.f9703a == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            com.alipay.sdk.m.q0.a aVar = this.f9704b;
            if (aVar != null) {
                return aVar.a();
            }
            return null;
        } catch (RemoteException e2) {
            a("getOAID error, RemoteException!");
            e2.printStackTrace();
            return null;
        }
    }

    public String c() {
        if (this.f9703a == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            com.alipay.sdk.m.q0.a aVar = this.f9704b;
            if (aVar != null) {
                return aVar.b();
            }
            return null;
        } catch (RemoteException e2) {
            a("getUDID error, RemoteException!");
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            a("getUDID error, Exception!");
            e3.printStackTrace();
            return null;
        }
    }

    public String d() {
        Context context = this.f9703a;
        if (context == null) {
            b("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        b("liufeng, getVAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            b("input package is null!");
            return null;
        }
        try {
            com.alipay.sdk.m.q0.a aVar = this.f9704b;
            if (aVar != null) {
                return aVar.b(packageName);
            }
            return null;
        } catch (RemoteException e2) {
            a("getVAID error, RemoteException!");
            e2.printStackTrace();
            return null;
        }
    }

    public boolean e() {
        try {
            if (this.f9704b == null) {
                return false;
            }
            b("Device support opendeviceid");
            return this.f9704b.c();
        } catch (RemoteException unused) {
            a("isSupport error, RemoteException!");
            return false;
        }
    }

    public void f() {
        try {
            this.f9703a.unbindService(this.f9705c);
            b("unBind Service successful");
        } catch (IllegalArgumentException unused) {
            a("unBind Service exception");
        }
        this.f9704b = null;
    }

    public int a(Context context, InterfaceC0054b<String> interfaceC0054b) {
        if (context != null) {
            this.f9703a = context;
            this.f9706d = interfaceC0054b;
            this.f9705c = new a();
            Intent intent = new Intent();
            intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
            if (this.f9703a.bindService(intent, this.f9705c, 1)) {
                b("bindService Successful!");
                return 1;
            }
            b("bindService Failed!");
            return -1;
        }
        throw new NullPointerException("Context can not be null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (f9702f) {
            Log.i(f9701e, str);
        }
    }

    public String a() {
        Context context = this.f9703a;
        if (context != null) {
            String packageName = context.getPackageName();
            b("liufeng, getAAID package：" + packageName);
            if (packageName != null && !packageName.equals("")) {
                try {
                    com.alipay.sdk.m.q0.a aVar = this.f9704b;
                    if (aVar == null) {
                        return null;
                    }
                    String strA = aVar.a(packageName);
                    return ((strA == null || "".equals(strA)) && this.f9704b.c(packageName)) ? this.f9704b.a(packageName) : strA;
                } catch (RemoteException unused) {
                    a("getAAID error, RemoteException!");
                    return null;
                }
            }
            b("input package is null!");
            return null;
        }
        b("Context is null.");
        throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
    }

    public void a(boolean z2) {
        f9702f = z2;
    }

    private void a(String str) {
        if (f9702f) {
            Log.e(f9701e, str);
        }
    }
}

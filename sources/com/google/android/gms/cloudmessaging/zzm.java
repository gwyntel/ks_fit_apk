package com.google.android.gms.cloudmessaging;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
final class zzm implements ServiceConnection {

    /* renamed from: c, reason: collision with root package name */
    zzn f12673c;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ zzs f12676f;

    /* renamed from: a, reason: collision with root package name */
    int f12671a = 0;

    /* renamed from: b, reason: collision with root package name */
    final Messenger f12672b = new Messenger(new com.google.android.gms.internal.cloudmessaging.zzf(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.gms.cloudmessaging.zzf
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            zzm zzmVar = this.zza;
            int i2 = message.arg1;
            if (Log.isLoggable("MessengerIpcClient", 3)) {
                StringBuilder sb = new StringBuilder(41);
                sb.append("Received response to request: ");
                sb.append(i2);
                Log.d("MessengerIpcClient", sb.toString());
            }
            synchronized (zzmVar) {
                try {
                    zzp zzpVar = (zzp) zzmVar.f12675e.get(i2);
                    if (zzpVar == null) {
                        StringBuilder sb2 = new StringBuilder(50);
                        sb2.append("Received response for unknown request: ");
                        sb2.append(i2);
                        Log.w("MessengerIpcClient", sb2.toString());
                        return true;
                    }
                    zzmVar.f12675e.remove(i2);
                    zzmVar.f();
                    Bundle data = message.getData();
                    if (data.getBoolean("unsupported", false)) {
                        zzpVar.c(new zzq(4, "Not supported by GmsCore", null));
                        return true;
                    }
                    zzpVar.a(data);
                    return true;
                } finally {
                }
            }
        }
    }));

    /* renamed from: d, reason: collision with root package name */
    final Queue f12674d = new ArrayDeque();

    /* renamed from: e, reason: collision with root package name */
    final SparseArray f12675e = new SparseArray();

    /* synthetic */ zzm(zzs zzsVar, zzl zzlVar) {
        this.f12676f = zzsVar;
    }

    final synchronized void a(int i2, String str) {
        b(i2, str, null);
    }

    final synchronized void b(int i2, String str, Throwable th) {
        try {
            if (Log.isLoggable("MessengerIpcClient", 3)) {
                String strValueOf = String.valueOf(str);
                Log.d("MessengerIpcClient", strValueOf.length() != 0 ? "Disconnected: ".concat(strValueOf) : new String("Disconnected: "));
            }
            int i3 = this.f12671a;
            if (i3 == 0) {
                throw new IllegalStateException();
            }
            if (i3 != 1 && i3 != 2) {
                if (i3 != 3) {
                    return;
                }
                this.f12671a = 4;
                return;
            }
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Unbinding service");
            }
            this.f12671a = 4;
            ConnectionTracker.getInstance().unbindService(this.f12676f.zzb, this);
            zzq zzqVar = new zzq(i2, str, th);
            Iterator it = this.f12674d.iterator();
            while (it.hasNext()) {
                ((zzp) it.next()).c(zzqVar);
            }
            this.f12674d.clear();
            for (int i4 = 0; i4 < this.f12675e.size(); i4++) {
                ((zzp) this.f12675e.valueAt(i4)).c(zzqVar);
            }
            this.f12675e.clear();
        } catch (Throwable th2) {
            throw th2;
        }
    }

    final void c() {
        this.f12676f.zzc.execute(new Runnable() { // from class: com.google.android.gms.cloudmessaging.zzh
            @Override // java.lang.Runnable
            public final void run() {
                final zzp zzpVar;
                final zzm zzmVar = this.zza;
                while (true) {
                    synchronized (zzmVar) {
                        try {
                            if (zzmVar.f12671a != 2) {
                                return;
                            }
                            if (zzmVar.f12674d.isEmpty()) {
                                zzmVar.f();
                                return;
                            } else {
                                zzpVar = (zzp) zzmVar.f12674d.poll();
                                zzmVar.f12675e.put(zzpVar.f12677a, zzpVar);
                                zzmVar.f12676f.zzc.schedule(new Runnable() { // from class: com.google.android.gms.cloudmessaging.zzk
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        zzmVar.e(zzpVar.f12677a);
                                    }
                                }, 30L, TimeUnit.SECONDS);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (Log.isLoggable("MessengerIpcClient", 3)) {
                        String strValueOf = String.valueOf(zzpVar);
                        StringBuilder sb = new StringBuilder(strValueOf.length() + 8);
                        sb.append("Sending ");
                        sb.append(strValueOf);
                        Log.d("MessengerIpcClient", sb.toString());
                    }
                    Context context = zzmVar.f12676f.zzb;
                    Messenger messenger = zzmVar.f12672b;
                    Message messageObtain = Message.obtain();
                    messageObtain.what = zzpVar.f12679c;
                    messageObtain.arg1 = zzpVar.f12677a;
                    messageObtain.replyTo = messenger;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("oneWay", zzpVar.b());
                    bundle.putString("pkg", context.getPackageName());
                    bundle.putBundle("data", zzpVar.f12680d);
                    messageObtain.setData(bundle);
                    try {
                        zzmVar.f12673c.a(messageObtain);
                    } catch (RemoteException e2) {
                        zzmVar.a(2, e2.getMessage());
                    }
                }
            }
        });
    }

    final synchronized void d() {
        if (this.f12671a == 1) {
            a(1, "Timed out while binding");
        }
    }

    final synchronized void e(int i2) {
        zzp zzpVar = (zzp) this.f12675e.get(i2);
        if (zzpVar != null) {
            StringBuilder sb = new StringBuilder(31);
            sb.append("Timing out request: ");
            sb.append(i2);
            Log.w("MessengerIpcClient", sb.toString());
            this.f12675e.remove(i2);
            zzpVar.c(new zzq(3, "Timed out waiting for response", null));
            f();
        }
    }

    final synchronized void f() {
        try {
            if (this.f12671a == 2 && this.f12674d.isEmpty() && this.f12675e.size() == 0) {
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
                }
                this.f12671a = 3;
                ConnectionTracker.getInstance().unbindService(this.f12676f.zzb, this);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    final synchronized boolean g(zzp zzpVar) {
        int i2 = this.f12671a;
        if (i2 != 0) {
            if (i2 == 1) {
                this.f12674d.add(zzpVar);
                return true;
            }
            if (i2 != 2) {
                return false;
            }
            this.f12674d.add(zzpVar);
            c();
            return true;
        }
        this.f12674d.add(zzpVar);
        Preconditions.checkState(this.f12671a == 0);
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Starting bind to GmsCore");
        }
        this.f12671a = 1;
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.setPackage("com.google.android.gms");
        try {
            if (ConnectionTracker.getInstance().bindService(this.f12676f.zzb, intent, this, 1)) {
                this.f12676f.zzc.schedule(new Runnable() { // from class: com.google.android.gms.cloudmessaging.zzi
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zza.d();
                    }
                }, 30L, TimeUnit.SECONDS);
            } else {
                a(0, "Unable to bind to service");
            }
        } catch (SecurityException e2) {
            b(0, "Unable to bind to service", e2);
        }
        return true;
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        this.f12676f.zzc.execute(new Runnable() { // from class: com.google.android.gms.cloudmessaging.zzj
            @Override // java.lang.Runnable
            public final void run() {
                zzm zzmVar = this.zza;
                IBinder iBinder2 = iBinder;
                synchronized (zzmVar) {
                    if (iBinder2 == null) {
                        zzmVar.a(0, "Null service connection");
                        return;
                    }
                    try {
                        zzmVar.f12673c = new zzn(iBinder2);
                        zzmVar.f12671a = 2;
                        zzmVar.c();
                    } catch (RemoteException e2) {
                        zzmVar.a(0, e2.getMessage());
                    }
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        this.f12676f.zzc.execute(new Runnable() { // from class: com.google.android.gms.cloudmessaging.zzg
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.a(2, "Service disconnected");
            }
        });
    }
}

package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.util.GlideSuppliers;
import com.bumptech.glide.util.Util;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
final class SingletonConnectivityReceiver {
    private static final String TAG = "ConnectivityMonitor";
    private static volatile SingletonConnectivityReceiver instance;

    /* renamed from: a, reason: collision with root package name */
    final Set f12407a = new HashSet();
    private final FrameworkConnectivityMonitor frameworkConnectivityMonitor;

    @GuardedBy("this")
    private boolean isRegistered;

    private interface FrameworkConnectivityMonitor {
        boolean register();

        void unregister();
    }

    @RequiresApi(24)
    private static final class FrameworkConnectivityMonitorPostApi24 implements FrameworkConnectivityMonitor {

        /* renamed from: a, reason: collision with root package name */
        boolean f12411a;

        /* renamed from: b, reason: collision with root package name */
        final ConnectivityMonitor.ConnectivityListener f12412b;
        private final GlideSuppliers.GlideSupplier<ConnectivityManager> connectivityManager;
        private final ConnectivityManager.NetworkCallback networkCallback = new AnonymousClass1();

        /* renamed from: com.bumptech.glide.manager.SingletonConnectivityReceiver$FrameworkConnectivityMonitorPostApi24$1, reason: invalid class name */
        class AnonymousClass1 extends ConnectivityManager.NetworkCallback {
            AnonymousClass1() {
            }

            private void postOnConnectivityChange(final boolean z2) {
                Util.postOnUiThread(new Runnable() { // from class: com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitorPostApi24.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass1.this.a(z2);
                    }
                });
            }

            void a(boolean z2) {
                Util.assertMainThread();
                FrameworkConnectivityMonitorPostApi24 frameworkConnectivityMonitorPostApi24 = FrameworkConnectivityMonitorPostApi24.this;
                boolean z3 = frameworkConnectivityMonitorPostApi24.f12411a;
                frameworkConnectivityMonitorPostApi24.f12411a = z2;
                if (z3 != z2) {
                    frameworkConnectivityMonitorPostApi24.f12412b.onConnectivityChanged(z2);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(@NonNull Network network) {
                postOnConnectivityChange(true);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(@NonNull Network network) {
                postOnConnectivityChange(false);
            }
        }

        FrameworkConnectivityMonitorPostApi24(GlideSuppliers.GlideSupplier glideSupplier, ConnectivityMonitor.ConnectivityListener connectivityListener) {
            this.connectivityManager = glideSupplier;
            this.f12412b = connectivityListener;
        }

        @Override // com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitor
        @SuppressLint({"MissingPermission"})
        public boolean register() {
            this.f12411a = this.connectivityManager.get().getActiveNetwork() != null;
            try {
                this.connectivityManager.get().registerDefaultNetworkCallback(this.networkCallback);
                return true;
            } catch (RuntimeException e2) {
                if (Log.isLoggable(SingletonConnectivityReceiver.TAG, 5)) {
                    Log.w(SingletonConnectivityReceiver.TAG, "Failed to register callback", e2);
                }
                return false;
            }
        }

        @Override // com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitor
        public void unregister() {
            this.connectivityManager.get().unregisterNetworkCallback(this.networkCallback);
        }
    }

    private static final class FrameworkConnectivityMonitorPreApi24 implements FrameworkConnectivityMonitor {

        /* renamed from: a, reason: collision with root package name */
        final ConnectivityMonitor.ConnectivityListener f12416a;

        /* renamed from: b, reason: collision with root package name */
        boolean f12417b;
        private final GlideSuppliers.GlideSupplier<ConnectivityManager> connectivityManager;
        private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver() { // from class: com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitorPreApi24.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NonNull Context context, Intent intent) {
                FrameworkConnectivityMonitorPreApi24 frameworkConnectivityMonitorPreApi24 = FrameworkConnectivityMonitorPreApi24.this;
                boolean z2 = frameworkConnectivityMonitorPreApi24.f12417b;
                frameworkConnectivityMonitorPreApi24.f12417b = frameworkConnectivityMonitorPreApi24.a();
                if (z2 != FrameworkConnectivityMonitorPreApi24.this.f12417b) {
                    if (Log.isLoggable(SingletonConnectivityReceiver.TAG, 3)) {
                        Log.d(SingletonConnectivityReceiver.TAG, "connectivity changed, isConnected: " + FrameworkConnectivityMonitorPreApi24.this.f12417b);
                    }
                    FrameworkConnectivityMonitorPreApi24 frameworkConnectivityMonitorPreApi242 = FrameworkConnectivityMonitorPreApi24.this;
                    frameworkConnectivityMonitorPreApi242.f12416a.onConnectivityChanged(frameworkConnectivityMonitorPreApi242.f12417b);
                }
            }
        };
        private final Context context;

        FrameworkConnectivityMonitorPreApi24(Context context, GlideSuppliers.GlideSupplier glideSupplier, ConnectivityMonitor.ConnectivityListener connectivityListener) {
            this.context = context.getApplicationContext();
            this.connectivityManager = glideSupplier;
            this.f12416a = connectivityListener;
        }

        boolean a() {
            try {
                NetworkInfo activeNetworkInfo = this.connectivityManager.get().getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            } catch (RuntimeException e2) {
                if (Log.isLoggable(SingletonConnectivityReceiver.TAG, 5)) {
                    Log.w(SingletonConnectivityReceiver.TAG, "Failed to determine connectivity status when connectivity changed", e2);
                }
                return true;
            }
        }

        @Override // com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitor
        public boolean register() {
            this.f12417b = a();
            try {
                this.context.registerReceiver(this.connectivityReceiver, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
                return true;
            } catch (SecurityException e2) {
                if (!Log.isLoggable(SingletonConnectivityReceiver.TAG, 5)) {
                    return false;
                }
                Log.w(SingletonConnectivityReceiver.TAG, "Failed to register", e2);
                return false;
            }
        }

        @Override // com.bumptech.glide.manager.SingletonConnectivityReceiver.FrameworkConnectivityMonitor
        public void unregister() {
            this.context.unregisterReceiver(this.connectivityReceiver);
        }
    }

    private SingletonConnectivityReceiver(@NonNull final Context context) {
        GlideSuppliers.GlideSupplier glideSupplierMemorize = GlideSuppliers.memorize(new GlideSuppliers.GlideSupplier<ConnectivityManager>() { // from class: com.bumptech.glide.manager.SingletonConnectivityReceiver.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bumptech.glide.util.GlideSuppliers.GlideSupplier
            public ConnectivityManager get() {
                return (ConnectivityManager) context.getSystemService("connectivity");
            }
        });
        ConnectivityMonitor.ConnectivityListener connectivityListener = new ConnectivityMonitor.ConnectivityListener() { // from class: com.bumptech.glide.manager.SingletonConnectivityReceiver.2
            @Override // com.bumptech.glide.manager.ConnectivityMonitor.ConnectivityListener
            public void onConnectivityChanged(boolean z2) {
                ArrayList arrayList;
                synchronized (SingletonConnectivityReceiver.this) {
                    arrayList = new ArrayList(SingletonConnectivityReceiver.this.f12407a);
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ConnectivityMonitor.ConnectivityListener) it.next()).onConnectivityChanged(z2);
                }
            }
        };
        this.frameworkConnectivityMonitor = Build.VERSION.SDK_INT >= 24 ? new FrameworkConnectivityMonitorPostApi24(glideSupplierMemorize, connectivityListener) : new FrameworkConnectivityMonitorPreApi24(context, glideSupplierMemorize, connectivityListener);
    }

    static SingletonConnectivityReceiver a(Context context) {
        if (instance == null) {
            synchronized (SingletonConnectivityReceiver.class) {
                try {
                    if (instance == null) {
                        instance = new SingletonConnectivityReceiver(context.getApplicationContext());
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    @GuardedBy("this")
    private void maybeRegisterReceiver() {
        if (this.isRegistered || this.f12407a.isEmpty()) {
            return;
        }
        this.isRegistered = this.frameworkConnectivityMonitor.register();
    }

    @GuardedBy("this")
    private void maybeUnregisterReceiver() {
        if (this.isRegistered && this.f12407a.isEmpty()) {
            this.frameworkConnectivityMonitor.unregister();
            this.isRegistered = false;
        }
    }

    synchronized void b(ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.f12407a.add(connectivityListener);
        maybeRegisterReceiver();
    }

    synchronized void c(ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.f12407a.remove(connectivityListener);
        maybeUnregisterReceiver();
    }
}

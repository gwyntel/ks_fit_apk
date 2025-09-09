package com.bumptech.glide.manager;

import android.content.Context;
import com.bumptech.glide.manager.ConnectivityMonitor;

/* loaded from: classes3.dex */
final class DefaultConnectivityMonitor implements ConnectivityMonitor {

    /* renamed from: a, reason: collision with root package name */
    final ConnectivityMonitor.ConnectivityListener f12403a;
    private final Context context;

    DefaultConnectivityMonitor(Context context, ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.context = context.getApplicationContext();
        this.f12403a = connectivityListener;
    }

    private void register() {
        SingletonConnectivityReceiver.a(this.context).b(this.f12403a);
    }

    private void unregister() {
        SingletonConnectivityReceiver.a(this.context).c(this.f12403a);
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        register();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        unregister();
    }
}

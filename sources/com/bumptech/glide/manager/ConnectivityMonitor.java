package com.bumptech.glide.manager;

/* loaded from: classes3.dex */
public interface ConnectivityMonitor extends LifecycleListener {

    public interface ConnectivityListener {
        void onConnectivityChanged(boolean z2);
    }
}

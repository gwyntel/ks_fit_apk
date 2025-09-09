package com.xiaomi.push;

import android.net.ConnectivityManager;
import android.net.Network;

/* loaded from: classes4.dex */
class bh extends ConnectivityManager.NetworkCallback {
    bh() {
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        super.onAvailable(network);
        bg.b();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        super.onLost(network);
        bg.b();
    }
}

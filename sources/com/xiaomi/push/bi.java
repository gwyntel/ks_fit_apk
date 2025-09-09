package com.xiaomi.push;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
class bi implements Callable<bj> {
    bi() {
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public bj call() {
        NetworkInfo activeNetworkInfo;
        Context contextM684a = C0472r.m684a();
        if (contextM684a == null) {
            return null;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) contextM684a.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return null;
            }
            return new bj(activeNetworkInfo);
        } catch (Exception unused) {
            return null;
        }
    }
}

package com.google.android.gms.cloudmessaging;

import android.os.Bundle;
import android.util.Log;
import com.alipay.sdk.m.u.i;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
abstract class zzp<T> {

    /* renamed from: a, reason: collision with root package name */
    final int f12677a;

    /* renamed from: b, reason: collision with root package name */
    final TaskCompletionSource f12678b = new TaskCompletionSource();

    /* renamed from: c, reason: collision with root package name */
    final int f12679c;

    /* renamed from: d, reason: collision with root package name */
    final Bundle f12680d;

    zzp(int i2, int i3, Bundle bundle) {
        this.f12677a = i2;
        this.f12679c = i3;
        this.f12680d = bundle;
    }

    abstract void a(Bundle bundle);

    abstract boolean b();

    final void c(zzq zzqVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(zzqVar);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 14 + strValueOf2.length());
            sb.append("Failing ");
            sb.append(strValueOf);
            sb.append(" with ");
            sb.append(strValueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.f12678b.setException(zzqVar);
    }

    final void d(Object obj) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(obj);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 16 + strValueOf2.length());
            sb.append("Finishing ");
            sb.append(strValueOf);
            sb.append(" with ");
            sb.append(strValueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.f12678b.setResult(obj);
    }

    public final String toString() {
        int i2 = this.f12679c;
        int i3 = this.f12677a;
        StringBuilder sb = new StringBuilder(55);
        sb.append("Request { what=");
        sb.append(i2);
        sb.append(" id=");
        sb.append(i3);
        sb.append(" oneWay=");
        sb.append(b());
        sb.append(i.f9804d);
        return sb.toString();
    }
}

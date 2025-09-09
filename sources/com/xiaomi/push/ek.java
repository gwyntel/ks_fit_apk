package com.xiaomi.push;

import android.util.Log;
import android.util.Pair;
import java.util.Date;

/* loaded from: classes4.dex */
class ek implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ej f23661a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f331a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Throwable f332a;

    ek(ej ejVar, String str, Throwable th) {
        this.f23661a = ejVar;
        this.f331a = str;
        this.f332a = th;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        ej.f328a.add(new Pair(String.format("%1$s %2$s %3$s ", ej.f327a.format(new Date()), this.f23661a.f23659b, this.f331a), this.f332a));
        if (ej.f328a.size() > 20000) {
            int size = ej.f328a.size() - 19950;
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    if (ej.f328a.size() > 0) {
                        ej.f328a.remove(0);
                    }
                } catch (IndexOutOfBoundsException unused) {
                }
            }
            ej.f328a.add(new Pair(String.format("%1$s %2$s %3$s ", ej.f327a.format(new Date()), this.f23661a.f23659b, "flush " + size + " lines logs."), null));
        }
        try {
            if (z.d()) {
                this.f23661a.m310a();
            } else {
                Log.w(this.f23661a.f23659b, "SDCard is unavailable.");
            }
        } catch (Exception e2) {
            Log.e(this.f23661a.f23659b, "", e2);
        }
    }
}

package com.google.android.gms.common;

import android.util.Log;
import com.google.errorprone.annotations.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
/* loaded from: classes3.dex */
class zzx {
    private static final zzx zze = new zzx(true, 3, 1, null, null);

    /* renamed from: a, reason: collision with root package name */
    final boolean f12907a;

    /* renamed from: b, reason: collision with root package name */
    final String f12908b;

    /* renamed from: c, reason: collision with root package name */
    final Throwable f12909c;

    /* renamed from: d, reason: collision with root package name */
    final int f12910d;

    private zzx(boolean z2, int i2, int i3, @Nullable String str, @Nullable Throwable th) {
        this.f12907a = z2;
        this.f12910d = i2;
        this.f12908b = str;
        this.f12909c = th;
    }

    static zzx b() {
        return zze;
    }

    static zzx c(String str) {
        return new zzx(false, 1, 5, str, null);
    }

    static zzx d(String str, Throwable th) {
        return new zzx(false, 1, 5, str, th);
    }

    static zzx f(int i2) {
        return new zzx(true, i2, 1, null, null);
    }

    static zzx g(int i2, int i3, String str, Throwable th) {
        return new zzx(false, i2, i3, str, th);
    }

    String a() {
        return this.f12908b;
    }

    final void e() {
        if (this.f12907a || !Log.isLoggable("GoogleCertificatesRslt", 3)) {
            return;
        }
        if (this.f12909c != null) {
            Log.d("GoogleCertificatesRslt", a(), this.f12909c);
        } else {
            Log.d("GoogleCertificatesRslt", a());
        }
    }
}

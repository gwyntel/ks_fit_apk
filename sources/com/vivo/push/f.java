package com.vivo.push;

import android.text.TextUtils;
import com.vivo.push.util.z;

/* loaded from: classes4.dex */
final class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f23165a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f23166b;

    f(e eVar, String str) {
        this.f23166b = eVar;
        this.f23165a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f23166b.f23145h == null || TextUtils.isEmpty(this.f23165a) || !z.b(this.f23166b.f23145h, this.f23166b.f23145h.getPackageName(), this.f23165a)) {
            return;
        }
        this.f23166b.i();
    }
}

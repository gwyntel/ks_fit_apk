package com.alipay.apmobilesecuritysdk.f;

import android.os.Process;

/* loaded from: classes2.dex */
public final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b f9085a;

    public c(b bVar) {
        this.f9085a = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Process.setThreadPriority(0);
            while (!this.f9085a.f9084c.isEmpty()) {
                Runnable runnable = (Runnable) this.f9085a.f9084c.get(0);
                this.f9085a.f9084c.remove(0);
                if (runnable != null) {
                    runnable.run();
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            b.b(this.f9085a);
            throw th;
        }
        b.b(this.f9085a);
    }
}

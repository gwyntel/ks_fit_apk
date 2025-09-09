package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zav implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zaaa f12776a;

    zav(zaaa zaaaVar) {
        this.f12776a = zaaaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f12776a.zam.lock();
        try {
            zaaa.j(this.f12776a);
        } finally {
            this.f12776a.zam.unlock();
        }
    }
}

package com.google.android.gms.common.api.internal;

/* loaded from: classes3.dex */
final class zabo implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zabp f12744a;

    zabo(zabp zabpVar) {
        this.f12744a = zabpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zabq zabqVar = this.f12744a.f12745a;
        zabqVar.zac.disconnect(zabqVar.zac.getClass().getName().concat(" disconnecting because it was signed out."));
    }
}

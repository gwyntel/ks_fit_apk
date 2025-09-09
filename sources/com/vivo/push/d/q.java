package com.vivo.push.d;

import com.vivo.push.model.UnvarnishedMessage;

/* loaded from: classes4.dex */
final class q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UnvarnishedMessage f23124a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ p f23125b;

    q(p pVar, UnvarnishedMessage unvarnishedMessage) {
        this.f23125b = pVar;
        this.f23124a = unvarnishedMessage;
    }

    @Override // java.lang.Runnable
    public final void run() {
        p pVar = this.f23125b;
        ((z) pVar).f23137b.onTransmissionMessage(((com.vivo.push.l) pVar).f23178a, this.f23124a);
    }
}

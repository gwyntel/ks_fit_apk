package com.vivo.push.d;

import com.vivo.push.model.UPSNotificationMessage;

/* loaded from: classes4.dex */
final class w implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSNotificationMessage f23133a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ u f23134b;

    w(u uVar, UPSNotificationMessage uPSNotificationMessage) {
        this.f23134b = uVar;
        this.f23133a = uPSNotificationMessage;
    }

    @Override // java.lang.Runnable
    public final void run() {
        u uVar = this.f23134b;
        ((z) uVar).f23137b.onNotificationMessageClicked(((com.vivo.push.l) uVar).f23178a, this.f23133a);
    }
}

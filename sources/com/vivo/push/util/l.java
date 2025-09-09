package com.vivo.push.util;

import java.util.List;

/* loaded from: classes4.dex */
final class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ List f23253a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ k f23254b;

    l(k kVar, List list) {
        this.f23254b = kVar;
        this.f23253a = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f23254b.f23248b != null) {
            w.b().a("com.vivo.push.notify_key", this.f23254b.f23249c);
            NotifyAdapterUtil.pushNotification(this.f23254b.f23247a, this.f23253a, this.f23254b.f23248b, this.f23254b.f23249c, this.f23254b.f23251e, this.f23254b.f23252f);
        }
    }
}

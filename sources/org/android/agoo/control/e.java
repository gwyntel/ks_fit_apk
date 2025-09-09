package org.android.agoo.control;

/* loaded from: classes5.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f26530a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f26531b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ AgooFactory f26532c;

    e(AgooFactory agooFactory, String str, String str2) {
        this.f26532c = agooFactory;
        this.f26530a = str;
        this.f26531b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f26532c.updateMsgStatus(this.f26530a, this.f26531b);
    }
}

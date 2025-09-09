package com.aliyun.alink.linksdk.channel.mobile.b;

import com.aliyun.alink.linksdk.channel.mobile.api.IMobileDownstreamListener;
import com.aliyun.alink.linksdk.channel.mobile.b.d;

/* loaded from: classes2.dex */
class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f11042a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ IMobileDownstreamListener f11043b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ a f11044c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ d.b f11045d;

    j(d.b bVar, String str, IMobileDownstreamListener iMobileDownstreamListener, a aVar) {
        this.f11045d = bVar;
        this.f11042a = str;
        this.f11043b = iMobileDownstreamListener;
        this.f11044c = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strC = this.f11045d.c(this.f11042a);
        IMobileDownstreamListener iMobileDownstreamListener = this.f11043b;
        String strA = this.f11045d.a(this.f11044c.b());
        if (strC == null) {
            strC = this.f11042a;
        }
        iMobileDownstreamListener.onCommand(strA, strC);
    }
}

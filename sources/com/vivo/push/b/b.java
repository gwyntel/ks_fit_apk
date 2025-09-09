package com.vivo.push.b;

import androidx.media3.common.PlaybackException;

/* loaded from: classes4.dex */
public final class b extends c {

    /* renamed from: a, reason: collision with root package name */
    private String f23030a;

    /* renamed from: b, reason: collision with root package name */
    private String f23031b;

    /* renamed from: c, reason: collision with root package name */
    private String f23032c;

    /* renamed from: d, reason: collision with root package name */
    private String f23033d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f23034e;

    public b(boolean z2, String str) {
        super(z2 ? PlaybackException.ERROR_CODE_IO_NO_PERMISSION : PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED, str);
        this.f23034e = false;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("sdk_clients", this.f23030a);
        aVar.a("sdk_version", 323L);
        aVar.a("BaseAppCommand.EXTRA_APPID", this.f23032c);
        aVar.a("BaseAppCommand.EXTRA_APPKEY", this.f23031b);
        aVar.a("PUSH_REGID", this.f23033d);
    }

    public final void d() {
        this.f23032c = null;
    }

    public final void e() {
        this.f23031b = null;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final String toString() {
        return "AppCommand:" + b();
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23030a = aVar.a("sdk_clients");
        this.f23032c = aVar.a("BaseAppCommand.EXTRA_APPID");
        this.f23031b = aVar.a("BaseAppCommand.EXTRA_APPKEY");
        this.f23033d = aVar.a("PUSH_REGID");
    }
}

package com.vivo.push.ups;

import android.content.Context;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

/* loaded from: classes4.dex */
final class a implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSRegisterCallback f23212a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Context f23213b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ VUpsManager f23214c;

    a(VUpsManager vUpsManager, UPSRegisterCallback uPSRegisterCallback, Context context) {
        this.f23214c = vUpsManager;
        this.f23212a = uPSRegisterCallback;
        this.f23213b = context;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f23212a.onResult(new TokenResult(i2, PushClient.getInstance(this.f23213b).getRegId()));
    }
}

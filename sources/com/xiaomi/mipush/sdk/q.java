package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.fd;
import com.xiaomi.push.is;
import com.xiaomi.push.service.az;

/* loaded from: classes4.dex */
class q extends az.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23420a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    q(int i2, String str, Context context) {
        super(i2, str);
        this.f23420a = context;
    }

    @Override // com.xiaomi.push.service.az.a
    protected void onCallback() {
        fd.a(this.f23420a).a(az.a(this.f23420a).a(is.AwakeInfoUploadWaySwitch.a(), 0));
    }
}

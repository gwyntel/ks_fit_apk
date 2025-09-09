package com.xiaomi.mipush.sdk;

import com.xiaomi.push.is;
import com.xiaomi.push.service.az;

/* loaded from: classes4.dex */
class g extends az.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ f f23410a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(f fVar, int i2, String str) {
        super(i2, str);
        this.f23410a = fVar;
    }

    @Override // com.xiaomi.push.service.az.a
    protected void onCallback() {
        boolean zA = az.a(this.f23410a.f147a).a(is.AggregatePushSwitch.a(), true);
        if (this.f23410a.f150a != zA) {
            this.f23410a.f150a = zA;
            i.b(this.f23410a.f147a);
        }
    }
}

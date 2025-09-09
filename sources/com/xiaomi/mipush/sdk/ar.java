package com.xiaomi.mipush.sdk;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.push.bg;
import com.xiaomi.push.service.bm;

/* loaded from: classes4.dex */
class ar extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ao f23386a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ar(ao aoVar, Handler handler) {
        super(handler);
        this.f23386a = aoVar;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        ao aoVar = this.f23386a;
        aoVar.f128a = Integer.valueOf(bm.a(aoVar.f124a).a());
        if (this.f23386a.f128a.intValue() != 0) {
            this.f23386a.f124a.getContentResolver().unregisterContentObserver(this);
            if (bg.b(this.f23386a.f124a)) {
                this.f23386a.m137c();
            }
        }
    }
}

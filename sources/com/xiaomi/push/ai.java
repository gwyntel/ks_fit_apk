package com.xiaomi.push;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class ai extends ah.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ah f23435a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f167a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f168a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ai(ah ahVar, ah.a aVar, boolean z2, String str) {
        super(aVar);
        this.f23435a = ahVar;
        this.f168a = z2;
        this.f167a = str;
    }

    @Override // com.xiaomi.push.ah.b
    void a() {
        super.a();
    }

    @Override // com.xiaomi.push.ah.b
    void b() {
        if (this.f168a) {
            return;
        }
        this.f23435a.f163a.edit().putLong(this.f167a, System.currentTimeMillis()).commit();
    }
}

package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class dk implements hg, ho {

    /* renamed from: a, reason: collision with root package name */
    private Context f23580a;

    public dk(Context context) {
        this.f23580a = context;
    }

    @Override // com.xiaomi.push.ho
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo281a(hs hsVar) {
        return true;
    }

    @Override // com.xiaomi.push.hg
    public void a(hs hsVar) {
        dv.b(this.f23580a);
    }

    @Override // com.xiaomi.push.hg
    public void a(gq gqVar) {
        if (gqVar != null && gqVar.a() == 0 && "PING".equals(gqVar.m444a())) {
            dv.d(this.f23580a);
        } else {
            dv.b(this.f23580a);
        }
    }
}

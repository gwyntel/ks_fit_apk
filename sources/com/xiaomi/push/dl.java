package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class dl implements hg, ho {

    /* renamed from: a, reason: collision with root package name */
    private Context f23581a;

    public dl(Context context) {
        this.f23581a = context;
    }

    @Override // com.xiaomi.push.ho
    /* renamed from: a */
    public boolean mo281a(hs hsVar) {
        return true;
    }

    @Override // com.xiaomi.push.hg
    public void a(hs hsVar) {
        dv.a(this.f23581a);
    }

    @Override // com.xiaomi.push.hg
    public void a(gq gqVar) {
        if (gqVar != null && gqVar.a() == 0 && "PING".equals(gqVar.m444a())) {
            dv.c(this.f23581a);
        } else {
            dv.a(this.f23581a);
        }
    }
}

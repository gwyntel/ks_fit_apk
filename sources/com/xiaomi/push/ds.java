package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class ds implements he {

    /* renamed from: a, reason: collision with root package name */
    private Context f23609a;

    public ds(Context context) {
        this.f23609a = context;
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar) {
    }

    @Override // com.xiaomi.push.he
    public void b(hb hbVar) {
        dm.m282a(this.f23609a);
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, Exception exc) {
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, int i2, Exception exc) {
        dm.a(this.f23609a, hbVar.mo468a(), i2);
    }
}

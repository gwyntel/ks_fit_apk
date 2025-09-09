package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.em;
import com.xiaomi.push.in;
import com.xiaomi.push.ja;
import com.xiaomi.push.jm;

/* loaded from: classes4.dex */
public class r implements em {

    /* renamed from: a, reason: collision with root package name */
    private Context f23421a;

    public r(Context context) {
        this.f23421a = context;
    }

    @Override // com.xiaomi.push.em
    public String a() {
        return b.m140a(this.f23421a).d();
    }

    @Override // com.xiaomi.push.em
    public void a(jm jmVar, in inVar, ja jaVar) {
        ao.a(this.f23421a).a((ao) jmVar, inVar, jaVar);
    }
}

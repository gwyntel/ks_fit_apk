package com.vivo.push.b;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class t extends s {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<String> f23064a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<String> f23065b;

    public t(int i2) {
        super(i2);
        this.f23064a = null;
        this.f23065b = null;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("content", this.f23064a);
        aVar.a("error_msg", this.f23065b);
    }

    public final ArrayList<String> d() {
        return this.f23064a;
    }

    public final List<String> e() {
        return this.f23065b;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnSetTagsCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23064a = aVar.c("content");
        this.f23065b = aVar.c("error_msg");
    }
}

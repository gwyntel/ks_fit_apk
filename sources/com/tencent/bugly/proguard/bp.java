package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes4.dex */
public final class bp extends m implements Cloneable {

    /* renamed from: b, reason: collision with root package name */
    static ArrayList<bo> f21002b;

    /* renamed from: a, reason: collision with root package name */
    public ArrayList<bo> f21003a = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a((Collection) this.f21003a, 0);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        if (f21002b == null) {
            f21002b = new ArrayList<>();
            f21002b.add(new bo());
        }
        this.f21003a = (ArrayList) kVar.a((k) f21002b, 0, true);
    }
}

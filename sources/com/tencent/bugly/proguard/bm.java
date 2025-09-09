package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes4.dex */
public final class bm extends m implements Cloneable {

    /* renamed from: c, reason: collision with root package name */
    static ArrayList<String> f20969c;

    /* renamed from: a, reason: collision with root package name */
    public String f20970a = "";

    /* renamed from: b, reason: collision with root package name */
    public ArrayList<String> f20971b = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f20970a, 0);
        ArrayList<String> arrayList = this.f20971b;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 1);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f20970a = kVar.b(0, true);
        if (f20969c == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            f20969c = arrayList;
            arrayList.add("");
        }
        this.f20971b = (ArrayList) kVar.a((k) f20969c, 1, false);
    }
}

package com.huawei.hms.scankit.p;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class z5 implements g4 {

    /* renamed from: a, reason: collision with root package name */
    private List<g4> f18058a = new ArrayList();

    @Override // com.huawei.hms.scankit.p.g4
    public void a(@NonNull w5 w5Var) {
        Iterator<g4> it = this.f18058a.iterator();
        while (it.hasNext()) {
            it.next().a(w5Var);
        }
    }

    public void a(@NonNull g4 g4Var) {
        if (this.f18058a == null) {
            this.f18058a = new ArrayList();
        }
        this.f18058a.add(g4Var);
    }
}

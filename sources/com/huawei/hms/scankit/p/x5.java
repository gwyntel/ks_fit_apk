package com.huawei.hms.scankit.p;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class x5 implements f4 {

    /* renamed from: a, reason: collision with root package name */
    private List<f4> f17989a = new ArrayList();

    @Override // com.huawei.hms.scankit.p.f4
    public void a(@NonNull w5 w5Var, long j2) {
        Iterator<f4> it = this.f17989a.iterator();
        while (it.hasNext()) {
            it.next().a(w5Var, j2);
        }
    }

    public void a(@NonNull f4 f4Var) {
        if (this.f17989a == null) {
            this.f17989a = new ArrayList();
        }
        this.f17989a.add(f4Var);
    }
}

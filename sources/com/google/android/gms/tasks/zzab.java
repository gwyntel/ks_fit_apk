package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes3.dex */
final class zzab implements Continuation {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Collection f13341a;

    zzab(Collection collection) {
        this.f13341a = collection;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final /* bridge */ /* synthetic */ Object then(@NonNull Task task) throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.f13341a);
        return Tasks.forResult(arrayList);
    }
}

package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zaac implements OnCompleteListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12698a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zaad f12699b;

    zaac(zaad zaadVar, TaskCompletionSource taskCompletionSource) {
        this.f12699b = zaadVar;
        this.f12698a = taskCompletionSource;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task task) {
        this.f12699b.zab.remove(this.f12698a);
    }
}

package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdi implements zzdr {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ListenerHolder f13149a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13150b;

    zzdi(zzdz zzdzVar, ListenerHolder listenerHolder, TaskCompletionSource taskCompletionSource) {
        this.f13149a = listenerHolder;
        this.f13150b = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final ListenerHolder zza() {
        return this.f13149a;
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final void zzb(ListenerHolder listenerHolder) {
        throw new IllegalStateException();
    }

    @Override // com.google.android.gms.internal.identity.zzdr
    public final void zzc() {
        this.f13150b.trySetResult(null);
    }
}

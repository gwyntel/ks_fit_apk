package com.google.android.gms.internal.identity;

import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdn extends zzq {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Object f13156a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13157b;

    zzdn(Object obj, TaskCompletionSource taskCompletionSource) {
        this.f13156a = obj;
        this.f13157b = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zzd(zzl zzlVar) {
        TaskUtil.setResultOrApiException(zzlVar.getStatus(), this.f13156a, this.f13157b);
    }

    @Override // com.google.android.gms.internal.identity.zzr
    public final void zze() {
    }
}

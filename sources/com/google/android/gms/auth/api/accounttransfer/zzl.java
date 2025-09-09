package com.google.android.gms.auth.api.accounttransfer;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.auth.zzap;
import com.google.android.gms.internal.auth.zzau;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
abstract class zzl extends TaskApiCall {

    /* renamed from: a, reason: collision with root package name */
    protected TaskCompletionSource f12630a;

    /* synthetic */ zzl(int i2, zzk zzkVar) {
        super(null, false, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* bridge */ /* synthetic */ void a(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        this.f12630a = taskCompletionSource;
        b((zzau) ((zzap) anyClient).getService());
    }

    protected abstract void b(zzau zzauVar);
}

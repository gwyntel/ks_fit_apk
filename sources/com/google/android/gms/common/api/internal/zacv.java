package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zacv extends TaskApiCall {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskApiCall.Builder f12760a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zacv(TaskApiCall.Builder builder, Feature[] featureArr, boolean z2, int i2) {
        super(featureArr, z2, i2);
        this.f12760a = builder;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final void a(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.f12760a.zaa.accept(anyClient, taskCompletionSource);
    }
}

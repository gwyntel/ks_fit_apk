package com.google.android.gms.internal.auth;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzbn extends zzbd {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12989a;

    zzbn(zzbo zzboVar, TaskCompletionSource taskCompletionSource) {
        this.f12989a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.auth.zzbd, com.google.android.gms.internal.auth.zzbg
    public final void zzc(String str) throws RemoteException {
        TaskUtil.setResultOrApiException(str != null ? Status.RESULT_SUCCESS : new Status(3006), str, this.f12989a);
    }
}

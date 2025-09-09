package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzdg extends zzs {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13146a;

    zzdg(TaskCompletionSource taskCompletionSource) {
        this.f13146a = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.identity.zzt
    public final void zzb(int i2, String[] strArr) {
        TaskUtil.setResultOrApiException(new Status(GeofenceStatusCodes.zza(i2)), this.f13146a);
    }

    @Override // com.google.android.gms.internal.identity.zzt
    public final void zzc(int i2, String[] strArr) {
        TaskUtil.setResultOrApiException(new Status(GeofenceStatusCodes.zza(i2)), this.f13146a);
    }

    @Override // com.google.android.gms.internal.identity.zzt
    public final void zzd(int i2, PendingIntent pendingIntent) {
        TaskUtil.setResultOrApiException(new Status(GeofenceStatusCodes.zza(i2)), this.f13146a);
    }
}

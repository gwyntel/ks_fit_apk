package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
abstract class zza extends zzc {

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12849b;
    public final int zza;

    @Nullable
    public final Bundle zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected zza(BaseGmsClient baseGmsClient, int i2, Bundle bundle) {
        super(baseGmsClient, Boolean.TRUE);
        this.f12849b = baseGmsClient;
        this.zza = i2;
        this.zzb = bundle;
    }

    @Override // com.google.android.gms.common.internal.zzc
    protected final /* bridge */ /* synthetic */ void a(Object obj) {
        if (this.zza != 0) {
            this.f12849b.zzp(1, null);
            Bundle bundle = this.zzb;
            c(new ConnectionResult(this.zza, bundle != null ? (PendingIntent) bundle.getParcelable(BaseGmsClient.KEY_PENDING_INTENT) : null));
        } else {
            if (d()) {
                return;
            }
            this.f12849b.zzp(1, null);
            c(new ConnectionResult(8, null));
        }
    }

    @Override // com.google.android.gms.common.internal.zzc
    protected final void b() {
    }

    protected abstract void c(ConnectionResult connectionResult);

    protected abstract boolean d();
}

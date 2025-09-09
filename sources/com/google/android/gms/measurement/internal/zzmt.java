package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes3.dex */
final class zzmt implements zznf {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzmq f13320a;

    zzmt(zzmq zzmqVar) {
        this.f13320a = zzmqVar;
    }

    @Override // com.google.android.gms.measurement.internal.zznf
    public final void zza(String str, String str2, Bundle bundle) throws IllegalStateException {
        if (!TextUtils.isEmpty(str)) {
            this.f13320a.zzl().zzb(new zzmv(this, str, str2, bundle));
        } else if (this.f13320a.zzm != null) {
            this.f13320a.zzm.zzj().zzg().zza("AppId not known when logging event", str2);
        }
    }
}

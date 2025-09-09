package com.google.android.gms.internal.auth;

import android.accounts.Account;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zzad extends zzah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzae f12979a;

    zzad(zzae zzaeVar) {
        this.f12979a = zzaeVar;
    }

    @Override // com.google.android.gms.internal.auth.zzah, com.google.android.gms.auth.account.zzb
    public final void zzb(@Nullable Account account) {
        this.f12979a.setResult((zzae) new zzai(account != null ? Status.RESULT_SUCCESS : zzal.zza, account));
    }
}

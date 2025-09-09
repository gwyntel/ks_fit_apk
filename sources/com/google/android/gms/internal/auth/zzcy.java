package com.google.android.gms.internal.auth;

import android.util.Base64;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
final class zzcy extends zzdc {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzhy f13004c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcy(zzcz zzczVar, String str, Object obj, boolean z2, zzhy zzhyVar) {
        super(zzczVar, "getTokenRefactor__blocked_packages", obj, true, null);
        this.f13004c = zzhyVar;
    }

    @Override // com.google.android.gms.internal.auth.zzdc
    final Object a(Object obj) {
        try {
            return zzhs.zzp(Base64.decode((String) obj, 3));
        } catch (IOException | IllegalArgumentException unused) {
            Log.e("PhenotypeFlag", "Invalid byte[] value for " + this.f13011b + ": " + ((String) obj));
            return null;
        }
    }
}

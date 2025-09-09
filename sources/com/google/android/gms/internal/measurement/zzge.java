package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
final class zzge implements zzgd {

    @GuardedBy("GservicesLoader.class")
    private static zzge zza;

    @Nullable
    private final Context zzb;

    @Nullable
    private final ContentObserver zzc;

    private zzge() {
        this.zzb = null;
        this.zzc = null;
    }

    static zzge a(Context context) {
        zzge zzgeVar;
        synchronized (zzge.class) {
            try {
                if (zza == null) {
                    zza = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzge(context) : new zzge();
                }
                zzgeVar = zza;
            } catch (Throwable th) {
                throw th;
            }
        }
        return zzgeVar;
    }

    static synchronized void b() {
        Context context;
        try {
            zzge zzgeVar = zza;
            if (zzgeVar != null && (context = zzgeVar.zzb) != null && zzgeVar.zzc != null) {
                context.getContentResolver().unregisterContentObserver(zza.zzc);
            }
            zza = null;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzgd
    @Nullable
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final String zza(final String str) {
        Context context = this.zzb;
        if (context != null && !zzfv.zza(context)) {
            try {
                return (String) zzgc.zza(new zzgf() { // from class: com.google.android.gms.internal.measurement.zzgh
                    @Override // com.google.android.gms.internal.measurement.zzgf
                    public final Object zza() {
                        return this.zza.c(str);
                    }
                });
            } catch (IllegalStateException | NullPointerException | SecurityException e2) {
                Log.e("GservicesLoader", "Unable to read GServices for: " + str, e2);
            }
        }
        return null;
    }

    final /* synthetic */ String c(String str) {
        return zzft.zza(this.zzb.getContentResolver(), str, (String) null);
    }

    private zzge(Context context) {
        this.zzb = context;
        zzgg zzggVar = new zzgg(this, null);
        this.zzc = zzggVar;
        context.getContentResolver().registerContentObserver(zzft.zza, true, zzggVar);
    }
}

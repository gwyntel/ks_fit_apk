package com.google.android.gms.measurement.internal;

import android.net.Uri;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzjx implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ Uri zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ zzjy zze;

    zzjx(zzjy zzjyVar, boolean z2, Uri uri, String str, String str2) {
        this.zze = zzjyVar;
        this.zza = z2;
        this.zzb = uri;
        this.zzc = str;
        this.zzd = str2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, InvocationTargetException {
        zzjy.a(this.zze, this.zza, this.zzb, this.zzc, this.zzd);
    }
}

package com.google.android.gms.measurement.internal;

import androidx.media3.exoplayer.ExoPlayer;

/* loaded from: classes3.dex */
final class zzlz {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzly f13307a;
    private zzmc zzb;

    zzlz(zzly zzlyVar) {
        this.f13307a = zzlyVar;
    }

    final void a() {
        this.f13307a.zzt();
        if (this.zzb != null) {
            this.f13307a.zzc.removeCallbacks(this.zzb);
        }
        this.f13307a.zzk().zzn.zza(false);
        this.f13307a.d(false);
    }

    final void b(long j2) {
        this.zzb = new zzmc(this, this.f13307a.zzb().currentTimeMillis(), j2);
        this.f13307a.zzc.postDelayed(this.zzb, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }
}

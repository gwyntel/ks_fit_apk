package com.google.android.gms.internal.auth;

import android.net.Uri;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzcz {

    /* renamed from: a, reason: collision with root package name */
    final Uri f13005a;

    /* renamed from: b, reason: collision with root package name */
    final String f13006b;

    /* renamed from: c, reason: collision with root package name */
    final String f13007c;

    /* renamed from: d, reason: collision with root package name */
    final boolean f13008d;

    /* renamed from: e, reason: collision with root package name */
    final boolean f13009e;

    private zzcz(String str, Uri uri, String str2, String str3, boolean z2, boolean z3, boolean z4, boolean z5, @Nullable zzdg zzdgVar) {
        this.f13005a = uri;
        this.f13006b = "";
        this.f13007c = "";
        this.f13008d = z2;
        this.f13009e = z4;
    }

    public final zzcz zza() {
        return new zzcz(null, this.f13005a, this.f13006b, this.f13007c, this.f13008d, false, true, false, null);
    }

    public final zzcz zzb() {
        if (this.f13006b.isEmpty()) {
            return new zzcz(null, this.f13005a, this.f13006b, this.f13007c, true, false, this.f13009e, false, null);
        }
        throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
    }

    public final zzdc zzc(String str, double d2) {
        return new zzcx(this, str, Double.valueOf(0.0d), true);
    }

    public final zzdc zzd(String str, long j2) {
        return new zzcv(this, str, Long.valueOf(j2), true);
    }

    public final zzdc zze(String str, boolean z2) {
        return new zzcw(this, str, Boolean.valueOf(z2), true);
    }

    public final zzdc zzf(String str, Object obj, zzhy zzhyVar) {
        return new zzcy(this, "getTokenRefactor__blocked_packages", obj, true, zzhyVar);
    }

    public zzcz(Uri uri) {
        this(null, uri, "", "", false, false, false, false, null);
    }
}

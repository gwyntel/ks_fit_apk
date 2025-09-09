package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzgt {

    /* renamed from: a, reason: collision with root package name */
    final String f13204a;

    /* renamed from: b, reason: collision with root package name */
    final Uri f13205b;

    /* renamed from: c, reason: collision with root package name */
    final String f13206c;

    /* renamed from: d, reason: collision with root package name */
    final String f13207d;

    /* renamed from: e, reason: collision with root package name */
    final boolean f13208e;

    /* renamed from: f, reason: collision with root package name */
    final boolean f13209f;

    /* renamed from: g, reason: collision with root package name */
    final boolean f13210g;

    /* renamed from: h, reason: collision with root package name */
    final zzhg f13211h;
    private final boolean zzi;

    public zzgt(Uri uri) {
        this(null, uri, "", "", false, false, false, false, null);
    }

    public final zzgt zza() {
        return new zzgt(this.f13204a, this.f13205b, this.f13206c, this.f13207d, this.f13208e, this.f13209f, true, this.f13210g, this.f13211h);
    }

    public final zzgt zzb() {
        if (!this.f13206c.isEmpty()) {
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }
        zzhg zzhgVar = this.f13211h;
        if (zzhgVar == null) {
            return new zzgt(this.f13204a, this.f13205b, this.f13206c, this.f13207d, true, this.f13209f, this.zzi, this.f13210g, zzhgVar);
        }
        throw new IllegalStateException("Cannot skip gservices both always and conditionally");
    }

    private zzgt(String str, Uri uri, String str2, String str3, boolean z2, boolean z3, boolean z4, boolean z5, @Nullable zzhg<Context, Boolean> zzhgVar) {
        this.f13204a = str;
        this.f13205b = uri;
        this.f13206c = str2;
        this.f13207d = str3;
        this.f13208e = z2;
        this.f13209f = z3;
        this.zzi = z4;
        this.f13210g = z5;
        this.f13211h = zzhgVar;
    }

    public final zzgl<Double> zza(String str, double d2) {
        return zzgl.b(this, str, Double.valueOf(-3.0d), true);
    }

    public final zzgl<Long> zza(String str, long j2) {
        return zzgl.c(this, str, Long.valueOf(j2), true);
    }

    public final zzgl<String> zza(String str, String str2) {
        return zzgl.d(this, str, str2, true);
    }

    public final zzgl<Boolean> zza(String str, boolean z2) {
        return zzgl.a(this, str, Boolean.valueOf(z2), true);
    }
}

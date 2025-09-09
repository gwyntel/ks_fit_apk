package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzbc {

    /* renamed from: a, reason: collision with root package name */
    final String f13265a;

    /* renamed from: b, reason: collision with root package name */
    final String f13266b;

    /* renamed from: c, reason: collision with root package name */
    final long f13267c;

    /* renamed from: d, reason: collision with root package name */
    final long f13268d;

    /* renamed from: e, reason: collision with root package name */
    final long f13269e;

    /* renamed from: f, reason: collision with root package name */
    final long f13270f;

    /* renamed from: g, reason: collision with root package name */
    final long f13271g;

    /* renamed from: h, reason: collision with root package name */
    final Long f13272h;

    /* renamed from: i, reason: collision with root package name */
    final Long f13273i;

    /* renamed from: j, reason: collision with root package name */
    final Long f13274j;

    /* renamed from: k, reason: collision with root package name */
    final Boolean f13275k;

    zzbc(String str, String str2, long j2, long j3, long j4, long j5, Long l2, Long l3, Long l4, Boolean bool) {
        this(str, str2, 0L, 0L, 0L, j4, 0L, null, null, null, null);
    }

    final zzbc a(long j2) {
        return new zzbc(this.f13265a, this.f13266b, this.f13267c, this.f13268d, this.f13269e, j2, this.f13271g, this.f13272h, this.f13273i, this.f13274j, this.f13275k);
    }

    final zzbc b(long j2, long j3) {
        return new zzbc(this.f13265a, this.f13266b, this.f13267c, this.f13268d, this.f13269e, this.f13270f, j2, Long.valueOf(j3), this.f13273i, this.f13274j, this.f13275k);
    }

    final zzbc c(Long l2, Long l3, Boolean bool) {
        return new zzbc(this.f13265a, this.f13266b, this.f13267c, this.f13268d, this.f13269e, this.f13270f, this.f13271g, this.f13272h, l2, l3, (bool == null || bool.booleanValue()) ? bool : null);
    }

    zzbc(String str, String str2, long j2, long j3, long j4, long j5, long j6, Long l2, Long l3, Long l4, Boolean bool) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j6 >= 0);
        this.f13265a = str;
        this.f13266b = str2;
        this.f13267c = j2;
        this.f13268d = j3;
        this.f13269e = j4;
        this.f13270f = j5;
        this.f13271g = j6;
        this.f13272h = l2;
        this.f13273i = l3;
        this.f13274j = l4;
        this.f13275k = bool;
    }
}

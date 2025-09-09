package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzev;

/* loaded from: classes3.dex */
final /* synthetic */ class zzw {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f13327a;

    /* renamed from: b, reason: collision with root package name */
    static final /* synthetic */ int[] f13328b;

    static {
        int[] iArr = new int[zzev.zzd.zza.values().length];
        f13328b = iArr;
        try {
            iArr[zzev.zzd.zza.LESS_THAN.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f13328b[zzev.zzd.zza.GREATER_THAN.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f13328b[zzev.zzd.zza.EQUAL.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f13328b[zzev.zzd.zza.BETWEEN.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        int[] iArr2 = new int[zzev.zzf.zzb.values().length];
        f13327a = iArr2;
        try {
            iArr2[zzev.zzf.zzb.REGEXP.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f13327a[zzev.zzf.zzb.BEGINS_WITH.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f13327a[zzev.zzf.zzb.ENDS_WITH.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f13327a[zzev.zzf.zzb.PARTIAL.ordinal()] = 4;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f13327a[zzev.zzf.zzb.EXACT.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f13327a[zzev.zzf.zzb.IN_LIST.ordinal()] = 6;
        } catch (NoSuchFieldError unused10) {
        }
    }
}

package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzfa;

/* loaded from: classes3.dex */
final /* synthetic */ class zzgx {

    /* renamed from: a, reason: collision with root package name */
    static final /* synthetic */ int[] f13282a;

    /* renamed from: b, reason: collision with root package name */
    static final /* synthetic */ int[] f13283b;

    static {
        int[] iArr = new int[zzfa.zza.zze.values().length];
        f13283b = iArr;
        try {
            iArr[zzfa.zza.zze.AD_STORAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f13283b[zzfa.zza.zze.ANALYTICS_STORAGE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f13283b[zzfa.zza.zze.AD_USER_DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f13283b[zzfa.zza.zze.AD_PERSONALIZATION.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        int[] iArr2 = new int[com.google.android.gms.internal.measurement.zzs.values().length];
        f13282a = iArr2;
        try {
            iArr2[com.google.android.gms.internal.measurement.zzs.DEBUG.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f13282a[com.google.android.gms.internal.measurement.zzs.ERROR.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f13282a[com.google.android.gms.internal.measurement.zzs.WARN.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f13282a[com.google.android.gms.internal.measurement.zzs.VERBOSE.ordinal()] = 4;
        } catch (NoSuchFieldError unused8) {
        }
    }
}

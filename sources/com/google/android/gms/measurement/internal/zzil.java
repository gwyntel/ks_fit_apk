package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;

@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzil {

    /* renamed from: a, reason: collision with root package name */
    final Context f13287a;

    /* renamed from: b, reason: collision with root package name */
    String f13288b;

    /* renamed from: c, reason: collision with root package name */
    String f13289c;

    /* renamed from: d, reason: collision with root package name */
    String f13290d;

    /* renamed from: e, reason: collision with root package name */
    Boolean f13291e;

    /* renamed from: f, reason: collision with root package name */
    long f13292f;

    /* renamed from: g, reason: collision with root package name */
    com.google.android.gms.internal.measurement.zzdd f13293g;

    /* renamed from: h, reason: collision with root package name */
    boolean f13294h;

    /* renamed from: i, reason: collision with root package name */
    Long f13295i;

    /* renamed from: j, reason: collision with root package name */
    String f13296j;

    @VisibleForTesting
    public zzil(Context context, @Nullable com.google.android.gms.internal.measurement.zzdd zzddVar, @Nullable Long l2) {
        this.f13294h = true;
        Preconditions.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.f13287a = applicationContext;
        this.f13295i = l2;
        if (zzddVar != null) {
            this.f13293g = zzddVar;
            this.f13288b = zzddVar.zzf;
            this.f13289c = zzddVar.zze;
            this.f13290d = zzddVar.zzd;
            this.f13294h = zzddVar.zzc;
            this.f13292f = zzddVar.zzb;
            this.f13296j = zzddVar.zzh;
            Bundle bundle = zzddVar.zzg;
            if (bundle != null) {
                this.f13291e = Boolean.valueOf(bundle.getBoolean("dataCollectionDefaultEnabled", true));
            }
        }
    }
}

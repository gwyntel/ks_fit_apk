package com.google.android.gms.fitness;

import com.google.android.gms.common.Feature;

/* loaded from: classes3.dex */
public final class zzf {
    public static final Feature zza;
    public static final Feature zzb;
    public static final Feature[] zzc;

    static {
        Feature feature = new Feature("temp_data_point_changelogs", 1L);
        zza = feature;
        Feature feature2 = new Feature("temp_session_changelogs", 1L);
        zzb = feature2;
        zzc = new Feature[]{feature, feature2};
    }
}

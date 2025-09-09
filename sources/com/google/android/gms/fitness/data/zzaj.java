package com.google.android.gms.fitness.data;

import androidx.annotation.Nullable;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.kingsmith.miot.KsProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@ShowFirstParty
/* loaded from: classes3.dex */
public final class zzaj {
    public static final double zza;
    public static final double zzb;
    public static final double zzc;
    public static final Set zzd;
    private static final zzaj zze;
    private final Map zzf;
    private final Map zzg;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        zza = 10.0d / timeUnit.toNanos(1L);
        zzb = 2000.0d / TimeUnit.HOURS.toNanos(1L);
        zzc = 100.0d / timeUnit.toNanos(1L);
        zzd = Collections.unmodifiableSet(new HashSet(Arrays.asList("altitude", "duration", "food_item", "meal_type", "repetitions", "resistance", "resistance_type")));
        zze = new zzaj();
    }

    private zzaj() {
        HashMap map = new HashMap();
        map.put("latitude", new zzai(-90.0d, 90.0d, null));
        map.put("longitude", new zzai(-180.0d, 180.0d, null));
        map.put("accuracy", new zzai(0.0d, 10000.0d, null));
        map.put("bpm", new zzai(0.0d, 1000.0d, null));
        map.put("altitude", new zzai(-100000.0d, 100000.0d, null));
        map.put("percentage", new zzai(0.0d, 100.0d, null));
        map.put("confidence", new zzai(0.0d, 100.0d, null));
        map.put("duration", new zzai(0.0d, 9.223372036854776E18d, null));
        map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, new zzai(0.0d, 3.0d, null));
        map.put("weight", new zzai(0.0d, 1000.0d, null));
        map.put(KsProperty.Speed, new zzai(0.0d, 11000.0d, null));
        this.zzg = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put("com.google.step_count.delta", zzd("steps", new zzai(0.0d, zza, null)));
        map2.put("com.google.calories.expended", zzd(Field.NUTRIENT_CALORIES, new zzai(0.0d, zzb, null)));
        map2.put("com.google.distance.delta", zzd("distance", new zzai(0.0d, zzc, null)));
        this.zzf = Collections.unmodifiableMap(map2);
    }

    public static zzaj zzc() {
        return zze;
    }

    private static Map zzd(Object obj, Object obj2) {
        HashMap map = new HashMap();
        map.put(obj, obj2);
        return map;
    }

    @Nullable
    public final zzai zza(String str) {
        return (zzai) this.zzg.get(str);
    }

    @Nullable
    public final zzai zzb(String str, String str2) {
        Map map = (Map) this.zzf.get(str);
        if (map != null) {
            return (zzai) map.get(str2);
        }
        return null;
    }
}

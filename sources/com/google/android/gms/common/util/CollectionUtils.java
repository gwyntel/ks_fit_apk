package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.InlineMe;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@KeepForSdk
/* loaded from: classes3.dex */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    @KeepForSdk
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        if (collection == null) {
            return true;
        }
        return collection.isEmpty();
    }

    @NonNull
    @Deprecated
    @InlineMe(imports = {"java.util.Collections"}, replacement = "Collections.emptyList()")
    @KeepForSdk
    public static <T> List<T> listOf() {
        return Collections.emptyList();
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(@NonNull K k2, @NonNull V v2, @NonNull K k3, @NonNull V v3, @NonNull K k4, @NonNull V v4) {
        Map mapZza = zza(3, false);
        mapZza.put(k2, v2);
        mapZza.put(k3, v3);
        mapZza.put(k4, v4);
        return Collections.unmodifiableMap(mapZza);
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOfKeyValueArrays(@NonNull K[] kArr, @NonNull V[] vArr) {
        int length = kArr.length;
        int length2 = vArr.length;
        if (length != length2) {
            throw new IllegalArgumentException("Key and values array lengths not equal: " + length + " != " + length2);
        }
        if (length == 0) {
            return Collections.emptyMap();
        }
        if (length == 1) {
            return Collections.singletonMap(kArr[0], vArr[0]);
        }
        Map mapZza = zza(length, false);
        for (int i2 = 0; i2 < kArr.length; i2++) {
            mapZza.put(kArr[i2], vArr[i2]);
        }
        return Collections.unmodifiableMap(mapZza);
    }

    @NonNull
    @KeepForSdk
    public static <T> Set<T> mutableSetOfWithSize(int i2) {
        return i2 == 0 ? new ArraySet() : zzb(i2, true);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(@NonNull T t2, @NonNull T t3, @NonNull T t4) {
        Set setZzb = zzb(3, false);
        setZzb.add(t2);
        setZzb.add(t3);
        setZzb.add(t4);
        return Collections.unmodifiableSet(setZzb);
    }

    private static Map zza(int i2, boolean z2) {
        return i2 <= 256 ? new ArrayMap(i2) : new HashMap(i2, 1.0f);
    }

    private static Set zzb(int i2, boolean z2) {
        if (i2 <= (true != z2 ? 256 : 128)) {
            return new ArraySet(i2);
        }
        return new HashSet(i2, true != z2 ? 1.0f : 0.75f);
    }

    @NonNull
    @Deprecated
    @InlineMe(imports = {"java.util.Collections"}, replacement = "Collections.singletonList(item)")
    @KeepForSdk
    public static <T> List<T> listOf(@NonNull T t2) {
        return Collections.singletonList(t2);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> List<T> listOf(@NonNull T... tArr) {
        int length = tArr.length;
        if (length == 0) {
            return Collections.emptyList();
        }
        if (length != 1) {
            return Collections.unmodifiableList(Arrays.asList(tArr));
        }
        return Collections.singletonList(tArr[0]);
    }

    @NonNull
    @KeepForSdk
    public static <K, V> Map<K, V> mapOf(@NonNull K k2, @NonNull V v2, @NonNull K k3, @NonNull V v3, @NonNull K k4, @NonNull V v4, @NonNull K k5, @NonNull V v5, @NonNull K k6, @NonNull V v6, @NonNull K k7, @NonNull V v7) {
        Map mapZza = zza(6, false);
        mapZza.put(k2, v2);
        mapZza.put(k3, v3);
        mapZza.put(k4, v4);
        mapZza.put(k5, v5);
        mapZza.put(k6, v6);
        mapZza.put(k7, v7);
        return Collections.unmodifiableMap(mapZza);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static <T> Set<T> setOf(@NonNull T... tArr) {
        int length = tArr.length;
        if (length == 0) {
            return Collections.emptySet();
        }
        if (length == 1) {
            return Collections.singleton(tArr[0]);
        }
        if (length == 2) {
            T t2 = tArr[0];
            T t3 = tArr[1];
            Set setZzb = zzb(2, false);
            setZzb.add(t2);
            setZzb.add(t3);
            return Collections.unmodifiableSet(setZzb);
        }
        if (length == 3) {
            return setOf(tArr[0], tArr[1], tArr[2]);
        }
        if (length != 4) {
            Set setZzb2 = zzb(length, false);
            Collections.addAll(setZzb2, tArr);
            return Collections.unmodifiableSet(setZzb2);
        }
        T t4 = tArr[0];
        T t5 = tArr[1];
        T t6 = tArr[2];
        T t7 = tArr[3];
        Set setZzb3 = zzb(4, false);
        setZzb3.add(t4);
        setZzb3.add(t5);
        setZzb3.add(t6);
        setZzb3.add(t7);
        return Collections.unmodifiableSet(setZzb3);
    }
}

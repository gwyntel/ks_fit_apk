package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    private final HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap<>();

    @Override // androidx.arch.core.internal.SafeIterableMap
    protected SafeIterableMap.Entry a(Object obj) {
        return this.mHashMap.get(obj);
    }

    @Nullable
    public Map.Entry<K, V> ceil(K k2) {
        if (contains(k2)) {
            return this.mHashMap.get(k2).f2368d;
        }
        return null;
    }

    public boolean contains(K k2) {
        return this.mHashMap.containsKey(k2);
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V putIfAbsent(@NonNull K k2, @NonNull V v2) {
        SafeIterableMap.Entry entryA = a(k2);
        if (entryA != null) {
            return (V) entryA.f2366b;
        }
        this.mHashMap.put(k2, b(k2, v2));
        return null;
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V remove(@NonNull K k2) {
        V v2 = (V) super.remove(k2);
        this.mHashMap.remove(k2);
        return v2;
    }
}

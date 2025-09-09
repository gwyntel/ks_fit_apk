package anet.channel.strategy.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class SerialLruCache<K, V> extends LinkedHashMap<K, V> {

    /* renamed from: a, reason: collision with root package name */
    private int f7057a;

    public SerialLruCache(LinkedHashMap<K, V> linkedHashMap, int i2) {
        super(linkedHashMap);
        this.f7057a = i2;
    }

    public boolean entryRemoved(Map.Entry<K, V> entry) {
        return true;
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        if (size() > this.f7057a) {
            return entryRemoved(entry);
        }
        return false;
    }

    @Deprecated
    public SerialLruCache(LinkedHashMap<K, V> linkedHashMap) {
        this(linkedHashMap, 256);
    }

    public SerialLruCache(int i2) {
        super(i2 + 1, 1.0f, true);
        this.f7057a = i2;
    }
}

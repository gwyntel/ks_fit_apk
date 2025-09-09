package com.aliyun.alink.business.devicecenter.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class MapUtils<K, V> {
    public static final int MAP_TYPE_CON_HASH_MAP = 0;
    public static final int MAP_TYPE_HASH_MAP = 2;
    public static final int MAP_TYPE_TREE_MAP = 1;

    /* renamed from: a, reason: collision with root package name */
    public Map<K, V> f10642a;

    public MapUtils() {
        this.f10642a = null;
        this.f10642a = new ConcurrentHashMap();
    }

    public MapUtils<K, V> addKV(K k2, V v2) {
        this.f10642a.put(k2, v2);
        return this;
    }

    public MapUtils<K, V> addKVNoN(K k2, V v2) {
        if (v2 != null) {
            this.f10642a.put(k2, v2);
        }
        return this;
    }

    public MapUtils<K, V> addKVs(Map<K, V> map) {
        if (map == null) {
            return this;
        }
        this.f10642a.putAll(map);
        return this;
    }

    public Map<K, V> build() {
        return this.f10642a;
    }

    public void clear() {
        Map<K, V> map = this.f10642a;
        if (map != null) {
            map.clear();
        }
    }

    public MapUtils(int i2) {
        this.f10642a = null;
        if (i2 == 2) {
            this.f10642a = new HashMap();
        } else if (i2 == 1) {
            this.f10642a = new TreeMap();
        } else {
            this.f10642a = new ConcurrentHashMap();
        }
    }
}

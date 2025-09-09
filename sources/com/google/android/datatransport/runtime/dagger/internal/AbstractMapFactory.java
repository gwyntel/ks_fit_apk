package com.google.android.datatransport.runtime.dagger.internal;

import com.kingsmith.miot.KsProperty;
import com.umeng.analytics.pro.f;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: classes3.dex */
abstract class AbstractMapFactory<K, V, V2> implements Factory<Map<K, V2>> {
    private final Map<K, Provider<V>> contributingMap;

    public static abstract class Builder<K, V, V2> {

        /* renamed from: a, reason: collision with root package name */
        final LinkedHashMap f12528a;

        Builder(int i2) {
            this.f12528a = DaggerCollections.newLinkedHashMapWithExpectedSize(i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        Builder put(Object obj, Provider provider) {
            this.f12528a.put(Preconditions.checkNotNull(obj, KsProperty.Key), Preconditions.checkNotNull(provider, f.M));
            return this;
        }

        Builder putAll(Provider provider) {
            if (provider instanceof DelegateFactory) {
                return putAll(((DelegateFactory) provider).a());
            }
            this.f12528a.putAll(((AbstractMapFactory) provider).contributingMap);
            return this;
        }
    }

    AbstractMapFactory(Map map) {
        this.contributingMap = Collections.unmodifiableMap(map);
    }

    final Map b() {
        return this.contributingMap;
    }
}

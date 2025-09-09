package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMakerInternalMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@J2ktIncompatible
@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class MapMaker {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /* renamed from: a, reason: collision with root package name */
    boolean f14134a;

    /* renamed from: b, reason: collision with root package name */
    int f14135b = -1;

    /* renamed from: c, reason: collision with root package name */
    int f14136c = -1;

    /* renamed from: d, reason: collision with root package name */
    MapMakerInternalMap.Strength f14137d;

    /* renamed from: e, reason: collision with root package name */
    MapMakerInternalMap.Strength f14138e;

    /* renamed from: f, reason: collision with root package name */
    Equivalence f14139f;

    enum Dummy {
        VALUE
    }

    int a() {
        int i2 = this.f14136c;
        if (i2 == -1) {
            return 4;
        }
        return i2;
    }

    int b() {
        int i2 = this.f14135b;
        if (i2 == -1) {
            return 16;
        }
        return i2;
    }

    Equivalence c() {
        return (Equivalence) MoreObjects.firstNonNull(this.f14139f, d().defaultEquivalence());
    }

    @CanIgnoreReturnValue
    public MapMaker concurrencyLevel(int i2) {
        int i3 = this.f14136c;
        Preconditions.checkState(i3 == -1, "concurrency level was already set to %s", i3);
        Preconditions.checkArgument(i2 > 0);
        this.f14136c = i2;
        return this;
    }

    MapMakerInternalMap.Strength d() {
        return (MapMakerInternalMap.Strength) MoreObjects.firstNonNull(this.f14137d, MapMakerInternalMap.Strength.STRONG);
    }

    MapMakerInternalMap.Strength e() {
        return (MapMakerInternalMap.Strength) MoreObjects.firstNonNull(this.f14138e, MapMakerInternalMap.Strength.STRONG);
    }

    MapMaker f(Equivalence equivalence) {
        Equivalence equivalence2 = this.f14139f;
        Preconditions.checkState(equivalence2 == null, "key equivalence was already set to %s", equivalence2);
        this.f14139f = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.f14134a = true;
        return this;
    }

    MapMaker g(MapMakerInternalMap.Strength strength) {
        MapMakerInternalMap.Strength strength2 = this.f14137d;
        Preconditions.checkState(strength2 == null, "Key strength was already set to %s", strength2);
        this.f14137d = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(strength);
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.f14134a = true;
        }
        return this;
    }

    MapMaker h(MapMakerInternalMap.Strength strength) {
        MapMakerInternalMap.Strength strength2 = this.f14138e;
        Preconditions.checkState(strength2 == null, "Value strength was already set to %s", strength2);
        this.f14138e = (MapMakerInternalMap.Strength) Preconditions.checkNotNull(strength);
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.f14134a = true;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public MapMaker initialCapacity(int i2) {
        int i3 = this.f14135b;
        Preconditions.checkState(i3 == -1, "initial capacity was already set to %s", i3);
        Preconditions.checkArgument(i2 >= 0);
        this.f14135b = i2;
        return this;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        return !this.f14134a ? new ConcurrentHashMap(b(), 0.75f, a()) : MapMakerInternalMap.create(this);
    }

    public String toString() {
        MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
        int i2 = this.f14135b;
        if (i2 != -1) {
            stringHelper.add("initialCapacity", i2);
        }
        int i3 = this.f14136c;
        if (i3 != -1) {
            stringHelper.add("concurrencyLevel", i3);
        }
        MapMakerInternalMap.Strength strength = this.f14137d;
        if (strength != null) {
            stringHelper.add("keyStrength", Ascii.toLowerCase(strength.toString()));
        }
        MapMakerInternalMap.Strength strength2 = this.f14138e;
        if (strength2 != null) {
            stringHelper.add("valueStrength", Ascii.toLowerCase(strength2.toString()));
        }
        if (this.f14139f != null) {
            stringHelper.addValue("keyEquivalence");
        }
        return stringHelper.toString();
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public MapMaker weakKeys() {
        return g(MapMakerInternalMap.Strength.WEAK);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public MapMaker weakValues() {
        return h(MapMakerInternalMap.Strength.WEAK);
    }
}

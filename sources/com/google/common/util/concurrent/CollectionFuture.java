package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AggregateFuture;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {

    @CheckForNull
    @LazyInit
    private List<Present<V>> values;

    static final class ListFuture<V> extends CollectionFuture<V, List<V>> {
        ListFuture(ImmutableCollection immutableCollection, boolean z2) {
            super(immutableCollection, z2);
            A();
        }

        @Override // com.google.common.util.concurrent.CollectionFuture
        public List<V> combine(List<Present<V>> list) {
            ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(list.size());
            Iterator<Present<V>> it = list.iterator();
            while (it.hasNext()) {
                Present<V> next = it.next();
                arrayListNewArrayListWithCapacity.add(next != null ? next.f14852a : null);
            }
            return Collections.unmodifiableList(arrayListNewArrayListWithCapacity);
        }
    }

    private static final class Present<V> {

        /* renamed from: a, reason: collision with root package name */
        final Object f14852a;

        Present(Object obj) {
            this.f14852a = obj;
        }
    }

    CollectionFuture(ImmutableCollection immutableCollection, boolean z2) {
        super(immutableCollection, z2, true);
        List<Present<V>> listEmptyList = immutableCollection.isEmpty() ? Collections.emptyList() : Lists.newArrayListWithCapacity(immutableCollection.size());
        for (int i2 = 0; i2 < immutableCollection.size(); i2++) {
            listEmptyList.add(null);
        }
        this.values = listEmptyList;
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    void B(AggregateFuture.ReleaseResourcesReason releaseResourcesReason) {
        super.B(releaseResourcesReason);
        this.values = null;
    }

    abstract Object combine(List list);

    @Override // com.google.common.util.concurrent.AggregateFuture
    final void y(int i2, Object obj) {
        List<Present<V>> list = this.values;
        if (list != null) {
            list.set(i2, new Present<>(obj));
        }
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    final void z() {
        List<Present<V>> list = this.values;
        if (list != null) {
            set(combine(list));
        }
    }
}

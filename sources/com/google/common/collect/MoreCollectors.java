package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.MoreCollectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import kotlin.text.Typography;

@GwtCompatible
@ElementTypesAreNonnullByDefault
@IgnoreJRERequirement
/* loaded from: classes3.dex */
public final class MoreCollectors {
    private static final Collector<Object, ?, Optional<Object>> TO_OPTIONAL = Collector.of(new Supplier() { // from class: com.google.common.collect.d3
        @Override // java.util.function.Supplier
        public final Object get() {
            return new MoreCollectors.ToOptionalState();
        }
    }, new BiConsumer() { // from class: com.google.common.collect.e3
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((MoreCollectors.ToOptionalState) obj).a(obj2);
        }
    }, new BinaryOperator() { // from class: com.google.common.collect.f3
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((MoreCollectors.ToOptionalState) obj).b((MoreCollectors.ToOptionalState) obj2);
        }
    }, new Function() { // from class: com.google.common.collect.g3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((MoreCollectors.ToOptionalState) obj).d();
        }
    }, Collector.Characteristics.UNORDERED);
    private static final Object NULL_PLACEHOLDER = new Object();
    private static final Collector<Object, ?, Object> ONLY_ELEMENT = Collector.of(new Supplier() { // from class: com.google.common.collect.d3
        @Override // java.util.function.Supplier
        public final Object get() {
            return new MoreCollectors.ToOptionalState();
        }
    }, new BiConsumer() { // from class: com.google.common.collect.h3
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            MoreCollectors.lambda$static$0((MoreCollectors.ToOptionalState) obj, obj2);
        }
    }, new BinaryOperator() { // from class: com.google.common.collect.f3
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((MoreCollectors.ToOptionalState) obj).b((MoreCollectors.ToOptionalState) obj2);
        }
    }, new Function() { // from class: com.google.common.collect.i3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return MoreCollectors.lambda$static$1((MoreCollectors.ToOptionalState) obj);
        }
    }, Collector.Characteristics.UNORDERED);

    /* JADX INFO: Access modifiers changed from: private */
    static final class ToOptionalState {

        /* renamed from: a, reason: collision with root package name */
        Object f14208a = null;

        /* renamed from: b, reason: collision with root package name */
        List f14209b = Collections.emptyList();

        ToOptionalState() {
        }

        void a(Object obj) {
            Preconditions.checkNotNull(obj);
            if (this.f14208a == null) {
                this.f14208a = obj;
                return;
            }
            if (this.f14209b.isEmpty()) {
                ArrayList arrayList = new ArrayList(4);
                this.f14209b = arrayList;
                arrayList.add(obj);
            } else {
                if (this.f14209b.size() >= 4) {
                    throw e(true);
                }
                this.f14209b.add(obj);
            }
        }

        ToOptionalState b(ToOptionalState toOptionalState) {
            if (this.f14208a == null) {
                return toOptionalState;
            }
            if (toOptionalState.f14208a == null) {
                return this;
            }
            if (this.f14209b.isEmpty()) {
                this.f14209b = new ArrayList();
            }
            this.f14209b.add(toOptionalState.f14208a);
            this.f14209b.addAll(toOptionalState.f14209b);
            if (this.f14209b.size() <= 4) {
                return this;
            }
            List list = this.f14209b;
            list.subList(4, list.size()).clear();
            throw e(true);
        }

        Object c() {
            if (this.f14208a == null) {
                throw new NoSuchElementException();
            }
            if (this.f14209b.isEmpty()) {
                return this.f14208a;
            }
            throw e(false);
        }

        Optional d() {
            if (this.f14209b.isEmpty()) {
                return Optional.ofNullable(this.f14208a);
            }
            throw e(false);
        }

        IllegalArgumentException e(boolean z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("expected one element but was: <");
            sb.append(this.f14208a);
            for (Object obj : this.f14209b) {
                sb.append(", ");
                sb.append(obj);
            }
            if (z2) {
                sb.append(", ...");
            }
            sb.append(Typography.greater);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private MoreCollectors() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(ToOptionalState toOptionalState, Object obj) {
        if (obj == null) {
            obj = NULL_PLACEHOLDER;
        }
        toOptionalState.a(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$static$1(ToOptionalState toOptionalState) {
        Object objC = toOptionalState.c();
        if (objC == NULL_PLACEHOLDER) {
            return null;
        }
        return objC;
    }

    public static <T> Collector<T, ?, T> onlyElement() {
        return (Collector<T, ?, T>) ONLY_ELEMENT;
    }

    public static <T> Collector<T, ?, Optional<T>> toOptional() {
        return (Collector<T, ?, Optional<T>>) TO_OPTIONAL;
    }
}

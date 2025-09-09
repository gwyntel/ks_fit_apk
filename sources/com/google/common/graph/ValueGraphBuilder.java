package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.ImmutableValueGraph;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ValueGraphBuilder<N, V> extends AbstractGraphBuilder<N> {
    private ValueGraphBuilder(boolean z2) {
        super(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N, V1 extends V> ValueGraphBuilder<N1, V1> cast() {
        return this;
    }

    public static ValueGraphBuilder<Object, Object> directed() {
        return new ValueGraphBuilder<>(true);
    }

    public static <N, V> ValueGraphBuilder<N, V> from(ValueGraph<N, V> valueGraph) {
        return new ValueGraphBuilder(valueGraph.isDirected()).allowsSelfLoops(valueGraph.allowsSelfLoops()).nodeOrder(valueGraph.nodeOrder()).incidentEdgeOrder(valueGraph.incidentEdgeOrder());
    }

    public static ValueGraphBuilder<Object, Object> undirected() {
        return new ValueGraphBuilder<>(false);
    }

    ValueGraphBuilder a() {
        ValueGraphBuilder valueGraphBuilder = new ValueGraphBuilder(this.f14494a);
        valueGraphBuilder.f14495b = this.f14495b;
        valueGraphBuilder.f14496c = this.f14496c;
        valueGraphBuilder.f14498e = this.f14498e;
        valueGraphBuilder.f14497d = this.f14497d;
        return valueGraphBuilder;
    }

    @CanIgnoreReturnValue
    public ValueGraphBuilder<N, V> allowsSelfLoops(boolean z2) {
        this.f14495b = z2;
        return this;
    }

    public <N1 extends N, V1 extends V> MutableValueGraph<N1, V1> build() {
        return new StandardMutableValueGraph(this);
    }

    @CanIgnoreReturnValue
    public ValueGraphBuilder<N, V> expectedNodeCount(int i2) {
        this.f14498e = Optional.of(Integer.valueOf(Graphs.a(i2)));
        return this;
    }

    public <N1 extends N, V1 extends V> ImmutableValueGraph.Builder<N1, V1> immutable() {
        return new ImmutableValueGraph.Builder<>(cast());
    }

    public <N1 extends N> ValueGraphBuilder<N1, V> incidentEdgeOrder(ElementOrder<N1> elementOrder) {
        Preconditions.checkArgument(elementOrder.type() == ElementOrder.Type.UNORDERED || elementOrder.type() == ElementOrder.Type.STABLE, "The given elementOrder (%s) is unsupported. incidentEdgeOrder() only supports ElementOrder.unordered() and ElementOrder.stable().", elementOrder);
        ValueGraphBuilder<N1, V> valueGraphBuilder = (ValueGraphBuilder<N1, V>) cast();
        valueGraphBuilder.f14497d = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return valueGraphBuilder;
    }

    public <N1 extends N> ValueGraphBuilder<N1, V> nodeOrder(ElementOrder<N1> elementOrder) {
        ValueGraphBuilder<N1, V> valueGraphBuilder = (ValueGraphBuilder<N1, V>) cast();
        valueGraphBuilder.f14496c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return valueGraphBuilder;
    }
}

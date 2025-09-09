package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.ImmutableGraph;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock
@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class GraphBuilder<N> extends AbstractGraphBuilder<N> {
    private GraphBuilder(boolean z2) {
        super(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N> GraphBuilder<N1> cast() {
        return this;
    }

    public static GraphBuilder<Object> directed() {
        return new GraphBuilder<>(true);
    }

    public static <N> GraphBuilder<N> from(Graph<N> graph) {
        return new GraphBuilder(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder()).incidentEdgeOrder(graph.incidentEdgeOrder());
    }

    public static GraphBuilder<Object> undirected() {
        return new GraphBuilder<>(false);
    }

    GraphBuilder a() {
        GraphBuilder graphBuilder = new GraphBuilder(this.f14494a);
        graphBuilder.f14495b = this.f14495b;
        graphBuilder.f14496c = this.f14496c;
        graphBuilder.f14498e = this.f14498e;
        graphBuilder.f14497d = this.f14497d;
        return graphBuilder;
    }

    @CanIgnoreReturnValue
    public GraphBuilder<N> allowsSelfLoops(boolean z2) {
        this.f14495b = z2;
        return this;
    }

    public <N1 extends N> MutableGraph<N1> build() {
        return new StandardMutableGraph(this);
    }

    @CanIgnoreReturnValue
    public GraphBuilder<N> expectedNodeCount(int i2) {
        this.f14498e = Optional.of(Integer.valueOf(Graphs.a(i2)));
        return this;
    }

    public <N1 extends N> ImmutableGraph.Builder<N1> immutable() {
        return new ImmutableGraph.Builder<>(cast());
    }

    public <N1 extends N> GraphBuilder<N1> incidentEdgeOrder(ElementOrder<N1> elementOrder) {
        Preconditions.checkArgument(elementOrder.type() == ElementOrder.Type.UNORDERED || elementOrder.type() == ElementOrder.Type.STABLE, "The given elementOrder (%s) is unsupported. incidentEdgeOrder() only supports ElementOrder.unordered() and ElementOrder.stable().", elementOrder);
        GraphBuilder<N1> graphBuilderCast = cast();
        graphBuilderCast.f14497d = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return graphBuilderCast;
    }

    public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> elementOrder) {
        GraphBuilder<N1> graphBuilderCast = cast();
        graphBuilderCast.f14496c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return graphBuilderCast;
    }
}

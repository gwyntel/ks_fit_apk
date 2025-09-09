package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.ImmutableNetwork;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class NetworkBuilder<N, E> extends AbstractGraphBuilder<N> {

    /* renamed from: f, reason: collision with root package name */
    boolean f14541f;

    /* renamed from: g, reason: collision with root package name */
    ElementOrder f14542g;

    /* renamed from: h, reason: collision with root package name */
    Optional f14543h;

    private NetworkBuilder(boolean z2) {
        super(z2);
        this.f14541f = false;
        this.f14542g = ElementOrder.insertion();
        this.f14543h = Optional.absent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N, E1 extends E> NetworkBuilder<N1, E1> cast() {
        return this;
    }

    public static NetworkBuilder<Object, Object> directed() {
        return new NetworkBuilder<>(true);
    }

    public static <N, E> NetworkBuilder<N, E> from(Network<N, E> network) {
        return new NetworkBuilder(network.isDirected()).allowsParallelEdges(network.allowsParallelEdges()).allowsSelfLoops(network.allowsSelfLoops()).nodeOrder(network.nodeOrder()).edgeOrder(network.edgeOrder());
    }

    public static NetworkBuilder<Object, Object> undirected() {
        return new NetworkBuilder<>(false);
    }

    @CanIgnoreReturnValue
    public NetworkBuilder<N, E> allowsParallelEdges(boolean z2) {
        this.f14541f = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public NetworkBuilder<N, E> allowsSelfLoops(boolean z2) {
        this.f14495b = z2;
        return this;
    }

    public <N1 extends N, E1 extends E> MutableNetwork<N1, E1> build() {
        return new StandardMutableNetwork(this);
    }

    public <E1 extends E> NetworkBuilder<N, E1> edgeOrder(ElementOrder<E1> elementOrder) {
        NetworkBuilder<N, E1> networkBuilder = (NetworkBuilder<N, E1>) cast();
        networkBuilder.f14542g = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return networkBuilder;
    }

    @CanIgnoreReturnValue
    public NetworkBuilder<N, E> expectedEdgeCount(int i2) {
        this.f14543h = Optional.of(Integer.valueOf(Graphs.a(i2)));
        return this;
    }

    @CanIgnoreReturnValue
    public NetworkBuilder<N, E> expectedNodeCount(int i2) {
        this.f14498e = Optional.of(Integer.valueOf(Graphs.a(i2)));
        return this;
    }

    public <N1 extends N, E1 extends E> ImmutableNetwork.Builder<N1, E1> immutable() {
        return new ImmutableNetwork.Builder<>(cast());
    }

    public <N1 extends N> NetworkBuilder<N1, E> nodeOrder(ElementOrder<N1> elementOrder) {
        NetworkBuilder<N1, E> networkBuilder = (NetworkBuilder<N1, E>) cast();
        networkBuilder.f14496c = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return networkBuilder;
    }
}

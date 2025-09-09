package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.CheckForNull;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface MutableValueGraph<N, V> extends ValueGraph<N, V> {
    @CanIgnoreReturnValue
    boolean addNode(N n2);

    @CanIgnoreReturnValue
    @CheckForNull
    V putEdgeValue(EndpointPair<N> endpointPair, V v2);

    @CanIgnoreReturnValue
    @CheckForNull
    V putEdgeValue(N n2, N n3, V v2);

    @CanIgnoreReturnValue
    @CheckForNull
    V removeEdge(EndpointPair<N> endpointPair);

    @CanIgnoreReturnValue
    @CheckForNull
    V removeEdge(N n2, N n3);

    @CanIgnoreReturnValue
    boolean removeNode(N n2);
}

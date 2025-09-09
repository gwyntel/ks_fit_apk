package com.google.common.graph;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
interface NetworkConnections<N, E> {
    void addInEdge(E e2, N n2, boolean z2);

    void addOutEdge(E e2, N n2);

    N adjacentNode(E e2);

    Set<N> adjacentNodes();

    Set<E> edgesConnecting(N n2);

    Set<E> inEdges();

    Set<E> incidentEdges();

    Set<E> outEdges();

    Set<N> predecessors();

    @CanIgnoreReturnValue
    @CheckForNull
    N removeInEdge(E e2, boolean z2);

    @CanIgnoreReturnValue
    N removeOutEdge(E e2);

    Set<N> successors();
}

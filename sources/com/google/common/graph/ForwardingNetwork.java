package com.google.common.graph;

import java.util.Set;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class ForwardingNetwork<N, E> extends AbstractNetwork<N, E> {
    ForwardingNetwork() {
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> adjacentEdges(E e2) {
        return l().adjacentEdges(e2);
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n2) {
        return l().adjacentNodes(n2);
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return l().allowsParallelEdges();
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return l().allowsSelfLoops();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int degree(N n2) {
        return l().degree(n2);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    @CheckForNull
    public E edgeConnectingOrNull(N n2, N n3) {
        return (E) l().edgeConnectingOrNull(n2, n3);
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return l().edgeOrder();
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return l().edges();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n2, N n3) {
        return l().edgesConnecting(n2, n3);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public boolean hasEdgeConnecting(N n2, N n3) {
        return l().hasEdgeConnecting(n2, n3);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int inDegree(N n2) {
        return l().inDegree(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n2) {
        return l().inEdges(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n2) {
        return l().incidentEdges(n2);
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e2) {
        return l().incidentNodes(e2);
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return l().isDirected();
    }

    abstract Network l();

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return l().nodeOrder();
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return l().nodes();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int outDegree(N n2) {
        return l().outDegree(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n2) {
        return l().outEdges(n2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((ForwardingNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((ForwardingNetwork<N, E>) obj);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    @CheckForNull
    public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
        return (E) l().edgeConnectingOrNull(endpointPair);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
        return l().edgesConnecting(endpointPair);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        return l().hasEdgeConnecting(endpointPair);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return l().predecessors((Network) n2);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return l().successors((Network) n2);
    }
}
